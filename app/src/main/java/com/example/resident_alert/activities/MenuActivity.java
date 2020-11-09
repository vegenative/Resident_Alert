package com.example.resident_alert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resident_alert.R;
import com.example.resident_alert.SessionManager;
import com.example.resident_alert.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {

    private Button logout_btn;
    private Button ticketButton,history_button;
    private TextView dataInfo_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        logout_btn = (Button) findViewById(R.id.logout_btn);
        history_button = (Button)findViewById(R.id.historyButton);

//        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
//        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();
//
//        String name = userDetails.get(sessionManager.KEY_NAME);
//        String surname = userDetails.get(sessionManager.KEY_SURNAME);
//        String phone = userDetails.get(sessionManager.KEY_PHONE);
//        String email = userDetails.get(sessionManager.KEY_EMAIL);
//
//
        history_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkersAlertPanelActivity.class);
            startActivity(intent);
        });


        ticketButton = findViewById(R.id.ticketButton);
        ticketButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, PlaceActivity.class);

            startActivity(intent2);
        });


        logout_btn.setOnClickListener(v -> {

            //delete data from session
            SessionManager sessionManager1 = new SessionManager(this,SessionManager.SESSION_REMEMBERME);
            sessionManager1.logoutUserSession();

            logout();
        });

    }
    //logout
    public void logout() {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}