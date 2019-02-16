package com.coffee.just.Controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.coffee.just.Controller.Crime;
import com.coffee.just.Controller.CrimeLab;
import com.coffee.just.Fragment.CrimeFragment;
import com.coffee.just.R;

import java.util.List;
import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CrimePageActivity extends FragmentActivity {
    public final static String EXTRA_CRIME_ID = "com.just.coffee.page.crime_ID";
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_crime);
        mCrimes = CrimeLab.getCrimeLab(this).getCrimes();
        ViewPager viewPager = findViewById(R.id.activity_crime_view_page);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);



        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return CrimeFragment.newIntent(mCrimes.get(position).getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        for (int i = 0; i < mCrimes.size() ; i++) {

            if(mCrimes.get(i).getId().equals(crimeId))
            {
                viewPager.setCurrentItem(i);
                Log.d("setCurrentltem", "onCreate:  "+String.valueOf(i));
                break;
            }
        }
    }

    public static Intent newIntent(Context context, UUID crimeId)
    {
        Intent i = new Intent(context, CrimePageActivity.class);
        i.putExtra(EXTRA_CRIME_ID,crimeId);
        return i;
    }


}
