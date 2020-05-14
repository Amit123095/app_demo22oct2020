package com.mindyourlovedone.healthcare.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.DashBoard.AddInfoActivity;
import com.mindyourlovedone.healthcare.HomeActivity.LinkAdapter;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Links;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.utility.WebPDFActivity;

import java.util.ArrayList;

/**
 * Class: ADInfoActivity
 * Screen: Advance Directive Information Screen
 * A class that manages Advance Directive Information list
 */
public class ADInfoActivity extends AppCompatActivity {
    Context context = this;
    ArrayList<Links> UrlList;
    ListView list;
    ImageView imgBack;
    Preferences preferences;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adinfo);
        preferences=new Preferences(context);

        //Initialize user interface view and components
        initUI();

        //Define list data
        getData();

        //Set list data
        setData();
    }

    /**
     * Function: Set List Data
     */
    private void setData() {
        LinkAdapter adapter = new LinkAdapter(context, UrlList);
        list.setAdapter(adapter);
    }

    /**
     * Function: Define all Advance directive list data
     */
    private void getData() {
        UrlList = new ArrayList<>();

        Links l4 = new Links();
        l4.setName("AARP links to Advance Directive Forms by State (PDF)");
        l4.setUrl("https://www.aarp.org/caregiving/financial-legal/free-printable-advance-directives/");
        l4.setImage(R.drawable.link_one);

        Links l5 = new Links();
        l5.setName("Aging with Dignity, Five Wishes");
        l5.setUrl("https://www.agingwithdignity.org/");
        l5.setImage(R.drawable.aging);

        Links l11 = new Links();
        l11.setName("ABA-American Bar Association, Commission on Law and Aging");
        l11.setUrl("https://www.americanbar.org/groups/law_aging/resources/health_care_decision_making/consumer_s_toolkit_for_health_care_advance_planning/");
        l11.setImage(R.drawable.aba_market_new);

        Links l7 = new Links();
        l7.setName("American Hospital Association, Put It In Writing");
        l7.setUrl("http://www.aha.org/advocacy-issues/initiatives/piiw/index.shtml");
        l7.setImage(R.drawable.link_three);

        Links l8 = new Links();
        l8.setName("Caring Connections links to Advance Directive Forms by State (PDF)");
        l8.setUrl("http://www.caringinfo.org/i4a/pages/index.cfm?pageid=3289");
        l8.setImage(R.drawable.care);

        Links l9 = new Links();
        l9.setName("Center for Practical Bioethics, Caring Conversations");
        l9.setUrl("https://www.practicalbioethics.org/resources/caring-conversations.html");
        l9.setImage(R.drawable.link_five);

        Links l10 = new Links();
        l10.setName("National Healthcare Decisions Day (NHDD),  Advance Care Planning");
        l10.setUrl("https://www.nhdd.org/public-resources/#where-can-i-get-an-advance-directive");
        l10.setImage(R.drawable.link_six);

        Links l12 = new Links();
        l12.setName("PREPARE for Your Care™");
        l12.setUrl("https://www.prepareforyourcare.org");
        l12.setImage(R.drawable.prepare);

        UrlList.add(l11);
        UrlList.add(l4);
        UrlList.add(l5);
        UrlList.add(l7);
        UrlList.add(l8);
        UrlList.add(l9);
        UrlList.add(l10);
        UrlList.add(l12);

    }


    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        txtTitle=findViewById(R.id.txtTitle);
        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            txtTitle.setText("Policy Number");
        }else
        {
            txtTitle.setText("Advance Directive Information");
        }
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
        list = findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntentD = new Intent(ADInfoActivity.this, WebPDFActivity.class);
                browserIntentD.putExtra("URL", UrlList.get(position).getUrl());
                browserIntentD.putExtra("Name", UrlList.get(position).getName());
                startActivity(browserIntentD);

            }
        });
    }
}
