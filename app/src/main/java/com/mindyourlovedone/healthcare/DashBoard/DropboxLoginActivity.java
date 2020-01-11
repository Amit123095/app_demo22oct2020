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
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.FileMemberActionResult;
import com.dropbox.core.v2.sharing.MemberSelector;
import com.dropbox.core.v2.sharing.SharedFileMetadata;
import com.dropbox.core.v2.users.FullAccount;
import com.mindyourlovedone.healthcare.DropBox.DropBoxFileItem;
import com.mindyourlovedone.healthcare.DropBox.DropboxActivity;
import com.mindyourlovedone.healthcare.DropBox.DropboxClientFactory;
import com.mindyourlovedone.healthcare.DropBox.FilesActivity;
import com.mindyourlovedone.healthcare.DropBox.GetCurrentAccountTask;
import com.mindyourlovedone.healthcare.DropBox.ListReceivedFolderTask;
import com.mindyourlovedone.healthcare.DropBox.ShareFileTask;
import com.mindyourlovedone.healthcare.DropBox.ZipListner;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
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

/**
 * Class: DropboxLoginActivity
 * Screen: Dropbox Backup,restore,share screen
 * A class that manages to dropbox login,logout,Backup,restore,share profile
 * implements OnclickListener for onClick event on views
 */
public class DropboxLoginActivity extends DropboxActivity {
    private static final int RESULTCODE = 400;
    private static final String APP_KEY = "428h5i4dsj95eeh";
    private static final String APP_SECRET = "6vlowskz2x12xil";
    private static final int REQUEST_CALL_PERMISSION = 100;
    Context context = this;
    Button btnLogin, btnShare;
    Button btnBackup, btnRestore;
    TextView txtName, txtBackup2, txtLogoutDropbox, txtLoginPerson;
    ImageView imgBack;
    TextView txtLoginDropbox;
    Preferences preferences;
    String from = "";
    String todo = "";
    String todoWhat = "";
    RelativeLayout rlBackup;
    String id = "";
    int Fun_Type = 0;
    int isFile = 0;

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
        preferences.putString(PrefConstants.SHARE, "");
        preferences.putString(PrefConstants.FILE, "");
        preferences.putString(PrefConstants.WOLE, "Default");
        preferences.putString(PrefConstants.MYACCESS, "");
        //Check for runtime permission
        accessPermission();

        txtLoginPerson = findViewById(R.id.txtLoginPerson);
        txtLogoutDropbox = findViewById(R.id.txtLogoutDropbox);
        txtLoginDropbox = findViewById(R.id.txtLoginDropbox);
        txtLogoutDropbox.setVisibility(View.GONE);
        txtLogoutDropbox.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {//Logout from Dropbox
                showLogOutDialog("Do you want to logout dropbox account");
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {//Share with dropbox
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    showEmailDialog("Share");
                } else {
                    Fun_Type = 1;
                    preferences.putString(PrefConstants.WOLE, "Share");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        });
        txtName = findViewById(R.id.txtLoginPerson);
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
        rlBackup = findViewById(R.id.rlBackup);
        imgBack = findViewById(R.id.imgBacks);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        preferences.putString(PrefConstants.ACCESS, "Default");
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            from = intent.getExtras().getString("FROM");
            todo = intent.getExtras().getString("ToDo");
            todoWhat = intent.getExtras().getString("ToDoWhat");
            //Check from which screen request it came from
            if (from.equals("Document")) {
                rlBackup.setVisibility(View.GONE);
            } else if (from.equals("Backup")) {
                rlBackup.setVisibility(View.VISIBLE);
                Fun_Type = 4;
                preferences.putString(PrefConstants.STORE, "Backup");
                preferences.putString(PrefConstants.TODO, todo);
                preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            } else if (from.equals("Share")) {
                rlBackup.setVisibility(View.VISIBLE);
                Fun_Type = 4;
                preferences.putString(PrefConstants.STORE, "Share");
                preferences.putString(PrefConstants.TODO, todo);
                preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            } else if (from.equals("Restore")) {
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.ACCESS, "Restore");
                    preferences.putString(PrefConstants.STORE, "Restore");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                } else {
                    Fun_Type = 2;
                    preferences.putString(PrefConstants.ACCESS, "Restore");
                    preferences.putString(PrefConstants.MYACCESS, "");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {// Login from dropbox
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                        }
                    });
                } else {
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        });

        txtLoginDropbox.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
