package com.mindyourlovedone.healthcare.DropBox;

import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.AddFileMemberBuilder;
import com.dropbox.core.v2.sharing.FileMemberActionResult;
import com.dropbox.core.v2.sharing.MemberSelector;

import java.util.List;

/**
 * Async task to upload a file to a directory
 */
public class ShareFileTask extends AsyncTask<String, Void, List<FileMemberActionResult>> {

    private final Context mContext;
    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;
    List<MemberSelector> mnewMembers;
    public ShareFileTask(List<MemberSelector> newMembers, Context context, DbxClientV2 dbxClient, Callback callback) {
        mContext = context;
        mDbxClient = dbxClient;
        mCallback = callback;
        mnewMembers=newMembers;
    }


    @Override
    protected void onPostExecute(List<FileMemberActionResult> result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onError(mException);
        } else if (result == null) {
            mCallback.onError(null);
        } else {
            mCallback.onUploadComplete(result);
        }
    }

    @Override
    protected List<FileMemberActionResult> doInBackground(String... params) {


        List<FileMemberActionResult> fileMemberActionResults =
                null;
        try {
if (params[1].equals("MYLO.zip")) {
    return mDbxClient.sharing().addFileMemberBuilder(params[0], mnewMembers).withCustomMessage("I have shared a MYLO Profile with you (" + params[1] + "). \n" +
            "\n" +
            "To upload the profile, open MYLO and tap “Backup,Restore,Share” from menu, then tap Restore button. It will automatically load into the App \n").start();
}else{
    return mDbxClient.sharing().addFileMemberBuilder(params[0], mnewMembers).withCustomMessage("I have shared a MYLO Profile with you (" + params[1] + "). \n" +
            "\n" +
            "To upload the profile, open MYLO and tap “Add a new Profile”, then tap import from Dropbox, then tap restore. It will automatically load into the App \n").start();

}
        } catch (DbxException e) {
            e.printStackTrace();
        }catch (ClassCastException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(fileMemberActionResults);

        return null;
    }

    public interface Callback {
        void onUploadComplete(List<FileMemberActionResult> result);

        void onError(Exception e);
    }
}
