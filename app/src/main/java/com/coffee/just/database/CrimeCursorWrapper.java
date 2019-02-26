package com.coffee.just.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.coffee.just.Controller.Crime;
import com.coffee.just.database.CrimeDbSchema.CrimeTable.Cols;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        long date = getLong(getColumnIndex(Cols.DATE));
        int solved = getInt(getColumnIndex(Cols.SOLVED));


        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(solved != 0);
        return crime;
}
}
