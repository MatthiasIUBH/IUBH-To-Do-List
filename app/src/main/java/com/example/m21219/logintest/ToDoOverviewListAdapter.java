package com.example.m21219.logintest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hakan Akkurt on 03.02.2017.
 */

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
            view = LayoutInflater.from(getContext()).inflate(R.layout.todo_overwiev_list_item, parent, false);
        }

        ((TextView) view.findViewById(R.id.name)).setText(currentToDo.getName());

        TextView completiondate = (TextView) view.findViewById(R.id.completiondate);
        ImageView favorite = (ImageView) view.findViewById(R.id.favorite_icon);
        ImageView completionstatus = (ImageView) view.findViewById(R.id.completionstatus);

        if(currentToDo.getCompletiondate()== null){
            completiondate.setVisibility(View.GONE);
        }else{
            completiondate.setVisibility(View.VISIBLE);
            completiondate.setText(getDateInString(currentToDo.getCompletiondate()));
        }

        if(currentToDo.isFavorite()){
            favorite.setVisibility(View.VISIBLE);
            favorite.setImageResource(R.mipmap.ic_star);
        }else{
            favorite.setVisibility(View.INVISIBLE);
        }
        if(currentToDo.isCompletionstatus()) {
            completionstatus.setVisibility(View.VISIBLE);
            completionstatus.setImageResource(R.mipmap.ic_checkedbox);
        } else {
            completionstatus.setVisibility(View.INVISIBLE);
        }

        return view;
    } private String getDateInString(Calendar calendar) {
        return String.format(Locale.GERMANY, "%02d.%02d.%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY));
        //return calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }
}

