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
import android.widget.DatePicker;
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
import com.mindyourlovedone.healthcare.database.AppointmentQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DateQuery;
import com.mindyourlovedone.healthcare.model.Appoint;
import com.mindyourlovedone.healthcare.pdfCreation.EventPdfNew;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


/**
 * Class: MedicalAppointActivity
 * A class that manages Routine appointment list
 * implements OnclickListener for onclick event on views
 */
public class MedicalAppointActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    RecyclerView lvNote;
    ArrayList<Appoint> noteList = new ArrayList<>();
    ImageView imgHome, imgBack, imgAdd, imgEdit, imgRight, imghelp;
    RelativeLayout rlGuide;
    Preferences preferences;
    ArrayList<DateClass> dateList;
    DBHelper dbHelper;
    RelativeLayout header;
    boolean flag = false;
    TextView txtMsg, txtFTU, txtAdd, txthelp;
    ScrollView scrollvw;
    ImageView floatProfile, floatAdd, floatOptions;

    public static String getFormattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        switch (day % 10) {
            case 1:
                return new SimpleDateFormat("MMM d'st', yyyy").format(date);
            case 2:
                return new SimpleDateFormat("MMM d'nd', yyyy").format(date);
            case 3:
                return new SimpleDateFormat("MMM d'rd', yyyy").format(date);
            default:
                return new SimpleDateFormat("MMM d'th', yyyy").format(date);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_appoint);

        //Initialize database, get primary data and set data
        initComponent();

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
        floatOptions.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
        //imgAdd.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        floatOptions = findViewById(R.id.floatOptions);
        floatProfile = findViewById(R.id.floatProfile);
        floatAdd = findViewById(R.id.floatAdd);
        txthelp = findViewById(R.id.txthelp);
        imghelp = findViewById(R.id.imghelp);
        scrollvw = findViewById(R.id.scrollvw);
        txtMsg = findViewById(R.id.txtMsg);
        //nikita
        final RelativeLayout relMsg = findViewById(R.id.relMsg);
        TextView txt61 = findViewById(R.id.txtPolicy61);
        TextView txt62 = findViewById(R.id.txtPolicy62);
        TextView txt63 = findViewById(R.id.txtPolicy63);
        TextView txt64 = findViewById(R.id.txtPolicy64);
        TextView txt65 = findViewById(R.id.txtPolicy65);

        //shradha
        txt61.setText(Html.fromHtml("This <b>section</b> allows the User to create a master list of annual or reoccurring appointments. The purpose is to ensure appointments are made and not overlooked.\n\n"));
        txt62.setText(Html.fromHtml("To <b>add</b> an Appointment click on add button at the top right of the screen. Choose a Specialist or Type of Test, add the name of your doctor and frequency of appointment. Once completed <b>click SAVE</b> on the top right corner of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> the Appointment click the picture of the pencil to the right of the screen. To save your edits click the green bar marked Update Appointment. To <b>delete</b> the appointment swipe right to left and click the garbage can.\n\n"));
        txt64.setText(Html.fromHtml("To <b>add</b> the completed date(s) click Set Completion Date and click Add. \n\n "));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data, in each section click the three dots on the upper right side of the screen.\n\n "));


        txtFTU = findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentEmerInstruc = new Intent(context, InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "CheckListInstruction");
                startActivity(intentEmerInstruc);
                rlGuide.setVisibility(View.GONE);//nikita
                imghelp.setVisibility(View.GONE);
                txthelp.setVisibility(View.GONE);
            }
        });
        header = findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorEventPink);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);

        txtAdd = findViewById(R.id.txtAdd);

        imgAdd = findViewById(R.id.imgAdd);
        imgRight = findViewById(R.id.imgRight);
        lvNote = findViewById(R.id.lvNote);
        rlGuide = findViewById(R.id.rlGuide);

        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvNote.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvNote.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        //or
        lvNote.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));
        //...
        if (noteList.size() != 0) {
            setNoteData();
        }
    }

    /**
     * Function: Delete selected Appoitment
     */
    public void deleteNote(final Appoint item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = AppointmentQuery.deleteRecord(item.getUnique());
                if (flag == true) {
                    Toast.makeText(context, "Routine Appointment has been deleted succesfully", Toast.LENGTH_SHORT).show();
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
     * Function: Set Appointment list data
     */
    public void setNoteData() {
        if (noteList.size() != 0) {
            lvNote.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.GONE);
            imghelp.setVisibility(View.GONE);
            rlGuide.setVisibility(View.GONE);
            scrollvw.setVisibility(View.GONE);
        } else {
            rlGuide.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
            imghelp.setVisibility(View.VISIBLE);
            lvNote.setVisibility(View.GONE);
            scrollvw.setVisibility(View.GONE);
        }
        ArrayList<DateClass> dates = new ArrayList<>();
        for (int i = 0; i < noteList.size(); i++) {
            dates = noteList.get(i).getDateList();
            if (dates.size() != 0) {
                for (int j = 0; j < dates.size(); j++) {

                    Collections.sort(dates, new Comparator<DateClass>() {
                        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");

                        @Override
                        public int compare(DateClass o1, DateClass o2) {
                            try {
                                return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                }

                noteList.get(i).setDate(dates.get(0).getDate());
            }
        }

        for (int j = 0; j < noteList.size(); j++) {

            Collections.sort(noteList, new Comparator<Appoint>() {
                SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");

                @Override
                public int compare(Appoint o1, Appoint o2) {
                    try {
                        return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }
        AppointAdapter adapter = new AppointAdapter(context, noteList);
        lvNote.setAdapter(adapter);
    }

    /**
     * Function: Fecth all appoinments data from database
     * @param index
     */
    public void getData(final int index) {
        noteList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getUnique() == index) {
                noteList.get(i).setOpen(true);
            } else {
                noteList.get(i).setOpen(false);
            }
        }
    }

    /**
     * Function: Fetch all appointment list records
     */
    public void getData() {
        noteList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    //Initialize Database, Preferences
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        AppointmentQuery a = new AppointmentQuery(context, dbHelper);
        DateQuery d = new DateQuery(context, dbHelper);
    }

    /**
     * Function: To display floating menu for reports
     */
    private void showFloatDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "Appointment.pdf");
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
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "ROUTINE APPOINTMENTS", calendarWite, profileWite);

        HeaderNew.addusereNameChank("ROUTINE APPOINTMENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("eve_one.png", context);
        ArrayList<Appoint> AppointList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        new EventPdfNew(AppointList, pp);
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
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
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
                        + "/Appointment.pdf";

                File f = new File(path);
                preferences.emailAttachement(f, context, "Routine Appointments");

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
                        + "/Appointment.pdf";

                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getAppointInfo());
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
            case R.id.floatOptions://REports
                showFloatDialog();
                break;

            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                startActivity(intentDashboard);
                break;

            case R.id.imgHome://Navigate to Profile list screen
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;

            case R.id.imgBack://Navigate to previous screen
                finish();
                break;
            case R.id.floatAdd://Add New Contact
                Intent i = new Intent(context, AddAppointmentActivity.class);
                i.putExtra("FROM", "Add");
                startActivity(i);
                break;
            case R.id.imgRight://Instructions
                Intent ih = new Intent(context, InstructionActivity.class);
                ih.putExtra("From", "CheckListInstruction");
                startActivity(ih);

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
    }

    /**
     * Function: Set date of appointment
     * @param a
     * @param position
     */
    public void SetDate(final Appoint a, final int position) {
        final ArrayList<DateClass> list = new ArrayList<DateClass>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int id = a.getId();
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                long selectedMilli = newDate.getTimeInMillis();

                Date datePickerDate = new Date(selectedMilli);
                String reportDate = new SimpleDateFormat("dd MMM yyyy").format(datePickerDate);

                DateClass d = new DateClass();
                d.setDate(reportDate);
                list.add(d);

                ArrayList<DateClass> ds = DateQuery.fetchAllDosageRecord(a.getUserid(), a.getUnique());
                Boolean flag = DateQuery.insertDosageData(a.getUserid(), list, a.getUnique());

                if (flag == true) {
                    Toast.makeText(context, "You have inserted date successfully", Toast.LENGTH_SHORT).show();
                    getData(a.getUnique());
                    setNoteData();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, year, month, day);
        dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dpd.show();
    }

    /**
     * Function: Delete Date
     */
    public void deleteDateNote(final int items, final int index) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = DateQuery.deleteRecords(items);
                if (flag == true) {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    getData(index);
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
}
