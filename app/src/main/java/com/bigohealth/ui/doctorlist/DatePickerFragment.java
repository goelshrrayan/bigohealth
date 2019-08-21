package com.bigohealth.ui.doctorlist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog((Activity) getContext(),(DatePickerDialog.OnDateSetListener) getTargetFragment(),year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        Log.i("In Date Picker"," Fragment"+getContext());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+604800000);
        return datePickerDialog;

    }


//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        c.set(Calendar.MONTH, month);
//        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
//        Log.i("current date",currentDate+"yo");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Toast.makeText(getContext(), currentDate+"hey", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}
