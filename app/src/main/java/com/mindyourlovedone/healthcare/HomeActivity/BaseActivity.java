package com.mindyourlovedone.healthcare.HomeActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.crashlytics.android.Crashlytics;
import com.dropbox.core.android.Auth;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.mindyourlovedone.healthcare.Connections.FragmentConnectionNew;
import com.mindyourlovedone.healthcare.Connections.UploadFiles;
import com.mindyourlovedone.healthcare.DashBoard.AddDocumentActivity;
import com.mindyourlovedone.healthcare.DashBoard.AddInsuranceFormActivity;
import com.mindyourlovedone.healthcare.DashBoard.CustomArrayAdapter;
import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.PrescriptionUploadActivity;
import com.mindyourlovedone.healthcare.DropBox.DropboxActivity;
import com.mindyourlovedone.healthcare.DropBox.ZipListner;
import com.mindyourlovedone.healthcare.Fragment.FragmentContactUs;
import com.mindyourlovedone.healthcare.Fragment.FragmentResourcesNew;
import com.mindyourlovedone.healthcare.Fragment.FragmentSetting;
import com.mindyourlovedone.healthcare.Fragment.FragmentSponsor;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.utility.WorkerPost;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class: BaseActivity
 * Screen: Main Screen
 * A class that manages Profile, Dashboard, Drawer and Drawer fragments
 * implements OnclickListener for onClick event on views
 */
public class BaseActivity extends DropboxActivity implements View.OnClickListener , ZipListner {
    private static final int REQUEST_CALL_PERMISSION = 600;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    private static final String APP_KEY = "428h5i4dsj95eeh";
    Context context = this;
    FragmentDashboard fragmentDashboard = null;
    FragmentResources fragmentResources = null;
    FragmentMarketPlace fragmentMarketPlace = null;
    FragmentVideos fragmentVideos = null;
    FragmentConnectionNew fragmentConnection = null;
    ImageView imgHelp, imgR, imgDrawer, imgNoti, imgLocationFeed, imgProfile, imgDrawerProfile, imgPdf, imgDoc, imgRight;
    TextView txtTitle, txtName, txtRel, txtDrawerName, txtFname, txtAdd;
    TextView txtBank, txtForm, txtSenior, txtAdvance, txtPodcast;
    DrawerLayout drawerLayout;
    RelativeLayout leftDrawer, container, header;
    FrameLayout flLogout;
    Preferences preferences;
    ImageView txtDrawer;
    ProgressBar progressBar;
    TextView txtPrivacyPolicy, txtEULA;
    RelativeLayout rlBackup, rlSettings, rlWebsite, rlProfiles, rlHome, rlContactUs, rlSponsor, rlResources, rlPrivacy, rlMarketPlace, rlVideos, rlResourcesDetail, rlMarketDetail, rlPrivacyDetail;
    TextView txtBackup, txtSettings, txtProfiles, txtHome, txtContactUs, txtSponsor, txtResources, txtPrivacy, txtMarketPlace, txtVideos, txtResourcesDetail, txtMarketDetail, txtPrivacyDetail;
    ImageView imgBackup, imgSettings, imgProfiles, imgHome, imgContactUs, imgSponsor, imgResources, imgPrivacy, imgMarketPlace, imgVideos, imgResourcesDetail, imgMarketDetail, imgPrivacyDetail;

    int p = 0, bginit = 0;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    final CharSequence[] dialog_add = {"Add to Advance Directives", "Add to Other Documents", "Add to Medical Records", "Add to Insurance Forms", "Add to Prescription List"};
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //  Crashlytics.getInstance().crash(); // Force a crash
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Profile_And_Menu_Screen", null /* class override */);

        //Initialize Image loading and displaying at ImageView
        initImageLoader();

        //Initialize database, get primary data and set data
        initComponent();

        //Initialize user interface view and components
        initUI();


        pd = new ProgressDialog(this);//nikita

        //Get external Files for add into application
        checkForExternalFiles();
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
                .showImageOnLoading(R.drawable.ic_profiles)
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

