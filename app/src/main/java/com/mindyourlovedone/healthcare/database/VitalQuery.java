package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Dosage;
import com.mindyourlovedone.healthcare.model.Hospital;
import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.model.Prescription;
import com.mindyourlovedone.healthcare.model.VitalSigns;

import java.io.File;
import java.util.ArrayList;

/**
 * Class: VitalQuery
 * Screen: Add VitalSigns
 * A class that manages VitalSigns Table CRUD Operations
 */
public class VitalQuery {
    public static final String TABLE_NAME = "VitalSigns";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";

    public static final String COL_LOCATION = "Location";
    public static final String COL_DATE = "Date";
    public static final String COL_TIME = "Time";
    public static final String COL_BP = "BloodPressure";
    public static final String COL_HEART_RATE = "HeartRate";
    public static final String COL_TEMPERATURE = "Temperature";
    public static final String COL_PULSE_RATE = "PulseRate";
    public static final String COL_RESP_RATE = "RespiratoryRate";
    public static final String COL_NOTE = "Note";
    public static final String COL_OTHER = "Other";
    public static final String COL_COLESTEROL = "Cholesterol";
    static DBHelper dbHelper;
    static Context context;

    public VitalQuery(Context context, DBHelper dbHelper) {
        VitalQuery.context = context;
        VitalQuery.dbHelper = dbHelper;
    }

    public static String createVitalTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USERID + " INTEGER," +
                COL_LOCATION + " TEXT," + COL_DATE + " TEXT," + COL_TIME + " TEXT," + COL_BP + " TEXT," +
                COL_HEART_RATE + " TEXT," + COL_TEMPERATURE + " TEXT," + COL_PULSE_RATE + " TEXT," +COL_COLESTEROL + " VARCHAR(50)," +COL_OTHER + " VARCHAR(50)," +
                COL_RESP_RATE + " TEXT," +
                COL_NOTE + " TEXT);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertVitalData(int userid, String location, String date, String time, String bp, String heartRate, String temperature, String pulseRate, String respRate, String note, String oter, String col) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_LOCATION, location);
        cv.put(COL_DATE, date);
        cv.put(COL_TIME, time);
        cv.put(COL_BP, bp);
        cv.put(COL_HEART_RATE, heartRate);
        cv.put(COL_TEMPERATURE, temperature);
        cv.put(COL_PULSE_RATE, pulseRate);
        cv.put(COL_RESP_RATE, respRate);
        cv.put(COL_NOTE, note);
        cv.put(COL_OTHER, oter);
        cv.put(COL_COLESTEROL, col);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }


    public static ArrayList<VitalSigns> fetchAllVitalRecord(int userid) {
        ArrayList<VitalSigns> vitalList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + ";";

        // String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id+ ";";
        Cursor c = db.rawQuery(query, null);

        // Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    VitalSigns vital = new VitalSigns();
                    vital.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    vital.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    vital.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                    vital.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    vital.setTime(c.getString(c.getColumnIndex(COL_TIME)));
                    vital.setBp(c.getString(c.getColumnIndex(COL_BP)));
                    vital.setHeartRate(c.getString(c.getColumnIndex(COL_HEART_RATE)));
                    vital.setTemperature(c.getString(c.getColumnIndex(COL_TEMPERATURE)));
                    vital.setPulseRate(c.getString(c.getColumnIndex(COL_PULSE_RATE)));
                    vital.setRespRate(c.getString(c.getColumnIndex(COL_RESP_RATE)));
                    vital.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    vital.setOther(c.getString(c.getColumnIndex(COL_OTHER)));
                    vital.setCol(c.getString(c.getColumnIndex(COL_COLESTEROL)));

                    vitalList.add(vital);
                } while (c.moveToNext());
            }
        }

        return vitalList;
    }

    public static boolean deleteRecord(int id) {
        ArrayList<VitalSigns> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                VitalSigns vital = new VitalSigns();
                vital.setId(c.getInt(c.getColumnIndex(COL_ID)));
                vital.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                vital.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                vital.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                vital.setTime(c.getString(c.getColumnIndex(COL_TIME)));
                vital.setBp(c.getString(c.getColumnIndex(COL_BP)));
                vital.setHeartRate(c.getString(c.getColumnIndex(COL_HEART_RATE)));
                vital.setTemperature(c.getString(c.getColumnIndex(COL_TEMPERATURE)));
                vital.setPulseRate(c.getString(c.getColumnIndex(COL_PULSE_RATE)));
                vital.setRespRate(c.getString(c.getColumnIndex(COL_RESP_RATE)));
                vital.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                vital.setOther(c.getString(c.getColumnIndex(COL_OTHER)));
                vital.setCol(c.getString(c.getColumnIndex(COL_COLESTEROL)));
                noteList.add(vital);

                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        return true;
    }

    public static Boolean updateVitalData(int id, String location, String date, String time, String bp, String heartRate, String temperature, String pulseRate, String respRate, String note, String oter, String col) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_LOCATION, location);
        cv.put(COL_DATE, date);
        cv.put(COL_TIME, time);
        cv.put(COL_BP, bp);
        cv.put(COL_HEART_RATE, heartRate);
        cv.put(COL_TEMPERATURE, temperature);
        cv.put(COL_PULSE_RATE, pulseRate);
        cv.put(COL_RESP_RATE, respRate);
        cv.put(COL_NOTE, note);
        cv.put(COL_OTHER, oter);
        cv.put(COL_COLESTEROL, col);

        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
       /* int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        if (rowid == 0) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;*/
    }


}