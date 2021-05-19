package com.example.real_life_app_with_db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Editor extends AppCompatActivity {
    EditText title, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        title = findViewById(R.id.editTitle);
        note = findViewById(R.id.editNote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            String toastText = "Something went wrong.";
            String input_title = "";
            String input_note = "";
            input_title = title.getText().toString().trim();
            input_note = note.getText().toString().trim();
            if (input_title.isEmpty() || input_note.isEmpty()) {
                toastText = "Please insert title and note both.";
            } else {
                // save note in data base
                Note noteObj = new Note(input_title, input_note);
                DatabaseHandler dbHandler = new DatabaseHandler(Editor.this);
                try {
                    dbHandler.addNote(noteObj);
                    toastText = "Saved.";
                    title.setText("");
                    note.setText("");
                } catch (android.database.sqlite.SQLiteConstraintException e) {
                    toastText = "Title name is already saved.";
                    title.setText("");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
            Toast.makeText(Editor.this, toastText, Integer.valueOf(0)).show();
        }
        return super.onOptionsItemSelected(item);
    }
}