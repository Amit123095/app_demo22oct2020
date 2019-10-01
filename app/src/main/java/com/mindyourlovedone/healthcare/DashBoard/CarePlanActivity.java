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

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.Fragment.ADInfoActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
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
    TextView txtOne, txtTwo, txtUserName, txtClick;
   // FloatingActionButton floatOptions;
    ImageView floatOptions;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_plan_new);
        initComponent();
        initUI();
        initListener();
       /* StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getDocuments();

        setDocuments();*/
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        DocumentQuery d = new DocumentQuery(context, dbHelper);
    }

    private void setDocuments() {

    }

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

        //llAddDoc.setOnClickListener(this);
    }

    private void initUI() {
        floatOptions = findViewById(R.id.floatOptions);
        txtClick = findViewById(R.id.txtClick);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserName.setBackgroundColor(getResources().getColor(R.color.colorDirectiveSubRed));
       String rel ="<b>"+preferences.getString(PrefConstants.CONNECTED_NAME)+"</b>-"+preferences.getString(PrefConstants.CONNECTED_RELATION);
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

        //  String textone = "Click <b><a href='http://www.myhealthcarewishes.com'>here </a> </b> to learn more about them, why they are important, and how to get them completed.";
        String textone = "Click <b><a href=''>here </a> </b> to learn more about them, why they are important, and how to get them completed.";
       /* txtOne.setText(Html.fromHtml(textone));
        txtOne.setClickable(true);
        txtOne.setMovementMethod(LinkMovementMethod.getInstance());*/

        txtOne.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) txtOne.getText();
        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent(context, ResourceActivity.class);
                startActivity(i);
            }
        };
        spans.setSpan(clickSpan, 6, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtTwo.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans2 = (Spannable) txtTwo.getText();
        ClickableSpan clickSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent(context, ChecklistActivity.class);
                startActivity(i);
            }
        };
        spans2.setSpan(clickSpan2, 14, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


       /* String texttwo = "Download this <b><a href=''> Checklist </a> </b> for help updating and organizing your estate planning documents.";

        // String texttwo = "Download this <b><a href='http://www.myhealthcarewishes.com'> Checklist </a> </b> for help updating and organizing your estate planning documents.";
        txtTwo.setText(Html.fromHtml(texttwo));
        txtTwo.setClickable(true);
        txtTwo.setMovementMethod(LinkMovementMethod.getInstance());*/
    /*   lvDoc= (ListView) findViewById(R.id.lvDoc);
        llAddDoc= (RelativeLayout) findViewById(R.id.llAddDoc);
        DocumentAdapter documentAdapter=new DocumentAdapter(context,documentList);
        lvDoc.setAdapter(documentAdapter);*/
    }

    private void getDocuments() {
      /*  documentList=new ArrayList<>();

        Document P1=new Document();
        P1.setName("HealthCare Proxy");
        P1.setImage(R.drawable.pdf);
        P1.setDesc("This is Helath care proxy Document");
        P1.setDocument("healthcare_proxy_nys.pdf");

        Document P2=new Document();
        P2.setName("Living Will");
        P2.setImage(R.drawable.pdf);
        P2.setDesc("This is Living will form");
        P2.setDocument("HCD_forms.pdf");

        Document P3=new Document();
        P3.setName("HIPAA Form");
        P3.setImage(R.drawable.pdf);
        P3.setDesc("This is Hippa form");
        P3.setDocument("ABA Consumer Tool Kit.pdf");

        Document P4=new Document();
        P4.setName("DNR");
        P4.setImage(R.drawable.pdf);
        P4.setDesc("This is dnr form");
        P4.setDocument("dnr.pdf");

        documentList.add(P1);
        documentList.add(P2);
        documentList.add(P3);
        documentList.add(P4 );*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtClick:
                Intent intentf = new Intent(CarePlanActivity.this, ADInfoActivity.class);
                startActivity(intentf);
                break;
            case R.id.floatOptions:
                showFloatDialog();
                break;

            case R.id.imgBack:
                finish();
                break;

            case R.id.imgHomes:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;

            case R.id.imgRight:
                //showInstructionDialog();
                Intent ia = new Intent(context, InstructionActivity.class);
                ia.putExtra("From", "DirectiveSection");
                startActivity(ia);
               /* final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Documents.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                copyFile("ic_launcher.png");
                Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Documents");//preferences.getString(PrefConstants.CONNECTED_NAME));
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

                  *//* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Specialty");*//*

              *//*  Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*//*

                ArrayList<Document> AdList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "AD");
                ArrayList<Document> OtherList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Other");
                ArrayList<Document> RecordList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Record");
                new DocumentPdf(AdList);
                new DocumentPdf(OtherList, 1);
                new DocumentPdf(RecordList, "Record");

                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/Documents.pdf";
                        switch (itemPos) {
                            case 0: // view
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getAdvanceDocuments());
                                result.append(new MessageString().getOtherDocuments());
                                result.append(new MessageString().getRecordDocuments());


                                new PDFDocumentProcess(path,
                                        context, result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f = new File(path);
                                preferences.emailAttachement(f, context, "Documents");
                                break;
                            case 2://FTU
                                showInstructionDialog();
                                break;
                           *//* case 2://fax
                                new FaxCustomDialog(context, path).show();
                                break;*//*
                           *//* case 3://FTU
                                Intent intentShare = new Intent(context, InstructionActivity.class);
                                intentShare.putExtra("From", "SharePdf");
                                startActivity(intentShare);
                                break;*//*
                        }
                    }

                });
                builder.create().show();*/
                break;

            case R.id.rlOther:
                Intent i = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Other");
                startActivity(i);
                break;

            case R.id.rlMedical:
                Intent i1 = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Medical");
                startActivity(i1);
                break;

            case R.id.rlInsurance:
                Intent i2 = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Insurance");
                startActivity(i2);
                break;

            case R.id.rlHome:
                Intent i3 = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Home");
                startActivity(i3);
                break;

            case R.id.rlAD:
                Intent i4 = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "AD");

                startActivity(i4);
                break;
            case R.id.rlMedicalRecord:
                Intent intent = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Record");
