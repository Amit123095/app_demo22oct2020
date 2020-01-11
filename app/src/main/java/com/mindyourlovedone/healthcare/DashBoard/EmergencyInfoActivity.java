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

/**
 * Class: EmergencyInfoActivity
 * Screen: Personal Info and medical info section list
 * A class that manages to display list of subsection of Personal info and medical info
 * implements OnclickListener for onClick event on views
 */
public class EmergencyInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    Context context = this;

    FragmentEmergency fragmentEmergency = null;
    FragmentMedicalInfo fragmentMedicalInfo = null;
    FragmentPhysician fragmentPhysician = null;
    ImageView imgBack, imgRight, imgHome;
    TextView txtTitle, txtsave;
    RelativeLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_info);
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        //Initialize Fragment data
        fragmentData();

        //Initialize database, get primary data and set data
        initComponent();
    }

    /**
     * Function: Initialize fragments to list options
     */
    private void initComponent() {
        Intent i = getIntent();
        if (i.getExtras() != null) {
            String fragment = i.getExtras().getString("FRAGMENT");
            switch (fragment) {
                case "Information":// Navigate to Medical Information
                    header.setBackgroundColor(getResources().getColor(R.color.colorRegisteredGreen));
                    callFragment("INFORMATION", fragmentMedicalInfo);
                    imgRight.setVisibility(View.VISIBLE);
                    break;
                case "Emergency":// Navigate to Emergency Contact
                    header.setBackgroundColor(getResources().getColor(R.color.colorRegisteredGreen));
                    callFragment("EMERGENCY", fragmentEmergency);
                    imgRight.setVisibility(View.VISIBLE);
                    break;
                case "Physician":// Navigate to Physician Contact
                    header.setBackgroundColor(getResources().getColor(R.color.colorRegisteredGreen));
                    callFragment("PHYSICIAN", fragmentPhysician);
                    imgRight.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * Function: Attach related fragment
     * @param fragName
     * @param fragment
     */
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
        fragmentEmergency = new FragmentEmergency();
        fragmentMedicalInfo = new FragmentMedicalInfo();
        fragmentPhysician = new FragmentPhysician();
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
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

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                break;
            case R.id.imgBack:
                hideSoftKeyboard();
                finish();
                break;
        }
    }


    /**
     * Function: Hide device keyboard.
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


}
