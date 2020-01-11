package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.DashBoard.DateClass;
import com.mindyourlovedone.healthcare.model.Appoint;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */
/**
 * Class: AppointmentQuery
 * Screen: Add Appointment
 * A class that manages Appointment Table CRUD Operations
 */
public class AppointmentQuery {
    public static final String TABLE_NAME = "Appointment";
    public static final String COL_ID = "Id";
    //ListView lvNote;
    public static final String COL_USERID = "UserId";
    public static final String COL_TYPE = "Type";
    public static final String COL_DOCTORNAME = "Doctor";
    public static final String COL_UNIQUE = "UniqueNumber";
    public static final String COL_FREQUENCY = "Frequency";
    public static final String COL_OTHER_FREQUENCY = "OtherFrequency";
    public static final String COL_OTHER_COCTOR = "OtherDoctor";
    public static final String COL_DATE_TIME = "DateTime";
    public static final String COL_NOTE = "Note";
    static DBHelper dbHelper;
    Context context;

    public AppointmentQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        AppointmentQuery.dbHelper = dbHelper;
    }

    public static String createAppointmentTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER," + COL_UNIQUE + " INTEGER," + COL_TYPE + " VARCHAR(50)," + COL_DOCTORNAME + " VARCHAR(50)," + COL_OTHER_COCTOR + " VARCHAR(50)," + COL_OTHER_FREQUENCY + " VARCHAR(50)," + COL_FREQUENCY + " VARCHAR(50)," + COL_NOTE + " TEXT," + COL_DATE_TIME + " VARCHAR(20));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean  insertAppointmentData(int userid, String name, String date, String note, String type, String frequency, String otherType, String otherFrequency, ArrayList<DateClass> dateList, int unique) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_TYPE, type);
        cv.put(COL_FREQUENCY, frequency);
        cv.put(COL_DOCTORNAME, name);
        cv.put(COL_DATE_TIME, date);
        cv.put(COL_NOTE, note);
        cv.put(COL_OTHER_COCTOR, otherType);
        cv.put(COL_OTHER_FREQUENCY, otherFrequency);
        cv.put(COL_UNIQUE, unique);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;
        if (dateList != null) {
            Boolean flags = DateQuery.insertDosageData(userid, dateList, unique);
        }
        return flag;
    }

    public static ArrayList<Appoint> fetchAllAppointmentRecord(int userid) {
        ArrayList<Appoint> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        // Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Appoint notes = new Appoint();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setDoctor(c.getString(c.getColumnIndex(COL_DOCTORNAME)));
                    notes.setFrequency(c.getString(c.getColumnIndex(COL_FREQUENCY)));
                    notes.setDate(c.getString(c.getColumnIndex(COL_DATE_TIME)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    notes.setOtherFrequency(c.getString(c.getColumnIndex(COL_OTHER_FREQUENCY)));
                    notes.setOtherDoctor(c.getString(c.getColumnIndex(COL_OTHER_COCTOR)));
                    notes.setUnique(c.getInt(c.getColumnIndex(COL_UNIQUE)));

                    ArrayList<DateClass> Dosagelist = DateQuery.fetchAllDosageRecord(c.getInt(c.getColumnIndex(COL_USERID)), c.getInt(c.getColumnIndex(COL_UNIQUE)));
                    if (Dosagelist.size() != 0) {
                        notes.setDateList(Dosagelist);
                    }
                    noteList.add(notes);


                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static boolean deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_UNIQUE + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_UNIQUE + "='" + id + "';");
            } while (c.moveToNext());
        }
        boolean flag = DateQuery.deleteRecord(id);
        return true;
    }



    public static Boolean addDate(String string, int id, String reportDate) {
        return null;
    }

    public static Boolean updateAppointmentData(int id, String name, String date, String note, String type, String frequency, String otherType, String otherFrequency, ArrayList<DateClass> dateList, int unique) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        cv.put(COL_TYPE, type);
        cv.put(COL_FREQUENCY, frequency);
        cv.put(COL_DOCTORNAME, name);
        cv.put(COL_DATE_TIME, date);
        cv.put(COL_NOTE, note);
        cv.put(COL_OTHER_COCTOR, otherType);
        cv.put(COL_OTHER_FREQUENCY, otherFrequency);
        cv.put(COL_UNIQUE, unique);

        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }

}