//                intent.putExtra("Ins", "MedicalIns");
                startActivity(intent);
                break;

            case R.id.rlLegal:
                Intent i5 = new Intent(context, CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM, "Legal");
//                i5.putExtra("Ins", "LegalIns");
                startActivity(i5);
                break;

          /*  case R.id.llAddDoc:
                Intent i=new Intent(context,AddDocumentActivity.class);
                startActivity(i);
                break;*/
        }
    }

    private void showFloatDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "AdvDirectivesOtherDocs.pdf");
        if (file.exists()) {
            file.delete();
        }
        /*new Header().createPdfHeader(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
        copyFile("ic_launcher.png");
        Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
        Header.addEmptyLine(1);
        Header.addusereNameChank("Documents");//preferences.getString(PrefConstants.CONNECTED_NAME));
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

                  *//* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Specialty");*//*

              *//*  Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*//*

        ArrayList<Document> AdList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "AD");
        ArrayList<Document> OtherList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Other");
        ArrayList<Document> RecordList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Record");
        new DocumentPdf(AdList);
        new DocumentPdf(OtherList, 1);
        new DocumentPdf(RecordList, "Record");

        Header.document.close();*/

        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png", context);
        calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
        profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"DOCUMENTS", calendarWite, profileWite);

        HeaderNew.addusereNameChank("DOCUMENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);

        ArrayList<Document> AdList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "AD");
        ArrayList<Document> OtherList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Other");
        ArrayList<Document> RecordList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Record");
        Image pp1 = null,pp2 = null,pp3 = null;
        pp1=preferences.addFile("dir_one.png", context);
        pp2=preferences.addFile("dir_two.png", context);
        pp3=preferences.addFile("dir_three.png", context);
        new DocumentPdfNew(AdList,pp1);
        new DocumentPdfNew(OtherList, 1,pp2);
        new DocumentPdfNew(RecordList, "Record",pp3);

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
                        + "/AdvDirectivesOtherDocs.pdf";

                File f = new File(path);
                preferences.emailAttachement(f, context, "Documents");
                dialog.dismiss();
            }

        });

        floatContact.setOnClickListener(new View.OnClickListener() {
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


    @SuppressLint("ResourceAsColor")
    private void showInstructionDialog() {
        final Dialog dialogInstruction = new Dialog(context);
        dialogInstruction.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInstruction.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_user_instruction, null);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);

        dialogInstruction.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogInstruction.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogInstruction.getWindow().setAttributes(lp);
        dialogInstruction.setCanceledOnTouchOutside(false);
        dialogInstruction.show();


        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInstruction.dismiss();
            }
        });
    }


    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;

        try {
            File dir = new File(TARGET_BASE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.i("tag", "copyFile() " + filename);
            in = assetManager.open(filename);
            newFileName = TARGET_BASE_PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of " + newFileName);
            Log.e("tag", "Exception in copyFile() " + e.toString());
        }

    }

    public void CopyReadAssets(String fileName) {
        File dir = new File(Environment.getExternalStorageDirectory()
                + "/mhcw/");
        dir.mkdirs();

        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory()
                + "/mhcw/" + fileName);
        try {
            in = assetManager.open(fileName);
            out = new BufferedOutputStream(new FileOutputStream(file));

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public void onPDFClicked(String fileName) {
        final File item = new File(fileName);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("");

        builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int itemPos) {

                switch (itemPos) {
                    case 0: // email

                       /* emailAttachement(item);

                        ShearedValues.activityID = getApplicationContext();*/
                        break;
                    case 1: // email

                       /* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*/

                        break;
                    case 2: // view

                        String path_of_file = item.getAbsolutePath();
                        viewFile(context, path_of_file);

                        // ShearedValues.activityID = getApplicationContext();
                        break;

                }
            }
        });

        builder.create().show();
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
            //intent.setPackage("com.adobe.reader");//varsa
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

    public void CopyAssets() {
        File fileBrochure = new File(Environment.getExternalStorageDirectory() + "/mhcw/MHCW_APP_Wallet_Card.pdf");
        if (!fileBrochure.exists()) {
            CopyAssetsbrochure();
        }

        /** PDF reader code */
        File file = new File(Environment.getExternalStorageDirectory() + "/mhcw/MHCW_APP_Wallet_Card.pdf");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Toast.makeText(SecondActivity.this, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
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
