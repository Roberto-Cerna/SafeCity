package com.example.safecity.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.safecity.databinding.FragmentProfileBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import com.example.safecity.data.user.User;

public class ProfileFragment extends Fragment {
    //Datos del perfil
    private String profileName = User.name;
    private String profileEmail = User.email;
    private String profilePhone = User.phone;

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    private Button updateProfileButton;
    private Button saveProfileButton;
    private TextInputLayout nameProfileTextField;
    private TextInputLayout emailProfileTextField;
    private TextInputLayout phoneProfileTextField;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textProfile;
        updateProfileButton = binding.updateProfileButton;
        saveProfileButton = binding.saveProfileButton;
        nameProfileTextField = binding.nameProfileTextField;
        emailProfileTextField = binding.emailProfileTextField;
        phoneProfileTextField = binding.phoneProfileTextField;

        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //textView.setText(s);
            }
        });

        setProfileInfo();

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatingProfile(true);
            }
        });

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatingProfile(false);
                Toast.makeText(getActivity(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setProfileInfo() {
        Objects.requireNonNull(nameProfileTextField.getEditText()).setText(profileName);
        Objects.requireNonNull(emailProfileTextField.getEditText()).setText(profileEmail);
        Objects.requireNonNull(phoneProfileTextField.getEditText()).setText(profilePhone);
    }

    public void updatingProfile(boolean isUpdating) {
        nameProfileTextField.setEnabled(isUpdating);
        emailProfileTextField.setEnabled(isUpdating);
        phoneProfileTextField.setEnabled(isUpdating);
        if(isUpdating) {
            saveProfileButton.setVisibility(View.VISIBLE);
            updateProfileButton.setVisibility(View.INVISIBLE);
        }
        else {
            saveProfileButton.setVisibility(View.INVISIBLE);
            updateProfileButton.setVisibility(View.VISIBLE);
        }
    }
}