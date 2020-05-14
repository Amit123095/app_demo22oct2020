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
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.mindyourlovedone.healthcare.utility.UIEmails;

/**
 * Class: UserInsActivity
 * Screen: Display Instruction
 * A class that manages to display all screen instructions
 * implements OnclickListener for onClick event on views
 */
public class UserInsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack, txtEmail;
    TextView txtStep1, txtStep2, txtStep22, txtStep222, txtStep3, txtStep4, txtStep5, txtStep55, txtStep555, txtStep6, txtStep7, txtStep8, txtStep9, txtHeader;
    Context context = this;
    FloatingActionButton floatProfile;
    String UI = "", From = "";
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ins);
        preferences=new Preferences(context);

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
        txtEmail.setOnClickListener(this);
    }

    /**
     * Function:Initialize UI and View
     */
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
            if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
            {
                txtStep2.setText(Html.fromHtml("<b>The Dashboard.</b> Within each Profile is a Dashboard. The Dashboard is where a User will input and store information, business cards and documents. There are six sections within the Dashboard,  and within each section are sub-sections. The main sections are (1) Personal, Medical & Emergency Contacts; (2) Healthcare & Other Contacts; (3) Notes & Appointments; (4) Medical & Other Important Documents; (5) Insurance; and (6) Prescriptions. "));
            }else{
                txtStep2.setText(Html.fromHtml("<b>The Dashboard.</b> Within each Profile is a Dashboard. The Dashboard is where a User will input and store information, business cards and documents. There are six sections within the Dashboard,  and within each section are sub-sections. The main sections are (1) Personal, Medical & Emergency Contacts; (2) Specialty Contacts; (3) Notes & Appointments; (4) Advance Directives & Other Documents; (5) Insurance; and (6) Prescriptions. "));
            }
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
            if(preferences.getString(PrefConstants.REGION).equalsIgnoreCase(getResources().getString(R.string.India)))
            {
                txtStep1.setText(Html.fromHtml("<b>Function.</b> The Dashboard is where Users will input and store information, business cards and documents. There are six sections on the Dashboard and within each section are sub-sections. The main sections are (1) Personal, Medical and Emergency Contacts; (2) Healthcare & Other Contacts; (3) Notes and Appointments; (4) Medical & Other Important Documents; (5) Insurance; and (6) Prescriptions. "));
            }else{
                txtStep1.setText(Html.fromHtml("<b>Function.</b> The Dashboard is where Users will input and store information, business cards and documents. There are six sections on the Dashboard and within each section are sub-sections. The main sections are (1) Personal, Medical and Emergency Contacts; (2) Specialty Contacts; (3) Notes and Appointments; (4) Advance Directives and Other Documents; (5) Insurance; and (6) Prescriptions. "));
            }
            txtStep2.setText(Html.fromHtml("<b>User Instructions.</b> Each section and sub-section have First-time User instructions as well as standing instructions. User instructions are available by clicking the Question Mark on the top right corner of the screen. To forward instructions, click the Up Arrow on the right top corner of the screen. To exit the User instructions, click the back Arrow on the top right corner of the screen. The Menu Bar also contains “How-to Videos” for many of the sections. "));
            txtStep3.setText(Html.fromHtml("<b>Viewing, Sharing and Printing Information (Reports).</B> Users can View, Share or Print a particular sub-section or an entire section by clicking the green circle (with three white dots) located at the bottom of the screen.  Fax capability is available in certain sections to help Users meet HIPAA requirements. "));
            txtStep4.setText(Html.fromHtml("<b>The Menu Bar.</b> The Menu Bar is located on the top left corner of the Dashboard (it’s the three horizontal lines). The Menu Bar is full of important information, including MYLO FAQs, User Guide and Contact Us. Please take a look at the Resource section – it includes information about Advance Care Directives, MYLO forms and templates, and interesting podcasts and videos. To exit the Menu Bar click ALL PROFILES. "));// txtStep5.setText(Html.fromHtml("<b>Instructions.</b>  Many of the sub-screens have first time user instructions.  If you click on the instructions you can send them to yourself by clicking <b>the share button</b> on the top right side of the screen.   <b>To exit the screen</b> click the <b>arrow back button</b> on the top left side of the screen.   If you <b>click the house</b> – you will be brought back to the dashboard of the person’s profile. \n\n"));
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

            UI = Html.fromHtml("" + txtStep1.getText().toString() + " <br>" +
                    "<br>" + txtStep2.getText().toString() + " <br>" +
                    "<br>" + txtStep3.getText().toString() + " <br>" +

                    "<br>" + txtStep4.getText().toString() + "<br>").toString();
        } else if (From.equalsIgnoreCase("Restore")) {
            txtHeader.setText("Dropbox Restore Instructions");
            txtStep1.setText(Html.fromHtml("You will see all the MYLO zip files that you have stored in the past."));
            txtStep2.setText(Html.fromHtml("Choose the most current file."));
            txtStep3.setText(Html.fromHtml("For a single Profile note the name."));
            txtStep4.setText(Html.fromHtml("To upload all Profiles the file is titled MYLO.zip"));
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

            UI = Html.fromHtml("" + txtStep1.getText().toString() + " <br>" +
                    "<br>" + txtStep2.getText().toString() + " <br>" +
                    "<br>" + txtStep3.getText().toString() + " <br>" +
                    "<br>" + txtStep4.getText().toString() + "<br>").toString();
        }
    }

    /**
     * Function: Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtEmail://Email Instruction
                if (From.equalsIgnoreCase("Profile")) {
                    showEmailDialog("Getting Started Instructions");
                } else if (From.equalsIgnoreCase("Dashboard")) {
                    showEmailDialog("Understanding the Dashboard Instructions");
                } else if (From.equalsIgnoreCase("Restore")) {
                    showEmailDialog("Dropbox Restore Instructions");
                }
                break;
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(context, BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                startActivity(intentDashboard);
                break;
        }
    }

    /**
     * Function: Prepare email body with instrucion content of section for share
     * @param sub
     */
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
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                UIEmails ui = new UIEmails();
                ui.emailAttachement(UserInsActivity.this, sub, UI);
//                Toast.makeText(context, "Still in progress please wait..!!", Toast.LENGTH_SHORT).show();
                dialogEmailIns.dismiss();
            }
        });

        txtNo.setOnClickListener(new View.OnClickListener() {
            /**
             * Function: Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                dialogEmailIns.dismiss();
            }
        });


    }
}
