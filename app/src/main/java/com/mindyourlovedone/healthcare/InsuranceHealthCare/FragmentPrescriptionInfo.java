package com.mindyourlovedone.healthcare.InsuranceHealthCare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.DashBoard.AddPrescriptionActivity;
import com.mindyourlovedone.healthcare.DashBoard.FaxActivity;
import com.mindyourlovedone.healthcare.DashBoard.InstructionActivity;
import com.mindyourlovedone.healthcare.DashBoard.PrescriptionInfoAdapter;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.HospitalHealthQuery;
import com.mindyourlovedone.healthcare.database.PrescriptionQuery;
import com.mindyourlovedone.healthcare.model.Hospital;
import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.model.Prescription;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.PrescriptionPdfNew;
import com.mindyourlovedone.healthcare.utility.CallDialog;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

public class FragmentPrescriptionInfo extends Fragment implements View.OnClickListener {
    final String dialog_items[] = {"View", "Email", "User Instructions"};
    ImageView imgRight;
    View rootview;
    RecyclerView lvPrescriptionInfo;
//    ArrayList<Prescription> prescriptonInfoList =new ArrayList<>();
    RelativeLayout llAddPrescriptionInfo;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout rlGuide;
    TextView txtMsg, txtFTU;
    FloatingActionButton floatProfile;
    ImageView floatAdd, floatOptions;
    ScrollView scroll;
    TextView txthelp; ImageView imghelp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_pres_info, null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

//    private void initComponent() {
//        preferences = new Preferences(getActivity());
//        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
//        //  HospitalHealthQuery f = new HospitalHealthQuery(getActivity(), dbHelper);
//    }

    private void setListData() {
        if (PrescriptionList.size() != 0) {
            PrescriptionInfoAdapter hospitalAdapter = new PrescriptionInfoAdapter(getActivity(), PrescriptionList, FragmentPrescriptionInfo.this);
            lvPrescriptionInfo.setAdapter(hospitalAdapter);
            lvPrescriptionInfo.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            imghelp .setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
        } else {
            lvPrescriptionInfo.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
            imghelp .setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
        }
    }

    public void deletePrescription(final Prescription item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = PrescriptionQuery.deleteRecord(item.getUnique());
                if (flag == true) {
                    ArrayList<PrescribeImage> imageList = new ArrayList<>();
                    imageList = item.getPrescriptionImageList();
//                    for(int i=0;i<imageList.size();i++){
//                        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH) + imageList.get(i).getImage());//nikita
//                        if (imgFile.exists()) {
//                            imgFile.delete();
//                        }
//                    }
                    Toast.makeText(getActivity(), "Prescription has been deleted succesfully", Toast.LENGTH_SHORT).show();
                    getData();
                    setListData();
                }
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

    private void initListener() {
        llAddPrescriptionInfo.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
        floatOptions.setOnClickListener(this);

    }

    private void initUI() {
        //shradha
        floatProfile = rootview.findViewById(R.id.floatProfile);
        floatAdd = rootview.findViewById(R.id.floatAdd);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);

        //nikita
        PrescriptionList = new ArrayList<>();
        scroll = rootview.findViewById(R.id.scroll);
        floatOptions = rootview.findViewById(R.id.floatOptions);

        final RelativeLayout relMsg = rootview.findViewById(R.id.relMsg);
        TextView txt61 = rootview.findViewById(R.id.txtPolicy61);
        TextView txt62 = rootview.findViewById(R.id.txtPolicy62);
        TextView txt63 = rootview.findViewById(R.id.txtPolicy63);
        TextView txt64 = rootview.findViewById(R.id.txtPolicy64);
        TextView txt65 = rootview.findViewById(R.id.txtPolicy65);
        TextView txt66 = rootview.findViewById(R.id.txtPolicy66);
        TextView txt67 = rootview.findViewById(R.id.txtPolicy67);
        TextView txt68 = rootview.findViewById(R.id.txtPolicy68);
        ImageView img67 = rootview.findViewById(R.id.img67);
        ImageView img68 = rootview.findViewById(R.id.img68);


        //shradha
        txt61.setText(Html.fromHtml("To <b>add</b> information click the orange bar at the bottom of the screen. If the entity is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load the data.\n\n"));
        txt62.setText(Html.fromHtml("To <b>save</b> information click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits <b>click</b> the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>make an automated phone call</b> or <b>delete</b> the entry, left swipe the arrow symbol on the <b>right side</b> of the screen.\n\n "));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data in each section click the three dots on the top right side of the screen.\n\n"));
        txt66.setText(Html.fromHtml("To <b>add a picture</b> click the <b>picture</b> of the <b>pencil</b> and either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again. Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card.\n\n"));
        txt67.setText(Html.fromHtml("\n\n"));
       /* txt68.setText(Html.fromHtml("\n\n"));
        img67.setVisibility(View.GONE);
        img68.setVisibility(View.GONE);
*/

        // txtMsg = rootview.findViewById(R.id.txtMsg);
        String msg = "To <b>add</b> information click the green bar at the bottom of the screen. If the entity is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load data." +
                "<br><br>" +
                "To <b>save</b> information click the green bar at the bottom of the screen." +
                "<br><br>" +
                "To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits click the <b>green bar</b> at the bottom of the screen." +
                "<br><br>" +
                "To <b>make an automated phone call</b> or <b>delete</b> the entry <b>swipe right to left</b> arrow symbol." +
                "<br><br>" +
                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen." +
                "<br><br>" +
                "To <b>add a picture</b> click the picture of the <b>pencil</b> and" +
                "either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again.Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card";
        // txtMsg.setText(Html.fromHtml(msg));
        txtFTU = rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmerInstruc = new Intent(getActivity(), InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "PrescriptionInstruction");
                startActivity(intentEmerInstruc);
                // relMsg.setVisibility(View.VISIBLE);
            }
        });
        imgRight = getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        rlGuide = rootview.findViewById(R.id.rlGuide);
        llAddPrescriptionInfo = rootview.findViewById(R.id.llAddPrescriptionInfo);
        lvPrescriptionInfo = rootview.findViewById(R.id.lvPrescriptionInfo);
        // setListData();

        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvPrescriptionInfo.setLayoutManager(linearLayoutManager);

