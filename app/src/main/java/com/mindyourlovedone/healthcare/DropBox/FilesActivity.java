package com.mindyourlovedone.healthcare.DropBox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.FileAction;
import com.dropbox.core.v2.sharing.SharedFileMetadata;
import com.mindyourlovedone.healthcare.DashBoard.AddDocumentActivity;
import com.mindyourlovedone.healthcare.DashBoard.UserInsActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static android.graphics.Color.TRANSPARENT;

public class FilesActivity extends DropboxActivity implements ZipListner {
    private static final String APP_KEY = "428h5i4dsj95eeh";
    public final static String EXTRA_PATH = "FilesActivity_Path";
    private static final String TAG = FilesActivity.class.getName();
    private static final int PICKFILE_REQUEST_CODE = 1;
    Preferences preferences;
    RelativeLayout rlBackup, rlHeader, rlParent;
    private String mPath;
    //    private FilesAdapter mFilesAdapter;
    private SharedFilesAdapter mSharedFilesAdapter;
    private DropBoxFileItem sSelectedFile;
    ImageView imgBack, imgBack2, imgRight;
    RecyclerView recyclerView, srecyclerView;
    ArrayList<DropBoxFileItem> resultListBackup = new ArrayList<>();
    String dirName="";

    public static Intent getIntent(Context context, String path) {
        Intent filesIntent = new Intent(context, FilesActivity.class);
        filesIntent.putExtra(FilesActivity.EXTRA_PATH, path);
        return filesIntent;
    }

    private static void zipFolder(String inputFolderPath, String outZipPath) {
        try {
            FileOutputStream fos = new FileOutputStream(outZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File srcFile = new File(inputFolderPath);
            File[] files = srcFile.listFiles();
            Log.d("", "Zip directory: " + srcFile.getName());
            for (int i = 0; i < files.length; i++) {
                Log.d("", "Adding file: " + files[i].getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (IOException ioe) {
            Log.e("", ioe.getMessage());
        }
    }

    public void setNameFile(String dirName) {
        this.dirName=dirName;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new Preferences(FilesActivity.this);
        String path = getIntent().getStringExtra(EXTRA_PATH);
        mPath = path == null ? "" : path;

        setContentView(R.layout.activity_files);
        rlBackup = findViewById(R.id.rlBackup);
        rlParent = findViewById(R.id.rlParent);
        rlHeader = findViewById(R.id.rlHeader);

        //nikita - 7-10-19
        imgRight = findViewById(R.id.imgRight);
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUserIns = new Intent(FilesActivity.this, UserInsActivity.class);
                intentUserIns.putExtra("From", "Restore");
                startActivity(intentUserIns);
            }
        });

        imgBack = findViewById(R.id.imgBack);
        imgBack2 = findViewById(R.id.imgBack2);
        recyclerView = findViewById(R.id.files_list);
        srecyclerView = findViewById(R.id.sharefiles_list);

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.FINIS, "true");
                finish();
            }
        });

        imgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.FINIS, "true");
                finish();
            }
        });
        preferences.putString(PrefConstants.ACCESS, "Default");
        String from = preferences.getString(PrefConstants.STORE);
        if (from.equals("Document")) {
            rlBackup.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            srecyclerView.setVisibility(View.VISIBLE);
            rlHeader.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.GONE);
        } else if (from.equals("Backup")) {
            rlBackup.setVisibility(View.GONE);
            imgBack.setVisibility(View.GONE);
            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            if (prefs.contains("access-token")) {
                rlParent.setBackgroundColor(TRANSPARENT);
                rlBackup.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                srecyclerView.setVisibility(View.GONE);
                imgBack.setVisibility(View.GONE);
                rlHeader.setVisibility(View.GONE);
                performWithPermissions(FilesActivity.FileAction.UPLOAD);
            } else {
                preferences.putString(PrefConstants.ACCESS, "Backup");
                Auth.startOAuth2Authentication(FilesActivity.this, APP_KEY);
            }


        } else if (from.equals("Share")) {
            rlBackup.setVisibility(View.GONE);
            imgBack.setVisibility(View.GONE);
            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            if (prefs.contains("access-token")) {
                rlParent.setBackgroundColor(TRANSPARENT);
                rlBackup.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                srecyclerView.setVisibility(View.GONE);
                imgBack.setVisibility(View.GONE);
                rlHeader.setVisibility(View.GONE);

                performWithPermissions(FilesActivity.FileAction.UPLOAD);
            } else {
                preferences.putString(PrefConstants.ACCESS, "Share");
                Auth.startOAuth2Authentication(FilesActivity.this, APP_KEY);
            }
        } else if (from.equals("Restore")) {
            // preferences.putString(PrefConstants.ACCESS,"Restore");
            rlBackup.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            srecyclerView.setVisibility(View.VISIBLE);
            rlHeader.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.GONE);
            imgRight.setVisibility(View.VISIBLE);
            // loadData();
