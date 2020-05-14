package com.mindyourlovedone.healthcare.DashBoard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.Fragment.ADInfoActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.SpecialistsActivity;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DocumentQuery;
import com.mindyourlovedone.healthcare.model.Document;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.DocumentPdfNew;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Class: CarePlanActivity
 * Screen: Advance Directive SubSection List Screen
 * A class that manages to Display Advance Directive SubSection List i.e AD,OD,MR
 * implements OnclickListener for onClick event on views
 */
public class CarePlanActivity extends AppCompatActivity implements View.OnClickListener {
    final static String TARGET_BASE_PATH = "/sdcard/MYLO/images/";
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    ListView lvDoc;
    ArrayList<Document> documentList;
    ImageView imgBack, imgRight, imgHomes;
    RelativeLayout llAddDoc;
    Preferences preferences;
    DBHelper dbHelper;

    RelativeLayout rlAD, rlHome, rlMedical, rlInsurance, rlOther, rlLegal, rlMedicalRecord;
    TextView txtOne, txtTwo, txtUserName, txtClick,txtTitle;
    ImageView floatOptions;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_plan_new);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(CarePlanActivity.this,"Adv_Directive_Docs_SubSection_List",null);

        //Initialize database, get primary data and set data
        initComponent();

        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();
    }

    /**
     * Function: Initialize Database,preferences
     */
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        DocumentQuery d = new DocumentQuery(context, dbHelper);
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        floatOptions.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        rlAD.setOnClickListener(this);
        rlMedicalRecord.setOnClickListener(this);
        rlLegal.setOnClickListener(this);
        rlHome.setOnClickListener(this);
        rlMedical.setOnClickListener(this);
        rlInsurance.setOnClickListener(this);
        rlOther.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        imgHomes.setOnClickListener(this);
        txtClick.setOnClickListener(this);

    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        floatOptions = findViewById(R.id.floatOptions);
        txtClick = findViewById(R.id.txtClick);
        txtTitle=findViewById(R.id.txtTitle);

        txtUserName = findViewById(R.id.txtUserName);
        txtUserName.setBackgroundColor(getResources().getColor(R.color.colorDirectiveSubRed));
        String rel = "";
        if (preferences.getString(PrefConstants.CONNECTED_RELATION).equals("Other")) {
            rel = "<b>" + preferences.getString(PrefConstants.CONNECTED_NAME) + "</b> - " + preferences.getString(PrefConstants.CONNECTED_OtherRELATION);
        } else {
            rel = "<b>" + preferences.getString(PrefConstants.CONNECTED_NAME) + "</b> - " + preferences.getString(PrefConstants.CONNECTED_RELATION);
        }

        txtUserName.setText(Html.fromHtml(rel));
        imgBack = findViewById(R.id.imgBack);
        imgRight = findViewById(R.id.imgRight);
        imgHomes = findViewById(R.id.imgHomes);
        rlAD = findViewById(R.id.rlAD);
        rlMedicalRecord = findViewById(R.id.rlMedicalRecord);
        rlLegal = findViewById(R.id.rlLegal);
        rlHome = findViewById(R.id.rlHome);
        rlMedical = findViewById(R.id.rlMedical);
        rlInsurance = findViewById(R.id.rlInsurance);
        rlOther = findViewById(R.id.rlOther);
        txtOne = findViewById(R.id.txtOne);
        txtTwo = findViewById(R.id.txtTwo);
        rlOther = findViewById(R.id.rlOther);

        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            txtTitle.setText("Medical & Other Important Documents");
            rlAD.setVisibility(View.GONE);
        }else{
            txtTitle.setText("Advance Directives & Documents");
            rlAD.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtClick://Navigate to Advance Directive resources
                Intent intentf = new Intent(CarePlanActivity.this, ADInfoActivity.class);
                startActivity(intentf);
                break;
            case R.id.floatOptions://Reports
                showFloatDialog();
                break;

            case R.id.imgBack://Back
                finish();
                break;

            case R.id.imgHomes://Home Screen
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;

            case R.id.imgRight://Instruction Screen

                Bundle bundle = new Bundle();
                bundle.putInt("Directive_Section_Instruction", 1);
                mFirebaseAnalytics.logEvent("OnClick_QuestionMark", bundle);
                Intent ia = new Intent(context, InstructionActivity.class);
                ia.putExtra("From", "DirectiveSection");
                startActivity(ia);
                break;

            case R.id.rlOther://Navigate to Other Document list
                Intent i = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Other");
                startActivity(i);
                break;

            case R.id.rlAD://Navigate to Advance directive Document list
                Intent i4 = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "AD");
                startActivity(i4);
                break;

            case R.id.rlMedicalRecord://Navigate to Medical record list
                Intent intent = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Record");
                startActivity(intent);
                break;

        }
    }

    /**
     * Function: To display floating menu for report
     */
    private void showFloatDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "AdvDirectivesOtherDocs.pdf");
        if (file.exists()) {
            file.delete();
        }

        Image pdflogo = null, calendar = null, profile = null, calendarWite = null, profileWite = null;
        pdflogo = preferences.addFile("pdflogo.png", context);
        calendar = preferences.addFile("calpdf.png", context);
        calendarWite = preferences.addFile("calpdf_wite.png", context);
        profile = preferences.addFile("profpdf.png", context);
        profileWite = preferences.addFile("profpdf_wite.png", context);

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME), preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO), pdflogo, calendar, profile, "DOCUMENTS", calendarWite, profileWite);
        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            HeaderNew.addusereNameChank("MEDICAL & OTHER IMPORTANT DOCUMENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        }else
        {
            HeaderNew.addusereNameChank("DOCUMENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        }
        HeaderNew.addEmptyLine(1);

        ArrayList<Document> AdList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "AD");
        ArrayList<Document> OtherList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Other");
        ArrayList<Document> RecordList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Record");
        Image pp1 = null, pp2 = null, pp3 = null;
        pp1 = preferences.addFile("dir_one.png", context);
        pp2 = preferences.addFile("dir_two.png", context);
        pp3 = preferences.addFile("dir_three.png", context);
        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            //new DocumentPdfNew(AdList, pp1);
            new DocumentPdfNew(OtherList, 1, pp2);
            new DocumentPdfNew(RecordList, "Record", pp3,context);
        }else
        {
            new DocumentPdfNew(AdList, pp1);
            new DocumentPdfNew(OtherList, 1, pp2);
            new DocumentPdfNew(RecordList, "Record", pp3,context);
        }

        HeaderNew.document.close();

        //----------------------------------------------
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        //  final ImageView floatCancel = dialogview.findViewById(R.id.floatCancel);        // Rahul
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
                        + "/AdvDirectivesOtherDocs.pdf";

                File f = new File(path);
                if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
                {
                    preferences.emailAttachement(f, context, "Medical & Other Important Documents");
                }else
                {
                    preferences.emailAttachement(f, context, "Documents");
                }

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
                        + "/AdvDirectivesOtherDocs.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getAdvanceDocuments());
                result.append(new MessageString().getOtherDocuments());
                result.append(new MessageString().getRecordDocuments());
                new PDFDocumentProcess(path,
                        context, result);
                System.out.println("\n" + result + "\n");
                dialog.dismiss();
            }

        });
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void viewFile(Context context, String filename) {
        File targetFile = new File(filename);
        Uri targetUri = Uri.fromFile(targetFile);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileprovider", targetFile);
            intent.setDataAndType(contentUri, "application/pdf");
        } else {
            intent.setDataAndType(Uri.fromFile(targetFile), "application/pdf");
        }

        if (targetFile.getName().endsWith(".pdf")) {
            intent.setDataAndType(targetUri, "application/pdf");

            try {
                startActivity(intent);

            } catch (ActivityNotFoundException e) {
                // No application to view, ask to download one

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No Application Found");
                builder.setMessage("Download Office Tool from Google Play ?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent marketIntent = new Intent(
                                        Intent.ACTION_VIEW);
                                marketIntent.setData(Uri
                                        .parse("market://details?id=cn.wps.moffice_eng"));//varsa ("market://details?id=com.adobe.reader"));
                                startActivity(marketIntent);
                            }
                        });
                builder.setNegativeButton("No", null);
                builder.create().show();
            }

        }
    }

    //method to write the PDFs file to sd card
    private void CopyAssetsbrochure() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i < files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase("abc.pdf")) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }
}
