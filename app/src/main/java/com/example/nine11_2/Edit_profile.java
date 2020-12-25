package com.example.nine11_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Edit_profile extends AppCompatActivity {

        private static final String TAG = Edit_profile.class.getSimpleName();

        private EditText editProfileName;

        private EditText editProfilePhoneNumber;

        private EditText editProfileoldPass;

        private EditText editprofilenewpass;

       private FirebaseAuth.AuthStateListener authStateListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile);

            setTitle("Edit Profile Information");

            editProfileName = (EditText)findViewById(R.id.et_name);
            editProfilePhoneNumber = (EditText)findViewById(R.id.et_phone);
            editProfileoldPass = (EditText)findViewById(R.id.oldPass);
            editprofilenewpass = (EditText)findViewById(R.id.newPass);

            Button saveEditButton = (Button)findViewById(R.id.btn_up);
            saveEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String profileName = editProfileName.getText().toString();
                    String profilePhoneNumber = editProfilePhoneNumber.getText().toString();
                    String profileoldPass = editProfileoldPass.getText().toString();
                    String profilenewpass = editprofilenewpass.getText().toString();

                    // update the user profile information in Firebase database.
                    if(TextUtils.isEmpty(profileName) || TextUtils.isEmpty(profileoldPass) || TextUtils.isEmpty(profilePhoneNumber)
                            || TextUtils.isEmpty(profilenewpass)){
                      // Helper.displayMessageToast(Edit_Profile.this, "All fields must be filled");
                    }

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null) {
                        Intent firebaseUserIntent = new Intent(Edit_profile.this, Sign_in.class);
                        startActivity(firebaseUserIntent);
                        finish();
                    } else {
                        String userId = user.getProviderId();
                        String id = user.getUid();

                      /*  FirebaseUserEntity userEntity = new FirebaseUserEntity(id, profileName, profilePhoneNumber, profileoldPass, profilenewpass);
                        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                        firebaseDatabaseHelper.createUserInFirebaseDatabase(id, userEntity);*/
                        editProfileName.setText("");
                        editProfilePhoneNumber.setText("");
                        editProfileoldPass.setText("");
                        editprofilenewpass.setText("");
                    }
                }
            });
        }
    }