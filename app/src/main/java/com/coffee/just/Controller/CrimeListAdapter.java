package com.coffee.just.Controller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.coffee.just.Controller.activity.CrimePageActivity;
import com.coffee.just.Fragment.CrimeListFragment;
import com.coffee.just.R;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * Recycle的适配器
 */
public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.ViewHolder> {
    private List<Crime> mCrimes;
    public static int mPosition;


    public CrimeListAdapter(List<Crime> crimes) {
                mCrimes = crimes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.list_item_crime,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Crime crime = mCrimes.get(position);

        holder.bindCrime(crime);
    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }

    public void serCrime(List<Crime> crimes){
        mCrimes =crimes;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;
        private TextView mTitleTextView;
        private CheckBox mCheckBoxView;
        private TextView mDateTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.Crime_Title_list);
            mCheckBoxView = itemView.findViewById(R.id.Crime_Checked_list);
            mDateTextView = itemView.findViewById(R.id.Crime_Date_list);
            itemView.setOnClickListener(this);
        }
        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分" ).format(mCrime.getDate()));
            mCheckBoxView.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
                Intent i = CrimePageActivity.newIntent(v.getContext(),mCrime.getId());
                mPosition = getAdapterPosition();
                v.getContext().startActivity(i);
        }


    }


}
