package com.example.m21219.logintest;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class ToDoDetailActivity extends AppCompatActivity implements TextWatcher, DatePickerDialog.OnDateSetListener {

    public static final String TODO_ID_KEY = "TODO";

    private EditText name;
    private TextView completiondate;
    private EditText description;
    private CheckBox favorite;
    private CheckBox completionstatus;
    private Button update_button;

    private ToDo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);

        long id = getIntent().getLongExtra(TODO_ID_KEY, 0);
        this.todo = TodoDatabase.getInstance(this).readToDo(id);

        update_button = (Button) findViewById(R.id.update_button);

        name = (EditText) findViewById(R.id.name);
        completiondate = (TextView) findViewById(R.id.completiondate);
        description = (EditText) findViewById(R.id.description);
        favorite = (CheckBox) findViewById(R.id.favorite);
        completionstatus = (CheckBox) findViewById(R.id.completionstatus);

        name.setText(todo.getName());
        completiondate.setText(todo.getCompletiondate() == null ? "-" : getDateInString(todo.getCompletiondate()));
        description.setText(todo.getDescription() == null ? "-" : todo.getDescription());
        favorite.setChecked(todo.isFavorite());
        completionstatus.setChecked(todo.isCompletionstatus());

        this.completiondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });


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

        this.update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (todo.getName() == null) {
                    Toast.makeText(ToDoDetailActivity.this, "Fehler beim Speichern, bitte noch einen Namen eingeben.", Toast.LENGTH_SHORT).show();
                    return;
                }

                TodoDatabase.getInstance(ToDoDetailActivity.this).updateToDo(todo);
                finish();
            }
        });

    }

    @Override
    public void onDateSet(final DatePicker datePicker, final int i, final int i1, final int i2) {
        this.completiondate.setText(String.format(Locale.GERMANY, "%02d.%02d.%d", i2, i1 + 1, i));

        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);

        todo.setCompletiondate(c);
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


    private String getDateInString(Calendar calendar) {
        return String.format(Locale.GERMANY, "%02d.%02d.%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        //return calendar.get(Calendar.DAY_OF_MONTH) + ". " + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }


}
