package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.DashBoard.AddPrescriptionActivity;
import com.mindyourlovedone.healthcare.DashBoard.FaxActivity;
import com.mindyourlovedone.healthcare.DashBoard.InstructionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.customview.NonScrollListView;
import com.mindyourlovedone.healthcare.database.AideQuery;
import com.mindyourlovedone.healthcare.database.AllergyQuery;
import com.mindyourlovedone.healthcare.database.AppointmentQuery;
import com.mindyourlovedone.healthcare.database.CardQuery;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DateQuery;
import com.mindyourlovedone.healthcare.database.EventNoteQuery;
import com.mindyourlovedone.healthcare.database.FinanceQuery;
import com.mindyourlovedone.healthcare.database.FormQuery;
import com.mindyourlovedone.healthcare.database.HistoryQuery;
import com.mindyourlovedone.healthcare.database.HospitalHealthQuery;
import com.mindyourlovedone.healthcare.database.HospitalQuery;
import com.mindyourlovedone.healthcare.database.InsuranceQuery;
import com.mindyourlovedone.healthcare.database.LivingQuery;
import com.mindyourlovedone.healthcare.database.MedInfoQuery;
import com.mindyourlovedone.healthcare.database.MedicalConditionQuery;
import com.mindyourlovedone.healthcare.database.MedicalImplantsQuery;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.PetQuery;
import com.mindyourlovedone.healthcare.database.PharmacyQuery;
import com.mindyourlovedone.healthcare.database.PrescriptionQuery;
import com.mindyourlovedone.healthcare.database.PrescriptionUpload;
import com.mindyourlovedone.healthcare.database.SpecialistQuery;
import com.mindyourlovedone.healthcare.database.VaccineQuery;
import com.mindyourlovedone.healthcare.database.VitalQuery;
import com.mindyourlovedone.healthcare.model.Allergy;
import com.mindyourlovedone.healthcare.model.Appoint;
import com.mindyourlovedone.healthcare.model.Card;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.model.Finance;
import com.mindyourlovedone.healthcare.model.Form;
import com.mindyourlovedone.healthcare.model.History;
import com.mindyourlovedone.healthcare.model.Hospital;
import com.mindyourlovedone.healthcare.model.Implant;
import com.mindyourlovedone.healthcare.model.Insurance;
import com.mindyourlovedone.healthcare.model.Living;
import com.mindyourlovedone.healthcare.model.Note;
import com.mindyourlovedone.healthcare.model.Pet;
import com.mindyourlovedone.healthcare.model.Pharmacy;
import com.mindyourlovedone.healthcare.model.Prescription;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.Specialist;
import com.mindyourlovedone.healthcare.model.Vaccine;
import com.mindyourlovedone.healthcare.model.VitalSigns;
import com.mindyourlovedone.healthcare.pdfCreation.EventPdfNew;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.IndividualNew;
import com.mindyourlovedone.healthcare.pdfdesign.InsurancePdfNew;
import com.mindyourlovedone.healthcare.pdfdesign.PrescriptionPdfNew;
import com.mindyourlovedone.healthcare.pdfdesign.SpecialtyNew;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class SpecialistsActivity extends AppCompatActivity implements View.OnClickListener {
    final static String TARGET_BASE_PATH = "/sdcard/MYLO/images/";
    // final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    String[] specialist;
    int[] profile;
    NonScrollListView listSpeciallist;
    ImageView imgBack, imgRight, imgHome;
    TextView txtTitle, txtUser;
    String from;
    boolean isEmergency, isInsurance;
    RelativeLayout header;
    TextView txtName;
    Preferences preferences;
    DBHelper dbHelper;
   // FloatingActionButton  floatAdd,floatOptions;
    ImageView floatOptions,floatAdd,floatOptions2;
    public static final int REQUEST_PRES = 100;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialists);
        initComponent();
        initUi();
        initListener();
    }

    private void initComponent() {
        databases();
        Intent i = getIntent();
        floatAdd = findViewById(R.id.floatAdd);
        floatOptions= findViewById(R.id.floatOptions);
        floatOptions2=findViewById(R.id.floatOptions2);
        txtTitle = findViewById(R.id.txtTitle);
        txtUser = findViewById(R.id.txtUser);
        header = findViewById(R.id.header);
        txtName = findViewById(R.id.txtName);
        String name="";
        if (preferences.getString(PrefConstants.CONNECTED_RELATION).equals("Other"))
        {
            name=preferences.getString(PrefConstants.CONNECTED_NAME)+"-"+preferences.getString(PrefConstants.CONNECTED_OtherRELATION);
        }else
        {
            name=preferences.getString(PrefConstants.CONNECTED_NAME)+"-"+preferences.getString(PrefConstants.CONNECTED_RELATION);

        }
        txtName.setText(name);
        //   imgRight= (ImageView) findViewById(R.id.imgRight);
        if (i.getExtras() != null) {
            from = i.getExtras().getString("FROM");
            if (from.equals("Speciality")) {
                floatAdd.setVisibility(View.GONE);
                txtTitle.setText("Specialty Contacts");
                txtName.setBackgroundColor(getResources().getColor(R.color.colorSpecialitySub));
                txtUser.setVisibility(View.GONE);
                //  imgRight.setVisibility(View.VISIBLE);
                header.setBackgroundResource(R.color.colorSpecialityYellow);
                profile = new int[]{R.drawable.sp_one, R.drawable.sp_two, R.drawable.sp_three, R.drawable.sp_four};
                //   specialist= new String[]{"DOCTORS","HOSPITALS AND \nOTHER HEALTH PROFESSIONALS", "PHARMACIES AND \nHOME MEDICAL EQUIPMENT", "HOME HEALTH SERVICES", "FINANCE, INSURANCE, LEGAL"};
                // specialist= new String[]{"DOCTORS & OTHER HEALTH\nPROFESSIONALS","HOSPITALS & REHABILITATION CENTERS", "PHARMACIES & HOME\nMEDICAL EQUIPMENT", "HOME HEALTH SERVICES", "FINANCE, INSURANCE, LEGAL"};
                specialist = new String[]{"Doctors & Other Health Care Professionals", "Urgent Care, TeleMed, Hospitals, Rehab, Home Care", "Pharmacies & Home Medical Equipment", "Finance, Legal, Other"};

                isEmergency = false;
                isInsurance = false;
            } else if (from.equals("Emergency")) {
                floatAdd.setVisibility(View.GONE);
                // imgRight.setVisibility(View.GONE);
                txtName.setBackgroundColor(getResources().getColor(R.color.colorEmerSubGreen));
                txtUser.setVisibility(View.GONE);
                txtTitle.setText("Personal & Medical Profile & Emergency Contacts");
                header.setBackgroundResource(R.color.colorEmerMainGreen);
                isEmergency = true;
                isInsurance = false;
                profile = new int[]{R.drawable.pp, R.drawable.emergency_two, R.drawable.emergency_three, R.drawable.emergency_four};
                specialist = new String[]{"Personal Profile", "Medical Profile", "Emergency Contacts & Health Care Proxy Agents", "Primary Physician"};

               /* profile=new int[]{R.drawable.contacts,R.drawable.medicalinfos,R.drawable.emer_contacts,R.drawable.physician,R.drawable.proxys};
                specialist= new String[] { "PERSONAL PROFILE", "MEDICAL PROFILE", "EMERGENCY CONTACTS AND \nHEALTH CARE PROXY AGENT", "PRIMARY PHYSICIAN", "HEALTH CARE PROXY AGENT" };
*/
            } else if (from.equals("Insurance")) {
                floatAdd.setVisibility(View.GONE);
                //  imgRight.setVisibility(View.VISIBLE);
                txtUser.setVisibility(View.GONE);
                txtTitle.setText("Insurance");
                txtName.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSub));
                header.setBackgroundResource(R.color.colorFive);
                profile = new int[]{R.drawable.insu_three, R.drawable.insu_two, R.drawable.insu_one};
                specialist = new String[]{"Insurance Companies", "Insurance Cards", "Insurance Forms"};
                isEmergency = false;
                isInsurance = true;
            } else if (from.equals("Event")) {
                floatAdd.setVisibility(View.GONE);
                txtName.setBackgroundColor(getResources().getColor(R.color.colorEventSubPink));
                txtUser.setVisibility(View.GONE);
                txtUser.setVisibility(View.GONE);
                //  imgRight.setVisibility(View.VISIBLE);
                txtTitle.setText("Notes, Appt. Checklist, ADLs, Vital Signs");
                header.setBackgroundResource(R.color.colorEventPink);
                profile = new int[]{R.drawable.eve, R.drawable.eve_one, R.drawable.eve_three, R.drawable.eve_four};
                specialist = new String[]{"Event Notes", "Routine Appointments", "Activities of Daily Living", "Vital Signs"};
                isEmergency = false;
                isInsurance = false;
            } else if (from.equals("Prescription")) {
                floatAdd.setVisibility(View.GONE);
                floatOptions.setVisibility(View.VISIBLE);
                floatOptions2.setVisibility(View.GONE);
                txtName.setBackgroundColor(getResources().getColor(R.color.colorPrescriptionGray));
                txtUser.setVisibility(View.GONE);
                //  imgRight.setVisibility(View.VISIBLE);
                txtTitle.setText("Prescriptions");
                header.setBackgroundResource(R.color.colorPrescriptionSub);
                profile = new int[]{R.drawable.pres_one, R.drawable.pres_two};
                specialist = new String[]{"Prescription Information", "Prescription List Upload"};
                isEmergency = false;
                isInsurance = false;
            }
        }
    }

    private void databases() {
        preferences = new Preferences(context);


        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));

        //PersonalInfoQuery p=new PersonalInfoQuery(context,dbHelper);
        MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
        AllergyQuery a = new AllergyQuery(context, dbHelper);
        MedicalImplantsQuery ml = new MedicalImplantsQuery(context, dbHelper);
        HistoryQuery o = new HistoryQuery(context, dbHelper);
        HospitalQuery h = new HospitalQuery(context, dbHelper);
        SpecialistQuery s = new SpecialistQuery(context, dbHelper);
        HospitalHealthQuery hhh = new HospitalHealthQuery(context, dbHelper);
        PharmacyQuery ph = new PharmacyQuery(context, dbHelper);
        AideQuery ad = new AideQuery(context, dbHelper);
        FinanceQuery f = new FinanceQuery(context, dbHelper);
        AppointmentQuery df = new AppointmentQuery(context, dbHelper);
        DateQuery da = new DateQuery(context, dbHelper);
        InsuranceQuery i = new InsuranceQuery(context, dbHelper);
        PetQuery pet = new PetQuery(context, dbHelper);
        EventNoteQuery e = new EventNoteQuery(context, dbHelper);
        LivingQuery l = new LivingQuery(context, dbHelper);
        CardQuery c = new CardQuery(context, dbHelper);
        MedicalConditionQuery mu = new MedicalConditionQuery(context, dbHelper);
        VaccineQuery v = new VaccineQuery(context, dbHelper);
        FormQuery fo = new FormQuery(context, dbHelper);
        ContactDataQuery cd=new ContactDataQuery(context,dbHelper);
        VitalQuery vc=new VitalQuery(context,dbHelper);
        PrescriptionUpload pu=new PrescriptionUpload(context,dbHelper);
        PrescriptionQuery p = new PrescriptionQuery(context, dbHelper);
        final RelativeConnection personalInfoList = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
