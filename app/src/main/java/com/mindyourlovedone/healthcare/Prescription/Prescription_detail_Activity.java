/*
 * * Created by Amit on 5/10/2020.
 */

package com.mindyourlovedone.healthcare.Prescription;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.customview.TouchImageView;
import com.mindyourlovedone.healthcare.database.DBHelper;
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
import java.util.Calendar;
import java.util.Random;

public class Prescription_detail_Activity extends AppCompatActivity implements View.OnClickListener {


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


    //header & ueser fields
    private TextView txt_Titles, txtEdit, txtSave, tv_UserNane;
    private ImageView imgBacks, imgDones, imgDots;
    //views of table layout
    private TextView archive_Text, archive_Date;
    //table row_1
    private EditText edt_RXName, edt_DOU, edt_Feq, edt_Dose, edt_Malady;
    //table row_2
    private EditText edt_Doctor, edt_Date, edt_Pharmacy;
    //table row_3
    private TableRow taleRow_3;
    private Switch switch1;
    //table row_4
    private EditText edt_Notes;
    //table row_5
    //to add snap of prescription to the the list(horizontal)
    private ImageView imgAddPic;
    private HorizontalScrollView scroll_PrescriptionImg;
    private LinearLayout linear_ListPrescImg;
    private ImageView img_PrspImg;
    private TextView tv_PrspImgDate, tv_AddPic;
    String imagepath = "";

    ArrayList<PrescribeImage> imageList = new ArrayList<>();
    ArrayList<PrescribeImage> imageListOld = new ArrayList<>();

    //table row_6
    // for btn (archive , delete , copy and the archive_date)
    private LinearLayout linear_archive, linear_copy, linear_delete, linear_DatePicker;
    //date picker used in liner_DatePicker
    private DatePickerDialog datePickerDialog;
    // the custom dialog for the above btn
    private Dialog dialog;
    private TextView tv_CancelCopy, tv_CancelDelete, tv_CancelArchive, tv_Delete, tv_Copy, tv_Archive;
    private TextView tv_archive_date;


    String pre = "";


