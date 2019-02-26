package com.coffee.just.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coffee.just.database.CrimeDbSchema.CrimeTable;

import androidx.annotation.Nullable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "crimeBase";
    private static final int VERSION = 1;

    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create TABLE "+ CrimeTable.NAME
        +"("+"_id Integer primary key autoincrement, "+
              CrimeTable.Cols.UUID +", "+
                CrimeTable.Cols.TITLE+", "+
                CrimeTable.Cols.DATE+", "+
                CrimeTable.Cols.SOLVED+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
