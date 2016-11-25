package com.example.danyllo.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private EditText updateText;
    SharedPreferences prefs;
    private int id;
    private DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        id = prefs.getInt("id", -1);
        updateText = (EditText) findViewById(R.id.editText2);
    }

    public void updateTask(View view) {
        String newTask = updateText.getText().toString().trim();
        if (newTask.length() == 0) {
            Toast invalid = Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT);
            invalid.show();
        } else {
            db.update(newTask, id);
            Intent goBack = new Intent(this, MainActivity.class);
            startActivity(goBack);
            finish();
        }
    }
}
