package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.resident_alert.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TargetActivity extends AppCompatActivity {

    private TextView nameTicket, surnameTicket, phoneTicket, adressTicket, cityTicket, placeTicket, actionTicket, submissionDateTicket, infoTicket, statusTicket;
    private Button acceptBtn, deleteBtn, endBtn;
    DatabaseReference refTicket, refProfile;
    public String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        nameTicket = findViewById(R.id.nameTicket);
        surnameTicket = findViewById(R.id.surnameTicket);
        phoneTicket = findViewById(R.id.phoneTicket);
        adressTicket = findViewById(R.id.adressTicket);
        cityTicket = findViewById(R.id.cityTicket);
        placeTicket = findViewById(R.id.placeTicket);
        actionTicket = findViewById(R.id.actionTicket);
        submissionDateTicket = findViewById(R.id.submissionDateTicket);
        infoTicket = findViewById(R.id.infoTicket);
        statusTicket = findViewById(R.id.statusTicket);
        acceptBtn = findViewById(R.id.acceptBtn);
        endBtn = findViewById(R.id.endBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        refTicket = FirebaseDatabase.getInstance().getReference().child("Tickets");
        refProfile = FirebaseDatabase.getInstance().getReference().child("Users");


        String key = getIntent().getStringExtra("key");
        phone = getIntent().getStringExtra("phone");

        refTicket.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String action = snapshot.child("action").getValue().toString();
                String place = snapshot.child("place").getValue().toString();
                String submissionDate = snapshot.child("submissionDate").getValue().toString();
                String status = snapshot.child("status").getValue().toString();
                String info = snapshot.child("info").getValue().toString();
                String phoneT = snapshot.child("phone").getValue().toString();

                actionTicket.setText(action);
                placeTicket.setText(place);
                submissionDateTicket.setText(submissionDate);
                statusTicket.setText(status);
                infoTicket.setText(info);
                phoneTicket.setText(phoneT);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refProfile.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String surname = snapshot.child("surname").getValue().toString();
                String adress = snapshot.child("street").getValue().toString()
                        +" "+snapshot.child("block").getValue().toString()
                        +snapshot.child("flatLetter").getValue().toString()
                        +" m."+snapshot.child("flat").getValue().toString();
                String city = snapshot.child("city").getValue().toString();

                nameTicket.setText(name);
                surnameTicket.setText(surname);
                adressTicket.setText(adress);
                cityTicket.setText(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        acceptBtn.setOnClickListener(v -> {
            refTicket.child(key).child("status").setValue("Zaakceptowane");

        });
        endBtn.setOnClickListener(v -> {
            refTicket.child(key).child("status").setValue("Zakończone");

        });
        deleteBtn.setOnClickListener(v -> {
            refTicket.child(key).removeValue();
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);

        });

    }
}