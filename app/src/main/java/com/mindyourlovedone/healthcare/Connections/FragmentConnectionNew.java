package com.mindyourlovedone.healthcare.Connections;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.InstructionActivity;
import com.mindyourlovedone.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedone.healthcare.DashBoard.UserInsActivity;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by varsha on 8/26/2017.
 */

public class FragmentConnectionNew extends Fragment implements View.OnClickListener {
    final CharSequence[] delete_backup = {"Delete Profile", "Share Profile"};
    final CharSequence[] backup_profile = {"Share Profile"};
    View rootview;
    GridView lvConnection;
    ListView lvSelf;
    TextView txtUser, txtRelation;
   // FloatingActionButton fab;
    ImageView fab;
    LinearLayout llSelf;
    ImageView imgSelfFolder, imgSelf, imgBacks;
    ArrayList<RelativeConnection> connectionList;
    TextView txtAdd, txtMsg, txtFTU, txtStep1, txtStep2, txtStep3, txtStep4, txtStep22, txtStep55, txtStep555, txtStep5, txtStep6, txtStep7, txtStep8;
    //RelativeLayout llAddConn;
    TextView txtTitle, txtName,txtRel, txtDrawerName;
    ImageView imgNoti, imgProfile, imgLogo, imgPdf, imgDrawerProfile, imgRight, imgR;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;
    // PersonalInfo personalInfo;
    RelativeLayout leftDrawer, rlMsg,rlSelf;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    RelativeLayout rlGuide;
    RelativeConnection connection;
    TextView txthelp,txtYour; ImageView imghelp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_connection_new, null);
        initComponent();

        // getProfile();

        // getData();
        initUI();
        initListener();
        initImageLoader();
        return rootview;
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.profile_darkbluecolor)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(), "MASTER");
        // PersonalInfoQuery p = new PersonalInfoQuery(getActivity(), dbHelper);
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
    }

    public void setListData() {
        if (connectionList.size() != 0) {
            connectionAdapter = new ConnectionAdapter(getActivity(), connectionList);
            lvSelf.setAdapter(connectionAdapter);
            imghelp .setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
            lvSelf.setVisibility(View.VISIBLE);
          //  if (connectionList.size() > 1) {
                rlGuide.setVisibility(View.GONE);
txtYour.setVisibility(View.VISIBLE);
         //   } else {


          //  }
        }
        else
        {
            imghelp .setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.VISIBLE);
            txtYour.setVisibility(View.GONE);
            lvSelf.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        //llAddConn.setOnClickListener(this);
        imgLogo.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        fab.setOnClickListener(this);
        imgSelfFolder.setOnClickListener(this);
        rlSelf.setOnClickListener(this);
        imgSelf.setOnClickListener(this);
    }

    private void initUI() {
        lvSelf = rootview.findViewById(R.id.lvSelf);
        rlSelf= rootview.findViewById(R.id.rlSelf);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);
        txtYour=rootview.findViewById(R.id.txtYour);
        fab = rootview.findViewById(R.id.fab);
       // fab.setImageResource(R.drawable.ic_add);
        llSelf = rootview.findViewById(R.id.llSelf);
        imgSelfFolder = rootview.findViewById(R.id.imgSelfFolder);
        imgSelf = rootview.findViewById(R.id.imgSelf);
        imgBacks= getActivity().findViewById(R.id.imgBacks);
        imgBacks.setVisibility(View.GONE);
        txtUser = rootview.findViewById(R.id.txtUser);
        txtRelation = rootview.findViewById(R.id.txtRelation);

        imgR = getActivity().findViewById(R.id.imgR);
        imgR.setVisibility(View.GONE);
        imgRight = getActivity().findViewById(R.id.imgRight);
        imgRight.setVisibility(View.VISIBLE);
        // txtMsg = rootview.findViewById(R.id.txtMsg);
        rlMsg = rootview.findViewById(R.id.rlMsg);
       /* txtStep1 = rootview.findViewById(R.id.txtStep1);
        txtStep1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        txtStep1.setGravity(Gravity.START | Gravity.END);

        txtStep2 = rootview.findViewById(R.id.txtStep2);
        txtStep2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        txtStep2.setGravity(Gravity.START | Gravity.END);

        txtStep3 = rootview.findViewById(R.id.txtStep3);
        txtStep4 = rootview.findViewById(R.id.txtStep4);
        txtStep5 = rootview.findViewById(R.id.txtStep5);
        txtStep55 = rootview.findViewById(R.id.txtStep55);
        txtStep555 = rootview.findViewById(R.id.txtStep555);
        txtStep22 = rootview.findViewById(R.id.txtStep22);
        txtStep6 = rootview.findViewById(R.id.txtStep6);
        txtStep7 = rootview.findViewById(R.id.txtStep7);
        txtStep8 = rootview.findViewById(R.id.txtStep8);


        txtStep1.setText(Html.fromHtml("<b>Welcome to MYLO</b> –  This App allows you to maintain a digital library of critical documents and medical information for you and your loved ones. There are 6 main sections. The App provides unlimited profiles, pdf reports, email and fax functionality, and the ability to share profiles and eliminate the need to re-enter data. \n\n"));
        txtStep2.setText(Html.fromHtml("<b>Getting Started</b> - Before you begin we would like to explain the data entry process. Except for this Profiles page, each screen has a top bar which includes the name of the screen, an arrow back button on the top left, three dots on the top right (which allows you to view or email the data), and a save button on data entry screens. \n\n"));
        txtStep22.setText(Html.fromHtml("\n\nFirst time Users will see First Time User Instructions directly on the screen. After adding information. the User can access User Instructions by clicking on the three buttons. A User Guide is also available from the dropdown Menu located on the top left of the Profiles page. \n\n"));
        txtStep3.setText(Html.fromHtml("\n\n<b>A reminder to all Users</b> - MYLO is a native to your phone. This means that we do not have access to your information. If you forget the name and email address used for this app then the data will be lost. It’s important that you  remember this information and to periodically back up the and or send yourself the pdf reports. Backup instructions are included on the Menu page.\n\n"));
        txtStep4.setText(Html.fromHtml("<b>MYLO Would Like to Access Your Contacts and MYLO Would Like to Access Your Camera</b> - This message does NOT mean that the company has access to your information, it simply allows the APP to utilise the functions of your phone and provide the USER with a better experience. \n\n"));
        txtStep5.setText(Html.fromHtml("<b>Adding a Profile</b> - click the plus box. You will see two options. Create New and Import from Dropbox. \n\n"));
        txtStep55.setText(Html.fromHtml("<b>Option 1</b> : Create New. You will be brought to the Add Profile  Screen. You can type in the new profile or if the person is in your Contacts then click the gray bar on the top right side of your screen to load information. Once completed click either Save (top right of screen) or Add Profile (bottom of screen).\n\n"));
        txtStep555.setText(Html.fromHtml("<b>Option 2</b> : Import from Dropbox. Using this feature you can upload a profile from Dropbox. If you click that option, you will be provided additional instructions. Once completed click either Save (top right of screen) or Add Profile (bottom of screen). \n\n"));
        txtStep6.setText(Html.fromHtml("There are <b>three</b> required elements to create a Profile –  <b>name, relationship</b>, and an <b>email</b> address.\n\n"));
        txtStep7.setText(Html.fromHtml("<b>To Share a Profile –</b> Long press on the Profile Box and you will be able to upload the Profile to your Dropbox account.\n\n"));
        txtStep8.setText(Html.fromHtml("<b>To Delete a Profile –</b> Long Press on the Profile Box.\n\n"));
*/

      /*  String msg1 = "" + getRe
      sources().getString(R.string.connection_info);
        txtStep1.setText(Html.fromHtml(msg1));

        String msg3 = "" + getResources().getString(R.string.step1_three);
        txtStep3.setText(Html.fromHtml(msg3));*/
        //txtMsg.setText(Html.fromHtml(msg));


        txtFTU = rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserIns = new Intent(getActivity(), UserInsActivity.class);
                intentUserIns.putExtra("From", "Profile");
                startActivity(intentUserIns);
                // txtMsg.setVisibility(View.VISIBLE);
            }
        });
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Profiles");
        imgPdf = getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgProfile = getActivity().findViewById(R.id.imgProfile);
        txtName = getActivity().findViewById(R.id.txtName);
        txtRel = getActivity().findViewById(R.id.txtRel);
        leftDrawer = getActivity().findViewById(R.id.leftDrawer);
        txtDrawerName = leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = leftDrawer.findViewById(R.id.imgDrawerProfile);
        txtName.setVisibility(View.GONE);
        txtRel.setVisibility(View.GONE);
        imgProfile.setVisibility(View.GONE);
        imgNoti = getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);
        imgLogo = getActivity().findViewById(R.id.imgLogo);
        rlGuide = rootview.findViewById(R.id.rlGuide);
        imgLogo.setVisibility(View.INVISIBLE);
        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;
        //   Toast.makeText(getActivity(), deviceMan + " " + deviceName, Toast.LENGTH_LONG).show();
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        //llAddConn = (RelativeLayout) rootview.findViewById(llAddConn);
        lvConnection = rootview.findViewById(R.id.lvConnection);
       /* if (connectionList.size()!=0||connectionList!=null)
        {
            setListData();
        }*/

