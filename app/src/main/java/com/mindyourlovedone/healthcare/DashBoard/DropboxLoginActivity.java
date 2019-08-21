package com.mindyourlovedone.healthcare.DashBoard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.mindyourlovedone.healthcare.DropBox.DropboxActivity;
import com.mindyourlovedone.healthcare.DropBox.DropboxClientFactory;
import com.mindyourlovedone.healthcare.DropBox.FilesActivity;
import com.mindyourlovedone.healthcare.DropBox.GetCurrentAccountTask;
import com.mindyourlovedone.healthcare.DropBox.ListFolderTask;
import com.mindyourlovedone.healthcare.DropBox.UnZipTask;
import com.mindyourlovedone.healthcare.DropBox.ZipListner;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DropboxLoginActivity extends DropboxActivity implements ZipListner {
    private static final int RESULTCODE = 400;
    private static final String APP_KEY = "428h5i4dsj95eeh";
    private static final String APP_SECRET = "6vlowskz2x12xil";
    private static final int REQUEST_CALL_PERMISSION = 100;
    private static final int REQUESTRESULT = 200;
    static boolean isLogin = false;
    Context context = this;
    Button btnLogin, btnShare, btnAdd;
    Button btnFiles, btnBackup, btnRestore;
    TextView txtHBackUp, txtHRestore, txtHShare, txtHUpload;
    TextView txtName, txtFile, txtBackup2, txtLogoutDropbox, txtLoginPerson;
    ImageView imgBack, imgDot;
    TextView txtLoginDropbox;
    Preferences preferences;
    String from = "";
    String todo = "";
    String todoWhat = "";
    RelativeLayout rlBackup, rlView;
    LinearLayout llHowToBackUp, llHowToRetore, llHowToShare, llHowToUpload;
    LinearLayout llBackup;
    boolean flagBackup = false;

    int Fun_Type = 0;
    int isFile=0;

    // For to Delete the directory inside list of files and inner Directory //nikita
    public static boolean deleteDir(File dir) {//nikita
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        preferences = new Preferences(context);
        preferences.putString(PrefConstants.RESULT, "");
        preferences.putString(PrefConstants.URI, "");

        accessPermission();



        //   imgDot = findViewById(R.id.imgDot);
/*
        imgDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InstructionActivity.class);
                i.putExtra("From", "Dropbox");
                startActivity(i);
            }
        });
*/
      /*  txtHBackUp = findViewById(R.id.txtHBackUp);
        txtHBackUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssetss("mylo_backup.pdf");

            }
        });*/

        llHowToBackUp = findViewById(R.id.rlHowToBackUp);
        llHowToBackUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Please Wait in progress..!!!", Toast.LENGTH_SHORT).show();
                CopyReadAssetss("mylo_backup.pdf");

            }
        });
        llHowToRetore = findViewById(R.id.rlHowToRetore);
        llHowToRetore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Please Wait in progress..!!!", Toast.LENGTH_SHORT).show();
                CopyReadAssetss("mylo_restore.pdf");

            }
        });

        llHowToUpload = findViewById(R.id.rlHowToUpload);
        llHowToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Please Wait in progress..!!!", Toast.LENGTH_SHORT).show();
                CopyReadAssetss("mylo_share.pdf");

            }
        });
        llHowToShare = findViewById(R.id.rlHowToShare);
        llHowToShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssetss("mylo_share.pdf");

            }
        });


        llBackup = findViewById(R.id.llBackup);
