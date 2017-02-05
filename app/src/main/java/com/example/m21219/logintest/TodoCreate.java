package com.example.m21219.logintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TodoCreate extends AppCompatActivity implements TextWatcher{
    public static final String TODO_ID_KEY = "TODO";

    private ToDo todo;

    private EditText name;
    private EditText description;
    private EditText completiondate;
    private CheckBox favorite;
    private CheckBox completionstatus;

    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);

        long id = getIntent().getLongExtra(TODO_ID_KEY, 0);
        this.todo = TodoDatabase.getInstance(this).readToDo(id);

        this.todo = new ToDo();

        this.name = (EditText) findViewById(R.id.name);
        this.description = (EditText) findViewById(R.id.description);
        this.completiondate = (EditText) findViewById(R.id.completiondate);
        this.favorite = (CheckBox) findViewById(R.id.favorite);
        this.completionstatus = (CheckBox) findViewById(R.id.completionstatus);
        this.submit = (Button) findViewById(R.id.submit);



    /*

    public void onClickSubmit (View Submit) {

        //neue ToDo Instanz generieren
        ToDo newToDo = new ToDo();


        //Textfelder auslesen und als String übergeben
        EditText name = (EditText) findViewById(R.id.name);
        String sName = name.getText().toString();
        newToDo.setName(sName);

        EditText description = (EditText) findViewById(R.id.description);
        String sDescription = description.getText().toString();
        newToDo.setDescription(sDescription);

        //...



        //Instanz in Datenbank schreiben
        TodoDatabase.getInstance(this).createToDo(newToDo);



        //UserID auslesen
        String UserID = getIntent().getExtras().getString("UserID");

        //zurück zur Main Activity
        Intent MainView = new Intent(this, MainView.class);
        MainView.putExtra("UserID", UserID);
        startActivity(MainView);

    }

    */
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
                if (todo.getName() == null) {
                    Toast.makeText(TodoCreate.this, "Fehler beim Speichern, bitte noch einen Namen eingeben.", Toast.LENGTH_SHORT).show();
                    return;
                }

                TodoDatabase.getInstance(TodoCreate.this).createToDo(todo);
                finish();
            }
        });

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
