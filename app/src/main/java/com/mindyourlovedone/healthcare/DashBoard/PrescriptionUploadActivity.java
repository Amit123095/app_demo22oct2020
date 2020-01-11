package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.PrescriptionUpload;
import com.mindyourlovedone.healthcare.model.Form;
import com.mindyourlovedone.healthcare.utility.FilePath;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class: PrescriptionInfoAdapter
 * Screen: Add prescription upload file
 * A class that manages to Add , Edit prescription upload
 * implements OnclickListener for onClick event on views
 */
public class PrescriptionUploadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULTCODE = 200;
    private static final int RQUESTCODE = 400;
    final CharSequence[] alert_items = {"Phone Storage", "Dropbox"};
    final CharSequence[] dialog_items = {"View", "Email", "Fax"};
    Context context = this;
    ImageView imgBack, imgDot, imgDone, imgDoc, imgAdd, imgEdit, floatOptions;
    TextView txtName, txtAdd, txtSave, txtAttach;
    TextInputLayout tilName;
    String From;
    Preferences preferences;
    Form document;
    DBHelper dbHelper;
    String name = "";
    RelativeLayout rlDoc, rlDocument;
    FrameLayout flDelete;

    String documentPath = "";
    String originPath = "";
    int photo;
    String path = "";
    String date = "";
    String Goto = "";
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_upload);
        //Initialize database, get primary data and set data
        initComponent();

        //Initialize UI and Views
        initUi();
        //Register a callback to be invoked when this views are clicked.
        initListener();

    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        imgBack.setOnClickListener(this);
        imgDot.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        imgDoc.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        rlDoc.setOnClickListener(this);
        rlDocument.setOnClickListener(this);
        flDelete.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        txtName.setOnClickListener(this);
        floatOptions.setOnClickListener(this);

    }
    /**
     * Function: Initialize User Interface and View
     */
    private void initUi() {
        txtAttach = findViewById(R.id.txtAttach);
        imgDot = findViewById(R.id.imgDot);
        imgDone = findViewById(R.id.imgDone);
        imgBack = findViewById(R.id.imgBack);
        imgDoc = findViewById(R.id.imgDoc);
        imgAdd = findViewById(R.id.imgAdd);
        txtName = findViewById(R.id.txtName);
        tilName = findViewById(R.id.tilName);
        txtAdd = findViewById(R.id.txtAdd);
        txtSave = findViewById(R.id.txtSave);
        rlDoc = findViewById(R.id.rlDoc);
        rlDocument = findViewById(R.id.rlDocument);
        flDelete = findViewById(R.id.flDelete);
        imgEdit = findViewById(R.id.imgEdit);
        floatOptions = findViewById(R.id.floatOptions);
        txtName.setClickable(true);
        txtName.setFocusable(false);

        Intent i = getIntent();
        if (i.getExtras() != null) {
            if (i.hasExtra("GoTo")) {
                Goto = i.getExtras().getString("GoTo");
            }
            if (i.hasExtra("Path")) {
                path = i.getExtras().getString("Path");
            }
        }

        if (Goto.equals("View")) {
            imgDot.setVisibility(View.VISIBLE);
            txtSave.setVisibility(View.GONE);
            imgDone.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);
            floatOptions.setVisibility(View.VISIBLE);
            document = (Form) i.getExtras().getSerializable("FormObject");
            txtName.setText(document.getName());
            documentPath = document.getDocument();
            imgEdit.setVisibility(View.VISIBLE);
            // imgDoc.setImageResource(document.getImage());
            String extension = FilenameUtils.getExtension(document.getName());
            showDocIcon(extension, preferences.getString(PrefConstants.CONNECTED_PATH) + documentPath);
            imgDoc.setVisibility(View.GONE);
            txtAttach.setVisibility(View.GONE);
            txtAdd.setVisibility(View.GONE);

        } else if (Goto.endsWith("Edit")) {
            floatOptions.setVisibility(View.VISIBLE);
            document = (Form) i.getExtras().getSerializable("FormObject");
            flDelete.setVisibility(View.GONE);
            txtName.setText(document.getName());
            documentPath = document.getDocument();
            // imgDoc.setImageResource(document.getImage());
            String extension = FilenameUtils.getExtension(document.getName());
            showDocIcon(extension, preferences.getString(PrefConstants.CONNECTED_PATH) + documentPath);
            imgEdit.setVisibility(View.VISIBLE);
            imgDoc.setVisibility(View.GONE);
            txtAttach.setVisibility(View.GONE);
            id = document.getId();
            imgDot.setVisibility(View.GONE);
            txtSave.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.GONE);
            txtAdd.setVisibility(View.GONE);
        } else {
        }

    }
    /**
     * Function: Initialize database , preferences
     */
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        PrescriptionUpload d = new PrescriptionUpload(context, dbHelper);

        Intent i = getIntent();
        Log.v("URI", i.getExtras().toString());
        if (i.hasExtra("PDF_EXT")) {
            final Uri audoUri = Uri.parse(i.getStringExtra("PDF_EXT"));
            if (audoUri != null) {
                Log.v("URI", audoUri.toString());

                From = i.getStringExtra("FROM");
                initUi();
                addfile(audoUri);
                //  external_flag = true;
            }
        }
    }
    /**
     * Function: Select and Add File into storae and db
     * @param audoUri
     */
    private void addfile(Uri audoUri) {
        try {
            originPath = audoUri.toString();
            String path = null;

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                path = FilePath.getPath(context, audoUri);
            }

            File f;
            if (path != null) {
                f = new File(path);
            } else {
                f = new File(audoUri.getPath());
            }
            originPath = f.getPath();
            originPath = originPath.replace("/root_path/", "");

            documentPath = f.getName();
            name = f.getName();
            txtName.setText(name);
            if (!name.equalsIgnoreCase("") && !documentPath.equalsIgnoreCase("")) {
                String text = "You Have selected <b>" + name + "</b> Document";
                Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
                imgDoc.setClickable(false);
                // imgDoc.setImageResource(R.drawable.pdf);
                String extension = FilenameUtils.getExtension(name);
                showDocIcon(extension, originPath);
                imgDoc.setVisibility(View.GONE);
                imgEdit.setVisibility(View.VISIBLE);
                imgDoc.setVisibility(View.GONE);
                txtAttach.setVisibility(View.GONE);
                txtAdd.setVisibility(View.GONE);
                ShowWindowDialog(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showDialogWindow(String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(Html.fromHtml(text));
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                // navigate previous screen after checking data modification done or not, if yes it ask user to save
                name = txtName.getText().toString().trim();
                if (!Goto.equals("Edit")) {
                    if (name.equals("") && documentPath.equals("")) {
                        finish();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Save");
                        alert.setMessage("Do you want to save information?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                txtSave.performClick();

                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        alert.show();
                    }

                } else {

                    if (document.getName().equals(name) && document.getDocument().equals(documentPath)) {
                        finish();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Save");
                        alert.setMessage("Do you want to save information?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                txtSave.performClick();

                            }
                        });

                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        alert.show();
                    }
                }
                break;
            case R.id.flDelete:
                deleteForm(document);
                break;

            case R.id.floatOptions://View,Email,Fax Form Document
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = lf.inflate(R.layout.activity_transparent, null);
                final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
                final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
//   final ImageView floatCancel = dialogview.findViewById(R.id.floatCancel);  // Rahul
                final FloatingActionButton floatViewPdf = dialogview.findViewById(R.id.floatContact);
                floatViewPdf.setImageResource(R.drawable.eyee);
                final FloatingActionButton floatEmail = dialogview.findViewById(R.id.floatNew);
                floatEmail.setImageResource(R.drawable.closee);

                RelativeLayout rlFloatfax = dialogview.findViewById(R.id.rlFloatfax);
                rlFloatfax.setVisibility(View.VISIBLE);
                TextView txtNew = dialogview.findViewById(R.id.txtNew);
                txtNew.setText("Email Prescription List");

                TextView txtContact = dialogview.findViewById(R.id.txtContact);
                txtContact.setText("View Prescription List");

                TextView txtFax = dialogview.findViewById(R.id.txtfax);
                txtFax.setText("\n" +
                        "Fax Prescription List");
                rlFloatfax.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uris = Uri.parse(documentPath);
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + txtName.getText().toString();
                        Intent i = new Intent(context, FaxActivity.class);
                        i.putExtra("PATH", preferences.getString(PrefConstants.CONNECTED_PATH) + documentPath);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });


                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(lp);
                dialog.show();

                rlView.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                floatCancel.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Function: Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                floatEmail.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Function: Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        if (path.equals("No")) {
                            File file = new File(getExternalFilesDir(null), documentPath);
                            Uri urifile = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                urifile = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", file);
                            } else {
                                urifile = Uri.fromFile(file);
                            }

                            // emailAttachement(urifile, txtFName.getText().toString());
                        } else {
                            // Uri uris = Uri.parse(documentPath);
                            emailAttachement(documentPath, txtName.getText().toString());
                        }
                        dialog.dismiss();

                    }
                });

                floatViewPdf.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Function: Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        Uri uri = null;
                        if (path.equals("No")) {
                            CopyReadAssetss(documentPath);
                        } else {
                            File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), documentPath);
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", targetFile);
                            } else {
                                uri = Uri.fromFile(targetFile);
                            }

                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(documentPath));
                            intent.setDataAndType(uri, mimeType);
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
                        dialog.dismiss();
                    }
                });

                break;

            case R.id.txtName://View Prescription upload Document
                Uri uri = null;
                if (!documentPath.equals("")) {
                    File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), documentPath);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", targetFile);
                    } else {
                        uri = Uri.fromFile(targetFile);
                    }
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(documentPath));
                    intent.setDataAndType(uri, mimeType);
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

                break;

            case R.id.txtSave: //Save Prescription upload deatils
                //Validate if user input is valid or not, If true then goes for next task
                if (validate()) {
                    documentPath = copydb(originPath, name);
                    DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
                    Date dates = new Date();
                    date = dateFormat.format(dates);
                    if (Goto.equals("Edit")) {
                        Boolean flag = PrescriptionUpload.updateDocumentData(id, name, photo, documentPath, date);
                        if (flag == true) {
                            Toast.makeText(context, "Prescription upload has been updated succesfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Boolean flag = PrescriptionUpload.insertDocumentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, photo, documentPath, date);
                        if (flag == true) {
                            Toast.makeText(context, "Prescription upload has been saved succesfully", Toast.LENGTH_SHORT).show();
                            try {
                                InputMethodManager inm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                                inm.hideSoftInputFromWindow(PrescriptionUploadActivity.this.getCurrentFocus().getWindowToken(), 0);
                            } catch (Exception e) {
                                //Todo: handle exception
                            }

                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                break;
            case R.id.imgEdit:
                formDialog();
                break;
            case R.id.rlDoc:
                Uri uris = null;
                if (!documentPath.equals("")) {
                    File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), documentPath);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        uris = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", targetFile);
                    } else {
                        uris = Uri.fromFile(targetFile);
                    }

                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(documentPath));
                    intent.setDataAndType(uris, mimeType);
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

                } else {
                    formDialog();
                }
                break;


            case R.id.imgAdd:
                formDialog();
                break;

            case R.id.imgDot:

                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("");

                alert.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                            case 0: //view
                                Uri uri = null;
                                if (!documentPath.equals("")) {
                                    File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", targetFile);
                                    } else {
                                        uri = Uri.fromFile(targetFile);
                                    }
                                    // Uri uris = Uri.parse(documentPath);
                                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(documentPath));
                                    intent.setDataAndType(uri, mimeType);
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

                                break;
                            case 1: //email
                                Uri urisd = Uri.parse(documentPath);
                                emailAttachement(documentPath, txtName.getText().toString());
                                break;
                            case 2: // fax
                                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + txtName.getText().toString();
                                new FaxCustomDialog(PrescriptionUploadActivity.this, preferences.getString(PrefConstants.CONNECTED_PATH) + documentPath).show();
                                break;
                        }
                    }
                });
                alert.create().show();
                break;

        }
    }
    /**
     * Function: Delete document record
     * @param item
     */
    private void deleteForm(final Form item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = PrescriptionUpload.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(context, "Prescription upload has been deleted succesfully", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                finish();
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
    /**
     * Function: Add documents through given methods i.e storae , dropbox, email
     */
    private void formDialog() {
        final Dialog dialogDirective = new Dialog(context);
        dialogDirective.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDirective.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_directives, null);
        final TextView txtPhoneStorage = dialogview.findViewById(R.id.txtPhoneStorage);
        final TextView txtDropbox = dialogview.findViewById(R.id.txtDropbox);
        final TextView txtEmail = dialogview.findViewById(R.id.txtEmail);
        final TextView txtCancel = dialogview.findViewById(R.id.txtCancel);

        dialogDirective.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogDirective.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
        dialogDirective.getWindow().setAttributes(lp);
        dialogDirective.setCanceledOnTouchOutside(false);
        dialogDirective.show();

        txtPhoneStorage.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DocumentSdCardList.class);
                startActivityForResult(i, RESULTCODE);
                dialogDirective.dismiss();
            }
        });

        txtDropbox.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DropboxLoginActivity.class);
                intent.putExtra("FROM", "Document");
                startActivityForResult(intent, RQUESTCODE);
                dialogDirective.dismiss();
            }
        });
        txtEmail.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                showEmailInsDialog();
                dialogDirective.dismiss();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                dialogDirective.dismiss();
            }
        });


    }
    /**
     * Function: Instruction for adding document via email
     */
    private void showEmailInsDialog() {
        final Dialog dialogEmail = new Dialog(context);
        dialogEmail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEmail.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_email, null);
        final TextView txtIns = dialogview.findViewById(R.id.txtIns);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);

        String data = Html.fromHtml(
                "<li> √ To upload an email attachment open the attachment from your email and click the forward button on the upper right side of the screen. <br></li>" +
                        "<li> √ Scroll through the App until you find MYLO.  Click MYLO – then click the Profile you wish to attach the document to, then click the sub-section the document pertains to and click OK. <br></li>" +
                        "<li> √ Enter additional information and then click Save. <br></li>"
                //+ "<li> √ Watch a 10 second video found in the Menu section of “How to Videos”. <br></li>"

        ).toString();

        txtIns.setText(data);
        dialogEmail.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEmail.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogEmail.getWindow().setAttributes(lp);
        dialogEmail.setCanceledOnTouchOutside(false);
        dialogEmail.show();

        txtOk.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                dialogEmail.dismiss();
            }
        });
    }


    private String copydb(String originPath, String name) {
        String sd = preferences.getString(PrefConstants.CONNECTED_PATH);
        File data = new File(originPath);
        Log.e("", data.getAbsolutePath());
        String backupDBPath = "/MYLO/MYLO/";

        File file = new File(sd);
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        File currentDB = new File(data.getAbsolutePath());
        File backupDB = new File(path, name);

        if (!backupDB.exists()) {
            try {
                backupDB.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            copy(backupDB, currentDB);
            return backupDB.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void copy(File backupDB, File currentDB) throws IOException {
        try (InputStream in = new FileInputStream(currentDB)) {
            try (OutputStream out = new FileOutputStream(backupDB)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
    /**
     * Function: Prepare email body with attachment form for share
     * @param uris
     * @param s
     */
    private void emailAttachement(String uris, String s) {
        Uri uri = null;
        File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), documentPath);
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
        String name = preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                name + " - " + s); // subject


        String body = "Hi, \n" +
                "\n" +
                //  "\n" + name +
                "I shared these document with you. Please check the attachment. \n" +
                "\n" +
                "Thank you,\n" +
                name;
        // "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", targetFile);
        } else {
            uri = Uri.fromFile(targetFile);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    /**
     * Function: Validation of data input by user
     *
     * @return boolean, True if given input is valid, false otherwise.
     */
    private boolean validate() {
        photo = R.drawable.pdf;
        name = txtName.getText().toString();

        if (name.length() == 0) {
            Toast.makeText(context, "Add Name of document", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTCODE && data != null) {
            name = data.getExtras().getString("Name");
            originPath = data.getExtras().getString("URI");
            if (!name.equalsIgnoreCase("")) {
                txtName.setText(name);
                String text = "You Have selected <b>" + name + "</b> Document";
                Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
                imgDoc.setClickable(false);
                // imgDoc.setImageResource(R.drawable.pdf);
                String extension = FilenameUtils.getExtension(name);
                showDocIcon(extension, originPath);
                imgDoc.setVisibility(View.GONE);
                imgEdit.setVisibility(View.VISIBLE);
                txtAttach.setVisibility(View.GONE);
                txtAdd.setText("Edit File");
                ShowWindowDialog(text);
            }
        } else if (requestCode == RQUESTCODE) {
            name = preferences.getString(PrefConstants.RESULT);
            originPath = preferences.getString(PrefConstants.URI);//data.getExtras().getString("URI");
            if (!name.equalsIgnoreCase("")) {
                txtName.setText(name);
                String text = "You Have selected <b>" + name + "</b> Document";
                Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
                String extension = FilenameUtils.getExtension(name);
                showDocIcon(extension, originPath);
                imgDoc.setVisibility(View.GONE);
                imgEdit.setVisibility(View.VISIBLE);
                txtAttach.setVisibility(View.GONE);
                imgDoc.setClickable(false);
                txtAdd.setText("Edit File");
                ShowWindowDialog(text);
            }
        }
    }

    private void ShowWindowDialog(String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(Html.fromHtml(text));
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
    /**
     * Function: Store And Access View file
     * @param documentPath
     */
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
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(documentPath));
        // Uri uris = Uri.parse(documentPath);
        intent.setDataAndType(uri, mimeType);
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

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void showDocIcon(String extension, String originPath) {
        // Toast.makeText(context,extension,Toast.LENGTH_SHORT).show();
        switch (extension) {
            case "pdf":
                rlDoc.setBackgroundResource(R.drawable.pdf);
                break;
            case "txt":
                rlDoc.setBackgroundResource(R.drawable.docx);
                break;
            case "docx":
                rlDoc.setBackgroundResource(R.drawable.docx);
                break;
            case "xlsx":
                rlDoc.setBackgroundResource(R.drawable.excel);
                break;
            case "doc":
                rlDoc.setBackgroundResource(R.drawable.docx);
                break;
            case "xls":
                rlDoc.setBackgroundResource(R.drawable.excel);
                break;
            case "png":
                BitmapDrawable background = new BitmapDrawable(originPath);
                rlDoc.setBackgroundDrawable(background);
                break;
            case "PNG":
                BitmapDrawable background1 = new BitmapDrawable(originPath);
                rlDoc.setBackgroundDrawable(background1);
                break;
            case "jpg":
                BitmapDrawable background2 = new BitmapDrawable(originPath);
                rlDoc.setBackgroundDrawable(background2);
                break;
            case "jpeg":
                BitmapDrawable background3 = new BitmapDrawable(originPath);
                rlDoc.setBackgroundDrawable(background3);
                break;
            case "ppt":
                rlDoc.setBackgroundResource(R.drawable.ppt);
                break;
            case "pptx":
                rlDoc.setBackgroundResource(R.drawable.ppt);
                break;
            default:
                rlDoc.setBackgroundResource(R.drawable.pdf);
                break;

        }

    }
}
