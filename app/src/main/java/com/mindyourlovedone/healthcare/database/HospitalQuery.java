package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Hospital;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */
/**
 * Class: HospitalQuery
 * Screen: Add HospitalInfo
 * A class that manages HospitalInfo Table CRUD Operations
 */
public class HospitalQuery {
    public static final String TABLE_NAME = "HospitalInfo";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_HOSPITAL = "Hospital";
    static DBHelper dbHelper;
    Context context;

    public HospitalQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        HospitalQuery.dbHelper = dbHelper;
    }

    public static String createHospitalTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_HOSPITAL + " TEXT" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertHospitalData(int userid, String value) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_HOSPITAL, value);


        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<String> fetchAllRecord(int userid) {
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    arrayList.add(c.getString(c.getColumnIndex(COL_HOSPITAL)));
                } while (c.moveToNext());
            }
        }

        return arrayList;
    }

    public static boolean deleteRecord(int userid, String s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_HOSPITAL + "='" + s + "' and " + COL_USERID + "='" + userid + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_HOSPITAL + "='" + s + "' and " + COL_USERID + "='" + userid + "';");
            } while (c.moveToNext());
        }

        return true;
    }

    public static Boolean updateHospitalData(int userid, String value, String name) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int rowid = db.update(TABLE_NAME,
                cv,
                COL_USERID + " = " + userid + " AND " + COL_HOSPITAL + " = '" + name + "'",
                null);
        flag = rowid != 0;
        return flag;
    }


}
