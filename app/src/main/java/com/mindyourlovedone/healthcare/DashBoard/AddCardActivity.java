package com.mindyourlovedone.healthcare.DashBoard;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.Connections.RelationActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.CardQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.model.Card;
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

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CARD = 50;
    private static int RESULT_CAMERA_IMAGE1 = 1;
    private static int RESULT_SELECT_PHOTO1 = 2;
    private static int RESULT_CAMERA_IMAGE2 = 3;
    private static int RESULT_SELECT_PHOTO2 = 4;
    private static final int RESULT_INSURANCECard = 269;
    Bitmap PHOTO1 = null, PHOTO2 = null;
    ContentValues values;
    Uri imageUriFront, imageUriBack;
    Context context = this;
    TextView txtName, txttype, txtTitle, txtCard, txtSave,txtOther;
    TextInputLayout tilTitle,tilOther;
    Bitmap bitmap1, bitmap2;
    String imagePathFront = "", imagePathBack = "";
    Card C;
    ImageView imgDone, imgHome, imgBack, imgEdit1, imgEdit2, imgfrontCard, imgBackCard;
    String imagepath = "";//
    String name = "", type = "", oter = "";
    Boolean isEdit = false;
    FrameLayout flFront, flBack;
    int id;

    Preferences preferences;
    DBHelper dbHelper;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    LinearLayout llFrontCam, llBackCam;
    boolean frontFlag, backFlag;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initComponent();
        initImageLoader();
        initUI();
        initListener();
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ins_card)
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

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context, preferences.getString(PrefConstants.CONNECTED_USERDB));
        CardQuery c = new CardQuery(context, dbHelper);
    }

    private void initListener() {
        txtSave.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgEdit1.setOnClickListener(this);
        imgEdit2.setOnClickListener(this);
        llBackCam.setOnClickListener(this);
        llFrontCam.setOnClickListener(this);
        imgfrontCard.setOnClickListener(this);
        imgBackCard.setOnClickListener(this);
    }

    private void initUI() {
        txtCard = findViewById(R.id.txtCard);
        tilOther= findViewById(R.id.tilOther);
        txtOther= findViewById(R.id.txtOther);
        llFrontCam = findViewById(R.id.llFrontCam);
        llBackCam = findViewById(R.id.llBackCam);

        txtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddCardActivity.this, InstructionActivity.class);
                i.putExtra("From", "Card");
                startActivity(i);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        imgDone = findViewById(R.id.imgDone);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        imgEdit1 = findViewById(R.id.imgEdit1);
        imgEdit2 = findViewById(R.id.imgEdit2);
        imgfrontCard = findViewById(R.id.imgFrontCard);
        flFront = findViewById(R.id.flFront);
        flBack = findViewById(R.id.flBack);
        imgBackCard = findViewById(R.id.imgBackCard);


        txtSave = findViewById(R.id.txtSave);
        txtName = findViewById(R.id.txtName);
        txttype = findViewById(R.id.txtType);
        txttype.setFocusable(false);
        tilTitle = findViewById(R.id.tilTitle);
        tilTitle.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilTitle.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });

        Intent i = getIntent();
        if (i.getExtras() != null) {
            txtTitle.setText("Add/Edit Insurance Card");
            if (i.getExtras().getBoolean("IsEdit") == true) {
                isEdit = true;
                tilTitle.setHintEnabled(true);
            }
            Card card = (Card) i.getExtras().getSerializable("CardObject");
            C=(Card) i.getExtras().getSerializable("CardObject");
            txtName.setText(card.getName());
            txttype.setText(card.getType());
            txtOther.setText(card.getOtertype());
            if (card.getType().equalsIgnoreCase("Other"))
            {
                tilOther.setVisibility(View.VISIBLE);
            }
            else
            {
                tilOther.setVisibility(View.GONE);
            }

            id = card.getId();
            String photo = card.getImgFront();
            imagePathFront = photo;
            llFrontCam.setVisibility(View.GONE);

           /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
           imgfrontCard.setImageBitmap(bmp);*/
            File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo);


            if (imgFile.exists()&&!photo.equalsIgnoreCase("")) {
               /* if (imgfrontCard.getDrawable() == null) {
                  //  imgfrontCard.setImageDrawable(null);
                    frontFlag = false;
                    llFrontCam.setVisibility(View.VISIBLE);
                    imgEdit1.setVisibility(View.GONE);
                }
                else {*/
                imgfrontCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                frontFlag = true;
                imgEdit1.setVisibility(View.VISIBLE);
                //}
                // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
            }
            else {
                llFrontCam.setVisibility(View.VISIBLE);
                imgEdit1.setVisibility(View.GONE);
                frontFlag = false;
                //llFrontCam.setVisibility(View.VISIBLE);
            }//new change for default image display

            /*  if (imgFile.exists()) {
             *//* BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),bmOptions);
                Matrix matrix = new Matrix();
                if (bitmap.getHeight()>bitmap.getWidth()) {
                    matrix.postRotate(90);
                }
                else
                {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
                imgfrontCard.setImageBitmap(rotatedBitmap);*//*
                //bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgfrontCard, displayImageOptions);
                frontFlag = true;
            } else {
                frontFlag = false;
                imgfrontCard.setImageDrawable(null);
                llFrontCam.setVisibility(View.VISIBLE);
            }*/
            String photo1 = card.getImgBack();
            imagePathBack = photo1;
            llBackCam.setVisibility(View.GONE);
            File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH), photo1);
            if (imgFile1.exists()&&!photo1.equalsIgnoreCase("")) {
                /*if (imgBackCard.getDrawable() == null) {
                   // imgBackCard.setImageDrawable(null);
                    backFlag = false;
                    llBackCam.setVisibility(View.VISIBLE);
                    imgEdit2.setVisibility(View.GONE);
                }
                else {*/
                imgBackCard.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile1))));
                imgEdit2.setVisibility(View.VISIBLE);
                backFlag = true;
                // }
                // imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)), viewHolder.imgProfile, displayImageOptionsProfile);
            }
            else {
                imgBackCard.setImageDrawable(null);
                imgEdit2.setVisibility(View.GONE);
                backFlag = false;
                llBackCam.setVisibility(View.VISIBLE);
                //imgEdit2.setVisibility(View.VISIBLE);
            }//new change for default image display

           /* Bitmap bmp1 = BitmapFactory.decodeByteArray(photo1, 0, photo1.length);
            imgBackCard.setImageBitmap(bmp1);*/
        }

        txttype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RelationActivity.class);
                i.putExtra("Category", "Insurance");
                i.putExtra("Selected",txttype.getText().toString());
                startActivityForResult(i, RESULT_INSURANCECard);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llFrontCam:

                imgEdit1.performClick();

               /* if (frontFlag == true) {

                }*/
                break;

            case R.id.imgFrontCard:
                if (frontFlag == true) {
                    Intent i = new Intent(context, AddFormActivity.class);
                    i.putExtra("Image", imagePathFront);
                    i.putExtra("FROM", "Insurance");
                    startActivityForResult(i, REQUEST_CARD);
                }
                break;
            case R.id.llBackCam:
                imgEdit2.performClick();
               /* if (backFlag == true) {
                    Intent j = new Intent(context, AddFormActivity.class);
                    j.putExtra("Image", imagePathBack);
                    j.putExtra("FROM", "Insurance");
                    startActivityForResult(j, REQUEST_CARD);
                }*/
                break;
            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
            case R.id.imgBackCard:
                if (backFlag == true) {
                    Intent j = new Intent(context, AddFormActivity.class);
                    j.putExtra("Image", imagePathBack);
                    j.putExtra("FROM", "Insurance");
                    startActivityForResult(j, REQUEST_CARD);
                }
                break;
            case R.id.txtSave:
                //Shradha
                if (validate()) {
                    type = txttype.getText().toString();
                    name = txtName.getText().toString();
                    oter=txtOther.getText().toString();
                    storeImage(PHOTO1, "Front");
                    storeImage(PHOTO2, "Back");

                    if (isEdit == false) {
                        boolean flag = CardQuery.insertInsuranceCardData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, type, imagePathFront, imagePathBack,oter);
                        if (flag) {
                            Toast.makeText(context, "You have added insurance information successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    } else if (isEdit == true) {
                        boolean flag = CardQuery.updateInsuranceCardData(id, name, type, imagePathFront, imagePathBack,oter);
                        if (flag) {
                            Toast.makeText(context, "You have updated insurance information successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(context, "Credentials Saved..", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Invalid Credentials..", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.imgBack:
                type = txttype.getText().toString();
                name = txtName.getText().toString();
                oter=txtOther.getText().toString();

                if(isEdit==false) {
                    if (type.equals("")&&name.equals("")&&
                            oter.equals("")&&imagePathBack.equals("")&&
                            imagePathFront.equals(""))
                    {
                        finish();
                    }
                    else{
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
                else{

                    if (C.getType().equals(type)&&C.getName().equals(name)&&
                            C.getOtertype().equals(oter)&&C.getImgFront().equals(imagePathFront)&&
                            C.getImgBack().equals(imagePathBack))
                    {
                        finish();
                    }
                    else{
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
            case R.id.imgEdit1:
                showDialogs(RESULT_CAMERA_IMAGE1, RESULT_SELECT_PHOTO1, "Front");

                break;
            case R.id.imgEdit2:
                showDialogs(RESULT_CAMERA_IMAGE2, RESULT_SELECT_PHOTO2, "Back");

//                dialogCameraBack();

                break;


        }
    }

    //shradha
    private boolean validate() {
        String name = txtName.getText().toString().trim();
        String type = txttype.getText().toString().trim();
        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (type.equals("")) {
            txttype.setError("Please Enter Type");
            DialogManager.showAlert("Please Enter Type", context);
        } else {
            return true;
        }
        return false;
    }

    private void dialogCameraFront(final int resultCameraImage, final int resultSelectPhoto, final String from) {
        final Dialog dialogCamera = new Dialog(context);
        dialogCamera.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCamera.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_camera_ins, null);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);


        dialogCamera.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogCamera.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogCamera.getWindow().setAttributes(lp);
        dialogCamera.setCanceledOnTouchOutside(false);
        dialogCamera.show();


        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (from.equals("Front")) {
                    imageUriFront = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFront);
                } else if (from.equals("Back")) {
                    imageUriBack = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriBack);
                }
                startActivityForResult(intent, resultCameraImage);
//                showDialogs(RESULT_CAMERA_IMAGE1, RESULT_SELECT_PHOTO1, "Front");
                dialogCamera.dismiss();
            }
        });
    }

    private void dialogCameraBack() {
        final Dialog dialogCamera = new Dialog(context);
        dialogCamera.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCamera.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_camera_ins, null);
        final TextView txtOk = dialogview.findViewById(R.id.txtOk);


        dialogCamera.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogCamera.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogCamera.getWindow().setAttributes(lp);
        dialogCamera.setCanceledOnTouchOutside(false);
        dialogCamera.show();


        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogs(RESULT_CAMERA_IMAGE2, RESULT_SELECT_PHOTO2, "Back");
                dialogCamera.dismiss();
            }
        });
    }

