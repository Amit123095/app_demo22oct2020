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
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentInsurance;

/**
 * Class: InsuranceInfoActivity
 * Screen: Insurance SubSection Screen
 * A class that manages to display list of subsection of Speciality Contacts
 * implements OnclickListener for onClick event on views
 */
public class InsuranceInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentInsurance fragmentInsurance = null;
    FragementInsuarnceCard fragementInsuarnceCard = null;
    FragementForm fragementform = null;
    ImageView imgBack, imgHome;
    TextView txtTitle;
    RelativeLayout header;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_info);
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        //initialize Fragment
        fragmentData();

        //Initialize database, get primary data and set data
        initComponent();
    }

    /**
     * Function: Initialize Subsection screen
     */
    private void initComponent() {
        Intent i = getIntent();
        if (i.getExtras() != null) {
            String fragment = i.getExtras().getString("FRAGMENT");
            switch (fragment) {
                case "Insurance Info":
                    txtTitle.setText("Insurance Companies");
                    callFragment("INFO", fragmentInsurance);
                    break;
                case "INSURANCE CARD":
                    txtTitle.setText("Insurance Cards");
                    callFragment("CARD", fragementInsuarnceCard);
                    break;
                case "Insurance Form":
                    txtTitle.setText("Insurance Forms");
                    callFragment("FORM", fragementform);
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
        fragementInsuarnceCard = new FragementInsuarnceCard();
        fragmentInsurance = new FragmentInsurance();
        fragementform = new FragementForm();
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
        header = findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorFive);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("INSURANCE");
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
