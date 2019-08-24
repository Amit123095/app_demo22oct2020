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
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentAids;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentFinance;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentHospital;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentInsurance;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentPharmacy;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentPrescriptionInfo;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentSpecialist;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentVitalSigns;

public class InsuranceActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentInsurance fragmentInsurance = null;
    FragmentSpecialist fragmentSpecialist = null;
    FragmentAids fragmentAids = null;
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

    //  String[] specialist = { "Insurance", "DOCTORS", "HOME HEALTH SERVICES", "FINANCE, INSURANCE & LEGAL" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        initUI();

        initListener();
        fragmentData();
        initComponent();
        //  callFragment("INSURANCE", fragmentInsurance);
        //  if (fragmentManager.findFragmentByTag("INSURANCE") == null) {
        //    callFragment("INSURANCE", fragmentInsurance);
        // }
    }

    private void initComponent() {
        Intent i = getIntent();
        if (i.getExtras() != null) {
            String fragment = i.getExtras().getString("FRAGMENT");
            switch (fragment) {
                case "Insurance":
                    txtTitle.setText("INSURANCE");
                    callFragment("INSURANCE", fragmentInsurance);
                    break;
                case "Doctors":
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("Doctors & Other Health\nCare Professionals");
                    callFragment("SPECIALIST", fragmentSpecialist);
                    break;

                case "Hospitals":
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                    callFragment("HOSPITAL", fragmentHospital);
                    break;
                case "Pharmacies":
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("Pharmacies & Home\nMedical Equipment");
                    callFragment("PHARMACY", fragmentPharmacy);
                    break;
                case "Home Health Services":
                    header.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                    txtTitle.setText("HOME HEALTH SERVICES");
                    callFragment("AIDS", fragmentAids);
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

    private void fragmentData() {
        fragmentInsurance = new FragmentInsurance();
        fragmentSpecialist = new FragmentSpecialist();
        fragmentAids = new FragmentAids();
        fragmentFinance = new FragmentFinance();
        fragmentPharmacy = new FragmentPharmacy();
        fragmentHospital = new FragmentHospital();
        fragmentPrescriptionInfo = new FragmentPrescriptionInfo();
        fragmentVitalSigns = new FragmentVitalSigns();
        fragmentPrescriptionUpload = new FragmentPrescriptionUpload();
    }

    private void initListener() {
        imgHome.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        header = findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorThree);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        txtTitle = findViewById(R.id.txtTitle);
        // spinner=(Spinner) findViewById(R.id.spinner);


       /* ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,specialist);
        aa.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),specialist[position] ,Toast.LENGTH_LONG).show();
                switch (specialist[position] )
                {
                    case "Insurance":
                       // if (fragmentManager.findFragmentByTag("INSURANCE") == null) {
                            callFragment("INSURANCE", fragmentInsurance);
                       // }
                        break;
                    case "Doctors":
                      //  if (fragmentManager.findFragmentByTag("SPECIALIST") == null) {
                            callFragment("SPECIALIST", fragmentSpecialist);
                     //   }
                        break;
                    case "Health Aides":
                    //    if (fragmentManager.findFragmentByTag("AIDS") == null) {
                            callFragment("AIDS", fragmentAids);
                      //  }
                        break;
                    case "Finance and Legal":
                        callFragment("FINANCE", fragmentFinance);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

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
