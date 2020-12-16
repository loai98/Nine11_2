package com.example.nine11_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DelegatedAdminReceiver;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(MainActivity.this , Sign_in.class);
        startActivity(intent);
        finish();
    }
}
