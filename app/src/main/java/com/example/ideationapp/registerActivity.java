package com.example.ideationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class registerActivity extends AppCompatActivity {

    Button register;
    TextInputEditText username,email,password;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.signup);
        username = findViewById(R.id.namesignup);
        email = findViewById(R.id.emailsignup);
        password = findViewById(R.id.passwordsignup);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString().trim();
                String emails = email.getText().toString().trim();
                String passwords = password.getText().toString().trim();
                if (userName.isEmpty()) username.setError("Name is Required!");
                else if (emails.isEmpty()) email.setError("Email is Required! ");
                else if (passwords.isEmpty()) password.setError("Password is Required! ");
                else{
                    mAuth.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userID = mAuth.getCurrentUser().getUid();
                                userModel user = new userModel(userName, "Student", userID);
                                fstore.collection("Users").document(userID).set(user);
                            } else {
                                makeToast("" + task.getException().getMessage());
                            }
                        }
                    });
                }

            }
        });
    }

    private void makeToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void LoginPage(View view) {
        startActivity(new Intent(registerActivity.this,LoginActivity.class));
        finish();
    }

}