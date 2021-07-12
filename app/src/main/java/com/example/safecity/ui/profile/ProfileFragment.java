package com.example.safecity.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safecity.R;
import com.example.safecity.databinding.FragmentProfileBinding;
import com.example.safecity.ui.SafePreferences;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import com.example.safecity.data.user.User;
import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.user.*;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private SafePreferences preferences;
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

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
                saveProfile();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

    public void setProfileInfo() {
        Objects.requireNonNull(nameProfileTextField.getEditText()).setText(profileName);
        Objects.requireNonNull(emailProfileTextField.getEditText()).setText(profileEmail);
        Objects.requireNonNull(phoneProfileTextField.getEditText()).setText(profilePhone);
        updatingProfile(false);
    }

    public void saveProfile() {
        String name = Objects.requireNonNull(nameProfileTextField.getEditText()).getText().toString();
        String email = Objects.requireNonNull(emailProfileTextField.getEditText()).getText().toString();
        String phone = Objects.requireNonNull(phoneProfileTextField.getEditText()).getText().toString();

        UpdateProfileBody updateProfileBody = new UpdateProfileBody(name, email, phone);
        Call<DefaultResult> call = MainRetrofit.userAPI.updateProfile(User.id, updateProfileBody);
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(@NotNull Call<DefaultResult> call, @NotNull Response<DefaultResult> response) {
                Log.i("response", String.valueOf(response));
                String message = "";
                if(response.code() == 200) {
                    DefaultResult defaultResult = response.body();
                    assert defaultResult != null;
                    if(defaultResult.getErr() == null) {
                        Log.i("Result of update profile", defaultResult.getMsg());
                        message = defaultResult.getMsg();
                        preferences = new SafePreferences(requireContext());
                        preferences.SetName(name);
                        preferences.setEmail(email);
                        preferences.setPhone(phone);
                        User.name = name;
                        User.email = email;
                        User.phone = phone;
                        NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
                        TextView headerNameTextView = navigationView.getHeaderView(0).findViewById(R.id.headerNameTextView);
                        TextView headerEmailTextView = navigationView.getHeaderView(0).findViewById(R.id.headerEmailTextView);
                        headerNameTextView.setText(name);
                        headerEmailTextView.setText(email);
                        updatingProfile(false);
                    }
                    else {
                        Log.i("Result of update profile", defaultResult.getErr());
                        message = defaultResult.getErr();
                    }
                }
                else {
                    ResponseBody defaultResult = response.errorBody();
                    assert defaultResult != null;
                    message = "Ocurrrió un error, vuelva a intentarlo más tarde";
                    setProfileInfo();
                    updatingProfile(false);
                    Log.i("Result of update profile", defaultResult.toString());
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<DefaultResult> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
            }
        });

    }
}