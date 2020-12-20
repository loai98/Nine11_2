package com.example.nine11_2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nine11_2.Model.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {
    //variable
    Animation topAnim, botAnim;
    ImageView logo;
    TextView wel, nameapp;

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //delete state bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //check
        Paper.init(this);
        username= Paper.book().read(Common.USER_KEY);



        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //hooks
        logo = findViewById(R.id.logoId0);
        wel = findViewById(R.id.wlcomId0);
        nameapp = findViewById(R.id.stackId0);

        logo.setAnimation(topAnim);
        nameapp.setAnimation(botAnim);
        wel.setAnimation(botAnim);

        int SPLASH_SCREEN = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              /*  Intent intent = new Intent(MainActivity.this, Sign_in.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logo, "logo_image");
               pairs[1] = new Pair<View, String>(nameapp, "logo_text");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                startActivity(intent);
                }*/

                if(username!=null && !username.isEmpty()){
                    logIn();
                }else
                {
                    startActivity(new Intent(MainActivity.this , Sign_in.class));
                    finish();
                }



            }
        }, SPLASH_SCREEN);







    }

    private void logIn() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.child(username).getValue(User.class);
                Intent intent=new Intent(MainActivity.this , Dashboard.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}