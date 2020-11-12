package com.example.resident_alert.activities.Menu;

import com.example.resident_alert.SessionManager;
import com.example.resident_alert.UserHelperClass;
import com.example.resident_alert.adapters.MyViewHolder;
import com.example.resident_alert.network.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.resident_alert.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    DatabaseReference ref;

    private FirebaseRecyclerOptions<model> options;
    private FirebaseRecyclerAdapter<model, MyViewHolder> adapter;
    public String phone;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progres_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USERSESSION);
        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();


        phone = userDetails.get(sessionManager.KEY_PHONE);

        ref = FirebaseDatabase.getInstance().getReference().child("Tickets");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<model>().setQuery(ref,model.class).build();
        adapter= new FirebaseRecyclerAdapter<model, MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull model model) {

                    holder.submissionDateTextFB.setText(model.getSubmissionDate());
                    holder.statusTextFB.setText(model.getStatus());
                    holder.actionTextFB.setText(model.getAction());
                    holder.placeTextFB.setText(model.getPhone());
                    String key = getRef(position).getKey();
                    holder.anulujBtn.setOnClickListener(v -> {

                        ref.child(key).child("status").setValue("Anulowane");

                    });

                    holder.deleteBtn.setOnClickListener(v -> {
                        ref.child(key).removeValue();
                    });
                    
            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout,parent,false);


                return new MyViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();

    }
}