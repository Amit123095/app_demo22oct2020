package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Dosage;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */
/**
 * Class: DosageQuery
 * Screen: Add Dosage
 * A class that manages Dosage Table CRUD Operations
 */
public class DosageQuery {
    public static final String TABLE_NAME = "Dosage";
    public static final String COL_ID = "Id";
    //ListView lvNote;
    public static final String COL_USERID = "UserId";
    public static final String COL_PREID = "PreId";
    public static final String COL_MEDICINE = "MedicineName";
    public static final String COL_FREQUENCY = "Frequency";
    public static final String COL_DOSE = "Dose";
    public static final String COL_RX = "RX";
    static DBHelper dbHelper;
    Context context;

    public DosageQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        DosageQuery.dbHelper = dbHelper;
    }

    public static String createDosageTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY," + COL_USERID + " INTEGER," + COL_MEDICINE + " TEXT," + COL_DOSE + " VARCHAR(100)," + COL_FREQUENCY + " VARCHAR(50)," + COL_RX + " VARCHAR(100)," +
                COL_PREID + " Integer);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertDosageData(int userid, String medicine, String dose) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_MEDICINE, medicine);
        cv.put(COL_DOSE, dose);
        cv.put(COL_FREQUENCY, dose);
        cv.put(COL_RX, dose);
        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<Dosage> fetchAllDosageRecord(int userid, int id) {
        ArrayList<Dosage> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "' and " + COL_PREID + "='" + id + "';", null);
        //  Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid+"';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Dosage notes = new Dosage();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setMedicine(c.getString(c.getColumnIndex(COL_MEDICINE)));
                    notes.setDose(c.getString(c.getColumnIndex(COL_DOSE)));
                    notes.setFrequency(c.getString(c.getColumnIndex(COL_FREQUENCY)));
                    notes.setRx(c.getString(c.getColumnIndex(COL_RX)));
                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static Boolean insertDosageData(int userid, ArrayList<Dosage> dosageList, int id) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < dosageList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_USERID, userid);
            cv.put(COL_MEDICINE, dosageList.get(i).getMedicine());
            cv.put(COL_DOSE, dosageList.get(i).getDose());
            cv.put(COL_FREQUENCY, dosageList.get(i).getFrequency());
            cv.put(COL_PREID, id);
            cv.put(COL_RX, dosageList.get(i).getRx());
            long rowid = db.insert(TABLE_NAME, null, cv);

            flag = rowid != -1;
        }
        return flag;
    }

    public static void deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';");
            } while (c.moveToNext());
        }

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

    public static Boolean updateDosageData(ArrayList<Dosage> dosageList, int unique, ArrayList<Dosage> d) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < dosageList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_MEDICINE, dosageList.get(i).getMedicine());
            cv.put(COL_DOSE, dosageList.get(i).getDose());
            cv.put(COL_FREQUENCY, dosageList.get(i).getFrequency());
            cv.put(COL_RX, dosageList.get(i).getRx());
            int rowid = db.update(TABLE_NAME, cv, COL_PREID + "=" + unique + " and " + COL_ID + "=" + d.get(i).getId(), null);

            flag = rowid != 0;
        }
        return flag;
    }
}
