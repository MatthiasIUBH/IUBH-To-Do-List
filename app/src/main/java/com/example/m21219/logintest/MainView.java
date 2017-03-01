package com.example.m21219.logintest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainView extends AppCompatActivity {

    private ListView listView;
    private ToDoOverviewListAdapter adapter;
    private List<ToDo> dataSource;
    private List<ToDo> dataexport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);


        TextView message = (TextView) findViewById(R.id.message);

        //Willkommensnachricht mit UserName ausgeben
        message.setText("Willkommen " + getIntent().getExtras().getString("UserName") + "!");


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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                Object element = adapterView.getAdapter().getItem(position);

                if(element instanceof ToDo){
                    final ToDo todo = (ToDo)element;
                    new AlertDialog.Builder(MainView.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Sind Sie sicher?")
                            .setMessage("Möchten Sie diesen Eintrag löschen?")
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    TodoDatabase.getInstance(MainView.this).deleteToDo(todo);
                                    refreshListView();
                                }
                            })
                            .setNegativeButton("Nein", null)
                            .show();
                }
                return true;
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
            case R.id.clearAll:
                this.clearAll();
                return true;
            case R.id.heute:
                this.heute();
                return true;
            case R.id.export:
                this.export();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void export() {
        try {
            String OutputString="";
            SimpleDateFormat simpleDate =  new SimpleDateFormat("dd.MM.yyyy");
            this.dataexport = TodoDatabase.getInstance(this).readAllToDos();

            FileOutputStream fOut = openFileOutput("Export.csv", Context.MODE_PRIVATE);
            OutputStreamWriter outputstream = new OutputStreamWriter(fOut);

            // Schreibe Daten in die Datei "Export.csv" unter \data\data\com.example.m21219.logintest\files
            OutputString+="Name;Beschreibung;Erledigungsstatus;Favorit;Erledigungsdatum\n"; //Kopfzeile schreiben

            //Daten schreiben
            for (ToDo s : dataexport) {
                OutputString+= s.getName().toString() + ";"+s.getDescription().toString()+ ";"+s.isCompletionstatus()+";"+s.isFavorite()+";";

                if ( s.getCompletiondate() == null) {
                    OutputString+="\n";
                }
                else {
                    OutputString+= simpleDate.format(s.getCompletiondate().getTime())+"\n";
                }
            }

            //Datei schreiben
            outputstream.write(OutputString);
            outputstream.flush();
            outputstream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void heute() {
        //Hier müssen wir was tun!
    }


    private void clearAll() {
        TodoDatabase database = TodoDatabase.getInstance(MainView.this);
        database.deleteAllToDos();
        refreshListView();
    }

    public void sort() {
        //Hier müssen wir noch was tun!
    }


    public void newTodo(){
        Intent i = new Intent(MainView.this, TodoCreate.class);
        startActivity(i);
    }

}





