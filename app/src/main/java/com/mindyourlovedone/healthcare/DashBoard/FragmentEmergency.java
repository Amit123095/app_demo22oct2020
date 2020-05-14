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
import com.mindyourlovedone.healthcare.Connections.ConnectionAdapter;
import com.mindyourlovedone.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.Card;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Emergency;
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
 * Class: FragmentEmergency
 * Screen: Emergency Contacts List
 * A class that manages List of Emergency Contacts
 * Add New Emergency Contact
 * Call Emergency Contact
 * Generate, View, Email, Fax PDF Reports
 * extends Fragment
 * implements OnclickListener for onclick event on views
 */
public class FragmentEmergency extends Fragment implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    ImageView imgRight;
    View rootview;
    RecyclerView lvEmergency;
    ArrayList<Emergency> emergencyList;
    TextView txtAdd, txtMsg, txtFTU;
    RelativeLayout llAddConn;
    TextView txtTitle;
    DBHelper dbHelper;
    Preferences preferences;
    EmergencyAdapter emergencyAdapter;
    RelativeLayout rlGuide;
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
        rootview = inflater.inflate(R.layout.fragment_emergency, null);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        //Initialize database, get primary data and set data
        initComponent();

        //Fetch Emergency Contact Data
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
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        ContactDataQuery c = new ContactDataQuery(getActivity(), dbHelper);
    }

    /**
     * Function: Set Contact data on list
     */
    private void setListData() {
        if (emergencyList.size() != 0) {
            final String[] priorityType = {"Primary Emergency Contact", "Primary Health Care Proxy Agent", "Secondary Emergency Contact", "Secondary Health Care Proxy Agent", "Primary Emergency Contact and Health Care Proxy Agent"};



            Collections.sort(emergencyList, new Comparator<Emergency>() {
                @Override
                public int compare(Emergency o1, Emergency o2) {
                    String o1Priority=priorityType[o1.getIsPrimary()];
                    String o2Priority=priorityType[o2.getIsPrimary()];
                    return o1Priority.compareToIgnoreCase(o2Priority);
                }
            });
            emergencyAdapter = new EmergencyAdapter(getActivity(), emergencyList, FragmentEmergency.this);//Changes done by nikita on 18/6/18
            lvEmergency.setAdapter(emergencyAdapter);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
            rlGuide.setVisibility(View.GONE);
            lvEmergency.setVisibility(View.VISIBLE);
        } else {
            imghelp.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.VISIBLE);
            lvEmergency.setVisibility(View.GONE);
        }
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        llAddConn.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
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

        txt61.setText(Html.fromHtml("To <b>add</b> information click the green bar at the bottom of the screen. If the person is in your <b>Contacts</b> click the gray bar on the top right side of your screen.\n\n"));
        txt62.setText(Html.fromHtml("To <b>save</b> information click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil</b>. To save your edits click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>make an automated phone call</b> or <b>delete</b> the entry left <b>swipe right to left</b> the arrow symbol on the right side.\n\n"));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data in each section click the three dots on the top right side of the screen.\n\n"));

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
                intentEmerInstruc.putExtra("From", "EmergencyInstruction");
                startActivity(intentEmerInstruc);
                relMsg.setVisibility(View.GONE);//nikita
            }
        });
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            txtTitle.setText("Emergent Contacts");
        }else
        {
            txtTitle.setText("Emergency Contacts &\nHealth Care Proxy Agents");
        }
        rlGuide = rootview.findViewById(R.id.rlGuide);
        imgRight = getActivity().findViewById(R.id.imgRight);
        llAddConn = rootview.findViewById(R.id.llAddConn);
        lvEmergency = rootview.findViewById(R.id.lvEmergency);
        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvEmergency.setLayoutManager(linearLayoutManager);
        //add ItemDecoration
        lvEmergency.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        //or
        lvEmergency.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...

        if (emergencyList.size() != 0 || emergencyList != null) {
            setListData();
        }

    }

    /**
     * Function: Make Call to clicked contact person
     */
    public void callUser(Emergency item) {
        ArrayList<ContactData> phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), item.getId(), "Emergency");
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
    public void deleteEmergency(final Emergency item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = MyConnectionsQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Emergency Contact has been deleted succesfully", Toast.LENGTH_SHORT).show();
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
     * Function: Fetch all emergency contacts
     */
    private void getData() {
        emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatOptions://Reports
                showFloatPdfDialog();
                break;

            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                startActivity(intentDashboard);
                break;
            case R.id.floatAdd://Add New Contact//Add New Contact
                showFloatDialog();
                break;

            case R.id.imgRight:// Instructions
                Bundle bundle = new Bundle();
                bundle.putInt("EmergencyContact_Instruction", 1);
                mFirebaseAnalytics.logEvent("OnClick_QuestionMark", bundle);

                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "EmergencyInstruction");
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
        File file = new File(dirfile, "EMERGENCYCONTACTS.pdf");
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
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "EMERGENCY CONTACTS & HEALTH CARE PROXY AGENTS", calendarWite, profileWite);
        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            HeaderNew.addusereNameChank("EMERGENT CONTACTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        }else
        {
            HeaderNew.addusereNameChank("EMERGENCY CONTACTS & HEALTH CARE PROXY AGENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        }
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("emergency_three.png", getActivity());
        ArrayList<Emergency> emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
        for (int i = 0; i < emergencyList.size(); i++) {
            final ArrayList<ContactData> phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), emergencyList.get(i).getId(), "Emergency");
            new IndividualNew("Emergency", emergencyList.get(i), phonelist, i, pp,getActivity());
        }
        HeaderNew.document.close();

        //--------------------------------------
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent_pdf, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
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
                        + "/EMERGENCYCONTACTS.pdf";
                File f = new File(path);
                if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
                {
                    preferences.emailAttachement(f, getActivity(), "Emergent Contacts"); }else
                {
                    preferences.emailAttachement(f, getActivity(), "Emergency Contacts"); }

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
                        + "/EMERGENCYCONTACTS.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getEmergencyInfo());

                new PDFDocumentProcess(path,
                        getActivity(), result);

                System.out.println("\n" + result + "\n");
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
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
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
                preferences.putString(PrefConstants.SOURCE, "Emergency");
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
                preferences.putString(PrefConstants.SOURCE, "Emergency");
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
