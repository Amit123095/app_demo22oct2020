package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindyourlovedone.healthcare.Connections.RelationActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DocumentQuery;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.Document;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class: AddDocumentActivity
 * Screen: Add Document Screen
 * A class that manages to add documents for advance directive, other documents, medical record
 * implements OnclickListener for onClick event on views
 */
public class AddDocumentActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final int RQUESTCODE = 400;
    private static final int RESULTCODE = 200;
    private static final int RESULT_ADVANCE = 20;
    private static final int RESULT_OTHER = 30;
    final CharSequence[] alert_items = {"Phone Storage", "Dropbox"};
    //final CharSequence[] dialog_items = { "Email", "Bluetooth", "View", "Print", "Fax" };
    final CharSequence[] dialog_items = {"View", "Email", "Fax"};
    final CharSequence[] dialog_add = {"Add to Advance Directives", "Add to Other Documents", "Add to Medical Records"};
    Context context = this;
    ImageView imgBack, imgDot, imgDone, imgDoc, imgAdd, imgHome, imgType;
    MySpinner spinnerDoc, spinnerType, spinnerPro;
    TextView txtDelete, txtSave, txtTitle, txtOtherDocType, txtName, txtAdd, txtHosp, txtLocator, txtDate, txtNote, txtLocation, txtHolderName, txtDist, txtOther, txtPName, txtFName, txtDocTYpe;
    String From;
    Preferences preferences;
    ArrayAdapter<String> adapter, adapter1, adapterPro;
    TextInputLayout tilLocator, tilDate, tilOther, tilOtherDocType, tilDocType, tilHosp, tilName, tilPName;
    RelativeLayout rlDocType, rlDelete;
    Document document;
    DBHelper dbHelper;
    String name = "";
    String type = "", note = "";
    String docType = "", otherDocType = "", person = "", principle = "";
    String otherCategory = "";
    String Hosp = "", locator = "";
    String documentPath = "";
    String originPath = "";
    String location = "";
    String holder = "";
    int photo;
    String date = "";
    String category = "";
    String Goto = "";
    String path = "";
    int id;
    Intent i;
    String[] ADList = {"HIPAA Authorization", "Health Care Proxy", "Living Will", "Living Will/Health Care Proxy", "MOLST", "Non-Hospital DNR Order", "POLST", "Other"};
    String[] OtherList = {"Financial", "Insurance", "Legal", "Other"};

    TextInputLayout tilDocuType, tilSpinDoc;
    TextView txtDocuType, txtSpinDoc;

    ScrollView scroll;

    boolean external_flag = false;
    List<RelativeConnection> items;
    FrameLayout flDelete, flProfile;
    ImageView floatOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Initialize database, get primary data and set data
        initComponent();

        //Initialize UI
        initUi();

        //Register a callback to be invoked when this views are clicked.
        initListener();
    }

    /**
     * Function: Initialize database and fetc all document records
     */
    public void getData() {
        DBHelper dbHelper = new DBHelper(this, "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(this, dbHelper);
        items = MyConnectionsQuery.fetchAllRecord();
    }

    /**
     * Initialize database,preferences, add external files
     */
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        DocumentQuery d = new DocumentQuery(context, dbHelper);
        if (preferences == null) {
            preferences = new Preferences(AddDocumentActivity.this);
        }

        From = preferences.getString(PrefConstants.FROM);//it could be Advance Directive, Other documents, medical record
        i = getIntent();
        Log.v("URI", i.getExtras().toString());
        if (i.hasExtra("PDF_EXT")) {// If addin file from externally
            final Uri audoUri = Uri.parse(i.getStringExtra("PDF_EXT"));
            if (audoUri != null) {
                Log.v("URI", audoUri.toString());

                From = i.getStringExtra("FROM");
                initUi();

                addfile(audoUri);
                external_flag = true;
            }
        }
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
        flProfile.setOnClickListener(this);
        txtFName.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        txtDelete.setOnClickListener(this);
        txtDocuType.setOnClickListener(this);
        txtSpinDoc.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
    }

    /**
     * Function: Initialize User Interface and view
     */
    private void initUi() {
        scroll = findViewById(R.id.scroll);
        scroll.smoothScrollTo(0, 0);
        tilDocuType = findViewById(R.id.tilDocuType);
        imgType = findViewById(R.id.imgType);
        txtDocuType = findViewById(R.id.txtDocuType);
        txtDocuType.setFocusable(false);
        floatOptions = findViewById(R.id.floatOptions);
        flProfile = findViewById(R.id.flProfile);
        tilSpinDoc = findViewById(R.id.tilSpinDoc);
        txtSpinDoc = findViewById(R.id.txtSpinDoc);
        txtSpinDoc.setFocusable(false);

        txtDelete = findViewById(R.id.txtDelete);

        imgHome = findViewById(R.id.imgHome);
        imgDot = findViewById(R.id.imgDot);
        imgDone = findViewById(R.id.imgDone);
        imgBack = findViewById(R.id.imgBack);
        imgDoc = findViewById(R.id.imgDoc);
        imgAdd = findViewById(R.id.imgAdd);
        spinnerDoc = findViewById(R.id.spinnerDoc);
        rlDocType = findViewById(R.id.rlDocType);
        // rlDelete = findViewById(R.id.rlDelete);
        flDelete = findViewById(R.id.flDelete);
        spinnerType = findViewById(R.id.spinnerType);

        txtSave = findViewById(R.id.txtSave);
        txtName = findViewById(R.id.txtName);
        txtHosp = findViewById(R.id.txtHosp);
        tilHosp = findViewById(R.id.tilHosp);
        txtLocator = findViewById(R.id.txtLocator);
        txtLocator.setMovementMethod(LinkMovementMethod.getInstance());
        tilLocator = findViewById(R.id.tilLocator);
        txtLocation = findViewById(R.id.txtLocation);
        txtNote = findViewById(R.id.txtNote);
        txtHolderName = findViewById(R.id.txtHolderName);
        txtDist = findViewById(R.id.txtDist);
        txtOther = findViewById(R.id.txtOther);
        txtAdd = findViewById(R.id.txtAdd);
        txtDocTYpe = findViewById(R.id.txtDocType);
        tilOther = findViewById(R.id.tilOther);
        tilOtherDocType = findViewById(R.id.tilOtherDocType);
        tilOtherDocType.setVisibility(View.GONE);
        txtOtherDocType = findViewById(R.id.txtOtherDocType);
        tilName = findViewById(R.id.tilName);
        tilPName = findViewById(R.id.tilPName);
        tilHosp = findViewById(R.id.tilHosp);
        tilDocType = findViewById(R.id.tilDocType);
        txtPName = findViewById(R.id.txtPName);
        txtFName = findViewById(R.id.txtFName);
        txtTitle = findViewById(R.id.txtTitle);
        tilDate = findViewById(R.id.tilDate);
        txtDate = findViewById(R.id.txtDate);

        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            tilLocator.setHint("Document Provider Name and Website");
        }else
        {
            tilLocator.setHint("Electronic Health Record (add web address, username and password)");
        }

        adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, OtherList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setHint("Document Category");

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoc.setAdapter(adapter);
        spinnerDoc.setHint("Document Description");

        txtLocator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                Linkify.addLinks(s, Linkify.WEB_URLS);

            }
        });
        txtLocator.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Do you want to remove Link?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        txtLocator.setText("");
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
                return false;
            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOther.setVisibility(View.VISIBLE);
                } else {
                    tilOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if (From.equals("AD")) {// if Come From Advance Directives
            // spinnerDoc.setVisibility(View.VISIBLE);
            tilSpinDoc.setVisibility(View.VISIBLE);
            rlDocType.setVisibility(View.VISIBLE);
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDoc.setAdapter(adapter);
            spinnerDoc.setHint("Document Description");
            spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).toString().equals("Other")) {
                        tilOtherDocType.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherDocType.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            tilDocType.setVisibility(View.GONE);
            tilHosp.setVisibility(View.GONE);
            txtHosp.setVisibility(View.GONE);
            tilLocator.setVisibility(View.GONE);
            txtLocator.setVisibility(View.GONE);
            txtName.setVisibility(View.GONE);
            tilName.setVisibility(View.GONE);

            tilPName.setHint("Document Received from");
            tilDate.setHint("Document Date");
            txtTitle.setText("Advance Directives");

        } else if (From.equals("Other")) {// if come from Other docs  section
            // spinnerDoc.setVisibility(View.GONE);
            tilSpinDoc.setVisibility(View.GONE);
            tilDocType.setVisibility(View.VISIBLE);
            tilHosp.setVisibility(View.GONE);
            txtHosp.setVisibility(View.GONE);
            tilLocator.setVisibility(View.GONE);
            txtLocator.setVisibility(View.GONE);
            txtName.setVisibility(View.GONE);
            tilName.setVisibility(View.GONE);

            tilPName.setHint("Document Received from");
            tilDate.setHint("Date of Document");
            txtTitle.setText("Other Documents");
        } else if (From.equals("Record")) {// if come from medical record section
            spinnerDoc.setVisibility(View.GONE);
            tilSpinDoc.setVisibility(View.GONE);
            spinnerType.setVisibility(View.GONE);
            tilDocuType.setVisibility(View.GONE);
            imgType.setVisibility(View.GONE);
            tilDocType.setVisibility(View.VISIBLE);
            tilHosp.setVisibility(View.VISIBLE);
            txtHosp.setVisibility(View.VISIBLE);
            tilLocator.setVisibility(View.VISIBLE);
            txtLocator.setVisibility(View.VISIBLE);
            txtName.setVisibility(View.GONE);
            tilName.setVisibility(View.GONE);
            txtTitle.setText("Medical Records");
            tilPName.setHint("Name on Document");
            tilDate.setHint("Date of Document");
        }
        switch (From) {
            case "AD":
                tilDocuType.setVisibility(View.GONE);
                imgType.setVisibility(View.GONE);
                break;

            case "Other":

                tilDocuType.setVisibility(View.VISIBLE);
                imgType.setVisibility(View.VISIBLE);
                break;
        }

        // Figure out what to do based on the intent type
        if (i.getExtras() != null) {
            if (i.hasExtra("GoTo")) {
                Goto = i.getExtras().getString("GoTo");
            }
            if (i.hasExtra("Path")) {
                path = i.getExtras().getString("Path");
            }
        }

        if (Goto.equals("View")) {
            txtSave.setVisibility(View.GONE);
            imgDone.setVisibility(View.GONE);
            imgDot.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);
            txtAdd.setVisibility(View.GONE);
            imgDoc.setClickable(true);
            floatOptions.setVisibility(View.VISIBLE);
            flDelete.setVisibility(View.GONE);
            disableView();
        } else if (Goto.equals("Edit")) {
            txtSave.setVisibility(View.VISIBLE);
            imgDot.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);
            flDelete.setVisibility(View.GONE);
            floatOptions.setVisibility(View.VISIBLE);
        } else {
            floatOptions.setVisibility(View.GONE);
            txtSave.setVisibility(View.VISIBLE);
            imgDot.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);
            flDelete.setVisibility(View.GONE);
            imgDoc.setClickable(false);
        }

        if (Goto.equals("View") || Goto.equals("Edit")) {// check screen Open For update
            // Update Documents data, Set initial data
            if (From.equals("AD")) {
                tilSpinDoc.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Description");
                spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (parent.getItemAtPosition(position).toString().equals("Other")) {
                            tilOtherDocType.setVisibility(View.VISIBLE);

                        } else {
                            tilOtherDocType.setVisibility(View.GONE);
                            txtOtherDocType.setText("");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                tilDocType.setVisibility(View.GONE);
                tilHosp.setVisibility(View.GONE);
                txtHosp.setVisibility(View.GONE);
                tilLocator.setVisibility(View.GONE);
                txtLocator.setVisibility(View.GONE);
                txtName.setVisibility(View.GONE);
                tilName.setVisibility(View.GONE);
            } else if (From.equals("Other")) {
                spinnerDoc.setVisibility(View.GONE);
                tilSpinDoc.setVisibility(View.GONE);
                tilDocType.setVisibility(View.VISIBLE);
                txtHosp.setVisibility(View.GONE);
                tilHosp.setVisibility(View.GONE);
                tilLocator.setVisibility(View.GONE);
                txtLocator.setVisibility(View.GONE);
                txtName.setVisibility(View.GONE);
                tilName.setVisibility(View.GONE);
            } else if (From.equals("Record")) {
                spinnerDoc.setVisibility(View.GONE);
                tilSpinDoc.setVisibility(View.GONE);
                tilDocuType.setVisibility(View.GONE);
                imgType.setVisibility(View.GONE);
                tilDocType.setVisibility(View.VISIBLE);
                txtHosp.setVisibility(View.VISIBLE);
                tilHosp.setVisibility(View.VISIBLE);
                txtLocator.setVisibility(View.VISIBLE);
                tilLocator.setVisibility(View.VISIBLE);
                txtName.setVisibility(View.GONE);
                tilName.setVisibility(View.GONE);
            }
            document = (Document) i.getExtras().getSerializable("DocumentObject");
            txtSpinDoc.setText(document.getType());
            txtDocuType.setText(document.getCategory());
            txtDate.setText(document.getDate());
            txtHolderName.setText(document.getHolder());
            txtLocation.setText(document.getLocation());
            txtNote.setText(document.getNote());
            txtFName.setText(document.getName());

            txtPName.setText(document.getPerson());
            txtName.setText(document.getPrinciple());
            txtOther.setText(document.getOtherCategory());
            txtHosp.setText(document.getHospital());
            txtLocator.setText(Html.fromHtml(document.getLocator()));
            txtLocator.setLinksClickable(true);
            txtLocator.setAutoLinkMask(Linkify.WEB_URLS);
            txtLocator.setMovementMethod(LinkMovementMethod.getInstance());
//If the edit text contains previous text with potential links
            Linkify.addLinks(txtLocator, Linkify.WEB_URLS);
            documentPath = document.getDocument();
            String extension = FilenameUtils.getExtension(document.getName());
            showDocIcon(extension, preferences.getString(PrefConstants.CONNECTED_PATH) + documentPath);
            imgAdd.setVisibility(View.VISIBLE);
            if (document.getType().equals("Other")) {
                tilOtherDocType.setVisibility(View.VISIBLE);
            } else {
                tilOtherDocType.setVisibility(View.GONE);
            }
            if (document.getCategory().equals("Other")) {
                tilOther.setVisibility(View.VISIBLE);
            } else {
                tilOther.setVisibility(View.GONE);
            }
            id = document.getId();

            int index = 0;
            if (From.equals("AD")) {
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Description");

            } else {
                txtDocTYpe.setText(document.getType());
                if (!document.getCategory().equals("")) {
                    txtDocuType.setText(document.getCategory());
                }
            }

            if (document.getFrom().equals("AD")) {
                if (document.getType().equals("Other")) {
                    tilOtherDocType.setVisibility(View.VISIBLE);
                    txtOtherDocType.setText(document.getOtherDoc());
                } else {
                    tilOtherDocType.setVisibility(View.GONE);
                }
            }


        } else {

        }
        mFirebaseAnalytics.setCurrentScreen(AddDocumentActivity.this,"Add_Document_Screen",null);

    }

    /**
     * Function: Select Document for add in to folder and database
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
            originPath = originPath.replace("/root_path/", "");//nikita-uncommented the code

            documentPath = f.getName();
            name = f.getName();
            //  preferences.putInt(PrefConstants.CONNECTED_USERID, 1);
            txtFName.setText(name);
            // imgDoc.setClickable(false);
            if (!name.equalsIgnoreCase("") && !documentPath.equalsIgnoreCase("")) {
                String text = "You Have selected <b>" + name + "</b> Document";
                Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
                showDialogWindow(text);
                txtAdd.setText("Edit File");
                String extension = FilenameUtils.getExtension(name);
                showDocIcon(extension, originPath);
                imgAdd.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void disableView() {
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtDocuType://Select Document type
                Intent j = new Intent(AddDocumentActivity.this, RelationActivity.class);
                j.putExtra("Category", "Other");
                startActivityForResult(j, RESULT_OTHER);
                break;

            case R.id.txtSpinDoc://Select Document Desc
                Intent x = new Intent(AddDocumentActivity.this, RelationActivity.class);
                x.putExtra("Category", "Advance");
                startActivityForResult(x, RESULT_ADVANCE);

                break;

            case R.id.imgHome://Navigate to Home screen
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;

            case R.id.imgBack:
                // navigate previous screen after checking data modification done or not, if yes it ask user to save
                getValues();
                if (!Goto.equals("Edit")) {
                    if (From.equals("AD")) {
                        if (name.equals("") && documentPath.equals("") &&
                                date.equals("") && location.equals("") &&
                                holder.equals("") && docType.equals("") &&
                                otherDocType.equals("") && note.equals("") && person.equals("")) {
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
                        if (name.equals("") && documentPath.equals("") &&
                                date.equals("") && location.equals("") &&
                                holder.equals("") && docType.equals("") &&
                                locator.equals("") && principle.equals("") &&
                                Hosp.equals("") && category.equals("") &&
                                otherCategory.equals("") && person.equals("") &&
                                otherDocType.equals("") && note.equals("")) {
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
                } else {
                    if (From.equals("AD")) {
                        if (document.getName().equals(name) && document.getDocument().equals(documentPath) &&
                                document.getDate().equals(date) && document.getType().equals(docType) &&
                                document.getOtherDoc().equals(otherDocType) && document.getNote().equals(note) &&
                                document.getPerson().equals(person) && document.getHolder().equals(holder) && document.getLocation().equals(location)) {
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

                        if (document.getName().equals(name) && document.getDocument().equals(documentPath) &&
                                document.getDate().equals(date) && document.getType().equals(docType) &&
                                document.getOtherDoc().equals(otherDocType) && document.getNote().equals(note) &&
                                document.getLocator().equals(locator) && document.getHospital().equals(Hosp) && document.getPrinciple().equals(principle) &&
                                document.getCategory().equals(category) && document.getOtherCategory().equals(otherCategory) &&
                                document.getPerson().equals(person) && document.getHolder().equals(holder) && document.getLocation().equals(location)) {
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
                }

                break;

            case R.id.txtFName:// Open file document on click of file name
                if (getIntent().hasExtra("PDF_EXT")) {

                } else {
                    if (!documentPath.equals("")) {
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
                    }

                }
                break;

            case R.id.txtDate:// Open Dialo to set Documene date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        long selectedMilli = newDate.getTimeInMillis();

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate = new SimpleDateFormat("d MMM yyyy").format(datePickerDate);

                        DateClass d = new DateClass();
                        d.setDate(reportDate);
                        txtDate.setText(reportDate);
                        // txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;
            //Shradha
            case R.id.txtDelete:
                deleteDocument(document);
                break;
            case R.id.txtSave: // Save Document details
                //Validate if user input is valid or not, If true then goes for next task
                if (validate()) {

                    documentPath = copydb(originPath, name);
                    if (Goto.equals("Edit")) {
                        Boolean flag = DocumentQuery.updateDocumentData(id, name, category, date, location, holder, photo, documentPath, docType, From, person, principle, otherCategory, Hosp, otherDocType, locator, note);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            if (From.equals("AD"))
                            {
                                bundle.putInt("Edit_Document",1);
                                mFirebaseAnalytics.logEvent("OnClick_Save_Document", bundle);
                            }else if (From.equals("Other"))
                            {
                                bundle.putInt("Edit_Document", 1);
                                mFirebaseAnalytics.logEvent("OnClick_Save_Document", bundle);
                            } else if (From.equals("Record"))
                            {
                                bundle.putInt("Edit_Document",1);
                                mFirebaseAnalytics.logEvent("OnClick_Save_Document", bundle);
                            }

                            Toast.makeText(context, "Document has been updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Boolean flag = DocumentQuery.insertDocumentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, category, date, location, holder, photo, documentPath, docType, From, person, principle, otherCategory, Hosp, otherDocType, locator, note);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            if (From.equals("AD"))
                            {
                                bundle.putInt("Add_AdvanceDocument",1);
                                mFirebaseAnalytics.logEvent("OnClick_Save_Document", bundle);
                            }else if (From.equals("Other"))
                            {
                                bundle.putInt("Add_OtherDocument",1);
                                mFirebaseAnalytics.logEvent("OnClick_Save_Document", bundle);
                            } else if (From.equals("Record"))
                            {
                                bundle.putInt("Add_MedicalDocument", 1);
                                mFirebaseAnalytics.logEvent("OnClick_Save_Document", bundle);
                            }

                            Toast.makeText(context, "Document has been saved successfully", Toast.LENGTH_SHORT).show();
                            if (external_flag == true) {
                                Intent i = new Intent(AddDocumentActivity.this, CarePlanListActivity.class);
                                startActivity(i);
                                preferences.putString(PrefConstants.FROM, From);
                            }
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;

            case R.id.imgDoc://OPen Document
                if (getIntent().hasExtra("PDF_EXT")) {

                } else {
                    if (!documentPath.equals("")) {
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
                            // Uri uris = Uri.parse(documentPath);
                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(documentPath));
                            intent.setDataAndType(uri, mimeType);
                            //  //intent.setPackage("com.adobe.reader");//varsa
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
                    } else {
                        DirectiveDialog();
                    }

                }

                break;
            case R.id.imgAdd://Select and add new Document
                DirectiveDialog();
                break;

            case R.id.floatOptions://View,Email,Fax added Document
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
                txtNew.setText("Email Document");

                TextView txtContact = dialogview.findViewById(R.id.txtContact);
                txtContact.setText("View Document");

                TextView txtFax = dialogview.findViewById(R.id.txtfax);
                txtFax.setText("Fax Document");
                rlFloatfax.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uris = Uri.parse(documentPath);
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + txtFName.getText().toString();
                        Intent i = new Intent(context, FaxActivity.class);
                        i.putExtra("PATH", preferences.getString(PrefConstants.CONNECTED_PATH) + documentPath);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });


                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                // int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                //lp.gravity = Gravity.CENTER;
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
                            emailAttachement(documentPath, txtFName.getText().toString());
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
                            // Uri uris = Uri.parse(documentPath);
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
                        dialog.dismiss();
                    }
                });

                break;

            case R.id.imgDot:

                AlertDialog.Builder builders = new AlertDialog.Builder(context);

                builders.setTitle("");

                builders.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                            case 0: // email
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
                                    // Uri uris = Uri.parse(documentPath);
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

                                break;
                            case 1: // email
                                if (path.equals("No")) {
                                    File file = new File(getExternalFilesDir(null), documentPath);
                                    Uri urifile = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        urifile = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", file);
                                    } else {
                                        urifile = Uri.fromFile(file);
                                    }

                                } else {
                                    emailAttachement(documentPath, txtFName.getText().toString());
                                }


                                break;
                            case 2: // Fax
                                Uri uris = Uri.parse(documentPath);
                                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + txtFName.getText().toString();
                                new FaxCustomDialog(AddDocumentActivity.this, preferences.getString(PrefConstants.CONNECTED_PATH) + documentPath).show();
                                break;
                        }
                    }
                });
                builders.create().show();
                break;

        }
    }
    /**
     * Function - Get values from all elements
     */
    private void getValues() {
        person = txtPName.getText().toString();
        principle = txtName.getText().toString();
        if (From.equals("AD")) {
            category = "AD";
            docType = txtSpinDoc.getText().toString();
            otherDocType = txtOtherDocType.getText().toString().trim();
        } else {
            otherDocType = "";
            docType = txtDocTYpe.getText().toString();
            category = txtDocuType.getText().toString();
            otherCategory = txtOther.getText().toString();
        }

        Hosp = txtHosp.getText().toString();
        locator = txtLocator.getText().toString();
        name = txtFName.getText().toString();
        location = txtLocation.getText().toString();
        note = txtNote.getText().toString();
        holder = txtHolderName.getText().toString();
        date = txtDate.getText().toString();
    }

    /**
     * Function: Delete selected document
     * @param item
     */
    private void deleteDocument(final Document item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = DocumentQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(context, "Document has been deleted successfully", Toast.LENGTH_SHORT).show();
                    if (context instanceof CarePlanListActivity) {
                        ((CarePlanListActivity) context).getData();
                        ((CarePlanListActivity) context).setDocuments();
                    }
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
     * Function: Chooser dialog for select document from i.e phone storage, email, dropbox
     */
    private void DirectiveDialog() {
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
            public void onClick(View v) {//Navigate to file list from storage
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
            public void onClick(View v) {//Navigate to file list from dropbox
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
                showEmailInsDialog();//Steps for addin document via email
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
     * Function: Instruction dialog for adding document via email
     */
    private void showEmailInsDialog() {
        final Dialog dialogEmail = new Dialog(context);
        dialogEmail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEmail.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_email, null);
        final TextView txtIns = dialogview.findViewById(R.id.txtIns);

        String data = Html.fromHtml(
                "<li> √ To upload an email attachment open the attachment from your email and click the forward button on the upper right side of the screen. <br></li>" +
                        "<li> √ Scroll through the App until you find MYLO.  Click MYLO – then click the Profile you wish to attach the document to, then click the sub-section the document pertains to and click OK. <br></li>" +
                        "<li> √ Enter additional information and then click Save. <br></li>"
                //  + "<li> √ Watch a 10 second video found in the Menu section of “How to Videos”. <br></li>"
        ).toString();

        txtIns.setText(data);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);


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

    private void serverAttachement(Uri path) {
        String urlPath = path.getPath();
        System.out.println("Path of the file    " + path);
        //WebService.sendPDFToFax(convertFileToByteArray(file));
        new FaxCustomDialog(AddDocumentActivity.this, urlPath).show();
    }

    /**
     * Function: Set Email body and document for send
     * @param documentPath
     * @param s
     */
    private void emailAttachement(String documentPath, String s) {
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
     * Function: Read document from storae
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
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(documentPath));
        intent.setDataAndType(uri, mimeType);
        try {
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {
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

    /**
     * Function: Validation of data input by user
     *
     * @return boolean, True if given input is valid, false otherwise.
     */
    private boolean validate() {
        int indexValues = spinnerType.getSelectedItemPosition();
        int indexValue = spinnerDoc.getSelectedItemPosition();
        person = txtPName.getText().toString();

        principle = txtName.getText().toString();
        if (From.equals("AD")) {
            category = "AD";
            docType = txtSpinDoc.getText().toString();
            otherDocType = txtOtherDocType.getText().toString().trim();
        } else {
            otherDocType = "";
            docType = txtDocTYpe.getText().toString();
            //  if (indexValues != 0) {
            category = txtDocuType.getText().toString();
            // }
            otherCategory = txtOther.getText().toString();
        }

        Hosp = txtHosp.getText().toString();
        locator = txtLocator.getText().toString();
        name = txtFName.getText().toString();
        location = txtLocation.getText().toString();
        note = txtNote.getText().toString();
        holder = txtHolderName.getText().toString();
        date = txtDate.getText().toString();
        photo = R.drawable.pdf;
        if (name.equals("")) {
            Toast.makeText(context, "Please Select File", Toast.LENGTH_SHORT).show();
        } else if (docType.equals("")) {
            txtDocTYpe.setError("Please Enter Document Description");
            Toast.makeText(context, "Please Enter Document Description", Toast.LENGTH_SHORT).show();
        } else if (date.equals("")) {
            txtDate.setError("Please Select Document Date");
            Toast.makeText(context, "Please Select Document Date", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTCODE && data != null) {// Selected Document URI
            name = data.getExtras().getString("Name");
            originPath = data.getExtras().getString("URI");
            if (!name.equalsIgnoreCase("")) {
                txtFName.setText(name);
                //  imgDoc.setClickable(false);
                String text = "You Have selected <b>" + name + "</b> Document";
                Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
                showDialogWindow(text);
                //  txtAdd.setText("Edit File");
                String extension = FilenameUtils.getExtension(name);
                showDocIcon(extension, originPath);
                imgAdd.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == RQUESTCODE) {//&& data != null) {//Selected Document URI

            name = preferences.getString(PrefConstants.RESULT);//data.getExtras().getString("Name");
            originPath = preferences.getString(PrefConstants.URI);//data.getExtras().getString("URI");
            if (!name.equalsIgnoreCase("")) {
                txtFName.setText(name);
                //   imgDoc.setClickable(false);
                String text = "You Have selected <b>" + name + "</b> Document";
                Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
                showDialogWindow(text);
                //  txtAdd.setText("Edit File");
                String extension = FilenameUtils.getExtension(name);
                showDocIcon(extension, originPath);
                imgAdd.setVisibility(View.VISIBLE);
            } else {
                imgAdd.setVisibility(View.GONE);
            }
        } else if (requestCode == RESULT_ADVANCE && data != null) {//Selected Document desc cateory
            docType = data.getStringExtra("Category");
            txtSpinDoc.setText(docType);
            if (docType.equals("Other")) {
                tilOtherDocType.setVisibility(View.VISIBLE);
            } else {
                tilOtherDocType.setVisibility(View.GONE);
                txtOtherDocType.setText("");
            }
        } else if (requestCode == RESULT_OTHER && data != null) {//Selected Document type


            category = data.getStringExtra("Category");
            txtDocuType.setText(category);
            if (category.equals("Other")) {
                tilOther.setVisibility(View.VISIBLE);
            } else {
                tilOther.setVisibility(View.GONE);
                txtOther.setText("");
            }
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

    private void copy(File backupDB, File currentDB) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Do something for KITKAT and above versions
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
        } else {
            // do something for phones running an SDK before KITKAT
            try {
                InputStream in = new FileInputStream(currentDB);
                OutputStream out = new FileOutputStream(backupDB);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Function: Activity Callback method called when screen gets visible and interactive
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showDocIcon(String extension, String originPath) {
        //  Toast.makeText(context,extension,Toast.LENGTH_SHORT).show();
        switch (extension) {
            case "pdf":
                imgDoc.setImageResource(R.drawable.pdf);
                break;
            case "txt":
                imgDoc.setImageResource(R.drawable.docx);
                break;
            case "docx":
                imgDoc.setImageResource(R.drawable.docx);
                break;

            case "xlsx":
                imgDoc.setImageResource(R.drawable.excel);
                break;
            case "doc":
                imgDoc.setImageResource(R.drawable.docx);
                break;
            case "xls":
                imgDoc.setImageResource(R.drawable.excel);
                break;
            case "png":
                imgDoc.setImageURI(Uri.parse(originPath));
                ;
                break;
            case "PNG":
                imgDoc.setImageURI(Uri.parse(originPath));
                break;
            case "jpg":
                imgDoc.setImageURI(Uri.parse(originPath));
                break;
            case "jpeg":
                imgDoc.setImageURI(Uri.parse(originPath));
                break;
            case "ppt":
                imgDoc.setImageResource(R.drawable.ppt);
                break;
            case "pptx":
                imgDoc.setImageResource(R.drawable.ppt);
                break;
            default:
                imgDoc.setImageResource(R.drawable.pdf);
                break;

        }

    }

}