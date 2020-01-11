package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Document;

import java.util.ArrayList;

/**
 * Created by welcome on 10/7/2017.
 */
/**
 * Class: DocumentQuery
 * Screen: Add Documents
 * A class that manages Documents Table CRUD Operations
 */
public class DocumentQuery {
    public static final String TABLE_NAME = "Documents";
    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "DocumentType";
    public static final String COL_DATE = "DateSigned";
    public static final String COL_HOLDER = "HolderName";
    public static final String COL_LOCATION = "Location";
    public static final String COL_DOCUMENT = "Document";
    public static final String COL_CATEGORY = "Category";
    public static final String COL_PERSON = "Person";
    public static final String COL_PRINCIPLE = "Principle";
    public static final String COL_HOSP = "Hospital";
    public static final String COL_OTHER = "Other_Category";
    public static final String COL_FROM = "Froms";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_OTHER_DOC = "OtherDocType";
    public static final String COL_LOCATOR = "Locator";
    public static final String COL_NOTE = "Note";
    static DBHelper dbHelper;
    Context context;

    public DocumentQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        DocumentQuery.dbHelper = dbHelper;
    }


    public static String createDocumentTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(100)," + COL_DATE + " VARCHAR(50)," + COL_LOCATOR + " VARCHAR(40),"
                + COL_OTHER + " VARCHAR(100)," + COL_OTHER_DOC + " VARCHAR(100)," + COL_HOSP + " VARCHAR(100)," + COL_TYPE + " VARCHAR(100)," + COL_HOLDER + " VARCHAR(50)," + COL_LOCATION + " VARCHAR(50)," + COL_NOTE + " TEXT," +
                COL_CATEGORY + " VARCHAR(50)," + COL_FROM + " VARCHAR(50)," + COL_PERSON + " VARCHAR(100)," + COL_PRINCIPLE + " VARCHAR(100)," +
                COL_DOCUMENT + " VARCHAR(100)," +
                COL_PHOTO + " INTEGER);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static ArrayList<Document> fetchAllDocumentRecord(int userid, String from) {
        ArrayList<Document> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_FROM + "='" + from + "';", null);

        // Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "' and "+COL_FROM + "='" + from + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Document notes = new Document();

                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    notes.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    notes.setHolder(c.getString(c.getColumnIndex(COL_HOLDER)));
                    notes.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                    notes.setDocument(c.getString(c.getColumnIndex(COL_DOCUMENT)));
                    notes.setCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
                    notes.setImage(c.getInt(c.getColumnIndex(COL_PHOTO)));
                    notes.setFrom(c.getString(c.getColumnIndex(COL_FROM)));
                    notes.setPrinciple(c.getString(c.getColumnIndex(COL_PRINCIPLE)));
                    notes.setPerson(c.getString(c.getColumnIndex(COL_PERSON)));
                    notes.setOtherCategory(c.getString(c.getColumnIndex(COL_OTHER)));
                    notes.setHospital(c.getString(c.getColumnIndex(COL_HOSP)));
                    notes.setOtherDoc(c.getString(c.getColumnIndex(COL_OTHER_DOC)));
                    notes.setLocator(c.getString(c.getColumnIndex(COL_LOCATOR)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }
        return noteList;
    }

    public static Boolean insertDocumentData(int userid, String name, String category, String date, String loacation, String holder, int photo, String document, String type, String from, String person, String principle, String otherCategory, String hosp, String otherDocType, String locator, String note) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, userid);
        cv.put(COL_NAME, name);
        cv.put(COL_DATE, date);
        cv.put(COL_HOLDER, holder);
        cv.put(COL_TYPE, type);
        cv.put(COL_LOCATION, loacation);
        cv.put(COL_DOCUMENT, name);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_CATEGORY, category);
        cv.put(COL_FROM, from);
        cv.put(COL_PRINCIPLE, principle);
        cv.put(COL_PERSON, person);
        cv.put(COL_HOSP, hosp);
        cv.put(COL_OTHER, otherCategory);
        cv.put(COL_OTHER_DOC, otherDocType);
        cv.put(COL_LOCATOR, locator);
        cv.put(COL_NOTE, note);
        long rowid = db.insert(TABLE_NAME, null, cv);

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

    public static Boolean updateDocumentData(int id, String name, String category, String date, String loacation, String holder, int photo, String document, String type, String from, String person, String principle, String otherCategory, String hosp, String otherDocType, String locator, String note) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_DATE, date);
        cv.put(COL_HOLDER, holder);
        cv.put(COL_TYPE, type);
        cv.put(COL_LOCATION, loacation);
        cv.put(COL_DOCUMENT, name);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_CATEGORY, category);
        cv.put(COL_FROM, from);
        cv.put(COL_PRINCIPLE, principle);
        cv.put(COL_PERSON, person);
        cv.put(COL_HOSP, hosp);
        cv.put(COL_OTHER, otherCategory);
        cv.put(COL_OTHER_DOC, otherDocType);
        cv.put(COL_LOCATOR, locator);
        cv.put(COL_NOTE, note);
        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }

}
