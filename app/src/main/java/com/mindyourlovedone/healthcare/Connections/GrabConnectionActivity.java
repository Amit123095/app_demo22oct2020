package com.mindyourlovedone.healthcare.Connections;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

public class GrabConnectionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    Context context = this;
    Preferences preferences;
    FragmentNewContact fragmentNewContact = null;
    FragmentGrabContact fragmentGrabContact = null;
    TextView txtNew, txtTitle, txtsave, txtContact,txtDelete;
    ImageView imgContact, imgFb, imgGoogle, imgBack, imgRefresh;
    public static String source = "";
    LinearLayout llGrab;
    RelativeLayout header;
    ProgressDialog pd;//nikita
    boolean i = false;
    int c = 1;
    String tab = "";


    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_connection);


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

    }

    private void loadata() {//nikita

        accessPermission();
        preferences = new Preferences(context);
        initUI();
        initListener();
        fragmentData();
        Intent i = getIntent();
        if (i.getExtras() != null)//Shradha-intent
        {
            if (i.getStringExtra("TAB")!=null&&i.getStringExtra("TAB").equalsIgnoreCase("Contact")) {
                callFragment("NEWCONTACT", fragmentNewContact);
                callFragment("CONTACT", fragmentGrabContact);
            }
            else {
              //  callFragment("CONTACT", fragmentGrabContact);
                callFragment("NEWCONTACT", fragmentNewContact);
            }
        } else {
            callFragment("NEWCONTACT", fragmentNewContact);
        }
        //  if (fragmentManager.findFragmentByTag("INSURANCE") == null) {
        source = preferences.getString(PrefConstants.SOURCE);
        if (source.equals("PhysicianViewData") || source.equals("HospitalViewData") || source.equals("PharmacyDataView") || source.equals("ProxyUpdateView") || source.equals("EmergencyView") || source.equals("SpecialistViewData") || source.equals("FinanceViewData") || source.equals("InsuranceViewData") || source.equals("AidesViewData")) {
            llGrab.setVisibility(View.GONE);
            txtTitle.setVisibility(View.GONE);
            txtsave.setVisibility(View.GONE);

        } else
            {
            //llGrab.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.VISIBLE);
            txtsave.setVisibility(View.VISIBLE);

        }


        switch (source) {
            case "Connection":
                header.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                break;

            case "Pharmacy":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "PharmacyData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "PharmacyDataView":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Proxy":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "ProxyUpdate":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "ProxyUpdateView":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "Emergency":
                header.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                break;

            case "EmergencyUpdate":
                header.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));

                break;

            case "EmergencyView":
                header.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                break;

            case "Speciality":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Physician":
                header.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                break;

            case "PhysicianData":
                header.setBackgroundResource(R.color.colorEmerMainGreen);
                break;

            case "PhysicianViewData":
                header.setBackgroundResource(R.color.colorEmerMainGreen);
                break;
            case "SpecialistData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "SpecialistViewData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Insurance":
                header.setBackgroundResource(R.color.colorInsuaranceSkyBlue);
                break;

            case "InsuranceData":
                header.setBackgroundResource(R.color.colorInsuaranceSkyBlue);
                break;

            case "InsuranceViewData":
                header.setBackgroundResource(R.color.colorInsuaranceSkyBlue);
                break;

            case "Aides":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "AidesData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "AidesViewData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Finance":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "Hospital":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "PrescriptionInfo":
                header.setBackgroundResource(R.color.colorPrescriptionGray);
                break;

            case "HospitalData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;
            case "HospitalViewData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;


            case "FinanceData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;

            case "FinanceViewData":
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                break;
        }
    }


    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS
            }, PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {

        }
    }

    public void callFragment(String fragName, Fragment fragment) {
        switch (fragName) {
            case "NEWCONTACT":
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                txtNew.setTextColor(getResources().getColor(R.color.colorGray));

                txtContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtContact.setTextColor(getResources().getColor(R.color.colorLightBlue));
               /* imgContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgContact.setImageResource(R.drawable.ic_person_white);
               */
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgFb.setImageResource(R.drawable.fb);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgGoogle.setImageResource(R.drawable.g);
                imgRefresh.setVisibility(View.GONE);
                //header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                txtsave.setVisibility(View.VISIBLE);

                //shradha:Code for hide Save when profile is viewed
                source = preferences.getString(PrefConstants.SOURCE);
                if (source.equals("PhysicianViewData") || source.equals("HospitalViewData") || source.equals("PharmacyDataView") || source.equals("ProxyUpdateView") || source.equals("EmergencyView") || source.equals("SpecialistViewData") || source.equals("FinanceViewData") || source.equals("InsuranceViewData") || source.equals("AidesViewData")) {
                    txtsave.setVisibility(View.GONE);
                    txtTitle.setGravity(Gravity.CENTER);
                } else {
                    txtsave.setVisibility(View.VISIBLE);
                }

                break;

            case "CONTACT":
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtNew.setTextColor(getResources().getColor(R.color.colorLightBlue));

                txtContact.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                txtContact.setTextColor(getResources().getColor(R.color.colorGray));

               /* imgContact.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                imgContact.setImageResource(R.drawable.ic_person_gray);
               */
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgFb.setImageResource(R.drawable.fb);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgGoogle.setImageResource(R.drawable.g);
                imgRefresh.setVisibility(View.VISIBLE);
                txtsave.setVisibility(View.GONE);
                break;
        }


        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();


        new Handler().postDelayed(new Runnable() {//nikita
            @Override
            public void run() {
                //Here you can send the extras.
                pd.dismiss();
            }
        }, 100);
    }


    private void fragmentData() {
        fragmentNewContact = new FragmentNewContact();
        fragmentGrabContact = new FragmentGrabContact();
    }

    private void initListener() {
        txtNew.setOnClickListener(this);
        txtsave.setOnClickListener(this);
        txtContact.setOnClickListener(this);
        // imgContact.setOnClickListener(this);
        imgFb.setOnClickListener(this);
        imgGoogle.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        txtsave = findViewById(R.id.txtsave);
        txtTitle = findViewById(R.id.txtTitle);
        llGrab = findViewById(R.id.llGrab);
        txtNew = findViewById(R.id.txtNew);
        imgContact = findViewById(R.id.imgContact);
        txtContact = findViewById(R.id.txtContact);
        imgFb = findViewById(R.id.imgFb);
        imgGoogle = findViewById(R.id.imgGoogle);
        imgBack = findViewById(R.id.imgBack);
        imgRefresh = findViewById(R.id.imgRefresh);
        header = findViewById(R.id.header);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


           /* case R.id.txtsave:
              //  Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show();
//                if (fragmentManager.findFragmentByTag("NEWCONTACT") == null) {
                fragmentNewContact.savedata();
//                }

                break;*/

            case R.id.txtNew:
                if (fragmentManager.findFragmentByTag("NEWCONTACT") == null) {
                    callFragment("NEWCONTACT", fragmentNewContact);
                }

                break;

            case R.id.txtContact:
                if (fragmentManager.findFragmentByTag("CONTACT") == null) {
                    callFragment("CONTACT", fragmentGrabContact);
                }

                break;

            case R.id.imgFb:
                /*if (fragmentManager.findFragmentByTag("CONTACT") == null) {
                    callFragment("CONTACT", fragmentGrabContact);
                }*/
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtNew.setTextColor(getResources().getColor(R.color.colorLightBlue));

                txtContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtContact.setTextColor(getResources().getColor(R.color.colorLightBlue));

               /* imgContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgContact.setImageResource(R.drawable.ic_person_white);
               */
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                imgFb.setImageResource(R.drawable.fb_gray);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgGoogle.setImageResource(R.drawable.g);
                imgRefresh.setVisibility(View.GONE);
                txtsave.setVisibility(View.VISIBLE);
                break;
            case R.id.imgGoogle:
               /* if (fragmentManager.findFragmentByTag("CONTACT") == null) {
                    callFragment("CONTACT", fragmentGrabContact);
                }*/
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtNew.setTextColor(getResources().getColor(R.color.colorLightBlue));

                txtContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtContact.setTextColor(getResources().getColor(R.color.colorLightBlue));

               /* imgContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgContact.setImageResource(R.drawable.ic_person_white);
               */
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgFb.setImageResource(R.drawable.fb);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                imgGoogle.setImageResource(R.drawable.g_gray);
                imgRefresh.setVisibility(View.GONE);
                txtsave.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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

    public class AsynData extends AsyncTask {//nikita

        @Override
        protected Object doInBackground(Object[] objects) {

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            loadata();
            super.onPostExecute(o);
        }
    }

}