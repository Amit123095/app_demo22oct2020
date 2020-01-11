package com.mindyourlovedone.healthcare.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DashBoard.AddFormActivity;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 9/12/2017.
 */

public class SliderPagerAdapter extends PagerAdapter {

    private static final int REQUEST_CARD = 100;
    Context activity;
    ArrayList<String> image_arraylist;
    ArrayList<String> slider_text_list;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    Preferences preferences;
    private LayoutInflater layoutInflater;

    public SliderPagerAdapter(Context activity, ArrayList<String> image_arraylist, ArrayList<String> slider_text_list) {
        preferences = new Preferences(activity);
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.slider_text_list = slider_text_list;
        //Initialize Image loading and displaying at ImageView
        initImageLoader();
    }

    /**
     * Function: Image loading and displaying at ImageView
     * Presents configuration for ImageLoader & options for image display.
     */
    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ins_card)
                .considerExifParams(false) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        final ImageView im_slider = view.findViewById(R.id.im_slider);
        TextView textview = view.findViewById(R.id.text);
//        textview.setText(slider_text_list.get(position));
        im_slider.setAdjustViewBounds(true);
        im_slider.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), image_arraylist.get(position));
        if (imgFile.exists()) {
            imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)), im_slider, displayImageOptions);
        } else {
            im_slider.setImageResource(R.drawable.ins_card);
        }

        im_slider.setOnClickListener(new View.OnClickListener() {
            /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
                Intent i = new Intent(activity, AddFormActivity.class);
                File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), image_arraylist.get(position));
                if (!image_arraylist.get(position).equals("")) {
                    if (imgFile.exists()) {
                        i.putExtra("Image", image_arraylist.get(position));
                        i.putExtra("FROM", "Insurance");
                        activity.startActivity(i);
                    } else {
                        Toast.makeText(activity, "No Image for View or Share", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "No Image for View or Share", Toast.LENGTH_SHORT).show();
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

