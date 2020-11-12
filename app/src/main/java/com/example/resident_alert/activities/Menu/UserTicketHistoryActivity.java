package com.example.resident_alert.activities.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.resident_alert.R;
import com.example.resident_alert.SessionManager;
import com.example.resident_alert.UserHelperClass;
import com.example.resident_alert.adapters.TicketDataAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserTicketHistoryActivity extends AppCompatActivity implements TicketDataAdapter.OnTicketClick {


    private static final String TAG = "kliknięto ";
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ChildEventListener childEventListener;
    private TicketDataAdapter ticketDataAdapter;
    private List<UserHelperClass> dataList;
    private ArrayList<String> keyList;
    String phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ticket_history);

        recyclerView = (RecyclerView) findViewById(R.id.ticketData_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        keyList = new ArrayList<>();

        ticketDataAdapter = new TicketDataAdapter(this,dataList,this);
        recyclerView.setAdapter(ticketDataAdapter);

        //get data from session
        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();
        phone = userDetails.get(sessionManager.KEY_PHONE);

        //reference
        FirebaseDatabase.getInstance().getReference().child("Tickets").orderByChild("phone").equalTo(phone).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                UserHelperClass ticket = snapshot.getValue(UserHelperClass.class);

                String ticketKey = snapshot.getKey();
                keyList.add(ticketKey); // collect key of object

                dataList.add(ticket);
                ticketDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //take key from snapshot, check index and set changes
                UserHelperClass ticket = snapshot.getValue(UserHelperClass.class);
                String ticketKey = snapshot.getKey();
                Integer index = dataList.indexOf(ticketKey);

                dataList.set(index,ticket);
                ticketDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            UserHelperClass ticket = snapshot.getValue(UserHelperClass.class);
                String ticketKey = snapshot.getKey();
                Integer index = dataList.indexOf(ticketKey);

                dataList.remove(ticket);
                ticketDataAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onTicketClick(int position) {
        Log.d(TAG,"Ticket nr " + position);

        //pass values to another activity
        Intent intent = new Intent(this, InfoTicketActivity.class);
        UserHelperClass ticket = dataList.get(position);

        intent.putExtra("submissionDate",ticket.getSubmissionDate());
        intent.putExtra("status",ticket.getStatus());
        intent.putExtra("action",ticket.getAction());
        intent.putExtra("place",ticket.getPlace());
        intent.putExtra("info",ticket.getInfo());

        startActivity(intent);
    }
}
