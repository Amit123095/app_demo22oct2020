package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.SpecialistAdapter;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.model.Specialist;

import java.util.ArrayList;

public class EmergencyActivity extends AppCompatActivity implements View.OnClickListener {
    static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    Context context = this;
    ImageView imgBack, img1, img2, img3;

    LocationRequest locationRequest;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    Marker LoactionMarker;
    RelativeLayout rlMaps;
    ArrayList<Emergency> EmergencyList;
    ArrayList<Specialist> specialistList;
    RecyclerView lvPrimary;
    RecyclerView lvEmergency;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        initUI();
        getEmergencyData();
        getPhysicianData();
        Intent intent = getIntent();

        initUI();
        initListener();
        setEmergencyAdapter();
        setSpecilistAdapter();
    }

    private void setSpecilistAdapter() {
        SpecialistAdapter specialistAdapter = new SpecialistAdapter(context, specialistList);
        lvPrimary.setAdapter(specialistAdapter);
    }

    private void getPhysicianData() {
        specialistList = new ArrayList<>();

        Specialist P1 = new Specialist();
        P1.setName("Dr. John");
        P1.setType("Orthopedic");
        P1.setAddress("#203,10 los Street, los Angeles, California.");
        P1.setImage(R.drawable.doct);
        P1.setOfficePhone("789-789-5236");

        specialistList.add(P1);
    }

    private void setEmergencyAdapter() {
        EmergencyAdapter emergencyAdapter = new EmergencyAdapter(context, EmergencyList);
        lvEmergency.setAdapter(emergencyAdapter);
    }

    private void getEmergencyData() {
        EmergencyList = new ArrayList<>();

        Emergency P1 = new Emergency();
        P1.setName("Mary Charlo");
        P1.setRelationType("Daughter");
        P1.setAddress("#203,10 Downing Street, los Angeles, California.");
        P1.setImage(R.drawable.profile_rounds);
        P1.setPhone("987-789-5236");

        Emergency P2 = new Emergency();
        P2.setName("Chuck Charlo");
        P2.setRelationType("Husband");
        P2.setAddress("#203,10 Downing Street, los Angeles, California.");
        P2.setImage(R.drawable.profile_circle);
        P2.setPhone("789-789-5236");

        EmergencyList.add(P1);
        EmergencyList.add(P2);
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
    }

    private void initUI() {
        imgBack = findViewById(R.id.imgBack);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        rlMaps = findViewById(R.id.rlMaps);
        lvEmergency = findViewById(R.id.lvEmergency);
        lvPrimary = findViewById(R.id.lvPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                finish();
                break;
            case R.id.img1:
                Intent in = new Intent(context, ZoomActivity.class);
                startActivity(in);
                break;
            case R.id.img2:
                Intent inte = new Intent(context, ZoomActivity.class);
                startActivity(inte);
                break;
            case R.id.img3:
                Intent intent = new Intent(context, ZoomActivity.class);
                startActivity(intent);
                break;
        }
    }


    private boolean checkRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }

    }

}
