package com.example.myride;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myride.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Objects;
import java.util.UUID;

public class MyProfile extends AppCompatActivity {
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
    EditText fullName, oldPass, newPass;
    ImageView profilePic;
    Button uploadBtn, saveBtn,savePassBtn;

    private Uri filePath = null;
    private StorageReference storageReference = null;
    private DatabaseReference databaseReference = null;
    private ProgressBar progressBar;
    private final int PICK_IMAGE_GALLERY_CODE = 78;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 12345;
    private final int CAMERA_PICTURE_REQUEST_CODE = 56789;
    userModel user = new userModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        fullName=findViewById(R.id.fullNameField);
        profilePic = findViewById(R.id.imageView99);
        progressBar = findViewById(R.id.progressBar);
        saveBtn= findViewById(R.id.saveButton);
        uploadBtn= findViewById(R.id.uploadButton);
        savePassBtn=findViewById(R.id.changePassButton);
        oldPass=findViewById(R.id.passwordField);
        newPass=findViewById(R.id.newPasswordField);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference().child("upload/users/"+mAuth.getCurrentUser().getUid());

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("fullName").getValue() != null) {
                    fullName.setText(snapshot.child("fullName").getValue().toString());}

                if (snapshot.child("profilePicUrl").getValue() != null) {
                    profilePic.setVisibility(View.VISIBLE);
                        Picasso.get().load(snapshot.child("profilePicUrl").getValue().toString()).into(profilePic);
//                    carImage.setImageDrawable(LoadImageFromWebOperations());
//                    carImageView.setSrc(LoadImageFromWebOperations(snapshot.child("picture").getValue().toString()));}
                }


//                if (snapshot.child("mainCar").getValue() != null) {
//                    name = snapshot.child("mainCar").getValue().toString();
//                    textNickname.setText(name);
//                    textNickname.setVisibility(View.VISIBLE);
//                } else {
//                    textNickname.setVisibility(View.INVISIBLE);
//                    addCarBtn.setVisibility(View.VISIBLE);
//                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showImageSelectedDialog();
//                uploadImage();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        savePassBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changePass(oldPass.getText().toString(),newPass.getText().toString());
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    if (!validateForm()) {
                        return;
                    }

                    user.setFullName(fullName.getText().toString());

                //Check what fields were changed and update
                    if(user.getFullName()!=null){
                    reff.child("fullName").setValue(user.getFullName());}
                    if(user.getProfilePicUrl()!=null){
                    reff.child("profilePicUrl").setValue(user.getProfilePicUrl());}


                    Toast.makeText(getApplicationContext(),
                            "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                    i.putExtra("key", mAuth.getCurrentUser());
                    startActivity(i);
                }

        });

                BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),MyProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
//                        startActivity(new Intent(getApplicationContext(),GoogleMaps.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_logout:
                        mAuth.signOut();
                        Toast.makeText(getApplicationContext(),
                                "You are logged out.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String maken = fullName.getText().toString();
        if (TextUtils.isEmpty(maken)) {
            fullName.setError("Required.");
            valid = false;
        } else {
            fullName.setError(null);
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
                            user.setProfilePicUrl(uri.toString());

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
                    Toast.makeText(MyProfile.this, "Image uploaded failed", Toast.LENGTH_SHORT).show();
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
        if(ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MyProfile.this, new String[] {
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
                profilePic.setImageBitmap(bitmap);
            }catch (Exception e) {

            }
        } else if(requestCode == CAMERA_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap  = (Bitmap)extras.get("data");
            profilePic.setImageBitmap(bitmap);
            filePath =getImageUri(getApplicationContext(), bitmap);
        }

    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);
    }

    private void changePass(String oldPass, String newPass){


// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(Objects.requireNonNull(mUser.getEmail()), oldPass);

        mUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MyProfile.this, "Password updated ", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(MyProfile.this, "Error password not updated ", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MyProfile.this, "Error: Please provide a correct current password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}