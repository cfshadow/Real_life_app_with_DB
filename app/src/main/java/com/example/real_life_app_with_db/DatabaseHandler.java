package com.example.real_life_app_with_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "notesManager";
    public static final String TABLE_NOTES = "notes";
    public static final String KEY_TITLE = "title";
    public static final String KEY_NOTE = "note";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTES +
                "( " + KEY_TITLE + " TEXT PRIMARY KEY, " +
                KEY_NOTE + " TEXT );";
        db.execSQL(CREATE_NOTE_TABLE);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
        //db.close();
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_NOTE, note.getNote());
        db.insertOrThrow(TABLE_NOTES, null, values);
        db.close();
    }

    public void editNote(Note noteNew, Note noteOld) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, noteNew.getTitle());
        values.put(KEY_NOTE, noteNew.getNote());
        db.update(TABLE_NOTES, values, KEY_TITLE + "=?", new
                String[]{noteOld.getTitle()});
        db.close();
    }

    public Note getNote(String title) {
        Note note = new Note();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, new String[]{KEY_TITLE, KEY_NOTE}
                , KEY_TITLE + "=?", new String[]{title}
                , null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            note.setTitle(cursor.getString(0));
            note.setNote(cursor.getString(1));
        }
        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<Note>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            do {
                Note note = new Note();
                note.setTitle(cursor.getString(0));
                note.setNote(cursor.getString(1));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return noteList;
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_TITLE + "=?", new String[]{note.getTitle()});
        db.close();
    }

    public void getNotesCount() {
        String countQuery = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        db.close();
    }
}