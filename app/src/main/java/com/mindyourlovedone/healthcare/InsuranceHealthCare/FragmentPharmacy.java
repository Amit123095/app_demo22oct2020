package com.mindyourlovedone.healthcare.InsuranceHealthCare;

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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedone.healthcare.DashBoard.InstructionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.PharmacyQuery;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.model.Pharmacy;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.SpecialtyNew;
import com.mindyourlovedone.healthcare.utility.CallDialog;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by varsha on 8/28/2017.
 */

/**
 * Class: FragmentPharmacy
 * Screen: Pharmacy Contacts List
 * A class that manages List of Pharmacy Contacts
 * Add New Pharmacy Contact
 * Call Pharmacy Contact
 * Generate, View, Email, Fax PDF Reports
 * extends Fragment
 * implements OnclickListener for onclick event on views
 */
public class FragmentPharmacy extends Fragment implements View.OnClickListener {
    ImageView imgRight;
    View rootview;
    RecyclerView lvPharmacy;
    ArrayList<Pharmacy> PharmacyList;
    RelativeLayout llAddPharmacy;
    Preferences preferences;
    RelativeLayout rlGuide;
    DBHelper dbHelper;
    TextView txtMsg, txtFTU;
    FloatingActionButton floatProfile;
    ImageView floatAdd, floatOptions;
    TextView txthelp;
    ImageView imghelp;

    private FirebaseAnalytics mFirebaseAnalytics;