/*
            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
              if (prefs.contains("access-token")) {
                  rlBackup.setVisibility(View.GONE);
                  recyclerView.setVisibility(View.VISIBLE);
                  srecyclerView.setVisibility(View.VISIBLE);
                  rlHeader.setVisibility(View.VISIBLE);
                  imgBack.setVisibility(View.GONE);
                  loadDropboxData();
              }
              else{
                  preferences.putString(PrefConstants.ACCESS,"Restore");
                  Auth.startOAuth2Authentication(FilesActivity.this, APP_KEY);
              }*/
        }
        rlBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    performWithPermissions(FilesActivity.FileAction.UPLOAD);
                } else {
                    preferences.putString(PrefConstants.ACCESS, "Backup");
                    Auth.startOAuth2Authentication(FilesActivity.this, APP_KEY);
                }
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
//        mSelectedFile = null;
//
//        mFilesAdapter = new FilesAdapter(PicassoClient.getPicasso(), new FilesAdapter.Callback() {
//            @Override
//            public void onFolderClicked(FolderMetadata folder) {
//                startActivity(FilesActivity.getIntent(FilesActivity.this, folder.getPathLower()));
//            }
//
//            @Override
//            public void onFileClicked(final FileMetadata file) {
//                mSelectedFile = file;
//                performWithPermissions(FilesActivity.FileAction.DOWNLOAD);
//            }
//        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(mFilesAdapter);


        mSharedFilesAdapter = new SharedFilesAdapter(FilesActivity.this, PicassoClient.getPicasso(), new SharedFilesAdapter.Callback() {
            @Override
            public void onFolderClicked(DropBoxFileItem folder) {
                if (folder.getShared() == 1) {
                    startActivity(FilesActivity.getIntent(FilesActivity.this, folder.getSharefmd().getPathLower()));
                } else {
                    startActivity(FilesActivity.getIntent(FilesActivity.this, folder.getFilemd().getPathLower()));
                }
            }

            @Override
            public void onFolderClicked(FolderMetadata folder) {
                startActivity(FilesActivity.getIntent(FilesActivity.this, folder.getPathLower()));
            }


            @Override
            public void onFileClicked(DropBoxFileItem file) {
                sSelectedFile = file;
                performWithPermissions(FilesActivity.FileAction.DOWNLOAD);
            }


        });
        srecyclerView.setLayoutManager(new LinearLayoutManager(this));
        srecyclerView.setAdapter(mSharedFilesAdapter);
        sSelectedFile = null;
    }

    public void launchFilePicker() {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            File folder = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                folder.delete();
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + preferences.getString(PrefConstants.ZIPFILE) + ".zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                destfolder.delete();
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            new ZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder.getAbsolutePath()).execute();
            /*File folder = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                folder.delete();
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File folder2 = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+preferences.getString(PrefConstants.ZIPFILE)+"/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
            if (!folder2.exists()) {
                try {
                    folder2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                folder2.delete();
                try {
                    folder2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileUtils.copyDirectory(folder,folder2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folder3 = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+preferences.getString(PrefConstants.ZIPFILE)+"/" );


            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+preferences.getString(PrefConstants.ZIPFILE)+ ".zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                destfolder.delete();
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            new ZipTask(FilesActivity.this, folder3.getAbsolutePath(), destfolder.getAbsolutePath()).execute();
       */ } else {

            File folder = new File(Environment.getExternalStorageDirectory() + "/MYLO/");
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                folder.delete();
//                try {
//                    folder.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            File destfolder = new File(Environment.getExternalStorageDirectory(),"/"+ preferences.getString(PrefConstants.ZIPFILE) + ".zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                destfolder.delete();
//                try {
//                    destfolder.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
            new ZipTask(FilesActivity.this, folder.getPath(), destfolder.getPath()).execute();
           /* File folder = new File(Environment.getExternalStorageDirectory() + "/MYLO/");
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                folder.delete();
//                try {
//                    folder.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            File folder2 = new File(Environment.getExternalStorageDirectory(), preferences.getString(PrefConstants.ZIPFILE)+"/"+"MYLO/");
            if (!folder2.exists()) {
                try {
                    folder2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                folder2.delete();
                try {
                    folder2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileUtils.copyDirectory(folder,folder2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folder3 = new File(Environment.getExternalStorageDirectory(), preferences.getString(PrefConstants.ZIPFILE)+"/" );

            File destfolder = new File(Environment.getExternalStorageDirectory(),preferences.getString(PrefConstants.ZIPFILE)+ ".zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                destfolder.delete();
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//
            }
            new ZipTask(FilesActivity.this, folder3.getPath(), destfolder.getPath()).execute();
*/
        }
    }

    private void copy(File backupDB, File currentDB) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // This is the result of a call to launchFilePicker
                uploadFile(data.getData().toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int actionCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        FileAction action = FileAction.fromCode(actionCode);

        boolean granted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                Log.w(TAG, "User denied " + permissions[i] +
                        " permission to perform file action: " + action);
                granted = false;
                break;
            }
        }

        if (granted) {
            performAction(action);
        } else {
            switch (action) {
                case UPLOAD:
                    Toast.makeText(this,
                            "Can't upload file: read access denied. " +
                                    "Please grant storage permissions to use this functionality.",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case DOWNLOAD:
                    Toast.makeText(this,
                            "Can't download file: write access denied. " +
                                    "Please grant storage permissions to use this functionality.",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
            }
        }
    }

    private void performAction(FileAction action) {
        switch (action) {
            case UPLOAD:
                launchFilePicker();
                break;
            case DOWNLOAD:
                if (sSelectedFile != null) {
                    if (sSelectedFile.getShared() == 1) {
                        downloadFiles(sSelectedFile.getSharefmd());
                    } else {
                        downloadFile((FileMetadata) sSelectedFile.getFilemd());
                    }
                } else {
                    Log.e(TAG, "No file selected to download.");
                }
                break;
            default:
                Log.e(TAG, "Can't perform unhandled file action: " + action);
        }
    }

    @Override
    protected void loadData() {

        if (preferences.getString(PrefConstants.ACCESS).equals("Share")) {
            preferences.putString(PrefConstants.ACCESS, "Default");
            performWithPermissions(FilesActivity.FileAction.UPLOAD);
        }else if (preferences.getString(PrefConstants.ACCESS).equals("Backup")) {
            preferences.putString(PrefConstants.ACCESS, "Default");
            performWithPermissions(FilesActivity.FileAction.UPLOAD);
        }else {/*else if (preferences.getString(PrefConstants.ACCESS).equals("Restore"))
        {
            rlBackup.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            srecyclerView.setVisibility(View.VISIBLE);
            rlHeader.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.GONE);
            loadDropboxData();
        }*//*else if(preferences.getString(PrefConstants.ACCESS).equals("Default"))
        {
            FilesActivity.this.finish();
        }*/

            // Code for inbox files
/*
        new ListFolderTask(DropboxClientFactory.getClient(), new ListFolderTask.Callback() {
            @Override
            public void onDataLoaded(ListFolderResult result) {
                dialog.dismiss();
                final ArrayList<Metadata> resultList = new ArrayList<Metadata>();
                for (int i = 0; i < result.getEntries().size(); i++) {
                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                        String name = result.getEntries().get(i).getName();
                        if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
                            // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                            resultList.add(result.getEntries().get(i));
                        }
                    } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                        if (result.getEntries().get(i).getName().endsWith(".zip")) {
                            if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
                                if (result.getEntries().get(i).getName().equals("MYLO.zip")) {
                                } else {
                                    resultList.add(result.getEntries().get(i));
                                }
                            } else {
                                if (result.getEntries().get(i).getName().equals("MYLO.zip")) {
                                    resultList.add(result.getEntries().get(i));
                                }
                            }
                            // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                        }
                    }
                }  if (resultList.size()!=0) {
                mFilesAdapter.setFiles(resultList);
                }else{
                    Toast.makeText(FilesActivity.this,"No Document or Backup File available in your dropbox",Toast.LENGTH_SHORT).show();
                    FilesActivity.this.finish();
                }
            }
            @Override
            public void onError(Exception e) {
                dialog.dismiss();
                Log.e(TAG, "Failed to list folder.", e);
                Toast.makeText(FilesActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(mPath);*/

// Code for Received files
            if (resultListBackup.size() == 0) {
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setCancelable(false);
                dialog.setMessage("Loading \nPlease be patient");
                dialog.show();
                new ListReceivedFolderTask(FilesActivity.this,DropboxClientFactory.getClient(), new ListReceivedFolderTask.Callback() {//Nikita - new changes for merged data
                    @Override
                    public void onDataLoaded(ArrayList<DropBoxFileItem> result) {
                        dialog.dismiss();
                        ArrayList<DropBoxFileItem> resultList = new ArrayList<>();
                        resultListBackup=resultList;
                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i).getShared() == 1) {
                                SharedFileMetadata ss = result.get(i).getSharefmd();
                                if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                                    resultList.add(result.get(i));
                                   /* String name = ss.getName();
                                    if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
                                        // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                                        resultList.add(result.get(i));
                                    }*/
                                } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                                    if (ss.getName().endsWith(".zip")) {
                                        if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
                                            if (ss.getName().equals("MYLO.zip")) {

                                            } else {
                                                String name=ss.getName();//whole_MYLO.zip
                                                int l=name.length();
                                                if (l>=10){
                                                    name = name.substring((l - 9),l);
                                                    if (name.equalsIgnoreCase("_MYLO.zip")) {

                                                    }else{
                                                        resultList.add(result.get(i));
                                                    }
                                                }
                                            }
                                        } else {
                                            if (ss.getName().equals("MYLO.zip")) {
                                                resultList.add(result.get(i));
                                            } else {
                                                String name=ss.getName();//whole_MYLO.zip
                                                int l=name.length();
                                                if (l>=10){
                                                    name = name.substring((l - 9),l);
                                                    if (name.equalsIgnoreCase("_MYLO.zip")) {
                                                        resultList.add(result.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {

                                    }
                                }
                            } else {
                                Metadata ss = result.get(i).getFilemd();
                                if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                                    resultList.add(result.get(i));
                                    /*String name = ss.getName();
                                    if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
                                        // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                                        resultList.add(result.get(i));
                                    }*/
                                } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                                    if (ss.getName().endsWith(".zip")) {
                                        if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
                                            if (ss.getName().equals("MYLO.zip")) {
                                                //resultList.add(result.get(i));
                                            } else {
                                                String name=ss.getName();//whole_MYLO.zip
                                                int l=name.length();
                                                if (l>=10){
                                                    name = name.substring((l - 9),l);
                                                    if (name.equalsIgnoreCase("_MYLO.zip")) {

                                                    }else{
                                                        resultList.add(result.get(i));
                                                    }
                                                }
                                            }
                                        } else {
                                            if (ss.getName().equals("MYLO.zip")) {
                                                resultList.add(result.get(i));
                                            } else {
                                                String name=ss.getName();//whole_MYLO.zip
                                                int l=name.length();
                                                if (l>=10){
                                                    name = name.substring((l - 9),l);
                                                    if (name.equalsIgnoreCase("_MYLO.zip")) {
                                                        resultList.add(result.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {

                                    }
                                }
                            }
                        }
                        if (resultList.size() != 0) {
                            mSharedFilesAdapter.setSharedFiles(resultList);
                        } else {
                            //   Toast.makeText(FilesActivity.this,"Backup file is not present in your account for restore",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        dialog.dismiss();

                        Log.e(TAG, "Failed to list folder.", e);
                        Toast.makeText(FilesActivity.this,
                                "An error has occurred",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }).execute(mPath);

            }
        }
    }

    private void downloadFile(FileMetadata file) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Downloading");
        dialog.show();

        new DownloadFileTask(FilesActivity.this, DropboxClientFactory.getClient(), new DownloadFileTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {
                dialog.dismiss();

                if (result != null) {
                    //viewFileInExternalApp(result);


                    AddDocumentActivity a = new AddDocumentActivity();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = result.getName().substring(result.getName().indexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);

                   /*25 Oct Commented for pdf back/restore
                    Uri contentUri = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        contentUri = FileProvider.getUriForFile(FilesActivity.this, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", result);
                    } else {
                        contentUri = Uri.fromFile(result);
                    }
                    intent.setDataAndType(contentUri, type);*/

                    preferences = new Preferences(FilesActivity.this);
                    preferences.putString(PrefConstants.URI, result.getAbsolutePath());
                    preferences.putString(PrefConstants.RESULT, result.getName());

                    Intent i = new Intent();
                    i.putExtra("URI", result.getAbsolutePath());
                    i.putExtra("Name", result.getName());

                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                        setResult(45, i);
                        finish();
                    } else {

                        final AlertDialog.Builder alert = new AlertDialog.Builder(FilesActivity.this);
                        alert.setTitle("Restore?");
                        alert.setMessage("Do you want to unzip and  restore " + preferences.getString(PrefConstants.RESULT) + " database?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                String name = preferences.getString(PrefConstants.RESULT);
                                Log.v("NAME", name);
                                if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                                    String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                                    //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                                    String backupDBPath = "/Download/" + name;
                                    String newname = name.replace(".zip", "");
                                    final File folder = new File(sd, backupDBPath);
                                    final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                            "/MYLO/" + newname);
                                    final File destfolder1 = new File(Environment.getExternalStorageDirectory(),
                                            "/MYLO/");//nikita
                                    if (!destfolder.exists()) {
                                        destfolder.mkdir();
                                        FileInputStream fis = null;
                                        ZipInputStream zipIs = null;
                                        ZipEntry zEntry = null;
                                        try {
                                            fis = new FileInputStream(folder);
                                            zipIs = new ZipInputStream(new BufferedInputStream(fis));
                                            // Log.v("FILESF",zipIs.getNextEntry().getName());
                                            String fnamelist[]=zipIs.getNextEntry().getName().split("/");
                                            String fname=fnamelist[0];
                                            if (fname.equals(""))
                                            {
                                                fname=fnamelist[1];
                                            }

                                            File[] files= destfolder1.listFiles();
                                            boolean flag=false;
                                            boolean nofull=false;
                                            for (int i=0;i<files.length;i++)
                                            {
                                                if (files[i].getName().equals(fname)) {
                                                    flag = true;
                                                    Log.v("FILESF", files[i].getName() + "-" + fname + "-" + "Profile exists");
                                                    Toast.makeText(FilesActivity.this, "Profile exists", Toast.LENGTH_SHORT);
                                                    break;
                                                }

                                            }

                                            for (int i=0;i<fnamelist.length;i++)
                                            {
                                                if (fnamelist[i].equalsIgnoreCase("MYLO"))
                                                {
                                                    nofull=true;
                                                    break;
                                                }
                                            }
                                            /*while((zEntry = zipIs.getNextEntry()) != null){
                                                Log.v("FILESF",zEntry.getName());
                                            }*/
                                            zipIs.close();
                                            if (nofull!=true) {
                                                if (flag == true) {
                                                    AlertDialog.Builder alerts = new AlertDialog.Builder(FilesActivity.this);
                                                    alerts.setTitle("Replace?");
                                                    alerts.setMessage("Profile is already exists, Do you want to replace?");
                                                    alerts.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        /*destfolder.delete();//nikita
                                                        try {
                                                            destfolder.createNewFile();//nikita
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }*/
                                                            new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath(), FilesActivity.this).execute();//nikita
                                                            // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    alerts.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    alerts.show();
                                                } else {
                                                    new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath(), FilesActivity.this).execute();//nikita
                                                }
                                            }else{
                                                AlertDialog.Builder alertf = new AlertDialog.Builder(FilesActivity.this);
                                                alertf.setTitle("Invalid");
                                                alertf.setMessage("Invalid Profile Backup");
                                                alertf.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        try {
                                                            FileUtils.deleteDirectory(destfolder);
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        dialog.dismiss();
                                                    }
                                                });

                                                alertf.show();
                                            }
                                        } catch (FileNotFoundException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
                                    } else {
                                        Log.v("FILESF",newname+"-"+"Profile exists");
                                        AlertDialog.Builder alerts = new AlertDialog.Builder(FilesActivity.this);
                                        alerts.setTitle("Replace?");
                                        alerts.setMessage("Profile is already exists, Do you want to replace?");
                                        alerts.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                        /*destfolder.delete();//nikita
                                                        try {
                                                            destfolder.createNewFile();//nikita
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }*/
                                                new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath(),FilesActivity.this).execute();//nikita
                                                // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
                                                dialog.dismiss();
                                            }
                                        });

                                        alerts.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        });

                                        alerts.show();

                                    }

                                } else {

                                    String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                                    //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                                    String backupDBPath = "/Download/" + name;
                                    String newname = name.replace(".zip", "");
                                    final File folder = new File(sd, backupDBPath);
                                    final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                            newname);
                                    final File destfolders = new File(Environment.getExternalStorageDirectory(),
                                            "MYLO");

                                    FileInputStream fis = null;
                                    ZipInputStream zipIs = null;
                                    try {
                                        fis = new FileInputStream(folder);
                                        zipIs = new ZipInputStream(new BufferedInputStream(fis));
                                        // Log.v("FILESF",zipIs.getNextEntry().getName());
                                        String fnamelist[] = zipIs.getNextEntry().getName().split("/");

                                        boolean nofull = false;

                                        for (int i = 0; i < fnamelist.length; i++) {
                                            if (fnamelist[i].equalsIgnoreCase("MYLO")) {
                                                nofull = true;
                                                break;
                                            }
                                        }
                                        zipIs.close();
                                        if (nofull==true){
                                            if (destfolders.exists()) {
                                                try {
                                                    FileUtils.deleteDirectory(destfolders);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath(),FilesActivity.this).execute();//nikita

                                        }else{
                                            AlertDialog.Builder alertf = new AlertDialog.Builder(FilesActivity.this);
                                            alertf.setTitle("Invalid");
                                            alertf.setMessage("Invalid MYLO Whole Backup");
                                            alertf.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            alertf.show();
                                        }
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath());



                                }

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
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to download file.", e);
                Toast.makeText(FilesActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(file);

    }

    private void downloadFiles(SharedFileMetadata file) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Downloading");
        dialog.show();

        new DownloadReceivedFileTask(FilesActivity.this, DropboxClientFactory.getClient(), new DownloadReceivedFileTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {
                dialog.dismiss();

                if (result != null) {
                    //viewFileInExternalApp(result);


                    AddDocumentActivity a = new AddDocumentActivity();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = result.getName().substring(result.getName().indexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);

                   /*25 Oct Commented for pdf back/restore
                    Uri contentUri = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        contentUri = FileProvider.getUriForFile(FilesActivity.this, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", result);
                    } else {
                        contentUri = Uri.fromFile(result);
                    }
                    intent.setDataAndType(contentUri, type);*/

                    preferences = new Preferences(FilesActivity.this);
                    preferences.putString(PrefConstants.URI, result.getAbsolutePath());
                    preferences.putString(PrefConstants.RESULT, result.getName());


                    Intent i = new Intent();
                    i.putExtra("URI", result.getAbsolutePath());
                    i.putExtra("Name", result.getName());
                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                        setResult(45, i);
                        finish();
                    } else {

                        final AlertDialog.Builder alert = new AlertDialog.Builder(FilesActivity.this);
                        alert.setTitle("Restore?");
                        alert.setMessage("Do you want to unzip and  restore " + preferences.getString(PrefConstants.RESULT) + " database?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                String name = preferences.getString(PrefConstants.RESULT);
                                Log.v("NAME", name);
                                if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                                    String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                                    //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                                    String backupDBPath = "/Download/" + name;
                                    String newname = name.replace(".zip", "");
                                    final File folder = new File(sd, backupDBPath);
                                    final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                            "/MYLO/" + newname);
                                    final File destfolder1 = new File(Environment.getExternalStorageDirectory(),
                                            "/MYLO/");//nikita

                                    if (!destfolder.exists()) {
                                        destfolder.mkdir();
                                        FileInputStream fis = null;
                                        ZipInputStream zipIs = null;
                                        ZipEntry zEntry = null;
                                        try {
                                            fis = new FileInputStream(folder);
                                            zipIs = new ZipInputStream(new BufferedInputStream(fis));
                                            // Log.v("FILESF",zipIs.getNextEntry().getName());
                                            String fnamelist[]=zipIs.getNextEntry().getName().split("/");
                                            String fname=fnamelist[0];

                                            File[] files= destfolder1.listFiles();
                                            boolean flag=false;
                                            for (int i=0;i<files.length;i++)
                                            {
                                                if (files[i].getName().equals(fname))
                                                {
                                                    flag=true;
                                                    Log.v("FILESF",files[i].getName()+"-"+fname+"-"+"Profile exists");
                                                    Toast.makeText(FilesActivity.this,"Profile exists",Toast.LENGTH_SHORT);
                                                    break;
                                                }

                                            }
                                            /*while((zEntry = zipIs.getNextEntry()) != null){
                                                Log.v("FILESF",zEntry.getName());
                                            }*/
                                            boolean nofull=false;
                                            for (int i=0;i<fnamelist.length;i++)
                                            {
                                                if (fnamelist[i].equalsIgnoreCase("MYLO"))
                                                {
                                                    nofull=true;
                                                    break;
                                                }
                                            }
                                            zipIs.close();
                                            if (nofull!=true) {
                                                if (flag==true)
                                                {
                                                    AlertDialog.Builder alerts = new AlertDialog.Builder(FilesActivity.this);
                                                    alerts.setTitle("Replace?");
                                                    alerts.setMessage("Profile is already exists, Do you want to replace?");
                                                    alerts.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        /*destfolder.delete();//nikita
                                                        try {
                                                            destfolder.createNewFile();//nikita
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }*/
                                                            new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath(),FilesActivity.this).execute();//nikita
                                                            // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    alerts.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    alerts.show();
                                                } else{
                                                    new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath(),FilesActivity.this).execute();//nikita
                                                }
                                            }else{
                                                AlertDialog.Builder alertf = new AlertDialog.Builder(FilesActivity.this);
                                                alertf.setTitle("Invalid");
                                                alertf.setMessage("Invalid Profile Backup");
                                                alertf.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        try {
                                                            FileUtils.deleteDirectory(destfolder);
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        dialog.dismiss();
                                                    }
                                                });

                                                alertf.show();
                                            }
                                        } catch (FileNotFoundException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
                                    } else {

                                        AlertDialog.Builder alerts = new AlertDialog.Builder(FilesActivity.this);
                                        alerts.setTitle("Replace?");
                                        alerts.setMessage("Profile is already exists, Do you want to replace?");
                                        alerts.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                        /*destfolder.delete();//nikita
                                                        try {
                                                            destfolder.createNewFile();//nikita
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }*/
                                                new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath(),FilesActivity.this).execute();//nikita
                                                // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
                                                dialog.dismiss();
                                            }
                                        });

                                        alerts.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        });

                                        alerts.show();
                                    }
                                } else {

                                    String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                                    //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                                    String backupDBPath = "/Download/" + name;
                                    String newname = name.replace(".zip", "");
                                    final File folder = new File(sd, backupDBPath);
                                    final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                            newname);
                                    // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath());
                                    final File destfolders = new File(Environment.getExternalStorageDirectory(),
                                            "MYLO");
                                    FileInputStream fis = null;
                                    ZipInputStream zipIs = null;
                                    try {
                                        fis = new FileInputStream(folder);
                                        zipIs = new ZipInputStream(new BufferedInputStream(fis));
                                        // Log.v("FILESF",zipIs.getNextEntry().getName());
                                        String fnamelist[] = zipIs.getNextEntry().getName().split("/");

                                        boolean nofull = false;

                                        for (int i = 0; i < fnamelist.length; i++) {
                                            if (fnamelist[i].equalsIgnoreCase("MYLO")) {
                                                nofull = true;
                                                break;
                                            }
                                        }
                                        zipIs.close();
                                        if (nofull==true){
                                            if (destfolders.exists()) {
                                                try {
                                                    FileUtils.deleteDirectory(destfolders);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            new UnZipTask(FilesActivity.this, folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath(),FilesActivity.this).execute();//nikita

                                        }else{
                                            AlertDialog.Builder alertf = new AlertDialog.Builder(FilesActivity.this);
                                            alertf.setTitle("Invalid");
                                            alertf.setMessage("Invalid MYLO Whole Backup");
                                            alertf.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            alertf.show();
                                        }
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    // new DropboxLoginActivity().unZip(folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath());


                                }

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
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to download file.", e);
                Toast.makeText(FilesActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(file);

    }

    private void viewFileInExternalApp(File result) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = result.getName().substring(result.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(FilesActivity.this, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", result);
        } else {
            contentUri = Uri.fromFile(result);
        }

        intent.setDataAndType(contentUri, type);


        // Check for a handler first to avoid a crash
        PackageManager manager = getPackageManager();
        List<ResolveInfo> resolveInfo = manager.queryIntentActivities(intent, 0);
        if (resolveInfo.size() > 0) {
            startActivity(intent);
        }
    }

    private void loadDropboxData() {


// Code for Received files
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();

        new ListReceivedFolderTask(FilesActivity.this, DropboxClientFactory.getClient(), new ListReceivedFolderTask.Callback() {
            @Override
            public void onDataLoaded(ArrayList<DropBoxFileItem> result) {

//
                ArrayList<DropBoxFileItem> resultList = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getShared() == 1) {
                        SharedFileMetadata ss = result.get(i).getSharefmd();
                        if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                            String name = ss.getName();
                            if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
                                // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                                resultList.add(result.get(i));
                            }
                        } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                            if (ss.getName().endsWith(".zip")) {
                                if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
                                    if (ss.getName().equals("MYLO.zip")) {

                                    } else {
                                        resultList.add(result.get(i));
                                    }
                                } else {
                                    if (ss.getName().equals("MYLO.zip")) {
                                        resultList.add(result.get(i));
                                    }
                                }
                                // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {

                            }
                        }
                    } else {
                        Metadata ss = result.get(i).getFilemd();
                        if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                            String name = ss.getName();
                            if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
                                // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                                resultList.add(result.get(i));
                            }
                        } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                            if (ss.getName().endsWith(".zip")) {
                                if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
                                    if (ss.getName().equals("MYLO.zip")) {

                                    } else {
                                        resultList.add(result.get(i));
                                    }
                                } else {
                                    if (ss.getName().equals("MYLO.zip")) {
                                        resultList.add(result.get(i));
                                    }
                                }
                                // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {

                            }
                        }
                    }
                }

                if (resultList.size() != 0) {
                    // finish();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                        DialogNodata("There is no PDF files in your Dropbox account.");
                    } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                        if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
                            DialogNodata("There is no Zip files in your Dropbox account.");
                        } else {
                            DialogNodata("There is no MYLO.zip file in your Dropbox account.");
                        }
                    }
