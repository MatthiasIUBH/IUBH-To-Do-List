package com.example.m21219.logintest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.example.m21219.logintest.ToDo;

public class MainView extends AppCompatActivity {

    SQLiteDatabase myDB=null;
    private ListView listView;
    private ToDoOverviewListAdapter adapter;
    private List<ToDo> dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);


        TextView message = (TextView) findViewById(R.id.message);
        message.setText("Willkommen " + getIntent().getExtras().getString("UserID")+"!");

        this.listView = (ListView) findViewById(R.id.ListView_Tasks);

        this.dataSource = TodoDatabase.getInstance(this).readAllToDos();

        this.adapter = new ToDoOverviewListAdapter(this, dataSource);

        this.listView.setAdapter(new ToDoOverviewListAdapter(this, dataSource));
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int position, long id) {
                Object element = adapterView.getAdapter().getItem(position);

                if(element instanceof ToDo){
                    ToDo todo = (ToDo) element;

                    Intent intent = new Intent(MainView.this, ToDoDetailActivity.class);
                    intent.putExtra(ToDoDetailActivity.TODO_ID_KEY, todo.getId());

                    startActivity(intent);
                }
                Log.e("ClickOnList", element.toString());

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    public void refreshListView() {
        dataSource.clear();
        dataSource.addAll(TodoDatabase.getInstance(this).readAllToDos());
        adapter.notifyDataSetChanged();
        //macht ToDos direkt nach Erstellung sichtbar
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort:
               this.sort();
                return true;
            case R.id.menu_new_todo:
                this.newTodo();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sort() {
        //Hier müssen wir noch was tun!
    }

    public void newTodo(){
        Intent i = new Intent(MainView.this, TodoCreate.class);
        startActivity(i);
    }


}





