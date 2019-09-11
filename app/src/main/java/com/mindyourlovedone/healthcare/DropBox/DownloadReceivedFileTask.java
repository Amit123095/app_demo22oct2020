package com.mindyourlovedone.healthcare.DropBox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedFileMetadata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Task to download a file from Dropbox and put it in the Downloads folder
 */
class DownloadReceivedFileTask extends AsyncTask<SharedFileMetadata, Void, File> {

    private final Context mContext;
    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    DownloadReceivedFileTask(Context context, DbxClientV2 dbxClient, Callback callback) {
        mContext = context;
        mDbxClient = dbxClient;
        mCallback = callback;
    }


    @Override
    protected void onPostExecute(final File result) {
        super.onPostExecute(result);

        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDownloadComplete(result);
        }

    }


    @Override
    protected File doInBackground(SharedFileMetadata... params) {
        SharedFileMetadata metadata = params[0];
        try {
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, metadata.getName());

            // Make sure the Downloads directory exists.
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    mException = new RuntimeException("Unable to create directory: " + path);
                }
            } else if (!path.isDirectory()) {
                mException = new IllegalStateException("Download path is not a directory: " + path);
                return null;
            }

            try (OutputStream outputStream = new FileOutputStream(file)) {// Nikita - DropBox API
                mDbxClient.sharing().getSharedLinkFile(metadata.getPreviewUrl())
                        .download(outputStream);
            }

            // Tell android about the file
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri contentUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                contentUri = FileProvider.getUriForFile(mContext, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", file);
            } else {
                contentUri = Uri.fromFile(file);
            }
            intent.setData(contentUri);
            mContext.sendBroadcast(intent);

            return file;
        } catch (Exception e) {
            mException = e;
        }

        return null;
    }

    public interface Callback {
        void onDownloadComplete(File result);
        void onError(Exception e);
    }
}

