package com.mindyourlovedone.healthcare.DashBoard;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Image;
import com.mindyourlovedone.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.InsuranceHealthCare.SpecialistAdapter;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.DoctorQuery;
import com.mindyourlovedone.healthcare.database.SpecialistQuery;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Specialist;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.IndividualNew;
import com.mindyourlovedone.healthcare.utility.CallDialog;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentPhysician extends Fragment implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    ImageView imgRight;
    View rootview;
    RecyclerView lvSpecialist;
    ArrayList<Specialist> specialistList;
    RelativeLayout llAddSpecialist;
    Preferences preferences;
    TextView txtTitle;
    TextView txtAdd, txtMsg, txtFTU;
    DBHelper dbHelper;
    SpecialistAdapter specialistAdapter;
    RelativeLayout rlGuide;
    FloatingActionButton floatProfile;
    ImageView floatAdd,floatOptions;
    TextView txthelp; ImageView imghelp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_physician, null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        DoctorQuery d = new DoctorQuery(getActivity(), dbHelper);
        SpecialistQuery s = new SpecialistQuery(getActivity(), dbHelper);
    }

    private void setListData() {
        if (specialistList.size() != 0) {
            Collections.sort(specialistList, new Comparator<Specialist>() {
                @Override
                public int compare(Specialist o1, Specialist o2) {
                    return o1.getType().compareTo(o2.getType());
                }
            });
            specialistAdapter = new SpecialistAdapter(getActivity(), specialistList, FragmentPhysician.this);
            lvSpecialist.setAdapter(specialistAdapter);
            lvSpecialist.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            imghelp .setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
        } else {
            lvSpecialist.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
            imghelp .setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddSpecialist.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
    }

    private void initUI() {
        floatAdd = rootview.findViewById(R.id.floatAdd);
        floatOptions = rootview.findViewById(R.id.floatOptions);
        floatProfile = rootview.findViewById(R.id.floatProfile);
        txtMsg = rootview.findViewById(R.id.txtMsg);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);
//        String msg = "To <b>add</b> information click the green bar at the bottom of the screen.If the person is in your <b>Contacts</b> click the gray bar on the top right side of your screen" +
//                "<br><br>" +
//                "To <b>save</b> information click the green bar at the bottom of the screen." +
//                "<br><br>" +
//                "To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits click the <b>green bar</b> at the bottom of the screen." +
//                "<br><br>" +
//                "To <b>make an automated phone call</b> or <b>delete</b> the entry <b>swipe right to left</b> arrow symbol." +
//                "<br><br>" +
//                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen." +
//                "<br><br>" +
//                "To <b>add a picture</b> click the picture of the <b>pencil</b> and" +
//                "either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again.Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card";
//        txtMsg.setText(Html.fromHtml(msg));

        //nikita
        final RelativeLayout relMsg = rootview.findViewById(R.id.relMsg);
       /* TextView txt61 = rootview.findViewById(R.id.txtPolicy61);
        TextView txt62 = rootview.findViewById(R.id.txtPolicy62);
        TextView txt63 = rootview.findViewById(R.id.txtPolicy63);
        TextView txt64 = rootview.findViewById(R.id.txtPolicy64);
        TextView txt65 = rootview.findViewById(R.id.txtPolicy65);
        TextView txt66 = rootview.findViewById(R.id.txtPolicy66);
        TextView txt67 = rootview.findViewById(R.id.txtPolicy67);
        ImageView img67 = rootview.findViewById(R.id.img67);

        //shradha
        txt61.setText(Html.fromHtml("To <b>add</b> information click the green bar at the bottom of the screen. If the entity is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load data.\n\n"));
        txt62.setText(Html.fromHtml("To <b>save</b> information click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil</b>. To save your edits <b>click</b> the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>make an automated phone call</b> or <b>delete</b> the entry, left swipe the arrow symbol on the <b>right side</b> of the screen.\n\n "));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data in each section click the three dots on the top right side of the screen.\n\n"));
        txt66.setText(Html.fromHtml("To <b>add a picture</b> click the <b>picture</b> of the <b>pencil</b> and either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again. Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card.\n\n"));
        txt67.setText(Html.fromHtml("\n"));
        img67.setVisibility(View.GONE);
*/
        txtFTU = rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmerInstruc = new Intent(getActivity(), InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "PhysicianInstruction");
                startActivity(intentEmerInstruc);
//                txtMsg.setVisibility(View.VISIBLE);
              //  relMsg.setVisibility(View.VISIBLE);//nikita
            }
        });
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Primary Physician");
        rlGuide = rootview.findViewById(R.id.rlGuide);
        imgRight = getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddSpecialist = rootview.findViewById(R.id.llAddSpecialist);
        llAddSpecialist.setVisibility(View.INVISIBLE);
        lvSpecialist = rootview.findViewById(R.id.lvSpecialist);

        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvSpecialist.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvSpecialist.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        //or
        lvSpecialist.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...

        if (specialistList.size() != 0 || specialistList != null) {
            setListData();
        }


    }

    public void callUser(Specialist item) {
        ArrayList<ContactData>  phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),item.getId(), "Primary");


        if (phonelist.size()>0)
        {
            CallDialog c = new CallDialog();
            c.showCallDialogs(getActivity(), phonelist);
        }else {
            Toast.makeText(getActivity(), "You have not added phone number for call", Toast.LENGTH_SHORT).show();
        }
        /*String mobile = item.getOfficePhone();
        String hphone = item.getHourPhone();
        String wPhone = item.getOtherPhone();

        if (mobile.length() != 0 || hphone.length() != 0 || wPhone.length() != 0) {
            CallDialog c = new CallDialog();
            c.showCallDialog(getActivity(), mobile, hphone, wPhone);
        } else {
            Toast.makeText(getActivity(), "You have not added phone number for call", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void deleteSpecialist(final Specialist item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = SpecialistQuery.deleteRecord(item.getId(), 1);
                // boolean flags=SpecialistQuery.deleteRecord(item.getUnique());
                if (flag == true) {//Shradha delete whole record and image
                    ArrayList<Specialist> specialistList = new ArrayList<>();
                    //specialistList = item.getImage();
                    for (int i = 0; i < specialistList.size(); i++) {
                        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH) + specialistList.get(i).getImage());//nikita
                        if (imgFile.exists()) {
                            imgFile.delete();
                        }
                    }
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

    private void getData() {
        specialistList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);

       /*specialistList=new ArrayList<>();

        Specialist P1=new Specialist();
        P1.setName("Dr. John");
        P1.setType("Orthopedic");
        P1.setAddress("#203,10 los Street, los Angeles, California.");
        P1.setImage(R.drawable.doct);
        P1.setPhone("789-789-5236");


        Specialist P2=new Specialist();
        P2.setName("Dr. James");
        P2.setType("Neuro Surgeon");
        P2.setAddress("#204,10 top Street, los Angeles, California.");
        P2.setImage(R.drawable.docto);
        P2.setPhone("987-789-5236");

        Specialist P3=new Specialist();
        P3.setName("Dr. Smith");
        P3.setType("Neuro Surgeon");
        P3.setAddress("#205,10 Left Street, los Angeles, California.");
        P3.setImage(R.drawable.doctors);
        P3.setPhone("789-789-5236");

        specialistList.add(P1);
       specialistList.add(P2);
        specialistList.add(P3);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatAdd:
                showFloatDialog();
               /* preferences.putString(PrefConstants.SOURCE, "Emergency");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                startActivity(i);*/
                break;
            case R.id.floatOptions:
                showFloatPdfDialog();
                break;
            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                //   intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentDashboard.putExtra("c", 1);//Profile Data
                startActivity(intentDashboard);
                break;
          /*  case R.id.llAddSpecialist:
                // hideSoftKeyboard();
                preferences.putString(PrefConstants.SOURCE, "Physician");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                startActivity(i);
                // DialogManager dialogManager=new DialogManager(new FragmentSpecialist());
                // dialogManager.showCommonDialog("Add?","Do you want to add new specialist?",getActivity(),"ADD_SPECIALIST",null);
                break;*/
            case R.id.imgRight:
                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "PhysicianInstruction");
                startActivity(i);

