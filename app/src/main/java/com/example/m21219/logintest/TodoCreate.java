package com.example.m21219.logintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TodoCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);
    }

    public void onClickSubmit (View Submit) {

        /*neue ToDo Instanz generieren
        ToDo newToDo = new ToDo();
        */

        /*Textfelder auslesen und als String übergeben
        EditText name = (EditText) findViewById(R.id.name);
        String sName = name.getText().toString();
        newToDo.setName(sName);

        */

        /*EditText description = (EditText) findViewById(R.id.view_description);
        newToDo.setDescription(description.getText().toString());
        */


        //...



        /*Instanz in Datenbank schreiben
        TodoDatabase.getInstance(this).createToDo(newToDo);
        */

        //zurück zur Main Activity
        Intent MainView = new Intent(this, MainView.class);
        startActivity(MainView);

    }
}
