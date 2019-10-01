package com.mindyourlovedone.healthcare.Connections;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.v2.sharing.FileMemberActionResult;
import com.dropbox.core.v2.sharing.MemberSelector;
import com.dropbox.core.v2.users.FullAccount;
import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedone.healthcare.DashBoard.InstructionActivity;
import com.mindyourlovedone.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedone.healthcare.DashBoard.UserInsActivity;
import com.mindyourlovedone.healthcare.DropBox.DropboxActivity;
import com.mindyourlovedone.healthcare.DropBox.DropboxClientFactory;
import com.mindyourlovedone.healthcare.DropBox.FilesActivity;
import com.mindyourlovedone.healthcare.DropBox.GetCurrentAccountTask;
import com.mindyourlovedone.healthcare.DropBox.ShareFileTask;
import com.mindyourlovedone.healthcare.DropBox.UnZipTask;
import com.mindyourlovedone.healthcare.DropBox.ZipListner;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.SwipeCode.DividerItemDecoration;
import com.mindyourlovedone.healthcare.SwipeCode.VerticalSpaceItemDecoration;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.DialogManager;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by varsha on 8/26/2017.
 */

public class FragmentConnectionNew extends Fragment implements View.OnClickListener, ZipListner {
    static int ni;
    View rootview;
    GridView lvConnection;
    RecyclerView lvSelf;
    TextView txtUser, txtRelation;

    ImageView fab;
    LinearLayout llSelf;
    ImageView imgSelfFolder, imgSelf, imgBacks;
    ArrayList<RelativeConnection> connectionList;
    TextView txtFTU;

    TextView txtTitle, txtName, txtRel, txtDrawerName;
    ImageView imgNoti, imgProfile, imgLogo, imgPdf, imgDrawerProfile, imgRight, imgR;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;

    RelativeLayout leftDrawer, rlMsg, rlSelf;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    RelativeLayout rlGuide;
    RelativeConnection connection;
    TextView txthelp, txtYour;
    ImageView imghelp;
    final CharSequence[] delete_backup = {"Delete Profile", "Share Profile"};
    final CharSequence[] backup_profile = {"Share Profile"};
    // FloatingActionButton fab;
    //RelativeLayout llAddConn;
    // PersonalInfo personalInfo
    int noti=0;

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

