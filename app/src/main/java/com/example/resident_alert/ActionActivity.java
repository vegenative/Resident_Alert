package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView placeText;
    private Button installButton;
    private Button electricButton;
    private Button gasButton;
    private Button waterButton;
    private Button cominButton;
    private Button glassButton;
    private Button decarButton;
    private Button lockButton;
    private Button constructionButton;
    private Button carpentryButton;
    private Button disifectionButton;
    private Button cleanButton;
    private Button otherButton;
    private Button infoButton;
    private String action;
    public String placeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        placeText = findViewById(R.id.placeText);

        installButton = findViewById(R.id.installButton);
        electricButton = findViewById(R.id.electricButton);
        gasButton = findViewById(R.id.gasButton);
        waterButton = findViewById(R.id.waterButton);
        cominButton = findViewById(R.id.cominButton);
        glassButton = findViewById(R.id.glassButton);
        decarButton = findViewById(R.id.decarButton);
        lockButton = findViewById(R.id.lockButton);
        constructionButton = findViewById(R.id.constructionButton);
        carpentryButton = findViewById(R.id.carpentryButton);
        disifectionButton = findViewById(R.id.disifectionButton);
        cleanButton = findViewById(R.id.cleanButton);
        otherButton = findViewById(R.id.otherButton);
        infoButton = findViewById(R.id.infoButton);


        installButton.setOnClickListener(this);
        electricButton .setOnClickListener(this);
        gasButton.setOnClickListener(this);
        waterButton.setOnClickListener(this);
        cominButton.setOnClickListener(this);
        glassButton.setOnClickListener(this);
        decarButton.setOnClickListener(this);
        lockButton.setOnClickListener(this);
        constructionButton.setOnClickListener(this);
        carpentryButton.setOnClickListener(this);
        disifectionButton.setOnClickListener(this);
        cleanButton.setOnClickListener(this);
        otherButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);

        Intent intent = getIntent();
        placeName = intent.getStringExtra("Place");

        placeText.setText("Jakiego rodzaju jest usterka w "+placeName+"?");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.installButton:
                action = "Instalatorskie";
                transition(action);
                break;
            case R.id.electricButton:
                action = "Elektryczne";
                transition(action);
                break;
            case R.id.gasButton:
                action = "Gazowe";
                transition(action);
                break;
            case R.id.waterButton:
                action = "Hydrauliczne";
                transition(action);
                break;
            case R.id.cominButton:
                action = "Kominiarskie";
                transition(action);
                break;
            case R.id.glassButton:
                action = "Szklarskie";
                transition(action);
                break;
            case R.id.decarButton:
                action = "Dekarskie";
                transition(action);
                break;
            case R.id.lockButton:
                action = "Ślusarskie";
                transition(action);
                break;
            case R.id.constructionButton:
                action = "Budowlane";
                transition(action);
                break;
            case R.id.carpentryButton:
                action= "Stolarskie";
                transition(action);
                break;
            case R.id.disifectionButton:
                action = "Dezynfekcja";
                transition(action);
                break;
            case R.id.cleanButton:
                action = "Sprzątanie";
                transition(action);
                break;
            case R.id.otherButton:
                action = "Inne";
                transition(action);
                break;
            case R.id.infoButton:
                action = "Informacyjne";
                transition(action);
                break;


        }
    }

    public void transition(String action){
        Intent intentt = new Intent(this, CompleteActivity.class);
        intentt.putExtra("Place",placeName);
        intentt.putExtra("Action",action);
        startActivity(intentt);
    }
}