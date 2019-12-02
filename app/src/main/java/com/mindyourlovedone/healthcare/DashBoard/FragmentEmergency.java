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
import android.text.Html;
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
import com.mindyourlovedone.healthcare.Connections.ConnectionAdapter;
import com.mindyourlovedone.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.ContactData;
import com.mindyourlovedone.healthcare.model.Emergency;
import com.mindyourlovedone.healthcare.pdfCreation.MessageString;
import com.mindyourlovedone.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedone.healthcare.pdfdesign.HeaderNew;
import com.mindyourlovedone.healthcare.pdfdesign.IndividualNew;
import com.mindyourlovedone.healthcare.utility.CallDialog;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentEmergency extends Fragment implements View.OnClickListener {
    private static final int VERTICAL_ITEM_SPACE = 0;
    final CharSequence[] dialog_items = {"View", "Email", "User Instructions"};
    ImageView imgRight;
    View rootview;
    RecyclerView lvEmergency;//Changes done by nikita on 18/6/18
    ArrayList<Emergency> emergencyList;
    TextView txtAdd, txtMsg, txtFTU;
    RelativeLayout llAddConn;
    TextView txtTitle;
    ImageView imgNoti;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;
    EmergencyAdapter emergencyAdapter;
    RelativeLayout rlGuide;
    String finalText = "";
    FloatingActionButton floatProfile;
    ImageView floatAdd,floatOptions;
    TextView txthelp; ImageView imghelp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_emergency, null);
        initComponent();
        getData();
        initUI();
        initListener();
        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), preferences.getString(PrefConstants.CONNECTED_USERDB));
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        ContactDataQuery c=new ContactDataQuery(getActivity(),dbHelper);
    }

    private void setListData() {
     /*   emergencyAdapter = new EmergencyAdapter(getActivity(), emergencyList);
        lvEmergency.setAdapter(emergencyAdapter);*/
        if (emergencyList.size() != 0) {
            emergencyAdapter = new EmergencyAdapter(getActivity(), emergencyList, FragmentEmergency.this);//Changes done by nikita on 18/6/18
            lvEmergency.setAdapter(emergencyAdapter);
            imghelp .setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
            rlGuide.setVisibility(View.GONE);
            lvEmergency.setVisibility(View.VISIBLE);
        } else {
            imghelp .setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.VISIBLE);
            lvEmergency.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        floatProfile.setOnClickListener(this);
        floatOptions.setOnClickListener(this);
        floatAdd.setOnClickListener(this);
    }

    private void initUI() {
        floatProfile = rootview.findViewById(R.id.floatProfile);
        floatAdd = rootview.findViewById(R.id.floatAdd);
        floatOptions = rootview.findViewById(R.id.floatOptions);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);
