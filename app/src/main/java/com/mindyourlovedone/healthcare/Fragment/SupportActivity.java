package com.mindyourlovedone.healthcare.Fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.ResourceAdapter;
import com.mindyourlovedone.healthcare.model.ResourcesNew;
import com.mindyourlovedone.healthcare.utility.WebPDFActivity;
import com.mindyourlovedone.healthcare.webservice.WebService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Class: SupportActivity
 * Screen: User guide And Support Faq as Well Ende User License  Screen
 * A class that manages User guide And Support Faq list, End user And License,Privcy policy
 */
public class SupportActivity extends AppCompatActivity {

    Context context = this;
    ArrayList<ResourcesNew> supportList, enduserList;
    ListView lvSupport, lvEndUser;
    TextView txtTitle, txtName;
    ImageView imgDrawer, imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        //Initialize user interface view and components
        initUI();

        //Define List
        getData();

        //Set List
        setData();

    }

    private void setData() {
        //User uide list
        ResourceAdapter ud = new ResourceAdapter(context, supportList);
        lvSupport.setAdapter(ud);

        //EULA list
        ResourceAdapter ed = new ResourceAdapter(context, enduserList);
        lvEndUser.setAdapter(ed);
    }

    /**
     * Function: Fetch all Supportlist data
     */
    private void getData() {

        supportList = new ArrayList<ResourcesNew>();
        enduserList = new ArrayList<ResourcesNew>();

        ResourcesNew u1 = new ResourcesNew();
        u1.setName("Support FAQs");
        u1.setResImage(R.drawable.faq);

        ResourcesNew u2 = new ResourcesNew();
        u2.setName("User Guide");
        u2.setResImage(R.drawable.useruide);

        supportList.add(u1);
        supportList.add(u2);


        ResourcesNew e2 = new ResourcesNew();
        e2.setName("End User License Agreement");
        e2.setResImage(R.drawable.enduser);

        ResourcesNew e1 = new ResourcesNew();
        e1.setName("Privacy Policy");
        e1.setResImage(R.drawable.enduser);

        enduserList.add(e2);
        enduserList.add(e1);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtTitle = findViewById(R.id.txtTitle);
        lvSupport = findViewById(R.id.lvSupport);
        lvEndUser = findViewById(R.id.lvEndUser);
        Intent i = getIntent();
        String from = i.getExtras().getString("FROM");
        if (from.equals("Support")) {
            lvSupport.setVisibility(View.VISIBLE);
            lvEndUser.setVisibility(View.GONE);
            txtTitle.setText("Support FAQs and User Guide");

        } else if (from.equals("EndUser")) {
            lvSupport.setVisibility(View.GONE);
            lvEndUser.setVisibility(View.VISIBLE);
            txtTitle.setText("End User License Agreement \nand Privacy Policy");
        }
        lvSupport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://Support Faq
                        Intent browserIntent = new Intent(context, WebPDFActivity.class);
                        browserIntent.putExtra("Name", "Support FAQs");
                        browserIntent.putExtra("URL", WebService.FAQ_URL);
                        startActivity(browserIntent);

                        break;
                    case 1://User Guide-Section
                        Intent browserIntents = new Intent(context, WebPDFActivity.class);
                        browserIntents.putExtra("Name", "User Guide");
                        browserIntents.putExtra("URL", WebService.USERGUIDE_URL);
                        startActivity(browserIntents);
                        break;
                }
            }
        });
        lvEndUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://End User License Agreement-Section
                        Intent browserIntentD = new Intent(SupportActivity.this, WebPDFActivity.class);
                        browserIntentD.putExtra("Name", "End User License Agreement");
                        browserIntentD.putExtra("URL", WebService.EULA_URL);
                        startActivity(browserIntentD);
                        break;
                    case 1://Privacy Policy-Section
                        Intent browserIntentD2 = new Intent(SupportActivity.this, WebPDFActivity.class);
                        browserIntentD2.putExtra("Name", "Privacy Policy");
                        browserIntentD2.putExtra("URL", WebService.PRIVACY_URL);
                        startActivity(browserIntentD2);

                        break;
                }
            }
        });

    }
}