//                final String RESULT = Environment.getExternalStorageDirectory()
//                        + "/mylopdf/";
//                File dirfile = new File(RESULT);
//                dirfile.mkdirs();
//                File file = new File(dirfile, "Physician.pdf");
//                if (file.exists()) {
//                    file.delete();
//                }
//
//                new Header().createPdfHeader(file.getAbsolutePath(),
//                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                preferences.copyFile("ic_launcher.png", getActivity());
//                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
//                Header.addEmptyLine(1);
//                Header.addusereNameChank("Primary Physician");//preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(1);
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
//              /*  new Header().createPdfHeader(file.getAbsolutePath(),
//                        "Primary Physician");
//                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(2);*/
//
//
//                ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
//                new Individual(specialistsList, "Physician");
//                Header.document.close();
//
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
//                                + "/Physician.pdf";
//                        switch (itemPos) {
//                            case 0: //View
//                                if (preferences.getInt(PrefConstants.CONNECTED_USERID) == (preferences.getInt(PrefConstants.USER_ID))) {
//                                    StringBuffer result = new StringBuffer();
//                                    result.append(new MessageString().getPhysicianInfo());
//
//                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
//                                            + "/mylopdf/"
//                                            + "/Physician.pdf",
//                                            getActivity(), result);
//
//                                    System.out.println("\n" + result + "\n");
//                                } else {
//                                    StringBuffer result = new StringBuffer();
//                                    result.append(new MessageString().getPhysicianInfo());
//
//                                    new PDFDocumentProcess(path,
//                                            getActivity(), result);
//
//                                    System.out.println("\n" + result + "\n");
//                                }
//                                break;
//                            case 1://Email
//                                File f = new File(path);
//                                preferences.emailAttachement(f, getActivity(), "Primary Physician");
//                                break;
//                          /*  case 2://fax
//                                new FaxCustomDialog(getActivity(), path).show();
//                                break;*/
//                            case 2://FTU
//                                Intent i = new Intent(getActivity(), InstructionActivity.class);
//                                i.putExtra("From", "PhysicianInstruction");
//                                startActivity(i);
//                                break;
//                        }
//                    }
//
//                });
//                builder.create().show();
                break;
        }
    }

    private void showFloatPdfDialog() {
        final String RESULT = Environment.getExternalStorageDirectory()
                + "/mylopdf/";
        File dirfile = new File(RESULT);
        dirfile.mkdirs();
        File file = new File(dirfile, "Physician.pdf");
        if (file.exists()) {
            file.delete();
        }
// Old Pdf varsa
       /* new Header().createPdfHeader(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
        preferences.copyFile("ic_launcher.png", getActivity());
        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
        Header.addEmptyLine(1);
        Header.addusereNameChank("Primary Physician");//preferences.getString(PrefConstants.CONNECTED_NAME));
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

        ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
        for(int i=0;i<specialistsList.size();i++) {
            final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), specialistsList.get(i).getId(),"Physician");
            new Individual("Physician", specialistsList.get(i), phonelists,i);
        }
        Header.document.close();*/

       // New pdf varsa
        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png", getActivity());
        calendar=preferences.addFile("calpdf.png", getActivity());calendarWite=preferences.addFile("calpdf_wite.png", getActivity());
        profile=preferences.addFile("profpdf.png", getActivity()); profileWite=preferences.addFile("profpdf_wite.png", getActivity());

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"PRIMARY PHYSICIAN", calendarWite, profileWite);

        HeaderNew.addusereNameChank("PRIMARY PHYSICIAN");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp=preferences.addFile("emergency_four.png", getActivity());
        ArrayList<Specialist> specialistsList = SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 1);
        for(int i=0;i<specialistsList.size();i++) {
            final ArrayList<ContactData> phonelists= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), specialistsList.get(i).getId(),"Primary");
            new IndividualNew("Physician", specialistsList.get(i), phonelists,i,pp);
        }
        HeaderNew.document.close();

        //--------------------------------------------------------------------
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.activity_transparent_pdf, null);
        final RelativeLayout rlView = dialogview.findViewById(R.id.rlView);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
