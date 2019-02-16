package com.coffee.just.Controller.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.coffee.just.Fragment.CrimeFragment;
import com.coffee.just.Fragment.singleFragmentActivity;

import java.util.UUID;

public class CrimeActivity extends singleFragmentActivity {
   private static final String EXTRA_CRIME_ID = "com.coffee.just.crime_id";

    @Override
    protected Fragment createFragment() {
        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newIntent(id);
    }

    public static Intent newIntent(Context context, UUID crimeId)
    {
        Intent i = new Intent(context,CrimeActivity.class);
        i.putExtra(EXTRA_CRIME_ID,crimeId);
        return i;
    }
}

