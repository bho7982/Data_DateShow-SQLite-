package com.example.inthetech.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by inthetech on 2017-01-18.
 */

public class TestDB extends SQLiteOpenHelper {
    public TestDB(Context context){
        super(context, "TestDB2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table member (_id INTEGER PRIMARY KEY AUTOINCREMENT, lux_val char(20), Testdate date(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS member");
        onCreate(db);
    }
}
