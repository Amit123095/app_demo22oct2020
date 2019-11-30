package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.Connections.RelationActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.AllergyQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.HistoryQuery;
import com.mindyourlovedone.healthcare.database.HospitalQuery;
import com.mindyourlovedone.healthcare.database.MedInfoQuery;
import com.mindyourlovedone.healthcare.database.MedicalConditionQuery;
import com.mindyourlovedone.healthcare.database.MedicalImplantsQuery;
import com.mindyourlovedone.healthcare.database.VaccineQuery;
import com.mindyourlovedone.healthcare.model.Allergy;
import com.mindyourlovedone.healthcare.model.History;
import com.mindyourlovedone.healthcare.model.Implant;
import com.mindyourlovedone.healthcare.model.Vaccine;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_ALLERGY = 100;
    public static final int RESULT_HISTORY = 200;
    public static final int RESULT_IMPLANTS = 300;
    public static final int RESULT_HOSPITAL = 400;
    private static final int RESULT_CONDITION = 500;
    private static final int RESULT_VACCINE = 700;
    public static final int REQUEST_REACTION = 800;
    Context context = this;
    ImageView imgBack, imgInfo,imgHome;
    RelativeLayout llAddConn, rlInfo, rlPdf;
    TextView txttype,txttypeReaction, txtSave, txtName, txtReaction, txtTreatment, txtTitle, txtAdd, txtDate,txtImmuniDate, txtDoctor, txtDone, txtOtherVaccine, txtOtherReaction;
    TextInputLayout tilTitle, tilReaction, tilTreatment, tilDate,tilImmuniDate, tilDoctor, tilDone, tilOtherVaccine, tilOtherReaction,tilType22;
    String from, name, title;
    Boolean isAllergy, isHistory, isImplant;
    Preferences preferences;
    DBHelper dbHelper;
    Button btn_delete;
    String condName="";
    int id;
    String data = "";
    String header = "";
    String msg = "";
    TextView txtHeader, txtInfo, txtMedical;
    TextInputLayout tilMedical, tilLocation, tilDetails, tilNote;
    RelativeLayout rlName, rlReactionSpinner,relbtm;
    //    MySpinner spinner;// spinnerReaction;
    FloatingActionButton floatProfile;
    private static int RESULT_MEDICAL = 2;
    TextView txtLocation, txtDetails, txtNote;
    FrameLayout fltypeReaction,fltype,fltype222;
    String reactions = "";
    String[] vaccineList = {"Chickenpox (Varicella)","Haemophilus influenza type B (Hib)", "Hepatitis A", "Hepatitis B", "Human Papillomavirus (HPV)", "Influenza (Flu)", "Measles, Mumps, Rubella (MMR)", "Meningococcal", "Polio (IPV)", "Pneumococcal (PCV and PPSV)", "Shingles (Herpes Zoster)", "Tetanus, Diphtheria, Pertussis (Td, Tdap)", "Other"};
    String[] implantList = {"Aneurysm Stent or Aneurysm Clip", "Artifical Limbs", "Artificial Heart Value", "Body Art/Tatoos", "Coronary Stents(Drug Coated/Bare Methal/Unknown)", "Metal Crowns, Fillings, Implants", "Gastric Band", "Body Piercing", "Implanted Cardio Defibrilator (ICD)", "Implanted Devices/Pumps/Stimulator", "Joint Replacements (specify)", "Lens Implants", "Metal Implants", "Middle Ear Prosthesis", "None", "Pacemaker", "Penile Implant", "Pins/Rods/Screws", "Prosthetic Eye", "Renal or other Stents", "Tracheotomy", "Other"};
    String[] reactionList = {"Anaphylaxis","Chest pain","Congestion","Difficulty Breathing","Hives","Itching","Mucus","Nausea","Rash","Runny nose","Sneezing","Vomiting", "Other"};
    String[] surgeryList = {"Appendix", "Breast Biopsy", "Cataract", "Colon","Fibroids", "Gallbladder", "Heart - Angio/Stent", "Heart - Bypass", "Heart - Valve", "Hernia", "Hip Replacement", "Hysterectomy", "Knee Surgery/Replacement", "Lasik Surgery","Low back pain", "Mastectomy", "Metal Implants", "Middle Ear Prosthesis", "Mohs – Basal Cell", "Mohs – Squamous Cell", "None","Prostate", "Spine Surgery", "Thyroid Surgery", "Tonsils","Uterine", "Vascular Surgery", "Wisdom Teeth", "Other"};
Allergy AL;
     Implant IM;
     History Sur;
     Vaccine vaccines;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        initComponent2();
        initUi();
        initListener();
        InitComponent();
    }

    private void InitComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        AllergyQuery a = new AllergyQuery(context, dbHelper);

        Intent i = getIntent();
        if (i.getExtras() != null) {
            from = i.getExtras().getString("ADD");
            name = i.getExtras().getString("Name");
            title = i.getExtras().getString("Title");

            txtTitle.setText(title);
            txtAdd.setText(title);
            tilTitle.setHint(name);
           /* tilTitle.setHintEnabled(false);
            txtName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    tilTitle.setHintEnabled(true);
                    txtName.setFocusable(true);

                    return false;
                }
            });*/
            if (from.equals("VaccineUpdate") || from.equals("Vaccine")) {
//                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, vaccineList);
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(adapter1);


            } else if (from.equals("ImplantUpdate") || from.equals("Implants")) {
               /* ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, implantList);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);*/
            } else if (from.equals("HistoryUpdate") || from.equals("History")) {
//                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, surgeryList);
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(adapter1);

            }

            txttype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (from.equals("VaccineUpdate") || from.equals("Vaccine")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(context);
                        b.setTitle("Type");

                        b.setItems(vaccineList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txttype.setText(vaccineList[which]);
                                if (vaccineList[which].equals("Other") || vaccineList[which].equals("Joint Replacements (specify)")) {
                                    tilOtherVaccine.setVisibility(View.VISIBLE);
                                } else {
                                    tilOtherVaccine.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }

                        });
                        b.show();
                    }else if (from.equals("HistoryUpdate") || from.equals("History")){
                        AlertDialog.Builder b = new AlertDialog.Builder(context);
                        b.setTitle("Type");

                        b.setItems(surgeryList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txttype.setText(surgeryList[which]);

                                if (surgeryList[which].equals("Other") || surgeryList[which].equals("Joint Replacements (specify)")) {
                                    tilOtherVaccine.setVisibility(View.VISIBLE);
                                } else {
                                    tilOtherVaccine.setVisibility(View.GONE);
                                }

                                dialog.dismiss();
                            }

                        });
                        b.show();
                    }


                }
            });


