package com.example.myride;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class GoogleMaps extends AppCompatActivity {

    EditText startPoint, destination;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        startPoint = findViewById(R.id.startPoint);
        destination = findViewById(R.id.destination);
        search = findViewById(R.id.searchGoogle);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sp = startPoint.getText().toString().trim();
                String d = destination.getText().toString().trim();
                DisplaySearch(sp, d);
            }



            private void DisplaySearch(String sp, String d) {
                try {
                    // call the installed google maps lib
                    Uri uri = Uri.parse("https://www.google.com/maps/dir/" + sp + "/" + d);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }catch(ActivityNotFoundException e){

                }
            }
        });

    }
}
