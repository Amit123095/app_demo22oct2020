package com.mindyourlovedone.healthcare.DropBox;

import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.ListFilesResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Async task to list items in a folder
 */
public class ListReceivedFolderTask extends AsyncTask<String, Void, ArrayList<DropBoxFileItem>> {

    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    public ListReceivedFolderTask(DbxClientV2 dbxClient, Callback callback) {
        mDbxClient = dbxClient;
        mCallback = callback;
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
                DropBoxFileItem db = new DropBoxFileItem();
                db.setShared(1);
                db.setSharefmd(ss.getEntries().get(i));
                rr.add(db);
            }

            for (int i = 0; i < ff.getEntries().size(); i++) {
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
