package com.example.nine11_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class NewReport extends AppCompatActivity {

    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button senButton ;
    ImageView addImageBtn ,selectLocationBtn;
    EditText typeEditText , discreptionEditText;
    String department =null , city=null;
    Complaint complaint;
    int IMAGE_REQUEST_CODE = 101 , LOCATION_REQUEST_CODE = 201;;
    Uri imageuri;
    TextView textAddImage , selectLocationTextView;

    Address address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        senButton=findViewById(R.id.buttonSend);
        typeEditText=findViewById(R.id.inputTypeReport);
        discreptionEditText=findViewById(R.id.inputDescReport);
        addImageBtn=findViewById(R.id.addImage);
        textAddImage=findViewById(R.id.addImageText);
        selectLocationBtn =findViewById(R.id.selectLocation);
        selectLocationTextView=findViewById(R.id.selectLocationTextView);

        
        Spinner typeSpinner = findViewById(R.id.inputNameReport);
        final ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.spinner_type_item ,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //Toast.makeText(NewReport.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if(position!=0)
                department = parent.getSelectedItem().toString();
                else
                    department=null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner citySpinner = findViewById(R.id.inputNameCity);
        ArrayAdapter<CharSequence> cityAdapter =ArrayAdapter.createFromResource(this, R.array.spinner_city_item ,android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                    city = parent.getSelectedItem().toString();
                else
                    city=null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        senButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(department==null) {
                    Toast.makeText(NewReport.this, "اختر القسم", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(city==null) {
                    Toast.makeText(NewReport.this, "اختر المدينة", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(typeEditText.getText().toString().isEmpty()){
                    Toast.makeText(NewReport.this, "ادخل نوع البلاغ", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(discreptionEditText.getText().toString().isEmpty()){
                    Toast.makeText(NewReport.this, "ادخل الوصف", Toast.LENGTH_SHORT).show();
                    return ;
                }

                Calendar calendar = Calendar.getInstance();
                String date = DateFormat.getDateInstance().format(calendar.getTime());

                if(address==null){
                    Toast.makeText(NewReport.this, "الرجاء اضافة الموقع", Toast.LENGTH_SHORT).show();
                    return;
                }

                complaint=new Complaint(department ,
                        typeEditText.getText().toString(),
                       discreptionEditText.getText().toString(),
                        city,
                        "noImage",
                        date   ,
                        address);

                if(imageuri!=null){
                    UploadImage();
                }else
                PlaceReport();


            }
        });
        
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        textAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

        selectLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_location();
            }
        });






    }

    private void Select_location() {
        Intent intent = new Intent(NewReport.this, SelectLocation.class);
        startActivityForResult(intent, LOCATION_REQUEST_CODE);

    }

    private void UploadImage() {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.show();

        String imageName = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("images/" + imageName);
        imageFolder.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mDialog.dismiss();
                        imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {

                                complaint.setImage(uri.toString());
                                PlaceReport();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mDialog.dismiss();
                Toast.makeText(NewReport.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                  /*  double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploading...  " + (int) progress + "%");*/
                mDialog.setMessage("Please wait...");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data.getData() != null) {
            imageuri = data.getData();
            textAddImage.setText("تم اضافة الصورة");

        }

        if(requestCode==LOCATION_REQUEST_CODE && resultCode == RESULT_OK
                    ){

            address=new Address();
            address.setLat(data.getStringExtra("latitude"));
            address.setLon(data.getStringExtra("longitude"));
            selectLocationTextView.setText("تم تحديد الموقع");


        }

    }

    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST_CODE);
    }

    private void PlaceReport() {
        complaint.setUser(new User(User.phoneNumber , User.fullName));
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Waiting");
        progressDialog.show();

        databaseReference.child("Complaint").child(department).child(User.SSN).push().setValue(complaint).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(NewReport.this, "تم ارسال البلاغ بنجاح", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
