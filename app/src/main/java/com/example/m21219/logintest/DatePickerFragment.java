package com.example.m21219.logintest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Hakan Akkurt on 06.02.2017.
 */

public class DatePickerFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this.listener, year, month, day);
    }

    @Override
    public void onAttach(final Activity activity) {
        if (activity instanceof DatePickerDialog.OnDateSetListener) {
            this.listener = (DatePickerDialog.OnDateSetListener) activity;
        }
        super.onAttach(activity);
    }
}