preferences.putInt(PrefConstants.ID,personalInfoList.getId());
    }

    private void initListener() {
        imgHome.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOptions.setOnClickListener(this);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showInstructionDialog();
                if (from.equals("Speciality")) {
                    Intent ia = new Intent(context, InstructionActivity.class);
                    ia.putExtra("From", "SpecialitySection");
                    startActivity(ia);
                }else if (from.equals("Emergency")) {
                    Intent ia = new Intent(context, InstructionActivity.class);
                    ia.putExtra("From", "EmergencySection");
                    startActivity(ia);
                }else if (from.equals("Insurance")) {
                    Intent ia = new Intent(context, InstructionActivity.class);
                    ia.putExtra("From", "InsuranceSection");
                    startActivity(ia);
                }else if (from.equals("Event")) {
                    Intent ia = new Intent(context, InstructionActivity.class);
                    ia.putExtra("From", "EventSection");
                    startActivity(ia);
                }
                else if (from.equals("Prescription")) {
                    Intent ia = new Intent(context, InstructionActivity.class);
                    ia.putExtra("From", "PrescriptionSection");
                    startActivity(ia);
                }

            }
//            {
//
//                if (from.equals("Speciality")) {
//                    final String RESULT = Environment.getExternalStorageDirectory()
//                            + "/mylopdf/";
//                    File dirfile = new File(RESULT);
//                    dirfile.mkdirs();
//                    File file = new File(dirfile, "Specialty.pdf");
//                    if (file.exists()) {
//                        file.delete();
//                    }
//
//                    new Header().createPdfHeader(file.getAbsolutePath(),
//                            "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                    copyFile("ic_launcher.png");
//                    Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
//                    Header.addEmptyLine(1);
//                    Header.addusereNameChank("Specialty Contacts");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Header.addEmptyLine(1);
//
//                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Paragraph p = new Paragraph(" ");
//                    LineSeparator line = new LineSeparator();
//                    line.setOffset(-4);
//                    line.setLineColor(BaseColor.LIGHT_GRAY);
//                    p.add(line);
//                    try {
//                        Header.document.add(p);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                    Header.addEmptyLine(1);
//
//                  /* new Header().createPdfHeader(file.getAbsolutePath(),
//                            "Specialty");*/
//
//                    // Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                    //  Header.addEmptyLine(2);
//
//                    ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
//                    ArrayList<Hospital> HospitalList = HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    ArrayList<Pharmacy> PharmacyList = PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    // ArrayList<Aides> AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    ArrayList<Finance> financeList = FinanceQuery.fetchAllFinanceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//
//                    new Specialty(specialistsList, "Doctors");
//                    new Specialty("Hospital", HospitalList);
//                    new Specialty(PharmacyList);
//                    //   new Specialty(AidesList,1);
//                    new Specialty(1, financeList);
//
//                    Header.document.close();
//
//                } else if (from.equals("Emergency")) {
//                    final String RESULT = Environment.getExternalStorageDirectory()
//                            + "/mylopdf/";
//                    File dirfile = new File(RESULT);
//                    dirfile.mkdirs();
//                    File file = new File(dirfile, "Profile.pdf");
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                    new Header().createPdfHeader(file.getAbsolutePath(),
//                            "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                    copyFile("ic_launcher.png");
//                    Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
//                    Header.addEmptyLine(1);
//                    Header.addusereNameChank("Personal & Medical Profile");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Header.addEmptyLine(1);
//                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Paragraph p = new Paragraph(" ");
//                    LineSeparator line = new LineSeparator();
//                    line.setOffset(-4);
//                    line.setLineColor(BaseColor.LIGHT_GRAY);
//                    p.add(line);
//                    try {
//                        Header.document.add(p);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                    Header.addEmptyLine(1);
///*
//                    new Header().createPdfHeader(file.getAbsolutePath(),
//                            "Personal & Medical Profile");
//
//                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Header.addEmptyLine(2);*/
//
//                  /*  if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
//                        final ArrayList<Pet> PetLists = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//
//                        new Individual((PersonalInfoQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID))),PetLists);
//                    }
//                    else{*/
//
//                    final ArrayList<Pet> PetList = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    final RelativeConnection con = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    final ArrayList<ContactData> phonelist= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),con.getId(),"Connection");
//
//                    new Individual((MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID))), PetList, phonelist);
//                    // }
//                    // new MessageString().getProfileProfile(connection);
//                    final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    final ArrayList<Implant> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    final ArrayList<String> conditionList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    final ArrayList<Vaccine> vaccineList = VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//
//
//                    new Individual(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)), AllargyLists, implantsList, historList, hospitalList, conditionList, vaccineList);
//
//
//                    ArrayList<Emergency> emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
//                    ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
//                    // ArrayList<Proxy> proxyList=MyConnectionsQuery.fetchAllProxyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),3);
//                    new Individual("Emergency", emergencyList, phonelist);
//                    new Individual(specialistsList, "Physician");
//                    //   new Individual(proxyList);
//
//
//                    Header.document.close();
//
//                } else if (from.equals("Insurance")) {
//                    final String RESULT = Environment.getExternalStorageDirectory()
//                            + "/mylopdf/";
//                    File dirfile = new File(RESULT);
//                    dirfile.mkdirs();
//                    File file = new File(dirfile, "Insurance.pdf");
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                    new Header().createPdfHeader(file.getAbsolutePath(),
//                            "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                    copyFile("ic_launcher.png");
//                    Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
//                    Header.addEmptyLine(1);
//                    Header.addusereNameChank("Insurance");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Header.addEmptyLine(1);
//
//                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Paragraph p = new Paragraph(" ");
//                    LineSeparator line = new LineSeparator();
//                    line.setOffset(-4);
//                    line.setLineColor(BaseColor.LIGHT_GRAY);
//                    p.add(line);
//                    try {
//                        Header.document.add(p);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                    Header.addEmptyLine(1);
//
//                    /* new Header().createPdfHeader(file.getAbsolutePath(),
//                            "Insurance");
//
//                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));*/
//                    // Header.addEmptyLine(2);
//
//                    ArrayList<Insurance> insuranceList = InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    ArrayList<Card> CardList = CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    ArrayList<Form> formList = FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//
//                    new InsurancePdf(insuranceList);
//                    new InsurancePdf(CardList, 1);
//                    new InsurancePdf(formList, "form");
//
//                    Header.document.close();
//                } else if (from.equals("Event")) {
//                    final String RESULT = Environment.getExternalStorageDirectory()
//                            + "/mylopdf/";
//                    File dirfile = new File(RESULT);
//                    dirfile.mkdirs();
//                    File file = new File(dirfile, "Event.pdf");
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                    new Header().createPdfHeader(file.getAbsolutePath(),
//                            "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                    copyFile("ic_launcher.png");
//                    Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
//                    Header.addEmptyLine(1);
//                    Header.addusereNameChank("Event And Appointment Checklist");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Header.addEmptyLine(1);
//
//                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Paragraph p = new Paragraph(" ");
//                    LineSeparator line = new LineSeparator();
//                    line.setOffset(-4);
//                    line.setLineColor(BaseColor.LIGHT_GRAY);
//                    p.add(line);
//                    try {
//                        Header.document.add(p);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                    Header.addEmptyLine(1);
//
//                   /* new Header().createPdfHeader(file.getAbsolutePath(),
//                            "Event And Appointment Checklist");
//
//                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                    Header.addEmptyLine(2);*/
//
//                    ArrayList<Appoint> AppointList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    ArrayList<Note> NoteList = EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    new EventPdf(NoteList, 1);
//                    new EventPdf(AppointList);
//                    Living Live = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                    ArrayList<Living> LivingList = new ArrayList<Living>();
//                    LivingList.add(Live);
//                    new EventPdf(1, LivingList, 1);
//
//
//                    Header.document.close();
//                }
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setTitle("");
//
//                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int itemPos) {
//
//                        switch (itemPos) {
//                            /*    case 0: // email
//
//                             *//* emailAttachement(item);
//
//                        ShearedValues.activityID = getApplicationContext();*//*
//                                break;
//                            case 1: // email
//
//                       *//* bluetoothAttachement(new File(item.getAbsolutePath()),
//                                context);
//                        ShearedValues.activityID = getApplicationContext();*//*
//
//                                break;*/
//                            case 0: // view
//                                if (from.equals("Speciality")) {
//                                    StringBuffer result = new StringBuffer();
//                                    result.append(new MessageString().getDoctorsInfo());
//                                    result.append(new MessageString().getHospitalInfo());
//                                    result.append(new MessageString().getPharmacyInfo());
//                                    // result.append(new MessageString().getAideInfo());
//                                    result.append(new MessageString().getFinanceInfo());
//
//                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Specialty.pdf",
//                                            context, result);
//
//                                    System.out.println("\n" + result + "\n");
//
//                                } else if (from.equals("Emergency")) {
//                                    /*if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
//                                        StringBuffer result = new StringBuffer();
//                                        result.append(new MessageString().getProfileUser());
//                                        result.append(new MessageString().getMedicalInfo());
//                                        result.append(new MessageString().getEmergencyInfo());
//                                        result.append(new MessageString().getPhysicianInfo());
//                                     //   result.append(new MessageString().getProxyInfo());
//
//
//                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
//                                                + "/mylopdf/"
//                                                + "/Profile.pdf",
//                                                context, result);
//
//                                        System.out.println("\n" + result + "\n");
//                                    }else{*/
//                                    StringBuffer result = new StringBuffer();
//                                    result.append(new MessageString().getProfileProfile());
//                                    result.append(new MessageString().getMedicalInfo());
//                                    result.append(new MessageString().getLivingInfo());
//                                    result.append(new MessageString().getEmergencyInfo());
//                                    result.append(new MessageString().getPhysicianInfo());
//                                    result.append(new MessageString().getProxyInfo());
//
//                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Profile.pdf",
//                                            context, result);
//
//                                    System.out.println("\n" + result + "\n");
//                 /* new PDFDocumentProcess(Environment.getExternalStorageDirectory()
//                            + "/mylopdf/"
//                            + "/Profile.pdf", context,
//                            new MessageString().getProfileProfile(connection));*/
//                                    //  }
//                                } else if (from.equals("Insurance")) {
//                                    StringBuffer result = new StringBuffer();
//                                    result.append(new MessageString().getInsuranceInfo());
//                                    result.append(new MessageString().getInsuranceCard());
//                                    result.append(new MessageString().getFormInfo());
//
//                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Insurance.pdf",
//                                            context, result);
//
//                                    System.out.println("\n" + result + "\n");
//                                } else if (from.equals("Event")) {
//                                    StringBuffer result = new StringBuffer();
//                                    result.append(new MessageString().getEventInfo());
//                                    result.append(new MessageString().getAppointInfo());
//                                    result.append(new MessageString().getLivingInfo());
//
//
//                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/" + "/Event.pdf",
//                                            context, result);
//
//                                    System.out.println("\n" + result + "\n");
//                                }
//                                break;
//
//                            case 1: // email
//                                if (from.equals("Speciality")) {
//                                    File f = new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/" + "/Specialty.pdf");
//                                    emailAttachement(f, "Speciality");
//                                } else if (from.equals("Emergency")) {
//                                    File f = new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/" + "/Profile.pdf");
//                                    emailAttachement(f, "Profile");
//                                } else if (from.equals("Insurance")) {
//                                    File f = new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Insurance.pdf");
//                                    emailAttachement(f, "Insurance");
//                                } else if (from.equals("Event")) {
//                                    File f = new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Event.pdf");
//                                    emailAttachement(f, "Event");
//                                }
//                                break;
//
//                            case 2://Fax
//                                if (from.equals("Speciality")) {
//                                    showInstructionDialog();
//                                    //Commented by shradha for user instructions
//                                  /*  serverAttachement(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Specialty.pdf");*/
//
//                                  /*  File f =new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Specialty.pdf");
//                                    emailAttachement(f);*/
//                                } else if (from.equals("Emergency")) {
//                                    showInstructionDialog();
//                                   /* serverAttachement(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Profile.pdf");*/
//
//                                  /*  File f =new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Profile.pdf");
//                                    emailAttachement(f);*/
//                                } else if (from.equals("Insurance")) {
//                                    showInstructionDialog();
//                                   /* serverAttachement(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Insurance.pdf");*/
//
//                                    /*File f =new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Insurance.pdf");*/
//                                    // emailAttachement(f);
//                                } else if (from.equals("Event")) {
//                                    showInstructionDialog();
//                                   /* serverAttachement(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Event.pdf");*/
//
//                                  /*  File f =new File(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Event.pdf");
//                                    emailAttachement(f);*/
//                                }
//
//                                break;
//
//                        }
//                    }
//                });
//
//                builder.create().show();
//                // ((CarePlanActivity)context).CopyAssets();
//            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void showInstructionDialog() {
        final Dialog dialogInstruction = new Dialog(context);
        dialogInstruction.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInstruction.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_user_instruction, null);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);

        dialogInstruction.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogInstruction.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogInstruction.getWindow().setAttributes(lp);
        dialogInstruction.setCanceledOnTouchOutside(false);
        dialogInstruction.show();


        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInstruction.dismiss();
            }
        });
    }

