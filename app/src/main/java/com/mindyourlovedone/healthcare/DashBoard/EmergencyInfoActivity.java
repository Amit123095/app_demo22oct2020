package com.mindyourlovedone.healthcare.DashBoard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;


public class EmergencyInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    Context context = this;
    FragmentIndividualContact fragmentIndividualContact = null;

    FragmentProxy fragmentProxy = null;
    FragmentEmergency fragmentEmergency = null;
    FragmentMedicalInfo fragmentMedicalInfo = null;
    FragmentPhysician fragmentPhysician = null;
    FragmentLiving fragmentLiving = null;
    ImageView imgBack, imgRight, imgHome;
    TextView txtTitle, txtsave;
    RelativeLayout header;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_info);
        initUI();
        initListener();
        fragmentData();
        initComponent();
    }

    private void initComponent() {
        Intent i = getIntent();
        if (i.getExtras() != null) {
            String fragment = i.getExtras().getString("FRAGMENT");
            switch (fragment) {
                case "Individual":
                     /* callFragment("INDIVIDUAL", fragmentIndividualContact);
                     imgRight.setVisibility(View.VISIBLE);
                     Intent intent=new Intent(context,ProfileActivity.class);
                      startActivity(intent);*/
                    break;
                case "Information":
                    header.setBackgroundColor(getResources().getColor(R.color.colorRegisteredGreen));
                    callFragment("INFORMATION", fragmentMedicalInfo);
                    imgRight.setVisibility(View.VISIBLE);
//                    txtsave.setVisibility(View.VISIBLE);
                    break;
                case "Emergency":
                    header.setBackgroundColor(getResources().getColor(R.color.colorRegisteredGreen));
                    callFragment("EMERGENCY", fragmentEmergency);
                    imgRight.setVisibility(View.VISIBLE);
                    break;
                case "Physician":
                    header.setBackgroundColor(getResources().getColor(R.color.colorRegisteredGreen));
                    callFragment("PHYSICIAN", fragmentPhysician);
                    imgRight.setVisibility(View.VISIBLE);
                    break;
                case "Functional":
                    callFragment("FUNCTIONAL", fragmentLiving);
                    imgRight.setVisibility(View.VISIBLE);

                    break;
                case "Proxy":
                    callFragment("PROXY", fragmentProxy);
                    imgRight.setVisibility(View.VISIBLE);
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
        fragmentEmergency = new FragmentEmergency();
        fragmentIndividualContact = new FragmentIndividualContact();
        fragmentMedicalInfo = new FragmentMedicalInfo();
        fragmentPhysician = new FragmentPhysician();
        fragmentProxy = new FragmentProxy();
        fragmentLiving = new FragmentLiving();
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
    }

    private void initUI() {
        txtsave = findViewById(R.id.txtsave);
        header = findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorOne);
        imgRight = findViewById(R.id.imgRight);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("PERSONAL AND MEDICAL PROFILE AND EMERGENCY CONTACTS");
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               /* intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                startActivity(intentHome);
                break;
            case R.id.imgBack:
                hideSoftKeyboard();
                finish();
                break;
        }
    }


    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


}