    /**
     * Function: Initialize preference
     */
    private void initComponent() {
        preferences = new Preferences(context);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        imgHelp = findViewById(R.id.imgHelp);
        imgDrawer = findViewById(R.id.imgDrawer);
        drawerLayout = findViewById(R.id.drawerLayout);
        leftDrawer = findViewById(R.id.leftDrawer);
        container = findViewById(R.id.fragmentContainer);

        rlHome = leftDrawer.findViewById(R.id.rlHome);
        rlProfiles = leftDrawer.findViewById(R.id.rlProfiles);
        rlResources = leftDrawer.findViewById(R.id.rlResources);
        rlSponsor = leftDrawer.findViewById(R.id.rlSponsor);
        rlSettings = leftDrawer.findViewById(R.id.rlSettings);
        rlBackup = leftDrawer.findViewById(R.id.rlBackup);
        rlContactUs = leftDrawer.findViewById(R.id.rlContactUs);
        rlMarketPlace = leftDrawer.findViewById(R.id.rlMarketPlace);
        rlVideos = leftDrawer.findViewById(R.id.rlVideos);

        imgHome = leftDrawer.findViewById(R.id.imgHome);
        imgProfiles = leftDrawer.findViewById(R.id.imgProfiles);
        imgResources = leftDrawer.findViewById(R.id.imgResources);
        imgSponsor = leftDrawer.findViewById(R.id.imgSponsor);
        imgSettings = leftDrawer.findViewById(R.id.imgSettings);
        imgBackup = leftDrawer.findViewById(R.id.imgBackup);
        imgContactUs = leftDrawer.findViewById(R.id.imgContactUs);
        imgMarketPlace = leftDrawer.findViewById(R.id.imgMarketPlace);
        imgVideos = leftDrawer.findViewById(R.id.imgVideos);

        txtHome = leftDrawer.findViewById(R.id.txtHome);
        txtProfiles = leftDrawer.findViewById(R.id.txtProfiles);
        txtResources = leftDrawer.findViewById(R.id.txtResources);
        txtSponsor = leftDrawer.findViewById(R.id.txtSponsor);
        txtSettings = leftDrawer.findViewById(R.id.txtSettings);
        txtBackup = leftDrawer.findViewById(R.id.txtBackup);
        txtContactUs = leftDrawer.findViewById(R.id.txtContactUs);
        txtMarketPlace = leftDrawer.findViewById(R.id.txtMarketPlace);
        txtVideos = leftDrawer.findViewById(R.id.txtVideos);
        flLogout = leftDrawer.findViewById(R.id.flLogout);


        txtFname = findViewById(R.id.txtFName);
        txtAdd = findViewById(R.id.txtAdd);
        txtDrawer = findViewById(R.id.txtDrawer);

        imgNoti = findViewById(R.id.imgNoti);
        imgR = findViewById(R.id.imgR);
        imgR.setVisibility(View.GONE);
        imgProfile = findViewById(R.id.imgProfile);
        imgPdf = findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgLocationFeed = findViewById(R.id.imgLocationFeed);
        txtHome = findViewById(R.id.txtHome);
        txtTitle = findViewById(R.id.txtTitle);
        txtName = findViewById(R.id.txtName);
        txtRel = findViewById(R.id.txtRel);

        /*txtBank = findViewById(R.id.txtBank);
        txtForm = findViewById(R.id.txtForm);
        txtSenior = findViewById(R.id.txtSenior);
        txtAdvance = findViewById(R.id.txtAdvance);*/
        progressBar=findViewById(R.id.progressBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        leftDrawer = findViewById(R.id.leftDrawer);
        header = findViewById(R.id.header);
        txtDrawerName = leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = leftDrawer.findViewById(R.id.imgDrawerProfile);
        imgDrawerProfile.setVisibility(View.VISIBLE);
        imgRight = leftDrawer.findViewById(R.id.imgRight);
       /* rlWebsite = leftDrawer.findViewById(R.id.rlWebsite);
        txtPrivacyPolicy = leftDrawer.findViewById(R.id.txtPrivacyPolicy);
        txtEULA = leftDrawer.findViewById(R.id.txtEULA);
        txtPodcast = leftDrawer.findViewById(R.id.txtPodcast);*/
    }


    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        txtDrawer.setOnClickListener(this);
        imgDrawer.setOnClickListener(this);
        rlHome.setOnClickListener(this);
        rlProfiles.setOnClickListener(this);
        rlResources.setOnClickListener(this);
        rlMarketPlace.setOnClickListener(this);
        rlVideos.setOnClickListener(this);
        rlSponsor.setOnClickListener(this);
        rlSettings.setOnClickListener(this);
        rlBackup.setOnClickListener(this);
        rlContactUs.setOnClickListener(this);
        // flLogout.setOnClickListener(this);

    }

