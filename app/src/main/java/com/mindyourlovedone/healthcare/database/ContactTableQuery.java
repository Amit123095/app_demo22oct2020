package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Contact;

import java.util.ArrayList;

/**
 * Created by varsha on 9/1/2017.
 */
/**
 * Class: ContactTableQuery
 * Screen: Add Contacts
 * A class that manages Contacts Table CRUD Operations
 */
public class ContactTableQuery {
    public static final String TABLE_NAME = "Contacts";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_HPHONE = "home_phone";
    public static final String COL_WPHONE = "work_phone";
    public static final String COL_EMAIL = "email";
    public static final String COL_ADDRESS = "address";
    public static final String COL_IMAGE = "image";
    static DBHelper dbHelper;
    Context context;


    public ContactTableQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        ContactTableQuery.dbHelper = dbHelper;
    }

    public static String createContactTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " VARCHAR(10)," + COL_NAME + " VARCHAR(20)," + COL_EMAIL + " VARCHAR(20)," + COL_ADDRESS + " VARCHAR(20)," + COL_PHONE + " VARCHAR(15)," + COL_HPHONE + " VARCHAR(15)," + COL_WPHONE + " VARCHAR(15)," + COL_IMAGE + " BLOB);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static boolean insertContactData(String id, String name, String number, String email, byte[] image, String address, String homePhone, String workPhone) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, number);
        cv.put(COL_EMAIL, email);
        cv.put(COL_IMAGE, image);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_HPHONE, homePhone);
        cv.put(COL_WPHONE, workPhone);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<Contact> fetchAllContactDetails() {
        ArrayList contactList = new ArrayList();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                int roll = c.getInt(c.getColumnIndex(COL_ID));
                String name = c.getString(c.getColumnIndex(COL_NAME));
                String phone = c.getString(c.getColumnIndex(COL_PHONE));
                String wphone = c.getString(c.getColumnIndex(COL_WPHONE));
                String hphone = c.getString(c.getColumnIndex(COL_HPHONE));
                String email = c.getString(c.getColumnIndex(COL_EMAIL));
                String address = c.getString(c.getColumnIndex(COL_ADDRESS));
                byte[] image = c.getBlob(c.getColumnIndex(COL_IMAGE));

                Contact contact = new Contact();
                contact.setName(name);
                contact.setPhone(phone);
                contact.setHomePhone(hphone);
                contact.setWorkPhone(wphone);
                contact.setEmail(email);
                contact.setImage(image);
                contact.setAddress(address);

                contactList.add(contact);

            } while (c.moveToNext());
        }

        return contactList;
    }

    public static void deleteContactData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