//        //add ItemDecoration
//        lvPrescriptionInfo.addItemDecoration(new VerticalSpaceItemDecoration(48));
//
//        //or
//        lvPrescriptionInfo.addItemDecoration(
//                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...
    }

    public void callUser(Hospital item) {
        String mobile = item.getOfficePhone();
        String hphone = item.getMobile();
        String wPhone = item.getOtherPhone();

        if (mobile.length() != 0 || hphone.length() != 0 || wPhone.length() != 0) {
            CallDialog c = new CallDialog();
            c.showCallDialog(getActivity(), mobile, hphone, wPhone);
        } else {
            Toast.makeText(getActivity(), "You have not added phone number for call", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteHospital(final Hospital item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = HospitalHealthQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getData();
                    setListData();
                }
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


    ArrayList<Prescription> PrescriptionList = new ArrayList<>();

    private void getData() {
        PrescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        PrescriptionQuery p = new PrescriptionQuery(getActivity(), dbHelper);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //   intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;
            case R.id.floatAdd:
                Intent is = new Intent(getActivity(), AddPrescriptionActivity.class);
                is.putExtra("IsEdit", false);
                startActivityForResult(is, 100);
                break;
            case R.id.floatOptions:
                showFloatOption();
                break;
            case R.id.imgRight:
                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "PrescriptionInstruction");
                startActivity(i);
//                final String RESULT = Environment.getExternalStorageDirectory()
//                        + "/mylopdf/";
//                File dirfile = new File(RESULT);
//                dirfile.mkdirs();
//                File file = new File(dirfile, "Hospital.pdf");
//                if (file.exists()) {
//                    file.delete();
//                }
//                new Header().createPdfHeader(file.getAbsolutePath(),
//                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                preferences.copyFile("ic_launcher.png", getActivity());
//                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
//                Header.addEmptyLine(1);
//                Header.addusereNameChank("Hospitals, Rehab, Home Care");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(1);
//
//                Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
//
//                Paragraph p = new Paragraph(" ");
//                LineSeparator line = new LineSeparator();
//                line.setOffset(-4);
//                line.setLineColor(BaseColor.LIGHT_GRAY);
//                p.add(line);
//                try {
//                    Header.document.add(p);
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
//                Header.addEmptyLine(1);
//
//              /*  new Header().createPdfHeader(file.getAbsolutePath(),
//                        "Hospitals, Rehab, Home Care");
//
//                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(2);*/
//
//                ArrayList<Hospital> HospitalList = HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
//                new Specialty("Hospital", HospitalList);
//                Header.document.close();
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//                builder.setTitle("");
//
//                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int itemPos) {
//                        String path = Environment.getExternalStorageDirectory()
//                                + "/mylopdf/"
//                                + "/Hospital.pdf";
//                        switch (itemPos) {
//
//                            case 0: // view
//                                StringBuffer result = new StringBuffer();
//                                result.append(new MessageString().getHospitalInfo());
//
//                                new PDFDocumentProcess(path,
//                                        getActivity(), result);
//
//                                System.out.println("\n" + result + "\n");
//
//                                break;
//                            case 1://Email
//                                File f = new File(path);
//                                preferences.emailAttachement(f, getActivity(), "Hospitals And Other Health Preofessional");
//                                break;
//                            case 2://FTU
//                                Intent i = new Intent(getActivity(), InstructionActivity.class);
//                                i.putExtra("From", "PrescriptionInstruction");
//                                startActivity(i);
//                                break;
//                           /* case 2://fax
//                                new FaxCustomDialog(getActivity(), path).show();
//                                break;*/
//                        }
//                    }
//
//                });
//                builder.create().show();
                break;
        }
    }

//    private void setPrescriptionData() {
//        if (PrescriptionList.size() != 0) {
//            lvPrescriptionInfo.setVisibility(View.VISIBLE);
//            rlGuide.setVisibility(View.GONE);
//            scroll.setVisibility(View.GONE);
//        } else {
//            rlGuide.setVisibility(View.VISIBLE);
//            lvPrescriptionInfo.setVisibility(View.GONE);
//            scroll.setVisibility(View.GONE);
//        }
//        PrescriptionInfoAdapter adapter = new PrescriptionInfoAdapter(getActivity(), PrescriptionList, FragmentPrescriptionInfo.this);
//        lvPrescriptionInfo.setAdapter(adapter);
//    }


    private void showFloatOption() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "Prescription.pdf");
        if (file.exists()) {
            file.delete();
        }
        /*new Header().createPdfHeader(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
        preferences.copyFile("ic_launcher.png", getActivity());
                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
        Header.addEmptyLine(1);
        Header.addusereNameChank("Prescription Information");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
               *//* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Prescription");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*//*

        ArrayList<Prescription> prescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

        //     ArrayList<Dosage> DosageList= DosageQuery.fetchAllDosageRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),);

        new PrescriptionPdf(prescriptionList);

        Header.document.close();
*/

        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png", getActivity());
        calendar=preferences.addFile("calpdf.png", getActivity());calendarWite=preferences.addFile("calpdf_wite.png", getActivity());
        profile=preferences.addFile("profpdf.png", getActivity()); profileWite=preferences.addFile("profpdf_wite.png", getActivity());

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"PRESCRIPTION INFORMATION", calendarWite, profileWite);

        HeaderNew.addusereNameChank("PRESCRIPTION INFORMATION");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp=preferences.addFile("pres_one.png", getActivity());
        ArrayList<Prescription> prescriptionList = PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       new PrescriptionPdfNew(prescriptionList,pp);
        HeaderNew.document.close();
