package com.example.journalpersonnel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
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

        TextView textViewEntryTitle = convertView.findViewById(R.id.textViewEntryTitle);
        TextView textViewEntryDate = convertView.findViewById(R.id.textViewEntryDate);
        TextView textViewEntryTags = convertView.findViewById(R.id.textViewEntryTags);
        ImageView imageViewMenu = convertView.findViewById(R.id.imageViewMenu);

        JournalEntry entry = journalEntries.get(position);

        textViewEntryTitle.setText(entry.getTitle());
        textViewEntryDate.setText(entry.getDate());
        textViewEntryTags.setText(entry.getTags().toString());

        // Configure le menu contextuel
        imageViewMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, imageViewMenu);
            popupMenu.getMenuInflater().inflate(R.menu.entry_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_details) {
                    showDetails(entry);
                    return true;
                } else if (item.getItemId() == R.id.menu_edit) {
                    showEditDialog(entry, position);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    deleteEntry(position);
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });

        return convertView;
    }

    private void showDetails(JournalEntry entry) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("title", entry.getTitle());
        intent.putExtra("date", entry.getDate());
        intent.putExtra("content", entry.getContent());
        intent.putExtra("tags", entry.getTags().toString());
        context.startActivity(intent);
    }

    private void showEditDialog(JournalEntry entry, int position) {
        View dialogView = inflater.inflate(R.layout.dialog_edit_entry, null);

        EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        EditText editTextContent = dialogView.findViewById(R.id.editTextContent);
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        CheckBox checkBoxPersonal = dialogView.findViewById(R.id.checkBoxPersonal);
        CheckBox checkBoxWork = dialogView.findViewById(R.id.checkBoxWork);
        CheckBox checkBoxTravel = dialogView.findViewById(R.id.checkBoxTravel);

        editTextTitle.setText(entry.getTitle());
        editTextContent.setText(entry.getContent());
        String[] dateParts = entry.getDate().split("/");
        datePicker.updateDate(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[0]));
        checkBoxPersonal.setChecked(entry.getTags().contains("Personnel"));
        checkBoxWork.setChecked(entry.getTags().contains("Travail"));
        checkBoxTravel.setChecked(entry.getTags().contains("Voyage"));

        new AlertDialog.Builder(context)
                .setTitle("Modifier l'entrée")
                .setView(dialogView)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    entry.setTitle(editTextTitle.getText().toString());
                    entry.setContent(editTextContent.getText().toString());
                    String newDate = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                    entry.setDate(newDate);

                    List<String> newTags = new ArrayList<>();
                    if (checkBoxPersonal.isChecked()) newTags.add("Personnel");
                    if (checkBoxWork.isChecked()) newTags.add("Travail");
                    if (checkBoxTravel.isChecked()) newTags.add("Voyage");
                    entry.setTags(newTags);

                    notifyDataSetChanged();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void deleteEntry(int position) {
        journalEntries.remove(position);
        notifyDataSetChanged();
    }
}