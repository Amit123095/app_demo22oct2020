package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageState;

/**
 * Class: DocumentSdCardList
 * Screen: Document list from device storage
 * A class that manages to display document list from device storage
 * implements OnclickListener for onClick event on views
 */
public class DocumentSdCardList extends AppCompatActivity {
    private static final int RESULTCODE = 200;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 400;
    public File[] imagelist;
    Context context = this;
    ArrayAdapter adapter;
    int clickCounter = 0;
    ListView lvSd, lvDownload;
    ArrayList listItems = new ArrayList();
    String[] pdflist, pdflist1;
    File[] downloadList;
    ImageView imgBack;
    private File[] imagelist1;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_sd_card_list);

        checkRuntimePermission();
        lvSd = findViewById(R.id.lvSd);
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

        showFile();
    }

    /**
     * Function: Fetc documents from device storae
     */
    private void showFile() {
        Boolean isSDPresent = getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
        File Pdfs = null;
        if (isSDSupportedDevice && isSDPresent) {
            String path = getExternalStorageState();
            Pdfs = new File(path);
        } else {
            Pdfs = Environment.getExternalStorageDirectory();
        }

        imagelist = Pdfs.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                // return ((name.endsWith(".pdf")));
                return (name.endsWith(".pdf")||name.endsWith(".txt")||name.endsWith(".docx")||name.endsWith(".doc")||name.endsWith(".xlsx")||name.endsWith(".xls")||name.endsWith(".png")||name.endsWith(".PNG")||name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".ppt")||name.endsWith(".pptx"));
            }
        });



        File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        downloadList = download.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return (name.endsWith(".pdf")||name.endsWith(".txt")||name.endsWith(".docx")||name.endsWith(".doc")||name.endsWith(".xlsx")||name.endsWith(".xls")||name.endsWith(".png")||name.endsWith(".PNG")||name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".ppt")||name.endsWith(".pptx"));
            }
        });

        int size = imagelist.length + downloadList.length;
        int s = imagelist.length;
        imagelist1 = new File[size];
        for (int i = 0; i < imagelist.length; i++) {
            imagelist1[i] = imagelist[i];
        }
        for (int i = 0; i < downloadList.length; i++) {
            imagelist1[s] = downloadList[i];
            s++;
        }
        if (imagelist1 != null) {
            pdflist = new String[imagelist1.length];
            for (int i = 0; i < imagelist1.length; i++) {
                pdflist[i] = imagelist1[i].getName();
            }
            PdfAdapter adapter = new PdfAdapter(context, pdflist, imagelist1);
            lvSd.setAdapter(adapter);
        } else {
            Toast.makeText(context, "No Files Available in Download", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Function: Check for runtime permission
     * If granted go further else request for permission to be granted
     * Return result in onRequestPermissionsResult method
     */
    private boolean checkRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Function: Fetch file name and path
     * @param name
     * @param uri
     */
    public void getData(String name, String uri) {

        Intent i = new Intent();
        i.putExtra("Name", name);
        i.putExtra("URI", uri);

        setResult(RESULTCODE, i);
    }
}

