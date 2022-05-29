package com.example.ideationapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ideationapp.databinding.FragmentProfileFragmentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile_fragment extends Fragment {

    FragmentProfileFragmentBinding binding;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentProfileFragmentBinding.
                inflate(inflater, container, false);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        getUser();

        if (fAuth.getCurrentUser().isEmailVerified())
        binding.userName.setText("verified");
        else binding.userName.setText("not verified");

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getContext(),registerActivity.class));
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }

    private void getUser() {
        String userID= fAuth.getCurrentUser().getUid();
        fstore.collection("Users").document(userID).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot shot) {
                userModel user = shot.toObject(userModel.class);
                if (user!=null) updateUser(user);
            }
        });
    }

    private void updateUser(userModel user) {
        binding.userName.setText(user.getUserName());
        binding.workProfession.setText(user.getProfession());
        binding.followCount.setText(String.valueOf(user.getFollowCount()));
        binding.totalHits.setText(String.valueOf(user.getTotalHits()));
        binding.maxHits.setText(String.valueOf(user.getMaxHits()));
    }
}