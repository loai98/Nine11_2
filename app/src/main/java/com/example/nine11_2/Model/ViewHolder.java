package com.example.nine11_2.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nine11_2.R;
import static com.example.nine11_2.Model.Adapter.listener;
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ImageView imageView;
    TextView titleTextView ,dateTextView , department;



    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        imageView  =itemView.findViewById(R.id.list_item_image);
        titleTextView  =itemView.findViewById(R.id.list_item_title);
        dateTextView  =itemView.findViewById(R.id.list_item_date);
        department  =itemView.findViewById(R.id.type);
    }


    @Override
    public void onClick(View v) {

       listener.onClick(v , getAdapterPosition());
    }
}

