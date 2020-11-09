package com.example.resident_alert.adapters;

import com.example.resident_alert.R;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;




public class MyViewHolder extends RecyclerView.ViewHolder {

   public TextView submissionDateTextFB, statusTextFB, actionTextFB, placeTextFB;
   public Button anulujBtn, deleteBtn;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        submissionDateTextFB = itemView.findViewById(R.id.submissionDateTextFB);
        statusTextFB = itemView.findViewById(R.id.statusTextFB);
        actionTextFB = itemView.findViewById(R.id.actionTextFB);
        placeTextFB = itemView.findViewById(R.id.placeTextFB);
        anulujBtn = itemView.findViewById(R.id.anulujBtn);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);

    }
}
