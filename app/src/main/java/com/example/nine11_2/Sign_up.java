package com.example.nine11_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sign_up extends AppCompatActivity {

    EditText SSNEditText , fullNameEditText , passwordEditText , phoneNumberEditText ;
    Button signUpBtn;
    TextView signInTextView;

    String SSN , fullName , password , phoneNumber;

    User user;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SSNEditText=findViewById(R.id.inputIdNum);
        fullNameEditText=findViewById(R.id.name);
        passwordEditText=findViewById(R.id.inputPass);
        phoneNumberEditText=findViewById(R.id.phoneNumber);

        signUpBtn=findViewById(R.id.buttonSignUp);
        signInTextView=findViewById(R.id.signIn);

        databaseReference= FirebaseDatabase.getInstance().getReference("User");


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!SSNEditText.getText().toString().isEmpty()){
                    SSN = SSNEditText.getText().toString();

                    if(SSN.length()!=10)
                    {
                        Toast.makeText(Sign_up.this, "الرقم الوطني غير صحيح", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(fullNameEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(Sign_up.this, "دخل الاسم الرباعي", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(phoneNumberEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(Sign_up.this, "ادخل رقم الهاتف", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwordEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(Sign_up.this, "ادخل كلمة السر", Toast.LENGTH_SHORT).show();
                    return;
                }

                fullName=fullNameEditText.getText().toString();
                password= passwordEditText.getText().toString();
                phoneNumber=phoneNumberEditText.getText().toString();


              user=new User(phoneNumber , fullName , password);
              user.setSSN(SSN);
               // Toast.makeText(Sign_up.this, user.phoneNumber, Toast.LENGTH_SHORT).show();

                CreatNewUser();


            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void CreatNewUser() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Waiting");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(SSN).exists())
                {
                    progressDialog.dismiss();
                    Toast.makeText(Sign_up.this, "الرقم الوطني مستخدم", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    databaseReference.child(SSN).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){

                                Toast.makeText(Sign_up.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();

                                // to Home activity

                                Toast.makeText(Sign_up.this, "to Home activity...", Toast.LENGTH_SHORT).show();
                            }

                            else
                                Toast.makeText(Sign_up.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
