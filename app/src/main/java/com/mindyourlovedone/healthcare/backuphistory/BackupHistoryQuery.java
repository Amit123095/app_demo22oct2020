package com.mindyourlovedone.healthcare.backuphistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.BackupHistory;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */

/**
 * Class: VaccineQuery
 * Screen: Add VaccineInfo
 * A class that manages VaccineInfo Table CRUD Operations
 */
public class BackupHistoryQuery {
    public static final String TABLE_NAME = "BackupHistory";
    public static final String COL_ID = "Id";
    public static final String COL_REASON = "Reason";
    public static final String COL_FILENAME = "FileName";
    public static final String COL_DATE = "Date";
    public static final String COL_TYPE = "Type";
    public static final String COL_STATUS = "Status";
    public static final String COL_FILEMETADATA = "FileMetaData";
    public static final String COL_PROFILE = "PROFILE";
    static DBHelperHistory dbHelper;
    Context context;


    public BackupHistoryQuery(Context context, DBHelperHistory dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createBackupHistoryTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FILENAME + " VARCHAR(50)," +
                COL_REASON + " VARCHAR(50)," +
                COL_TYPE + " VARCHAR(20)," +
                COL_STATUS + " VARCHAR(20)," +
                COL_PROFILE + " VARCHAR(20)," +
                COL_FILEMETADATA + " Text," +
                COL_DATE + " VARCHAR(10));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertHistoryData(String filename, String type, String date, String reason,String status,String profile,String filemetadata) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_REASON, reason);
        cv.put(COL_FILENAME, filename);
        cv.put(COL_DATE, date);
        cv.put(COL_TYPE, type);
        cv.put(COL_STATUS, status);
        cv.put(COL_FILEMETADATA, filemetadata);
        cv.put(COL_PROFILE, profile);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid==-1)
        {
            flag=false;
        }else{
            flag=true;
        }

        return flag;
    }

    public static ArrayList<BackupHistory> fetchAllRecord() {
        ArrayList<BackupHistory> historyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);

        //  Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    BackupHistory allergy = new BackupHistory();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setReason(c.getString(c.getColumnIndex(COL_REASON)));
                    allergy.setFileName(c.getString(c.getColumnIndex(COL_FILENAME)));
                    allergy.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    allergy.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    allergy.setStatus(c.getString(c.getColumnIndex(COL_STATUS)));
                    allergy.setProfile(c.getString(c.getColumnIndex(COL_PROFILE)));
                    allergy.setFileMetaData(c.getString(c.getColumnIndex(COL_FILEMETADATA)));

                    historyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return historyList;
    }

    public static Boolean updateCompletedHistoryData(String completed, int lastbackuprecord, String result, String reason) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_STATUS, completed);
        cv.put(COL_FILEMETADATA,result);
        cv.put(COL_REASON,reason);

        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" +lastbackuprecord, null);

        flag = rowid != 0;

        return flag;
    }

    public static Boolean updateFailedHistoryData(String fail, String message, int lastbackuprecord) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_STATUS, fail);
        cv.put(COL_REASON, message);

        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" +lastbackuprecord, null);

        flag = rowid != 0;

        return flag;
    }

   /* public static Boolean updateHistoryData(int id, String value, String date, String others) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

       cv.put(COL_NAME, value);
       cv.put(COL_DATE, date);
       cv.put(COL_OTHER, others);
       cv.put(COL_OTHER, others);
        cv.put(COL_OTHER, others);
        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }

    public static boolean deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        return true;
    }*/

}
