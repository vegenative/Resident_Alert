package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActionActivity extends AppCompatActivity {
    TextView placeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        placeText = findViewById(R.id.placeText);


        Intent intent = getIntent();
        String placeName = intent.getStringExtra("Place");

        placeText.setText("Jakiego rodzaju jest usterka w "+placeName+"?");
    }
}