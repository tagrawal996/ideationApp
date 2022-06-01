package com.example.ideationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ideationapp.Model.userModel;
import com.example.ideationapp.databinding.ActivityCommentPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CommentPage extends AppCompatActivity {

    ActivityCommentPageBinding binding;
    private String postID,authorID;
    Intent intent;
    FirebaseUser fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        postID = intent.getStringExtra("postId");
        authorID = intent.getStringExtra("authorId");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        
        getUserImage();

        binding.postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = binding.commentText.getText().toString().trim();
                if (!comment.isEmpty()){
                    pushComment(comment);
                }
            }
        });

    }

    private void pushComment(String comment) {
        HashMap<String,Object> map = new HashMap<>();

        map.put("comment",comment);
        map.put("publisher",fuser.getUid());

        FirebaseDatabase.getInstance().getReference().child("comments").child(postID).push().setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            makeToast("Comment Posted");
                        }
                        else makeToast(task.getException().getMessage());
                    }
                });
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getUserImage() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(fuser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userModel user = snapshot.getValue(userModel.class);
                        if (!user.getImageURL().equals("default"))
                        Picasso.get().load(user.getImageURL()).into(binding.userProfile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}