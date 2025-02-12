package com.example.journalpersonnel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextContent;
    private DatePicker datePicker;
    private CheckBox checkBoxPersonal, checkBoxWork, checkBoxTravel;
    private Button buttonSave;
    private ListView listViewEntries;

    private List<JournalEntry> journalEntries = new ArrayList<>();
    private JournalEntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        datePicker = findViewById(R.id.datePicker);
        checkBoxPersonal = findViewById(R.id.checkBoxPersonal);
        checkBoxWork = findViewById(R.id.checkBoxWork);
        checkBoxTravel = findViewById(R.id.checkBoxTravel);
        buttonSave = findViewById(R.id.buttonSave);
        listViewEntries = findViewById(R.id.listViewEntries);

        // Initialisation de l'adaptateur
        adapter = new JournalEntryAdapter(this, journalEntries);
        listViewEntries.setAdapter(adapter);

        buttonSave.setOnClickListener(v -> saveJournalEntry());
    }

    private void saveJournalEntry() {
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String date = day + "/" + month + "/" + year;
        List<String> tags = new ArrayList<>();
        if (checkBoxPersonal.isChecked()) tags.add("Personnel");
        if (checkBoxWork.isChecked()) tags.add("Travail");
        if (checkBoxTravel.isChecked()) tags.add("Voyage");
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }
        JournalEntry newEntry = new JournalEntry(title, content, date, tags);
        journalEntries.add(newEntry);
        adapter.notifyDataSetChanged();
        editTextTitle.setText("");
        editTextContent.setText("");
        checkBoxPersonal.setChecked(false);
        checkBoxWork.setChecked(false);
        checkBoxTravel.setChecked(false);
        Toast.makeText(this, "Entrée enregistrée avec succès", Toast.LENGTH_SHORT).show();
    }
}