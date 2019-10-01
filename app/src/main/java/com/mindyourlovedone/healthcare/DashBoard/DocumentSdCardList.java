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

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

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
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showFile();
    }

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
        /*if (downloadList!=null) {
            pdflist1 = new String[downloadList.length];
            for (int i = 0; i < downloadList.length; i++) {
                pdflist1[i] = downloadList[i].getName();
            }
            PdfAdapter adapter=new PdfAdapter(context,pdflist1,downloadList);
            lvDownload.setAdapter(adapter);
        }
        else{
            Toast.makeText(context,"No Files Available in Download",Toast.LENGTH_SHORT).show();
        }*/

    }

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
       /* if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(DocumentSdCardList.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(DocumentSdCardList.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(DocumentSdCardList.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);

            }
            return false;
        } else {
            return true;
        }*/
    }

    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0 && imagelist[(int) id].isFile()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(imagelist[(int) id].getAbsoluteFile());
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        }*/

    // }

    public void getData(String name, String uri) {

        Intent i = new Intent();
        i.putExtra("Name", name);
        i.putExtra("URI", uri);

        setResult(RESULTCODE, i);
    }
}

