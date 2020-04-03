package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindyourlovedone.healthcare.Activity.RelationshipActivity;
import com.mindyourlovedone.healthcare.Connections.RelationActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.database.AppointmentQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DateQuery;
import com.mindyourlovedone.healthcare.model.Appoint;
import com.mindyourlovedone.healthcare.model.TypeSpecialist;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Class: AddAppointmentActivity
 * Screen: Add Medical Appointment Screen
 * A class that manages routine appointments details
 * implements OnclickListener for onClick event on views
 */
public class AddAppointmentActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    TextView txtRelation, txtFrequency, txtName, txtNote, txtDate, txtOtherSpecialist, txtOtherFrequency, txtAdd, txtSave;
    Preferences preferences;
    MySpinner spinnerType, spinnerFrequency;
    DBHelper dbHelper;
    ArrayList<DateClass> dateList = null;
    ImageView imgBack, imgHome;
    RelativeLayout llAddConn, rlDelete;
    TextInputLayout tilName, tilOtherFrequency, tilOtherSpecialist, tilOtherType;
    RadioGroup rgCompleted;
    RadioButton rbYes, rbNo;
    String otherFrequency = "";
    String otherType = "";
    String status = "No";
    boolean isUpdate = false;
    Appoint p;
    private static int RESULT_TYPE = 10;
    private static int RESULT_FREQUENCY = 110;
    String[] Type3 = {"", "Type of Test", "Blood Work", "Colonoscopy", "CT Scan", "Echocardiogram", "EKG", "Glucose Test"};
    String[] Type1 = {"Hyperthyroid Blood Test", "Hypothyroid Blood Test", "Mammogram", "MRI", "Prostate Specific Antigen (PSA)", "Sonogram", "Thyroid Scan", "",
    };
    private FirebaseAnalytics mFirebaseAnalytics;

    private ArrayList<TypeSpecialist> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Initialize database, get primary data and set data
        initComponent();

        //Initialize UI
        initUi();
        //Register a callback to be invoked when this views are clicked.
        initListener();

    }
    /**
     * Function: Initialize database and preferences
     */
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        AppointmentQuery a = new AppointmentQuery(context, dbHelper);
        DateQuery d = new DateQuery(context, dbHelper);
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {

        rlDelete.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtNote.setOnClickListener(this);
        txtRelation.setOnClickListener(this);
        txtFrequency.setOnClickListener(this);

    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUi() {

        txtRelation = findViewById(R.id.txtRelation);
        txtRelation.setFocusable(false);
        txtFrequency = findViewById(R.id.txtFrequency);
        txtFrequency.setFocusable(false);
        txtSave = findViewById(R.id.txtSave);
        txtName = findViewById(R.id.txtName);
        tilName = findViewById(R.id.tilName);
        txtDate = findViewById(R.id.txtDate);
        txtNote = findViewById(R.id.txtNote);
        txtOtherFrequency = findViewById(R.id.txtOtherFrequency);
        txtOtherSpecialist = findViewById(R.id.txtOtherType);
        tilOtherFrequency = findViewById(R.id.tilOtherFrequency);
        tilOtherSpecialist = findViewById(R.id.tilOtherType);
        tilOtherSpecialist.setHint("Other Specialist or Test");
        spinnerType = findViewById(R.id.spinnerType);
        spinnerFrequency = findViewById(R.id.spinnerFrequency);
        imgBack = findViewById(R.id.imgBack);
        rlDelete = findViewById(R.id.rlDelete);
        imgHome = findViewById(R.id.imgHome);
        llAddConn = findViewById(R.id.llAddConn);
        txtAdd = findViewById(R.id.txtAdd);

        rgCompleted = findViewById(R.id.rgCompleted);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        items = new ArrayList<TypeSpecialist>();


        //shradha
        for (int i = 0; i < Type3.length; i++) {

            TypeSpecialist ts = new TypeSpecialist();
            ts.setType(Type3[i]);
            if (i == 1) {
                ts.setDiff(99);
            } else {
                ts.setDiff(0);
            }
            items.add(ts);
        }

        for (int i = 0; i < Type1.length; i++) {
            TypeSpecialist ts = new TypeSpecialist();
            ts.setType(Type1[i]);
            ts.setDiff(0);
            items.add(ts);
        }


        rgCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYes) {
                    txtDate.setVisibility(View.VISIBLE);
                    status = "Yes";
                } else if (checkedId == R.id.rbNo) {
                    txtDate.setVisibility(View.GONE);
                    status = "No";
                }
            }
        });

        Intent i = getIntent();
        if (i.getExtras() != null) {
            if (i.getExtras().get("FROM").equals("View")) {
                rlDelete.setVisibility(View.VISIBLE);
                txtAdd.setText("Update Appointment");
                isUpdate = true;
                Appoint a = (Appoint) i.getExtras().getSerializable("AppointObject");
                p = (Appoint) i.getExtras().getSerializable("AppointObject");
                if (a.getType() != null) {
                    txtRelation.setText(a.getType());
                    if (a.getType().equals("Other")) {

                        tilOtherSpecialist.setVisibility(View.VISIBLE);
                        txtOtherSpecialist.setText(a.getOtherDoctor());

                    } else {
                        tilOtherSpecialist.setVisibility(View.GONE);
                        txtOtherSpecialist.setText("");
                    }
                }
                if (a.getFrequency() != null) {
                    txtFrequency.setText(a.getFrequency());
                    if (a.getFrequency().equals("Other")) {
                        txtOtherFrequency.setText(a.getOtherFrequency());
                        tilOtherFrequency.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherFrequency.setVisibility(View.GONE);
                        txtOtherFrequency.setText("");
                    }
                }


                if (a.getDoctor() != null) {
                    txtName.setText(a.getDoctor());
                }
                if (a.getNote() != null) {
                    txtNote.setText(a.getNote());
                }

            } else if (i.getExtras().get("FROM").equals("Add")) {
                txtAdd.setText("Add Routine Appointment");
                isUpdate = false;
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_TYPE && data != null) {// Selected specialist or type
            String type = data.getExtras().getString("TypeAppointment");
            if (type.equals("Other")) {
                txtRelation.setText(type);
                tilOtherSpecialist.setVisibility(View.VISIBLE);
            } else {
                txtRelation.setText(type);
                tilOtherSpecialist.setVisibility(View.GONE);
                txtOtherSpecialist.setText("");
            }
        } else if (requestCode == RESULT_FREQUENCY && data != null) {//Selected Frequency
            String freq = data.getExtras().getString("Category");
            txtFrequency.setText(freq);

            if (freq.equals("Other")) {
                tilOtherFrequency.setVisibility(View.VISIBLE);
                txtOtherFrequency.setVisibility(View.VISIBLE);
            } else {
                tilOtherFrequency.setVisibility(View.GONE);
                txtOtherFrequency.setVisibility(View.GONE);
                txtOtherFrequency.setText("");
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
            case R.id.txtRelation://Select Specialist or test
                Intent intentType = new Intent(context, RelationshipActivity.class);
                intentType.putExtra("Category", "TypeAppointment");
                intentType.putExtra("Selected", txtRelation.getText().toString());
                startActivityForResult(intentType, RESULT_TYPE);
                break;
            case R.id.txtFrequency://Select frequency
                Intent intentFrequency = new Intent(context, RelationActivity.class);
                intentFrequency.putExtra("Category", "TypeFrequency");
                startActivityForResult(intentFrequency, RESULT_FREQUENCY);
                break;

            case R.id.imgBack://Move to back screen
                String types = txtRelation.getText().toString().trim();
                String frequencys = txtFrequency.getText().toString().trim();
                String names = txtName.getText().toString().trim();
                //String dates = txtDate.getText().toString().trim();
                String notes = txtNote.getText().toString().trim();
                otherType = txtOtherSpecialist.getText().toString();
                otherFrequency = txtOtherFrequency.getText().toString();
                if (isUpdate == false) {
                    if (types.equals("") && frequencys.equals("") && names.equals("") && notes.equals("")) {
                        finish();
                    } else {
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
                } else {
                    if (p.getType().equals(types) && p.getOtherDoctor().equals(otherType) &&
                            p.getFrequency().equals(frequencys) && p.getOtherFrequency().equals(otherFrequency) &&
                            p.getNote().equals(notes) && p.getDoctor().equals(names)) {
                        finish();
                    } else {
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

                break;
            //Shradha
            case R.id.rlDelete:
                deleteAppointment(p);
                break;

            case R.id.imgHome://Move to Home profile list screen
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;

            case R.id.txtDate: //select date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;
           /* case R.id.txtNote:

                break;*/
            case R.id.txtSave:// Save information
                hideSoftKeyboard();
                int unique = generateRandom();
                String type = txtRelation.getText().toString().trim();
                String frequency = txtFrequency.getText().toString().trim();
                String name = txtName.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String note = txtNote.getText().toString().trim();
                otherType = txtOtherSpecialist.getText().toString();
                otherFrequency = txtOtherFrequency.getText().toString();

                if (isUpdate == false) {
                    Boolean flag = AppointmentQuery.insertAppointmentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, date, note, type, frequency, otherType, otherFrequency, dateList, unique);
                    if (flag == true) {
                        hideSoftKeyboard();
                       Bundle bundle = new Bundle();
                        bundle.putInt("Add_Appointment", 1);
                        mFirebaseAnalytics.logEvent("OnClick_Save_Appointment", bundle);


                        Toast.makeText(context, "Routine Appointment has been saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else if (isUpdate == true) {
                    Boolean flag = AppointmentQuery.updateAppointmentData(p.getId(), name, date, note, type, frequency, otherType, otherFrequency, dateList, p.getUnique());
                    if (flag == true) {
                        hideSoftKeyboard();
                        Bundle bundle = new Bundle();
                        bundle.putInt("Edit_Appointment", 1);
                        mFirebaseAnalytics.logEvent("OnClick_Save_Appointment", bundle);

                        Toast.makeText(context, "Routine Appointment has been updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    /**
     * Function: Delete selected appointment record
     */
    private void deleteAppointment(final Appoint p) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = AppointmentQuery.deleteRecord(p.getUnique());
                if (flag == true) {
                    Toast.makeText(context, "Routine Appointment has been deleted successfully", Toast.LENGTH_SHORT).show();

                    if (context instanceof MedicalAppointActivity) {
                        ((MedicalAppointActivity) context).getData();
                        ((MedicalAppointActivity) context).setNoteData();
                    }
                }
                dialog.dismiss();
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();
    }

    private int generateRandom() {
        Random r = new Random();
        int randomNumber = r.nextInt(500);

        return randomNumber;
    }

    /**
     * Function: Hide device keyboard.
     */
    public void hideSoftKeyboard() {
        InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
