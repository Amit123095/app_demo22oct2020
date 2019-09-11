package com.mindyourlovedone.healthcare.utility;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;

import com.mindyourlovedone.healthcare.HomeActivity.LoginActivity;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.database.SubscriptionQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.model.SubscrptionData;
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

            if (userid == 0) {
                RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
                userid = connection.getUserid();
            }

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
                if (preferences.getInt(PrefConstants.FROM_Dropbox) == 1) {
                    preferences.putInt(PrefConstants.FROM_Dropbox, 0);
                    getSubscription();
                } else {
                    moveToLogin();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Indicate success or failure with your return value:
        return WorkerResult.SUCCESS;

        // (Returning RETRY tells WorkManager to try context task again
        // later; FAILURE says not to try again.)
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
            String result = webService.getSubscriptionData(sub.getUserId() + "");
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
                                moveToLogin();
                            }
                        } else {
                            moveToLogin();
                        }
                    } else {
                        moveToLogin();
                    }
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "Success : Get Subscription", Toast.LENGTH_LONG).show();
//                        }
//                    });
                } else {

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
}