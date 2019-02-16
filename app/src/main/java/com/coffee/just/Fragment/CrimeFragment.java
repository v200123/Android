package com.coffee.just.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.coffee.just.Controller.Crime;
import com.coffee.just.Controller.CrimeLab;
import com.coffee.just.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CrimeFragment extends Fragment {
    private final static String ARG_CRIME_ID = "com.coffee.just.crimefragment.crime_id";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolved;

    /**
     * 输入犯罪界面得详细信息
     * @param savedInstanceState
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getCrimeLab(getContext()).getCrime(crimeId); //这里获取到了点击后传入的ID，然后从Lab里查询到指定的Crime对象
    }

    public static CrimeFragment newIntent(UUID crimeId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return  crimeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_crime,container,false);
        mTitleField = v. findViewById(R.id.crime_title);
        Log.d("mCrime",mCrime.toString());
        mTitleField.setText(mCrime.getTitle());
        mDateButton=v.findViewById(R.id.DateButton);
        mSolved = v.findViewById(R.id.checked);
        mSolved.setChecked(mCrime.isSolved());
        updateDate();
        mDateButton.setOnClickListener((View view)->{
            FragmentManager fm =getFragmentManager();
            DateFragment dateFragment = DateFragment.newIntent(mCrime.getDate());
            dateFragment.setTargetFragment(CrimeFragment.this,1);
            dateFragment.show(fm,"选择日期");
        });
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSolved.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked)->{mCrime.setSolved(isChecked);
           });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1)
        return;

        if(requestCode == 1)
        {Date date = (Date) data.getSerializableExtra(DateFragment.EXTRA_CRIME_DATE);
        mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(mCrime.getDate()));
    }
}
