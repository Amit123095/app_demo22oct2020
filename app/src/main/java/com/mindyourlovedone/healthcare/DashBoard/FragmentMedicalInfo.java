package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.Connections.MedAdapter;
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
import com.mindyourlovedone.healthcare.model.MedInfo;
import com.mindyourlovedone.healthcare.model.Vaccine;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.IndividualNew;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentMedicalInfo extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final int REQUEST_ALLERGY = 100;
    public static final int REQUEST_HISTORY = 200;
    public static final int REQUEST_IMPLANTS = 300;
    public static final int REQUEST_HOSPITAL = 400;
    private static final int REQUEST_CONDITION = 500;
    private static final int REQUEST_VACCINE = 700;
    public static final int REQUEST_BLOOD = 1;
    MedInfo medInfo;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    View rootview;
    RelativeLayout rlMedical, rlDrugDesc, rlDrinkDesc, rlTobacoDesc;
    ImageView imgBack, imgDone, imgRight, imgInfo;
    TextView txtTitle, imgAddFlueShot, txtSave;
    EditText etPreNote, etAllergyNote, etMouthNote, etVisionNote, etAideNote, etFunctionalNote, etDietNote, etVaccineNote, etImplantNote, etHistoryNote, etBloodNote;
    TextView imgAddPneumonia, imgAddHPV, imgAddRubella, imgAddVaricella, imgAddShingles, imgAddTetanus, imgAddHepatitis, imgAddFlue, imgAddFlueNH, imgAddPneumococcal;
    TextView txtFlueShotDate, txtPneumoniaDate, txtHPVDate, txtRubellaDate, txtVaricellaDate, txtShinglesDate, txtTetanusDate, txtHepatitisDate, txtFlueDate, txtFlueNHDate, txtPneumococcalDate;
    EditText etFt, etInch, etWeight, etAdditional, etPet;
    ToggleButton tbGlass, tbLense, tbFalse, tbImplants, tbHearingAid, tbMouth, tbColor, tbSpeech, tbFeed, tbToilet, tbMedicate;
    RadioButton rbYes, rbNo, rbDrugCurrent, rbDrugPast, rbDrugNever, rbDrinkCurrent, rbDrinkPast, rbDrinkNever, rbTobacoCurrent, rbTobacoPast, rbTobacoNever;
    //String glass = "", lense = "", falses = "", implants = "", aid = "", donor = "", mouth = "", blind = "", speech = "", feed = "", toilet = "", medicate = "";
    String glass = "NO", lense = "NO", falses = "NO", implants = "NO", aid = "NO", donor = "NO", mouth = "NO", blind = "NO", speech = "NO", feed = "NO", toilet = "NO", medicate = "NO";

    TextView txtTobacoType, txtTobacoAmt, txtTobacoYear, txtDrugType, txtDrugAmt, txtDrugYear, txtDrinkAmt, txtDrinkYear;
    String tobaco = "Never", t_type = "", t_amt = "", t_year = "";
    String drug = "Never", drug_type = "", drug_amt = "", drug_year = "";
    String drink = "Never", drink_amt = "", drink_year = "";
    String ft, inch, weight, color, lang1, lang2, blood, pet;
    RadioGroup rgDonor, rgDrug, rgDrink, rgTobaco;
    TextView txtName;
    Spinner spinnerEyes, spinnerBlood, spinnerLang;
    TextView txtAddAllergy;
    TextView txtAddCondition, txtAddImplants, txtAddHistory, txtAddHospital, txtAddVaccine;
    ImageView imgDonnerDrop, imgVisionDrop, imgAidsDrop, imgDietDrop, imgVaccineDrop, imgTobacoDrop, imgDrugDrop, imgDrinkDrop, imgAddAllergy, imgAddImplants, imgAddHospital, imgAddHistory, imgAddCondition, imgAddVaccine, imgTeethDrop;
    ListView ListHistory, ListAllergy, ListImplants, ListHospital, ListCondition, ListVaccine;
    String note = "", allergynote = "";
    String mouthnote = "";
    String visionnote = "";
    String Aidenote = "";
    String functionnote = "";
    String dietnote = "", vaccinenote = "", historynote = "", implantsnote = "", bloodnote = "";
    ArrayList historList = new ArrayList();
    ArrayList hospitalList = new ArrayList();
    String[] LangList = {"English", "French", "German", "Greek", "Italian", "Japanese", "Russian", "Spanish"};
    String[] EyesList = {"Blue", "Brown", "Green", "Hazel"};
    String[] BloodList = {"", "A - negative", "A - positive", "AB - negative", "AB - positive", "B - negative", "B - positive", "O - negative", "O - positive", "I don't know"};
    Preferences preferences;
    DBHelper dbHelper;
    FloatingActionButton floatProfile;
    ImageView floatOptions;
    ToggleButton tbOrgan;
    TextView txtBlood;
    ImageView imgBloodDrop;
    LinearLayout linorgan, llVision, llAidsDiet, llSubDiet, linVaccine, lintobaco, lindrug, lindrink, llSubAllergis, llSubPre, llSubImplants, llSubHistory, llSubHospital, llSubBlood, llSubTeeth;
    RelativeLayout rlAllergis;
    //    View viewAllergyBottom, viewBloodBottom, viewPreBottom, viewImplantsBottom, viewHistoryBottom, viewHospitalBottom;
    boolean flagOrgan = false, flagVission = false, flagAids = false, flagDiet = false, flagVaccine = false, flagTobaco = false, flagDrug = false, flagDrink = false, flagAllergy = false, flagBlood = false, flagPre = false, flagImplants = false, flagHistory = false, flagHospital = false, flagTeeth = false;
    private static int RESULT_BLOOD = 1;
    RelativeLayout headAllergy, headPre, headImplants, headHistory, headHospital, headBlood, headTeeath, headDiet, headOrgan, headTobaco, headDrug, headDrink;
    RelativeLayout headVision, headAids, headVaccine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_medical_info, null);
        preferences = new Preferences(getActivity());
        initComponent();
        initUI();
        initListener();
        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        MedInfoQuery p = new MedInfoQuery(getActivity(), dbHelper);
        AllergyQuery a = new AllergyQuery(getActivity(), dbHelper);
        MedicalImplantsQuery m = new MedicalImplantsQuery(getActivity(), dbHelper);
        MedicalConditionQuery f = new MedicalConditionQuery(getActivity(), dbHelper);
        HospitalQuery h = new HospitalQuery(getActivity(), dbHelper);
        HistoryQuery hi = new HistoryQuery(getActivity(), dbHelper);
        VaccineQuery v = new VaccineQuery(getActivity(), dbHelper);
    }

    private void initListener() {
        floatProfile.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        txtSave.setOnClickListener(this);

        imgDone.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        txtAddAllergy.setOnClickListener(this);
        txtAddVaccine.setOnClickListener(this);
        txtAddHistory.setOnClickListener(this);
        txtAddHospital.setOnClickListener(this);
        txtAddImplants.setOnClickListener(this);
        txtAddCondition.setOnClickListener(this);

        imgAddAllergy.setOnClickListener(this);
        imgAddVaccine.setOnClickListener(this);
        imgAddHistory.setOnClickListener(this);
        imgAddHospital.setOnClickListener(this);
        imgAddImplants.setOnClickListener(this);
        imgTeethDrop.setOnClickListener(this);
        imgAddCondition.setOnClickListener(this);
        imgDonnerDrop.setOnClickListener(this);
        imgVisionDrop.setOnClickListener(this);
        imgAidsDrop.setOnClickListener(this);
        imgDietDrop.setOnClickListener(this);
        imgVaccineDrop.setOnClickListener(this);
        imgTobacoDrop.setOnClickListener(this);
        imgDrugDrop.setOnClickListener(this);
        imgDrinkDrop.setOnClickListener(this);


        imgAddPneumonia.setOnClickListener(this);
        imgAddFlueShot.setOnClickListener(this);
        imgAddHPV.setOnClickListener(this);
        imgAddRubella.setOnClickListener(this);
        imgAddVaricella.setOnClickListener(this);
        imgAddShingles.setOnClickListener(this);
        imgAddTetanus.setOnClickListener(this);
        imgAddHepatitis.setOnClickListener(this);
        imgAddFlue.setOnClickListener(this);
        imgAddFlueNH.setOnClickListener(this);
        imgAddPneumococcal.setOnClickListener(this);
        imgBloodDrop.setOnClickListener(this);
        tbGlass.setOnCheckedChangeListener(this);
        tbMouth.setOnCheckedChangeListener(this);
        tbLense.setOnCheckedChangeListener(this);
        tbFalse.setOnCheckedChangeListener(this);
        tbImplants.setOnCheckedChangeListener(this);
        tbHearingAid.setOnCheckedChangeListener(this);

        tbSpeech.setOnCheckedChangeListener(this);
        tbColor.setOnCheckedChangeListener(this);
        tbMedicate.setOnCheckedChangeListener(this);
        tbToilet.setOnCheckedChangeListener(this);
        tbFeed.setOnCheckedChangeListener(this);

        headAllergy.setOnClickListener(this);
        headPre.setOnClickListener(this);
        headImplants.setOnClickListener(this);
        headHistory.setOnClickListener(this);
        headHospital.setOnClickListener(this);
        headBlood.setOnClickListener(this);
        headTeeath.setOnClickListener(this);
        headDiet.setOnClickListener(this);
        headOrgan.setOnClickListener(this);
        headTobaco.setOnClickListener(this);
        headDrug.setOnClickListener(this);
        headDrink.setOnClickListener(this);
        headVision.setOnClickListener(this);
        headAids.setOnClickListener(this);
        headVaccine.setOnClickListener(this);

    }

    private void initUI() {
        headAllergy = rootview.findViewById(R.id.headAllergy);
        headPre = rootview.findViewById(R.id.headPre);
        headImplants = rootview.findViewById(R.id.headImplants);
        headHistory = rootview.findViewById(R.id.headHistory);
        headHospital = rootview.findViewById(R.id.headHospital);

        headBlood = rootview.findViewById(R.id.headBlood);
        headTeeath = rootview.findViewById(R.id.headTeeath);
        headDiet = rootview.findViewById(R.id.headDiet);
        headOrgan = rootview.findViewById(R.id.headOrgan);
        headTobaco = rootview.findViewById(R.id.headTobaco);

        headDrug = rootview.findViewById(R.id.headDrug);
        headDrink = rootview.findViewById(R.id.headDrink);
        headVision = rootview.findViewById(R.id.headVision);
        headAids = rootview.findViewById(R.id.headAids);
        headVaccine = rootview.findViewById(R.id.headVaccine);

        rlAllergis = rootview.findViewById(R.id.rlAllergis);
        llSubAllergis = rootview.findViewById(R.id.llSubAllergis);
        llSubPre = rootview.findViewById(R.id.llSubPre);
        llSubImplants = rootview.findViewById(R.id.llSubImplants);
        llSubHistory = rootview.findViewById(R.id.llSubHistory);
        llSubHospital = rootview.findViewById(R.id.llSubHospital);
        llSubBlood = rootview.findViewById(R.id.llSubBlood);
        llSubTeeth = rootview.findViewById(R.id.llSubTeeth);

        llVision = rootview.findViewById(R.id.llVision);
        llAidsDiet = rootview.findViewById(R.id.llAidsDiet);
        llSubDiet = rootview.findViewById(R.id.llSubDiet);
        linVaccine = rootview.findViewById(R.id.linVaccine);
        lintobaco = rootview.findViewById(R.id.lintobaco);
        lindrug = rootview.findViewById(R.id.lindrug);
        lindrink = rootview.findViewById(R.id.lindrink);
        linorgan = rootview.findViewById(R.id.linorgan);

//        viewAllergyBottom = rootview.findViewById(R.id.viewAllergyBottom);
//        viewPreBottom = rootview.findViewById(R.id.viewPreBottom);
//        viewImplantsBottom = rootview.findViewById(R.id.viewImplantsBottom);
//        viewHistoryBottom = rootview.findViewById(R.id.viewHistoryBottom);
//        viewHospitalBottom = rootview.findViewById(R.id.viewHospitalBottom);
//        viewBloodBottom = rootview.findViewById(R.id.viewBloodBottom);

        imgBloodDrop = rootview.findViewById(R.id.imgBloodDrop);
        txtBlood = rootview.findViewById(R.id.txtBlood);
        txtBlood.setFocusable(false);

        floatProfile = rootview.findViewById(R.id.floatProfile);
        floatOptions = rootview.findViewById(R.id.floatOptions);
        imgInfo = getActivity().findViewById(R.id.imgHelp);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "Medical");
                startActivity(i);
            }
        });
        rlMedical = rootview.findViewById(R.id.rlMedical);

        txtAddAllergy = rootview.findViewById(R.id.txtAddAllergy);
        txtAddCondition = rootview.findViewById(R.id.txtAddCondition);
        txtAddImplants = rootview.findViewById(R.id.txtAddImplants);
        txtAddHistory = rootview.findViewById(R.id.txtAddHistory);
        txtAddHospital = rootview.findViewById(R.id.txtAddHospital);
        txtAddVaccine = rootview.findViewById(R.id.txtAddVaccine);

        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Medical Profile");

        txtFlueShotDate = rootview.findViewById(R.id.txtFlueShotDate);
        txtPneumoniaDate = rootview.findViewById(R.id.txtPneumoniaDate);
        txtHPVDate = rootview.findViewById(R.id.txtHPVDate);
        txtRubellaDate = rootview.findViewById(R.id.txtRubellaDate);
        txtVaricellaDate = rootview.findViewById(R.id.txtVaricellaDate);
        txtShinglesDate = rootview.findViewById(R.id.txtShinglesDate);
        txtTetanusDate = rootview.findViewById(R.id.txtTetanusDate);
        txtHepatitisDate = rootview.findViewById(R.id.txtHepatitisDate);
        txtFlueDate = rootview.findViewById(R.id.txtFlueDate);
        txtFlueNHDate = rootview.findViewById(R.id.txtFlueNHDate);
        txtPneumococcalDate = rootview.findViewById(R.id.txtPneumococcalDate);


        imgAddPneumonia = rootview.findViewById(R.id.imgAddPneumonia);
        imgAddFlueShot = rootview.findViewById(R.id.imgAddFlueShot);
        imgAddHPV = rootview.findViewById(R.id.imgAddHPV);
        imgAddRubella = rootview.findViewById(R.id.imgAddRubella);
        imgAddVaricella = rootview.findViewById(R.id.imgAddVaricella);
        imgAddShingles = rootview.findViewById(R.id.imgAddShingles);
        imgAddTetanus = rootview.findViewById(R.id.imgAddTetanus);
        imgAddHepatitis = rootview.findViewById(R.id.imgAddHepatitis);
        imgAddFlue = rootview.findViewById(R.id.imgAddFlue);
        imgAddFlueNH = rootview.findViewById(R.id.imgAddFlueNH);
        imgAddPneumococcal = rootview.findViewById(R.id.imgAddPneumococcal);

        imgBack = getActivity().findViewById(R.id.imgBack);
        txtSave = getActivity().findViewById(R.id.txtSave);
        imgDone = getActivity().findViewById(R.id.imgDone);
        txtSave.setVisibility(View.VISIBLE);
        //imgDone.setVisibility(View.VISIBLE);
        imgRight = getActivity().findViewById(R.id.imgRight);
        etPreNote = rootview.findViewById(R.id.etNote);
        etAllergyNote = rootview.findViewById(R.id.etAllergyNote);
        etMouthNote = rootview.findViewById(R.id.etMouthNote);
        etVisionNote = rootview.findViewById(R.id.etVisionNote);
        etAideNote = rootview.findViewById(R.id.etAideNote);
        etFunctionalNote = rootview.findViewById(R.id.etFunctionalNote);
        etDietNote = rootview.findViewById(R.id.etDietNote);

        etVaccineNote = rootview.findViewById(R.id.etVaccineNote);
        etHistoryNote = rootview.findViewById(R.id.etHistoryNote);
        etBloodNote = rootview.findViewById(R.id.etBloodNote);
        etImplantNote = rootview.findViewById(R.id.etImplantNote);
        txtName = rootview.findViewById(R.id.txtName);
        //  txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));

        spinnerBlood = rootview.findViewById(R.id.spinnerBlood);
        spinnerEyes = rootview.findViewById(R.id.spinnerEyes);
        spinnerLang = rootview.findViewById(R.id.spinnerPrimary);
        imgAddAllergy = rootview.findViewById(R.id.imgAddAllergy);
        imgAddVaccine = rootview.findViewById(R.id.imgAddVaccine);
        imgAddImplants = rootview.findViewById(R.id.imgAddImplants);
        imgTeethDrop = rootview.findViewById(R.id.imgTeethDrop);
        imgAddCondition = rootview.findViewById(R.id.imgAddCondition);
        imgAddHospital = rootview.findViewById(R.id.imgAddHospital);
        imgAddHistory = rootview.findViewById(R.id.imgAddHistory);
        ListHistory = rootview.findViewById(R.id.ListHistory);
        ListAllergy = rootview.findViewById(R.id.ListAllergy);
        ListVaccine = rootview.findViewById(R.id.ListVaccine);
        ListImplants = rootview.findViewById(R.id.ListImplants);
        ListHospital = rootview.findViewById(R.id.ListHospital);
        ListCondition = rootview.findViewById(R.id.ListCondtion);
        imgDonnerDrop = rootview.findViewById(R.id.imgDonnerDrop);
        imgVisionDrop = rootview.findViewById(R.id.imgVisionDrop);
        imgAidsDrop = rootview.findViewById(R.id.imgAidsDrop);
        imgDietDrop = rootview.findViewById(R.id.imgDietDrop);
        imgVaccineDrop = rootview.findViewById(R.id.imgVaccineDrop);
        imgTobacoDrop = rootview.findViewById(R.id.imgTobacoDrop);
        imgDrugDrop = rootview.findViewById(R.id.imgDrugDrop);
        imgDrinkDrop = rootview.findViewById(R.id.imgDrinkDrop);

        etFt = rootview.findViewById(R.id.etFt);
        etInch = rootview.findViewById(R.id.etInch);
        etWeight = rootview.findViewById(R.id.etWeight);
        etAdditional = rootview.findViewById(R.id.etAdditional);
        etPet = rootview.findViewById(R.id.etPet);

        tbGlass = rootview.findViewById(R.id.tbGlass);
        tbMouth = rootview.findViewById(R.id.tbMouth);
        tbLense = rootview.findViewById(R.id.tbLense);
        tbFalse = rootview.findViewById(R.id.tbFalse);
        tbImplants = rootview.findViewById(R.id.tbImplants);
        tbHearingAid = rootview.findViewById(R.id.tbHearingAid);

        tbColor = rootview.findViewById(R.id.tbBlind);
        tbSpeech = rootview.findViewById(R.id.tbSpeech);
        tbFeed = rootview.findViewById(R.id.tbfeed);
        tbToilet = rootview.findViewById(R.id.tbToileting);
        tbMedicate = rootview.findViewById(R.id.tbMedicate);

        rbYes = rootview.findViewById(R.id.rbYes);
        rbNo = rootview.findViewById(R.id.rbNo);
        tbOrgan = rootview.findViewById(R.id.tbOrgan);

        txtTobacoType = rootview.findViewById(R.id.txtTobacoType);
        txtTobacoAmt = rootview.findViewById(R.id.txtTobacoAmt);
        txtTobacoYear = rootview.findViewById(R.id.txtTobacoYear);
        txtDrugType = rootview.findViewById(R.id.txtDrugType);
        txtDrinkAmt = rootview.findViewById(R.id.txtDrinkAmt);
        txtDrugAmt = rootview.findViewById(R.id.txtDrugAmt);
        txtDrugYear = rootview.findViewById(R.id.txtDrugYear);
        txtDrinkYear = rootview.findViewById(R.id.txtDrinkYear);

        rbDrugCurrent = rootview.findViewById(R.id.rbDrugCurrent);
        rbDrugPast = rootview.findViewById(R.id.rbDrugPast);
        rbDrugNever = rootview.findViewById(R.id.rbDrugNever);
        rbDrinkCurrent = rootview.findViewById(R.id.rbDrinkCurrent);
        rbDrinkPast = rootview.findViewById(R.id.rbDrinkPast);
        rbDrinkNever = rootview.findViewById(R.id.rbDrinkNever);
        rbTobacoCurrent = rootview.findViewById(R.id.rbTobacoCurrent);
        rbTobacoPast = rootview.findViewById(R.id.rbTobacoPast);
        rbTobacoNever = rootview.findViewById(R.id.rbTobacoNever);

        rgDrug = rootview.findViewById(R.id.rgDrug);
        rgDrink = rootview.findViewById(R.id.rgDrink);
        rgTobaco = rootview.findViewById(R.id.rgTobaco);

        rlDrugDesc = rootview.findViewById(R.id.rlDrugDesc);
        rlDrinkDesc = rootview.findViewById(R.id.rlDrinkDesc);
        rlTobacoDesc = rootview.findViewById(R.id.rlTobacoDesc);

        rlMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, EyesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEyes.setAdapter(adapter);
        spinnerEyes.setPrompt("Select Eyes Color");

        ArrayAdapter<String> langadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, LangList);
        langadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(langadapter);
        spinnerLang.setPrompt("Select Primary Language");

        //Commented for spinner blood-shradha
       /* ArrayAdapter<String> financeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, BloodList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBlood.setAdapter(financeadapter);
        spinnerBlood.setPrompt("Select Blood Type");*/


