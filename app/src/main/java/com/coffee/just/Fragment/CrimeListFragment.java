package com.coffee.just.Fragment;

/**
 *
 * 这个是对Recycle列表进行视图渲染
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.coffee.just.Controller.Crime;
import com.coffee.just.Controller.CrimeListAdapter;
import com.coffee.just.Controller.CrimeLab;
import com.coffee.just.Controller.activity.CrimePageActivity;
import com.coffee.just.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class CrimeListFragment extends Fragment {
        private RecyclerView mCrimeRecycleView;
    private CrimeListAdapter mCrimeListAdapter;
    private Button mAddCrime;
    private TextView mCrimePoint;
    private String[] premissions = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION};
    private ArrayList<String> mPremissions = new ArrayList<>();
    private List<Crime> crimes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecycleView = view.findViewById(R.id.crime_recycle_view);
        mCrimeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddCrime = view.findViewById(R.id.fragment_crime_addCrime);
        mCrimePoint = view.findViewById(R.id.fragment_crime_show_Point);
        updateUI();
        SDKInitializer.initialize(getContext().getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        initPremissions();
        if(crimes.size()==0)
        {
            mAddCrime.setVisibility(View.VISIBLE);
            mCrimePoint.setVisibility(View.VISIBLE);
        }
        mAddCrime.setOnClickListener((v)->{
            Crime crime = new Crime();
            CrimeLab crimeLab =CrimeLab.getCrimeLab(getContext());
            crimeLab.addCrime(crime);
            Intent i = CrimePageActivity.newIntent(getContext(),crime.getId());
            startActivity(i);
        });
        return  view;
    }
    private void initPremissions(){
        mPremissions.clear();//清空权限列表

        for (String premission : premissions)
        {
            if (ContextCompat.checkSelfPermission(getContext(),premission)!= PackageManager.PERMISSION_GRANTED)
            {
                mPremissions.add(premission);
            }
        }

        if(mPremissions.size()>0)
            ActivityCompat.requestPermissions(this.getActivity(),premissions,1);
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        if (crimes.size() != 0)
        {
            mAddCrime.setVisibility(View.INVISIBLE);
            mCrimePoint.setVisibility(View.INVISIBLE);
        }
        }


    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
         crimes = crimeLab.getCrimes();
        if(mCrimeListAdapter ==null){
        mCrimeListAdapter = new CrimeListAdapter(crimes);
        mCrimeRecycleView.setAdapter(mCrimeListAdapter);}
        else {
            mCrimeListAdapter.serCrime(crimes);
            mCrimeListAdapter.notifyItemChanged(CrimeListAdapter.mPosition);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case  R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getCrimeLab(getContext()).addCrime(crime);
                Intent i = CrimePageActivity.newIntent(getContext(),crime.getId());
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
