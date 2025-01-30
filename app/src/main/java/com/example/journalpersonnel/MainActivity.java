package com.example.journalpersonnel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextContent;
    private DatePicker datePicker;
    private CheckBox checkBoxPersonal, checkBoxWork, checkBoxTravel;
    private Button buttonSave;
    private ListView listViewEntries;

    private List<JournalEntry> journalEntries = new ArrayList<>();

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


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJournalEntry();
            }
        });


        updateListView();
        listViewEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JournalEntry entry = journalEntries.get(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("title", entry.getTitle());
                intent.putExtra("date", entry.getDate());
                intent.putExtra("content", entry.getContent());
                intent.putExtra("tags", entry.getTags().toString());

                startActivity(intent);

            }
        });
    }

    private void saveJournalEntry() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String date = day + "/" + month + "/" + year;
        List<String> tags = new ArrayList<>();
        if (checkBoxPersonal.isChecked()) tags.add("Personnel");
        if (checkBoxWork.isChecked()) tags.add("Travail");
        if (checkBoxTravel.isChecked()) tags.add("Voyage");
        JournalEntry entry = new JournalEntry(title, content, date, tags);
        journalEntries.add(entry);
        updateListView();
        Toast.makeText(this, "Entrée enregistrée avec succés", Toast.LENGTH_SHORT).show();
        editTextTitle.setText("");
        editTextContent.setText("");
        checkBoxPersonal.setChecked(false);
        checkBoxWork.setChecked(false);
        checkBoxTravel.setChecked(false);
    }

    private void updateListView() {
        JournalEntryAdapter adapter = new JournalEntryAdapter(this, journalEntries);
        listViewEntries.setAdapter(adapter);
    }
}
