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


public class Medicine extends FragmentActivity {

    private Context context;
    private ImageView imgBacks;
    private String[] Medicine = {"Acetaminophen 50mg","Cyclobenzaprine 50mg","Kevzara 50mg","Ozempic 50mg","Tramadol 50mg","Tramadol 150mg"};
    private ListView listMedicine;
    private ImageView imgBack;
    private TextView tv_listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_acvtlist_layout);

        imgBack = findViewById(R.id.imgBacks);
        listMedicine = findViewById(R.id.actvList);
        tv_listName = findViewById(R.id.tv_listName);

        ArrayAdapter adapter_aCTV = new ArrayAdapter(this,R.layout.row_actvlist, R.id.tv_actvListName,Medicine);
        listMedicine.setAdapter(adapter_aCTV);
        listMedicine.deferNotifyDataSetChanged();

        tv_listName.setText("Medication");

        listMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Medicine.this, Prescription_List_Activity.class);
                Medicine.this.startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Medicine.this, Prescription_List_Activity.class);
                Medicine.this.startActivity(intent);
            }
        });
    }
}
