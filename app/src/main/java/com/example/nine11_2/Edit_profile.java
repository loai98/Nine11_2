package com.example.nine11_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;


public class Edit_profile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //    private static final String TAG = Edit_profile.class.getSimpleName();

       DatabaseReference databaseReference;
        private EditText editProfileName , editProfilePhoneNumber ,
                editProfileoldPass,editprofilenewpass;
        ImageView imageMenu ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about_us);

            setTitle("Edit Profile Information");

            imageMenu = findViewById(R.id.imageMenu);
            databaseReference= FirebaseDatabase.getInstance().getReference("User");
            editProfileName = (EditText)findViewById(R.id.et_name);
            editProfilePhoneNumber = (EditText)findViewById(R.id.et_phone);
            editProfileoldPass = (EditText)findViewById(R.id.oldPass);
            editprofilenewpass = (EditText)findViewById(R.id.newPass);

            Button saveEditButton = (Button)findViewById(R.id.btn_up);
            saveEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ProgressDialog progressDialog=new ProgressDialog(Edit_profile.this);
                    progressDialog.setMessage("جار تحديث المعلومات...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    if(!editProfileoldPass.getText().toString().isEmpty())
                    {
                        progressDialog.dismiss();
                    if(!editProfileoldPass.getText().toString().equals(User.password)){
                        progressDialog.dismiss();
                        Toast.makeText(Edit_profile.this, "كلمة المرور الحالية غير صحيحة", Toast.LENGTH_SHORT).show();
                        return ;
                    }}
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Edit_profile.this, "الرجاء ادخال كلمة المرور الحالية لحفظ التعديلات", Toast.LENGTH_SHORT).show();
                        return;
                    }



                    if(!editProfileName.getText().toString().equals(User.fullName) && !editProfileName.getText().toString().isEmpty()){
                        databaseReference.child(User.SSN).child("fullName").setValue(editProfileName.getText().toString());
                        User.fullName= editProfileName.getText().toString();
                        Toast.makeText(Edit_profile.this, "تم تحديث المعلومات", Toast.LENGTH_SHORT).show();
                    }

                    if(!editProfilePhoneNumber.getText().toString().equals(User.phoneNumber) && !editProfilePhoneNumber.getText().toString().isEmpty()){
                        databaseReference.child(User.SSN).child("phoneNumber").setValue(editProfilePhoneNumber.getText().toString());
                        User.phoneNumber= editProfilePhoneNumber.getText().toString();
                        Toast.makeText(Edit_profile.this, "تم تحديث المعلومات", Toast.LENGTH_SHORT).show();
                    }

                    if(!editprofilenewpass.getText().toString().equals(User.password) && !editprofilenewpass.getText().toString().isEmpty()){
                        databaseReference.child(User.SSN).child("password").setValue(editprofilenewpass.getText().toString());
                        User.password= editprofilenewpass.getText().toString();
                        Toast.makeText(Edit_profile.this, "تم تحديث المعلومات", Toast.LENGTH_SHORT).show();
                    }


                    editProfileName.setText("");
                    editProfilePhoneNumber.setText("");
                    editProfileoldPass.setText("");
                    editprofilenewpass.setText("");


                    progressDialog.dismiss();

                }
            });

            imageMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(Edit_profile.this, v);
                    popupMenu.setOnMenuItemClickListener(Edit_profile.this);
                    popupMenu.inflate(R.menu.menu3);
                    popupMenu.show();
                }
            });
        }

    public void finhsh(View view) {
            finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.logOut) {
            SignOut();
            return true;
        }

        if (item.getItemId() == R.id.myReportss) {
            startActivity(new Intent(Edit_profile.this , MyReports.class));
            return true;
        }
        return false;
    }

    private void SignOut() {
        Intent logInIntent = new Intent(Edit_profile.this, Sign_in.class);
        Paper.book().destroy();
        logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInIntent);

    }


}