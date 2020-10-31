package com.example.resident_alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

        Intent intent = getIntent();
       String actionName = intent.getStringExtra("Action");
        String placeName = intent.getStringExtra("Place");
        phone = intent.getStringExtra("phone");

        testText.setText("Usterka w "+placeName+". Rodzaj: "+actionName+ " Zalogowany jako: "+ phone);
        StoreUserData(placeName,actionName);

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