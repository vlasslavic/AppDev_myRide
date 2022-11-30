package com.example.myride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    private FirebaseAuth mAuth;
    DatabaseReference reff;
    EditText fieldEmail, fieldPassword;
    Button loginButton;
    private Object MultiFactorSignInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldPassword = findViewById(R.id.fieldPassword);
        loginButton = findViewById(R.id.loginButton);
        // Initialize Firebase Auth
        btSignIn=findViewById(R.id.bt_sign_in);
        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        // Initialize sign in options
        // the client-id is copied form
        // google-services.json file
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(LoginActivity.this
                ,googleSignInOptions);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize sign in intent
                Intent intent=googleSignInClient.getSignInIntent();
                // Start activity for result
                startActivityForResult(intent,100);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = fieldEmail.getText().toString();
                String password = fieldPassword.getText().toString();
                logIn(email, password);
            }
        });


        // Initialize firebase auth
        firebaseAuth=FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        // Check condition
        if(firebaseUser!=null)
        {
            // When user already sign in
            // redirect to profile activity
            startActivity(new Intent(LoginActivity.this,Dashboard.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if(requestCode==100)
        {
            // When request code is equal to 100
            // Initialize task
            Task<GoogleSignInAccount> signInAccountTask=GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            // check condition
            if(signInAccountTask.isSuccessful())
            {
                // When google sign in successful
                // Initialize string
                String s="Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount=signInAccountTask
                            .getResult(ApiException.class);
                    // Check condition
                    if(googleSignInAccount!=null)
                    {
                        // When sign in account is not equal to null
                        // Initialize auth credential
                        AuthCredential authCredential= GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        ,null);
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Check condition
                                        if(task.isSuccessful())
                                        {
                                            // When task is successful
                                            // Redirect to profile activity
                                            startActivity(new Intent(LoginActivity.this
                                                    ,Dashboard.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            // Display Toast
                                            displayToast("Firebase authentication successful");
                                        }
                                        else
                                        {
                                            // When task is unsuccessful
                                            // Display Toast
                                            displayToast("Authentication Failed :"+task.getException()
                                                    .getMessage());
                                        }
                                    }
                                });

                    }
                }
                catch (ApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }


    private void updateUI(FirebaseUser user){
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        reff.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Intent i = new Intent(LoginActivity.this, CreateProfile.class);
                    i.putExtra("key", user);
                    startActivity(i);
                }
                else {
                    if((task.getResult().getValue())!=null){
                        Intent i = new Intent(LoginActivity.this, Dashboard.class);
                        i.putExtra("key", user);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(LoginActivity.this, CreateProfile.class);
                        i.putExtra("key", user);
                        startActivity(i);
                    }
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
//        if(reff.child(user.getUid()).has()){
//            Intent i = new Intent(LoginActivity.this, Dashboard.class);
//            i.putExtra("key", user);
//            startActivity(i);
//        }else {
//            Intent i = new Intent(LoginActivity.this, CreateProfile.class);
//            i.putExtra("key", user);
//            startActivity(i);
//        }
    }


        private void logIn(String email, String password) {
            Log.d(TAG, "signIn:" + email);
            if (!validateForm()) {
                return;
            }

//            showProgressBar();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);

                            }

                            if (!task.isSuccessful()) {
//                                status.setText(R.string.auth_failed);
                            }

                        }
                    });
        }



    private boolean validateForm() {
        boolean valid = true;

        String email = fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            fieldEmail.setError("Required.");
            valid = false;
        } else {
            fieldEmail.setError(null);
        }

        String password = fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            fieldPassword.setError("Required.");
            valid = false;
        } else {
            fieldPassword.setError(null);
        }

        return valid;
    }
//
//        private void signOut() {
//            mAuth.signOut();
//            updateUI(null);
//        }
//
}