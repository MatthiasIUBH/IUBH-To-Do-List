package com.example.m21219.logintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TodoCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);
    }

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
}