    /**
     * Function: check for user is registered or login
     * If user is login, Add pdf by calling extPDF method
     */
    private void checkForExternalFiles() {
        try {
            Intent i = getIntent();
            if (i != null) {
                Uri audoUri = i.getParcelableExtra(Intent.EXTRA_STREAM);
                if (audoUri != null) {
                    Log.v("URI", audoUri.toString());
                    preferences = new Preferences(context);
                    //Check if application is registered or login by user
                    if (preferences.getREGISTERED() && preferences.isLogin()) {
                        //If true, Add into app
                        asyninit();
                        extPDF(audoUri + "");
                    } else {
                        //If false, Navigate to splash screen for register of login
                        Toast.makeText(getApplicationContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BaseActivity.this, SplashNewActivity.class));
                        finish();
                    }
                } else {
                    asyninit();
                }
            } else {
                asyninit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Background Operation
     */
    private void asyninit() {
        new Handler().postDelayed(new Runnable() {//nikita
            @Override
            public void run() {
                //Here you can send the extras.
                new AsynData().execute("");
            }
        }, 100);
    }

    /**
     * Function: Add External PDF into profile and chosen sub-section from list
     *
     * @param URI URI of file
     */
    private void extPDF(final String URI) {//nikita
        getData();

        //Dialog for Profile in which documents want to be add
        final Dialog dialogSharePdf = new Dialog(context);
        dialogSharePdf.setCancelable(false);
        dialogSharePdf.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSharePdf.setContentView(R.layout.dialog_share_storage_pdf);

        TextView txtSelect = dialogSharePdf.findViewById(R.id.txtSelect);
        TextView txtOk = dialogSharePdf.findViewById(R.id.txtOk);
        final MySpinner spinnerPro = dialogSharePdf.findViewById(R.id.spinnerPro);
        CustomArrayAdapter adapter = new CustomArrayAdapter(context,
                android.R.layout.simple_spinner_dropdown_item, items);

        spinnerPro.setAdapter(adapter);
        spinnerPro.setHint("Profile");

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = items.get(spinnerPro.getSelectedItemPosition() - 1).getEmail();
                String mail = email;
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                dialogSharePdf.dismiss();

                //Dialog for subsections in which documents need to be add
                AlertDialog.Builder builders = new AlertDialog.Builder(context);
                builders.setTitle("");
                builders.setCancelable(false);
                builders.setItems(dialog_add, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        switch (itemPos) {
                            case 0: // Advance Directive
                                Intent in = new Intent(BaseActivity.this, AddDocumentActivity.class);
                                in.putExtra("FROM", "AD");
                                in.putExtra("PDF_EXT", URI);
                                startActivity(in);
                                break;
                            case 1: // Other Doc
                                Intent in1 = new Intent(BaseActivity.this, AddDocumentActivity.class);
                                in1.putExtra("FROM", "Other");
                                in1.putExtra("PDF_EXT", URI);
                                startActivity(in1);
                                break;
                            case 2: // Medical Record
                                Intent in2 = new Intent(BaseActivity.this, AddDocumentActivity.class);
                                in2.putExtra("FROM", "Record");
                                in2.putExtra("PDF_EXT", URI);
                                startActivity(in2);
                                break;
                            case 3: // Insurance Form
                                Intent in3 = new Intent(BaseActivity.this, AddInsuranceFormActivity.class);
                                in3.putExtra("FROM", "Insurance");
                                in3.putExtra("PDF_EXT", URI);
                                startActivity(in3);
                                break;
                            case 4: // Prescription list
                                Intent in4 = new Intent(BaseActivity.this, PrescriptionUploadActivity.class);
                                in4.putExtra("FROM", "Prescription");
                                in4.putExtra("PDF_EXT", URI);
                                startActivity(in4);
                                break;
                        }
                    }
                });

                AlertDialog dialog = builders.create();
                builders.show();
            }
        });

        dialogSharePdf.show();

    }

    // Fetch All Profiles
    public void getData() {//nikita
        DBHelper dbHelper = new DBHelper(this, "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(this, dbHelper);
        items = MyConnectionsQuery.fetchAllRecord();
    }

    private String getContentName(ContentResolver resolver, Uri uri) {
        Cursor cursor = resolver.query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
        cursor.moveToFirst();
        int nameIndex = cursor.getColumnIndex(cursor.getColumnNames()[0]);
        if (nameIndex >= 0) {
            return cursor.getString(nameIndex);
        } else {
            return null;
        }
    }


    private void initBGProcess() {//Nikita#Sub Background check on subscription
        Data inputData = new Data.Builder()
                .build();

        OneTimeWorkRequest mywork =
                new OneTimeWorkRequest.Builder(WorkerPost.class)
                        .setInputData(inputData).build();// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
        String id = mywork.getId().toString();
        System.out.println("NIKITA WORK ID: " + id);
        if(mywork!=null) {
            WorkManager.getInstance().enqueue(mywork);
        }
    }


    private void callFragmentData(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();

        new Handler().postDelayed(new Runnable() {//nikita
            @Override
            public void run() {
                //Here you can send the extras.
                pd.dismiss();
            }
        }, 1000);

    }

    /**
     * Function: Load initial Data for fragment and drawer
     */
    private void loadDatadata() {
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
//                    //I'm also creating a log message, which we'll look at in more detail later//
        FirebaseCrash.log("MainActivity started");
        //Check for runtime permission
        accessPermission();
        //Register a callback to be invoked when this views are clicked.
        initListener();
        fragmentData();
        if (fragmentManager.findFragmentByTag("CONNECTION") == null) {
            if (preferences.getREGISTERED()) {
                callFirstFragment("CONNECTION", fragmentConnection);
            }
        }

        try {
            Intent intent = getIntent();
            if (intent != null) {
                p = intent.getExtras().getInt("c");
                if (p == 1) {
                    callFragmentData(new FragmentDashboard());
                    txtTitle.setVisibility(View.GONE);
                    p = 1;
                    //nikita
                    imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);

                    txtHome.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                    txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                } else if (p == 3) {
                    callFragmentData(new FragmentConnectionNew());
                    p = 1;
                    //nikita
                    imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);


                    txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                    txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                } else if (p == 2) {
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText("Resources");
                    imgProfile.setVisibility(View.GONE);
                    callFragmentData(new FragmentResourcesNew());
                    //nikita
                    imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);


                    txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtResources.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                    txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                } else if (p == 7) {
                    imgProfile.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText("Backup, Restore, Share");
                    callFragmentData(new FragmentSetting());
                    //nikita
                    imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);


                    txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtBackup.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                    txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                } else if (p == 5) {
                    imgProfile.setVisibility(View.GONE);
                    callFragmentData(new FragmentResources());
                } else if (p == 6) {
                    imgProfile.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText("In Cooperation With");
                    callFragmentData(new FragmentSponsor());

                    //nikita
                    imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);


                    txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                    txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);

                } else if (p == 9) {
                    imgProfile.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText("Contact Us");
                    callFragmentData(new FragmentContactUs());

                    //nikita
                    imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);


                    txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                    txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                }

                /*Ends here*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    List<RelativeConnection> items;//nikita

    /**
     * Function: Initialize Fragment
     */
    private void fragmentData() {
        fragmentManager = getFragmentManager();
        fragmentDashboard = new FragmentDashboard();
        fragmentConnection = new FragmentConnectionNew();
        fragmentResources = new FragmentResources();

        fragmentMarketPlace = new FragmentMarketPlace();
        fragmentVideos = new FragmentVideos();

    }

    public void callFirstFragment(String fragName, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();

        new Handler().postDelayed(new Runnable() {//shradha
            @Override
            public void run() {
                //Here you can send the extras.
                pd.dismiss();
            }
        }, 1000);
    }

    public void callFragment(String fragName, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (fragName.equals("DASHBOARD"))
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName).addToBackStack("CONNECTION");
        else
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();

        new Handler().postDelayed(new Runnable() {//shradha
            @Override
            public void run() {
                //Here you can send the extras.
                pd.dismiss();
            }
        }, 1000);

    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtDrawer://Oopen Drawer
                Bundle bundle = new Bundle();
                mFirebaseAnalytics.logEvent("OnClick_Menu_Drawer", bundle);
                drawerLayout.openDrawer(leftDrawer);
                break;

            case R.id.rlHome:
                Intent intentHome = new Intent(context, SplashNewActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlProfiles://Profile list
                Intent intentProfile = new Intent(context, BaseActivity.class);
                intentProfile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentProfile);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlResources://Resources
                Bundle bundles = new Bundle();
                mFirebaseAnalytics.logEvent("OnClick_Resources", bundles);
                Intent intentResources = new Intent(context, BaseActivity.class);
                intentResources.putExtra("c", 2);
                intentResources.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentResources.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentResources);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlSponsor://sponsor
                Intent intentSponsor = new Intent(context, BaseActivity.class);
                intentSponsor.putExtra("c", 6);
                intentSponsor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentSponsor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSponsor);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlSettings://Settins
                Intent intentSettings = new Intent(context, BaseActivity.class);
                intentSettings.putExtra("c", 8);
                intentSettings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSettings);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlBackup://Backup,restore
                Intent intentBackup = new Intent(context, DropboxLoginActivity.class);
                // intentBackup.putExtra("c", 7);
                intentBackup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentBackup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentBackup);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlContactUs://Contact us
                Intent intentContactUs = new Intent(context, BaseActivity.class);
                intentContactUs.putExtra("c", 9);
                intentContactUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentContactUs.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentContactUs);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlMarketPlace://Market place
                drawerLayout.closeDrawer(leftDrawer);
                dialogCommingSoon();
                imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);

                txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                break;

            case R.id.rlVideos://Video
                Bundle bundless = new Bundle();
                mFirebaseAnalytics.logEvent("OnClick_How_to_Videos", bundless);

                drawerLayout.closeDrawer(leftDrawer);
                dialogCommingSoon();

                //nikita
                imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);


                txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtSettings.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                txtVideos.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                break;

            /*Ends here...*/


        }
    }

    /**
     * Function: Comming Soon Dialog
     */
    private void dialogCommingSoon() {
        final Dialog dialogBank = new Dialog(context);
        dialogBank.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBank.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_bank, null);
        final TextView txtComming = dialogview.findViewById(R.id.txtComming);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);
        dialogBank.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogBank.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogBank.getWindow().setAttributes(lp);
        dialogBank.show();

        txtOk.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                dialogBank.dismiss();
            }
        });
    }

    /**
     * Function: Activity Callback method called when screen gets visible and interactive
     */
    @Override
    protected void onResume() {
        super.onResume();
        String image = preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));

        if (!image.equals("")) {
            File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/Master/", image);
            imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
            if (imgFile.exists()) {
                if (imgDrawerProfile.getDrawable() == null)
                    imgDrawerProfile.setImageResource(R.drawable.ic_profiles);
                else
                    imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
            }
        } else {
            imgDrawerProfile.setImageResource(R.drawable.ic_profiles);
        }

        if (preferences.getInt(PrefConstants.FROM_Dropbox) == 1 || bginit == 0) {
            bginit = 1;
            initBGProcess();
        }
    }

    @Override
    protected void loadData() {
        //fragmentConnection.getlogin();
    }

    /**
     * Function:Ceck for runtime permission
     */
    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CALL_PERMISSION);

        }
    }

    /**
     * Function: Callback for the result from requesting permissions.
     *
     * @param requestCode  int: The request code passed in requestPermissions(android.app.Activity, String[], int)
     * @param permissions  String: The requested permissions. Never null.
     * @param grantResults int: The grant results for the corresponding permissions which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  checkForRegistration();

                } else {
                    //Check for runtime permission
                    accessPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                callFirstFragment("CONNECTION", fragmentConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Back buttons was pressed, do whatever logic you want
        }

        return false;
    }

    int c2 = 0;

    @Override
    public void onBackPressed() {

        if (getIntent().hasExtra("c")) {
            if (c2 == 0) {
                if (getIntent().getExtras().getInt("c") == 1) {
                    c2 = 1;
                    callFragmentData(new FragmentConnectionNew());
                } else {
                    Intent intent = new Intent(context, SplashNewActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } else {
            SplashNewActivity.fromDash = true;

            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code

            } else {
                if (getFragmentManager().findFragmentByTag("CONNECTION").isResumed()) {
                    finish();
                } else {
                    getFragmentManager().popBackStack();
                }
            }
        }
    }

    @Override
    public void getFile(String res) {
        // fragmentConnection.getFile(res,progressBar);
        {
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


            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            if (prefs.contains("access-token")) {
                Uri contentUri = null;
                contentUri = Uri.fromFile(destfolder);
                uploadFile(contentUri.toString());

            }else{
                Auth.startOAuth2Authentication(context, APP_KEY);
            }

        }
    }

    private void uploadFile(String fileUri) {
        UploadFiles f=new UploadFiles(fileUri,preferences,BaseActivity.this,progressBar);
        f.upload();
        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void setNameFile(String dirName) {
        //fragmentConnection.setNameFile(dirName);
        progressBar.setVisibility(View.VISIBLE);
    }


    public class AsynData extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            loadDatadata();
            super.onPostExecute(o);
        }
    }
}

