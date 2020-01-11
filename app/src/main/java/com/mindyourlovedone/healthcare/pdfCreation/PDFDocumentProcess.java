package com.mindyourlovedone.healthcare.pdfCreation;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by welcome on 11/1/2017.
 */

/**
 * Class: PDFDocumentProcess
 * Generate and View PDF
 */
public class PDFDocumentProcess {

    public PDFDocumentProcess(String path, Context context, StringBuffer result) {
        viewFile(context, path);
    }

    public void viewFile(final Context context, String filename) {
        File targetFile = new File(filename);

        Uri targetUri = null;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (targetFile.getName().endsWith(".pdf")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                targetUri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", targetFile);
            } else {
                targetUri = Uri.fromFile(targetFile);
            }
            intent.setDataAndType(targetUri, "application/pdf");
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

        } else if (targetFile.getName().endsWith(".txt")) {

            intent = new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(targetUri, "application/*");

            context.startActivity(intent);

        } else {
            intent.setDataAndType(targetUri, "application/msword");

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
                                        .parse("market://details?id=cn.wps.moffice_eng"));
                                context.startActivity(marketIntent);
                            }
                        });
                builder.setNegativeButton("No", null);

                builder.create().show();
            }

        }
    }
}