//   final ImageView floatCancel = dialogview.findViewById(R.id.floatCancel);  // Rahul
        final FloatingActionButton floatViewPdf = dialogview.findViewById(R.id.floatContact);
        floatViewPdf.setImageResource(R.drawable.eyee);
        final FloatingActionButton floatEmail = dialogview.findViewById(R.id.floatNew);
        floatEmail.setImageResource(R.drawable.closee);

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

        floatEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Physician.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Primary Physician");
                dialog.dismiss();

            }
        });

        floatViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/Physician.pdf";

                        if (preferences.getInt(PrefConstants.CONNECTED_USERID) == (preferences.getInt(PrefConstants.USER_ID))) {
                            StringBuffer result = new StringBuffer();
                            result.append(new MessageString().getPhysicianInfo());

                            new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                    + "/mylopdf/"
                                    + "/Physician.pdf",
                                    getActivity(), result);

                            System.out.println("\n" + result + "\n");
                        } else {
                            StringBuffer result = new StringBuffer();
                            result.append(new MessageString().getPhysicianInfo());

                            new PDFDocumentProcess(path,
                                    getActivity(), result);

                            System.out.println("\n" + result + "\n");
                        }
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
                preferences.putString(PrefConstants.SOURCE, "Physician");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB","New");
                startActivity(i);
                dialog.dismiss();
            }
        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(),"Work in progress",Toast.LENGTH_SHORT).show();
                preferences.putString(PrefConstants.SOURCE, "Physician");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB","Contact");
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    public void postCommonDialog() {
        //preferences.putString(PrefConstants.SOURCE,"Speciality");
        Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
