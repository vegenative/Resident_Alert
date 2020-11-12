package com.example.resident_alert.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resident_alert.R;
import com.example.resident_alert.UserHelperClass;
import com.example.resident_alert.activities.Menu.InfoTicketActivity;
import com.example.resident_alert.activities.Menu.UserTicketHistoryActivity;

import java.util.List;

public class TicketDataAdapter extends RecyclerView.Adapter<TicketDataAdapter.myViewHolder>
{
    private Context context;
    private List<UserHelperClass> dataList;
    private OnTicketClick onTicketClick;

    public TicketDataAdapter(Context context, List<UserHelperClass> dataList, OnTicketClick onTicketClick){
        this.context = context;
        this.dataList = dataList;
        this.onTicketClick = onTicketClick;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ticket,parent,false);
        return new myViewHolder(view, onTicketClick);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        UserHelperClass ticket = dataList.get(position);

        holder.phone.setText(ticket.getPhone());
        holder.day.setText(ticket.getSubmissionDate());
        holder.status.setText(ticket.getStatus());
        holder.action.setText(ticket.getAction());
        holder.place.setText(ticket.getPlace());


    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView phone, day,status,action,place;
        LinearLayout single_ticket;
        OnTicketClick onTicketClick;

        public myViewHolder(@NonNull View itemView, OnTicketClick onTicketClick)
        {
            super(itemView);

            this.onTicketClick = onTicketClick;

            single_ticket =  (LinearLayout) itemView.findViewById(R.id.single_ticket_ll);
            phone = (TextView) itemView.findViewById(R.id.phone_ticket);
            day = (TextView) itemView.findViewById(R.id.data_Ticket);
            status = (TextView) itemView.findViewById(R.id.data_ticket);
            action = (TextView) itemView.findViewById(R.id.action_ticket);
            place = (TextView) itemView.findViewById(R.id.place_ticket);




            single_ticket.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onTicketClick.onTicketClick(getAdapterPosition());

        }
    }
    public interface OnTicketClick{
        void onTicketClick(int position);
    }
}
