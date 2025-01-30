package com.example.journalpersonnel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class JournalEntryAdapter extends BaseAdapter {
    private Context context;
    private List<JournalEntry> journalEntries;
    private LayoutInflater inflater;

    public JournalEntryAdapter(Context context, List<JournalEntry> journalEntries) {
        this.context = context;
        this.journalEntries = journalEntries;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return journalEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return journalEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_entry, parent, false);
        }

        ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
        TextView textViewEntryTitle = convertView.findViewById(R.id.textViewEntryTitle);
        TextView textViewEntryDate = convertView.findViewById(R.id.textViewEntryDate);
        TextView textViewEntryTags = convertView.findViewById(R.id.textViewEntryTags);

        JournalEntry entry = journalEntries.get(position);

        textViewEntryTitle.setText(entry.getTitle());
        textViewEntryDate.setText(entry.getDate());
        textViewEntryTags.setText(entry.getTags().toString());

        return convertView;
    }
}
