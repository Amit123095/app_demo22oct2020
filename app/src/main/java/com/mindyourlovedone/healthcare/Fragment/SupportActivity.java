package com.mindyourlovedone.healthcare.Fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.ResourceAdapter;
import com.mindyourlovedone.healthcare.model.ResourcesNew;
import com.mindyourlovedone.healthcare.utility.WebPDFActivity;
import com.mindyourlovedone.healthcare.webservice.WebService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SupportActivity extends AppCompatActivity {

    Context context = this;
    ArrayList<ResourcesNew> supportList, enduserList;
    ListView lvSupport, lvEndUser;
    TextView txtTitle, txtName;
    ImageView imgDrawer, imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initUI();
        initListener();
        getData();
        setData();

    }

    private void setData() {
        ResourceAdapter ud = new ResourceAdapter(context, supportList);
        lvSupport.setAdapter(ud);

        ResourceAdapter ed = new ResourceAdapter(context, enduserList);
        lvEndUser.setAdapter(ed);
    }

    private void getData() {

        supportList = new ArrayList<ResourcesNew>();
        enduserList = new ArrayList<ResourcesNew>();

        ResourcesNew u1 = new ResourcesNew();
        u1.setName("Support FAQs");
        u1.setResImage(R.drawable.faq);

        ResourcesNew u2 = new ResourcesNew();
        u2.setName("User Guide");
        u2.setResImage(R.drawable.useruide);

        supportList.add(u1);
        supportList.add(u2);


        ResourcesNew e2 = new ResourcesNew();
        e2.setName("End User License Agreement");
        e2.setResImage(R.drawable.enduser);

        ResourcesNew e1 = new ResourcesNew();
        e1.setName("Privacy Policy");
        e1.setResImage(R.drawable.enduser);

        enduserList.add(e2);
        enduserList.add(e1);
    }

    private void initListener() {

    }

    private void initUI() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtTitle = findViewById(R.id.txtTitle);
        lvSupport = findViewById(R.id.lvSupport);
        lvEndUser = findViewById(R.id.lvEndUser);
        Intent i = getIntent();
        String from = i.getExtras().getString("FROM");
        if (from.equals("Support")) {
            lvSupport.setVisibility(View.VISIBLE);
            lvEndUser.setVisibility(View.GONE);
            txtTitle.setText("Support FAQs and User Guide");

        } else if (from.equals("EndUser")) {
            lvSupport.setVisibility(View.GONE);
            lvEndUser.setVisibility(View.VISIBLE);
            txtTitle.setText("End User License Agreement \nand Privacy Policy");
        }
        lvSupport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://Support Faq
                        Intent browserIntent = new Intent(context, WebPDFActivity.class);
                        browserIntent.putExtra("Name", "Support FAQs");
                        browserIntent.putExtra("URL", WebService.FAQ_URL);
                        startActivity(browserIntent);

                        break;
                    case 1://User Guide-Section
                          Intent browserIntents = new Intent(context,WebPDFActivity.class);
                        browserIntents.putExtra("Name","User Guide");
                        browserIntents.putExtra("URL", WebService.USERGUIDE_URL);
                         startActivity(browserIntents);
                       // Intent browserIntentD = new Intent(Intent.ACTION_VIEW, Uri.parse("http://docs.google.com/gview?embedded=true&url=http://mindyour-lovedones.com/MYLO/uploads/User_Guide.pdf"));
                       // startActivity(browserIntentD);

                        break;
                }
            }
        });
        lvEndUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://End User License Agreement-Section
//                        Intent intentx = new Intent();
//                        intentx.setAction(Intent.ACTION_VIEW);
//                        intentx.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intentx.setData(Uri.parse("market://details?id=cn.wps.moffice_eng"));//varsa ("market://details?id=com.adobe.reader"));
//                        intentx.setType(String.valueOf(Uri.parse("application/pdf")));
//                        CopyReadAssetss("eula_new.pdf");
                        Intent browserIntentD = new Intent(SupportActivity.this, WebPDFActivity.class);
                        browserIntentD.putExtra("Name", "End User License Agreement");
                        browserIntentD.putExtra("URL", WebService.EULA_URL);
                        startActivity(browserIntentD);
                        break;
                    case 1://Privacy Policy-Section
                        Intent browserIntentD2 = new Intent(SupportActivity.this, WebPDFActivity.class);
                        browserIntentD2.putExtra("Name", "Privacy Policy");
                        browserIntentD2.putExtra("URL", WebService.PRIVACY_URL);
                        startActivity(browserIntentD2);

//                        Intent intentp = new Intent();
//                        intentp.setAction(Intent.ACTION_VIEW);
//                        intentp.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intentp.setData(Uri.parse("market://details?id=cn.wps.moffice_eng"));//varsa ("market://details?id=com.adobe.reader"));
//                        intentp.setType(String.valueOf(Uri.parse("application/pdf")));
//                       CopyReadAssetss("Privacy Policy.pdf");
                        break;
                }
            }
        });

    }

    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try {
            in = assetManager.open(documentPath);
            outFile = new File(getExternalFilesDir(null), documentPath);
            out = new FileOutputStream(outFile);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            /*out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;*/
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri uri = null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        try {
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            // No application to view, ask to download one

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                            context.startActivity(marketIntent);
                        }
                    });
            builder.setNegativeButton("No", null);
            builder.create().show();
        }


    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