//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    if (parent.getItemAtPosition(position).toString().equals("Other") || parent.getItemAtPosition(position).toString().equals("Joint Replacements (specify)")) {
//                        tilOtherVaccine.setVisibility(View.VISIBLE);
//                    } else {
//                        tilOtherVaccine.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                }
//            });

            if (from.equals("AllergyUpdate") || from.equals("Allergy")) {
//                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, reactionList);
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerReaction.setAdapter(adapter1);

                txttypeReaction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, ReactionListActivity.class);
                        i.putExtra("Category", "Reaction");
                        i.putExtra("Selected",txttypeReaction.getText().toString());
                        startActivityForResult(i, REQUEST_REACTION);
                        /*AlertDialog.Builder b = new AlertDialog.Builder(context);
                        b.setTitle("Type");

                        b.setItems(reactionList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txttypeReaction.setText(reactionList[which]);
                                if (reactionList[which].equalsIgnoreCase("Other")) {
                                    tilOtherReaction.setVisibility(View.VISIBLE);
                                } else {
                                    tilOtherReaction.setVisibility(View.GONE);
                                    reactions = "";
                                    txtOtherReaction.setText("");
                                }
                                dialog.dismiss();
                            }

                        });
                        b.show();*/

                    }
                });
            }

//            spinnerReaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    if (parent.getItemAtPosition(position).toString().equals("Other")) {
//                        tilOtherReaction.setVisibility(View.VISIBLE);
//
//                    } else {
//                        tilOtherReaction.setVisibility(View.GONE);
//                        reactions = "";
//                        txtOtherReaction.setText("");
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                }
//            });
            switch (from) {
                case "Allergy":
                    btn_delete.setVisibility(View.GONE);
                    relbtm.setVisibility(View.GONE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.GONE);
                    fltype.setVisibility(View.GONE);
                    fltypeReaction.setVisibility(View.VISIBLE);
                    tilReaction.setVisibility(View.GONE);
                    tilOtherReaction.setHint("Other Reaction");
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    //  txttypeReaction.setHint("Reaction");
                    break;

                case "AllergyUpdate":
                    relbtm.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.GONE);
                    fltype.setVisibility(View.GONE);
                    fltypeReaction.setVisibility(View.VISIBLE);
                    tilReaction.setVisibility(View.GONE);
                    tilOtherReaction.setHint("Other Reaction");
                    //txttypeReaction.setHint("Reaction");
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "Implants":
                    relbtm.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.GONE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.GONE);
                    tilMedical.setVisibility(View.VISIBLE);
                    fltype222.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.VISIBLE);
                    tilLocation.setVisibility(View.VISIBLE);
                    tilDetails.setVisibility(View.VISIBLE);
                    tilNote.setVisibility(View.VISIBLE);
                    txtMedical.setFocusable(false);
                    tilMedical.setHintEnabled(true);
                    tilMedical.setHint("Medical Implant");
                    fltype.setVisibility(View.GONE);
                    tilOtherVaccine.setHint("Other Implant");
                    txttype.setHint("Medical Implant");
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "ImplantUpdate":
                    relbtm.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.GONE);
                    fltype222.setVisibility(View.VISIBLE);
                    tilMedical.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.VISIBLE);
                    txtMedical.setFocusable(false);
                    tilMedical.setHintEnabled(true);
                    tilMedical.setHint("Medical Implant");
                    tilLocation.setVisibility(View.VISIBLE);
                    tilDetails.setVisibility(View.VISIBLE);
                    tilNote.setVisibility(View.VISIBLE);

                    fltype.setVisibility(View.GONE);
                    tilOtherVaccine.setHint("Other Implant");
                    txttype.setHint("Medical Implant");
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "Condition":
                    relbtm.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.GONE);
                    rlPdf.setVisibility(View.VISIBLE);
                    tilTitle.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.GONE);
                    fltype.setVisibility(View.GONE);
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "ConditionUpdate":
                    relbtm.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    rlPdf.setVisibility(View.VISIBLE);
                    tilTitle.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.GONE);
                    fltype.setVisibility(View.GONE);
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "Hospital":
                    relbtm.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.GONE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.GONE);
                    fltype.setVisibility(View.GONE);
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "HospitalUpdate":
                    relbtm.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.VISIBLE);
                    txtMedical.setVisibility(View.GONE);
                    fltype.setVisibility(View.GONE);
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);

                    break;
                case "History":
                    relbtm.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.GONE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.GONE);
                    txtMedical.setVisibility(View.VISIBLE);
                    fltype.setVisibility(View.VISIBLE);
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    tilOtherVaccine.setHint("Other History");
                    txttype.setHint("Surgical/Hospitalization History");
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "HistoryUpdate":
                    relbtm.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.GONE);
                    txtMedical.setVisibility(View.VISIBLE);
                    fltype.setVisibility(View.VISIBLE);
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    tilOtherVaccine.setHint("Other History");
                    txttype.setHint("Surgical/Hospitalization History");
                    txtDate.setFocusable(false);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cal_c, 0);
                    break;

                case "Vaccine":
                    relbtm.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.GONE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.GONE);
                    txtMedical.setVisibility(View.VISIBLE);
                    fltype.setVisibility(View.VISIBLE);
                    tilOtherVaccine.setHint("Other Vaccine");
                    txttype.setHint("Immunization/Vaccine");
                    fltypeReaction.setVisibility(View.GONE);
                    tilReaction.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(true);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    break;

                case "VaccineUpdate":
                    relbtm.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    rlPdf.setVisibility(View.GONE);
                    tilTitle.setVisibility(View.GONE);
                    txtMedical.setVisibility(View.VISIBLE);
                    fltype.setVisibility(View.VISIBLE);
                    tilOtherVaccine.setHint("Other Vaccine");
                    txttype.setHint("Immunization/Vaccine");
                    fltypeReaction.setVisibility(View.GONE);
                    tilType22.setVisibility(View.VISIBLE);
                    txtDate.setFocusable(true);
                    txtDate.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
                    break;
            }
            switch (from) {


                case "Allergy":
                    header = "Types of Reaction to Consider :";
                    msg = "<br>Anaphylaxis<br>" +
                            "Difficulty Breathing<br>" +
                            "Hives<br>" +
                            "Nausea<br>" +
                            "Rash<br>" +
                            "Vomiting";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "AllergyUpdate":
                    header = "Types of Reaction to Consider :";
                    msg = "<br>Anaphylaxis<br>" +
                            "Difficulty Breathing<br>" +
                            "Hives<br>" +
                            "Nausea<br>" +
                            "Rash<br>" +
                            "Vomiting";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;
                case "Implants":
                    header = "Medical Implants to Consider :";
                    msg = "<br><ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                            "<li>Artifical Limbs</li>" +
                            "<li>Artificial Heart Value</li>" +
                            "<li>Body Art</li>" +
                            "<li>Body Piercing </li>" +
                            "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                            "<li>Crowns</li>" +
                            "<li>Dental metal implants</li>" +
                            "<li>Fillings</li>" +
                            "<li>Gastric Band</li>" +
                            "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                            "<li>Implanted Devices/Pumps/Stimulator</li>" +
                            "<li>Joint Replacements (specify)______</li>" +
                            "<li>Lens Implants</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Pacemaker</li>" +
                            "<li>Penile Implant</li>" +
                            "<li>Pins/Rods/Screws (specify)</li>" +
                            "<li>Prosthetic Eye</li>" +
                            "<li>Renal or other Stents</li>" +
                            "<li>Tracheotomy</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "ImplantUpdate":
                    header = "Medical Implants to Consider :";
                    msg = "<br><ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                            "<li>Artifical Limbs</li>" +
                            "<li>Artificial Heart Value</li>" +
                            "<li>Body Art</li>" +
                            "<li>Body Piercing </li>" +
                            "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                            "<li>Gastric Band</li>" +
                            "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                            "<li>Implanted Devices/Pumps/Stimulator</li>" +
                            "<li>Joint Replacements (specify)______</li>" +
                            "<li>Lens Implants</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Pacemaker</li>" +
                            "<li>Penile Implant</li>" +
                            "<li>Pins/Rods/Screws (specify)</li>" +
                            "<li>Prosthetic Eye</li>" +
                            "<li>Renal or other Stents</li>" +
                            "<li>Tracheotomy</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;
                case "Condition":
                    header = "For assistance, see medical history template. The purpose of the template is to  jog your memory. The template will not automatically populate MYLO.";
                    msg = "<br>" +
                            "Click<br>" +
                            "<a><font color='blue'><u>Resources/Forms and Templates/Medical History Template</u></font></a>";
                    txtHeader.setText(Html.fromHtml(header));
                    txtInfo.setText(Html.fromHtml(msg));

                    txtInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CopyReadAssetss("medical_history_form_new.pdf");
                        }
                    });
                    break;

                case "ConditionUpdate":
                    header = "For Assistance See Medical History Template";
                    msg = "<br>" +
                            "Click on<br>" +
                            "<a><font color='blue'><u>Resources/Forms and Templates/Medical History Template</u></font></a>";
                    txtHeader.setText(Html.fromHtml(header));
                    txtInfo.setText(Html.fromHtml(msg));

                    txtInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CopyReadAssetss("medical_history_form_new.pdf");
                        }
                    });
                    break;
                case "Hospital":

                    break;

                case "HospitalUpdate":

                    break;
                case "History":
                    header = "Surgical/Hospitalization History to Consider :";
                    msg = "<br><ul><li>Appendix</li>" +
                            "<li>Breast Biopsy/Mastectomy</li>" +
                            "<li>Cataract</li>" +
                            "<li>Colon</li>" +
                            "<li>Gallbladder</li>" +
                            "<li>Heart, Angio/Stent</li>" +
                            "<li>Heart, Bypass</li>" +
                            "<li>Heart, Valve</li>" +
                            "<li>Hernia</li>" +
                            "<li>Hip Replacement</li>" +
                            "<li>Hysterectomy</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Knee Surgery/Replacement</li>" +
                            "<li>Lasik Surgery</li>" +
                            "<li>Spine Surgery</li>" +
                            "<li>Thyroid Surgery</li>" +
                            "<li>Tonsils</li>" +
                            "<li>Vascular Surgery</li>" +
                            "<li>Wisdom Teeth</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "HistoryUpdate":
                    header = "Surgical/Hospitalization History to Consider :";
                    msg = "<br><ul><li>Appendix</li>" +
                            "<li>Breast Biopsy/Mastectomy</li>" +
                            "<li>Cataract</li>" +
                            "<li>Colon</li>" +
                            "<li>Gallbladder</li>" +
                            "<li>Heart, Angio/Stent</li>" +
                            "<li>Heart, Bypass</li>" +
                            "<li>Heart, Valve</li>" +
                            "<li>Hernia</li>" +
                            "<li>Hip Replacement</li>" +
                            "<li>Hysterectomy</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Knee Surgery/Replacement</li>" +
                            "<li>Lasik Surgery</li>" +
                            "<li>Spine Surgery</li>" +
                            "<li>Thyroid Surgery</li>" +
                            "<li>Tonsils</li>" +
                            "<li>Vascular Surgery</li>" +
                            "<li>Wisdom Teeth</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "Vaccine":
                    header = "Vaccines to Consider :";
                    msg = "<br><ul><li>Chickenpox (Varicella)</li>" +
                            "<li>Hepatitis A</li>" +
                            "<li>Hepatitis B</li>" +
                            "<li>Hib</li>" +
                            "<li>Human Papillomavirus (HPV)</li>" +
                            "<li>Influenza (Flu)</li>" +
                            "<li>Measles, Mumps, Rubella (MMR)</li>" +
                            "<li>Meningococcal</li>" +
                            "<li>Polio (IPV)</li>" +
                            "<li>Pneumococcal (PCV and PPSV)</li>" +
                            "<li>Shingles (Herpes Zoster)</li>" +
                            "<li>Tetanus, Diphtheria, Pertussis (Td, Tdap)</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "VaccineUpdate":
                    header = "Vaccines to Consider :";
                    msg = "<br><ul><li>Chickenpox (Varicella)</li>" +
                            "<li>Hepatitis A</li>" +
                            "<li>Hepatitis B</li>" +
                            "<li>Hib</li>" +
                            "<li>Human Papillomavirus (HPV)</li>" +
                            "<li>Influenza (Flu)</li>" +
                            "<li>Measles, Mumps, Rubella (MMR)</li>" +
                            "<li>Meningococcal</li>" +
                            "<li>Polio (IPV)</li>" +
                            "<li>Pneumococcal (PCV and PPSV)</li>" +
                            "<li>Shingles (Herpes Zoster)</li>" +
                            "<li>Tetanus, Diphtheria, Pertussis (Td, Tdap)</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;
            }
          /*  switch (from) {
                case "Allergy":
                  imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "AllergyUpdate":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;
                case "Implants":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "ImplantUpdate":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;
                case "Condition":
                    imgInfo.setVisibility(View.GONE);
                    break;

                case "ConditionUpdate":
                    imgInfo.setVisibility(View.GONE);
                    break;
                case "Hospital":
                    imgInfo.setVisibility(View.GONE);
                    break;

                case "HospitalUpdate":
                    imgInfo.setVisibility(View.GONE);
                    break;
                case "History":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "HistoryUpdate":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "Vaccine":
                    imgInfo.setVisibility(View.GONE);
                    break;

                case "VaccineUpdate":
                    imgInfo.setVisibility(View.GONE);
                    break;
            }*/
            isAllergy = i.getExtras().getBoolean("IsAllergy");
            isHistory = i.getExtras().getBoolean("IsHistory");
            isImplant = i.getExtras().getBoolean("IsImplant");
            if (isAllergy == true) {
                tilTreatment.setVisibility(View.VISIBLE);
                tilReaction.setVisibility(View.GONE);
                fltypeReaction.setVisibility(View.VISIBLE);
            } else {
                tilTreatment.setVisibility(View.GONE);
                tilReaction.setVisibility(View.GONE);
                fltypeReaction.setVisibility(View.GONE);
            }

            if (isHistory == true) {
                tilDone.setVisibility(View.VISIBLE);
                tilDoctor.setVisibility(View.VISIBLE);
                // tilDate.setVisibility(View.VISIBLE);
            } else {
                tilDone.setVisibility(View.GONE);
                tilDoctor.setVisibility(View.GONE);
                //tilDate.setVisibility(View.GONE);
            }
            if (isImplant == true || isHistory == true) {
                tilDate.setVisibility(View.VISIBLE);
                if (isImplant == true) {
                    tilDate.setHint("Note (Date, Location, Details)");
                } else {
                    tilDate.setHint("Date");
                }
            } else {
                tilDate.setVisibility(View.GONE);
            }

            if(i.getExtras().getBoolean("IsImplant2")){
                tilDate.setVisibility(View.VISIBLE);
                tilDate.setHint("Date");
            }

            switch (from) {
                case "AllergyUpdate":
                    Allergy allergy = (Allergy) i.getExtras().getSerializable("AllergyObject");
                    AL= (Allergy) i.getExtras().getSerializable("AllergyObject");
                    txtName.setText(allergy.getAllergy());
                    //  txtReaction.setText(allergy.getReaction());
                    txtTreatment.setText(allergy.getTreatment());
                    id = allergy.getId();
                    int indexd = 0;
                    for (int j = 0; j < reactionList.length; j++) {
                        if (allergy.getReaction().equals(reactionList[j])) {
                            indexd = j;
                            if (allergy.getReaction().equals("Other")) {
                                txtOtherReaction.setText(allergy.getOtherReaction());
                            } else {
                                txtOtherReaction.setText("");
                            }
                        }
                    }

                    txttypeReaction.setText(allergy.getReaction());
                    ArrayList<String> selectedList;
                    if (allergy.getReaction().contains(",")) {
                        String[] elements = allergy.getReaction().split(",");
                        List<String> fixedLenghtList = Arrays.asList(elements);
                        selectedList = new ArrayList<String>(fixedLenghtList);
                    } else {
                        selectedList = new ArrayList<>();
                        if (!allergy.getReaction().equals(""))
                            selectedList.add(allergy.getReaction());
                    }
                    for (int j=0;j<selectedList.size();j++) {
                        if (selectedList.get(j).equals("Other")) {
                            tilOtherReaction.setVisibility(View.VISIBLE);
                            txtOtherReaction.setVisibility(View.VISIBLE);
                            txtOtherReaction.setText(allergy.getOtherReaction());
                            break;
                        } else {
                            tilOtherReaction.setVisibility(View.GONE);
                            txtOtherReaction.setVisibility(View.GONE);
                        }
                    }
//                    spinnerReaction.setSelection(indexd + 1);
                    break;

                case "ImplantUpdate":
                    Implant implant = (Implant) i.getExtras().getSerializable("ImplantObject");
                    IM= (Implant) i.getExtras().getSerializable("ImplantObject");
                    //txtName.setText(implant.getName());
                    //shradha
                    if (implant.getName().equalsIgnoreCase("Other")) {
                        tilOtherVaccine.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherVaccine.setVisibility(View.GONE);
                    }
                    txtOtherVaccine.setText(implant.getOther());
                    txtDate.setText(implant.getDate());
                    txtMedical.setText(implant.getName());
                    txtLocation.setText(implant.getLocation());
                    txtDetails.setText(implant.getDetails());
                    txtNote.setText(implant.getNotes());
                    id = implant.getId();
                   /* int index = 0;
                    for (int j = 0; j < implantList.length; j++) {
                        if (implant.getName().equals(implantList[j])) {
                            index = j;
                        }
                    }
                    spinner.setSelection(index + 1);*/
                    break;

                case "VaccineUpdate":
                    Vaccine vaccine = (Vaccine) i.getExtras().getSerializable("VaccineObject");
                    vaccines=(Vaccine) i.getExtras().getSerializable("VaccineObject");

                    // txtName.setText(vaccine.getName());
                    txtDate.setText(vaccine.getDate());
                    txtOtherVaccine.setText(vaccine.getOther());
                    id = vaccine.getId();
                    int indexs = 0;
                    for (int j = 0; j < vaccineList.length; j++) {
                        if (vaccine.getName().equals(vaccineList[j])) {
                            indexs = j;
                        }
                    }
                    txttype.setText(vaccine.getName());
//                    spinner.setSelection(indexs + 1);
                    break;

                case "ConditionUpdate":
                    String valuef = i.getExtras().getString("ConditionObject");
                    condName = i.getExtras().getString("ConditionObject");
                    txtName.setText(valuef);
                    data = valuef;
                    break;

                case "HospitalUpdate":
                    String values = i.getExtras().getString("HospitalObject");
                    txtName.setText(values);
                    condName=values;
                    data = values;
                    break;

                case "HistoryUpdate":
                    History history = (History) i.getExtras().getSerializable("HistoryObject");
                    Sur = (History) i.getExtras().getSerializable("HistoryObject");
                    // txtName.setText(history.getName());
                    txtDone.setText(history.getDone());
                    txtDate.setText(history.getDate());
                    txtDoctor.setText(history.getDoctor());
                    txtOtherVaccine.setText(history.getOther());
                    id = history.getId();
                    int indexH = 0;
                    for (int j = 0; j < surgeryList.length; j++) {
                        if (history.getName().equals(surgeryList[j])) {
                            indexH = j;
                        }
                    }
                    txttype.setText(history.getName());
//                    spinner.setSelection(indexH + 1);
                    break;

            }

        }
    }

    private void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try {
            in = assetManager.open(documentPath);
            outFile = new File(getExternalFilesDir(null), documentPath);
            out = new FileOutputStream(outFile);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            /*out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;*/
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri uri = null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }


    }

    private void initListener() {
        floatProfile.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgInfo.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtMedical.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    private void initUi() {
        fltype = findViewById(R.id.fltype);
        txttype = findViewById(R.id.txttype);
        fltypeReaction = findViewById(R.id.fltypeReaction);
        txttypeReaction = findViewById(R.id.txttypeReaction);
        btn_delete = findViewById(R.id.btn_delete);
        relbtm = findViewById(R.id.relbtm);
        btn_delete.setVisibility(View.GONE);
        relbtm.setVisibility(View.GONE);
        floatProfile = findViewById(R.id.floatProfile);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        imgInfo = findViewById(R.id.imgInfo);
        llAddConn = findViewById(R.id.llAddConn);
        txtSave = findViewById(R.id.txtSave);
        tilTitle = findViewById(R.id.tilTitle);
        txtName = findViewById(R.id.txtName);
        tilReaction = findViewById(R.id.tilReaction);
        txtReaction = findViewById(R.id.txtReaction);
        txtAdd = findViewById(R.id.txtAdd);
        txtTitle = findViewById(R.id.txtTitle);
        txtHeader = findViewById(R.id.txtHeader);
        txtInfo = findViewById(R.id.txtInfo);
        txtMedical = findViewById(R.id.txtMedical);
        tilMedical = findViewById(R.id.tilMedical);
        tilType22 = findViewById(R.id.tilType22);
        fltype222 = findViewById(R.id.fltype222);
        tilLocation = findViewById(R.id.tilLocation);
        tilDetails = findViewById(R.id.tilDetails);
        tilNote = findViewById(R.id.tilNote);
        tilNote.setHint("Note");
        txtDate = findViewById(R.id.txtDate);
        // txtDate.setFocusable(false);
        // txtImmuniDate = findViewById(R.id.txtImmuniDate);
        txtDoctor = findViewById(R.id.txtDoctor);
        txtDone = findViewById(R.id.txtDone);
        tilTreatment = findViewById(R.id.tilTreatment);
        txtTreatment = findViewById(R.id.txtTreatment);
        txtOtherVaccine = findViewById(R.id.txtOtherVaccine);

        txtLocation = findViewById(R.id.txtLocation);
        txtDetails = findViewById(R.id.txtDetails);
        txtNote = findViewById(R.id.txtNote);

        tilOtherVaccine = findViewById(R.id.tilOtherVaccine);

        txtOtherReaction = findViewById(R.id.txtOtherReaction);

        tilOtherReaction = findViewById(R.id.tilOtherReaction);

        tilDate = findViewById(R.id.tilDate);
        tilDoctor = findViewById(R.id.tilDoctor);
        tilDone = findViewById(R.id.tilDone);

//        spinner = findViewById(R.id.spinner);
//        spinnerReaction = findViewById(R.id.spinnerReaction);

        rlName = findViewById(R.id.rlName);
        rlReactionSpinner = findViewById(R.id.rlReactionSpinner);
        rlPdf = findViewById(R.id.rlPdf);
        rlInfo = findViewById(R.id.rlInfo);
        rlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_MEDICAL && data != null) {
            String medical = data.getExtras().getString("Medical");
            txtMedical.setText(medical);

            if (medical.equals("Other")) {
                tilOtherVaccine.setVisibility(View.VISIBLE);
                txtOtherVaccine.setVisibility(View.VISIBLE);
            } else {
                tilOtherVaccine.setVisibility(View.GONE);
                txtOtherVaccine.setVisibility(View.GONE);
            }
        }

        if (requestCode == REQUEST_REACTION && data != null) {
            String relation = data.getExtras().getString("Category");;
            txttypeReaction.setText(relation);
            ArrayList<String> selectedList;
            if (relation.contains(",")) {
                String[] elements = relation.split(",");
                List<String> fixedLenghtList = Arrays.asList(elements);
                selectedList = new ArrayList<String>(fixedLenghtList);
            } else {
                selectedList = new ArrayList<>();
                if (!relation.equals(""))
                    selectedList.add(relation);
            }
            for (int i=0;i<selectedList.size();i++) {
                if (selectedList.get(i).equals("Other")) {
                    tilOtherReaction.setVisibility(View.VISIBLE);
                    txtOtherReaction.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tilOtherReaction.setVisibility(View.GONE);
                    txtOtherReaction.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initComponent2() {
        preferences = new Preferences(this);
        dbHelper = new DBHelper(this, preferences.getString(PrefConstants.CONNECTED_USERDB));
        MedInfoQuery p = new MedInfoQuery(this, dbHelper);
        AllergyQuery a = new AllergyQuery(this, dbHelper);
        MedicalImplantsQuery m = new MedicalImplantsQuery(this, dbHelper);
        MedicalConditionQuery f = new MedicalConditionQuery(this, dbHelper);
        HospitalQuery h = new HospitalQuery(this, dbHelper);
        HistoryQuery hi = new HistoryQuery(this, dbHelper);
        VaccineQuery v = new VaccineQuery(this, dbHelper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);
                startActivity(intentDashboard);
                break;

            case R.id.imgBack:
                if (from.equals("Allergy")||from.equals("AllergyUpdate"))
                {
                    String treatment = txtTreatment.getText().toString();
                    String otherReaction = txtOtherReaction.getText().toString();
                    String reactions = txttypeReaction.getText().toString();
                    String name = txtName.getText().toString();
                    if (from.equals("Allergy"))
                    {
                        if (name.equals("") && treatment.equals("") &&
                                otherReaction.equals("") &&
                                reactions.equals("")) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }else if (from.equals("AllergyUpdate")){
                    /*if (name.equals(AL.getAllergy()) && (treatment.equals(AL.getTreatment())||treatment.equals("--")) &&
                            (otherReaction.equals(AL.getOtherReaction())||otherReaction.equals("--")) &&
                            (reactions.equals(AL.getReaction())||reactions.equals("--"))) {*/
                        if (name.equals(AL.getAllergy()) && (treatment.equals(AL.getTreatment())||treatment.equals("")) &&
                                (otherReaction.equals(AL.getOtherReaction())||otherReaction.equals("")) &&
                                (reactions.equals(AL.getReaction())||reactions.equals(""))) {
                        finish();
                    } else {
                        showSaveAlert();
                    }
                 }
                }else if (from.equals("Condition")||from.equals("ConditionUpdate"))
                {
                    String name = txtName.getText().toString();
                    if (from.equals("Condition"))
                    {
                        if (name.equals("")) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }else if (from.equals("ConditionUpdate")){
                        if (name.equals(condName)) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }
                }else if (from.equals("Implants")||from.equals("ImplantUpdate"))
                {
                    String name = txtMedical.getText().toString();
                    String datee = txtDate.getText().toString();
                    String location = txtLocation.getText().toString().trim();
                    String details = txtDetails.getText().toString().trim();
                    String notes = txtNote.getText().toString().trim();

                    String otherd = "";
                    if (name.equals("Other") || name.equals("Joint Replacements (specify)")) {
                        otherd = txtOtherVaccine.getText().toString();
                    }

                    if (from.equals("Implants"))
                    {
                        if (name.equals("")&&datee.equals("")&&
                                location.equals("")&&details.equals("")&&
                                notes.equals("")&&otherd.equals("")) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }else if (from.equals("ImplantUpdate")){
                        if (name.equals(IM.getName())&&(datee.equals(IM.getDate())||datee.equals(""))&&
                                (location.equals(IM.getLocation())||location.equals(""))&&(details.equals(IM.getDetails())||details.equals(""))&&
                                (notes.equals(IM.getNotes())||notes.equals(""))&&(otherd.equals(IM.getOther())||otherd.equals(""))) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }
                }else if (from.equals("History")||from.equals("HistoryUpdate"))
                {
                    String name = txttype.getText().toString();
                    String dateu = txtDate.getText().toString();
                    String doctors = txtDoctor.getText().toString();
                    String dones = txtDone.getText().toString();
                    String otherHU = txtOtherVaccine.getText().toString();
                    if (from.equals("History"))
                    {
                        if (name.equals("")&&dateu.equals("")&&
                                doctors.equals("")&&dones.equals("")&&
                                otherHU.equals("")) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }else if (from.equals("HistoryUpdate")){
                        if (name.equals(Sur.getName())&&(dateu.equals(Sur.getDate())||dateu.equals(""))&&
                        (doctors.equals(Sur.getDoctor())||doctors.equals(""))&&(dones.equals(Sur.getDone())||dones.equals(""))&&
                                (otherHU.equals(Sur.getOther())||otherHU.equals(""))) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }
                }
                else if (from.equals("Hospital")||from.equals("HospitalUpdate"))
                {
                    String name = txtName.getText().toString();
                    if (from.equals("Hospital"))
                    {
                        if (name.equals("")) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }else if (from.equals("HospitalUpdate")){
                        if (name.equals(condName)) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }
                }
                if (from.equals("Vaccine")||from.equals("VaccineUpdate"))
                {
                    String dates = txtDate.getText().toString();
                    String others = txtOtherVaccine.getText().toString();
                    String name = txttype.getText().toString();
                    if (from.equals("Vaccine"))
                    {
                        if (name.equals("") && dates.equals("") &&
                                others.equals("")) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }else if (from.equals("VaccineUpdate")){
                        if (name.equals(vaccines.getName()) &&( dates.equals(vaccines.getDate()) ||dates.equals(""))&&
                                (others.equals(vaccines.getOther())||others.equals(""))) {
                            finish();
                        } else {
                            showSaveAlert();
                        }
                    }
                }



                break;

            case R.id.imgHome:
                hideSoftKeyboard();
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               /* intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                startActivity(intentHome);
                finish();
                break;

            case R.id.imgInfo:
                switch (from) {
                    case "Allergy":
                        header = "Allergy";
                        msg = "<b>Types of Reaction to Consider :</b><br>" +
                                "Anaphylaxis<br>" +
                                "Difficulty Breathing<br>" +
                                "Hives<br>" +
                                "Nausea<br>" +
                                "Rash<br>" +
                                "Vomiting <br>";
                        showViewDialog(context, msg, header);
                        break;

                    case "AllergyUpdate":
                        header = "Allergy";
                        msg = "<b>Types of Reaction to Consider :</b><br>" +
                                "Anaphylaxis<br>" +
                                "Difficulty Breathing<br>" +
                                "Hives<br>" +
                                "Nausea<br>" +
                                "Rash<br>" +
                                "Vomiting <br>";
                        showViewDialog(context, msg, header);
                        break;
                    case "Implants":
                        header = "Medical Implants to Consider";
                        msg = "<ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                                "<li>Artifical Limbs</li>" +
                                "<li>Artificial Heart Value</li>" +
                                "<li>Body Art</li>" +
                                "<li>Body Piercing </li>" +
                                "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                                "<li>Gastric Band</li>" +
                                "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                                "<li>Implanted Devices/Pumps/Stimulator</li>" +
                                "<li>Joint Replacements (specify)______</li>" +
                                "<li>Lens Implants</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Pacemaker</li>" +
                                "<li>Penile Implant</li>" +
                                "<li>Pins/Rods/Screws (specify)</li>" +
                                "<li>Prosthetic Eye</li>" +
                                "<li>Renal or other Stents</li>" +
                                "<li>Tracheotomy</li></ul>";
                        showViewDialog(context, msg, header);
                        break;

                    case "ImplantUpdate":
                        header = "Medical Implants to Consider";
                        msg = "<ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                                "<li>Artifical Limbs</li>" +
                                "<li>Artificial Heart Value</li>" +
                                "<li>Body Art</li>" +
                                "<li>Body Piercing </li>" +
                                "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                                "<li>Gastric Band</li>" +
                                "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                                "<li>Implanted Devices/Pumps/Stimulator</li>" +
                                "<li>Joint Replacements (specify)______</li>" +
                                "<li>Lens Implants</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Pacemaker</li>" +
                                "<li>Penile Implant</li>" +
                                "<li>Pins/Rods/Screws (specify)</li>" +
                                "<li>Prosthetic Eye</li>" +
                                "<li>Renal or other Stents</li>" +
                                "<li>Tracheotomy</li></ul>";
                        showViewDialog(context, msg, header);
                        break;
                    case "Condition":

                        break;

                    case "ConditionUpdate":

                        break;
                    case "Hospital":

                        break;

                    case "HospitalUpdate":

                        break;
                    case "History":
                        header = "Surgical/Hospitalization History to Consider";
                        msg = "<ul><li>Appendix</li>" +
                                "<li>Breast Biopsy/Mastectomy</li>" +
                                "<li>Cataract</li>" +
                                "<li>Colon</li>" +
                                "<li>Gallbladder</li>" +
                                "<li>Heart, Angio/Stent</li>" +
                                "<li>Heart, Bypass</li>" +
                                "<li>Heart, Valve</li>" +
                                "<li>Hernia</li>" +
                                "<li>Hip Replacement</li>" +
                                "<li>Hysterectomy</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Knee Surgery/Replacement</li>" +
                                "<li>Lasik Surgery</li>" +
                                "<li>Spine Surgery</li>" +
                                "<li>Thyroid Surgery</li>" +
                                "<li>Tonsils</li>" +
                                "<li>Vascular Surgery</li>" +
                                "<li>Wisdom Teeth</li></ul>";
                        showViewDialog(context, msg, header);
                        break;

                    case "HistoryUpdate":
                        header = "Surgical/Hospitalization History to Consider";
                        msg = "<ul><li>Appendix</li>" +
                                "<li>Breast Biopsy/Mastectomy</li>" +
                                "<li>Cataract</li>" +
                                "<li>Colon</li>" +
                                "<li>Gallbladder</li>" +
                                "<li>Heart, Angio/Stent</li>" +
                                "<li>Heart, Bypass</li>" +
                                "<li>Heart, Valve</li>" +
                                "<li>Hernia</li>" +
                                "<li>Hip Replacement</li>" +
                                "<li>Hysterectomy</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Knee Surgery/Replacement</li>" +
                                "<li>Lasik Surgery</li>" +
                                "<li>Spine Surgery</li>" +
                                "<li>Thyroid Surgery</li>" +
                                "<li>Tonsils</li>" +
                                "<li>Vascular Surgery</li>" +
                                "<li>Wisdom Teeth</li></ul>";
                        showViewDialog(context, msg, header);
                        break;
                }

                break;
            case R.id.txtDate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        long selectedMilli = newDate.getTimeInMillis();

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                        DateClass d=new DateClass();
                        d.setDate(reportDate);
                        txtDate.setText(reportDate);
                    }
                }, year, month, day);
                dpd.show();
                break;

            case R.id.txtMedical:
                Intent intentType = new Intent(context, RelationActivity.class);
                intentType.putExtra("Category", "Medical");
                startActivityForResult(intentType, RESULT_MEDICAL);
                break;

            case R.id.btn_delete:
                boolean flagsr = false;
                Intent intentdelete = new Intent();
                String idss = getIntent().getExtras().getString("ID");
                switch (from) {

                    case "AllergyUpdate":
                        flagsr = AllergyQuery.deleteRecord(Integer.parseInt(idss));
                        setResult(RESULT_ALLERGY, intentdelete);
                        break;


                    case "ImplantUpdate":
                        flagsr = MedicalImplantsQuery.deleteRecord(Integer.parseInt(idss));
                        setResult(RESULT_IMPLANTS, intentdelete);
                        break;


                    case "ConditionUpdate":
                        flagsr = MedicalConditionQuery.deleteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), idss);
                        setResult(RESULT_CONDITION, intentdelete);
                        break;

                    case "HospitalUpdate":
                        flagsr = HospitalQuery.deleteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), idss);
                        setResult(RESULT_HOSPITAL, intentdelete);
                        break;

                    case "HistoryUpdate":
                        flagsr = HistoryQuery.deleteRecord(Integer.parseInt(idss));
                        setResult(RESULT_HISTORY, intentdelete);
                        break;

                    case "VaccineUpdate":
                        flagsr = VaccineQuery.deleteRecord(Integer.parseInt(idss));
                        setResult(RESULT_VACCINE, intentdelete);
                        break;
                }

                if (flagsr) {
                    finish();
                }

                break;

            case R.id.txtSave:
                String value = txtMedical.getText().toString().trim();

                // String medical = txtMedical.getText().toString().trim();
                if (from.equals("VaccineUpdate") || from.equals("Vaccine")) {
//                    int indexValue = spinner.getSelectedItemPosition();
//                    if (indexValue != 0) {
//                        value = vaccineList[indexValue - 1];
//                    }
                    value = txttype.getText().toString();
                } else if (from.equals("ImplantUpdate") || from.equals("Implants")) {
                    txtMedical.setText(value);

                   /* int indexValue = spinner.getSelectedItemPosition();
                    if (indexValue != 0) {
                        value = implantList[indexValue - 1];
                    }*/
                } else if (from.equals("HistoryUpdate") || from.equals("History")) {
//                    int indexValue = spinner.getSelectedItemPosition();
//                    if (indexValue != 0) {
//                        value = surgeryList[indexValue - 1];
//                    }
                    value = txttype.getText().toString();
                } else {
                    value = txtName.getText().toString().trim();
                }

                if (value.length() != 0) {
                    hideSoftKeyboard();//shradha
                    switch (from) {
                        case "Allergy":
                            //  String reaction=txtReaction.getText().toString();
                            String treatment = txtTreatment.getText().toString();

                            String otherReaction = txtOtherReaction.getText().toString();
//                            int indexValue = spinnerReaction.getSelectedItemPosition();
//                            if (indexValue != 0) {
//                                reactions = reactionList[indexValue - 1];
//                            }
                            reactions = txttypeReaction.getText().toString();
                            Boolean flag = AllergyQuery.insertAllergyData(preferences.getInt(PrefConstants.CONNECTED_USERID), value, reactions, treatment, otherReaction);
                            if (flag == true) {
                                Toast.makeText(context, "Allergy Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentAllergy = new Intent();
                           /* intentAllergy.putExtra("Value", value);
                            intentAllergy.putExtra("Reaction", reaction);
                            intentAllergy.putExtra("Treatment", treatment);*/
                            setResult(RESULT_ALLERGY, intentAllergy);
                            finish();
                            break;

                        case "AllergyUpdate":
                            //  String reactions=txtReaction.getText().toString();
                            String treatments = txtTreatment.getText().toString();

                            String otherReactions = txtOtherReaction.getText().toString();
//                            int indexValues = spinnerReaction.getSelectedItemPosition();
//                            if (indexValues != 0) {
//                                reactions = reactionList[indexValues - 1];
//                            }
                            reactions = txttypeReaction.getText().toString();
                            Boolean flags = AllergyQuery.updateAllergyData(id, value, reactions, treatments, otherReactions);
                            if (flags == true) {
                                Toast.makeText(context, "Allergy updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentAllergys = new Intent();
                           /* intentAllergy.putExtra("Value", value);
                            intentAllergy.putExtra("Reaction", reaction);
                            intentAllergy.putExtra("Treatment", treatment);*/
                            setResult(RESULT_ALLERGY, intentAllergys);
                            finish();
                            break;
                        case "Implants":
                            txtLocation.setVisibility(View.VISIBLE);
                            txtDetails.setVisibility(View.VISIBLE);
                            txtNote.setVisibility(View.VISIBLE);
                            String dater = txtDate.getText().toString();
                            String location = txtLocation.getText().toString().trim();
                            String details = txtDetails.getText().toString().trim();
                            String notes = txtNote.getText().toString().trim();

                            String otheri = "";
                            if (value.equals("Other") || value.equals("Joint Replacements (specify)")) {
                                otheri = txtOtherVaccine.getText().toString();
                            }
                            Boolean flage = MedicalImplantsQuery.insertImplantData(preferences.getInt(PrefConstants.CONNECTED_USERID), value, dater, otheri, location, details, notes);
                            if (flage == true) {
                                Toast.makeText(context, "Implant Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVaccines = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_IMPLANTS, intentVaccines);
                            finish();
                            break;

                        case "Vaccine":

                            String datev = txtDate.getText().toString();
                            String other = txtOtherVaccine.getText().toString();
                            Boolean flagr = VaccineQuery.insertVaccineData(preferences.getInt(PrefConstants.CONNECTED_USERID), value, datev, other);
                            if (flagr == true) {
                                Toast.makeText(context, "Vaccine Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVaccine = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_VACCINE, intentVaccine);
                            finish();
                            break;

                        case "VaccineUpdate":
                            String dates = txtDate.getText().toString();
                            String others = txtOtherVaccine.getText().toString();
                            Boolean flagf = VaccineQuery.updateVaccineData(id, value, dates, others);
                            if (flagf == true) {
                                Toast.makeText(context, "Vaccine Updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVaccinef = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_VACCINE, intentVaccinef);
                            finish();
                            break;

                        case "ImplantUpdate":
                            String datee = txtDate.getText().toString();
                            location = txtLocation.getText().toString().trim();
                            details = txtDetails.getText().toString().trim();
                            notes = txtNote.getText().toString().trim();

                            String otherd = "";
                            if (value.equals("Other") || value.equals("Joint Replacements (specify)")) {
                                otherd = txtOtherVaccine.getText().toString();
                            }

                            Boolean flagw = MedicalImplantsQuery.updateImplantData(id, value, datee, otherd, location, details, notes);
                            if (flagw == true) {
                                Toast.makeText(context, "Implant Updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVacciney = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_IMPLANTS, intentVacciney);
                            finish();
                            break;
                        case "Condition":
                            Boolean flagj = MedicalConditionQuery.insertImplantsData(preferences.getInt(PrefConstants.CONNECTED_USERID), value);
                            if (flagj == true) {
                                Toast.makeText(context, "Medical Condition Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentImplantss = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_CONDITION, intentImplantss);
                            finish();
                            break;

                        case "ConditionUpdate":
                            Boolean flag1ss = MedicalConditionQuery.updateImplantsData(preferences.getInt(PrefConstants.CONNECTED_USERID), value, data);
                            if (flag1ss == true) {
                                Toast.makeText(context, "Medical Condition updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentImplanto = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_CONDITION, intentImplanto);
                            finish();
                            break;
                        case "Hospital":
                            Boolean flag2 = HospitalQuery.insertHospitalData(preferences.getInt(PrefConstants.CONNECTED_USERID), value);
                            if (flag2 == true) {
                                Toast.makeText(context, "Hospital Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHospital = new Intent();
                            //  intentHospital.putExtra("Value", value);
                            setResult(RESULT_HOSPITAL, intentHospital);
                            finish();
                            break;

                        case "HospitalUpdate":
                            Boolean flag2s = HospitalQuery.updateHospitalData(preferences.getInt(PrefConstants.CONNECTED_USERID), value, data);
                            if (flag2s == true) {
                                Toast.makeText(context, "Hospital updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHospitals = new Intent();
                            //  intentHospital.putExtra("Value", value);
                            setResult(RESULT_HOSPITAL, intentHospitals);
                            finish();
                            break;
                        case "History":
                            String date = txtDate.getText().toString();
                            String doctor = txtDoctor.getText().toString();
                            String done = txtDone.getText().toString();
                            String otherH = txtOtherVaccine.getText().toString();
                            Boolean flag3 = HistoryQuery.insertHistoryData(preferences.getInt(PrefConstants.CONNECTED_USERID), value, date, doctor, done, otherH);
                            if (flag3 == true) {
                                Toast.makeText(context, "History Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHistory = new Intent();
                            // intentHistory.putExtra("Value", value);
                            setResult(RESULT_HISTORY, intentHistory);
                            finish();
                            break;

                        case "HistoryUpdate":
                            String dateu = txtDate.getText().toString();
                            String doctors = txtDoctor.getText().toString();
                            String dones = txtDone.getText().toString();
                            String otherHU = txtOtherVaccine.getText().toString();
                            Boolean flag3s = HistoryQuery.updateHistoryData(id, value, dateu, doctors, dones, otherHU);
                            if (flag3s == true) {
                                Toast.makeText(context, "History updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHistorys = new Intent();
                            // intentHistory.putExtra("Value", value);
                            setResult(RESULT_HISTORY, intentHistorys);
                            finish();
                            break;
                    }
                    break;

                } else {
                    Toast.makeText(context, "Value should not be empty", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void getValues(String connection) {
    }

    private void showViewDialog(Context context, String Message, String title) {
        final Dialog customDialog;

        // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        //  View customView = inflater.inflate(R.layout.dialog_input, null);
        // Build the dialog
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_living);
        customDialog.setCancelable(false);
        TextView txtNotes = customDialog.findViewById(R.id.txtNotes);
        txtNotes.setText(Html.fromHtml(Message));
        TextView txtNoteHeader = customDialog.findViewById(R.id.txtNoteHeader);
        txtNoteHeader.setText(title);
        TextView btnYes = customDialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void showSaveAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Save");
        alert.setMessage("Do you want to save information?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                txtSave.performClick();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
               finish();
            }
        });
        alert.show();
    }

}
