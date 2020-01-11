package com.mindyourlovedone.healthcare.DashBoard;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;

import com.mindyourlovedone.healthcare.InsuranceHealthCare.SpecialistsActivity;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MedInfoQuery;
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

import java.io.File;

/**
 * Class: FragmentDashboard
 * A class that manages an Dashboard functionality.
 * extends Fragment
 * implements OnclickListener for onclick event on views
 */

public class FragmentDashboard extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CALL_PERMISSION = 300;
    ImageView imgProfile, imgLocationFeed, imgNoti, imgLogo, imgPdf, imgDrawerProfile, imgRight, imgR;
    TextView txtName, txtRel, txtAddress, txtRelation, txtDrawerName;
    RelativeLayout rlEmergencyContact, rlSpecialist, rlInsuranceCard, rlEmergencyEvent, rlPrescription, rlCarePlan;
    View rootview;
    TextView txtTitle;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout leftDrawer;
    RelativeConnection connection;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    ImageView imgBacks;

    /**
     * @param inflater           LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view. This value may be null.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_dashboard_news, null);
        preferences = new Preferences(getActivity());
        //Check for runtime permission
        accessPermission();

        //Initialize Image loading and displaying at ImageView
        initImageLoader();

        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        //Initialize database, get primary data and set data
        initComponent();

        return rootview;
    }

    /**
     * Function: Check for runtime permission
     * If granted go further else request for permission to be granted
     * Return result in onRequestPermissionsResult method
     */
    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CALL_PERMISSION);

        }
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
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        imgBacks = getActivity().findViewById(R.id.imgBacks);
        imgBacks.setVisibility(View.VISIBLE);
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.GONE);
        txtTitle.setText("");
        imgRight = getActivity().findViewById(R.id.imgRight);
        imgRight.setVisibility(View.VISIBLE);
        imgR = getActivity().findViewById(R.id.imgR);
        imgR.setVisibility(View.INVISIBLE);
        imgNoti = getActivity().findViewById(R.id.imgNoti);
        imgPdf = getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgNoti.setVisibility(View.INVISIBLE);
        imgLogo = getActivity().findViewById(R.id.imgLogo);
        imgLogo.setVisibility(View.GONE);
        imgProfile = getActivity().findViewById(R.id.imgProfile);
        imgProfile.setVisibility(View.VISIBLE);
        txtName = getActivity().findViewById(R.id.txtName);
        txtRel = getActivity().findViewById(R.id.txtRel);
        txtName.setVisibility(View.VISIBLE);
        txtRel.setVisibility(View.VISIBLE);
        leftDrawer = getActivity().findViewById(R.id.leftDrawer);
        txtDrawerName = leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = leftDrawer.findViewById(R.id.imgDrawerProfile);
        rlCarePlan = rootview.findViewById(R.id.rlCarePlan);
        rlEmergencyContact = rootview.findViewById(R.id.rlEmergencyContact);
        rlSpecialist = rootview.findViewById(R.id.rlSpecialist);
        rlInsuranceCard = rootview.findViewById(R.id.rlInsuranceCard);
        rlEmergencyEvent = rootview.findViewById(R.id.rlEmergencyEvent);
        rlPrescription = rootview.findViewById(R.id.rlPrescription);
        txtAddress = rootview.findViewById(R.id.txtAddress);
        txtRelation = rootview.findViewById(R.id.txtRelation);
        imgLocationFeed = getActivity().findViewById(R.id.imgLocationFeed);
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        rlCarePlan.setOnClickListener(this);
        rlEmergencyContact.setOnClickListener(this);
        rlSpecialist.setOnClickListener(this);
        rlInsuranceCard.setOnClickListener(this);
        rlEmergencyEvent.setOnClickListener(this);
        rlPrescription.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgPdf.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        imgBacks.setOnClickListener(this);
    }

    /**
     * Function: Initialize database, get data and set data
     */
    private void initComponent() {
        //initialize sqlite open helper database
        dbHelper = new DBHelper(getActivity(), "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        MedInfoQuery mq = new MedInfoQuery(getActivity(), dbHelper);

        // if connected profile is mainuser profile or relative Profile
        if (preferences.getInt(PrefConstants.CONNECTED_USERID) == (preferences.getInt(PrefConstants.USER_ID))) {
            connection = MyConnectionsQuery.fetchOneRecord("Self");
            preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
            preferences.putString(PrefConstants.CONNECTED_NAME, connection.getName());

            preferences.putString(PrefConstants.CONNECTED_RELATION, "Self");
            String name = connection.getName();
            String relation = "Self";
            if (!connection.getPhoto().equals("")) {
                String mail1 = connection.getEmail();
                mail1 = mail1.replace(".", "_");
                mail1 = mail1.replace("@", "_");
                File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/" + mail1 + "/", connection.getPhoto());
                imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                if (imgFile.exists()) {
                    if (imgProfile.getDrawable() == null)
                        imgProfile.setImageResource(R.drawable.ic_profiles);
                    else
                        imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                }
            } else {
                imgProfile.setImageResource(R.drawable.ic_profiles);
            }
            txtName.setText(name);
            txtRel.setText(relation);
            preferences.putString(PrefConstants.CONNECTED_PHOTO, connection.getPhoto());
        } else {
            connection = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            preferences.putString(PrefConstants.CONNECTED_NAME, connection.getName());
            preferences.putString(PrefConstants.CONNECTED_RELATION, connection.getRelationType());
            String name = connection.getName();
            String address = connection.getAddress();
            String relation = connection.getRelationType();
            String otherrelation = connection.getOtherRelation();
            preferences.putString(PrefConstants.CONNECTED_OtherRELATION, connection.getOtherRelation());
            File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), connection.getPhoto());
            imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
            if (imgFile.exists()) {
                if (imgProfile.getDrawable() == null)
                    imgProfile.setImageResource(R.drawable.ic_profiles);
                else
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));

            } else {
                imgProfile.setImageResource(R.drawable.ic_profiles);
            }

            if (relation.equals("Other")) {
                txtName.setText(name);
                txtRel.setText(otherrelation);
            } else {
                txtName.setText(name);
                txtRel.setText(relation);

            }
            preferences.putString(PrefConstants.CONNECTED_PHOTO, connection.getPhoto());
        }
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // click event on
        switch (v.getId()) {
            case R.id.imgBacks: //Back button
                getActivity().onBackPressed();
                imgBacks.setVisibility(View.GONE);
                break;

            case R.id.rlEmergencyContact: //Personal & Medical Info Box(Green box)
                Intent intentOverview = new Intent(getActivity(), SpecialistsActivity.class);
                intentOverview.putExtra("FROM", "Emergency");
                startActivity(intentOverview);
                break;

            case R.id.imgProfile: //User Profile photo on Top
                Intent intentProfile = new Intent(getActivity(), ProfileActivity.class);
                intentProfile.putExtra("FRAGMENT", "Individual");
                startActivity(intentProfile);
                break;

            case R.id.rlEmergencyEvent: //Event, Appointment box(Pink box)
                Intent intentContact = new Intent(getActivity(), SpecialistsActivity.class);
                intentContact.putExtra("FROM", "Event");
                startActivity(intentContact);
                break;

            case R.id.rlPrescription: //Prescription box(Gray box)
                Intent intentPrescription = new Intent(getActivity(), SpecialistsActivity.class);
                intentPrescription.putExtra("FROM", "Prescription");
                startActivity(intentPrescription);
                break;
            case R.id.imgRight: //User instructions(Question Mark on top right)
                Intent intentUserIns = new Intent(getActivity(), UserInsActivity.class);
                intentUserIns.putExtra("From", "Dashboard");
                startActivity(intentUserIns);
                break;

            case R.id.rlCarePlan: //Advance directive box(Red box)
                Intent intentCarePlan = new Intent(getActivity(), CarePlanActivity.class);
                startActivity(intentCarePlan);
                break;

            case R.id.rlSpecialist: //Doctor,Hospital box(Yellow box)
                Intent intentInsurance = new Intent(getActivity(), SpecialistsActivity.class);
                intentInsurance.putExtra("FROM", "Speciality");
                startActivity(intentInsurance);
                break;

            case R.id.rlInsuranceCard: //Insurance Box(Blue box)
                Intent intentInsuarnc3e = new Intent(getActivity(), SpecialistsActivity.class);
                intentInsuarnc3e.putExtra("FROM", "Insurance");
                startActivity(intentInsuarnc3e);
                break;

        }
    }

    /**
     * Function: Activity Callback method called when screen gets visible and interactive
     */
    @Override
    public void onResume() {
        super.onResume();
        //Initialize database, get primary data and set data
        initComponent();
        getProfile();
        setDrawerProfile();
    }

    /**
     * Function: Set Drawer profile name, image
     */
    private void setDrawerProfile() {
        String image = preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/Master/", image);
            if (imgFile.exists()) {
                imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                if (imgFile.exists()) {
                    if (imgDrawerProfile.getDrawable() == null)
                        imgDrawerProfile.setImageResource(R.drawable.ic_profiles);
                    else
                        imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                }
            }
        } else {
            imgDrawerProfile.setImageResource(R.drawable.ic_profiles);
        }
    }

    /**
     * Function: Callback for the result from requesting permissions.
     *
     * @param requestCode  int: The request code passed in requestPermissions(android.app.Activity, String[], int)
     * @param permissions  String: The requested permissions. Never null.
     * @param grantResults int: The grant results for the corresponding permissions which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
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
                } else {
                    //Check for runtime permission
        accessPermission();
                }
                return;
            }
        }
    }

    /**
     * Function: Get profile details of connected user
     */
    private void getProfile() {
        connection = MyConnectionsQuery.fetchOneRecord("Self");
        preferences.putInt(PrefConstants.USER_ID, connection.getUserid());
        preferences.putString(PrefConstants.USER_NAME, connection.getName());
        preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
    }
}
