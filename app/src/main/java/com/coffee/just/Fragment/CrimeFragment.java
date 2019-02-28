package com.coffee.just.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.coffee.just.Controller.Crime;
import com.coffee.just.Controller.CrimeLab;
import com.coffee.just.Controller.activity.CrimeMapActivity;
import com.coffee.just.Controller.activity.CrimePageActivity;
import com.coffee.just.R;
import com.coffee.just.View.ClockView;
import com.coffee.just.View.MyViewActivity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CrimeFragment extends Fragment {
    private final static String ARG_CRIME_ID = "com.coffee.just.crimefragment.crime_id";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolved;
    private Button mJumpMap;
    private Button Jump;
//    private MapView mMapView;
//    private LocationClient  locationClient;
//    private LocationClientOption  option;
////
//    private BaiduMap mBaiduMap;

    /**
     * 输入犯罪界面得详细信息
     * @param savedInstanceState
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getCrimeLab(getContext()).getCrime(crimeId); //这里获取到了点击后传入的ID，然后从Lab里查询到指定的Crime对象



        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
//        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mMapView.onPause();
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        if (mCrime.getTitle()==null){
            Toast.makeText(getActivity(),"没有输入犯罪信息",Toast.LENGTH_SHORT).show();
            crimeLab.reCrime(mCrime);
        }
        else {
        crimeLab.updateCrime(mCrime);
        }
    }

    @Override
    public void onDestroy() {
//        locationClient.stop();
//        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
//        mMapView=null;
        super.onDestroy();
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
//        Log.d("mCrime",mCrime.toString());
        mTitleField.setText(mCrime.getTitle());
        Jump = v.findViewById(R.id.jump);
        Jump.setOnClickListener((view)->{
            Intent i = new Intent(getActivity(), MyViewActivity.class);
            startActivity(i);
        });
        mDateButton=v.findViewById(R.id.DateButton);
        mSolved = v.findViewById(R.id.checked);
        mSolved.setChecked(mCrime.isSolved());
        mJumpMap = v.findViewById(R.id.MapButton);
        mJumpMap.setOnClickListener((view)->{
            Intent i = new Intent(getContext(),CrimeMapActivity.class);
            startActivity(i);
        });
//
//        mMapView = v.findViewById(R.id.baidu_Map);
//        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setMyLocationEnabled(true);
//        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,null);
//        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);


//        //获取定位信息
//        locationClient  = new LocationClient(getActivity());
//        option= new LocationClientOption();
//        option.setOpenGps(true);
//        option.setCoorType("bd09ll");
//        option.setScanSpan(1000);
//        locationClient.setLocOption(option);
//
//        MyLocationListener myLocationListener = new MyLocationListener();
//        locationClient.registerLocationListener(myLocationListener);
//        locationClient.start();
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
        {
            Date date = (Date) data.getSerializableExtra(DateFragment.EXTRA_CRIME_DATE);
        mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(new SimpleDateFormat("yyyy年MM月dd日  HH时mm分").format(mCrime.getDate()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_delect,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.fragment_crime_delect:
                CrimeLab.getCrimeLab(getContext()).reCrime(mCrime);
//                Log.e("delect", "onOptionsItemSelected: "+"删除当前页面成功" );
                getActivity().finish();
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }
    }
}
