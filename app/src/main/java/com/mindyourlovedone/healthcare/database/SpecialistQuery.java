package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Specialist;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 9/28/2017.
 */
/**
 * Class: SpecialistQuery
 * Screen: Add Specialist
 * A class that manages Specialist Table CRUD Operations
 */
public class SpecialistQuery {
    public static final String TABLE_NAME = "Specialist";
    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_OFFICE_PHONE = "OfficePhone";
    public static final String COL_HOUR_PHONE = "AfterHourPhone";
    public static final String COL_OTHER_PHONE = "OtherPhone";
    public static final String COL_SPECIALITY = "Speciality";
    public static final String COL_OTHER_SPECIALITY = "OtherSpeciality";
    public static final String COL_FAX = "Faxno";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_PRACTICENAME = "PracticeName";
    public static final String COL_ISPHISYCIAN = "IsPhysician";
    public static final String COL_NOTE = "Note";
    public static final String COL_NETWORK = "NetworkStats";
    public static final String COL_AFFIL = "Affilitations";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_LASTSEEN = "LastSeen";
    public static final String COL_LOCATOR = "Locator";
    public static final String COL_PHOTOCARD = "PhotoCard";
    public static final String COL_HASCARD= "Has_Card";
    static Context context;
    static DBHelper dbHelper;


    public SpecialistQuery(Context context, DBHelper dbHelper) {
        SpecialistQuery.context = context;
        SpecialistQuery.dbHelper = dbHelper;
    }

    public static ArrayList<Specialist> fetchAllPhysicianRecord(int id, int physician) {
        ArrayList<Specialist> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where " + COL_ISPHISYCIAN + "=" + physician + ";";

        //   String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id + " and " + COL_ISPHISYCIAN + "=" + physician + ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {

                Specialist connection = new Specialist();
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setHourPhone(c.getString(c.getColumnIndex(COL_HOUR_PHONE)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setType(c.getString(c.getColumnIndex(COL_SPECIALITY)));
                connection.setHospAffiliation(c.getString(c.getColumnIndex(COL_AFFIL)));
                connection.setPracticeName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setNetwork(c.getString(c.getColumnIndex(COL_NETWORK)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setIsPhysician(c.getInt(c.getColumnIndex(COL_ISPHISYCIAN)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setOtherType(c.getString(c.getColumnIndex(COL_OTHER_SPECIALITY)));
                connectionList.add(connection);

            } while (c.moveToNext());
        }


        return connectionList;
    }
    public static ArrayList<Specialist> fetchPhysicianRecord(int id, int physician) {
        ArrayList<Specialist> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where " + COL_ISPHISYCIAN + "=" + physician + ";";

        //   String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id + " and " + COL_ISPHISYCIAN + "=" + physician + ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {

                Specialist connection = new Specialist();
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setHourPhone(c.getString(c.getColumnIndex(COL_HOUR_PHONE)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setType(c.getString(c.getColumnIndex(COL_SPECIALITY)));
                connection.setHospAffiliation(c.getString(c.getColumnIndex(COL_AFFIL)));
                connection.setPracticeName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setNetwork(c.getString(c.getColumnIndex(COL_NETWORK)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setIsPhysician(c.getInt(c.getColumnIndex(COL_ISPHISYCIAN)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setOtherType(c.getString(c.getColumnIndex(COL_OTHER_SPECIALITY)));
                connectionList.add(connection);

            } while (c.moveToNext());
        }


        return connectionList;
    }
    public static Boolean insertPhysicianData(int userId, String name, String website, String address, String officephone, String hourphone, String otherphone, String speciality, String photo, String fax, String practice_name, String network, String affil, String note, int i, String lastseen, String photoCard, String otherDoctor, String locator,String has_card) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, userId);
        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_LASTSEEN, lastseen);
        cv.put(COL_LOCATOR, locator);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_OFFICE_PHONE, officephone);
        cv.put(COL_HOUR_PHONE, hourphone);
        cv.put(COL_OTHER_PHONE, otherphone);
        cv.put(COL_NOTE, note);
        cv.put(COL_NETWORK, network);
        cv.put(COL_ISPHISYCIAN, i);
        cv.put(COL_AFFIL, affil);
        cv.put(COL_PRACTICENAME, practice_name);
        cv.put(COL_SPECIALITY, speciality);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_OTHER_SPECIALITY, otherDoctor);
        cv.put(COL_HASCARD, has_card);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != 0;

        return flag;
    }

    public static String createDoctorTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_WEBSITE + " VARCHAR(50)," + COL_LASTSEEN + " TEXT," +
                COL_HOUR_PHONE + " VARCHAR(20)," + COL_LOCATOR + " TEXT," + COL_OTHER_PHONE + " VARCHAR(20)," + COL_ADDRESS + " TEXT," +
                COL_OFFICE_PHONE + " VARCHAR(20)," + COL_SPECIALITY + " VARCHAR(50)," + COL_PRACTICENAME + " TEXT," + COL_FAX +
                " VARCHAR(20)," + COL_ISPHISYCIAN + " INTEGER," +
                COL_NETWORK + " TEXT," + COL_AFFIL + " TEXT," + COL_OTHER_SPECIALITY + " TEXT," + COL_NOTE + " TEXT," +
                COL_PHOTOCARD + " VARCHAR(50)," +COL_HASCARD + " VARCHAR(10),"+
                COL_PHOTO + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean updatePhysicianData(int id, String name, String website, String address, String officephone, String hourphone, String otherphone, String speciality, String photo, String fax, String practice_name, String network, String affil, String note, int i, String lastseen, String photoCard, String otherDoctor, String locator,String has_card) {

        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_LASTSEEN, lastseen);
        cv.put(COL_LOCATOR, locator);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_OFFICE_PHONE, officephone);
        cv.put(COL_HOUR_PHONE, hourphone);
        cv.put(COL_OTHER_PHONE, otherphone);
        cv.put(COL_NOTE, note);
        cv.put(COL_NETWORK, network);
        cv.put(COL_ISPHISYCIAN, i);
        cv.put(COL_AFFIL, affil);
        cv.put(COL_PRACTICENAME, practice_name);
        cv.put(COL_SPECIALITY, speciality);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_OTHER_SPECIALITY, otherDoctor);
        cv.put(COL_HASCARD, has_card);
        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }


