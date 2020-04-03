package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.DocumentQuery;
import com.mindyourlovedone.healthcare.database.EventNoteQuery;
import com.mindyourlovedone.healthcare.model.Document;
import com.mindyourlovedone.healthcare.model.Note;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class: ViewEventActivity
 * Screen: Add,Edit Event Note
 * A class that manages to Add, display event and edit
 * implements OnclickListener for onClick event on views
 */
public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    ImageView imgBack, imgEdit, imgHome;
    EditText etNote;
    TextView txtDate, txtSave, txtTitle, txtDelete;
    int id, userid;
    Note note;

    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();
        //Initialize database, get primary data and set data
        initComponent();
    }

    /**
     * Function: Initialize initial data , database ,prefrences
     */
    private void initComponent() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.hasExtra("NEW")) {
                txtTitle.setText("Add Event Notes");
                txtDelete.setVisibility(View.GONE);
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy - hh:mm a");
                String currentDateandTime = sdf.format(new Date());
                txtDate.setText(currentDateandTime);
            } else {
                note = (Note) intent.getExtras().getSerializable("NoteObject");
                txtTitle.setText("Edit Event Notes");
                String notes = note.getTxtNote();
                String dates = note.getTxtDate();
                id = note.getId();
                userid = note.getUserid();
                etNote.setText(notes);
                txtDate.setText(dates);
            }
        }
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgBack.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        txtDelete.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        imgHome.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        imgHome = findViewById(R.id.imgHome);
        imgBack = findViewById(R.id.imgBack);
        imgEdit = findViewById(R.id.imgEdit);
        txtDelete = findViewById(R.id.txtDelete);
        etNote = findViewById(R.id.etNote);
        txtDate = findViewById(R.id.txtDate);
        txtSave = findViewById(R.id.txtSave);
        txtTitle = findViewById(R.id.txtTitle);
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
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
                // Navigate previous screen after checking data modification done or not, if yes it ask user to save
                String notes = etNote.getText().toString();
                if (getIntent().hasExtra("NEW")) {
                    if (notes.equals("")) {
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
                    if (note.getTxtNote().equals(notes)) {
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
                //finish();
                break;
            case R.id.txtSave:// Save Event
                if (!getIntent().hasExtra("NEW")) {
                    String note = etNote.getText().toString();
                    String date = txtDate.getText().toString();
                    Boolean flag = EventNoteQuery.updateEvent(id, note, date);
                    if (flag == true) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("Edit_Event",1);
                        mFirebaseAnalytics.logEvent("OnClick_Save_EventNote", bundle);

                        Toast.makeText(context, "Event Note has been updated succesfully", Toast.LENGTH_SHORT).show();
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String note = etNote.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy - hh:mm a");
                    String currentDateandTime = sdf.format(new Date());
                    if (note.length() != 0) {
                        Boolean flag = EventNoteQuery.insertNoteData(new Preferences(ViewEventActivity.this).getInt(PrefConstants.CONNECTED_USERID), note, currentDateandTime);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("Add_Event",1);
                            mFirebaseAnalytics.logEvent("OnClick_Save_EventNote", bundle);

                            Toast.makeText(context, "Event Note has been saved succesfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Enter Note", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.txtDelete:
                deleteNote();
                break;
        }
    }

    /**
     * Function: Delete Event record
     */
    private void deleteNote() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flags = EventNoteQuery.deleteRecord(id);
                if (flags == true) {
                    Toast.makeText(context, "Event Note has been deleted succesfully", Toast.LENGTH_SHORT).show();
                }
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
}
