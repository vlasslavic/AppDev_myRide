package com.example.myride;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myride.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference reff;
//    Button addCarBtn;
    userModel currentUser;
    TextView textNickname,serviceHistory, fullName, carsNumber;
    Button addCarBtn,myGarageBtn,googleMapsBtn;
    ImageView carImage,napaImg;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addCarBtn = findViewById(R.id.addCarBtn);
        myGarageBtn = findViewById(R.id.garageBtn);
        textNickname = findViewById(R.id.textNickname);
        serviceHistory = findViewById(R.id.serviceHistory);
        fullName = findViewById(R.id.textFullName);
        napaImg = findViewById(R.id.imageViewNapa);
        carsNumber = findViewById(R.id.textCars);
        carImage = findViewById(R.id.imageView8);
        napaImg = findViewById(R.id.imageViewNapa);
        googleMapsBtn = findViewById(R.id.mapsButton);
        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child("uploads").child("users").child(mAuth.getCurrentUser().getUid());
//        StorageReference imagesRef = storageRef.child("uploads").child("users").child(name+".jpg");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//        addCarBtn = findViewById(R.id.addCarBtn);
        reff = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());


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



// Lister for Main Car Card
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("fullName").getValue() != null) {
                    fullName.setText(snapshot.child("fullName").getValue().toString());}
                if(snapshot.child("mainCar").getValue()!=null){
                if (snapshot.child("myGarage").child(snapshot.child("mainCar").getValue().toString()).child("picture").getValue() != null) {
                    carImage.setVisibility(View.VISIBLE);
                    Picasso.get().load((snapshot.child("myGarage").child(snapshot.child("mainCar").getValue().toString()).child("picture").getValue()).toString()).into(carImage);
//                    carImage.setImageDrawable(LoadImageFromWebOperations());
//                    carImageView.setSrc(LoadImageFromWebOperations(snapshot.child("picture").getValue().toString()));}
                }}
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

        serviceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ServiceHistory.class);
                i.putExtra("nickname", textNickname.getText().toString());
                startActivity(i);

            }

        });

        googleMapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // call the installed google maps lib
                    Uri uri = Uri.parse("https://www.google.com/maps/dir/");
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }catch(ActivityNotFoundException e){

                }
            }
        });
        napaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NapaMaps.class);
                startActivity(i);

            }
        });

    }

//                    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflater is the class that converts xml to java Object
//        // this inflator converts the menu resource file to java object and deploy on main activity
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.item_menu, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.nav_logout:
//                mAuth.signOut();
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//        }
//        return true;
//    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is,"srcName");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}