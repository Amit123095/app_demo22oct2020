package com.mindyourlovedone.healthcare.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;

import com.android.vending.billing.IInAppBillingService;
import com.mindyourlovedone.healthcare.HomeActivity.LoginActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.SubscriptionQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.SubscrptionData;
import com.mindyourlovedone.healthcare.util.IabHelper;
import com.mindyourlovedone.healthcare.util.IabResult;
import com.mindyourlovedone.healthcare.util.Inventory;
import com.mindyourlovedone.healthcare.util.Purchase;
import com.mindyourlovedone.healthcare.util.SkuDetails;
import com.mindyourlovedone.healthcare.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Nikita on 24-8-19.
 */

public class WorkerPost extends Worker {
    int userid, uploadFlag;
    String email = "";
    SubscrptionData sub;
    Context context = getApplicationContext();
    Preferences preferences;

    @Override
    public WorkerResult doWork() {
        context = getApplicationContext();
        try {
            userid = getInputData().getInt("userId", 0);

            DBHelper db = new DBHelper(context, "MASTER");
            MyConnectionsQuery m = new MyConnectionsQuery(context, db);
            SubscriptionQuery ss = new SubscriptionQuery(context, db);

            preferences = new Preferences(context);

            preferences.putInt(PrefConstants.SUBSCRIPTION_ENDS, 0);

            if (preferences.getInt(PrefConstants.FROM_Dropbox) == 1) {
                preferences.putInt(PrefConstants.FROM_Dropbox, 0);
            }

            RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
            if (userid == 0) {
                userid = connection.getUserid();
            }

            email = connection.getEmail();

            subscriptionCheck();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Indicate success or failure with your return value:
        return WorkerResult.SUCCESS;

        // (Returning RETRY tells WorkManager to try context task again
        // later; FAILURE says not to try again.)
    }

    private void subscriptionCheck() {
        sub = SubscriptionQuery.fetchSubscriptionRecord(userid);

        if (sub != null && sub.getTransactionID() != null) {
            uploadFlag = preferences.getInt(PrefConstants.UPLOAD_FLAG);

            if (uploadFlag == 0) {
                postSubscription();
            } else {
                if (!validDateChecker(sub.getEndDate())) {
                    getSubscription();
                } else {
                    //nothing to do
                }
            }
        } else {
            getSubscription();
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

    private void moveToLogin() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Subscription expired OR data is invalid OR not found...\nTry Login again...", Toast.LENGTH_LONG).show();
            }
        });

        RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("name", connection.getName());
        intent.putExtra("email", connection.getEmail());
        intent.putExtra("from", "WorkerPost");