rlSelf.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        ShowOptionDialog("Profiles", -1,preferences.getString(PrefConstants.USER_EMAIL));
        return true;
    }
});
        lvSelf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
               // ShowOptionDialog("delete", position);
                //  Toast.makeText(getActivity(),"Long Pressed",Toast.LENGTH_SHORT).show();
                if (position != 0) {
                    if (position != connectionList.size()) {

                        ShowOptionDialog("delete", position, connectionList.get(position).getEmail());

//                        //Shradha Custom dialog for delete and share and share ftu
//                        final Dialog dialogShare = new Dialog(getActivity());
//                        dialogShare.setCancelable(false);
//                        dialogShare.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialogShare.setContentView(R.layout.dialog_share);
//                        TextView txtDelProfile = dialogShare.findViewById(R.id.txtDelProfile);
//                        TextView txtShareProfile = dialogShare.findViewById(R.id.txtShareProfile);
//                        final TextView txtMsgInfo = dialogShare.findViewById(R.id.txtMsgInfo);
//                        ImageView imgInfo = dialogShare.findViewById(R.id.imgInfo);
//                        final boolean flagInfo = true;
//
//                        txtDelProfile.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//                                alert.setTitle("Delete");
//                                alert.setMessage("Do you want to Delete " + connectionList.get(position).getName() + "'s profile?");
//                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        String mail = connectionList.get(position).getEmail();
//                                        mail = mail.replace(".", "_");
//                                        mail = mail.replace("@", "_");
//                                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
//                                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
//                                        deleteConnection(connectionList.get(position).getId());
//                                        dialog.dismiss();
//                                    }
//                                });
//                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        dialog.dismiss();
//                                    }
//                                });
//                                alert.show();
//                                dialogShare.dismiss();
//                            }
//                        });
//
//                        dialogShare.show();
//
//                        txtShareProfile.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent i = new Intent(getActivity(), DropboxLoginActivity.class);
//                                i.putExtra("FROM", "Backup");
//                                i.putExtra("ToDo", "Individual");
//                                i.putExtra("ToDoWhat", "Share");
//                                String mail = connectionList.get(position).getEmail();
//                                mail = mail.replace(".", "_");
//                                mail = mail.replace("@", "_");
//                                preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
//                                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
//                                startActivity(i);
//                                dialogShare.dismiss();
//                            }
//                        });
//
//                        imgInfo.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (flagInfo) {
//                                    txtMsgInfo.setVisibility(View.VISIBLE);
//                                } else {
//                                    txtMsgInfo.setVisibility(View.GONE);
//                                }
//                            }
//                        });



                       /* AlertDialog.Builder builders = new AlertDialog.Builder(getActivity());
                        builders.setTitle("");
                        builders.setItems(delete_backup, new DialogInterface.OnClickListener() {
                        */  /*  public void onClick(DialogInterface dialog, int itemPos) {
                                switch (itemPos) {
                                    case 0: // delete

                                       *//*
                                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                        alert.setTitle("Delete");
                                        alert.setMessage("Do you want to Delete " + connectionList.get(position).getName() + "'s profile?");
                                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String mail = connectionList.get(position).getEmail();
                                                mail = mail.replace(".", "_");
                                                mail = mail.replace("@", "_");
                                                preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                                                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                                                deleteConnection(connectionList.get(position).getId());
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
                                        dialog.dismiss();
                                        break;
                                    case 1: // backup
                                        Intent i = new Intent(getActivity(), DropboxLoginActivity.class);
                                        i.putExtra("FROM", "Backup");
                                        i.putExtra("ToDo", "Individual");
                                        i.putExtra("ToDoWhat", "Share");
                                        String mail = connectionList.get(position).getEmail();
                                        mail = mail.replace(".", "_");
                                        mail = mail.replace("@", "_");
                                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                                        startActivity(i);
                                        dialog.dismiss();
                                        break;
                                }
                            }*/
                    }/*);
                        builders.create().show();
                    }*/
                } else {
                    ShowOptionDialog("share", position,connectionList.get(position).getEmail());

//                    final Dialog dialogShare = new Dialog(getActivity());
//                    dialogShare.setCanceledOnTouchOutside(false);
//                    dialogShare.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialogShare.setContentView(R.layout.dialog_self_share);
//                    TextView txtShareProfile = dialogShare.findViewById(R.id.txtShareProfile);
//                    ImageView imgInfo = dialogShare.findViewById(R.id.imgInfo);
//                    final TextView txtMsgInfo = dialogShare.findViewById(R.id.txtMsgInfo);
//                    final boolean flagInfo = true;
//                    txtShareProfile.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent i = new Intent(getActivity(), DropboxLoginActivity.class);
//                            i.putExtra("FROM", "Backup");
//                            i.putExtra("ToDo", "Individual");
//                            i.putExtra("ToDoWhat", "Share");
//                            String mail = connectionList.get(position).getEmail();
//                            mail = mail.replace(".", "_");
//                            mail = mail.replace("@", "_");
//                            preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
//                            preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
//                            startActivity(i);
//                            dialogShare.dismiss();
//                        }
//                    });
//                    dialogShare.show();
//                    imgInfo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (flagInfo) {
//                                txtMsgInfo.setVisibility(View.VISIBLE);
//                            } else {
//                                txtMsgInfo.setVisibility(View.GONE);
//                            }
//                        }
//                    });


                   /* AlertDialog.Builder builders = new AlertDialog.Builder(getActivity());
                    builders.setTitle("");
                    builders.setItems(backup_profile,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int itemPos) {
                                    switch (itemPos) {
                                        case 0: // backup
                                            Intent i = new Intent(getActivity(), DropboxLoginActivity.class);
                                            i.putExtra("FROM", "Backup");
                                            i.putExtra("ToDo", "Individual");
                                            i.putExtra("ToDoWhat", "Share");
                                            String mail = connectionList.get(position).getEmail();
                                            mail = mail.replace(".", "_");
                                            mail = mail.replace("@", "_");
                                            preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                                            preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                                            startActivity(i);
                                            dialog.dismiss();
                                            break;
                                    }
                                }
                            });
                    builders.create().show();*/
                }


                return false;
            }
        });

        //lvConnection.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        //SwipeMenuCreation s=new SwipeMenuCreation();
        //SwipeMenuCreator creator=s.createMenu(getActivity());
        //  lvConnection.setMenuCreator(creator);
      /*  lvConnection.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                RelativeConnection item = connectionList.get(position);
                switch (index) {
                    case 0:
                        // open
                      //  open(item);
                        break;
                    case 1:
                        // delete
                        if (item.getEmail().equals(preferences.getString(PrefConstants.USER_EMAIL)))
                        {
                            Toast.makeText(getActivity(),"You can not delete self connection",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            deleteConnection(item);
                        }
                       // mAppList.remove(position);
                       // mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        // set SwipeListener
        lvConnection.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });


*/
    }


    private void ShowOptionDialog(final String Type, final int position, final String email) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_add_contacts, null);

        final TextView textOption1 = dialogview.findViewById(R.id.txtOption1);
        final TextView textOption2 = dialogview.findViewById(R.id.txtOption2);
        TextView textCancel = dialogview.findViewById(R.id.txtCancel);

       if (Type.equalsIgnoreCase("Profiles")) {
           textOption1.setVisibility(View.GONE);
           textOption2.setText("Backup/Share Profile");
        } else {
           textOption1.setText("Delete Profile");
           textOption2.setText("Backup/Share Profile");

        }

        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        textOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (Type.equalsIgnoreCase("delete")) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Delete");
                alert.setMessage("Do you want to Delete " + connectionList.get(position).getName() + "'s profile?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = connectionList.get(position).getEmail();
                        mail = mail.replace(".", "_");
                        mail = mail.replace("@", "_");
                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                        deleteConnection(connectionList.get(position).getId());
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
                //   } else {*/
                    /*Intent i = new Intent(getActivity(), DropboxLoginActivity.class);
                    i.putExtra("FROM", "Backup");
                    i.putExtra("ToDo", "Individual");
                    i.putExtra("ToDoWhat", "Share");
                    String mail = connectionList.get(position).getEmail();
                    mail = mail.replace(".", "_");
                    mail = mail.replace("@", "_");
                    preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                    startActivity(i);*/
                // }
                dialog.dismiss();
            }
        });

        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DropboxLoginActivity.class);
                i.putExtra("FROM", "Backup");
                i.putExtra("ToDo", "Individual");
                i.putExtra("ToDoWhat", "Share");
                String mail = email;
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                startActivity(i);
               /* if (Type.equalsIgnoreCase("delete")) {
                    Intent i = new Intent(getActivity(), DropboxLoginActivity.class);
                    i.putExtra("FROM", "Backup");
                    i.putExtra("ToDo", "Individual");
                    i.putExtra("ToDoWhat", "Share");
                    String mail = connectionList.get(position).getEmail();
                    mail = mail.replace(".", "_");
                    mail = mail.replace("@", "_");
                    preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                    startActivity(i);
                } else {

                }*/
                dialog.dismiss();
            }


        });

        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }


        });


    }
    private void getProfile() {
        dbHelper = new DBHelper(getActivity(), "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        connection = MyConnectionsQuery.fetchOneRecord("Self");
        preferences.putInt(PrefConstants.USER_ID, connection.getId());
        preferences.putString(PrefConstants.USER_NAME, connection.getName());
        preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
        preferences.putString(PrefConstants.USER_EMAIL,connection.getEmail());
    }

    /* private void deleteConnection(RelativeConnection item) {
         boolean flag= MyConnectionsQuery.deleteRecord(item.getEmail());
         if(flag==true)
         {
             Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
             getData();
             setListData();
         }
     }
 */
    public void getData() {
        DBHelper dbHelper = new DBHelper(getActivity(), "MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        ArrayList<RelativeConnection> myconnectionList = MyConnectionsQuery.fetchAllRecord();
        connectionList = new ArrayList<>();
        for (int i = 0; i < myconnectionList.size(); i++) {
            if (!myconnectionList.get(i).getRelationType().equalsIgnoreCase("self")) {
                connectionList.add(myconnectionList.get(i));
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgSelf:
                getProfile();
                Intent intentP= new Intent(getActivity(),ProfileActivity.class);
                preferences.putString(PrefConstants.USER_IMAGE, connection.getPhoto());
                preferences.putInt(PrefConstants.USER_ID, connection.getUserid());
                preferences.putString(PrefConstants.USER_NAME, connection.getName());
                preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
                preferences.putString(PrefConstants.USER_EMAIL,connection.getEmail());
                String mail1 = preferences.getString(PrefConstants.USER_EMAIL);
                mail1 = mail1.replace(".", "_");
                mail1 = mail1.replace("@", "_");
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail1);
                preferences.putInt(PrefConstants.CONNECTED_USERID, connection.getId());
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");

                startActivity(intentP);

                break;

            case R.id.rlSelf:
                imgSelfFolder.performClick();
                break;
            case R.id.imgSelfFolder:
                FragmentDashboard ldf = new FragmentDashboard();
                Bundle args = new Bundle();
                args.putString("Name", preferences.getString(PrefConstants.USER_NAME));
                // args.putString("Address", connectionList.get(position).getAddress());
                args.putString("Relation","Self");
                getProfile();
                //String saveThis = Base64.encodeToString(connectionList.get(position).getPhoto(), Base64.DEFAULT);
                preferences.putString(PrefConstants.USER_IMAGE, preferences.getString(PrefConstants.USER_PROFILEIMAGE));
                preferences.putString(PrefConstants.CONNECTED_NAME,preferences.getString(PrefConstants.USER_NAME));
                preferences.putString(PrefConstants.CONNECTED_USEREMAIL, preferences.getString(PrefConstants.USER_EMAIL));
                preferences.putInt(PrefConstants.CONNECTED_USERID, connection.getId());
                String mail = preferences.getString(PrefConstants.USER_EMAIL);
                mail = mail.replace(".", "_");
                mail = mail.replace("@", "_");
                preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                ldf.setArguments(args);
                ((BaseActivity) getActivity()).callFragment("DASHBOARD", ldf);
                break;
            case R.id.imgRight:
                Intent intentUserIns = new Intent(getActivity(), UserInsActivity.class);
                intentUserIns.putExtra("From", "Profile");
                startActivity(intentUserIns);
//                showInstructionDialog();
                break;
            case R.id.imgLogo:
               /* Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://mindyour-lovedones.com/"));
                startActivity(intent);
               */
                break;
            case R.id.fab:
                //showContactDialog();
                showFloatDialog();
                break;
        }
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    private void showContactDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_add_contacts, null);

        final TextView textOption1 = dialogview.findViewById(R.id.txtOption1);
        final TextView textOption2 = dialogview.findViewById(R.id.txtOption2);
        TextView textCancel = dialogview.findViewById(R.id.txtCancel);

        textOption1.setText("Create New");
        textOption2.setText("Import From Dropbox");

        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        textOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFloatDialog();
               /* Intent intent = new Intent(getActivity(), TransparentActivity.class);
                startActivity(intent);*/
                dialog.dismiss();
            }
        });

        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), DropboxLoginActivity.class);
                in.putExtra("FROM","Backup");
                in.putExtra("ToDo","Individual");
                in.putExtra("ToDoWhat","Import");
                getActivity().startActivity(in);
                dialog.dismiss();
            }

        });

        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        final RelativeLayout rlFloatfax = dialogview.findViewById(R.id.rlFloatfax);
        rlFloatfax.setVisibility(View.VISIBLE);
        final FloatingActionButton floatCancel = dialogview.findViewById(R.id.floatCancel);
        final FloatingActionButton floatContact = dialogview.findViewById(R.id.floatContact);
        final FloatingActionButton floatNew = dialogview.findViewById(R.id.floatNew);
        final FloatingActionButton floatfax = dialogview.findViewById(R.id.floatfax);
        floatfax.setImageResource(R.drawable.dropbox);
