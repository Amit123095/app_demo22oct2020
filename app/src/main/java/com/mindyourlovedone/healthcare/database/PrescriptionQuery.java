package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Dosage;
import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.model.Prescription;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */
/**
 * Class: PrescriptionQuery
 * Screen: Add Prescription
 * A class that manages Prescription Table CRUD Operations
 */
public class PrescriptionQuery {
    public static final String TABLE_NAME = "Prescription";
    public static final String COL_ID = "Id";
    //ListView lvNote;
    public static final String COL_USERID = "UserId";
    public static final String COL_DOCTOR = "Doctor";
    public static final String COL_UNIQUE = "UniqueNumber";
    public static final String COL_PURPOSE = "Purpose";
    public static final String COL_NOTE = "Note";
    public static final String COL_DATE_TIME = "DateTime";
    public static final String COL_PRE = "Prescription";
    public static final String COL_RX = "RX";
    public static final String COL_MEDICINE = "MedicineName";
    public static final String COL_FREQUENCY = "Frequency";
    public static final String COL_DOSE = "Dose";
    static Context context;
    static DBHelper dbHelper;

    public PrescriptionQuery(Context context, DBHelper dbHelper) {
        PrescriptionQuery.context = context;
        PrescriptionQuery.dbHelper = dbHelper;
        DosageQuery d = new DosageQuery(context, dbHelper);
        PrescribeImageQuery p = new PrescribeImageQuery(context, dbHelper);
    }

    public static String createPrescriptionTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER," + COL_UNIQUE + " INTEGER," + COL_NOTE + " TEXT," + COL_DOCTOR + " VARCHAR(20)," + COL_MEDICINE + " TEXT," + COL_DOSE + " VARCHAR(100)," + COL_FREQUENCY + " VARCHAR(50)," + COL_PURPOSE + " VARCHAR(50)," + COL_PRE + " VARCHAR(20)," + COL_RX + " VARCHAR(100)," + COL_DATE_TIME + " VARCHAR(10));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertPrescriptionData(int userid, String doctor, String purpose, String notes, String dt, ArrayList<Dosage> dosageList, ArrayList<PrescribeImage> imageList, int unique, String pre, String rx, String dose, String frequency, String medicine) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_NOTE, notes);
        cv.put(COL_DATE_TIME, dt);
        cv.put(COL_DOCTOR, doctor);
        cv.put(COL_PURPOSE, purpose);
        cv.put(COL_UNIQUE, unique);
        cv.put(COL_RX, rx);
        cv.put(COL_PRE, pre);
        cv.put(COL_MEDICINE, medicine);
        cv.put(COL_DOSE, dose);
        cv.put(COL_FREQUENCY, frequency);


        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;

/*
            Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "' and " + COL_UNIQUE + "='" + unique + "';", null);
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {*/
            Boolean flags = DosageQuery.insertDosageData(userid, dosageList, unique);
            Boolean flag3 = PrescribeImageQuery.insertImageData(userid, imageList, unique);
                   /* } while (c.moveToNext());
                }
            }*/
        }
       /* Boolean flags = DosageQuery.insertDosageData(userid,dosageList);
        Boolean flag3 = PrescribeImageQuery.insertImageData(userid,imageList);*/


        return flag;
    }

    public static ArrayList<Prescription> fetchAllPrescrptionRecord(int userid) {
        ArrayList<Prescription> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        //Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Prescription notes = new Prescription();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setDates(c.getString(c.getColumnIndex(COL_DATE_TIME)));
                    notes.setDoctor(c.getString(c.getColumnIndex(COL_DOCTOR)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setPurpose(c.getString(c.getColumnIndex(COL_PURPOSE)));
                    notes.setUnique(c.getInt(c.getColumnIndex(COL_UNIQUE)));
                    notes.setPre(c.getString(c.getColumnIndex(COL_PRE)));
                    notes.setRX(c.getString(c.getColumnIndex(COL_RX)));
                    notes.setMedicine(c.getString(c.getColumnIndex(COL_MEDICINE)));
                    notes.setDose(c.getString(c.getColumnIndex(COL_DOSE)));
                    notes.setFrequency(c.getString(c.getColumnIndex(COL_FREQUENCY)));
                    ArrayList<Dosage> Dosagelist = DosageQuery.fetchAllDosageRecord(c.getInt(c.getColumnIndex(COL_USERID)), c.getInt(c.getColumnIndex(COL_UNIQUE)));
                    if (Dosagelist.size() != 0) {
                        notes.setDosageList(Dosagelist);
                    }
                    ArrayList<PrescribeImage> imageList = PrescribeImageQuery.fetchAllImageRecord(c.getInt(c.getColumnIndex(COL_USERID)), c.getInt(c.getColumnIndex(COL_UNIQUE)));
                    if (imageList.size() != 0) {
                        notes.setPrescriptionImageList(imageList);
                    }

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static boolean deleteRecord(int unique) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_UNIQUE + "='" + unique + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_UNIQUE + "='" + unique + "';");
            } while (c.moveToNext());
        }
        DosageQuery.deleteRecord(unique);
        PrescribeImageQuery.deleteRecord(unique);
        return true;
    }

    public static Boolean updatePrescriptionData(int colid, int id, String doctor, String purpose, String note, String date, ArrayList<Dosage> dosageList, ArrayList<PrescribeImage> imageList, int userid, String pre, String rx, String dose, String frequency, String medicine, ArrayList<PrescribeImage> imageListOld) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_NOTE, doctor);
        cv.put(COL_DATE_TIME, date);
        cv.put(COL_DOCTOR, doctor);
        cv.put(COL_NOTE, note);
        cv.put(COL_PURPOSE, purpose);
        cv.put(COL_PRE, pre);
        cv.put(COL_RX, rx);
        cv.put(COL_MEDICINE, medicine);
        cv.put(COL_DOSE, dose);
        cv.put(COL_FREQUENCY, frequency);
        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + colid, null);

        if (rowid == 0) {
            flag = false;
        } else {
            flag = true;
            //ArrayList<Dosage> d=DosageQuery.fetchAllDosageRecord(userid,id);


            //  Boolean flags = DosageQuery.updateDosageData(dosageList,id,d);
            ArrayList<PrescribeImage> i = PrescribeImageQuery.fetchAllImageRecord(userid, id);
            Boolean flag3 = PrescribeImageQuery.updateImageData(imageList, id, i, imageListOld, userid);
        }
        return flag;
    }
}
