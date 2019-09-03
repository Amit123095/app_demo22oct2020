package com.mindyourlovedone.healthcare.DropBox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.FileAction;
import com.dropbox.core.v2.sharing.FileMemberActionResult;
import com.dropbox.core.v2.sharing.MemberSelector;
import com.mindyourlovedone.healthcare.DashBoard.AddDocumentActivity;
import com.mindyourlovedone.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedone.healthcare.HomeActivity.R;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static android.graphics.Color.TRANSPARENT;

public class FilesActivity extends DropboxActivity implements ZipListner {
    public final static String EXTRA_PATH = "FilesActivity_Path";
    private static final String TAG = FilesActivity.class.getName();
    private static final int PICKFILE_REQUEST_CODE = 1;
    Preferences preferences;
    RelativeLayout rlBackup, rlHeader,rlParent;
    private String mPath;
    private FilesAdapter mFilesAdapter;
    private FileMetadata mSelectedFile;
    ImageView imgBack;


    public static Intent getIntent(Context context, String path) {
        Intent filesIntent = new Intent(context, FilesActivity.class);
        filesIntent.putExtra(FilesActivity.EXTRA_PATH, path);
        return filesIntent;
    }

    private static void zipFolder(String inputFolderPath, String outZipPath) {
        try {
            FileOutputStream fos = new FileOutputStream(outZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File srcFile = new File(inputFolderPath);
            File[] files = srcFile.listFiles();
            Log.d("", "Zip directory: " + srcFile.getName());
            for (int i = 0; i < files.length; i++) {
                Log.d("", "Adding file: " + files[i].getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (IOException ioe) {
            Log.e("", ioe.getMessage());
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new Preferences(FilesActivity.this);
        String path = getIntent().getStringExtra(EXTRA_PATH);
        mPath = path == null ? "" : path;

        setContentView(R.layout.activity_files);
        rlBackup = findViewById(R.id.rlBackup);
        rlParent = findViewById(R.id.rlParent);
        rlHeader = findViewById(R.id.rlHeader);
        imgBack = findViewById(R.id.imgBack);
        RecyclerView recyclerView = findViewById(R.id.files_list);

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String from = preferences.getString(PrefConstants.STORE);
        if (from.equals("Document") || from.equals("Restore")) {
            rlBackup.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            rlHeader.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.GONE);


        } else if (from.equals("Backup")||from.equals("Share")) {
            rlParent.setBackgroundColor(TRANSPARENT);
            rlBackup.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            imgBack.setVisibility(View.GONE);
            rlHeader.setVisibility(View.GONE);

          performWithPermissions(FilesActivity.FileAction.UPLOAD);
        }
        rlBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performWithPermissions(FilesActivity.FileAction.UPLOAD);
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        mFilesAdapter = new FilesAdapter(PicassoClient.getPicasso(), new FilesAdapter.Callback() {
            @Override
            public void onFolderClicked(FolderMetadata folder) {
                startActivity(FilesActivity.getIntent(FilesActivity.this, folder.getPathLower()));
            }

            @Override
            public void onFileClicked(final FileMetadata file) {
                mSelectedFile = file;
                performWithPermissions(FilesActivity.FileAction.DOWNLOAD);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mFilesAdapter);

        mSelectedFile = null;


    }

    public void launchFilePicker() {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            File folder = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                folder.delete();
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + ".zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//nikita
                destfolder.delete();
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            new ZipTask(FilesActivity.this, folder.getAbsolutePath(), destfolder.getAbsolutePath()).execute();
        } else {
            File folder = new File(Environment.getExternalStorageDirectory() + "/MYLO/");
            if (!folder.exists()) {
                try {
                    folder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                folder.delete();
//                try {
//                    folder.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO.zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
//                destfolder.delete();
//                try {
//                    destfolder.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
            new ZipTask(FilesActivity.this, folder.getPath(), destfolder.getPath()).execute();

        }
    }

    private void copy(File backupDB, File currentDB) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // This is the result of a call to launchFilePicker
                uploadFile(data.getData().toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int actionCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        FileAction action = FileAction.fromCode(actionCode);

        boolean granted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                Log.w(TAG, "User denied " + permissions[i] +
                        " permission to perform file action: " + action);
                granted = false;
                break;
            }
        }

        if (granted) {
            performAction(action);
        } else {
            switch (action) {
                case UPLOAD:
                    Toast.makeText(this,
                            "Can't upload file: read access denied. " +
                                    "Please grant storage permissions to use this functionality.",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case DOWNLOAD:
                    Toast.makeText(this,
                            "Can't download file: write access denied. " +
                                    "Please grant storage permissions to use this functionality.",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
            }
        }
    }

    private void performAction(FileAction action) {
        switch (action) {
            case UPLOAD:
                launchFilePicker();
                break;
            case DOWNLOAD:
                if (mSelectedFile != null) {
                    downloadFile(mSelectedFile);
                } else {
                    Log.e(TAG, "No file selected to download.");
                }
                break;
            default:
                Log.e(TAG, "Can't perform unhandled file action: " + action);
        }
    }

    @Override
    protected void loadData() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();

        new ListFolderTask(DropboxClientFactory.getClient(), new ListFolderTask.Callback() {
            @Override
            public void onDataLoaded(ListFolderResult result) {
                dialog.dismiss();
                ArrayList<Metadata> resultList = new ArrayList<Metadata>();
                for (int i = 0; i < result.getEntries().size(); i++) {
                    if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                        String name = result.getEntries().get(i).getName();
                        if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
                            // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                            resultList.add(result.getEntries().get(i));
                        }
                    } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                        if (result.getEntries().get(i).getName().endsWith(".zip")) {
                            if (preferences.getString(PrefConstants.TODOWHAT).equals("Import")) {
                                if (result.getEntries().get(i).getName().equals("MYLO.zip")) {

                                } else {
                                    resultList.add(result.getEntries().get(i));
                                }
                            } else {
                                if (result.getEntries().get(i).getName().equals("MYLO.zip")) {
                                    resultList.add(result.getEntries().get(i));
                                }
                            }
                            // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {

                        }
                    }
                }

                //   if (resultList.size()!=0) {
                mFilesAdapter.setFiles(resultList);
             /*   }else{
                    Toast.makeText(FilesActivity.this,"No Document or Backup File available in your dropbox",Toast.LENGTH_SHORT).show();
                    FilesActivity.this.finish();
                }*/
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to list folder.", e);
                Toast.makeText(FilesActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(mPath);
    }

    private void downloadFile(FileMetadata file) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Downloading");
        dialog.show();

        new DownloadFileTask(FilesActivity.this, DropboxClientFactory.getClient(), new DownloadFileTask.Callback() {
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

                   /*25 Oct Commented for pdf back/restore
                    Uri contentUri = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        contentUri = FileProvider.getUriForFile(FilesActivity.this, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", result);
                    } else {
                        contentUri = Uri.fromFile(result);
                    }

                    intent.setDataAndType(contentUri, type);*/

                    preferences = new Preferences(FilesActivity.this);
                    preferences.putString(PrefConstants.URI, result.getAbsolutePath());
                    preferences.putString(PrefConstants.RESULT, result.getName());

                    Intent i = new Intent();
                    i.putExtra("URI", result.getAbsolutePath());
                    i.putExtra("Name", result.getName());
                    setResult(45, i);
                    finish();
                }
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to download file.", e);
                Toast.makeText(FilesActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(file);

    }

    private void viewFileInExternalApp(File result) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = result.getName().substring(result.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(FilesActivity.this, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", result);
        } else {
            contentUri = Uri.fromFile(result);
        }

        intent.setDataAndType(contentUri, type);


        // Check for a handler first to avoid a crash
        PackageManager manager = getPackageManager();
        List<ResolveInfo> resolveInfo = manager.queryIntentActivities(intent, 0);
        if (resolveInfo.size() > 0) {
            startActivity(intent);
        }
    }

    private void uploadFile(final String fileUri) {
        // Uri contentUri = null;
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Backing Data can take several minutes");
        dialog.show();

        new UploadFileTask(this, DropboxClientFactory.getClient(), new UploadFileTask.Callback() {
            @Override
            public void onUploadComplete(final FileMetadata result) {
                dialog.dismiss();
                preferences.putString(PrefConstants.SHARE,result.getId());
                dialog.dismiss();
                FilesActivity.this.finish();
               /* String message = "Backup is stored in: " + result.getName() + "\n\nsize: " + result.getSize() + "\n\nmodified: " +
                        DateFormat.getDateTimeInstance().format(result.getClientModified());
               *//* Toast.makeText(FilesActivity.this, message, Toast.LENGTH_SHORT)
                        .show();*//*
                final AlertDialog.Builder alert = new AlertDialog.Builder(FilesActivity.this);
                alert.setTitle("Backup Stored successfully");
                alert.setMessage(message);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FilesActivity.this.finish();

                    }
                });
                alert.show();*/


              /*  String message = "Backup stored successfully, Do you want to share profile?";
                final AlertDialog.Builder alert = new AlertDialog.Builder(FilesActivity.this);
                alert.setTitle("Share Profile");
                alert.setMessage(message);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        preferences.putString(PrefConstants.SHARE,result.getId());
                        dialog.dismiss();
                        FilesActivity.this.finish();*/

                   /*     List<MemberSelector> newMembers = new ArrayList<MemberSelector>();
                        MemberSelector newMember = MemberSelector.email("varsha.shirtode@quagnitia.com");
                        MemberSelector newMember1 = MemberSelector.email("kmllnk@j.uyu");
                        newMembers.add(newMember);
                        newMembers.add(newMember1);
                        new ShareFileTask(newMembers, FilesActivity.this, DropboxClientFactory.getClient(), new ShareFileTask.Callback() {
                            @Override
                            public void onUploadComplete(List<FileMemberActionResult> result) {

                                final AlertDialog.Builder alerts = new AlertDialog.Builder(FilesActivity.this);
                                alerts.setTitle("Success");
                                alerts.setMessage("Profile shared successfully");
                                alerts.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        FilesActivity.this.finish();

                                    }
                                });
                                alerts.show();
                            }

                            @Override
                            public void onError(Exception e) {
                                FilesActivity.this.finish();
                            }
                        }).execute(result.getId());*/
            /*        }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FilesActivity.this.finish();
                    }
                });
                alert.show();
*/

            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to upload file.", e);
                Toast.makeText(FilesActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(fileUri, mPath);
    }

    public void performWithPermissions(final FileAction action) {
        if (hasPermissionsForAction(action)) {
            performAction(action);
            return;
        }

        if (shouldDisplayRationaleForAction(action)) {
            new AlertDialog.Builder(this)
                    .setMessage("This app requires storage access to download and upload files.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissionsForAction(action);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        } else {
            requestPermissionsForAction(action);
        }
    }

    private boolean hasPermissionsForAction(FileAction action) {
        for (String permission : action.getPermissions()) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldDisplayRationaleForAction(FileAction action) {
        for (String permission : action.getPermissions()) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    private void requestPermissionsForAction(FileAction action) {
        ActivityCompat.requestPermissions(
                this,
                action.getPermissions(),
                action.getCode()
        );
    }

    public void getFile(String s) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + ".zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Uri contentUri = null;
            contentUri = Uri.fromFile(destfolder);
            uploadFile(contentUri.toString());
        } else {
            File destfolder = new File(Environment.getExternalStorageDirectory(), "/MYLO.zip");
            if (!destfolder.exists()) {
                try {
                    destfolder.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Uri contentUri = null;
            contentUri = Uri.fromFile(destfolder);
            uploadFile(contentUri.toString());
        }
    }

    private void copyFile(File backupDB, File currentDB) throws IOException {
        InputStream in = new FileInputStream(backupDB);
        try {
            OutputStream out = new FileOutputStream(currentDB);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }

    public enum FileAction {
        DOWNLOAD(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        UPLOAD(Manifest.permission.READ_EXTERNAL_STORAGE);

        private static final FileAction[] values = values();

        private final String[] permissions;

        FileAction(String... permissions) {
            this.permissions = permissions;
        }

        public static FileAction fromCode(int code) {
            if (code < 0 || code >= values.length) {
                throw new IllegalArgumentException("Invalid FileAction code: " + code);
            }
            return values[code];
        }

        public int getCode() {
            return ordinal();
        }

        public String[] getPermissions() {
            return permissions;
        }
    }

}