TextView txtFax=dialogview.findViewById(R.id.txtfax);
txtFax.setText("Add From Dropbox");
        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        // int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.95);
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
                preferences.putString(PrefConstants.SOURCE, "Connection");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB","New");
                getActivity().startActivity(i);
                dialog.dismiss();
            }

        });

        floatContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Work in Progress due to ph number",Toast.LENGTH_SHORT).show();
                preferences.putString(PrefConstants.SOURCE, "Connection");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                i.putExtra("TAB","Contact");
                getActivity().startActivity(i);
                dialog.dismiss();
            }


        });
        floatfax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), DropboxLoginActivity.class);
                in.putExtra("FROM","Backup");
                in.putExtra("ToDo","Individual");
                in.putExtra("ToDoWhat","Import");
                getActivity().startActivity(in);
                dialog.dismiss();
            }


        });
/* */
    }

    @SuppressLint("ResourceAsColor")
    private void showInstructionDialog() {
        final Dialog dialogInstruction = new Dialog(getActivity());
        dialogInstruction.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInstruction.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.instruction_dialog, null);
        final TextView textOption1 = dialogview.findViewById(R.id.txtInstruction);
        final TextView textOption2 = dialogview.findViewById(R.id.txtCancel);
        final View viewIns = dialogview.findViewById(R.id.viewIns);

        textOption1.setText("User Instructions");
        textOption2.setText("Cancel");


        dialogInstruction.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogInstruction.getWindow().getAttributes());
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.95);
        lp.width = width;
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // buttonLayoutParams.setMargins(0, 0, 0, 10);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.BOTTOM;
        dialogInstruction.getWindow().setAttributes(lp);
        dialogInstruction.setCanceledOnTouchOutside(false);
        dialogInstruction.show();


        textOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentUserIns = new Intent(getActivity(), UserInsActivity.class);
                startActivity(intentUserIns);
                dialogInstruction.dismiss();
