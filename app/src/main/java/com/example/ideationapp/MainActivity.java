package com.example.ideationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fuser= FirebaseAuth.getInstance().getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("handleReg",MODE_PRIVATE);
                int ck=sp.getInt("posi",0);
                if (ck==0) startActivity(new Intent(MainActivity.this, registerActivity.class));
                else if (ck==1) startActivity(new Intent(MainActivity.this, EmailVerification.class));
                else startActivity(new Intent(MainActivity.this,Bottom_nav.class));
                finish();
            }
        },2000);
    }

}