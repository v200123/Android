package com.coffee.just.Controller.activity;

import com.coffee.just.Fragment.CrimeListFragment;
import com.coffee.just.Fragment.singleFragmentActivity;

import androidx.fragment.app.Fragment;

public class CrimeListActivity extends singleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
