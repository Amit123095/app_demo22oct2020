package com.mindyourlovedone.healthcare.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.TypeAdapter;
/**
 * Class: RelationshipActivity
 * Screen: Select Specialist or Test Tab Screen
 * A class that manages tabs to select Specialist or test
 * implements OnclickListener for onClick event on views
 */
public class RelationshipActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack, imgHome;
    TextView txtType, txtSpecialist, txtTitles;
    RelativeLayout rlType, rlSpecialist;
    Context context = this;
    TypeAdapter rd;
    ListView lvType, lvSpecialist;
    String Type[] = {"Blood Work", "Colonoscopy ", "CT Scan", "Echocardiogram", "EKG", "Glucose Test", "Hyperthyroid Blood Test", "Hypothyroid Blood Test", "Mammogram", "MRI", "Prostate Specific Antigen (PSA)", "Sonogram", "Thyroid Scan"};
    String Specialist[] = {"Acupuncturist", "Allergist (Immunologist)", "Anesthesiologist", "Audiologist", "Cardiologist", "Cardiothoracic Surgeon", "Chiropractor", "Colorectal Surgeon", "Cosmetic Surgeon", "Critical Care Medicine", "Dentist", "Dermatologist", "Dietitian/Nutritionist", "Diabetes & Metabolism", "Ear, Nose & Throat Doctor (ENT, Otolaryngologist)", "Emergency Medicine", "Endocrinologist (incl. Diabetes Specialists)", "Endodontics", "Endovascular Medicine", "Family Medicine", "Gastroenterologist", "Geriatrician", "Gynecologist", "Hearing Specialist", "Hematologist (Blood Specialist)", "Hospice", "Hospitalist", "Infectious Disease Specialist", "Infertility Specialist", "Internal Medicine", "Midwife", "Naturopathic Doctor", "Nephrologist (Kidney Specialist)", "Neurologist (Incl. Headache Specialist)", "Neurosurgeon", "OB-GYN (Obstetrician-Gynecologist)", "Occupational Therapist", "Oncologist", "Ophthalmologist", "Optometrist", "Oral Surgeon", "Orthodontist", "Orthopedic Surgeon (Orthopedist)", "Osteopath", "Otolaryngologist", "Pain Management Specialist", "Palliative Care Specialist", "Pediatric Dentist", "Pediatrician", "Periodontist", "Physician Assistant", "Physiatrist (Physical Medicine)", "Physical Therapist", "Plastic & Reconstructive Surgeon", "Podiatrist (Foot and Ankle Specialist)", "Primary Care Doctor (PCP)", "Prosthodontist", "Psychiatrist", "Psychologist", "Psychotherapist", "Pulmonologist (Lung Doctor)", "Radiologist", "Rheumatologist", "Sleep Medicine Specialist", "Speech Therapist", "Sports Medicine Specialist", "Surgeon - General", "Therapist/Counselor", "Thoracic & Cardiac Surgery", "Urgent Care Specialist", "Urological Surgeon", "Urologist", "Vascular Surgeon", "Other"};
    private static int RESULT_TYPE = 10;
    public static String selected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationship);
        //Initialize user interface view and components
        initUi();
        //Register a callback to be invoked when this views are clicked.
        initListener();
    }
    /**
     * Function: Initialize user interface view and components
     */
    private void initUi() {
        txtTitles = findViewById(R.id.txtTitles);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        rlType = findViewById(R.id.rlType);
        rlSpecialist = findViewById(R.id.rlSpecialist);
        lvType = findViewById(R.id.lvType);
        lvSpecialist = findViewById(R.id.lvSpecialist);
        rd = new TypeAdapter(context, Specialist);
        lvType.setAdapter(rd);
        lvType.setVisibility(View.VISIBLE);
        lvSpecialist.setVisibility(View.GONE);
        txtType = findViewById(R.id.txtType);
        txtSpecialist = findViewById(R.id.txtSpecialist);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            selected = intent.getExtras().getString("Selected");
            final String category = intent.getExtras().getString("Category");
            if (category != null) {
                switch (category) {
                    case "TypeSpecialist":
                        rd = new TypeAdapter(context, Type);
                        lvSpecialist.setAdapter(rd);
                        lvType.setVisibility(View.GONE);
                        lvSpecialist.setVisibility(View.VISIBLE);

                        break;
                    case "TypeAppointment":
                        rd = new TypeAdapter(context, Specialist);
                        lvType.setAdapter(rd);
                        lvType.setVisibility(View.VISIBLE);
                        lvSpecialist.setVisibility(View.GONE);
                        break;
                }
            }


            lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView txtType = view.findViewById(R.id.txtType);
                    Intent i1 = new Intent();

                    if (category.equalsIgnoreCase("TypeAppointment")) {
                        i1.putExtra("TypeAppointment", txtType.getText().toString());
                        setResult(RESULT_TYPE, i1);
                    }
                    finish();
                }
            });

            lvSpecialist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView txtType = view.findViewById(R.id.txtType);
                    Intent i1 = new Intent();
                    if (category.equalsIgnoreCase("TypeAppointment")) {
                        i1.putExtra("TypeAppointment", txtType.getText().toString());
                        setResult(RESULT_TYPE, i1);
                    }
                    finish();
                }
            });

        }
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        txtSpecialist.setOnClickListener(this);
        txtType.setOnClickListener(this);
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHome:// Navigate to home screen
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;

            case R.id.imgBack:// Navigate to back screen
                finish();
                break;

            case R.id.txtType: // List of specialist
                txtTitles.setText("Select Specialist");
                txtType.setTextColor(getResources().getColor(R.color.colorWhite));
                txtType.setBackgroundResource(R.drawable.border_type);
                txtSpecialist.setTextColor(getResources().getColor(R.color.colorBlue));
                txtSpecialist.setBackgroundResource(R.drawable.border_specialist);
                rlType.setVisibility(View.VISIBLE);
                rd = new TypeAdapter(context, Specialist);
                lvType.setAdapter(rd);
                lvType.setVisibility(View.VISIBLE);
                lvSpecialist.setVisibility(View.GONE);
                break;

            case R.id.txtSpecialist: // Lisr of test
                txtTitles.setText("Select Test");
                txtType.setTextColor(getResources().getColor(R.color.colorBlue));
                txtType.setBackgroundResource(R.drawable.border_type2);
                txtSpecialist.setTextColor(getResources().getColor(R.color.colorWhite));
                txtSpecialist.setBackgroundResource(R.drawable.border_specialist1);
                rlType.setVisibility(View.GONE);
                rlSpecialist.setVisibility(View.VISIBLE);
                rd = new TypeAdapter(context, Type);
                lvSpecialist.setAdapter(rd);
                lvType.setVisibility(View.GONE);
                lvSpecialist.setVisibility(View.VISIBLE);
                break;
        }
    }
}
