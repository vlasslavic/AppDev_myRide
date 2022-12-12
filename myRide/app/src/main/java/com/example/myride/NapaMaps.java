package com.example.myride;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NapaMaps extends AppCompatActivity {
    Button call;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napa_maps);
        call = findViewById(R.id.button_call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:5143514210"));
                startActivity(callIntent);
            }

        });
    }
}