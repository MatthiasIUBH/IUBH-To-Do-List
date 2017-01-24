package com.example.m21219.logintest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDB=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //SQLiteDatabase myDB= null;

        try {
            myDB = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS Users (name VARCHAR, password VARCHAR)");
            myDB.execSQL("INSERT INTO Users (name, password) VALUES ('matthias', 'test1')");
            myDB.execSQL("INSERT INTO Users (name, password) VALUES ('hakan', 'test2')");
        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
        }

        final Button button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText editTextUserName =  (EditText) findViewById(R.id.txt_username);
                final EditText editTextPassword =  (EditText) findViewById(R.id.txt_password);

                // get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                Cursor cursor = myDB.rawQuery("SELECT password FROM Users where name='"+userName.trim()+"'", null);

                try {
                    if(cursor.getCount()<1) // UserName Not Exist
                    {
                        cursor.close();
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid Username", 3);
                        toast.show();
                    }

                    cursor.moveToFirst();
                    String strPassword= cursor.getString(cursor.getColumnIndex("password"));
                    cursor.close();

                    // check if the Stored password matches with  Password entered by user
                    if(password.equals(strPassword))
                    {
                        Toast.makeText(getApplicationContext(), "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(MainActivity.this, MainView.class);
                        //myIntent.putExtra("key", value); //Optional parameters
                        MainActivity.this.startActivity(myIntent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "User Name or Password does not match", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // exception handling
                } finally {
                    if(cursor != null){
                        cursor.close();
                    }
                }


            }
        });



    }
}
