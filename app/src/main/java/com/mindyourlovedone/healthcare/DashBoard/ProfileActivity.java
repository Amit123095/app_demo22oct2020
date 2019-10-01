package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.Connections.PetAdapter;
import com.mindyourlovedone.healthcare.Connections.PhoneAdapter;
import com.mindyourlovedone.healthcare.Connections.RelationActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.customview.NonScrollListView;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.FormQuery;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.PetQuery;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Pet;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.IndividualNew;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.NetworkUtils;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.webservice.WebService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/*shradha changes*/
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final int REQUEST_PET = 400;
    private static final int REQUEST_CARD = 50;
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    private static int RESULT_CAMERA_IMAGE_CARD = 3;
    private static int RESULT_SELECT_PHOTO_CARD = 4;
    PhoneAdapter pd;
    public static final int REQUEST_RELATIONP = 100;
    public static final int REQUEST_MARITAL = 22;
    public static final int REQUEST_EYES = 23;
    public static final int REQUEST_LANGUAGE= 24;
    public ArrayList<ContactData> phonelist=new ArrayList<>();
    public ArrayList<ContactData> Originalphonelist=new ArrayList<>();
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    Bitmap ProfileMap = null, CardMap = null;
    ContentValues values;
    boolean backflap;
    Uri imageUriProfile = null, imageUriCard = null;
    // byte[] photoCard=null;
    ImageView imgRight, imgInfo, imgR;
    RelativeLayout llIndividual;
    boolean isfinis;
    String has_card="NO";
    //  Button floatingBtn;
    TextView txtPeople,txtAddPet, txtSignUp, txtLogin, txtForgotPassword, txtOther, txtOtherLanguage, txtMsg, txtSave;
    ImageView imgHome,imgEdit, imgProfile, imgDone, imgAddpet, imgEditCard, imgCard;
    TextView txtHeight, txtWeight, txtProfession, txttelephone, txtEmployed, txtReligion, txtIdNumber, txtOtherRelation, txtTitle, txtName, txtEmail, txtAddress, txtCountry, txtPhone, txtHomePhone, txtWorkPhone, txtBdate, txtGender, txtPassword, txtRelation;
    TextInputLayout tilOtherRelation, tilId, tilOther, tilOtherLanguage;
    RelativeLayout rlPet, rlLive;
    String name = "", Email = "", email = "", phone = "", manager_phone = "", country = "", bdate = "", address = "", homePhone = "", workPhone = "", gender = "";
    String height = "", weight = "", profession = "", employed = "", religion = "", idnumber = "",people="";
    String pet = "", veteran = "", english = "", live = "";
    String eyes="", language="", marital_status="";
    String otherRelation;
    CheckBox chkOther, chkChild, chkFriend, chkGrandParent, chkParent, chkSpouse, chkSibling;
    String child = "", friend = "", grandParent = "", parent = "", spouse = "", other = "", sibling = "";
    String liveOther = "";
    ListView ListPet;
    MySpinner spinner, spinnerRelation, spinnerEyes, spinnerLanguage, spinnerMarital;
    TextInputLayout tilSpinMarital,tilSpinEye,tilSpinLang;
    FrameLayout flrel;
    TextView txtEyes,txtvGender,txtSpinMarital,txtSpinEye,txtSpinLang;
    View vgender;
    String[] countryList = {"Canada", "Mexico", "USA", "UK", "California", "India"};
    String imagepath = "", cardpath = "";//
    String relation = "Self";
    String OtherLang = "";
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    RelativeLayout rlCard;
    ImageView txtCard;
    DBHelper dbHelper, dbHelper1;
    View rootview;
    Preferences preferences;
    ImageView imgBack;
    RelativeConnection connection;
    //   PersonalInfo personalInfo;
    RadioGroup rgGender;
    RadioButton rbMale,rbFemale,rbTrans;
    LinearLayout llAddPhone;
    ToggleButton tbLive,tbEnglish,tbVeteran,tbPet,tbCard;

    TextInputLayout tilBdate, tilName, tilWorkPhone;
    String[] Relationship = {"Aunt", "Brother", "Brother-in-law", "Client", "Cousin", "Dad", "Daughter", "Father-in-law", "Friend", "GrandDaughter", "GrandMother", "GrandFather", "GrandSon", "Husband", "Mom", "Mother-in-law", "Neighbor", "Nephew", "Niece", "Patient", "Roommate", "Significant Other", "Sister", "Sister-in-law", "Son", "Uncle", "Wife", "Other"};
    String[] EyesList = {"Blue", "Green", "Hazel", "Brown"};
    String[] MaritalList = {"Divorced", "Domestic Partner", "Married", "Other", "Separated", "Single", "Widowed"};
    String[] LangList = {"Arabic", "Chinese", "English", "French", "German", "Greek", "Hebrew", "Hindi", "Italian", "Japanese", "Korean", "Russian", "Spanish", "Other"};
    ImageLoader imageLoaderProfile, imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile, displayImageOptionsCard;
    boolean checkSave = false;
    boolean isOnActivityResult = false;
    String cardImgPath = "";
    FloatingActionButton floatProfile;
    ImageView floatOptions;;
    NonScrollListView listPhone;
    ContactData contactData;
    RelativeConnection con;
    FrameLayout flFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferences = new Preferences(context);
        initComponent();
        initImageLoader();
        initUI();
        initListener();
    }

    private void initImageLoader() {

        //Profile
        displayImageOptionsProfile = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default//shradha
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//shradha
                .displayer(new RoundedBitmapDisplayer(120)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptionsProfile)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoaderProfile = ImageLoader.getInstance();


        //Card
        displayImageOptionsCard = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.busi_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration configs = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptionsCard)
                .build();
        ImageLoader.getInstance().init(configs);
        imageLoaderCard = ImageLoader.getInstance();
    }

    private void initComponent() {
        dbHelper = new DBHelper(context, "MASTER");

        String ss = preferences.getString(PrefConstants.CONNECTED_USERDB);
        dbHelper1 = new DBHelper(context, ss);

        PetQuery p = new PetQuery(context, dbHelper1);
        ContactDataQuery c=new ContactDataQuery(context,dbHelper1);
        MyConnectionsQuery md = new MyConnectionsQuery(context, dbHelper1);con = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

        phonelist=ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),-1,"Personal Profile");
        Originalphonelist=ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),-1,"Personal Profile");

        MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
        connection = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }


    private void initListener() {
        imgBack.setOnClickListener(this);
        txtBdate.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgEditCard.setOnClickListener(this);
        imgCard.setOnClickListener(this);
        flFront.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtGender.setOnClickListener(this);
        imgAddpet.setOnClickListener(this);
        txtAddPet.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        chkChild.setOnCheckedChangeListener(this);
        chkSibling.setOnCheckedChangeListener(this);
        chkFriend.setOnCheckedChangeListener(this);
        chkGrandParent.setOnCheckedChangeListener(this);
        chkParent.setOnCheckedChangeListener(this);
        chkSpouse.setOnCheckedChangeListener(this);
        floatProfile.setOnClickListener(this);
    }

    private void initUI() {
        llAddPhone = findViewById(R.id.llAddPhone);
        floatProfile = findViewById(R.id.floatProfile);
        floatOptions = findViewById(R.id.floatOptions);
        int user = preferences.getInt(PrefConstants.CONNECTED_USERID);

        flrel=findViewById(R.id.flrel);
        txtRelation=findViewById(R.id.txtRelation);
        txtRelation.setFocusable(false);
        listPhone=findViewById(R.id.listPhone);
        tilSpinEye=findViewById(R.id.tilSpinEyes);
        txtSpinEye=findViewById(R.id.txtSpinEyes);
        txtSpinEye.setFocusable(false);

        tilSpinLang=findViewById(R.id.tilSpinLang);
        txtSpinLang=findViewById(R.id.txtSpinLang);
        txtSpinLang.setFocusable(false);

        tilSpinMarital=findViewById(R.id.tilSpinMarital);
        txtSpinMarital=findViewById(R.id.txtSpinMarital);
        txtSpinMarital.setFocusable(false);

        txtvGender=findViewById(R.id.txtvGender);
        vgender=findViewById(R.id.vgender);
        imgInfo = findViewById(R.id.imgInfo);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InstructionActivity.class);
                i.putExtra("From", "Personal");
                startActivity(i);

            }
        });

        llIndividual = findViewById(R.id.llIndividual);
        rlCard = findViewById(R.id.rlCard);
        rlLive = findViewById(R.id.rlLive);
        txtCard = findViewById(R.id.txtCard);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Personal Profile");
        imgRight = findViewById(R.id.imgRight);

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
        chkOther = findViewById(R.id.chkOther);
        Typeface font = Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf");
        chkOther.setTypeface(font);
