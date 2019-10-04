package com.mindyourlovedone.healthcare.Connections;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.ContactData;

import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<ContactData> phonelist;
    LayoutInflater lf;
    int count;
    View previousView;
//    ViewHolder holder;
   int val;
    TextView txtRel,txtPhoNum,txtType;
    ImageView imgdeletePhone;


    public PhoneAdapter(Context context, ArrayList<ContactData> phonelist) {
        this.context=context;
        this.phonelist=phonelist;
        lf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return phonelist.size();
    }

    @Override
    public Object getItem(int position) {
        return phonelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

//        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_phone, parent, false);
//            holder = new ViewHolder();

           // txtRel=  convertView.findViewById(R.id.txtRel);
            imgdeletePhone=  convertView.findViewById(R.id.imgdeletePhone);
            txtPhoNum=convertView.findViewById(R.id.txtPhoNum);
            txtType=convertView.findViewById(R.id.txtType);

//            convertView.setTag(holder);
            view = convertView;
//        } else {
//            view = convertView;
//            holder = (ViewHolder) convertView.getTag();
//        }
       /* CustomWatcher oldWatcher = (CustomWatcher)txtPhoNum.getTag();
        if(oldWatcher != null)
            txtPhoNum.removeTextChangedListener(oldWatcher);
        */
        txtType.setText(phonelist.get(position).getContactType());
        txtPhoNum.setText(phonelist.get(position).getValue());

        /*CustomWatcher newWatcher = new CustomWatcher(phonelist.get(position),context);
        txtPhoNum.setTag(newWatcher);
        txtPhoNum.addTextChangedListener(newWatcher);*/



        txtPhoNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {

                   // final int position = v.getId();
                    final TextView Caption = (TextView) v;
                    phonelist.get(position).setValue(Caption.getText().toString());

                   /* phonelist.get(position).setContactType(txtType.getText().toString());
                    txtType.setText(phonelist.get(position).getContactType());
                    txtPhoNum.setText(phonelist.get(position).getValue());
                    notifyDataSetChanged();*/
                }
            }
        });

        txtPhoNum.setTag(position);

    txtPhoNum.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL =  txtPhoNum.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();

                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
//                    txtPhoNum.setText(editable.toString());
//                    phonelist.get((Integer) txtPhoNum.getTag()).setValue(editable.toString()+"-");
//                    notifyDataSetChanged();
                    //Toast.makeText(context,""+length,Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Type");
                final String[] types = {"Mobile", "Office", "Home", "Fax"};
                b.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (types[which].equalsIgnoreCase("None")) {
                            phonelist.get(position).setValue(phonelist.get(position).getValue());
                            phonelist.get(position).setContactType("");
                            txtType.setText(phonelist.get(position).getContactType());
                            txtPhoNum.setText(phonelist.get(position).getValue());

                        }else{
                            phonelist.get(position).setValue(phonelist.get(position).getValue());
                            phonelist.get(position).setContactType(types[which]);
                            txtType.setText(phonelist.get(position).getContactType());
                            txtPhoNum.setText(phonelist.get(position).getValue());
                        }
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }

                });
                b.show();


              /*  PopupMenu popup = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    popup = new PopupMenu(context, v, Gravity.BOTTOM);
                }
                // Inflate the menu from xml
                    popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
                    // Setup menu item selection
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                *//*case R.id.troy:
                                    Toast.makeText(MainActivity.this, "troy", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.rise:
                                    Toast.makeText(MainActivity.this, "300 Rise of Empire", Toast.LENGTH_SHORT).show();
                                    return true;
                                default:*//*

                            }
                            return true;
                        }
                    });
                    // Handle dismissal with: popup.setOnDismissListener(...);
                    // Show the menu
                    popup.show();*/

            }
        });
        imgdeletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context,"CLicked",Toast.LENGTH_SHORT).show();
                if (position==0)
                {
                    ((ProfileActivity)context).addNewPhone(position);
                }else{
                    ((ProfileActivity)context).deletePhone(position);
                }
            }
        });
       // txtRel.setText(relationship[position]);
        //  txtName.setText(student.getName());
        // txtCity.setText(student.getCity());

        if (position==0)
        {
            imgdeletePhone.setImageResource(R.drawable.add_n);
        }else{
            imgdeletePhone.setImageResource(R.drawable.delete_n);
        }

        return convertView;
    }

    public class ViewHolder {
        TextView txtRel,txtPhoNum,txtType;
        ImageView imgdeletePhone;
    }

    public class CustomWatcher  implements TextWatcher
    {
        private ContactData item;
        Context context;
        String s="";
        int prevL = 0;
        public CustomWatcher(ContactData item, Context context)
        {
            this.item = item;
            this.context=context;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            prevL = txtPhoNum.getText().toString().length();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

            int length = editable.length();

            if ((prevL < length) && (length == 3 || length == 7)) {
                editable.append("-");
                Toast.makeText(context,editable,Toast.LENGTH_SHORT).show();
            }

        }
    }

}