    /**
     * @param inflater           LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view. This value may be null.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_pharmacy, null);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());


        //Initialize database, get primary data and set data
        initComponent();
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
        PharmacyQuery p = new PharmacyQuery(getActivity(), dbHelper);
    }

    /**
     * Function: Set Contact data on list
     */
    private void setListData() {
        if (PharmacyList.size() != 0) {
            Collections.sort(PharmacyList, new Comparator<Pharmacy>() {
                @Override
                public int compare(Pharmacy o1, Pharmacy o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
            PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(getActivity(), PharmacyList, FragmentPharmacy.this);
            lvPharmacy.setAdapter(pharmacyAdapter);
            lvPharmacy.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
        } else {
            lvPharmacy.setVisibility(View.GONE);
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
        llAddPharmacy.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        //shradha
        floatProfile = rootview.findViewById(R.id.floatProfile);
        floatAdd = rootview.findViewById(R.id.floatAdd);
        floatOptions = rootview.findViewById(R.id.floatOptions);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);

        final RelativeLayout relMsg = rootview.findViewById(R.id.relMsg);
        TextView txt61 = rootview.findViewById(R.id.txtPolicy61);
        TextView txt62 = rootview.findViewById(R.id.txtPolicy62);
        TextView txt63 = rootview.findViewById(R.id.txtPolicy63);
        TextView txt64 = rootview.findViewById(R.id.txtPolicy64);
        TextView txt65 = rootview.findViewById(R.id.txtPolicy65);
        TextView txt66 = rootview.findViewById(R.id.txtPolicy66);
        TextView txt67 = rootview.findViewById(R.id.txtPolicy67);
        ImageView img67 = rootview.findViewById(R.id.img67);

        //shradha
        txt61.setText(Html.fromHtml("To <b>add</b> information click the orange bar at the bottom of the screen. If the entity is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load data.\n\n"));
        txt62.setText(Html.fromHtml("To <b>save</b> information click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits <b>click</b> the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>make an automated phone call</b> or <b>delete</b> the entry, left swipe the arrow symbol on the <b>right side</b> of the screen.\n\n "));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data in each section click the three dots on the top right side of the screen.\n\n"));
        txt66.setText(Html.fromHtml("To <b>add a picture</b> click the <b>picture</b> of the <b>pencil</b> and either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again. Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card.\n\n"));
        txt67.setText(Html.fromHtml("\n\n"));
        img67.setVisibility(View.GONE);


        // txtMsg = rootview.findViewById(R.id.txtMsg);
        String msg = "To <b>add</b> information click the green bar at the bottom of the screen.If the person is in your <b>Contacts</b> click the gray  bar on the top right side of your screen." +
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
                intentEmerInstruc.putExtra("From", "PharmacyInstruction");
                startActivity(intentEmerInstruc);
                // relMsg.setVisibility(View.VISIBLE);
            }
        });
        imgRight = getActivity().findViewById(R.id.imgRight);
        rlGuide = rootview.findViewById(R.id.rlGuide);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddPharmacy = rootview.findViewById(R.id.llAddPharmacy);
        lvPharmacy = rootview.findViewById(R.id.lvPharmacy);

        //Changes done by nikita on 18/6/18
        lvPharmacy = rootview.findViewById(R.id.lvPharmacy);
        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvPharmacy.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvPharmacy.addItemDecoration(new VerticalSpaceItemDecoration(48));

        //or
        lvPharmacy.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...
        if (PharmacyList.size() != 0) {
            setListData();
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
        File file = new File(dirfile, "Pharmacy.pdf");
        if (file.exists()) {
            file.delete();
        }

        // New pdf varsa
        Image pdflogo = null, calendar = null, profile = null, calendarWite = null, profileWite = null;
        pdflogo = preferences.addFile("pdflogo.png", getActivity());
        calendar = preferences.addFile("calpdf.png", getActivity());
        calendarWite = preferences.addFile("calpdf_wite.png", getActivity());
        profile = preferences.addFile("profpdf.png", getActivity());
        profileWite = preferences.addFile("profpdf_wite.png", getActivity());

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "PHARMACIES AND HOME MEDICAL EQUIPMENT", calendarWite, profileWite);

        HeaderNew.addusereNameChank("PHARMACIES AND HOME MEDICAL EQUIPMENT");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("sp_three.png", getActivity());
        ArrayList<Pharmacy> PharmacyList = PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        // new Specialty(PharmacyList);
        for (int i = 0; i < PharmacyList.size(); i++) {
            final ArrayList<ContactData> phonelists = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), PharmacyList.get(i).getId(), "Pharmacy");
            new SpecialtyNew(PharmacyList.get(i), "Pharmacy", phonelists, i, pp);
        }
        HeaderNew.document.close();
        //------------------------------------------------------
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
                        + "/Pharmacy.pdf";

                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Pharmacies & Home Medical Equipment");
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
                        + "/Pharmacy.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getDoctorsInfo());
                result.append(new MessageString().getHospitalInfo());
                result.append(new MessageString().getPharmacyInfo());
                result.append(new MessageString().getAideInfo());
                result.append(new MessageString().getFinanceInfo());

                new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Pharmacy.pdf",
                        getActivity(), result);

                System.out.println("\n" + result + "\n");
                dialog.dismiss();
            }
        });

    }

    /**
     * Function: Make Call to clicked contact person
     */
    public void callUser(Pharmacy item) {
        ArrayList<ContactData> phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), item.getId(), "Pharmacy");


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
    public void deletePharmacy(final Pharmacy item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = PharmacyQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Pharmacy has been deleted succesfully", Toast.LENGTH_SHORT).show();
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
     * Function: Fetch all Pharmacy contacts
     */
    private void getData() {
        PharmacyList = PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                startActivity(intentDashboard);
                break;
            case R.id.floatAdd://Add New Contact
                showFloatDialog();
                break;

            case R.id.floatOptions://Reports
                showFloatPdfDialog();
                break;

            case R.id.imgRight://Instructions
                Bundle bundle = new Bundle();
                bundle.putInt("Pharmacy_Instruction", 1);
                mFirebaseAnalytics.logEvent("OnClick_QuestionMark", bundle);

                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "PharmacyInstruction");
                startActivity(i);
                break;
        }
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
                preferences.putString(PrefConstants.SOURCE, "Pharmacy");
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
                preferences.putString(PrefConstants.SOURCE, "Pharmacy");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB", "Contact");
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
