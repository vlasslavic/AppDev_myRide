package com.example.myride;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myride.models.userModel;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText name;
    Button accButton;
    DatabaseReference reff;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    userModel user;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.fieldName);
        accButton = findViewById(R.id.nameCreateAccountButton);
        userModel user = new userModel();


        reff = FirebaseDatabase.getInstance().getReference().child("users");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    maxid = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int ageEmployee = Integer.parseInt(age.getText().toString().trim());
//                float height = Float.parseFloat(ht.getText().toString().trim());
//                Long ph = Long.parseLong(phone.getText().toString().trim());
                user.setFullName(name.getText().toString());
                user.setEmail(mAuth.getCurrentUser().getEmail());

                reff.child(mAuth.getCurrentUser().getUid()).setValue(user);
                Toast.makeText(getApplicationContext(),
                        "data inserted successful", Toast.LENGTH_SHORT).show();
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

    // write a method to initiate an action when click the items on the menu bar


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_logout:


                mAuth.signOut();
                Intent i = new Intent(CreateProfile.this, MainActivity.class);
                startActivity(i);
        }


        return true;





    }
}