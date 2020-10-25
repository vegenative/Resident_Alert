package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener {


    private Button bathroomButton;
    private Button kitchenButton;
    private Button salonButton;
    private Button corridorButton;
    private  Button cellarButton;
    private Button staircaseButton;
    private  Button garageButton;
    private Button gardenButton;
    private String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        bathroomButton = findViewById(R.id.bathroomButton);
        kitchenButton = findViewById(R.id.kitchenButton);
        salonButton = findViewById(R.id.salonButton);
        corridorButton = findViewById(R.id.corridorButton);
        cellarButton = findViewById(R.id.cellarButton);
        staircaseButton = findViewById(R.id.staircaseButton);
        garageButton = findViewById(R.id.garageButton);
        gardenButton = findViewById(R.id.gardenButton);

        bathroomButton.setOnClickListener(this);
        kitchenButton.setOnClickListener(this);
        salonButton.setOnClickListener(this);
        corridorButton.setOnClickListener(this);
        cellarButton.setOnClickListener(this);
        staircaseButton.setOnClickListener(this);
        gardenButton.setOnClickListener(this);
        garageButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bathroomButton:
                place = "Bathroom";
                transition(place);
                break;
            case R.id.kitchenButton:
                place = "Kitchen";
                transition(place);
                break;
            case R.id.salonButton:
                place = "Salon";
                transition(place);
                break;
            case R.id.corridorButton:
                place = "Corridor";
                transition(place);
                break;
            case R.id.cellarButton:
                place = "Cellar";
                transition(place);
                break;
            case R.id.staircaseButton:
                place = "Staircase";
                transition(place);
                break;
            case R.id.gardenButton:
                place = "Garden";
                transition(place);
                break;
            case R.id.garageButton:
                place = "Garage";
                transition(place);
                break;


        }
    }

    public void transition(String place){
        Intent intent = new Intent(this, ActionActivity.class);
        intent.putExtra("Place",place);
        startActivity(intent);
    }
}