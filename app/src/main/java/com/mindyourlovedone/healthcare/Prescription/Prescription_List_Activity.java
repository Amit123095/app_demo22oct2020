package com.mindyourlovedone.healthcare.Prescription;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.DashBoard.AddPrescriptionActivity;
import com.mindyourlovedone.healthcare.DashBoard.FaxActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentPrescriptionUpload;
import com.mindyourlovedone.healthcare.DashBoard.InstructionActivity;
import com.mindyourlovedone.healthcare.DashBoard.PresDocumentsAdapter;
import com.mindyourlovedone.healthcare.DashBoard.PrescriptionUploadActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.Prescription.Adapter.archivePrescription_ListAdapter;
import com.mindyourlovedone.healthcare.Prescription.Adapter.currentPrescription_ListAdapter;
import com.mindyourlovedone.healthcare.Prescription.Class.Doctor;
import com.mindyourlovedone.healthcare.Prescription.Class.Malady;
import com.mindyourlovedone.healthcare.Prescription.Class.Medicine;
import com.mindyourlovedone.healthcare.Prescription.Model.Pojo_list;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.PrescriptionQuery;
import com.mindyourlovedone.healthcare.database.PrescriptionUpload;
import com.mindyourlovedone.healthcare.model.Form;
import com.mindyourlovedone.healthcare.model.Hospital;
import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.model.Prescription;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.PrescriptionPdfNew;
import com.mindyourlovedone.healthcare.utility.CallDialog;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Prescription_List_Activity extends AppCompatActivity implements View.OnClickListener {


    private ImageView imgBacks, imgAddPresc;
    private SwipeMenuListView current_presc_list, archive_presc_list;
    private AutoCompleteTextView aCTV_Search;
    private ArrayList<Prescription> aList_current = new ArrayList<>();
    private ArrayList<Prescription> aList_archive = new ArrayList<>();
    private archivePrescription_ListAdapter archivePrescriptionLlistAdapter;
    private currentPrescription_ListAdapter currentPrescriptionLlistAdapter;
    private Context context = this;
    public static final String TAG = "MyActivity";
    private String[] searchItem = {"Search by", "Medication", "Malady", "Prescribing Doctor"};

    private RelativeLayout relativeListInfo;
    private FrameLayout frameLayout_prescription;

    ImageView imgRight;
    
    RelativeLayout llAddPrescriptionInfo;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout rlGuide;
    TextView txtMsg, txtFTU;
    FloatingActionButton floatProfile;
    ImageView floatAdd, floatOptions;
    ScrollView scroll;
    TextView txthelp;
    ImageView imghelp;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_information_);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        //Initialize database, get primary data and set data
        initComponent();

        getData();

        initUI();

        initListener();

        setAdapter();


    }


    //Function: Initialize preference,database

    private void initComponent() {
        preferences = new Preferences(this);
        dbHelper = new DBHelper(getApplicationContext(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        PrescriptionQuery i = new PrescriptionQuery(this, dbHelper);
    }


    //Function: Set Prescription upload data on list

    private void setListData() {
        if (aList_current.size() != 0) {
            Collections.sort(aList_current, new Comparator<Prescription>() {
                @Override
                public int compare(Prescription c1, Prescription c2) {
                    return c1.getMedicine().compareToIgnoreCase(c2.getMedicine());
                }
            });
            if (aList_archive.size() != 0) {
                Collections.sort(aList_archive, new Comparator<Prescription>() {
                    @Override
                    public int compare(Prescription a1, Prescription a2) {
                        return a1.getMedicine().compareToIgnoreCase(a2.getMedicine());
                    }
                });
            }
            currentPrescriptionLlistAdapter = new currentPrescription_ListAdapter(this, aList_current);
            current_presc_list.setAdapter(currentPrescriptionLlistAdapter);
            relativeListInfo.setVisibility(View.VISIBLE);
            frameLayout_prescription.setVisibility(View.GONE);

        } else {
            relativeListInfo.setVisibility(View.GONE);
            frameLayout_prescription.setVisibility(View.VISIBLE);
        }
    }


    // * Function: Delete selected prescription document

    public void deletePrescription(final Prescription item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = PrescriptionQuery.deleteRecord(item.getUnique());
                if (flag == true) {
                    ArrayList<PrescribeImage> imageList = new ArrayList<>();
//                    }
                    Toast.makeText(context, "Prescription has been deleted succesfully", Toast.LENGTH_SHORT).show();
                    getData();
                    setListData();
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


    private void initListener() {
        //click to list
        /*current_presc_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentCurrentList = new Intent(Prescription_List_Activity.this, Prescription_detail_Activity.class);
                Prescription_List_Activity.this.startActivity(intentCurrentList);
                finish();
            }
        });
        archive_presc_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentCurrentList = new Intent(Prescription_List_Activity.this, Prescription_Edit_Activity.class);
                Prescription_List_Activity.this.startActivity(intentCurrentList);
                finish();
            }
        });*/
        imgBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onTouch: back touch");
                Toast.makeText(Prescription_List_Activity.this, "Still Working on it", Toast.LENGTH_SHORT).show();
            }
        });
        imgAddPresc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_add = new Intent(Prescription_List_Activity.this, Prescription_detail_Activity.class);
                Log.d(TAG, "onTouch: add");
                Prescription_List_Activity.this.startActivity(intent_add);
                finish();
            }
        });

        llAddPrescriptionInfo.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setAdapter() {
//this is code is for setting the Archive list of prescription.
        archivePrescriptionLlistAdapter = new archivePrescription_ListAdapter(this, aList_archive);
        Log.d(TAG, "MyActivity: current adapter set");
        archive_presc_list.setAdapter(archivePrescriptionLlistAdapter);
        archive_presc_list.deferNotifyDataSetChanged();
//for dynamic hight of a list
        archive_presc_list.setDivider(null);
        ListUtils.setDynamicHeight(archive_presc_list);
        //Utility.setListViewHeightBasedOnChildren(archive_presc_list);
        //(the above line is for the dynamic hight for listview but it makes the screen into 2 half)

        //swipe laylot code and fuctionallty
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "Archive" item
                SwipeMenuItem archiveItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                archiveItem.setBackground(new ColorDrawable(Color.rgb(0xE0, 0xE0, 0xE4)));
                // set item width
                archiveItem.setWidth(150);
                // set a icon
                archiveItem.setIcon(R.drawable.baseline_check_black_24);
                //set iten title
                archiveItem.setTitle("Archive");
                // set item title fontsize
                archiveItem.setTitleSize(8);
                // set item title font color
                archiveItem.setTitleColor(Color.BLACK);
                // add to menu
                menu.addMenuItem(archiveItem);

                // create "Copy" item
                SwipeMenuItem copyItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                copyItem.setBackground(new ColorDrawable(Color.rgb(0xE0, 0xE0, 0xE4)));
                // set item width
                copyItem.setWidth(150);
                // set a icon
                copyItem.setIcon(R.drawable.outline_file_copy_black_24);
                //set iten title
                copyItem.setTitle("Copy");
                // set item title fontsize
                copyItem.setTitleSize(8);
                // set item title font color
                copyItem.setTitleColor(Color.BLACK);
                // add to menu
                menu.addMenuItem(copyItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xE0, 0xE0, 0xE4)));
                // set item width
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setIcon(R.drawable.outline_delete_forever_black_24);
                //set iten title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(8);
                // set item title font color
                deleteItem.setTitleColor(Color.BLACK);
                // add to menu
                menu.addMenuItem(deleteItem);


                // create "Edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0xE0, 0xE0, 0xE4)));
                // set item width
                editItem.setWidth(150);
                //set icon
                editItem.setIcon(R.drawable.outline_create_black_24);
                // set item title
                editItem.setTitle("Edit");
                // set item title fontsize
                editItem.setTitleSize(8);
                // set item title font color
                editItem.setTitleColor(Color.BLACK);
                // add to menu
                menu.addMenuItem(editItem);
            }

        };
        archive_presc_list.setMenuCreator(creator);
        archive_presc_list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // Archive
                        Toast.makeText(Prescription_List_Activity.this, "Archived", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // Copy
                        Toast.makeText(Prescription_List_Activity.this, "Copy", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        // Delete
                        Toast.makeText(Prescription_List_Activity.this, "Delete", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        // Edit
                        Toast.makeText(Prescription_List_Activity.this, "Edit", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


//this code is for setting the Current listAdapter of prescription.
        currentPrescriptionLlistAdapter = new currentPrescription_ListAdapter(this, aList_current);
        Log.d(TAG, "MyActivity: archive adapter set");
        current_presc_list.setAdapter(currentPrescriptionLlistAdapter);
        current_presc_list.deferNotifyDataSetChanged();
//for dynamic hight of a list
        current_presc_list.setDivider(null);
        ListUtils.setDynamicHeight(current_presc_list);
        //Utility.setListViewHeightBasedOnChildren(current_presc_list);

//adding item in aCTV dropdown
        final ArrayAdapter<String> adapterSearch = new ArrayAdapter<String>(context, R.layout.autocompletetextview_dropdown_list,
                R.id.textList, searchItem);
        aCTV_Search.setThreshold(1);
        aCTV_Search.setDropDownAnchor(R.id.aCTV_Search);
        aCTV_Search.setAdapter(adapterSearch);
        //set onTouchListener to show all the dropdown list by a touch on search bar
        aCTV_Search.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

              /* //to blur the forground
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) v.getLayoutParams();
                // add flag
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.3f;
                wm.updateViewLayout(v, p);
*/
                if (aCTV_Search.length() >= 0) {
                    if (!aCTV_Search.getText().toString().equals(""))
                        adapterSearch.getFilter().filter(null);
                    aCTV_Search.showDropDown();
                    Log.d(TAG, "onTouch: dropdown");
                }
                return false;
            }
        });
        //setting onClickListener on actv list and adding a static item to it as a Header.
        aCTV_Search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = searchItem[i];

                if (s.equalsIgnoreCase("Search by")) {
                    Toast.makeText(context, "It is header of the list", Toast.LENGTH_SHORT).show();

                    Typeface arial = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf");
                    aCTV_Search.setTypeface(arial);
                    aCTV_Search.setText("Search by -");

                } else if (s.equalsIgnoreCase("Medication")) {
                    Intent intent = new Intent(Prescription_List_Activity.this, Medicine.class);
                    Prescription_List_Activity.this.startActivity(intent);
                    finish();
                } else if (s.equalsIgnoreCase("Malady")) {
                    Intent intent = new Intent(Prescription_List_Activity.this, Malady.class);
                    Prescription_List_Activity.this.startActivity(intent);
                    finish();
                } else if (s.equalsIgnoreCase("Prescribing Doctor")) {
                    Intent intent = new Intent(Prescription_List_Activity.this, Doctor.class);
                    Prescription_List_Activity.this.startActivity(intent);
                    finish();
                }
            }
        });
    }


    ArrayList<Prescription> PrescriptionList = new ArrayList<>();

    private void getData() {

        PrescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

    }


    private void initUI() {

// UI link
        imgBacks = findViewById(R.id.imgBackss);
        imgAddPresc = findViewById(R.id.imgAddPresc);
        current_presc_list = findViewById(R.id.current_presc_list);
        archive_presc_list = findViewById(R.id.archive_presc_list);
        aCTV_Search = findViewById(R.id.aCTV_Search);


        relativeListInfo = findViewById(R.id.relativeListInfo);
        frameLayout_prescription = findViewById(R.id.frameLayout_prescription);

        floatProfile = findViewById(R.id.floatProfile);
        floatAdd = findViewById(R.id.floatAdd);
        imghelp = findViewById(R.id.imghelp);
        txthelp = findViewById(R.id.txthelp);

        PrescriptionList = new ArrayList<>();
        scroll = findViewById(R.id.scroll);
        floatOptions = findViewById(R.id.floatOptions);

        final RelativeLayout relMsg = findViewById(R.id.relMsg);
        TextView txt61 = findViewById(R.id.txtPolicy61);
        TextView txt62 = findViewById(R.id.txtPolicy62);
        TextView txt63 = findViewById(R.id.txtPolicy63);
        TextView txt64 = findViewById(R.id.txtPolicy64);
        TextView txt65 = findViewById(R.id.txtPolicy65);
        TextView txt66 = findViewById(R.id.txtPolicy66);
        TextView txt67 = findViewById(R.id.txtPolicy67);
        TextView txt68 = findViewById(R.id.txtPolicy68);
        ImageView img67 = findViewById(R.id.img67);
        ImageView img68 = findViewById(R.id.img68);


        //shradha
        txt61.setText(Html.fromHtml("To <b>add</b> information click the orange bar at the bottom of the screen. If the entity is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load the data.\n\n"));
        txt62.setText(Html.fromHtml("To <b>save</b> information click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits <b>click</b> the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>make an automated phone call</b> or <b>delete</b> the entry, left swipe the arrow symbol on the <b>right side</b> of the screen.\n\n "));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data in each section click the three dots on the top right side of the screen.\n\n"));
        txt66.setText(Html.fromHtml("To <b>add a picture</b> click the <b>picture</b> of the <b>pencil</b> and either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again. Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card.\n\n"));
        txt67.setText(Html.fromHtml("\n\n"));


        String msg = "To <b>add</b> information click the green bar at the bottom of the screen. If the entity is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load data." +
                "<br><br>" +
                "To <b>save</b> information click the green bar at the bottom of the screen." +
                "<br><br>" +
                "To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits click the <b>green bar</b> at the bottom of the screen." +
                "<br><br>" +
                "To <b>make an automated phone call</b> or <b>delete</b> the entry <b>swipe right to left</b> arrow symbol." +
                "<br><br>" +
                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen." +
                "<br><br>" +
                "To <b>add a picture</b> click the picture of the <b>pencil</b> and" +
                "either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again.Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card";
        // txtMsg.setText(Html.fromHtml(msg));
        txtFTU = findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentEmerInstruc = new Intent(Prescription_List_Activity.this, InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "PrescriptionInstruction");
                startActivity(intentEmerInstruc);
                // relMsg.setVisibility(View.VISIBLE);
            }
        });
        imgRight = this.findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) .findViewById(imgADMTick);
        rlGuide = findViewById(R.id.rlGuide);
        llAddPrescriptionInfo = findViewById(R.id.llAddPrescriptionInfo);

    }


    /* //** Function: Make Call to clicked contact person
    public void callUser(Hospital item) {
        String mobile = item.getOfficePhone();
        String hphone = item.getMobile();
        String wPhone = item.getOtherPhone();

        if (mobile.length() != 0 || hphone.length() != 0 || wPhone.length() != 0) {
            CallDialog c = new CallDialog();
            c.showCallDialog(this, mobile, hphone, wPhone);
        } else {
            Toast.makeText(this, "You have not added phone number for call", Toast.LENGTH_SHORT).show();
        }
    }*/


    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(Prescription_List_Activity.this, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                startActivity(intentDashboard);
                break;
            case R.id.floatAdd://Add New Contact
                Intent is = new Intent(Prescription_List_Activity.this, Prescription_detail_Activity.class);
                is.putExtra("IsEdit", false);
                startActivityForResult(is, 100);
                break;
            case R.id.floatOptions:
                showFloatOption();
                break;
            case R.id.imgRight:
                Bundle bundle = new Bundle();
                bundle.putInt("PrescriptionInformation_Instruction", 1);
                mFirebaseAnalytics.logEvent("OnClick_QuestionMark", bundle);

                Intent i = new Intent(Prescription_List_Activity.this, InstructionActivity.class);
                i.putExtra("From", "PrescriptionInstruction");
                startActivity(i);
                break;
        }

    }


    //** Function - Display dialog for Reports options i.e view, email, fax
    private void showFloatOption() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "Prescription.pdf");
        if (file.exists()) {
            file.delete();
        }

        Image pdflogo = null, calendar = null, profile = null, calendarWite = null, profileWite = null;
        pdflogo = preferences.addFile("pdflogo.png", this);
        calendar = preferences.addFile("calpdf.png", this);
        calendarWite = preferences.addFile("calpdf_wite.png", this);
        profile = preferences.addFile("profpdf.png", this);
        profileWite = preferences.addFile("profpdf_wite.png", this);

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "PRESCRIPTION INFORMATION", calendarWite, profileWite);

        HeaderNew.addusereNameChank("PRESCRIPTION INFORMATION");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("pres_one.png", this);
        ArrayList<Prescription> prescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        new PrescriptionPdfNew(prescriptionList, pp, this);
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
        final RelativeLayout rlFloatfax = dialogview.findViewById(R.id.rlFloatfax);
        rlFloatfax.setVisibility(View.VISIBLE);
        final FloatingActionButton floatfax = dialogview.findViewById(R.id.floatfax);
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
        floatfax.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Prescription.pdf";

                Intent i = new Intent(Prescription_List_Activity.this, FaxActivity.class);
                i.putExtra("PATH", path);
                startActivity(i);
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
                        + "/Prescription.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, context, "Prescription");

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
                        + "/Prescription.pdf";

                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getInsuranceInfo());
                new PDFDocumentProcess(path,
                        context, result);
                System.out.println("\n" + result + "\n");

                dialog.dismiss();
            }


        });
    }


    // Function: To display floating menu for add new profile

    private void showFloatDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        //floatContact.setImageResource(R.drawable.closee);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        // floatNew.setImageResource(R.drawable.eyee);

        TextView txtNew = dialogview.findViewById(R.id.txtNew);
        txtNew.setText(getResources().getString(R.string.AddNew));

        TextView txtContact = dialogview.findViewById(R.id.txtContact);
        txtContact.setText(getResources().getString(R.string.AddContacts));

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
                Toast.makeText(context, "Please wait still in progress..!!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Please wait still in progress..!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }


    // this class is to set dynamic hight of multiple list in single Activity/Fragment/List_page.
    private static class ListUtils {
        private static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }


    }

}