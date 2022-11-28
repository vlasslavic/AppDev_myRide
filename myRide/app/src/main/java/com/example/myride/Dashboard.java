package com.example.myride;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.myride.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference reff;
//    Button addCarBtn;
    userModel currentUser;
    TextView textNickname, fullName, carsNumber;
    Button addCarBtn,myGarageBtn;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addCarBtn = findViewById(R.id.addCarBtn);
        myGarageBtn = findViewById(R.id.garageBtn);
        textNickname = findViewById(R.id.textNickname);
        fullName = findViewById(R.id.textFullName);
        carsNumber = findViewById(R.id.textCars);
        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child("uploads").child("users").child(mAuth.getCurrentUser().getUid());
//        StorageReference imagesRef = storageRef.child("uploads").child("users").child(name+".jpg");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//        addCarBtn = findViewById(R.id.addCarBtn);
        reff = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());


// Lister for Main Car Card
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("fullName").getValue() != null) {
                    fullName.setText(snapshot.child("fullName").getValue().toString());}
                if(snapshot.child("myGarage").getValue() != null){
                    String count = String.valueOf((snapshot.child("myGarage").getChildrenCount()));
                    if(count.equals("1")){
                        carsNumber.setText((snapshot.child("myGarage").getChildrenCount())+" car");
                    }else{
                        carsNumber.setText((snapshot.child("myGarage").getChildrenCount())+" cars");
                    }
                }

                if (snapshot.child("mainCar").getValue() != null) {
                    name = snapshot.child("mainCar").getValue().toString();
                    textNickname.setText(name);
                    textNickname.setVisibility(View.VISIBLE);
                } else {
                    textNickname.setVisibility(View.INVISIBLE);
                    addCarBtn.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddCar.class);
                startActivity(i);
            }

        });

        myGarageBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyGarage.class);
                startActivity(i);
            }

        });

    }

                    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflater is the class that converts xml to java Object
        // this inflator converts the menu resource file to java object and deploy on main activity

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_logout:
                mAuth.signOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
        }
        return true;
    }
}