//        chkOther.setTypeface(Typeface.defaultFromStyle(R.font.lato));
        txtOtherLanguage = findViewById(R.id.txtOtherLanguage);
        tilOtherLanguage = findViewById(R.id.tilOtherLanguage);
        tilOtherLanguage.setHint("Other Language");

        chkChild = findViewById(R.id.chkChild);
        chkSibling = findViewById(R.id.chkSibling);
        chkFriend = findViewById(R.id.chkFriend);
        chkGrandParent = findViewById(R.id.chkGrandParent);
        chkParent = findViewById(R.id.chkParent);
        chkSpouse = findViewById(R.id.chkSpouse);

        chkChild.setTypeface(font);
        chkSibling.setTypeface(font);
        chkFriend.setTypeface(font);
        chkGrandParent.setTypeface(font);
        chkParent.setTypeface(font);
        chkSpouse.setTypeface(font);

        ListPet = findViewById(R.id.ListPet);
        imgProfile = findViewById(R.id.imgProfile);


        imgCard = findViewById(R.id.imgCard);
        imgEditCard = findViewById(R.id.imgEditCard);
        flFront = findViewById(R.id.flFront);
        imgHome=findViewById(R.id.imgHome);
        imgAddpet = findViewById(R.id.imgAddPet);
        txtAddPet = findViewById(R.id.txtAddPet);
        if (pet.equals(""))
        {
            txtAddPet.setVisibility(View.GONE);
        }

        tilName = findViewById(R.id.tilName);
        tilOtherRelation = findViewById(R.id.tilOtherRelation);
        tilOtherRelation.setHint("Other Relation");
        tilWorkPhone = findViewById(R.id.tilWorkPhone);
        tilId = findViewById(R.id.tilId);
        rlPet = findViewById(R.id.rlPet);
        txtOtherRelation = findViewById(R.id.txtOtherRelation);
        txtLogin = findViewById(R.id.txtLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtBdate = findViewById(R.id.txtBdate);
        txtGender = findViewById(R.id.txtGender);
        imgBack = findViewById(R.id.imgBack);
        imgEdit = findViewById(R.id.imgEdit);
        imgDone = findViewById(R.id.imgDone);
        txtSave = findViewById(R.id.txtSave);
        //imgDone.setVisibility(View.VISIBLE);
        txtRelation = findViewById(R.id.txtRelation);
        tilBdate = findViewById(R.id.tilBdate);
        spinnerRelation = findViewById(R.id.spinnerRelation);
        txtAddress = findViewById(R.id.txtAddress);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtCountry = findViewById(R.id.txtCountry);
        txtPhone = findViewById(R.id.txtPhone);
        txtHomePhone = findViewById(R.id.txtHomePhone);
        txtWorkPhone = findViewById(R.id.txtWorkPhone);

        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtProfession = findViewById(R.id.txtProfession);
        txtPeople= findViewById(R.id.txtPeople);
        txtEmployed = findViewById(R.id.txtEmployedBy);
        txttelephone = findViewById(R.id.txttelephone);
        txtReligion = findViewById(R.id.txtReligion);
        txtIdNumber = findViewById(R.id.txtId);
        txtOther = findViewById(R.id.txtOther);
        tilOther = findViewById(R.id.tilOther);

        rgGender=findViewById(R.id.rgGender);
        rbMale=findViewById(R.id.rbMale);
        rbFemale=findViewById(R.id.rbFemale);
        rbTrans=findViewById(R.id.rbTrans);

        spinner = findViewById(R.id.spinner);
        spinnerEyes = findViewById(R.id.spinnerEyes);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        spinnerMarital = findViewById(R.id.spinnerMarital);

        ArrayAdapter<String> adapterm = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, MaritalList);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarital.setAdapter(adapterm);
        spinnerMarital.setHint("Marital Status");


        ArrayAdapter<String> adapters = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, EyesList);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEyes.setAdapter(adapters);
        spinnerEyes.setHint("Eye Color");

        ArrayAdapter<String> adapterl = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LangList);
        adapterl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterl);
        spinnerLanguage.setHint("Language Spoken");

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Country");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relationship");

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherLanguage.setVisibility(View.VISIBLE);
                    //txtOtherLanguage.requestFocus();
                } else {
                    tilOtherLanguage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chkOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    tilOther.setVisibility(View.VISIBLE);
                    other = "YES";

                } else if (isChecked == false) {
                    tilOther.setVisibility(View.GONE);
                    other = "NO";
                    liveOther="";
                }
            }
        });

        tbLive=findViewById(R.id.tbLive);
        tbEnglish=findViewById(R.id.tbEnglish);
        tbVeteran=findViewById(R.id.tbVeteran);
        tbCard=findViewById(R.id.tbCard);
        tbCard.setChecked(false);
        tbPet=findViewById(R.id.tbPet);

        tbCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true)
                {
                    has_card="YES";
                    rlCard.setVisibility(View.VISIBLE);
                    imgEditCard.setVisibility(View.VISIBLE);
                    if(flFront.getVisibility()==View.VISIBLE){
                        imgEditCard.setVisibility(View.GONE);
                    }
                }
                else{
                    has_card="NO";
                    // imgCard.setImageResource(R.drawable.busi_card);
                    txtCard.setVisibility(View.VISIBLE);
                    flFront.setVisibility(View.VISIBLE);
                    imgEditCard.setVisibility(View.GONE);
                    imgCard.setVisibility(View.GONE);
                    cardpath = "";
                    CardMap = null;
                    rlCard.setVisibility(View.GONE);

                }
            }
        });

        tbLive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    live = "YES";
                    rlLive.setVisibility(View.GONE);
                    child = "NO";
                    friend = "NO";
                    grandParent = "NO";
                    parent = "NO";
                    spouse = "NO";
                    other = "NO";
                    sibling = "NO";
                    liveOther = "";
                    chkChild.setChecked(false);
                    chkSibling.setChecked(false);
                    chkFriend.setChecked(false);
                    chkGrandParent.setChecked(false);
                    chkParent.setChecked(false);
                    chkSpouse.setChecked(false);
                    chkOther.setChecked(false);
                }
                else {
                    live = "NO";
                    rlLive.setVisibility(View.VISIBLE);
                }
            }
        });

        tbEnglish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                    english = "YES";
                else
                    english = "NO";
            }
        });

        tbPet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pet = "YES";
                    rlPet.setVisibility(View.VISIBLE);
                    txtAddPet.setVisibility(View.VISIBLE);

                }else {
                    pet = "NO";
                    rlPet.setVisibility(View.GONE);
                    txtAddPet.setVisibility(View.GONE);

                    boolean flag = PetQuery.deleteRecords(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    if (flag == true) {
                        //  Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                        setPetData();
                        // ListPet.requestFocus();
                    }
                }
            }
        });

        tbVeteran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    veteran = "YES";
                    tilId.setVisibility(View.VISIBLE);
                }
                else {
                    veteran = "NO";
                    tilId.setVisibility(View.GONE);
                    idnumber = "";
                    txtIdNumber.setText(idnumber);
                }
            }
        });


        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbMale) {
                    gender="Male";

                } else if (checkedId == R.id.rbFemale) {
                    gender="Female";
                }
                else if (checkedId == R.id.rbTrans) {
                    gender="Trans";
                }
            }
        });

        llIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
        txtPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txttelephone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txttelephone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        txtHomePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHomePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtWorkPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtWorkPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtHeight.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHeight.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 1)) {
                    editable.append("-");
                }
            }
        });
        setValues();
        txtRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RelationActivity.class);
                i.putExtra("Category", "Relationp");
                i.putExtra("Selected",txtRelation.getText().toString());
                startActivityForResult(i, REQUEST_RELATIONP);
            }
        });

        txtSpinMarital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RelationActivity.class);
                i.putExtra("Category", "Marital");
                i.putExtra("Selected",txtSpinMarital.getText().toString());
                startActivityForResult(i, REQUEST_MARITAL);
            }
        });

        txtSpinLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RelationActivity.class);
                i.putExtra("Category", "language");
                i.putExtra("Selected",txtSpinLang.getText().toString());
                startActivityForResult(i, REQUEST_LANGUAGE);
            }
        });

        txtSpinEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RelationActivity.class);
                i.putExtra("Category", "eyes");
                i.putExtra("Selected",txtSpinEye.getText().toString());
                startActivityForResult(i, REQUEST_EYES);
            }
        });

        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherRelation.setVisibility(View.VISIBLE);
                    tilOtherRelation.setHint("Other Relation");
                } else {
                    tilOtherRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        setPetData();
        setListPh();
    /*  listPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"CLicked",Toast.LENGTH_SHORT).show();
                final TextView txtPhoNum=view.findViewById(R.id.txtPhoNum);
                txtPhoNum.setClickable(true);
                txtPhoNum.setFocusable(true);
                final TextView txtType=view.findViewById(R.id.txtType);
                txtType.setFocusable(true);

                txtPhoNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"CLicked",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }


    //Nikita - PH format code ends here
    ArrayList<EditText> mTextViewListValue = new ArrayList<>();
    ArrayList<TextView> mTextViewListType = new ArrayList<>();
    ArrayList<ImageView> mImageViewType = new ArrayList<>();

    public class CustomTextWatcher implements TextWatcher {
        EditText et = null;

        CustomTextWatcher(EditText et) {
            this.et = et;
        }

        int prevL = 0;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            prevL = et.getText().toString().length();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int length = editable.length();
//            int poss = Integer.parseInt(et.getTag().toString());
            if ((prevL < length) && (length == 3 || length == 7)) {
                et.setText(editable.toString() + "-");
                et.setSelection(et.getText().length());
            }
//            phonelist.get(poss).setValue(et.getText().toString());
        }

    }

    public void deletePhone(int position) {// Tricky code to delete required item
        try {
            for (int i = 0; i < phonelist.size(); i++) {
                if (phonelist.get(i).getId() == position) {//uses index As it is but matching ids
                    ContactData cc = phonelist.get(i);
                    phonelist.remove(cc);
                    llAddPhone.removeAllViews();
                    mTextViewListValue.clear();
                    mTextViewListType.clear();
                    mImageViewType.clear();
                    setListPh();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            llAddPhone.removeAllViews();
            mTextViewListValue.clear();
            mTextViewListType.clear();
            mImageViewType.clear();
            setListPh();
        }
    }

    public void addNewPhone(final int pos) {
        try {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.row_phone, null);

            ImageView imgdeletePhone;
            TextView txtType;
            EditText txtPhoNum;

            imgdeletePhone = view.findViewById(R.id.imgdeletePhone);
            txtPhoNum = view.findViewById(R.id.txtPhoNum);
            txtType = view.findViewById(R.id.txtType);

            //Add the instance to the ArrayList -  to maintian separate tags of views
            mTextViewListValue.add(pos, txtPhoNum);
            mTextViewListType.add(pos, txtType);
            mImageViewType.add(pos, imgdeletePhone);

            if (pos == 0) {
                imgdeletePhone.setImageResource(R.drawable.add_n);
            } else {
                imgdeletePhone.setImageResource(R.drawable.delete_n);
            }

            mImageViewType.get(pos).setTag("" + pos);
            mTextViewListType.get(pos).setTag("" + pos);
            mTextViewListValue.get(pos).setTag("" + pos);

            mTextViewListType.get(pos).setText("" + phonelist.get(pos).getContactType());
            mTextViewListValue.get(pos).setText("" + phonelist.get(pos).getValue());

            mTextViewListValue.get(pos).addTextChangedListener(new CustomTextWatcher(mTextViewListValue.get(pos)));

            mTextViewListValue.get(pos).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        int poss = Integer.parseInt(mTextViewListValue.get(pos).getTag().toString());
                        final TextView Caption = (TextView) view;
                        phonelist.get(poss).setValue(Caption.getText().toString());
                    }
                }
            });

            mImageViewType.get(pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backflap=true;
                    int poss = Integer.parseInt(mImageViewType.get(pos).getTag().toString());
                    if (poss == 0) {
                        ContactData c = new ContactData();
                        c.setId(phonelist.size());
                        phonelist.add(c);
                        addNewPhone(c.getId());
                    } else {
                        deletePhone(poss);
                    }
                }
            });

            mTextViewListType.get(pos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = Integer.parseInt(mTextViewListType.get(pos).getTag().toString());
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setTitle("Type");
                    final String[] types = {"Fax", "Home", "Mobile", "Office"};
                    b.setItems(types, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (types[which].equalsIgnoreCase("None")) {
                                phonelist.get(position).setValue(phonelist.get(position).getValue());
                                phonelist.get(position).setContactType("");
                                mTextViewListType.get(pos).setText(phonelist.get(position).getContactType());
                            } else {
                                backflap=true;
                                phonelist.get(position).setValue(phonelist.get(position).getValue());
                                phonelist.get(position).setContactType(types[which]);
                                mTextViewListType.get(pos).setText(phonelist.get(position).getContactType());
                            }
                            dialog.dismiss();
                        }

                    });
                    b.show();
                }
            });

            llAddPhone.addView(view, pos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setListPh() {

        if (phonelist.isEmpty()) {
            ContactData c=new ContactData();
            c.setId(0);
            phonelist.add(c);
            addNewPhone(0);
        } else {
            for (int i = 0; i < phonelist.size(); i++) {
                if (phonelist.get(i) != null && phonelist.get(i).getValue() != null) {
                    phonelist.get(i).setId(i);
                    String input = phonelist.get(i).getValue();

                    if(!input.contains("-")) {
                        if (input.contains("(")) {
                            input = input.replace("(", "");
                        }

                        if (input.contains(")")) {
                            input = input.replace(")", "");
                        }

                        if(input.contains("+")) {
                            if (input.length() == 13) {
                                String str_getMOBILE = input.substring(3);

                                input = str_getMOBILE;
                            } else if (input.length() == 12) {
                                String str_getMOBILE = input.substring(2);
                                input = str_getMOBILE;
                            }
                        }

                        String number = input.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
                        phonelist.get(i).setValue(number);
                        System.out.println(number);
                    }
                    addNewPhone(i);
                }
            }
        }

    }

    //Nikita - PH Format code ends here

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setValues() {
        if (connection.getRelationType().equals("Self")) {
            tilBdate.setVisibility(View.VISIBLE);
            // spinner.setVisibility(View.VISIBLE);
            //  SavwGender.setVisibility(View.VISIBLE);
            rgGender.setVisibility(View.VISIBLE);
            txtvGender.setVisibility(View.VISIBLE);
            vgender.setVisibility(View.VISIBLE);
            spinnerRelation.setVisibility(View.GONE);
            flrel.setVisibility(View.GONE);
            //  txtWorkPhone.setVisibility(View.VISIBLE);//shradha
            // tilWorkPhone.setVisibility(View.VISIBLE);
            txtHomePhone.setVisibility(View.VISIBLE);
        } else {
            // Varsa commented 3 june
            /*tilBdate.setVisibility(View.GONE);
            flrel.setVisibility(View.VISIBLE);
            txtGender.setVisibility(View.GONE);
            txtvGender.setVisibility(View.GONE);
            vgender.setVisibility(View.GONE);
            rgGender.setVisibility(View.GONE);*/
            txtBdate.setText(connection.getDob());
        }
        if (connection != null) {
            txtName.setText(connection.getName());
            txtEmail.setText(connection.getEmail());
            Email = connection.getEmail();
            txtAddress.setText(connection.getAddress());
            txtPhone.setText(connection.getMobile());
            txtOtherRelation.setText(connection.getOtherRelation());
            txtHomePhone.setText(connection.getPhone());
            txtWorkPhone.setText(connection.getWorkPhone());txtBdate.setText(connection.getDob());
            //txtGender.setText(connection.getGender());
            if (connection.getGender() != null) {
                if (connection.getGender().equalsIgnoreCase("Male")) {
                    rbMale.setChecked(true);
                    rbFemale.setChecked(false);
                    rbTrans.setChecked(false);
                    gender = "Male";

                } else if (connection.getGender().equalsIgnoreCase("Female")) {
                    rbMale.setChecked(false);
                    rbFemale.setChecked(true);
                    rbTrans.setChecked(false);
                    gender = "Female";

                }else  if (connection.getGender().equalsIgnoreCase("Trans")||connection.getGender().equalsIgnoreCase("Trans*")) {
                    rbMale.setChecked(false);
                    rbFemale.setChecked(false);
                    rbTrans.setChecked(true);
                    gender = "Trans";

                }
            }
            txtBdate.setText(connection.getDob());
            txtRelation.setText(connection.getRelationType());

            if (connection.getRelationType().equalsIgnoreCase("Other"))
            {
                tilOtherRelation.setVisibility(View.VISIBLE);
                txtOtherRelation.setVisibility(View.VISIBLE);
            }else{
                tilOtherRelation.setVisibility(View.GONE);
                txtOtherRelation.setVisibility(View.GONE);
            }
           /* if (index!=0)
                spinnerRelation.setSelection(index+1);*/
            txtOther.setText(connection.getOther_person());

            if (connection.getLive() != null) {
                if (connection.getLive().equals("YES")) {
                    tbLive.setChecked(true);
                    live = "YES";
                    rlLive.setVisibility(View.GONE);
                } else if (connection.getLive().equals("NO")){
                    tbLive.setChecked(false);

                    live = "NO";
                    rlLive.setVisibility(View.VISIBLE);
                }
            }
            if (connection.getChildren() != null) {
                if (connection.getChildren().equals("YES")) {
                    chkChild.setChecked(true);
                    child = "YES";
                } else if (connection.getChildren().equals("NO")) {
                    chkChild.setChecked(false);
                    child = "NO";
                }
            }

            if (connection.getSibling() != null) {
                if (connection.getSibling().equals("YES")) {
                    chkSibling.setChecked(true);
                    sibling = "YES";
                } else if (connection.getSibling().equals("NO")) {
                    chkSibling.setChecked(false);
                    sibling = "NO";
                }
            }

            if (connection.getFriend() != null) {
                if (connection.getFriend().equals("YES")) {
                    chkFriend.setChecked(true);
                    friend = "YES";
                } else if (connection.getFriend().equals("NO")) {
                    chkFriend.setChecked(false);
                    friend = "NO";
                }
            }

            if (connection.getGrand() != null) {
                if (connection.getGrand().equals("YES")) {
                    chkGrandParent.setChecked(true);
                    grandParent = "YES";
                } else if (connection.getGrand().equals("NO")) {
                    chkGrandParent.setChecked(false);
                    grandParent = "NO";
                }
            }
            if (connection.getParents() != null) {
                if (connection.getParents().equals("YES")) {
                    chkParent.setChecked(true);
                    parent = "YES";
                } else if (connection.getParents().equals("NO")) {
                    chkParent.setChecked(false);
                    parent = "NO";
                }
            }
            if (connection.getSpouse() != null) {
                if (connection.getSpouse().equals("YES")) {
                    chkSpouse.setChecked(true);
                    spouse = "YES";
                } else if (connection.getSpouse().equals("NO")) {
                    chkSpouse.setChecked(false);
                    spouse = "NO";
                }
            }
            if (connection.getSign_other() != null) {
                if (connection.getSign_other().equals("YES")) {
                    chkOther.setChecked(true);
                    other = "YES";
                    tilOther.setVisibility(View.VISIBLE);

                } else if (connection.getSign_other().equals("NO")) {
                    chkOther.setChecked(false);
                    other = "NO";
                    tilOther.setVisibility(View.GONE);
                }
            }

            imagepath = connection.getPhoto();
            if (!imagepath.equals("")) {
                File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), imagepath);
                if (imgFile.exists()) {
                    //Shradha
                    imgProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                    imgEdit.setVisibility(View.VISIBLE);
                    //imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgProfile, displayImageOptionsProfile);
                } else {
                    Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show();
                }
            } else {
//                Toast.makeText(context, "You have done wrong", Toast.LENGTH_SHORT).show();

                imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                imgEdit.setVisibility(View.GONE);
            }
              /*  byte[] photo=personalInfo.getPhoto();
            Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imgProfile.setImageBitmap(bmp);*/
            cardpath = connection.getPhotoCard();
            if (!connection.getPhotoCard().equals("")) {
                File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), connection.getPhotoCard());
                if (imgFile1.exists()) {
                      /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                    imgCard.setImageBitmap(myBitmap);*/

                    imgCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));

                    // imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgCard, displayImageOptionsCard);
                }
                   /* byte[] photoCard = personalInfo.getPhotoCard();
                Bitmap bmps = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);*/
                // imgCard.setImageBitmap(bmps);
                imgCard.setVisibility(View.VISIBLE);
                imgEditCard.setVisibility(View.VISIBLE);
                rlCard.setVisibility(View.VISIBLE);
                txtCard.setVisibility(View.GONE);
                flFront.setVisibility(View.GONE);
                tbCard.setChecked(true);
            } else {
                imgCard.setVisibility(View.GONE);
                rlCard.setVisibility(View.VISIBLE);
                txtCard.setVisibility(View.VISIBLE);
                imgEditCard.setVisibility(View.GONE);
                tbCard.setChecked(false);
            }

            txtHeight.setText(connection.getHeight());
            txtWeight.setText(connection.getWeight());
            txtProfession.setText(connection.getProfession());
            if(connection.getPeople()!=null) {
                if (connection.getPeople().equalsIgnoreCase("null")) {
                    txtPeople.setText("");
                } else {
                    txtPeople.setText(connection.getPeople());
                }
            } else {
                txtPeople.setText("");
            }
            txtEmployed.setText(connection.getEmployed());
            txttelephone.setText(connection.getManager_phone());
            txtReligion.setText(connection.getReligion());
            txtIdNumber.setText(connection.getIdnumber());
            int indexd = 0;
            txtBdate.setText(connection.getDob());

            if (connection.getEyes().equalsIgnoreCase("null"))
            {
                txtSpinEye.setText("");
            }else {
                txtSpinEye.setText(connection.getEyes());
            }

            txtSpinLang.setText(connection.getLanguage());
            if (connection.getLanguage().equalsIgnoreCase("Other"))
            {
                tilOtherLanguage.setVisibility(View.VISIBLE);
                txtOtherLanguage.setText(connection.getOtherLang());
            }else{
                tilOtherLanguage.setVisibility(View.GONE);
                txtOtherLanguage.setText("");
            }

            if (connection.getMarital_status().equalsIgnoreCase("null"))
            {
                txtSpinMarital.setText("");
            }else
            {
                txtSpinMarital.setText(connection.getMarital_status());
            }


            if (connection.getVeteran() != null) {
                if (connection.getVeteran().equals("YES")) {
                    tbVeteran.setChecked(true);
                    veteran="YES";
                } else if (connection.getVeteran().equals("NO")){
                    tbVeteran.setChecked(false);
                    veteran="NO";
                }
            }

            if (connection.getHas_card() != null) {
                if (connection.getHas_card().equals("YES")) {
                    tbCard.setChecked(true);
                    has_card="YES";
                    rlCard.setVisibility(View.VISIBLE);
                } else if (connection.getHas_card().equals("NO")){
                    tbCard.setChecked(false);
                    has_card="NO";
                    rlCard.setVisibility(View.GONE);
                    cardpath = "";
                    CardMap = null;
                }
            }else{
                tbCard.setChecked(false);
                has_card="NO";
                rlCard.setVisibility(View.GONE);
                cardpath = "";
                CardMap = null;
            }
            if (connection.getEnglish() != null) {
                if (connection.getEnglish().equals("YES")) {
                    tbEnglish.setChecked(true);
                    english="YES";
                } else if (connection.getEnglish().equals("NO")){
                    tbEnglish.setChecked(false);
                    english="NO";
                }
            }
            if (connection.getPet() != null) {
                if (connection.getPet().equals("YES")) {
                    tbPet.setChecked(true);
                    pet="YES";

                    txtAddPet.setVisibility(View.VISIBLE);
                } else if (connection.getPet().equals("NO")){
                    tbPet.setChecked(false);
                    pet="NO";
                    txtAddPet.setVisibility(View.GONE);
                }
            }

        }
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAddPet:
                Intent intent = new Intent(context, AddPetActivity.class);
                intent.putExtra("FROM", "View");
                startActivityForResult(intent, REQUEST_PET);
                break;

            case R.id.floatOptions:
                showFloatPdfDialog();
                break;

            case R.id.txtSave:

                if (pet.equals("NO")) {
                    boolean flag = PetQuery.deleteRecords(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    if (flag == true) {
                        setPetData();
                    }
                }

                if (validateConnection()) {
                    if (email.equals("") || email.equals(Email)) {
                        editToConnection(imagepath, cardpath);
                    } else {
                        Boolean flags = MyConnectionsQuery.fetchEmailRecord(email);
                        if (flags == true) {
                            Toast.makeText(context, "This email address is already registered by another profile, Please add another email address", Toast.LENGTH_SHORT).show();
                            txtEmail.setError("This email address is already registered by another profile, Please add another email address");
                        } else {
                            editToConnection(imagepath, cardpath);
                        }
                    }
                }


                break;
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
//                intentDashboard.putExtra("DASHBOARD", 1);
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intentDashboard);


                break;

            case R.id.imgBack:

               getValues();
                    if (connection.getName().equals(name) && connection.getAddress().equals(address) && connection.getEmail().equals(email) && connection.getRelationType().equals(relation) &&
                            connection.getPhoto().equals(imagepath) && connection.getOtherRelation().equals(otherRelation) && connection.getHeight().equals(height) && connection.getWeight().equals(weight) &&
                            connection.getEyes().equals(eyes) && connection.getProfession().equals(profession) && connection.getEmployed().equals(employed) && connection.getLanguage().equals(language) &&
                            connection.getMarital_status().equals(marital_status) && connection.getReligion().equals(religion) && connection.getVeteran().equals(veteran) && connection.getIdnumber().equals(idnumber) &&
                            connection.getPet().equals(pet) && connection.getManager_phone().equals(manager_phone) && connection.getPhotoCard().equals(cardpath) && connection.getEnglish().equals(english) &&
                            connection.getChildren().equals(child) && connection.getFriend().equals(friend) && connection.getGrand().equals(grandParent) && connection.getParents().equals(parent) &&
                            connection.getSpouse().equals(spouse) && connection.getOther_person().equals(liveOther) && connection.getLive().equals(live) && connection.getSign_other().equals(other) && connection.getOtherLang().equals(OtherLang) &&
                            connection.getDob().equals(bdate) && connection.getGender().equals(gender) && connection.getSibling().equals(sibling) && connection.getHas_card().equals(has_card) && connection.getPeople().equals(people)
                            && backflap==false){
                        hideSoftKeyboard();
                        finish();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Save");
                        alert.setMessage("Do you want to save information?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!connection.getName().equals(name) || !connection.getEmail().equals(address))
                                {
                                    isfinis=true;
                                }
                                hideSoftKeyboard();
                                boolean s=  txtSave.performClick();
                                backflap=false;
                                dialog.dismiss();
                                if (connection.getName().equals(name) || connection.getEmail().equals(address))
                                {
                                    finish();
                                }

                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                hideSoftKeyboard();
                                finish();
                            }
                        });
                        alert.show();
                    }

                break;

            case R.id.txtGender:
                final Dialog dialogs = new Dialog(context);
                dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogs.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf1 = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview1 = lf1.inflate(R.layout.dialog_gender1, null);
                final TextView textOptions1 = dialogview1.findViewById(R.id.txtOption1);
                final TextView textOptions2 = dialogview1.findViewById(R.id.txtOption2);
                final TextView textOptions3 = dialogview1.findViewById(R.id.txtOption3);
                TextView textCancels = dialogview1.findViewById(R.id.txtCancel);
                textOptions1.setText("Female");
                textOptions2.setText("Male");
                textOptions3.setText("Trans");
                dialogs.setContentView(dialogview1);
                WindowManager.LayoutParams lps = new WindowManager.LayoutParams();
                lps.copyFrom(dialogs.getWindow().getAttributes());
                int widths = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
                lps.width = widths;
                lps.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lps.gravity = Gravity.CENTER;
                dialogs.getWindow().setAttributes(lps);
                dialogs.show();
                textOptions1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtGender.setText("Female");
                        dialogs.dismiss();
                    }
                });
                textOptions2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtGender.setText("Male");
                        dialogs.dismiss();
                    }
                });

                textOptions3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtGender.setText("Trans");
                        dialogs.dismiss();
                    }
                });
                textCancels.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogs.dismiss();
                    }
                });

                break;

            case R.id.imgRight:
                Intent ia = new Intent(context, InstructionActivity.class);
                ia.putExtra("From", "Personal");
                startActivity(ia);
                break;

            case R.id.imgEdit:
                showCardDialog(RESULT_CAMERA_IMAGE, RESULT_SELECT_PHOTO, imgProfile, "Profile");

                break;
            case R.id.imgProfile:
                showCardDialog(RESULT_CAMERA_IMAGE, RESULT_SELECT_PHOTO, imgProfile, "Profile");

                break;
            case R.id.imgEditCard:
                showCardDialog(RESULT_CAMERA_IMAGE_CARD, RESULT_SELECT_PHOTO_CARD, imgCard, "Card");

                break;
            case R.id.flFront:
                showCardDialog(RESULT_CAMERA_IMAGE_CARD, RESULT_SELECT_PHOTO_CARD, imgCard, "Card");
                break;

            case R.id.imgCard:
               /* Bitmap bitma = ((BitmapDrawable) imgCard.getDrawable()).getBitmap();
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitma.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] photoCard = bao.toByteArray();*/
                if (cardpath != "") {
                    Intent i = new Intent(context, AddFormActivity.class);
                    i.putExtra("Image", cardpath);
                    i.putExtra("IsDelete", true);
                    i.putExtra("isOnActivityResult", isOnActivityResult);
                    i.putExtra("cardImgPath", cardImgPath);
                    startActivityForResult(i, REQUEST_CARD);
                }
                break;

            case R.id.txtBdate:
                final Calendar calendar = Calendar.getInstance();
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
                        String reportDate = new SimpleDateFormat("dd MMM yyyy").format(datePickerDate);

                        DateClass d = new DateClass();
                        d.setDate(reportDate);
                        if (datePickerDate.after(calendar.getTime())) {
                            Toast.makeText(context, "Birthdate should be greater than today's date", Toast.LENGTH_SHORT).show();
                        } else {
                            txtBdate.setText(reportDate);
                        }
                    }
                }, year, month, day);
                dpd.show();
                break;

        }
    }

    private void getValues() {
        for (int i = 0; i < phonelist.size(); i++) {
            if (phonelist.get(i).getContactType() == "" && phonelist.get(i).getValue() == "") {
                phonelist.remove(phonelist.get(i));
            }
        }
        storeImage(ProfileMap, "Profile");
        storeImage(CardMap, "Card");
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        workPhone = txtWorkPhone.getText().toString().trim();
        homePhone = txtHomePhone.getText().toString().trim();
        otherRelation = txtOtherRelation.getText().toString().trim();
        address = txtAddress.getText().toString().trim();
        relation=txtRelation.getText().toString();
        eyes=txtSpinEye.getText().toString();
        marital_status=txtSpinMarital.getText().toString();
        language=txtSpinLang.getText().toString();
        OtherLang = txtOtherLanguage.getText().toString();

        bdate = txtBdate.getText().toString().trim();
        homePhone = txtHomePhone.getText().toString().trim();
        //  gender = txtGender.getText().toString().trim();
        liveOther = txtOther.getText().toString();
        idnumber = txtIdNumber.getText().toString();
        height = txtHeight.getText().toString();
        weight = txtWeight.getText().toString();
        profession = txtProfession.getText().toString();
        people = txtPeople.getText().toString();
        employed = txtEmployed.getText().toString();
        manager_phone = txttelephone.getText().toString();
        religion = txtReligion.getText().toString();
    }

    private void showFloatPdfDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "PersonalProfile.pdf");
        if (file.exists()) {
            file.delete();
        }

        // Pdf New
        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png", context);

        calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
        profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + imagepath,pdflogo,calendar,profile,"PERSONAL PROFILE", calendarWite, profileWite);

        HeaderNew.addusereNameChank("PERSONAL PROFILE");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);


        final RelativeConnection personalInfoList = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        final ArrayList<Pet> PetList = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        final ArrayList<ContactData> phonelist=ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),-1,"Personal Profile");

        Image pp = null;
        pp=preferences.addFile("pp.png", context);
        new IndividualNew(personalInfoList, PetList, phonelist,pp);

        HeaderNew.document.close();

        //------------------------------------------------------------------------
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
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf"
                        + "/PersonalProfile.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, context, "Personal Profile");
                dialog.dismiss();

            }
        });

        floatViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf"
                        + "/PersonalProfile.pdf";
                StringBuffer result = new StringBuffer();
                               /* if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                                    result.append(new MessageString().getProfileUser());
                                }else {*/
                result.append(new MessageString().getProfileProfile());
                // }

                new PDFDocumentProcess(path,
                        context, result);

                System.out.println("\n" + result + "\n");
                dialog.dismiss();
            }
        });

    }

    private void showCardDialog(final int resultCameraImage, final int resultSelectPhoto, final ImageView imgProfile, final String from) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_gender1, null);
        final TextView textOption1 = dialogview.findViewById(R.id.txtOption1);
        final TextView textOption2 = dialogview.findViewById(R.id.txtOption2);
        final TextView textOption3 = dialogview.findViewById(R.id.txtOption3);
        TextView textCancel = dialogview.findViewById(R.id.txtCancel);
        textOption1.setText("Take Picture");
        textOption2.setText("Gallery");
        textOption3.setText("Remove Picture");
        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        textOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dispatchTakePictureIntent(resultCameraImage,from);
                if (from.equals("Profile")) {
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    imageUriProfile = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


                    //  intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriProfile);
                    startActivityForResult(intent, resultCameraImage);
                } else if (from.equals("Card")) {
                    dialogCameraFront(resultCameraImage);
                }


                dialog.dismiss();
            }
        });
        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, resultSelectPhoto);

                dialog.dismiss();
            }
        });
        textOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("Profile")) {
                    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    imgEdit.setVisibility(View.GONE);
                    imagepath = "";
                    ProfileMap = null;
                } else if (from.equals("Card")) {
//                    imgCard.setImageResource(R.drawable.busi_card);
                    imgEditCard.setVisibility(View.GONE);
                    flFront.setVisibility(View.VISIBLE);
                    imgCard.setVisibility(View.GONE);
                    rlCard.setVisibility(View.VISIBLE);
                    txtCard.setVisibility(View.GONE);
                    cardpath = "";
                    CardMap = null;
                    //photoCard = null;
                }
                dialog.dismiss();
            }
        });
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private File createImageFile(String from) throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if (from.equals("Profile")) {
            imagepath = image.getAbsolutePath();
        } else if (from.equals("Card")) {
            cardpath = image.getAbsolutePath();
        }
        return image;
    }

    private boolean validateConnection() {
        for (int i = 0; i < phonelist.size(); i++) {
            ContactData c = phonelist.get(i);
            for (int k = 0; k < mTextViewListValue.size(); k++) {
                if (Integer.parseInt(mTextViewListValue.get(k).getTag().toString()) == c.getId()) {
                    phonelist.get(i).setValue(mTextViewListValue.get(k).getText().toString());
                }
            }
        }


        for (int i=0;i<phonelist.size();i++)
        {
            if (phonelist.get(i).getValue().isEmpty()&&phonelist.get(i).getContactType().isEmpty())
            {
                // phonelist.remove(phonelist.get(i));
                //  DialogManager.showAlert("Please add Phone number with Type", context);
            }else if (phonelist.get(i).getValue()==""&& phonelist.get(i).getContactType()!="")
            {
                DialogManager.showAlert("Please add Phone number with Type", context);
                return false;
            }else if (phonelist.get(i).getContactType()==""&&phonelist.get(i).getValue()!="")
            {
                DialogManager.showAlert("Please add Phone number with Type", context);
                return false;
            }else if (phonelist.get(i).getValue().length()<12||phonelist.get(i).getValue().length()>12)
            {
                DialogManager.showAlert("Phone number needs to be 10 digits", context);
                return false;
            }
        }

        if(tbCard.isChecked() && (CardMap==null && imgEditCard.getVisibility()!=View.VISIBLE)){
            DialogManager.showAlert("Please Add Business Card.", context);
            return false;
        }

        storeImage(ProfileMap, "Profile");
        storeImage(CardMap, "Card");
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        workPhone = txtWorkPhone.getText().toString().trim();
        homePhone = txtHomePhone.getText().toString().trim();
        otherRelation = txtOtherRelation.getText().toString().trim();
        address = txtAddress.getText().toString().trim();
        relation=txtRelation.getText().toString();
        eyes=txtSpinEye.getText().toString();
        marital_status=txtSpinMarital.getText().toString();
        language=txtSpinLang.getText().toString();
        OtherLang = txtOtherLanguage.getText().toString();

        bdate = txtBdate.getText().toString().trim();
        homePhone = txtHomePhone.getText().toString().trim();
        //  gender = txtGender.getText().toString().trim();
        liveOther = txtOther.getText().toString();
        idnumber = txtIdNumber.getText().toString();
        height = txtHeight.getText().toString();
        weight = txtWeight.getText().toString();
        profession = txtProfession.getText().toString();
        people = txtPeople.getText().toString();
        employed = txtEmployed.getText().toString();
        manager_phone = txttelephone.getText().toString();
        religion = txtReligion.getText().toString();
        for (int i=0;i<phonelist.size();i++)
        {
            if (phonelist.get(i).getContactType()=="" && phonelist.get(i).getValue()=="")
            {
                phonelist.remove(phonelist.get(i));
            }
            // Log.d("TERE",phonelist.get(i).getContactType()+"-"+phonelist.get(i).getValue());
        }
        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (relation.equals("")) {
            spinnerRelation.setError("Please Select Relation");
            DialogManager.showAlert("Please Select Relation", context);
        } else if (relation.equals("Other") && otherRelation.equals("")) {
            txtOtherRelation.setError("Please Enter Other Relation");
            DialogManager.showAlert("Please Enter Other Relation", context);
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            DialogManager.showAlert("Please Enter email",  context);
        } else if (!email.equals("") && !email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            DialogManager.showAlert("Please enter valid email", context);
        }
        else if (phone.length() != 0 && phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            DialogManager.showAlert("Phone number should be 10 digits", context);
        } else if (manager_phone.length() != 0 && manager_phone.length() < 10) {
            txttelephone.setError("Mobile number should be 10 digits");
            DialogManager.showAlert("Mobile number should be 10 digits", context);
        }
        else {
            return true;
        }
        return false;

    }




    private void editToConnection(String photo, String photoCard) {

        if (connection.getRelationType().equals("Self")) {
            if (connection.getName().equals(name) && connection.getEmail().equals(email)) {
                DBHelper dbHelper1 =new DBHelper(context, "MASTER");
                MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelper1);
                Boolean flag = MyConnectionsQuery.updateMyConnectionsData(1, name, email, address, phone, homePhone, workPhone, relation, imagepath, "", 1, 2, otherRelation, height, weight, eyes, profession, employed, language, marital_status, religion, veteran, idnumber, pet, manager_phone, cardpath, english, child, friend, grandParent, parent, spouse, other, liveOther, live, OtherLang, bdate, gender, sibling, has_card,people);
                if (flag == true) {
                    preferences.putString(PrefConstants.CONNECTED_PHOTO,imagepath);
                    DBHelper dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
                    MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                    Boolean flags = MyConnectionsQuery.updateMyConnectionsData(connection.getId(), name, email, address, phone, homePhone, workPhone, relation, imagepath, "", 1, 2, otherRelation, height, weight, eyes, profession, employed, language, marital_status, religion, veteran, idnumber, pet, manager_phone, cardpath, english, child, friend, grandParent, parent, spouse, other, liveOther, live, OtherLang, bdate, gender, sibling, has_card, people);
                    if (flags == true) {
                        Toast.makeText(context, "You have edited profile information successfully", Toast.LENGTH_SHORT).show();
                        connection = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                        validateConnection();
                        ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                        boolean flagf = ContactDataQuery.deleteRecord("Personal Profile", -1);
                        if (flagf == true) {
                            //  Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < phonelist.size(); i++) {
                                if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                    Boolean flagc = ContactDataQuery.insertContactsData(-1, preferences.getInt(PrefConstants.CONNECTED_USERID), connection.getEmail(), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Personal Profile");
                                    if (flagc == true) {
                                        // Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                        backflap=false;
                                    }
                                }
                            }
                        }
                        if (isfinis==true){
                            isfinis=false;
                            finish();
                        }
                    }
                    //Toast.makeText(context, "You have edited connection successfully", Toast.LENGTH_SHORT).show();
                    preferences.putString(PrefConstants.CONNECTED_NAME, name);
                    preferences.putString(PrefConstants.CONNECTED_RELATION, relation);
                    //   finish(); //Varsa
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!NetworkUtils.getConnectivityStatusString(ProfileActivity.this).equals("Not connected to Internet")) {
                    UpdateUserAsynk asynk = new UpdateUserAsynk(name, email, "" + preferences.getInt(PrefConstants.USER_ID));
                    asynk.execute();
                } else {
                    DialogManager.showAlert("Network Error, Check your internet connection", ProfileActivity.this);
                }
            }

        } else {
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelpers);
            Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, email, address, phone, homePhone, workPhone, relation, imagepath, "", 1, 2, otherRelation, height, weight, eyes, profession, employed, language, marital_status, religion, veteran, idnumber, pet, manager_phone, cardpath, english, child, friend, grandParent, parent, spouse, other, liveOther, live, OtherLang, bdate, gender, sibling, has_card, people);
            if (flag == true) {
                DBHelper dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
                MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                Boolean flags = MyConnectionsQuery.updateMyConnectionsData(1, name, email, address, phone, homePhone, workPhone, relation, imagepath, "", 1, 2, otherRelation, height, weight, eyes, profession, employed, language, marital_status, religion, veteran, idnumber, pet, manager_phone, cardpath, english, child, friend, grandParent, parent, spouse, other, liveOther, live, OtherLang, bdate, gender, sibling, has_card, people);
                if (flags == true) {
                    preferences.putString(PrefConstants.CONNECTED_PHOTO,imagepath);
                    Toast.makeText(context, "You have edited profile data successfully", Toast.LENGTH_SHORT).show();
                    connection = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    validateConnection();
                    preferences.putString(PrefConstants.CONNECTED_NAME, name);
                    preferences.putString(PrefConstants.CONNECTED_RELATION, relation);
                    String mail = email;
                    mail = mail.replace(".", "_");
                    mail = mail.replace("@", "_");
                    File oldFolder = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
                    File newFolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + mail + "/");
                    boolean success = oldFolder.renameTo(newFolder);
                    if (success) {
                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                    }
                    ContactDataQuery c = new ContactDataQuery(context, dbHelper);
                    boolean flagf = ContactDataQuery.deleteRecord("Personal Profile", -1);
                    if (flagf == true) {
                        //     Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < phonelist.size(); i++) {
                            if (!phonelist.get(i).getContactType().equalsIgnoreCase("") && !phonelist.get(i).getValue().equalsIgnoreCase("")) {
                                Boolean flagc = ContactDataQuery.insertContactsData(-1, preferences.getInt(PrefConstants.CONNECTED_USERID), connection.getEmail(), phonelist.get(i).getValue(), phonelist.get(i).getContactType(), "Personal Profile");
                                if (flagc == true) {
                                    //     Toast.makeText(context, "record inserted", Toast.LENGTH_SHORT).show();
                                    backflap=false;

                                }
                            }
                        }
                    }
                    if (isfinis==true){
                        isfinis=false;
                        finish();
                    }
                }
                // Toast.makeText(context, "You have edited connection successfully", Toast.LENGTH_SHORT).show();
                //   finish(); //Varsa
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = findViewById(R.id.imgProfile);
        ImageView profileCard = findViewById(R.id.imgCard);
        if (REQUEST_PET == requestCode) {
            setPetData();
            ListPet.requestFocus();
        }

        if (requestCode == RESULT_SELECT_PHOTO && data != null) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                // profileImage.setImageBitmap(selectedImage);
