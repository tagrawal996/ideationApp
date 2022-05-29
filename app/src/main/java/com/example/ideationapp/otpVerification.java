package com.example.ideationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ideationapp.databinding.ActivityOtpVerificationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class otpVerification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityOtpVerificationBinding binding;
    FirebaseUser fuser;
    String profession;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialise();
        setSpinner();

        binding.userEmail.setText(email);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = fuser.getUid();
                String userName = getIntent().getStringExtra("userName");
                String adress = binding.address.getText().toString().trim();
                if (adress.isEmpty()){
                    binding.address.setError("Address is Required");
                    return;
                }
                if (profession.equals("Profession")){
                    makeToast("Profession is Required");
                    return;
                }
                userModel user = new userModel(userName, profession, userID, adress);
                fstore.collection("Users").document(userID).set(user);
                startActivity(new Intent(otpVerification.this,HomePage.class));
            }
        });
    }

    public void check(View view) {
        fAuth.signInWithEmailAndPassword(email,pass);
        if (fAuth.getCurrentUser().isEmailVerified()){
            binding.textView6.setVisibility(View.GONE);
            binding.resendText.setVisibility(View.GONE);
            binding.check.setVisibility(View.GONE);
            binding.profession.setVisibility(View.VISIBLE);
            binding.address.setVisibility(View.VISIBLE);
            binding.verefied.setVisibility(View.VISIBLE);
            binding.submitButton.setVisibility(View.VISIBLE);
            Toast.makeText(otpVerification.this, "Email is Verified", Toast.LENGTH_SHORT).show();
        }
        else makeToast("Please Verify Email First");
    }


    private void initialise() {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        email= getIntent().getStringExtra("email");
        pass = getIntent().getStringExtra("password");
    }

    public void resendEmail(View view){
        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(otpVerification.this, "Verification Code is send", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(otpVerification.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeToast(String s){
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.profession, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.profession.setAdapter(adapter);
        binding.profession.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        profession = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}