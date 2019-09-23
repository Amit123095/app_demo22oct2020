package com.mindyourlovedone.healthcare.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Links;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {
    Context context=this;
    ArrayList<String> Datalist;
    ArrayList<Links> UrlList;
    ListView list;
    TextView txtTitle, txtName;
    ImageView imgNoti, imgProfile, imgBack, imgPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initUI();
        initListener();
        getData();
        setData();

    }

    private void setData() {
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.row_video, R.id.txtName, Datalist);
        list.setAdapter(adapter);
    }

    private void getData() {
        UrlList = new ArrayList<>();
        Links l1 = new Links();
        l1.setName("Overview | Aging Matters | NPT Reports");
        l1.setUrl("https://www.youtube.com/watch?v=CyIepl3V4Ro");
        UrlList.add(l1);

        Links l2 = new Links();
        l2.setName("Introduction to MYLO");
        l2.setUrl("https://youtu.be/FSHKcKzecTQ");
        UrlList.add(l2);

        Links l3 = new Links();
        l3.setName("How to choose a health care proxy agent");
        l3.setUrl("https://youtu.be/iTxv-20ULwQ");
        UrlList.add(l3);

        Links l4 = new Links();
        l4.setName("How to Choose a Health Care Proxy & How to Be a Health Care Proxy");
        l4.setUrl("https://youtu.be/fOhg2KrzL_I");
        UrlList.add(l4);

        //Fol show
        Datalist = new ArrayList<>();
        Datalist.add("Overview | Aging Matters | NPT Reports");
        Datalist.add("Introduction to MYLO");
        Datalist.add("How to choose a health care proxy agent");
        Datalist.add("How to Choose a Health Care Proxy & How to Be a Health Care Proxy");

    }

    private void initListener() {

    }

    private void initUI() {
//        txtTitle = findViewById(R.id.txtTitle);
//        txtTitle.setVisibility(View.VISIBLE);
//        txtTitle.setText("VIDEOS");
//        imgPdf = findViewById(R.id.imgPdf);
//        imgPdf.setVisibility(View.GONE);
//        imgProfile = findViewById(R.id.imgProfile);
//        txtName = findViewById(R.id.txtName);
//        txtName.setVisibility(View.GONE);
//        imgProfile.setVisibility(View.GONE);
//        imgNoti = findViewById(R.id.imgNoti);
//        imgNoti.setVisibility(View.GONE);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        list = findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Datalist.get(position).equals(UrlList.get(position).getName())) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(UrlList.get(position).getUrl()));
                    startActivity(intent);
                }
            }
        });
    }


}
