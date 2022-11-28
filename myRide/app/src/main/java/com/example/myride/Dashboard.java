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

import com.example.myride.fragments.addCar;
import com.example.myride.fragments.mainCar;
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

import java.io.Serializable;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference reff;
//    Button addCarBtn;
    userModel currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//        addCarBtn = findViewById(R.id.addCarBtn);
        reff = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
        FragmentManager fm = getSupportFragmentManager();
        mainCar fGarage = (mainCar) fm.findFragmentById(R.id.mainCarContainerView);
        Query mUserReference;
        Bundle bundle = new Bundle();

// set Fragmentclass Arguments
                reff.addValueEventListener(new ValueEventListener() {
                       @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.child("mainCar").getValue()!=null){
                           String mainCar = (String) snapshot.child("mainCar").getValue();
                           bundle.putString("mainCar",  mainCar);
                           mainCar fragobj = new mainCar();
                           fragobj.setArguments(bundle);
                           }
                         }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

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