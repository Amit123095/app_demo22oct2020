package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.Insurance;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 10/7/2017.
 */
/**
 * Class: InsuranceQuery
 * Screen: Add Insurance
 * A class that manages Insurance Table CRUD Operations
 */
public class InsuranceQuery {
    public static final String TABLE_NAME = "Insurance";
    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "InsuranceType";
    public static final String COL_OTHER_TYPE = "OtherInsuranceType";
    public static final String COL_OFFICE_PHONE = "OfficePhone";
    public static final String COL_FAX = "Faxno";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_NOTE = "Note";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_MEMBERID = "MemberId";
    public static final String COL_GROUP = "GroupId";
    public static final String COL_SUBSCRIBER = "Subscriber";
    public static final String COL_EMAIL = "Email";
    public static final String COL_AGENT = "Agent";
    public static final String COL_AGENTEMAIl = "AgentEmail";
    public static final String COL_AGENTPONE= "AgentNumber";
    public static final String COL_PHOTOCARD = "PhotoCard";
    public static final String COL_HASCARD= "Has_Card";
    static Context context;
    static DBHelper dbHelper;


    public InsuranceQuery(Context context, DBHelper dbHelper) {
        InsuranceQuery.context = context;
        InsuranceQuery.dbHelper = dbHelper;
    }


    public static String createInsuranceTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(100)," + COL_WEBSITE + " VARCHAR(50),"
                + COL_TYPE + " VARCHAR(70)," + COL_AGENT + " TEXT," + COL_OTHER_TYPE + " VARCHAR(70)," + COL_OFFICE_PHONE + " VARCHAR(20)," + COL_FAX +
                " VARCHAR(20)," + COL_NOTE + " TEXT," + COL_MEMBERID + " VARCHAR(50)," +COL_HASCARD + " VARCHAR(10),"+
                COL_SUBSCRIBER + " VARCHAR(50)," + COL_GROUP + " VARCHAR(50)," + COL_EMAIL + " VARCHAR(50)," +COL_AGENTEMAIl + " VARCHAR(50)," + COL_AGENTPONE + " VARCHAR(20)," +
                COL_PHOTOCARD + " VARCHAR(50)," +
                COL_PHOTO + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static ArrayList<Insurance> fetchAllInsuranceRecord(int userid) {
        ArrayList<Insurance> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        //   Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Insurance notes = new Insurance();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setPhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                    notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                    notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    notes.setMember(c.getString(c.getColumnIndex(COL_MEMBERID)));
                    notes.setGroup(c.getString(c.getColumnIndex(COL_GROUP)));
                    notes.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setSubscriber(c.getString(c.getColumnIndex(COL_SUBSCRIBER)));
                    notes.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    notes.setOtherInsurance(c.getString(c.getColumnIndex(COL_OTHER_TYPE)));
                    notes.setAgent(c.getString(c.getColumnIndex(COL_AGENT)));
                    notes.setAgent_email(c.getString(c.getColumnIndex(COL_AGENTEMAIl)));
                    notes.setAgentPhone(c.getString(c.getColumnIndex(COL_AGENTPONE)));
                    notes.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                    notes.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }
        return noteList;
    }

    public static Boolean insertInsuranceData(int userid, String name, String website, String type, String phone, String photo, String fax, String note, String member, String group, String subscriber, String email, String otherInsurance, String agent, String photoCard, String aentEmail, String aentPhone, String has_card) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, userid);
        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_GROUP, group);
        cv.put(COL_MEMBERID, member);
        cv.put(COL_EMAIL, email);
        cv.put(COL_TYPE, type);
        cv.put(COL_OFFICE_PHONE, phone);
        cv.put(COL_NOTE, note);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_SUBSCRIBER, subscriber);
        cv.put(COL_OTHER_TYPE, otherInsurance);
        cv.put(COL_AGENT, agent);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_AGENTEMAIl,aentEmail);
        cv.put(COL_AGENTPONE,aentPhone);
        cv.put(COL_HASCARD, has_card);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != 0;

        return flag;
    }

    public static boolean deleteRecord(int id) {//nikita - to delete images from storage
        ArrayList<Insurance> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                Insurance notes = new Insurance();
                notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                notes.setPhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                notes.setMember(c.getString(c.getColumnIndex(COL_MEMBERID)));
                notes.setGroup(c.getString(c.getColumnIndex(COL_GROUP)));
                notes.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                notes.setSubscriber(c.getString(c.getColumnIndex(COL_SUBSCRIBER)));
                notes.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                notes.setOtherInsurance(c.getString(c.getColumnIndex(COL_OTHER_TYPE)));
                notes.setAgent(c.getString(c.getColumnIndex(COL_AGENT)));
                notes.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                notes.setAgent_email(c.getString(c.getColumnIndex(COL_AGENTEMAIl)));
                notes.setAgentPhone(c.getString(c.getColumnIndex(COL_AGENTPONE)));
                notes.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));

                noteList.add(notes);

                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        for (int i = 0; i < noteList.size(); i++) {
            File imgFile = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + noteList.get(i).getPhoto());//nikita
            if (imgFile.exists()) {
                imgFile.delete();
            }
            File imgFilecard = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + noteList.get(i).getPhotoCard());//nikita
            if (imgFilecard.exists()) {
                imgFilecard.delete();
            }
        }


        return true;
    }

    public static Boolean updateInsuranceData(int id, String name, String website, String type, String phone, String photo, String fax, String note, String member, String group, String subscriber, String email, String otherInsurance, String agent, String photoCard, String aentEmail, String aentPhone, String has_card) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, name);
        cv.put(COL_WEBSITE, website);
        cv.put(COL_GROUP, group);
        cv.put(COL_MEMBERID, member);
        cv.put(COL_EMAIL, email);
        cv.put(COL_TYPE, type);
        cv.put(COL_OFFICE_PHONE, phone);
        cv.put(COL_NOTE, note);
        cv.put(COL_PHOTO, photo);
        cv.put(COL_FAX, fax);
        cv.put(COL_SUBSCRIBER, subscriber);
        cv.put(COL_OTHER_TYPE, otherInsurance);
        cv.put(COL_AGENT, agent);
        cv.put(COL_PHOTOCARD, photoCard);
        cv.put(COL_AGENTEMAIl,aentEmail);
        cv.put(COL_AGENTPONE,aentPhone);
        cv.put(COL_HASCARD, has_card);
        int rowid = db.update(TABLE_NAME, cv, COL_ID + "=" + id, null);

        flag = rowid != 0;

        return flag;
    }

    public static Insurance getLastInsurance() {
        ArrayList<Insurance> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Insurance notes = new Insurance();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        //   Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {

                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setPhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                    notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                    notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    notes.setMember(c.getString(c.getColumnIndex(COL_MEMBERID)));
                    notes.setGroup(c.getString(c.getColumnIndex(COL_GROUP)));
                    notes.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setSubscriber(c.getString(c.getColumnIndex(COL_SUBSCRIBER)));
                    notes.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    notes.setOtherInsurance(c.getString(c.getColumnIndex(COL_OTHER_TYPE)));
                    notes.setAgent(c.getString(c.getColumnIndex(COL_AGENT)));
                    notes.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                    notes.setAgent_email(c.getString(c.getColumnIndex(COL_AGENTEMAIl)));
                    notes.setAgentPhone(c.getString(c.getColumnIndex(COL_AGENTPONE)));
                    notes.setHas_card(c.getString(c.getColumnIndex(COL_HASCARD)));


                   // noteList.add(notes);
                } while (c.moveToNext());
            }
        }
        return notes;
    }
}