/*
    private void dialogCameraInstruction() {

    }
*/

    private void showDialogs(final int resultCameraImage, final int resultSelectPhoto, final String from) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_gender1, null);
        final TextView textOption1 = dialogview.findViewById(R.id.txtOption1);
        final TextView textOption2 = dialogview.findViewById(R.id.txtOption2);
        final TextView textOption3 = dialogview.findViewById(R.id.txtOption3);
        TextView textCancel = dialogview.findViewById(R.id.txtCancel);
        textOption1.setText("Take Picture");
        textOption2.setText("Gallery");
        textOption3.setText("Remove Picture");
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
            @Override
            public void onClick(View v) {
                //shradha for back and front capture image from camera and gallery
                if (from.equals("Front")) {
                    dialogCameraFront(RESULT_CAMERA_IMAGE1, RESULT_SELECT_PHOTO1, "Front");
                } else if (from.equals("Back")) {
                    dialogCameraFront(RESULT_CAMERA_IMAGE2, RESULT_SELECT_PHOTO2, "Back");
                }

              /*  values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (from.equals("Front")) {
                    imageUriFront = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFront);
                } else if (from.equals("Back")) {
                    imageUriBack = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriBack);
                }
                startActivityForResult(intent, resultCameraImage);
              */  // dispatchTakePictureIntent(resultCameraImage);
                dialog.dismiss();
            }
        });
        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, resultSelectPhoto);
                dialog.dismiss();
            }
        });
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("Front")) {
                    // flFront.setBackgroundResource(R.drawable.ins_card);
                    imgfrontCard.setImageDrawable(null);
                    llFrontCam.setVisibility(View.VISIBLE);
                    imgEdit1.setVisibility(View.GONE);

                    imagePathFront = "";
                    frontFlag = false;
                    PHOTO1 = null;
                } else if (from.equals("Back")) {
                    imagePathBack = "";
                    //flBack.setBackgroundResource(R.drawable.ins_card);
                    imgBackCard.setImageDrawable(null);
                    llBackCam.setVisibility(View.VISIBLE);
                    backFlag = false;
                    imgEdit2.setVisibility(View.GONE);
                    PHOTO2 = null;
                }
                dialog.dismiss();
            }
        });
    }

    private void dispatchTakePictureIntent(int resultCameraImage) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               /* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.infidigi.fotobuddies.fileprovider",
                        photoFile);*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, resultCameraImage);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagepath = image.getAbsolutePath();
        return image;
    }

    private String getOrientation(Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String orientation = "No result";
        try {
            String image = new File(uri.getPath()).getAbsolutePath();
            BitmapFactory.decodeFile(image, options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            if (imageHeight > imageWidth) {
                orientation = "portrait";
            } else {
                orientation = "landscape";
            }
        } catch (Exception e) {
            //Do nothing
        }
        return orientation;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imgfrontCard = findViewById(R.id.imgFrontCard);
        ImageView imgBackCard = findViewById(R.id.imgBackCard);
        if (requestCode == RESULT_SELECT_PHOTO1 && null != data) {
            frontFlag = true;
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //  profileImage.setImageBitmap(selectedImage);

                if (getOrientation(imageUri).equalsIgnoreCase("landscape")) {//nikita
                    Toast.makeText(AddCardActivity.this, "Landscape image", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCardActivity.this, "Portrait image", Toast.LENGTH_SHORT).show();
                }
                /*shradha uncommented imageLoader line code*/
                imageLoader.displayImage(String.valueOf(imageUri), imgfrontCard, displayImageOptions);
                Matrix matrix = new Matrix();
                if (selectedImage.getHeight() > selectedImage.getWidth()) {
                    matrix.postRotate(90);
                } else {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(selectedImage, 0, 0, selectedImage.getWidth(), selectedImage.getHeight(), matrix, true);
                imgfrontCard.setImageBitmap(rotatedBitmap);
                llFrontCam.setVisibility(View.GONE);
                imgEdit1.setVisibility(View.VISIBLE);
                PHOTO1 = rotatedBitmap;
                storeImage(rotatedBitmap,"Front");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
          /*  try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bitmap1=selectedImage;
                imgfrontCard.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        } else if (requestCode == RESULT_CAMERA_IMAGE1) {
            frontFlag = true;
            try {

                if (getOrientation(imageUriFront).equalsIgnoreCase("landscape")) {//nikita
                    Toast.makeText(AddCardActivity.this, "Landscape image", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCardActivity.this, "Portrait image", Toast.LENGTH_SHORT).show();
                }

                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriFront);
                // String imageurl = getRealPathFromURI(imageUriFront);
                // Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
                //     imageLoader.displayImage(String.valueOf(imageUriFront),imgfrontCard,displayImageOptions);
                Matrix matrix = new Matrix();
                if (thumbnail.getHeight() > thumbnail.getWidth()) {
                    matrix.postRotate(90);
                } else {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
                imgfrontCard.setImageBitmap(rotatedBitmap);
                llFrontCam.setVisibility(View.GONE);
                imgEdit1.setVisibility(View.VISIBLE);
                PHOTO1 = rotatedBitmap;
                storeImage(rotatedBitmap,"Front");
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgfrontCard.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWInsurance/");
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                file.mkdirs();
            }


            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    new File(file, children[i]).delete();
                }
            }
            try {

                imagepath = path + "/MHCWInsurance_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
*/

        } else if (requestCode == RESULT_SELECT_PHOTO2 && null != data) {
            backFlag = true;
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                if (getOrientation(imageUri).equalsIgnoreCase("landscape")) {//nikita
                    Toast.makeText(AddCardActivity.this, "Landscape image", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCardActivity.this, "Portrait image", Toast.LENGTH_SHORT).show();
                }

                //  profileImage.setImageBitmap(selectedImage);
                /*Code added for setting back card image */
                imageLoader.displayImage(String.valueOf(imageUri), imgBackCard, displayImageOptions);
                Matrix matrix = new Matrix();
                if (selectedImage.getHeight() > selectedImage.getWidth()) {
                    matrix.postRotate(90);
                } else {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(selectedImage, 0, 0, selectedImage.getWidth(), selectedImage.getHeight(), matrix, true);
                imgBackCard.setImageBitmap(rotatedBitmap);
                llBackCam.setVisibility(View.GONE);
                imgEdit2.setVisibility(View.VISIBLE);
                PHOTO2 = rotatedBitmap;
                /*shradha*/
                storeImage(rotatedBitmap, "Back");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == RESULT_CAMERA_IMAGE2) {
            backFlag = true;
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriBack);

                if (getOrientation(imageUriBack).equalsIgnoreCase("landscape")) {//nikita
                    Toast.makeText(AddCardActivity.this, "Landscape image", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCardActivity.this, "Portrait image", Toast.LENGTH_SHORT).show();
                }

                // String imageurl = getRealPathFromURI(imageUriBack);
                // Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
                // imageLoader.displayImage(String.valueOf(imageUriBack),imgBackCard,displayImageOptions);
                Matrix matrix = new Matrix();
                if (thumbnail.getHeight() > thumbnail.getWidth()) {
                    matrix.postRotate(90);
                } else {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
                imgBackCard.setImageBitmap(rotatedBitmap);
                llBackCam.setVisibility(View.GONE);
                imgEdit2.setVisibility(View.VISIBLE);
                // profileImage.setImageBitmap(bitmap);
                storeImage(rotatedBitmap,"Back");
                PHOTO2 = rotatedBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imgBackCard.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWInsurance/");
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                file.mkdirs();
            }


            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    new File(file, children[i]).delete();
                }
            }
            try {

                imagepath = path + "/MHCWInsurance_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }

*/
        }
        else if (requestCode == RESULT_INSURANCECard && data != null) {
            type = data.getStringExtra("Category");
            txttype.setText(type);
            if (type.equals("Other")) {
                tilOther.setVisibility(View.VISIBLE);
            } else {
                tilOther.setVisibility(View.GONE);
                txtOther.setText("");
            }
        }

    }

    private void storeImage(Bitmap selectedImage, String profile) {
        FileOutputStream outStream = null;
        File file = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            if (selectedImage != null) {
                if (profile.equals("Front")) {
                    imagePathFront = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + imagePathFront);
                } else if (profile.equals("Back")) {
                    imagePathBack = "MYLO_" + String.valueOf(System.currentTimeMillis())
                            + ".jpg";
                    // Write to SD Card
                    outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH) + imagePathBack);
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private String getRealPathFromURI(Uri imageUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(imageUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private Bitmap imageOreintationValidator(Bitmap bitmap, String path) {

        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private Bitmap rotateImage(Bitmap source, int angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }
}
