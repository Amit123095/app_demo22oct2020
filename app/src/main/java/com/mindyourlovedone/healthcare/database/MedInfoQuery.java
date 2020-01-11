package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.MedInfo;

/**
 * Created by welcome on 9/24/2017.
 */
/**
 * Class: MedInfoQuery
 * Screen: Add MedInfo
 * A class that manages MedInfo Table CRUD Operations
 */
public class MedInfoQuery {

    public static final String TABLE_NAME = "MedInfo";
    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_NOTE = "ConditionNote";
    public static final String COL_MOUTH_NOTE = "MouthNote";
    public static final String COL_EYE_GLASSES = "Glasses";
    public static final String COL_EYE_LENSE = "Lense";
    public static final String COL_TEETH_FALSE = "UpperMouth";
    public static final String COL_TEETH_IMPLANTS = "LowerMouth";
    public static final String COL_TEETH_MOUTH = "DryMouth";
    public static final String COL_HEARING_AIDES = "Aides";
    public static final String COL_BLOODTYPE = "BloodType";
    public static final String COL_ORGANDONOR = "OrganDonor";
    public static final String COL_SPEECH = "Speech";
    public static final String COL_BLIND = "Blind";
    public static final String COL_AIDE_NOTE = "Aide_Note";
    public static final String COL_VISION_NOTE = "VisionNote";
    public static final String COL_DIET_NOTE = "DietNote";
    public static final String COL_ALLERGY_NOTE = "AllergyNote";
    public static final String COL_TOBACO = "Tobaco";
    public static final String COL_T_TYPE = "Tobaco_type";
    public static final String COL_T_AMT = "Tobaco_amt";
    public static final String COL_T_YEAR = "Tobaco_year";
    public static final String COL_DRINK = "Drink";
    public static final String COL_DRINK_AMT = "Drink_amt";
    public static final String COL_DRINK_YEAR = "Drink_Year";
    public static final String COL_DRUG = "Drug";
    public static final String COL_DRUG_TYPE = "Drug_type";
    public static final String COL_DRUG_AMT = "Drug_Amt";
    public static final String COL_DRUG_YEAR = "Drug_Year";
    public static final String COL_Mouth = "Mouth";
    public static final String COL_FunctionNote = "FunctionNote";
    public static final String COL_HISTORY_NOTE = "SurgicalHistoryNote";
    public static final String COL_IMPLANTS_NOTE = "ImplantsNote";
    public static final String COL_VACCINE_NOTE = "ImmunizationNote";
   // public static final String COL_BLOOD_NOTE = "BloodNote";
    static DBHelper dbHelper;
    Context context;

