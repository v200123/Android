package com.coffee.just.View;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;

import com.coffee.just.R;

import androidx.annotation.Nullable;

public class MyViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myview);
    }
}