        SubscriptionQuery.deleteRecord(userid);
        preferences.clearPreferences();
        preferences.putInt(PrefConstants.SUBSCRIPTION_ENDS, 1);
        context.startActivity(intent);
    }

    private void getSubscription() {
        try {
            if (!NetworkUtils.getConnectivityStatusString(context).equals("Not connected to Internet")) {
                GetSubAsynk asynkTask = new GetSubAsynk();
                asynkTask.execute();
            } else {
                //nothing to do
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class GetSubAsynk extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            WebService webService = new WebService();
            String result = webService.getSubscriptionData(userid + "");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            if (!result.equals("")) {
                if (result.equals("Exception")) {
                } else {
                    Log.e("CreateUserAsynk", result);
                    parseSubscriptionResponse(result);
                }
            }
            super.onPostExecute(result);
        }

        private void parseSubscriptionResponse(final String result) {
            Log.e("Response", result);
            JSONObject job = null;
            String errorCode = "";
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "op: " + result, Toast.LENGTH_LONG).show();
//                }
//            });
            try {
                job = new JSONObject(result);
                JSONObject jobB = job.optJSONObject("response");
                errorCode = jobB.optString("errorCode");

                if (errorCode.equals("0")) {

                    JSONObject jobS = jobB.optJSONObject("respMsg");

                    if (jobS != null) {
                        // User has subscription data on server
                        String userid = jobS.optString("user_id");
                        String transId = jobS.optString("transaction_id");
                        String startDate = jobS.optString("start_date");
                        String endDate = jobS.optString("end_date");
                        String source = jobS.optString("source");

                        if (!transId.isEmpty() && !endDate.isEmpty()) {
                            if (validDateChecker(endDate)) {// definately server has greater enddate value
                                SubscrptionData sub = new SubscrptionData();
                                sub.setSource(source);
                                sub.setEndDate(endDate);
                                sub.setStartDate(startDate);
                                sub.setTransactionID(transId);
                                sub.setUserId(Integer.parseInt(userid));
                                sub.setEmail(sub.getEmail());

                                preferences.putInt(PrefConstants.UPLOAD_FLAG, 1);

                                Boolean ssflag = SubscriptionQuery.updateSubscriptionData(sub.getUserId(), sub);
                            } else {
                                inApp();
                            }
                        } else {
                            inApp();
                        }
                    } else {
                        inApp();
                    }
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "Success : Get Subscription", Toast.LENGTH_LONG).show();
//                        }
//                    });
                } else {
                    inApp();
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "Failure : Get Subscription", Toast.LENGTH_LONG).show();
//                        }
//                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, "Get Subscription : Exception detected ", Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        }
    }


    private void postSubscription() {
        try {
            if (!NetworkUtils.getConnectivityStatusString(context).equals("Not connected to Internet")) {
                PostSubAsynk asynkTask = new PostSubAsynk();
                asynkTask.execute();
            } else {
                preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
            }
        } catch (Exception e) {
            preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
            e.printStackTrace();
        }
    }

    class PostSubAsynk extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            WebService webService = new WebService();
            String result = webService.postSubscriptionData(sub.getUserId() + "", sub.getTransactionID(), sub.getStartDate(), sub.getEndDate());
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            if (!result.equals("")) {
                if (result.equals("Exception")) {
                    preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
                } else {
                    Log.e("CreateUserAsynk", result);
                    parseSubscriptionResponse(result);
                }
            }
            super.onPostExecute(result);
        }

        String message = "";

        private void parseSubscriptionResponse(final String result) {
            Log.e("Response", result);
            JSONObject job = null;
            String errorCode = "";
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "op: " + result, Toast.LENGTH_LONG).show();
//                }
//            });
            try {
                job = new JSONObject(result);
                JSONObject jobB = job.optJSONObject("response");
                errorCode = jobB.optString("errorCode");


                if (errorCode.equals("0")) {
                    message = jobB.optString("respMsg");
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
//                        }
//                    });
                    // Update upload flag for subcription as 1
                    preferences.putInt(PrefConstants.UPLOAD_FLAG, 1);
                } else if (errorCode.equals("1")) {
                    message = jobB.optString("errorMsg");
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
//
//                        }
//                    });
                    preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
                } else if (errorCode.equals("2")) {// Duplicate trans-id
                    preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
//                        }
//                    });
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "This device subscription is already active for other user.\nKinldy use different device.", Toast.LENGTH_LONG).show();
                        }
                    });
                    moveToLogin();
                } else {
                    preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "Unexpected error from server.", Toast.LENGTH_LONG).show();
