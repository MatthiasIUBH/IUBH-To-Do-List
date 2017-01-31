package com.example.m21219.logintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainView extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        TextView message = (TextView) findViewById(R.id.message);
        message.setText("Willkommen " + getIntent().getExtras().getString(MainActivity.KEY)+"!");






    }
}
