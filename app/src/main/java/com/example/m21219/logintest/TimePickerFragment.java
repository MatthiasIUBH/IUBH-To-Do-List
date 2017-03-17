package com.example.m21219.logintest;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by Hakan Akkurt on 25.02.2017.
 */

/*Hier definieren wir unsere Uhranzeige zum Auswählen von gewünschenter Uhrzeit für Todos
wir vererben von der Superklasse "DialogFragment"*/

public class TimePickerFragment extends DialogFragment {
    /* Der Listener wird benutzt, um festzustellen, dass der User eine Uhrzeit ausgewählt hat.
   Bei den Klassen "TodoCreate" und "TodoDetailActivity" wird dieser Listener aufegerufen*/
    private TimePickerDialog.OnTimeSetListener listener;

    //Ein neuer Dialog wird erstellt.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Wir nehmen die Systemzeit als Defaultwert für die Uhranzeige
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Eine neue Instanz von TimePickerDialog erstellen und zurückgeben
        return new TimePickerDialog(getActivity(), this.listener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //"onAttach"Funktion wird aufgerufen, um die Activity mit diesem Dialog zu verbinden.
    @Override
    public void onAttach(final Activity activity) {
        if (activity instanceof TimePickerDialog.OnTimeSetListener) {
            this.listener = (TimePickerDialog.OnTimeSetListener) activity;
        }
        super.onAttach(activity);
    }

}
