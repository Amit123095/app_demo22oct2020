package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.EventNoteQuery;
import com.mindyourlovedone.healthcare.model.Note;
import com.mindyourlovedone.healthcare.pdfCreation.EventPdfNew;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Class: EventNoteActivity
 * Screen: Event Note List Screen
 * A class that manages to display list of event notes
 * implements OnclickListener for onClick event on views
 */
public class EventNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    RecyclerView lvNote;
    ArrayList<Note> noteList = new ArrayList<>();
    ImageView imgBack, imgHome, imgAdd, imgEdit, imgRight, imghelp;
    RelativeLayout rlGuide;
    Preferences preferences;
    DBHelper dbHelper;
    TextView txtMsg, txtFTU, txtAdd, txthelp;
    RelativeLayout header, rlEvent;
    ScrollView scrollvw;
    ImageView floatAdd, floatOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_note);

        //Initialize database, get primary data and set data
        initComponent();

        //Fetc Notes
        getData();

        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        txtAdd.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        scrollvw = findViewById(R.id.scrollvw);
        floatAdd = findViewById(R.id.floatAdd);
        floatOptions = findViewById(R.id.floatOptions);
        txtMsg = findViewById(R.id.txtMsg);
        txthelp = findViewById(R.id.txthelp);
        imghelp = findViewById(R.id.imghelp);
        final RelativeLayout relMsg = findViewById(R.id.relMsg);
        TextView txt61 = findViewById(R.id.txtPolicy61);
        TextView txt62 = findViewById(R.id.txtPolicy62);
        TextView txt63 = findViewById(R.id.txtPolicy63);
        TextView txt64 = findViewById(R.id.txtPolicy64);

        //shradha
        txt61.setText(Html.fromHtml("To add a note click on add button at the top right of the screen. Once completed click Add. The note is automatically saved.\n\n"));
        txt62.setText(Html.fromHtml("To <b>edit</b> the note click the picture of the pencil to the right of the screen. To save your edits click the <b>SAVE</b> at the top right of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>delete</b> the note click the garbage can at the bottom of the screen or <b>delete</b> the entry , left swipe the arrow symbol on the <b>right side</b> of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data, click the three dots on the upper right side of the summary screen.\n\n "));

        txtFTU = findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {//First time instruction
                Intent intentEmerInstruc = new Intent(context, InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "EventNotesInstruction");
                startActivity(intentEmerInstruc);
                rlGuide.setVisibility(View.GONE);//nikita
            }
        });
        rlEvent = findViewById(R.id.rlEvent);
        header = findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorEventPink);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);

        txtAdd = findViewById(R.id.txtAdd);
        imgAdd = findViewById(R.id.imgAdd);
        imgRight = findViewById(R.id.imgRight);
        lvNote = findViewById(R.id.lvNote);
        rlEvent.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        rlGuide = findViewById(R.id.rlGuide);
        if (noteList.size() != 0) {
            setNoteData();
        }
        //Changes done by nikita on 20/6/18
        lvNote = findViewById(R.id.lvNote);
        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvNote.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvNote.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        //or
        lvNote.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));
        //...
    }

    /**
     * Function: View Event note in details
     * @param item
     */
    public void imgFrowardClick(final Note item) {
        Intent intent = new Intent(context, ViewEventActivity.class);
        intent.putExtra("NoteObject", item);
        context.startActivity(intent);
    }

    public void datetimeDialog(final Note item) {
        Calendar calendar = Calendar.getInstance();
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

                Boolean flag = EventNoteQuery.updateNoteDate(item.getId(), reportDate);

                if (flag == true) {
                    Toast.makeText(context, "Event Note Date Updated Succesfully", Toast.LENGTH_SHORT).show();
                    getData();
                    setNoteData();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }, year, month, day);
        dpd.show();
    }

    /**
     * Function: Delete selected Note
     */
    public void deleteNote(final Note item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = EventNoteQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(context, "Event Note has been deleted succesfully", Toast.LENGTH_SHORT).show();
                    getData();
                    setNoteData();
                }
                dialog.dismiss();
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

    /**
     * Function: Set all Event note to the list
     */
    private void setNoteData() {
        if (noteList.size() != 0) {
            lvNote.setVisibility(View.VISIBLE);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
            rlGuide.setVisibility(View.GONE);
            scrollvw.setVisibility(View.GONE);
        } else {
            rlGuide.setVisibility(View.VISIBLE);
            imghelp.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
            lvNote.setVisibility(View.GONE);
            scrollvw.setVisibility(View.GONE);
        }
        Collections.reverse(noteList);
        NoteAdapter adapter = new NoteAdapter(context, noteList);
        lvNote.setAdapter(adapter);
    }

    /**
     * Function: Fetch all Event notes record
     */
    private void getData() {
        noteList = EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    /**
     * Function: Initialize database and preferences
     */
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        EventNoteQuery e = new EventNoteQuery(context, dbHelper);
    }

    /**
     * Function: To display floating menu for reports
     */
    private void showFloatDialog() {

        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "EventNote.pdf");
        if (file.exists()) {
            file.delete();
        }
        //New PDF Varsa
        Image pdflogo = null, calendar = null, profile = null, calendarWite = null, profileWite = null;
        pdflogo = preferences.addFile("pdflogo.png", context);
        calendar = preferences.addFile("calpdf.png", context);
        calendarWite = preferences.addFile("calpdf_wite.png", context);
        profile = preferences.addFile("profpdf.png", context);
        profileWite = preferences.addFile("profpdf_wite.png", context);

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "EVENT NOTE", calendarWite, profileWite);

        HeaderNew.addusereNameChank("EVENT NOTE");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("eve.png", context);
        ArrayList<Note> NoteList = EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        new EventPdfNew(NoteList, 1, pp);
        HeaderNew.document.close();
//--------------------------------------------------------------------------------------
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent_pdf, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        floatContact.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        floatNew.setImageResource(R.drawable.closee);

        TextView txtNew = dialogview.findViewById(R.id.txtNew);
        txtNew.setText(getResources().getString(R.string.EmailReports));

        TextView txtContact = dialogview.findViewById(R.id.txtContact);
        txtContact.setText(getResources().getString(R.string.ViewReports));

        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        // int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        rlView.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        floatCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        floatNew.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/EventNote.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, context, "Event Note");
                dialog.dismiss();
            }

        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/EventNote.pdf";

                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getEventInfo());
                new PDFDocumentProcess(path,
                        context, result);

                System.out.println("\n" + result + "\n");
                dialog.dismiss();
            }


        });


    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatAdd://Add New Contact
                Intent intent = new Intent(context, ViewEventActivity.class);
                intent.putExtra("NEW", true);
                context.startActivity(intent);
                break;

            case R.id.imgBack:
                hideSoftKeyboard();
                finish();
                break;

            case R.id.imgHome://Move to Home screen i.e Profile list
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;


            case R.id.floatOptions://Reports
                showFloatDialog();
                break;

            case R.id.imgRight://Instructions
                Intent i = new Intent(context, InstructionActivity.class);
                i.putExtra("From", "EventNotesInstruction");
                startActivity(i);
                break;

        }
    }

    /**
     * Function: Activity Callback method called when screen gets visible and interactive
     */
    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setNoteData();
        hideSoftKeyboard();
    }

    /**
     * Function: Hide device keyboard.
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


}
