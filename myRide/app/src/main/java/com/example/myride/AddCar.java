package com.example.myride;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import com.example.myride.models.garageModel;
import com.example.myride.models.userModel;
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

public class AddCar extends AppCompatActivity {
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
    Button addCarBtn;
    EditText make,model,year,registration,color,nickname;
    garageModel car= new garageModel();
    DatabaseReference reff, newReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        title = findViewById(R.id.textView);
        addCarBtn=findViewById(R.id.addCarButton);
        make=findViewById(R.id.fieldMake);
        model=findViewById(R.id.fieldModel);
        year=findViewById(R.id.fieldYear);
        nickname=findViewById(R.id.fieldNickname);
        registration=findViewById(R.id.fieldRegistration);
        color=findViewById(R.id.fieldColor);
        mAuth = FirebaseAuth.getInstance();
        String messageToast = "Vehicle added successfully";

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference().child("upload/users/"+mAuth.getCurrentUser().getUid());



        String editNickname = getIntent().getStringExtra("nickname");
        if (editNickname != null) {
            title.setText("Edit Vehicle");
            addCarBtn.setText("Save Vehicle");
            messageToast = "Vehicle updated successfully";
            newReff = reff.child(mAuth.getCurrentUser().getUid()).child("myGarage").child(editNickname);
            newReff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("make").getValue() != null) {
                        make.setText(snapshot.child("make").getValue().toString());}
                    if (snapshot.child("model").getValue() != null) {
                        model.setText(snapshot.child("model").getValue().toString());}
                    if (snapshot.child("year").getValue() != null) {
                        year.setText(snapshot.child("year").getValue().toString());}
                    if (snapshot.child("registration").getValue() != null) {
                        registration.setText(snapshot.child("registration").getValue().toString());}
                    if (snapshot.child("color").getValue() != null) {
                        color.setText(snapshot.child("color").getValue().toString());}
                    if (snapshot.child("picture").getValue() != null) {
                        Picasso.get().load((snapshot.child("picture").getValue()).toString()).into(imagePreviw);
                        }
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
        Button selectButton = findViewById(R.id.selectButton);
        Button uploadButton = findViewById(R.id.uploadButton);
        imagePreviw = findViewById(R.id.imagePreview);
        progressBar = findViewById(R.id.progressBar);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectedDialog();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        String finalMessageToast = messageToast;
        addCarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (!validateForm()) {
                    return;
                }

                car.setMake(make.getText().toString());
                car.setModel(model.getText().toString());
                car.setYear(year.getText().toString());
                car.setRegistration(registration.getText().toString());
                car.setColor(color.getText().toString());
                car.setNickname(nickname.getText().toString());
//                uploadImage();

                reff.child(mAuth.getCurrentUser().getUid()).child("myGarage").child(nickname.getText().toString()).setValue(car);

                if(reff.child(mAuth.getCurrentUser().getUid()).child("mainCar").toString().isEmpty()) {
                    reff.child(mAuth.getCurrentUser().getUid()).child("mainCar").setValue(nickname.getText().toString());
                }


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

        String maken = make.getText().toString();
        if (TextUtils.isEmpty(maken)) {
            make.setError("Required.");
            valid = false;
        } else {
            make.setError(null);
        }

        String modeln = model.getText().toString();
        if (TextUtils.isEmpty(modeln)) {
            model.setError("Required.");
            valid = false;
        } else {
            model.setError(null);
        }

        String yearn = year.getText().toString();
        if (TextUtils.isEmpty(yearn)) {
            year.setError("Required.");
            valid = false;
        } else {
            year.setError(null);
        }

        String nickn = nickname.getText().toString();
        if (TextUtils.isEmpty(nickn)) {
            nickname.setError("Required.");
            valid = false;
        } else {
            nickname.setError(null);
        }

        return valid;
    }

    private void uploadImage() {
        if(filePath != null) {
            progressBar.setVisibility(View.VISIBLE);
            StorageReference ref = storageReference.child( UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            car.setPicture(uri.toString());

//                           databaseReference.push().setValue(uri.toString());
                            Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddCar.this, "Image uploaded failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void showImageSelectedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setMessage("Please select an option");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkCameraPermission();
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectFromGallery();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(AddCar.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(AddCar.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(AddCar.this, new String[] {
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[1] ==PackageManager.PERMISSION_GRANTED){
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_PICTURE_REQUEST_CODE);
        }
    }

    private void selectFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  ==  PICK_IMAGE_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            if(data == null || data.getData() == null)
                return;

            try {
                filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagePreviw.setImageBitmap(bitmap);
            }catch (Exception e) {

            }
        } else if(requestCode == CAMERA_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap  = (Bitmap)extras.get("data");
            imagePreviw.setImageBitmap(bitmap);
            filePath =getImageUri(getApplicationContext(), bitmap);
        }

    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);
    }

}