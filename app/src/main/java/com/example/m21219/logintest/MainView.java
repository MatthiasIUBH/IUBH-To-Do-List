package com.example.m21219.logintest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainView extends AppCompatActivity {

    SQLiteDatabase myDB=null;
    private ListView TaskListView;
    private ArrayAdapter<String> listAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        TextView message = (TextView) findViewById(R.id.message);
        message.setText("Willkommen " + getIntent().getExtras().getString(MainActivity.UserID)+"!");

        myDB = this.openOrCreateDatabase("IUBHtoDoApp", MODE_PRIVATE, null);
        Cursor cursor = myDB.rawQuery("SELECT id, name, description FROM Tasks where userid='"+getIntent().getExtras().getString(MainActivity.UserID)+"'", null);

        try {
            if(cursor.getCount()<1) //wenn keine Tasks gefunden wurden!
            {
                cursor.close();
                Toast toast = Toast.makeText(getApplicationContext(), "keine Tasks gefunden!", 3);
                toast.show();
            }
            else
            {
                TaskListView = (ListView) findViewById(R.id.ListView_Tasks);
                ArrayList<String> TaskList = new ArrayList<String>();

                //Sqlite Cursor iterieren und in ein Array schreiben
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    TaskList.add(cursor.getString(cursor.getColumnIndex("description")));
                }

                //Array ausgeben in einem Listview
                listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TaskList);
                TaskListView.setAdapter(listAdapter);

            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
    }
}




