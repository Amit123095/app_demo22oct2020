package com.mindyourlovedone.healthcare.backuphistory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DropBox.ZipListner;
import com.mindyourlovedone.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;
import com.mindyourlovedone.healthcare.database.DBHelper;
import com.mindyourlovedone.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedone.healthcare.model.BackupHistory;
import com.mindyourlovedone.healthcare.model.RelativeConnection;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BackupHistoryActivity extends AppCompatActivity implements ZipListner {
 Context context=this;
 ArrayList<BackupHistory> HistoryList;
 ImageView imgBack;
 ListView listHistory;
 DBHelperHistory sqliteHelper;
    private String dirName="";
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_history);
        initUi();
        sqliteHelper=new DBHelperHistory(context);
        getHistoryData();
        setHistoryData();
      //  initListener();
    }

    private void setHistoryData() {
        HistoryAdapter historyAdapter=new HistoryAdapter(context,HistoryList);
        listHistory.setAdapter(historyAdapter);
    }

    private void getHistoryData() {
        HistoryList =new ArrayList<>();

        BackupHistoryQuery backupHistoryQuery=new BackupHistoryQuery(context,sqliteHelper);
        HistoryList = BackupHistoryQuery.fetchAllRecord();


       /* BackupHistory b=new BackupHistory();
        b.setFileName("MYLO_1223434.zip");
        b.setDate("2020 08 25 - 18:35:51");
        b.setStatus("Completed");
        b.setType("Auto Backup");
        b.setReason("");

        BackupHistory b1=new BackupHistory();
        b1.setFileName("MYLO_23434.zip");
        b1.setDate("2020 08 25 - 18:35:51");
        b1.setStatus("Fail");
        b1.setType("Manual");
        b1.setReason("Reason : Insufficient Space at Dropbox");

        HistoryList.add(b);
        HistoryList.add(b1);*/



    }

    private void initUi() {
     imgBack=findViewById(R.id.imgBacks);
     imgBack.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish();
         }
     });

     listHistory=findViewById(R.id.listHistory);

    }


    

    @Override
    public void setNameFile(String dirName) {
        this.dirName = dirName;
    }
    /**
     * Function: Result after Download and unzip file
     * @param s
     */
    public void getFile(String s) {
        preferences=new Preferences(context);
    
        Preferences preferences = new Preferences(context);
        if (s.equals("Yes")) {
            if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                copydb(context);
            } else {
                copydbWholeBU(context);
            }
            Toast.makeText(context, "Unzipped and restored files successfully", Toast.LENGTH_SHORT).show();


            Intent intentDashboard = new Intent(context, BaseActivity.class);
            intentDashboard.putExtra("c", 3);//Profile Data

            startActivity(intentDashboard);
            finish();
        } else {
            Toast.makeText(context, "Restoring Failed, Please try again", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Function: Copy and restore data after Import single profile
     * @param context
     */
    private void copydb(Context context) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            File destfolder1 = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+backupDBPath);

            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+dirName);
            File[] files= destfolder.listFiles();
            boolean flag=false;
            for (int i=0;i<files.length;i++)
            {
                if (files[i].getName().equalsIgnoreCase("MASTER.db"))
                {
                    flag=true;
                    break;
                }
            }
            DBHelper dbHelper;
            if (flag==true)
            {
                String backup = preferences.getString(PrefConstants.RESULT);
                backup = backupDBPath.replace(".zip", "");
                dbHelper = new DBHelper(context, dirName);
            }else{
                String backupDBPaths=files[0].getName();
                File folder2 = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                File srcfolders = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+dirName+"/");

                try {
                    FileUtils.copyDirectory(srcfolders,folder2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dbHelper = new DBHelper(context, backupDBPaths,1);
            }

            //open new imported db
            // dbHelper = new DBHelper(context, backupDBPath,1);
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
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(),connection.getHas_card());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                    }
                } else {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(),connection.getHas_card());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                        ContactDataQuery c = new ContactDataQuery(context, dbHelpers);
                        Boolean flagf = ContactDataQuery.updateUserId(connections.getId());
                        if (flagf == true) {
                            Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } else {
                if (!connection.getEmail().equals("")) {
                    Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, true);

                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                    }
                }
            }
            try {
                FileUtils.deleteDirectory(destfolder1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folderzip = new File(Environment.getExternalStorageDirectory(), "/MYLO/"+backupDBPath+".zip" );
            folderzip.delete();
        } else {
            //Toast.makeText(context, "Need Data save to master db", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Function: Copy and restore data after Import Whole profile
     * @param context
     */
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
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(), connection.getHas_card());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", dirName);
                        }
                    } else {
                        Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard(), connection.getHas_card());
                        if (flags == true) {
                            Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                            storeImage(connection.getPhoto(), "Profile", dirName);
                        }
                    }
                } else {
//                    Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getUserid(), connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), "", connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());

                    Boolean flags = MyConnectionsQuery.insertMyConnectionsDataBACKUP(connection, false);
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", dirName);
                    }
                }
            }
        } else {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            File destfolder = new File(Environment.getExternalStorageDirectory(),"MYLO");

            File[] files= destfolder.listFiles();
            boolean flag=false;
            for (int i=0;i<files.length;i++)
            {
                if (files[i].getName().equalsIgnoreCase("MYLO"))
                {
                    flag=true;
                    break;
                }
            }
            if (flag==true)
            {
                String backup = preferences.getString(PrefConstants.RESULT);
                backup = backupDBPath.replace(".zip", "");


                File folder2 = new File(Environment.getExternalStorageDirectory(), "/MYLO/");
                File srcfolders = new File(Environment.getExternalStorageDirectory(),"/"+dirName+"/");
                try {
                    FileUtils.copyDirectory(srcfolders,folder2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DBHelper dbHelpers = new DBHelper(context, "MASTER");
                ContactTableQuery ms = new ContactTableQuery(context, dbHelpers);
                ContactTableQuery.deleteContactData();
            }else{
                DBHelper dbHelpers = new DBHelper(context, "MASTER");
                ContactTableQuery ms = new ContactTableQuery(context, dbHelpers);
                ContactTableQuery.deleteContactData();
            }



            //Toast.makeText(context, "Need Data save to master db", Toast.LENGTH_SHORT).show();
        }


    }
    /**
     * Function: Store Image in application storage folder
     * @param selectedImage
     * @param profile
     * @param backupDBPath
     */
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

    }


}
