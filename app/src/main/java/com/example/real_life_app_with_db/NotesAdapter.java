package com.example.real_life_app_with_db;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Note> noteList;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public NotesAdapter(Context context) {
        this.context = context;
    }

    public NotesAdapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    public NotesAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    View.OnClickListener listener;

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
// Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View noteView = inflater.inflate(R.layout.item_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        TextView textViewTitle = holder.textViewTitle;
        textViewTitle.setText(note.getTitle());
        TextView textViewNote = holder.textViewNote;
        textViewNote.setText(note.getNote());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReader = new Intent(context, Reader.class);
                String title = note.getTitle();
                intentReader.putExtra(MainActivity.TITLE, title);
                context.startActivity(intentReader);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle, textViewNote;
        public CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.item_note_title);
            textViewNote = itemView.findViewById(R.id.item_note_note);
            layout = itemView.findViewById(R.id.item_note_layout);
        }
    }
}