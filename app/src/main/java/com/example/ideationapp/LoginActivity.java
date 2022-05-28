package com.example.ideationapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

import io.grpc.internal.AbstractReadableBuffer;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText email,password;
    Button login;
    String user,pass;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email =  findViewById(R.id.emailsignin);
        password = findViewById(R.id.passwordsignin);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        bar = findViewById(R.id.loading);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                user = email.getText().toString().trim();
                pass = password.getText().toString().trim();
                if (user.isEmpty()){
                    email.setError("Email is Required");
                    email.requestFocus();
                    bar.setVisibility(View.GONE);
                }
                if (pass.isEmpty()){
                    password.setError("Email is Required");
                    password.requestFocus();
                    bar.setVisibility(View.GONE);
                }
                else{
                    mAuth.signInWithEmailAndPassword(user,pass).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Failed "+task.getException().
                                                getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                    bar.setVisibility(View.GONE);
                                }
                            });
                }

            }
        });
    }

    public void RegisterPage(View view) {
        startActivity(new Intent(LoginActivity.this,registerActivity.class));
        finish();
    }
}