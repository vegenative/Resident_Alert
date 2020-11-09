package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resident_alert.SessionManager;
import com.example.resident_alert.UserHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.resident_alert.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CompleteActivity extends AppCompatActivity {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    public String phone;
    public String placeName;
    public String actionName;
    private TextView phoneNameText;
    private TextView placeNameText;
    private TextView actionNameText;
    private EditText infoNameText;
    private Button submitTicket;
    public String infoText;
    public String status = "Rozpoczęte";
    public String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        phoneNameText = findViewById(R.id.phoneNameText);
        placeNameText = findViewById(R.id.placeNameText);
        actionNameText = findViewById(R.id.actionNameText);
        infoNameText = findViewById(R.id.infoNameText);
        submitTicket = findViewById(R.id.submitTicket);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        Intent intent = getIntent();
        placeName = intent.getStringExtra("Place");
        actionName = intent.getStringExtra("Action");


        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();


        phone = userDetails.get(sessionManager.KEY_PHONE);
        phoneNameText.setText(phone);
        placeNameText.setText(placeName);
        actionNameText.setText(actionName);


        submitTicket.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progres_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            infoText = infoNameText.getText().toString();
            StoreUserData(placeName,actionName,infoText, status,currentDate);
            progressDialog.dismiss();

            startActivity(new Intent(this,MenuActivity.class));
        });



    }
    private void StoreUserData(String place, String action, String info, String status, String currentDate){

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Tickets");

        UserHelperClass addNewTicket =
                new UserHelperClass(place,action,info,status, currentDate,phone);


        reference.push().setValue(addNewTicket);
        Toast.makeText(this,"Zgłoszenie zostało wysłane",Toast.LENGTH_SHORT).show();


    }

}