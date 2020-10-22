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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedone.healthcare.DropBox.FilesActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.RecyclerSwipeAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.SimpleSwipeListener;

import com.mindyourlovedone.healthcare.SwipeCode.SwipeLayout;
import com.mindyourlovedone.healthcare.backuphistory.BackupHistoryQuery;
import com.mindyourlovedone.healthcare.backuphistory.DBHelperHistory;
import com.mindyourlovedone.healthcare.backuphistory.SqliteHelper;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.VaccineQuery;
import com.mindyourlovedone.healthcare.model.BackupHistory;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by varsha on 8/23/2017.
 */

/**
 * Class: ConnectionAdapter
 * Screen: Profile List
 * A class that manages user profile as well users related profile list with image and relation.
 * facilitate for backup,share profile database,delete profile
 */
public class ConnectionAdapter extends RecyclerSwipeAdapter<ConnectionAdapter.ViewHolder> {

    final CharSequence[] import_new = {"Create New", "Import From Dropbox"};
    Context context;
    ArrayList<RelativeConnection> connectionList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    String[] Relationship = {"Aunt", "Brother", "Brother-in-law", "Client", "Cousin", "Dad", "Daughter", "Daughter-in-law", "Father-in-law", "Friend", "Granddaughter", "Grandmother", "Grandfather", "Grandson", "Husband", "Mom", "Mother-in-law", "Neighbor", "Nephew", "Niece", "Patient", "Roommate", "Significant Other", "Sister", "Sister-in-law", "Son", "Son-in-law", "Uncle", "Wife", "Other"};
    FragmentConnectionNew fragmentConnectionNew;
    SqliteHelper sqliteHelper;

    /**
     * Constructor: ConnectionAdapter
     *
     * @param context               Scope
     * @param connectionList        List to be display
     * @param fragmentConnectionNew fragment
     */
    public ConnectionAdapter(Context context, ArrayList<RelativeConnection> connectionList, FragmentConnectionNew fragmentConnectionNew) {
        preferences = new Preferences(context);
        this.context = context;
        this.connectionList = connectionList;
        this.fragmentConnectionNew = fragmentConnectionNew;
       sqliteHelper=new SqliteHelper(context);
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    /**
     * Function: To set relation to profile while profile imported from dropbox and stored
     *
     * @param context
     * @param id
     * @param mail
     */
    private void showInputDialog(final Context context, final int id, final String mail) {
        final Dialog customDialog;
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_relation);
        customDialog.setCancelable(false);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);

