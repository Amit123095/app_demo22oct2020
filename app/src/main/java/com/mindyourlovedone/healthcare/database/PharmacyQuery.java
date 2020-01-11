package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Pharmacy;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 10/6/2017.
 */
/**
 * Class: PharmacyQuery
 * Screen: Add Pharmacy
 * A class that manages Pharmacy Table CRUD Operations
 */
public class PharmacyQuery {
    public static final String TABLE_NAME = "Pharmacy";
    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_OFFICE_PHONE = "OfficePhone";
    public static final String COL_FAX = "Faxno";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_NOTE = "Note";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_PHOTOCARD = "PhotoCard";
    public static final String COL_LOCATOR = "Locator";
    public static final String COL_HASCARD= "Has_Card";
    static Context context;
    static DBHelper dbHelper;

    public PharmacyQuery(Context context, DBHelper dbHelper) {
        PharmacyQuery.context = context;
        PharmacyQuery.dbHelper = dbHelper;
    }


    public static String createPharmacyTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_WEBSITE + " VARCHAR(50),"
                + COL_ADDRESS + " TEXT," + COL_OFFICE_PHONE + " VARCHAR(20)," + COL_FAX +
                " VARCHAR(20)," + COL_NOTE + " TEXT," +
                COL_PHOTOCARD + " VARCHAR(50)," + COL_LOCATOR + " TEXT," +COL_HASCARD + " VARCHAR(10),"+
                COL_PHOTO + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static ArrayList<Pharmacy> fetchAllPharmacyRecord(int userid) {
        ArrayList<Pharmacy> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        // Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Pharmacy notes = new Pharmacy();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    notes.setPhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                    notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                    notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    notes.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                    notes.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                    notes.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));
                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static Boolean insertPharmacyData(int userid, String name, String website, String address, String phone, String photo, String fax, String note, String photoCard, String locator, String has_card) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, userid);
        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_OFFICE_PHONE, phone);
        cv.put(COL_NOTE, note);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_LOCATOR, locator);
        cv.put(COL_HASCARD, has_card);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != 0;

        return flag;
    }

    public static Boolean updatePharmacyData(int id, String name, String website, String address, String phone, String photo, String fax, String note, String photoCard, String locator, String has_card) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_OFFICE_PHONE, phone);
        cv.put(COL_NOTE, note);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_LOCATOR, locator);
        cv.put(COL_HASCARD, has_card);

        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }

    public static boolean deleteRecord(int id) {//nikita - to delete images from storage
        ArrayList<Pharmacy> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                Pharmacy notes = new Pharmacy();
                notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                notes.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                notes.setPhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                notes.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                notes.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                notes.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                notes.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));
                noteList.add(notes);

                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        for (int i = 0; i < noteList.size(); i++) {
            File imgFile = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + noteList.get(i).getPhoto());//nikita
            if (imgFile.exists()) {
                imgFile.delete();
            }
            File imgFilecard = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + noteList.get(i).getPhotoCard());//nikita
            if (imgFilecard.exists()) {
                imgFilecard.delete();
            }
        }

        return true;
    }

    public static Pharmacy getLastPharmacy() {
        ArrayList<Pharmacy> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);
        Pharmacy notes = new Pharmacy();
        // Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {

                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    notes.setPhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                    notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                    notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    notes.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                    notes.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                    notes.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));
                  //  noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return notes;
    }
}
