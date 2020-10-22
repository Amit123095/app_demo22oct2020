package com.mindyourlovedone.healthcare.Appointment.Class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.Appointment.AppointmentsList_Activity;
import com.mindyourlovedone.healthcare.HomeActivity.R;



public class Doctor_list extends FragmentActivity {

    private Context context;
    private ImageView imgBacks;
    private String[] Doctor = {"Dr. Olga Conner","Dr. Violet Stone","Dr. Clyde Moore","Dr. Lewis Vasquez","Dr. Olivia Cunningham","Dr. Jim Conner"};
    private ListView listDoctor;
    private ImageView imgAppBacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_actvlist_display);

        imgAppBacks = findViewById(R.id.imgAppBacks);
        listDoctor = findViewById(R.id.actvLists);

        ArrayAdapter adapter_aCTV = new ArrayAdapter(getApplication(),R.layout.row_actvlist,R.id.tv_actvListName,Doctor);
        listDoctor.setAdapter(adapter_aCTV);
        listDoctor.deferNotifyDataSetChanged();

        listDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "still working", Toast.LENGTH_SHORT).show();
              /*  Intent intent = new Intent(Doctor_list.this,);
                Doctor_list.this.startActivity(intent);*/
            }
        });
        imgAppBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_list.this, AppointmentsList_Activity.class);
                Doctor_list.this.startActivity(intent);
            }
        });
    }
}
