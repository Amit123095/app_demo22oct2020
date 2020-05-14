package com.mindyourlovedone.healthcare.DashBoard;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindyourlovedone.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.customview.MySpinner;
import com.mindyourlovedone.healthcare.customview.TouchImageView;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DosageQuery;
import com.mindyourlovedone.healthcare.database.PrescribeImageQuery;
import com.mindyourlovedone.healthcare.database.PrescriptionQuery;
import com.mindyourlovedone.healthcare.model.Dosage;
import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.model.Prescription;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class: AddPrescriptionActivity
 * Screen: Add Prescription Screen
 * A class that manages to Add, Update Prescription details
 * implements OnclickListener for onClick event on views
 */
public class AddPrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_PRES = 100;
    private static final int REQUEST_CARD = 50;
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    Context context = this;
    String currentImage = null;
    Uri imageUriProfile = null;
    ContentValues values = null;
    Prescription presc;
    int userid, uniqID;
    View view1;
    TextView txtPhotoHeader;//Shradha
    ImageView txtAddPhoto, imgBack, imgAddDosage, imgAddPhoto, imgDone;
    ListView ListDosage;//ListPhoto;
    ArrayList<Dosage> dosageList = new ArrayList<>();
    ArrayList<PrescribeImage> imageList = new ArrayList<>();
    ArrayList<PrescribeImage> imageListOld = new ArrayList<>();
    RelativeLayout llAddPrescription;
    String pre = "";
    ToggleButton tbPre;
    TextInputLayout tilTitle;
    TextView txtName, txtDate, txtPurpose, txtNote, txtRX, txtPre, txtMedicine, txtDose, txtFrequency, txtTitle, txtSave;
    EditText etNote;
    MySpinner spinner;
    RadioGroup rgCounter;
    RadioButton rbYes, rbNo;
    String[] FormList = {"Capsule", "Cream", "Drops", "Gel", "Liquid", "Lotion", "Pills", "Powder", "Syrup", "Tablet", "Other"};
    String imagepath = "";//
    String counter = "No";
    RelativeLayout rlPre;

    Preferences preferences;
    DBHelper dbHelper;
    int unique;
    boolean isEdit, isView;//Shradha
    int id, colid;
    LinearLayout casts_container;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Initialize database, get primary data and set data
        initComponent();
        //Initialize Image loading and displaying at ImageView
        initImageLoader();
        //Initialize user interface view and components
        initUI();

        //Register a callback to be invoked when this views are clicked.
        initListener();
    }

    /**
     * Function: Image loading and displaying at ImageView
     * Presents configuration for ImageLoader & options for image display.
     */
    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * Function: Initialize Database and Preferences
     */
    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        PrescriptionQuery p = new PrescriptionQuery(context, dbHelper);
        PrescribeImageQuery i = new PrescribeImageQuery(context, dbHelper);
        DosageQuery d = new DosageQuery(context, dbHelper);
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        llAddPrescription.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgAddDosage.setOnClickListener(this);
        imgAddPhoto.setOnClickListener(this);
        txtAddPhoto.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        txtDate.setOnClickListener(this);

        rgCounter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYes) {
                    pre = "YES";
                } else if (checkedId == R.id.rbNo) {
                    pre = "NO";
                }
            }
        });

    }

    /**
     * Function: Initialize user interface view and components
     */
    private void initUI() {

        view1 = findViewById(R.id.view1);//Shradha
        casts_container = findViewById(R.id.casts_container);//nikita
        txtPhotoHeader = findViewById(R.id.txtPhotoHeader);//Shradha
        txtTitle = findViewById(R.id.txtTitle);
        txtName = findViewById(R.id.txtName);
        txtDate = findViewById(R.id.txtDate);
        txtPurpose = findViewById(R.id.txtPurpose);
        txtPre = findViewById(R.id.txtPre);
        txtRX = findViewById(R.id.txtRX);
        txtMedicine = findViewById(R.id.txtMedicine);
        txtDose = findViewById(R.id.txtDose);
        txtFrequency = findViewById(R.id.txtFrequency);

        tbPre = findViewById(R.id.tbPre);
        rgCounter = findViewById(R.id.rgCounter);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);

        spinner = findViewById(R.id.spinner);
        imgBack = findViewById(R.id.imgBack);
        imgAddDosage = findViewById(R.id.imgAddDosage);
        imgAddPhoto = findViewById(R.id.imgAddPhoto);
        txtAddPhoto = findViewById(R.id.txtAddPhoto);
        llAddPrescription = findViewById(R.id.llAddPrescription);
        ListDosage = findViewById(R.id.ListDosage);
        imgDone = findViewById(R.id.imgDone);
        etNote = findViewById(R.id.etNote);
        txtNote = findViewById(R.id.txtNote);
        txtSave = findViewById(R.id.txtSave);
        rlPre=findViewById(R.id.rlPre);
        if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
        {
            rlPre.setVisibility(View.GONE);
        }else
        {
           rlPre.setVisibility(View.VISIBLE);
        }
        tbPre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                    pre = "YES";
                else
                    pre = "NO";
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, FormList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Dosage Form");

        tilTitle = findViewById(R.id.tilTitle);
        Intent i = getIntent();
        if (i.getExtras() != null) {

            Prescription p = (Prescription) i.getExtras().getSerializable("PrescriptionObject");
            presc = (Prescription) i.getExtras().getSerializable("PrescriptionObject");

            isView = i.getExtras().getBoolean("IsView");//Shradha
            if (isView == true)//Shradha
            {
                disablePrescription();//Shradha
                txtTitle.setText("PRESCRIPTIONS");//Shradha
                txtSave.setVisibility(View.GONE);//Shradha
                id = p.getUnique();//Shradha
                uniqID = p.getUnique();//Shradha
                userid = p.getUserid();//Shradha
                colid = p.getId();//Shradha
                txtName.setText(p.getDoctor());//Shradha
                txtDate.setText(p.getDates());//Shradha
                etNote.setText(p.getNote());//Shradha
                if (p.getPre().equals("YES")) {//Shradha
                    tbPre.setChecked(true);//Shradha
                } else if (p.getPre().equals("NO")) {//Shradha
                    tbPre.setChecked(false);//Shradha
                }
                txtRX.setText(p.getRX());//Shradha
                txtPurpose.setText(p.getPurpose());//Shradha
                txtDose.setText(p.getDose());//Shradha
                txtFrequency.setText(p.getFrequency());//Shradha
                txtMedicine.setText(p.getMedicine());//Shradha
                dosageList = p.getDosageList();//Shradha
                imageList = PrescribeImageQuery.fetchAllImageRecord(p.getUserid(), p.getUnique());//Shradha
                imageListOld = imageList;//Shradha
                // setDosageData();//Shradha
                setImageListData();//Shradha
            }


            isEdit = i.getExtras().getBoolean("IsEdit");
            if (isEdit == true) {
                txtTitle.setText("Edit Prescription");
                id = p.getUnique();
                uniqID = p.getUnique();
                userid = p.getUserid();
                colid = p.getId();
                txtName.setText(p.getDoctor());
                txtDate.setText(p.getDates());
                etNote.setText(p.getNote());
                if (p.getPre().equals("YES")) {
                    tbPre.setChecked(true);
                } else if (p.getPre().equals("NO")) {
                    tbPre.setChecked(false);
                }
                txtRX.setText(p.getRX());
                txtPurpose.setText(p.getPurpose());
                txtDose.setText(p.getDose());
                txtFrequency.setText(p.getFrequency());
                txtMedicine.setText(p.getMedicine());
                dosageList = p.getDosageList();
                imageList = PrescribeImageQuery.fetchAllImageRecord(p.getUserid(), p.getUnique());
                imageListOld = imageList;
                // setDosageData();
                setImageListData();
            } else {
                txtTitle.setText("Add Prescription");
            }
        }


    }

    private void disablePrescription() {//Shradha
        view1.setVisibility(View.GONE);//Shradha
        txtPhotoHeader.setVisibility(View.GONE);//Shradha
        //imgAddPhoto.setVisibility(View.GONE);//Shradha
        txtAddPhoto.setVisibility(View.GONE);
        txtSave.setEnabled(false);//Shradha
        txtName.setEnabled(false);//Shradha
        txtDate.setEnabled(false);//Shradha
        etNote.setEnabled(false);//Shradha
        tbPre.setEnabled(false);//Shradha
        txtRX.setEnabled(false);//Shradha
        txtPurpose.setEnabled(false);//Shradha
        txtDose.setEnabled(false);//Shradha
        txtFrequency.setEnabled(false);//Shradha
        txtMedicine.setEnabled(false);//Shradha
    }


    /**
     * Function: Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:// Navgate to previous screen after saving changes
                String doctors = txtName.getText().toString().trim();
                String purposes = txtPurpose.getText().toString().trim();
                String notes = etNote.getText().toString().trim();
                String dates = txtDate.getText().toString().trim();
                String rxs = txtRX.getText().toString().trim();
                String doses = txtDose.getText().toString().trim();
                String frequencys = txtFrequency.getText().toString().trim();
                String medicines = txtMedicine.getText().toString().trim();
                if (isEdit == false) {
                    if (doctors.equals("") && purposes.equals("") &&
                            notes.equals("") && dates.equals("") &&
                            rxs.equals("") && doses.equals("") &&
                            frequencys.equals("") && medicines.equals("") && pre.equals("")) {
                        finish();
                    } else {
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
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

                    if (presc.getMedicine().equals(medicines) && presc.getDoctor().equals(doctors) &&
                            presc.getPurpose().equals(purposes) && presc.getNote().equals(notes) &&
                            presc.getDates().equals(dates) && presc.getPre().equals(pre) &&
                            presc.getFrequency().equals(frequencys) && presc.getRX().equals(rxs)) {
                        finish();
                    } else {
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
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

            case R.id.txtSave://Save Prescription details
                String doctor = txtName.getText().toString().trim();
                String purpose = txtPurpose.getText().toString().trim();
                String note = etNote.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String rx = txtRX.getText().toString().trim();
                String dose = txtDose.getText().toString().trim();
                String frequency = txtFrequency.getText().toString().trim();
                String medicine = txtMedicine.getText().toString().trim();
                if (medicine.equals("")) {
                    Toast.makeText(context, "Please enter Name of Medication/Supplement", Toast.LENGTH_SHORT).show();
                    txtMedicine.setError("Please enter Name of Medication/Supplement");
                } else {
                    if (isEdit == false) {
                        unique = generateRandom();
                        Boolean flag = PrescriptionQuery.insertPrescriptionData(preferences.getInt(PrefConstants.CONNECTED_USERID), doctor, purpose, note, date, dosageList, imageList, unique, pre, rx, dose, frequency, medicine);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("Add_PrescriptionInformation", 1);
                            mFirebaseAnalytics.logEvent("OnClick_Save_PrescriptionInformation", bundle);

                            Toast.makeText(context, "Prescription has been saved succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(AddPrescriptionActivity.this);
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Boolean flag = PrescriptionQuery.updatePrescriptionData(colid, uniqID, doctor, purpose, note, date, dosageList, imageList, preferences.getInt(PrefConstants.CONNECTED_USERID), pre, rx, dose, frequency, medicine, imageListOld);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("Edit_PrescriptionInformation",1);
                            mFirebaseAnalytics.logEvent("OnClick_Save_PrescriptionInformation", bundle);

                            Toast.makeText(context, "Prescription has been updated succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(AddPrescriptionActivity.this);
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    Intent i = new Intent();
                    setResult(RESULT_PRES, i);
                    finish();
                }
                break;

            case R.id.txtAddPhoto:
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = lf.inflate(R.layout.dialog_gender, null);
                final TextView textOption1 = dialogview.findViewById(R.id.txtOption1);
                final TextView textOption2 = dialogview.findViewById(R.id.txtOption2);
                final TextView textOption3 = dialogview.findViewById(R.id.txtOption3);
                TextView textCancel = dialogview.findViewById(R.id.txtCancel);
                textOption1.setText("Take Picture");
                textOption2.setText("Gallery");
                textOption3.setText("Remove Picture");
                textOption3.setVisibility(View.GONE);
                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
                lp.width = width;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                textOption1.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Function: Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        imageUriProfile = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriProfile);

                        startActivityForResult(intent, RESULT_CAMERA_IMAGE);
                        dialog.dismiss();
                        dialog.dismiss();
                    }
                });
                textOption2.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Function: Called when a view has been clicked.
                     *
                     * @param v The view that was clicked.
                     */
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_SELECT_PHOTO);
                        dialog.dismiss();
                    }
                });

                textOption3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!imageList.isEmpty()) {

                            int i = imageList.size() - 1;
                            PrescribeImage a = imageList.get(i);
                            boolean flag = PrescribeImageQuery.deleteRecords(a.getUserid(), a.getPreid());
                            if (flag == true) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                imageList.remove(a);
                                setImageListData();
                            } else {
                                Toast.makeText(context, "Record not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                textCancel.setOnClickListener(new View.OnClickListener() {
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

                break;

        }
    }

    private int generateRandom() {
        Random r = new Random();
        int randomNumber = r.nextInt(500);

        return randomNumber;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
               /* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.infidigi.fotobuddies.fileprovider",
                        photoFile);*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);
            }
        }
    }

    private File createImageFile() {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        imagepath = image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ImageView profileImage = (ImageView) findViewById(R.id.imgProfile);
        if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                // profileImage.setImageBitmap(selectedImage);
                storeImage(selectedImage, "Profile");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUriProfile);
                storeImage(thumbnail, "Profile");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CARD) {
            if (data != null) {
                if (data.getExtras().getString("Prescription").equals("Delete")) {
                    String photo = data.getExtras().getString("Photo");
                    for (int i = 0; i < imageList.size(); i++) {
                        if (imageList.get(i).getImage().equals(photo)) {
                            boolean flag = PrescribeImageQuery.deleteImageRecord(imageList.get(i).getId());
                            if (flag == true) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                imageList.remove(imageList.get(i));
                                setImageListData();
                            }
                        }
                    }

                }
            }
        }
    }
    /**
     * Function: Store Image in application storage folder
     * @param selectedImage
     * @param profile
     */
    private void storeImage(Bitmap selectedImage, String profile) {

        FileOutputStream outStream = null;
        File file = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        try {

            imagepath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                    + ".jpg";
            // Write to SD Card
            outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + imagepath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
            byte[] byteArray = stream.toByteArray();
            outStream.write(byteArray);
            outStream.close();

            PrescribeImage p = new PrescribeImage();
            p.setImage(imagepath);
            if (isEdit) {
                p.setUserid(colid);
                p.setPreid(uniqID);
            }

            imageList.add(p);
            setImageListData();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * Function: Set Images in list
     */
    private void setImageListData() {

        casts_container.removeAllViews();
        //create LayoutInflator class
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        int size = imageList.size();
        for (int i = 0; i < size; i++) {
            PrescribeImage cast = imageList.get(i);
// create dynamic LinearLayout and set Image on it.
            if (cast != null) {
                LinearLayout clickableColumn = (LinearLayout) inflater.inflate(
                        R.layout.img_presc, null);
                ImageView thumbnailImage = (ImageView) clickableColumn
                        .findViewById(R.id.img);
                ImageView imgdelete = (ImageView) clickableColumn
                        .findViewById(R.id.imgdelete);

                if (!cast.getImage().equals("")) {
                    File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH) + cast.getImage());
                    imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)), thumbnailImage, displayImageOptions);
                }
                thumbnailImage.setTag(cast);
                imgdelete.setTag(cast);
                thumbnailImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PrescribeImage pp = (PrescribeImage) view.getTag();
                        showFloatDialog(pp.getImage());
                    }
                });

                imgdelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PrescribeImage pp = (PrescribeImage) view.getTag();
                        boolean flag = PrescribeImageQuery.deleteImageRecord(pp.getId());
                        if (flag == true) {
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            imageList.remove(pp);
                            setImageListData();
                        }
                    }
                });

                casts_container.addView(clickableColumn);
            }
        }
    }

    File imgFile;

    private void showFloatDialog(String path) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_add_form, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);


        ImageView imgBack, imgDot;
        TextView txtTitle;
        TouchImageView imgDoc;
        Preferences preferences;
        Bitmap myBitmap;

        imgBack = dialogview.findViewById(R.id.imgBack);
        imgDoc = dialogview.findViewById(R.id.imgDoc);
        txtTitle = dialogview.findViewById(R.id.txtTitle);
        imgDot = dialogview.findViewById(R.id.imgDot);
        txtTitle.setText("Prescription");
        preferences = new Preferences(this);
        File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), path);
        imgFile = new File(path);
        if (imgFile1.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
            if (myBitmap.getWidth() > myBitmap.getHeight()) {
                // imgDoc.setRotation(180);
            } else {
                imgDoc.setRotation(90);
            }
            imgDoc.setImageBitmap(myBitmap);

            imgBack.setOnClickListener(new View.OnClickListener() {
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

            imgDot.setOnClickListener(new View.OnClickListener() {
                /**
                 * Function: Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(AddPrescriptionActivity.this);
                    alert.setTitle("Email ?");
                    alert.setMessage("Do you want to email prescription ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File f = new File(imgFile.getAbsolutePath());
                            emailAttachement(f, AddPrescriptionActivity.this, "Prescription Photo");
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
            dialog.show();

        }
    }
    /**
     * Function: Prepare email body with attachment file for share
     * @param f
     * @param context
     * @param s
     */
    private void emailAttachement(File f, Context context, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
        String name = preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, s); // subject


        String body = "Hi, \n" +
                "\n" +
                //"\n" + name +
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
        emailIntent.setType("application/email");

        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
