package com.mindyourlovedone.healthcare.DropBox;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.ListFilesResult;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Async task to list items in a shared folder
 */
public class ListReceivedFolderTask extends AsyncTask<String, Void, ArrayList<DropBoxFileItem>> {

    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;
    Preferences preferences;
    Context context;

    public ListReceivedFolderTask(Context filesActivity, DbxClientV2 dbxClient, Callback callback) {
        mDbxClient = dbxClient;
        mCallback = callback;
        context=filesActivity;
        preferences=new Preferences(context);
    }

    @Override
    protected void onPostExecute(ArrayList<DropBoxFileItem> result) {
        super.onPostExecute(result);

        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDataLoaded(result);
        }
    }

    @Override
    protected ArrayList<DropBoxFileItem> doInBackground(String... params) {//Nikita - new changes for merged data
        try {
            ArrayList<DropBoxFileItem> rr = new ArrayList<>();

            ListFilesResult ss = mDbxClient.sharing().listReceivedFiles();
            ListFolderResult ff = mDbxClient.files().listFolder(params[0]);


            //Nikita - changed the sequence - shared files on top now
            for (int i = 0; i < ss.getEntries().size(); i++) {

                if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                    String name = ss.getEntries().get(i).getName();
                    if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {
                        // if (result.getEntries().get(i).getName().endsWith(".pdf")||result.getEntries().get(i).getName().endsWith(".db")) {
                        DropBoxFileItem db = new DropBoxFileItem();
                        db.setShared(1);
                        db.setSharefmd(ss.getEntries().get(i));
                        Date mtime = ss.getEntries().get(i).getTimeInvited();
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        String dateString = formatter.format(mtime);
                        db.setDatetime(dateString);
                        rr.add(db);
                    }
                }else if (preferences.getString(PrefConstants.STORE).equals("Restore")){
                    if (ss.getEntries().get(i).getName().endsWith(".zip")) {
                        DropBoxFileItem db = new DropBoxFileItem();
                        db.setShared(1);
                        db.setSharefmd(ss.getEntries().get(i));
                        Date mtime = ss.getEntries().get(i).getTimeInvited();
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        String dateString = formatter.format(mtime);
                        db.setDatetime(dateString);
                        rr.add(db);
                    }
                }
            }

            Collections.sort(rr, new Comparator<DropBoxFileItem>() {
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                @Override
                public int compare(DropBoxFileItem o1, DropBoxFileItem o2) {
                    try {
                        return formatter.parse(o1.getDatetime()).compareTo(formatter.parse(o2.getDatetime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            for (int i = 0; i < ff.getEntries().size(); i++) {
                if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                    String name = ff.getEntries().get(i).getName();

                if (name.endsWith(".pdf") || name.endsWith(".txt") || name.endsWith(".docx") || name.endsWith(".doc") || name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".ppt") || name.endsWith(".pptx")) {

                    DropBoxFileItem db = new DropBoxFileItem();
                    db.setShared(0);
                    db.setFilemd(ff.getEntries().get(i));

                    Metadata pathMetadata = mDbxClient.files().getMetadata(ff.getEntries().get(i).getPathLower());

                    if (pathMetadata instanceof FileMetadata) {
                        FileMetadata metadata = (FileMetadata) pathMetadata;
                        long mtime = metadata.getServerModified().getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        String dateString = formatter.format(new Date(mtime));
                        db.setDatetime(dateString);
                    } else {

                    }


                    rr.add(db);
                }
                }else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                    if (ff.getEntries().get(i).getName().endsWith(".zip")) {
                        DropBoxFileItem db = new DropBoxFileItem();

                    db.setShared(0);
                    db.setFilemd(ff.getEntries().get(i));

                    Metadata pathMetadata = mDbxClient.files().getMetadata(ff.getEntries().get(i).getPathLower());

                    if (pathMetadata instanceof FileMetadata) {
                        FileMetadata metadata = (FileMetadata) pathMetadata;
                        long mtime = metadata.getServerModified().getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        String dateString = formatter.format(new Date(mtime));
                        db.setDatetime(dateString);
                    } else {

                    }
                    rr.add(db);
                    }
                }
            }

            return rr;
        } catch (DbxException e) {
            mException = e;
        }

        return null;
    }

    public interface Callback {
        void onDataLoaded(ArrayList<DropBoxFileItem> result);

        void onError(Exception e);
    }
}
