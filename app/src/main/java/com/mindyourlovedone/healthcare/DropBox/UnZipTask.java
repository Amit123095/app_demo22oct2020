package com.mindyourlovedone.healthcare.DropBox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by welcome on 12/14/2017.
 */
//Unzip folder
public class UnZipTask extends AsyncTask<String, Void, String> {
    Context con;
    String outZipPath;
    String inputFolderPath;
    ZipListner context;
    ProgressDialog dialog;
    Context cont;
    String dirName;

    public UnZipTask(FilesActivity filesActivity, String absolutePath, String path,Context cont) {
        inputFolderPath = absolutePath;
        outZipPath = path;
        context = filesActivity;
        con = filesActivity;
        this.cont=cont;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(cont);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Unzipping");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        FileInputStream fis = null;
        ZipInputStream zis = null;
        try {
            fis = new FileInputStream(inputFolderPath);
            zis = new ZipInputStream(
                    new BufferedInputStream(fis));
            try {
                ZipEntry ze;
                int count;
                byte[] buffer = new byte[8192];
                Log.v("ZENT",outZipPath);
                while ((ze = zis.getNextEntry()) != null) {
                    File file = new File(outZipPath, ze.getName());

                    Log.v("ZENT",ze.getName());
                    File dir = ze.isDirectory() ? file : file.getParentFile();
                    if (!dir.isDirectory() && !dir.mkdirs())
                        throw new FileNotFoundException("Failed to ensure directory: " +
                                dir.getAbsolutePath());
                    if (ze.isDirectory())
                        continue;
                    Log.v("ZENTD",dir.getName());
                    dirName=dir.getName();

                    FileOutputStream fout = new FileOutputStream(file);
                    try {
                        while ((count = zis.read(buffer)) != -1)
                            fout.write(buffer, 0, count);
                    } finally {
                        fout.close();
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "No";
            } catch (IOException e) {
                e.printStackTrace();
                return "No";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "No";
        } finally {
            try {
                zis.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "No";
            }
        }
        return "Yes";

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
       if (dialog.isShowing()) {
            dialog.dismiss();
        }
       context.setNameFile(dirName);
        context.getFile(s);
    }
}
