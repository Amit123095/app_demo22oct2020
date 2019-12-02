package com.mindyourlovedone.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.LivingQuery;
import com.mindyourlovedone.healthcare.model.Living;
import com.mindyourlovedone.healthcare.pdfCreation.EventPdfNew;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;

public class LivingActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    Context context = this;
    View rootview;
    RelativeLayout rlLiving;
    ImageView imgHome, imgBack, imgDone, imgRight, imgInfo;
    Preferences preferences;
    TextView txtTitle, txtName, txtSave;
    DBHelper dbHelper;
    ImageView imgInfoF, imgInfoI;
    EditText etOtherFunction, etFunctionalNote, etOtherInstrument, etInstrumentalNote;
    ToggleButton tbAlert, tbComputer, tbRemote, tbFinances, tbPreparing, tbShopping, tbUsing, tbBathing, tbContinence, tbDressing, tbfeed, tbToileting, tbTranfering, tbTransport, tbPets, tbDriving, tbKeeping, tbMedication;
    String alert = "NO", computer = "NO", remote = "NO", eating = "NO", finance = "NO", prepare = "NO", shop = "NO", use = "NO", bath = "NO", continence = "NO", dress = "NO", feed = "NO", toileting = "NO", transfer = "NO", transport = "NO", pets = "NO", drive = "NO", keep = "NO", medication = "NO";
    String functionnote = "", fouctionOther = "", instaOther = "", instaNote = "";
    // FloatingActionButton floatOptions;
    ImageView floatOptions;
    Living medInfo;

    @Override
    public void onBackPressed() {//Nikita-1-10-19
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living);
        preferences = new Preferences(context);
        initComponent();
        initUI();
        initListener();
    }

    private void initListener() {
        txtSave.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgInfoF.setOnClickListener(this);
        imgInfoI.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        tbFinances.setOnCheckedChangeListener(this);
        tbPreparing.setOnCheckedChangeListener(this);
        tbShopping.setOnCheckedChangeListener(this);

        tbRemote.setOnCheckedChangeListener(this);
        tbComputer.setOnCheckedChangeListener(this);
        tbAlert.setOnCheckedChangeListener(this);

        tbUsing.setOnCheckedChangeListener(this);
        tbBathing.setOnCheckedChangeListener(this);
        tbContinence.setOnCheckedChangeListener(this);
        tbDressing.setOnCheckedChangeListener(this);
        tbfeed.setOnCheckedChangeListener(this);
        tbToileting.setOnCheckedChangeListener(this);
        tbTranfering.setOnCheckedChangeListener(this);
        tbTransport.setOnCheckedChangeListener(this);
        tbPets.setOnCheckedChangeListener(this);
        tbDriving.setOnCheckedChangeListener(this);
        tbKeeping.setOnCheckedChangeListener(this);
        tbMedication.setOnCheckedChangeListener(this);
    }

    private void initUI() {
        floatOptions = findViewById(R.id.floatOptions);
        imgInfo = findViewById(R.id.imgInfo);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LivingActivity.this, InstructionActivity.class);
                i.putExtra("From", "Living");
                startActivity(i);
               /* final Dialog customDialog;
                customDialog = new Dialog(LivingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.dialog_living);
                customDialog.setCancelable(false);
                TextView txtNotes= (TextView) customDialog.findViewById(R.id.txtNotes);
                String msg="To save information click the check mark" +
                        " on the top right side of the screen." +
                        "<br><br>" +
                        "To edit information simply change the data and then save your edits by clicking on the check mark on the top right side of the screen." +
                        "<br><br>" +
                        "To view, email, or fax the data in each section click on the three dots on the top right side of the screen.";

                txtNotes.setText(Html.fromHtml(msg));
                TextView txtNoteHeader= (TextView) customDialog.findViewById(R.id.txtNoteHeader);
                txtNoteHeader.setText("Help");
                TextView btnYes= (TextView) customDialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();*/
            }
        });
        rlLiving = findViewById(R.id.rlLiving);
        txtName = findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Activities of Daily Living");

        txtSave = findViewById(R.id.txtSave);
        txtSave.setVisibility(View.VISIBLE);

        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        imgRight = findViewById(R.id.imgRight);
        imgDone = findViewById(R.id.imgDone);
        //imgDone.setVisibility(View.VISIBLE);

        imgInfoF = findViewById(R.id.imgInfoF);
        imgInfoI = findViewById(R.id.imgInfoI);

        etOtherFunction = findViewById(R.id.etOtherFunction);
        etFunctionalNote = findViewById(R.id.etFunctionalNote);
        etOtherInstrument = findViewById(R.id.etOtherInstrument);
        etInstrumentalNote = findViewById(R.id.etInstrumentalNote);

        tbFinances = findViewById(R.id.tbFinances);
        tbRemote = findViewById(R.id.tbRemote);
        tbComputer = findViewById(R.id.tbComputer);
        tbAlert = findViewById(R.id.tbAlert);

        tbPreparing = findViewById(R.id.tbPreparing);
        tbShopping = findViewById(R.id.tbShopping);
        tbUsing = findViewById(R.id.tbUsing);
        tbBathing = findViewById(R.id.tbBathing);
        tbContinence = findViewById(R.id.tbContinence);
        tbDressing = findViewById(R.id.tbDressing);
        tbfeed = findViewById(R.id.tbfeed);
        tbToileting = findViewById(R.id.tbToileting);
        tbTranfering = findViewById(R.id.tbTranfering);
        tbTransport = findViewById(R.id.tbTransport);
        tbPets = findViewById(R.id.tbPets);
        tbDriving = findViewById(R.id.tbDriving);
        tbKeeping = findViewById(R.id.tbKeeping);
        tbMedication = findViewById(R.id.tbMedication);

        rlLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        setLivingInfo();
    }

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setLivingInfo() {
        LivingQuery l=new LivingQuery(context,dbHelper);
        medInfo = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (medInfo != null) {
            etFunctionalNote.setText(medInfo.getFunctionNote());
            etOtherFunction.setText(medInfo.getFunctionOther());
            etInstrumentalNote.setText(medInfo.getInstNote());
            etOtherInstrument.setText(medInfo.getInstOther());


            if (medInfo.getFinance().equals("YES")) {
                tbFinances.setChecked(true);
                finance = "YES";
            } else if (medInfo.getFinance().equals("NO")||medInfo.getFinance().equals("")) {
                tbFinances.setChecked(false);
                finance = "NO";
            }

            if (medInfo.getAlert().equals("YES")) {
                tbAlert.setChecked(true);
                alert = "YES";
            } else if (medInfo.getAlert().equals("NO")||medInfo.getAlert().equals("")) {
                tbAlert.setChecked(false);
                alert = "NO";
            }

            if (medInfo.getComputer().equals("YES")) {
                tbComputer.setChecked(true);
                computer = "YES";
            } else if (medInfo.getComputer().equals("NO")||medInfo.getComputer().equals("")) {
                tbComputer.setChecked(false);
                computer = "NO";
            }

            if (medInfo.getRemote().equals("YES")) {
                tbRemote.setChecked(true);
                remote = "YES";
            } else if (medInfo.getRemote().equals("NO")||medInfo.getRemote().equals("")) {
                tbRemote.setChecked(false);
                remote = "NO";
            }

            if (medInfo.getPrepare().equals("YES")) {
                tbPreparing.setChecked(true);
                prepare = "YES";
            } else if (medInfo.getPrepare().equals("NO")||medInfo.getPrepare().equals("")) {
                tbPreparing.setChecked(false);
                prepare = "NO";
            }

            if (medInfo.getShop().equals("YES")) {
                tbShopping.setChecked(true);
                shop = "YES";
            } else if (medInfo.getShop().equals("NO")||medInfo.getShop().equals("")) {
                tbShopping.setChecked(false);
                shop = "NO";
            }

            if (medInfo.getUse().equals("YES")) {
                tbUsing.setChecked(true);
                use = "YES";
            } else if (medInfo.getUse().equals("NO")||medInfo.getUse().equals("")) {
                tbUsing.setChecked(false);
                use = "NO";
            }
            if (medInfo.getBath().equals("YES")) {
                tbBathing.setChecked(true);
                bath = "YES";
            } else if (medInfo.getBath().equals("NO")||medInfo.getBath().equals("")) {
                tbBathing.setChecked(false);
                bath = "NO";
            }
            if (medInfo.getContinence().equals("YES")) {
                tbContinence.setChecked(true);
                continence = "YES";
            } else if (medInfo.getContinence().equals("NO")||medInfo.getContinence().equals("")) {
                tbContinence.setChecked(false);
                continence = "NO";
            }

            if (medInfo.getDress().equals("YES")) {
                tbDressing.setChecked(true);
                dress = "YES";
            } else if (medInfo.getDress().equals("NO")||medInfo.getDress().equals("")) {
                tbDressing.setChecked(false);
                dress = "NO";
            }

            if (medInfo.getFeed().equals("YES")) {
                tbfeed.setChecked(true);
                feed = "YES";
            } else if (medInfo.getFeed().equals("NO")||medInfo.getFeed().equals("")) {
                tbfeed.setChecked(false);
                feed = "NO";
            }

            if (medInfo.getToileting().equals("YES")) {
                tbToileting.setChecked(true);
                toileting = "YES";
            } else if (medInfo.getToileting().equals("NO")||medInfo.getToileting().equals("")) {
                tbToileting.setChecked(false);
                toileting = "NO";
            }

            if (medInfo.getTransfer().equals("YES")) {
                tbTranfering.setChecked(true);
                transfer = "YES";
            } else if (medInfo.getTransfer().equals("NO")||medInfo.getTransfer().equals("")) {
                tbTranfering.setChecked(false);
                transfer = "NO";
            }

            if (medInfo.getTransport().equals("YES")) {
                tbTransport.setChecked(true);
                transport = "YES";
            } else if (medInfo.getTransport().equals("NO")||medInfo.getTransport().equals("")) {
                tbTransport.setChecked(false);
                transport = "NO";
            }

            if (medInfo.getPets().equals("YES")) {
                tbPets.setChecked(true);
                pets = "YES";
            } else if (medInfo.getPets().equals("NO")||medInfo.getPets().equals("")) {
                tbPets.setChecked(false);
                pets = "NO";
            }

            if (medInfo.getDrive().equals("YES")) {
                tbDriving.setChecked(true);
                drive = "YES";
            } else if (medInfo.getDrive().equals("NO")||medInfo.getDrive().equals("")) {
                tbDriving.setChecked(false);
                drive = "NO";
            }

            if (medInfo.getKeep().equals("YES")) {
                tbKeeping.setChecked(true);
                keep = "YES";
            } else if (medInfo.getKeep().equals("NO")||medInfo.getKeep().equals("")) {
                tbKeeping.setChecked(false);
                keep = "NO";
            }

            if (medInfo.getMedication().equals("YES")) {
                tbMedication.setChecked(true);
                medication = "YES";
            } else if (medInfo.getMedication().equals("NO")||medInfo.getMedication().equals("")) {
                tbMedication.setChecked(false);
                medication = "NO";
            }
        }
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context , preferences.getString(PrefConstants.CONNECTED_USERDB));
        LivingQuery p = new LivingQuery(context, dbHelper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatOptions:
                showFloatDialog();
                break;

            case R.id.imgInfoF:
                String msg = "<b>Bathing.</b> <i>The ability to clean oneself and perform grooming activities like shaving and brushing teeth.</i>  <br><br>" +
                        "<b>Dressing.</b> <i> The ability to get dressed by oneself without struggling with buttons and zippers.</i><br><br>" +
                        "<b>Eating.</b> <i> The ability to feed oneself.</i><br><br>" +
                        "<b>Transferring.</b> <i> Being able to either walk or move oneself from a bed to a wheelchair and back again.</i><br><br>" +
                        "<b>Toileting.</b> <i> The ability to get on and off the toilet.</i><br><br>" +
                        "<b>Continence.</b> <i> The ability to control one's bladder and bowel functions.</i>";
                String title = "Activities of Daily Living";
                showViewDialog(context, msg, title);
                break;
            case R.id.imgRight:
                Intent i = new Intent(context, InstructionActivity.class);
                i.putExtra("From", "LivingInstruction");
                startActivity(i);
               /* final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "ActivityLiving.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png", context);
                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Activities Of Daily Living");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);

                Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

                Paragraph p = new Paragraph(" ");
                LineSeparator line = new LineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                try {
                    Header.document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Header.addEmptyLine(1);

                Living Live = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                ArrayList<Living> LivingList = new ArrayList<Living>();
                LivingList.add(Live);
                new EventPdf(1, LivingList, 1);

                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path = Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/ActivityLiving.pdf";
                        switch (itemPos) {
                            case 0: //View
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getLivingInfo());
                                new PDFDocumentProcess(path,
                                        context, result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f = new File(path);
                                preferences.emailAttachement(f, context, "Activities of Daily Living");
                                break;
                          *//*  case 2://fax
                                new FaxCustomDialog(context, path).show();
                                break;*//*

                            case 2://fax
                                Intent i = new Intent(context, InstructionActivity.class);
                                i.putExtra("From", "LivingInstruction");
                                startActivity(i);
                                break;
                        }
                    }

                });
                builder.create().show();*/
                break;
            case R.id.txtSave:
                getValues();
                Boolean flag = LivingQuery.insertLivingData(preferences.getInt(PrefConstants.CONNECTED_USERID), finance, prepare, shop, use, bath, continence, dress, feed, toileting, transfer, transport, pets, drive, keep, medication, functionnote, fouctionOther, instaNote, instaOther, remote, alert, computer);
                if (flag == true) {
                    Toast.makeText(context, "Activity Living has been updated succesfully", Toast.LENGTH_SHORT).show();
                    medInfo = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                    hideSoftKeyboard();
                    //  finish();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.imgBack:
                getValues();

                if (medInfo.getFunctionNote().equals(functionnote)&&
                        medInfo.getFunctionOther().equals(fouctionOther)&&
                        medInfo.getInstNote().equals(instaNote)&&
                        medInfo.getInstOther().equals(instaOther)&&
                        (medInfo.getFinance().equals(finance)||medInfo.getFinance().equals(""))&&
                        (medInfo.getPrepare().equals(prepare)||medInfo.getPrepare().equals(""))&&
                        (medInfo.getShop().equals(shop)||medInfo.getShop().equals(""))&&
                        (medInfo.getUse().equals(use)||medInfo.getUse().equals(""))&&
                        (medInfo.getBath().equals(bath)||medInfo.getBath().equals(""))&&
                        (medInfo.getContinence().equals(continence)||medInfo.getContinence().equals(""))&&
                        (medInfo.getDress().equals(dress)||medInfo.getDress().equals(""))&&
                        (medInfo.getFeed().equals(feed)||medInfo.getFeed().equals(""))&&
                        (medInfo.getToileting().equals(toileting)||medInfo.getToileting().equals(""))&&
                        (medInfo.getTransfer().equals(transfer)||medInfo.getTransfer().equals(""))&&
                        (medInfo.getTransport().equals(transport)||medInfo.getTransport().equals(""))&&
                        (medInfo.getPets().equals(pets)||medInfo.getPets().equals(""))&&
                        (medInfo.getDrive().equals(drive)||medInfo.getDrive().equals(""))&&
                        (medInfo.getKeep().equals(keep)||medInfo.getKeep().equals(""))&&
                        (medInfo.getMedication().equals(medication)||medInfo.getMedication().equals(""))&&
                        (medInfo.getRemote().equals(remote)||medInfo.getRemote().equals(""))&&
                        (medInfo.getAlert().equals(alert)||medInfo.getAlert().equals(""))&&
                        (medInfo.getComputer().equals(computer)||medInfo.getComputer().equals("")))
                {
                    hideSoftKeyboard();
                    finish();
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Save");
                    alert.setMessage("Do you want to save information?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*if (!connection.getName().equals(name) || !connection.getEmail().equals(address))
                            {
                                isfinis=true;
                            }*/
                            hideSoftKeyboard();
                            boolean s=  txtSave.performClick();
                            // backflap=false;
                            dialog.dismiss();
                            finish();
                            /*if (connection.getName().equals(name) || connection.getEmail().equals(address))
                            {
                                finish();
                            }*/

                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            hideSoftKeyboard();
                            finish();
                        }
                    });
                    alert.show();
                }

                break;
            case R.id.imgHome:
                Intent intentHome = new Intent(context, BaseActivity.class);
                intentHome.putExtra("c", 1);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
                break;
        }
    }

    private void getValues() {
        functionnote = etFunctionalNote.getText().toString().trim();
        fouctionOther = etOtherFunction.getText().toString().trim();
        instaOther = etOtherInstrument.getText().toString().trim();
        instaNote = etInstrumentalNote.getText().toString().trim();
    }

    private void showFloatDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "ADL-IADLs.pdf");
        if (file.exists()) {
            file.delete();
        }
       /* new Header().createPdfHeader(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
        preferences.copyFile("ic_launcher.png", context);
        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
        Header.addEmptyLine(1);
        Header.addusereNameChank("Activities of Daily Living");//preferences.getString(PrefConstants.CONNECTED_NAME));
        Header.addEmptyLine(1);

        Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));

        Paragraph p = new Paragraph(" ");
        LineSeparator line = new LineSeparator();
        line.setOffset(-4);
        line.setLineColor(BaseColor.LIGHT_GRAY);
        p.add(line);
        try {
            Header.document.add(p);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Header.addEmptyLine(1);

        Living Live = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        ArrayList<Living> LivingList = new ArrayList<Living>();
        LivingList.add(Live);
        new EventPdf(1, LivingList, 1);

        Header.document.close();*/
        //New PDF Varsa
        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png",context);
        calendar=preferences.addFile("calpdf.png", context);calendarWite=preferences.addFile("calpdf_wite.png", context);
        profile=preferences.addFile("profpdf.png", context); profileWite=preferences.addFile("profpdf_wite.png", context);

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"ACTIVITIES OF DAILY LIVING", calendarWite, profileWite);

        HeaderNew.addusereNameChank("ACTIVITIES OF DAILY LIVING");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp=preferences.addFile("eve_three.png", context);
        Living Live = LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        // ArrayList<Living> LivingList = new ArrayList<Living>();
        // LivingList.add(Live);
        new EventPdfNew(1, Live, 1,pp);
        HeaderNew.document.close();
        //------------------------------------------------
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        floatContact.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        floatNew.setImageResource(R.drawable.closee);

        TextView txtNew = dialogview.findViewById(R.id.txtNew);
        txtNew.setText(getResources().getString(R.string.EmailReports));

        TextView txtContact = dialogview.findViewById(R.id.txtContact);
        txtContact.setText(getResources().getString(R.string.ViewReports));

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
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        floatNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  CopyReadAssetss("insu_pdf.pdf");
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/ADL-IADLs.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, context, "Activities of Daily Living");
                dialog.dismiss();
            }

        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/ADL-IADLs.pdf";

                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getLivingInfo());
                new PDFDocumentProcess(path,
                        context, result);

                System.out.println("\n" + result + "\n");
                dialog.dismiss();
            }
        });
    }

    private void showViewDialog(Context context, String Message, String title) {
        final Dialog customDialog;

        // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        //  View customView = inflater.inflate(R.layout.dialog_input, null);
        // Build the dialog
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_living);
        customDialog.setCancelable(false);
        TextView txtNotes = customDialog.findViewById(R.id.txtNotes);
        txtNotes.setText(Html.fromHtml(Message));
        TextView txtNoteHeader = customDialog.findViewById(R.id.txtNoteHeader);
        txtNoteHeader.setText(title);
        TextView btnYes = customDialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.tbFinances:
                if (isChecked == true)
                    finance = "YES";
                else
                    finance = "NO";
                break;

            case R.id.tbRemote:
                if (isChecked == true)
                    remote = "YES";
                else
                    remote = "NO";
                break;

            case R.id.tbAlert:
                if (isChecked == true)
                    alert = "YES";
                else
                    alert = "NO";
                break;

            case R.id.tbComputer:
                if (isChecked == true)
                    computer = "YES";
                else
                    computer = "NO";
                break;

            case R.id.tbPreparing:
                if (isChecked == true)
                    prepare = "YES";
                else
                    prepare = "NO";
                break;

            case R.id.tbShopping:
                if (isChecked == true)
                    shop = "YES";
                else
                    shop = "NO";
                break;

            case R.id.tbUsing:
                if (isChecked == true)
                    use = "YES";
                else
                    use = "NO";
                break;

            case R.id.tbBathing:
                if (isChecked == true)
                    bath = "YES";
                else
                    bath = "NO";
                break;

            case R.id.tbContinence:
                if (isChecked == true)
                    continence = "YES";
                else
                    continence = "NO";
                break;

            case R.id.tbDressing:
                if (isChecked == true)
                    dress = "YES";
                else
                    dress = "NO";
                break;

            case R.id.tbfeed:
                if (isChecked == true)
                    feed = "YES";
                else
                    feed = "NO";
                break;

            case R.id.tbToileting:
                if (isChecked == true)
                    toileting = "YES";
                else
                    toileting = "NO";
                break;

            case R.id.tbTranfering:
                if (isChecked == true)
                    transfer = "YES";
                else
                    transfer = "NO";
                break;

            case R.id.tbTransport:
                if (isChecked == true)
                    transport = "YES";
                else
                    transport = "NO";
                break;

            case R.id.tbMedication:
                if (isChecked == true)
                    medication = "YES";
                else
                    medication = "NO";
                break;

            case R.id.tbKeeping:
                if (isChecked == true)
                    keep = "YES";
                else
                    keep = "NO";
                break;

            case R.id.tbDriving:
                if (isChecked == true)
                    drive = "YES";
                else
                    drive = "NO";
                break;

            case R.id.tbPets:
                if (isChecked == true)
                    pets = "YES";
                else
                    pets = "NO";
                break;

        }
    }
}
