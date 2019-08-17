package com.mindyourlovedone.healthcare.DashBoard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.webservice.CustomDialog;
import com.mindyourlovedone.healthcare.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class FaxActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context=this;


    Preferences preferences;

    private String path="";
    private String number="";
    private String to="";
    private String from="";
    private String subject="";
    private String reply="";
    TextView textmsg;
    ImageView imgHome, imgBack;



    private TextView txtSave,etFaxnumber,etTo,etFrom,etSub,etReply;


    RelativeLayout rlMain;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fax);
        initUi();
        initComponent();
        initListener();

    }

    private void initComponent() {
        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            path=i.getExtras().getString("PATH");
        }
        int lastIndex = path.lastIndexOf("/");
        //  int prevIndex = path.lastIndexOf("/", lastIndex);
        String laststringa = path.substring(lastIndex + 1).trim();
        if (laststringa != null) {
            etSub.setText(preferences.getString(PrefConstants.CONNECTED_NAME) + " - " + laststringa);
        } else {
            etSub.setText("");
        }
    }

    private void initListener() {
        imgHome.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        txtSave.setOnClickListener(this);
    }

    private void initUi() {
        preferences = new Preferences(context);


        etFaxnumber = findViewById(R.id.etFaxnumber);
        etTo = findViewById(R.id.etTo);
        etFrom = findViewById(R.id.etFrom);
        etSub = findViewById(R.id.etSubject);
        etReply = findViewById(R.id.etReply);
        imgHome = findViewById(R.id.imgHome);
        imgBack = findViewById(R.id.imgBack);

        txtSave = findViewById(R.id.txtSave);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                finish();
                break;

            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
            case R.id.txtSave:
                if (validation()) {
                   // finish();

                    new ServiceAsyncTask().execute();
                    // WebService.uploadFile(path, number, to, from, subject,
                    // context);
                    // if(editnumber.getText().length()==0){

                    // }

                    // textmsg.setTextColor(context.getResources().getColor(R.color.red_color));

                    // Toast.makeText(context, "Please enter fax number",
                    // Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
    private void keyboardKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etFaxnumber.getWindowToken(), 0);

    }

    private boolean validation() {
        number = etFaxnumber.getText().toString();
        to = etTo.getText().toString();
        from = etFrom.getText().toString();
        subject = etSub.getText().toString();
        reply = etReply.getText().toString();

        if (number.equalsIgnoreCase("")) {
            etFaxnumber.setError("Field cannot be left blank.");
            return false;
        } else if (number.length() < 1) {
            etFaxnumber.setError("Please enter valid Fax number");
            return false;
        } else if (to.equalsIgnoreCase("")) {
            etTo.setError("Field cannot be left blank.");
            return false;
        } else if (from.equalsIgnoreCase("")) {
            etFrom.setError("Field cannot be left blank.");
            return false;
        } else if (subject.equalsIgnoreCase("")) {
            etFaxnumber.setError("Field cannot be left blank.");
            return false;
        } else if (reply.equalsIgnoreCase("")) {
            etReply.setError("Field cannot be left blank.");
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
                if (!responce.equals("")) {
                    JSONObject jObj = new JSONObject(responce);

                    JSONObject jObj1 = jObj.getJSONObject("response");

                    String errorcode = jObj1.optString("errorCode");

                    if (errorcode.equalsIgnoreCase("0")) {

                        String respmsg = jObj1.optString("respMsg");
                        Log.v("RES", respmsg);
                        System.out.println("" + respmsg);
                        CustomDialog.createCustomDialog(context, "Note", respmsg)
                                .show();

                    } else {

                        String errormsg = jObj1.optString("errorMsg");
                        System.out.println("" + errormsg);
                        CustomDialog.createCustomDialog(context, "Note", errormsg)
                                .show();

                    }
                }else{
                    CustomDialog.createCustomDialog(context, "Note", "Unable to send fax, Please try again later!")
                            .show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              //  Toast.makeText(context,"server issue",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
