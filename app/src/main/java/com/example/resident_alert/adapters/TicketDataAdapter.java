package com.example.resident_alert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resident_alert.R;
import com.example.resident_alert.UserHelperClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class TicketDataAdapter extends RecyclerView.Adapter<TicketDataAdapter.myViewHolder>
{
    private Context context;
    private List<UserHelperClass> dataList;

    public TicketDataAdapter(Context context, List<UserHelperClass> dataList){
        this.context = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ticket,parent,false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        UserHelperClass ticket = dataList.get(position);

        holder.name.setText(ticket.getName());
        holder.surname.setText(ticket.getSurname());
        holder.phone.setText(ticket.getTelephone());
        holder.day.setText(ticket.getSubmissionDate());
        holder.status.setText(ticket.getStatus());
        holder.action.setText(ticket.getAction());
        holder.place.setText(ticket.getPlace());
        holder.info.setText(ticket.getInfo());

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,surname,phone, day,status,action,place,info;
        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_ticket);
            surname = (TextView) itemView.findViewById(R.id.surname_ticket);
            phone = (TextView) itemView.findViewById(R.id.phone_ticket);
            day = (TextView) itemView.findViewById(R.id.data_Ticket);
            status = (TextView) itemView.findViewById(R.id.status_ticket);
            action = (TextView) itemView.findViewById(R.id.action_ticket);
            place = (TextView) itemView.findViewById(R.id.place_ticket);
            info = (TextView) itemView.findViewById(R.id.info_Ticket);



        }
    }
}
