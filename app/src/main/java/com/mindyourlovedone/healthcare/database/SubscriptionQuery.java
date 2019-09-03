package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Aides;
import com.mindyourlovedone.healthcare.model.SubscrptionData;

import java.util.ArrayList;

/**
 *
 * Created by Nikita on 10/6/2017. Nikita#Sub - new commit
 */

public class SubscriptionQuery {
    public static final String TABLE_NAME = "Subscription";
    public static final String COL_USER_ID = "UserId";
    public static final String COL_Source = "Source";
    public static final String COL_TransactionID = "TransactionID";
    public static final String COL_StartDate = "StartDate";
    public static final String COL_EndDate = "EndDate";
    public static final String COL_Status = "Status";
    public static final String COL_EMAIL = "Email";
//    public static final String COL_Upload = "Upload";
    public static final String COL_ID = "Id";
    static DBHelper dbHelper;
    Context context;


    public SubscriptionQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        SubscriptionQuery.dbHelper = dbHelper;
    }


    public static String createSubscriptionTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_TransactionID + " TEXT," + COL_StartDate + " TEXT," + COL_EndDate + " TEXT,"
                + COL_EMAIL + " TEXT," + COL_Source + " TEXT,"  + COL_Status + " TEXT);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static SubscrptionData fetchSubscriptionRecord(int userid) {
        ArrayList<Aides> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);
        SubscrptionData notes = new SubscrptionData();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {

                notes.setUserId(c.getInt(c.getColumnIndex(COL_USER_ID)));
                notes.setTransactionID(c.getString(c.getColumnIndex(COL_TransactionID)));
                notes.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                notes.setStartDate(c.getString(c.getColumnIndex(COL_StartDate)));
                notes.setEndDate(c.getString(c.getColumnIndex(COL_EndDate)));
                notes.setSource(c.getString(c.getColumnIndex(COL_Source)));
                notes.setStatus(c.getString(c.getColumnIndex(COL_Status)));
//                notes.setUpload(c.getInt(c.getColumnIndex(COL_Upload)));
            }
        }

        return notes;
    }

    public static Boolean insertSubscriptionData(int userid, SubscrptionData sub) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, userid);
        cv.put(COL_TransactionID, sub.getTransactionID());
        cv.put(COL_Source, sub.getSource());
        cv.put(COL_EMAIL, sub.getEmail());
        cv.put(COL_StartDate, sub.getStartDate());
        cv.put(COL_EndDate, sub.getEndDate());
        cv.put(COL_Status, sub.getStatus());
//        cv.put(COL_Upload, sub.getUpload());

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != 0;

        return flag;
    }

    public static Boolean updateSubscriptionData(int userid, SubscrptionData sub) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_TransactionID, sub.getTransactionID());
        cv.put(COL_Source, sub.getSource());
        cv.put(COL_EMAIL, sub.getEmail());
        cv.put(COL_StartDate, sub.getStartDate());
        cv.put(COL_EndDate, sub.getEndDate());
        cv.put(COL_Status, sub.getStatus());
//        cv.put(COL_Upload, sub.getUpload());



        int rowid = db.update(TABLE_NAME, cv, COL_USER_ID + "=" + userid, null);

        flag = rowid != 0;

        return flag;
    }

    public static boolean deleteRecord(int userid) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';");
            } while (c.moveToNext());
        }

        return true;
    }
}
