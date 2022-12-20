package com.example.myride;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myride.models.garageModel;
import com.example.myride.models.serviceHistoryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class AddTask extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private StorageReference storageReference = null;
    private DatabaseReference databaseReference = null;
    private ProgressBar progressBar;
    private ImageView imagePreviw;
    private Uri filePath = null;

    private final int PICK_IMAGE_GALLERY_CODE = 78;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 12345;
    private final int CAMERA_PICTURE_REQUEST_CODE = 56789;

    TextView title;
    Button addTaskButton;
    EditText serviceDetails,shopName,serviceStatus,odometer,nickname;
    serviceHistoryModel task= new serviceHistoryModel();
    DatabaseReference reff, newReff, newReff2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        title = findViewById(R.id.textView);
        addTaskButton=findViewById(R.id.addTaskButton);
        serviceDetails=findViewById(R.id.serviceDetails);
        shopName=findViewById(R.id.shopName);
        serviceStatus=findViewById(R.id.serviceStatus);
        odometer=findViewById(R.id.odometer);
        nickname=findViewById(R.id.nickname);

        mAuth = FirebaseAuth.getInstance();
        String messageToast = "Task added successfully";

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference().child("upload/users/"+mAuth.getCurrentUser().getUid());



        String editNickname = getIntent().getStringExtra("nickname");
        if (editNickname != null) {
//            title.setText("Edit Vehicle");
//            addTaskButton.setText("Save Vehicle");
//            messageToast = "Vehicle updated successfully";
            newReff = reff.child(mAuth.getCurrentUser().getUid()).child("myGarage").child(editNickname);
            newReff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.child("serviceDetails").getValue() != null) {
//                        serviceDetails.setText(snapshot.child("serviceDetails").getValue().toString());}
//                    if (snapshot.child("shopName").getValue() != null) {
//                        shopName.setText(snapshot.child("shopName").getValue().toString());}
//                    if (snapshot.child("serviceStatus").getValue() != null) {
//                        serviceStatus.setText(snapshot.child("serviceStatus").getValue().toString());}
//                    if (snapshot.child("odometer").getValue() != null) {
//                        odometer.setText(snapshot.child("odometer").getValue().toString());}
                    if (snapshot.child("nickname").getValue() != null) {
                        nickname.setText(snapshot.child("nickname").getValue().toString());}
                    else {
                        Toast.makeText(getApplicationContext(), "Sorry we couldn't find your car, please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        String serviceID = getIntent().getStringExtra("serviceID");
        if (serviceID != null) {
            title.setText("Edit Task");
            addTaskButton.setText("Save Task");
            messageToast = "Task updated successfully";
            newReff2 = reff.child(mAuth.getCurrentUser().getUid()).child("myGarage").child(editNickname).child("serviceHistory").child(serviceID);
            newReff2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("serviceDetails").getValue() != null) {
                        serviceDetails.setText(snapshot.child("serviceDetails").getValue().toString());}
                    if (snapshot.child("shopName").getValue() != null) {
                        shopName.setText(snapshot.child("shopName").getValue().toString());}
                    if (snapshot.child("serviceStatus").getValue() != null) {
                        serviceStatus.setText(snapshot.child("serviceStatus").getValue().toString());}
                    if (snapshot.child("odometer").getValue() != null) {
                        odometer.setText(snapshot.child("odometer").getValue().toString());}
                    if (snapshot.child("nickname").getValue() != null) {
                        nickname.setText(snapshot.child("nickname").getValue().toString());}
                    else {
                        Toast.makeText(getApplicationContext(), "Sorry we couldn't find your car, please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }




//        Upload Image
//        Button selectButton = findViewById(R.id.selectButton);
//        Button uploadButton = findViewById(R.id.uploadButton);
//        imagePreviw = findViewById(R.id.imagePreview);
        progressBar = findViewById(R.id.progressBar);

//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showImageSelectedDialog();
//            }
//        });
//
//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadImage();
//            }
//        });

        String finalMessageToast = messageToast;
        addTaskButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (!validateForm()) {
                    return;
                }

                task.setServiceDetails(serviceDetails.getText().toString());
                task.setServiceStatus(serviceStatus.getText().toString());
                task.setOdometer(odometer.getText().toString());
                task.setShopName(shopName.getText().toString());
                task.setNickname(nickname.getText().toString());
//                uploadImage();
                if(serviceID!=null){
                    task.setServiceID(serviceID);
                }else{
                    task.setServiceID(newReff.push().getKey());
                }
                newReff.child("serviceHistory").child(task.getServiceID()).setValue(task);

//                if(reff.child(mAuth.getCurrentUser().getUid()).child("mainCar").toString().isEmpty()) {
//                    reff.child(mAuth.getCurrentUser().getUid()).child("mainCar").setValue(nickname.getText().toString());
//                }


//                reff.child().setValue(car);
                Toast.makeText(getApplicationContext(),
                        finalMessageToast.toString(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                i.putExtra("key", mAuth.getCurrentUser());
                startActivity(i);
            }
        });

    }
    private boolean validateForm() {
        boolean valid = true;

        String maken = serviceDetails.getText().toString();
        if (TextUtils.isEmpty(maken)) {
            serviceDetails.setError("Required.");
            valid = false;
        } else {
            serviceDetails.setError(null);
        }

        String modeln = serviceStatus.getText().toString();
        if (TextUtils.isEmpty(modeln)) {
            serviceStatus.setError("Required.");
            valid = false;
        } else {
            serviceStatus.setError(null);
        }

        String yearn = nickname.getText().toString();
        if (TextUtils.isEmpty(yearn)) {
            nickname.setError("Required.");
            valid = false;
        } else {
            nickname.setError(null);
        }


        return valid;
    }


}