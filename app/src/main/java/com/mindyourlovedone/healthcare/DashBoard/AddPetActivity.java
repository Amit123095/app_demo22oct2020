package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.PetQuery;
import com.mindyourlovedone.healthcare.model.Pet;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Class: AddPetActivity
 * Screen: Add Pet Screen
 * A class that manages to Add, Update Pet details
 * implements OnclickListener for onClick event on views
 */
public class AddPetActivity extends AppCompatActivity {
    public static final int REQUEST_PET = 400;
    Context context = this;
    TextView txtTitle, txtAdd,txtSave;
    TextView txtDelete;
    TextView txtName, txtBreed, txtColor, txtChip, txtVeterian, txtCare, txtPetBirthDate, txtPetNotes;
    String name = "", breed = "", color = "", veterain = "", care = "", chip = "", bdate = "", notes = "",veterain_add="",veterain_ph="",care_add="",care_ph="";
    ImageView imgBack, imgDone,imgHome;
    boolean isUpdate = false;
    RelativeLayout llAddConn;
    Preferences preferences;
    DBHelper dbHelper;
    Pet p;
    Pet pet;
    TextView txtVeteranAd,txtCareAd;
    EditText txtCarePh,txtVeteranPh;

    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Initialize view
        initUi();
    }

    /**
     * Function: Initialize UI and Initial data
     * Initialize database,preferences
     */
    private void initUi() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        PetQuery a = new PetQuery(context, dbHelper);
        txtDelete=findViewById(R.id.txtDelete);
        imgBack = findViewById(R.id.imgBack);
        imgHome=findViewById(R.id.imgHome);
        imgDone = findViewById(R.id.imgDone);
        llAddConn = findViewById(R.id.llAddConn);
        txtSave = findViewById(R.id.txtSave);
        txtTitle = findViewById(R.id.txtTitle);
        txtAdd = findViewById(R.id.txtAdd);
        txtName = findViewById(R.id.txtName);
        txtBreed = findViewById(R.id.txtBreed);
        txtColor = findViewById(R.id.txtColor);
        txtChip = findViewById(R.id.txtChip);
        txtVeterian = findViewById(R.id.txtVeteran);
        txtVeteranAd = findViewById(R.id.txtVeteranAd);
        txtVeteranPh = findViewById(R.id.txtVeteranPh);
        txtCare = findViewById(R.id.txtCare);
        txtCareAd = findViewById(R.id.txtCareAd);
        txtCarePh = findViewById(R.id.txtCarePh);
        txtPetBirthDate = findViewById(R.id.txtPetBirthDate);
        txtPetNotes = findViewById(R.id.txtPetNote);

        imgHome.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
            }
        });
        txtVeteranPh.addTextChangedListener(new CustomTextWatcher(txtVeteranPh));

        txtCarePh.addTextChangedListener(new CustomTextWatcher(txtCarePh));


        Intent i = getIntent();
        if (i.getExtras() != null) {
            if (i.getExtras().get("FROM").equals("Update")) {
                isUpdate = true;
                txtDelete.setVisibility(View.VISIBLE);
                txtAdd.setText("Update Pet");
                txtTitle.setText("Update Pet");
                p = (Pet) i.getExtras().getSerializable("PetObject");
                pet = p;
                if (p.getName() != null) {
                    txtName.setText(p.getName());
                }
                if (p.getBreed() != null) {
                    txtBreed.setText(p.getBreed());
                }
                if (p.getColor() != null) {
                    txtColor.setText(p.getColor());
                }
                if (p.getVeterian() != null) {
                    txtVeterian.setText(p.getVeterian());
                }
                if (p.getVeterian_add() != null) {
                    txtVeteranAd.setText(p.getVeterian_add());
                }
                if (p.getVeterian_ph() != null) {
                    txtVeteranPh.setText(p.getVeterian_ph());
                }
                if (p.getChip() != null) {
                    txtChip.setText(p.getChip());
                }
                if (p.getGuard() != null) {
                    txtCare.setText(p.getGuard());
                }
                if (p.getCare_add() != null) {
                    txtCareAd.setText(p.getCare_add());
                }
                if (p.getCare_ph() != null) {
                    txtCarePh.setText(p.getCare_ph());
                }
                if (p.getBdate() != null) {
                    txtPetBirthDate.setText(p.getBdate());
                }
                if (p.getNotes() != null) {
                    txtPetNotes.setText(p.getNotes());
                }
            } else {
                isUpdate = false;
                txtDelete.setVisibility(View.GONE);
                txtAdd.setText("Add Pet");
                txtTitle.setText("Add Pet");
            }

        }
        txtDelete.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                boolean flag = PetQuery.deleteRecord(p.getId());
                if (flag == true) {
                    Toast.makeText(context, "Pet has been deleted succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        txtPetBirthDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
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

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate = new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                        DateClass d = new DateClass();
                        d.setDate(reportDate);
                        if (datePickerDate.after(calendar.getTime())) {
                            Toast.makeText(context, "Birthdate should be greater than today's date", Toast.LENGTH_SHORT).show();
                        } else {
                            txtPetBirthDate.setText(reportDate);
                        }
                    }
                }, year, month, day);
                dpd.show();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVales();
                name = txtName.getText().toString();
                breed = txtBreed.getText().toString();
                color = txtColor.getText().toString();
                chip = txtChip.getText().toString();
                veterain = txtVeterian.getText().toString();
                veterain_add = txtVeteranAd.getText().toString();
                veterain_ph = txtVeteranPh.getText().toString();
                care = txtCare.getText().toString();
                care_add = txtCareAd.getText().toString();
                care_ph = txtCarePh.getText().toString();
                bdate = txtPetBirthDate.getText().toString();
                notes = txtPetNotes.getText().toString();
                if(isUpdate==false) {
                    if (name.equals("") && breed.equals("") && color.equals("") && chip.equals("")&&
                            veterain.equals("") && veterain_add.equals("") && veterain_ph.equals("") && care.equals("")&&
                            care_add.equals("") && care_ph.equals("") && bdate.equals("") && notes.equals("")) {
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
                else{
                    if (name.equals(p.getName()) && breed.equals(p.getBreed()) && color.equals(p.getColor()) && chip.equals(p.getChip())&&
                            veterain.equals(p.getVeterian()) && veterain_add.equals(p.getVeterian_add()) && veterain_ph.equals(p.getVeterian_ph()) && care.equals(p.getGuard())&&
                            care_add.equals(p.getCare_add()) && care_ph.equals(p.getCare_ph()) && bdate.equals(p.getBdate()) && notes.equals(p.getNotes()))
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

            }
        });
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate if user input is valid or not, If true then goes for next task
                if (validate()) {

                    if (isUpdate == false) {
                        Boolean flag = PetQuery.insertPetData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, breed, color, chip, veterain, care, bdate, notes, veterain_add, veterain_ph, care_add, care_ph);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("Add_Pet",1);
                            mFirebaseAnalytics.logEvent("OnClick_SavePet", bundle);
                            Toast.makeText(context, "Pet has been saved succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else if (isUpdate == true) {
                        Boolean flag = PetQuery.updatePetData(pet.getId(), name, breed, color, chip, veterain, care, bdate, notes, veterain_add, veterain_ph, care_add, care_ph);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("Edit_Pet",1);
                            mFirebaseAnalytics.logEvent("OnClick_SavePet", bundle);
                            Toast.makeText(context, "Pet has been updated succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                    Intent intentAllergy = new Intent();
                    setResult(AddInfoActivity.RESULT_ALLERGY, intentAllergy);
                    finish();
                }
            }
        });
    }

    private void getVales() {
        name = txtName.getText().toString();
        breed = txtBreed.getText().toString();
        color = txtColor.getText().toString();
        chip = txtChip.getText().toString();
        veterain = txtVeterian.getText().toString();
        veterain_add = txtVeteranAd.getText().toString();
        veterain_ph = txtVeteranPh.getText().toString();
        care = txtCare.getText().toString();
        care_add = txtCareAd.getText().toString();
        care_ph = txtCarePh.getText().toString();
        bdate = txtPetBirthDate.getText().toString();
        notes = txtPetNotes.getText().toString();
    }

    /**
     * Function: Validation of data input by user
     * @return boolean, True if given input is valid, false otherwise.
     */
    private boolean validate() {
        name = txtName.getText().toString();
        breed = txtBreed.getText().toString();
        color = txtColor.getText().toString();
        chip = txtChip.getText().toString();
        veterain = txtVeterian.getText().toString();
        veterain_add = txtVeteranAd.getText().toString();
        veterain_ph = txtVeteranPh.getText().toString();
        care = txtCare.getText().toString();
        care_add = txtCareAd.getText().toString();
        care_ph = txtCarePh.getText().toString();
        bdate = txtPetBirthDate.getText().toString();
        notes = txtPetNotes.getText().toString();
        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (breed.equals("")) {
            txtBreed.setError("Please Select Type of Pet");
            DialogManager.showAlert("Please Type of Pet", context);
        }  else return true;
        return false;
    }
    /**
     * Class: CustomTextWatcher
     * Screen: Personal Profile Screen
     * A class that manages hypens from contact number
     */
    public class CustomTextWatcher implements TextWatcher {
        EditText et = null;

        CustomTextWatcher(EditText et) {
            this.et = et;
        }

        int prevL = 0;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            prevL = et.getText().toString().length();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int length = editable.length();
            if ((prevL < length) && (length == 3 || length == 7)) {
                et.setText(editable.toString() + "-");
                et.setSelection(et.getText().length());
            }
        }

    }
}
