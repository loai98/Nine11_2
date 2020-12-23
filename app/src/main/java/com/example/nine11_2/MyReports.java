package com.example.nine11_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nine11_2.Model.Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyReports extends AppCompatActivity {

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (myComplaints.size()==0)
            myComplaintState.setText("لا يوجد بلاغات");
        else
            myComplaintState.setText("");
        adapter.notifyDataSetChanged();
    }

    RecyclerView recyclerView;
Adapter adapter;
public static ArrayList<Complaint>myComplaints;
RecylclerViewClickListener listener;
DatabaseReference databaseReference;

TextView myComplaintState;
    ArrayList<String>keys;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);

        myComplaintState=findViewById(R.id.myComplaint_text);

        databaseReference= FirebaseDatabase.getInstance().getReference("User");

       listener=new RecylclerViewClickListener() {
           @Override
           public void onClick(View view, int position) {
               Intent intent = new Intent(MyReports.this , ReportDetails.class);
               intent.putExtra("Key", keys.get(position));
               intent.putExtra("Position", position);
               startActivity(intent);           }
       };
        myComplaints=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        adapter=new Adapter(myComplaints , listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        keys=new ArrayList<>();
        LoadItem();
        recyclerView.setAdapter(adapter);


    }

    private void LoadItem() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                for (DataSnapshot s : dataSnapshot.child(User.SSN).child("Complaint").getChildren()) {
                    Complaint complaint = s.getValue(Complaint.class);
                    myComplaints.add(complaint);
                    keys.add(s.getRef().getKey());

                    if (myComplaints.size()==0)
                        myComplaintState.setText("لا يوجد بلاغات");
                    else
                        myComplaintState.setText("");
                }


                progressBar.setVisibility(View.INVISIBLE);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
