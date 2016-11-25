package com.example.danyllo.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_list_item_1;
import static com.example.danyllo.todolist.R.id.editText;

public class MainActivity extends AppCompatActivity {

    private ListView toDoListView;
    private EditText insertText;
    private ArrayAdapter<String> toDoAdapter;
    private ArrayList<String> toDoList, colorList;
    private DBHelper db = new DBHelper(this);

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        initialize();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        String insert = insertText.getText().toString();
        if (insert.length() != 0) {
            savedInstanceState.putString("editText", insert);
        }
        /*ArrayList<String> adapVals = new ArrayList<String>();
        for (int i = 0; i < toDoAdapter.getCount(); i++) {
            adapVals.add(toDoAdapter.getItem(i));
        }
        savedInstanceState.putStringArrayList("Adapter", adapVals);*/
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        insertText.setText(savedInstanceState.getString("editText", ""));
        /*ArrayList<String> adapVals = savedInstanceState.getStringArrayList("Adapter");
        if (adapVals != null) {
            toDoAdapter = new ArrayAdapter<String>(this, simple_list_item_1, adapVals);
        }*/
    }

    //initialization function
    private void initialize(){
        setViews();
        setListeners();
    }

    //sets up the views and reads the lists from database
    private void setViews(){
        toDoListView = (ListView) findViewById(R.id.listView);
        insertText = (EditText) findViewById(R.id.editText);
        insertText.setHint("What do you have to do?");
        toDoList = db.read();
        colorList = db.readColors();
        toDoAdapter = new ArrayAdapter<String>(this, simple_list_item_1, toDoList);
        toDoListView.setAdapter(toDoAdapter);
        //setViewColors(); //off because it produces exception
        Log.d("got here", String.valueOf(toDoListView.getChildCount()));
    }

    //function that sets the colors of the children of the listview
    //doesn't work as childCount is zero for some reason
    private void setViewColors() {
        Log.d("BOI", String.valueOf(toDoListView.getCount()));
        for (int i = 0; i < toDoListView.getCount(); i++) {
            Log.d("GET", toDoList.get(i));
            Log.d("GET", colorList.get(i));
            int trueIndex = i - toDoListView.getFirstVisiblePosition();
            View child = toDoListView.getChildAt(trueIndex);
            if (child != null) {
                child.setBackgroundColor(Integer.parseInt(colorList.get(i)));
            }
        }
    }

    //function that sets onclick and onlongclick listeners for the listview
    private void setListeners() {
        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.delete(toDoList.get(position));
                resetList(); //delete element from list and reset
                return false;
            }
        });
        //sets background color to yellow on click
        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                View entry = toDoListView.getChildAt(position);
                entry.setBackgroundColor(Color.YELLOW);
                db.updateColor(toDoList.get(position), Color.YELLOW);
            }
        });
    }

    //function which rereads the list and resets the adapter of the ListView with the new list
    private void resetList() {
        toDoList = db.read();
        colorList = db.readColors();
        toDoAdapter.clear();
        toDoAdapter.addAll(toDoList);
        toDoAdapter.notifyDataSetChanged();
    }

    //onclick function which adds task to database and displays it on screen
    public void addTask(View view) {
        String input = insertText.getText().toString().trim();
        if (input.length() == 0) { //no empty strings
            Toast toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (!toDoList.contains(input)) {
                insertText.setText("");
                db.create(input);
                resetList();
            } else { //no entries already in the list
                Toast contains = Toast.makeText(this, "You're already doing this", Toast.LENGTH_SHORT);
                contains.show();
            }
        }
    }
}
