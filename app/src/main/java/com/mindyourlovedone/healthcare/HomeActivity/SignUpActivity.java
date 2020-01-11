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

import com.mindyourlovedone.healthcare.Fragment.SupportActivity;
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
import com.mindyourlovedone.healthcare.utility.WebPDFActivity;
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
/**
 * Class: SignUpActivity
 * Screen: Sign Up Screen
 * A class that manages Registration process of New User and Subscription
 * implements OnclickListener for onClick event on views
 * implements OnTouchListener for onTouch event on views
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    TextInputLayout tilName;
    TextView txtName, txtNext, txtEmail, txtPolicy2, txtPolicy4;
    ImageView imgBack;
    Context context = this;
    String name = "", email = "";
    boolean allow = false;
    String has_card = "NO";
    Preferences preferences;
    DBHelper dbHelper;
    CheckBox rbCheck;
    RelativeLayout touchInterceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Transperent Window, Notification bar, title bar
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

        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
initListener();

        //Initialize database, get primary data and set data
        initComponent();

    }


   /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        tilName = findViewById(R.id.tilName);
        txtName = findViewById(R.id.txtName);
        txtNext = findViewById(R.id.txtNext);
        rbCheck = findViewById(R.id.rbCheck);
        txtEmail = findViewById(R.id.txtEmail);
        imgBack = findViewById(R.id.imgBack);
        txtPolicy2 = findViewById(R.id.txtPolicy2);
        txtPolicy4 = findViewById(R.id.txtPolicy4);
        touchInterceptor = (RelativeLayout) findViewById(R.id.rlTops);
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


    }


    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgBack.setOnClickListener(this);
        txtNext.setOnClickListener(this);
        touchInterceptor.setOnTouchListener(this);
        txtName.setOnTouchListener(this);
        txtPolicy2.setOnTouchListener(this);
        txtPolicy4.setOnTouchListener(this);
    }

    /**
     * Function: Initialize database, get data and set data
     */
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
        //Initialize database
        dbHelper = new DBHelper(context, "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
    }

    /**
     * Function: Hide device keyboard.
     */
    /**
     * Function: Hide device keyboard.
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Called when a touch event is dispatched to a view.
     * @param v View: The view the touch event has been dispatched to.
     * @param event MotionEvent: The MotionEvent object containing full information about the event.
     * @return boolean, True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.rlTops:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (txtName.isFocused()) {
                        Rect outRect = new Rect();
                        txtName.getGlobalVisibleRect(outRect);
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
                break;
            case R.id.txtPolicy2:
                Intent browserIntentD2 = new Intent(SignUpActivity.this, WebPDFActivity.class);
                browserIntentD2.putExtra("Name", "Privacy Policy");
                browserIntentD2.putExtra("URL", WebService.PRIVACY_URL);
                startActivity(browserIntentD2);
                break;
            case R.id.txtPolicy4:
                finish(); Intent browserIntentD = new Intent(SignUpActivity.this, WebPDFActivity.class);
                browserIntentD.putExtra("Name", "End User License Agreement");
                browserIntentD.putExtra("URL", WebService.EULA_URL);
                startActivity(browserIntentD);
                break;
            case R.id.txtName:
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);
                break;
        }
        return false;
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack: //Navigate to back screen
                finish();
                break;
            case R.id.txtNext: // Registration Process
                onClickSignup();
                break;
        }
    }

    /**
     * Function: Sign up for new user
     */
    private void onClickSignup() {
        hideSoftKeyboard();
        name = txtName.getText().toString();
        email = txtEmail.getText().toString();

        //Validate if user input is valid or not, If true then goes for registration on server
        if (validate()) {
            //Check if network connection is available and connected or not.
            if (!NetworkUtils.getConnectivityStatusString(SignUpActivity.this).equals("Not connected to Internet")) {
                CreateUserAsynk asynkTask = new CreateUserAsynk(name, email);
                asynkTask.execute();
            } else {
                DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
            }
        }
    }


    /**
     * Function: Validation of data input by user
     * @return boolean, True if given input is valid, false otherwise.
     */
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


    /**
     * Class: CreateUserAsynk
     * Screen: Sign Up Screen
     * A class that manages background process of registering data on server side database
     * implements OnclickListener for onClick event on views
     * implements OnTouchListener for onTouch event on views
     */
    class CreateUserAsynk extends AsyncTask<Void, Void, String> {
        String name;
        String email;
        ProgressDialog pd;
        private String deviceUdId = "";
        private String deviceType = "Android";

        //
        // Initializes the object.

        /**
         * Constructor: CreateUserAsynk
         * Initializes the variables.
         * @param name user name of user
         * @param email email of user
         */
        public CreateUserAsynk(String name, String email) {
            this.name = name;
            this.email = email;
        }


        @Override
        protected void onPreExecute() {
            // device from registration has to be done
            deviceUdId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            // Initialize progress dialog
            pd = ProgressDialog.show(context, "", "Please Wait..");
            super.onPreExecute();
        }

        /**
         * Background long running code
         * @param params
         * @return String, Server Response after server operation
         */
        @Override
        protected String doInBackground(Void... params) {
            // Initialize webservice class
            WebService webService = new WebService();
            Log.e("URL parameter", name + "" + "" + " " + email
                    + " " + "" + " " + deviceUdId + " " + deviceType);

            //Call to create profile metod for connection and upload data on server db
            String result = webService.createProfile(name, "-",
                    "-", email, "-", deviceUdId, deviceType);
            return result;
        }


        /**
         * Called when received result from server in onPostExecute for set data and store at local
         * @param result Result received in onPostExecute
         */
        @Override
        protected void onPostExecute(String result) {
            if (pd != null) {
                if (pd.isShowing()) {
                    // dismiss progress dialog after getting output from server
                    pd.dismiss();
                }
            }

            if (!result.equals("")) {
                if (result.equals("Exception")) {
                    // ErrorDialog.errorDialog(context);
                    DialogManager.showAlert("Error", context);
                } else {
                    Log.e("CreateUserAsynk", result);

                    //Decode Response and save locally
                    parseResponse(result);

                }
            }
            super.onPostExecute(result);
        }

        /**
         * Function: Convert data from json format to string and store
         * @param result
         */
        private void parseResponse(String result)
        {
            Log.e("Response", result);
            JSONObject job = null;
            String errorCode = "";
            try {

                try {
                     /**
                     * Create MYLO Folder for new user for future storages and database realted to app
                      * if folder is already exist, delete old and create new for new user
                      */
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
                    // If user uccessfully registered not having active subcription , goes to next screen
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
                                // If user is already registered with having active subcription
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

        /**
         * Function: Navigate to next screen Important agreement
         * @param userid registered user's id , received from server
         */
        private void navigateToApp(int userid) {
            //Nikita#Sub - redirecting to agreement screen if payment successful then only saving data
            Intent signupIntent = new Intent(context, ImpAgreementActivity.class);

            //PDF_EXT used for receiving documents externally shared
            if (getIntent().hasExtra("PDF_EXT")) {
                signupIntent.putExtra("PDF_EXT", getIntent().getStringExtra("PDF_EXT"));
            }

            signupIntent.putExtra("userid", userid);
            signupIntent.putExtra("Name", name);
            signupIntent.putExtra("Email", email);
            startActivity(signupIntent);
            finish();
        }

        /**
         * Function: Store Subscription data, If user is already registered with having active subcription
         * Navigate to Inside App i.e. Profile screen
         * @param userid
         * @param sub SubscrptionData object
         */
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


        /**
         * Function: Checking for current date is before expiry date or not
         * @param date
         * @return boolean true if current date is before expiry date, false otherwise
         */
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

    }
}

