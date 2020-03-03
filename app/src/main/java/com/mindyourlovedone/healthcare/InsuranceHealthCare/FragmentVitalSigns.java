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
import com.mindyourlovedone.healthcare.Activity.AddVitalSignsActivity;
import com.mindyourlovedone.healthcare.DashBoard.InstructionActivity;
import com.mindyourlovedone.healthcare.DashBoard.NoteAdapter;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.VitalQuery;
import com.mindyourlovedone.healthcare.model.Note;
import com.mindyourlovedone.healthcare.model.VitalSigns;
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
import java.util.Collections;
import java.util.Comparator;

public class FragmentVitalSigns extends Fragment implements View.OnClickListener {
    final String dialog_items[] = {"View", "Email", "User Instructions"};
    ImageView imgRight;
    View rootview;
    RecyclerView lvVital;
    ArrayList<VitalSigns> vitalList;
    RelativeLayout llAddVital;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout rlGuide;
    TextView txtMsg, txtFTU;
    TextView txthelp;
    ImageView imghelp;
    FloatingActionButton floatProfile;
    ImageView floatAdd, floatOption;

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
        rootview = inflater.inflate(R.layout.fragment_vital_signs, null);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        //Initialize database, get primary data and set data
        initComponent();
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();
        getData();
        setListData();
        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        VitalQuery v = new VitalQuery(getActivity(), dbHelper);
    }

    public void setListData() {
        if (vitalList != null && !vitalList.isEmpty()) {
            //Collections.reverse(vitalList);
            //VitalAdpater vitalAdapter = new VitalAdpater(getActivity(), vitalList, FragmentVitalSigns.this);
            // lvVital.setAdapter(vitalAdapter);
            lvVital.setSystemUiVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
        } else {
            lvVital.setSystemUiVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
            imghelp.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
        }
        if (vitalList != null && !vitalList.isEmpty()) {
            Collections.sort(vitalList, new Comparator<VitalSigns>() {
                SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy hh:mm a");

                @Override
                public int compare(VitalSigns o1, VitalSigns o2) {
                    try {
                        String date1 = o2.getDate() + " " + o2.getTime();
                        String date2 = o1.getDate() + " " + o1.getTime();
                        return f.parse(date1).compareTo(f.parse(date2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

            });
            VitalAdpater vitalAdapter = new VitalAdpater(getActivity(), vitalList, FragmentVitalSigns.this);
            lvVital.setAdapter(vitalAdapter);
        }
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        llAddVital.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOption.setOnClickListener(this);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        //shradha
        floatProfile = rootview.findViewById(R.id.floatProfile);
        floatAdd = rootview.findViewById(R.id.floatAdd);
        floatOption = rootview.findViewById(R.id.floatOptions);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);

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
                intentEmerInstruc.putExtra("From", "VitalInstruction");
                startActivity(intentEmerInstruc);
            }
        });
        imgRight = getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        rlGuide = rootview.findViewById(R.id.rlGuide);
        llAddVital = rootview.findViewById(R.id.llAddVital);
        lvVital = rootview.findViewById(R.id.lvVital);
        setListData();

        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvVital.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvVital.addItemDecoration(new VerticalSpaceItemDecoration(0));

        //or
        lvVital.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...
    }

    /**
     * Function: Delete selected contact
     */
    public void deleteRecord(final VitalSigns item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = VitalQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Vital Sign has been deleted succesfully", Toast.LENGTH_SHORT).show();
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
     * Function: Fetch all Vital sin record
     */
    public void getData() {
        vitalList = new ArrayList<>();
        vitalList = VitalQuery.fetchAllVitalRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    /**
     * Function: To display floating menu for add new profile
     */
    private void showFloatDialog() {

        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "VitalSign.pdf");
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
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "VITAL SIGNS", calendarWite, profileWite);

        HeaderNew.addusereNameChank("VITAL SIGNS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("eve_four.png", getActivity());
        ArrayList<VitalSigns> HospitalList = VitalQuery.fetchAllVitalRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        new EventPdfNew("Vital", HospitalList, pp);
        HeaderNew.document.close();
//--------------------------------------------------------------------------------------
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
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
                        + "/VitalSign.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Vital Signs");

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
                        + "/VitalSign.pdf";

                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getHospitalInfo());

                new PDFDocumentProcess(path,
                        getActivity(), result);

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
            case R.id.floatOptions:
                showFloatDialog();
                break;

            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;
            case R.id.floatAdd://Add New Contact
                Intent intentVital = new Intent(getActivity(), AddVitalSignsActivity.class);
                intentVital.putExtra("Date", "Date");
                intentVital.putExtra("Time", "Time");
                startActivity(intentVital);
                break;
            case R.id.llAddVital:
                preferences.putString(PrefConstants.SOURCE, "Vital");
                Intent i = new Intent(getActivity(), AddVitalSignsActivity.class);
                i.putExtra("IsEdit", false);
                startActivity(i);
                break;
            case R.id.imgRight:
              /*  Bundle bundle = new Bundle();
                bundle.putInt("VitalSigns_Instruction", 1);
                mFirebaseAnalytics.logEvent("OnClick_QuestionMark", bundle);
*/
                Intent ifd = new Intent(getActivity(), InstructionActivity.class);
                ifd.putExtra("From", "VitalInstruction");
                startActivity(ifd);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }

    /**
     * Function: Delete selected contact
     */
    public void deleteVital(final VitalSigns item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = VitalQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Vital Sign has been deleted succesfully", Toast.LENGTH_SHORT).show();
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
}



