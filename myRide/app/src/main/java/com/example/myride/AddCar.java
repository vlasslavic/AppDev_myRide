package com.example.myride;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myride.models.garageModel;
import com.example.myride.models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCar extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button addCarBtn;
    EditText make,model,year,registration,color,nickname;
    garageModel car;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        addCarBtn=findViewById(R.id.addCarButton);
        make=findViewById(R.id.fieldMake);
        model=findViewById(R.id.fieldModel);
        year=findViewById(R.id.fieldYear);
        nickname=findViewById(R.id.fieldNickname);
        registration=findViewById(R.id.fieldRegistration);
        color=findViewById(R.id.fieldColor);
        mAuth = FirebaseAuth.getInstance();
        garageModel car = new garageModel();

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

                reff.child(mAuth.getCurrentUser().getUid()).child("myGarage").child(nickname.getText().toString()).setValue(car);
                reff.child(mAuth.getCurrentUser().getUid()).child("mainCar").setValue(nickname.getText().toString());
//                reff.child().setValue(car);
                Toast.makeText(getApplicationContext(),
                        "Data inserted successful", Toast.LENGTH_SHORT).show();
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
}