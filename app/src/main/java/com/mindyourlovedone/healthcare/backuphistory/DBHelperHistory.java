package com.mindyourlovedone.healthcare.backuphistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperHistory extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDB.db";


    public DBHelperHistory(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creating Student Table
       String query=BackupHistoryQuery.createBackupHistoryTable();
       sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropquery=BackupHistoryQuery.dropTable();
       sqLiteDatabase.execSQL(dropquery);
       onCreate(sqLiteDatabase);
    }
}
