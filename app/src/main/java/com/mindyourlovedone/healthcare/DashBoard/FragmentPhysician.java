package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.SpecialistAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DoctorQuery;
import com.mindyourlovedone.healthcare.database.SpecialistQuery;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Specialist;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.IndividualNew;
import com.mindyourlovedone.healthcare.utility.CallDialog;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by welcome on 9/14/2017.
 */

/**
 * Class: FragmentPhysician
 * Screen: Physician Contacts List
 * A class that manages List of Physician Contacts
 * Add New Physician Contact
 * Call Physician Contact
 * Generate, View, Email, Fax PDF Reports
 * extends Fragment
 * implements OnclickListener for onclick event on views
 */
public class FragmentPhysician extends Fragment implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    ImageView imgRight;
    View rootview;
    RecyclerView lvSpecialist;
    ArrayList<Specialist> specialistList;
    RelativeLayout llAddSpecialist;
    Preferences preferences;
    TextView txtTitle;
    TextView txtAdd, txtMsg, txtFTU;
    DBHelper dbHelper;
    SpecialistAdapter specialistAdapter;
    RelativeLayout rlGuide;
    FloatingActionButton floatProfile;
    ImageView floatAdd, floatOptions;
    TextView txthelp;
    ImageView imghelp;

    /**
     * @param inflater           LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view. This value may be null.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_physician, null);
        //Initialize database, get primary data and set data
        initComponent();

        //Fetch physician Contact Data
        getData();

        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();

        return rootview;
    }

    /**
     * Function: Initialize database, Preferences
     */
    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        DoctorQuery d = new DoctorQuery(getActivity(), dbHelper);
        SpecialistQuery s = new SpecialistQuery(getActivity(), dbHelper);
    }

   /**
     * Function: Set Contact data on list
     */
    private void setListData() {
        if (specialistList.size() != 0) {
            Collections.sort(specialistList, new Comparator<Specialist>() {
                @Override
                public int compare(Specialist o1, Specialist o2) {
                    return o1.getType().compareTo(o2.getType());
                }
            });
            specialistAdapter = new SpecialistAdapter(getActivity(), specialistList, FragmentPhysician.this);
            lvSpecialist.setAdapter(specialistAdapter);
            lvSpecialist.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
        } else {
            lvSpecialist.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
            imghelp.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        llAddSpecialist.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        floatAdd = rootview.findViewById(R.id.floatAdd);
        floatOptions = rootview.findViewById(R.id.floatOptions);
        floatProfile = rootview.findViewById(R.id.floatProfile);
        txtMsg = rootview.findViewById(R.id.txtMsg);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);

        final RelativeLayout relMsg = rootview.findViewById(R.id.relMsg);
        txtFTU = rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentEmerInstruc = new Intent(getActivity(), InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "PhysicianInstruction");
                startActivity(intentEmerInstruc);
            }
        });
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Primary Physician");
        rlGuide = rootview.findViewById(R.id.rlGuide);
        imgRight = getActivity().findViewById(R.id.imgRight);
        llAddSpecialist = rootview.findViewById(R.id.llAddSpecialist);
        llAddSpecialist.setVisibility(View.INVISIBLE);
        lvSpecialist = rootview.findViewById(R.id.lvSpecialist);

        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvSpecialist.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvSpecialist.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        //or
        lvSpecialist.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...

        if (specialistList.size() != 0 || specialistList != null) {
            setListData();
        }


    }

    /**
     * Function: Make Call to clicked contact person
     */
    public void callUser(Specialist item) {
        ArrayList<ContactData> phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), item.getId(), "Primary");


        if (phonelist.size() > 0) {
            CallDialog c = new CallDialog();
            c.showCallDialogs(getActivity(), phonelist);
        } else {
            Toast.makeText(getActivity(), "You have not added phone number for call", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function: Delete selected contact
     */
    public void deleteSpecialist(final Specialist item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = SpecialistQuery.deleteRecord(item.getId(), 1);
                // boolean flags=SpecialistQuery.deleteRecord(item.getUnique());
                if (flag == true) {//Shradha delete whole record and image
                    ArrayList<Specialist> specialistList = new ArrayList<>();
                    for (int i = 0; i < specialistList.size(); i++) {
                        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH) + specialistList.get(i).getImage());//nikita
                        if (imgFile.exists()) {
                            imgFile.delete();
                        }
                    }
                    Toast.makeText(getActivity(), "Primary Physician has been deleted succesfully", Toast.LENGTH_SHORT).show();
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

    /**
     * Function: Fetch all Physician contacts
     */
    private void getData() {
        specialistList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatAdd://Add New Contact// Add new Contact
                showFloatDialog();
                break;
            case R.id.floatOptions: //reports options
                showFloatPdfDialog();
                break;
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                startActivity(intentDashboard);
                break;
            case R.id.imgRight://Instruction
                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "PhysicianInstruction");
                startActivity(i);

                break;
        }
    }

    /**
     * Function - Display dialog for Reports options i.e view, email, fax
     */
    private void showFloatPdfDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "Physician.pdf");
        if (file.exists()) {
            file.delete();
        }

        Image pdflogo = null, calendar = null, profile = null, calendarWite = null, profileWite = null;
        pdflogo = preferences.addFile("pdflogo.png", getActivity());
        calendar = preferences.addFile("calpdf.png", getActivity());
        calendarWite = preferences.addFile("calpdf_wite.png", getActivity());
        profile = preferences.addFile("profpdf.png", getActivity());
        profileWite = preferences.addFile("profpdf_wite.png", getActivity());

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "PRIMARY PHYSICIAN", calendarWite, profileWite);

        HeaderNew.addusereNameChank("PRIMARY PHYSICIAN");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("emergency_four.png", getActivity());
        ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
        for (int i = 0; i < specialistsList.size(); i++) {
            final ArrayList<ContactData> phonelists = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), specialistsList.get(i).getId(), "Primary");
            new IndividualNew("Physician", specialistsList.get(i), phonelists, i, pp);
        }
        HeaderNew.document.close();

        //--------------------------------------------------------------------
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent_pdf, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
//   final ImageView floatCancel = dialogview.findViewById(R.id.floatCancel);  // Rahul
        final FloatingActionButton floatViewPdf = dialogview.findViewById(R.id.floatContact);
        floatViewPdf.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatEmail = dialogview.findViewById(R.id.floatNew);
        floatEmail.setImageResource(R.drawable.closee);

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

        floatEmail.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Physician.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Primary Physician");
                dialog.dismiss();

            }
        });

        floatViewPdf.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Physician.pdf";

                if (preferences.getInt(PrefConstants.CONNECTED_USERID) == (preferences.getInt(PrefConstants.USER_ID))) {
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getPhysicianInfo());

                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/"
                            + "/Physician.pdf",
                            getActivity(), result);

                    System.out.println("\n" + result + "\n");
                } else {
                    StringBuffer result = new StringBuffer();
                    result.append(new MessageString().getPhysicianInfo());

                    new PDFDocumentProcess(path,
                            getActivity(), result);

                    System.out.println("\n" + result + "\n");
                }
                dialog.dismiss();
            }
        });

    }

    /**
     * Function: To display floating menu for add new profile
     */
    private void showFloatDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
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
                preferences.putString(PrefConstants.SOURCE, "Physician");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB", "New");
                startActivity(i);
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
                // Toast.makeText(getActivity(),"Work in progress",Toast.LENGTH_SHORT).show();
                preferences.putString(PrefConstants.SOURCE, "Physician");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB", "Contact");
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    public void postCommonDialog() {
        //preferences.putString(PrefConstants.SOURCE,"Speciality");
        Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