// Login from dropbox
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                        }
                    });
                } else {
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        });

        btnBackup.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                //Process Backup on Dropbox
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    showEmailDialog("Backup");
                } else {
                    Fun_Type = 1;
                    preferences.putString(PrefConstants.WOLE, "Backup");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                //Proces Restore on Dropbox
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Restore");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                } else {
                    Fun_Type = 2;
                    preferences.putString(PrefConstants.WOLE, "Restore");
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }


            }
        });


        if (from.equals("Document")) {
            // For Add document from dropbox to Advance Directive Section
            preferences.putString(PrefConstants.STORE, "Document");
            Fun_Type = 5;
            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            if (prefs.contains("access-token")) {
                DropboxClientFactory.init(prefs.getString("access-token", ""));
                if (DropboxClientFactory.getClient() == null) {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                        }
                    });
                } else {
                    preferences.putString(PrefConstants.MYACCESS, "");
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                }
            } else {
                preferences.putString(PrefConstants.STORE, "Document");
                preferences.putString(PrefConstants.ACCESS, "Document");
                preferences.putString(PrefConstants.MYACCESS, "");
                Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
            }
            btnBackup.setVisibility(View.GONE);
            btnRestore.setVisibility(View.GONE);
            btnShare.setVisibility(View.GONE);
        } else if (from.equals("Backup") || from.equals("Restore")) {
            //Backup and restore
            btnBackup.setVisibility(View.VISIBLE);
            btnRestore.setVisibility(View.VISIBLE);
            if (todo.equals("Individual") && todoWhat.equals("Import")) {
                //Single Profile to Import
                btnRestore.setVisibility(View.VISIBLE);
                btnBackup.setVisibility(View.GONE);
                btnShare.setVisibility(View.GONE);
            } else if (todo.equals("Individual") && todoWhat.equals("Share")) {
                //Single Profile to Share
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
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    Fun_Type = 4;
                    preferences.putString(PrefConstants.STORE, "Backup");
                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
                } else {
                    Fun_Type = 1;
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
                dialogBank.dismiss();
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
                dialogBank.dismiss();
            }
        });
    }

    /**
     * Function: Ask for Logout from dropbox account
     *
     * @param msg
     */
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

                dialog.dismiss();
            }
        });

        alert.show();
    }

    /**
     * Function: Check for runtime permission
     */
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
        }
    }

    /**
     * Function: loadData()
     * Load files from dropbox
     * backup file to dropbox
     */
    @Override
    protected void loadData() {
        if (preferences.getString(PrefConstants.ACCESS).equals("Document")) {
            preferences.putString(PrefConstants.ACCESS, "Default");
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
        }
        if (preferences.getString(PrefConstants.ACCESS).equals("Restore")) {
            preferences.putString(PrefConstants.ACCESS, "Default");
            preferences.putString(PrefConstants.STORE, "Restore");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
        } else if (preferences.getString(PrefConstants.WOLE).equals("Backup")) {
            preferences.putString(PrefConstants.WOLE, "Default");
            preferences.putString(PrefConstants.STORE, "Backup");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

        } else if (preferences.getString(PrefConstants.WOLE).equals("Share")) {
            preferences.putString(PrefConstants.WOLE, "Default");
            preferences.putString(PrefConstants.STORE, "Share");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

        } else if (preferences.getString(PrefConstants.WOLE).equals("Restore")) {
            preferences.putString(PrefConstants.WOLE, "Default");
            preferences.putString(PrefConstants.STORE, "Restore");
            preferences.putString(PrefConstants.TODO, todo);
            preferences.putString(PrefConstants.TODOWHAT, todoWhat);
            startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
        }

// Check dropbox account details
        new GetCurrentAccountTask(DropboxClientFactory.getClient(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
                String value = "You are logged in Dropbox as " + result.getEmail();
                txtName.setText(value);
                txtLogoutDropbox.setVisibility(View.VISIBLE);
                txtLoginDropbox.setVisibility(View.GONE);
                preferences.putString(PrefConstants.MYACCESS, "");
            }

            @Override
            public void onError(Exception e) {
                Log.e("TAD", "Failed to get account details.", e);

            }
        }).execute();
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

    /**
     * Function: Activity Callback method called when screen gets visible and interactive
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (preferences.getString(PrefConstants.MYACCESS).equals("Default")) {
            preferences.putString(PrefConstants.MYACCESS, "");
            SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
            if (!prefs.contains("access-token")) {
                finish();
            }
        }
        if (preferences.getString(PrefConstants.MYACCESS).equals("") && preferences.getString(PrefConstants.ACCESS).equals("Restore")) {
            preferences.putString(PrefConstants.MYACCESS, "Default");
        }
        if (preferences.getString(PrefConstants.MYACCESS).equals("") && preferences.getString(PrefConstants.ACCESS).equals("Backup")) {
            preferences.putString(PrefConstants.MYACCESS, "Default");
        }
        if (preferences.getString(PrefConstants.MYACCESS).equals("") && preferences.getString(PrefConstants.ACCESS).equals("Share")) {
            preferences.putString(PrefConstants.MYACCESS, "Default");
        }
        if (preferences.getString(PrefConstants.FINIS).equals("true")) {
            preferences.putString(PrefConstants.FINIS, "false");
            finish();
        }
        if (preferences.getString(PrefConstants.URI).equals("") && preferences.getString(PrefConstants.RESULT).equals("")) {
            if (isFile == 0) {
                isFile++;
            } else if (preferences.getString(PrefConstants.URI).equals("") && preferences.getString(PrefConstants.RESULT).equals("") && preferences.getString(PrefConstants.STORE).equals("Document")) {
                finish();
            }

        } else {
            if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                finish();
            } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                preferences.putString(PrefConstants.FINIS, "Restore");
                finish();
            }
        }
        if (!preferences.getString(PrefConstants.SHARE).equals("")) {
            if (preferences.getString(PrefConstants.STORE).equals("Backup")) {
                if (preferences.getString(PrefConstants.FILE).equals("MYLO.zip")) {
                    showWoleBackupDialog();
                } else {
                    preferences.putString(PrefConstants.FINIS, "Backup");
                    DropboxLoginActivity.this.finish();
                }
            }
            if (preferences.getString(PrefConstants.STORE).equals("Share")) {
                if (preferences.getString(PrefConstants.FILE).equals("MYLO.zip")) {
                    showWoleEmailDialog();
                } else {
                    preferences.putString(PrefConstants.FINIS, "Share");
                    DropboxLoginActivity.this.finish();
                }

            }
        }
    }

    String txt = "Profile";

    /**
     * Function: Display dialog for input of dropbox email from user for sharing backup
     */
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
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                customDialog.dismiss();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String username = etNote.getText().toString().trim();
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
                    newMembers.add(newMember);

                    if (preferences.getString(PrefConstants.FILE).contains("MYLO.zip")) {
                        txt = "Whole Backup";
                    } else {
                        txt = "Profile";
                    }

                    final ProgressDialog dialog = new ProgressDialog(context);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setMessage("Sharing " + txt + " can take several minutes");
                    dialog.show();
                    new ShareFileTask(newMembers, context, DropboxClientFactory.getClient(), new ShareFileTask.Callback() {
                        @Override
                        public void onUploadComplete(List<FileMemberActionResult> result) {
                            dialog.dismiss();
                            final AlertDialog.Builder alerts = new AlertDialog.Builder(context);
                            alerts.setTitle("Success");
                            alerts.setMessage(txt + " shared successfully");
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

    /**
     * Function: Display dialog for backup stored successfully
     */
    private void showWoleBackupDialog() {
        String message = "";
        if (preferences.getString(PrefConstants.MSG) != null) {
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

    /**
     * Function: Hide device keyboard.
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Function: Display dialog for input of dropbox File name from user for sharing backup
     */
    private void showEmailDialog(final String from) {
        final Dialog customDialog;
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_zip);
        customDialog.setCancelable(false);
        final EditText etNote = customDialog.findViewById(R.id.etNote);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = customDialog.findViewById(R.id.btnNo);
        TextView txtnote = customDialog.findViewById(R.id.txtnote);
        txtnote.setVisibility(View.VISIBLE);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
        String formattedDate = df.format(date);
        etNote.setText("MYLO" + "_" + formattedDate);
        etNote.setSelection(etNote.getText().length());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                customDialog.dismiss();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String username = etNote.getText().toString().trim();
                username = username.replace(".", "_");
                username = username.replace("@", "_");
                username = username.replace(" ", "_");
                if (username.equals("")) {
                    etNote.setError("Please enter file name");
                    DialogManager.showAlert("Please enter file name", context);
                } else {
                    customDialog.dismiss();
                    Intent i = new Intent(context, DropboxLoginActivity.class);
                    if (from.equalsIgnoreCase("Share")) {
                        preferences.putString(PrefConstants.STORE, "Share");
                    } else if (from.equalsIgnoreCase("Backup")) {
                        preferences.putString(PrefConstants.STORE, "Backup");
                    }
                    Fun_Type = 4;

                    preferences.putString(PrefConstants.TODO, todo);
                    preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/");
                    preferences.putString(PrefConstants.ZIPFILE, username + "_MYLO");
                    startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));

                }
            }
        });

        customDialog.show();
    }
}
