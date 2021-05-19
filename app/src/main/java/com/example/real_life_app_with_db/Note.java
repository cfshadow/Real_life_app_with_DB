package com.example.real_life_app_with_db;

public class Note {
    private String title, note;

    //constructor
    public Note(String title, String note) {
        this.title = title;
        this.note = note;
    }

    // Default constructor
    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}