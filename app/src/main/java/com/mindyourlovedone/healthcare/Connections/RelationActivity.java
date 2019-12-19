package com.mindyourlovedone.healthcare.Connections;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.model.Specialist;

public class RelationActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listRelation;
    RelativeLayout titleheaders;
    TextView txtTitles;
    Context context = this;
    ImageView imgBack,imgHome;
    String category = "";
    String selected = "";
    private static int RESULT_RELATION = 10;
    private static int RESULT_PRIORITY = 12;
    private static int RESULT_SPECIALTY = 13;
    private static int RESULT_CATEGORY = 14;
    private static int RESULT_FINANCECAT = 15;
    private static final int RESULT_INSURANCE = 16;
    private static final int RESULT_ADVANCE = 20;
    private static final int RESULT_OTHER = 30;
    private static final int RESULT_FREQUENCY = 110;
    public static final int REQUEST_RELATIONP = 21;
    public static final int REQUEST_MARITAL = 22;
    public static final int REQUEST_EYES = 23;
    public static final int REQUEST_BLOOD = 1;
    public static final int REQUEST_LANGUAGE = 24;
    private static final int RESULT_SPECIALTY_NETWORK = 17;
    private static final int RESULT_HOSPITAL_NETWORK = 18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);
        initUi();
        initListener();
    }

    private void initListener() {
    }

    private void initUi() {
        titleheaders=findViewById(R.id.headers);
        txtTitles = findViewById(R.id.txtTitles);
        listRelation = findViewById(R.id.listRelation);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            category = i.getStringExtra("Category");
            if (getIntent().hasExtra("Selected")) {
                selected = i.getStringExtra("Selected");
            }
            if (category.equalsIgnoreCase("Relation")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Relationship");
                String[] Relationship = {"Aunt", "Brother", "Brother-in-law", "Client", "Cousin", "Dad", "Daughter","Daughter-in-law", "Father-in-law", "Friend", "Granddaughter", "Grandmother", "Grandfather", "Grandson", "Husband", "Mom", "Mother-in-law", "Neighbor", "Nephew", "Niece", "Patient", "Roommate", "Significant Other", "Sister", "Sister-in-law", "Son","Son-in-law", "Uncle", "Wife", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, Relationship,selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Priority")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Priority");
                String[] priorityType = {"Primary Emergency Contact", "Primary Health Care Proxy Agent", "Secondary Emergency Contact", "Secondary Health Care Proxy Agent","Primary Emergency Contact and Health Care Proxy Agent"};
                RelationsAdapter rd = new RelationsAdapter(context, priorityType, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Specialty")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                txtTitles.setText("Specialty");
                String[] healthSpeciality = {"Acupuncturist", "Allergist (Immunologist)", "Anesthesiologist", "Audiologist", "Cardiologist", "Cardiothoracic Surgeon", "Chiropractor", "Colorectal Surgeon", "Cosmetic Surgeon", "Critical Care Medicine", "Dentist", "Dermatologist", "Dietitian/Nutritionist", "Diabetes & Metabolism", "Ear, Nose & Throat Doctor (ENT, Otolaryngologist)", "Emergency Medicine", "Endocrinologist (incl. Diabetes Specialists)", "Endodontics", "Endovascular Medicine", "Family Medicine", "Gastroenterologist", "Geriatrician", "Gynecologist", "Hearing Specialist", "Hematologist (Blood Specialist)", "Hospice","Hospitalist", "Infectious Disease Specialist", "Infertility Specialist", "Internal Medicine", "Midwife", "Naturopathic Doctor", "Nephrologist (Kidney Specialist)", "Neurologist (Incl. Headache Specialist)", "Neurosurgeon", "OB-GYN (Obstetrician-Gynecologist)", "Occupational Therapist", "Oncologist", "Ophthalmologist", "Optometrist", "Oral Surgeon", "Orthodontist", "Orthopedic Surgeon (Orthopedist)", "Osteopath", "Otolaryngologist", "Pain Management Specialist", "Palliative Care Specialist", "Pediatric Dentist", "Pediatrician", "Periodontist", "Physician Assistant", "Physiatrist (Physical Medicine)", "Physical Therapist", "Plastic & Reconstructive Surgeon", "Podiatrist (Foot and Ankle Specialist)", "Primary Care Doctor (PCP)", "Prosthodontist", "Psychiatrist", "Psychologist", "Psychotherapist", "Pulmonologist (Lung Doctor)", "Radiologist", "Rheumatologist", "Sleep Medicine Specialist", "Speech Therapist", "Sports Medicine Specialist", "Surgeon - General", "Therapist/Counselor", "Thoracic & Cardiac Surgery", "Urgent Care Specialist", "Urological Surgeon", "Urologist", "Vascular Surgeon", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, healthSpeciality, selected);
                listRelation.setAdapter(rd);
            }else if (category.equalsIgnoreCase("Physician")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Specialty");
                String[] healthSpeciality = {"Acupuncturist", "Allergist (Immunologist)", "Anesthesiologist", "Audiologist", "Cardiologist", "Cardiothoracic Surgeon", "Chiropractor", "Colorectal Surgeon", "Cosmetic Surgeon", "Critical Care Medicine", "Dentist", "Dermatologist", "Dietitian/Nutritionist", "Diabetes & Metabolism", "Ear, Nose & Throat Doctor (ENT, Otolaryngologist)", "Emergency Medicine", "Endocrinologist (incl. Diabetes Specialists)", "Endodontics", "Endovascular Medicine", "Family Medicine", "Gastroenterologist", "Geriatrician", "Gynecologist", "Hearing Specialist", "Hematologist (Blood Specialist)", "Hospice","Hospitalist", "Infectious Disease Specialist", "Infertility Specialist", "Internal Medicine", "Midwife", "Naturopathic Doctor", "Nephrologist (Kidney Specialist)", "Neurologist (Incl. Headache Specialist)", "Neurosurgeon", "OB-GYN (Obstetrician-Gynecologist)", "Occupational Therapist", "Oncologist", "Ophthalmologist", "Optometrist", "Oral Surgeon", "Orthodontist", "Orthopedic Surgeon (Orthopedist)", "Osteopath", "Otolaryngologist", "Pain Management Specialist", "Palliative Care Specialist", "Pediatric Dentist", "Pediatrician", "Periodontist", "Physician Assistant", "Physiatrist (Physical Medicine)", "Physical Therapist", "Plastic & Reconstructive Surgeon", "Podiatrist (Foot and Ankle Specialist)", "Primary Care Doctor (PCP)", "Prosthodontist", "Psychiatrist", "Psychologist", "Psychotherapist", "Pulmonologist (Lung Doctor)", "Radiologist", "Rheumatologist", "Sleep Medicine Specialist", "Speech Therapist", "Sports Medicine Specialist", "Surgeon - General", "Therapist/Counselor", "Thoracic & Cardiac Surgery", "Urgent Care Specialist", "Urological Surgeon", "Urologist", "Vascular Surgeon", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, healthSpeciality, selected);
                listRelation.setAdapter(rd);
            }else if (category.equalsIgnoreCase("PhysicianNetwork")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Network Status");
                String[] healthSpeciality = {"In-Network", "Out-of-Network"};
                RelationsAdapter rd = new RelationsAdapter(context, healthSpeciality, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("SpecialtyNetwork")) {
            titleheaders.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
            txtTitles.setText("Network Status");
            String[] healthSpeciality = {"In-Network", "Out-of-Network"};
            RelationsAdapter rd = new RelationsAdapter(context, healthSpeciality, selected);
            listRelation.setAdapter(rd);
        } else if (category.equalsIgnoreCase("Category")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                txtTitles.setText("Category");
                String[] HospitalType = {"Home Health Care Agency", "Home Health Care Aide","Hospital", "Rehabilitation Center", "TeleMed", "Urgent Care", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, HospitalType, selected);
                listRelation.setAdapter(rd);
            }else if (category.equalsIgnoreCase("HospitalNetwork")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                txtTitles.setText("Network Status");
                String[] healthSpeciality = {"In-Network", "Out-of-Network"};
                RelationsAdapter rd = new RelationsAdapter(context, healthSpeciality, selected);
                listRelation.setAdapter(rd);
            }
            else if (category.equalsIgnoreCase("Insurance")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSkyBlue));
                txtTitles.setText("Type of Insurance");
                String[] insuaranceType = {"Apartment", "Auto", "Dental", "Disability", "Home", "Life (Whole Life or Term)", "Long Term Care", "Medicaid", "Medical", "Medicare", "Medicare Supplemental (Medigap)", "Supplemental","Tricare", "Umbrella", "Vision", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, insuaranceType, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("finance")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                txtTitles.setText("Category");
                String[] financeType = {"Accountant", "Attorney", "Broker", "Financial Adviser", "Financial Planner", "Notary", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, financeType, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Advance")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                txtTitles.setText("Advance Directive Document Type");
                String[] ADList = {"HIPAA Authorization", "Health Care Proxy", "Hospital Do Not Resuscitate (DNR) Order", "Living Will", "Living Will/Health Care Proxy", "Medical Orders for Life-Sustaining Treatment (MOLST)", "Non-Hospital DNR Order", "Provider Orders for Life-Sustaining Treatment (POLST)", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, ADList, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Other")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                txtTitles.setText("Document Category");
                String[] OtherList = {"Financial", "Insurance", "Legal", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, OtherList, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("TypeFrequency")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                txtTitles.setText("Frequency");
                String[] Frequency = {"Annual", "Daily", "Every 5 Years", "Monthly", "Quarterly", "Semi-Annual", "Weekly", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, Frequency, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Relationp")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Blood Relationship");
                String[] Relationships = {"Aunt", "Brother", "Brother-in-law", "Client", "Cousin", "Dad", "Daughter","Daughter-in-law", "Father-in-law", "Friend", "Granddaughter", "Grandmother", "Grandfather", "Grandson", "Husband", "Mom", "Mother-in-law", "Neighbor", "Nephew", "Niece", "Patient", "Roommate", "Significant Other", "Sister", "Sister-in-law", "Son","Son-in-law", "Uncle", "Wife", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, Relationships, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Marital")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Marital Status");
                String[] MaritalList = {"Divorced", "Domestic Partner", "Married", "Separated", "Single", "Widowed","Other"};
                RelationsAdapter rd = new RelationsAdapter(context, MaritalList, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("language")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Language");
                String[] LangList = {"Arabic", "Chinese", "English", "French", "German", "Greek", "Hebrew", "Hindi", "Italian", "Japanese", "Korean", "Russian", "Spanish", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, LangList, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("eyes")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Eye Color");
                String[] EyesList = {"Blue","Brown","Green", "Hazel"};
                RelationsAdapter rd = new RelationsAdapter(context, EyesList, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Blood")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Blood Type");
                String[] BloodList = {"A negative", "A positive", "AB negative", "AB positive", "B negative", "B positive", "O negative", "O positive", "I don't know"};
                RelationsAdapter rd = new RelationsAdapter(context, BloodList, selected);
                listRelation.setAdapter(rd);
            } else if (category.equalsIgnoreCase("Medical")) {
                titleheaders.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                txtTitles.setText("Medical Implants");
                String[] implantList = {"Aneurysm Stent or Aneurysm Clip", "Artifical Limbs", "Artificial Heart Value", "Body Art/Tattoos", "Coronary Stents(Drug Coated/Bare Metal/Unknown)", "Metal Crowns, Fillings, Implants", "Gastric Band", "Body Piercing", "Implanted Cardio Defibrilator (ICD)", "Implanted Devices/Pumps/Stimulator", "Joint Replacements (specify)", "Lens Implants", "Metal Implants", "Middle Ear Prosthesis", "None", "Pacemaker", "Penile Implant", "Pins/Rods/Screws", "Prosthetic Eye", "Renal or other Stents", "Tracheotomy", "Other"};
                RelationsAdapter rd = new RelationsAdapter(context, implantList, selected);
                listRelation.setAdapter(rd);
            }
        }

        // RelationsAdapter rd = new RelationsAdapter(context, Relationship);
        //listRelation.setAdapter(rd);


        listRelation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtRel = view.findViewById(R.id.txtRel);
                Intent i = new Intent();
                if (category.equalsIgnoreCase("Relation")) {
                    i.putExtra("Relation", txtRel.getText().toString());
                    setResult(RESULT_RELATION, i);
                } else if (category.equalsIgnoreCase("Priority")) {
                    i.putExtra("Priority", txtRel.getText().toString());
                    setResult(RESULT_PRIORITY, i);
                } else if (category.equalsIgnoreCase("Specialty")) {
                    i.putExtra("Specialty", txtRel.getText().toString());
                    setResult(RESULT_SPECIALTY, i);
                }else if (category.equalsIgnoreCase("Physician")) {
                    i.putExtra("Physician", txtRel.getText().toString());
                    setResult(RESULT_SPECIALTY, i);
                } else if (category.equalsIgnoreCase("SpecialtyNetwork")) {
                    i.putExtra("SpecialtyNetwork", txtRel.getText().toString());
                    setResult(RESULT_SPECIALTY_NETWORK, i);
                }else if (category.equalsIgnoreCase("PhysicianNetwork")) {
                    i.putExtra("PhysicianNetwork", txtRel.getText().toString());
                    setResult(RESULT_SPECIALTY_NETWORK, i);
                }else if (category.equalsIgnoreCase("HospitalNetwork")) {
                    i.putExtra("HospitalNetwork", txtRel.getText().toString());
                    setResult(RESULT_HOSPITAL_NETWORK, i);
                }
                else if (category.equalsIgnoreCase("Category")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(RESULT_CATEGORY, i);
                } else if (category.equalsIgnoreCase("finance")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(RESULT_FINANCECAT, i);
                } else if (category.equalsIgnoreCase("Insurance")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(RESULT_INSURANCE, i);
                } else if (category.equalsIgnoreCase("Advance")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(RESULT_ADVANCE, i);
                } else if (category.equalsIgnoreCase("Other")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(RESULT_OTHER, i);
                } else if (category.equalsIgnoreCase("TypeFrequency")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(RESULT_FREQUENCY, i);
                } else if (category.equalsIgnoreCase("Relationp")) {
                    String rel = txtRel.getText().toString();
                    i.putExtra("Category", rel);
                    setResult(REQUEST_RELATIONP, i);
                } else if (category.equalsIgnoreCase("Marital")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(REQUEST_MARITAL, i);
                } else if (category.equalsIgnoreCase("language")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(REQUEST_LANGUAGE, i);
                } else if (category.equalsIgnoreCase("eyes")) {
                    i.putExtra("Category", txtRel.getText().toString());
                    setResult(REQUEST_EYES, i);
                } else if (category.equalsIgnoreCase("Blood")) {
                    i.putExtra("Blood", txtRel.getText().toString());
                    setResult(REQUEST_BLOOD, i);
                } else if (category.equalsIgnoreCase("Medical")) {
                    i.putExtra("Medical", txtRel.getText().toString());
                    setResult(REQUEST_BLOOD, i);
                }
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
