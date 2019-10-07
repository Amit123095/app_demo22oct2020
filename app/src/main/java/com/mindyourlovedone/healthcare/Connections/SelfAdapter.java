package com.mindyourlovedone.healthcare.Connections;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.RecyclerSwipeAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.SimpleSwipeListener;
import com.mindyourlovedone.healthcare.SwipeCode.SwipeLayout;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Nikita on 7/10/2019.
 */

public class SelfAdapter extends RecyclerSwipeAdapter<SelfAdapter.ViewHolder> {

    Context context;
    ArrayList<RelativeConnection> connectionList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    FragmentConnectionNew fragmentConnectionNew;

    public SelfAdapter(Context context, ArrayList<RelativeConnection> connectionList, FragmentConnectionNew fragmentConnectionNew) {
        preferences = new Preferences(context);
        this.context = context;
        this.connectionList = connectionList;
        this.fragmentConnectionNew = fragmentConnectionNew;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initImageLoader();
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.lightblue)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lf.inflate(R.layout.self_item, parent, false);
        return new SelfAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //  YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });

        String image = preferences.getString(PrefConstants.USER_PROFILEIMAGE);

        holder.txtUser.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            String mail1 = preferences.getString(PrefConstants.USER_EMAIL);
            mail1 = mail1.replace(".", "_");
            mail1 = mail1.replace("@", "_");
            File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/" + mail1 + "/", image);

            if (imgFile.exists()) {

                if (holder.imgSelf.getDrawable() == null) {
                    holder.imgSelf.setImageResource(R.drawable.lightblue);
                } else {
                    holder.imgSelf.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                }
            }
        } else {

            holder.imgSelf.setImageResource(R.drawable.lightblue);
        }

        holder.imgSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeConnection connection = getProfile();
                Intent intentP = new Intent(context, ProfileActivity.class);
                preferences.putString(PrefConstants.USER_IMAGE, connection.getPhoto());
                preferences.putInt(PrefConstants.USER_ID, connection.getUserid());
                preferences.putString(PrefConstants.USER_NAME, connection.getName());
                preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
                preferences.putString(PrefConstants.USER_EMAIL, connection.getEmail());
                String mail1 = preferences.getString(PrefConstants.USER_EMAIL);
                mail1 = mail1.replace(".", "_");
                mail1 = mail1.replace("@", "_");
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail1);
                preferences.putInt(PrefConstants.CONNECTED_USERID, connection.getId());
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                context.startActivity(intentP);
            }
        });

        holder.rlSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDashboard ldf = new FragmentDashboard();
                Bundle args = new Bundle();
                args.putString("Name", preferences.getString(PrefConstants.USER_NAME));
                // args.putString("Address", connectionList.get(position).getAddress());
                args.putString("Relation", "Self");
                RelativeConnection connection = getProfile();
                //String saveThis = Base64.encodeToString(connectionList.get(position).getPhoto(), Base64.DEFAULT);
                preferences.putString(PrefConstants.USER_IMAGE, preferences.getString(PrefConstants.USER_PROFILEIMAGE));
                preferences.putString(PrefConstants.CONNECTED_NAME, preferences.getString(PrefConstants.USER_NAME));
                preferences.putString(PrefConstants.CONNECTED_USEREMAIL, preferences.getString(PrefConstants.USER_EMAIL));
                preferences.putInt(PrefConstants.CONNECTED_USERID, connection.getId());
                String mail = preferences.getString(PrefConstants.USER_EMAIL);
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                ldf.setArguments(args);
                ((BaseActivity) context).callFragment("DASHBOARD", ldf);
            }
        });

        holder.imgSelfFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDashboard ldf = new FragmentDashboard();
                Bundle args = new Bundle();
                args.putString("Name", preferences.getString(PrefConstants.USER_NAME));
                // args.putString("Address", connectionList.get(position).getAddress());
                args.putString("Relation", "Self");
                RelativeConnection connection = getProfile();
                //String saveThis = Base64.encodeToString(connectionList.get(position).getPhoto(), Base64.DEFAULT);
                preferences.putString(PrefConstants.USER_IMAGE, preferences.getString(PrefConstants.USER_PROFILEIMAGE));
                preferences.putString(PrefConstants.CONNECTED_NAME, preferences.getString(PrefConstants.USER_NAME));
                preferences.putString(PrefConstants.CONNECTED_USEREMAIL, preferences.getString(PrefConstants.USER_EMAIL));
                preferences.putInt(PrefConstants.CONNECTED_USERID, connection.getId());
                String mail = preferences.getString(PrefConstants.USER_EMAIL);
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                ldf.setArguments(args);
                ((BaseActivity) context).callFragment("DASHBOARD", ldf);
            }
        });


        holder.imgBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Backuping",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Backup");
                alert.setMessage("Do you want to Backup your profile?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(context, DropboxLoginActivity.class);
                        i.putExtra("FROM", "Backup");
                        i.putExtra("ToDo", "Individual");
                        i.putExtra("ToDoWhat", "Share");
                        String mail = preferences.getString(PrefConstants.USER_EMAIL);
                        ;
                        mail = mail.replace(".", "_");
                        mail = mail.replace("@", "_");
                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                        context.startActivity(i);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context,"Sharing",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Share");
                alert.setMessage("Do you want to Share your profile?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context, DropboxLoginActivity.class);
                        i.putExtra("FROM", "Share");
                        i.putExtra("ToDo", "Individual");
                        i.putExtra("ToDoWhat", "Share");
                        String mail = preferences.getString(PrefConstants.USER_EMAIL);
                        ;
                        mail = mail.replace(".", "_");
                        mail = mail.replace("@", "_");

                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                        context.startActivity(i);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

    }

    private RelativeConnection getProfile() {
        DBHelper dbHelper = new DBHelper(context, "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
        RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
        preferences.putInt(PrefConstants.USER_ID, connection.getId());
        preferences.putString(PrefConstants.USER_NAME, connection.getName());
        preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
        preferences.putString(PrefConstants.USER_EMAIL, connection.getEmail());
        return connection;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUser, txtRelation;
        LinearLayout llSelf;
        ImageView imgSelfFolder, imgSelf;
        ImageView imgBackup, imgShare;

        SwipeLayout swipeLayout;
        LinearLayout lincall;
        RelativeLayout rlSelf;

        public ViewHolder(View convertView) {
            super(convertView);
            lincall = itemView.findViewById(R.id.lincall);

            swipeLayout = itemView.findViewById(R.id.swipe);
            imgBackup = (ImageView) convertView.findViewById(R.id.imgBackup);
            imgShare = (ImageView) convertView.findViewById(R.id.imgShare);

            imgSelfFolder = (ImageView) convertView.findViewById(R.id.imgSelfFolder);
            rlSelf = convertView.findViewById(R.id.rlSelf);
            llSelf = convertView.findViewById(R.id.llSelf);
            imgSelfFolder = convertView.findViewById(R.id.imgSelfFolder);
            imgSelf = convertView.findViewById(R.id.imgSelf);
            txtUser = convertView.findViewById(R.id.txtUser);
            txtRelation = convertView.findViewById(R.id.txtRelation);
        }
    }

   /* @Override
    public boolean getSwipEnableByPosition(int position) {
        if(position % 2 == 0){
            return false;
        }
        return true;
    }*/
}


