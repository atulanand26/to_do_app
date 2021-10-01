package com.example.to_do_app;

package com.example.restro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_activity extends AppCompatActivity {
    EditText emailID, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);

        mAuthStateListner = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(Login_activity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login_activity.this, Home.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Login_activity.this, "Please login", Toast.LENGTH_SHORT).show();
                }

            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailID.setError("Provide a Email ID");
                } else if (pwd.isEmpty()) {
                    password.setError("Please a Password");
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(Login_activity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Login_activity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login_activity.this, "Login Error , Please Login Again ", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intToHome = new Intent(Login_activity.this, Home.class);
                                startActivity(intToHome);
                            }
                        }
                    });

                } else {
                    Toast.makeText(Login_activity.this, "Error Occurred! ", Toast.LENGTH_SHORT).show();
                }
            }

        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intMain = new Intent(Login_activity.this, Signup.class);
                startActivity(intMain);
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

}