//                        }
//                    });
                }

            } catch (JSONException e) {
                preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
                e.printStackTrace();
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, "Post Subscription : Exception detected", Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        }
    }


    // Subscription code starts here - Nikita#Sub

    static final String TAG = "TrivialDrive";
    static final String SKU_INFINITE_GAS = "subscribe_app";   //$4.99
    boolean mSubscribedToInfiniteGas = false;
    IabHelper mHelper;

    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

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

        Boolean ssflag = SubscriptionQuery.insertSubscriptionData(sub.getUserId(), sub);

        if (ssflag) {
            preferences.putInt(PrefConstants.UPLOAD_FLAG, 0);
        }

        subscriptionCheck();

    }


    private void inApp() {
        String base64EncodedPublicKey = context.getResources().getString(R.string.basekey);//"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq3i1ShkUzBAWxerhJne2R7KYwWVXyERXLxz7Co0kW9wS45C55XnM/kFHNZ0hI62Oz8HWbTO+RisBMQ5If21sHu5DgXLHa+LNYj+2ZPQWlh46jo/jhMgo+V9YJ7EeOLedH70fFRlhy9OT2ZmOWscxN5YJDp22RXvilale2WcoKVOriS+I9fNbeREDcKM4CsB0isJyDEVIagaRaa0Za8MleOVeYUdma5q3ENZDJ8g9W2Dy0h6fioCZ9OIgBCY63qr0jVxHUwD8Jebp91czKWRSRi433suBmSkoE6qkhwtDEdckeG+cx6xErHcoPSrwhaLlvqCC1KngYduRZy5j1jCAywIDAQAB"; //"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt/vQGFXEB+fQ7s5JbO/teKHjmvkZgqSeLSXmicYu4jDC5mBqfZ1/wBES/lhPGEfJAmjmSSQ1Z35XIcoTL74KVASTrUComknH4XiGaiXCjeCe9cFwYCXlWT+B3Y+dkRajRTi9G/iIgUZP6NTyblmKd5KcUn64CQIqgIZ8pD/4GsIR5abUFTEH9XXQEKzFjcdaBKB4uK1m2JLZ+w+FTFeNydzqSYdRL5lY4IHr8RHZwA3BReNMpzPt1Zp7URSkAGjXvbpOkURupUP+hB4VBYQYPfHfx3K4m32XKWl8zP0qwHS2kIIAjAEekzN+l+bDAU9fXdkDKuHIeXA0HLC6i9jRkQIDAQAB";

        // Some sanity checks to see if the developer (that's you!) really followed the
        // instructions to run this sample (don't put these checks on your app!)
        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
        }
        if (context.getPackageName().startsWith("com.example")) {
            throw new RuntimeException("Please change the sample's package name! See README.");
        }

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(context, base64EncodedPublicKey);

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
//                    alert("Problem setting up in-app billing: " + result);
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


    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {


            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
//                complain("Failed to query inventory: " + result);
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

                //Toast.makeText(context, "SUB_DATA\nTID : " + purchase.getToken() + "\nSdate : " + startdate + "\nEdate : " + enddate + "\nUID : " + userid, Toast.LENGTH_LONG).show();

                SubscrptionData sub = new SubscrptionData();
                sub.setSource("Android");
                sub.setEndDate(enddate);
                sub.setStartDate(startdate);
                sub.setTransactionID(purchase.getToken());
                sub.setUserId(userid);
                sub.setEmail(email);

                initBGProcess(sub);

            } else {
                moveToLogin();
//                complain("Kindly Subscribe");
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };


//    public Date getSubscriptionRenewingDate(String sku) {
//
//        // Get the Purchase object:
//        Purchase purchase = null;
//        Purchase.PurchasesResult purchasesResult = _billingClient.queryPurchases(BillingClient.SkuType.SUBS);
//        if (purchasesResult.getPurchasesList() != null) {
//            for (Purchase p : purchasesResult.getPurchasesList()) {
//                if (p.getSku().equals(sku) && p.getPurchaseState() == Purchase.PurchaseState.PURCHASED && p.isAutoRenewing()) {
//                    purchase = p;
//                    break;
//                }
//            }
//        }
//
//        // Get the SkuDetails object:
//        SkuDetails skuDetails = null;
//        for (SkuDetails s : _skuDetails) { // _skuDetails is an array of SkuDetails retrieved with querySkuDetailsAsync
//            if (s.getSku().equals(sku)) {
//                skuDetails = s;
//                break;
//            }
//        }
//
//        if (purchase != null && skuDetails != null) {
//
//            Date purchaseDate = new Date(purchase.getPurchaseTime());
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(purchaseDate);
//
//            Date now = new Date();
//
//            while (calendar.getTime().before(now)) {
//
//                switch (skuDetails.getSubscriptionPeriod()) {
//
//                    case "P1W": calendar.add(Calendar.HOUR, 7*24); break;
//                    case "P1M": calendar.add(Calendar.MONTH, 1); break;
//                    case "P3M": calendar.add(Calendar.MONTH, 3); break;
//                    case "P6M": calendar.add(Calendar.MONTH, 6); break;
//                    case "P1Y": calendar.add(Calendar.YEAR, 1); break;
//                }
//            }
//
//            return calendar.getTime();
//        }
//
//        return null;
//    }


// Subscription code ends here - Nikita#Sub
}