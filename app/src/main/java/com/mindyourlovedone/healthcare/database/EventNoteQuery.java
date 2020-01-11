package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Note;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */
/**
 * Class: EventNoteQuery
 * Screen: Add NoteInfo
 * A class that manages NoteInfo Table CRUD Operations
 */
public class EventNoteQuery {
    public static final String TABLE_NAME = "NoteInfo";
    public static final String COL_ID = "Id";
    //ListView lvNote;
    public static final String COL_USERID = "UserId";
    public static final String COL_NOTE = "Note";
    public static final String COL_DATE_TIME = "DateTime";
    static DBHelper dbHelper;
    Context context;

    public EventNoteQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        EventNoteQuery.dbHelper = dbHelper;
    }

    public static String createNoteTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER," + COL_NOTE + " TEXT," + COL_DATE_TIME + " VARCHAR(20));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertNoteData(int userid, String notes, String dt) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_NOTE, notes);
        cv.put(COL_DATE_TIME, dt);


        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<Note> fetchAllNoteRecord(int userid) {
        ArrayList<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        //  Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Note notes = new Note();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setTxtNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setTxtDate(c.getString(c.getColumnIndex(COL_DATE_TIME)));

                    noteList.add(notes);


                } while (c.moveToNext());
            }
        }

        return noteList;
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

    public static Boolean updateEvent(int id, String note, String date) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        cv.put(COL_NOTE, note);
        cv.put(COL_DATE_TIME, date);

        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }

    public static Boolean updateNoteDate(int id, String reportDate) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_DATE_TIME, reportDate);

        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }
}
