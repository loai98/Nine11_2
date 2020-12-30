package com.example.nine11_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

import io.paperdb.Paper;

public class MyReports extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

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

    ImageView backBtn , imageMenu ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);

        myComplaintState=findViewById(R.id.myComplaint_text);
        backBtn=findViewById(R.id.arrowBack);
        imageMenu = findViewById(R.id.imageMenu);


        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MyReports.this, v);
                popupMenu.setOnMenuItemClickListener(MyReports.this);
                popupMenu.inflate(R.menu.menu2);
                popupMenu.show();

            }
        });




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

      /*  backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


    }

    private void LoadItem() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                for (DataSnapshot s : dataSnapshot.child(User.SSN).child("Complaint").getChildren()) {
                    Complaint complaint = s.getValue(Complaint.class);
                    myComplaints.add(0,complaint);
                    keys.add( 0,s.getRef().getKey());

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

    public void finish(View view) {
        finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.logOut) {
            SignOut();
            return true;
        }
        if (item.getItemId() == R.id.information) {
            startActivity(new Intent(MyReports.this , about_us.class));
            return true;
        }
        if (item.getItemId() == R.id.editProfile) {
            startActivity(new Intent(MyReports.this , Edit_profile.class));
            return true;
        }
        return  false;
    }

    private void SignOut() {

        Intent logInIntent = new Intent(MyReports.this, Sign_in.class);
        Paper.book().destroy();
        logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInIntent);
    }
}
