package com.mindyourlovedone.healthcare.backuphistory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedone.healthcare.DashBoard.AddDocumentActivity;
import com.mindyourlovedone.healthcare.DashBoard.UnZipTaskHistory;
import com.mindyourlovedone.healthcare.DropBox.DownloadHistoryTask;
import com.mindyourlovedone.healthcare.DropBox.DropboxClientFactory;

import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.model.BackupHistory;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<BackupHistory> historyList;
    LayoutInflater lf;
    HistoryAdapter.Holder holder;
    private Preferences preferences;


    public HistoryAdapter(Context context, ArrayList<BackupHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
        lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_backup_history, parent, false);
            holder = new HistoryAdapter.Holder();
            holder.txtFileName = convertView.findViewById(R.id.txtFileName);
            holder.txtType = convertView.findViewById(R.id.txtBackupType);
            holder.txtStatus = convertView.findViewById(R.id.txtStatus);
            holder.txtReason = convertView.findViewById(R.id.txtReason);
            holder.txtDateTime = convertView.findViewById(R.id.txtDateTime);
            holder.imgDownload=convertView.findViewById(R.id.imgDownload);

            convertView.setTag(holder);
        } else {
            holder = (HistoryAdapter.Holder) convertView.getTag();
        }

        holder.txtFileName.setText(historyList.get(position).getFileName());
        holder.txtDateTime.setText(historyList.get(position).getDate());
        if (historyList.get(position).getReason().isEmpty()) {

         }else{
            holder.txtReason.setText("Reason: "+historyList.get(position).getReason());
        }
        holder.txtType.setText(historyList.get(position).getType());
        if (historyList.get(position).getStatus().equalsIgnoreCase("In Progress"))
        {
            holder.txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_orange_light));
            holder.txtStatus.setText(historyList.get(position).getStatus());

        }else if (historyList.get(position).getStatus().equalsIgnoreCase("Completed"))
        {
            holder.txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            holder.txtStatus.setText(historyList.get(position).getStatus());

        }else if (historyList.get(position).getStatus().equalsIgnoreCase("Fail"))
        {

            holder.txtStatus.setText(historyList.get(position).getStatus());
            holder.txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
        else if (historyList.get(position).getStatus().equalsIgnoreCase("KILL"))
        {

            holder.txtStatus.setText("Fail");
            holder.txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }


        holder.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* DropboxLoginActivity dropboxLoginActivity=new DropboxLoginActivity();
                dropboxLoginActivity.onRestore();*/
               Preferences preferences=new Preferences(context);
                preferences.putString(PrefConstants.STORE, "Restore");
                preferences.putString(PrefConstants.RESTORE, historyList.get(position).getFileName());
               // preferences.putString(PrefConstants.TODO, todo);
               // preferences.putString(PrefConstants.TODOWHAT, todoWhat);
                /*FilesActivity f=new FilesActivity();
                f.downloadfileRestore(historyList.get(position).getFileName(),historyList.get(position).getProfile(),historyList.get(position).getFileMetaData());
*/
                downloadfileRestore(historyList.get(position).getFileName(),historyList.get(position).getProfile(),historyList.get(position).getFileMetaData());


            }
        });
        return convertView;
    }


    
    public void downloadfileRestore(String fileName, final String profile, String fileMetaData) {
        String slash = "/";
        if (profile.equals("Individual")) {
            fileName = fileName.concat(".zip");

            fileName = slash.concat(fileName);
        } else {
            fileName = fileName.concat("_MYLO.zip");
            fileName = slash.concat(fileName);
        }
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Downloading");
        dialog.show();

        new DownloadHistoryTask(context, DropboxClientFactory.getClient(), new DownloadHistoryTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {
                  dialog.dismiss();

                if (result != null) {
                    //viewFileInExternalApp(result);
                    AddDocumentActivity a = new AddDocumentActivity();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = result.getName().substring(result.getName().indexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    preferences = new Preferences(context);
                    preferences.putString(PrefConstants.URI, result.getAbsolutePath());
                    preferences.putString(PrefConstants.RESULT, result.getName());

                    Intent i = new Intent();
                    i.putExtra("URI", result.getAbsolutePath());
                    i.putExtra("Name", result.getName());

                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                        //setResult(45, i);
                        //finish();
                    } else {

                        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Restore?");
                        alert.setMessage("Do you want to unzip and  restore " + preferences.getString(PrefConstants.RESULT) + " database?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                String name = preferences.getString(PrefConstants.RESULT);
                                Log.v("NAME", name);
                                if (profile.equals("Individual")) {
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
                                        FileInputStream fis = null;
                                        ZipInputStream zipIs = null;
                                        ZipEntry zEntry = null;
                                        try {
                                            fis = new FileInputStream(folder);
                                            zipIs = new ZipInputStream(new BufferedInputStream(fis));
                                            // Log.v("FILESF",zipIs.getNextEntry().getName());
                                            String fnamelist[] = zipIs.getNextEntry().getName().split("/");
                                            String fname = fnamelist[0];
                                            if (fname.equals("")) {
                                                fname = fnamelist[1];
                                            }

                                            File[] files = destfolder1.listFiles();
                                            boolean flag = false;
                                            boolean nofull = false;
                                            for (int i = 0; i < files.length; i++) {
                                                if (files[i].getName().equals(fname)) {
                                                    flag = true;
                                                    Log.v("FILESF", files[i].getName() + "-" + fname + "-" + "Profile exists");
                                                    Toast.makeText(context, "Profile exists", Toast.LENGTH_SHORT);
                                                    break;
                                                }

                                            }

                                            for (int i = 0; i < fnamelist.length; i++) {
                                                if (fnamelist[i].equalsIgnoreCase("MYLO")) {
                                                    nofull = true;
                                                    break;
                                                }
                                            }

                                            zipIs.close();
                                            if (nofull != true) {
                                                if (flag == true) {
                                                    AlertDialog.Builder alerts = new AlertDialog.Builder(context);
                                                    alerts.setTitle("Replace?");
                                                    alerts.setMessage("Profile is already exists, Do you want to replace?");
                                                    alerts.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            new UnZipTaskHistory(context, folder.getAbsolutePath(), destfolder1.getAbsolutePath(), context).execute();//nikita
                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    alerts.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    alerts.show();
                                                } else {
                                                    new UnZipTaskHistory(context, folder.getAbsolutePath(), destfolder1.getAbsolutePath(), context).execute();//nikita
                                                }
                                            } else {
                                                AlertDialog.Builder alertf = new AlertDialog.Builder(context);
                                                alertf.setTitle("Invalid");
                                                alertf.setMessage("Invalid Profile Backup");
                                                alertf.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        try {
                                                            FileUtils.deleteDirectory(destfolder);
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        dialog.dismiss();
                                                    }
                                                });

                                                alertf.show();
                                            }
                                        } catch (FileNotFoundException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                    } else {
                                        Log.v("FILESF", newname + "-" + "Profile exists");
                                        AlertDialog.Builder alerts = new AlertDialog.Builder(context);
                                        alerts.setTitle("Replace?");
                                        alerts.setMessage("Profile is already exists, Do you want to replace?");
                                        alerts.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new UnZipTaskHistory(context, folder.getAbsolutePath(), destfolder1.getAbsolutePath(), context).execute();//nikita
                                                dialog.dismiss();
                                            }
                                        });

                                        alerts.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        });

                                        alerts.show();

                                    }

                                } else {

                                    String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                                    String backupDBPath = "/Download/" + name;
                                    String newname = name.replace(".zip", "");
                                    final File folder = new File(sd, backupDBPath);
                                    final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                            newname);
                                    final File destfolders = new File(Environment.getExternalStorageDirectory(),
                                            "MYLO");

                                    FileInputStream fis = null;
                                    ZipInputStream zipIs = null;
                                    try {
                                        fis = new FileInputStream(folder);
                                        zipIs = new ZipInputStream(new BufferedInputStream(fis));
                                        String fnamelist[] = zipIs.getNextEntry().getName().split("/");

                                        boolean nofull = false;

                                        for (int i = 0; i < fnamelist.length; i++) {
                                            if (fnamelist[i].equalsIgnoreCase("MYLO")) {
                                                nofull = true;
                                                break;
                                            }
                                        }
                                        zipIs.close();
                                        if (nofull == true) {
                                            if (destfolders.exists()) {
                                                try {
                                                    FileUtils.deleteDirectory(destfolders);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            new UnZipTaskHistory(context, folder.getAbsolutePath(), Environment.getExternalStorageDirectory().getAbsolutePath(), context).execute();//nikita

                                        } else {
                                            AlertDialog.Builder alertf = new AlertDialog.Builder(context);
                                            alertf.setTitle("Invalid");
                                            alertf.setMessage("Invalid MYLO Whole Backup");
                                            alertf.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            alertf.show();
                                        }
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e("TAG", "Failed to download file.", e);
                Toast.makeText(context,
                        "An error has occurred" + "793",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        }).execute(fileName, fileMetaData);

    }

    private class Holder {
        TextView txtFileName,txtType,txtReason,txtDateTime,txtStatus;
        ImageView imgDownload;
    }
}