    Preferences preferences;
    DBHelper dbHelper;
    int unique;
    boolean isEdit, isView;
    int id, colid;

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_detail);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //for image list.
        initImageLoader();
        //Initialize user interface view and components.
        inUI();
        //Register a callback to be invoked when this views are clicked.
        initListener();
        //invocking the btn functionality of btn when they r clicked.
        btnListener();

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
                //.imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
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


    private void inUI() {

        txt_Titles = findViewById(R.id.txt_Titles);
        savetitle();

        // txtEdit = findViewById(R.id.txtEdit);
        txtSave = findViewById(R.id.txtSave);
        txt_Titles = findViewById(R.id.txt_Titles);
        tv_UserNane = findViewById(R.id.tv_UserNane);
        imgBacks = findViewById(R.id.imgBacksss);
        imgDones = findViewById(R.id.imgDones);
        imgDots = findViewById(R.id.imgDots);

        archive_Text = findViewById(R.id.archive_Text);
        archive_Date = findViewById(R.id.archive_Date);

        edt_RXName = findViewById(R.id.etv_RXName);
        edt_DOU = findViewById(R.id.edt_DOU);
        edt_Feq = findViewById(R.id.edt_Feq);
        edt_Dose = findViewById(R.id.edt_Dose);
        edt_Malady = findViewById(R.id.edt_Malady);
        edt_Doctor = findViewById(R.id.edt_Doctor);
        edt_Date = findViewById(R.id.edt_Date);
        edt_Pharmacy = findViewById(R.id.edt_Pharmacy);

        taleRow_3 = findViewById(R.id.taleRow_3);
        switch1 = findViewById(R.id.switch1);

        edt_Notes = findViewById(R.id.edt_Notes);

        imgAddPic = findViewById(R.id.imgAddPic);
        tv_AddPic = findViewById(R.id.tv_AddPic);
        scroll_PrescriptionImg = findViewById(R.id.scroll_PrescriptionImg);
        linear_ListPrescImg = findViewById(R.id.linear_ListPrescImg);
        img_PrspImg = findViewById(R.id.img_PrspImg);
        tv_PrspImgDate = findViewById(R.id.tv_PrspImgDate);


        linear_archive = findViewById(R.id.linear_archive);
        linear_copy = findViewById(R.id.linear_copy);
        linear_delete = findViewById(R.id.linear_delete);


    }


    private void initListener() {

        txtSave.setOnClickListener(this);
        imgBacks.setOnClickListener(this);
        imgAddPic.setOnClickListener(this);
        tv_AddPic.setOnClickListener(this);
        edt_Date.setOnClickListener(this);

        // for the switch , if region selected is india vis= gone or else visible
        if (preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India))) {
            taleRow_3.setVisibility(View.GONE);
        } else {
            taleRow_3.setVisibility(View.VISIBLE);
        }
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    pre = "YES";
                else
                    pre = "NO";
            }
        });

    /*   //demo for the UI flow
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Prescription_detail_Activity.this, Prescription_detail_Activity.class);
                txtEdit.setText("Save");
                Prescription_detail_Activity.this.setIntent(intent);
                txtEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Prescription_detail_Activity.this, Prescription_Information_Activity.class);
                        Prescription_detail_Activity.this.startActivity(intent);
                        finish();
                    }
                });
            }
        });
//foe back to main prescription list
        imgBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(Prescription_detail_Activity.this, Prescription_Information_Activity.class);
                Prescription_detail_Activity.this.startActivity(intent_back);
                finish();
            }
        });
        imgAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Prescription_detail_Activity.this, "Will be Updated soon with other options", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }


    private void savetitle() {
        Intent i = getIntent();
        if (i.getExtras() != null) {

            Prescription p = (Prescription) i.getExtras().getSerializable("PrescriptionObject");
            presc = (Prescription) i.getExtras().getSerializable("PrescriptionObject");

            isView = i.getExtras().getBoolean("IsView");
            if (isView == true) {
                disablePrescription();
                txt_Titles.setText("PRESCRIPTIONS");
                id = p.getUnique();
                uniqID = p.getUnique();
                userid = p.getUserid();
                colid = p.getId();
                edt_Doctor.setText(p.getDoctor());
                edt_Date.setText(p.getDates());
                edt_Notes.setText(p.getNote());
                if (p.getPre().equals("YES")) {
                    switch1.setChecked(true);
                } else if (p.getPre().equals("NO")) {
                    switch1.setChecked(false);
                }
                edt_RXName.setText(p.getRX());
                edt_Malady.setText(p.getPurpose());
                edt_Dose.setText(p.getDose());
                edt_Feq.setText(p.getFrequency());
                edt_Pharmacy.setText(p.getMedicine());
                imageList = PrescribeImageQuery.fetchAllImageRecord(p.getUserid(), p.getUnique());
                imageListOld = imageList;
                setImageListData();
            }


            isEdit = i.getExtras().getBoolean("IsEdit");
            if (isEdit == true) {
                txt_Titles.setText("Edit Prescription");
                id = p.getUnique();
                uniqID = p.getUnique();
                userid = p.getUserid();
                colid = p.getId();
                edt_Doctor.setText(p.getDoctor());
                edt_Date.setText(p.getDates());
                edt_Notes.setText(p.getNote());
                if (p.getPre().equals("YES")) {
                    switch1.setChecked(true);
                } else if (p.getPre().equals("NO")) {
                    switch1.setChecked(false);
                }
                edt_RXName.setText(p.getRX());
                edt_Malady.setText(p.getPurpose());
                edt_Dose.setText(p.getDose());
                edt_Feq.setText(p.getFrequency());
                edt_Pharmacy.setText(p.getMedicine());
                imageList = PrescribeImageQuery.fetchAllImageRecord(p.getUserid(), p.getUnique());
                imageListOld = imageList;
                // setDosageData();
                setImageListData();
            } else {
                txt_Titles.setText("Add Prescription");
            }
        }


    }

    

    private void disablePrescription() {
        /*txtPhotoHeader.setVisibility(View.GONE);
        imgAddPhoto.setVisibility(View.GONE);
        txtAddPhoto.setVisibility(View.GONE);
        txtSave.setEnabled(false);
        txtName.setEnabled(false);
        txtDate.setEnabled(false);
        etNote.setEnabled(false);
        tbPre.setEnabled(false);
        txtRX.setEnabled(false);
        txtPurpose.setEnabled(false);
        txtDose.setEnabled(false);
        txtFrequency.setEnabled(false);
        txtMedicine.setEnabled(false);*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // Navgate to previous screen after saving changes
            case R.id.imgBacks:
                String doctors = edt_Doctor.getText().toString().trim();
                String purposes = edt_Malady.getText().toString().trim();
                String notes = edt_Notes.getText().toString().trim();
                String dates = edt_Date.getText().toString().trim();
                String rxs = edt_RXName.getText().toString().trim();
                String doses = edt_Dose.getText().toString().trim();
                String frequencys = edt_Feq.getText().toString().trim();
                String medicines = edt_Pharmacy.getText().toString().trim();
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
                }
                break;


//Save Prescription details
            case R.id.txtSave:
                String doctor = edt_Doctor.getText().toString().trim();
                String purpose = edt_Malady.getText().toString().trim();
                String note = edt_Notes.getText().toString().trim();
                String date = edt_Date.getText().toString().trim();
                String rx = edt_RXName.getText().toString().trim();
                String dose = edt_Dose.getText().toString().trim();
                String frequency = edt_Feq.getText().toString().trim();
                String medicine = edt_Pharmacy.getText().toString().trim();
                String dirForUse = edt_DOU.getText().toString().trim();
                if (rx.equals("")) {
                    Toast.makeText(context, "Please enter Name of Medication/Supplement", Toast.LENGTH_SHORT).show();
                    edt_RXName.setError("Please enter Name of Medication/Supplement");
                } else {
                    ArrayList<Dosage> dosageList = null;
                    if (isEdit == false) {
                        unique = generateRandom();

                        Boolean flag = PrescriptionQuery.insertPrescriptionData(preferences.getInt(PrefConstants.CONNECTED_USERID), doctor, purpose, note, date,  dosageList, imageList, unique, pre, rx, dose  , frequency, medicine);

                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("Add_PrescriptionInformation", 1);
                            mFirebaseAnalytics.logEvent("OnClick_Save_PrescriptionInformation", bundle);

                            Toast.makeText(context, "Prescription has been saved succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(Prescription_detail_Activity.this);
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Boolean flag = PrescriptionQuery.updatePrescriptionData(colid, uniqID, doctor, purpose, note, date, dosageList , imageList, preferences.getInt(PrefConstants.CONNECTED_USERID), pre, rx, dose, frequency, medicine, imageListOld);
                        if (flag == true) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("Edit_PrescriptionInformation", 1);
                            mFirebaseAnalytics.logEvent("OnClick_Save_PrescriptionInformation", bundle);

                            Toast.makeText(context, "Prescription has been updated succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(Prescription_detail_Activity.this);
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Intent i = new Intent();
                    setResult(RESULT_PRES, i);
                    finish();
                }
                break;

            case R.id.imgAddPic:
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
     *  selectedImage
     *  profile
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

        linear_ListPrescImg.removeAllViews();
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

                linear_ListPrescImg.addView(clickableColumn);
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

                    AlertDialog.Builder alert = new AlertDialog.Builder(Prescription_detail_Activity.this);
                    alert.setTitle("Email ?");
                    alert.setMessage("Do you want to email prescription ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File f = new File(imgFile.getAbsolutePath());
                            emailAttachement(f, Prescription_detail_Activity.this, "Prescription Photo");
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
    
    
    
    

    // this is for the last 3 btn of the current page
    private void btnListener() {
        // popup dialog for the 3 button Archive,Copy,Delete
        dialog = new Dialog(this);
        linear_archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showArchivePopup();
            }
        });
        linear_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopup();
            }
        });
        linear_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCopyPopup();
            }
        });
    }


    private void showCopyPopup() {
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setContentView(R.layout.popup_copy);
        tv_CancelCopy = dialog.findViewById(R.id.tv_CancelCopy);
        tv_Copy = dialog.findViewById(R.id.tv_Copy);

        tv_CancelCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Prescription_detail_Activity.this, "Back to list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Prescription_detail_Activity.this, Prescription_List_Activity.class);
                Prescription_detail_Activity.this.startActivity(intent);
                finish();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showDeletePopup() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setContentView(R.layout.popup_delete);
        tv_CancelDelete = dialog.findViewById(R.id.tv_CancelDelete);
        tv_Delete = dialog.findViewById(R.id.tv_Delete);

        tv_CancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Prescription_detail_Activity.this, "Back to list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Prescription_detail_Activity.this, Prescription_List_Activity.class);
                Prescription_detail_Activity.this.startActivity(intent);
                finish();
            }
        });
    }

    private void showArchivePopup() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setContentView(R.layout.popup_archive);
        tv_CancelArchive = dialog.findViewById(R.id.tv_CancelArchive);
        tv_Archive = dialog.findViewById(R.id.tv_Archive);
        linear_DatePicker = dialog.findViewById(R.id.linear_DatePicker);
        tv_archive_date = dialog.findViewById(R.id.tv_archive_date);


        final Calendar calendar = Calendar.getInstance();

        linear_DatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Prescription_detail_Activity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        // set day of month , month and year value in the edit text
                        tv_archive_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }

        });


        tv_CancelArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        tv_Archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Prescription_detail_Activity.this, "Back to list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Prescription_detail_Activity.this, Prescription_List_Activity.class);
                Prescription_detail_Activity.this.startActivity(intent);
                finish();
            }
        });


    }


}
