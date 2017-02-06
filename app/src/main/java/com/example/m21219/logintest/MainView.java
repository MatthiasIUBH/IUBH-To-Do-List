package com.example.m21219.logintest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        /*
        myDB = this.openOrCreateDatabase("IUBHtoDoApp", MODE_PRIVATE, null);
        Cursor cursor = myDB.rawQuery("SELECT id, name, description FROM Tasks where userid='"+getIntent().getExtras().getString(MainActivity.UserID)+"'", null);
        */

        this.listView = (ListView) findViewById(R.id.ListView_Tasks);


        this.dataSource = TodoDatabase.getInstance(this).readAllToDos();

        this.adapter = new ToDoOverviewListAdapter(this, dataSource);
       /* List<ToDo> dataSource = new ArrayList<>();
        dataSource.add(new ToDo("Einkaufen"));
        dataSource.add(new ToDo("Klausur schreiben", Calendar.getInstance(), true, "Beschreibung", true));
        dataSource.add(new ToDo("Projektmitglieder updaten", Calendar.getInstance(), false, "haha", true));*/

        this.listView.setAdapter(new ToDoOverviewListAdapter(this, dataSource));
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int position, long id) {
                Object element = adapterView.getAdapter().getItem(position);

                if(element instanceof ToDo){
                    ToDo todo = (ToDo) element;


                    Intent intent = new Intent(MainView.this, TodoCreate.class);
                    intent.putExtra(TodoCreate.TODO_ID_KEY, todo.getId());

                    startActivity(intent);
                }
                Log.e("ClickOnList", element.toString());

            }
        });

        /*
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
    } */
}
/*
    public void createTodo (View view) {

        String UserID = getIntent().getExtras().getString("UserID");
        Intent createTodo = new Intent(this, TodoCreate.class);
        createTodo.putExtra("UserID", UserID);
        startActivity(createTodo);
    }
*/


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





