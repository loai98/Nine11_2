package com.example.nine11_2.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nine11_2.Complaint;
import com.example.nine11_2.R;
import com.example.nine11_2.RecylclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {


    public static RecylclerViewClickListener listener;
    ArrayList<Complaint>complaints ;
    public Adapter(){}

    public Adapter(ArrayList<Complaint> complaints , RecylclerViewClickListener listener) {
        this.complaints = complaints;
        this.listener =listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.titleTextView.setText(complaints.get(position).getType());
        holder.dateTextView.setText(complaints.get(position).getDate());
        holder.department.setText(complaints.get(position).getDepartment());
        //Picasso.with().load(donations.get(position).getImage()).into(holder.imageView);
        Picasso.get().load(complaints.get(position).getImage()).into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return complaints.size();
    }



}
