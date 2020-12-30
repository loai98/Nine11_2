package com.example.nine11_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class Dashboard extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    LinearLayout newReportLinearLayout, myReports, mysafty, urgentLayout;
    TextView userName;
    String firstName;
    FusedLocationProviderClient fusedLocationClient;
    ImageView imageMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        urgentLayout = findViewById(R.id.layout4);
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

        if (ActivityCompat.checkSelfPermission(Dashboard.this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(Dashboard.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 100 );
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

        if (item.getItemId() == R.id.information) {
            startActivity(new Intent(Dashboard.this , about_us.class));
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


    public void Urgent(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("أمر مستعجل !");
        builder.setMessage("هل تريد الإبلاغ حالا؟" + "\n"+"سيتم ارسال معلموماتك الشخصية وموقعك الجغرافي للجهات المعنية");
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlaceUrgent();


            }
        });

        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(Dashboard.this, "NO Click", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNeutralButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Toast.makeText(Dashboard.this, "cancel", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                PlaceUrgent();


    }

   void PlaceUrgent(){

       if (ActivityCompat.checkSelfPermission(Dashboard.this , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

           final ProgressDialog progressDialog=new ProgressDialog(this);
           progressDialog.setMessage( "جار ارسال البلاغ..." +"\n"+"الرجاء التأكد من تشغيل الموقع");
           progressDialog.show();
         fusedLocationClient.getLastLocation()
                   .addOnSuccessListener(Dashboard.this, new OnSuccessListener<Location>() {
                       @Override
                       public void onSuccess(Location location) {
                           if (location != null) {
                               Address address = new Address(Double.toString(location.getLatitude()) , Double.toString(location.getLongitude()));
                               UrgentReport urgentReport = new UrgentReport(User.fullName,User.phoneNumber , address);
                              DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UrgentComplaint");
                               databaseReference.child(User.SSN).push().setValue(urgentReport).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Toast.makeText(Dashboard.this, "تم ارسال البلاغ", Toast.LENGTH_SHORT).show();
                                       progressDialog.dismiss();
                                   }
                               });
                           }
                       }
                   });

       }else{
           ActivityCompat.requestPermissions(Dashboard.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 100 );
       }

    }

}