//        tbOrgan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                if (checkedId == R.id.rbYes) {
//                    donor = "YES";
//                } else if (checkedId == R.id.rbNo) {
//                    donor = "NO";
//                }
//            }
//        });
        txtBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RelationActivity.class);
                i.putExtra("Category", "Blood");
                i.putExtra("Selected", txtBlood.getText().toString());
                startActivityForResult(i, REQUEST_BLOOD);
            }
        });
        tbOrgan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    donor = "YES";
                } else {
                    donor = "NO";
                }
            }

        });

        rgDrug.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbDrugCurrent) {
                    drug = "Current";
                    rlDrugDesc.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbDrugPast) {
                    drug = "Past";
                    rlDrugDesc.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbDrugNever) {
                    drug = "Never";
                    drug_type = "";
                    drug_amt = "";
                    drug_year = "";
                    txtDrugType.setText(drug_type);
                    txtDrugAmt.setText(drug_amt);
                    txtDrugYear.setText(drug_year);
                    rlDrugDesc.setVisibility(View.GONE);
                }
            }
        });

        rgDrink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbDrinkCurrent) {
                    drink = "Current";
                    rlDrinkDesc.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbDrinkPast) {
                    drink = "Past";
                    rlDrinkDesc.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbDrinkNever) {
                    drink = "Never";
                    drink_amt = "";
                    drink_year = "";
                    txtDrinkAmt.setText(drink_amt);
                    txtDrinkYear.setText(drink_year);
                    rlDrinkDesc.setVisibility(View.GONE);
                }
            }
        });

        rgTobaco.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbTobacoCurrent) {
                    tobaco = "Current";
                    rlTobacoDesc.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbTobacoPast) {
                    tobaco = "Past";
                    rlTobacoDesc.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbTobacoNever) {
                    tobaco = "Never";
                    t_amt = "";
                    t_type = "";
                    t_year = "";
                    txtTobacoAmt.setText(t_amt);
                    txtTobacoType.setText(t_type);
                    txtTobacoYear.setText(t_year);
                    rlTobacoDesc.setVisibility(View.GONE);
                }
            }
        });

        setMedInfo();
        setAllergyData();
        setImplantData();
        setConditionData();
        setVaccineData();
        setHistoryData();
        setHospitalData();

    }

    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    ArrayList<String> ConditionList = new ArrayList<String>();//shradha

    private void setConditionData() {
        ConditionList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (ConditionList.size() != 0) {
            //shradha
            MedAdapter md = new MedAdapter(getActivity(), ConditionList, "condition", FragmentMedicalInfo.this);
            ListCondition.setAdapter(md);
            ListCondition.setVisibility(View.VISIBLE);
        } else {
            ListCondition.setVisibility(View.GONE);
        }
    }

    public void conditionEditDelete(int position, int type) {//shradha
        if (type == 0) {
            String value = ConditionList.get(position);
            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
            allergyIntent.putExtra("IsAllergy", false);
            allergyIntent.putExtra("IsHistory", false);
            allergyIntent.putExtra("IsImplant", false);
            allergyIntent.putExtra("ADD", "ConditionUpdate");
            allergyIntent.putExtra("Title", "Update Medical Condition");
            allergyIntent.putExtra("Name", "Add Medical Condition");
            allergyIntent.putExtra("ConditionObject", value);
            allergyIntent.putExtra("ID", value);
            startActivityForResult(allergyIntent, REQUEST_CONDITION);
        } else {
            boolean flag = MedicalConditionQuery.deleteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), ConditionList.get(position));
            if (flag == true) {
                Toast.makeText(getActivity(), "Medical condition has been deleted succesfully", Toast.LENGTH_SHORT).show();
                setConditionData();
                ListCondition.requestFocus();
            }
        }
    }

    private void setMedInfo() {
        medInfo = MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (medInfo != null) {

            etPreNote.setText(medInfo.getNote());
            etAllergyNote.setText(medInfo.getAllergyNote());
            etMouthNote.setText(medInfo.getMouthnote());
            etDietNote.setText(medInfo.getDietNote());
            etVisionNote.setText(medInfo.getVisionNote());
            etAideNote.setText(medInfo.getAideNote());

            etVaccineNote.setText(medInfo.getVaccinenote());
            etImplantNote.setText(medInfo.getImplantnote());
            etHistoryNote.setText(medInfo.getHistorynote());
            //  etBloodNote.setText(medInfo.getBloodnote());

            txtTobacoAmt.setText(medInfo.getT_amt());
            txtTobacoType.setText(medInfo.getT_type());
            txtTobacoYear.setText(medInfo.getT_year());

            txtDrinkAmt.setText(medInfo.getDrink_amt());
            txtDrinkYear.setText(medInfo.getDrink_year());

            txtDrugType.setText(medInfo.getDrug_type());
            txtDrugAmt.setText(medInfo.getDrug_amt());
            txtDrugYear.setText(medInfo.getDrug_year());

            txtBlood.setText(medInfo.getBloodType());
            //Commented for spinner blood-shradha
            /*int indexi = 0;
            for (int i = 0; i < BloodList.length; i++) {
                if (medInfo.getBloodType().equals(BloodList[i])) {
                    indexi = i;
                }
            }
            spinnerBlood.setSelection(indexi);*/


            if (medInfo.getSpeech().equals("YES")) {
                tbSpeech.setChecked(true);
                speech = "YES";
            } else if (medInfo.getSpeech().equals("NO")||medInfo.getSpeech().equals("")) {
                tbSpeech.setChecked(false);
                speech = "NO";
            }
            if (medInfo.getBlind().equals("YES")) {
                tbColor.setChecked(true);
                blind = "YES";
            } else if (medInfo.getBlind().equals("NO")||medInfo.getBlind().equals("")) {
                tbColor.setChecked(false);
                blind = "NO";
            }
            if (medInfo.getGlass().equals("YES")) {
                tbGlass.setChecked(true);
                glass = "YES";
            } else if (medInfo.getGlass().equals("NO")||medInfo.getGlass().equals("")) {
                tbGlass.setChecked(false);
                glass = "NO";
            }

            if (medInfo.getMouth().equals("YES")) {
                tbMouth.setChecked(true);
                mouth = "YES";
            } else if (medInfo.getMouth().equals("NO")||medInfo.getMouth().equals("")) {
                tbMouth.setChecked(false);
                mouth = "NO";
            }

            if (medInfo.getLense().equals("YES")) {
                tbLense.setChecked(true);
                lense = "YES";
            } else if (medInfo.getLense().equals("NO")||medInfo.getLense().equals("")) {
                tbLense.setChecked(false);
                lense = "NO";
            }

            if (medInfo.getFalses().equals("YES")) {
                tbFalse.setChecked(true);
                falses = "YES";
            } else if (medInfo.getFalses().equals("NO")||medInfo.getFalses().equals("")) {
                tbFalse.setChecked(false);
                falses = "NO";
            }

            if (medInfo.getImplants().equals("YES")) {
                tbImplants.setChecked(true);
                implants = "YES";
            } else if (medInfo.getImplants().equals("NO")||medInfo.getImplants().equals("")) {
                tbImplants.setChecked(false);
                implants = "NO";
            }

            if (medInfo.getAid().equals("YES")) {
                tbHearingAid.setChecked(true);
                aid = "YES";
            } else if (medInfo.getAid().equals("NO")||medInfo.getAid().equals("")) {
                tbHearingAid.setChecked(false);
                aid = "NO";
            }

            if (medInfo.getDonor().equals("YES")) {
                tbOrgan.setChecked(true);
                donor = "YES";
            } else if (medInfo.getDonor().equals("NO")||medInfo.getDonor().equals("")) {
                tbOrgan.setChecked(false);
                donor = "NO";
            }


            if (medInfo.getTobaco().equals("Current")) {
                rbTobacoCurrent.setChecked(true);
                tobaco = "Current";
                rlTobacoDesc.setVisibility(View.VISIBLE);

            } else if (medInfo.getTobaco().equals("Past")) {
                rbTobacoPast.setChecked(true);
                tobaco = "Past";
                rlTobacoDesc.setVisibility(View.VISIBLE);
            } else if (medInfo.getTobaco().equals("Never")||medInfo.getTobaco().equals("")) {
                rbTobacoNever.setChecked(true);
                tobaco = "Never";
                rlTobacoDesc.setVisibility(View.GONE);
            }

            if (medInfo.getDrink().equals("Current")) {
                rbDrinkCurrent.setChecked(true);
                drink = "Current";
                rlDrinkDesc.setVisibility(View.VISIBLE);
            } else if (medInfo.getDrink().equals("Past")) {
                rbDrinkPast.setChecked(true);
                drink = "Past";
                rlDrinkDesc.setVisibility(View.VISIBLE);
            } else if (medInfo.getDrink().equals("Never")||medInfo.getDrink().equals("")) {
                rbDrinkNever.setChecked(true);
                drink = "Never";
                rlDrinkDesc.setVisibility(View.GONE);
            }

            if (medInfo.getDrug().equals("Current")) {
                rbDrugCurrent.setChecked(true);
                drug = "Current";
                rlDrugDesc.setVisibility(View.VISIBLE);
            } else if (medInfo.getDrug().equals("Past")) {
                rbDrugPast.setChecked(true);
                drug = "Past";
                rlDrugDesc.setVisibility(View.VISIBLE);
            } else if (medInfo.getDrug().equals("Never")||medInfo.getDrug().equals("")) {
                rbDrugNever.setChecked(true);
                drug = "Never";
                rlDrugDesc.setVisibility(View.GONE);
            }
        }
    }

    ArrayList<Implant> ImplantsLists = new ArrayList<Implant>();//shradha

    private void setImplantData() {
        final ArrayList allergyList = new ArrayList();
        ImplantsLists = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (ImplantsLists.size() != 0) {
            if (flagImplants) {
                llSubImplants.setVisibility(View.VISIBLE);
            }
            ListImplants.setVisibility(View.VISIBLE);
            for (int i = 0; i < ImplantsLists.size(); i++) {
                Implant a = ImplantsLists.get(i);
                String allergy = "";
                if (a.getName().equals("Other")) {
                    allergy = "Medical Implant : ]" + a.getName() + " - " + a.getOther() + "]" + "Date : ]" + a.getDate() + "]" + "Location : ]" + a.getLocation() + "]" + "Details : ]" + a.getDetails() + "]" + "Note : ]" + a.getNotes();
                } else if (a.getName().equals("Joint Replacements (specify)")) {
                    allergy = "Medical Implant : ]" + a.getName() + " - " + a.getOther() + "]" + "Date : ]" + a.getDate() + "]" + "Location : ]" + a.getLocation() + "]" + "Details : ]" + a.getDetails() + "]" + "Note : ]" + a.getNotes();
                } else {
                    allergy = "Medical Implant : ]" + a.getName() + "]" + "Date : ]" + a.getDate() + "]" + "Location : ]" + a.getLocation() + "]" + "Details : ]" + a.getDetails() + "]" + "Note : ]" + a.getNotes();
                }
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                //shradha
                MedAdapter md = new MedAdapter(getActivity(), allergyList, "implants", FragmentMedicalInfo.this);
                ListImplants.setAdapter(md);
                ListImplants.setVisibility(View.VISIBLE);
            }
        } else {
            llSubImplants.setVisibility(View.GONE);
            ListImplants.setVisibility(View.GONE);
        }
    }

    public void implantsEditDelete(int position, int type) {  //shradha
        if (type == 0) {
            Implant a = ImplantsLists.get(position);
            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
            allergyIntent.putExtra("IsAllergy", false);
            allergyIntent.putExtra("IsHistory", false);
            allergyIntent.putExtra("IsImplant", false);
            allergyIntent.putExtra("IsImplant2", true);
            allergyIntent.putExtra("ADD", "ImplantUpdate");
            allergyIntent.putExtra("Title", "Update Medical Implant");
            allergyIntent.putExtra("Name", "Update Medical Implant");
            allergyIntent.putExtra("ImplantObject", a);
            allergyIntent.putExtra("ID", a.getId() + "");
            startActivityForResult(allergyIntent, REQUEST_IMPLANTS);
        } else {
            Implant a = ImplantsLists.get(position);
            boolean flag = MedicalImplantsQuery.deleteRecord(a.getId());
            if (flag == true) {
                Toast.makeText(getActivity(), "Implants has been deleted succesfully", Toast.LENGTH_SHORT).show();
                setImplantData();
                ListImplants.requestFocus();
            }
        }
    }

    ArrayList<History> HistoryLists = new ArrayList<History>();//shradha

    private void setHistoryData() {
        final ArrayList allergyList = new ArrayList();
        HistoryLists = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (HistoryLists.size() != 0) {
            if (flagHistory) {
                llSubHistory.setVisibility(View.VISIBLE);
            }
            ListHistory.setVisibility(View.VISIBLE);
            for (int i = 0; i < HistoryLists.size(); i++) {
                History a = HistoryLists.get(i);
                String allergy = "";
                if (a.getName().equals("Other")) {
                    allergy = "Surgical History : ]" + a.getName() + " - " + a.getOther() + "]" + "Date : ]" + a.getDate() + "]" + "Doctor : ]" + a.getDoctor() + "]" + "Location : ]" + a.getDone();
                } else {
                    allergy = "Surgical History : ]" + a.getName() + "]" + "Date : ]" + a.getDate() + "]" + "Doctor : ]" + a.getDoctor() + "]" + "Location : ]" + a.getDone();
                }
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                //shradha
                MedAdapter md = new MedAdapter(getActivity(), allergyList, "history", FragmentMedicalInfo.this);
                ListHistory.setAdapter(md);
                ListHistory.setVisibility(View.VISIBLE);
            }
        } else {
            ListHistory.setVisibility(View.GONE);
            llSubHistory.setVisibility(View.GONE);
        }
    }

    public void historyEditDelete(int position, int type) {  //shradha
        if (type == 0) {
            History value = HistoryLists.get(position);
            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
            allergyIntent.putExtra("IsAllergy", false);
            allergyIntent.putExtra("IsHistory", true);
            allergyIntent.putExtra("IsImplant", false);
            allergyIntent.putExtra("ADD", "HistoryUpdate");
            allergyIntent.putExtra("Title", "Update Surgical/Hospitalization History");
            allergyIntent.putExtra("Name", "Add Surgical/Hospitalization History");
            allergyIntent.putExtra("HistoryObject", value);
            allergyIntent.putExtra("ID", HistoryLists.get(position).getId() + "");
            startActivityForResult(allergyIntent, REQUEST_HISTORY);
        } else {
            boolean flag = HistoryQuery.deleteRecord(HistoryLists.get(position).getId());
            if (flag == true) {
                Toast.makeText(getActivity(), "History has been deleted succesfully", Toast.LENGTH_SHORT).show();
                setHistoryData();
                ListHistory.requestFocus();
            }
        }
    }

    ArrayList<String> HospitalList = new ArrayList<String>();//shradha

    private void setHospitalData() {
        HospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (HospitalList.size() != 0) {
            if (flagHospital) {
                llSubHospital.setVisibility(View.VISIBLE);
            }
            MedAdapter md = new MedAdapter(getActivity(), HospitalList, "hospital", FragmentMedicalInfo.this);
            ListHospital.setAdapter(md);
            ListHospital.setVisibility(View.VISIBLE);
        } else {
            ListHospital.setVisibility(View.GONE);
            llSubHospital.setVisibility(View.GONE);
        }
    }

    public void hospitalEditDelete(int position, int type) {  //shradha
        if (type == 0) {
            String value = HospitalList.get(position);
            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
            allergyIntent.putExtra("IsAllergy", false);
            allergyIntent.putExtra("IsHistory", false);
            allergyIntent.putExtra("IsImplant", false);
            allergyIntent.putExtra("ADD", "HospitalUpdate");
            allergyIntent.putExtra("Title", "Update Hospital Preference");
            allergyIntent.putExtra("Name", "Hospital Preference");
            allergyIntent.putExtra("HospitalObject", value);
            allergyIntent.putExtra("ID", HospitalList.get(position));
            startActivityForResult(allergyIntent, REQUEST_HOSPITAL);
        } else {
            boolean flag = HospitalQuery.deleteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), HospitalList.get(position));
            if (flag == true) {
                Toast.makeText(getActivity(), "Hospital has been deleted succesfully", Toast.LENGTH_SHORT).show();
                setHospitalData();
                ListHospital.requestFocus();
            }
        }
    }

    ArrayList<Vaccine> VaccineLists = new ArrayList<Vaccine>();//shradha

    private void setVaccineData() {
        final ArrayList allergyList = new ArrayList();
        VaccineLists = VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (VaccineLists.size() != 0) {
            if (flagVaccine) {
                linVaccine.setVisibility(View.VISIBLE);
            }
            ListVaccine.setVisibility(View.VISIBLE);
            for (int i = 0; i < VaccineLists.size(); i++) {
                Vaccine a = VaccineLists.get(i);
                String allergy = "";
                if (a.getName().equals("Other")) {
                    allergy = "Vaccine : ]" + a.getName() + " - " + a.getOther() + "]Date : ]" + a.getDate();
                } else {
                    allergy = "Vaccine : ]" + a.getName() + "]Date : ]" + a.getDate();
                }
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                //shradha
                MedAdapter md = new MedAdapter(getActivity(), allergyList, "vaccine", FragmentMedicalInfo.this);
                ListVaccine.setAdapter(md);
                ListVaccine.setVisibility(View.VISIBLE);
            }
        } else {
            linVaccine.setVisibility(View.GONE);
            ListVaccine.setVisibility(View.GONE);
        }
    }

    public void vaccineEditDelete(int position, int type) {  //shradha
        if (type == 0) {
            Vaccine a = VaccineLists.get(position);
            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
            allergyIntent.putExtra("IsAllergy", false);
            allergyIntent.putExtra("IsHistory", false);
            allergyIntent.putExtra("IsImplant", true);
            allergyIntent.putExtra("ADD", "VaccineUpdate");
            allergyIntent.putExtra("Title", "Update Immunization/Vaccine");
            allergyIntent.putExtra("Name", "Immunization/Vaccine");
            allergyIntent.putExtra("VaccineObject", a);
            allergyIntent.putExtra("ID", a.getId() + "");
            startActivityForResult(allergyIntent, REQUEST_VACCINE);
        } else {
            Vaccine a = VaccineLists.get(position);
            boolean flag = VaccineQuery.deleteRecord(a.getId());
            if (flag == true) {
                Toast.makeText(getActivity(), "Vaccine has been deleted succesfully", Toast.LENGTH_SHORT).show();
                setVaccineData();
                ListVaccine.requestFocus();
            }
        }
    }

    ArrayList<Allergy> AllargyLists = new ArrayList<Allergy>();//shradha

    private void setAllergyData() {
        final ArrayList allergyList = new ArrayList();
        AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (AllargyLists.size() != 0) {
            ListAllergy.setVisibility(View.VISIBLE);
            for (int i = 0; i < AllargyLists.size(); i++) {
                Allergy a = AllargyLists.get(i);
                String allergy = "";
                if (a.getReaction().equals("Other")) {
                    allergy = "Allergy :       ]" + a.getAllergy() + "]" + "Reaction :    ]" + a.getReaction() + " - " + a.getOtherReaction() + "]" + "Treatment : ]" + a.getTreatment();
                } else {
                    allergy = "Allergy :       ]" + a.getAllergy() + "]" + "Reaction :    ]" + a.getReaction() + "]" + "Treatment : ]" + a.getTreatment();
                }
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                //shradha
                MedAdapter md = new MedAdapter(getActivity(), allergyList, "allergy", FragmentMedicalInfo.this);
                ListAllergy.setAdapter(md);
                ListAllergy.setVisibility(View.VISIBLE);
            }
        } else {
            ListAllergy.setVisibility(View.GONE);
        }
    }

    public void allergyEditDelete(int position, int type) {  //shradha
        if (type == 0) {
            Allergy a = AllargyLists.get(position);
            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
            allergyIntent.putExtra("IsAllergy", true);
            allergyIntent.putExtra("IsHistory", false);
            allergyIntent.putExtra("IsImplant", false);
            allergyIntent.putExtra("ADD", "AllergyUpdate");
            allergyIntent.putExtra("Title", "Update Allergy and Medication Reaction");
            allergyIntent.putExtra("Name", "Allergy (Drug, Food, Environmental)");
            allergyIntent.putExtra("AllergyObject", a);
            allergyIntent.putExtra("ID", a.getId() + "");
            startActivityForResult(allergyIntent, REQUEST_ALLERGY);
        } else {
            Allergy a = AllargyLists.get(position);
            boolean flag = AllergyQuery.deleteRecord(a.getId());
            if (flag == true) {
                Toast.makeText(getActivity(), "Allergy has been deleted succesfully", Toast.LENGTH_SHORT).show();
                setAllergyData();
                ListAllergy.requestFocus();
            }
        }
    }

    private void showFloatDialog() {

        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "MedicalProfile.pdf");
        if (file.exists()) {
            file.delete();
        }