/*
        rlBackupNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgForward.setVisibility(View.GONE);
                imgDown.setVisibility(View.VISIBLE);
                if (flagBackup == false) {
                    llBackup.setVisibility(View.VISIBLE);
                    imgDown.setVisibility(View.VISIBLE);
                    imgForward.setVisibility(View.GONE);
                    flagBackup = true;
                } else if (flagBackup == true) {
                    llBackup.setVisibility(View.GONE);
                    imgDown.setVisibility(View.GONE);
                    imgForward.setVisibility(View.VISIBLE);
                    flagBackup = false;
                }

            }
        });
*/
        /*Shradha*/
        txtLoginPerson = findViewById(R.id.txtLoginPerson);
        txtLogoutDropbox = findViewById(R.id.txtLogoutDropbox);
        txtLoginDropbox = findViewById(R.id.txtLoginDropbox);
        txtLogoutDropbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogOutDialog("Do you want to logout dropbox account");
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShareActivity.class);
                startActivity(i);
            }
        });
        txtName = findViewById(R.id.txtLoginPerson);
        //  txtFile = findViewById(R.id.txtfile);
        txtBackup2 = findViewById(R.id.txtBackup2);
        txtBackup2.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) txtBackup2.getText();
        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorNewHereBlue)) ;    // you can use custom color
                ds.setUnderlineText(false);    // this remove the underline
            }
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.dropbox.com/help/account/create-account"));
                startActivity(intent);
            }
        };

        spans.setSpan(clickSpan, 32, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btnBackup = findViewById(R.id.btnBackup);
        btnRestore = findViewById(R.id.btnRestore);
        // rlView = findViewById(R.id.rlView);
        rlBackup = findViewById(R.id.rlBackup);
        // btnFiles = findViewById(R.id.btnFiles);
        imgBack = findViewById(R.id.imgBacks);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            from = intent.getExtras().getString("FROM");
            todo = intent.getExtras().getString("ToDo");
            todoWhat = intent.getExtras().getString("ToDoWhat");

            if (from.equals("Document")) {
                rlBackup.setVisibility(View.GONE);
                //  rlView.setVisibility(View.VISIBLE);
            } else if (from.equals("Backup")) {
                rlBackup.setVisibility(View.VISIBLE);
                //  rlView.setVisibility(View.GONE);
               /* if (todo.equals("Individual")&&todoWhat.equals("Import"))
                {
                    btnRestore.setVisibility(View.VISIBLE);
                    btnBackup.setVisibility(View.INVISIBLE);
                }
                else if (todo.equals("Individual")&&todoWhat.equals("Share"))
                {
                    btnRestore.setVisibility(View.INVISIBLE);
                    btnBackup.setVisibility(View.VISIBLE);
                }*/
            }
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                            // onResume();
                        }
                    });
                } else {
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        });

        txtLoginDropbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                            // onResume();
                        }
                    });
                } else {
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        });
/*
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.putExtra("Name", preferences.getString(PrefConstants.RESULT));
                i.putExtra("URI", preferences.getString(PrefConstants.URI));
                setResult(RESULTCODE, i);
                finish();
            }
        });
*/

/*
        btnFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.STORE, "Document");
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            }
        });
*/

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBackupDialog();

            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Restore");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
//                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                    loadDropboxData();
                } else {
                    Fun_Type = 2;
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }


            }
        });


        if (from.equals("Document")) {
            preferences.putString(PrefConstants.STORE, "Document");
            Fun_Type = 5;

            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            if (prefs.contains("access-token")) {
                DropboxClientFactory.init(prefs.getString("access-token", ""));

                if (DropboxClientFactory.getClient() == null) {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    //  loadDropboxData();
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                            // onResume();
                        }
                    });
                } else {
                    loadDropboxData();
                }
            } else {

                Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
            }

            //  btnFiles.setVisibility(View.VISIBLE);
            btnBackup.setVisibility(View.GONE);
            btnRestore.setVisibility(View.GONE);
        } else if (from.equals("Backup") || from.equals("Restore")) {
            btnBackup.setVisibility(View.VISIBLE);
            //   btnFiles.setVisibility(View.GONE);
            btnRestore.setVisibility(View.VISIBLE);
            if (todo.equals("Individual") && todoWhat.equals("Import")) {
                btnRestore.setVisibility(View.VISIBLE);
                btnBackup.setVisibility(View.GONE);
            } else if (todo.equals("Individual") && todoWhat.equals("Share")) {
                btnRestore.setVisibility(View.GONE);
                btnBackup.setVisibility(View.VISIBLE);
            }
        }

    }

    @SuppressLint("ResourceAsColor")
    private void showBackupDialog() {
        final Dialog dialogBank = new Dialog(context);
        dialogBank.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBank.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_backup, null);
        final TextView txtCancel = dialogview.findViewById(R.id.txtCancel);
        final TextView txtBackup = dialogview.findViewById(R.id.txtBackup);

        // txtComming.setText("Comming Soon");
        // txtComming.setTextColor(R.color.colorBlue);
        dialogBank.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogBank.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogBank.getWindow().setAttributes(lp);
        dialogBank.show();


        txtBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Backup");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                    //    loadDropboxData();
                } else {
                    Fun_Type = 1;
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
                dialogBank.dismiss();
            }


        });


        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBank.dismiss();
            }
        });
    }


    /*Shradha*/
    private void showLogOutDialog(String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Logout");
        alert.setMessage(msg);
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {

                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            txtLogoutDropbox.setVisibility(View.GONE);
                            txtLoginPerson.setVisibility(View.VISIBLE);
                            txtLoginDropbox.setVisibility(View.VISIBLE);
                        }
                    });
                    txtLoginPerson.setText("You are not yet logged in");
                }

               /* if () {
                    logOut();
                }*/

