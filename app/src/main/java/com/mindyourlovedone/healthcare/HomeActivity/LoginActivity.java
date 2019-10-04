package com.mindyourlovedone.healthcare.HomeActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.PersonalInfoQuery;
import com.mindyourlovedone.healthcare.database.SubscriptionQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.SubscrptionData;
import com.mindyourlovedone.healthcare.util.IabHelper;
import com.mindyourlovedone.healthcare.util.IabResult;
import com.mindyourlovedone.healthcare.util.Inventory;
import com.mindyourlovedone.healthcare.util.Purchase;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.NetworkUtils;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.utility.WorkerPost;
import com.mindyourlovedone.healthcare.webservice.WebService;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CALL_PERMISSION = 100;
    Context context = this;
    RelativeLayout rlLogin;
    TextView txtSignIn, txtNew, txtForgotPassword;
    ImageView imgFbSignup, imgGoogleSignup;
    TextView txtUserName, txtPassword, txtName;
    String username = "", password = "", name = "", userid = "", message = "";
    String lastusername = "", lastemail = "";
    Preferences preferences;
    TextInputLayout tilName;
    String has_card = "NO";
    DBHelper dbHelper;

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
        setContentView(R.layout.activity_login);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        preferences = new Preferences(context);
        initUI();
        initListener();
    }

    private void initComponent() {// Nikita - added additional check username and email are not similar to old db's
        if (preferences.getInt(PrefConstants.SUBSCRIPTION_ENDS) != 1 || !username.equalsIgnoreCase(lastemail) || !name.equalsIgnoreCase(lastusername)) {
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
        }

        dbHelper = new DBHelper(context, "MASTER");
        MyConnectionsQuery p = new MyConnectionsQuery(context, dbHelper);

    }

    private void initListener() {
        txtSignIn.setOnClickListener(this);
        txtNew.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        imgFbSignup.setOnClickListener(this);
        imgGoogleSignup.setOnClickListener(this);
    }

    private void initUI() {

/*
        String s = getResources().getString(R.string.FullAppname);
        TextView textlogo = findViewById(R.id.txtLogo);
        textlogo.setText(Html.fromHtml(s));
*/

        rlLogin = findViewById(R.id.rlLogin);
        txtSignIn = findViewById(R.id.txtSignIn);
        txtNew = findViewById(R.id.txtNew);
        txtName = findViewById(R.id.txtName);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        imgFbSignup = findViewById(R.id.imgFbSignup);
        imgGoogleSignup = findViewById(R.id.imgGoogleSignup);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        tilName = findViewById(R.id.tilName);
        tilName.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        if (getIntent().hasExtra("from")) {
            txtName.setText(getIntent().getExtras().getString("name"));
            txtUserName.setText(getIntent().getExtras().getString("email"));
            lastusername = getIntent().getExtras().getString("name");
            lastemail = getIntent().getExtras().getString("email");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtSignIn:
                if (validate()) {
//                    accessPermission();
                    if (!NetworkUtils.getConnectivityStatusString(LoginActivity.this).equals("Not connected to Internet")) {
                        GetUserAsynk asynkTask = new GetUserAsynk(name, username);
                        asynkTask.execute();
                    } else {
                        DialogManager.showAlert("Network Error, Check your internet connection", LoginActivity.this);
                    }

                   /* ArrayList<PersonalInfo> PersonList = PersonalInfoQuery.fetchOneRecord(username, password);
                    if (PersonList.size() != 0) {
                        for (int i = 0; i < PersonList.size(); i++) {
                            if (username.equals(PersonList.get(i).getEmail()) && password.equals(PersonList.get(i).getPassword())) {
                                Toast.makeText(context, "You have Logged in successfully", Toast.LENGTH_SHORT).show();
                                preferences.putString(PrefConstants.USER_EMAIL, PersonList.get(i).getEmail());
                                preferences.putString(PrefConstants.USER_NAME, PersonList.get(i).getName());
                               // String saveThis = Base64.encodeToString(PersonList.get(i).getPhoto(), Base64.DEFAULT);
                                preferences.putString(PrefConstants.USER_PROFILEIMAGE, PersonList.get(i).getPhoto());
                                preferences.putInt(PrefConstants.USER_ID, PersonList.get(i).getId());
                                preferences.setREGISTERED(true);
                                preferences.setLogin(true);
                                Intent signinIntent = new Intent(context, BaseActivity.class);
                                startActivity(signinIntent);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(context, "Enter correct Username or Password", Toast.LENGTH_SHORT).show();
                        txtUserName.setText("");
                        txtPassword.setText("");
                    }*/

                }

               /* Intent signinIntent = new Intent(context, BaseActivity.class);
                startActivity(signinIntent);
                finish();*/
                break;
            case R.id.txtNew:
                Intent signupIntent = new Intent(context, SignUpActivity.class);
                startActivity(signupIntent);
                finish();
                break;
            case R.id.txtForgotPassword:

                break;

            case R.id.imgFbSignup:

                break;

            case R.id.imgGoogleSignup:

                break;
        }
    }

    private boolean validate() {

        username = txtUserName.getText().toString().trim();
        name = txtName.getText().toString().trim();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (username.equals("")) {
            txtUserName.setError("Please Enter email");
            DialogManager.showAlert("Please Enter email", context);
        } else if (!username.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtUserName.setError("Please enter valid email");
            DialogManager.showAlert("Please enter valid email", context);
        } else {
            return true;
        }


        return false;
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void navigateToAPP() {
        int userId = Integer.parseInt(userid);
        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
        //After Success

        if (preferences.getInt(PrefConstants.SUBSCRIPTION_ENDS) == 1 && username.equalsIgnoreCase(lastemail) && name.equalsIgnoreCase(lastusername)) {
            preferences.putInt(PrefConstants.USER_ID, userId);
            preferences.putString(PrefConstants.USER_EMAIL, username);
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

            Boolean flag = MyConnectionsQuery.insertMyConnectionsData(userId, name, username, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);

            PersonalInfoQuery pi = new PersonalInfoQuery(context, dbHelper);
            Boolean flagPersonalinfo = PersonalInfoQuery.insertPersonalInfoData(name, username, "", "", "", "", "", "", "", "", "");
            if (flag == true) {
                RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
                String mail = connection.getEmail();
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");
                DBHelper dbHelper = new DBHelper(context, mail);
                MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
                Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getId(), name, username, "", "", "", "", "Self", "", "", 1, 2, "", "", has_card);
                if (flags == true) {
                    // Toast.makeText(context, "You have created db Successfully", Toast.LENGTH_SHORT).show();
                }
                //  Toast.makeText(context,"You have added profile Successfully",Toast.LENGTH_SHORT).show();
                preferences.putInt(PrefConstants.USER_ID, userId);
                preferences.putString(PrefConstants.USER_EMAIL, username);
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
    }

    public void parseResponseg(String result) {
        Log.e("Response", result);
        JSONObject job = null;
        String errorCode = "";
        try {
            initComponent();//Nikita - Making folder here after check

            job = new JSONObject(result);
            JSONObject job1 = job.optJSONObject("response");
            errorCode = job1.optString("errorCode");

            if (errorCode.equals("0")) {
                message = job1.optString("respMsg");
                JSONObject job2 = job1.optJSONObject("respData");
                userid = job2.optString("user_id");
                Log.e("SuccessFullLogin", "UserId= " + userid);

                // Nikita#Sub - code for existing user (if found) starts here...
                boolean iserror = false;

                JSONObject jobS = job1.optJSONObject("subscription");

                if (jobS != null) {
                    // User has subscription data on server
                    String transId = jobS.optString("transaction_id");
                    String startDate = jobS.optString("start_date");
                    String endDate = jobS.optString("end_date");
                    String source = jobS.optString("source");

                    if (!transId.isEmpty() && !endDate.isEmpty()) {
                        if (validDateChecker(endDate)) {
                            // User has valid subscription data on server
                            // Entry to App
                            SubscrptionData sub = new SubscrptionData();
                            sub.setSource(source);
                            sub.setEndDate(endDate);
                            sub.setStartDate(startDate);
                            sub.setTransactionID(transId);
                            sub.setUserId(Integer.parseInt(userid));
                            sub.setEmail(username);

                            preferences.putInt(PrefConstants.UPLOAD_FLAG, 1);

                            SubscriptionQuery ss = new SubscriptionQuery(context, dbHelper);
                            Boolean ssflag = SubscriptionQuery.insertSubscriptionData(sub.getUserId(), sub);

                            navigateToAPP();
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

                //navigating to login - if user is existing without subscription
                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                if (iserror) {
                    inApp();//calling subscription method here
                }
                // Nikita#Sub - code for existing user (if found) ends here...


            } else {
                message = job1.getString("errorMsg");
                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
//                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
//
//        ) {
//            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_CONTACTS
//            }, REQUEST_CALL_PERMISSION);
//
//        } else {
//
//            if (!NetworkUtils.getConnectivityStatusString(LoginActivity.this).equals("Not connected to Internet")) {
//                GetUserAsynk asynkTask = new GetUserAsynk(name, username);
//                asynkTask.execute();
//            } else {
//                DialogManager.showAlert("Network Error, Check your internet connection", LoginActivity.this);
//            }
//            if(preferences.getInt(PrefConstants.SUBSCRIPTION_ENDS)!=1) {
//                try {
//                    File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
//                    if (!f.exists()) {
//                        f.mkdirs();
//                    } else {
//                        try {
//                            File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
//                            FileUtils.deleteDirectory(file);
//                            f.mkdirs();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CALL_PERMISSION: {
//                if (grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                    if (!NetworkUtils.getConnectivityStatusString(LoginActivity.this).equals("Not connected to Internet")) {
//                        GetUserAsynk asynkTask = new GetUserAsynk(name, username);
//                        asynkTask.execute();
//                    } else {
//                        DialogManager.showAlert("Network Error, Check your internet connection", LoginActivity.this);
//                    }
//                    if(preferences.getInt(PrefConstants.SUBSCRIPTION_ENDS)!=1) {
//                        try {
//                            File f = new File(Environment.getExternalStorageDirectory(), "/MYLO/MASTER/");
//                            if (!f.exists()) {
//                                f.mkdirs();
//                            } else {
//                                try {
//                                    File file = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
//                                    FileUtils.deleteDirectory(file);
//                                    f.mkdirs();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
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

    class GetUserAsynk extends AsyncTask<Void, Void, String> {
        ProgressDialog pd;
        String name = "";
        String email = "";

        public GetUserAsynk(String name, String email) {
            this.name = name;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("Please Wait..");
            pd.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            WebService webService = new WebService();
            String result = webService.getProfile(name, email);
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
                    //ErrorDialog.errorDialog(context);
                } else {
                    Log.e("CreateUserAsynk", result);
                    parseResponseg(result);

                }
            }
            super.onPostExecute(result);
        }

    }

    // Subscription code starts here - Nikita#Sub

    static final String TAG = "TrivialDrive";
    static final String SKU_INFINITE_GAS = "subscribe_app";   //$4.99
    static final int RC_REQUEST = 10001;
    boolean mSubscribedToInfiniteGas = false;
    IabHelper mHelper;

    void complain(String message) {
        Log.e(TAG, "Error: " + message);
        alert(message);
        onInfiniteGasButtonClicked();// re-prompt payment portal
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                if (!result.getMessage().contains("canceled")) {
                    complain(result.getMessage());
                }

                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");
            if (purchase.getSku().equals(SKU_INFINITE_GAS)) {
                // bought the infinite gas subscription
                Log.d(TAG, "Mylo app subscription purchased.");
                alert("Thank you for subscribing to Mylo app!");
                mSubscribedToInfiniteGas = true;

                String startdate = toDateStr(purchase.getPurchaseTime());
                String enddate = toDateEnd(purchase.getPurchaseTime() + DateUtils.YEAR_IN_MILLIS);

                //Toast.makeText(LoginActivity.this, "SUB_DATA\nTID : " + purchase.getToken() + "\nSdate : " + startdate + "\nEdate : " + enddate + "\nUID : " + userid, Toast.LENGTH_LONG).show();

                SubscrptionData sub = new SubscrptionData();
                sub.setSource("Android");
                sub.setEndDate(enddate);
                sub.setStartDate(startdate);
                sub.setTransactionID(purchase.getToken());
                sub.setUserId(Integer.parseInt(userid));
                sub.setEmail(username);

                initBGProcess(sub);


            } else {
                complain("Kindly subscribe.");
            }
        }
    };

    public static String toDateStr(long milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formatter.format(date);
    }

    public static String toDateEnd(long milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formatter.format(date);
    }

    private void initBGProcess(SubscrptionData sub) {

        SubscriptionQuery ss = new SubscriptionQuery(context, dbHelper);
        Boolean ssflag = SubscriptionQuery.insertSubscriptionData(sub.getUserId(), sub);

        if (ssflag) {
            preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);

            //preventing repeat call - as calling in case activity
//            Data inputData = new Data.Builder()
//                    .putInt("userId", sub.getUserId())
//                    .build();
//
//            OneTimeWorkRequest mywork =
//                    new OneTimeWorkRequest.Builder(WorkerPost.class)
//                            .setInputData(inputData).build();// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
//            String id = mywork.getId().toString();
//            System.out.println("NIKITA WORK ID: " + id);
//            WorkManager.getInstance().enqueue(mywork);
        }

        navigateToAPP();
    }


    private void inApp() {
        String base64EncodedPublicKey = context.getResources().getString(R.string.basekey);//"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq3i1ShkUzBAWxerhJne2R7KYwWVXyERXLxz7Co0kW9wS45C55XnM/kFHNZ0hI62Oz8HWbTO+RisBMQ5If21sHu5DgXLHa+LNYj+2ZPQWlh46jo/jhMgo+V9YJ7EeOLedH70fFRlhy9OT2ZmOWscxN5YJDp22RXvilale2WcoKVOriS+I9fNbeREDcKM4CsB0isJyDEVIagaRaa0Za8MleOVeYUdma5q3ENZDJ8g9W2Dy0h6fioCZ9OIgBCY63qr0jVxHUwD8Jebp91czKWRSRi433suBmSkoE6qkhwtDEdckeG+cx6xErHcoPSrwhaLlvqCC1KngYduRZy5j1jCAywIDAQAB"; //"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt/vQGFXEB+fQ7s5JbO/teKHjmvkZgqSeLSXmicYu4jDC5mBqfZ1/wBES/lhPGEfJAmjmSSQ1Z35XIcoTL74KVASTrUComknH4XiGaiXCjeCe9cFwYCXlWT+B3Y+dkRajRTi9G/iIgUZP6NTyblmKd5KcUn64CQIqgIZ8pD/4GsIR5abUFTEH9XXQEKzFjcdaBKB4uK1m2JLZ+w+FTFeNydzqSYdRL5lY4IHr8RHZwA3BReNMpzPt1Zp7URSkAGjXvbpOkURupUP+hB4VBYQYPfHfx3K4m32XKWl8zP0qwHS2kIIAjAEekzN+l+bDAU9fXdkDKuHIeXA0HLC6i9jRkQIDAQAB";

        // Some sanity checks to see if the developer (that's you!) really followed the
        // instructions to run this sample (don't put these checks on your app!)
        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
        }
        if (getPackageName().startsWith("com.example")) {
            throw new RuntimeException("Please change the sample's package name! See README.");
        }

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    alert("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {


            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            // Do we have the infinite gas plan?
            Purchase purchase = inventory.getPurchase(SKU_INFINITE_GAS);
            mSubscribedToInfiniteGas = (purchase != null &&
                    verifyDeveloperPayload(purchase));
            Log.d(TAG, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
                    + " app subscription.");


            if (mSubscribedToInfiniteGas == true) {

                Log.d(TAG, "" + purchase.getPurchaseTime());

                String startdate = toDateStr(purchase.getPurchaseTime());
                String enddate = toDateEnd(purchase.getPurchaseTime() + DateUtils.YEAR_IN_MILLIS);

                //Toast.makeText(LoginActivity.this, "SUB_DATA\nTID : " + purchase.getToken() + "\nSdate : " + startdate + "\nEdate : " + enddate + "\nUID : " + userid, Toast.LENGTH_LONG).show();

                SubscrptionData sub = new SubscrptionData();
                sub.setSource("Android");
                sub.setEndDate(enddate);
                sub.setStartDate(startdate);
                sub.setTransactionID(purchase.getToken());
                sub.setUserId(Integer.parseInt(userid));
                sub.setEmail(username);

                initBGProcess(sub);

            } else {
                complain("Kindly Subscribe");
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };


    public void onInfiniteGasButtonClicked() {
        if (mHelper != null) {
            if (!mHelper.subscriptionsSupported()) {
                alert("Subscriptions not supported on your device yet. Sorry!");
                return;
            }
        }
        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";

        Log.d(TAG, "Launching purchase flow for Mylo subscription.");

        if (mHelper != null) {
            try {
                mHelper.launchPurchaseFlow(this,
                        SKU_INFINITE_GAS, IabHelper.ITEM_TYPE_SUBS,
                        RC_REQUEST, mPurchaseFinishedListener, payload);
            } catch (IllegalStateException ex) {
                Toast.makeText(this, "Please retry in a few seconds.", Toast.LENGTH_SHORT).show();
                mHelper.flagEndAsync();
            }
        }
    }
// Subscription code ends here - Nikita#Sub
}
