package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Finance;

import java.util.ArrayList;

/**
 * Created by welcome on 9/28/2017.
 */
/**
 * Class: FinanceQuery
 * Screen: Add FinanceLegal
 * A class that manages FinanceLegal Table CRUD Operations
 */
public class FinanceQuery {
    public static final String TABLE_NAME = "FinanceLegal";
    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_OFFICE_PHONE = "OfficePhone";
    public static final String COL_MOBILE_PHONE = "MobilePhone";
    public static final String COL_OTHER_PHONE = "OtherPhone";
    public static final String COL_CATEGORY = "Category";
    public static final String COL_OTHER_CATEGORY = "OtherCategory";
    public static final String COL_FAX = "Faxno";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_PRACTICENAME = "ContactName";
    public static final String COL_NOTE = "Note";
    public static final String COL_EMAIL = "Email";
    public static final String COL_LOCATION = "Location";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_LASTSEEN = "LastSeen";
    public static final String COL_PHOTOCARD = "PhotoCard";
    public static final String COL_PracticeName = "PracticeName";
    public static final String COL_ContactPerson = "ContactPerson";
    public static final String COL_HASCARD= "Has_Card";
    static DBHelper dbHelper;
    Context context;

    public FinanceQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        FinanceQuery.dbHelper = dbHelper;
    }

    public static ArrayList<Finance> fetchAllFinanceRecord(int id) {
        ArrayList<Finance> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + ";";

        //   String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id+ ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {

                Finance connection = new Finance();
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setHourPhone(c.getString(c.getColumnIndex(COL_MOBILE_PHONE)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
                connection.setContactName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherCategory(c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));

                connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                connection.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                connection.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));

                connectionList.add(connection);

            } while (c.moveToNext());
        }


        return connectionList;
    }

    public static Boolean insertFinanceData(int userId, String name, String website, String address, String officephone, String hourphone, String otherphone, String speciality, String photo, String fax, String practice_name, String note, String lastseen, String otherCategory, String photoCard, String email, String location, String contactName, String has_card) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, userId);
        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_LASTSEEN, lastseen);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_OFFICE_PHONE, officephone);
        cv.put(COL_MOBILE_PHONE, hourphone);
        cv.put(COL_OTHER_PHONE, otherphone);
        cv.put(COL_NOTE, note);
        cv.put(COL_PRACTICENAME, contactName);
        cv.put(COL_CATEGORY, speciality);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_OTHER_CATEGORY, otherCategory);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_EMAIL, email);
        cv.put(COL_LOCATION, location);
        cv.put(COL_HASCARD, has_card);
        cv.put(COL_PracticeName, "");
        cv.put(COL_ContactPerson, "");


        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != 0;

        return flag;
    }

    public static String createFinanceTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_WEBSITE + " VARCHAR(50)," + COL_LASTSEEN + " VARCHAR(50)," +
                COL_MOBILE_PHONE + " VARCHAR(20)," + COL_OTHER_PHONE + " VARCHAR(20)," + COL_ADDRESS + " TEXT," +
                COL_OFFICE_PHONE + " VARCHAR(20)," + COL_CATEGORY + " VARCHAR(50)," + COL_OTHER_CATEGORY + " VARCHAR(50)," + COL_PRACTICENAME + " VARCHAR(30)," + COL_FAX +
                " VARCHAR(20)," + COL_NOTE + " TEXT," + COL_EMAIL + " VARCHAR(50)," + COL_LOCATION + " VARCHAR(50)," +
                COL_PHOTOCARD + " VARCHAR(50)," +COL_HASCARD + " VARCHAR(10),"+
                COL_PHOTO + " VARCHAR(50)," + COL_PracticeName + " VARCHAR(30)," + COL_ContactPerson + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean updateFinanceData(int id, String name, String website, String address, String officephone, String hourphone, String otherphone, String speciality, String photo, String fax, String practice_name, String note, String lastseen, String otherCategory, String photoCard, String email, String location, String contactName, String has_card) {

        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_LASTSEEN, lastseen);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_OFFICE_PHONE, officephone);
        cv.put(COL_MOBILE_PHONE, hourphone);
        cv.put(COL_OTHER_PHONE, otherphone);
        cv.put(COL_NOTE, note);
        cv.put(COL_PRACTICENAME, contactName);
        cv.put(COL_CATEGORY, speciality);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_OTHER_CATEGORY, otherCategory);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_EMAIL, email);
        cv.put(COL_LOCATION, location);
        cv.put(COL_HASCARD, has_card);
        cv.put(COL_PracticeName, practice_name);
        cv.put(COL_ContactPerson, contactName);

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

    public static Finance getLastFinance() {
        ArrayList<Finance> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from " + TABLE_NAME + ";";
        Finance connection = new Finance();
        //   String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id+ ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {


                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setHourPhone(c.getString(c.getColumnIndex(COL_MOBILE_PHONE)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
                connection.setContactName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherCategory(c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));

                connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                connection.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                connection.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));

               // connectionList.add(connection);

            } while (c.moveToNext());
        }


        return connection;
    }
}
