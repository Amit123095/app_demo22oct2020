package com.mindyourlovedone.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedone.healthcare.model.PrescribeImage;
import com.mindyourlovedone.healthcare.utility.PrefConstants;
import com.mindyourlovedone.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */
/**
 * Class: PrescribeImageQuery
 * Screen: Add PrescriptionImage
 * A class that manages PrescriptionImage Table CRUD Operations
 */
public class PrescribeImageQuery {
    public static final String TABLE_NAME = "PrescriptionImage";
    public static final String COL_ID = "Id";
    //ListView lvNote;
    public static final String COL_PREID = "Pre_Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_Image = "Image";
    static Context context;
    static DBHelper dbHelper;

    public PrescribeImageQuery(Context context, DBHelper dbHelper) {
        PrescribeImageQuery.context = context;
        PrescribeImageQuery.dbHelper = dbHelper;
    }

    public static String createImageTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER," + COL_PREID + " INTEGER," + COL_Image + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertImageData(int userid, int preid, String image) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_Image, image);

        long rowid = db.insert(TABLE_NAME, null, cv);

        flag = rowid != -1;

        return flag;
    }

    public static ArrayList<PrescribeImage> fetchAllImageRecord(int userid, int id) {
        ArrayList<PrescribeImage> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';", null);// + " where " + COL_USERID + "='" + userid+"' and "+COL_PREID+"='"+id+"';",null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    PrescribeImage notes = new PrescribeImage();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setImage(c.getString(c.getColumnIndex(COL_Image)));
                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static Boolean insertImageData(int userid, ArrayList<PrescribeImage> imageList, int id) {



//        push check
        

        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < imageList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_USERID, userid);
            cv.put(COL_PREID, id);
            cv.put(COL_Image, imageList.get(i).getImage());

            long rowid = db.insert(TABLE_NAME, null, cv);

            flag = rowid != -1;
        }
        return flag;
    }

    public static void deleteRecord(int id) {//nikita - to delete images from storage
        ArrayList<PrescribeImage> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                PrescribeImage notes = new PrescribeImage();
                notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                notes.setImage(c.getString(c.getColumnIndex(COL_Image)));
                noteList.add(notes);

                db.execSQL("delete from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';");
            } while (c.moveToNext());
        }

        for (int i = 0; i < noteList.size(); i++) {
            File imgFile = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + noteList.get(i).getImage());//nikita
            if (imgFile.exists()) {
                imgFile.delete();
            }
        }
    }

    public static Boolean updateImageData(ArrayList<PrescribeImage> imageList, int unique, ArrayList<PrescribeImage> oldList, ArrayList<PrescribeImage> imageListOld, int userid) {
        boolean flag = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        deleteRecord(unique);

        for (int j = 0; j < imageList.size(); j++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_USERID, userid);
            cv.put(COL_PREID, unique);
            cv.put(COL_Image, imageList.get(j).getImage());

            long rowid = db.insert(TABLE_NAME, null, cv);

            flag = rowid != -1;
        }

//        if (oldList.size() < imageList.size()) {
//            if (oldList.size() == 0) {
//                for (int j = 0; j < imageList.size(); j++) {
//                    ContentValues cv = new ContentValues();
//                    cv.put(COL_USERID, userid);
//                    cv.put(COL_PREID, unique);
//                    cv.put(COL_Image, imageList.get(j).getImage());
//
//                    long rowid = db.insert(TABLE_NAME, null, cv);
//
//                    if (rowid == -1) {
//                        flag = false;
//                    } else {
//                        flag = true;
//                    }
//                }
//            } else {
//                // for(int i=0;i<oldList.size();i++) {
//                for (int j = 0; j < imageList.size(); j++) {
//                    if (imageList.get(j).getPreid() != 0) {
//                        ContentValues cv = new ContentValues();
//                        cv.put(COL_Image, imageList.get(j).getImage());
//                        int rowid = db.update(TABLE_NAME, cv, COL_PREID + "=" + unique + " and " + COL_ID + "=" + imageList.get(j).getId(), null);
//
//                        if (rowid == 0) {
//                            flag = false;
//                        } else {
//                            flag = true;
//                        }
//                    } else {
//                        ContentValues cv = new ContentValues();
//                        cv.put(COL_USERID, userid);
//                        cv.put(COL_PREID, unique);
//                        cv.put(COL_Image, imageList.get(j).getImage());
//
//                        long rowid = db.insert(TABLE_NAME, null, cv);
//
//                        if (rowid == -1) {
//                            flag = false;
//                        } else {
//                            flag = true;
//                        }
//                    }
//                }
//                //  }
//            }
//        } else {
//            for (int i = 0; i < imageList.size(); i++) {
//                ContentValues cv = new ContentValues();
//                cv.put(COL_Image, imageList.get(i).getImage());
//
//                int rowid = db.update(TABLE_NAME, cv, COL_PREID + "=" + unique + " and " + COL_ID + "=" + oldList.get(i).getId(), null);
//
//
//                if (rowid == 0) {
//                    flag = false;
//                } else {
//                    flag = true;
//                }
//            }
//        }
        return flag;
    }

    public static boolean deleteImageRecord(int id) {//nikita - to delete images from storage
        ArrayList<PrescribeImage> noteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                PrescribeImage notes = new PrescribeImage();
                notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                notes.setImage(c.getString(c.getColumnIndex(COL_Image)));
                noteList.add(notes);
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        for (int i = 0; i < noteList.size(); i++) {
            File imgFile = new File(new Preferences(context).getString(PrefConstants.CONNECTED_PATH) + noteList.get(i).getImage());//nikita
            if (imgFile.exists()) {
                imgFile.delete();
            }
        }

        return true;
    }

    public static boolean deleteRecords(int id,int preid) {
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