//old code
//        new Header().createPdfHeader(file.getAbsolutePath(),
//                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//        preferences.copyFile("ic_launcher.png", getActivity());
//        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
//        Header.addEmptyLine(1);
//        Header.addusereNameChank("Medical Profile");//preferences.getString(PrefConstants.CONNECTED_NAME));
//        Header.addEmptyLine(1);
//
//        Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
//
//        Paragraph p = new Paragraph(" ");
//        LineSeparator line = new LineSeparator();
//        line.setOffset(-4);
//        line.setLineColor(BaseColor.LIGHT_GRAY);
//        p.add(line);
//        try {
//            Header.document.add(p);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Header.addEmptyLine(1);
//              /*  new Header().createPdfHeader(file.getAbsolutePath(),
//                        "Medical Profile");
//                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(2);*/
//
//        final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//        final ArrayList<Implant> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//        final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//        final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//        final ArrayList<String> conditionList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//        final ArrayList<Vaccine> vaccineList = VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//
//
//        new Individual(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)), AllargyLists, implantsList, historList, hospitalList, conditionList, vaccineList);
//
//        Header.document.close();

        // New pdf
        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png", getActivity());
        calendar=preferences.addFile("calpdf.png", getActivity());
        calendarWite=preferences.addFile("calpdf_wite.png", getActivity());
        profile=preferences.addFile("profpdf.png", getActivity());
        profileWite=preferences.addFile("profpdf_wite.png", getActivity());
        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "MEDICAL PROFILE", calendarWite, profileWite);

        HeaderNew.addusereNameChank("MEDICAL PROFILE");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("emergency_two.png", getActivity());

        final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        final ArrayList<Implant> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        final ArrayList<String> conditionList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        final ArrayList<Vaccine> vaccineList = VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));


        new IndividualNew(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)), AllargyLists, implantsList, historList, hospitalList, conditionList, vaccineList, pp);

        HeaderNew.document.close();