        final TextInputLayout tilOtherRelation = customDialog.findViewById(R.id.tilOtherRelation);
        final TextView txtOtherRelation = customDialog.findViewById(R.id.txtOtherRelation);
        final MySpinner spinnerRelation = customDialog.findViewById(R.id.spinnerRelation);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relationship");

        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherRelation.setVisibility(View.VISIBLE);
                    tilOtherRelation.setHint("Other Relation");
                } else {
                    tilOtherRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = spinnerRelation.getSelectedItemPosition();
                if (i != 0) {
                    String relation = Relationship[i - 1];
                    String otherRelation = "";
                    if (relation.equals("Other")) {
                        otherRelation = txtOtherRelation.getText().toString();
                    } else {
                        otherRelation = "";
                    }
                    DBHelper dbHelpers = new DBHelper(context, "MASTER");
                    MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelpers);
                    Boolean flag = MyConnectionsQuery.updateMyConnectionsRelationData(id, relation, otherRelation);
                    if (flag == true) {
                        String email = mail.replace(".", "_");
                        String mail = email.replace("@", "_");
                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        DBHelper dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
                        MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                        Boolean flags = MyConnectionsQuery.updateMyConnectionsRelationData(1, relation, otherRelation);
                        if (flags == true) {
                            Toast.makeText(context, "Relation has been updated successfully", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, "Relation has been updated successfully", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(context, BaseActivity.class);
                        context.startActivity(myIntent);
                        ((BaseActivity) context).finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lf.inflate(R.layout.row_connections, parent, false);
        return new ConnectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //  YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(row_archivelist.findViewById(R.id.trash));
            }
        });

        if (position != connectionList.size()) {
            holder.txtConName.setText(connectionList.get(position).getName());
            if (connectionList.get(position).getRelationType().equals("Other")) {
                holder.txtConRelation.setText(connectionList.get(position).getOtherRelation());
            } else {
                holder.txtConRelation.setText(connectionList.get(position).getRelationType());
            }

            if (!connectionList.get(position).getPhoto().equals("")) {
                String mail1 = connectionList.get(position).getEmail();
                mail1 = mail1.replace(".", "_");
                mail1 = mail1.replace("@", "_");
                File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/" + mail1 + "/", connectionList.get(position).getPhoto());

                if (imgFile.exists()) {
                    if (holder.imgConPhoto.getDrawable() == null)
                        holder.imgConPhoto.setImageResource(R.drawable.lightblue);
                    else {
                        final String uri = Uri.fromFile(imgFile).toString();
                        Picasso.with(context)
                                .load(uri)
                                .into(holder.imgConPhoto);
                    }
                }
            } else {
                holder.imgConPhoto.setImageResource(R.drawable.lightblue);
            }

        }

//Navigate to Personal Profile of clicked profile
        holder.imgConPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to Personal Profile of clicked profile
                Intent intentP = new Intent(context, ProfileActivity.class);
                String mail1 = connectionList.get(position).getEmail();
                mail1 = mail1.replace(".", "_");
                mail1 = mail1.replace("@", "_");
                preferences.putString(PrefConstants.USER_IMAGE, connectionList.get(position).getPhoto());
                preferences.putString(PrefConstants.CONNECTED_NAME, connectionList.get(position).getName());
                preferences.putString(PrefConstants.CONNECTED_USEREMAIL, connectionList.get(position).getEmail());
                preferences.putInt(PrefConstants.CONNECTED_USERID, connectionList.get(position).getId());
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail1);
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");

                context.startActivity(intentP);
            }
        });

        //Navigate to Dashboard
        holder.rlEmergencyContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (connectionList.get(position).getRelationType().equals("")) {
                    showInputDialog(context, connectionList.get(position).getId(), connectionList.get(position).getEmail());
                } else {
                    FragmentDashboard ldf = new FragmentDashboard();
                    Bundle args = new Bundle();
                    args.putString("Name", connectionList.get(position).getName());
                    args.putString("Address", connectionList.get(position).getAddress());
                    args.putString("Relation", connectionList.get(position).getRelationType());
                    //String saveThis = Base64.encodeToString(connectionList.get(position).getPhoto(), Base64.DEFAULT);
                    preferences.putString(PrefConstants.USER_IMAGE, connectionList.get(position).getPhoto());
                    preferences.putString(PrefConstants.CONNECTED_NAME, connectionList.get(position).getName());
                    preferences.putString(PrefConstants.CONNECTED_USEREMAIL, connectionList.get(position).getEmail());
                    preferences.putInt(PrefConstants.CONNECTED_USERID, connectionList.get(position).getId());
                    String mail = connectionList.get(position).getEmail();
                    mail = mail.replace(".", "_");
                    mail = mail.replace("@", "_");
                    preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                    ldf.setArguments(args);

                    ((BaseActivity) context).callFragment("DASHBOARD", ldf);
                }
            }
        });

        //Backup Profile
        holder.imgBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Backuping",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Backup");
                alert.setMessage("Do you want to Backup " + connectionList.get(position).getName() + "'s profile?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showEmailDialog(position, "Backup");
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
        //Share Profile
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context,"Sharing",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Share");
                alert.setMessage("Do you want to Share " + connectionList.get(position).getName() + "'s profile?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showEmailDialog(position, "Share");
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

        //Delete Record
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to Delete " + connectionList.get(position).getName() + "'s profile?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = connectionList.get(position).getEmail();
                        mail = mail.replace(".", "_");
                        mail = mail.replace("@", "_");
                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                        fragmentConnectionNew.deleteConnection(connectionList.get(position).getId());
                        dialog.dismiss();
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

    /**
     * Function: Display dialog to take input for file name from user to be backup and share
     * by default file name would be username with current date
     */
    private void showEmailDialog(final int position, final String from) {
        final Dialog customDialog;
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_zip);
        customDialog.setCancelable(false);
        final EditText etNote = customDialog.findViewById(R.id.etNote);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = customDialog.findViewById(R.id.btnNo);
        String mail = connectionList.get(position).getName();
        mail = mail.replace(" ", "_");
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
        String formattedDate = df.format(date);
        etNote.setText(mail + "_" + formattedDate);
        etNote.setSelection(etNote.getText().length());
        //Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentConnectionNew.hideSoftKeyboard();
                customDialog.dismiss();

            }
        });
        // Navigate to DropboxloginActivity for next backup,share
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Inside code",Toast.LENGTH_SHORT).show();
                fragmentConnectionNew.hideSoftKeyboard();
                String mail = connectionList.get(position).getEmail();
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");

                String username = etNote.getText().toString().trim();
                username = username.replace(".", "_");
                username = username.replace("@", "_");
                username = username.replace(" ", "_");
                if (username.equals("")) {
                    etNote.setError("Please enter file name");
                    DialogManager.showAlert("Please enter file name", context);
                } else {

                    Intent i = new Intent(context, DropboxLoginActivity.class);
                    if (from.equalsIgnoreCase("Share")) {
                        i.putExtra("FROM", "Share");
                    } else if (from.equalsIgnoreCase("Backup")) {
                        i.putExtra("FROM", "Backup");
                    }

                    i.putExtra("ToDo", "Individual");
                    i.putExtra("ToDoWhat", "Share");

                    preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                    preferences.putString(PrefConstants.ZIPFILE, username);

                    DBHelperHistory sqliteHelper=new DBHelperHistory(context);
                    BackupHistoryQuery backupHistoryQuery=new BackupHistoryQuery(context,sqliteHelper);
                    Calendar c4 = Calendar.getInstance();
                    c4.getTime();
                    DateFormat df4 = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                    String date4 = df4.format(c4.getTime());
                    Boolean flagr = BackupHistoryQuery.insertHistoryData(etNote.getText().toString().trim(),"Manual",date4,"","In Progress","Individual","");
                    if (flagr == true) {
                        preferences.putString(PrefConstants.InProgress,"true");
                        Toast.makeText(context, "Backup history has been saved succesfully", Toast.LENGTH_SHORT).show();
                        ArrayList<BackupHistory> backupHistory=BackupHistoryQuery.fetchAllRecord();
                        for (int j=0;j<backupHistory.size();j++) {
                            preferences.putInt("LASTBACKUPRECORD", backupHistory.get(j).getId());
                        }
                        Toast.makeText(context,""+preferences.getInt("LASTBACKUPRECORD"),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                    context.startActivity(i);
                    customDialog.dismiss();
                }
            }
        });

        customDialog.show();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtConName, txtConRelation, txtAddress;
        ImageView imgConPhoto, imgSelfFolder, imgBackup, imgShare, imgDelete;
        SwipeLayout swipeLayout;
        RelativeLayout rlEmergencyContact;
        LinearLayout lincall;

        public ViewHolder(View convertView) {
            super(convertView);
            lincall = itemView.findViewById(R.id.lincall);
            swipeLayout = itemView.findViewById(R.id.swipe);
            imgBackup = (ImageView) convertView.findViewById(R.id.imgBackup);
            imgShare = (ImageView) convertView.findViewById(R.id.imgShare);
            imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
            txtAddress = convertView.findViewById(R.id.txtAddress);
            txtConName = (TextView) convertView.findViewById(R.id.txtConName);
            txtConRelation = (TextView) convertView.findViewById(R.id.txtConRelation);
            imgConPhoto = (ImageView) convertView.findViewById(R.id.imgConPhoto);
            imgSelfFolder = (ImageView) convertView.findViewById(R.id.imgSelfFolder);
            rlEmergencyContact = convertView.findViewById(R.id.rlEmergencyContact);
        }
    }


}


