package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.resident_alert.activities.Login.LoginActivity;
import com.example.resident_alert.activities.Menu.PlaceActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private Button logout_btn;
    private Button ticketButton;
    TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");





        ticketButton = findViewById(R.id.ticketButton);
        logout_btn = (Button) findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(v -> {
            logout();
        });

        ticketButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, PlaceActivity.class);
            intent2.putExtra("phone",phone);
            startActivity(intent2);

        });



    }
    //logout
    public void logout() {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}