package com.coffee.just.Fragment;

/**
 *
 * 这个是对Recycle列表的单个列表进行视图渲染
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coffee.just.Controller.Crime;
import com.coffee.just.Controller.CrimeListAdapter;
import com.coffee.just.Controller.CrimeLab;
import com.coffee.just.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListFragment extends Fragment {
        private RecyclerView mCrimeRecycleView;
    private CrimeListAdapter mCrimeListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecycleView = view.findViewById(R.id.crime_recycle_view);
        mCrimeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mCrimeListAdapter ==null){
        mCrimeListAdapter = new CrimeListAdapter(crimes);
        mCrimeRecycleView.setAdapter(mCrimeListAdapter);}
        else {

            mCrimeListAdapter.notifyItemChanged(CrimeListAdapter.mPosition);
        }
    }


}
