package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.LinkAdapter;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.Links;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Class: ChecklistActivity
 * Screen: Advance Directive Section
 * A Common class that manages to List of Advance Directive, Other documents, Medical record
 * implements OnclickListener for onClick event on views
 */
public class ChecklistActivity extends AppCompatActivity {
    Context context = this;
    ArrayList<Links> UrlList;
    ListView list;
    TextView txtTitle;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        //Initialize user interface view and components
        initUI();
        // get data
        getData();
        //Set data
        setData();
    }

    /**
     * Function: Set list data
     */
    private void setData() {
        LinkAdapter adapter = new LinkAdapter(context, UrlList);
        list.setAdapter(adapter);
    }

    /**
     * Function: Fetch all records
     */
    private void getData() {
        UrlList = new ArrayList<>();
        Links l4 = new Links();
        l4.setName("Medical History Form");
        l4.setUrl("medical_history_form_new.pdf");
        l4.setImage(R.drawable.pdf);
        UrlList.add(l4);
    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Helpful forms and\ntemplates");
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CopyReadAssetss(UrlList.get(position).getUrl());
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Email ?");
                alert.setMessage("Do you want to email pdf file ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Links link = UrlList.get(position);
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                new String[]{""});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                link.getName()); // subject
                        String body = "Hi, \n" +
                                "\n" +
                                " Please check the attachment. \n" +
                                "\n" +
                                "Thanks";
                        //"Mind Your Loved Ones - Support";
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

                        AssetManager assetManager = context.getAssets();
                        File outFile = null;
                        InputStream in = null;
                        OutputStream out = null;
                        File file = new File(context.getFilesDir(), link.getUrl());
                        try {
                            in = assetManager.open(link.getUrl());
                            outFile = new File(context.getExternalFilesDir(null), link.getUrl());
                            out = new FileOutputStream(outFile);

                            copyFiles(in, out);
                            in.close();
                            in = null;
                            out.flush();
                            out.close();
                            out = null;
                        } catch (Exception e) {
                            Log.e("tag", e.getMessage());
                        }
                        Uri uri = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", outFile);
                        } else {
                            uri = Uri.fromFile(outFile);
                        }
                        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        emailIntent.setType("application/email");
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
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

                return true;
            }
        });
    }

    /**
     * Function: Display Document
     * @param documentPath
     */
    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = context.getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(context.getFilesDir(), documentPath);
        try {
            in = assetManager.open(documentPath);
            outFile = new File(context.getExternalFilesDir(null), documentPath);
            out = new FileOutputStream(outFile);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri uri = null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }


    }
}
