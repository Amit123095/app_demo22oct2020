package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mindyourlovedone.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.RecyclerSwipeAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.SimpleSwipeListener;
import com.mindyourlovedone.healthcare.SwipeCode.SwipeLayout;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017. Changes done by nikita on 18/6/18
 */
/**
 * Class: EmergencyAdapter
 * Screen: Emergency contact list
 * A class that manages to Emergency contact list
 * implements OnclickListener for onClick event on views
 */
public class EmergencyAdapter extends RecyclerSwipeAdapter<EmergencyAdapter.ViewHolder> {
    Context context;
    ArrayList<Emergency> emergencyList;
    LayoutInflater lf;
    Preferences preferences;
    ImageLoader imageLoaderProfile, imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile, displayImageOptionsCard;
    FragmentEmergency fr;

    public EmergencyAdapter(Context context, ArrayList<Emergency> emergencyList, FragmentEmergency fr) {
        this.fr = fr;
        this.context = context;
        this.emergencyList = emergencyList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = new Preferences(context);
        //Initialize Image loading and displaying at ImageView
        initImageLoader();
    }

    public EmergencyAdapter(Context context, ArrayList<Emergency> emergencyList) {
        this.context = context;
        this.emergencyList = emergencyList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = new Preferences(context);
        //Initialize Image loading and displaying at ImageView
        initImageLoader();
    }


    /**
     * Function: Image loading and displaying at ImageView
     * Presents configuration for ImageLoader & options for image display.
     */
    private void initImageLoader() {

        //Profile
        displayImageOptionsProfile = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.all_profile)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptionsProfile)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoaderProfile = ImageLoader.getInstance();

        //Card
        displayImageOptionsCard = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.busi_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration configs = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptionsCard)
                .build();
        ImageLoader.getInstance().init(configs);
        imageLoaderCard = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_specialist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return emergencyList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        viewHolder.imgNext.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                if (fr != null) {
                    fr.callUser(emergencyList.get(position));
                }
            }
        });
        viewHolder.lintrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fr != null) {
                    fr.deleteEmergency(emergencyList.get(position));
                }
            }
        });

        if (emergencyList.get(position).getMobile().equals("")) {
            viewHolder.txtPhone.setVisibility(View.GONE);
        }
        if (emergencyList.get(position).getPhone().equals("")) {
            viewHolder.txtTelePhone.setVisibility(View.GONE);
        }

        String[] priorityType = {"Primary Emergency Contact", "Primary Health Care Proxy Agent", "Secondary Emergency Contact", "Secondary Health Care Proxy Agent", "Primary Emergency Contact and Health Care Proxy Agent"};
        if (emergencyList.get(position).getIsPrimary() == 4) {
            viewHolder.txtState.setVisibility(View.GONE);
        } else {
            if (emergencyList.get(position).getIsPrimary() == 0) {
                viewHolder.txtState.setText(priorityType[0]); //nikita
            } else if (emergencyList.get(position).getIsPrimary() == 1) {
                viewHolder.txtState.setText(priorityType[1]); //nikita
            } else if (emergencyList.get(position).getIsPrimary() == 2) {
                viewHolder.txtState.setText(priorityType[2]); //nikita
            } else if (emergencyList.get(position).getIsPrimary() == 3) {
                viewHolder.txtState.setText(priorityType[3]); //nikita
            } else if (emergencyList.get(position).getIsPrimary() == 0) {
                viewHolder.txtState.setText("");
            }
        }
        viewHolder.txtName.setText(emergencyList.get(position).getName());
        if (emergencyList.get(position).getIsPrimary() == 0) {
            String priority = "Primary Emergency Contact";
            viewHolder.txtType.setText(priority);
        } else if (emergencyList.get(position).getIsPrimary() == 1) {

            String priority = "Primary Health Care Proxy Agent";
            viewHolder.txtType.setText(priority);
        } else if (emergencyList.get(position).getIsPrimary() == 2) {

            String priority = "Secondary Emergency Contact";
            viewHolder.txtType.setText(priority);
        } else if (emergencyList.get(position).getIsPrimary() == 3) {

            String  priority = "Secondary Health Care Proxy Agent";
            viewHolder.txtType.setText(priority);
        } else if (emergencyList.get(position).getIsPrimary() == 4) {

            String  priority = "Primary Emergency Contact and Health Care Proxy Agent";
            viewHolder.txtType.setText(priority);
        } else {
            String priority = "";
            viewHolder.txtType.setText(priority);
        }

        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), emergencyList.get(position).getPhoto());
        viewHolder.imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));

        if (imgFile.exists()) {
            if (viewHolder.imgProfile.getDrawable() == null)
                viewHolder.imgProfile.setImageResource(R.drawable.all_profile);
            else
                viewHolder.imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
              }

      if (!emergencyList.get(position).getPhotoCard().equals("")) {
            File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), emergencyList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
                if (viewHolder.imgForword.getDrawable() == null)
                    viewHolder.imgForword.setImageResource(R.drawable.busi_card);
                else
                    viewHolder.imgForword.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));
            }

            viewHolder.imgForword.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgForword.setVisibility(View.GONE);
        }

        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE, "EmergencyUpdate");
                Intent i = new Intent(context, GrabConnectionActivity.class);
                i.putExtra("EmergencyObject", emergencyList.get(position));
                context.startActivity(i);
            }
        });
        viewHolder.rlEmergency.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE, "EmergencyUpdate");
                Intent i = new Intent(context, GrabConnectionActivity.class);
                i.putExtra("TAB","New");
                i.putExtra("EmergencyObject", emergencyList.get(position));
                context.startActivity(i);
            }
        });

        viewHolder.imgForword.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                Intent i = new Intent(context, AddFormActivity.class);
                i.putExtra("Image", emergencyList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType, txtTelePhone, txtOfficePhone, txtState;
        ImageView imgProfile, imgEdit, imgForword, imgNext;
        RelativeLayout rlMain, rlEmergency;
        SwipeLayout swipeLayout;
        LinearLayout lincall, lintrash;
        public ViewHolder(View convertView) {
            super(convertView);
            lincall = itemView.findViewById(R.id.lincall);
            lintrash = itemView.findViewById(R.id.lintrash);
            swipeLayout = itemView.findViewById(R.id.swipe);
            txtName = convertView.findViewById(R.id.txtName);
            txtOfficePhone = convertView.findViewById(R.id.txtOfficePhone);
            txtPhone = convertView.findViewById(R.id.txtPhone);
            txtState = convertView.findViewById(R.id.txtState);
            txtTelePhone = convertView.findViewById(R.id.txtTelePhone);
            txtType = convertView.findViewById(R.id.txtType);
            imgProfile = convertView.findViewById(R.id.imgProfile);
            imgEdit = convertView.findViewById(R.id.imgEdit);
            imgForword = convertView.findViewById(R.id.imgForword);
            rlMain = convertView.findViewById(R.id.rlMain);
            imgNext = convertView.findViewById(R.id.imgNext);
            rlEmergency = convertView.findViewById(R.id.rlEmergency);
        }
    }

}