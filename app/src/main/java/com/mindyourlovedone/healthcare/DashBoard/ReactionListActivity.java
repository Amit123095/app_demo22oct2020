package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.Connections.RelationActivity;
import com.mindyourlovedone.healthcare.Connections.RelationsAdapter;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReactionListActivity extends AppCompatActivity {
    public static final int REQUEST_REACTION = 800;
    ListView listRelation;
    RelativeLayout titleheaders;
    TextView txtTitles, txtSave;
    Context context = this;
    ImageView imgBack, imgHome;
    String category = "";
    String selected = "";
    ArrayList<String> selectedList = new ArrayList();
    String[] reactionList = {"Anaphylaxis", "Chest pain", "Congestion", "Difficulty Breathing", "Hives", "Itching", "Mucus", "Nausea", "Rash", "Runny nose", "Sneezing", "Vomiting", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_list);
        initUi();
        initListener();
    }

    private void initListener() {

    }

    private void initUi() {
        titleheaders = findViewById(R.id.headers);
        txtTitles = findViewById(R.id.txtTitles);
        txtSave = findViewById(R.id.txtSave);
        listRelation = findViewById(R.id.listRelation);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            category = i.getStringExtra("Category");
            if (getIntent().hasExtra("Selected")) {
                selected = i.getStringExtra("Selected");
                if (selected.contains("--")) {
                    selected = "";
                }
                if (selected.contains(",")) {
                    String[] elements = selected.split(",");
                    List<String> fixedLenghtList = Arrays.asList(elements);
                    selectedList = new ArrayList<String>(fixedLenghtList);
                } else {
                    selectedList = new ArrayList<>();
                    if (!selected.equals(""))
                        selectedList.add(selected);
                }

            }
            if (category.equalsIgnoreCase("Reaction")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Reaction");
                ReactionAdapter rd = new ReactionAdapter(context, reactionList, selectedList);
                listRelation.setAdapter(rd);
            }
        }

/*listRelation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            TextView txtRel = view.findViewById(R.id.txtRel);
            CheckBox imgCheck=  view.findViewById(R.id.imgCheck);
            imgCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        selectedList.add(reactionList[position]);
                    }else{
                        selectedList.remove(reactionList[position]);
                    }
                    //imgCheck.setTag(position);
                }
            });
            *//*Intent i = new Intent();
            if (category.equalsIgnoreCase("Category")) {
                i.putExtra("Category", txtRel.getText().toString());
                setResult(REQUEST_REACTION, i);
            }
            finish();*//*
        }
    });*/

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : selectedList) {
                        sb.append(s).append(",");
                    }
                    selected = sb.deleteCharAt(sb.length() - 1).toString();
                } else {
                    selected = "";
                }

 /* for (int i=0;i<selectedList.size();i++){
                selected = selectedList.get(i).toString();
                if (selectedList.size()>1) {
                    selected=selected+","+selectedList.get(i).toString();
                }else
                {

                }
            }*/
                Intent i = new Intent();
                if (category.equalsIgnoreCase("Reaction")) {
                    i.putExtra("Category", selected);
                    setResult(REQUEST_REACTION, i);
                }
                finish();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : selectedList) {
                        sb.append(s).append(",");
                    }
                    selected = sb.deleteCharAt(sb.length() - 1).toString();
                } else {
                    selected = "";
                }

                Intent i = new Intent();
                if (category.equalsIgnoreCase("Reaction")) {
                    i.putExtra("Category", selected);
                    setResult(REQUEST_REACTION, i);
                }
                finish();
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               /* intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                startActivity(intentHome);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    public void addList(String s, boolean b) {
        if (b == true) {
            selectedList.add(s);
        } else {
            selectedList.remove(s);
        }
    }
}
