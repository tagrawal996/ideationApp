package com.example.ideationapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideationapp.Adapter.PostAdapter;
import com.example.ideationapp.Model.PostModel;
import com.example.ideationapp.databinding.FragmentFeedFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Feed_fragment extends Fragment {

    FragmentFeedFragmentBinding binding;
    PostAdapter adapter;
    ArrayList<PostModel> posts = new ArrayList<>();
    FirebaseFirestore fstore;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFeedFragmentBinding.inflate(inflater, container, false);

        binding.feedRecyle.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostAdapter(posts,getContext(),true);
        binding.feedRecyle.setAdapter(adapter);

        fstore = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fstore.collection("posts").get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot shot: task.getResult()){
                                PostModel post = shot.toObject(PostModel.class);
                                if (post.getUserID().equals(userID)){
                                    posts.add(post);
                                }
                            }
                            adapter.notifyDataSetChanged();
                            binding.progressBar2.setVisibility(View.GONE);
                        }

                    }
                });


        return binding.getRoot();
    }
}