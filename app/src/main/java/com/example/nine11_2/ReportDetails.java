package com.example.nine11_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.nine11_2.MyReports.myComplaints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ReportDetails extends AppCompatActivity {

    TextView reportTypeTextView , reportDescriptionTextView,dateTextView;
    Button deleteBtn , locationBtn;
    ImageView reportImage;

    int position ;
    String key ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        position=getIntent().getIntExtra("Position" , -1);
        key=getIntent().getStringExtra("Key");

        if(position==-1 || key==null)
        {
            finish();
            Toast.makeText(this, "somthing wrong", Toast.LENGTH_SHORT).show();
        }

        reportDescriptionTextView=findViewById(R.id.reportDetails_description);
        reportTypeTextView=findViewById(R.id.reportDetails_title);
        dateTextView=findViewById(R.id.reportDetails_date);
        deleteBtn=findViewById(R.id.btnDelete);
        locationBtn=findViewById(R.id.btnLoc);
        reportImage=findViewById(R.id.reportDetails_image);

        SetValues();

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("الرجاء الانتضار");

        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressDialog.show();

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User");
                databaseReference.child(User.SSN).child("Complaint").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ReportDetails.this, "تم حذف البلاغ", Toast.LENGTH_SHORT).show();
                        myComplaints.remove(position);
                        progressDialog.dismiss();
                        finish();
                    }
                });


            }
        });






    }

    private void SetValues() {
        reportDescriptionTextView.setText(myComplaints.get(position).getDiscreption());
        reportTypeTextView.setText(myComplaints.get(position).getType());
        dateTextView.setText(myComplaints.get(position).getDate());
        Picasso.get().load(myComplaints.get(position).getImage()).into(reportImage);

    }
}