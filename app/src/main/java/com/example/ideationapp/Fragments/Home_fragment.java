package com.example.ideationapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideationapp.AddPost;
import com.example.ideationapp.Adapter.PostAdapter;
import com.example.ideationapp.Model.PostModel;
import com.example.ideationapp.databinding.FragmentHomeFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    FragmentHomeFragmentBinding binding;
    private PostAdapter adapter;
    private ArrayList<PostModel> posts = new ArrayList<PostModel>();
    FirebaseFirestore fstore;
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentHomeFragmentBinding.inflate(inflater, container, false);
        fstore = FirebaseFirestore.getInstance();
        adapter = new PostAdapter(posts,getContext(),false);
        db = FirebaseDatabase.getInstance();

        db.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot shot: snapshot.getChildren()){
                    PostModel post = shot.getValue(PostModel.class);
                    posts.add(post);
                }
                adapter.notifyDataSetChanged();
                binding.progressBar3.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);



        binding.createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddPost.class));
            }
        });
        return binding.getRoot();
    }
}