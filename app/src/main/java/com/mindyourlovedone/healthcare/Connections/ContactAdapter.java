package com.mindyourlovedone.healthcare.Connections;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Contact;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */
/**
 * Class: ContactAdapter
 * Screen: Contact List
 * A class that manages user device phone number list with image and email, number.
 */
public class ContactAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Contact> contactList;
    LayoutInflater lf;
    ViewHolder holder;
    private ArrayList<Contact> mOriginalValues; // Original Values

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        mOriginalValues = contactList;
        this.context = context;
        this.contactList = contactList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // convert from byte array to bitmap
    public static Bitmap getImagedata(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_contact, parent, false);
            holder = new ViewHolder();
            holder.txtConName = convertView.findViewById(R.id.txtConName);
            holder.txtPhone = convertView.findViewById(R.id.txtPhone);
            holder.txtHPhone = convertView.findViewById(R.id.txtHPhone);
            holder.txtWPhone = convertView.findViewById(R.id.txtWPhone);
            holder.txtEmail = convertView.findViewById(R.id.txtEmail);
            holder.txtAddress = convertView.findViewById(R.id.txtAddress);
            holder.imgConPhoto = convertView.findViewById(R.id.imgConPhoto);
            holder.imgForword = convertView.findViewById(R.id.imgForword);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact c = contactList.get(position);
        holder.txtConName.setText(contactList.get(position).getName());

        if (!contactList.get(position).getPhone().equals("")) {
            holder.txtPhone.setText(c.getPhone());
            holder.txtPhone.setVisibility(View.VISIBLE);
        } else {
            holder.txtPhone.setVisibility(View.GONE);
        }
        if (contactList.get(position).getHomePhone()!=null) {
        if (!contactList.get(position).getHomePhone().equals("")) {
            holder.txtHPhone.setText(c.getHomePhone());
            holder.txtHPhone.setVisibility(View.GONE);
        } else {
            holder.txtHPhone.setVisibility(View.GONE);
        }
        } else {
            holder.txtHPhone.setVisibility(View.GONE);
        }

        if (contactList.get(position).getWorkPhone()!=null) {
        if (!contactList.get(position).getWorkPhone().equals("")) {
            holder.txtWPhone.setText(c.getWorkPhone());
            holder.txtWPhone.setVisibility(View.GONE);
        } else {
            holder.txtWPhone.setVisibility(View.GONE);
        }
        } else {
            holder.txtWPhone.setVisibility(View.GONE);
        }

        if (contactList.get(position).getEmail().equals("")) {
            holder.txtEmail.setVisibility(View.GONE);
        } else {
            holder.txtEmail.setVisibility(View.GONE);
            holder.txtEmail.setText(contactList.get(position).getEmail());
        }

        if (contactList.get(position).getAddress().equals("")) {
            holder.txtAddress.setVisibility(View.GONE);
        } else {
            holder.txtAddress.setVisibility(View.GONE);
            holder.txtAddress.setText(contactList.get(position).getAddress());
        }

        if (contactList.get(position).getImage() != null || !contactList.get(position).getImage().equals("")) {
            if (getImagedata(contactList.get(position).getImage()) != null) {
                holder.imgConPhoto.setImageBitmap(getImagedata(contactList.get(position).getImage()));
            } else {
                Resources res = context.getResources();
                Drawable drawable = res.getDrawable(R.drawable.all_profile);
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                holder.imgConPhoto.setImageBitmap(bitmap);
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

                FragmentNewContact fragmentNewContact = new FragmentNewContact();
                Bundle args = new Bundle();
                args.putBoolean("FromDevice", true);
                args.putString("Name", contactList.get(position).getName());
                args.putString("Email", contactList.get(position).getEmail());
                args.putString("Phone", contactList.get(position).getPhone());
                args.putString("Address", contactList.get(position).getAddress());
                args.putString("HPhone", contactList.get(position).getHomePhone());
                args.putString("WPhone", contactList.get(position).getWorkPhone());
                args.putByteArray("Photo", contactList.get(position).getImage());
                fragmentNewContact.setArguments(args);
                ((GrabConnectionActivity) context).callFragment("NEWCONTACT", fragmentNewContact);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                contactList = (ArrayList<Contact>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Contact> FilteredArrList = new ArrayList<Contact>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Contact>(contactList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString())) {

                            FilteredArrList.add(new Contact(mOriginalValues.get(i).getName(), mOriginalValues.get(i).getEmail(), mOriginalValues.get(i).getPhone(), mOriginalValues.get(i).getImage(), mOriginalValues.get(i).getAddress(), mOriginalValues.get(i).getHomePhone(), mOriginalValues.get(i).getWorkPhone()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public class ViewHolder {
        TextView txtConName, txtPhone, txtEmail, txtAddress, txtHPhone, txtWPhone;
        ImageView imgConPhoto, imgForword;
    }
}

