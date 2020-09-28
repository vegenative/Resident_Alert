package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        logout_btn = (Button) findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(v -> {
            logout();
        });
    }
    //logout
    public void logout() {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}