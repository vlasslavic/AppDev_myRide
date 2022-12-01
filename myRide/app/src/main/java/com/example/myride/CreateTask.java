package com.example.myride;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.myride.models.taskModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreateTask extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private StorageReference storageReference = null;
    private DatabaseReference databaseReference = null;
    private ProgressBar progressBar;

    Button addTaskBtn;
    EditText tasks,location,company;
    taskModel notes = new taskModel();
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        addTaskBtn=findViewById(R.id.createTaskButton);
        tasks=findViewById(R.id.fieldTask);
        location=findViewById(R.id.fieldLocation);
        company=findViewById(R.id.fieldCompany);
        mAuth = FirebaseAuth.getInstance();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("upload/users/"+mAuth.getCurrentUser().getUid());

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                notes.setTask(tasks.getText().toString());
                notes.setServiceLocation(location.getText().toString());
                notes.setCompany(company.getText().toString());
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String taskn = tasks.getText().toString();
        if (TextUtils.isEmpty(taskn)) {
            tasks.setError("Required.");
            valid = false;
        } else {
            tasks.setError(null);
        }

        String locationn = location.getText().toString();
        if (TextUtils.isEmpty(locationn)) {
            location.setError("Required.");
            valid = false;
        } else {
            location.setError(null);
        }

        String companyn = company.getText().toString();
        if (TextUtils.isEmpty(companyn)) {
            company.setError("Required.");
            valid = false;
        } else {
            company.setError(null);
        }
        return valid;
    }
}