//                Toast.makeText(context, "Working on Logout please wait...!!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void logOut() {

    }

/*
    private void showLogOutDialog() {
    }
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
        context.startActivity(intent);

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(DropboxLoginActivity.this.getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(DropboxLoginActivity.this.getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CALL_PERMISSION);

        } else {
            // checkForRegistration();
        }
    }

    @Override
    protected void loadData() {
        new GetCurrentAccountTask(DropboxClientFactory.getClient(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
txtLogoutDropbox.setVisibility(View.GONE);
                if (Fun_Type == 1) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Backup");
                    preferences.putString(PrefConstants.TODO, todo);
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                } else if (Fun_Type == 2) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Restore");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
//                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                    loadDropboxData();

                }
                else if(Fun_Type == 5) {
                    Fun_Type = 4;
                    loadDropboxData();

                }
                else  {
                    Fun_Type = 4;
                    //startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                }

                String value = "You are logged in DropBox as " + result.getName().getDisplayName() /*+ " in dropbox."*/;
                txtName.setText(value);
                txtLogoutDropbox.setVisibility(View.VISIBLE);
                TextView txtLoginDropbox=findViewById(R.id.txtLoginDropbox);
                txtLoginDropbox.setVisibility(View.GONE);
//                btnLogin.setText("Login With Different User");

                //  Toast.makeText(DropboxLoginActivity.this,,Toast.LENGTH_SHORT).show();
                /*((TextView) findViewById(R.id.email_text)).setText(result.getEmail());
                ((TextView) findViewById(R.id.name_text)).setText(result.getName().getDisplayName());
                ((TextView) findViewById(R.id.type_text)).setText(result.getAccountType().name());*/
            }

            @Override
            public void onError(Exception e) {
                Log.e(getClass().getName(), "Failed to get account details.", e);
            }
        }).execute();
    }

    private void loadDropboxData() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();

        new ListFolderTask(DropboxClientFactory.getClient(), new ListFolderTask.Callback() {
            @Override
            public void onDataLoaded(ListFolderResult result) {

                ArrayList<Metadata> resultList = new ArrayList<Metadata>();
                for (int i = 0; i < result.getEntries().size(); i++) {
                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                        String name=result.getEntries().get(i).getName();
                        if (name.endsWith(".pdf")||name.endsWith(".txt")||name.endsWith(".docx")||name.endsWith(".doc")||name.endsWith(".xlsx")||name.endsWith(".xls")||name.endsWith(".png")||name.endsWith(".PNG")||name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".ppt")||name.endsWith(".pptx")) {
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
                }

                if (resultList.size() != 0) {
                    Fun_Type = 4;
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                    dialog.dismiss();
                } else {
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
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (preferences.getString(PrefConstants.URI).equals("") && preferences.getString(PrefConstants.RESULT).equals("")) {
//            btnAdd.setVisibility(View.GONE);
            //   txtFile.setVisibility(View.GONE);
            if (isFile==0)
            {
                isFile++;
            }else if(preferences.getString(PrefConstants.URI).equals("") && preferences.getString(PrefConstants.RESULT).equals("")&&preferences.getString(PrefConstants.STORE).equals("Document"))
            {
                finish();
            }

        } else {
            if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                finish();
                //  btnAdd.setVisibility(View.VISIBLE);
                //  txtFile.setVisibility(View.VISIBLE);
                //  txtFile.setText("Click on Add File for Add " + preferences.getString(PrefConstants.RESULT) + " File to your documents");
            } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
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
                                new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath()).execute();//nikita
                            } else {
                               /* AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Note");
                                alert.setMessage("Profile is already exists");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();*/
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Replace?");
                                alert.setMessage("Profile is already exists, Do you want to replace?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        destfolder.delete();//nikita
                                        try {
                                            destfolder.createNewFile();//nikita
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), destfolder1.getAbsolutePath()).execute();//nikita
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
                        } else {
                            String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                            //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                            String backupDBPath = "/Download/" + name;
                            String newname = name.replace(".zip", "");
                            final File folder = new File(sd, backupDBPath);
                            final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                    newname);
                            if (destfolder.exists()) {
                                deleteDir(destfolder);//nikita
                            }
//                            if (!destfolder.exists()) {
//                                destfolder.mkdir();
//                            }
                            new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath()).execute();//nikita

                                   /* else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Note");
                                alert.setMessage("Profile is already exists");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }*/
                           /* String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                            String backupDBPath = "/Download/" + "MYLO.zip";
                            File folder = new File(sd, backupDBPath);
                            File destfolder = new File(Environment.getExternalStorageDirectory(),
                                    "/MYLO/MYLO");
                            if (!destfolder.exists()) {
                                destfolder.mkdir();
                            }
                            new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), destfolder.getAbsolutePath()).execute();
                        */
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

           /* else if (preferences.getString(PrefConstants.STORE).equals("Backup")){
                final AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("BaCKUP");
                alert.setMessage("sUCCESS");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        copydb(context);
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
            }*/
        }
    }

    private void copydb(Context context) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            //open new imported db
            DBHelper dbHelper = new DBHelper(context, backupDBPath);
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
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                    }
                } else {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                        ContactDataQuery c=new ContactDataQuery(context,dbHelpers);
                        Boolean flagf = ContactDataQuery.updateUserId(connections.getId());
                        if (flagf == true) {
                            Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } else {
//                Boolean flags = MyConnectionqsQuery.insertMyConnectionsData(connection.getUserid(), connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), "", connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());

                Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, true);

                if (flags == true) {
                    Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                    storeImage(connection.getPhoto(), "Profile", backupDBPath);
                   /* RelativeConnection con = MyConnectionsQuery.fetchEmailRecords(connection.getEmail());
                    ContactDataQuery c=new ContactDataQuery(context,dbHelpers);
                    Boolean flagf = ContactDataQuery.updateUserId(con.getId());
                    if (flagf == true) {
                        Toast.makeText(context, "Insret updated", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
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
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", backupDBPath);
                        }
                    } else {
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", backupDBPath);
                        }
                    }
                } else {
//                    Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getUserid(), connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), "", connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());

                    Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, false);
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                    }
                }
            }
        } else {
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            ContactTableQuery ms = new ContactTableQuery(context, dbHelpers);
            ContactTableQuery.deleteContactData();
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

    private void copy(File backupDB, File currentDB) throws IOException {
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


    @Override
    public void getFile(String res) {
        if (res.equals("Yes")) {
            if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                copydb(context);
            } else {
                copydbWholeBU(context);
            }
            Toast.makeText(context, "Unzipped and restored files successfully", Toast.LENGTH_SHORT).show();
            //   Intent i=new Intent(context, FragmentConnectionNew.class); //Rahul Patil
            //   startActivity(i);                                           //Rahul Patil

            Intent intentDashboard = new Intent(context, BaseActivity.class);
            intentDashboard.putExtra("c", 3);//Profile Data
            //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentDashboard);
            finish();
        } else {
            Toast.makeText(context, "Restoring Failed, Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
