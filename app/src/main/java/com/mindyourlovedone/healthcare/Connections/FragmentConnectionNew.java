package com.mindyourlovedone.healthcare.Connections;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.FileMemberActionResult;
import com.dropbox.core.v2.sharing.MemberSelector;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedone.healthcare.DashBoard.UserInsActivity;
import com.mindyourlovedone.healthcare.DropBox.DropboxClientFactory;
import com.mindyourlovedone.healthcare.DropBox.FilesActivity;
import com.mindyourlovedone.healthcare.DropBox.ShareFileTask;
import com.mindyourlovedone.healthcare.DropBox.UploadFileTask;
import com.mindyourlovedone.healthcare.DropBox.ZipListner;
import com.mindyourlovedone.healthcare.DropBox.ZipTask;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.HomeActivity.SplashNewActivity;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by varsha on 8/26/2017.
 */

/**
 * Class: FragmentConnectionNew
 * Screen: Profile List
 * A class that manages user profile as well users related profile list with image and relation.
 * facilitate for backup,share profile database,delete profile
 * Provision for add new profile contacts by selecting option i.e. from contacts,create new,import from dropbox
 */
public class FragmentConnectionNew extends Fragment implements View.OnClickListener ,ZipListner{
    private static final String APP_KEY = "428h5i4dsj95eeh";
    private FirebaseAnalytics mFirebaseAnalytics;
    View rootview;
    GridView lvConnection;
    RecyclerView lvSelf, rlselflist;
    ImageView fab;
    ImageView imgBacks;
    ArrayList<RelativeConnection> connectionList;
    TextView txtFTU;
    TextView txtTitle, txtName, txtRel, txtDrawerName;
    ImageView imgNoti, imgProfile, imgLogo, imgPdf, imgDrawerProfile, imgRight, imgR;
    DBHelper dbHelper;
    Preferences preferences;
    RelativeLayout leftDrawer, rlMsg;//rlSelf;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    RelativeLayout rlGuide;
    RelativeConnection connection;
    TextView txthelp, txtYour;
    ImageView imghelp;
    ProgressBar progressBar;
    Date oldBackup=null;
    public final static String EXTRA_PATH = "FilesActivity_Path";
    private String mPath;
    boolean flags=false;
    /**
     * @param inflater           LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view. This value may be null.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_connection_new, null);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"All Profile Listing Screen",null);

        //Initialize database, get primary data and set data
        initComponent();

        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        //Initialize Image loading and displaying at ImageView
        initImageLoader();
        return rootview;
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
                .showImageOnLoading(R.drawable.profile_darkbluecolor)
                .considerExifParams(false) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * Function: Initialize database, preferences
     */
    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        //preferences.putString(PrefConstants.BACKUPDONE, "true");
        String path = getActivity().getIntent().getStringExtra(EXTRA_PATH);
        mPath = path == null ? "" : path;
        FilesActivity.getIntent(getActivity(),"");