/*
    private void showInstructionDialog() {
        final Dialog dialogInstruction=new Dialog(context);
        dialogInstruction.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInstruction.setContentView(R.layout.dialog_user_instruction);
        TextView txtOk=dialogInstruction.findViewById(R.id.txtOk);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInstruction.dismiss();
            }
        });
        dialogInstruction.show();
    }
*/

    private void serverAttachement(String path) {
        System.out.println("Path of the file    " + path);
        //WebService.sendPDFToFax(convertFileToByteArray(file));
        new FaxCustomDialog(SpecialistsActivity.this, path).show();
    }

    private void emailAttachement(File f, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
//        String name= preferences.getString(PrefConstants.CONNECTED_NAME);
        String username = preferences.getString(PrefConstants.USER_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                username + " - " + s); // subject


        String body = "Hi, \n" +
                "\n" +
                //"\n" + username +
                "I shared this document with you. Please check the attachment. \n" +
                "\n" +
                "Thank you,\n" +
                username;
        // "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", f);
        } else {
            uri = Uri.fromFile(f);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private void initUi() {
        imgHome = findViewById(R.id.imgHome);
        floatOptions = findViewById(R.id.floatOptions);
        floatAdd = findViewById(R.id.floatAdd);
        imgBack = findViewById(R.id.imgBack);
        imgRight = findViewById(R.id.imgRight);
        txtName = findViewById(R.id.txtName);
        listSpeciallist = findViewById(R.id.listSpecialist);
        SpecialistContactAdapter adapter = new SpecialistContactAdapter(context, specialist, profile, isEmergency, isInsurance, from);
        listSpeciallist.setAdapter(adapter);
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;

        try {
            File dir = new File(TARGET_BASE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.i("tag", "copyFile() " + filename);
            in = assetManager.open(filename);
            newFileName = TARGET_BASE_PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of " + newFileName);
            Log.e("tag", "Exception in copyFile() " + e.toString());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String name="";
        if (preferences.getString(PrefConstants.CONNECTED_RELATION).equals("Other"))
        {
            name= "<b>"+preferences.getString(PrefConstants.CONNECTED_NAME)+"</b> - "+preferences.getString(PrefConstants.CONNECTED_OtherRELATION);  }else
        {
           name= "<b>"+preferences.getString(PrefConstants.CONNECTED_NAME)+"</b> - "+preferences.getString(PrefConstants.CONNECTED_RELATION);

        }
        txtName.setText(Html.fromHtml(name));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatOptions:
                showFloatDialog();
                break;

            case R.id.floatAdd:
                Intent i = new Intent(context, AddPrescriptionActivity.class);
                i.putExtra("IsEdit", false);
                startActivityForResult(i, REQUEST_PRES);
             /*   Intent intentPrescription = new Intent(context, PrescriptionActivity.class);
                startActivity(intentPrescription);*/
                break;

            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
        }
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PRES && data != null) {
        }
    }

    private void showFloatDialog() {
        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png", context);
        calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
        profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);
        Image pp1 = null,pp2 = null,pp3 = null;
        //-----------------------enerate pdf code
        if (from.equals("Speciality")) {
            final String RESULT = Environment.getExternalStorageDirectory()
                    + "/mylopdf/";
            File dirfile = new File(RESULT);
            dirfile.mkdirs();
            File file = new File(dirfile, "Specialty.pdf");
            if (file.exists()) {
                file.delete();
            }

           /* new Header().createPdfHeader(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME));
            copyFile("ic_launcher.png");
            Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
            Header.addEmptyLine(1);
            Header.addusereNameChank("Specialty Contacts");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Header.addEmptyLine(1);

            Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Paragraph p = new Paragraph(" ");
            LineSeparator line = new LineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.LIGHT_GRAY);
            p.add(line);
            try {
                Header.document.add(p);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Header.addEmptyLine(1);

                  *//* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Specialty");*//*

            // Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
            //  Header.addEmptyLine(2);

            ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
            ArrayList<Hospital> HospitalList = HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Pharmacy> PharmacyList = PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            // ArrayList<Aides> AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Finance> financeList = FinanceQuery.fetchAllFinanceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

           // new Specialty(specialistsList, "Doctors");
            for(int i=0;i<specialistsList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), specialistsList.get(i).getId(),"Doctor");
                new Specialty(specialistsList.get(i), "Doctors", phonelists,i);
            }
           // new Specialty("Hospital", HospitalList);
            for(int i=0;i<HospitalList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), HospitalList.get(i).getId(),"Hospital");
                new Specialty(HospitalList.get(i), "Hospital", phonelists,i);
            }
           // new Specialty(PharmacyList);
            for(int i=0;i<PharmacyList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), PharmacyList.get(i).getId(),"Pharmacy");
                new Specialty(PharmacyList.get(i), "Pharmacy", phonelists,i);
            }
            //   new Specialty(AidesList,1);
           // new Specialty(1, financeList);
            for(int i=0;i<financeList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), financeList.get(i).getId(),"Finance");
                new Specialty(financeList.get(i), "Finance", phonelists,i);
            }

            Header.document.close();*/
            new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"SPECIALTY CONTACTS", calendarWite, profileWite);

            HeaderNew.addusereNameChank("SPECIALTY CONTACTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
            HeaderNew.addEmptyLine(1);

            pp1=preferences.addFile("sp_one.png", context);
            pp2=preferences.addFile("sp_two.png", context);
            pp3=preferences.addFile("sp_three.png", context);
            Image pp4=preferences.addFile("sp_four.png", context);
            ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
            ArrayList<Hospital> HospitalList = HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Pharmacy> PharmacyList = PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            // ArrayList<Aides> AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Finance> financeList = FinanceQuery.fetchAllFinanceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

            // new Specialty(specialistsList, "Doctors");
            for(int i=0;i<specialistsList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), specialistsList.get(i).getId(),"Doctor");
                new SpecialtyNew(specialistsList.get(i), "Doctors", phonelists,i,pp1);
            }
            // new Specialty("Hospital", HospitalList);
            for(int i=0;i<HospitalList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), HospitalList.get(i).getId(),"Hospital");
                new SpecialtyNew(HospitalList.get(i), "Hospital", phonelists,i,pp2);
            }
            // new Specialty(PharmacyList);
            for(int i=0;i<PharmacyList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), PharmacyList.get(i).getId(),"Pharmacy");
                new SpecialtyNew(PharmacyList.get(i), "Pharmacy", phonelists,i,pp3);
            }
            //   new Specialty(AidesList,1);
            // new Specialty(1, financeList);
            for(int i=0;i<financeList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), financeList.get(i).getId(),"Finance");
                new SpecialtyNew(financeList.get(i), "Finance", phonelists,i,pp4);
            }

            HeaderNew.document.close();

        } else if (from.equals("Emergency")) {
            final String RESULT = Environment.getExternalStorageDirectory()
                    + "/mylopdf/";
            File dirfile = new File(RESULT);
            dirfile.mkdirs();
            File file = new File(dirfile, "Profile.pdf");
            if (file.exists()) {
                file.delete();
            }
           /* new Header().createPdfHeader(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME));
            copyFile("ic_launcher.png");
            Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
            Header.addEmptyLine(1);
            Header.addusereNameChank("Personal, Medical and  Emergency Info");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Header.addEmptyLine(1);
            Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Paragraph p = new Paragraph(" ");
            LineSeparator line = new LineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.LIGHT_GRAY);
            p.add(line);
            try {
                Header.document.add(p);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Header.addEmptyLine(1);

            final ArrayList<Pet> PetList = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final RelativeConnection personalInfoList = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

            final ArrayList<ContactData> phonelist= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),-1,"Personal Profile");

            new Individual((MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID))), PetList, phonelist);
            // }
            // new MessageString().getProfileProfile(connection);
            final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<Implant> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<String> conditionList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<Vaccine> vaccineList = VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));


            new Individual(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)), AllargyLists, implantsList, historList, hospitalList, conditionList, vaccineList);

            ArrayList<Emergency> emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
            for(int i=0;i<emergencyList.size();i++) {
                final ArrayList<ContactData> phonelistd= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), emergencyList.get(i).getId(),"Emergency");
                new Individual("Emergency", emergencyList.get(i), phonelistd,i);
            }
            ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
            for(int i=0;i<specialistsList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), specialistsList.get(i).getId(),"Physician");
                new Individual("Physician", specialistsList.get(i), phonelists,i);
            }

            Header.document.close();*/

            new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"PERSONAL, MEDICAL AND  EMERGENCY INFO", calendarWite, profileWite);

            HeaderNew.addusereNameChank("PERSONAL, MEDICAL AND  EMERGENCY INFO");//preferences.getString(PrefConstants.CONNECTED_NAME));
            HeaderNew.addEmptyLine(1);

            pp1=preferences.addFile("pp.png", context);
            pp2=preferences.addFile("emergency_two.png", context);
            pp3=preferences.addFile("emergency_three.png", context);
            Image pp4=preferences.addFile("emergency_four.png", context);
            final ArrayList<Pet> PetList = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final RelativeConnection personalInfoList = MyConnectionsQuery.fetchEmailRecord(1);

            final ArrayList<ContactData> phonelist= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),-1,"Personal Profile");

            new IndividualNew(personalInfoList, PetList, phonelist,pp1);
            // }
            // new MessageString().getProfileProfile(connection);
            final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<Implant> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<String> conditionList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            final ArrayList<Vaccine> vaccineList = VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));


            new IndividualNew(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)), AllargyLists, implantsList, historList, hospitalList, conditionList, vaccineList,pp2);

            ArrayList<Emergency> emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
            for(int i=0;i<emergencyList.size();i++) {
                final ArrayList<ContactData> phonelistd= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), emergencyList.get(i).getId(),"Emergency");
                new IndividualNew("Emergency", emergencyList.get(i), phonelistd,i,pp3);
            }
            ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
            for(int i=0;i<specialistsList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), specialistsList.get(i).getId(),"Primary");
                new IndividualNew("Physician", specialistsList.get(i), phonelists,i,pp4 );
            }
            HeaderNew.document.close();
        } else if (from.equals("Insurance")) {
            final String RESULT = Environment.getExternalStorageDirectory()
                    + "/mylopdf/";
            File dirfile = new File(RESULT);
            dirfile.mkdirs();
            File file = new File(dirfile, "Insurance.pdf");
            if (file.exists()) {
                file.delete();
            }
           /* new Header().createPdfHeader(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME));
            copyFile("ic_launcher.png");
            Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
            Header.addEmptyLine(1);
            Header.addusereNameChank("Insurance");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Header.addEmptyLine(1);

            Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Paragraph p = new Paragraph(" ");
            LineSeparator line = new LineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.LIGHT_GRAY);
            p.add(line);
            try {
                Header.document.add(p);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Header.addEmptyLine(1);

                    *//* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Insurance");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));*//*
            // Header.addEmptyLine(2);

            ArrayList<Insurance> insuranceList = InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Card> CardList = CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Form> formList = FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

           // new InsurancePdf(insuranceList);
            for(int i=0;i<insuranceList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), insuranceList.get(i).getId(),"Insurance");
                final ArrayList<ContactData> aphonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), insuranceList.get(i).getId(),"Agent");
                new InsurancePdf(insuranceList.get(i), "Insurance", phonelists,i,aphonelists);
            }
            new InsurancePdf(CardList, 1);
            new InsurancePdf(formList, "form");

            Header.document.close();*/

            new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"INSURANCE", calendarWite, profileWite);

            HeaderNew.addusereNameChank("INSURANCE");//preferences.getString(PrefConstants.CONNECTED_NAME));
            HeaderNew.addEmptyLine(1);

            pp1=preferences.addFile("insu_one.png", context);
            pp2=preferences.addFile("insu_two.png", context);
            pp3=preferences.addFile("insu_three.png", context);
            ArrayList<Insurance> insuranceList = InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Card> CardList = CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Form> formList = FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

            // new InsurancePdf(insuranceList);
            for(int i=0;i<insuranceList.size();i++) {
                final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), insuranceList.get(i).getId(),"Insurance");
                final ArrayList<ContactData> aphonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), insuranceList.get(i).getId(),"Agent");
                new InsurancePdfNew(insuranceList.get(i), "Insurance", phonelists,i,aphonelists,pp3);
            }
            new InsurancePdfNew(CardList, 1,pp2);
            new InsurancePdfNew(formList, "form",pp1);

            HeaderNew.document.close();
        } else if (from.equals("Event")) {
            final String RESULT = Environment.getExternalStorageDirectory()
                    + "/mylopdf/";
            File dirfile = new File(RESULT);
            dirfile.mkdirs();
            File file = new File(dirfile, "Event.pdf");
            if (file.exists()) {
                file.delete();
            }
            /*new Header().createPdfHeader(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME));
            copyFile("ic_launcher.png");
            Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
            Header.addEmptyLine(1);
            Header.addusereNameChank("Notes, Appointments, ADLS, & Vital Signs");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Header.addEmptyLine(1);

            Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Paragraph p = new Paragraph(" ");
            LineSeparator line = new LineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.LIGHT_GRAY);
            p.add(line);
            try {
                Header.document.add(p);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Header.addEmptyLine(1);

                   *//* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Event And Appointment Checklist");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(2);*//*

            ArrayList<Appoint> AppointList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Note> NoteList = EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new EventPdf(NoteList, 1);
            new EventPdf(AppointList);
            Living Live = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Living> LivingList = new ArrayList<Living>();
            LivingList.add(Live);
            new EventPdf(1, LivingList, 1);
            ArrayList<VitalSigns> HospitalList = VitalQuery.fetchAllVitalRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new EventPdf("Vital", HospitalList);

            Header.document.close();*/

            new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"NOTES, ROUTINE APPTS., ADLs, VITAL SIGNS", calendarWite, profileWite);

            HeaderNew.addusereNameChank("NOTES, ROUTINE APPTS., ADLs, VITAL SIGNS");//preferences.getString(PrefConstants.CONNECTED_NAME));
            HeaderNew.addEmptyLine(1);

            pp1=preferences.addFile("eve.png", context);
            pp2=preferences.addFile("eve_one.png", context);
            pp3=preferences.addFile("eve_three.png", context);
            Image pp4=preferences.addFile("eve_four.png", context);
            ArrayList<Appoint> AppointList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Note> NoteList = EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new EventPdfNew(NoteList, 1,pp1);
            new EventPdfNew(AppointList,pp2);
            Living Live = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            ArrayList<Living> LivingList = new ArrayList<Living>();
            LivingList.add(Live);
            new EventPdfNew(1, LivingList.get(0), 1,pp3);
            ArrayList<VitalSigns> HospitalList = VitalQuery.fetchAllVitalRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new EventPdfNew("Vital", HospitalList,pp4);
            HeaderNew.document.close();
        } else if (from.equals("Prescription")) {
            final String RESULT = Environment.getExternalStorageDirectory()
                    + "/mylopdf/";
            File dirfile = new File(RESULT);
            dirfile.mkdirs();
            File file = new File(dirfile, "PrescriptionTracker.pdf");
            if (file.exists()) {
                file.delete();
            }
            /*new Header().createPdfHeader(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME));
            copyFile("ic_launcher.png");
            Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
            Header.addEmptyLine(1);
            Header.addusereNameChank("Prescription Tracker");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Header.addEmptyLine(1);

            Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
            Paragraph p = new Paragraph(" ");
            LineSeparator line = new LineSeparator();
            line.setOffset(-4);
            line.setLineColor(BaseColor.LIGHT_GRAY);
            p.add(line);
            try {
                Header.document.add(p);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Header.addEmptyLine(1);

            ArrayList<Prescription> prescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new PrescriptionPdf(prescriptionList);
            ArrayList<Form> prescriptionLists =PrescriptionUpload.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new PrescriptionPdf(prescriptionLists,1);

            Header.document.close();*/
            new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                    "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"PRESCRIPTIONS", calendarWite, profileWite);

            HeaderNew.addusereNameChank("PRESCRIPTIONS");//preferences.getString(PrefConstants.CONNECTED_NAME));
            HeaderNew.addEmptyLine(1);

            pp1=preferences.addFile("pres_one.png", context);
            pp2=preferences.addFile("pres_two.png", context);
           // pp3=preferences.addFile("dir_three.png", context);
            ArrayList<Prescription> prescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new PrescriptionPdfNew(prescriptionList,pp1);
            ArrayList<Form> prescriptionLists =PrescriptionUpload.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            new PrescriptionPdfNew(prescriptionLists,1,pp2);

            HeaderNew.document.close();
        }



        /*Dialo--------------------------*/
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
     //   final ImageView floatCancel = dialogview.findViewById(R.id.floatCancel);  // Rahul
        final FloatingActionButton floatViewPdf = dialogview.findViewById(R.id.floatContact);
        floatViewPdf.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatEmail = dialogview.findViewById(R.id.floatNew);
        floatEmail.setImageResource(R.drawable.closee);

        TextView txtNew = dialogview.findViewById(R.id.txtNew);
        txtNew.setText(getResources().getString(R.string.EmailReports));

        TextView txtContact = dialogview.findViewById(R.id.txtContact);
        txtContact.setText(getResources().getString(R.string.ViewReports));

        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        // int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        rlView.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        floatCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        floatEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("Speciality")) {
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/mylopdf/" + "/Specialty.pdf");
                    emailAttachement(f, "Speciality");
                } else if (from.equals("Emergency")) {
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/mylopdf/" + "/Profile.pdf");
                    emailAttachement(f, "Profile");
                } else if (from.equals("Insurance")) {
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/Insurance.pdf");
                    emailAttachement(f, "Insurance");
                } else if (from.equals("Event")) {
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/Event.pdf");
                    emailAttachement(f, "Notes, Appointments, ADLs, Vital Signs");
                }else if (from.equals("Prescription")) {
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/PrescriptionTracker.pdf");
                    emailAttachement(f, "Prescriptions");
                }
                dialog.dismiss();
            }

        });

        floatViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("Speciality")) {
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getDoctorsInfo());
                    result.append(new MessageString().getHospitalInfo());
                    result.append(new MessageString().getPharmacyInfo());
                    // result.append(new MessageString().getAideInfo());
                    result.append(new MessageString().getFinanceInfo());

                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/Specialty.pdf",
                            context, result);

                    System.out.println("\n" + result + "\n");

                } else if (from.equals("Emergency")) {
                                    /*if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getProfileUser());
                                        result.append(new MessageString().getMedicalInfo());
                                        result.append(new MessageString().getEmergencyInfo());
                                        result.append(new MessageString().getPhysicianInfo());
                                     //   result.append(new MessageString().getProxyInfo());


                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                                + "/mylopdf/"
                                                + "/Profile.pdf",
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                                    }else{*/
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getProfileProfile());
                    result.append(new MessageString().getMedicalInfo());
                    result.append(new MessageString().getLivingInfo());
                    result.append(new MessageString().getEmergencyInfo());
                    result.append(new MessageString().getPhysicianInfo());
                    result.append(new MessageString().getProxyInfo());

                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/Profile.pdf",
                            context, result);

                    System.out.println("\n" + result + "\n");
                 /* new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/Profile.pdf", context,
                            new MessageString().getProfileProfile(connection));*/
                    //  }
                } else if (from.equals("Insurance")) {
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getInsuranceInfo());
                    result.append(new MessageString().getInsuranceCard());
                    result.append(new MessageString().getFormInfo());

                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/Insurance.pdf",
                            context, result);

                    System.out.println("\n" + result + "\n");
                } else if (from.equals("Event")) {
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getEventInfo());
                    result.append(new MessageString().getAppointInfo());
                    result.append(new MessageString().getLivingInfo());


                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/" + "/Event.pdf",
                            context, result);

                    System.out.println("\n" + result + "\n");
                }else if (from.equals("Prescription")) {
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getInsuranceInfo());
                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/" + "/PrescriptionTracker.pdf",
                            context, result);

                    System.out.println("\n" + result + "\n");
                }
                dialog.dismiss();
            }
        });

        RelativeLayout rlFloatfax = dialogview.findViewById(R.id.rlFloatfax);
if(from.equals("Emergency")||from.equals("Prescription") )
{
    rlFloatfax.setVisibility(View.VISIBLE);
}

        rlFloatfax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equals("Emergency")) {

                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Profile.pdf";

                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getMedicalInfo());
                new PDFDocumentProcess(path,
                        context, result);
                Intent i = new Intent(context, FaxActivity.class);
                i.putExtra("PATH", path);
                startActivity(i);
                }
                else   if (from.equals("Prescription")) {
                    String path = Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/PrescriptionTracker.pdf";
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getInsuranceInfo());
                    new PDFDocumentProcess(path,
                            context, result);
                    Intent i = new Intent(context, FaxActivity.class);
                    i.putExtra("PATH", path);
                    startActivity(i);
                }
                dialog.dismiss();
            }
        });
    }

    public void CopyReadAssetss(String documentPath) {
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
}
