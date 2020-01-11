package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.webservice.CustomDialog;
import com.mindyourlovedone.healthcare.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by welcome on 11/17/2017.
 */

public class FaxCustomDialog extends Dialog implements
        android.view.View.OnClickListener {
    TextView textmsg;
    Preferences preferences;
    private TextView btn_ok;
    private String path;
    private String number;
    private EditText editnumber;
    private TextView btn_cancel;
    private EditText editto;
    private EditText editfrom;
    private EditText editsubject;
    private EditText editReply;
    private String to;
    private String from;
    private String subject;
    private String reply;
    RelativeLayout rlMain;
    ScrollView scroll;
    private Context context;

    /**
     * @param context
     */
    public FaxCustomDialog(final Context context, String path) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_fax);

        preferences = new Preferences(context);
        this.path = path;
        scroll = findViewById(R.id.scroll);
        scroll.setOnClickListener(this);
        rlMain = findViewById(R.id.rlMain);
        rlMain.setOnClickListener(this);
        btn_ok = findViewById(R.id.btnYes);
        btn_cancel = findViewById(R.id.btnNo);
        editnumber = findViewById(R.id.etFaxnumber);
        editto = findViewById(R.id.etTo);
        editfrom = findViewById(R.id.etFrom);
        editsubject = findViewById(R.id.etSubject);
        editReply = findViewById(R.id.etReply);

        int lastIndex = path.lastIndexOf("/");
        //  int prevIndex = path.lastIndexOf("/", lastIndex);
        String laststringa = path.substring(lastIndex + 1).trim();
        if (laststringa != null) {
            editsubject.setText(preferences.getString(PrefConstants.CONNECTED_NAME) + " - " + laststringa);
        } else {
            editsubject.setText("");
        }


        // textmsg.setText(""+msg);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scroll:
                keyboardKeyboard();
                break;
            case R.id.rlMain:
                keyboardKeyboard();
                break;

            case R.id.btnYes:

                if (validation()) {
                    this.dismiss();
                    new ServiceAsyncTask().execute();
                }
                break;

            case R.id.btnNo:

                this.dismiss();
                break;

        }

    }

    private void keyboardKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editnumber.getWindowToken(), 0);

    }

    private boolean validation() {
        number = editnumber.getText().toString();
        to = editto.getText().toString();
        from = editfrom.getText().toString();
        subject = editsubject.getText().toString();
        reply = editReply.getText().toString();

        if (number.equalsIgnoreCase("")) {
            editnumber.setError("Field cannot be left blank.");
            return false;
        } else if (number.length() < 1) {
            editnumber.setError("Please enter valid Fax number");
            return false;
        } else if (to.equalsIgnoreCase("")) {
            editto.setError("Field cannot be left blank.");
            return false;
        } else if (from.equalsIgnoreCase("")) {
            editfrom.setError("Field cannot be left blank.");
            return false;
        } else if (subject.equalsIgnoreCase("")) {
            editsubject.setError("Field cannot be left blank.");
            return false;
        } else if (reply.equalsIgnoreCase("")) {
            editReply.setError("Field cannot be left blank.");
            return false;
        }
        return true;
    }

    public class ServiceAsyncTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Fax Sending...");
            dialog.setCancelable(false);

            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Shradha
            return WebService.uploadFile(path, "001" + number, to, from, subject, reply,
                    context);
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            if (dialog.isShowing()) {
                dialog.cancel();
            }

            try {
                JSONObject jObj = new JSONObject(responce);

                JSONObject jObj1 = jObj.getJSONObject("response");

                String errorcode = jObj1.optString("errorCode");

                if (errorcode.equalsIgnoreCase("0")) {

                    String respmsg = jObj1.optString("respMsg");
                    Log.v("RES", respmsg);
                    System.out.println("" + respmsg);
                    CustomDialog.createCustomDialog(context, "Note", respmsg, "")
                            .show();

                } else {

                    String errormsg = jObj1.optString("errorMsg");
                    System.out.println("" + errormsg);
                    CustomDialog.createCustomDialog(context, "Note", errormsg, "")
                            .show();

                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
