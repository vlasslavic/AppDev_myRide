package com.example.myride;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myride.models.serviceHistoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceHistory extends AppCompatActivity {
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());

    Button addService;
    ArrayList<serviceHistoryModel> serviceList;
    RecyclerView recyclerView;
    TextView nickname;
    RecyclerHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history);
        addService=findViewById(R.id.addService);
        serviceList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String givenNickname = getIntent().getStringExtra("nickname");
        adapter = new RecyclerHistoryAdapter(serviceList ,this,givenNickname);
        recyclerView.setAdapter(adapter);
        nickname = findViewById(R.id.nickname);


        nickname.setText("Service History: "+givenNickname);
        if (givenNickname != null) {
            reff= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("myGarage").child(givenNickname).child("serviceHistory");
        }

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                serviceHistoryModel service = dataSnapshot.getValue(serviceHistoryModel.class);
                serviceList.add(service);
                }
                adapter.notifyDataSetChanged();
             }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        addService.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddTask.class);
                i.putExtra("nickname", givenNickname);
                startActivity(i);
            }
        });
        displayNav();
    }

    private void displayNav(){
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
}