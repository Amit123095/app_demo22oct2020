package com.mindyourlovedone.healthcare.DashBoard;

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
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


public class CarePlanListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    RecyclerView lvDoc;//by nikita on 20/6/18
    ArrayList<Document> documentList;
    ArrayList<Document> documentListOld = new ArrayList<>();
    ImageView imgBack, imgRight;
    TextView txtTitle, txtAdd;
    String From;
    RelativeLayout llAddDoc, header;
    Preferences preferences;
    RelativeLayout rlGuide;
    ImageView imgPicture, imgPicture2, imgHome,imghelp;
    TextView txtHeader, txtHeader2, txtMsg, txtFTU,txtHelp;
    DBHelper dbHelper;
    ScrollView scroll;
    ImageView floatProfile, floatOptions, floatAdd;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_careplan);
        initComponent();
        initUI();
        initListener();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //  getDocuments();
        setDocuments();
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        DocumentQuery d = new DocumentQuery(context, dbHelper);
    }

    public void setDocuments() {
        if (documentListOld.size() != 0) {
            lvDoc.setVisibility(View.VISIBLE);
            DocumentAdapter documentAdapter = new DocumentAdapter(context, documentListOld);
            lvDoc.setAdapter(documentAdapter);
            lvDoc.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            imghelp .setVisibility(View.GONE);
            txtHelp.setVisibility(View.GONE);
         //   scroll.setVisibility(View.GONE);
        } else {
            lvDoc.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
            imghelp .setVisibility(View.VISIBLE);
            txtHelp.setVisibility(View.VISIBLE);
         //   scroll.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        llAddDoc.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        imgHome.setOnClickListener(this);
    }

    /*  lvNote.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
      SwipeMenuCreation s = new SwipeMenuCreation();
      SwipeMenuCreator creator = s.createSingleMenu(context);
      lvNote.setMenuCreator(creator);*/
    private void initUI() {

        header = findViewById(R.id.header);
        //nikita
        floatProfile = findViewById(R.id.floatProfile);
        floatAdd = findViewById(R.id.floatAdd);
        floatOptions = findViewById(R.id.floatOptions);
        imgHome = findViewById(R.id.imgHome);
        txtHelp=findViewById(R.id.txtHelp);
        imghelp = findViewById(R.id.imghelp);

        final RelativeLayout relMsg = findViewById(R.id.relMsg);
        TextView txt61 = findViewById(R.id.txtPolicy61);
        TextView txt62 = findViewById(R.id.txtPolicy62);
        TextView txt63 = findViewById(R.id.txtPolicy63);
        TextView txt64 = findViewById(R.id.txtPolicy64);
        TextView txt65 = findViewById(R.id.txtPolicy65);
        TextView txt66 = findViewById(R.id.txtPolicy66);
        TextView txt67 = findViewById(R.id.txtPolicy67);

        imgBack = findViewById(R.id.imgBack);
        imgPicture = findViewById(R.id.imgPicture);
        imgPicture2 = findViewById(R.id.imgPicture2);
        txtHeader = findViewById(R.id.txtHeader);
        txtHeader2 = findViewById(R.id.txtHeader2);

        txtMsg = findViewById(R.id.txtMsg);
        txtFTU = findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Shradha*/
              /*  From = preferences.getString(PrefConstants.FROM);
                if (txtFTU.equals(From.equals("AD"))*//*From.equals("AD")*//*) {
                    txtHeader.setText("Add Advance Directives");
                } else if (txtFTU.equals(From.equals("Record"))) {
                    txtHeader.setText("Add Medical Records");
                } else if (txtFTU.equals(From.equals("Other"))*//*From.equals("Other")*//*) {
                    txtHeader.setText("Add Other Documents");
                } else {
                    Toast.makeText(context, "I'm not going in this..!!", Toast.LENGTH_SHORT).show();
                }*/
//                txtMsg.setVisibility(View.VISIBLE);
                rlGuide.setVisibility(View.GONE);//nikita
            //    scroll.setVisibility(View.VISIBLE);//nikita
                relMsg.setVisibility(View.VISIBLE);//nikita
            }
        });
      //  scroll = findViewById(R.id.scrollvw);
        imgRight = findViewById(R.id.imgRight);

        //Changes done by nikita on 20/6/18
        lvDoc = findViewById(R.id.lvDoc);
        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvDoc.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvDoc.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        //or
        lvDoc.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));
        //...

        rlGuide = findViewById(R.id.rlGuide);

        llAddDoc = findViewById(R.id.llAddDoc);
        llAddDoc = findViewById(R.id.llAddDoc);
        txtTitle = findViewById(R.id.txtTitle);
        txtAdd = findViewById(R.id.txtAdd);
        txtTitle.setAllCaps(false);
        From = preferences.getString(PrefConstants.FROM);
        switch (From) {
            case "AD":
                imgPicture.setImageResource(R.drawable.dir_one);
                txtHelp.setText("Add a new Advance Directive!");
                header.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                txtTitle.setText("Advance Directives");
                txtAdd.setText("Add Advance Directives");
                txtFTU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgPicture.setImageResource(R.drawable.dir_one);
                        Intent intentEmerInstruc = new Intent(context, InstructionActivity.class);
                        intentEmerInstruc.putExtra("From", "AdvanceInstruction");
                        startActivity(intentEmerInstruc);
                    }
                });
             /*   txtTitle.setText("Advance Directives");
                txtAdd.setText("Add Advance Directives");
                txtHeader.setText("Add Adv. Directive Docs");
                txtHeader2.setText("Add Adv. Directive Docs");

                imgPicture.setImageResource(R.drawable.dir_one);
                imgPicture2.setImageResource(R.drawable.dir_one);

                //shradha
                txt61.setText(Html.fromHtml("To <b>add</b> a document click the red bar at the bottom of the screen. Click the <b>plus</b> symbol to select the file.\n\n"));
                txt62.setText(Html.fromHtml("The file is either sitting in your File Manager or Dropbox or in your email as an attachment.\n\n"));
                txt63.setText(Html.fromHtml("If Dropbox click on file, then complete screen. When completed click <b>save</b> on the upper right of the screen.\n\n"));
                txt64.setText(Html.fromHtml("To <b>load an email attachment</b>,open attachment from your email, and click the forward button on the upper right side of the screen. Scroll through the Apps until you find MYLO. Click MYLO - then click the Profile you wish to attach the document to, then click the subsection the document pertains to and click OK.Enter additional data,then click <b>Save</b>.\n\n"));
                txt65.setText(Html.fromHtml("To <b>edit</b> information click the picture of the pencil. When finished making your changes click <b>save</b>\n\n"));
                txt66.setText(Html.fromHtml("To <b>delete</b> the entry swipe the green arrow from right to left.\n\n"));
                txt67.setText(Html.fromHtml("To <b>view</b> a report or to <b>email</b> or to fax the data in each section click on the three dots on the upper right side of the screen.\n\n"));
              */
                break;
            case "Record":
                imgPicture.setImageResource(R.drawable.dir_three);
                header.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                txtTitle.setText("Medical Records");
                txtAdd.setText("Add Medical Records");
                txtHelp.setText("Add a new File!");
                txtFTU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgPicture.setImageResource(R.drawable.dir_three);
                        Intent intentEmerInstruc = new Intent(context, InstructionActivity.class);
                        intentEmerInstruc.putExtra("From", "MedicalInfoInstruction");
                        startActivity(intentEmerInstruc);
                    }
                });

              /*  txtTitle.setText("Medical Records");
                txtAdd.setText("Add Medical Records");
                txtHeader.setText("Add Medical Records");
                txtHeader2.setText("Add Medical Records");

                imgPicture.setImageResource(R.drawable.v_record);
                imgPicture2.setImageResource(R.drawable.v_record);

                //shradha
                txt61.setText(Html.fromHtml("To <b>add</b> a document click the red bar at the bottom of the screen. Click the <b>plus</b> symbol to select the file.\n\n"));
                txt62.setText(Html.fromHtml("The file is either sitting in your File Manager or Dropbox or in your email as an attachment.\n\n"));
                txt63.setText(Html.fromHtml("If Dropbox click on file, then complete screen. When completed click <b>save</b> on the upper right of the screen.\n\n"));
                txt64.setText(Html.fromHtml("To <b>load an email attachment</b>,open attachment from your email, and click the forward button on the upper right side of the screen. Scroll through the Apps until you find MYLO. Click MYLO - then click the Profile you wish to attach the document to, then click the subsection the document pertains to and click OK.Enter additional data,then click <b>Save</b>.\n\n"));
                txt65.setText(Html.fromHtml("To <b>edit</b> information click the picture of the pencil. When finished making your changes click <b>save</b>\n\n"));
                txt66.setText(Html.fromHtml("To <b>delete</b> the entry swipe the green arrow from right to left.\n\n"));
                txt67.setText(Html.fromHtml("To <b>view</b> a report or to <b>email</b> or to fax the data in each section click on the three dots on the upper right side of the screen.\n\n"));
*/
                break;
            case "Other":
                imgPicture.setImageResource(R.drawable.dir_two);
                header.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                txtTitle.setText("Other Documents");
                txtHelp.setText("Add a new File!");
                txtAdd.setText("Add Other Documents");
                txtFTU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgPicture.setImageResource(R.drawable.dir_two);
                        Intent intentEmerInstruc = new Intent(context, InstructionActivity.class);
                        intentEmerInstruc.putExtra("From", "OtherInstruction");
                        startActivity(intentEmerInstruc);
                    }
                });
              /*  txtTitle.setText("Other Documents");
                txtAdd.setText("Add Other Documents");
                txtHeader.setText("Add Other Documents");
                txtHeader2.setText("Add Other Documents");

                imgPicture.setImageResource(R.drawable.v_other);
                imgPicture2.setImageResource(R.drawable.v_other);

                //shradha
                txt61.setText(Html.fromHtml("To <b>add</b> a document click the red bar at the bottom of the screen. Click the <b>plus</b> symbol to select the file.\n\n"));
                txt62.setText(Html.fromHtml("The file is either sitting in your File Manager or Dropbox or in your email as an attachment.\n\n"));
                txt63.setText(Html.fromHtml("If Dropbox click on file, then complete screen. When completed click <b>save</b> on the upper right of the screen.\n\n"));
                txt64.setText(Html.fromHtml("To <b>load an email attachment</b>,open attachment from your email, and click the forward button on the upper right side of the screen. Scroll through the Apps until you find MYLO. Click MYLO - then click the Profile you wish to attach the document to, then click the subsection the document pertains to and click OK.Enter additional data,then click <b>Save</b>.\n\n"));
                txt65.setText(Html.fromHtml("To <b>edit</b> information click the picture of the pencil. When finished making your changes click <b>save</b>\n\n"));
                txt66.setText(Html.fromHtml("To <b>delete</b> the entry swipe the green arrow from right to left.\n\n"));
                txt67.setText(Html.fromHtml("To <b>view</b> a report or to <b>email</b> or to fax the data in each section click on the three dots on the upper right side of the screen.\n\n"));
*/
                break;
            case "Legal":
                txtTitle.setText("Legal and Financial Documents");
                break;
            case "Home":
                txtTitle.setText("Home And Health Documents");
                break;
            case "Insurance":
                txtTitle.setText("Insurance Documents");
                break;
            case "Medical":
                txtTitle.setText("Medical Documents");
                break;
        }
    }

    public void deleteDocument(final Document item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = DocumentQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(context, "Document has been deleted succesfully", Toast.LENGTH_SHORT).show();
                    getData();
                    setDocuments();
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

    public void getData() {
        documentListOld = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), From);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
            case R.id.floatOptions:
                showFloatDialog();
                break;
            case R.id.floatAdd:
                Intent i = new Intent(context, AddDocumentActivity.class);
                i.putExtra("GoTo", "Add");
                startActivity(i);
                break;
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;

            case R.id.imgBack:
                finish();
                break;

           /* case R.id.llAddDoc:
                Intent i = new Intent(context, AddDocumentActivity.class);
                i.putExtra("GoTo", "Add");
                startActivity(i);
                break;*/

            case R.id.imgRight:
                switch (From) {
                    case "AD":
                        Intent iN = new Intent(context, InstructionActivity.class);
                        iN.putExtra("From", "AdvanceInstruction");
                        startActivity(iN);
                       /* final String RESULT1 = Environment.getExternalStorageDirectory()
                                + "/mylopdf/";
                        File dirfile1 = new File(RESULT1);
                        dirfile1.mkdirs();
                        File file1 = new File(dirfile1, "AdvanceDirectives.pdf");
                        if (file1.exists()) {
                            file1.delete();
                        }

                        new Header().createPdfHeader(file1.getAbsolutePath(),
                                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                        preferences.copyFile("ic_launcher.png", context);
                        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                        Header.addEmptyLine(1);
                        Header.addusereNameChank("Advance Directives");//preferences.getString(PrefConstants.CONNECTED_NAME));
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

                        ArrayList<Document> AdList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "AD");
                        new DocumentPdf(AdList);
                        Header.document.close();

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setTitle("");

                        builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int itemPos) {
                                String path = Environment.getExternalStorageDirectory()
                                        + "/mylopdf/"
                                        + "/AdvanceDirectives.pdf";
                                switch (itemPos) {
                                    case 0: // view
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getAdvanceDocuments());

                                        new PDFDocumentProcess(path,
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                                        break;
                                    case 1://Email

                                        File f = new File(path);
                                        preferences.emailAttachement(f, context, "Advance Directives");
                                        break;
                                    case 2://fax
                                        Intent i = new Intent(context, InstructionActivity.class);
                                        i.putExtra("From", "AdvanceInstruction");
                                        startActivity(i);
                                        break;
                                   *//* case 2://fax
                                        new FaxCustomDialog(context, path).show();
                                        break;*//*
                                }
                            }

                        });
                        builder.create().show();*/
                        break;
                    case "Record":
                        Intent ir = new Intent(context, InstructionActivity.class);
                        ir.putExtra("From", "MedicalInfoInstruction");
                        startActivity(ir);
                       /* final String RESULT2 = Environment.getExternalStorageDirectory()
                                + "/mylopdf/";
                        File dirfile2 = new File(RESULT2);
                        dirfile2.mkdirs();
                        File file2 = new File(dirfile2, "MedicalRecords.pdf");
                        if (file2.exists()) {
                            file2.delete();
                        }

                        new Header().createPdfHeader(file2.getAbsolutePath(),
                                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                        preferences.copyFile("ic_launcher.png", context);
                        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                        Header.addEmptyLine(1);
                        Header.addusereNameChank("Medical Records");//preferences.getString(PrefConstants.CONNECTED_NAME));
                        Header.addEmptyLine(1);
                        Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

                        Paragraph p1 = new Paragraph(" ");
                        LineSeparator line1 = new LineSeparator();
                        line1.setOffset(-4);
                        line1.setLineColor(BaseColor.LIGHT_GRAY);
                        p1.add(line1);
                        try {
                            Header.document.add(p1);
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        Header.addEmptyLine(1);

                        ArrayList<Document> RecordList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Record");
                        new DocumentPdf(RecordList, "Record");
                        Header.document.close();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);

                        builder1.setTitle("");

                        builder1.setItems(dialog_items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int itemPos) {
                                String path = Environment.getExternalStorageDirectory()
                                        + "/mylopdf/"
                                        + "/MedicalRecords.pdf";
                                switch (itemPos) {
                                    case 0: // view
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getRecordDocuments());


                                        new PDFDocumentProcess(path,
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                                        break;
                                    case 1://Email
                                        File f = new File(path);
                                        preferences.emailAttachement(f, context, "Medical Records");
                                        break;
                                    case 2://fax
                                        Intent i = new Intent(context, InstructionActivity.class);
                                        i.putExtra("From", "MedicalInfoInstruction");
                                        startActivity(i);
                                        break;

                                }
                            }

                        });
                        builder1.create().show();*/
                        break;
                    case "Other":
                        Intent iS = new Intent(context, InstructionActivity.class);
                        iS.putExtra("From", "OtherInstruction");
                        startActivity(iS);
                       /* final String RESULT3 = Environment.getExternalStorageDirectory()
                                + "/mylopdf/";
                        File dirfile3 = new File(RESULT3);
                        dirfile3.mkdirs();
                        File file3 = new File(dirfile3, "OtherDocuments.pdf");
                        if (file3.exists()) {
                            file3.delete();
                        }

                        new Header().createPdfHeader(file3.getAbsolutePath(),
                                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                        preferences.copyFile("ic_launcher.png", context);
                        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                        Header.addEmptyLine(1);
                        Header.addusereNameChank("Other Documents");//preferences.getString(PrefConstants.CONNECTED_NAME));
                        Header.addEmptyLine(1);
                        Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

                        Paragraph p2 = new Paragraph(" ");
                        LineSeparator line2 = new LineSeparator();
                        line2.setOffset(-4);
                        line2.setLineColor(BaseColor.LIGHT_GRAY);
                        p2.add(line2);
                        try {
                            Header.document.add(p2);
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        Header.addEmptyLine(1);

                        ArrayList<Document> OtherList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Other");
                        new DocumentPdf(OtherList, 1);
                        Header.document.close();
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);

                        builder2.setTitle("");

                        builder2.setItems(dialog_items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int itemPos) {
                                String path = Environment.getExternalStorageDirectory()
                                        + "/mylopdf/"
                                        + "/OtherDocuments.pdf";
                                switch (itemPos) {
                                    case 0: // view
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getOtherDocuments());
                                        new PDFDocumentProcess(path,
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                                        break;
                                    case 1://Email

                                        File f = new File(path);
                                        preferences.emailAttachement(f, context, "Other Documents");
                                        break;
                                    case 2://fax
                                        Intent i = new Intent(context, InstructionActivity.class);
                                        i.putExtra("From", "OtherInstruction");
                                        startActivity(i);
                                        startActivity(i);
                                        break;

                                }
                            }

                        });
                        builder2.create().show();*/
                        break;
                }
                break;


        }
    }

    private void showFloatDialog() {
        switch (From) {
            case "AD":
                final String RESULT1 = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile1 = new File(RESULT1);
                dirfile1.mkdirs();
                File file1 = new File(dirfile1, "AdvanceDirectives.pdf");
                if (file1.exists()) {
                    file1.delete();
                }
//Old code varsa
              /*  new Header().createPdfHeader(file1.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png", context);
                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Advance Directives");//preferences.getString(PrefConstants.CONNECTED_NAME));
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

                ArrayList<Document> AdList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "AD");
                new DocumentPdf(AdList);
                Header.document.close();*/

              //New code varsa
                Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
                pdflogo=preferences.addFile("pdflogo.png", context);
                calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
                profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);

                new HeaderNew().createPdfHeaders(file1.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"ADVANCE DIRECTIVES", calendarWite, profileWite);

                HeaderNew.addusereNameChank("ADVANCE DIRECTIVES");//preferences.getString(PrefConstants.CONNECTED_NAME));
                HeaderNew.addEmptyLine(1);
                Image pp = null;
                pp=preferences.addFile("dir_one.png", context);
                ArrayList<Document> AdList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "AD");
                new DocumentPdfNew(AdList,pp);
                HeaderNew.document.close();
                break;
            case "Other":
                final String RESULT3 = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile3 = new File(RESULT3);
                dirfile3.mkdirs();
                File file3 = new File(dirfile3, "OtherDocuments.pdf");
                if (file3.exists()) {
                    file3.delete();
                }
                //Old Code varsa
                /*

                new Header().createPdfHeader(file3.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png", context);
                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Other Documents");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

                Paragraph p2 = new Paragraph(" ");
                LineSeparator line2 = new LineSeparator();
                line2.setOffset(-4);
                line2.setLineColor(BaseColor.LIGHT_GRAY);
                p2.add(line2);
                try {
                    Header.document.add(p2);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Header.addEmptyLine(1);

                ArrayList<Document> OtherList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Other");
                new DocumentPdf(OtherList, 1);
                Header.document.close();*/

               // Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
                pdflogo=preferences.addFile("pdflogo.png", context);
                calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
                profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);

                new HeaderNew().createPdfHeaders(file3.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"OTHER DOCUMENTS", calendarWite, profileWite);

                HeaderNew.addusereNameChank("OTHER DOCUMENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
                HeaderNew.addEmptyLine(1);
                 pp = null;
                pp=preferences.addFile("dir_two.png", context);
                ArrayList<Document> OtherList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Other");
                new DocumentPdfNew(OtherList, 1,pp);
                HeaderNew.document.close();
                break;

            case "Record":
                final String RESULT2 = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile2 = new File(RESULT2);
                dirfile2.mkdirs();
                File file2 = new File(dirfile2, "MedicalRecords.pdf");
                if (file2.exists()) {
                    file2.delete();
                }

          /*     new Header().createPdfHeader(file2.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png", context);
                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Medical Records");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

                Paragraph p1 = new Paragraph(" ");
                LineSeparator line1 = new LineSeparator();
                line1.setOffset(-4);
                line1.setLineColor(BaseColor.LIGHT_GRAY);
                p1.add(line1);
                try {
                    Header.document.add(p1);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Header.addEmptyLine(1);

                ArrayList<Document> RecordList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Record");
                new DocumentPdf(RecordList, "Record");
                Header.document.close();*/
                // Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
                pdflogo=preferences.addFile("pdflogo.png", context);
                calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
                profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);

                new HeaderNew().createPdfHeaders(file2.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"MEDICAL RECORDS", calendarWite, profileWite);

                HeaderNew.addusereNameChank("MEDICAL RECORDS");//preferences.getString(PrefConstants.CONNECTED_NAME));
                HeaderNew.addEmptyLine(1);
                pp = null;
                pp=preferences.addFile("dir_three.png", context);
                ArrayList<Document> RecordList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), "Record");
                new DocumentPdfNew(RecordList, "Record",pp);
                HeaderNew.document.close();
                break;
        }
                //----------------------------------------
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
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

                switch (From) {
                    case "AD":
                        String path = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/AdvanceDirectives.pdf";
                        File f = new File(path);
                        preferences.emailAttachement(f, context, "Advance Directives");
                        break;
                    case "Other":
                        String path1 = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/OtherDocuments.pdf";
                        File f1 = new File(path1);
                        preferences.emailAttachement(f1, context, "Other Documents");
                        break;
                    case "Record":
                        String path3 = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/MedicalRecords.pdf";


                        File f3 = new File(path3);
                        preferences.emailAttachement(f3, context, "Medical Records");
                        break;

                }
                               dialog.dismiss();
            }

        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (From) {
                    case "AD":
                        String path = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/AdvanceDirectives.pdf";
                        StringBuffer result = new StringBuffer();
                        result.append(new MessageString().getAdvanceDocuments());

                        new PDFDocumentProcess(path,
                                context, result);

                        System.out.println("\n" + result + "\n");


                        break;
                    case "Other":
                        String path1 = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/OtherDocuments.pdf";

                        StringBuffer result1 = new StringBuffer();
                        result1.append(new MessageString().getOtherDocuments());
                        new PDFDocumentProcess(path1,
                                context, result1);

                        System.out.println("\n" + result1 + "\n");
                        break;
                    case "Record":
                        String path2 = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/MedicalRecords.pdf";
                        StringBuffer result2 = new StringBuffer();
                        result2.append(new MessageString().getRecordDocuments());


                        new PDFDocumentProcess(path2,
                                context, result2);

                        System.out.println("\n" + result2 + "\n");

                        break;

                }

                    dialog.dismiss();
            }


        });


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + files[i]);
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setDocuments();
    }
}
