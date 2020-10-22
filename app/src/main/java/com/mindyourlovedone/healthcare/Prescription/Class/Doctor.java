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


public class Doctor extends FragmentActivity {

    private Context context;
    private ImageView imgBacks;
    private String[] Doctor = {"Dr. Olga Conner","Dr. Violet Stone","Dr. Clyde Moore","Dr. Lewis Vasquez","Dr. Olivia Cunningham","Dr. Jim Conner"};
    private ListView listDoctor;
    private ImageView imgBack;
    private TextView tv_listName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_acvtlist_layout);

        imgBack = findViewById(R.id.imgBacks);
        listDoctor = findViewById(R.id.actvList);
        tv_listName = findViewById(R.id.tv_listName);

        ArrayAdapter adapter_aCTV = new ArrayAdapter(getApplication(),R.layout.row_actvlist,R.id.tv_actvListName,Doctor);
        listDoctor.setAdapter(adapter_aCTV);
        listDoctor.deferNotifyDataSetChanged();

        tv_listName.setText("Prescribing Doctor");

        listDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Doctor.this, Prescription_List_Activity.class);
                Doctor.this.startActivity(intent);
            }
        });
       imgBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Doctor.this, Prescription_List_Activity.class);
               Doctor.this.startActivity(intent);
           }
       });
    }
}
