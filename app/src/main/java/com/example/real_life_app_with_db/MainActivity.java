package com.example.real_life_app_with_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static String TITLE = "title";
    Intent editor, reader;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> notes;
    DatabaseHandler dbHandler;
    SearchView searchView;
    RecyclerView rvNotes;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        editor = new Intent(MainActivity.this, Editor.class);
        reader = new Intent(MainActivity.this, Reader.class);
        //listView = findViewById(R.id.mainActivityListView);
        dbHandler = new DatabaseHandler(MainActivity.this);
        searchView = findViewById(R.id.search);
        notes = new ArrayList<>();
        rvNotes = findViewById(R.id.mainActivityListView);
        adapter = new NotesAdapter(MainActivity.this);
        for (Note note : dbHandler.getAllNotes()) {
            notes.add(note.getTitle());
        }
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    System.out.println(query);
                    if (notes.contains(query)) {
                        List<Note> noteListTmp = new ArrayList<>();
                        noteListTmp.add(dbHandler.getNote(query));
                        adapter.setNoteList(noteListTmp);
                    } else {
                        Toast.makeText(MainActivity.this, "Not Found.", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.setNoteList(dbHandler.getAllNotes());
                    return false;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

    public void addNote(View view) {
        startActivity(editor);
    }

    public void setRecyclerView() {
        rvNotes.setAdapter(adapter);
        rvNotes.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        adapter.setNoteList(dbHandler.getAllNotes());
    }

    public void setArrayAdapter() {
        for (Note note : dbHandler.getAllNotes()) {
            notes.add(note.getTitle());
            arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1, notes);
            listView.setAdapter(arrayAdapter);
        }
    }
}