//                    Toast.makeText(DropboxLoginActivity.this, "No Document or Backup File available in your dropbox", Toast.LENGTH_SHORT).show();
                }
                //  dialog.dismiss();
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

            }
        }).execute("");


    }

    private void DialogNodata(String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(FilesActivity.this);
        alert.setTitle("Alert");
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void uploadFile(final String fileUri) {
        // Uri contentUri = null;
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Backing up data can take several minutes...\nPlease be patient");
        dialog.show();

        new UploadFileTask(this, DropboxClientFactory.getClient(), new UploadFileTask.Callback() {
            @Override
            public void onUploadComplete(final FileMetadata result) {
                dialog.dismiss();
               /* File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + ".zip");
                if (destfolder.exists()) {
                    destfolder.delete();
                }*/
                File folder3 = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+preferences.getString(PrefConstants.ZIPFILE)+"/" );

                if (folder3.exists()) {
                    try {
                        FileUtils.deleteDirectory(folder3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                File folderzip = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+preferences.getString(PrefConstants.ZIPFILE)+".zip" );
                folderzip.delete();

                if (preferences.getString(PrefConstants.STORE).equals("Share")) {
                    preferences.putString(PrefConstants.SHARE, result.getId());
                    preferences.putString(PrefConstants.FILE, result.getName());
                    dialog.dismiss();
                    FilesActivity.this.finish();
                }else if (preferences.getString(PrefConstants.STORE).equals("Backup")) {
                    double mbs=((result.getSize()/1024.0)/1024.0);
                    String message = "Backup is stored in: " + result.getName() + "\n\nsize: " + String.format("%.2f", mbs)+" MB" + "\n\nmodified: " +
                            DateFormat.getDateTimeInstance().format(result.getClientModified());

                    preferences.putString(PrefConstants.SHARE, result.getId());
                    preferences.putString(PrefConstants.FILE, result.getName());
                    preferences.putString(PrefConstants.MSG, message);

                    dialog.dismiss();
                    FilesActivity.this.finish();
                } else {
                    double mbs=((result.getSize()/1024.0)/1024.0);
                    String message = "Backup is stored in: " + result.getName() + "\n\nsize: " + String.format("%.2f", mbs)+" MB" + "\n\nmodified: " +
                            DateFormat.getDateTimeInstance().format(result.getClientModified());
                    if (result.getName().equals("MYLO.zip")) {
                        Calendar c = Calendar.getInstance();
                        c.getTime();
                        c.add(Calendar.MONTH, 1);
                        DateFormat df = new SimpleDateFormat("dd MM yy HH:mm:ss");
                        String date = df.format(c.getTime());
                        preferences.putString(PrefConstants.BACKUPDATE, date);
                        preferences.putBoolean(PrefConstants.NOTIFIED, true);
                    }
                   /* DateFormat df = new SimpleDateFormat("dd MM yy HH:mm:ss");
                    Date backupDate= null;
                    backupDate = result.getClientModified();
                    Calendar c=Calendar.getInstance();
                    c.setTime(backupDate);
                    c.add(Calendar.MONTH,1);
                    preferences.putString(PrefConstants.BACKUPDATE, String.valueOf(c.getTime()));
*/
                    final AlertDialog.Builder alert = new AlertDialog.Builder(FilesActivity.this);
                    alert.setTitle("Backup Stored successfully");
                    alert.setMessage(message);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preferences.putString(PrefConstants.FINIS, "true");
                            dialog.dismiss();
                            FilesActivity.this.finish();


                        }
                    });
                    alert.show();
                }

              /*  String message = "Backup stored successfully, Do you want to share profile?";
                final AlertDialog.Builder alert = new AlertDialog.Builder(FilesActivity.this);
                alert.setTitle("Share Profile");
                alert.setMessage(message);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferences.putString(PrefConstants.SHARE,result.getId());
                        dialog.dismiss();
                        FilesActivity.this.finish();*/

                   /*     List<MemberSelector> newMembers = new ArrayList<MemberSelector>();
                        MemberSelector newMember = MemberSelector.email("varsha.shirtode@quagnitia.com");
                        MemberSelector newMember1 = MemberSelector.email("kmllnk@j.uyu");
                        newMembers.add(newMember);
                        newMembers.add(newMember1);
                        new ShareFileTask(newMembers, FilesActivity.this, DropboxClientFactory.getClient(), new ShareFileTask.Callback() {
                            @Override
                            public void onUploadComplete(List<FileMemberActionResult> result) {
                                final AlertDialog.Builder alerts = new AlertDialog.Builder(FilesActivity.this);
                                alerts.setTitle("Success");
                                alerts.setMessage("Profile shared successfully");
                                alerts.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        FilesActivity.this.finish();
                                    }
                                });
                                alerts.show();
                            }
                            @Override
                            public void onError(Exception e) {
                                FilesActivity.this.finish();
                            }
                        }).execute(result.getId());*/
            /*        }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FilesActivity.this.finish();
                    }
                });
                alert.show();
*/

            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to upload file.", e);
                Toast.makeText(FilesActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(fileUri, mPath);
    }

    public void performWithPermissions(final FileAction action) {
        if (hasPermissionsForAction(action)) {
            performAction(action);
            return;
        }

        if (shouldDisplayRationaleForAction(action)) {
            new AlertDialog.Builder(this)
                    .setMessage("This app requires storage access to download and upload files.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissionsForAction(action);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        } else {
            requestPermissionsForAction(action);
        }
    }

    private boolean hasPermissionsForAction(FileAction action) {
        for (String permission : action.getPermissions()) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldDisplayRationaleForAction(FileAction action) {
        for (String permission : action.getPermissions()) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    private void requestPermissionsForAction(FileAction action) {
        ActivityCompat.requestPermissions(
                this,
                action.getPermissions(),
                action.getCode()
        );
    }

    public void getFile(String s) {
        if (preferences.getString(PrefConstants.STORE).equals("Backup") || preferences.getString(PrefConstants.STORE).equals("Share")) {
            if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                // File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + ".zip");
                File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+preferences.getString(PrefConstants.ZIPFILE)+".zip");
                if (!destfolder.exists()) {
                    try {
                        destfolder.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Uri contentUri = null;
                contentUri = Uri.fromFile(destfolder);
                uploadFile(contentUri.toString());
            } else {
                File destfolder = new File(Environment.getExternalStorageDirectory(), preferences.getString(PrefConstants.ZIPFILE)+".zip");
                if (!destfolder.exists()) {
                    try {
                        destfolder.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Uri contentUri = null;
                contentUri = Uri.fromFile(destfolder);
                uploadFile(contentUri.toString());
            }
        } else {
            Preferences preferences = new Preferences(FilesActivity.this);
            if (s.equals("Yes")) {
                if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                    copydb(FilesActivity.this);
                } else {
                    copydbWholeBU(FilesActivity.this);
                }
                Toast.makeText(FilesActivity.this, "Unzipped and restored files successfully", Toast.LENGTH_SHORT).show();


                Intent intentDashboard = new Intent(FilesActivity.this, BaseActivity.class);
                intentDashboard.putExtra("c", 3);//Profile Data

                startActivity(intentDashboard);
                finish();
            } else {
                Toast.makeText(FilesActivity.this, "Restoring Failed, Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void copyFile(File backupDB, File currentDB) throws IOException {
        InputStream in = new FileInputStream(backupDB);
        try {
            OutputStream out = new FileOutputStream(currentDB);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }

    public enum FileAction {
        DOWNLOAD(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        UPLOAD(Manifest.permission.READ_EXTERNAL_STORAGE);

        private static final FileAction[] values = values();

        private final String[] permissions;

        FileAction(String... permissions) {
            this.permissions = permissions;
        }

        public static FileAction fromCode(int code) {
            if (code < 0 || code >= values.length) {
                throw new IllegalArgumentException("Invalid FileAction code: " + code);
            }
            return values[code];
        }

        public int getCode() {
            return ordinal();
        }

        public String[] getPermissions() {
            return permissions;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        preferences.putString(PrefConstants.FINIS, "true");
        finish();
    }


    private void copydb(Context context) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            File destfolder1 = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+backupDBPath);

            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+dirName);
            File[] files= destfolder.listFiles();
            boolean flag=false;
            for (int i=0;i<files.length;i++)
            {
                if (files[i].getName().equalsIgnoreCase("MASTER.db"))
                {
                    flag=true;
                    break;
                }
            }
            DBHelper dbHelper;
            if (flag==true)
            {
                String backup = preferences.getString(PrefConstants.RESULT);
                backup = backupDBPath.replace(".zip", "");
                dbHelper = new DBHelper(context, dirName);
            }else{
                String backupDBPaths=files[0].getName();
                File folder2 = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                File srcfolders = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+dirName+"/");

                try {
                    FileUtils.copyDirectory(srcfolders,folder2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dbHelper = new DBHelper(context, backupDBPaths,1);
            }

            //open new imported db
            // dbHelper = new DBHelper(context, backupDBPath,1);
            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
            //fetch data
            RelativeConnection connection = MyConnectionsQuery.fetchConnectionRecordforImport(1);
       /* Boolean flag = MyConnectionsQuery.updateImportMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), connection.getUserid());
        if (flag == true) {*/
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelpers);
            RelativeConnection connections = MyConnectionsQuery.fetchConnectionRecordforImport(connection.getEmail());
            if (connections != null) {
                if (connection.getRelationType().equals("Self")) {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(),connection.getHas_card());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                    }
                } else {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(),connection.getHas_card());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                        ContactDataQuery c = new ContactDataQuery(context, dbHelpers);
                        Boolean flagf = ContactDataQuery.updateUserId(connections.getId());
                        if (flagf == true) {
                            Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } else {
                if (!connection.getEmail().equals("")) {
                    Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, true);

                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                    }
                }
            }
            try {
                FileUtils.deleteDirectory(destfolder1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folderzip = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+backupDBPath+".zip" );
            folderzip.delete();
        } else {
            //Toast.makeText(context, "Need Data save to master db", Toast.LENGTH_SHORT).show();
        }
        //  }
       /* String mail=connection.getEmail();
        mail=mail.replace(".","_");
        mail=mail.replace("@","_");
        preferences.putString(PrefConstants.CONNECTED_USERDB,mail);
        preferences.putString(PrefConstants.CONNECTED_PATH,Environment.getExternalStorageDirectory()+"/MYLO/"+preferences.getString(PrefConstants.CONNECTED_USERDB)+"/");
            */
       /* File data = DropboxLoginActivity.this.getDatabasePath("temp.db");
        Log.e("", data.getAbsolutePath());

        File currentDB = new File(data.getAbsolutePath());
        File backupDB = new File(sd, backupDBPath);
        try {
            copy(backupDB, currentDB);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    private void copydbWholeBU(Context context) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            //open new imported db
            DBHelper dbHelper = new DBHelper(context, backupDBPath);
            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
            //fetch data
            ArrayList<RelativeConnection> connectionlist = MyConnectionsQuery.fetchConnectionRecordforImportAll();
       /* Boolean flag = MyConnectionsQuery.updateImportMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), connection.getUserid());
        if (flag == true) {*/
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelpers);
            for (int i = 0; i < connectionlist.size(); i++) {
                RelativeConnection connection = connectionlist.get(i);
                RelativeConnection connections = MyConnectionsQuery.fetchConnectionRecordforImport(connection.getEmail());
                if (connections != null) {
                    if (connection.getRelationType().equals("Self")) {
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(), connection.getHas_card());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", dirName);
                        }
                    } else {
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(), connection.getHas_card());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", dirName);
                        }
                    }
                } else {
//                    Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getUserid(), connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), "", connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());

                    Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, false);
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                    }
                }
            }
        } else {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            File destfolder = new File(Environment.getExternalStorageDirectory(),"MYLO");

            File[] files= destfolder.listFiles();
            boolean flag=false;
            for (int i=0;i<files.length;i++)
            {
                if (files[i].getName().equalsIgnoreCase("MYLO"))
                {
                    flag=true;
                    break;
                }
            }
            if (flag==true)
            {
                String backup = preferences.getString(PrefConstants.RESULT);
                backup = backupDBPath.replace(".zip", "");


                File folder2 = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                File srcfolders = new File(Environment.getExternalStorageDirectory(),"/"+dirName+"/");
                try {
                    FileUtils.copyDirectory(srcfolders,folder2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DBHelper dbHelpers = new DBHelper(context, "MASTER");
                ContactTableQuery ms = new ContactTableQuery(context, dbHelpers);
                ContactTableQuery.deleteContactData();
            }else{
                DBHelper dbHelpers = new DBHelper(context, "MASTER");
                ContactTableQuery ms = new ContactTableQuery(context, dbHelpers);
                ContactTableQuery.deleteContactData();
            }



            //Toast.makeText(context, "Need Data save to master db", Toast.LENGTH_SHORT).show();
        }

        //  }
       /* String mail=connection.getEmail();
        mail=mail.replace(".","_");
        mail=mail.replace("@","_");
        preferences.putString(PrefConstants.CONNECTED_USERDB,mail);
        preferences.putString(PrefConstants.CONNECTED_PATH,Environment.getExternalStorageDirectory()+"/MYLO/"+preferences.getString(PrefConstants.CONNECTED_USERDB)+"/");
            */
       /* File data = DropboxLoginActivity.this.getDatabasePath("temp.db");
        Log.e("", data.getAbsolutePath());

        File currentDB = new File(data.getAbsolutePath());
        File backupDB = new File(sd, backupDBPath);
        try {
            copy(backupDB, currentDB);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void storeImage(String selectedImage, String profile, String backupDBPath) {
        FileOutputStream outStream1 = null;
        File createDir = new File(Environment.getExternalStorageDirectory() + "/MYLO/MASTER/");
        if (!createDir.exists()) {
            createDir.mkdir();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/MYLO/" + backupDBPath + "/" + selectedImage);
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        try {
            if (myBitmap != null) {
                if (profile.equals("Profile")) {
                    outStream1 = new FileOutputStream(Environment.getExternalStorageDirectory() + "/MYLO/MASTER/" + selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                    byte[] byteArray = stream.toByteArray();
                    outStream1.write(byteArray);
                    outStream1.close();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

      /*  FileOutputStream outStream2 = null;
        File fileori = new File(Environment.getExternalStorageDirectory()+"/MYLO/"+backupDBPath);
        File files = new File(Environment.getExternalStorageDirectory()+"/MYLO/MASTER/");
        if (!files.exists()) {
            files.mkdirs();
        }
        try {
            outStream2=new FileOutputStream(fileori);
            outStream2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }*/
    }
}