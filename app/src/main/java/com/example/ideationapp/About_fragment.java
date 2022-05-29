package com.example.ideationapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideationapp.databinding.FragmentAboutFragmentBinding;

public class About_fragment extends Fragment {

    FragmentAboutFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentAboutFragmentBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}