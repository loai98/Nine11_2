package com.example.nine11_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    LinearLayout newReportLinearLayout;
    TextView userName;
    String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userName=findViewById(R.id.nameUser);


        newReportLinearLayout =findViewById(R.id.layout1);
        newReportLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this , NewReport.class);
                startActivity(intent);
            }
        });


        try {
            firstName= User.fullName.substring(0 , User.fullName.indexOf(" "));
        }catch (Exception ex){
            firstName=User.fullName;
        }
        userName.setText(firstName);
    }
}