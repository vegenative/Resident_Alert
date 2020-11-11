package com.example.resident_alert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.resident_alert.R;
import com.example.resident_alert.SessionManager;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameProfile,surnameProfile,phoneProfile,adressProfile,cityProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameProfile = findViewById(R.id.nameProfile);
        surnameProfile = findViewById(R.id.surnameProfile);
        phoneProfile =  findViewById(R.id.phoneProfile);
        adressProfile = findViewById(R.id.adressProfile);
        cityProfile = findViewById(R.id.cityProfile);

        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();

        nameProfile.setText(userDetails.get(sessionManager.KEY_NAME));
        surnameProfile.setText(userDetails.get(sessionManager.KEY_SURNAME));
        phoneProfile.setText(userDetails.get(sessionManager.KEY_PHONE));
        adressProfile.setText(userDetails.get(sessionManager.KEY_STREET) + " " + userDetails.get(sessionManager.KEY_BLOCK)+ userDetails.get(sessionManager.KEY_FLATLETTER)+" m." + userDetails.get(sessionManager.KEY_FLAT));
        cityProfile.setText(userDetails.get(sessionManager.KEY_CITY));


    }
}