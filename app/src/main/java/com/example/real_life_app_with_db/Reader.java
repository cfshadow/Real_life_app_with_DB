package com.example.real_life_app_with_db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Reader extends AppCompatActivity {
    Intent intent;
    EditText readerTitle, readerNote;
    DatabaseHandler dbHandler;
    Note note;
    Menu menu;
    MenuItem menuItemEdit, menuItemSave, menuItemDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        setContentView(R.layout.activity_reader);
        readerNote = findViewById(R.id.readNote);
        readerTitle = findViewById(R.id.readTitle);
        intent = this.getIntent();
        String title = intent.getStringExtra("title");
        dbHandler = new DatabaseHandler(Reader.this);
        note = dbHandler.getNote(title);
        readerTitle.setText(note.getTitle());
        readerNote.setText(note.getNote());
        readerTitle.setEnabled(false);
        readerNote.setEnabled(false);
        readerTitle.setTextColor(Color.BLACK);
        readerNote.setTextColor(Color.BLACK);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        menuItemEdit = menu.findItem(R.id.readerEdit);
        menuItemSave = menu.findItem(R.id.saveEdited);
        menuItemDelete = menu.findItem(R.id.delete);
        switch (item.getItemId()) {
            case (R.id.delete):
                dbHandler.deleteNote(note);
                Toast.makeText(Reader.this, "Delete.", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
                break;
            case (R.id.readerEdit):
                readerNote.setEnabled(true);
                readerTitle.setEnabled(true);
                menuItemSave.setVisible(true);
                menuItemEdit.setVisible(false);
                break;
            case (R.id.saveEdited):
                Note noteNew = new Note();
                noteNew.setTitle(readerTitle.getText().toString().trim());
                noteNew.setNote(readerNote.getText().toString().trim());
                dbHandler.editNote(noteNew, note);
                note = noteNew;
                readerTitle.setEnabled(false);
                readerNote.setEnabled(false);
                menuItemSave.setVisible(false);
                menuItemEdit.setVisible(true);
                readerTitle.setTextColor(Color.BLACK);
                readerNote.setTextColor(Color.BLACK);
                Toast.makeText(Reader.this, "Saved.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(Reader.this, "Something Went Wrong.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_reader, menu);
        return true;
    }
}