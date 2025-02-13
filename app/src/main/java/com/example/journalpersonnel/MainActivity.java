package com.example.journalpersonnel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextContent;
    private TextView textViewSelectedDate;
    private CheckBox checkBoxPersonal, checkBoxWork, checkBoxTravel;
    private Button buttonSave, buttonSelectDate;
    private ListView listViewEntries;
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private JournalEntryAdapter adapter;
    private int selectedDay, selectedMonth, selectedYear;
    private TextView textViewSelectedTitle, textViewSelectedDateDetails, textViewSelectedContent, textViewSelectedTags;
    private LinearLayout selectedEntryDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        checkBoxPersonal = findViewById(R.id.checkBoxPersonal);
        checkBoxWork = findViewById(R.id.checkBoxWork);
        checkBoxTravel = findViewById(R.id.checkBoxTravel);
        buttonSave = findViewById(R.id.buttonSave);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);
        listViewEntries = findViewById(R.id.listViewEntries);
        // Initialize new views for selected entry details
        textViewSelectedTitle = findViewById(R.id.textViewSelectedTitle);
        textViewSelectedDateDetails = findViewById(R.id.textViewSelectedDateDetails);
        textViewSelectedContent = findViewById(R.id.textViewSelectedContent);
        textViewSelectedTags = findViewById(R.id.textViewSelectedTags);
        selectedEntryDetails = findViewById(R.id.selectedEntryDetails);
        Button buttonHideDetails = findViewById(R.id.buttonHideDetails);

        adapter = new JournalEntryAdapter(this, journalEntries);
        listViewEntries.setAdapter(adapter);

        // Set current date
        Calendar calendar = Calendar.getInstance();
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedYear = calendar.get(Calendar.YEAR);
        updateDateDisplay();

        buttonSelectDate.setOnClickListener(v -> showDatePickerDialog());

        buttonSave.setOnClickListener(v -> saveJournalEntry());

        // Handle ListView item clicks
        listViewEntries.setOnItemClickListener((parent, view, position, id) -> {
            JournalEntry selectedEntry = journalEntries.get(position);

            // Populate the details section
            textViewSelectedTitle.setText(selectedEntry.getTitle());
            textViewSelectedDateDetails.setText("Date: " + selectedEntry.getDate());
            textViewSelectedContent.setText(selectedEntry.getContent());
            textViewSelectedTags.setText("Tags: " + selectedEntry.getTags().toString());

            // Make the details section visible
            selectedEntryDetails.setVisibility(View.VISIBLE);
        });

        // Handle "Hide Details" button click
        buttonHideDetails.setOnClickListener(v -> selectedEntryDetails.setVisibility(View.GONE));
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDay = dayOfMonth;
                    selectedMonth = month;
                    selectedYear = year;
                    updateDateDisplay();
                }, selectedYear, selectedMonth, selectedDay);
        datePickerDialog.show();
    }

    private void updateDateDisplay() {
        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
        textViewSelectedDate.setText("Date sélectionnée: " + date);
    }

    private void saveJournalEntry() {
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
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
