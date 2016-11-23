package com.example.danyllo.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import static com.example.danyllo.todolist.R.id.editText;

public class MainActivity extends AppCompatActivity {

    private ListView toDoList;
    private EditText insertText;
    private ArrayAdapter<String> toDoAdapter = new ArrayAdapter<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize(){
        toDoList = (ListView) findViewById(R.id.listView);
        insertText = (EditText) findViewById(R.id.editText);
        insertText.setHint("What do you have to do?");
    }
}