//                imageLoaderProfile.displayImage(String.valueOf(imageUri), imgProfile, displayImageOptionsProfile);
                // storeImage(selectedImage,"Profile");

                int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imgProfile.setImageBitmap(scaled);
                imgEdit.setVisibility(View.VISIBLE);
//                ProfileMap = selectedImage;
                ProfileMap = scaled;
                storeImage(ProfileMap, "Profile");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        /* Camera Code */

        if (requestCode == RESULT_CAMERA_IMAGE) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUriProfile);
                String imageurl = getRealPathFromURI(imageUriProfile);
                Bitmap selectedImage = imageOreintationValidator(thumbnail, imageurl);

                int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imgProfile.setImageBitmap(scaled);
                imgEdit.setVisibility(View.VISIBLE);

                // imageLoaderProfile.displayImage(String.valueOf(imageUriProfile), imgProfile, displayImageOptionsProfile);
                // profileImage.setImageBitmap(bitmap);
                storeImage(scaled, "Profile");
//                ProfileMap = selectedImage;
            } catch (Exception e) {
                e.printStackTrace();
            }
        /* Bundle extras = data.getExtras();
         Bitmap imageBitmap = (Bitmap) extras.get("data");
         imgProfile.setImageBitmap(imageBitmap);

         storeImage(imageBitmap,"Profile");*/

        }

        String fileName = "";
        if (requestCode == RESULT_SELECT_PHOTO_CARD && data != null) {


            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                // profileImage.setImageBitmap(selectedImage);
//                imageLoaderProfile.displayImage(String.valueOf(imageUri), imgProfile, displayImageOptionsProfile);
                // storeImage(selectedImage,"Profile");

                int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
                imgCard.setImageBitmap(scaled);
                rlCard.setVisibility(View.VISIBLE);
                imgCard.setVisibility(View.VISIBLE);

                txtCard.setVisibility(View.GONE);
                imgEditCard.setVisibility(View.VISIBLE);
                flFront.setVisibility(View.GONE);
                CardMap = scaled;
                storeImage(scaled, "Card");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == RESULT_CAMERA_IMAGE_CARD) {

            try {

                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUriCard);

                String imageurl = getRealPathFromURI(imageUriCard);
                Bitmap selectedImage = imageOreintationValidator(thumbnail, imageurl);
                //  imageLoaderCard.displayImage(String.valueOf(imageUriCard), imgCard, displayImageOptionsCard);
                profileCard.setImageBitmap(selectedImage);
                //
                rlCard.setVisibility(View.VISIBLE);
                imgCard.setVisibility(View.VISIBLE);
                txtCard.setVisibility(View.GONE);
                flFront.setVisibility(View.GONE);
                imgEditCard.setVisibility(View.VISIBLE);
                //  storeImage(bitmap,"Card");
                CardMap = selectedImage;

                isOnActivityResult = true;
                cardImgPath = String.valueOf(imageUriCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CARD && data != null) {
            if (data.getExtras().getString("Card").equals("Delete")) {
                rlCard.setVisibility(View.GONE);
                imgCard.setVisibility(View.GONE);
                txtCard.setVisibility(View.VISIBLE);
                flFront.setVisibility(View.VISIBLE);
                imgEditCard.setVisibility(View.GONE);
                cardpath = "";
            }
            //photoCard=null;
        }if (requestCode == REQUEST_RELATIONP && data != null) {
            String relation = data.getStringExtra("Category");
            txtRelation.setText(relation);
            if (relation.equals("Other")) {
                tilOtherRelation.setVisibility(View.VISIBLE);
                txtOtherRelation.setVisibility(View.VISIBLE);
            } else {
                tilOtherRelation.setVisibility(View.GONE);
                txtOtherRelation.setVisibility(View.GONE);
            }
        }else if (requestCode == REQUEST_LANGUAGE && data != null) {
            language = data.getStringExtra("Category");
            txtSpinLang.setText(language);
            if (language.equals("Other")) {
                tilOtherLanguage.setVisibility(View.VISIBLE);
                txtOtherRelation.setVisibility(View.VISIBLE);
            } else {
                tilOtherLanguage.setVisibility(View.GONE);
                txtOtherRelation.setVisibility(View.GONE);
            }
        }
        else if (requestCode == REQUEST_EYES && data != null) {
            eyes = data.getStringExtra("Category");
            txtSpinEye.setText(eyes);
        }
        else if (requestCode == REQUEST_MARITAL && data != null) {
            marital_status = data.getStringExtra("Category");
            txtSpinMarital.setText(marital_status);

        }

    }
    private void setPetData() {
        final ArrayList allergyList = new ArrayList();
        final ArrayList<Pet> AllargyLists = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (AllargyLists.size() != 0) {
            ListPet.setVisibility(View.VISIBLE);

            PetAdapter adapter=new PetAdapter(context,AllargyLists);
            ListPet.setAdapter(adapter);
            ListPet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Pet a = AllargyLists.get(position);
                    Intent allergyIntent = new Intent(context, AddPetActivity.class);
                    allergyIntent.putExtra("FROM", "Update");
                    allergyIntent.putExtra("PetObject", a);
                    startActivityForResult(allergyIntent, REQUEST_PET);

                }
            });

        } else {
            ListPet.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.chkChild:
                if (isChecked == true)
                    child = "YES";
                else
                    child = "NO";
                break;

            case R.id.chkSibling:
                if (isChecked == true)
                    sibling = "YES";
                else
                    sibling = "NO";
                break;

            case R.id.chkFriend:
                if (isChecked == true)
                    friend = "YES";
                else
                    friend = "NO";
                break;

            case R.id.chkGrandParent:
                if (isChecked == true)
                    grandParent = "YES";
                else
                    grandParent = "NO";
                break;

            case R.id.chkParent:
                if (isChecked == true)
                    parent = "YES";
                else
                    parent = "NO";
                break;
            case R.id.chkSpouse:
                if (isChecked == true)
                    spouse = "YES";
                else
                    spouse = "NO";
                break;

        }
    }

    private Bitmap imageOreintationValidator(Bitmap bitmap, String path) {

        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }

    private String getRealPathFromURI(Uri imageUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(imageUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void storeImage(Bitmap selectedImage, String profile) {
        FileOutputStream outStream = null;
        FileOutputStream outStream1 = null;
        File file = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
        File files = new File(Environment.getExternalStorageDirectory() + "/MYLO/Master/");
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        if (!files.exists()) {
            files.mkdirs();
        }

        try {
            if (selectedImage != null) {
                if (profile.equals("Profile")) {
                    imagepath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + imagepath);
                    outStream1 = new FileOutputStream(Environment.getExternalStorageDirectory() + "/MYLO/Master/" + imagepath);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                    byte[] byteArray = stream.toByteArray();
                    outStream1.write(byteArray);
                    outStream1.close();
                } else if (profile.equals("Card")) {
                    cardpath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + cardpath);
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public String parseResponses(String result) {

        JSONObject job = null;

        String errorCode = "";

        try {
            job = new JSONObject(result);

            JSONObject job1 = job.getJSONObject("response");

            errorCode = job1.getString("errorCode");

            String message = "";

            if (errorCode.equals("0")) {
                try {
                    message = job1.getString("respMsg");
                    Toast.makeText(context, "" + message, Toast.LENGTH_LONG)
                            .show();
                } catch (JSONException jop) {

                }

                return errorCode;
            } else {
                message = job1.getString("errorMsg");
                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                return errorCode;
            }

        } catch (JSONException e) {

            e.printStackTrace();
            return "Exception";
        }

    }


    class UpdateUserAsynk extends AsyncTask<Void, Void, String> {

        ProgressDialog pd;
        String name;
        String email;
        String password;
        private String userId = "";
        private String deviceType = "Android";

        public UpdateUserAsynk(String name, String email, String userId) {
            this.name = name;
            this.email = email;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(context, "", "Please Wait..");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            WebService webService = new WebService();

            Log.e("URL parameter", name + " " + "-" + " " + email
                    + " " + password + " " + " " + deviceType);
            String result = webService.editProfile(userId,
                    name, "", "", email, "");
            // String result = webService.getProfile("35");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }

            if (!result.equals("")) {

                if (result.equalsIgnoreCase("Exception")) {
                    Toast.makeText(context, "Unable to edit profile, Please try again later!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("CreateUserAsynk", result);

                    String errorCode = parseResponses(result);
                    //
                    if (errorCode.equals("0")) {

                        Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, email, address, phone, homePhone, workPhone, relation, imagepath, "", 1, 2, otherRelation, height, weight, eyes, profession, employed, language, marital_status, religion, veteran, idnumber, pet, manager_phone, cardpath, english, child, friend, grandParent, parent, spouse, other, liveOther, live, OtherLang, bdate, gender, sibling, has_card, people);
                        if (flag == true) {
                            DBHelper dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
                            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                            Boolean flags = MyConnectionsQuery.updateMyConnectionsData(1, name, email, address, phone, homePhone, workPhone, relation, imagepath, "", 1, 2, otherRelation, height, weight, eyes, profession, employed, language, marital_status, religion, veteran, idnumber, pet, manager_phone, cardpath, english, child, friend, grandParent, parent, spouse, other, liveOther, live, OtherLang, bdate, gender, sibling, has_card, people);
                            if (flags == true) {
                                Toast.makeText(context, "You have edited profile information successfully", Toast.LENGTH_SHORT).show();
                                connection.setName(name);
                                connection.setEmail(email);
                                String mail = email;
                                mail = mail.replace(".", "_");
                                mail = mail.replace("@", "_");
                                File oldFolder = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
                                File newFolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + mail + "/");
                                boolean success = oldFolder.renameTo(newFolder);
                                if (success) {
                                    preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                                }
                            }
                            //  Toast.makeText(context, "You have edited connection successfully", Toast.LENGTH_SHORT).show();
                            preferences.putString(PrefConstants.CONNECTED_NAME, name);
                            preferences.putString(PrefConstants.CONNECTED_RELATION,relation);
                            //   finish(); //Varsa
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        /*Toast.makeText(context, "Updation Failed, Try again",
                                Toast.LENGTH_LONG).show();*/
                    }
                }

            }
            super.onPostExecute(result);


        }

    }

    private void dialogCameraFront(final int resultCameraImage) {
        final Dialog dialogCamera = new Dialog(context);
        dialogCamera.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCamera.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_camera_ins, null);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);


        dialogCamera.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogCamera.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogCamera.getWindow().setAttributes(lp);
        dialogCamera.setCanceledOnTouchOutside(false);
        dialogCamera.show();


        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                imageUriCard = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                // intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriCard);


                startActivityForResult(intent, resultCameraImage);
                dialogCamera.dismiss();
            }
        });
    }
/*
    public void loadImageFromFile() {

        ImageView view = (ImageView) this.findViewById(R.id.imgProfile);
        view.setVisibility(View.VISIBLE);


        int targetW = view.getWidth();
        int targetH = view.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        final Uri imageUri = data.getData();
        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        // profileImage.setImageBitmap(selectedImage);
//                imageLoaderProfile.displayImage(String.valueOf(imageUri), imgProfile, displayImageOptionsProfile);
        // storeImage(selectedImage,"Profile");

        int nh = (int) (selectedImage.getHeight() * (512.0 / selectedImage.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(selectedImage, 512, nh, true);
        imgProfile.setImageBitmap(scaled);


        Bitmap bmp = BitmapFactory.decodeFile(fileName, bmOptions);
        view.setImageBitmap(bmp);
        imgProfile.setImageBitmap(bmp);

     //   imgProfile = bmp;


    }
*/
}
