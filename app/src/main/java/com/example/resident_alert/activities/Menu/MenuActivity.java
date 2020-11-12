package com.example.resident_alert.activities.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.resident_alert.R;
import com.example.resident_alert.SessionManager;
import com.example.resident_alert.activities.Login.LoginActivity;
import com.example.resident_alert.activities.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private Button logout_btn;

    private Button ticketButton,history_button,workersHistory_btn;

    private Button profileButton;

    private TextView dataInfo_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        logout_btn = (Button) findViewById(R.id.logout_btn);

        history_button = (Button)findViewById(R.id.historyButton);
        workersHistory_btn = (Button) findViewById(R.id.workersHistory);
        ticketButton = findViewById(R.id.ticketButton);



        profileButton = findViewById(R.id.profileButton);


        history_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });



        ticketButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, PlaceActivity.class);

            startActivity(intent2);
        });



        workersHistory_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserTicketHistoryActivity.class);

            startActivity(intent);
        });





        profileButton.setOnClickListener(v -> {
            Intent intent4 = new Intent(this, ProfileActivity.class);

            startActivity(intent4);
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