    public static boolean deleteRecord(int id, int in) {//nikita - to delete images from storage
        ArrayList<Specialist> connectionList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "' and " + COL_ISPHISYCIAN + "='" + in + "';", null);

        if (c.moveToFirst()) {
            do {
                Specialist connection = new Specialist();
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setHourPhone(c.getString(c.getColumnIndex(COL_HOUR_PHONE)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setType(c.getString(c.getColumnIndex(COL_SPECIALITY)));
                connection.setHospAffiliation(c.getString(c.getColumnIndex(COL_AFFIL)));
                connection.setPracticeName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setNetwork(c.getString(c.getColumnIndex(COL_NETWORK)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setIsPhysician(c.getInt(c.getColumnIndex(COL_ISPHISYCIAN)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setOtherType(c.getString(c.getColumnIndex(COL_OTHER_SPECIALITY)));
                connectionList.add(connection);

                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "' and " + COL_ISPHISYCIAN + "='" + in + "';");
            } while (c.moveToNext());
        }

        for (int i = 0; i < connectionList.size(); i++) {
            File imgFile = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + connectionList.get(i).getPhoto());//nikita
            if (imgFile.exists()) {
                imgFile.delete();
            }
            File imgFilecard = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + connectionList.get(i).getPhotoCard());//nikita
            if (imgFilecard.exists()) {
                imgFilecard.delete();
            }
        }

        return true;
    }

    public static ArrayList<Specialist> getPhysician(int anInt, String name, String speciality, int i) {
        ArrayList<Specialist> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where " + COL_ISPHISYCIAN + "=" + i + ";";

        //   String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id + " and " + COL_ISPHISYCIAN + "=" + physician + ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {

                Specialist connection = new Specialist();

                    connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setHourPhone(c.getString(c.getColumnIndex(COL_HOUR_PHONE)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setType(c.getString(c.getColumnIndex(COL_SPECIALITY)));
                connection.setHospAffiliation(c.getString(c.getColumnIndex(COL_AFFIL)));
                connection.setPracticeName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setNetwork(c.getString(c.getColumnIndex(COL_NETWORK)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setIsPhysician(c.getInt(c.getColumnIndex(COL_ISPHISYCIAN)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setOtherType(c.getString(c.getColumnIndex(COL_OTHER_SPECIALITY)));
                if (name.equalsIgnoreCase(connection.getName()) && connection.getIsPhysician()==1) {
                    connectionList.add(connection);
                }

            } while (c.moveToNext());
        }


        return connectionList;
    }

    public static ArrayList<Specialist> getDoctor(int anInt, String name, String speciality, int i) {
        ArrayList<Specialist> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where " + COL_ISPHISYCIAN + "=" + i + ";";

        //   String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id + " and " + COL_ISPHISYCIAN + "=" + physician + ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {

                Specialist connection = new Specialist();

                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setHourPhone(c.getString(c.getColumnIndex(COL_HOUR_PHONE)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setType(c.getString(c.getColumnIndex(COL_SPECIALITY)));
                connection.setHospAffiliation(c.getString(c.getColumnIndex(COL_AFFIL)));
                connection.setPracticeName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setNetwork(c.getString(c.getColumnIndex(COL_NETWORK)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setIsPhysician(c.getInt(c.getColumnIndex(COL_ISPHISYCIAN)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setOtherType(c.getString(c.getColumnIndex(COL_OTHER_SPECIALITY)));
                if (name.equalsIgnoreCase(connection.getName()) && connection.getIsPhysician()==2) {
                    connectionList.add(connection);
                }

            } while (c.moveToNext());
        }


        return connectionList;
    }
}
