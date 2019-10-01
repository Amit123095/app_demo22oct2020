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


public class MedicalAppointActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    RecyclerView lvNote;
    ArrayList<Appoint> noteList = new ArrayList<>();
    ImageView imgHome, imgBack, imgAdd, imgEdit, imgRight,imghelp;
    RelativeLayout rlGuide;
    Preferences preferences;
    ArrayList<DateClass> dateList;
    DBHelper dbHelper;
    RelativeLayout header;
    boolean flag = false;
    TextView txtMsg, txtFTU, txtAdd,txthelp;
    ScrollView scrollvw;
   // FloatingActionButton floatProfile, floatAdd;
    ImageView floatProfile, floatAdd,floatOptions;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

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
        initComponent();
        //getData();
        initUI();
        initListener();
    }

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

    private void initUI() {
        floatOptions = findViewById(R.id.floatOptions);
        floatProfile = findViewById(R.id.floatProfile);
        floatAdd = findViewById(R.id.floatAdd);
        txthelp = findViewById(R.id.txthelp);
        imghelp = findViewById(R.id.imghelp);
        scrollvw = findViewById(R.id.scrollvw);
        txtMsg = findViewById(R.id.txtMsg);
//        String msg = "To <b>add</b> an Appointment  click  the <b>plus</b> box " +
//                "at the top right of the screen. Choose a Specialist or Type of Test, add the name of your doctor and frequency of appointment. Once completed click <b>Add Appointment</b> on the green bar." +
//                "<br><br>" +
//                "To edit the Appointment  click the picture of the pencil to the right of the screen. To save your edits click the green bar marked Update Appointment. To <b>delete</b> the appointment swipe right to left and  click the garbage can." +
//                "<br><br>" +
//                "To <b>add</b> the completed date(s) click <b>Set Completion Date</b> and click Add." +
//                "<br><br>" +
//                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the upper right side of the screen.";
//        txtMsg.setText(Html.fromHtml(msg));

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
            @Override
            public void onClick(View v) {
                Intent intentEmerInstruc = new Intent(context, InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "CheckListInstruction");
                startActivity(intentEmerInstruc);
//                txtMsg.setVisibility(View.VISIBLE);
                //  relMsg.setVisibility(View.VISIBLE);//nikita
                // scrollvw.setVisibility(View.VISIBLE);//nikita
                rlGuide.setVisibility(View.GONE);//nikita
                imghelp .setVisibility(View.GONE);
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
        //imgEdit= (ImageView) findViewById(R.id.imgEdit);
        lvNote = findViewById(R.id.lvNote);
//        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long l) {
//
//                final LinearLayout llDate = (LinearLayout) view.findViewById(R.id.llDate);
//                ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
//                TextView txtDate = (TextView) view.findViewById(R.id.txtDate);
//                //    final View finalConvertView = convertView;
//                Appoint a = noteList.get(position);
//                final ArrayList<DateClass> dates = a.getDateList();
//                llDate.requestFocus();
//
//              /*  view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {*/
//                        if (flag == false) {
//                            flag=true;
//                            LayoutInflater lf;
//                            for (int i = 0; i < dates.size() + 1; i++) {
//                                lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//                                View helperview = lf.inflate(R.layout.date_row, null);
//
//                                llDate.addView(helperview);
//                                TextView datetime = (TextView) helperview.findViewById(R.id.txtDateTime);
//
//                                if (i == dates.size()) {
//                                    datetime.setText("Add +");
//                                    datetime.setTextColor(context.getResources().getColor(R.color.colorBlue));
//                                    datetime.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            SetDate(noteList.get(position), position);
//
//                                        }
//                                    });
//                                } else {
//                                    datetime.setText("Completion Date:  " + dates.get(i).getDate());
//                                    if (i % 2 == 0) {
//                                        datetime.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
//                                    } else {
//                                        datetime.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
//                                    }
//                                }
//                            }
//
//                        } else if (flag == true) {
//                            llDate.removeAllViews();
//                            flag=false;
//                        }
//                  /*  }
//                });
//*/
//               /*txtDate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//            if ( llDate.getVisibility() == View.GONE)
//                {
//                    //expandedChildList.set(arg2, true);
//                    llDate.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    //expandedChildList.set(arg2, false);
//                    llDate.setVisibility(View.GONE);
//                }
//                    }
//                });*/
//                imgEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Appoint a=noteList.get(position);
//                        Intent intent=new Intent(context,AddAppointmentActivity.class);
//                        intent.putExtra("FROM","View");
//                        intent.putExtra("AppointObject",a);
//                        context.startActivity(intent);
//                    }
//                });
//            }
//        });

       /* lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Appoint a=noteList.get(position);
                final ArrayList<DateClass> list=new ArrayList<DateClass>();
                 TextView txtDate= (TextView) view.findViewById(R.id.txtDate);
                final TextView txtDateTime= (TextView) view.findViewById(R.id.txtDateTime);
                txtDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int id=a.getId();

                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, month, dayOfMonth);
                                long selectedMilli = newDate.getTimeInMillis();

                                Date datePickerDate = new Date(selectedMilli);
                                String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                                DateClass d=new DateClass();
                                d.setDate(reportDate);
                                list.add(d);

                                ArrayList<DateClass> ds= DateQuery.fetchAllDosageRecord(a.getUserid(),a.getUnique());
                                Boolean flag= DateQuery.insertDosageData(a.getUserid(),list,a.getUnique());

                                if (flag==true)
                                {
                                    Toast.makeText(context,"You have inserted date successfully",Toast.LENGTH_SHORT).show();
                                    getData();
                                    setNoteData();
                                }
                                else{
                                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                                }
                               *//* txtDateTime.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                noteList.get(position).setDate(dayOfMonth + "/" + (month + 1) + "/" + year);*//*
                            }
                        }, year, month, day);
                        dpd.show();
                    }
                });

            }
        });
*/

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

    public void deleteNote(final Appoint item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = AppointmentQuery.deleteRecord(item.getUnique());
                if (flag == true) {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
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
        ArrayList<DateClass> dates=new ArrayList<>();
        for (int i=0;i<noteList.size();i++)
        {
            dates=noteList.get(i).getDateList();
            if (dates.size()!=0) {
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

    public void getData(final int index) {
        noteList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        //   noteList=new ArrayList<>();
        for(int i=0;i<noteList.size();i++){
            if(noteList.get(i).getUnique()==index){
                noteList.get(i).setOpen(true);
            }else{
                noteList.get(i).setOpen(false);
            }
        }
    }

    public void getData() {
        noteList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        //   noteList=new ArrayList<>();
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        AppointmentQuery a = new AppointmentQuery(context, dbHelper);
        DateQuery d = new DateQuery(context, dbHelper);
        //      EventNoteQuery e=new EventNoteQuery(context,dbHelper);
    }

    private void showFloatDialog() {

        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "Appointment.pdf");
        if (file.exists()) {
            file.delete();
        }

       /* new Header().createPdfHeader(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
        preferences.copyFile("ic_launcher.png", context);
        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
        Header.addEmptyLine(1);
        Header.addusereNameChank("Appointment Checklist");//preferences.getString(PrefConstants.CONNECTED_NAME));
        Header.addEmptyLine(1);
        Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

        Paragraph p = new Paragraph(" ");
        LineSeparator line = new LineSeparator();
        line.setOffset(-4);
        line.setLineColor(BaseColor.LIGHT_GRAY);
        p.add(line);
        try {
            Header.document.add(p);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Header.addEmptyLine(1);


        ArrayList<Appoint> AppointList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        //  ArrayList<Note> NoteList= EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        // new EventPdf(NoteList,1);
        new EventPdf(AppointList);

        Header.document.close();*/
        //New PDF Varsa
        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png",context);
        calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
        profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"ROUTINE APPOINTMENTS", calendarWite, profileWite);

        HeaderNew.addusereNameChank("ROUTINE APPOINTMENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp=preferences.addFile("eve_one.png", context);
        ArrayList<Appoint> AppointList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        new EventPdfNew(AppointList,pp);
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
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        floatNew.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatOptions:
                showFloatDialog();
                break;

            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //   intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;

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
            case R.id.floatAdd:
                Intent i = new Intent(context, AddAppointmentActivity.class);
                i.putExtra("FROM", "Add");
                startActivity(i);
                break;
          /*  case R.id.txtAdd:
                Intent i = new Intent(context, AddAppointmentActivity.class);
                i.putExtra("FROM", "Add");
                startActivity(i);
                break;*/

            case R.id.imgRight:

                Intent ih = new Intent(context, InstructionActivity.class);
                ih.putExtra("From", "CheckListInstruction");
                startActivity(ih);

//                final String RESULT = Environment.getExternalStorageDirectory()
//                        + "/mylopdf/";
//                File dirfile = new File(RESULT);
//                dirfile.mkdirs();
//                File file = new File(dirfile, "Appointment.pdf");
//                if (file.exists()) {
//                    file.delete();
//                }
//
//                new Header().createPdfHeader(file.getAbsolutePath(),
//                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                preferences.copyFile("ic_launcher.png", context);
//                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
//                Header.addEmptyLine(1);
//                Header.addusereNameChank("Appointment Checklist");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(1);
//                Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
//
//                Paragraph p = new Paragraph(" ");
//                LineSeparator line = new LineSeparator();
//                line.setOffset(-4);
//                line.setLineColor(BaseColor.LIGHT_GRAY);
//                p.add(line);
//                try {
//                    Header.document.add(p);
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
//                Header.addEmptyLine(1);
//               /* new Header().createPdfHeader(file.getAbsolutePath(),
//                        "Medical Appointment");
//                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(2);*/
//                ArrayList<Appoint> AppointList = AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                //  ArrayList<Note> NoteList= EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                // new EventPdf(NoteList,1);
//                new EventPdf(AppointList);
//
//                Header.document.close();
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setTitle("");
//
//                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int itemPos) {
//                        String path = Environment.getExternalStorageDirectory()
//                                + "/mylopdf/"
//                                + "/Appointment.pdf";
//                        switch (itemPos) {
//                            case 0: //View
//                                StringBuffer result = new StringBuffer();
//                                result.append(new MessageString().getAppointInfo());
//                                new PDFDocumentProcess(path,
//                                        context, result);
//
//                                System.out.println("\n" + result + "\n");
//                                break;
//                            case 1://Email
//                                File f = new File(path);
//                                preferences.emailAttachement(f, context, "Appointment Checklist");
//                                break;
//                            case 2://FTU
//                                Intent i = new Intent(context, InstructionActivity.class);
//                                i.putExtra("From", "CheckListInstruction");
//                                startActivity(i);
//                                break;
//                           /* case 2://fax
//                                new FaxCustomDialog(context, path).show();
//                                break;*/
//
//                        }
//                    }
//
//                });
//                builder.create().show();
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==100&& data !=null)
//        {
//          //  Appoint appoint= (Appoint) data.getExtras().getSerializable("AppointObject");
//           // noteList.add(appoint);
//            getData();
//            setNoteData();
//        }
//    }

    /*private void showInputDialog(final Context context) {
            final Dialog customDialog;

            // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
            //  View customView = inflater.inflate(R.layout.dialog_input, null);
            // Build the dialog
            customDialog = new Dialog(context);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.dialog_input);
            customDialog.setCancelable(false);
            final EditText etNote= (EditText) customDialog.findViewById(R.id.etNote);
            TextView btnAdd = (TextView) customDialog.findViewById(R.id.btnYes);
            TextView btnCancel = (TextView) customDialog.findViewById(R.id.btnNo);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String note=etNote.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String currentDateandTime = sdf.format(new Date());
                    if (note.length()!=0) {
                        Boolean flag = EventNoteQuery.insertNoteData(preferences.getInt(PrefConstants.CONNECTED_USERID),note,currentDateandTime);
                        if (flag == true) {
                            Toast.makeText(context, "Event Note Added Succesfully", Toast.LENGTH_SHORT).show();
                            getData();
                            setNoteData();
                            customDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                        *//*Note notes=new Note();
                    notes.setTxtDate(currentDateandTime);
                    notes.setTxtNote(note);
                    noteList.add(notes);
                    customDialog.dismiss();
                    setNoteData();*//*
                }
                else {
                    Toast.makeText(context,"Enter Note",Toast.LENGTH_SHORT).show();
                }

            }
        });

        customDialog.show();
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setNoteData();
    }

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

//                noteList.get(position).setDate(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dpd.show();
    }

    /*Shradha delete date recor*/
    public void deleteDateNote(final int items,final int index) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                boolean flag = DateQuery.deleteDateRecord(items.getPreid(), items.getDate());
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
