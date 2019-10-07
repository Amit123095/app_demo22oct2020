package com.mindyourlovedone.healthcare.DashBoard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.utility.UIEmails;

public class UserInsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack, txtEmail;

    TextView txtStep1, txtStep2, txtStep22, txtStep222, txtStep3, txtStep4, txtStep5, txtStep55, txtStep555, txtStep6, txtStep7, txtStep8, txtStep9, txtHeader;
    Context context = this;
    FloatingActionButton floatProfile;

    String UI = "", From = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ins);
        initUi();
        initListener();
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        txtEmail.setOnClickListener(this);
        //  floatProfile.setOnClickListener(this);
    }

    private void initUi() {
        floatProfile = findViewById(R.id.floatProfile);
        txtHeader = findViewById(R.id.txtHeader);
        imgBack = findViewById(R.id.imgBack);
        txtEmail = findViewById(R.id.txtEmail);
        txtStep1 = findViewById(R.id.txtStep1);
        txtStep2 = findViewById(R.id.txtStep2);
        txtStep22 = findViewById(R.id.txtStep22);
        txtStep222 = findViewById(R.id.txtStep222);
        txtStep3 = findViewById(R.id.txtStep3);
        txtStep4 = findViewById(R.id.txtStep4);
        txtStep5 = findViewById(R.id.txtStep5);
        txtStep55 = findViewById(R.id.txtStep55);
        txtStep555 = findViewById(R.id.txtStep555);
        txtStep6 = findViewById(R.id.txtStep6);
        txtStep7 = findViewById(R.id.txtStep7);
        txtStep8 = findViewById(R.id.txtStep8);
        txtStep9 = findViewById(R.id.txtStep9);
        RelativeLayout relStep222 = findViewById(R.id.rlStep222);
        RelativeLayout relStep22 = findViewById(R.id.rlStep22);
        RelativeLayout relStep55 = findViewById(R.id.rlStep55);
        RelativeLayout relStep555 = findViewById(R.id.rlStep555);
        RelativeLayout relStep4 = findViewById(R.id.rlStep4);
        RelativeLayout relStep5 = findViewById(R.id.rlStep5);
        RelativeLayout relStep6 = findViewById(R.id.rlStep6);
        RelativeLayout relStep7 = findViewById(R.id.rlStep7);
        RelativeLayout relStep8 = findViewById(R.id.rlStep8);
        RelativeLayout relStep9 = findViewById(R.id.rlStep9);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            From = i.getExtras().getString("From");
        }
        if (From.equalsIgnoreCase("Profile")) {
            txtHeader.setText(R.string.prof_inst);
            txtStep1.setText(Html.fromHtml("<b>The Profiles Screen.</b> The Profiles screen is home base. Users can set up as many profiles as they want. To access MYLO’s functionality, click anywhere on the Profile cell and the User will be brought to the Dashboard screen. The file folder on the Profile cell represents the Dashboard. "));
            txtStep2.setText(Html.fromHtml("<b>The Dashboard.</b> Within each Profile is a Dashboard. The Dashboard is where a User will input and store information, business cards and documents. There are six sections within the Dashboard,  and within each section are sub-sections. The main sections are (1) Personal, Medical & Emergency Contacts; (2) Specialty Contacts; (3) Notes & Appointments; (4) Advance Directives & Other Documents; (5) Insurance; and (6) Prescriptions. "));
            txtStep3.setText(Html.fromHtml("<b>User Instructions.</b> Each section and sub-section have First-time User instructions as well as standing instructions. User instructions are available by clicking the Question Mark on the top right corner of the screen. To forward instructions click the Share icon on the right top corner of the screen. To exit User instructions, click the back Arrow on the top left corner of the screen.  The Menu Bar also contains “How-to Videos” for many of the sections. "));
            txtStep4.setText(Html.fromHtml("<b>Create a new Profile.</b> To create a Profile for a loved one click the green Plus icon located at the bottom right corner of the screen.  Users will be given a choice to “Create New” or “Import from Dropbox”.  “Create New” allows Users to initiate a new Profile. Users can automatically import data from their contacts, or type in new information.  The minimum amount of information needed to create a profile is the person’s name, the User’s relationship to the person, and their email address. To access the Dashboard click anywhere on the profile cell. "));
            txtStep5.setText(Html.fromHtml("<b>Import from Dropbox.</b> This functionality may be used when restoring a Profile or uploading a Profile shared by another person. Instructions regarding Backup, Restore, and Sharing can be found in the Menu Bar under Settings. "));
            txtStep6.setText(Html.fromHtml("<b>Delete a Profile.</b> To delete a profile, long press on the Profile bar.  Users will see two choices, “Delete” or “Backup/Share” the profile”. "));
            txtStep7.setText(Html.fromHtml("<b>Backup or Share a Profile.</b> To Backup or Share a Profile, long press on the Profile bar.  \n" +
                    "      Click “Backup/Share a Profile” and the User will be provided further instructions.\n"));
            txtStep8.setText(Html.fromHtml("<b>Viewing, Sharing and Printing Information (Reports).</b> Users can View, Share or Print a particular sub-section or an entire section by clicking on the green circle (with three white dots) located at the bottom of each section and sub-section screen.  Fax capability is also available to meet HIPAA requirements. "));
            txtStep9.setText(Html.fromHtml("<b>The Menu bar.</b> The Menu Bar is available from the Profiles page and the Dashboard. It’s \n" +
                    "located on the top left corner (the three horizontal lines). The Menu Bar is full of important information, including MYLO FAQs, the User Guide and Contact Us. Please remember to take a look at the Resources section – it includes information about Advance Care Directives, MYLO Forms and Templates, and interesting podcasts and videos. To exit the Menu Bar, click All Profiles. \n"));

            relStep222.setVisibility(View.GONE);
            relStep22.setVisibility(View.GONE);
            relStep55.setVisibility(View.GONE);
            relStep555.setVisibility(View.GONE);

            //   UI = Html.fromHtml("<b>Welcome to MYLO</b> –  This App allows you to maintain a digital library of critical documents and medical information for you and your loved ones. There are 6 main sections. The App provides unlimited profiles, pdf reports, email and fax functionality, and the ability to share profiles and eliminate the need to re-enter data. \n\n <b>Getting Started</b> - Before you begin we would like to explain the data entry process. Except for this Profiles page, each screen has a top bar which includes the name of the screen, an arrow back button on the top left, three dots on the top right (which allows you to view or email the data), and a save button on data entry screens. \n\n\n\nFirst time Users will see First Time User Instructions directly on the screen. After adding information. the User can access User Instructions by clicking on the three buttons. A User Guide is also available from the dropdown Menu located on the top left of the Profiles page. \n\n <b>A reminder to all Users</b> - MYLO is a native to your phone. This means that we do not have access to your information. If you forget the name and email address used for this app then the data will be lost. It’s important that you  remember this information and to periodically back up the and or send yourself the pdf reports. Backup instructions are included on the Menu page.\n\n <b>MYLO Would Like to Access Your Contacts and MYLO Would Like to Access Your Camera</b> - This message does NOT mean that the company has access to your information, it simply allows the APP to utilise the functions of your phone and provide the USER with a better experience. \n\n <b>Adding a Profile</b> - click the plus box. You will see two options. Create New and Import from Dropbox. \n\n <b>Option 1</b> : Create New. You will be brought to the Add Profile  Screen. You can type in the new profile or if the person is in your Contacts then click the gray bar on the top right side of your screen to load information. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).\n\n <b>Option 2</b> : Import from Dropbox. Using this feature you can upload a profile from Dropbox. If you click that option, you will be provided additional instructions. Once completed click either Save (top right of screen) or Add Profile (bottom of screen). \n\n There are <b>three</b> required elements to create a Profile –  <b>name, relationship</b>, and an <b>email</b> address.\n\n <b>To Share a Profile –</b> Long press on the Profile Box and you will be able to upload the Profile to your Dropbox account.\n\n <b>To Delete a Profile –</b> Long Press on the Profile Box.\n\nThanks & Regards").toString();
            UI = Html.fromHtml("" + txtStep1.getText().toString() + " <br>" +
                    "<br>" + txtStep2.getText().toString() + " <br>" +
                    "<br>" + txtStep3.getText().toString() + " <br>" +
                    "<br> " + txtStep4.getText().toString() + " <br>" +
                    "<br> " + txtStep5.getText().toString() + " <br>" +
                    "<br>" + txtStep6.getText().toString() + " <br>" +
                    "<br>" + txtStep7.getText().toString() + " <br>" +
                    "<br>" + txtStep8.getText().toString() + " <br>" +
                    "<br>" + txtStep9.getText().toString() + "<br>").toString();

        } else if (From.equalsIgnoreCase("Dashboard")) {
            txtHeader.setText("Dashboard Instructions");
            txtStep1.setText(Html.fromHtml("<b>Function.</b> The Dashboard is where Users will input and store information, business cards and documents. There are six sections on the Dashboard and within each section are sub-sections. The main sections are (1) Personal, Medical and Emergency Contacts; (2) Specialty Contacts; (3) Notes and Appointments; (4) Advance Directives and Other Documents; (5) Insurance; and (6) Prescriptions. "));
            txtStep2.setText(Html.fromHtml("<b>User Instructions.</b> Each section and sub-section have First-time User instructions as well as standing instructions. User instructions are available by clicking the Question Mark on the top right corner of the screen. To forward instructions, click the Up Arrow on the right top corner of the screen. To exit the User instructions, click the back Arrow on the top right corner of the screen. The Menu Bar also contains “How-to Videos” for many of the sections. "));
            txtStep3.setText(Html.fromHtml("<b>Viewing, Sharing and Printing Information (Reports).</B> Users can View, Share or Print a particular sub-section or an entire section by clicking the green circle (with three white dots) located at the bottom of the screen.  Fax capability is available in certain sections to help Users meet HIPAA requirements. "));
            txtStep4.setText(Html.fromHtml("<b>The Menu Bar.</b> The Menu Bar is located on the top left corner of the Dashboard (it’s the three horizontal lines). The Menu Bar is full of important information, including MYLO FAQs, User Guide and Contact Us. Please take a look at the Resource section – it includes information about Advance Care Directives, MYLO forms and templates, and interesting podcasts and videos. To exit the Menu Bar click ALL PROFILES. "));// txtStep5.setText(Html.fromHtml("<b>Instructions.</b>  Many of the sub-screens have first time user instructions.  If you click on the instructions you can send them to yourself by clicking <b>the share button</b> on the top right side of the screen.   <b>To exit the screen</b> click the <b>arrow back button</b> on the top left side of the screen.   If you <b>click the house</b> – you will be brought back to the dashboard of the person’s profile. \n\n"));
            // txtStep6.setText(Html.fromHtml("<b>The MENU bar.</b>  On the top left side of the dashboard screen are three parallel lines. This is the Menu bar.  If you click it  - you will see a dropdown menu.  There is lots of good information in this section.  I already explained how to use “All Profiles”.   For example “Resources” contains Advance Care Directives Information, Helpful MYLO Forms and Templates and interesting Podcasts and Videos. You should have a look in each of the topics. <b>To Return to the Menu bar</b> click the MENU bar. \n\n"));
            // txtStep7.setText(Html.fromHtml("<b>LET’S GET STARTED.</b>   I like to populate a profile by starting with the Personal and Medical section, then Specialty, then Notes and Routine Appointments, then Advance Care Directives, then Insurance and lastly Prescriptions.   But that’s me – you should do it however you feel most comfortable.   Make me happy…. Click the green box – Personal & Medical  Profile & Emergency Contacts. \n\n"));
            // txtStep8.setText(Html.fromHtml("Remember you can always find the instructions for the section by clicking the question mark on the top right side of the screen. \n\n"));
            relStep4.setVisibility(View.VISIBLE);
            relStep5.setVisibility(View.GONE);
            relStep6.setVisibility(View.GONE);
            relStep7.setVisibility(View.GONE);
            relStep8.setVisibility(View.GONE);
            relStep9.setVisibility(View.GONE);
            relStep222.setVisibility(View.GONE);
            relStep22.setVisibility(View.GONE);
            relStep55.setVisibility(View.GONE);
            relStep555.setVisibility(View.GONE);

            //   UI = Html.fromHtml("<b>Welcome to MYLO</b> –  This App allows you to maintain a digital library of critical documents and medical information for you and your loved ones. There are 6 main sections. The App provides unlimited profiles, pdf reports, email and fax functionality, and the ability to share profiles and eliminate the need to re-enter data. \n\n <b>Getting Started</b> - Before you begin we would like to explain the data entry process. Except for this Profiles page, each screen has a top bar which includes the name of the screen, an arrow back button on the top left, three dots on the top right (which allows you to view or email the data), and a save button on data entry screens. \n\n\n\nFirst time Users will see First Time User Instructions directly on the screen. After adding information. the User can access User Instructions by clicking on the three buttons. A User Guide is also available from the dropdown Menu located on the top left of the Profiles page. \n\n <b>A reminder to all Users</b> - MYLO is a native to your phone. This means that we do not have access to your information. If you forget the name and email address used for this app then the data will be lost. It’s important that you  remember this information and to periodically back up the and or send yourself the pdf reports. Backup instructions are included on the Menu page.\n\n <b>MYLO Would Like to Access Your Contacts and MYLO Would Like to Access Your Camera</b> - This message does NOT mean that the company has access to your information, it simply allows the APP to utilise the functions of your phone and provide the USER with a better experience. \n\n <b>Adding a Profile</b> - click the plus box. You will see two options. Create New and Import from Dropbox. \n\n <b>Option 1</b> : Create New. You will be brought to the Add Profile  Screen. You can type in the new profile or if the person is in your Contacts then click the gray bar on the top right side of your screen to load information. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).\n\n <b>Option 2</b> : Import from Dropbox. Using this feature you can upload a profile from Dropbox. If you click that option, you will be provided additional instructions. Once completed click either Save (top right of screen) or Add Profile (bottom of screen). \n\n There are <b>three</b> required elements to create a Profile –  <b>name, relationship</b>, and an <b>email</b> address.\n\n <b>To Share a Profile –</b> Long press on the Profile Box and you will be able to upload the Profile to your Dropbox account.\n\n <b>To Delete a Profile –</b> Long Press on the Profile Box.\n\nThanks & Regards").toString();
            UI = Html.fromHtml("" + txtStep1.getText().toString() + " <br>" +
                    "<br>" + txtStep2.getText().toString() + " <br>" +
                    "<br>" + txtStep3.getText().toString() + " <br>" +
                    //  "<br> "+ txtStep4.getText().toString()+" <br>" +
                    // "<br> "+ txtStep1.getText().toString()+" <br>" +
                    //  "<br>"+ txtStep1.getText().toString()+" <br>" +
                    //   "<br>"+ txtStep1.getText().toString()+" <br>" +
                    "<br>" + txtStep4.getText().toString() + "<br>").toString();
        } else if (From.equalsIgnoreCase("Restore")) {
            txtHeader.setText("Dropbox Restore Instructions");
            txtStep1.setText(Html.fromHtml("You will see all the MYLO zip files that you have stored in the past."));
            txtStep2.setText(Html.fromHtml("Choose the most current file."));
            txtStep3.setText(Html.fromHtml("For a single Profile note the name."));
            txtStep4.setText(Html.fromHtml("To upload all Profiles the file is titled MYLO.zip"));
            // txtStep6.setText(Html.fromHtml("<b>The MENU bar.</b>  On the top left side of the dashboard screen are three parallel lines. This is the Menu bar.  If you click it  - you will see a dropdown menu.  There is lots of good information in this section.  I already explained how to use “All Profiles”.   For example “Resources” contains Advance Care Directives Information, Helpful MYLO Forms and Templates and interesting Podcasts and Videos. You should have a look in each of the topics. <b>To Return to the Menu bar</b> click the MENU bar. \n\n"));
            // txtStep7.setText(Html.fromHtml("<b>LET’S GET STARTED.</b>   I like to populate a profile by starting with the Personal and Medical section, then Specialty, then Notes and Routine Appointments, then Advance Care Directives, then Insurance and lastly Prescriptions.   But that’s me – you should do it however you feel most comfortable.   Make me happy…. Click the green box – Personal & Medical  Profile & Emergency Contacts. \n\n"));
            // txtStep8.setText(Html.fromHtml("Remember you can always find the instructions for the section by clicking the question mark on the top right side of the screen. \n\n"));
            relStep4.setVisibility(View.VISIBLE);
            relStep5.setVisibility(View.GONE);
            relStep6.setVisibility(View.GONE);
            relStep7.setVisibility(View.GONE);
            relStep8.setVisibility(View.GONE);
            relStep9.setVisibility(View.GONE);
            relStep222.setVisibility(View.GONE);
            relStep22.setVisibility(View.GONE);
            relStep55.setVisibility(View.GONE);
            relStep555.setVisibility(View.GONE);

            //   UI = Html.fromHtml("<b>Welcome to MYLO</b> –  This App allows you to maintain a digital library of critical documents and medical information for you and your loved ones. There are 6 main sections. The App provides unlimited profiles, pdf reports, email and fax functionality, and the ability to share profiles and eliminate the need to re-enter data. \n\n <b>Getting Started</b> - Before you begin we would like to explain the data entry process. Except for this Profiles page, each screen has a top bar which includes the name of the screen, an arrow back button on the top left, three dots on the top right (which allows you to view or email the data), and a save button on data entry screens. \n\n\n\nFirst time Users will see First Time User Instructions directly on the screen. After adding information. the User can access User Instructions by clicking on the three buttons. A User Guide is also available from the dropdown Menu located on the top left of the Profiles page. \n\n <b>A reminder to all Users</b> - MYLO is a native to your phone. This means that we do not have access to your information. If you forget the name and email address used for this app then the data will be lost. It’s important that you  remember this information and to periodically back up the and or send yourself the pdf reports. Backup instructions are included on the Menu page.\n\n <b>MYLO Would Like to Access Your Contacts and MYLO Would Like to Access Your Camera</b> - This message does NOT mean that the company has access to your information, it simply allows the APP to utilise the functions of your phone and provide the USER with a better experience. \n\n <b>Adding a Profile</b> - click the plus box. You will see two options. Create New and Import from Dropbox. \n\n <b>Option 1</b> : Create New. You will be brought to the Add Profile  Screen. You can type in the new profile or if the person is in your Contacts then click the gray bar on the top right side of your screen to load information. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).\n\n <b>Option 2</b> : Import from Dropbox. Using this feature you can upload a profile from Dropbox. If you click that option, you will be provided additional instructions. Once completed click either Save (top right of screen) or Add Profile (bottom of screen). \n\n There are <b>three</b> required elements to create a Profile –  <b>name, relationship</b>, and an <b>email</b> address.\n\n <b>To Share a Profile –</b> Long press on the Profile Box and you will be able to upload the Profile to your Dropbox account.\n\n <b>To Delete a Profile –</b> Long Press on the Profile Box.\n\nThanks & Regards").toString();
            UI = Html.fromHtml("" + txtStep1.getText().toString() + " <br>" +
                    "<br>" + txtStep2.getText().toString() + " <br>" +
                    "<br>" + txtStep3.getText().toString() + " <br>" +
                    //  "<br> "+ txtStep4.getText().toString()+" <br>" +
                    // "<br> "+ txtStep1.getText().toString()+" <br>" +
                    //  "<br>"+ txtStep1.getText().toString()+" <br>" +
                    //   "<br>"+ txtStep1.getText().toString()+" <br>" +
                    "<br>" + txtStep4.getText().toString() + "<br>").toString();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtEmail:
                if (From.equalsIgnoreCase("Profile")) {
                    showEmailDialog("Getting Started Instructions");
                } else if (From.equalsIgnoreCase("Dashboard")) {
                    showEmailDialog("Understanding the Dashboard Instructions");
                }else if (From.equalsIgnoreCase("Restore")) {
                    showEmailDialog("Dropbox Restore Instructions");
                }
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

    private void showEmailDialog(final String sub) {
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
                UIEmails ui = new UIEmails();
                ui.emailAttachement(UserInsActivity.this, sub, UI);
//                Toast.makeText(context, "Still in progress please wait..!!", Toast.LENGTH_SHORT).show();
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