/*
                Intent i = new Intent(getActivity(), InstructionActivity.class);
                i.putExtra("From", "ConnectionInstuction");
                startActivity(i);
*/
            }
        });


        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInstruction.dismiss();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        // getProfile();
        getProfile();
        getData();
        setListData();
        String image = preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        //byte[] photo = Base64.decode(image, Base64.DEFAULT);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        txtUser.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            String mail1 = preferences.getString(PrefConstants.USER_EMAIL);
            mail1 = mail1.replace(".", "_");
            mail1 = mail1.replace("@", "_");
            File imgFile = new File(Environment.getExternalStorageDirectory()+"/MYLO/"+ mail1 +"/",image);

            //File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/Master/", image);
            imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
            if (imgFile.exists()) {
                if (imgDrawerProfile.getDrawable() == null) {
                    imgDrawerProfile.setImageResource(R.drawable.lightblue);
                } else {
                    imgDrawerProfile.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                }
                if (imgSelf.getDrawable() == null) {
                    imgSelf.setImageResource(R.drawable.lightblue);
                } else {
                    imgSelf.setImageURI(Uri.parse(String.valueOf(Uri.fromFile(imgFile))));
                }
            }
        } else {
            imgDrawerProfile.setImageResource(R.drawable.lightblue);
            imgSelf.setImageResource(R.drawable.lightblue);
        }
    }

    public void deleteConnection(int id) {
        boolean flag = MyConnectionsQuery.deleteRecord(id);
        if (flag == true) {
            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            getData();
            setListData();

            File dir = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* File dir = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            if (dir.isDirectory())
            {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(dir, children[i]).delete();
                }
            }*/
            /*File file=new File(preferences.getString(PrefConstants.CONNECTED_PATH));
          //  File data=getActivity().getDatabasePath( preferences.getString(PrefConstants.CONNECTED_USERDB)+".db");
            if (file.exists())
            {
                file.delete();
            }*/

           /* String currentDBPath = "//data//com.mindyourlovedone.healthcare.HomeActivity"
                    + "//databases//" + preferences.getString(PrefConstants.CONNECTED_USERDB)+".db";*/
           /* DBHelper dbHelper=new DBHelper(getActivity(),preferences.getString(PrefConstants.CONNECTED_USERDB));
            MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
            m.dropTable();*/
        }
    }


}
