package com.coffee.just.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coffee.just.database.CrimeBaseHelper;
import com.coffee.just.database.CrimeCursorWrapper;
import com.coffee.just.database.CrimeDbSchema;
import com.coffee.just.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;


//    private ArrayList<Crime> mCrimes;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeTable.Cols.SOLVED,crime.isSolved()?1:0);
        return values;
    }

    public CrimeLab(Context context) {
//            mCrimes = new ArrayList<>();
            mContext = context;
            mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        for (int i = 0; i <100 ; i++) {
//                Crime crime = new Crime();
//                crime.setTitle("Crime #"+i);
//                crime.setSolved(i%2==0);
//                mCrimes.add(crime);

    }

    public  void reCrime(int i )
    {
//        mCrimes.remove(i);
    }
 public  void reCrime(Crime crime )
    {
//        mCrimes.remove(crime);
        mDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID+" =?",new String []{crime.getId().toString()});
    }
    public void addCrime(Crime crime)
    {
//        mCrimes.add(crime);
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME,null,values);
    }

    public static CrimeLab getCrimeLab(Context context)
    {
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
            return sCrimeLab;
    }

    public void updateCrime(Crime crime)
    {
        String uuidstring = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME,values,CrimeTable.Cols.UUID+" = ?",new String[]{uuidstring});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,null,whereClause,whereArgs,null,null,null,null
        );

        return new CrimeCursorWrapper(cursor);
    }


    public Crime getCrime(UUID id){

//        for (Crime crime : mCrimes)
//        {
//            if(crime.getId().equals(id))
//            {
//                return crime;
//            }
//        }

        CrimeCursorWrapper cursorWrapper = queryCrimes(CrimeTable.Cols.UUID + " =?",new String[] {id.toString()});

        try {
            if(cursorWrapper.getCount() == 0)
            {
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getCrime();
        }finally {
            cursorWrapper.close();
        }

    }
    public ArrayList<Crime> getCrimes() {
       ArrayList<Crime> crimes = new ArrayList<>();

        try (CrimeCursorWrapper cursor = queryCrimes(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
     return crimes;
    }
}
