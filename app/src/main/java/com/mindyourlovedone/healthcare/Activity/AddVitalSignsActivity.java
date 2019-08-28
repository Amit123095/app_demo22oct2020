package com.mindyourlovedone.healthcare.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DashBoard.DateClass;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentVitalSigns;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.VitalQuery;
import com.mindyourlovedone.healthcare.model.Appoint;
import com.mindyourlovedone.healthcare.model.VitalSigns;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddVitalSignsActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout tilLocation;
    TextView txtTitle, txtLocation, txtDate, txtTime, txtBP, txtHeart, txtTemperature, txtPulseRate, txtRespRate, txtNote,txtOther,txtCol, txtSave;
    ImageView imgHome, imgBack;
    Context context = this;
    String location = "", Date = "", time = "", bp = "", heart = "", temperature = "", pulse = "", respiratory = "", note = "",oter = "",col = "";
    Preferences preferences;
    DBHelper dbHelper;
    int id, colid;
    VitalSigns vitalSigns;
    boolean isUpdate=false;
    FragmentVitalSigns fragmentVitalSigns = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vital_signs);
        initComponent();
        initUi();
        initListener();
        FragmentData();
    }


    private void initComponent() {
        try {
            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                String date = intent.getExtras().getString("Date");
                assert date != null;
                if (date.equalsIgnoreCase("Date")) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
                    String formattedDate = df.format(c.getTime());
                    txtDate = findViewById(R.id.txtDate);
                    txtDate.setText(formattedDate);
                }
                String time = intent.getExtras().getString("Time");
                if (time.equalsIgnoreCase("Time")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                    String currentDateandTime = sdf.format(new Date());
                    txtTime = findViewById(R.id.txtTime);
                    txtTime.setText(currentDateandTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        VitalQuery p = new VitalQuery(context, dbHelper);
    }

    private void initUi() {
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        tilLocation = findViewById(R.id.tilLocation);

        txtLocation = findViewById(R.id.txtLocation);


        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtBP = findViewById(R.id.txtBP);
        txtHeart = findViewById(R.id.txtHeart);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtPulseRate = findViewById(R.id.txtPulseRate);
        txtRespRate = findViewById(R.id.txtRespRate);
        txtNote = findViewById(R.id.txtNote);
        txtOther = findViewById(R.id.txtOther);
        txtCol = findViewById(R.id.txtCol);

        txtSave = findViewById(R.id.txtSave);
        txtTitle = findViewById(R.id.txtTitle);


        try {
            Intent intent1 = getIntent();
            if (intent1.getExtras() != null) {

                VitalSigns vi = (VitalSigns) intent1.getExtras().getSerializable("VitalEdit");
                vitalSigns = (VitalSigns) intent1.getExtras().getSerializable("VitalEdit");

                isUpdate = intent1.getExtras().getBoolean("IsUpdate");
                if (isUpdate == false) {
                    txtTitle.setText("Add Vital Sign");
                }else{
                    txtTitle.setText("Update Vital Sign");
                }
colid=vi.getId();
                if (vi.getLocation() != null) {
                    txtLocation.setText(vi.getLocation());
                }
                if (vi.getDate() != null) {
                    txtDate.setText(vi.getDate());
                }
                if (vi.getTime() != null) {
                    txtTime.setText(vi.getTime());
                }
                if (vi.getBp() != null) {
                    txtBP.setText(vi.getBp());
                }
                if (vi.getHeartRate() != null) {
                    txtHeart.setText(vi.getHeartRate());
                }
                if (vi.getTemperature() != null) {
                    txtTemperature.setText(vi.getTemperature());
                }
                if (vi.getPulseRate() != null) {
                    txtPulseRate.setText(vi.getPulseRate());
                }
                if (vi.getRespRate() != null) {
                    txtRespRate.setText(vi.getRespRate());
                }
                if (vi.getNote() != null) {
                    txtNote.setText(vi.getNote());
                }
                if (vi.getOther() != null) {
                    txtOther.setText(vi.getOther());
                }
                if (vi.getCol() != null) {
                    txtCol.setText(vi.getCol());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initListener() {
        imgHome.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
    }
    private void FragmentData() {
        fragmentVitalSigns = new FragmentVitalSigns();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtSave:
                if (validate()) {
                    if (isUpdate == false) {
                        Boolean flag = VitalQuery.insertVitalData(preferences.getInt(PrefConstants.CONNECTED_USERID), location, Date, time, bp, heart, temperature, pulse, respiratory, note,oter,col);
                        if (flag == true) {
                            Toast.makeText(context, "Vital Signs Added Succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(AddVitalSignsActivity.this);
                            clearData();
                            // fragmentVitalSigns.getData();
                            // fragmentVitalSigns.setListData();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else if (isUpdate == true) {
                        Boolean flag = VitalQuery.updateVitalData(colid, location, Date, time, bp, heart, temperature, pulse, respiratory, note,oter,col);
                        if (flag == true) {
                            Toast.makeText(context, "Vital Signs Updated Succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(AddVitalSignsActivity.this);
                            clearData();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "Im not here sorry..!!!", Toast.LENGTH_SHORT).show();
                }

               break;
        }
    }

    private boolean validate() {
        location = txtLocation.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        time = txtTime.getText().toString().trim();
        bp = txtBP.getText().toString().trim();

        heart = txtHeart.getText().toString().trim();
        temperature = txtTemperature.getText().toString().trim();
        pulse = txtPulseRate.getText().toString().trim();
        respiratory = txtRespRate.getText().toString().trim();
        note = txtNote.getText().toString().trim();
        oter = txtOther.getText().toString().trim();
        col = txtCol.getText().toString().trim();

      /*  if (Date.equals("")) {
            Toast.makeText(context, "Please Enter Date", Toast.LENGTH_SHORT).show();
            txtDate.setError("Please Enter Date");
        } else if (time.equals("")) {
            Toast.makeText(context, "Please Enter Time", Toast.LENGTH_SHORT).show();
            txtTime.setError("Please Enter Time");
        } else*/ if (bp.equals("")&&heart.equals("")&&temperature.equals("")) {
            Toast.makeText(context, "Please enter atleast one information among BP, Heart Rate, Temperature", Toast.LENGTH_SHORT).show();
           // txtBP.setError("Please Enter BP");
        } /*else if (heart.equals("")) {
            Toast.makeText(context, "Please Enter Heart Rate", Toast.LENGTH_SHORT).show();
            txtHeart.setError("Please Enter Heart Rate");
        } else if (temperature.equals("")) {
            Toast.makeText(context, "Please Enter Temperature", Toast.LENGTH_SHORT).show();
            txtTemperature.setError("Please Enter Temperature");
        } */else {
            return true;
        }

        return false;
    }

    private void showDateDialog() {
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

                java.util.Date datePickerDate = new Date(selectedMilli);
                String reportDate = new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                DateClass d = new DateClass();
                d.setDate(reportDate);
                txtDate.setText(reportDate);
               /* if (datePickerDate.after(calendar.getTime())) {
                    Toast.makeText(context, "Date should be greater than today's date", Toast.LENGTH_SHORT).show();
                } else {
                    txtDate.setText(reportDate);
                }*/
            }
        }, year, month, day);
        dpd.show();
    }

    private void clearData() {
        txtLocation.setText("");
        txtDate.setText("");
        txtTime.setText("");
        txtLocation.setText("");
        txtBP.setText("");
        txtHeart.setText("");
        txtTemperature.setText("");
        txtPulseRate.setText("");
        txtRespRate.setText("");
        txtLocation.setText("");
        txtNote.setText("");
        txtCol.setText("");
        txtOther.setText("");
    }
}

/*else if (isUpdate == true) {
                    Boolean flag = VitalQuery.updateVitalData(vitalSigns.getId(), location, Date, time, bp, heart, temperature, pulse, respiratory, note);
                    if (flag == true) {
                        Toast.makeText(context, "Vital Signs Updated Succesfully", Toast.LENGTH_SHORT).show();
                        DialogManager.closeKeyboard(AddVitalSignsActivity.this);
                        clearData();
                        finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }*/


               /*else {

                    Intent i = getIntent();
                    if (i.getExtras() != null) {

                        boolean update = i.getExtras().getBoolean("IsEDIT");
                        if (update == true) {
                            Boolean flag = VitalQuery.updateVitalData(colid, location, Date, time, bp, heart, temperature, pulse, respiratory, note);
                            if (flag == true) {
                                Toast.makeText(context, "Vital Signs Updated Succesfully", Toast.LENGTH_SHORT).show();
                                DialogManager.closeKeyboard(AddVitalSignsActivity.this);
                                clearData();
                                // fragmentVitalSigns.getData();
                                //  fragmentVitalSigns.setListData();
                                finish();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } else {
                            Boolean flag = VitalQuery.insertVitalData(preferences.getInt(PrefConstants.CONNECTED_USERID), location, Date, time, bp, heart, temperature, pulse, respiratory, note);
                            if (flag == true) {
                                Toast.makeText(context, "Vital Signs Added Succesfully", Toast.LENGTH_SHORT).show();
                                DialogManager.closeKeyboard(AddVitalSignsActivity.this);
                                clearData();
                                // fragmentVitalSigns.getData();
                                // fragmentVitalSigns.setListData();
                                finish();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                }*/