//        String msg = "To <b>add</b> information click the green bar at the bottom of the screen. If the person is in your <b>Contacts</b> click the gray  bar on the top right side of your screen." +
//                "<br><br>" +
//                "To <b>save</b> information click the green bar at the bottom of the screen." +
//                "<br><br>" +
//                "To <b>edit</b> information click the picture of the <b>pencil.</b> To save your edits click the <b>green bar</b> at the bottom of the screen." +
//                "<br><br>" +
//                "To <b>make an automated telephone call</b> or <b>delete</b> the entry <b>swipe right to left</b> the arrow symbol on the right side." +
//                "<br><br>" +
//                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen.";
//        txtMsg.setText(Html.fromHtml(msg));

        //shradha
        final RelativeLayout relMsg = rootview.findViewById(R.id.relMsg);
        TextView txt61 = rootview.findViewById(R.id.txtPolicy61);
        TextView txt62 = rootview.findViewById(R.id.txtPolicy62);
        TextView txt63 = rootview.findViewById(R.id.txtPolicy63);
        TextView txt64 = rootview.findViewById(R.id.txtPolicy64);
        TextView txt65 = rootview.findViewById(R.id.txtPolicy65);

        //shradha
      /*  txt61.setText(Html.fromHtml("To add information click the green bar at the bottom of the screen. If the person is in your Contacts click the gray bar on the top right side of your screen."));
        txt62.setText(Html.fromHtml("To <b>save</b> information click the green bar at the bottom of the screen."));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil.</b> To save your edits click the <b>green bar</b> at the bottom of the screen."));
        txt64.setText(Html.fromHtml("To <b>make an automated telephone call</b> or <b>delete</b> the entry <b>swipe right to left</b> the arrow symbol on the right side."));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen."));
*/
        txt61.setText(Html.fromHtml("To <b>add</b> information click the green bar at the bottom of the screen. If the person is in your <b>Contacts</b> click the gray bar on the top right side of your screen.\n\n"));
        txt62.setText(Html.fromHtml("To <b>save</b> information click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt63.setText(Html.fromHtml("To <b>edit</b> information click the picture of the <b>pencil</b>. To save your edits click the <b>SAVE</b> on the top right side of the screen.\n\n"));
        txt64.setText(Html.fromHtml("To <b>make an automated phone call</b> or <b>delete</b> the entry left <b>swipe right to left</b> the arrow symbol on the right side.\n\n"));
        txt65.setText(Html.fromHtml("To <b>view a report</b> or to <b>email</b> the data in each section click the three dots on the top right side of the screen.\n\n"));

        txtFTU = rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmerInstruc = new Intent(getActivity(), InstructionActivity.class);
                intentEmerInstruc.putExtra("From", "EmergencyInstruction");
                startActivity(intentEmerInstruc);
//                txtMsg.setVisibility(View.VISIBLE);
                relMsg.setVisibility(View.GONE);//nikita
            }
        });
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Emergency Contacts &\nHealth Care Proxy Agents");
        rlGuide = rootview.findViewById(R.id.rlGuide);
        imgRight = getActivity().findViewById(R.id.imgRight);
        /*imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);*/
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddConn = rootview.findViewById(R.id.llAddConn);

        //Changes done by nikita on 18/6/18
        lvEmergency = rootview.findViewById(R.id.lvEmergency);
        // Layout Managers:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvEmergency.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvEmergency.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        //or
        lvEmergency.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...

        if (emergencyList.size() != 0 || emergencyList != null) {
            setListData();
        }
//        lvEmergency.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
//        SwipeMenuCreation s=new SwipeMenuCreation();
//        SwipeMenuCreator creator=s.createMenu(getActivity());
//        lvEmergency.setMenuCreator(creator);
//        lvEmergency.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                Emergency item = emergencyList.get(position);
//                switch (index) {
//                    case 0:
//                        // open
//                        //  open(item);
//                        callUser(item);
//                        break;
//                    case 1:
//                        // delete
//                            deleteEmergency(item);
//                        break;
//                }
//                return false;
//            }
//        });
    }

    public void callUser(Emergency item) {
      ArrayList<ContactData>  phonelist = ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),item.getId(), "Emergency");


