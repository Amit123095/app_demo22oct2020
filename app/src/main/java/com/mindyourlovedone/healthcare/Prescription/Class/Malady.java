package com.mindyourlovedone.healthcare.Prescription.Class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.Prescription.Prescription_List_Activity;


public class Malady extends FragmentActivity {

    private Context context;
    private ImageView imgBacks;
    private String[] Malady = {"Arthritis","Phenelzine","Rasagiline","Tranylaypromine","Tranylaypromine","Tramadol"};
    private ListView listMalady;
    private ImageView imgBack;
    private TextView tv_listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_acvtlist_layout);

        imgBack = findViewById(R.id.imgBacks);
        listMalady = findViewById(R.id.actvList);
        tv_listName = findViewById(R.id.tv_listName);

        ArrayAdapter adapter_aCTV = new ArrayAdapter(this,R.layout.row_actvlist,R.id.tv_actvListName,Malady);
        listMalady.setAdapter(adapter_aCTV);
        listMalady.deferNotifyDataSetChanged();

        tv_listName.setText("Maladies");

        listMalady.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             Intent intent = new Intent(Malady.this, Prescription_List_Activity.class);
             Malady.this.startActivity(intent);
             finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Malady.this, Prescription_List_Activity.class);
                Malady.this.startActivity(intent);
            }
        });
    }
}
