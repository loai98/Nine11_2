package com.example.nine11_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class Dashboard extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    LinearLayout newReportLinearLayout, myReports, mysafty;
    TextView userName;
    String firstName;

    ImageView imageMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imageMenu = findViewById(R.id.imageMenu);
        userName = findViewById(R.id.nameUser);
        myReports = findViewById(R.id.layout2);
        mysafty = findViewById(R.id.layout3);
        mysafty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Safty_Instruction.class);
                startActivity(intent);
            }
        });

        newReportLinearLayout = findViewById(R.id.layout1);
        newReportLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, NewReport.class);
                startActivity(intent);
            }
        });


        try {
            firstName = User.fullName.substring(0, User.fullName.indexOf(" "));
        } catch (Exception ex) {
            firstName = User.fullName;
        }
        userName.setText(firstName);

        myReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, MyReports.class));
            }
        });

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Dashboard.this, v);
                popupMenu.setOnMenuItemClickListener(Dashboard.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();

            }
        });
    }


    private void SignOut() {
        Intent logInIntent = new Intent(Dashboard.this, Sign_in.class);
        Paper.book().destroy();
        logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInIntent);

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.logOut) {
            SignOut();
            return true;
        }

        if (item.getItemId() == R.id.myReportss) {
            startActivity(new Intent(Dashboard.this , MyReports.class));
            return true;
        }
        if (item.getItemId() == R.id.editProfile) {
            startActivity(new Intent(Dashboard.this , Edit_profile.class));
            return true;
        }


        return false;
    }
}

