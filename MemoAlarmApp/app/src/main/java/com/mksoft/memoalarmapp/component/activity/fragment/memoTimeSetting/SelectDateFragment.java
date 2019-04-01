package com.mksoft.memoalarmapp.component.activity.fragment.memoTimeSetting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SelectDateFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    DatePickerDialog datePickerDialog;
    final Calendar calendar =  Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH)+1;

        datePickerDialog = new DatePickerDialog(getContext(), this, yy, mm, dd);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
//        calendar.add(Calendar.DAY_OF_MONTH, 6);
        calendar.add(Calendar.MONTH, 6);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH).format(calendar.getTime());

        getTargetFragment().onActivityResult(
                getTargetRequestCode(), Activity.RESULT_OK, new Intent().putExtra("selectedDate", selectedDate)
        );
    }
}