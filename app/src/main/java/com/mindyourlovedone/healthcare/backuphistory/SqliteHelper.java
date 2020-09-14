package com.mindyourlovedone.healthcare.backuphistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.mindyourlovedone.healthcare.database.ContactDataQuery;
import com.mindyourlovedone.healthcare.database.ContactTableQuery;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "backuphistory.db";
    static final int DATABASE_VERSION = 1;//old version is less than 3


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBackupHistoryTable = BackupHistoryQuery.createBackupHistoryTable();
        db.execSQL(createBackupHistoryTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BackupHistoryQuery.dropTable());
            onCreate(db);
    }
}
