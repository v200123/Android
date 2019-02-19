package com.coffee.just.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.coffee.just.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DateFragment extends DialogFragment {
    public final static String EXTRA_CRIME_DATE = "com.coffee.just.crime.date";
    private DatePicker mDatePickerDialog;
    private Button mTimeButton;
    private TimePickerDialog mTimePickerDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =LayoutInflater.from(getActivity()).inflate(R.layout.fragment_crime_date_pick,null);
        Date date = (Date) getArguments().getSerializable(EXTRA_CRIME_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int mouth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Log.e("Time",String.valueOf(calendar.get(Calendar.MINUTE)));
        mTimeButton = view.findViewById(R.id.crime_time_choice);
        mTimeButton.setOnClickListener((v)->{
//            FragmentManager  fm = getFragmentManager();
//            TimeFragment dialog = TimeFragment.newIntent(getActivity(),date);
//            dialog.show(fm,"Crime_time_fragment");



/*通过提供Dialog,来对时间进行选择*/
            mTimePickerDialog =new TimePickerDialog(getActivity(), android.app.AlertDialog.THEME_HOLO_DARK,
                    (TimePicker timePicker,int hour,int minute)->{                             //这里是一个Lambda表达式

                        calendar.set(Calendar.HOUR_OF_DAY,hour);
                        calendar.set(Calendar.MINUTE,minute);
            },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
            mTimePickerDialog.setTitle("选择时间");

            mTimePickerDialog.show();

        });

        mDatePickerDialog=view.findViewById(R.id.dialog_crime_date_picker);
        mDatePickerDialog.init(year,mouth,day,null);
        return new AlertDialog.Builder(getActivity())
                        .setView(view)
                        .setTitle(R.string.date_pick_title)
                        .setPositiveButton("确定",(DialogInterface dialog,int which)->{
                        int pickerYear = mDatePickerDialog.getYear();
                        int pickerMonth = mDatePickerDialog.getMonth();
                        int pickerDay = mDatePickerDialog.getDayOfMonth();
                        Date date1 = new GregorianCalendar(pickerYear,pickerMonth,pickerDay,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)).getTime();
                        sendResult(1,date1);
                        })
                        .create();
    }

    public static DateFragment newIntent(Date date){
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_CRIME_DATE,date);

            DateFragment dateFragment = new DateFragment();
            dateFragment.setArguments(bundle);
            return dateFragment;
    }

    private void sendResult(int resutCode,Date date)
    {
        if (getTargetFragment()==null){
            return;
        }
        Intent i =new Intent();
        i.putExtra(EXTRA_CRIME_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resutCode,i);
    }
}
