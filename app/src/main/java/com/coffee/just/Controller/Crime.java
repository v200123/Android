package com.coffee.just.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Crime {

    private UUID mId;


    public Date getDate() {
       return mDate;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//        return simpleDateFormat.format(mDate) ;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    private Date mDate;
    private boolean mSolved;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate =new Date();
    }

    @Override
    public String toString() {
        return "Crime{" +
                "id=" + mId +
                ", mDate=" + mDate +
                ", mSolved=" + mSolved +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }

    private String  mTitle;

    public UUID getId() {
        return mId;
    }

//    public void setId(UUID id) {
//        this.id = id;
//    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
