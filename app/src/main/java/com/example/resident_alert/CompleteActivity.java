package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CompleteActivity extends AppCompatActivity {
        TextView testText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        testText = findViewById(R.id.testText);

        Intent intent = getIntent();
       String actionName = intent.getStringExtra("Action");
        String placeName = intent.getStringExtra("Place");

        testText.setText("Usterka w "+placeName+". Rodzaj: "+actionName);
    }
}