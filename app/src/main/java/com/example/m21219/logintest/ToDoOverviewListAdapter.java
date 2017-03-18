package com.example.m21219.logintest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hakan Akkurt on 03.02.2017.
 */

/* Diese Klasse stellt uns die ArrayAdapter für Todo_Übersicht zur Verfügung und erbt von der Klasse "ArrayAdapter*/
public class ToDoOverviewListAdapter extends ArrayAdapter<ToDo> {
    public  ToDoOverviewListAdapter(final Context context, final List<ToDo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDo currentToDo = getItem(position);

        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.todo_overwiev_list_item, parent, false); //Die Struktur von Todos wird hier übernommen.
        }

        ((TextView) view.findViewById(R.id.view_name)).setText(currentToDo.getName());

        TextView completiondate = (TextView) view.findViewById(R.id.view_completiondate);
        TextView completiontime = (TextView) view.findViewById(R.id.view_completiontime);
        ImageView favorite = (ImageView) view.findViewById(R.id.favorite_icon);
        ImageView completionstatus = (ImageView) view.findViewById(R.id.view_completionstatus);

        if(currentToDo.getCompletiondate()== null){//Wenn das CompletionDate leer ist
            completiondate.setVisibility(View.GONE); // Wird der TextView versteckt
        }else{
            completiondate.setVisibility(View.VISIBLE); //Sonst angezeigt
            completiondate.setText(getDateInString(currentToDo.getCompletiondate()));// Mit dem eingegeben Wert.
        }

        if(currentToDo.getCompletiontime() == null){ //Wenn das CompletionTime leer ist
            completiontime.setVisibility(View.GONE); // Wird der TextView versteckt
        }else {
            completiontime.setVisibility(View.VISIBLE);//Sonst angezeigt
            completiontime.setText(getTimeInString(currentToDo.getCompletiontime()));// Mit dem eingebenen Wert.
        }

        if(currentToDo.isFavorite()){//Wenn das Favorite-Feld angeklickt ist
            favorite.setVisibility(View.VISIBLE); //Wird das Favorite-Icon angezeigt.
            favorite.setImageResource(R.mipmap.ic_star);
        }else{
            favorite.setVisibility(View.INVISIBLE);//Sonst versteckt.
        }
        if(currentToDo.isCompletionstatus()) {//Wenn das Erledigt-Feld angeklickt ist
            completionstatus.setVisibility(View.VISIBLE); //Wird das Erledigt-Icon angezeigt.
            completionstatus.setImageResource(R.mipmap.ic_checkedbox);
        } else {
            completionstatus.setVisibility(View.INVISIBLE);//Sonst versteckt.
        }

        return view;
        //Das Datum und die Uhrzeit wird mit gewünschtem Format in der View angezeigt.
    } private String getDateInString(Calendar calendar) {
        return String.format(Locale.GERMANY, "%02d.%02d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1);
    }

    private String getTimeInString(Calendar time){
        return String.format(Locale.GERMANY, "%02d:%02d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
    }
}

