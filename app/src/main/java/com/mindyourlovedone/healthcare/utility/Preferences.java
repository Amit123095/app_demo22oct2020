package com.mindyourlovedone.healthcare.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

/**
 * Created by varsha on 8/21/2017.
 */

public class Preferences {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context mContext;
    private String FIRST_TIME_CALL = "FIRST_TIME_CALL";
    private String REGISTERED = "REGISTERED";
    private String SUBSCRIBED = "SUBSCRIBED";
    private String LOGIN = "LOGIN";

    public Preferences() {

    }

    public Preferences(Context context) {
        // TODO Auto-generated constructor stub\
        mContext = context;
        preferences = context.getSharedPreferences("MYLOPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean isLogin() {
        return preferences.getBoolean(LOGIN, true);
    }

    public void setLogin(boolean firstTimeCall) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.LOGIN, firstTimeCall);
        editor.commit();
    }
    public Boolean getFirstTime() {
        return preferences.getBoolean(PrefConstants.FIRST_TIME, false);
    }

    public void setFirstTime(Boolean frst) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PrefConstants.FIRST_TIME, frst);
        editor.commit();
    }
    public boolean isFirstTimeCall() {
        return preferences.getBoolean(FIRST_TIME_CALL, true);
    }

    public void setFirstTimeCall(boolean firstTimeCall) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.FIRST_TIME_CALL, firstTimeCall);
        editor.commit();
    }

    public Boolean getREGISTERED() {
        return preferences.getBoolean(REGISTERED, false);
    }

    public void setREGISTERED(Boolean REGISTERED) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.REGISTERED, REGISTERED);
        editor.commit();
    }

    public Boolean getSubscribed() {
        return preferences.getBoolean(SUBSCRIBED, false);
    }

    public void setSubscribed(Boolean SUBSCRIBED) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.SUBSCRIBED, SUBSCRIBED);
        editor.commit();
    }

    public Preferences getInstance(Context ctx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        mContext = ctx;
        return this;
    }

    public void clearPreferences() {
        editor.clear();
        editor.commit();
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }


    public void emailAttachement(File f, Context context, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
        //   String name= getString(PrefConstants.CONNECTED_NAME);
        String username = getString(PrefConstants.USER_NAME);

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                username + " - " + s); // subject


        String body = "Hi, \n" +
                "\n" +
                // "\n" + username +
                "I shared these document with you. Please check the attachment. \n" +
                "\n" +
                "Thank you,\n" +
                username;
        //"Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedone.healthcare.HomeActivity.fileProvider", f);
        } else {
            uri = Uri.fromFile(f);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
//emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        emailIntent.setType("application/email");

        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void copyFile(String filename, Context context) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;

        try {
            File dir = new File("/sdcard/MYLO/images/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.i("tag", "copyFile() " + filename);
            in = assetManager.open(filename);
            newFileName = "/sdcard/MYLO/images/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of " + newFileName);
            Log.e("tag", "Exception in copyFile() " + e.toString());
        }

    }

    public Image addFile(String s, Context context) {
        Image logostreamp=null;
        try {
            InputStream logostream=context.getAssets().open(s);
            logostreamp=addProfile(logostream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logostreamp;
    }
    public static Image addProfile(InputStream path) {
        Image image = null;
        try {
            // get input stream
            InputStream ims = path;
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 40, stream);
            image = Image.getInstance(stream.toByteArray());
            //  document.add(image);


        } catch (BadElementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return image;

    }
}
