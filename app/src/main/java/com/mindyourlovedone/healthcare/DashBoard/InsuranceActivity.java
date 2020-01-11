package com.mindyourlovedone.healthcare.DashBoard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentFinance;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentHospital;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentInsurance;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentPharmacy;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentPrescriptionInfo;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentSpecialist;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentVitalSigns;

/**
 * Class: InsuranceActivity
 * Screen: Speciality Contacts SubSection Screen
 * A class that manages to display list of subsection of Speciality Contacts
 * implements OnclickListener for onClick event on views
 */
public class InsuranceActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentInsurance fragmentInsurance = null;
    FragmentSpecialist fragmentSpecialist = null;
    FragmentPharmacy fragmentPharmacy = null;
    FragmentFinance fragmentFinance = null;
    FragmentHospital fragmentHospital = null;
    FragmentPrescriptionInfo fragmentPrescriptionInfo = null;
    FragmentPrescriptionUpload fragmentPrescriptionUpload = null;
    FragmentVitalSigns fragmentVitalSigns = null;

    RelativeLayout rlGuide;
    TextView txtTitle;
    Spinner spinner;
    Context context = this;

    ImageView imgBack, imgHome;
    RelativeLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        //Initialize Fragments
        fragmentData();

        //Initialize database, get primary data and set data
        initComponent();
    }

    /**
     * Function: Initialize Sections fragment,mnet
     */
    private void initComponent() {
        Intent i = getIntent();
        if (i.getExtras() != null) {
            String fragment = i.getExtras().getString("FRAGMENT");
            switch (fragment) {
                case "Insurance"://Insurance Company
                    txtTitle.setText("INSURANCE");
                    callFragment("INSURANCE", fragmentInsurance);
                    break;
                case "Doctors"://Doctor
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("Doctors & Other Health\nCare Professionals");
                    callFragment("SPECIALIST", fragmentSpecialist);
                    break;

                case "Hospitals"://Hospitals
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                    callFragment("HOSPITAL", fragmentHospital);
                    break;
                case "Pharmacies"://Pharmacies
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("Pharmacies & Home\nMedical Equipment");
                    callFragment("PHARMACY", fragmentPharmacy);
                    break;

                case "Finance,Insurance and Legal":
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("Finance, Legal, Other");
                    callFragment("FINANCE", fragmentFinance);
                    break;

                case "Prescription Information":
                    header.setBackgroundColor(getResources().getColor(R.color.colorPrescriptionGray));
                    txtTitle.setText("Prescription Information");
                    callFragment("Prescription Information", fragmentPrescriptionInfo);
                    break;

                case "Prescription List Upload":
                    header.setBackgroundColor(getResources().getColor(R.color.colorPrescriptionGray));
                    txtTitle.setText("Prescription List Upload");
                    callFragment("Prescription List Upload", fragmentPrescriptionUpload);
                    break;

                case "Vital Signs":
                    header.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                    txtTitle.setText("Vital Signs");
                    callFragment("Vital Signs", fragmentVitalSigns);
                    break;
            }
        }
    }

    private void callFragment(String fragName, Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();
    }

    /**
     * Function: Initialize Fragment
     */
    private void fragmentData() {
        fragmentInsurance = new FragmentInsurance();
        fragmentSpecialist = new FragmentSpecialist();
        fragmentFinance = new FragmentFinance();
        fragmentPharmacy = new FragmentPharmacy();
        fragmentHospital = new FragmentHospital();
        fragmentPrescriptionInfo = new FragmentPrescriptionInfo();
        fragmentVitalSigns = new FragmentVitalSigns();
        fragmentPrescriptionUpload = new FragmentPrescriptionUpload();
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgHome.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        header = findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorThree);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        txtTitle = findViewById(R.id.txtTitle);
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;

            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
        }
    }
}