        SharedPreferences prefs = getActivity().getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        if (prefs.contains("access-token")) {
            DropboxClientFactory.init(prefs.getString("access-token", ""));
            if (DropboxClientFactory.getClient() == null) {
                prefs.edit().remove("access-token").apply();
                com.dropbox.core.android.AuthActivity.result = null;
                DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                    @Override
                    public void onRevoke() {
                        // Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                    }
                });
            }
        }
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        lvSelf = rootview.findViewById(R.id.lvSelf);
        rlselflist = rootview.findViewById(R.id.rlselflist);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);
        txtYour = rootview.findViewById(R.id.txtYour);
        fab = rootview.findViewById(R.id.fab);
        imgBacks = getActivity().findViewById(R.id.imgBacks);
        imgBacks.setVisibility(View.GONE);
        imgR = getActivity().findViewById(R.id.imgR);
        imgR.setVisibility(View.GONE);
        imgRight = getActivity().findViewById(R.id.imgRight);
        imgRight.setVisibility(View.VISIBLE);
        progressBar=getActivity().findViewById(R.id.progressBar);
        rlMsg = rootview.findViewById(R.id.rlMsg);
        txtFTU = rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentUserIns = new Intent(getActivity(), UserInsActivity.class);
                intentUserIns.putExtra("From", "Profile");
                startActivity(intentUserIns);
            }
        });
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Profiles");
        imgPdf = getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgProfile = getActivity().findViewById(R.id.imgProfile);
        txtName = getActivity().findViewById(R.id.txtName);
        txtRel = getActivity().findViewById(R.id.txtRel);
        leftDrawer = getActivity().findViewById(R.id.leftDrawer);
        txtDrawerName = leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = leftDrawer.findViewById(R.id.imgDrawerProfile);
        txtName.setVisibility(View.GONE);
        txtRel.setVisibility(View.GONE);
        imgProfile.setVisibility(View.GONE);
        imgNoti = getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);
        imgLogo = getActivity().findViewById(R.id.imgLogo);
        rlGuide = rootview.findViewById(R.id.rlGuide);
        imgLogo.setVisibility(View.INVISIBLE);
        lvConnection = rootview.findViewById(R.id.lvConnection);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvSelf.setLayoutManager(linearLayoutManager);
        lvSelf.addItemDecoration(new VerticalSpaceItemDecoration(20));
        lvSelf.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        rlselflist.setLayoutManager(linearLayoutManager2);
        rlselflist.addItemDecoration(new VerticalSpaceItemDecoration(0));
        rlselflist.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));


    }

    /**
     * Function: Set main user and related profiles name,photo,relation into list.
     */
    public void setListData() {
        ArrayList<RelativeConnection> selflist = new ArrayList<>();
        selflist.clear();
        selflist.add(new RelativeConnection());
        SelfAdapter selfAdapter = new SelfAdapter(getActivity(), selflist, FragmentConnectionNew.this);
        rlselflist.setAdapter(selfAdapter);
        if (connectionList.size() != 0) {
            ConnectionAdapter connectionAdapter = new ConnectionAdapter(getActivity(), connectionList, FragmentConnectionNew.this);
            lvSelf.setAdapter(connectionAdapter);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
            lvSelf.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            txtYour.setVisibility(View.VISIBLE);
        } else {
            imghelp.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.VISIBLE);
            txtYour.setVisibility(View.GONE);
            lvSelf.setVisibility(View.GONE);
        }
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgLogo.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    /**
     * Function: Get profile details of main user
     */
    private void getProfile() {
        dbHelper = new DBHelper(getActivity(), "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        connection = MyConnectionsQuery.fetchOneRecord("Self");
        preferences.putInt(PrefConstants.USER_ID, connection.getId());
        preferences.putString(PrefConstants.USER_NAME, connection.getName());
        preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
        preferences.putString(PrefConstants.USER_EMAIL, connection.getEmail());
    }

    /**
     * Function: Get profile details of connected user
     */
    public void getData() {
        DBHelper dbHelper = new DBHelper(getActivity(), "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        ArrayList<RelativeConnection> myconnectionList = MyConnectionsQuery.fetchAllRecord();
        connectionList = new ArrayList<>();
        for (int i = 0; i < myconnectionList.size(); i++) {
            if (!myconnectionList.get(i).getRelationType().equalsIgnoreCase("self")) {
                connectionList.add(myconnectionList.get(i));
            }
        }
    }

    /**
     * Function: Called when a view has been clicked.
     * * @param v The view that was clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSelf: //Navigate to Personal Profile of clicked profile
                getProfile();
                Intent intentP = new Intent(getActivity(), ProfileActivity.class);
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
                startActivity(intentP);
                break;

            case R.id.imgSelfFolder: //Navigate top Dasboard of clicked profile
                openDashboard();
                break;

            case R.id.imgRight: //Navigate to User Instruction Screen -Profile Screen Getting started
                Bundle bundle = new Bundle();
                bundle.putInt("AllProfileScreen", 1);
                mFirebaseAnalytics.logEvent("OnClick_QuestionMark", bundle);
                Intent intentUserIns = new Intent(getActivity(), UserInsActivity.class);
                intentUserIns.putExtra("From", "Profile");
                startActivity(intentUserIns);
                break;

            case R.id.fab: //Display Add New Profile floating dialog
                showFloatDialog();
                break;
        }
    }

    /**
     * Function: Navigate to Dashboard of clicked profile
     */
    private void openDashboard() {
        FragmentDashboard ldf = new FragmentDashboard();
        Bundle args = new Bundle();
        args.putString("Name", preferences.getString(PrefConstants.USER_NAME));
        args.putString("Relation", "Self");
        getProfile();
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
        ((BaseActivity) getActivity()).callFragment("DASHBOARD", ldf);
    }

    /**
     * Function: To display floating menu for add new profile
     */
    /**
     * Function: To display floating menu for add new profile
     */
    private void showFloatDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final RelativeLayout rlFloatfax = dialogview.findViewById(R.id.rlFloatfax);
        rlFloatfax.setVisibility(View.VISIBLE);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        final FloatingActionButton floatfax = dialogview.findViewById(R.id.floatfax);
        floatfax.setImageResource(R.drawable.dropbox);
        TextView txtFax = dialogview.findViewById(R.id.txtfax);
        txtFax.setText("Import from Dropbox");
        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
        rlView.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        //Dismiss Dialog
        floatCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Create New
        floatNew.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE, "Connection");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB", "New");
                getActivity().startActivity(i);
                dialog.dismiss();
            }

        });

        //Add from contacts
        floatContact.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE, "Connection");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB", "Contact");
                getActivity().startActivity(i);
                dialog.dismiss();
            }


        });

        //Import from dropbox
        floatfax.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), DropboxLoginActivity.class);
                in.putExtra("FROM", "Restore");
                in.putExtra("ToDo", "Individual");
                in.putExtra("ToDoWhat", "Import");
                getActivity().startActivity(in);
                dialog.dismiss();
            }


        });
    }

    /**
     * Function: Activity Callback method called when screen gets visible and interactive
     */
    @Override
    public void onResume() {
        super.onResume();
        getProfile();
        getData();
        setListData();
        String image = preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            String mail1 = preferences.getString(PrefConstants.USER_EMAIL);
            mail1 = mail1.replace(".", "_");
            mail1 = mail1.replace("@", "_");
            File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/" + mail1 + "/", image);
            imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
            if (imgFile.exists()) {
                if (imgDrawerProfile.getDrawable() == null) {
                    imgDrawerProfile.setImageResource(R.drawable.lightblue);
                } else {
                    imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                }
            }
        } else {
            imgDrawerProfile.setImageResource(R.drawable.lightblue);
        }
        if (preferences.getString(PrefConstants.FINIS).equals("Share")) {
            preferences.putString(PrefConstants.FINIS, "False");
            showEmailDialog();
        } else if (preferences.getString(PrefConstants.FINIS).equals("Backup")) {
            preferences.putString(PrefConstants.FINIS, "False");
            showBackupDialog();
            Log.v("FINDATA","FIN");
        }
        progressBar=getActivity().findViewById(R.id.progressBar);
        // if(preferences.getBoolean(PrefConstants.NOTIFIED)==true) {
        validateBackupDate();
        //}


        if ( preferences.getString(PrefConstants.BACKUPDATE).equals("")) {
            Calendar c4 = Calendar.getInstance();
            c4.getTime();
            DateFormat df4 = new SimpleDateFormat("dd MM yy");
            String date4 = df4.format(c4.getTime());
            preferences.putString(PrefConstants.BACKUPDATE, date4);
        }
        //   Toast.makeText(getActivity(),preferences.getString(PrefConstants.BACKUPDATE),Toast.LENGTH_SHORT).show();
    }


    /**
     * Function: Display dropbox backup done successfully dialog for Whole database
     */
    private void showBackupDialog() {
        String message = "";
        if (preferences.getString(PrefConstants.MSG) != null) {
            message = preferences.getString(PrefConstants.MSG);
        }
       /* if (preferences.getString(PrefConstants.FILE).equals("MYLO.zip")) {
            Calendar c = Calendar.getInstance();
            c.getTime();
            c.add(Calendar.MONTH, 1);
            DateFormat df = new SimpleDateFormat("dd MM yy HH:mm:ss");
            String date = df.format(c.getTime());
            preferences.putString(PrefConstants.BACKUPDATE, date);
            preferences.putBoolean(PrefConstants.NOTIFIED, true);
        }*/

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Backup Stored successfully");
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    /**
     * Function: Display reminder for your data backup
     */
    private void validateBackupDate() {
        DateFormat df = new SimpleDateFormat("dd MM yy");
        if (preferences.getString(PrefConstants.BACKUPDATE) != null || !preferences.getString(PrefConstants.BACKUPDATE).equals("")) {
            String newbackupdatestring="";
            if (preferences.getString(PrefConstants.REMINDER) != null) {
                String frequency = preferences.getString(PrefConstants.REMINDER);
                String backupdatestring =preferences.getString(PrefConstants.BACKUPDATE);
                Date backupDate = null;
                try {
                    backupDate = df.parse(backupdatestring);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (backupDate!=null) {
                    switch (frequency) {
                        case "Daily":
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(backupDate);
                            c1.add(Calendar.DATE, 1);
                            DateFormat df1 = new SimpleDateFormat("dd MM yy");
                            newbackupdatestring = df1.format(c1.getTime());
                            // preferences.putBoolean(PrefConstants.NOTIFIED, true);
                            break;

                        case "Weekly":
                            Calendar c5 = Calendar.getInstance();
                            c5.setTime(backupDate);
                            c5.add(Calendar.WEEK_OF_MONTH, 1);
                            DateFormat df5 = new SimpleDateFormat("dd MM yy");
                            newbackupdatestring = df5.format(c5.getTime());
                            //  preferences.putBoolean(PrefConstants.NOTIFIED, true);
                            break;

                        case "Monthly":
                            Calendar c = Calendar.getInstance();
                            c.setTime(backupDate);
                            c.add(Calendar.MONTH, 1);
                            DateFormat dfs = new SimpleDateFormat("dd MM yy");
                            newbackupdatestring = dfs.format(c.getTime());
                            // preferences.putBoolean(PrefConstants.NOTIFIED, true);
                            break;

                        case "Yearly":
                            Calendar c2 = Calendar.getInstance();
                            c2.setTime(backupDate);
                            c2.add(Calendar.YEAR, 1);
                            DateFormat df2 = new SimpleDateFormat("dd MM yy");
                            newbackupdatestring = df2.format(c2.getTime());
                            //  preferences.putBoolean(PrefConstants.NOTIFIED, true);
                            break;

                        case "Off":
                            newbackupdatestring = "";
                            break;
                    }
                }
            }

            Date newbackupDate=null;
            try {
                newbackupDate = df.parse(newbackupdatestring);
                //  Toast.makeText(getActivity(),newbackupdatestring,Toast.LENGTH_SHORT).show();
                Log.v("TAFy",newbackupdatestring+" " +preferences.getString(PrefConstants.REMINDER));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar cal = Calendar.getInstance();
            final Date currentDate = cal.getTime();
            if (newbackupDate != null) {
                if (currentDate.after(newbackupDate)) {

                    SimpleDateFormat dft = new SimpleDateFormat("dd MM yy");
                    final String formattedDatecurrentdate = dft.format(currentDate);

                    if (!preferences.getString(PrefConstants.BACKUPDONE).equals(formattedDatecurrentdate)) {
                        Toast.makeText(getActivity(),preferences.getString(PrefConstants.REMINDER) + " auto backup is started....", Toast.LENGTH_LONG).show();
                        // askForBackupDialog(newbackupdatestring,newbackupDate,currentDate);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Date date = Calendar.getInstance().getTime();
                                SimpleDateFormat dft = new SimpleDateFormat("MMddyyyy");
                                String formattedDate = dft.format(date);
                                String username = "MYLO" + "_" + formattedDate;
                                preferences.putString(PrefConstants.TODO, "Backup");
                                preferences.putString(PrefConstants.TODOWHAT, "Share");
                                preferences.putString(PrefConstants.STORE, "Backup");
                                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/");
                                preferences.putString(PrefConstants.ZIPFILE, username + "_MYLO");
                                preferences.putString(PrefConstants.BACKUPDONE, formattedDatecurrentdate);
                                //FilesActivity f=new FilesActivity();
                                //  f.uploadFile(preferences.getString(PrefConstants.CONNECTED_PATH));

                                //  startActivity(FilesActivity.getIntent(getActivity(), ""));
                                {

                                    File folder = new File(Environment.getExternalStorageDirectory() + "/MYLO/");
                                    if (!folder.exists()) {
                                        try {
                                            folder.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                    }

                                    File destfolder = new File(Environment.getExternalStorageDirectory(), "/" + preferences.getString(PrefConstants.ZIPFILE) + ".zip");
                                    if (!destfolder.exists()) {
                                        try {
                                            destfolder.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {

                                    }
                                    new ZipTask((BaseActivity) getActivity(), folder.getPath(), destfolder.getPath()).execute();
                                }
                            }
                        }, 1000);


                        //Auto Backup
                  /*  Intent i=new Intent(getActivity(),DropboxLoginActivity.class);
                    i.putExtra("SilentBackup",true);
                    getActivity().startActivity(i);*/
                    }
                }
            }
        }
    }

    private void askForBackupDialog(String newbackupdatestring, final Date newbackupDate, final Date currentDate) {
        preferences.putBoolean(PrefConstants.NOTIFIED,true);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("It's time to "+preferences.getString(PrefConstants.REMINDER)+" backup");
        // alert.setMessage("It's time to "+preferences.getString(PrefConstants.REMINDER)+" Backup");
        alert.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar c4 = Calendar.getInstance();
                c4.getTime();
                DateFormat df4 = new SimpleDateFormat("dd MM yy");
                String date4 = df4.format(c4.getTime());
                preferences.putString(PrefConstants.BACKUPDATE, date4);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(getActivity(),DropboxLoginActivity.class);
                i.putExtra("SilentBackup",true);
                getActivity().startActivity(i);
                dialog.dismiss();

            }
        });
        alert.setCancelable(false);
        alert.show();
    }

    /**
     * Function: Create Notification channel for show notification in latest devices
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "desc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("10", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Function: Create Notification
     */
    private void sendNotification() {
        preferences.putBoolean(PrefConstants.NOTIFIED, false);
        createNotificationChannel();
        Intent intent = new Intent(getActivity(), DropboxLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
        String msg = "This is a reminder for your data backup, your last backup was a month ago. You should backup regularly.";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "10")
                .setSmallIcon(R.mipmap.mylo_new_cropped_logo)
                .setContentTitle("Backup Your Data")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        notificationManager.notify(1, builder.build());
    }

    /**
     * Function: Delete selected profile from database and list
     *
     * @param id The id of profile user was clicked for delete record.
     */
    public void deleteConnection(int id) {
        boolean flag = MyConnectionsQuery.deleteRecord(id);
        if (flag == true) {
            getData();
            setListData();
            File dir = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function: Hide device keyboard.
     */
    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
    /**
     * Function: Display dialog for input of dropbox File name from user for sharing backup
     */
    public void showWholeEmailDialog(final String from) {
        final Dialog customDialog;
        customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_zip);
        customDialog.setCancelable(false);
        final EditText etNote = customDialog.findViewById(R.id.etNote);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = customDialog.findViewById(R.id.btnNo);
        TextView txtnote = customDialog.findViewById(R.id.txtnote);
        txtnote.setVisibility(View.VISIBLE);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
        String formattedDate = df.format(date);
        etNote.setText("MYLO" + "_" + formattedDate);
        etNote.setSelection(etNote.getText().length());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                customDialog.dismiss();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String username = etNote.getText().toString().trim();
                username = username.replace(".", "_");
                username = username.replace("@", "_");
                username = username.replace(" ", "_");
                if (username.equals("")) {
                    etNote.setError("Please enter file name");
                    DialogManager.showAlert("Please enter file name", getActivity());
                } else {
                    customDialog.dismiss();
                    Intent i = new Intent( getActivity(), DropboxLoginActivity.class);
                    if (from.equalsIgnoreCase("Share")) {
                        i.putExtra("FROM", "Share");
                    } else if (from.equalsIgnoreCase("Backup")) {
                        i.putExtra("FROM", "Backup");
                    }

                    i.putExtra("ToDo", "Individual");
                    i.putExtra("ToDoWhat", "Share");
                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/");
                    preferences.putString(PrefConstants.ZIPFILE, username + "_MYLO");
                    startActivity(i);

                }
            }
        });

        customDialog.show();
    }

    String txt = "Profile";

    /**
     * Function: Display dialog for input of dropbox email from user for sharing backup
     */
    private void showEmailDialog() {
        final Dialog customDialog;
        customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_email);
        customDialog.setCancelable(false);
        final EditText etNote = customDialog.findViewById(R.id.etNote);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = customDialog.findViewById(R.id.btnNo);

        //dismiss
        btnCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                customDialog.dismiss();

            }
        });

        //Validate and share
        btnAdd.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String username = etNote.getText().toString().trim();
                if (username.equals("")) {
                    etNote.setError("Please Enter email");
                    DialogManager.showAlert("Please Enter email", getActivity());
                } else if (!username.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    etNote.setError("Please enter valid email");
                    DialogManager.showAlert("Please enter valid email", getActivity());
                } else {
                    customDialog.dismiss();
                    List<MemberSelector> newMembers = new ArrayList<MemberSelector>();
                    MemberSelector newMember = MemberSelector.email(username);
                    newMembers.add(newMember);
                    if (preferences.getString(PrefConstants.FILE).contains("MYLO.zip")) {
                        txt = "Whole Backup";
                    } else {
                        txt = "Profile";
                    }

                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setMessage("Sharing " + txt + " can take several minutes");
                    dialog.show();
                    // Start backuping and sharing process
                    new ShareFileTask(newMembers, getActivity(), DropboxClientFactory.getClient(), new ShareFileTask.Callback() {
                        @Override
                        public void onUploadComplete(List<FileMemberActionResult> result) {
                            dialog.dismiss();
                            final AlertDialog.Builder alerts = new AlertDialog.Builder(getActivity());
                            alerts.setTitle("Success");
                            alerts.setMessage(txt + " shared successfully");
                            alerts.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alerts.show();
                        }

                        @Override
                        public void onError(Exception e) {
                            dialog.dismiss();

                        }
                    }).execute(preferences.getString(PrefConstants.SHARE), preferences.getString(PrefConstants.FILE));
                }
            }
        });

        customDialog.show();
    }


    @Override
    public void getFile(String res) {
        if (progressBar.getVisibility()==View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        // Toast.makeText(getActivity(),"Frag Getfile",Toast.LENGTH_SHORT).show();
        final File destfolder = new File(Environment.getExternalStorageDirectory(), preferences.getString(PrefConstants.ZIPFILE)+".zip");
        if (!destfolder.exists()) {
            try {
                destfolder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        SharedPreferences prefs = getActivity().getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        if (prefs.contains("access-token")) {
            Uri contentUri = null;
            contentUri = Uri.fromFile(destfolder);
            uploadFile(contentUri.toString());

        }else{
            Auth.startOAuth2Authentication(getActivity(), APP_KEY);
        }

    }

    private void uploadFile(String fileUri)
    {
        UploadFiles f=new UploadFiles(fileUri,preferences,getActivity(),progressBar);
        f.upload();
        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void setNameFile(String dirName) {
        //    progressBar=getActivity().findViewById(R.id.progressBar);
        // progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void getFile(String res, ProgressBar progressBar) {
        Preferences preferences=new Preferences(getActivity());
        if (progressBar.getVisibility()==View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        // Toast.makeText(getActivity(),"Frag Getfile",Toast.LENGTH_SHORT).show();
        final File destfolder = new File(Environment.getExternalStorageDirectory(), preferences.getString(PrefConstants.ZIPFILE)+".zip");
        if (!destfolder.exists()) {
            try {
                destfolder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        SharedPreferences prefs = getActivity().getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        if (prefs.contains("access-token")) {
            Uri contentUri = null;
            contentUri = Uri.fromFile(destfolder);
            uploadFile(contentUri.toString());

        }else{
            Auth.startOAuth2Authentication(getActivity(), APP_KEY);
        }

    }
}
