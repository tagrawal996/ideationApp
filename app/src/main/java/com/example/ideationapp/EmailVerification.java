package com.example.ideationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ideationapp.Model.UserModel;
import com.example.ideationapp.databinding.ActivityEmailVerificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmailVerification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityEmailVerificationBinding binding;
    FirebaseUser fuser;
    String profession;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String email,pass;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailVerificationBinding.inflate(getLayoutInflater());
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
                UserModel user = new UserModel(userName, profession, userID, adress,"default");
                db.getReference().child("Users").child(userID).setValue(user).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                SharedPreferences sp = getSharedPreferences("handleReg",MODE_PRIVATE);
                                sp.edit().putInt("posi",2).apply();
                                startActivity(new Intent(EmailVerification.this, Bottom_nav.class));
                            }
                        });
                fstore.collection("Users").document(userID).set(user);
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
            Toast.makeText(EmailVerification.this, "Email is Verified", Toast.LENGTH_SHORT).show();
        }
        else makeToast("Please Verify Email First");
    }


    private void initialise() {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        email= getIntent().getStringExtra("email");
        pass = getIntent().getStringExtra("password");
        db = FirebaseDatabase.getInstance();
    }

    public void resendEmail(View view){
        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EmailVerification.this, "Verification Code is send", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EmailVerification.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void changeEmail(View view) {
        fuser.delete();
        startActivity(new Intent(EmailVerification.this,registerActivity.class));
    }

    @Override
    protected void onDestroy() {
        fuser.delete();
        super.onDestroy();

    }
}