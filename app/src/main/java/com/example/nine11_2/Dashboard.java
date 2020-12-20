package com.example.nine11_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class Dashboard extends AppCompatActivity {
    LinearLayout newReportLinearLayout  , myReports;
    TextView userName;
    String firstName;

    ImageView imageMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imageMenu=findViewById(R.id.imageMenu);
        userName=findViewById(R.id.nameUser);

        myReports=findViewById(R.id.layout2);

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

        myReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this , MyReports.class));
            }
        });

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });
    }

    private void SignOut(){
        Intent logInIntent = new Intent(Dashboard.this, Sign_in.class);
        Paper.book().destroy();
        logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInIntent);

    }
}