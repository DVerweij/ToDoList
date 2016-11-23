package com.example.danyllo.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static com.example.danyllo.todolist.R.id.editText;

public class MainActivity extends AppCompatActivity {

    private ListView toDoListView;
    private EditText insertText;
    private ArrayAdapter<String> toDoAdapter;
    private ArrayList<String> toDoList;
    private DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize(){
        toDoListView = (ListView) findViewById(R.id.listView);
        insertText = (EditText) findViewById(R.id.editText);
        insertText.setHint("What do you have to do?");
        toDoList = db.read();
        toDoAdapter = new ArrayAdapter<String>(this, simple_list_item_1, toDoList);
        toDoListView.setAdapter(toDoAdapter);
    }

    public void addTask(View view) {
        String input = insertText.getText().toString().trim();
        if (input.length() == 0) {
            Toast toast = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (!toDoList.contains(input)) {
                db.create(input);
                toDoList = db.read();
                toDoAdapter.clear();
                toDoAdapter.addAll(toDoList);
                toDoAdapter.notifyDataSetChanged();
            } else {
                Toast contains = Toast.makeText(this, "You're already doing this", Toast.LENGTH_SHORT);
                contains.show();
            }
        }
    }
}
