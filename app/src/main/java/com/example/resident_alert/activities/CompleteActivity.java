package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;

public class CompleteActivity extends AppCompatActivity {
        TextView testText;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    public String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        testText = findViewById(R.id.testText);



        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();

        String name = userDetails.get(sessionManager.KEY_NAME);
        String surname = userDetails.get(sessionManager.KEY_SURNAME);
        phone = userDetails.get(sessionManager.KEY_PHONE);
        String email = userDetails.get(sessionManager.KEY_EMAIL);

        testText.setText("Usterka w "+name+ " Zalogowany jako: "+ phone);
        StoreUserData(name,email);

    }
    private void StoreUserData(String place, String action){

//        Query checkUserTelephone = FirebaseDatabase.getInstance().getReference("Users").orderByChild("telephone").equalTo(phone);
//
//        checkUserTelephone.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String phoneCurrentuser = snapshot.child(fullPhone).child("telephone").getValue(String.class);
//                    int ticketNumber = snapshot.child(phone).child("tickets").
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(CompleteActivity.this,"Coś poszło nie tak",Toast.LENGTH_SHORT).show();
//            }
//        });







        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users/"+phone);

        UserHelperClass addNewTicket =
                new UserHelperClass(place,action);


        reference.child("tickets").push().setValue(addNewTicket);



    }

}