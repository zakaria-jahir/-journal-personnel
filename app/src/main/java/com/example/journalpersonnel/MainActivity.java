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

        // Action du bouton "Enregistrer"
        buttonSave.setOnClickListener(v -> saveJournalEntry());
    }

    private void saveJournalEntry() {
        // Récupérer les données saisies par l'utilisateur
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Les mois commencent à 0
        int year = datePicker.getYear();
        String date = day + "/" + month + "/" + year;

        // Récupérer les tags sélectionnés
        List<String> tags = new ArrayList<>();
        if (checkBoxPersonal.isChecked()) tags.add("Personnel");
        if (checkBoxWork.isChecked()) tags.add("Travail");
        if (checkBoxTravel.isChecked()) tags.add("Voyage");

        // Vérifier que le titre et le contenu ne sont pas vides
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer une nouvelle entrée de journal
        JournalEntry newEntry = new JournalEntry(title, content, date, tags);

        // Ajouter l'entrée à la liste
        journalEntries.add(newEntry);

        // Mettre à jour la liste
        adapter.notifyDataSetChanged();

        // Réinitialiser les champs
        editTextTitle.setText("");
        editTextContent.setText("");
        checkBoxPersonal.setChecked(false);
        checkBoxWork.setChecked(false);
        checkBoxTravel.setChecked(false);

        // Afficher un message de confirmation
        Toast.makeText(this, "Entrée enregistrée avec succès", Toast.LENGTH_SHORT).show();
    }
}