package com.example.journalpersonnel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button buttonMain = findViewById(R.id.buttonMain);

        textViewTitle.setText(title);
        textViewDate.setText(date);
        textViewContent.setText(content);
        textViewTags.setText(tags);


        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}