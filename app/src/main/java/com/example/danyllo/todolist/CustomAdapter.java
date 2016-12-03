package com.example.danyllo.todolist;

//Courtesy of: http://www.vogella.com/tutorials/AndroidListView/article.html

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Danyllo on 3-12-2016.
 */



public class CustomAdapter extends ArrayAdapter<String>{
    private final Context context;
    private ArrayList<String> toDoList;
    private ArrayList<String> colorList;

    public CustomAdapter(Context context, ArrayList<String> toDoList, ArrayList<String> colorList) {
        super(context, R.layout.activity_main, toDoList);
        this.context = context;
        this.toDoList = toDoList;
        this.colorList = colorList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = inflater.inflate(R.layout.activity_list, parent, false);
        View rowView = inflater.inflate(R.layout.listview_row_item, parent, false);
        TextView listRow = (TextView) rowView.findViewById(R.id.rowItem);
        listRow.setText(toDoList.get(position));
        listRow.setBackgroundColor(Integer.parseInt(colorList.get(position)));
        return rowView;
    }
}
