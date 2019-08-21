package com.mindyourlovedone.healthcare.HomeActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.PersonalInfoQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.NetworkUtils;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.webservice.WebService;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class ImpAgreementActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack,checkedIcon1,uncheckedIcon1,checkedIcon2,uncheckedIcon2,checkedIcon3,uncheckedIcon3,checkedIcon4,uncheckedIcon4,checkedIcon5,uncheckedIcon5;
    TextView txtSignup;
    Context context = this;
    String name="", email="";
    private static final int REQUEST_CALL_PERMISSION = 100;
    Preferences preferences;
    DBHelper dbHelper;

    boolean ck=false,ck2=false,ck3=false,Ck4=false,Ck5=false;
    String has_card="NO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_agreement);
        initUi();
        Intent i=getIntent();
        name=i.getStringExtra("Name");
        email=i.getStringExtra("Email");
     //   initComponent();
        initListener();
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

    private void initListener() {
        imgBack.setOnClickListener(this);
        txtSignup.setOnClickListener(this);

        checkedIcon1.setOnClickListener(this);
        uncheckedIcon1.setOnClickListener(this);

        checkedIcon2.setOnClickListener(this);
        uncheckedIcon2.setOnClickListener(this);

        checkedIcon3.setOnClickListener(this);
        uncheckedIcon3.setOnClickListener(this);

        checkedIcon4.setOnClickListener(this);
        uncheckedIcon4.setOnClickListener(this);

        checkedIcon5.setOnClickListener(this);
        uncheckedIcon5.setOnClickListener(this);

    }

    private void initUi() {
        imgBack = findViewById(R.id.imgBack);
        txtSignup = findViewById(R.id.txtSignup);
        checkedIcon1= findViewById(R.id.checkedIcon1);
        uncheckedIcon1=findViewById(R.id.uncheckedIcon1);

        checkedIcon2= findViewById(R.id.checkedIcon2);
        uncheckedIcon2=findViewById(R.id.uncheckedIcon2);
        checkedIcon3= findViewById(R.id.checkedIcon3);
        uncheckedIcon3=findViewById(R.id.uncheckedIcon3);
        checkedIcon4= findViewById(R.id.checkedIcon4);
        uncheckedIcon4=findViewById(R.id.uncheckedIcon4);
        checkedIcon5= findViewById(R.id.checkedIcon5);
        uncheckedIcon5=findViewById(R.id.uncheckedIcon5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;

            case R.id.txtSignup:
              //  if (validate()) {
                    //CallWebservice get user profile


                    if ( validation())
                    {
                       // preferences.putInt(PrefConstants.USER_ID, userid);
                        Intent signupIntent = new Intent(context, BaseActivity.class);
                        /*preferences.putString(PrefConstants.USER_EMAIL, email);
                        preferences.putString(PrefConstants.USER_NAME, name);
                        preferences.setREGISTERED(true);
                        preferences.setLogin(true);
                        if(getIntent().hasExtra("PDF_EXT")) {
                            signupIntent.putExtra("PDF_EXT", getIntent().getStringExtra("PDF_EXT"));
                        }*/
                        startActivity(signupIntent);
                        finish();
                        //accessPermission();
                    }
                   else {
                     /*   Toast toast = Toast.makeText(context, Html.fromHtml("<big><b>Click to Accept</b></big>"), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/
                    }
              //  }
               /* Intent intentBase = new Intent(context, BaseActivity.class);
                startActivity(intentBase);*/
                break;


            case R.id.checkedIcon1:
                checkedIcon1.setVisibility(View.GONE);
                ck=false;
                uncheckedIcon1.setVisibility(View.VISIBLE);
                break;
            case R.id.uncheckedIcon1:
                uncheckedIcon1.setVisibility(View.GONE);
                ck=true;
                checkedIcon1.setVisibility(View.VISIBLE);
                break;

            case R.id.checkedIcon2:
                checkedIcon2.setVisibility(View.GONE);
                ck2=false;
                uncheckedIcon2.setVisibility(View.VISIBLE);

                break;
            case R.id.uncheckedIcon2:
                uncheckedIcon2.setVisibility(View.GONE);
                ck2=true;
                checkedIcon2.setVisibility(View.VISIBLE);
                break;

            case R.id.checkedIcon3:
                checkedIcon3.setVisibility(View.GONE);
                ck3=false;
                uncheckedIcon3.setVisibility(View.VISIBLE);
                break;
            case R.id.uncheckedIcon3:
                uncheckedIcon3.setVisibility(View.GONE);
                ck3=true;
                checkedIcon3.setVisibility(View.VISIBLE);
                break;

            case R.id.checkedIcon4:
                checkedIcon4.setVisibility(View.GONE);
                Ck4=false;
                uncheckedIcon4.setVisibility(View.VISIBLE);
                break;
            case R.id.uncheckedIcon4:
                uncheckedIcon4.setVisibility(View.GONE);
                Ck4=true;
                checkedIcon4.setVisibility(View.VISIBLE);
                break;

            case R.id.checkedIcon5:
                checkedIcon5.setVisibility(View.GONE);
                Ck5=false;
                uncheckedIcon5.setVisibility(View.VISIBLE);
                break;
            case R.id.uncheckedIcon5:
                uncheckedIcon5.setVisibility(View.GONE);
                Ck5=true;
                checkedIcon5.setVisibility(View.VISIBLE);
                break;

        }
    }

    private boolean validation() {
        boolean f=false;

         if (ck==true&&ck2==true&&ck3==true&&Ck4==true&&Ck5==true)
         {
             f=true; }
         else {
             Toast toast = Toast.makeText(context, Html.fromHtml("<big><b>Click to Accept</b></big>"), Toast.LENGTH_SHORT);
             toast.setGravity(Gravity.CENTER, 0, 0);
             toast.show();
         }

     return f;
    }


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
                ||
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS
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

            if (!NetworkUtils.getConnectivityStatusString(ImpAgreementActivity.this).equals("Not connected to Internet")) {
                CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
                asynkTask.execute();
            } else {
                DialogManager.showAlert("Network Error, Check your internet connection", ImpAgreementActivity.this);
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
                    if (!NetworkUtils.getConnectivityStatusString(ImpAgreementActivity.this).equals("Not connected to Internet")) {
                        CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
                        asynkTask.execute();
                    } else {
                        DialogManager.showAlert("Network Error, Check your internet connection", ImpAgreementActivity.this);
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

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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

        private String parseResponse(String result) {
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
                        Boolean flag = MyConnectionsQuery.insertMyConnectionsData(userid, name, email, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);

                        PersonalInfoQuery pi = new PersonalInfoQuery(context, dbHelper);
                        Boolean flagPersonalinfo = PersonalInfoQuery.insertPersonalInfoData(name, email, "", "", "", "", "", "", "", "", "");
                        if (flag == true) {
                    File file = new File(Environment.getExternalStorageDirectory(),
                            "/MYLO/");
                            String path = file.getAbsolutePath();
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
                            String mail = connection.getEmail();
                            mail = mail.replace(".", "_");
                            mail = mail.replace("@", "_");
                            DBHelper dbHelper = new DBHelper(context, mail);
                            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                            Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getId(), name, email, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);
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

    }
   /* private boolean validate() {
        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            DialogManager.showAlert("Please Enter email", context);
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            DialogManager.showAlert("Please enter valid email", context);
        }else {
            return true;
//        }
        return false;
    }*/
}
