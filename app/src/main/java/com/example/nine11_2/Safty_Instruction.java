package com.example.nine11_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import io.paperdb.Paper;

public class Safty_Instruction extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    ImageView imageMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safty__instruction);

        imageMenu=findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Safty_Instruction.this, view);
                popupMenu.setOnMenuItemClickListener(Safty_Instruction.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();

            }
        });


    }

    public void Finhsh(View view) {
        finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {


        if (menuItem.getItemId() == R.id.logOut) {
            SignOut();
            return true;
        }

        if (menuItem.getItemId() == R.id.myReportss) {
            startActivity(new Intent(Safty_Instruction.this , MyReports.class));
            return true;
        }
        if (menuItem.getItemId() == R.id.editProfile) {
            startActivity(new Intent(Safty_Instruction.this , Edit_profile.class));
            return true;
        }


        return false;
    }

    private void SignOut() {
        Intent logInIntent = new Intent(Safty_Instruction.this, Sign_in.class);
        Paper.book().destroy();
        logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInIntent);
    }
}