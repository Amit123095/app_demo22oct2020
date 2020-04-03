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
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.FormQuery;
import com.mindyourlovedone.healthcare.database.InsuranceQuery;
import com.mindyourlovedone.healthcare.model.Card;
import com.mindyourlovedone.healthcare.model.Document;
import com.mindyourlovedone.healthcare.model.Form;
import com.mindyourlovedone.healthcare.model.Insurance;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.InsurancePdfNew;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by shradha on 8/02/2019.
 */

/**
 * Class: FragementForm
 * A class that manages an insurance form list
 * extends Fragment
 * implements OnclickListener for onclick event on views
 */
public class FragementForm extends Fragment implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final String dialog_items[] = {"View", "Email", "User Instructions"};
    View rootview;
    ImageView imgRight;
    RecyclerView lvDoc;
    ArrayList<Form> documentList;
    ArrayList<Document> documentListOld;
    ImageView imgBack;
    TextView txtTitle, txtMsg, txtFTU;
    String From;
    RelativeLayout llAddDoc;
    Preferences preferences;
    DBHelper dbHelper;
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
        rootview = inflater.inflate(R.layout.fragment_insurance_form, null);
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
     * Function:Initialize preferences, database
     */
    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        FormQuery i = new FormQuery(getActivity(), dbHelper);
    }

    /**
     * Function: Set Form list data
     */
    public void setListData() {
        if (documentList.size() != 0) {
            Collections.sort(documentList, new Comparator<Form>() {
                @Override
                public int compare(Form o1, Form o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
            DocumentsAdapter insuranceAdapter = new DocumentsAdapter(getActivity(), documentList, FragementForm.this);
            lvDoc.setAdapter(insuranceAdapter);
            lvDoc.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
        } else {
            lvDoc.setVisibility(View.GONE);
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
        //  imgADMTick.setOnClickListener(this);
        llAddDoc.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
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
        txtMsg = rootview.findViewById(R.id.txtMsg);

        final RelativeLayout relMsg = rootview.findViewById(R.id.relMsg);
        TextView txt61 = rootview.findViewById(R.id.txtPolicy61);
        TextView txt62 = rootview.findViewById(R.id.txtPolicy62);
        TextView txt63 = rootview.findViewById(R.id.txtPolicy63);
        TextView txt64 = rootview.findViewById(R.id.txtPolicy64);
        TextView txt65 = rootview.findViewById(R.id.txtPolicy65);
        TextView txt66 = rootview.findViewById(R.id.txtPolicy66);
        TextView txt67 = rootview.findViewById(R.id.txtPolicy67);
        TextView txt68 = rootview.findViewById(R.id.txtPolicy68);
        ImageView img68 = rootview.findViewById(R.id.img68);

        //shradha
        txt61.setText(Html.fromHtml("To <b>add</b> information click the bar at the bottom of the screen. Click the add sign to Select the File.\n\n"));
        txt62.setText(Html.fromHtml("The file is either sitting on your phone or in your Dropbox. Choose the location and click Add.\n\n"));
        txt63.setText(Html.fromHtml("If Dropbox click on the file, then click the <b>save</b> on upper right side of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>load an email attachment</b>, open attachment from your email, and click the forward button on upper right side of the screen.Scroll through the Apps until you find MYLO. Click MYLO - then click the Profile you wish to attach the document to, then click the subsection the document pertains to and click OK. Enter additional data, then  click <b>Save</b>\n\n"));
        txt65.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits click the <b>SAVE</b> again.\n\n"));
        txt66.setText(Html.fromHtml("To <b>delete</b> the entry swipe the arrow symbol on the <b>right side</b> of the screen.\n\n"));
        txt67.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data in each section click the three dots on the upper right side of the screen\n\n"));
        txt68.setText(Html.fromHtml("\n"));
        img68.setVisibility(View.GONE);

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
                intentEmerInstruc.putExtra("From", "FormInstruction");
                startActivity(intentEmerInstruc);
            }
        });
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddDoc = rootview.findViewById(R.id.llAddDoc);
        rlGuide = rootview.findViewById(R.id.rlGuide);
        imgRight = getActivity().findViewById(R.id.imgRight);
        lvDoc = rootview.findViewById(R.id.lvDoc);
        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvDoc.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvDoc.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        //or
        lvDoc.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...

        setListData();
    }

    /**
     * Function: Delete selected Document Form
     */
    public void deleteDocument(final Form item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = FormQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Form has been deleted succesfully", Toast.LENGTH_SHORT).show();
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

    private void deleteInsurance(Insurance item) {
        boolean flag = InsuranceQuery.deleteRecord(item.getId());
        if (flag == true) {
            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    /**
     * Function: Fetch all Form record
     */
    public void getData() {
        documentList = new ArrayList<>();
        documentList = FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
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
                //    intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //   intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;
            case R.id.floatAdd://Add New Contact
                //preferences.putString(PrefConstants.SOURCE, "InsuranceForm");
                Intent i = new Intent(getActivity(), AddInsuranceFormActivity.class);
                i.putExtra("GoTo", "Add");
                startActivity(i);
                break;

            case R.id.floatOptions://Reports
                showFloatPdfDialog();
                break;

            case R.id.imgRight://Instructions
                Bundle bundle = new Bundle();
                bundle.putInt("InsuranceForm_Instruction",1);
                mFirebaseAnalytics.logEvent("OnClick_QuestionMark", bundle);


                Intent ib = new Intent(getActivity(), InstructionActivity.class);
                ib.putExtra("From", "FormInstruction");
                startActivity(ib);
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
        File file = new File(dirfile, "InsuranceForm.pdf");
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
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "INSURANCE FORM", calendarWite, profileWite);

        HeaderNew.addusereNameChank("INSURANCE FORM");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp = preferences.addFile("insu_one.png", getActivity());
        ArrayList<Form> formList = FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        new InsurancePdfNew(formList, "form", pp);
        HeaderNew.document.close();
        //----------------------------------
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
                        + "/InsuranceForm.pdf";

                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Insurance Form");
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
                        + "/InsuranceForm.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getFormInfo());

                new PDFDocumentProcess(path,
                        getActivity(), result);

                System.out.println("\n" + result + "\n");
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
