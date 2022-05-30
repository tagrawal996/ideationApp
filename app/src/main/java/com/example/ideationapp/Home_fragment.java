package com.example.ideationapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideationapp.databinding.FragmentHomeFragmentBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Home_fragment extends Fragment {

    FragmentHomeFragmentBinding binding;
    private HomeAdapter adapter;
    private ArrayList<PostModel> posts = new ArrayList<PostModel>();
    FirebaseFirestore fstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentHomeFragmentBinding.inflate(inflater, container, false);
        fstore = FirebaseFirestore.getInstance();
        adapter = new HomeAdapter(posts,getContext());

        fstore.collection("posts").get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                posts.add(document.toObject(PostModel.class));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);



        binding.createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AddPost.class));
            }
        });


        return binding.getRoot();
    }
}