    public MedInfoQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        MedInfoQuery.dbHelper = dbHelper;
    }

    public static String createMedInfoTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_EYE_GLASSES + " VARCHAR(20)," + COL_EYE_LENSE + " VARCHAR(20)," +
                COL_TEETH_FALSE + " VARCHAR(20)," + COL_TEETH_IMPLANTS + " VARCHAR(20)," + COL_HEARING_AIDES + " VARCHAR(20)," +
                COL_BLOODTYPE + " VARCHAR(20)," + COL_ORGANDONOR + " VARCHAR(20)," + COL_NOTE + " TEXT," +
                COL_SPEECH + " VARCHAR(20)," + COL_BLIND + " VARCHAR(20)," + COL_AIDE_NOTE + " TEXT," +
                COL_VACCINE_NOTE + " TEXT," +COL_IMPLANTS_NOTE + " TEXT," +COL_HISTORY_NOTE + " TEXT," +

                COL_VISION_NOTE + " TEXT," + COL_DIET_NOTE + " TEXT," +
                COL_MOUTH_NOTE + " TEXT," + COL_TEETH_MOUTH + " VARCHAR(20)," + COL_ALLERGY_NOTE + " TEXT," +
                COL_TOBACO + " VARCHAR(20)," + COL_T_TYPE + " VARCHAR(20)," +
                COL_T_AMT + " VARCHAR(20)," + COL_T_YEAR + " VARCHAR(20)," +
                COL_DRINK + " VARCHAR(20)," + COL_DRINK_AMT + " VARCHAR(20)," +
                COL_DRUG + " VARCHAR(20)," + COL_DRINK_YEAR + " VARCHAR(20)," + COL_Mouth + " VARCHAR(20)," + COL_FunctionNote + " VARCHAR(20)," + COL_DRUG_AMT + " VARCHAR(20)," +
                COL_DRUG_YEAR + " VARCHAR(20)," + COL_DRUG_TYPE + " VARCHAR(20)" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertMedInfoData(int userid, String blood, String glass, String lense, String falses, String implants, String aid, String donor, String note, String mouth, String mouthnote, String visionnote, String aidenote, String dietnote, String blind, String speech, String allergynote, String tobaco, String t_type, String t_amt, String t_year, String drink, String drink_amt, String drug, String drug_type, String drug_amt, String drug_year, String drink_year, String func_note, String historynote, String vaccinenote, String implantsnote) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, userid);
        cv.put(COL_NOTE, note);
        cv.put(COL_EYE_GLASSES, glass);
        cv.put(COL_EYE_LENSE, lense);
        cv.put(COL_TEETH_FALSE, falses);
        cv.put(COL_TEETH_IMPLANTS, implants);
        cv.put(COL_HEARING_AIDES, aid);
        cv.put(COL_BLOODTYPE, blood);
        cv.put(COL_ORGANDONOR, donor);
        cv.put(COL_MOUTH_NOTE, mouthnote);
        cv.put(COL_TEETH_MOUTH, mouth);
        cv.put(COL_SPEECH, speech);
        cv.put(COL_BLIND, blind);
        cv.put(COL_AIDE_NOTE, aidenote);
        cv.put(COL_VISION_NOTE, visionnote);
        cv.put(COL_DIET_NOTE, dietnote);
        cv.put(COL_ALLERGY_NOTE, allergynote);

        cv.put(COL_TOBACO, tobaco);
        cv.put(COL_T_TYPE, t_type);
        cv.put(COL_T_AMT, t_amt);
        cv.put(COL_T_YEAR, t_year);
        cv.put(COL_DRINK, drink);
        cv.put(COL_DRINK_AMT, drink_amt);
        cv.put(COL_DRUG, drug);
        cv.put(COL_DRUG_TYPE, drug_type);
        cv.put(COL_DRINK_YEAR, drink_year);
        cv.put(COL_DRUG_AMT, drug_amt);
        cv.put(COL_DRUG_YEAR, drug_year);

        cv.put(COL_FunctionNote, "");
        cv.put(COL_Mouth, "");
        cv.put(COL_HISTORY_NOTE, historynote);
        cv.put(COL_VACCINE_NOTE, vaccinenote);
        cv.put(COL_IMPLANTS_NOTE,implantsnote);

        Cursor c = MedInfoQuery.fetchOneRecordCursor(userid);
        if (c.moveToFirst()) {
            int rowid = db.update(TABLE_NAME, cv,null , null);
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

        //Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        return c;
    }

    public static MedInfo fetchOneRecord(int userid) {
        MedInfo medInfo = new MedInfo();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);

        //  Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);

        if (c.moveToFirst()) {
            do {
                medInfo.setId(c.getInt(c.getColumnIndex(COL_ID)));
                medInfo.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                medInfo.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                medInfo.setGlass(c.getString(c.getColumnIndex(COL_EYE_GLASSES)));
                medInfo.setLense(c.getString(c.getColumnIndex(COL_EYE_LENSE)));
                medInfo.setFalses(c.getString(c.getColumnIndex(COL_TEETH_FALSE)));
                medInfo.setImplants(c.getString(c.getColumnIndex(COL_TEETH_IMPLANTS)));
                medInfo.setAid(c.getString(c.getColumnIndex(COL_HEARING_AIDES)));
                medInfo.setBloodType(c.getString(c.getColumnIndex(COL_BLOODTYPE)));
                medInfo.setDonor(c.getString(c.getColumnIndex(COL_ORGANDONOR)));
                medInfo.setMouth(c.getString(c.getColumnIndex(COL_TEETH_MOUTH)));
                medInfo.setMouthnote(c.getString(c.getColumnIndex(COL_MOUTH_NOTE)));
                medInfo.setSpeech(c.getString(c.getColumnIndex(COL_SPEECH)));
                medInfo.setBlind(c.getString(c.getColumnIndex(COL_BLIND)));
                medInfo.setAideNote(c.getString(c.getColumnIndex(COL_AIDE_NOTE)));
                medInfo.setVisionNote(c.getString(c.getColumnIndex(COL_VISION_NOTE)));
                medInfo.setDietNote(c.getString(c.getColumnIndex(COL_DIET_NOTE)));
                medInfo.setAllergyNote(c.getString(c.getColumnIndex(COL_ALLERGY_NOTE)));

                medInfo.setTobaco(c.getString(c.getColumnIndex(COL_TOBACO)));
                medInfo.setT_type(c.getString(c.getColumnIndex(COL_T_TYPE)));
                medInfo.setT_amt(c.getString(c.getColumnIndex(COL_T_AMT)));
                medInfo.setT_year(c.getString(c.getColumnIndex(COL_T_YEAR)));
                medInfo.setDrink(c.getString(c.getColumnIndex(COL_DRINK)));
                medInfo.setDrink_amt(c.getString(c.getColumnIndex(COL_DRINK_AMT)));
                medInfo.setDrug(c.getString(c.getColumnIndex(COL_DRUG)));
                medInfo.setDrug_type(c.getString(c.getColumnIndex(COL_DRUG_TYPE)));

                medInfo.setDrink_year(c.getString(c.getColumnIndex(COL_DRINK_YEAR)));
                medInfo.setDrug_amt(c.getString(c.getColumnIndex(COL_DRUG_AMT)));
                medInfo.setDrug_year(c.getString(c.getColumnIndex(COL_DRUG_YEAR)));

                medInfo.setHistorynote(c.getString(c.getColumnIndex(COL_HISTORY_NOTE)));
                medInfo.setVaccinenote(c.getString(c.getColumnIndex(COL_VACCINE_NOTE)));
                medInfo.setImplantnote(c.getString(c.getColumnIndex(COL_IMPLANTS_NOTE)));

            } while (c.moveToNext());
        }

        return medInfo;
    }
}
