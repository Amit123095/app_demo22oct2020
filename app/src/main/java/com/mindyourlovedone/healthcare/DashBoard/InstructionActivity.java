package com.mindyourlovedone.healthcare.DashBoard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.utility.UIEmails;

/**
 * Class: UserInsActivity
 * Screen: Display Instruction
 * A class that manages to display all screen instructions
 * implements OnclickListener for onClick event on views
 */
public class InstructionActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout titleHeader;
    ImageView imgBack, imgPicture, txtEmail;
    TextView txtHeader, txtTitle, txtMsg;
    String From;
    ImageView imgDot;
    Context context = this;
    String UI = "";
    UIEmails ui = new UIEmails();
    FloatingActionButton floatProfile;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        preferences=new Preferences(context);

        initUI();
        //Register a callback to be invoked when this views are clicked.
        initListener();
    }

    /**
     * Function: Register a callback to be invoked when this views are clicked.
     * If this views are not clickable, it becomes clickable.
     */
    private void initListener() {
        txtEmail.setOnClickListener(this);
        floatProfile.setOnClickListener(this);

    }

    /**
     * Function:Initialize UI and View
     */
    private void initUI() {
        //shradha
        floatProfile = findViewById(R.id.floatProfile);
        final RelativeLayout relMsg = findViewById(R.id.relMsg);
        TextView txt61 = findViewById(R.id.txtPolicy61);
        TextView txt62 = findViewById(R.id.txtPolicy62);
        TextView txt622 = findViewById(R.id.txtPolicy622);
        TextView txt63 = findViewById(R.id.txtPolicy63);
        TextView txt64 = findViewById(R.id.txtPolicy64);
        TextView txt65 = findViewById(R.id.txtPolicy65);
        TextView txt66 = findViewById(R.id.txtPolicy66);
        TextView txt67 = findViewById(R.id.txtPolicy67);
        TextView txt68 = findViewById(R.id.txtPolicy68);

        ImageView img61 = findViewById(R.id.img61);
        ImageView img62 = findViewById(R.id.img62);
        ImageView img622 = findViewById(R.id.img622);
        ImageView img63 = findViewById(R.id.img63);
        ImageView img64 = findViewById(R.id.img64);
        ImageView img65 = findViewById(R.id.img65);
        ImageView img66 = findViewById(R.id.img66);
        ImageView img67 = findViewById(R.id.img67);
        ImageView img68 = findViewById(R.id.img68);

        RelativeLayout rel622 = findViewById(R.id.rel622);

        RelativeLayout rel69 = findViewById(R.id.rel69);
        RelativeLayout rel71 = findViewById(R.id.rel71);
        RelativeLayout rel72 = findViewById(R.id.rel72);
        RelativeLayout rel73 = findViewById(R.id.rel73);

        TextView txtPolicy69 = findViewById(R.id.txtPolicy69);
        TextView txtPolicy71 = findViewById(R.id.txtPolicy71);
        TextView txtPolicy72 = findViewById(R.id.txtPolicy72);
        TextView txtPolicy73 = findViewById(R.id.txtPolicy73);

        titleHeader = findViewById(R.id.header);
        imgBack = findViewById(R.id.imgBack);
        imgPicture = findViewById(R.id.imgPicture);
        txtHeader = findViewById(R.id.txtHeader);
        txtTitle = findViewById(R.id.txtTitle);
        txtEmail = findViewById(R.id.txtEmail);
        txtMsg = findViewById(R.id.txtMsg);
        imgDot = findViewById(R.id.imgDot);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            From = i.getExtras().getString("From");
        }
        switch (From) {

            case "Dropbox":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.VISIBLE);

                //nikita
                String msgg = "<b>Guidelines:</b>" +
                        "<br>" +
                        "<b>1) Login: </b>" +
                        "<br>" +
                        "Click on <b>\"Login With DropBox\" </b>button, It will redirect you to dropbox site. Now you can login with your personal dropbox account." +
                        "<br>" +
                        "After logging in, To allow the access to the files and folders from your dropbox, Click the <b>'Allow'</b>  Button.So now you can Access your files." +
                        "<br>" +
                        "<b>2) Backup: </b>" +
                        "<br>" +
                        "If you have logged in to dropbox from application, It will show you Backup Options." +
                        "<br>" +
                        "On clicking  <b>'BACKUP'</b> button, you need to Click on <b>'Backup your Data'</b> button to store your profile." +
                        "<br>" +
                        "<b>3) Restore: </b>" +
                        "<br>" +
                        "If you have logged in to dropbox from application, It will show you Restore Options. It will help to restore profiles." +
                        "<br>" +
                        "On clicking  <b>'RESTORE'</b>  button, you will get list of zip files for download. On clicking any of the zip file, It will be downloaded and you will get a popup message  to unzip and restore file." +
                        "<br>" +
                        "<b>4) Get Files: </b>" +
                        "<br>" +
                        "If you have logged in to dropbox from application, It will show you View Files Options. It will help you to get the documents in Advance Directive section  from dropbox storage." +
                        "<br>" +
                        "On clicking <b>'VIEW FILES'</b> button, you will get list of documents files for download. On clicking any of the document  file, It will be downloaded ." +
                        "<br>" +
                        "Now you can add this document  to your application by clicking on <b>'ADD FILES'</b> button.";
                //nikita
                txt61.setText(Html.fromHtml("This is a method for personal cloud storage. It is commonly referred to as a method to backup one's files online. In addition to store the files, one can also share them with colleagues and friends, all using Dropbox. To store, retrieve and manage your files, one can use a Dropbox application."));
                txt62.setText(Html.fromHtml("You need to login with your dropbox account. If you don't  have dropbox account, You need to have one. "));
                txt63.setText(Html.fromHtml(msgg));

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                txt64.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);

                txtHeader.setText("Dropbox");
                break;
            case "PrescriptionUploadInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorPrescriptionGray));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to upload a list of prescriptions for themselves or their loved ones.  The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("<b>Add a Document.</b> Click the Plus icon on the bottom right corner of the screen. Click the ATTACH FILE icon at the top of the screen and select the location of the file.  Choose the file. To edit the document, click the blue Pencil icon and upload a new document or delete the entire entry (see Delete Entry below)."));
                txt63.setText(Html.fromHtml("<b>Add a Document from your Email.</b> If the document is in your email, go to your email account and choose the email with the file you want to upload. Click on the attachment and open it. After it opens, click the forward icon and scroll to find MYLO.  Choose the specific profile and section for the upload.  For more information consult the “How-to Video” found in the Menu Bar."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click the SAVE icon on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Summary Data Screen.</b> After a document is stored the User will be brought back to the Summary screen. The User can view, print or email a Summary Report by clicking the green icon on the bottom of the screen. (The specific document can only be viewed or shared if working inside the cell – see View Document below)."));
                txt66.setText(Html.fromHtml("<b>Delete Entry.</b> From the Summary screen swipe right to left on applicable cell of the document."));
                txt67.setText(Html.fromHtml("<b>Edit Entry.</b> From the Summary screen, click on the cell of the document and make changes by clicking the blue Pencil icon. To save your edits click SAVE on the top right corner of the screen."));
                txt68.setText(Html.fromHtml("<b>View Document.</b> Click the green circle located on the bottom of the screen and then click View Document."));
                txtPolicy69.setText(Html.fromHtml("<b>Email or Fax Document.</b> Click the green circle located on the bottom of the screen and then click Email or Fax Document."));
                txtPolicy71.setText(Html.fromHtml("<b>Faxing a Document.</b> Users should only send documents via fax to meet an organizations’ HIPAA requirements (e.g. health, insurance).  Users can receive notification that the fax was received by including a Reply Email Address. The fax will be sent immediately, but the machine you are sending to may be turned off or very busy.  If a reply is not received within 20 minutes, check the fax number and resend. "));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Prescription List Upload");
                break;
            case "PrescriptionInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorPrescriptionGray));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                //shradha
                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to store information about their Prescriptions and those of their loved ones. Users can also take photos of the prescription and the pills."));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. The minimum amount of information you need to complete this section is the name of the medication or supplement and the frequency of use."));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text. You may use your phone’s microphone to add certain data verbally."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click the SAVE icon on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Photo of Prescription/Pills:</b> To add a Photo click the blue Plus icon and follow directions (take picture or pull a photo from your gallery).  Users can store more than one photo in this section. To edit the photo, click the blue Pencil icon and follow the directions on the screen."));
                txt66.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a summary screen.  To edit information, click the cell of the prescription. Make changes and then click SAVE on the top right corner of the screen. Clicking SAVE, takes Users back to the Summary page. "));
                txt67.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txt68.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input, print or email Summary Reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>"
                ).toString();

                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);


                txtTitle.setText("User Instructions");
                txtHeader.setText("Prescription Information");
                break;

            case "VitalInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                //shradha
                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep a Summary of Vital Signs and monitor important changes. Users choose which Vital Sign they want to store. The Summary screen includes the most commonly tracked: Blood Pressure, Heart Rate, and Temperature, (BP, HR, TEMP)."));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. Data is entered via free text. You can use your phone’s microphone to add certain data verbally.  We recommend adding the location, date and time of the test. "));
                txt63.setText(Html.fromHtml("<b>Save Input.</b> SAVE on the top right corner of the screen."));
                txt64.setText(Html.fromHtml("<b>Edit Vital Signs.</b> Edit the Vital Signs by clicking the green Down Arrow on the right of the entry. This will show the edit icon, click it to edit the Vital Sign. To save your edits click the SAVE icon at the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Delete Vital Signs.</b> To delete the entry swipe left (right to left) the summary cell. This will bring up the Delete icon."));
                txt66.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input, print or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>"
                ).toString();

                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);


                txtTitle.setText("User Instructions");
                txtHeader.setText("Vital Signs");
                break;

            case "FormInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSkyBlue));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep a copies of insurance forms. The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("<b>Add a Form.</b> Click the Plus icon on the bottom right corner of the screen. \n" +
                        "Click the ATTACH FILE icon at the top of the screen and select the location of the file.  Choose the file.  To edit the document, click the blue Pencil icon and upload a new document or delete the entire entry (see Delete Entry below).\n"));
                txt63.setText(Html.fromHtml("<b>Add a Document from your Email.</b> If the document is in your email, go to your email account and choose the email with the file you want to upload. Click on the attachment and open it. After it opens, click the forward icon and scroll to find MYLO.  Choose the specific profile and section for the upload.  For more information consult the “How-to Video” found in the Menu Bar."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Summary Data Screen.</b> After a document is stored the User will be brought to the Summary Screen. The User can view, print or email a Summary Report of all documents stored in the section by clicking the green icon on the bottom of the screen. (The specific document can only be viewed or shared if working inside the cell – see View Document below)."));
                txt66.setText(Html.fromHtml("<b>Delete Entry.</b> From Summary screen swipe right to left on applicable cell of the document."));
                txt67.setText(Html.fromHtml("<b>Edit Entry.</b>  From the Summary screen, click on the cell of the document and make changes by clicking the blue Pencil icon. To save your edits click SAVE on the top right corner of the screen."));
                txt68.setText(Html.fromHtml("<b>View Document.</b> .  Click the green circle located on the bottom of the screen and then click View Document."));
                txtPolicy69.setText(Html.fromHtml("<b>Email or Fax Document.</b> Click the green circle located on the bottom of the screen and then click Email or Fax Document."));
                txtPolicy71.setText(Html.fromHtml("<b>Faxing a Document.</b> Users should only send documents via fax to meet an organizations’ HIPAA requirements (e.g. health, insurance).  Users can receive notification that the fax was received by including a Reply Email Address. The fax will be sent immediately, but the machine you are sending to may be turned off or very busy.  If a reply is not received within 20 minutes, check the fax number and resend. "));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Insurance Forms");
                break;
            case "CardInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSkyBlue));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                //shradha
                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to maintain photos of their Insurance Cards and those of their loved ones."));
                txt62.setText(Html.fromHtml("<b>Add a Card.</b> Click the Plus icon on the bottom right corner of the screen. Enter the name of the Provider and Type of Insurance and click SAVE on the top right corner of the screen."));
                txt63.setText(Html.fromHtml("<b>Picture of Insurance Card.</b> To take a picture of an insurance card (front and back). Click the ADD CARD icon. It is recommended that you hold your phone horizontal when taking a picture of the card. Once saved, click on the picture of the card for a closer view. Use the share function on the top right of the screen to send via email. Arrow back to get to the Summary screen. To edit the picture of the card, click the blue Pencil icon."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Edit Entry.</b> To edit information, click the cell of the added card. To save edits click SAVE again."));
                txt66.setText(Html.fromHtml("<b>Delete Entry.</b> To delete the entry swipe right to left on the cell of the card."));
                txt67.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input, print or email Summary Reports by clicking the green circle located on the bottom of the summary screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Insurance Cards");
                break;

            case "InsuranceInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSkyBlue));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep Insurance Coverage information for themselves and their loved ones."));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. If the company or person is in your Contacts click the option - “Add from Contacts”. The minimum amount of information you need to complete this section is the name of the entity, type of insurance, and phone number (including type e.g. mobile, home, office or fax).  You can add more than one phone number by clicking the blue Plus icon."));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open, and you can add your information. Business cards can also be added."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Profile Photo:</b> To add a Photo, click anywhere in the circle, choose ”Take picture” or “Gallery”, select the photo you want, and then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt66.setText(Html.fromHtml("<b>Adding a Business Card.</b> The last entry on the screen allows the User to add a business card. Click ADD CARD and further instructions will pop up.  Click SAVE on the top right corner of the screen when you are done.  To forward the card, click on the card and click the Share icon on the top right of the screen. To edit the card, click the Pencil icon and follow the directions on the screen."));
                txt67.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a Summary screen.  To edit information, click the cell of the contact, make changes and then click SAVE on the top right corner of the screen. Clicking SAVE returns the User back to the Summary page. "));
                txt68.setText(Html.fromHtml("<b>Automated Phone Calls.</b> From the Summary screen, click the Phone icon."));
                txtPolicy69.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txtPolicy71.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                //txtPolicy72.setText(Html.fromHtml("<b>To view a report or to email the data</b> click the green circle with the three dots located on the left side of the screen. \n<br>"));
                // txtPolicy73.setText(Html.fromHtml("To exit the User Instructions and go back to the prior screen click the arrow back button on the top left side of the screen. If you want to send or share these instructions click the share button on the top right side of the screen. Clicking the little house will bring you back to the dashboard.\n<br>"));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Insurance Companies");
                break;

            case "AdvanceInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);


                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep a copy of their Advance Directives as well as copies of their loved ones’ documents. The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("<b>Add a Document.</b> Click the Plus icon on the bottom right corner of the screen. \n" +
                        "Click ATTACH FILE at the top of the screen and select the location of the file.  Choose the file.  A message should appear identifying the name of the file, “Name of File Selected” -  Click SAVE on the upper right corner of the screen.   The minimum amount of information needed is Document Description and Document Date.  To edit the document, click the blue Pencil icon and upload a new document or delete the entire entry (see Delete Entry below)."));
                txt63.setText(Html.fromHtml("<b>Add a Document from your Email.</b> If the document is in your email, go to your email account and choose the email with the file you want to upload. Click on the attachment and open it. After it opens, click the forward icon and scroll to find MYLO.  Choose the specific profile and section for the upload.  For more information consult the “How-to Video” found in the Menu Bar."));
                txt64.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open and you can add your information."));
                txt65.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt66.setText(Html.fromHtml("<b>Summary Data Screen.</b> After a document is stored the User will be brought to the Summary Screen. The User can View, Print or Email a Summary Report of all documents stored in the section by clicking the green icon on the bottom of the screen. (The specific document can be viewed or shared only if working inside the cell – see View Document below)."));
                txt67.setText(Html.fromHtml("<b>Delete Entry.</b> From the Summary screen, swipe right to left on the applicable cell in the document."));
                txt68.setText(Html.fromHtml("<b>Edit Entry.</b> From the Summary screen, click on the cell of the document and make changes. To save your edits click SAVE on the top right corner of the screen."));
                txtPolicy69.setText(Html.fromHtml("<b>View Document.</b> Click the green circle located on the bottom of the screen and then click View Document."));
                txtPolicy71.setText(Html.fromHtml("<b>Email or Fax Document.</b> Click the green circle located on the bottom of the screen and then click Email or Fax Document."));
                txtPolicy72.setText(Html.fromHtml("<b>Faxing a Document.</b> Users should only send documents via fax to meet an organizations, (e.g. health, insurance) HIPAA requirements.  Users can receive notification that the fax was received by including a Reply Email Address. The fax will be sent immediately, but the machine you are sending to may be turned off or very busy.  If a reply is not received within 20 minutes, check the fax number and resend."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>" +
                        "<br>" + txtPolicy72.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.VISIBLE);
                rel73.setVisibility(View.GONE);


                txtTitle.setText("User Instructions");
                txtHeader.setText("Advance Directives");
                break;

            case "OtherInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);


                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep copies of important documents, and to categorize them. The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("<b>Add a Document.</b> Click the Plus icon on the bottom right corner of the screen. \n" +
                        "Click ATTACH FILE at the top of the screen and select the location of the file.  Choose the file.  A message should appear identifying the name of the file, “Name of File Selected” -  Click SAVE on the upper right corner of the screen.   The minimum amount of information needed is Document Description and Document Date.  To edit the document, click the blue Pencil icon and upload a new document or delete the entire entry (see Delete Entry below)."));
                txt63.setText(Html.fromHtml("<b>Add a Document from your Email.</b> If the document is in your email, go to your email account and choose the email with the file you want to upload. Click on the attachment and open it. After it opens, click the forward icon and scroll to find MYLO.  Choose the specific profile and section for the upload.  For more information consult the “How-to Video” found in the Menu Bar."));
                txt64.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open and you can add your information."));
                txt65.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt66.setText(Html.fromHtml("<b>Summary Data Screen.</b> After a document is stored the User will be brought to the Summary Screen. The User can View, Print or Email a Summary Report of all documents stored in the section by clicking the green icon on the bottom of the screen. (The specific document can be viewed or shared only if working inside the cell – see View Document below)."));
                txt67.setText(Html.fromHtml("<b>Delete Entry.</b> From the Summary screen, swipe right to left on the applicable cell in the document."));
                txt68.setText(Html.fromHtml("<b>Edit Entry.</b> From the Summary screen, click on the cell of the document and make changes. To save your edits click SAVE on the top right corner of the screen."));
                txtPolicy69.setText(Html.fromHtml("<b>View Document.</b> Click the green circle located on the bottom of the screen and then click View Document."));
                txtPolicy71.setText(Html.fromHtml("<b>Email or Fax Document.</b> Click the green circle located on the bottom of the screen and then click Email or Fax Document."));
                txtPolicy72.setText(Html.fromHtml("<b>Faxing a Document.</b> Users should only send documents via fax to meet an organizations, (e.g. health, insurance) HIPAA requirements.  Users can receive notification that the fax was received by including a Reply Email Address. The fax will be sent immediately, but the machine you are sending to may be turned off or very busy.  If a reply is not received within 20 minutes, check the fax number and resend."));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>" +
                        "<br>" + txtPolicy72.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.VISIBLE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Other Documents");

                break;

            case "MedicalInfoInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep copies of important documents, and to categorize them. The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("<b>Add a Document.</b> Click the Plus icon on the bottom right corner of the screen. \n" +
                        "Click ATTACH FILE at the top of the screen and select the location of the file.  Choose the file.  A message should appear identifying the name of the file, “Name of File Selected” -  Click SAVE on the upper right corner of the screen.   The minimum amount of information needed is Document Description and Document Date.  To edit the document, click the blue Pencil icon and upload a new document or delete the entire entry (see Delete Entry below)."));
                txt63.setText(Html.fromHtml("<b>Add a Document from your Email.</b> If the document is in your email, go to your email account and choose the email with the file you want to upload. Click on the attachment and open it. After it opens, click the forward icon and scroll to find MYLO.  Choose the specific profile and section for the upload.  For more information consult the “How-to Video” found in the Menu Bar."));
                txt64.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open and you can add your information."));
                txt65.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt66.setText(Html.fromHtml("<b>Summary Data Screen.</b> After a document is stored the User will be brought to the Summary Screen. The User can View, Print or Email a Summary Report of all documents stored in the section by clicking the green icon on the bottom of the screen. (The specific document can be viewed or shared only if working inside the cell – see View Document below)."));
                txt67.setText(Html.fromHtml("<b>Delete Entry.</b> From the Summary screen, swipe right to left on the applicable cell in the document."));
                txt68.setText(Html.fromHtml("<b>Edit Entry.</b> From the Summary screen, click on the cell of the document and make changes. To save your edits click SAVE on the top right corner of the screen."));
                txtPolicy69.setText(Html.fromHtml("<b>View Document.</b> Click the green circle located on the bottom of the screen and then click View Document."));
                txtPolicy71.setText(Html.fromHtml("<b>Email or Fax Document.</b> Click the green circle located on the bottom of the screen and then click Email or Fax Document."));
                txtPolicy72.setText(Html.fromHtml("<b>Faxing a Document.</b> Users should only send documents via fax to meet an organizations, (e.g. health, insurance) HIPAA requirements.  Users can receive notification that the fax was received by including a Reply Email Address. The fax will be sent immediately, but the machine you are sending to may be turned off or very busy.  If a reply is not received within 20 minutes, check the fax number and resend."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>" +
                        "<br>" + txtPolicy72.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.VISIBLE);
                rel73.setVisibility(View.GONE);

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>" +
                        "<br>" + txtPolicy72.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.VISIBLE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Medical Records");

                break;
            case "LivingInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                //shradha
                txt61.setText(Html.fromHtml("<b>Function.</b> Allows users to keep a record of any patterns or progress and can help in early detection of upcoming critical situations."));
                txt62.setText(Html.fromHtml("<b>Add or Edit Information.</b> Toggle yes or no and use the note functionality.  Click SAVE to store the information."));
                txt63.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input, print or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Activities of Daily Living");
                break;

            case "CheckListInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                //shradha
                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to create a master checklist of routine appointments (specialists and tests) and the dates of completion.  The Summary page contains name of appointment and the last completion date. "));
                txt62.setText(Html.fromHtml("<b>Add an Appointment.</b> Click the Plus icon on the bottom right corner of the screen.\n" +
                        "Click the first line “Type of Specialist or Test”.  Choose a Specialist or Test. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open and you can add your information. The minimum amount of information you need to complete this section is the category and frequency.\n"));
                txt63.setText(Html.fromHtml("<b>Add a Completion Date.</b> Add the Completed Date(s) by clicking the green Down Arrow on the right of the appointment then click ADD NEW and enter the date.  The information will automatically be saved. Click the Up Arrow to close the cell."));
                txt64.setText(Html.fromHtml("<b>Edit an Appointment</b> Edit the Appointment by clicking the green Down Arrow on the right of the appointment. You will see the Edit icon - click and edit the appointment. Once completed click the SAVE icon at the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Delete an Appointment.</b> To delete the entry swipe left (right to left). You will see the Delete icon (Trash can) - click it. You will be asked if you are sure you want to delete the record. "));
                txt66.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input, print or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>"
                ).toString();

                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Routine Appointments");
                break;
            case "EventNotesInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                //shradha
                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep a running record of particularly important notes.  For example, what the doctor said, how you or your loved one felt at a particular time, time you took certain medicine."));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. Start typing your note. Users may use their phone’s microphone to add information verbally.  The date and time will automatically be created. Once completed click SAVE. "));
                txt63.setText(Html.fromHtml("<b>Edit a Note.</b> To edit the note, click the green Down Arrow on the right of the note. You will see the ”EDIT NOTE” function. Click, ”EDIT NOTE” and type your changes. To save your edits click SAVE at the top right corner of the screen."));
                txt64.setText(Html.fromHtml("<b>Delete a Note.</b> To delete the entry swipe left (right to left) the cell of your note. You will see the delete icon (Trash can) – click it. You will be asked if you are sure you want to delete the record."));
                txt65.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input, print or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>"
                ).toString();

                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Event Notes");
                break;
            case "PharmacyInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep their important contact information and the important contact information for each of their loved ones. "));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus on the bottom right corner of the screen. If the person is in your Contacts click the option “Add from Contacts”. The minimum amount of information you need to complete this section is the name of the entity and a phone number. To add a phone number, Users must include the type of number, e.g. mobile, home, office or fax.  You can add more than one phone number by clicking the blue Plus icon."));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open, and you can add your information. Business cards can also be added."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Profile Photo:</b> To add a Photo, click anywhere in the circle, choose ”Take picture” or “Gallery”, select the photo you want, and then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt66.setText(Html.fromHtml("<b>Adding a Business Card.</b> The last entry on the screen allows the User to add a business card. Click ADD CARD and further instructions will pop up.  Click SAVE on the top right corner of the screen when you are done.  To forward the card, click on the card and click the Share icon on the top right of the screen. To edit the card, click the Pencil icon and follow the directions on the screen."));
                txt67.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a Summary screen.  To edit information, click the cell of the contact, make changes and then click SAVE on the top right corner of the screen. Clicking SAVE returns the User back to the Summary page. "));
                txt68.setText(Html.fromHtml("<b>Automated Phone Calls.</b> From the Summary screen, click the Phone icon."));
                txtPolicy69.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txtPolicy71.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                //txtPolicy72.setText(Html.fromHtml("<b>To view a report or to email the data</b> click the green circle with the three dots located on the left side of the screen. \n<br>"));
                // txtPolicy73.setText(Html.fromHtml("To exit the User Instructions and go back to the prior screen click the arrow back button on the top left side of the screen. If you want to send or share these instructions click the share button on the top right side of the screen. Clicking the little house will bring you back to the dashboard.\n<br>"));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Pharmacies & Home Medical Equipment");
                break;

            case "FinanceInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep their important contact information and the important contact information for each of their loved ones. "));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus on the bottom right corner of the screen. If the person is in your Contacts click the option “Add from Contacts”. The minimum amount of information you need to complete this section is the category, name of the Firm and the phone number. To store a phone number, Users must include the type of number, e.g. mobile, home, office or fax.  You can add more than one phone number by clicking the blue Plus icon."));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open, and you can add your information. Business cards can also be added."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Profile Photo:</b> To add a Photo, click anywhere in the circle, choose ”Take picture” or “Gallery”, select the photo you want, and then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt66.setText(Html.fromHtml("<b>Adding a Business Card.</b> The last entry on the screen allows the User to add a business card. Click ADD CARD and further instructions will pop up.  Click SAVE on the top right corner of the screen when you are done.  To forward the card, click on the card and click the Share icon on the top right of the screen. To edit the card, click the Pencil icon and follow the directions on the screen."));
                txt67.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a Summary screen.  To edit information, click the cell of the contact, make changes and then click SAVE on the top right corner of the screen. Clicking SAVE returns the User back to the Summary page. "));
                txt68.setText(Html.fromHtml("<b>Automated Phone Calls.</b> From the Summary screen, click the Phone icon."));
                txtPolicy69.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txtPolicy71.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                //txtPolicy72.setText(Html.fromHtml("<b>To view a report or to email the data</b> click the green circle with the three dots located on the left side of the screen. \n<br>"));
                // txtPolicy73.setText(Html.fromHtml("To exit the User Instructions and go back to the prior screen click the arrow back button on the top left side of the screen. If you want to send or share these instructions click the share button on the top right side of the screen. Clicking the little house will bring you back to the dashboard.\n<br>"));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Finance, Legal, Other");
                break;
            case "HospitalInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep their important contact information and the important contact information for each of their loved ones. "));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. If the entity is in your Contacts click the option “Add from Contacts”. The minimum amount of information you need to complete this section is the name of the entity, category, and phone number including type (e.g. mobile, home, office or fax). You can add more than one phone number by clicking the blue Plus icon. "));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open, and you can add your information. Business cards can also be added."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Profile Photo:</b> To add a Photo, click anywhere in the circle, choose ”Take picture” or “Gallery”, select the photo you want, and then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt66.setText(Html.fromHtml("<b>Adding a Business Card.</b> The last entry on the screen allows the User to add a business card. Click ADD CARD and further instructions will pop up.  Click SAVE on the top right corner of the screen when you are done.  To forward the card, click on the card and click the Share icon on the top right of the screen. To edit the card, click the Pencil icon and follow the directions on the screen."));
                txt67.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a Summary screen.  To edit information, click the cell of the contact, make changes and then click SAVE on the top right corner of the screen. Clicking SAVE returns the User back to the Summary page. "));
                txt68.setText(Html.fromHtml("<b>Automated Phone Calls.</b> From the Summary screen, click the Phone icon."));
                txtPolicy69.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txtPolicy71.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                //txtPolicy72.setText(Html.fromHtml("<b>To view a report or to email the data</b> click the green circle with the three dots located on the left side of the screen. \n<br>"));
                // txtPolicy73.setText(Html.fromHtml("To exit the User Instructions and go back to the prior screen click the arrow back button on the top left side of the screen. If you want to send or share these instructions click the share button on the top right side of the screen. Clicking the little house will bring you back to the dashboard.\n<br>"));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Urgent Care, TeleMed, Hospitals, Rehab, Home Care");
                break;
            // txtStep1.setText(Html.fromHtml("<b>"));

            case "DoctorInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to store the name and contact information of the doctor(s) or other health care professional’s."));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. If the person is in your Contacts click the option, “Add from Contacts”. The minimum amount of information needed to complete this section is the physician’s or other health care professional’s name, his/her specialty and phone number including type (e.g. mobile, home, office or fax).  You can add more than one phone number by clicking the blue Plus icon."));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open, and you can add your information. Business cards can also be added."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Profile Photo:</b> To add a Photo, click anywhere in the circle, choose ”Take picture” or “Gallery”, select the photo you want, and then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt66.setText(Html.fromHtml("<b>Adding a Business Card.</b> The last entry on the screen allows the User to add a business card. Click ADD CARD and further instructions will pop up.  Click SAVE on the top right corner of the screen when you are done.  To forward the card, click on the card and click the Share icon on the top right of the screen. To edit the card, click the Pencil icon and follow the directions on the screen."));
                txt67.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a Summary screen.  To edit information, click the cell of the contact, make changes and then click SAVE on the top right corner of the screen. Clicking SAVE returns the User back to the Summary page. "));
                txt68.setText(Html.fromHtml("<b>Automated Phone Calls.</b> From the Summary screen, click the Phone icon."));
                txtPolicy69.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txtPolicy71.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                //txtPolicy72.setText(Html.fromHtml("<b>To view a report or to email the data</b> click the green circle with the three dots located on the left side of the screen. \n<br>"));
                // txtPolicy73.setText(Html.fromHtml("To exit the User Instructions and go back to the prior screen click the arrow back button on the top left side of the screen. If you want to send or share these instructions click the share button on the top right side of the screen. Clicking the little house will bring you back to the dashboard.\n<br>"));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Doctors & Other Health Care Professionals");
                break;

            case "PhysicianInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to store the name and contact information of the primary care physician(s)."));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. If the person is in your Contacts click the option, “Add from Contacts”. The minimum amount of information needed to complete this section is the physician’s name, his/her specialty, and phone number including type (e.g. mobile, home, office or fax).  You can add more than one phone number by clicking the blue Plus icon."));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open, and you can add your information. Business cards can also be added."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Profile Photo:</b> To add a Photo, click anywhere in the circle, choose ”Take picture” or “Gallery”, select the photo you want, and then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt66.setText(Html.fromHtml("<b>Adding a Business Card.</b> The last entry on the screen allows the User to add a business card. Click ADD CARD and further instructions will pop up.  Click SAVE on the top right corner of the screen when you are done.  To forward the card, click on the card and click the Share icon on the top right of the screen. To edit the card, click the Pencil icon and follow the directions on the screen."));
                txt67.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a Summary screen.  To edit information, click the cell of the contact, make changes and then click SAVE on the top right corner of the screen. Clicking SAVE returns the User back to the Summary page. "));
                txt68.setText(Html.fromHtml("<b>Automated Phone Calls.</b> From the Summary screen, click the Phone icon."));
                txtPolicy69.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txtPolicy71.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                //txtPolicy72.setText(Html.fromHtml("<b>To view a report or to email the data</b> click the green circle with the three dots located on the left side of the screen. \n<br>"));
                // txtPolicy73.setText(Html.fromHtml("To exit the User Instructions and go back to the prior screen click the arrow back button on the top left side of the screen. If you want to send or share these instructions click the share button on the top right side of the screen. Clicking the little house will bring you back to the dashboard.\n<br>"));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);


                txtTitle.setText("User Instructions");
                txtHeader.setText("Primary Physician");
                break;
            case "EmergencyInstruction":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> Allows Users to keep Emergency Contact Information and identify the Healthcare Proxy Agent."));
                txt62.setText(Html.fromHtml("<b>Add Information.</b> Click the Plus icon on the bottom right corner of the screen. If the person is in your Contacts click the option “Add from Contacts”. The minimum amount of information needed to complete this section is the person’s name, the relationship to the person, the priority, and a phone number including type (e.g. mobile, home, office or fax). You can add more than one phone number by clicking the blue Plus icon. "));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. You may use your phone’s microphone to add certain data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text box will open, and you can add your information. Business cards can also be added."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen."));
                txt65.setText(Html.fromHtml("<b>Profile Photo:</b> To add a Photo, click anywhere in the circle, choose ”Take picture” or “Gallery”, select the photo you want, and then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt66.setText(Html.fromHtml("<b>Adding a Business Card.</b> The last entry on the screen allows the User to add a business card. Click ADD CARD and further instructions will pop up.  Click SAVE on the top right corner of the screen when you are done.  To forward the card, click on the card and click the Share icon on the top right of the screen. To edit the card, click the Pencil icon and follow the directions on the screen."));
                txt67.setText(Html.fromHtml("<b>Summary Screen.</b> MYLO provides Users with a Summary screen.  To edit information, click the cell of the contact, make changes and then click SAVE on the top right corner of the screen. Clicking SAVE returns the User back to the Summary page. "));
                txt68.setText(Html.fromHtml("<b>Automated Phone Calls.</b> From the Summary screen, click the Phone icon."));
                txtPolicy69.setText(Html.fromHtml("<b>Delete an Entry.</b> From the Summary screen, identify the entry to delete, then swipe left (start from the right side of the cell and swipe left)."));
                txtPolicy71.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));
                //txtPolicy72.setText(Html.fromHtml("<b>To view a report or to email the data</b> click the green circle with the three dots located on the left side of the screen. \n<br>"));
                // txtPolicy73.setText(Html.fromHtml("To exit the User Instructions and go back to the prior screen click the arrow back button on the top left side of the screen. If you want to send or share these instructions click the share button on the top right side of the screen. Clicking the little house will bring you back to the dashboard.\n<br>"));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        "<br>" + txt67.getText().toString() + "<br>" +
                        "<br>" + txt68.getText().toString() + "<br>" +
                        "<br>" + txtPolicy69.getText().toString() + "<br>" +
                        "<br>" + txtPolicy71.getText().toString() + "<br>"
                ).toString();


                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel69.setVisibility(View.VISIBLE);
                rel71.setVisibility(View.VISIBLE);
                rel72.setVisibility(View.GONE);
                rel73.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Emergency Contacts and Health Care Proxy Agent");
                break;
            case "ConnectionInstuction":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);
                //shradha
                txt61.setText(Html.fromHtml("<b>Welcome to MYLO -</b> This App allows you to maintain a digital library of critical documents and medical information for you and your loved ones. There are 6 main sections. The App provides unlimited profiles, pdf reports, email and fax functionality, and the ability to share profiles and eliminate the need to re-enter data.<br>"));
                txt62.setText(Html.fromHtml("<b>Getting Started -</b> Before you begin we would like to explain the data entry process. Except for this Profiles page, each screen has a top bar which includes the name of the screen, an arrow back button on the top left, three dots on the top right (which allows you to view or email the data), and a save button on data entry screens.<br>First time Users will see First Time User Instructions directly on the screen. After adding information. the User can access User Instructions by clicking on the three buttons. A User Guide is also available from the dropdown Menu located on the top left of the Profiles page.<br>"));
                txt622.setText(Html.fromHtml("<br>"));
                txt63.setText(Html.fromHtml("<br><b>A reminder to all Users -</b> MYLO is a native to your phone. This means that we do not have access to your information. If you forget the name and email address used for this app then the data will be lost. It’s important that you  remember this information and to periodically back up the and or send yourself the pdf reports. Backup instructions are included on the Menu page.<br>"));
                txt64.setText(Html.fromHtml("<b>MYLO Would Like to Access Your Contacts and MYLO Would Like to Access Your Camera -</b>This message does NOT mean that the company has access to your information, it simply allows the APP to utilise the functions of your phone and provide the USER with a better experience.<br>"));
                txt65.setText(Html.fromHtml("<b>Adding a Profile - </b>click the plus box. You will see two options. Create New and Import from Dropbox." + "<br> <b>Option 1 : Create New.</b> You will be brought to the Add Profile  Screen. You can type in the new profile or if the person is in your Contacts then click the gray bar on the top right side of your screen to load information. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).<br> <b>Option 2 : Import from Dropbox.</b> Using this feature you can upload a profile from Dropbox. If you click that option, you will be provided additional instructions. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).<br>"));
                txt66.setText(Html.fromHtml("There are <b>three</b> required elements to create a Profile –  <b>name, relationship</b>, and an <b>email</b> address.<br>"));
                txt67.setText(Html.fromHtml("<b>To Share a Profile –</b> Long press on the Profile Box and you will be able to upload the Profile to your Dropbox account.<br>"));
                txt68.setText(Html.fromHtml("<b>To Delete a Profile –</b> Long Press on the Profile Box.<br>Thanks & Regards"));

                UI = Html.fromHtml("<br><b>Welcome to MYLO -</b> This App allows you to maintain a digital library of critical documents and medical information for you and your loved ones. There are 6 main sections. The App provides unlimited profiles, pdf reports, email and fax functionality, and the ability to share profiles and eliminate the need to re-enter data.<br>" +
                        "<br><b>Getting Started -</b> Before you begin we would like to explain the data entry process. Except for this Profiles page, each screen has a top bar which includes the name of the screen, an arrow back button on the top left, three dots on the top right (which allows you to view or email the data), and a save button on data entry screens.<br>First time Users will see First Time User Instructions directly on the screen. After adding information. the User can access User Instructions by clicking on the three buttons. A User Guide is also available from the dropdown Menu located on the top left of the Profiles page.<br>" +
                        "<br>" +
                        "<br><br><b>A reminder to all Users -</b> MYLO is a native to your phone. This means that we do not have access to your information. If you forget the name and email address used for this app then the data will be lost. It’s important that you  remember this information and to periodically back up the and or send yourself the pdf reports. Backup instructions are included on the Menu page.<br>" +
                        "<br><b>MYLO Would Like to Access Your Contacts and MYLO Would Like to Access Your Camera -</b>This message does NOT mean that the company has access to your information, it simply allows the APP to utilise the functions of your phone and provide the USER with a better experience.<br>" +
                        "<br><b>Adding a Profile - </b>click the plus box. You will see two options. Create New and Import from Dropbox." + "<br> <b>Option 1 : Create New.</b> You will be brought to the Add Profile  Screen. You can type in the new profile or if the person is in your Contacts then click the gray bar on the top right side of your screen to load information. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).<br> <b>Option 2 : Import from Dropbox.</b> Using this feature you can upload a profile from Dropbox. If you click that option, you will be provided additional instructions. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).<br>" +
                        "<br>There are <b>three</b> required elements to create a Profile –  <b>name, relationship</b>, and an <b>email</b> address.<br>" +
                        "<br><b>To Share a Profile –</b> Long press on the Profile Box and you will be able to upload the Profile to your Dropbox account.<br>" +
                        "<br><b>To Delete a Profile –</b> Long Press on the Profile Box.<br>").toString();

                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);

                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img622.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.VISIBLE);
                img622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                // txtEmail.setText("Email");
                txtTitle.setText("User Instructions");
                txtHeader.setText("Profile");
                break;
            case "Personal":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);


                txt61.setText(Html.fromHtml("<b>Function.</b> The Personal Profile allows Users to store data about themselves or their loved ones."));
                txt62.setText(Html.fromHtml("<b>Data Entry.</b> Data is entered via free text and dropdown menus. User’s may use their phone’s microphone to add data verbally.  Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open. "));
                txt63.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen. Users should click SAVE after every few entries. "));
                txt64.setText(Html.fromHtml("<b>Profile Photo.</b> To add a Photo click anywhere in the circle and choose, ”Take Picture” or “Gallery” then click SAVE. To edit or delete the photo, click the Pencil icon."));
                txt65.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements. "));
                txt66.setText(Html.fromHtml("<b>Exiting the Sub-Section.</b> To return to the prior screen, click the back Arrow icon on the top left corner of the screen. To return to the Dashboard, click the Home icon on the top left corner of the screen."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>"
                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Personal Profile");
                break;


            //shradha
            case "SharePdf":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                //shradha
                txt61.setText(Html.fromHtml("You can<b>add</b> the pdf documents from storage."));
                txt62.setText(Html.fromHtml("To <b>use</b> pdf from the email attachments, you need to <b>download</b>it from the email then you can<b>add</b> it from storage's download folder."));

                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.GONE);
                txt64.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);


                //shradha
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.GONE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);


                txtHeader.setText("Share Pdf Instructions");
                break;

            case "Medical":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("<b>Function.</b> The Medical Profile allows Users to maintain a comprehensive summary of their own or their loved ones’ medical conditions and health history."));
                txt62.setText(Html.fromHtml("<b>To Open a Section.</b> Click the green Down Arrow."));
                txt63.setText(Html.fromHtml("<b>Data Entry.</b> In many sections Users will need to click the blue Plus icon before they can enter data and to enter additional entries. Data is entered via free text and dropdown menus.  You may use your phone’s microphone to add certain data verbally. Dropdown menus are identified by the little grey Arrow to the right of the line. Click anywhere on the line and the dropdown menu will open.  For each dropdown menu we have included an “Other” as the last option on the menu – if you click “Other” a text field will open and you can add your information.  Use Note if there is a general comment you wish to include."));
                txt64.setText(Html.fromHtml("<b>Save Input.</b> Click SAVE on the top right corner of the screen.  It’s recommended that you click SAVE after you complete each section. "));
                txt65.setText(Html.fromHtml("<b>To Close a Section.</b> Click the green Up Arrow on the right corner of the section. "));
                txt66.setText(Html.fromHtml("<b>To Edit or Delete Information.</b> Click the Pencil icon or Trashcan."));
                txt67.setText(Html.fromHtml("<b>View Input and Reports.</b> Users can view the input or email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements. "));
                //txt68.setText(Html.fromHtml("<b>To Close a Section.</b> Click the green UP ARROW on the right side of the section."));
                //txtPolicy69.setText(Html.fromHtml("<b>Reports.</b> Users can view and email reports by clicking the green circle located on the bottom of the screen. Fax capability has been included in certain sections and should only be used to meet HIPAA requirements."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>" +
                        "<br>" + txt65.getText().toString() + "<br>" +
                        "<br>" + txt66.getText().toString() + "<br>" +
                        //  "<br>"+txt67.getText().toString()+"<br>"+
                        //  "<br>"+txt68.getText().toString()+"<br>"+
                        "<br>" + txt67.getText().toString() + "<br>"
                ).toString();
                //shradha
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.VISIBLE);
                txt66.setVisibility(View.VISIBLE);
                txt67.setVisibility(View.VISIBLE);
                txt68.setVisibility(View.GONE);
                rel69.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);


                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.VISIBLE);
                img66.setVisibility(View.VISIBLE);
                img67.setVisibility(View.VISIBLE);
                img68.setVisibility(View.GONE);

                img622.setVisibility(View.GONE);


                txtTitle.setText("User Instructions");
                txtHeader.setText("Medical Profile");
                break;

            case "Living":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);
