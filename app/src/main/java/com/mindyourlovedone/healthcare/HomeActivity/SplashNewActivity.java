package com.mindyourlovedone.healthcare.HomeActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mindyourlovedone.healthcare.util.IabHelper;
import com.mindyourlovedone.healthcare.util.IabResult;
import com.mindyourlovedone.healthcare.util.Inventory;
import com.mindyourlovedone.healthcare.util.Purchase;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;

/**
 * Class: SplashNewActivity
 * A class that manages initial operations like new user, existing user, subscription
 * implements OnclickListener for onClick event on views
 */

public class SplashNewActivity extends AppCompatActivity implements View.OnClickListener {
    static final String TAG = "TrivialDrive";
    static final String SKU_INFINITE_GAS = "subscribe_app";   //$9.99
    private static final int REQUEST_CALL_PERMISSION = 100;
    Context context = this;
    TextView txtNew, txtRegistered, txtWelcome, txtSubscribe;
    Preferences preferences;
    ImageView img1, img2, img3, imgForword;
    RelativeLayout llBottom, llSubscribe;
    LinearLayout llSplash;
    RelativeLayout rlBottom;
    boolean mSubscribedToInfiniteGas = false;
    IabHelper mHelper;
    public static boolean fromDash = false;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;


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
        setContentView(R.layout.activity_splash_no_courtesy);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        ImageView imageView = findViewById(R.id.imgSplash);
        //Initialize Image loading and displaying at ImageView
        initImageLoader(); // Initialize image loader for load and set image

        if (isTablet(context)) {
            String imageUri = "drawable://" + R.drawable.sp_tabnew;
            imageLoader.displayImage(String.valueOf(imageUri), imageView, displayImageOptions);
        } else {
            String imageUri = "drawable://" + R.drawable.sp_new;
            imageLoader.displayImage(String.valueOf(imageUri), imageView, displayImageOptions);
        }

        // Execute Code in Background
        new AsynData().execute("");

