
package com.mindyourlovedone.healthcare.DashBoard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;


public class ViewImageActivity extends AppCompatActivity {
    private static final int RESULT_CARD = 50;
    Context context = this;
    ImageView imgDoc, imgBack, imgDelete, imgDot;
    ImageLoader imageLoaderCard;

    boolean IsDelete = false;
    String photo = "";
    TextView txtTitle;
    File imgFile;
    Preferences preferences;
    Bitmap myBitmap;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);
        preferences = new Preferences(context);
        initUi();
        initListener();
    }

    private void initListener() {

    }

    private void initUi() {
        imgBack = findViewById(R.id.imgBack);
        imgDoc = findViewById(R.id.imgDoc);
        imgDelete = findViewById(R.id.imgDelete);
        txtTitle = findViewById(R.id.txtTitle);
        imgDot = findViewById(R.id.imgDot);
        txtTitle.setText("Prescription");

      /*  Intent i=getIntent();
        if (i.getExtras()!=null) {
            photo = i.getExtras().getString("Image");
            if (!photo.equals("")) {
                imgFile = new File(photo);
                //  if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgDoc.setImageBitmap(myBitmap);
                //imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgConPhoto,displayImageOptions);
            }
           *//* Bitmap photoCard = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imgDoc.setImageBitmap(photoCard);*//*

          *//*  if (i.getExtras().containsKey("IsDelete")) {
                IsDelete = i.getExtras().getBoolean("IsDelete");
                if (IsDelete == true) {
                    imgDelete.setVisibility(View.VISIBLE);
                } else {
                    imgDelete.setVisibility(View.GONE);
                }
            }*//*
        }*/

        Intent i = getIntent();
        if (i.getExtras() != null) {
            photo = i.getExtras().getString("Image");
            if (!photo.equals("")) {
                File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);
                imgFile = new File(photo);
                if (imgFile1.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                    if (myBitmap.getWidth() > myBitmap.getHeight()) {
                        // imgDoc.setRotation(180);
                    } else {
                        imgDoc.setRotation(90);
                    }
                    imgDoc.setImageBitmap(myBitmap);

                    //   imgCard.setImageBitmap(myBitmap);
                }
            }
           /* Bitmap photoCard = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imgDoc.setImageBitmap(photoCard);*/

/*
            if (i.getExtras().containsKey("IsDelete")) {
                IsDelete = i.getExtras().getBoolean("IsDelete");
                if (IsDelete == true) {
                    imgDelete.setVisibility(View.VISIBLE);
                } else {
                    imgDelete.setVisibility(View.GONE);
                }
            }
*/

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.putExtra("Prescription", "Delete");
                    i.putExtra("Photo", photo);
                    setResult(RESULT_CARD, i);
                    finish();
                }
            });
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

/*
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("Prescription","Delete");
                i.putExtra("Photo",photo);
                setResult(RESULT_CARD,i);
                finish();
            }
        });
*/

        imgDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ViewImageActivity.this);
                alert.setTitle("Email ?");
                alert.setMessage("Do you want to email prescription ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File f = new File(imgFile.getAbsolutePath());
                        emailAttachement(f, ViewImageActivity.this, "Prescription Photo");
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
        });
    }

    private void emailAttachement(File f, Context context, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
        String name = preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, s); // subject


        String body = "Hi, \n" +
                "\n" +
               // "\n" + name +
                "I shared these document with you. Please check the attachment. \n" +
                "\n" +
                "Thank you,\n" +
                name;
        //"Mind Your Loved Ones - Support";
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

        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}