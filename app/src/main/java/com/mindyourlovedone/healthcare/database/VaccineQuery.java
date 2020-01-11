package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Vaccine;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */
/**
 * Class: VaccineQuery
 * Screen: Add VaccineInfo
 * A class that manages VaccineInfo Table CRUD Operations
 */
public class VaccineQuery {
    public static final String TABLE_NAME = "VaccineInfo";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_DATE = "Date";
    public static final String COL_OTHER = "OtherVaccine";
    static DBHelper dbHelper;
    Context context;


    public VaccineQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        VaccineQuery.dbHelper = dbHelper;
    }

    public static String createVaccineTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_NAME + " VARCHAR(100)," + COL_OTHER + " TEXT," + COL_DATE + " TEXT" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertVaccineData(int userid, String value, String date, String other) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_NAME, value);
        cv.put(COL_DATE, date);
        cv.put(COL_OTHER, other);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<Vaccine> fetchAllRecord(int userid) {
        ArrayList<Vaccine> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);

        //  Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Vaccine allergy = new Vaccine();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    allergy.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    allergy.setOther(c.getString(c.getColumnIndex(COL_OTHER)));
                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }

    public static Boolean updateVaccineData(int id, String value, String date, String others) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, value);
        cv.put(COL_DATE, date);
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
    }

}