        //Check for runtime permission
        accessPermission();
    }

    /**
     * Function: Image loading and displaying at ImageView
     * Presents configuration for ImageLoader & options for image display.
     */
    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * Function: Check for device is tablet or phone
     */
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
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
            Purchase infiniteGasPurchase = inventory.getPurchase(SKU_INFINITE_GAS);
            mSubscribedToInfiniteGas = (infiniteGasPurchase != null &&
                    verifyDeveloperPayload(infiniteGasPurchase));
            Log.d(TAG, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
                    + " app subscription.");
            if (mSubscribedToInfiniteGas == true) {
                llSubscribe.setVisibility(View.GONE);
                txtNew.setVisibility(View.GONE);
            } else {
                llSplash.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
            }
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };


    /**
     * Function: Activity Callback method called when screen gets visible and interactive
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (fromDash) {//nikita
            fromDash = false;
            new AsynData().execute("");
        }
    }


    private void loadata() {
        variableInitialization();
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        if (preferences == null) {
            preferences = new Preferences(SplashNewActivity.this);
        }

        if (preferences.getREGISTERED()) {
            llBottom.setVisibility(View.VISIBLE);
            llSplash.setVisibility(View.GONE);
        } else {
            //Check for subscription
            inApp();
        }

        //Check for crashlytics
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);


    }

    /**
     * Function: Check for google subscription
     */
    private void inApp() {
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq3i1ShkUzBAWxerhJne2R7KYwWVXyERXLxz7Co0kW9wS45C55XnM/kFHNZ0hI62Oz8HWbTO+RisBMQ5If21sHu5DgXLHa+LNYj+2ZPQWlh46jo/jhMgo+V9YJ7EeOLedH70fFRlhy9OT2ZmOWscxN5YJDp22RXvilale2WcoKVOriS+I9fNbeREDcKM4CsB0isJyDEVIagaRaa0Za8MleOVeYUdma5q3ENZDJ8g9W2Dy0h6fioCZ9OIgBCY63qr0jVxHUwD8Jebp91czKWRSRi433suBmSkoE6qkhwtDEdckeG+cx6xErHcoPSrwhaLlvqCC1KngYduRZy5j1jCAywIDAQAB"; //"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt/vQGFXEB+fQ7s5JbO/teKHjmvkZgqSeLSXmicYu4jDC5mBqfZ1/wBES/lhPGEfJAmjmSSQ1Z35XIcoTL74KVASTrUComknH4XiGaiXCjeCe9cFwYCXlWT+B3Y+dkRajRTi9G/iIgUZP6NTyblmKd5KcUn64CQIqgIZ8pD/4GsIR5abUFTEH9XXQEKzFjcdaBKB4uK1m2JLZ+w+FTFeNydzqSYdRL5lY4IHr8RHZwA3BReNMpzPt1Zp7URSkAGjXvbpOkURupUP+hB4VBYQYPfHfx3K4m32XKWl8zP0qwHS2kIIAjAEekzN+l+bDAU9fXdkDKuHIeXA0HLC6i9jRkQIDAQAB";

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
                    complain("Problem setting up in-app billing: " + result);
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

    /**
     * Function: Check for hashKey of system
     */
    private void hashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.mindyourlovedone.healthcare.HomeActivity",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("VKey:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    /**
     * Function: Check for runtime permission
     * If granted go further else request for permission to be granted
     * Return result in onRequestPermissionsResult method
     */
    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS
            }, REQUEST_CALL_PERMISSION);

        }
    }

    /**
     * Function: Initialize preference and access file
     */
    private void variableInitialization() {
        preferences = new Preferences(this);
        //for dashboard webservice call
        preferences.setFirstTimeCall(true);
    }


    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        txtNew.setOnClickListener(this);
        txtRegistered.setOnClickListener(this);
        txtWelcome.setOnClickListener(this);
        imgForword.setOnClickListener(this);
    }


    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        txtNew = findViewById(R.id.txtNew);
        txtRegistered = findViewById(R.id.txtRegistered);
        txtSubscribe = findViewById(R.id.txtSubscribe);
        txtWelcome = findViewById(R.id.txtWelcome);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        imgForword = findViewById(R.id.imgForword);
        llBottom = findViewById(R.id.llBottom);
        llSplash = findViewById(R.id.llSplash);
        llSubscribe = findViewById(R.id.llSubscribe);
        rlBottom = findViewById(R.id.rlBottom);

        if (preferences == null) {
            preferences = new Preferences(SplashNewActivity.this);
        }
        if (preferences.getREGISTERED()) {
            if (preferences.isLogin()) {
                llBottom.setVisibility(View.VISIBLE);
                llSplash.setVisibility(View.GONE);
                txtWelcome.setText("Welcome Back " + preferences.getString(PrefConstants.USER_NAME));
            } else {
                llBottom.setVisibility(View.GONE);
                llSplash.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtNew: //New
                onClickNew();
                break;

            case R.id.txtRegistered: //Registered
                onClickRegistered();
                break;

            case R.id.txtWelcome: //Welcome Back
               onClickWelcome();
                break;

            case R.id.imgForword: //Next
                if (preferences == null) {
                    preferences = new Preferences(SplashNewActivity.this);
                }
                //Check if user is already logged in or not in application, If Yes will goes to Profile Screen else to Login Screen

                if (preferences.getREGISTERED()) {
                    startActivity(new Intent(SplashNewActivity.this, BaseActivity.class));
                } else {
                    startActivity(new Intent(SplashNewActivity.this, LoginActivity.class));
                }
                break;
        }
    }

    /**
     * Function: User reopens app and click on welcome button goes inside app
     */
    private void onClickWelcome() {
        if (preferences == null) {
            preferences = new Preferences(SplashNewActivity.this);
        }
        //Check if user is already logged in or not in application, If Yes will goes to Profile Screen else to Login Screen
        if (preferences.getREGISTERED()) {
            startActivity(new Intent(SplashNewActivity.this, BaseActivity.class));
        } else {
            startActivity(new Intent(SplashNewActivity.this, LoginActivity.class));
        }
    }

    /**
     * Function: Login With registered user
     */
    private void onClickRegistered() {
        if (preferences == null) {
            preferences = new Preferences(SplashNewActivity.this);
        }

        //Check if user is already logged in or not in application, If Yes will goes to Profile Screen else to Login Screen
        if (preferences.getREGISTERED()) {
            Intent intent1 = new Intent(SplashNewActivity.this, BaseActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(SplashNewActivity.this, LoginActivity.class);
            startActivity(intent2);
        }
    }

    /**
     * Function: Register as New User
     */
    private void onClickNew() {
        Intent intent = new Intent(context, SignUpActivity.class);
        startActivity(intent);
    }


    /**
     * Function: Callback for the result from requesting permissions.
     *
     * @param requestCode  int: The request code passed in requestPermissions(android.app.Activity, String[], int)
     * @param permissions  String: The requested permissions. Never null.
     * @param grantResults int: The grant results for the corresponding permissions which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    //Request to permission access
                    //Check for runtime permission
                    accessPermission();
                }
                return;
            }
        }
    }

    /**
     * @param requestCode The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
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

    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            try {
                mHelper.dispose();
                mHelper = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Display error dialog
     *
     * @param message Error message to be display
     */
    void complain(String message) {
        Log.e(TAG, "Error: " + message);
        alert(message);
    }

    /**
     * Display alert dialog
     *
     * @param message Alert message to be display
     */
    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    /**
     * Execute long running code in backgrround
     */
    private class AsynData extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            loadata();
            super.onPostExecute(o);
        }
    }
}
