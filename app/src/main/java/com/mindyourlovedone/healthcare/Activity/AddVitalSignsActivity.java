package com.mindyourlovedone.healthcare.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
/**
 * Class: AddVitalSignsActivity
 * Screen: Add Vital Signs Screen
 * A class that manages to add new vital sin
 * implements OnclickListener for onClick event on views
 */
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
        //Initialize database, get primary data and set data
        initComponent();

        //Initialize user interface view and components
        initUi();
        //Register a callback to be invoked when this views are clicked.
        initListener();

        //Initialize Fragments
        FragmentData();
    }

    /**
     * Function: Initialize preferences and database
     */
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

    /**
     * Function: Initialize user interface view and components
     */
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

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
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

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHome:// Navigate to home screen
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
            case R.id.imgBack: // navigate previous screen after checking data modification done or not, if yes it ask user to save

                getValues();

                if(isUpdate==false) {
                    if (location.equals("")&&bp.equals("")&&heart.equals("")&&temperature.equals("")&&
                            pulse.equals("")&&respiratory.equals("")&&note.equals("")&&note.equals("")&&
                            oter.equals("")&&col.equals(""))
                    {
                        finish();
                    }
                    else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Save");
                        alert.setMessage("Do you want to save information?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                txtSave.performClick();

                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        alert.show();
                    }

                }
                else{

                    if (vitalSigns.getLocation().equals(location)&&vitalSigns.getBp().equals(bp)&&
                            vitalSigns.getHeartRate().equals(heart)&&vitalSigns.getTemperature().equals(temperature)&&
                            vitalSigns.getPulseRate().equals(pulse)&&vitalSigns.getRespRate().equals(respiratory)&&
                            vitalSigns.getNote().equals(note)&&vitalSigns.getOther().equals(oter)&&
                            vitalSigns.getCol().equals(col))
                    {
                        finish();
                    }
                    else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Save");
                        alert.setMessage("Do you want to save information?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                txtSave.performClick();

                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        alert.show();
                    }
                }
                //finish();
                break;
            case R.id.txtSave://insert or update
                //Validate if user input is valid or not, If true then goes for next task
                if (validate()) {
                    if (isUpdate == false) {
                        Boolean flag = VitalQuery.insertVitalData(preferences.getInt(PrefConstants.CONNECTED_USERID), location, Date, time, bp, heart, temperature, pulse, respiratory, note,oter,col);
                        if (flag == true) {
                            Toast.makeText(context, "Vital Signs has been saved successfully", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context, "Vital Signs has been updated successfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(AddVitalSignsActivity.this);
                            clearData();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Toast.makeText(context, "Im not here sorry..!!!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

   /**
     * Function: Validation of data input by user
     * @return boolean, True if given input is valid, false otherwise.
     */
    private boolean validate() {
        getValues();
        if (bp.equals("")&&heart.equals("")&&temperature.equals("")) {
            Toast.makeText(context, "Please enter atleast one information among BP, Heart Rate, Temperature", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }

        return false;
    }
    /**
     * Function - Get values from all elements
     */
    private void getValues() {
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

