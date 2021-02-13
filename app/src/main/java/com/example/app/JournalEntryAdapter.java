package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalEntryAdapter extends RecyclerView.Adapter<JournalEntryAdapter.ViewHolder> {
    private List<JournalEntry> entries;
    private Context context;

    public JournalEntryAdapter(List<JournalEntry> entries, Context context ){
        this.entries = entries;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_past_journal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry entry = entries.get(position);

        holder.tv_text.setText(entry.getText());
        holder.tv_dateCompleted.setText(entry.getDate());

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_text;
        public TextView tv_dateCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            tv_dateCompleted = (TextView) itemView.findViewById(R.id.tv_dateCompleted);
        }
    }
}