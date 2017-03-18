package com.example.m21219.logintest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Locale;


/*Diese Klasse bzw. Activity wird aufgerufen, wenn der User einen neuen Eintrag eingeben und anschließend speichern möchte.
 Wir implentieren die Schnitstellen "TextWatcher", da wir Text eingeben und bearbeiten, "DatePickerDialog.OnDateSetListener" und
 "TimePickerDialog.OnTimeSetListener, damit werden die Dialogfenster mit Kalender und Uhr aufgerufen.*/

public class TodoCreate extends AppCompatActivity implements TextWatcher, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private ToDo todo;
    private EditText name;
    private EditText description;
    private TextView completiondate;
    private TextView completiontime;
    private CheckBox favorite;
    private CheckBox completionstatus;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);

        this.todo = new ToDo();

        this.name = (EditText) findViewById(R.id.name);
        this.description = (EditText) findViewById(R.id.description);
        this.favorite = (CheckBox) findViewById(R.id.favorite);
        this.completionstatus = (CheckBox) findViewById(R.id.completionstatus);
        this.submit = (Button) findViewById(R.id.submit);

        this.completiondate = (TextView) findViewById(R.id.completiondate);
        this.completiontime = (TextView) findViewById(R.id.completiontime);

        //OnClickListener für jeweils Kalender- und Uhranzeige.
        this.completiondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        this.completiontime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        //Listener für Texteingabe
        this.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                todo.setName(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                todo.setDescription(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                todo.setFavorite(b);
            }
        });
        this.completionstatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                todo.setCompletionstatus(b);
            }
        });

        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (todo.getName() == null) { //Die Prüfung, ob der Name des Eintrags "null" ist oder nicht.
                    Toast.makeText(TodoCreate.this, "Fehler beim Speichern, bitte noch einen Namen eingeben.", Toast.LENGTH_SHORT).show();
                    return;
                }

                TodoDatabase.getInstance(TodoCreate.this).createToDo(todo);
                finish();
            }
        });

    }

    @Override
    public void onDateSet(final DatePicker datePicker, final int i, final int i1, final int i2) {
        this.completiondate.setText(String.format(Locale.GERMANY, "%02d.%02d.%d", i2, i1 + 1, i));  //Datumsformat festlegen

        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);

        todo.setCompletiondate(c);//Das Systemdatum wird als Defaultwert genommen
    }

    @Override
    public void onTimeSet(final TimePicker timePicker, final int hourOfDay, final int minute){
        this.completiontime.setText(String.format(Locale.GERMANY, "%02d:%02d", hourOfDay, minute)); //Zeitformat festlegen.

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        todo.setCompletiontime(c);//Die Systemzeit wird als Defaultwert genommen.
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
