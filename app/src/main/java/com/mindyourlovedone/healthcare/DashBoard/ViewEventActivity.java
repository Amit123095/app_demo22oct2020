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

public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    ImageView imgBack, imgEdit,imgHome;
    EditText etNote;
    TextView txtDate, txtSave, txtTitle, txtDelete;
    int id, userid;
    Note note;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        //  getData();
        initUI();
        initListener();
        initComponent();
    }

    private void initComponent() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if(intent.hasExtra("NEW")){
                txtTitle.setText("Add Event Notes");
                txtDelete.setVisibility(View.GONE);
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy - hh:mm a");
                String currentDateandTime = sdf.format(new Date());
                txtDate.setText(currentDateandTime);
            }else {
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

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        txtDelete.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        imgHome.setOnClickListener(this);
    }

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
                String notes = etNote.getText().toString();
                String dates = txtDate.getText().toString();
                if(getIntent().hasExtra("NEW")) {
                    if (notes.equals(""))
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
                    if (note.getTxtNote().equals(notes))
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
            case R.id.txtSave:
                if(!getIntent().hasExtra("NEW")) {
                    String note = etNote.getText().toString();
                    String date = txtDate.getText().toString();
                    Boolean flag = EventNoteQuery.updateEvent(id, note, date);
                    if (flag == true) {
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
                }else{
                    String note = etNote.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy - hh:mm a");
                    String currentDateandTime = sdf.format(new Date());
                    if (note.length() != 0) {
                        Boolean flag = EventNoteQuery.insertNoteData(new Preferences(ViewEventActivity.this).getInt(PrefConstants.CONNECTED_USERID), note, currentDateandTime);
                        if (flag == true) {
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
