package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FragmentPrescriptionInfo;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.PrescriptionQuery;
import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.model.Prescription;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.Header;
import com.mindyourlovedone.healthcare.pdfdesign.PrescriptionPdf;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_PRES = 100;
    private static final String TARGET_BASE_PATH = "/sdcard/MYLO/images/";
    // final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    RecyclerView lvPrescription;
    // ListView lvPrescription;
    ImageView imgBack, imgRight;
    ArrayList<Prescription> PrescriptionList;
    RelativeLayout llAddPrescription;
    RelativeLayout rlGuide;
    Preferences preferences;
    DBHelper dbHelper;
    boolean isEdit;
    TextView txtName, txtMsg, txtFTU;
    ScrollView scroll;
    FloatingActionButton floatProfile;
    FragmentPrescriptionInfo fragmentPrescriptionInfo = null;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        initComponent();
        getData();
        initUI();
        initListener();
        setPrescriptionData();
    }

    private void getData() {
        PrescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        PrescriptionQuery p = new PrescriptionQuery(context, dbHelper);
    }

    private void initListener() {
        llAddPrescription.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
    }

    private void initUI() {
        floatProfile = findViewById(R.id.floatProfile);
        scroll = findViewById(R.id.scroll);
        txtMsg = findViewById(R.id.txtMsg);
//        String msg = "To <b>add</b> information click the green bar at the bottom of the screen Add Prescription." +
//                "<br><br>" +
//                "To <b>save</b> information click the <b>check mark</b> on the top right side of the screen" +
//                "<br><br>" +
//                "To <b>edit</b> information click the arrow symbol on the right side of the screen and make changes. Save by clicking the <b>check mark</b> again." +
//                "<br><br>" +
//                "To <b>delete</b> left swipe and click the garbage can." +
//                "<br><br>" +
//                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen.";
//
//        txtMsg.setText(Html.fromHtml(msg));

        final RelativeLayout relMsg = findViewById(R.id.relMsg);
        TextView txt61 = findViewById(R.id.txtPolicy61);
        TextView txt62 = findViewById(R.id.txtPolicy62);
        TextView txt63 = findViewById(R.id.txtPolicy63);
        TextView txt64 = findViewById(R.id.txtPolicy64);
        TextView txt65 = findViewById(R.id.txtPolicy65);

        //shradha
        txt61.setText(Html.fromHtml("To <b>add</b> information click the grey bar at the bottom of the screen Add Prescription.\n\n"));
        txt62.setText(Html.fromHtml("To save  information click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the arrow symbol on the right side of the screen and make changes.Save by clicking the <b>SAVE</b> again.\n\n"));
        txt64.setText(Html.fromHtml("To <b>delete</b> the entry, left swipe the arrow symbol on the right side of the screen.\n\n"));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data click the three dots on the top right side of the screen.\n\n"));


        txtFTU = findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                txtMsg.setVisibility(View.VISIBLE);
                rlGuide.setVisibility(View.GONE);
                scroll.setVisibility(View.VISIBLE);
                relMsg.setVisibility(View.VISIBLE);
            }
        });
        txtName = findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        PrescriptionList = new ArrayList<>();
        imgBack = findViewById(R.id.imgBack);
        llAddPrescription = findViewById(R.id.llAddPrescription);
        lvPrescription = findViewById(R.id.lvPrescription);
        // lvPrescription= (ListView)findViewById(R.id.lvPrescription);
        rlGuide = findViewById(R.id.rlGuide);
        imgRight = findViewById(R.id.imgRight);

        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvPrescription.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvPrescription.addItemDecoration(new VerticalSpaceItemDecoration(48));

        //or
        lvPrescription.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));
        //...

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Prescription.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                copyFile("ic_launcher.png");
                Header.addImage(TARGET_BASE_PATH + "ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Prescriptions");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Prescription");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                ArrayList<Prescription> prescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                //     ArrayList<Dosage> DosageList= DosageQuery.fetchAllDosageRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),);

                new PrescriptionPdf(prescriptionList);

                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                            /*    case 0: // email

                             *//* emailAttachement(item);

                        ShearedValues.activityID = getApplicationContext();*//*
                                break;
                            case 1: // email

                       *//* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*//*

                                break;*/
                            case 0: // view


                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getInsuranceInfo());


                                new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                        + "/mylopdf/"
                                        + "/Prescription.pdf",
                                        context, result);

                                System.out.println("\n" + result + "\n");

                                break;

                            case 1: // email
                                File f = new File(Environment.getExternalStorageDirectory()
                                        + "/mylopdf/"
                                        + "/Prescription.pdf");
                                emailAttachement(f, "Prescription");

                                break;

                            case 2://FTU
                                Intent i = new Intent(context, InstructionActivity.class);
                                i.putExtra("From", "PrescriptionInstruction");
                                startActivity(i);
                                break;
                            /*case 2: // Fax
                                serverAttachement(Environment.getExternalStorageDirectory()
                                        + "/mylopdf/"
                                        + "/Prescription.pdf");
                                break;*/

                        }


                    }
                });

                builder.create().show();
                // ((CarePlanActivity)context).CopyAssets();
            }
        });

    }

    private void serverAttachement(String path) {
        System.out.println("Path of the file    " + path);
        //WebService.sendPDFToFax(convertFileToByteArray(file));
        new FaxCustomDialog(PrescriptionActivity.this, path).show();
    }

    private void shareAttachement(File fil) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "MIND YOUR ELDERS"); // Body

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fil));
        emailIntent.setType("application/text");
        startActivity(Intent.createChooser(emailIntent, "Send file..."));
    }

    private void emailAttachement(File f, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
        //  String name = preferences.getString(PrefConstants.CONNECTED_NAME);
        String username = preferences.getString(PrefConstants.USER_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                username + " - " + s); // subject


        String body = "Hi, \n" +
                "\n" +
               // "\n" + username +
                "I shared these document with you. Please check the attachment. \n" +
                "\n" +
                "Thank you,\n" +
                "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", f);
        } else {
            uri = Uri.fromFile(f);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void deletePrescription(final Prescription item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = PrescriptionQuery.deleteRecord(item.getUnique());
                if (flag == true) {
                    ArrayList<PrescribeImage> imageList = new ArrayList<>();
                    imageList = item.getPrescriptionImageList();
//                    for(int i=0;i<imageList.size();i++){
//                        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH) + imageList.get(i).getImage());//nikita
//                        if (imgFile.exists()) {
//                            imgFile.delete();
//                        }
//                    }
                    Toast.makeText(context, "Prescription has been deleted succesfully", Toast.LENGTH_SHORT).show();
                    getData();
                    setPrescriptionData();
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


    private void setPrescriptionData() {
        if (PrescriptionList.size() != 0) {
            lvPrescription.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            scroll.setVisibility(View.GONE);
        } else {
            rlGuide.setVisibility(View.VISIBLE);
            lvPrescription.setVisibility(View.GONE);
            scroll.setVisibility(View.GONE);
        }
        PrescriptionAdapter adapter = new PrescriptionAdapter(context, PrescriptionList);
        lvPrescription.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
              //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               // intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;

            case R.id.imgBack:
                finish();
                break;
            case R.id.llAddPrescription:
                // preferences.putString(PrefConstants.SOURCE,"Prescription");
                Intent i = new Intent(context, AddPrescriptionActivity.class);
                i.putExtra("IsEdit", false);
                startActivityForResult(i, REQUEST_PRES);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PRES && data != null) {

      /*  Prescription p=new Prescription();
        p.setDates(data.getExtras().getString("Date"));
        p.setDoctor(data.getExtras().getString("Name"));
        p.setDosageList((ArrayList<Dosage>)data.getExtras().getSerializable("Dosage"));
        p.setPrescriptionImageList((ArrayList<PrescribeImage>) data.getExtras().getSerializable("Image"));*/

            //  PrescriptionList.add((Prescription) data.getSerializableExtra("PrObj"));
            //  setPrescriptionData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setPrescriptionData();
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
}
