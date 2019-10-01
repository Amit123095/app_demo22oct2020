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

    private void fragmentData() {
        fragementInsuarnceCard = new FragementInsuarnceCard();
        fragmentInsurance = new FragmentInsurance();
        fragementform = new FragementForm();
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
    }

    private void initUI() {
        header = findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorFive);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("INSURANCE");
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
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
