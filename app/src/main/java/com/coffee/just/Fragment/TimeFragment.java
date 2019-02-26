package com.coffee.just.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.baidu.mapapi.SDKInitializer;
import com.coffee.just.R;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TimeFragment extends DialogFragment {
    private final static String ARG_TIME= "crime_fragment_time";

    private TimePickerDialog  mTimePickerDialog;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        View  v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_crime_time_picker,null);
        return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.time_picker_title)
                    .setPositiveButton("确认",null)
                    .create();
    }


    public static TimeFragment newIntent(Context context, Date date)
    {
        Bundle arg = new Bundle();
        arg.putSerializable(ARG_TIME,date);

        TimeFragment timeFragment = new TimeFragment();
        timeFragment.setArguments(arg);
        return timeFragment;
    }
}