    private void initUI() {
        lvSelf = rootview.findViewById(R.id.lvSelf);
        rlSelf = rootview.findViewById(R.id.rlSelf);
        imghelp = rootview.findViewById(R.id.imghelp);
        txthelp = rootview.findViewById(R.id.txthelp);
        txtYour = rootview.findViewById(R.id.txtYour);
        fab = rootview.findViewById(R.id.fab);
        llSelf = rootview.findViewById(R.id.llSelf);
        imgSelfFolder = rootview.findViewById(R.id.imgSelfFolder);
        imgSelf = rootview.findViewById(R.id.imgSelf);
        imgBacks = getActivity().findViewById(R.id.imgBacks);
        imgBacks.setVisibility(View.GONE);
        txtUser = rootview.findViewById(R.id.txtUser);
        txtRelation = rootview.findViewById(R.id.txtRelation);

        imgR = getActivity().findViewById(R.id.imgR);
        imgR.setVisibility(View.GONE);
        imgRight = getActivity().findViewById(R.id.imgRight);
        imgRight.setVisibility(View.VISIBLE);
        rlMsg = rootview.findViewById(R.id.rlMsg);
        txtFTU = rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserIns = new Intent(getActivity(), UserInsActivity.class);
                intentUserIns.putExtra("From", "Profile");
                startActivity(intentUserIns);
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
        lvConnection = rootview.findViewById(R.id.lvConnection);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lvSelf.setLayoutManager(linearLayoutManager);

        //add ItemDecoration
        lvSelf.addItemDecoration(new VerticalSpaceItemDecoration(20));

        //or
        lvSelf.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        //...
        rlSelf.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShowOptionDialog("Profiles", -1, preferences.getString(PrefConstants.USER_EMAIL));
                return true;
            }
        });

      /*  lvSelf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if (position != 0) {
                    if (position != connectionList.size()) {
                        ShowOptionDialog("delete", position, connectionList.get(position).getEmail());
                    }
                } else {
                    ShowOptionDialog("share", position, connectionList.get(position).getEmail());
                }
                return false;
            }
        });*/


    }

    public void setListData() {
        if (connectionList.size() != 0) {
            ConnectionAdapter connectionAdapter = new ConnectionAdapter(getActivity(), connectionList,FragmentConnectionNew.this);
            lvSelf.setAdapter(connectionAdapter);
            imghelp.setVisibility(View.GONE);
            txthelp.setVisibility(View.GONE);
            lvSelf.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
            txtYour.setVisibility(View.VISIBLE);
        } else {
            imghelp.setVisibility(View.VISIBLE);
            txthelp.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.VISIBLE);
            txtYour.setVisibility(View.GONE);
            lvSelf.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        imgLogo.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        fab.setOnClickListener(this);
        imgSelfFolder.setOnClickListener(this);
        rlSelf.setOnClickListener(this);
        imgSelf.setOnClickListener(this);
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
        preferences.putString(PrefConstants.USER_EMAIL, connection.getEmail());
    }

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
                Intent intentP = new Intent(getActivity(), ProfileActivity.class);
                preferences.putString(PrefConstants.USER_IMAGE, connection.getPhoto());
                preferences.putInt(PrefConstants.USER_ID, connection.getUserid());
                preferences.putString(PrefConstants.USER_NAME, connection.getName());
                preferences.putString(PrefConstants.USER_PROFILEIMAGE, connection.getPhoto());
                preferences.putString(PrefConstants.USER_EMAIL, connection.getEmail());
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
                args.putString("Relation", "Self");
                getProfile();
                //String saveThis = Base64.encodeToString(connectionList.get(position).getPhoto(), Base64.DEFAULT);
                preferences.putString(PrefConstants.USER_IMAGE, preferences.getString(PrefConstants.USER_PROFILEIMAGE));
                preferences.putString(PrefConstants.CONNECTED_NAME, preferences.getString(PrefConstants.USER_NAME));
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

            case R.id.fab:
                //showContactDialog();
                showFloatDialog();
                break;
        }
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
        TextView txtFax = dialogview.findViewById(R.id.txtfax);
        txtFax.setText("Import from Dropbox");
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
                i.putExtra("TAB", "New");
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
                i.putExtra("TAB", "Contact");
                getActivity().startActivity(i);
                dialog.dismiss();
            }


        });

        floatfax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), DropboxLoginActivity.class);
                in.putExtra("FROM", "Restore");
                in.putExtra("ToDo", "Individual");
                in.putExtra("ToDoWhat", "Import");
                getActivity().startActivity(in);
               /* preferences.putString(PrefConstants.STORE, "Restore");
                preferences.putString(PrefConstants.TODO, "Individual");
                preferences.putString(PrefConstants.TODOWHAT, "Import");
                startActivity(FilesActivity.getIntent(getActivity(), ""));*/
                dialog.dismiss();
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
            File imgFile = new File(Environment.getExternalStorageDirectory() + "/MYLO/" + mail1 + "/", image);

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
        if (preferences.getString(PrefConstants.FINIS).equals("Share"))
        {
            preferences.putString(PrefConstants.FINIS,"False");
            showEmailDialog();
        }
     /*   if (preferences.getString(PrefConstants.FINIS).equals("Restore"))
        {
            preferences.putString(PrefConstants.FINIS,"False");
            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Restore?");
            alert.setMessage("Do you want to unzip and  restore " + preferences.getString(PrefConstants.RESULT) + " database?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String name = preferences.getString(PrefConstants.RESULT);
                    Log.v("NAME", name);
                    if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                        String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                        //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                        String backupDBPath = "/Download/" + name;
                        String newname = name.replace(".zip", "");
                        final File folder = new File(sd, backupDBPath);
                        final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                "/MYLO/" + newname);
                        final File destfolder1 = new File(Environment.getExternalStorageDirectory(),
                                "/MYLO/");//nikita
                        if (!destfolder.exists()) {
                            destfolder.mkdir();
                             new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
                           // new UnZipTask((DropboxLoginActivity) getActivity(), folder.getAbsolutePath(), destfolder1.getAbsolutePath()).execute();//nikita
                        } else {

                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setTitle("Replace?");
                            alert.setMessage("Profile is already exists, Do you want to replace?");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    destfolder.delete();//nikita
                                    try {
                                        destfolder.createNewFile();//nikita
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                   // new UnZipTask((DropboxLoginActivity) getActivity(), folder.getAbsolutePath(), destfolder1.getAbsolutePath()).execute();//nikita
                                    new DropboxLoginActivity().unZip(folder.getAbsolutePath(), destfolder1.getAbsolutePath());
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
                    } else {

                        String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                        //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                        String backupDBPath = "/Download/" + name;
                        String newname = name.replace(".zip", "");
                        final File folder = new File(sd, backupDBPath);
                        final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                newname);
                        new DropboxLoginActivity().unZip(folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath());

                      //  new UnZipTask((DropboxLoginActivity) getActivity(), folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath()).execute();//nikita


                    }

                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }*/

        validateBackupDate();

        if (preferences.getString(PrefConstants.BACKUPDATE)!=null) {
            String backupdatestring = preferences.getString(PrefConstants.BACKUPDATE);
           // Toast.makeText(getActivity(),backupdatestring,Toast.LENGTH_SHORT).show();
        }
    }

    private void validateBackupDate() {
        DateFormat df = new SimpleDateFormat("dd MM yy HH:mm:ss");
        if (preferences.getString(PrefConstants.BACKUPDATE)!=null||!preferences.getString(PrefConstants.BACKUPDATE).equals("")) {
            String backupdatestring = preferences.getString(PrefConstants.BACKUPDATE);
            Date backupDate = null;
            try {
                backupDate = df.parse(backupdatestring);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            Date currentDate = cal.getTime();
            if (backupDate!=null)
            {
                if (currentDate.after(backupDate)) {
                    if (preferences.getBoolean(PrefConstants.NOTIFIED)==true)
                    {
                        sendNotification();
                    }
                }}

        }
        //String currentDateString=df.format(backupDate);
        // preferences.putString(PrefConstants.BACKUPDATE,currentDateString);
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "desc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("10", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void sendNotification() {
        preferences.putBoolean(PrefConstants.NOTIFIED,false);
        createNotificationChannel();
// Create an explicit intent for an Activity in your app
        Intent intent = new Intent(getActivity(), DropboxLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
        String msg="This is a reminder for your data backup, your last backup was a month ago. You should backup regularly.";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "10")
                .setSmallIcon(R.mipmap.mylo_new_cropped_logo)
                .setContentTitle("Backup Your Data")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
      /*  NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Unread Message")   //this is the title of notification
                        .setColor(101)
                        .setContentText("You have an unread message.");   //this is the message showed in notification
        Intent intent = new Intent(getActivity(), BaseActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());*/
       /* Intent intent = new Intent(getActivity(), BaseActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(),(int) System.currentTimeMillis(), intent, 0);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification  = (NotificationCompat.Builder) new NotificationCompat.Builder(getActivity())
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject dsiu nioef jh dfihre ijre kjhgf jf oirf iof iof uirf juiyhree uirf uirf uirf ijurg uirge uir")
                .setSmallIcon(R.drawable.ic_launcher_new)
                .setContentIntent(pIntent)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setTicker("Subject dsiu nioef jh dfihre ijre kjhgf jf oirf iof iof uirf juiyhree uirf uirf uirf ijurg uirge uir");

        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        notificationManager.notify(ni++, notification.build());
*/
    }


    public void deleteConnection(int id) {
        boolean flag = MyConnectionsQuery.deleteRecord(id);
        if (flag == true) {
            //     Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            getData();
            setListData();

            File dir = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void showInstructionDialog() {
        final Dialog dialogInstruction = new Dialog(getActivity());
        dialogInstruction.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInstruction.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                Intent in = new Intent(getActivity(), DropboxLoginActivity.class);
                in.putExtra("FROM", "Backup");
                in.putExtra("ToDo", "Individual");
                in.putExtra("ToDoWhat", "Import");
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

    public void deleteConnections(final int position) {
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
    }
    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
    @Override
    public void getFile(String res) {
        preferences=new Preferences(getActivity());
        if (res.equals("Yes")) {
            if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                copydb(getActivity());
            } else {
                copydbWholeBU(getActivity());
            }
            Toast.makeText(getActivity(), "Unzipped and restored files successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Restoring Failed, Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void callBackup() {
    }

    String txt = "Profile";

    private void showEmailDialog() {
        final Dialog customDialog;
        customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_email);
        customDialog.setCancelable(false);
        final EditText etNote = customDialog.findViewById(R.id.etNote);
        TextView btnAdd = customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = customDialog.findViewById(R.id.btnNo);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                customDialog.dismiss();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String username = etNote.getText().toString();
                if (username.equals("")) {
                    etNote.setError("Please Enter email");
                    DialogManager.showAlert("Please Enter email", getActivity());
                } else if (!username.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    etNote.setError("Please enter valid email");
                    DialogManager.showAlert("Please enter valid email", getActivity());
                } else {
                    customDialog.dismiss();
                    List<MemberSelector> newMembers = new ArrayList<MemberSelector>();
                    MemberSelector newMember = MemberSelector.email(username);
                    // MemberSelector newMember1 = MemberSelector.email("kmllnk@j.uyu");
                    newMembers.add(newMember);
                    // newMembers.add(newMember1);


                    if(preferences.getString(PrefConstants.FILE).contains("MYLO.zip")){
                        txt = "Whole Backup";
                    }else{
                        txt = "Profile";
                    }

                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(false);
                    dialog.setMessage("Sharing "+txt+" can take several minutes");
                    dialog.show();
                    new ShareFileTask(newMembers, getActivity(), DropboxClientFactory.getClient(), new ShareFileTask.Callback() {
                        @Override
                        public void onUploadComplete(List<FileMemberActionResult> result) {
                            dialog.dismiss();
                            final AlertDialog.Builder alerts = new AlertDialog.Builder(getActivity());
                            alerts.setTitle("Success");
                            alerts.setMessage(txt+" shared successfully");
                            alerts.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();


                                }
                            });
                            alerts.show();
                        }

                        @Override
                        public void onError(Exception e) {
                            dialog.dismiss();

                        }
                    }).execute(preferences.getString(PrefConstants.SHARE), preferences.getString(PrefConstants.FILE));
                }
            }
        });

        customDialog.show();
    }


    private void copydb(Context context) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            //open new imported db
            DBHelper dbHelper = new DBHelper(context, backupDBPath);
            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
            //fetch data
            RelativeConnection connection = MyConnectionsQuery.fetchConnectionRecordforImport(1);
       /* Boolean flag = MyConnectionsQuery.updateImportMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), connection.getUserid());
        if (flag == true) {*/
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelpers);
            RelativeConnection connections = MyConnectionsQuery.fetchConnectionRecordforImport(connection.getEmail());
            if (connections != null) {
                if (connection.getRelationType().equals("Self")) {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                    }
                } else {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                        ContactDataQuery c = new ContactDataQuery(context, dbHelpers);
                        Boolean flagf = ContactDataQuery.updateUserId(connections.getId());
                        if (flagf == true) {
                            Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } else {
//                Boolean flags = MyConnectionqsQuery.insertMyConnectionsData(connection.getUserid(), connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), "", connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());

                Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, true);

                if (flags == true) {
                    Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                    storeImage(connection.getPhoto(), "Profile", backupDBPath);
                   /* RelativeConnection con = MyConnectionsQuery.fetchEmailRecords(connection.getEmail());
                    ContactDataQuery c=new ContactDataQuery(context,dbHelpers);
                    Boolean flagf = ContactDataQuery.updateUserId(con.getId());
                    if (flagf == true) {
                        Toast.makeText(context, "Insret updated", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        } else {
            //Toast.makeText(context, "Need Data save to master db", Toast.LENGTH_SHORT).show();
        }
        //  }
       /* String mail=connection.getEmail();
        mail=mail.replace(".","_");
        mail=mail.replace("@","_");
        preferences.putString(PrefConstants.CONNECTED_USERDB,mail);
        preferences.putString(PrefConstants.CONNECTED_PATH,Environment.getExternalStorageDirectory()+"/MYLO/"+preferences.getString(PrefConstants.CONNECTED_USERDB)+"/");
            */
       /* File data = DropboxLoginActivity.this.getDatabasePath("temp.db");
        Log.e("", data.getAbsolutePath());

        File currentDB = new File(data.getAbsolutePath());
        File backupDB = new File(sd, backupDBPath);
        try {
            copy(backupDB, currentDB);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    private void copydbWholeBU(Context context) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            //open new imported db
            DBHelper dbHelper = new DBHelper(context, backupDBPath);
            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
            //fetch data
            ArrayList<RelativeConnection> connectionlist = MyConnectionsQuery.fetchConnectionRecordforImportAll();
       /* Boolean flag = MyConnectionsQuery.updateImportMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), connection.getUserid());
        if (flag == true) {*/
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelpers);
            for (int i = 0; i < connectionlist.size(); i++) {
                RelativeConnection connection = connectionlist.get(i);
                RelativeConnection connections = MyConnectionsQuery.fetchConnectionRecordforImport(connection.getEmail());
                if (connections != null) {
                    if (connection.getRelationType().equals("Self")) {
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", backupDBPath);
                        }
                    } else {
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", backupDBPath);
                        }
                    }
                } else {
//                    Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getUserid(), connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), "", connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());

                    Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, false);
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                    }
                }
            }
        } else {
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            ContactTableQuery ms = new ContactTableQuery(context, dbHelpers);
            ContactTableQuery.deleteContactData();
            //Toast.makeText(context, "Need Data save to master db", Toast.LENGTH_SHORT).show();
        }

        //  }
       /* String mail=connection.getEmail();
        mail=mail.replace(".","_");
        mail=mail.replace("@","_");
        preferences.putString(PrefConstants.CONNECTED_USERDB,mail);
        preferences.putString(PrefConstants.CONNECTED_PATH,Environment.getExternalStorageDirectory()+"/MYLO/"+preferences.getString(PrefConstants.CONNECTED_USERDB)+"/");
            */
       /* File data = DropboxLoginActivity.this.getDatabasePath("temp.db");
        Log.e("", data.getAbsolutePath());

        File currentDB = new File(data.getAbsolutePath());
        File backupDB = new File(sd, backupDBPath);
        try {
            copy(backupDB, currentDB);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void storeImage(String selectedImage, String profile, String backupDBPath) {
        FileOutputStream outStream1 = null;
        File createDir = new File(Environment.getExternalStorageDirectory() + "/MYLO/MASTER/");
        if (!createDir.exists()) {
            createDir.mkdir();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/MYLO/" + backupDBPath + "/" + selectedImage);
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        try {
            if (myBitmap != null) {
                if (profile.equals("Profile")) {
                    outStream1 = new FileOutputStream(Environment.getExternalStorageDirectory() + "/MYLO/MASTER/" + selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                    byte[] byteArray = stream.toByteArray();
                    outStream1.write(byteArray);
                    outStream1.close();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

      /*  FileOutputStream outStream2 = null;
        File fileori = new File(Environment.getExternalStorageDirectory()+"/MYLO/"+backupDBPath);
        File files = new File(Environment.getExternalStorageDirectory()+"/MYLO/MASTER/");
        if (!files.exists()) {
            files.mkdirs();
        }

        try {
            outStream2=new FileOutputStream(fileori);
            outStream2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }*/
    }

}
