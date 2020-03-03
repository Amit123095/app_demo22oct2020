package com.mindyourlovedone.healthcare.Connections;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dropbox.core.v2.files.FileMetadata;
import com.mindyourlovedone.healthcare.DropBox.DropboxClientFactory;
import com.mindyourlovedone.healthcare.DropBox.FilesActivity;
import com.mindyourlovedone.healthcare.DropBox.UploadFileTask;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadFiles {
    String fileUri = "";
    Preferences preferences;
    Context context;
    ProgressBar progressBar;
    private String mPath;
    public final static String EXTRA_PATH = "FilesActivity_Path";

    public UploadFiles(String fileUri, Preferences preferences, Activity activity, ProgressBar progressBar) {
        this.fileUri = fileUri;
        this.preferences = preferences;
        context=activity;
        this.progressBar=progressBar;
        String path =activity.getIntent().getStringExtra(EXTRA_PATH);
        mPath = path == null ? "" : path;
        FilesActivity.getIntent(activity,"");
    }


    public void upload() {
        new UploadFileTask(context, DropboxClientFactory.getClient(), new UploadFileTask.Callback() {
            @Override
            public void onUploadComplete(final FileMetadata result) {
                progressBar.setVisibility(View.GONE);
                File folder3 = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + preferences.getString(PrefConstants.ZIPFILE) + "/");
                if (folder3.exists()) {
                    try {
                        FileUtils.deleteDirectory(folder3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                File folderzip = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + preferences.getString(PrefConstants.ZIPFILE) + ".zip");
                folderzip.delete();
                if (preferences.getString(PrefConstants.STORE).equals("Backup")) {
                    double mbs = ((result.getSize() / 1024.0) / 1024.0);
                    String message = "Backup is stored in: " + result.getName() + "\n\nsize: " + String.format("%.2f", mbs) + " MB" + "\n\nmodified: " +
                            DateFormat.getDateTimeInstance().format(result.getClientModified());
                    String name = result.getName();//whole_MYLO.zip
                    int l = name.length();
                    if (l >= 10) {
                        name = name.substring((l - 9), l);
                    }
                    if (result.getName().equals("MYLO.zip") || name.equals("_MYLO.zip")) {
                        Calendar c4 = Calendar.getInstance();
                        c4.getTime();
                        DateFormat df4 = new SimpleDateFormat("dd MM yy");
                        String date4 = df4.format(c4.getTime());
                        preferences.putString(PrefConstants.BACKUPDATE, date4);
                    }
                    preferences.putString(PrefConstants.SHARE, result.getId());
                    preferences.putString(PrefConstants.FILE, result.getName());
                    preferences.putString(PrefConstants.MSG, message);

                    //if (preferences.getString(PrefConstants.BACKUPDONE).equals("false")) {
                     //   preferences.putString(PrefConstants.BACKUPDONE, "true");
                       // showBackupDialog();
                        Log.v("FINDATA", "DONE");
                    //}

                }
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);

                Log.e("TAG", "Failed to upload file.", e);
                Toast.makeText(context,
                        "An error has occurred" + e.getMessage(),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(fileUri, mPath);
    }
}
