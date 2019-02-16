package com.coffee.just.Controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private ArrayList<Crime> mCrimes;

    public CrimeLab(Context context) {
            mCrimes = new ArrayList<>();
        for (int i = 0; i <100 ; i++) {
                Crime crime = new Crime();
                crime.setTitle("Crime #"+i);
                crime.setSolved(i%2==0);
                mCrimes.add(crime);
        }
    }

    public static CrimeLab getCrimeLab(Context context)
    {
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
            return sCrimeLab;
    }
    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes)
        {
            if(crime.getId().equals(id))
            {
                return crime;
            }
        }
        return null;
    }
    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }
}
