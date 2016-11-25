package com.example.danyllo.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    private void initialize(){
        setViews();
        setListeners();
    }


    private void setViews(){
        toDoListView = (ListView) findViewById(R.id.listView);
        insertText = (EditText) findViewById(R.id.editText);
        insertText.setHint("What do you have to do?");
        toDoList = db.read();
        colorList = db.readColors();
        toDoAdapter = new ArrayAdapter<String>(this, simple_list_item_1, toDoList);
        toDoListView.setAdapter(toDoAdapter);
        Log.d("got here", String.valueOf(toDoListView.getChildCount()));
        setViewColors();
    }

    private void setViewColors() {
        Log.d("BOI", String.valueOf(toDoListView.getChildCount()));
        for (int i = 0; i < toDoListView.getChildCount(); i++) {
            Log.d("GET", toDoList.get(i));
            Log.d("GET", colorList.get(i));
            toDoListView.getChildAt(i).setBackgroundColor(Integer.parseInt(colorList.get(i)));
        }
    }

    private void setListeners() {
        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.delete(toDoList.get(position));
                resetList();
                Log.d("POS", String.valueOf(position));
                Log.d("ID", String.valueOf(id));
                return false;
            }
        });

        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("TEMP", "TEMP");
                View entry = toDoListView.getChildAt(position);
                entry.setBackgroundColor(Color.YELLOW);
                db.updateColor(toDoList.get(position), Color.YELLOW);
            }
        });
    }

    private void resetList() {
        toDoList = db.read();
        colorList = db.readColors();
        toDoAdapter.clear();
        toDoAdapter.addAll(toDoList);
        toDoAdapter.notifyDataSetChanged();
    }


    public void addTask(View view) {
        String input = insertText.getText().toString().trim();
        if (input.length() == 0) {
            Toast toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (!toDoList.contains(input)) {
                insertText.setText("");
                db.create(input);
                resetList();
            } else {
                Toast contains = Toast.makeText(this, "You're already doing this", Toast.LENGTH_SHORT);
                contains.show();
            }
        }
    }
}
