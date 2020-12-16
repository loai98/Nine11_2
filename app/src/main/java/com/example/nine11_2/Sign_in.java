package com.example.nine11_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Sign_in extends AppCompatActivity {

    DatabaseReference database;

     Button signUpButton , signInButton;

     EditText SSNEditText, passwordEditText;
     String SSN , passsword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        signUpButton =findViewById(R.id.buttonCreateAcc);
        signInButton=findViewById(R.id.buttonLogin);
        SSNEditText=findViewById(R.id.inputIdNum);
        passwordEditText=findViewById(R.id.inputPass);


        database=FirebaseDatabase.getInstance().getReference("User");



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sign_in.this , Sign_up.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SSNEditText.getText().toString().isEmpty()){
                    Toast.makeText(Sign_in.this, "ادخل الرقم الوطني", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(Sign_in.this, "ادخل الرقم السري", Toast.LENGTH_SHORT).show();
                    return;
                }

                passsword=passwordEditText.getText().toString();
                SSN=SSNEditText.getText().toString();

                SignIn();

            }
        });

    }

    private void SignIn() {


        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Waiting");
        progressDialog.show();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                if(snapshot.child(SSN).exists()){
                    User user= snapshot.child(SSN).getValue(User.class);

                    if(user.getPassword().equalsIgnoreCase(passsword))
                    {
                        //Toast.makeText(Sign_in.this, "Log in succesfull", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Sign_in.this, "To "+user.getFullName()+"Activity", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Sign_in.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Sign_in.this, "كلمة السر غير صحيحة", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else{
                    Toast.makeText(Sign_in.this, "الرقم الوطني غير موجود", Toast.LENGTH_SHORT).show();
                    return;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
