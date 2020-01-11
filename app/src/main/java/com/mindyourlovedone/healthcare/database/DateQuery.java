package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.DashBoard.DateClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 10/28/2017.
 */
/**
 * Class: DateQuery
 * Screen: Add Date
 * A class that manages DATES Table CRUD Operations
 */
public class DateQuery {
    public static final String TABLE_NAME = "DATES";
    public static final String COL_ID = "Id";
    //ListView lvNote;
    public static final String COL_USERID = "UserId";
    public static final String COL_PREID = "PreId";
    public static final String COL_DATE = "MedicineName";
    static DBHelper dbHelper;
    Context context;


    public DateQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        DateQuery.dbHelper = dbHelper;
    }

    public static String createDosageTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY," + COL_USERID + " INTEGER," + COL_DATE + " VARCHAR(20)," +
                COL_PREID + " Integer);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static ArrayList<DateClass> fetchAllDosageRecord(int userid, int id) {
        ArrayList<DateClass> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "' and " + COL_PREID + "='" + id + "';", null);
        //  Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid+"';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    DateClass notes = new DateClass();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setDate(c.getString(c.getColumnIndex(COL_DATE)));

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static Boolean insertDosageData(int userid, ArrayList<DateClass> dosageList, int id) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (dosageList != null) {
            for (int i = 0; i < dosageList.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(COL_USERID, userid);
                cv.put(COL_DATE, dosageList.get(i).getDate());
                cv.put(COL_PREID, id);
                long rowid = db.insert(TABLE_NAME, null, cv);

                flag = rowid != -1;
            }
        }
        return flag;
    }

    public static boolean deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';");
            } while (c.moveToNext());
        }
        return false;
    }


    public static boolean deleteRecords(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        return true;
    }

    public static Boolean updateDateData(ArrayList<DateClass> dosageList, int unique, ArrayList<DateClass> d) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < dosageList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_DATE, dosageList.get(i).getDate());

            int rowid = db.update(TABLE_NAME, cv, COL_PREID + "=" + unique + " and " + COL_ID + "=" + d.get(i).getId(), null);

            flag = rowid != 0;
        }
        return flag;
    }

    public static Boolean updateDate(ArrayList<DateClass> list, int id, ArrayList<DateClass> dateList) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < list.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_DATE, list.get(i).getDate());
            int rowid = db.update(TABLE_NAME, cv, COL_PREID + "=" + id + " and " + COL_ID + "=" + dateList.get(i).getId(), null);

            flag = rowid != 0;
        }
        return flag;
    }


    public static List<DateClass> fetchAllDosageRecords(int userid) {
        ArrayList<DateClass> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        //  Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid+"';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    DateClass notes = new DateClass();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setDate(c.getString(c.getColumnIndex(COL_DATE)));

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }
    /*Shradha delete date record*/
    public static boolean deleteDateRecord(int preId, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_PREID + "='" + preId + "' AND  " + COL_DATE + "='" + date + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_PREID + "='" + preId + "' AND " + COL_DATE + "='" + date + "';");
            } while (c.moveToNext());
        }
//        boolean flag = DateQuery.deleteDateRecord(preId,date);

        return true;
    }


}
