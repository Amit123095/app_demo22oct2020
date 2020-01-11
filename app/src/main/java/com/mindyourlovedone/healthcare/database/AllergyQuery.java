package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Allergy;

import java.util.ArrayList;

/**
 * Class: AllergyQuery
 * Screen: Add Allergy , Medical Information
 * A class that manages AllergyInfo Table CRUD Operations
 */
public class AllergyQuery {
    public static final String TABLE_NAME = "AllergyInfo";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_ALLERGY = "Allergy";
    public static final String COL_REACTION = "Reaction";
    public static final String COL_OTHER_REACTION = "OtherReaction";
    public static final String COL_TREATMENT = "Treatment";
    static DBHelper dbHelper;
    Context context;

    public AllergyQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        AllergyQuery.dbHelper = dbHelper;
    }

    public static String createAllergyTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_ALLERGY + " VARCHAR(200)," + COL_REACTION + " VARCHAR(100)," + COL_OTHER_REACTION + " VARCHAR(100)," +
                COL_TREATMENT + " TEXT" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertAllergyData(int userid, String value, String reaction, String treatment, String otherReaction) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_ALLERGY, value);
        cv.put(COL_TREATMENT, treatment);
        cv.put(COL_REACTION, reaction);
        cv.put(COL_OTHER_REACTION, otherReaction);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<Allergy> fetchAllRecord(int userid) {
        ArrayList<Allergy> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);

        // Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Allergy allergy = new Allergy();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setAllergy(c.getString(c.getColumnIndex(COL_ALLERGY)));
                    allergy.setTreatment(c.getString(c.getColumnIndex(COL_TREATMENT)));
                    allergy.setReaction(c.getString(c.getColumnIndex(COL_REACTION)));
                    allergy.setOtherReaction(c.getString(c.getColumnIndex(COL_OTHER_REACTION)));

                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }

    public static Boolean updateAllergyData(int id, String value, String reactions, String treatments, String otherReactions) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_ALLERGY, value);
        cv.put(COL_TREATMENT, treatments);
        cv.put(COL_REACTION, reactions);
        cv.put(COL_OTHER_REACTION, otherReactions);

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

}
