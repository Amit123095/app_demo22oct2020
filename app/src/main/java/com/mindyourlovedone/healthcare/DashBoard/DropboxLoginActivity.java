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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.FileMemberActionResult;
import com.dropbox.core.v2.sharing.ListFilesResult;
import com.dropbox.core.v2.sharing.MemberSelector;
import com.dropbox.core.v2.sharing.SharedFileMetadata;
import com.dropbox.core.v2.users.FullAccount;
import com.mindyourlovedone.healthcare.Connections.FragmentConnectionNew;
import com.mindyourlovedone.healthcare.DropBox.DropBoxFileItem;
import com.mindyourlovedone.healthcare.DropBox.DropboxActivity;
import com.mindyourlovedone.healthcare.DropBox.DropboxClientFactory;
import com.mindyourlovedone.healthcare.DropBox.FilesActivity;
import com.mindyourlovedone.healthcare.DropBox.GetCurrentAccountTask;
import com.mindyourlovedone.healthcare.DropBox.ListFolderTask;
import com.mindyourlovedone.healthcare.DropBox.ListReceivedFolderTask;
import com.mindyourlovedone.healthcare.DropBox.ShareFileTask;
import com.mindyourlovedone.healthcare.DropBox.UnZipTask;
import com.mindyourlovedone.healthcare.DropBox.ZipListner;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.EventNoteQuery;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.DialogManager;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
    String id = "";

    int Fun_Type = 0;
    int isFile = 0;
    Context contextl;

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

    public void unZip(String absolutePath, String absolutePath1) {
        // new UnZipTask(DropboxLoginActivity.this, absolutePath, absolutePath1).execute();//nikita
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        preferences = new Preferences(context);
        preferences.putString(PrefConstants.RESULT, "");
        preferences.putString(PrefConstants.URI, "");
        preferences.putString(PrefConstants.SHARE, "");
        preferences.putString(PrefConstants.FILE, "");
        preferences.putString(PrefConstants.WOLE,"Default");
        preferences.putString(PrefConstants.MYACCESS, "");
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
        //nikita - 7-10-19
        txtLoginDropbox.setVisibility(View.GONE);
        txtLogoutDropbox.setVisibility(View.GONE);

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
                // Intent i = new Intent(context, ShareActivity.class);
                //   startActivity(i);
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Share");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

                } else {
                    Fun_Type = 1;
                    preferences.putString(PrefConstants.WOLE, "Share");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
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
                ds.setColor(getResources().getColor(R.color.colorNewHereBlue));    // you can use custom color
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
        preferences.putString(PrefConstants.ACCESS,"Default");
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            from = intent.getExtras().getString("FROM");
            todo = intent.getExtras().getString("ToDo");
            todoWhat = intent.getExtras().getString("ToDoWhat");

            if (from.equals("Document")) {
                rlBackup.setVisibility(View.GONE);
                /*SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {

                    preferences.putString(PrefConstants.ACCESS,"Document");
                    preferences.putString(PrefConstants.STORE, "Document");

                } else {

                    preferences.putString(PrefConstants.ACCESS,"Document");
                    preferences.putString(PrefConstants.MYACCESS,"");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }*/
            } else if (from.equals("Backup")) {
                rlBackup.setVisibility(View.VISIBLE);
                //SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                // if (prefs.contains("access-token")) {
                Fun_Type = 4;
                preferences.putString(PrefConstants.STORE, "Backup");
                preferences.putString(PrefConstants.TODO, todo);
                preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                //    loadDropboxData();
                //} else {
                //   Fun_Type = 1;
                //   preferences.putString(PrefConstants.ACCESS,"Backup");
                ///   Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                //
                // }


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
            else if (from.equals("Share")) {
                rlBackup.setVisibility(View.VISIBLE);
                // SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                // if (prefs.contains("access-token")) {
                Fun_Type = 4;
                preferences.putString(PrefConstants.STORE, "Share");
                preferences.putString(PrefConstants.TODO, todo);
                preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                //    loadDropboxData();
                // } else {
                //    Fun_Type = 1;
                //   Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                // }
            }
            else if (from.equals("Restore")){
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.ACCESS,"Restore");
                    preferences.putString(PrefConstants.STORE, "Restore");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                    //  startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                    // loadDropboxData();
                } else {
                    Fun_Type = 2;
                    preferences.putString(PrefConstants.ACCESS,"Restore");
                    preferences.putString(PrefConstants.MYACCESS,"");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
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
                // showBackupDialog();
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Backup");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

                } else {
                    Fun_Type = 1;
                    preferences.putString(PrefConstants.WOLE,"Backup");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
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
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                    // loadDropboxData();
                } else {
                    Fun_Type = 2;
                    preferences.putString(PrefConstants.WOLE,"Restore");
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

                    preferences.putString(PrefConstants.MYACCESS,"");
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

                    //   loadDropboxData();
                }
            } else {
                preferences.putString(PrefConstants.STORE, "Document");
                preferences.putString(PrefConstants.ACCESS,"Document");
                preferences.putString(PrefConstants.MYACCESS,"");
                Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
            }

            //  btnFiles.setVisibility(View.VISIBLE);
            btnBackup.setVisibility(View.GONE);
            btnRestore.setVisibility(View.GONE);
            btnShare.setVisibility(View.GONE);
        } else if (from.equals("Backup") || from.equals("Restore")) {
            btnBackup.setVisibility(View.VISIBLE);
            //   btnFiles.setVisibility(View.GONE);
            btnRestore.setVisibility(View.VISIBLE);
            if (todo.equals("Individual") && todoWhat.equals("Import")) {
                btnRestore.setVisibility(View.VISIBLE);
                btnBackup.setVisibility(View.GONE);
                btnShare.setVisibility(View.GONE);
            } else if (todo.equals("Individual") && todoWhat.equals("Share")) {
                btnRestore.setVisibility(View.GONE);
                btnBackup.setVisibility(View.VISIBLE);
                btnShare.setVisibility(View.VISIBLE);
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
//                            txtLogoutDropbox.setVisibility(View.GONE);
                            txtLoginPerson.setVisibility(View.VISIBLE);
//                            txtLoginDropbox.setVisibility(View.VISIBLE);
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
        if(preferences.getString(PrefConstants.ACCESS).equals("Document")) {
            preferences.putString(PrefConstants.ACCESS, "Default");
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
        }
        if (preferences.getString(PrefConstants.ACCESS).equals("Restore")) {
            preferences.putString(PrefConstants.ACCESS, "Default");
            preferences.putString(PrefConstants.STORE, "Restore");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
        }/*else if(preferences.getString(PrefConstants.ACCESS).equals("Document")) {
            preferences.putString(PrefConstants.ACCESS, "Default");
            preferences.putString(PrefConstants.STORE, "Document");
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
        }*/

        else
        if (preferences.getString(PrefConstants.WOLE).equals("Backup")) {
            preferences.putString(PrefConstants.WOLE, "Default");
            preferences.putString(PrefConstants.STORE, "Backup");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

        }else if (preferences.getString(PrefConstants.WOLE).equals("Share")) {
            preferences.putString(PrefConstants.WOLE, "Default");
            preferences.putString(PrefConstants.STORE, "Share");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

        }else if (preferences.getString(PrefConstants.WOLE).equals("Restore")) {
            preferences.putString(PrefConstants.WOLE, "Default");
            preferences.putString(PrefConstants.STORE, "Restore");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
        }


        new GetCurrentAccountTask(DropboxClientFactory.getClient(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
                String value = "You are logged in Dropbox as " + result.getEmail() ;
                txtName.setText(value);
//                txtLogoutDropbox.setVisibility(View.VISIBLE);
//                TextView txtLoginDropbox = findViewById(R.id.txtLoginDropbox);
//                txtLoginDropbox.setVisibility(View.GONE);

                preferences.putString(PrefConstants.MYACCESS,"");

            }

            @Override
            public void onError(Exception e) {
                Log.e( "TAD", "Failed to get account details.", e);

            }
        }).execute();
    }

    private void loadDropboxData() {
// Code for inbox files

        /*final ProgressDialog dialog = new ProgressDialog(this);
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
        }).execute("");*/

// Code for Received files
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();

        new ListReceivedFolderTask(DropboxClientFactory.getClient(), new ListReceivedFolderTask.Callback() {
            @Override
            public void onDataLoaded(ArrayList<DropBoxFileItem> result) {

//                ArrayList<SharedFileMetadata> resultList = new ArrayList<>();
//                for (int i = 0; i < result.getEntries().size(); i++) {
//                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
//                        String name = result.getEntries().get(i).getName();
//                        if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
//                            // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
//                            resultList.add(result.getEntries().get(i));
//                        }
//                    } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
//                        if (result.getEntries().get(i).getName().endsWith(".zip")) {
//                            if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
//                                if (result.getEntries().get(i).getName().equals("MYLO.zip")) {
//
//                                } else {
//                                    resultList.add(result.getEntries().get(i));
//                                }
//                            } else {
//                                if (result.getEntries().get(i).getName().equals("MYLO.zip")) {
//                                    resultList.add(result.getEntries().get(i));
//                                }
//                            }
//                            // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
//
//                        }
//                    }
//                }

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
                    Fun_Type = 4;
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
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
        if (preferences.getString(PrefConstants.MYACCESS).equals("Default"))
        {
            preferences.putString(PrefConstants.MYACCESS,"");
            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            if (!prefs.contains("access-token")) {
                finish();
            }
        }
        if (preferences.getString(PrefConstants.MYACCESS).equals("")&&preferences.getString(PrefConstants.ACCESS).equals("Restore"))
        {
            preferences.putString(PrefConstants.MYACCESS,"Default");
        }
        if (preferences.getString(PrefConstants.MYACCESS).equals("")&&preferences.getString(PrefConstants.ACCESS).equals("Backup"))
        {
            preferences.putString(PrefConstants.MYACCESS,"Default");
        }
        if (preferences.getString(PrefConstants.MYACCESS).equals("")&&preferences.getString(PrefConstants.ACCESS).equals("Share"))
        {
            preferences.putString(PrefConstants.MYACCESS,"Default");
        }
        if (preferences.getString(PrefConstants.FINIS).equals("true"))
        {
            preferences.putString(PrefConstants.FINIS,"false");
            finish();
        }
        if (preferences.getString(PrefConstants.URI).equals("") && preferences.getString(PrefConstants.RESULT).equals("")) {
//            btnAdd.setVisibility(View.GONE);
            //   txtFile.setVisibility(View.GONE);
            if (isFile == 0) {
                isFile++;
            } else if (preferences.getString(PrefConstants.URI).equals("") && preferences.getString(PrefConstants.RESULT).equals("") && preferences.getString(PrefConstants.STORE).equals("Document")) {
                finish();
            }

        } else {
            if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                finish();
                //  btnAdd.setVisibility(View.VISIBLE);
                //  txtFile.setVisibility(View.VISIBLE);
                //  txtFile.setText("Click on Add File for Add " + preferences.getString(PrefConstants.RESULT) + " File to your documents");
            } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                preferences.putString(PrefConstants.FINIS,"Restore");
                finish();
              /*  final AlertDialog.Builder alert = new AlertDialog.Builder(context);
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

                            new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath()).execute();//nikita


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
            }*/


            }
        }if (!preferences.getString(PrefConstants.SHARE).equals("")) {
            if (preferences.getString(PrefConstants.STORE).equals("Backup")) {
                if (preferences.getString(PrefConstants.FILE).equals("MYLO.zip")) {
                    showWoleBackupDialog();
                }
                else {
                    preferences.putString(PrefConstants.FINIS,"Backup");
                    DropboxLoginActivity.this.finish();
                }
            }
            if (preferences.getString(PrefConstants.STORE).equals("Share")) {
                if (preferences.getString(PrefConstants.FILE).equals("MYLO.zip")) {
                    showWoleEmailDialog();
                }
                else {
                    preferences.putString(PrefConstants.FINIS,"Share");
                    DropboxLoginActivity.this.finish();
                }

            }
        }
    }
    String txt = "Profile";
    private void showWoleEmailDialog() {
        final Dialog customDialog;
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_email);
        customDialog.setCancelable(false);
        final EditText etNote = customDialog.findViewById(R.id.etNote);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = customDialog.findViewById(R.id.btnNo);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                customDialog.dismiss();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String username = etNote.getText().toString();
                if (username.equals("")) {
                    etNote.setError("Please Enter email");
                    DialogManager.showAlert("Please Enter email", context);
                } else if (!username.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    etNote.setError("Please enter valid email");
                    DialogManager.showAlert("Please enter valid email", context);
                } else {
                    customDialog.dismiss();
                    List<MemberSelector> newMembers = new ArrayList<MemberSelector>();
                    MemberSelector newMember = MemberSelector.email(username);
                    // MemberSelector newMember1 = MemberSelector.email("kmllnk@j.uyu");
                    newMembers.add(newMember);
                    // newMembers.add(newMember1);


                    if(preferences.getString(PrefConstants.FILE).contains("MYLO.zip")){
                        txt = "Whole Backup";
                    }else{
                        txt = "Profile";
                    }

                    final ProgressDialog dialog = new ProgressDialog(context);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setMessage("Sharing "+txt+" can take several minutes");
                    dialog.show();
                    new ShareFileTask(newMembers,context, DropboxClientFactory.getClient(), new ShareFileTask.Callback() {
                        @Override
                        public void onUploadComplete(List<FileMemberActionResult> result) {
                            dialog.dismiss();
                            final AlertDialog.Builder alerts = new AlertDialog.Builder(context);
                            alerts.setTitle("Success");
                            alerts.setMessage(txt+" shared successfully");
                            alerts.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();


                                }
                            });
                            alerts.show();
                        }

                        @Override
                        public void onError(Exception e) {
                            dialog.dismiss();

                        }
                    }).execute(preferences.getString(PrefConstants.SHARE), preferences.getString(PrefConstants.FILE));
                }
            }
        });

        customDialog.show();
    }

    private void showWoleBackupDialog() {
        String message="";
        if (preferences.getString(PrefConstants.MSG)!=null) {
            message = preferences.getString(PrefConstants.MSG);
        }
        if (preferences.getString(PrefConstants.FILE).equals("MYLO.zip")) {
            Calendar c = Calendar.getInstance();
            c.getTime();
            c.add(Calendar.MONTH, 1);
            DateFormat df = new SimpleDateFormat("dd MM yy HH:mm:ss");
            String date = df.format(c.getTime());
            preferences.putString(PrefConstants.BACKUPDATE, date);
            preferences.putBoolean(PrefConstants.NOTIFIED, true);
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Backup Stored successfully");
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void showEmailDialog() {
        final Dialog customDialog;
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_email);
        customDialog.setCancelable(false);
        final EditText etNote = customDialog.findViewById(R.id.etNote);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = customDialog.findViewById(R.id.btnNo);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                hideSoftKeyboard();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String username = etNote.getText().toString();
                if (username.equals("")) {
                    etNote.setError("Please Enter email");
                    DialogManager.showAlert("Please Enter email", context);
                } else if (!username.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    etNote.setError("Please enter valid email");
                    DialogManager.showAlert("Please enter valid email", context);
                } else {
                    customDialog.dismiss();
                    List<MemberSelector> newMembers = new ArrayList<MemberSelector>();
                    MemberSelector newMember = MemberSelector.email(username);
                    // MemberSelector newMember1 = MemberSelector.email("kmllnk@j.uyu");
                    newMembers.add(newMember);
                    // newMembers.add(newMember1);

                    final ProgressDialog dialog = new ProgressDialog(DropboxLoginActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setMessage("Sharing profile can take several minutes");
                    dialog.show();
                    new ShareFileTask(newMembers, DropboxLoginActivity.this, DropboxClientFactory.getClient(), new ShareFileTask.Callback() {
                        @Override
                        public void onUploadComplete(List<FileMemberActionResult> result) {
                            dialog.dismiss();
                            final AlertDialog.Builder alerts = new AlertDialog.Builder(DropboxLoginActivity.this);
                            alerts.setTitle("Success");
                            alerts.setMessage("Profile shared successfully");
                            alerts.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    DropboxLoginActivity.this.finish();

                                }
                            });
                            alerts.show();
                        }

                        @Override
                        public void onError(Exception e) {
                            dialog.dismiss();
                            DropboxLoginActivity.this.finish();
                        }
                    }).execute(preferences.getString(PrefConstants.SHARE), preferences.getString(PrefConstants.FILE));
                }
            }
        });

        customDialog.show();
    }

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
                        ContactDataQuery c = new ContactDataQuery(context, dbHelpers);
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
      /*  FragmentConnectionNew f=new FragmentConnectionNew();
        f.getFile(res);
        finish();*/
        Preferences preferences=new Preferences(DropboxLoginActivity.this);
        if (res.equals("Yes")) {
            if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                copydb(context);
            } else {
                copydbWholeBU(context);
            }
            Toast.makeText(context, "Unzipped and restored files successfully", Toast.LENGTH_SHORT).show();


            Intent intentDashboard = new Intent(context, BaseActivity.class);
            intentDashboard.putExtra("c", 3);//Profile Data

            startActivity(intentDashboard);
            finish();
        } else {
            Toast.makeText(context, "Restoring Failed, Please try again", Toast.LENGTH_SHORT).show();
        }

    }

}