//                String msgl = "To <b>save</b> information click the check mark" +
//                        " on the top right side of the screen." +
//                        "<br>" +
//                        "To <b>edit</b> information simply change the data and then save your edits by clicking on the check mark on the top right side of the screen." +
//                        "<br>" +
//                        "To <b>view</b>, <b>email</b>, or <b>fax</b> the data in each section click on the three dots on the top right side of the screen.";
//
//                txtMsg.setText(Html.fromHtml(msgl));

                //nikita
                txt61.setText(Html.fromHtml("To <b>save</b> information click the check mark on the top right side of the screen."));
                txt62.setText(Html.fromHtml("To <b>edit</b> information simply change the data and then save your edits by clicking on the check mark on the top right side of the screen."));
                txt63.setText(Html.fromHtml("To <b>view</b>, <b>email</b>, or <b>fax</b> the data in each section click on the three dots on the top right side of the screen."));

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtHeader.setText("Living Activity Instructions");
                break;

            case "Card":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSkyBlue));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("You can <b>add</b> the card from both the sides i.e <b>front and back</b>."));
                txt62.setText(Html.fromHtml("For adding front and back card, You need to click on the <b>plus</b> icon. Now you can add picture of card either from <b>Camera or Gallery</b>."));
                txt63.setText(Html.fromHtml("For adding card through <b>camera</b>, It is <b>recommended</b> that you hold your phone <b>horizontal</b> while taking a picture of a card."));
                txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                        "<br>" + txt62.getText().toString() + "<br>" +
                        "<br>" + txt63.getText().toString() + "<br>" +
                        "<br>" + txt64.getText().toString() + "<br>"

                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Insurance Card GuideLines");
                /*titleHeader.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSkyBlue));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);
                String msgc = "You can <b>add</b> the card from both the sides i.e <b>front and back</b>." +
                        "<br>" +
                        "For adding front and back card, You need to click on the <b>plus</b> icon. Now you can add picture of card either from <b>Camera or Gallery</b>." +
                        "<br>" +
                        "For adding card through <b>camera</b>, It is <b>recommended</b> that you hold your phone <b>horizontal</b> while taking a picture of a card." +
                        "<br>" +
                        "To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen.";

                txtMsg.setText(Html.fromHtml(msgc));

                //nikita
                txt61.setText(Html.fromHtml("You can <b>add</b> the card from both the sides i.e <b>front and back</b>."));
                txt62.setText(Html.fromHtml("For adding front and back card, You need to click on the <b>plus</b> icon. Now you can add picture of card either from <b>Camera or Gallery</b>."));
                txt63.setText(Html.fromHtml("For adding card through <b>camera</b>, It is <b>recommended</b> that you hold your phone <b>horizontal</b> while taking a picture of a card."));
                txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.VISIBLE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.VISIBLE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtHeader.setText("Insurance Card GuideLines");*/
                break;

            case "EmergencySection":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEmerMainGreen));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("This section allows Users to maintain information most often needed during an emergency event."));
                txt62.setText(Html.fromHtml("Users can view the Input, Print and Email a report of the entire section by clicking the green circle located on the bottom of the screen."));
                txt63.setText(Html.fromHtml("User instructions are available within each sub-section by clicking the Question Mark on the top right corner of the screen.  To email the instructions click the Share icon on the top right corner of the screen. To exit click the back Arrow on the top left corner of the screen. To return to the Dashboard, click the Home icon on the top left corner of the screen."));
                //   txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                                "<br>" + txt62.getText().toString() + "<br>" +
                                "<br>" + txt63.getText().toString() + "<br>" //+
                        //  "<br>"+txt64.getText().toString()+"<br>"

                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Personal & Medical Profile & Emergency Contacts");

                break;

            case "SpecialitySection":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorSpecialityYellow));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("This section allows users to store important contact information (including business cards).  The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("Users can view input, print and email a report of the entire section by clicking the green circle located on the bottom of the screen."));
                txt63.setText(Html.fromHtml("User instructions are available within each sub-section by clicking the Question Mark on the top right corner of the screen.  To email the instructions click the Share icon on the top right corner of the screen. To exit click the back Arrow on the top left corner of the screen. To return to the Dashboard, click the Home icon on the top left corner of the screen."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                                "<br>" + txt62.getText().toString() + "<br>" +
                                "<br>" + txt63.getText().toString() + "<br>" //+
                        //  "<br>"+txt64.getText().toString()+"<br>"

                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
               if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
                {
                    txtHeader.setText("Healthcare & Other Contacts");
                }else{
                    txtHeader.setText("Specialty Contacts");
                }
                break;

            case "InsuranceSection":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorInsuaranceSkyBlue));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("This section allows users to store Insurance Companies, cards and forms.  The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("Users can view input, print and email a report of the entire section by clicking the green circle located on the bottom of the screen."));
                txt63.setText(Html.fromHtml("User instructions are available within each sub-section by clicking the Question Mark on the top right corner of the screen.  To email the instructions click the Share icon on the top right corner of the screen. To exit click the back Arrow on the top left corner of the screen. To return to the Dashboard, click the Home icon on the top left corner of the screen."));
                //   txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                                "<br>" + txt62.getText().toString() + "<br>" +
                                "<br>" + txt63.getText().toString() + "<br>" //+
                        //  "<br>"+txt64.getText().toString()+"<br>"

                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Insurance Companies ");

                break;

            case "EventSection":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorEventPink));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("This section allows Users to maintain Event Notes, document a Routine Appointment Checklist along with completed dates, record progress of ADLs and IADLs and keep a summary history of Vital Signs."));
                txt62.setText(Html.fromHtml("Users can view input, print and email a report of the entire section by clicking the green circle located on the bottom of the screen."));
                txt63.setText(Html.fromHtml("User instructions are available within each sub-section by clicking the Question Mark on the top right corner of the screen.  To email the instructions click the Share icon on the top right corner of the screen. To exit click the back Arrow on the top left corner of the screen. To return to the Dashboard, click the Home icon on the top left corner of the screen."));
                //   txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));

                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                                "<br>" + txt62.getText().toString() + "<br>" +
                                "<br>" + txt63.getText().toString() + "<br>" //+
                        //  "<br>"+txt64.getText().toString()+"<br>"

                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Notes & Appointments Checklist");

                break;

            case "PrescriptionSection":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorPrescriptionGray));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("This section allows Users to store individual prescriptions, take pictures of their prescriptions and pills, as well as upload a list of prescriptions. The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("Users can view input, print and email a report of the entire section by clicking the green circle located on the bottom of the screen. "));
                txt63.setText(Html.fromHtml("User instructions are available within each sub-section by clicking the Question Mark on the top right corner of the screen.  To email the instructions click the Share icon on the top right corner of the screen. To exit click the back Arrow on the top left corner of the screen. To return to the Dashboard, click the Home icon on the top left corner of the screen."));
                //   txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                                "<br>" + txt62.getText().toString() + "<br>" +
                                "<br>" + txt63.getText().toString() + "<br>" //+
                        //  "<br>"+txt64.getText().toString()+"<br>"

                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
                txtHeader.setText("Prescriptions");

                break;

            case "DirectiveSection":
                titleHeader.setBackgroundColor(getResources().getColor(R.color.colorDirectiveRed));
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);

                txt61.setText(Html.fromHtml("This section allows users to store important documents.  The following formats can be uploaded: PDF, Excel, Word, Text, JPG."));
                txt62.setText(Html.fromHtml("Users can view input, print and email a report of the entire section by clicking the green circle located on the bottom of the screen."));
                txt63.setText(Html.fromHtml("User instructions are available within each sub-section by clicking the Question Mark on the top right corner of the screen.  To email the instructions click the Share icon on the top right corner of the screen. To exit click the back Arrow on the top left corner of the screen. To return to the Dashboard, click the Home icon on the top left corner of the screen."));
                //   txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));

                //   txt64.setText(Html.fromHtml("To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen."));


                UI = Html.fromHtml("<br>" + txt61.getText().toString() + "<br>" +
                                "<br>" + txt62.getText().toString() + "<br>" +
                                "<br>" + txt63.getText().toString() + "<br>" //+
                        //  "<br>"+txt64.getText().toString()+"<br>"

                ).toString();

                //nikita
                txt61.setVisibility(View.VISIBLE);
                txt62.setVisibility(View.VISIBLE);
                txt63.setVisibility(View.VISIBLE);
                txt64.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                txt65.setVisibility(View.GONE);
                txt66.setVisibility(View.GONE);
                txt67.setVisibility(View.GONE);
                txt68.setVisibility(View.GONE);
                txt622.setVisibility(View.GONE);
                rel622.setVisibility(View.GONE);

                //nikita
                img61.setVisibility(View.VISIBLE);
                img62.setVisibility(View.VISIBLE);
                img63.setVisibility(View.VISIBLE);
                img64.setVisibility(View.GONE);
                img65.setVisibility(View.GONE);
                img66.setVisibility(View.GONE);
                img67.setVisibility(View.GONE);
                img68.setVisibility(View.GONE);
                img622.setVisibility(View.GONE);

                txtTitle.setText("User Instructions");
               if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
                {
                    txtHeader.setText("Medical & Other Important Documents");
                }else
                {
                    txtHeader.setText("Advance Directives & Documents");
                }

                break;

        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(InstructionActivity.this);
                alert.setTitle("Share ?");
                alert.setMessage("Do you want to share dropbox instructions ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = "This is a method for personal cloud storage. It is commonly referred to as a method to backup one's files online. In addition to store the files, one can also share them with colleagues and friends, all using Dropbox. To store, retrieve and manage your files, one can use a Dropbox application." +
                                "<br>" +
                                "You need to login with your dropbox account. If you don't  have dropbox account, You need to have one. " +
                                "<br>" +
                                "<b>Guidelines:</b>" +
                                "<br>" +
                                "<b>1) Login: </b>" +
                                "<br>" +
                                "Click on <b>\"Login With DropBox\" </b>button, It will redirect you to dropbox site. Now you can login with your personal dropbox account." +
                                "<br>" +
                                "After logging in, To allow the access to the files and folders from your dropbox, Click the <b>'Allow'</b>  Button.So now you can Access your files." +
                                "<br>" +
                                "<b>2) Backup: </b>" +
                                "<br>" +
                                "If you have logged in to dropbox from application, It will show you Backup Options." +
                                "<br>" +
                                "On clicking  <b>'BACKUP'</b> button, you need to Click on <b>'Backup your Data'</b> button to store your profile." +
                                "<br>" +
                                "<b>3) Restore: </b>" +
                                "<br>" +
                                "If you have logged in to dropbox from application, It will show you Restore Options. It will help to restore profiles." +
                                "<br>" +
                                "On clicking  <b>'RESTORE'</b>  button, you will get list of zip files for download. On clicking any of the zip file, It will be downloaded and you will get a popup message  to unzip and restore file." +
                                "<br>" +
                                "<b>4) Get Files: </b>" +
                                "<br>" +
                                "If you have logged in to dropbox from application, It will show you View Files Options. It will help you to get the documents in Advance Directive section  from dropbox storage." +
                                "<br>" +
                                "On clicking <b>'VIEW FILES'</b> button, you will get list of documents files for download. On clicking any of the document  file, It will be downloaded ." +
                                "<br>" +
                                "Now you can add this document  to your application by clicking on <b>'ADD FILES'</b> button.";


                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                new String[]{""});
                        // String name= getString(PrefConstants.CONNECTED_NAME);
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                "DropBox Instructions"); // subject
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(msg)); // Body
                        emailIntent.setType("application/email");
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
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

    /**
     * Function: Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtEmail://Email instruction
                showEmailDialog();
                break;
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;

        }
    }

    /**
     * Function: Prepare email body with instrucion content of section for share
     */
    private void showEmailDialog() {
        final Dialog dialogEmailIns = new Dialog(context);
        dialogEmailIns.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEmailIns.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_email_ins, null);
        final TextView txtYes = dialogview.findViewById(R.id.txtYes);
        final TextView txtNo = dialogview.findViewById(R.id.txtNo);

        dialogEmailIns.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEmailIns.getWindow().getAttributes());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogEmailIns.getWindow().setAttributes(lp);
        dialogEmailIns.setCanceledOnTouchOutside(false);
        dialogEmailIns.show();


        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (From.equals("PrescriptionInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Prescription Information", UI);
                } else if (From.equals("PrescriptionUploadInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Prescription List Upload", UI);
                } else if (From.equals("EventNotesInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Event Notes", UI);
                } else if (From.equals("CheckListInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Routine Appointments", UI);
                } else if (From.equals("VitalInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Vital Signs", UI);
                } else if (From.equals("LivingInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Activity of daily living", UI);
                } else if (From.equals("InsuranceInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Insurance Companies", UI);
                } else if (From.equals("CardInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Insurance Cards", UI);
                } else if (From.equals("FormInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Insurance Forms", UI);
                } else if (From.equals("DoctorInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Doctors & Other Health Care Professionals", UI);
                } else if (From.equals("HospitalInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Urgent Care, TeleMed, Hospitals, Rehab, Home Care", UI);
                } else if (From.equals("PharmacyInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Pharmacies & Home Medical Equipment", UI);
                } else if (From.equals("FinanceInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Finance, Legal, Other ", UI);
                } else if (From.equals("Personal")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Personal Profile", UI);
                } else if (From.equals("Medical")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Medical Profile", UI);
                } else if (From.equals("PhysicianInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Primary Physician", UI);
                } else if (From.equals("EmergencyInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Emergency Contacts and Health Care Proxy Agent", UI);
                } else if (From.equals("OtherInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Other Documents", UI);
                } else if (From.equals("MedicalInfoInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Medical Records", UI);
                } else if (From.equals("Card")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Insurance Card GuideLines", UI);
                } else if (From.equals("AdvanceInstruction")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Advance Directives", UI);
                } else if (From.equals("SpecialitySection")) {
                    if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
                    {
                        ui.emailAttachement(InstructionActivity.this, "Instructions for Healthcare & Other Contacts", UI);
                    }else
                    {
                        ui.emailAttachement(InstructionActivity.this, "Instructions for Specialty Contacts", UI);
                    }
                 } else if (From.equals("EmergencySection")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Personal & Medical Profile & Emergency Contacts   ", UI);
                } else if (From.equals("InsuranceSection")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Insurance Companies ", UI);
                } else if (From.equals("EventSection")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Notes & Appointments Checklist", UI);
                } else if (From.equals("PrescriptionSection")) {
                    ui.emailAttachement(InstructionActivity.this, "Instructions for Prescriptions", UI);
                } else if (From.equals("DirectiveSection")) {
                    if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
                    {
                        ui.emailAttachement(InstructionActivity.this, "Instructions for Medical & Other Important Documents", UI);
                    }else{
                        ui.emailAttachement(InstructionActivity.this, "Instructions for Advance Directives & Documents", UI);
                    }
                } else {
                    Toast.makeText(context, "Some thing went wrong...!!", Toast.LENGTH_SHORT).show();
                }
                dialogEmailIns.dismiss();
            }
        });

        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEmailIns.dismiss();
            }
        });


    }
}
