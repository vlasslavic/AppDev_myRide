package com.example.myride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reff;
    Button registerButton, loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButton=findViewById(R.id.registerButton);
        loginButton=findViewById(R.id.loginButton);


//      Checks if User is already logged in and redirects
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

//      Checks if user created the profile if true redirects to Dashboard else to CreateProfile
                reff = FirebaseDatabase.getInstance().getReference().child("users");
                reff.child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Intent i = new Intent(getApplicationContext(), CreateProfile.class);
                            i.putExtra("key", currentUser);
                            startActivity(i);
                        }
                        else {
                            if((task.getResult().getValue())!=null){
                                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                i.putExtra("key", currentUser);
                                startActivity(i);
                            }else {
                                Intent i = new Intent(getApplicationContext(), CreateProfile.class);
                                i.putExtra("key", currentUser);
                                startActivity(i);
                            }
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                    }
                });
        }

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i); }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i); }
        });
    }
}