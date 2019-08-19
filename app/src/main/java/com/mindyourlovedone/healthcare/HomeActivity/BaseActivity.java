package com.mindyourlovedone.healthcare.HomeActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
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
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.mindyourlovedone.healthcare.Connections.FragmentConnectionNew;
import com.mindyourlovedone.healthcare.DashBoard.AddDocumentActivity;
import com.mindyourlovedone.healthcare.DashBoard.AddInsuranceFormActivity;
import com.mindyourlovedone.healthcare.DashBoard.CustomArrayAdapter;
import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.FragmentNotification;
import com.mindyourlovedone.healthcare.DashBoard.PrescriptionUploadActivity;
import com.mindyourlovedone.healthcare.Fragment.FragmentContactUs;
import com.mindyourlovedone.healthcare.Fragment.FragmentResourcesNew;
import com.mindyourlovedone.healthcare.Fragment.FragmentSetting;
import com.mindyourlovedone.healthcare.Fragment.FragmentSponsor;
import com.mindyourlovedone.healthcare.IndexMenu.FragmentOverview;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CALL_PERMISSION = 600;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    Context context = this;
    FragmentDashboard fragmentDashboard = null;
    FragmentResources fragmentResources = null;
    FragmentForm fragmentForm = null;
    FragmentMarketPlace fragmentMarketPlace = null;
    FragmentVideos fragmentVideos = null;
    FragmentBackup fragmentBackup = null;
    FragmentConnectionNew fragmentConnection = null;
    FragmentNotification fragmentNotification = null;
    FragmentOverview fragmentOverview = null;
    ImageView imgHelp, imgR, imgDrawer, imgNoti, imgLogout, imgLocationFeed, imgProfile, imgDrawerProfile, imgPdf, imgDoc, imgRight;
    TextView txtTitle, txtName, txtRel, txtDrawerName, txtFname, txtAdd;
    TextView txtBank, txtForm, txtSenior, txtAdvance, txtPodcast;
    DrawerLayout drawerLayout;
    RelativeLayout leftDrawer, container, footer, header;
    RelativeLayout rlLogOutt;
    FrameLayout flLogout;
    Preferences preferences;
    ImageView txtDrawer;
    TextView txtPrivacyPolicy, txtEULA, txtversion;
    RelativeLayout rlBackup, rlSettings, rlWebsite, rlGuide, rlProfiles, rlHome, rlSupport, rlContactUs, rlSponsor, rlResources, rlPrivacy, rlMarketPlace, rlVideos, rlResourcesDetail, rlMarketDetail, rlPrivacyDetail;
    TextView txtBackup,txtSettings, txtWebsite, txtGuide, txtProfiles, txtHome, txtSupport, txtContactUs, txtSponsor, txtResources, txtPrivacy, txtMarketPlace, txtVideos, txtResourcesDetail, txtMarketDetail, txtPrivacyDetail;
    ImageView imgBackup,imgSettings, imgWebsite, imgGuide, imgProfiles, imgHome, imgSupport, imgContactUs, imgSponsor, imgResources, imgPrivacy, imgMarketPlace, imgVideos, imgResourcesDetail, imgMarketDetail, imgPrivacyDetail;

    boolean flagResource = false, flagMarket = false, flagPrivacy = false;
    int p = 0;

    ImageLoader imageLoader, imageLoaderProfile;
    DisplayImageOptions displayImageOptions, displayDrawerImageOptions;
    DBHelper dbHelper;
    boolean external_flag = false;
    String originPath = "";
    String documentPath = "";
    String name = "";

    String From;
    Intent i;
    final CharSequence[] dialog_add = {"Add to Advance Directives", "Add to Other Documents", "Add to Medical Records", "Add to Insurance Forms", "Add to Prescription List"};
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    //    Crashlytics.getInstance().crash(); // Force a crash
        pd = new ProgressDialog(this);//nikita
        pd.setTitle("Loading UI...");
        pd.show();
        new Handler().postDelayed(new Runnable() {//nikita
            @Override
            public void run() {
                //Here you can send the extras.
                new AsynData().execute("");
            }
        }, 100);

        try {

            //nikita -pdf
            Intent i = getIntent();
            if (i != null) {
               Uri audoUri = i.getParcelableExtra(Intent.EXTRA_STREAM);

                if (audoUri != null) {
                    Log.v("URI", audoUri.toString());
                    preferences = new Preferences(context);
                    if (preferences.getREGISTERED() && preferences.isLogin()) {
                        loadData();
                        extPDF(audoUri + "");
                    } else {
                        Toast.makeText(getApplicationContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BaseActivity.this, SplashNewActivity.class));
                        finish();
                    }
                } else {
                    loadData();
                }
            } else {
                loadData();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private void loadData() {

        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
//                    //I'm also creating a log message, which we'll look at in more detail later//
        FirebaseCrash.log("MainActivity started");
        accessPermission();
        //Crashlytics.getInstance().crash(); // Force a crash
        initImageLoader();
        initComponent();
        initUI();
        initListener();


        fragmentData();


        if (fragmentManager.findFragmentByTag("CONNECTION") == null) {
            if (preferences.getREGISTERED()) {
                callFirstFragment("CONNECTION", fragmentConnection);
            }
        }
        /*shradha*/
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
                }/*New Changes*/ else if (p == 2) {
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
                } else if (p == 8) {
                    imgProfile.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText("Settings");
                    callFragmentData(new FragmentSetting());
                    //nikita
                    imgHome.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgProfiles.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgResources.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSponsor.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgSettings.setColorFilter(ContextCompat.getColor(context, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgContactUs.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgMarketPlace.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgVideos.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);
                    imgBackup.setColorFilter(ContextCompat.getColor(context, R.color.colorGray), android.graphics.PorterDuff.Mode.MULTIPLY);


                    txtHome.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtProfiles.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtResources.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSponsor.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtSettings.setTypeface(txtHome.getTypeface(), Typeface.BOLD);
                    txtBackup.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtContactUs.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtMarketPlace.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                    txtVideos.setTypeface(txtHome.getTypeface(), Typeface.NORMAL);
                } else if (p == 5) {
                    imgProfile.setVisibility(View.GONE);
                    callFragmentData(new FragmentResources());
                }

                else if (p == 6) {
                    imgProfile.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtTitle.setText("Sponsor");
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

                }else if (p ==9) {
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
        /*Intent intent = getIntent();
        if (intent.getExtras() != null) {
            p = intent.getExtras().getInt("c");
            if (p == 1) {
                callFragmentData(new FragmentDashboard());
                p = 1;
            } else if (p == 3) {
                callFragmentData(new FragmentConnectionNew());
                p = 1;
            }
        }*/
    }


    List<RelativeConnection> items;//nikita


    public void getData() {//nikita
        DBHelper dbHelper = new DBHelper(this, "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(this, dbHelper);
        items = MyConnectionsQuery.fetchAllRecord();
    }

    private void extPDF(final String URI) {//nikita
        getData();
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
                //silly mistake - forgets this line to give path for saving copy of pdfs
                //-corrected by nikita on 30-5-19
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                dialogSharePdf.dismiss();
                AlertDialog.Builder builders = new AlertDialog.Builder(context);
                builders.setTitle("");
                builders.setCancelable(false);
                builders.setItems(dialog_add, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        switch (itemPos) {
                            case 0: // email
                                Intent in = new Intent(BaseActivity.this, AddDocumentActivity.class);
                                in.putExtra("FROM", "AD");
                                in.putExtra("PDF_EXT", URI);
                                startActivity(in);
                                break;
                            case 1: // email
                                Intent in1 = new Intent(BaseActivity.this, AddDocumentActivity.class);
                                in1.putExtra("FROM", "Other");
                                in1.putExtra("PDF_EXT", URI);
                                startActivity(in1);
                                break;
                            case 2: // Fax
                                Intent in2 = new Intent(BaseActivity.this, AddDocumentActivity.class);
                                in2.putExtra("FROM", "Record");
                                in2.putExtra("PDF_EXT", URI);
                                startActivity(in2);
                                break;
                            case 3: // Fax
                                Intent in3 = new Intent(BaseActivity.this, AddInsuranceFormActivity.class);
                                in3.putExtra("FROM", "Insurance");
                                in3.putExtra("PDF_EXT", URI);
                                startActivity(in3);
                                break;
                            case 4: // Fax
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

/*
    private void showSharePdfDialog() {
      final   Dialog dialogSharePdf = new Dialog(context);
        dialogSharePdf.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSharePdf.setContentView(R.layout.dialog_share_storage_pdf);
        TextView txtSelect = dialogSharePdf.findViewById(R.id.txtSelect);
        TextView txtOk = dialogSharePdf.findViewById(R.id.txtOk);
        txtSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialogSharePdf.dismiss();
            }
        });

        dialogSharePdf.show();
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSharePdf.dismiss();
                initComponent();

            }
        });

    }
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
/*
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        DocumentQuery d = new DocumentQuery(context, dbHelper);
        if (preferences == null) {
            preferences = new Preferences(BaseActivity.this);
        }

        if (preferences.getREGISTERED() && preferences.isLogin()) {

        } else {
            Toast.makeText(getApplicationContext(), "You need to login first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BaseActivity.this, SplashNewActivity.class));
            finish();
        }

        From = preferences.getString(PrefConstants.FROM);

        i = getIntent();
        Log.v("URI", i.getExtras().toString());
        final Uri audoUri = i.getParcelableExtra(Intent.EXTRA_STREAM);
        if (audoUri != null) {
            Log.v("URI", audoUri.toString());
            AlertDialog.Builder builders = new AlertDialog.Builder(context);
            builders.setTitle("");
            builders.setCancelable(false);
            builders.setItems(dialog_add, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int itemPos) {
                    switch (itemPos) {
                        case 0: // email
                            From = "AD";
                            addfile(audoUri);
                            //initUi();
                            external_flag = true;
                            break;
                     */
/*   case 1: // email
                            From = "Other";
                            addfile(audoUri);
                            initUi();
                            external_flag = true;
                            break;
                        case 2: // Fax
                            From = "Record";
                            addfile(audoUri);
                            initUi();
                            external_flag = true;
                            break;*//*

                    }
                }
            });
            builders.create().show();
        }
    }
*/

/*
    private void addfile(Uri audoUri) {
        originPath = audoUri.toString();

        File f = new File(audoUri.getPath());
        originPath = f.getPath();
        originPath = originPath.replace("/root_path/", "");
        documentPath = f.getName();
        name = f.getName();
        preferences.putInt(PrefConstants.CONNECTED_USERID, 1);
        txtFname.setText(name);
        imgDoc.setClickable(false);
        String text = "You Have selected <b>" + name + "</b> Document";
        Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
        showDialogWindow(text);
        txtAdd.setText("Edit File");
        imgDoc.setImageResource(R.drawable.pdf);
    }
*/

    private void showDialogWindow(String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(Html.fromHtml(text));
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }


    private void initComponent() {
        preferences = new Preferences(context);
    }

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
        txtBackup= leftDrawer.findViewById(R.id.txtBackup);
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
        //  txtversion= findViewById(R.id.txtversion);

       /* try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            SpannableString s = new SpannableString("Version - " + version);
            s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length(), 0);
            txtversion.setText(s);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/

        txtBank = findViewById(R.id.txtBank);
        txtForm = findViewById(R.id.txtForm);
        txtSenior = findViewById(R.id.txtSenior);
        txtAdvance = findViewById(R.id.txtAdvance);

        drawerLayout = findViewById(R.id.drawerLayout);
        leftDrawer = findViewById(R.id.leftDrawer);
        header = findViewById(R.id.header);
        txtDrawerName = leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = leftDrawer.findViewById(R.id.imgDrawerProfile);
        imgDrawerProfile.setVisibility(View.VISIBLE);
        imgRight = leftDrawer.findViewById(R.id.imgRight);
        rlWebsite = leftDrawer.findViewById(R.id.rlWebsite);
        txtPrivacyPolicy = leftDrawer.findViewById(R.id.txtPrivacyPolicy);
        txtEULA = leftDrawer.findViewById(R.id.txtEULA);
        txtPodcast = leftDrawer.findViewById(R.id.txtPodcast);
    }

    private void fragmentData() {
        fragmentManager = getFragmentManager();
        fragmentDashboard = new FragmentDashboard();
        fragmentOverview = new FragmentOverview();
        fragmentConnection = new FragmentConnectionNew();
        fragmentNotification = new FragmentNotification();
        fragmentResources = new FragmentResources();
        fragmentForm = new FragmentForm();
        fragmentMarketPlace = new FragmentMarketPlace();
        fragmentVideos = new FragmentVideos();
        fragmentBackup = new FragmentBackup();
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
    /*|| fragName.equals("ADVANCE")*/

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*Left drawer clicks-Shradha*/

            case R.id.txtDrawer:
                // Intent intn=new Intent(context, ImageActivity.class);
                // startActivity(intn);
                drawerLayout.openDrawer(leftDrawer);
                // copydb(context);
                break;

            case R.id.rlHome:
              /*  Drawable mDrawable = context.getResources().getDrawable(R.drawable.drawer_home);
                mDrawable.setColorFilter(new
                        PorterDuffColorFilter(context.getResources().getColor(R.color.colorMaroon),PorterDuff.Mode.MULTIPLY));
*/

                Intent intentHome = new Intent(context, SplashNewActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                drawerLayout.closeDrawer(leftDrawer);
                break;
            case R.id.rlProfiles:
                Intent intentProfile = new Intent(context, BaseActivity.class);
                intentProfile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentProfile);
                drawerLayout.closeDrawer(leftDrawer);


                break;
            case R.id.rlResources:
                Intent intentResources = new Intent(context, BaseActivity.class);
                intentResources.putExtra("c", 2);
                intentResources.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentResources.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentResources);
                drawerLayout.closeDrawer(leftDrawer);


                break;
            case R.id.rlSponsor:

                Intent intentSponsor = new Intent(context, BaseActivity.class);
                intentSponsor.putExtra("c", 6);
                intentSponsor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentSponsor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSponsor);
                drawerLayout.closeDrawer(leftDrawer);


                break;
            case R.id.rlSettings:
                Intent intentSettings = new Intent(context, BaseActivity.class);
                intentSettings.putExtra("c", 8);
                intentSettings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSettings);
                drawerLayout.closeDrawer(leftDrawer);


                break;
            case R.id.rlBackup:
                Intent intentBackup = new Intent(context, DropboxLoginActivity.class);
               // intentBackup.putExtra("c", 7);
                intentBackup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentBackup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentBackup);
                drawerLayout.closeDrawer(leftDrawer);

             /*   Intent intentSettings = new Intent(context, BaseActivity.class);
                intentSettings.putExtra("c", 7);
                intentSettings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSettings);
                drawerLayout.closeDrawer(leftDrawer);*/


                break;
            case R.id.rlContactUs:
                Intent intentContactUs = new Intent(context, BaseActivity.class);
                intentContactUs.putExtra("c", 9);
                intentContactUs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentContactUs.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentContactUs);
                drawerLayout.closeDrawer(leftDrawer);


                break;
            case R.id.rlMarketPlace:
                drawerLayout.closeDrawer(leftDrawer);
                dialogCommingSoon();

                //nikita
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
            case R.id.rlVideos:
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

    private void dialogCommingSoon() {
        final Dialog dialogBank = new Dialog(context);
        dialogBank.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBank.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_bank, null);
        final TextView txtComming = dialogview.findViewById(R.id.txtComming);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);

        // txtComming.setText("Comming Soon");
        // txtComming.setTextColor(R.color.colorBlue);
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
            @Override
            public void onClick(View v) {
                dialogBank.dismiss();
            }
        });
    }

  /*  private void dialogCommingSoon() {

    }*/


    //Shradha
    private void showBankDialog() {
        final Dialog dialogBank = new Dialog(context);
        dialogBank.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBank.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_bank, null);
        final TextView txtComming = dialogview.findViewById(R.id.txtComming);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);

        // txtComming.setText("Comming Soon");
        // txtComming.setTextColor(R.color.colorBlue);
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
            @Override
            public void onClick(View v) {
                dialogBank.dismiss();
            }
        });
    }


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

                //   imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgDrawerProfile, displayImageOptions);

            }
        } else {
            imgDrawerProfile.setImageResource(R.drawable.ic_profiles);
        }
    }


    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try {
            in = assetManager.open(documentPath);
            outFile = new File(getExternalFilesDir(null), documentPath);
            out = new FileOutputStream(outFile);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            /*out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;*/
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri uri = null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        try {
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            // No application to view, ask to download one

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Application Found");
            builder.setMessage("Download Office Tool from Google Play ?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Intent marketIntent = new Intent(
                                    Intent.ACTION_VIEW);
                            marketIntent.setData(Uri
                                    .parse("market://details?id=cn.wps.moffice_eng"));//varsa ("market://details?id=com.adobe.reader"));
                            context.startActivity(marketIntent);
                        }
                    });
            builder.setNegativeButton("No", null);
            builder.create().show();
        }

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

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

        } else {
            // checkForRegistration();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: {
                if (grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //  checkForRegistration();

                } else {

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
            }catch (Exception e)
            {
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


    public class AsynData extends AsyncTask {//shradha

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            loadData();
            super.onPostExecute(o);
        }
    }
}


