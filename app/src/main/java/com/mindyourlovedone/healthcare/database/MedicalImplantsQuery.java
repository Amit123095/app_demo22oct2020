package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Implant;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */
/**
 * Class: MedicalImplantsQuery
 * Screen: Add Medical Implants Activity
 * A class that manages ImplantsInfo Table CRUD Operations
 */
public class MedicalImplantsQuery {
    public static final String TABLE_NAME = "ImplantsInfo";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_DATE = "Date";
    public static final String COL_OTHER = "OtherImplant";
    public static final String COL_LOCATION = "Location";
    public static final String COL_DETAILS = "Details";
    public static final String COL_NOTE_IMPLANT = "Note";
    static DBHelper dbHelper;
    Context context;


    public MedicalImplantsQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        MedicalImplantsQuery.dbHelper = dbHelper;
    }

    public static String createVaccineTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_NAME + " VARCHAR(100)," + COL_OTHER + " VARCHAR(100)," + COL_LOCATION + " VARCHAR(100)," + COL_DETAILS + " VARCHAR(100)," + COL_NOTE_IMPLANT + " TEXT," + COL_DATE + " TEXT" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertImplantData(int userid, String value, String date, String other, String location, String details, String note) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_NAME, value);
        cv.put(COL_DATE, date);
        cv.put(COL_OTHER, other);
        cv.put(COL_LOCATION, location);
        cv.put(COL_DETAILS, details);
        cv.put(COL_NOTE_IMPLANT, note);
        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<Implant> fetchAllRecord(int userid) {
        ArrayList<Implant> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME +/* " where " + COL_USERID + "='" + userid +*/ ";", null);

        // Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Implant allergy = new Implant();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    allergy.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    allergy.setOther(c.getString(c.getColumnIndex(COL_OTHER)));
                    allergy.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                    allergy.setDetails(c.getString(c.getColumnIndex(COL_DETAILS)));
                    allergy.setNotes(c.getString(c.getColumnIndex(COL_NOTE_IMPLANT)));
                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }

    public static Boolean updateImplantData(int id, String value, String date, String otherd, String location, String details, String note) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, value);
        cv.put(COL_DATE, date);
        cv.put(COL_OTHER, otherd);
        cv.put(COL_LOCATION, location);
        cv.put(COL_DETAILS, details);
        cv.put(COL_NOTE_IMPLANT, note);
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
