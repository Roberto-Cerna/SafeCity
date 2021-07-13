package com.example.safecity.ui.review;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safecity.R;
import com.example.safecity.databinding.NewEmergencyContactFragmentBinding;
import com.example.safecity.databinding.ReviewFragmentBinding;

public class ReviewFragment extends Fragment {

    private ReviewViewModel mViewModel;
    private ReviewFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ReviewFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

}