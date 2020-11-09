package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.resident_alert.R;
import com.example.resident_alert.UserHelperClass;
import com.example.resident_alert.adapters.TicketDataAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class WorkersAlertPanelActivity extends AppCompatActivity {


    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ChildEventListener childEventListener;
    private TicketDataAdapter ticketDataAdapter;
    private List<UserHelperClass> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_alert_panel);

        recyclerView = (RecyclerView) findViewById(R.id.ticketData_rv) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();

        ticketDataAdapter = new TicketDataAdapter(this,dataList);
        recyclerView.setAdapter(ticketDataAdapter);

        //reference
        /*
        FirebaseDatabase.getInstance().getReference().child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    UserHelperClass ticket = snapshot1.getValue(UserHelperClass.class);
                    dataList.add(ticket);

                }

                ticketDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         */


        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    UserHelperClass ticket = snapshot1.getValue(UserHelperClass.class);
                    dataList.add(ticket);

                }

                ticketDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*FirebaseRecyclerOptions<UserHelperClass> options =
                new FirebaseRecyclerOptions.Builder<UserHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), UserHelperClass.class)
                        .build();
                        
         */




        }

    @Override
    protected void onStart() {
        super.onStart();

    }

}
