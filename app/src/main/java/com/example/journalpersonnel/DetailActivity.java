package com.example.journalpersonnel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String content = getIntent().getStringExtra("content");
        String tags = getIntent().getStringExtra("tags");

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewContent = findViewById(R.id.textViewContent);
        TextView textViewTags = findViewById(R.id.textViewTags);

        textViewTitle.setText(title);
        textViewDate.setText(date);
        textViewContent.setText(content);
        textViewTags.setText(tags);
    }
}