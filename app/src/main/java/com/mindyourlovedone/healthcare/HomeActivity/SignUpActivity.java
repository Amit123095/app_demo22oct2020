package com.mindyourlovedone.healthcare.HomeActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.PersonalInfoQuery;
import com.mindyourlovedone.healthcare.database.SubscriptionQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.SubscrptionData;
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

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout tilName;
    TextView txtName, txtNext, txtEmail, txtPolicy2, txtPolicy4;
    ImageView imgBack;
    Context context = this;
    String name = "", email = "";
    boolean allow = false;
    private static final int REQUEST_CALL_PERMISSION = 100;
    String has_card = "NO";
    /*  private static final int REQUEST_CALL_PERMISSION = 100;
      private static int RESULT_CAMERA_IMAGE = 1;
      private static int RESULT_SELECT_PHOTO = 2;
      Context context = this;
      RelativeLayout llSignUp;
      TextView txtSignUp, txtLogin, txtForgotPassword,, txtPolicy5;
      ImageView imgBack, imgEdit, imgProfile;
      TextView txtName, txtEmail, txtAddress, txtCountry, txtPhone, txtBdate, txtPassword, txtGender, txtHomePhone,txtname,txtOnly ,txtPolicy61, txtPolicy62, txtPolicy63;
      TextInputLayout tilName;
      String name = "", email = "", mobile = "", country = "", bdate = "", password = "", address = "", phone = "";
      Uri imageUriProfile = null;
      ContentValues values = null;
      MySpinner spinner;
      String[] countryList = {"Canada", "Mexico", "USA", "UK", "california", "India"};
      String imagepath = "";//

      ImageLoader imageLoader;
      DisplayImageOptions displayImageOptions;txtPolicy2

      Preferences preferences;
      DBHelper dbHelper;

      int userid = 1;*/
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout rlTops;
    CheckBox rbCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0x00000000);  // transparent
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.addFlags(flags);
        }
        setContentView(R.layout.activity_sign_up);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        initUI();
        initListener();
        initComponent();
      /*
        initComponent();
        initListener();
        initImageLoader();*/
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        txtNext.setOnClickListener(this);

    }

    private void initComponent() {

        try {
            File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
            if (!f.exists()) {
                f.mkdirs();
            } else {
                try {
                    File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                    FileUtils.deleteDirectory(file);
                    f.mkdirs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, "MASTER");
        // PersonalInfoQuery s=new PersonalInfoQuery(context,dbHelper);
        MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
    }

    private void initUI() {
        tilName = findViewById(R.id.tilName);
        txtName = findViewById(R.id.txtName);
        txtNext = findViewById(R.id.txtNext);
        rbCheck = findViewById(R.id.rbCheck);
        txtEmail = findViewById(R.id.txtEmail);
        imgBack = findViewById(R.id.imgBack);
        txtPolicy2 = findViewById(R.id.txtPolicy2);
        txtPolicy4 = findViewById(R.id.txtPolicy4);
        rbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allow = true;
                } else {
                    allow = false;
                }
            }
        });
        // rlTops= findViewById(R.id.rlTops);
       /* rlTops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                txtName.setFocusable(false);
                txtEmail.setFocusable(false);
            }
        });*/

        RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.rlTops);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (txtName.isFocused()) {
                        Rect outRect = new Rect();
                        txtName.getGlobalVisibleRect(outRect);
//                        txtName.setHintTextColor(R.color.colorGray);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            txtName.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                    if (txtEmail.isFocused()) {
                        Rect outRect = new Rect();
                        txtEmail.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            txtEmail.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);
                return false;
            }
        });

        txtPolicy2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                CopyReadAssetss("Privacy Policy.pdf");
                return false;
            }
        });

        txtPolicy4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CopyReadAssetss("eula_new.pdf");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtNext:
                name = txtName.getText().toString();
                email = txtEmail.getText().toString();
                if (validate()) {
                   /* Intent intentNext = new Intent(context, ImpAgreementActivity.class);
                    intentNext.putExtra("Name", name);
                    intentNext.putExtra("Email", email);
                    startActivity(intentNext);*/
//                    accessPermission();

                    if (!NetworkUtils.getConnectivityStatusString(SignUpActivity.this).equals("Not connected to Internet")) {
                        CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
                        asynkTask.execute();
                    } else {
                        DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
                    }

                } else {
//                    Toast toast = Toast.makeText(context, Html.fromHtml("<big><b>Click to Accept</b></big>"), Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                }
                break;
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void accessPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                ContextCompat.checkSelfPermission(getApplicationContext(),
//                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
//                ||
//                ContextCompat.checkSelfPermission(getApplicationContext(),
//                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                ||
//                ContextCompat.checkSelfPermission(getApplicationContext(),
//                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                ||
//                ContextCompat.checkSelfPermission(getApplicationContext(),
//                        android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_CONTACTS
//            }, REQUEST_CALL_PERMISSION);
//
//        } else {
//
//            try {
//                File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
//                if (!f.exists()) {
//                    f.mkdirs();
//                } else {
//                    try {
//                        File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
//                        FileUtils.deleteDirectory(file);
//                        f.mkdirs();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
////            if (!NetworkUtils.getConnectivityStatusString(SignUpActivity.this).equals("Not connected to Internet")) {
//////                CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
//////                asynkTask.execute();
//////            } else {
//////                DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
//////            }
//        }
//    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CALL_PERMISSION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    if (!NetworkUtils.getConnectivityStatusString(SignUpActivity.this).equals("Not connected to Internet")) {
//                        CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
//                        asynkTask.execute();
//                    } else {
//                        DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
//                    }
//                    //  checkForRegistration();
//                    try {
//                        File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
//                        if (!f.exists()) {
//                            f.mkdirs();
//                        } else {
//                            try {
//                                File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
//                                FileUtils.deleteDirectory(file);
//                                f.mkdirs();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//
//                    accessPermission();
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'switch' lines to check for other
//            // permissions this app might request
//        }
//    }

    private boolean validate() {
        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            DialogManager.showAlert("Please Enter email", context);
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            DialogManager.showAlert("Please enter valid email", context);
        } else if (allow == false) {

            Toast toast = Toast.makeText(context, Html.fromHtml("<big><b>Click to Accept</b></big>"), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            return true;
        }
        return false;
    }

    /*
        private void initComponent() {
            try {
                File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
                if (!f.exists()) {
                    f.mkdirs();
                } else {
                    try {
                        File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                        FileUtils.deleteDirectory(file);
                        f.mkdirs();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            preferences = new Preferences(context);
            dbHelper = new DBHelper(context, "MASTER");
            // PersonalInfoQuery s=new PersonalInfoQuery(context,dbHelper);
            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
        }

        private void initImageLoader() {
            displayImageOptions = new DisplayImageOptions.Builder() // resource
                    .resetViewBeforeLoading(true) // default
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    // .showImageOnLoading(R.drawable.images)
                    .considerExifParams(false) // default
    //                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                    .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                    .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                    .handler(new Handler()) // default
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(displayImageOptions)
                    .build();
            ImageLoader.getInstance().init(config);
            imageLoader = ImageLoader.getInstance();
        }

        private void initListener() {
            txtSignUp.setOnClickListener(this);
            txtLogin.setOnClickListener(this);
            txtForgotPassword.setOnClickListener(this);
            imgBack.setOnClickListener(this);
            txtBdate.setOnClickListener(this);
            imgEdit.setOnClickListener(this);
            txtPolicy2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    CopyReadAssetss("Privacy Policy.pdf");
                    return false;
                }
            });

            txtPolicy4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    CopyReadAssetss("eula_new.pdf");
                    return false;
                }
            });
        }
    */
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

    /*
        private void initUI() {
            String s = getResources().getString(R.string.signup_appname);
            TextView textlogo = findViewById(R.id.txtPolicy1);
            textlogo.setText(Html.fromHtml(s));

            llSignUp = findViewById(R.id.llSignUp);
            imgProfile = findViewById(R.id.imgProfile);
            txtSignUp = findViewById(R.id.txtSignUp);
            txtLogin = findViewById(R.id.txtLogin);
            txtForgotPassword = findViewById(R.id.txtForgotPassword);
            txtBdate = findViewById(R.id.txtBdate);
            imgBack = findViewById(R.id.imgBack);
            imgEdit = findViewById(R.id.imgEdit);

            txtPolicy61 = findViewById(R.id.txtPolicy61);
            txtPolicy61.setText(Html.fromHtml(getString(R.string.signupt1)));

            txtPolicy62 = findViewById(R.id.txtPolicy62);
            txtPolicy62.setText(Html.fromHtml(getString(R.string.signupt2)));
           *//* txtPolicy63 = findViewById(R.id.txtPolicy63);
        txtPolicy63.setText(Html.fromHtml(getString(R.string.signupt3)));*//*

     *//*  txtOnly=findViewById(R.id.txtOnly);
        txtOnly.setText(Html.fromHtml(getString(R.string.signupt4)));

        txtname=findViewById(R.id.txtname);
        txtname.setText(Html.fromHtml(getString(R.string.signupt5)));
*//*
        tilName = findViewById(R.id.tilName);
        txtAddress = findViewById(R.id.txtAddress);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtCountry = findViewById(R.id.txtCountry);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        txtPolicy2 = findViewById(R.id.txtPolicy2);
        txtPolicy4 = findViewById(R.id.txtPolicy4);
        txtPolicy5 = findViewById(R.id.txtPolicy5);
        txtPolicy2.setClickable(true);
        txtPolicy2.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a> <font color='orange'><u>Privacy Policy</u></a></font>";
        txtPolicy2.setText(Html.fromHtml(text));

        txtPolicy4.setClickable(true);
        txtPolicy4.setMovementMethod(LinkMovementMethod.getInstance());
        String text1 = "<a><u>End User License Agreement</u></a>";
        txtPolicy4.setText(Html.fromHtml(text1));
//unbold all info and privacy
        String texts = " <a> All information </a>  on this app resides on your smartphone or tablet.<b> HIPAA </b>federal privacy rules ";
        String texts2 = "  <a> do not apply </a>.";
        txtPolicy5.setText(Html.fromHtml(texts + texts2));
        String data = txtPolicy5.getText().toString();

       *//* txtPolicy5.setMovementMethod(LinkMovementMethod.getInstance());
        txtPolicy5.setText(addClickablePart(data), TextView.BufferType.SPANNABLE);*//*

        spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Country");



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
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);
                return false;
            }
        });
    }

    private SpannableStringBuilder addClickablePart(String str) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        int idx1 = 0;
        int idx2 = 14;
       *//* while (idx1 != -1) {
            idx2 = str.indexOf("]", idx1) + 1;
*//*
        final String clickString = str.substring(idx1, idx2);
        ssb.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                Toast.makeText(context, clickString,
                        Toast.LENGTH_SHORT).show();
            }
        }, idx1, idx2, 0);
        idx1 = str.indexOf("[", idx2);
        // }

        return ssb;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtSignUp:
                if (validate()) {
                    //CallWebservice get user profile
                    accessPermission();
                }

                   *//* Boolean flags = PersonalInfoQuery.searchEmailAvailability(email);
                    if (flags == true) {
                        Toast.makeText(context, "This email is already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] photo = baos.toByteArray();*//**//**//**//*
                      Log.v("Path",imagepath);
                        Boolean flag = PersonalInfoQuery.insertPersonalInfoData(name, email, address, country, mobile, bdate, password, imagepath,"","","");
                        if (flag == true) {
                            Toast.makeText(context, "You have registered successfully", Toast.LENGTH_SHORT).show();
                            Intent signupIntent = new Intent(context, BaseActivity.class);
                            preferences.putString(PrefConstants.USER_EMAIL, email);

                            PersonalInfo personalInfo=PersonalInfoQuery.fetchProfile(email);
                            preferences.putInt(PrefConstants.USER_ID, personalInfo.getId());
                            preferences.putString(PrefConstants.USER_NAME, personalInfo.getName());
                          //  String saveThis = Base64.encodeToString( personalInfo.getPhoto(), Base64.DEFAULT);
                            preferences.putString(PrefConstants.USER_PROFILEIMAGE, personalInfo.getPhoto());
                            preferences.setREGISTERED(true);
                            preferences.setLogin(true);
                            startActivity(signupIntent);

                            SplashNewActivity s=new SplashNewActivity();
                            s.finish();
                            saveToConnection(personalInfo.getId());
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }*//*

                // }

                break;

            case R.id.txtLogin:
                Intent signinIntent = new Intent(context, LoginActivity.class);
                startActivity(signinIntent);
                finish();
                break;

            case R.id.txtForgotPassword:
                // if (validate()) {
                    *//*CreateUserAsynk asynkTask = new CreateUserAsynk(name, email, password);
                    asynkTask.execute();*//*
     *//*
                    UpdateUserAsynk asynkTask = new UpdateUserAsynk(name, email, password);
                    asynkTask.execute();*//*


                //GetUserAsynk asynkTask = new GetUserAsynk();
                //asynkTask.execute();
                //  }
                break;

            case R.id.imgBack:
                finish();
                break;

            case R.id.imgEdit:
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
                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
                lp.width = width;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                textOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //    dispatchTakePictureIntent();
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        imageUriProfile = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        //  intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriProfile);

                        startActivityForResult(intent, RESULT_CAMERA_IMAGE);
                        dialog.dismiss();

                    }
                });
                textOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_SELECT_PHOTO);
                        dialog.dismiss();
                    }
                });
                textOption3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                        imagepath = "";
                        dialog.dismiss();
                    }
                });
                textCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                break;

            case R.id.txtBdate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;


           *//* case R.id.imgGoogleSignup:

                break;*//*
        }
    }


    private void saveToConnection(int id) {
       *//* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] photo = baos.toByteArray();*//*
        Boolean flag = MyConnectionsQuery.insertMyConnectionsData(id, name, email, address, mobile, " ", "", "Self", imagepath, " ", 1, 2, "", "");
        if (flag == true) {
            Toast.makeText(context, "You have added connection successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               *//* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.infidigi.fotobuddies.fileprovider",
                        photoFile);*//*
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagepath = image.getAbsolutePath();
        return image;
    }

    private boolean validate() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        //   mobile = txtPhone.getText().toString().trim();
        //      bdate = txtBdate.getText().toString().trim();
//        country=spinner.getSelectedItem().toString();
        // password = txtPassword.getText().toString().trim();
        //  address=txtAddress.getText().toString().trim();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            DialogManager.showAlert("Please Enter email", context);
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            DialogManager.showAlert("Please enter valid email", context);
        }
      *//*  else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address", context);
        }
        else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country", context);
        } else if (mobile.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone", context);
        } else if (mobile.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits", context);
        } else if (bdate.equals("")) {
            txtBdate.setError("Please Enter Birth date");
            showAlert("Please Enter Birth date", context);
        }*//* *//*else if (password.equals("")) {
            txtPassword.setError("Please Enter Password");
            showAlert("Please Enter Password", context);
        } else if (password.length() < 6) {
            txtPassword.setError("Password should be minimum 6 characters");
            showAlert("Password should be minimum 6 characters", context);
        } *//*
        else {
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = findViewById(R.id.imgProfile);
        if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileImage.setImageBitmap(selectedImage);
                storeImage(selectedImage, "Profile");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUriProfile);
                //  String imageurl = getRealPathFromURI(imageUriProfile);
                // Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
                profileImage.setImageBitmap(thumbnail);
                storeImage(thumbnail, "Profile");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // ImageView profileImage = (ImageView) findViewById(R.id.imgProfile);
           *//* Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgProfile.setImageBitmap(imageBitmap);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MYLO/");
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                file.mkdirs();
            }
            try {

                imagepath = path + "/MYLO_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
*//*
        }

    }

    private void storeImage(Bitmap selectedImage, String profile) {

        FileOutputStream outStream = null;
        File file = new File(Environment.getExternalStorageDirectory(),
                "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB));
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        try {

            imagepath = path + "/MYLO_" + String.valueOf(System.currentTimeMillis())
                    + ".jpg";
            // Write to SD Card
            outStream = new FileOutputStream(imagepath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
            byte[] byteArray = stream.toByteArray();
            outStream.write(byteArray);
            outStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public String parseResponse(String result) {
        Log.e("Response", result);
        JSONObject job = null;
        String errorCode = "";
        try {
            job = new JSONObject(result);
            JSONObject job1 = job.getJSONObject("response");
            errorCode = job1.getString("errorCode");
            String message = "";
            if (errorCode.equals("0")) {
                message = job1.getString("respMsg");
                JSONObject job2 = job1.getJSONObject("respData");
                String userId = job2.getString("user_id");
                Log.e("SuccessFullRegisterd", "UserId= " + userId);
                int userid = Integer.parseInt(userId);
                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();

                //After Success
                Boolean flag = MyConnectionsQuery.insertMyConnectionsData(userid, name, email, "", "", "", "", "Self", "", "", 1, 2, "", "");

                PersonalInfoQuery pi = new PersonalInfoQuery(context, dbHelper);
                Boolean flagPersonalinfo = PersonalInfoQuery.insertPersonalInfoData(name, email, "", "", "", "", "", "", "", "", "");
                if (flag == true) {
                   *//* File file = new File(Environment.getExternalStorageDirectory(),
                            "/MYLO/");
                    String path = file.getAbsolutePath();
                    if (!file.exists()) {
                        file.mkdirs();
                    }*//*
                    RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
                    String mail = connection.getEmail();
                    mail = mail.replace(".", "_");
                    mail = mail.replace("@", "_");
                    DBHelper dbHelper = new DBHelper(context, mail);
                    MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                    Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getId(), name, email, "", "", "", "", "Self", "", "", 1, 2, "", "");
                    if (flags == true) {
                        // Toast.makeText(context, "You have created db successfully", Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(context,"You have added profile successfully",Toast.LENGTH_SHORT).show();
                    preferences.putInt(PrefConstants.USER_ID, userid);
                    Intent signupIntent = new Intent(context, BaseActivity.class);
                    preferences.putString(PrefConstants.USER_EMAIL, email);
                    preferences.putString(PrefConstants.USER_NAME, name);
                    preferences.setREGISTERED(true);
                    preferences.setLogin(true);
                    if(getIntent().hasExtra("PDF_EXT")) {
                        signupIntent.putExtra("PDF_EXT", getIntent().getStringExtra("PDF_EXT"));
                    }
                    startActivity(signupIntent);
                    finish();
                } else {
                    Toast.makeText(context, "Error to save in database", Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CALL_PERMISSION);

        } else {

            try {
                File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
                if (!f.exists()) {
                    f.mkdirs();
                } else {
                    try {
                        File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                        FileUtils.deleteDirectory(file);
                        f.mkdirs();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!NetworkUtils.getConnectivityStatusString(SignUpActivity.this).equals("Not connected to Internet")) {
                CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
                asynkTask.execute();
            } else {
                DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: {
                if (grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (!NetworkUtils.getConnectivityStatusString(SignUpActivity.this).equals("Not connected to Internet")) {
                        CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
                        asynkTask.execute();
                    } else {
                        DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
                    }
                    //  checkForRegistration();
                    try {
                        File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
                        if (!f.exists()) {
                            f.mkdirs();
                        } else {
                            try {
                                File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                                FileUtils.deleteDirectory(file);
                                f.mkdirs();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    accessPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }



    class CreateUserAsynk extends AsyncTask<Void, Void, String> {
        String name;
        String email;
        ProgressDialog pd;

        private String deviceUdId = "";
        private String deviceType = "Android";

        public CreateUserAsynk(String name, String email) {
            this.name = name;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            deviceUdId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            pd = ProgressDialog.show(context, "", "Please Wait..");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            WebService webService = new WebService();
            Log.e("URL parameter", name + "" + "" + " " + email
                    + " " + "" + " " + deviceUdId + " " + deviceType);
            String result = webService.createProfile(name, "-",
                    "-", email, "-", deviceUdId, deviceType);
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
                if (result.equals("Exception")) {
                    // ErrorDialog.errorDialog(context);
                    DialogManager.showAlert("Error", context);
                } else {
                    Log.e("CreateUserAsynk", result);
                    String errorCode = parseResponse(result);
                    if (errorCode.equals("0")) {
                        // DialogManager.showAlert("000", context);
                    } else {
                        //Toast.makeText(context, "Registration Failed, Try again", Toast.LENGTH_LONG).show();
                    }
                }
            }
            super.onPostExecute(result);
        }

    }*/
    class CreateUserAsynk extends AsyncTask<Void, Void, String> {
        String name;
        String email;
        ProgressDialog pd;

        private String deviceUdId = "";
        private String deviceType = "Android";

        public CreateUserAsynk(String name, String email) {
            this.name = name;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            deviceUdId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            pd = ProgressDialog.show(context, "", "Please Wait..");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            WebService webService = new WebService();
            Log.e("URL parameter", name + "" + "" + " " + email
                    + " " + "" + " " + deviceUdId + " " + deviceType);
            String result = webService.createProfile(name, "-",
                    "-", email, "-", deviceUdId, deviceType);
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
                if (result.equals("Exception")) {
                    // ErrorDialog.errorDialog(context);
                    DialogManager.showAlert("Error", context);
                } else {
                    Log.e("CreateUserAsynk", result);
                    parseResponse(result);

                }
            }
            super.onPostExecute(result);
        }

        private void navigateToApp(int userid) {
            //Nikita#Sub - redirecting to agreement screen if payment successful then only saving data
            Intent signupIntent = new Intent(context, ImpAgreementActivity.class);
            if (getIntent().hasExtra("PDF_EXT")) {
                signupIntent.putExtra("PDF_EXT", getIntent().getStringExtra("PDF_EXT"));
            }
            signupIntent.putExtra("userid", userid);
            signupIntent.putExtra("Name", name);
            signupIntent.putExtra("Email", email);
            startActivity(signupIntent);
            finish();


            //old code
            //After Success

//            Boolean flag = MyConnectionsQuery.insertMyConnectionsData(userid, name, email, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);
//
//            PersonalInfoQuery pi = new PersonalInfoQuery(context, dbHelper);
//            Boolean flagPersonalinfo = PersonalInfoQuery.insertPersonalInfoData(name, email, "", "", "", "", "", "", "", "", "");
//            if (flag == true) {
//                File file = new File(Environment.getExternalStorageDirectory(),
//                        "/MYLO/");
//                String path = file.getAbsolutePath();
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
//                String mail = connection.getEmail();
//                mail = mail.replace(".", "_");
//                mail = mail.replace("@", "_");
//                DBHelper dbHelper = new DBHelper(context, mail);
//                MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
//                Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getId(), name, email, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);
//                if (flags == true) {
//                    // Toast.makeText(context, "You have created db Successfully", Toast.LENGTH_SHORT).show();
//                }
//                //  Toast.makeText(context,"You have added profile Successfully",Toast.LENGTH_SHORT).show();
//            preferences.putInt(PrefConstants.USER_ID, userid);
//
//            preferences.putString(PrefConstants.USER_EMAIL, email);
//            preferences.putString(PrefConstants.USER_NAME, name);
//            preferences.setREGISTERED(true);
//            preferences.setLogin(true);
//            Intent signupIntent = new Intent(context, ImpAgreementActivity.class);
//            if (getIntent().hasExtra("PDF_EXT")) {
//                signupIntent.putExtra("PDF_EXT", getIntent().getStringExtra("PDF_EXT"));
//            }
//            signupIntent.putExtra("userid", userid);
//            signupIntent.putExtra("Name", name);
//            signupIntent.putExtra("Email", email);
//            startActivity(signupIntent);
//            finish();
//        } else
//
//        {
//            Toast.makeText(context, "Error to save in database", Toast.LENGTH_SHORT).show();
//        }

        }

        private void navigateToAPPSUB(int userid, SubscrptionData sub) {
            Toast.makeText(context, "Already registered user with active subscription", Toast.LENGTH_LONG).show();
            //After Success
            SubscriptionQuery ss = new SubscriptionQuery(context, dbHelper);
            Boolean ssflag = SubscriptionQuery.insertSubscriptionData(userid, sub);

            Boolean flag = MyConnectionsQuery.insertMyConnectionsData(userid, name, email, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);

            PersonalInfoQuery pi = new PersonalInfoQuery(context, dbHelper);
            Boolean flagPersonalinfo = PersonalInfoQuery.insertPersonalInfoData(name, email, "", "", "", "", "", "", "", "", "");
            if (flag == true) {
                RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
                String mail = connection.getEmail();
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");
                DBHelper dbHelper = new DBHelper(context, mail);
                MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getId(), name, email, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);
                if (flags == true) {
                    // Toast.makeText(context, "You have created db Successfully", Toast.LENGTH_SHORT).show();
                }
                //  Toast.makeText(context,"You have added profile Successfully",Toast.LENGTH_SHORT).show();
                preferences.putInt(PrefConstants.USER_ID, userid);
                preferences.putString(PrefConstants.USER_EMAIL, email);
                preferences.putString(PrefConstants.USER_NAME, name);
                preferences.setREGISTERED(true);
                preferences.setLogin(true);

                Intent signupIntent = new Intent(context, BaseActivity.class);
                if (getIntent().hasExtra("PDF_EXT")) {
                    signupIntent.putExtra("PDF_EXT", getIntent().getStringExtra("PDF_EXT"));
                }

                startActivity(signupIntent);
                finish();

            } else {
                Toast.makeText(context, "Error to save in database", Toast.LENGTH_SHORT).show();
            }
        }

        private boolean validDateChecker(String date) {
            // Nikita#Sub - Checking for current date is before expiry date or not
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date strDate = sdf.parse(date);

                if (strDate.after(new Date())) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        private void parseResponse(String result) {
            Log.e("Response", result);
            JSONObject job = null;
            String errorCode = "";
            try {

                try {
                    File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
                    if (!f.exists()) {
                        f.mkdirs();
                    } else {
                        try {
                            File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                            FileUtils.deleteDirectory(file);
                            f.mkdirs();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                job = new JSONObject(result);
                JSONObject jobB = job.optJSONObject("response");
                errorCode = jobB.optString("errorCode");
                String message = "";

                if (errorCode.equals("0")) {
                    message = jobB.optString("respMsg");
                    JSONObject job2 = jobB.optJSONObject("respData");
                    String userId = job2.optString("user_id");
                    int userid = Integer.parseInt(userId);

                    Log.e("SuccessFullRegisterd", "UserId= " + userId);
                    Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();

                    navigateToApp(userid);

                } else if (errorCode.equals("1")) {
                    // Nikita#Sub - code for existing user (if found) starts here...
                    message = jobB.optString("errorMsg");

                    boolean iserror = false;

                    JSONObject jobS = jobB.optJSONObject("subscription");

                    if (jobS != null) {
                        // User has subscription data on server
                        String transId = jobS.optString("transaction_id");
                        String startDate = jobS.optString("start_date");
                        String endDate = jobS.optString("end_date");
                        String source = jobS.optString("source");
                        String user_id = jobS.optString("user_id");

                        if (!transId.isEmpty() && !endDate.isEmpty()) {
                            if (validDateChecker(endDate)) {
                                // User has valid subscription data on server
                                String userId = jobS.optString("user_id");
                                int userid = Integer.parseInt(userId);

                                Log.e("Success", "UserId= " + userId);
                                SubscrptionData sub = new SubscrptionData();
                                sub.setSource(source);
                                sub.setEndDate(endDate);
                                sub.setStartDate(startDate);
                                sub.setTransactionID(transId);
                                sub.setUserId(Integer.parseInt(user_id));
                                sub.setEmail(email);

                                preferences.putInt(PrefConstants.UPLOAD_FLAG, 1);

                                navigateToAPPSUB(userid, sub); // Entry to App
                            } else {
                                // User has invalid subscription data on server
                                iserror = true;
                            }
                        } else {
                            // User doesn't have subscription data on server
                            iserror = true;
                        }
                    } else {
                        // User doesn't have subscription data on server
                        iserror = true;
                    }

                    Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();

                    //navigating to login - if user is existing without subscription
                    if (iserror && message.contains("Existing")) {

                        Intent in = new Intent(SignUpActivity.this, LoginActivity.class);
                        in.putExtra("name", name);
                        in.putExtra("email", email);
                        in.putExtra("from", "signup");
                        startActivity(in);
                        finish();
                    }
                    // Nikita#Sub - code for existing user (if found) ends here...

                } else {
                    Toast.makeText(context, "Unexpected error from server.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(context, "Exception detected : " + (e.getCause()), Toast.LENGTH_LONG).show();
            }

        }

    }
}