//--------------------------------------------------------------------------------------
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent_pdf, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        floatContact.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        floatNew.setImageResource(R.drawable.closee);
        final RelativeLayout rlFloatfax = dialogview.findViewById(R.id.rlFloatfax);
        rlFloatfax.setVisibility(View.VISIBLE);
        final FloatingActionButton floatfax = dialogview.findViewById(R.id.floatfax);
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
        floatfax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Prescription.pdf";

             /* StringBuffer result = new StringBuffer();
                result.append(new MessageString().getInsuranceInfo());
                new PDFDocumentProcess(path,
                        getActivity(), result);*/
                Intent i = new Intent(getActivity(), FaxActivity.class);
                i.putExtra("PATH", path);
                startActivity(i);
                dialog.dismiss();
            }


        });
        floatNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Prescription.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Prescription");

                dialog.dismiss();
            }

        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Prescription.pdf";

                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getInsuranceInfo());
                new PDFDocumentProcess(path,
                        getActivity(), result);
                System.out.println("\n" + result + "\n");

                dialog.dismiss();
            }


        });
    }

    private void showFloatDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        //floatContact.setImageResource(R.drawable.closee);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        // floatNew.setImageResource(R.drawable.eyee);

        TextView txtNew = dialogview.findViewById(R.id.txtNew);
        txtNew.setText(getResources().getString(R.string.AddNew));

        TextView txtContact = dialogview.findViewById(R.id.txtContact);
        txtContact.setText(getResources().getString(R.string.AddContacts));

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
                Toast.makeText(getActivity(), "Please wait still in progress..!!", Toast.LENGTH_SHORT).show();
                /*preferences.putString(PrefConstants.SOURCE, "PrescriptionInfo");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                startActivity(i);*/
                dialog.dismiss();
            }
        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Please wait still in progress..!!", Toast.LENGTH_SHORT).show();
               /* preferences.putString(PrefConstants.SOURCE, "PrescriptionInfo");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                startActivity(i);*/
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}


