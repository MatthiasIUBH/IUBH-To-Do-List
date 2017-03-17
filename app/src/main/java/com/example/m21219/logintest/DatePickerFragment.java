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

/*Hier definieren wir unsere Kalenderansicht zum Auswählen von gewünschentem Datum für Todos
wir vererben von der Superklasse "DialogFragment"*/

public class DatePickerFragment extends DialogFragment {
    /* Der Listener wird benutzt, um festzustellen, dass der User ein Datum ausgewählt hat.
    Bei den Klassen "TodoCreate" und "TodoDetailActivity" wird dieser Listener aufegerufen*/
    private DatePickerDialog.OnDateSetListener listener;

    //Ein neuer Dialog wird erstellt.
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Wir nehmen das Systemdatum als Defaultwert für den Kalender
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //Eine neue Instanz von DatePickerDialog erstellen und zurückgeben
        return new DatePickerDialog(getActivity(), this.listener, year, month, day);
    }

    //"onAttach"Funktion wird aufgerufen, um die Activity mit diesem Dialog zu verbinden.
    @Override
    public void onAttach(final Activity activity) {
        if (activity instanceof DatePickerDialog.OnDateSetListener) {
            this.listener = (DatePickerDialog.OnDateSetListener) activity;
        }
        super.onAttach(activity);
    }
}