if (phonelist.size()>0)
{
    CallDialog c = new CallDialog();
    c.showCallDialogs(getActivity(), phonelist);
}else {
    Toast.makeText(getActivity(), "You have not added phone number for call", Toast.LENGTH_SHORT).show();
}
       /* if (mobile.length() != 0 || hphone.length() != 0 || wPhone.length() != 0) {
            CallDialog c = new CallDialog();
            c.showCallDialog(getActivity(), mobile, hphone, wPhone);
        } else {
            Toast.makeText(getActivity(), "You have not added phone number for call", Toast.LENGTH_SHORT).show();
        }*/
    }
   /* private void showCallDialog(final Context context, String mobile, String hphone, String wphone) {
        //   String text=mobile;
            if (mobile.contains("-")) {
                mobile = mobile.replaceAll("-", "");
            }
            if (hphone.contains("-")) {
                hphone = hphone.replaceAll("-", "");
           }
            if (wphone.contains("-")) {
                wphone = wphone.replaceAll("-", "");
            }
           // System.out.println("" + text);
            try {
                Double.parseDouble(mobile);
                Double.parseDouble(hphone);
                Double.parseDouble(wphone);
            } catch (NumberFormatException ex) {
                System.out.println("Some Mistake");
            }
         String[] num={mobile,hphone,wphone};
         final ArrayList<String> a=new ArrayList();
        *//*final String finalMobile = mobile;
        final String finalHphone = hphone;
        final String finalWphone = wphone;*//*
        for (int i=0;i<num.length;i++)
        {
            if (num[i].length()!=0)
            {
                a.add(num[i]);
            }

        }
     if (a.size()==1)
     {
         String value=a.get(0);
         new AlertDialog.Builder(context)
                 .setTitle("Calling Alert")
                 .setMessage("Do you want to call this number? ")
                 .setPositiveButton(a.get(0),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(0));
                             }
                         })
                 .setCancelable(true).show();
     }
     else if(a.size()==2)
     {
         new AlertDialog.Builder(context)
                 .setTitle("Calling Alert")
                 .setMessage("Do you want to call this number? ")
                 .setPositiveButton(a.get(0),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(0));
                             }
                         })

                 .setNegativeButton(a.get(1),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(1));
                             }
                         }).setCancelable(true).show();
     }
     else if (a.size()==3) {
         new AlertDialog.Builder(context)
                 .setTitle("Calling Alert")
                 .setMessage("Do you want to call this number? ")
                 .setPositiveButton(a.get(0),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(0));
                             }
                         })
                 .setNeutralButton(a.get(1), new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         onCall(a.get(1));
                     }
                 })
                 .setNegativeButton(a.get(2),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(2));
                             }
                         }).setCancelable(true).show();
     }

    }*/

    public void deleteEmergency(final Emergency item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = MyConnectionsQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Emergency Contact has been deleted succesfully", Toast.LENGTH_SHORT).show();
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
        emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
      /* //emergencyList = MyConnectionsQuery.fetchAllRecord();
emergencyList=new ArrayList<>();
        Emergency P1 = new Emergency();
        P1.setName("Clark Smith");
        P1.setRelationType("Self");
        P1.setImage(R.drawable.father);
        P1.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P1.setPhone("789566236");

        Emergency P2 = new Emergency();
        P2.setName("Cherry Smith");
        P2.setRelationType("Wife");
        P2.setImage(R.drawable.mother);
        P2.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P2.setPhone("789566236");



        emergencyList.add(P1);
        emergencyList.add(P2);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatOptions:
              //  Toast.makeText(getActivity(), "Yet to come..!!", Toast.LENGTH_SHORT).show();
                  showFloatPdfDialog();
                break;

            case R.id.floatProfile:
                Intent intentDashboard = new Intent(getActivity(), BaseActivity.class);
                intentDashboard.putExtra("c", 1);//Profile Data
                // intentDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //  intentDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDashboard);
                break;
            case R.id.floatAdd:
                showFloatDialog();
               /* preferences.putString(PrefConstants.SOURCE, "Emergency");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                startActivity(i);*/
                break;

            case R.id.imgRight:

                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "EmergencyInstruction");
                startActivity(i);

//                final String RESULT = Environment.getExternalStorageDirectory()
//                        + "/mylopdf/";
//                File dirfile = new File(RESULT);
//                dirfile.mkdirs();
//                File file = new File(dirfile, "Emergency.pdf");
//                if (file.exists()) {
//                    file.delete();
//                }
//
//                new Header().createPdfHeader(file.getAbsolutePath(),
//                        "" + preferences.getString(PrefConstants.CONNECTED_NAME));
//                preferences.copyFile("ic_launcher.png", getActivity());
//                Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
//                Header.addEmptyLine(1);
//                Header.addusereNameChank("Emergency Contacts & \nHealth Care Proxy Agent");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
///*
//                new Header().createPdfHeader(file.getAbsolutePath(),
//                        "Emergency Contacts");
//                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
//                Header.addEmptyLine(2);*/
//
//
//                ArrayList<Emergency> emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
//             final ArrayList<ContactData> phonelist= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),preferences.getInt(PrefConstants.ID),"Emergency");
//
//                new Individual("Emergency", emergencyList, phonelist);
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
//                                + "/Emergency.pdf";
//                        switch (itemPos) {
//                            case 0: //View
//                                StringBuffer result = new StringBuffer();
//                                result.append(new MessageString().getEmergencyInfo());
//
//                                new PDFDocumentProcess(path,
//                                        getActivity(), result);
//
//                                System.out.println("\n" + result + "\n");
//                                break;
//                            case 1://Email
//                                File f = new File(path);
//                                preferences.emailAttachement(f, getActivity(), "Emergency Contact");
//                                break;
//                           /* case 2://Fax
//
//                               // new FaxCustomDialog(getActivity(), path).show();
//                                break;*/
//                            case 2://FTU
//                                Intent i = new Intent(getActivity(), InstructionActivity.class);
//                                i.putExtra("From", "EmergencyInstuction");
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
        File file = new File(dirfile, "EMERGENCYCONTACTS.pdf");
        if (file.exists()) {
            file.delete();
        }
