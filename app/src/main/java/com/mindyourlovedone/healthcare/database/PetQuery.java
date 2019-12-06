package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Pet;

import java.util.ArrayList;

import static com.mindyourlovedone.healthcare.database.AllergyQuery.COL_ALLERGY;
import static com.mindyourlovedone.healthcare.database.AllergyQuery.COL_REACTION;
import static com.mindyourlovedone.healthcare.database.AllergyQuery.COL_TREATMENT;

/**
 * Created by welcome on 9/25/2017.
 */

public class PetQuery {
    public static final String TABLE_NAME = "PetInfo";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_BREED = "Breed";
    public static final String COL_COLOR = "Color";
    public static final String COL_VETERIAN = "Veteran";
    public static final String COL_GUARD = "Guard";
    public static final String COL_VETERIAN_AD = "VeterianAddress";

    public static final String COL_GUARD_AD = "GuardAddress";
    public static final String COL_VETERIAN_PH = "VeterianPhone";
    public static final String COL_GUARD_PH = "GuardPhone";
    public static final String COL_CHIP = "Chip";
    public static final String COL_Bdate = "Bdate";
    public static final String COL_NOTES = "Notes";
    static DBHelper dbHelper;
    Context context;

    public PetQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        PetQuery.dbHelper = dbHelper;
    }

    public static String createPetTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_NAME + " VARCHAR(30)," + COL_BREED + " TEXT," + COL_COLOR + " VARCHAR(30)," +
                COL_VETERIAN + " TEXT," + COL_GUARD + " TEXT," + COL_Bdate + " VARCHAR(50)," + COL_NOTES + " TEXT," + COL_CHIP + " TEXT," +
                COL_VETERIAN_AD + " TEXT," + COL_VETERIAN_PH + " TEXT," +COL_GUARD_AD + " TEXT," + COL_GUARD_PH + " TEXT" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertPetData(int userid, String name, String breed, String color, String chip, String veterain, String care, String bdate, String notes, String veterain_add, String veterain_ph, String care_add, String care_ph) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_BREED, breed);
        cv.put(COL_COLOR, color);
        cv.put(COL_VETERIAN, veterain);
        cv.put(COL_GUARD, care);
        cv.put(COL_CHIP, chip);
        cv.put(COL_NAME, name);
        cv.put(COL_Bdate, bdate);
        cv.put(COL_NOTES, notes);
        cv.put(COL_VETERIAN_AD, veterain_add);
        cv.put(COL_GUARD_AD, care_add);
        cv.put(COL_VETERIAN_PH, veterain_ph);
        cv.put(COL_GUARD_PH, care_ph);
        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<Pet> fetchAllRecord(int userid) {
        ArrayList<Pet> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);//+ " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {

                    Pet allergy = new Pet();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    allergy.setChip(c.getString(c.getColumnIndex(COL_CHIP)));
                    allergy.setColor(c.getString(c.getColumnIndex(COL_COLOR)));
                    allergy.setVeterian(c.getString(c.getColumnIndex(COL_VETERIAN)));
                    allergy.setGuard(c.getString(c.getColumnIndex(COL_GUARD)));
                    allergy.setBreed(c.getString(c.getColumnIndex(COL_BREED)));
                    allergy.setBdate(c.getString(c.getColumnIndex(COL_Bdate)));
                    allergy.setNotes(c.getString(c.getColumnIndex(COL_NOTES)));
                    allergy.setVeterian_add(c.getString(c.getColumnIndex(COL_VETERIAN_AD)));
                    allergy.setVeterian_ph(c.getString(c.getColumnIndex(COL_VETERIAN_PH)));
                    allergy.setCare_add(c.getString(c.getColumnIndex(COL_GUARD_AD)));
                    allergy.setCare_ph(c.getString(c.getColumnIndex(COL_GUARD_PH)));
                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }

    public static Boolean updateAllergyData(int id, String value, String reactions, String treatments) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_ALLERGY, value);
        cv.put(COL_TREATMENT, treatments);
        cv.put(COL_REACTION, reactions);

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

    public static Boolean updatePetData(int id, String name, String breed, String color, String chip, String veterain, String care, String bdate, String notes, String veterain_add, String veterain_ph, String care_add, String care_ph) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        cv.put(COL_BREED, breed);
        cv.put(COL_COLOR, color);
        cv.put(COL_VETERIAN, veterain);
        cv.put(COL_GUARD, care);
        cv.put(COL_CHIP, chip);
        cv.put(COL_NAME, name);
        cv.put(COL_Bdate, bdate);
        cv.put(COL_NOTES, notes);
        cv.put(COL_VETERIAN_AD, veterain_add);
        cv.put(COL_GUARD_AD, care_add);
        cv.put(COL_VETERIAN_PH, veterain_ph);
        cv.put(COL_GUARD_PH, care_ph);
        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }

    public static boolean deleteRecords(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_USERID + "='" + id + "';");
            } while (c.moveToNext());
        }

        return true;
    }
}
