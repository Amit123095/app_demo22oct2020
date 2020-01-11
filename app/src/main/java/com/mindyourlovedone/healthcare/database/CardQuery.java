package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Card;

import java.util.ArrayList;

/**
 * Created by welcome on 10/10/2017.
 */
/**
 * Class: CardQuery
 * Screen: Add InsuranceCard
 * A class that manages InsuranceCard Table CRUD Operations
 */
public class CardQuery {
    public static final String TABLE_NAME = "InsuranceCard";
    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "ProviderType";
    public static final String COL_FRONT = "FrontPhoto";
    public static final String COL_BACK = "BackPhoto";
    public static final String COL_ID = "Id";
    public static final String COL_Oter = "OtherInsurance";
    static DBHelper dbHelper;
    Context context;

    public CardQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        CardQuery.dbHelper = dbHelper;
    }


    public static String createCardTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50),"
                + COL_TYPE + " VARCHAR(50)," + COL_BACK + " VARCHAR(50)," +COL_Oter + " VARCHAR(50)," +
                COL_FRONT + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static boolean insertInsuranceCardData(int userid, String name, String type, String photo1, String photo2, String oter) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean flag = false;
        try {

            ContentValues cv = new ContentValues();
            cv.put(COL_USER_ID, userid);
            cv.put(COL_NAME, name);
            cv.put(COL_TYPE, type);
            cv.put(COL_FRONT, photo1);
            cv.put(COL_BACK, photo2);
            cv.put(COL_Oter, oter);

            long rowid = db.insert(TABLE_NAME, null, cv);

            flag = rowid != 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return flag;
    }

    public static ArrayList<Card> fetchAllCardRecord(int userid) {
        ArrayList<Card> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        //   Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Card notes = new Card();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    notes.setImgFront(c.getString(c.getColumnIndex(COL_FRONT)));
                    notes.setImgBack(c.getString(c.getColumnIndex(COL_BACK)));
                    notes.setOtertype(c.getString(c.getColumnIndex(COL_Oter)));

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }
        return noteList;
    }

    public static boolean deleteRecord(int id, int i) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        return true;
    }

    public static boolean updateInsuranceCardData(int id, String name, String type, String photo1, String photo2, String oter) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean flag = false;

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_TYPE, type);
        cv.put(COL_FRONT, photo1);
        cv.put(COL_BACK, photo2);
        cv.put(COL_Oter, oter);

        int rowid = db.update(TABLE_NAME,
                cv,
                COL_ID + " = " + id,
                null);

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
}