//Old Pdsf code varsa
       /* new Header().createPdfHeader(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME));
        preferences.copyFile("ic_launcher.png", getActivity());
        Header.addImage("/sdcard/MYLO/images/" + "ic_launcher.png");
        Header.addEmptyLine(1);
        Header.addusereNameChank("Emergency Contacts & \nHealth Care Proxy Agent");//preferences.getString(PrefConstants.CONNECTED_NAME));
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

        ArrayList<Emergency> emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
        for(int i=0;i<emergencyList.size();i++) {
            final ArrayList<ContactData> phonelist= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), emergencyList.get(i).getId(),"Emergency");
            new Individual("Emergency", emergencyList.get(i), phonelist,i);
        }
        Header.document.close();
*/

//New Pdf Code varsa
        Image pdflogo = null,calendar= null,profile= null,calendarWite= null,profileWite= null;
        pdflogo=preferences.addFile("pdflogo.png", getActivity());
        calendar=preferences.addFile("calpdf.png", getActivity());calendarWite=preferences.addFile("calpdf_wite.png", getActivity());
        profile=preferences.addFile("profpdf.png", getActivity()); profileWite=preferences.addFile("profpdf_wite.png", getActivity());

        new HeaderNew().createPdfHeaders(file.getAbsolutePath(),
                "" + preferences.getString(PrefConstants.CONNECTED_NAME),preferences.getString(PrefConstants.CONNECTED_PATH) + preferences.getString(PrefConstants.CONNECTED_PHOTO),pdflogo,calendar,profile,"EMERGENCY CONTACTS & HEALTH CARE PROXY AGENTS", calendarWite, profileWite);

        HeaderNew.addusereNameChank("EMERGENCY CONTACTS & HEALTH CARE PROXY AGENTS");//preferences.getString(PrefConstants.CONNECTED_NAME));
        HeaderNew.addEmptyLine(1);
        Image pp = null;
        pp=preferences.addFile("emergency_three.png", getActivity());
        ArrayList<Emergency> emergencyList = MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), 2);
        for(int i=0;i<emergencyList.size();i++) {
            final ArrayList<ContactData> phonelist= ContactDataQuery.fetchContactRecord(preferences.getInt(PrefConstants.CONNECTED_USERID), emergencyList.get(i).getId(),"Emergency");
            new IndividualNew("Emergency", emergencyList.get(i), phonelist,i,pp);
        }
        HeaderNew.document.close();

        //--------------------------------------
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
                        + "/EMERGENCYCONTACTS.pdf";
                File f = new File(path);
                preferences.emailAttachement(f, getActivity(), "Emergency Contact");
                dialog.dismiss();

            }
        });

        floatViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory()
                        + "/mylopdf/"
                        + "/EMERGENCYCONTACTS.pdf";
                StringBuffer result = new StringBuffer();
                result.append(new MessageString().getEmergencyInfo());

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
                preferences.putString(PrefConstants.SOURCE, "Emergency");
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
                preferences.putString(PrefConstants.SOURCE, "Emergency");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB","Contact");
                startActivity(i);
                dialog.dismiss();
            }
        });
    }


   /* private void emailAttachement(File f) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "MIND YOUR ELDERS"); // subject
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ""); // Body

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

        emailIntent.setType("application/email");

        getActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }*/

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }


    /*private void onCall(String finalMobile) {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                   getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + finalMobile)));
        }
    }*/
}
