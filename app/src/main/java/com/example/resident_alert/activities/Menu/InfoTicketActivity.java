package com.example.resident_alert.activities.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.resident_alert.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoTicketActivity extends AppCompatActivity {

    public String ticketKey,submissionDate, status, action,place,info;
    private TextView submissionDate_tv, status_tv ,action_tv,place_tv,info_tv;
    private Button back;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ticket);

        submissionDate_tv = (TextView) findViewById(R.id.data_ticket) ;
        status_tv = (TextView) findViewById(R.id.status_ticket) ;
        action_tv = (TextView) findViewById(R.id.action_ticket) ;
        place_tv = (TextView) findViewById(R.id.place_ticket) ;
        info_tv = (TextView) findViewById(R.id.info_ticket) ;
        back = (Button) findViewById(R.id.backInfoTicket_btn);


        //get values from previous activity
        Intent intent = getIntent();
        ticketKey = intent.getStringExtra("ticketKey");
        submissionDate = intent.getStringExtra("submissionDate");
        status = intent.getStringExtra("status");
        action = intent.getStringExtra("action");
        place = intent.getStringExtra("place");
        info = intent.getStringExtra("info");



        submissionDate_tv.setText(submissionDate);
        status_tv.setText(status);
        action_tv.setText(action);
        place_tv.setText(place);
        info_tv.setText(info);

        //close this activity
        back.setOnClickListener(v -> {
            this.finish();
        });

    }
}