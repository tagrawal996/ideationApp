package com.example.ideationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ideationapp.databinding.ActivityAddPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPost extends AppCompatActivity {

    FirebaseAuth fAuth;
    ActivityAddPostBinding binding;
    FirebaseFirestore db;
    String uID,username,profession;
    PostModel post = new PostModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());

        initialise();

        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!setUser()) return;
                Date time = Calendar.getInstance().getTime();
                db.collection("posts").
                        document(uID+time.toString()).set(post);
                startActivity(new Intent(AddPost.this,HomePage.class));
            }
        });


        setContentView(binding.getRoot());
    }

    private boolean setUser() {
        String overview = binding.overview.getText().toString().trim();
        String description = binding.problemDesc.getText().toString().trim();
        if (overview.isEmpty()){
            binding.overview.setError("Overview is Required");
            return false;
        }
        if (description.isEmpty()){
            binding.problemDesc.setError("Description is Required");
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String time = dateFormat.format(Calendar.getInstance().getTime());
        db.collection("Users").document(uID).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot shot) {
                        userModel user = shot.toObject(userModel.class);
                        username = user.getUserName();
                        profession = user.getProfession();
                    }
                });
        post.setUserID(uID);
        post.setOverview(overview);
        post.setDescription(description);
        post.setHitCount(0);
        post.setTime(time);
        post.setProfession(profession);
        post.setUsername(username);

        return true;
    }

    private void initialise() {
        fAuth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uID = fAuth.getCurrentUser().getUid();
    }
}