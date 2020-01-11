package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Living;

/**
 * Created by welcome on 9/24/2017.
 */
/**
 * Class: LivingQuery
 * Screen: Add Living Activity
 * A class that manages Living Table CRUD Operations
 */
public class LivingQuery {

    public static final String TABLE_NAME = "Living";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_FINANCE = "finance";
    public static final String COL_FUNCTION_NOTE = "functionNote";
    public static final String COL_INST_NOTE = "InstNote";
    public static final String COL_PREPARE = "prepare";
    public static final String COL_SHOP = "shop";
    public static final String COL_USE = "use";
    public static final String COL_BATH = "bath";
    public static final String COL_CONTINENCE = "continence";
    public static final String COL_DRESS = "dress";
    public static final String COL_FEED = "feed";
    public static final String COL_TOILETING = "toileting";
    public static final String COL_TRANSFER = "transfer";
    public static final String COL_TRANSPORT = "transport";
    public static final String COL_PETS = "pets";
    public static final String COL_DRIVE = "drive";
    public static final String COL_KEEP = "keep";
    public static final String COL_MEDICATION = "medication";
    public static final String COL_COMPUTER = "computer";
    public static final String COL_REMOTE = "remote";
    public static final String COL_ALERT = "alert";
    public static final String COL_INST_OTHER = "InstOther";
    public static final String COL_FUNCTION_OTHER = "functionOther";
    static DBHelper dbHelper;
    Context context;

    public LivingQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        LivingQuery.dbHelper = dbHelper;
    }

    public static String createLivingTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_FINANCE + " VARCHAR(20)," + COL_INST_NOTE + " TEXT," +
                COL_PREPARE + " VARCHAR(20)," + COL_SHOP + " VARCHAR(20)," + COL_USE + " VARCHAR(20)," +
                COL_REMOTE + " VARCHAR(20)," + COL_ALERT + " VARCHAR(20)," + COL_COMPUTER + " VARCHAR(20)," +
                COL_BATH + " VARCHAR(20)," + COL_FEED + " VARCHAR(20)," + COL_CONTINENCE + " VARCHAR(20)," + COL_DRESS + " VARCHAR(20)," +
                COL_TOILETING + " VARCHAR(20)," + COL_TRANSFER + " VARCHAR(20)," + COL_TRANSPORT + " VARCHAR(20)," +
                COL_DRIVE + " VARCHAR(20)," + COL_KEEP + " VARCHAR(20)," + COL_PETS + " VARCHAR(20)," + COL_MEDICATION + " VARCHAR(20)," +
                COL_INST_OTHER + " TEXT," + COL_FUNCTION_OTHER + " TEXT," + COL_FUNCTION_NOTE + " TEXT" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertLivingData(int userid, String finance, String prepare, String shop, String use, String bath, String continence, String dress, String feed, String toileting, String transfer, String transport, String pets, String drive, String keep, String medication, String functionnote, String fouctionOther, String instaNote, String instaOther, String remote, String alert, String computer) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_FINANCE, finance);
        cv.put(COL_FUNCTION_NOTE, functionnote);
        cv.put(COL_INST_NOTE, instaNote);
        cv.put(COL_PREPARE, prepare);
        cv.put(COL_SHOP, shop);
        cv.put(COL_USE, use);
        cv.put(COL_BATH, bath);
        cv.put(COL_CONTINENCE, continence);
        cv.put(COL_DRESS, dress);
        cv.put(COL_FEED, feed);
        cv.put(COL_TOILETING, toileting);
        cv.put(COL_TRANSFER, transfer);
        cv.put(COL_TRANSPORT, transport);
        cv.put(COL_PETS, pets);
        cv.put(COL_DRIVE, drive);
        cv.put(COL_KEEP, keep);
        cv.put(COL_MEDICATION, medication);
        cv.put(COL_INST_OTHER, instaOther);
        cv.put(COL_FUNCTION_OTHER, fouctionOther);
        cv.put(COL_COMPUTER, computer);
        cv.put(COL_ALERT, alert);
        cv.put(COL_REMOTE, remote);

        Cursor c = LivingQuery.fetchOneRecordCursor(userid);
        if (c.moveToFirst()) {
            int rowid = db.update(TABLE_NAME, cv, COL_USERID + "=" + userid, null);
            flag = rowid != 0;

        } else {
            long rowid = db.insert(TABLE_NAME, null, cv);

            flag = rowid != -1;

        }


        return flag;
    }

    private static Cursor fetchOneRecordCursor(int userid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);

        //  Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        return c;
    }

    public static Living fetchOneRecord(int userid) {
        Living medInfo = new Living();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);

        //  Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);

        if (c.moveToFirst()) {
            do {
                medInfo.setId(c.getInt(c.getColumnIndex(COL_ID)));
                medInfo.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                medInfo.setFinance(c.getString(c.getColumnIndex(COL_FINANCE)));
                medInfo.setFunctionNote(c.getString(c.getColumnIndex(COL_FUNCTION_NOTE)));
                medInfo.setInstNote(c.getString(c.getColumnIndex(COL_INST_NOTE)));
                medInfo.setPrepare(c.getString(c.getColumnIndex(COL_PREPARE)));
                medInfo.setShop(c.getString(c.getColumnIndex(COL_SHOP)));
                medInfo.setUse(c.getString(c.getColumnIndex(COL_USE)));
                medInfo.setBath(c.getString(c.getColumnIndex(COL_BATH)));
                medInfo.setContinence(c.getString(c.getColumnIndex(COL_CONTINENCE)));
                medInfo.setDress(c.getString(c.getColumnIndex(COL_DRESS)));
                medInfo.setFeed(c.getString(c.getColumnIndex(COL_FEED)));
                medInfo.setToileting(c.getString(c.getColumnIndex(COL_TOILETING)));
                medInfo.setTransfer(c.getString(c.getColumnIndex(COL_TRANSFER)));
                medInfo.setTransport(c.getString(c.getColumnIndex(COL_TRANSPORT)));
                medInfo.setPets(c.getString(c.getColumnIndex(COL_PETS)));
                medInfo.setDrive(c.getString(c.getColumnIndex(COL_DRIVE)));
                medInfo.setKeep(c.getString(c.getColumnIndex(COL_KEEP)));
                medInfo.setMedication(c.getString(c.getColumnIndex(COL_MEDICATION)));
                medInfo.setInstOther(c.getString(c.getColumnIndex(COL_INST_OTHER)));
                medInfo.setFunctionOther(c.getString(c.getColumnIndex(COL_FUNCTION_OTHER)));

                medInfo.setComputer(c.getString(c.getColumnIndex(COL_COMPUTER)));
                medInfo.setAlert(c.getString(c.getColumnIndex(COL_ALERT)));
                medInfo.setRemote(c.getString(c.getColumnIndex(COL_REMOTE)));
            } while (c.moveToNext());
        }

        return medInfo;
    }
}