//--------------------------------------------------------------------------------------
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        floatContact.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        floatNew.setImageResource(R.drawable.closee);

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

        floatNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/MedicalProfile.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Medical Profile");
                dialog.dismiss();
            }

        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/MedicalProfile.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getMedicalInfo());
                new PDFDocumentProcess(path,
                        getActivity(), result);

                System.out.println("\n" + result + "\n");
                dialog.dismiss();
            }


        });

        RelativeLayout rlFloatfax = dialogview.findViewById(R.id.rlFloatfax);
        rlFloatfax.setVisibility(View.VISIBLE);

        rlFloatfax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/MedicalProfile.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getMedicalInfo());
                new PDFDocumentProcess(path,
                        getActivity(), result);
                Intent i = new Intent(getActivity(), FaxActivity.class);
                i.putExtra("PATH", path);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //  String ft,inch,weight,color,lang1,lang2,blood,pet;

            case R.id.imgAddPneumonia:
                getDate(getActivity(), imgAddPneumonia);
                break;

            case R.id.imgAddFlueShot:
                getDate(getActivity(), imgAddFlueShot);
                break;

            case R.id.imgAddHPV:
                getDate(getActivity(), imgAddHPV);
                break;

            case R.id.imgAddRubella:
                getDate(getActivity(), imgAddRubella);
                break;

            case R.id.imgAddVaricella:
                getDate(getActivity(), imgAddVaricella);
                break;

            case R.id.imgAddShingles:
                getDate(getActivity(), imgAddShingles);
                break;

            case R.id.imgAddTetanus:
                getDate(getActivity(), imgAddTetanus);
                break;

            case R.id.imgAddHepatitis:
                getDate(getActivity(), imgAddHepatitis);
                break;

            case R.id.imgAddFlue:
                getDate(getActivity(), imgAddFlue);
                break;

            case R.id.imgAddFlueNH:
                getDate(getActivity(), imgAddFlueNH);
                break;

            case R.id.imgAddPneumococcal:
                getDate(getActivity(), imgAddPneumococcal);
                break;

            case R.id.imgRight:
                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "Medical");
                startActivity(i);
              /*  final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "MedicalProfile.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png", getActivity());
                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Medical Profile");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
              *//*  new Header().createPdfHeader(file.getAbsolutePath(),
                        "Medical Profile");
                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*//*

                final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                final ArrayList<Implant> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                final ArrayList<String> conditionList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                final ArrayList<Vaccine> vaccineList = VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));


                new Individual(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)), AllargyLists, implantsList, historList, hospitalList, conditionList, vaccineList);

                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/MedicalProfile.pdf";
                        switch (itemPos) {
                            case 0: //View
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getMedicalInfo());
                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f = new File(path);
                                preferences.emailAttachement(f, getActivity(), "Medical Profile");
                                break;
                           *//* case 2://fax
                                new FaxCustomDialog(getActivity(), path).show();
                                break;*//*

                            case 2://fax
                                Intent i = new Intent(getActivity(), InstructionActivity.class);
                                i.putExtra("From", "Medical");
                                startActivity(i);
                                break;

                        }
                    }

                });
                builder.create().show();*/
                break;

            case R.id.imgVisionDrop:
                if (flagVission == false) {
                    llVision.setVisibility(View.VISIBLE);
                    imgVisionDrop.setImageResource(R.drawable.dropup);
                    flagVission = true;
                } else if (flagVission == true) {
                    llVision.setVisibility(View.GONE);
                    imgVisionDrop.setImageResource(R.drawable.drop_down);
                    flagVission = false;
                }
                break;

            case R.id.imgDonnerDrop:
                if (flagOrgan == false) {
                    linorgan.setVisibility(View.VISIBLE);
                    imgDonnerDrop.setImageResource(R.drawable.dropup);
                    flagOrgan = true;
                } else if (flagOrgan == true) {
                    linorgan.setVisibility(View.GONE);
                    imgDonnerDrop.setImageResource(R.drawable.drop_down);
                    flagOrgan = false;
                }
                break;

            case R.id.imgAidsDrop:
                if (flagAids == false) {
                    llAidsDiet.setVisibility(View.VISIBLE);
                    imgAidsDrop.setImageResource(R.drawable.dropup);
                    flagAids = true;
                } else if (flagAids == true) {
                    llAidsDiet.setVisibility(View.GONE);
                    imgAidsDrop.setImageResource(R.drawable.drop_down);
                    flagAids = false;
                }
                break;
            case R.id.imgDietDrop:
                if (flagDiet == false) {
                    llSubDiet.setVisibility(View.VISIBLE);
                    imgDietDrop.setImageResource(R.drawable.dropup);
                    flagDiet = true;
                } else if (flagDiet == true) {
                    llSubDiet.setVisibility(View.GONE);
                    imgDietDrop.setImageResource(R.drawable.drop_down);
                    flagDiet = false;
                }
                break;
            case R.id.imgVaccineDrop:
                if (flagVaccine == false) {
                     /*if(!VaccineLists.isEmpty()){
                        linVaccine.setVisibility(View.VISIBLE);
                    }else{
                      linVaccine.setVisibility(View.GONE);
                    } */
                    linVaccine.setVisibility(View.VISIBLE);
                    txtAddVaccine.setVisibility(View.VISIBLE);
                    imgVaccineDrop.setImageResource(R.drawable.dropup);
                    flagVaccine = true;
                } else if (flagVaccine == true) {
                    txtAddVaccine.setVisibility(View.GONE);
                    linVaccine.setVisibility(View.GONE);
                    imgVaccineDrop.setImageResource(R.drawable.drop_down);
                    flagVaccine = false;
                }
                break;
            case R.id.imgTobacoDrop:
                if (flagTobaco == false) {
                    lintobaco.setVisibility(View.VISIBLE);
                    imgTobacoDrop.setImageResource(R.drawable.dropup);
                    flagTobaco = true;
                } else if (flagTobaco == true) {
                    lintobaco.setVisibility(View.GONE);
                    imgTobacoDrop.setImageResource(R.drawable.drop_down);
                    flagTobaco = false;
                }
                break;
            case R.id.imgDrugDrop:
                if (flagDrug == false) {
                    lindrug.setVisibility(View.VISIBLE);
                    imgDrugDrop.setImageResource(R.drawable.dropup);
                    flagDrug = true;
                } else if (flagDrug == true) {
                    lindrug.setVisibility(View.GONE);
                    imgDrugDrop.setImageResource(R.drawable.drop_down);
                    flagDrug = false;
                }
                break;
            case R.id.imgDrinkDrop:
                if (flagDrink == false) {
                    lindrink.setVisibility(View.VISIBLE);
                    imgDrinkDrop.setImageResource(R.drawable.dropup);
                    flagDrink = true;
                } else if (flagDrink == true) {
                    lindrink.setVisibility(View.GONE);
                    imgDrinkDrop.setImageResource(R.drawable.drop_down);
                    flagDrink = false;
                }
                break;


            case R.id.imgAddAllergy:
                if (flagAllergy == false) {
                    llSubAllergis.setVisibility(View.VISIBLE);
                    imgAddAllergy.setImageResource(R.drawable.dropup);
                    txtAddAllergy.setVisibility(View.VISIBLE);
//                    viewAllergyBottom.setVisibility(View.GONE);
                    flagAllergy = true;
                } else if (flagAllergy == true) {
                    llSubAllergis.setVisibility(View.GONE);
                    imgAddAllergy.setImageResource(R.drawable.drop_down);
                    txtAddAllergy.setVisibility(View.GONE);
//                    viewAllergyBottom.setVisibility(View.VISIBLE);
                    flagAllergy = false;
                }

                break;

            case R.id.headAllergy:
                imgAddAllergy.performClick();
                break;
            case R.id.headPre:
                imgAddCondition.performClick();
                break;
            case R.id.headImplants:
                imgAddImplants.performClick();
                break;
            case R.id.headHistory:
                imgAddHistory.performClick();
                break;
            case R.id.headHospital:
                imgAddHospital.performClick();
                break;
            case R.id.headBlood:
                imgBloodDrop.performClick();
                break;
            case R.id.headTeeath:
                imgTeethDrop.performClick();
                break;

            case R.id.headDiet:
                imgDietDrop.performClick();
                break;
            case R.id.headOrgan:
                imgDonnerDrop.performClick();
                break;
            case R.id.headTobaco:
                imgTobacoDrop.performClick();
                break;
            case R.id.headDrug:
                imgDrugDrop.performClick();
                break;
            case R.id.headDrink:
                imgDrinkDrop.performClick();
                break;
            case R.id.headVision:
                imgVisionDrop.performClick();
                break;
            case R.id.headAids:
                imgAidsDrop.performClick();
                break;
            case R.id.headVaccine:
                imgVaccineDrop.performClick();
                break;

            case R.id.imgAddCondition:
                if (flagPre == false) {
                    llSubPre.setVisibility(View.VISIBLE);
                    imgAddCondition.setImageResource(R.drawable.dropup);
                    txtAddCondition.setVisibility(View.VISIBLE);
//                    viewPreBottom.setVisibility(View.GONE);
                    flagPre = true;
                } else if (flagPre == true) {
                    llSubPre.setVisibility(View.GONE);
                    imgAddCondition.setImageResource(R.drawable.drop_down);
                    txtAddCondition.setVisibility(View.GONE);
//                    viewPreBottom.setVisibility(View.VISIBLE);
                    flagPre = false;
                }
                break;

            case R.id.imgAddImplants:
                if (flagImplants == false) {
                   /* if(!ImplantsLists.isEmpty()){
                        llSubImplants.setVisibility(View.VISIBLE);
                    }else{
                        llSubImplants.setVisibility(View.GONE);
                    }*/
                    llSubImplants.setVisibility(View.VISIBLE);
                    imgAddImplants.setImageResource(R.drawable.dropup);
                    txtAddImplants.setVisibility(View.VISIBLE);
//                    viewImplantsBottom.setVisibility(View.GONE);
                    flagImplants = true;
                } else if (flagImplants == true) {
                    llSubImplants.setVisibility(View.GONE);
                    imgAddImplants.setImageResource(R.drawable.drop_down);
                    txtAddImplants.setVisibility(View.GONE);
//                    viewImplantsBottom.setVisibility(View.VISIBLE);
                    flagImplants = false;
                }
                break;

            case R.id.imgAddHistory:
                if (flagHistory == false) {
                   /* if(!HistoryLists.isEmpty()){
                        llSubHistory.setVisibility(View.VISIBLE);
                    }else{
                        llSubHistory.setVisibility(View.GONE);
                    }*/
                    llSubHistory.setVisibility(View.VISIBLE);
                    imgAddHistory.setImageResource(R.drawable.dropup);
                    txtAddHistory.setVisibility(View.VISIBLE);
//                    viewHistoryBottom.setVisibility(View.GONE);
                    flagHistory = true;
                } else if (flagHistory == true) {
                    llSubHistory.setVisibility(View.GONE);
                    imgAddHistory.setImageResource(R.drawable.drop_down);
                    txtAddHistory.setVisibility(View.GONE);
//                    viewHistoryBottom.setVisibility(View.VISIBLE);
                    flagHistory = false;
                }
                break;
            case R.id.imgAddHospital:
                if (flagHospital == false) {
                    if (!HospitalList.isEmpty()) {
                        llSubHospital.setVisibility(View.VISIBLE);
                    } else {
                        llSubHospital.setVisibility(View.GONE);
                    }
                    imgAddHospital.setImageResource(R.drawable.dropup);
                    txtAddHospital.setVisibility(View.VISIBLE);
//                    viewHospitalBottom.setVisibility(View.GONE);
                    flagHospital = true;
                } else if (flagHospital == true) {
                    llSubHospital.setVisibility(View.GONE);
                    imgAddHospital.setImageResource(R.drawable.drop_down);
                    txtAddHospital.setVisibility(View.GONE);
//                    viewHospitalBottom.setVisibility(View.VISIBLE);
                    flagHospital = false;
                }
                break;
            case R.id.imgBloodDrop:
                if (flagBlood == false) {
                    llSubBlood.setVisibility(View.VISIBLE);
                    imgBloodDrop.setImageResource(R.drawable.dropup);
                    //  viewBloodBottom.setVisibility(View.VISIBLE);
                    flagBlood = true;
                } else if (flagBlood == true) {
                    llSubBlood.setVisibility(View.GONE);
                    imgBloodDrop.setImageResource(R.drawable.drop_down);
                    //  viewBloodBottom.setVisibility(View.VISIBLE);
                    flagBlood = false;
                }
                break;

            case R.id.imgTeethDrop:
                if (flagTeeth == false) {
                    llSubTeeth.setVisibility(View.VISIBLE);
                    imgTeethDrop.setImageResource(R.drawable.dropup);
                    flagTeeth = true;
                } else if (flagTeeth == true) {
                    llSubTeeth.setVisibility(View.GONE);
                    imgTeethDrop.setImageResource(R.drawable.drop_down);
                    flagTeeth = false;
                }
                break;

            case R.id.txtBlood:
                Intent intentType = new Intent(getActivity(), RelationActivity.class);
                intentType.putExtra("Category", "Blood");
                startActivityForResult(intentType, RESULT_BLOOD);
                break;

            case R.id.txtSave:
                getValues();

                Boolean flag = MedInfoQuery.insertMedInfoData(preferences.getInt(PrefConstants.CONNECTED_USERID), blood, glass, lense, falses, implants, aid, donor, note, mouth, mouthnote, visionnote, Aidenote, dietnote, blind, speech, allergynote, tobaco, t_type, t_amt, t_year, drink, drink_amt, drug, drug_type, drug_amt, drug_year, drink_year, functionnote, historynote, vaccinenote, implantsnote);
                if (flag == true) {
                    Toast.makeText(getActivity(), "Medical Profile has been updated succesfully", Toast.LENGTH_SHORT).show();
                    medInfo = MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    hideSoftKeyboard();
                    // getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;

            case R.id.floatOptions:
                showFloatDialog();
                break;

            case R.id.imgBack:
                getValues();

                if ((medInfo.getGlass().equalsIgnoreCase(glass)||medInfo.getGlass().equals(""))&&
                        (medInfo.getLense().equalsIgnoreCase(lense)||medInfo.getLense().equals(""))&&
                        (medInfo.getFalses().equalsIgnoreCase(falses)||medInfo.getFalses().equals(""))&&
                        (medInfo.getImplants().equalsIgnoreCase(implants)||medInfo.getImplants().equals(""))&&
                        (medInfo.getAid().equalsIgnoreCase(aid)||medInfo.getAid().equals(""))&&
                        (medInfo.getDonor().equalsIgnoreCase(donor)||medInfo.getDonor().equals(""))&&
                        (medInfo.getMouth().equalsIgnoreCase(mouth)||medInfo.getMouth().equals(""))&&
                        (medInfo.getBlind().equalsIgnoreCase(blind)||medInfo.getBlind().equals(""))&&
                        (medInfo.getSpeech().equalsIgnoreCase(speech)||medInfo.getSpeech().equals(""))&&
                        (medInfo.getTobaco().equalsIgnoreCase(tobaco)||medInfo.getTobaco().equals(""))&&
                        (medInfo.getDrink().equalsIgnoreCase(drink)||medInfo.getDrink().equals(""))&&
                        (medInfo.getDrug().equalsIgnoreCase(drug)||medInfo.getDrug().equals(""))&&
                        medInfo.getT_amt().equals(t_amt)&&
                        medInfo.getT_type().equals(t_type)&&
                        medInfo.getT_year().equals(t_year)&&
                        medInfo.getDrink_amt().equals(drink_amt)&&
                        medInfo.getDrink_year().equals(drink_year)&&
                        medInfo.getDrug_type().equals(drug_type)&&
                        medInfo.getDrug_amt().equals(drug_amt) &&
                        medInfo.getDrug_year().equals(drug_year)&&
                        medInfo.getBloodType().equals(blood)&&
                        medInfo.getNote().equals(note)&&
                        medInfo.getAllergyNote().equals(allergynote)&&
                        medInfo.getMouthnote().equals(mouthnote)&&
                        medInfo.getVisionNote().equals(visionnote)&&
                        medInfo.getVaccinenote().equals(vaccinenote)&&
                        medInfo.getAideNote().equals(Aidenote)&&
                        medInfo.getFunctionnote().equals(functionnote)&&
                        medInfo.getDietNote().equals(dietnote)&&
                        medInfo.getImplantnote().equals(implantsnote)&&
                        medInfo.getHistorynote().equals(historynote)&&
                        medInfo.getAideNote().equals(Aidenote))
                {
                    hideSoftKeyboard();
                    getActivity().finish();
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Save");
                    alert.setMessage("Do you want to save information?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*if (!connection.getName().equals(name) || !connection.getEmail().equals(address))
                            {
                                isfinis=true;
                            }*/
                            hideSoftKeyboard();
                            boolean s=  txtSave.performClick();
                            // backflap=false;
                            dialog.dismiss();
                            getActivity().finish();
                            /*if (connection.getName().equals(name) || connection.getEmail().equals(address))
                            {
                                finish();
                            }*/

                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            hideSoftKeyboard();
                            getActivity().finish();
                        }
                    });
                    alert.show();
                }


                break;
            case R.id.txtAddAllergy:
                Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                allergyIntent.putExtra("IsAllergy", true);
                allergyIntent.putExtra("IsHistory", false);
                allergyIntent.putExtra("IsImplant", false);
                allergyIntent.putExtra("ADD", "Allergy");
                allergyIntent.putExtra("Title", "Add Allergy and Medication Reaction");
                allergyIntent.putExtra("Name", "Allergy (Drug, Food, Environmental) ");
                startActivityForResult(allergyIntent, REQUEST_ALLERGY);
                break;

            case R.id.txtAddVaccine:
                Intent vaccineIntent = new Intent(getActivity(), AddInfoActivity.class);
                vaccineIntent.putExtra("IsAllergy", false);
                vaccineIntent.putExtra("IsHistory", false);
                vaccineIntent.putExtra("IsImplant", true);
                vaccineIntent.putExtra("ADD", "Vaccine");
                vaccineIntent.putExtra("Title", "Add Immunization/Vaccine");
                vaccineIntent.putExtra("Name", "Immunization/Vaccine");
                startActivityForResult(vaccineIntent, REQUEST_VACCINE);
                break;

            case R.id.txtAddImplants:
                Intent implantsIntent = new Intent(getActivity(), AddInfoActivity.class);
                implantsIntent.putExtra("IsAllergy", false);
                implantsIntent.putExtra("IsHistory", false);
                implantsIntent.putExtra("IsImplant", false);
                implantsIntent.putExtra("IsImplant2", true);
                implantsIntent.putExtra("ADD", "Implants");
                implantsIntent.putExtra("Title", "Add Medical Implant");
                implantsIntent.putExtra("Name", "Add Medical Implant");
                startActivityForResult(implantsIntent, REQUEST_IMPLANTS);
                break;
            case R.id.txtAddCondition:
                Intent implantsIntents = new Intent(getActivity(), AddInfoActivity.class);
                implantsIntents.putExtra("IsAllergy", false);
                implantsIntents.putExtra("IsHistory", false);
                implantsIntents.putExtra("IsImplant", false);
                implantsIntents.putExtra("ADD", "Condition");
                implantsIntents.putExtra("Title", "Add Medical Condition");
                implantsIntents.putExtra("Name", "Medical Condition");
                startActivityForResult(implantsIntents, REQUEST_CONDITION);
                break;
            case R.id.txtAddHospital:
                Intent hospIntent = new Intent(getActivity(), AddInfoActivity.class);
                hospIntent.putExtra("IsAllergy", false);
                hospIntent.putExtra("IsHistory", false);
                hospIntent.putExtra("IsImplant", false);
                hospIntent.putExtra("ADD", "Hospital");
                hospIntent.putExtra("Title", "Add Hospital Preference");
                hospIntent.putExtra("Name", "Hospital Preference");
                startActivityForResult(hospIntent, REQUEST_HOSPITAL);
                break;
            case R.id.txtAddHistory:
                Intent historyIntent = new Intent(getActivity(), AddInfoActivity.class);
                historyIntent.putExtra("IsAllergy", false);
                historyIntent.putExtra("IsHistory", true);
                historyIntent.putExtra("IsImplant", false);
                historyIntent.putExtra("ADD", "History");
                historyIntent.putExtra("Title", "Add Surgical/Hospitalization History");
                historyIntent.putExtra("Name", "Add Surgical/Hospitalization History");
                startActivityForResult(historyIntent, REQUEST_HISTORY);
                break;
        }
    }

    private void getValues() {
        ft = etFt.getText().toString().trim();
        inch = etInch.getText().toString().trim();
        weight = etWeight.getText().toString().trim();
        color = spinnerEyes.getSelectedItem().toString();
        lang1 = spinnerLang.getSelectedItem().toString();
        lang2 = etAdditional.getText().toString();
        blood = txtBlood.getText().toString().trim();
        pet = etPet.getText().toString().trim();
        note = etPreNote.getText().toString().trim();
        allergynote = etAllergyNote.getText().toString().trim();
        mouthnote = etMouthNote.getText().toString().trim();

        visionnote = etVisionNote.getText().toString().trim();
        Aidenote = etAideNote.getText().toString().trim();
        functionnote = etFunctionalNote.getText().toString().trim();
        dietnote = etDietNote.getText().toString().trim();

        vaccinenote = etVaccineNote.getText().toString().trim();
        implantsnote = etImplantNote.getText().toString().trim();
        historynote = etHistoryNote.getText().toString().trim();
        //  bloodnote = etBloodNote.getText().toString().trim();

        t_type = txtTobacoType.getText().toString().trim();
        t_amt = txtTobacoAmt.getText().toString().trim();
        t_year = txtTobacoYear.getText().toString().trim();
        drink_amt = txtDrinkAmt.getText().toString().trim();
        drink_year = txtDrinkYear.getText().toString().trim();
        drug_type = txtDrugType.getText().toString().trim();
        drug_amt = txtDrugAmt.getText().toString().trim();
        drug_year = txtDrugYear.getText().toString().trim();

        /*if (tbSpeech.isChecked())
        {
            speech="Yes";
        }else{
            speech="No";
        }

        if (tbFalse.isChecked())
        {
            falses="Yes";
        }else{
            falses="No";
        }

        if (tbGlass.isChecked())
        {
            glass="Yes";
        }else{
            glass="No";
        }

        if (tbImplants.isChecked())
        {
            implants="Yes";
        }else{
            implants="No";
        }

        if (tbHearingAid.isChecked())
        {
            aid="Yes";
        }else{
            aid="No";
        }

        if (tbLense.isChecked())
        {
            lense="Yes";
        }else{
            lense="No";
        }

        if (tbOrgan.isChecked())
        {
            donor="Yes";
        }else{
            donor="No";
        }

        if (tbMouth.isChecked())
        {
            mouth="Yes";
        }else{
            mouth="No";
        }

        if (tbColor.isChecked())
        {
            blind="Yes";
        }else{
            blind="No";
        }
        if (rbTobacoNever.isChecked())
        {
            tobaco="Never";
        }
        if (rbDrinkNever.isChecked())
        {
            drink="Never";
        }
        if (rbDrugNever.isChecked())
        {
            drug="Never";
        }*/
    }

    private void getDate(Context context, final TextView txtview) {
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
                String reportDate = new SimpleDateFormat("d - MMM - yyyy").format(datePickerDate);

                DateClass d = new DateClass();
                d.setDate(reportDate);
                txtview.setText(reportDate);
                txtview.setVisibility(View.VISIBLE);
            }
        }, year, month, day);
        dpd.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ALLERGY == requestCode) {
           /* String value=data.getExtras().getString("Value");
            String reaction=data.getExtras().getString("Reaction");
            String treatment=data.getExtras().getString("Treatment");
            String allergy="Allergy: "+value+"\nReaction: "+reaction+"\nTreatment: "+treatment;
            allergyList.add(allergy);*/
            setAllergyData();
            ListAllergy.requestFocus();
        } else if (REQUEST_IMPLANTS == requestCode) {
            /*String value=data.getExtras().getString("Value");
            implantsList.add(value);*/
            setImplantData();
            ListImplants.requestFocus();
        } else if (REQUEST_CONDITION == requestCode) {
            /*String value=data.getExtras().getString("Value");
            implantsList.add(value);*/
            setConditionData();
            ListCondition.requestFocus();
        } else if (REQUEST_HOSPITAL == requestCode && data != null) {
           /* String value = data.getExtras().getString("Value");
            hospitalList.add(value);*/
            setHospitalData();
            ListHospital.requestFocus();
        } else if (REQUEST_HISTORY == requestCode && data != null) {
           /* String value = data.getExtras().getString("Value");
            historList.add(value);*/
            setHistoryData();
            ListHistory.requestFocus();
        } else if (REQUEST_VACCINE == requestCode && data != null) {
           /* String value = data.getExtras().getString("Value");
            historList.add(value);*/
            setVaccineData();
            ListVaccine.requestFocus();
        } else if (requestCode == RESULT_BLOOD && data != null) {
            String blood = data.getExtras().getString("Blood");
            txtBlood.setText(blood);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tbGlass:
                if (isChecked == true)
                    glass = "YES";
                else
                    glass = "NO";
                break;

            case R.id.tbBlind:
                if (isChecked == true)
                    blind = "YES";
                else
                    blind = "NO";
                break;

            case R.id.tbfeed:
                if (isChecked == true)
                    feed = "YES";
                else
                    feed = "NO";
                break;

            case R.id.tbToileting:
                if (isChecked == true)
                    toilet = "YES";
                else
                    toilet = "NO";
                break;
            case R.id.tbMedicate:
                if (isChecked == true)
                    medicate = "YES";
                else
                    medicate = "NO";
                break;
            case R.id.tbSpeech:
                if (isChecked == true)
                    speech = "YES";
                else
                    speech = "NO";
                break;

            case R.id.tbMouth:
                if (isChecked == true)
                    mouth = "YES";
                else
                    mouth = "NO";
                break;
            case R.id.tbLense:
                if (isChecked == true)
                    lense = "YES";
                else
                    lense = "NO";
                break;
            case R.id.tbFalse:
                if (isChecked == true)
                    falses = "YES";
                else
                    falses = "NO";
                break;
            case R.id.tbImplants:
                if (isChecked == true)
                    implants = "YES";
                else
                    implants = "NO";
                break;
            case R.id.tbHearingAid:
                if (isChecked == true)
                    aid = "YES";
                else
                    aid = "NO";
                break;
        }
    }


}
