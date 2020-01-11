package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.mindyourlovedone.healthcare.DashBoard.AddFormActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.RecyclerSwipeAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.SimpleSwipeListener;
import com.mindyourlovedone.healthcare.SwipeCode.SwipeLayout;
import com.mindyourlovedone.healthcare.model.Finance;
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
 * Class: FinanceAdapter
 * Screen: Finance Contact List Screen
 * A class that manages Adpater for Finance Contact List
 */
public class FinanceAdapter extends RecyclerSwipeAdapter<FinanceAdapter.ViewHolder> {
    Context context;
    ArrayList<Finance> FinanceList;
    LayoutInflater lf;
    Preferences preferences;
    ImageLoader imageLoaderProfile, imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile, displayImageOptionsCard;
    FragmentFinance fr;

    public FinanceAdapter(Context context, ArrayList<Finance> FinanceList) {
        preferences = new Preferences(context);
        this.context = context;
        this.FinanceList = FinanceList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Initialize Image loading and displaying at ImageView
        initImageLoader();
    }

    public FinanceAdapter(Context context, ArrayList<Finance> FinanceList, FragmentFinance fr) {
        this.fr = fr;
        preferences = new Preferences(context);
        this.context = context;
        this.FinanceList = FinanceList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                .showImageOnLoading(R.drawable.ic_profile_defaults)
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
    public FinanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_finance, parent, false);
        return new FinanceAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return FinanceList.size();
    }

    @Override
    public void onBindViewHolder(final FinanceAdapter.ViewHolder holder, final int position) {
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                if (fr != null) {
                    fr.callUser(FinanceList.get(position));
                }
            }
        });
        holder.lintrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fr != null) {
                    fr.deleteFinance(FinanceList.get(position));
                }
            }
        });

        if (FinanceList.get(position).getOfficePhone().equals("")) {
            holder.txtPhone.setVisibility(View.GONE);
        }

        if (FinanceList.get(position).getAddress().equals("")) {
            holder.txtAddress.setVisibility(View.GONE);
        }

        if (FinanceList.get(position).getCategory().equals("")) {
            holder.txtCategory.setVisibility(View.GONE);
        } else {
            holder.txtCategory.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText(FinanceList.get(position).getName());

        if (FinanceList.get(position).getCategory().equals("Other")) {
            holder.txtCategory.setVisibility(View.VISIBLE);
            holder.txtCategory.setText(FinanceList.get(position).getCategory() + " - " + FinanceList.get(position).getOtherCategory());
        } else {
            holder.txtCategory.setVisibility(View.VISIBLE);
            holder.txtCategory.setText(FinanceList.get(position).getCategory());
        }

        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), FinanceList.get(position).getPhoto());
        holder.imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));

        if (imgFile.exists()) {
            if (holder.imgProfile.getDrawable() == null)
                holder.imgProfile.setImageResource(R.drawable.all_profile);
            else
                holder.imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
            // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
        }
        if (!FinanceList.get(position).getPhotoCard().equals("")) {
            File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), FinanceList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
                if (holder.imgForward.getDrawable() == null)
                    holder.imgForward.setImageResource(R.drawable.busi_card);
                else
                    holder.imgForward.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));
            }

            holder.imgForward.setVisibility(View.VISIBLE);
        } else {
            holder.imgForward.setVisibility(View.GONE);
        }


        holder.imgForward.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                Intent i = new Intent(context, AddFormActivity.class);
                i.putExtra("Image", FinanceList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });

        holder.rlFinance.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "FinanceData");
                Finance finance = FinanceList.get(position);
                i.putExtra("FinanceObject", finance);
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
        TextView txtName, txtAddress, txtPhone, txtType, txtCategory;
        ImageView imgProfile, imgForward, imgEdit;
        ImageView imgNext;
        SwipeLayout swipeLayout;
        RelativeLayout rlFinance;
        LinearLayout lincall, lintrash;
        // SwipeRevealLayout swipeLayout;

        public ViewHolder(View convertView) {
            super(convertView);
            lincall = itemView.findViewById(R.id.lincall);
            lintrash = itemView.findViewById(R.id.lintrash);
            swipeLayout = itemView.findViewById(R.id.swipe);
            txtName = convertView.findViewById(R.id.txtName);
            txtAddress = convertView.findViewById(R.id.txtAddress);
            txtPhone = convertView.findViewById(R.id.txtPhone);
            txtType = convertView.findViewById(R.id.txtType);
            txtCategory = convertView.findViewById(R.id.txtCategory);
            imgProfile = convertView.findViewById(R.id.imgProfile);
            imgForward = convertView.findViewById(R.id.imgForword);
            imgEdit = convertView.findViewById(R.id.imgEdit);
            imgNext = convertView.findViewById(R.id.imgNext);
            rlFinance = convertView.findViewById(R.id.rlFinance);
        }
    }
}