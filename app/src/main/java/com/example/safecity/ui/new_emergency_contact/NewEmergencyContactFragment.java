package com.example.safecity.ui.new_emergency_contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.safecity.R;
import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.user.DefaultResult;
import com.example.safecity.connection.user.GetEmergencyContactsResult;
import com.example.safecity.connection.user.PostEmergencyContactBody;
import com.example.safecity.data.user.User;
import com.example.safecity.databinding.NewEmergencyContactFragmentBinding;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEmergencyContactFragment extends Fragment {

    private NewEmergencyContactViewModel mViewModel;
    private NewEmergencyContactFragmentBinding binding;
    private TextInputLayout newContactNameTextField;
    private TextInputLayout newContactNumberTextField;
    private Button saveNewContactButton;
    private Button cancelButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = NewEmergencyContactFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        newContactNameTextField = binding.newContactNameTextField;
        newContactNumberTextField = binding.newContactNumberTextField;
        saveNewContactButton = binding.saveNewContactButton;
        cancelButton = binding.cancelNewContactButton;

        saveNewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newContactName = Objects.requireNonNull(newContactNameTextField.getEditText()).getText().toString();
                String newContactPhone = Objects.requireNonNull(newContactNumberTextField.getEditText()).getText().toString();
                if(newContactName.equals("")) {
                    Toast.makeText(requireContext(), "El campo Nombre está vacío", Toast.LENGTH_SHORT).show();
                }
                else if(newContactPhone.equals("")) {
                    Toast.makeText(requireContext(), "El campo Teléfono está vacío", Toast.LENGTH_SHORT).show();
                }
                else {
                    Call<DefaultResult> call = MainRetrofit.userAPI
                            .postEmergencyContact(new PostEmergencyContactBody(User.id, newContactName,
                                    newContactPhone));
                    call.enqueue(new Callback<DefaultResult>() {
                        @Override
                        public void onResponse(@NotNull Call<DefaultResult> call, @NotNull Response<DefaultResult> response) {
                            if(response.code() == 200) {
                                DefaultResult defaultResult = response.body();
                                assert defaultResult != null;
                                Toast.makeText(requireContext(), defaultResult.getMsg(), Toast.LENGTH_SHORT).show();
                                Call<GetEmergencyContactsResult> insideCall = MainRetrofit.userAPI.getEmergencyContacts(User.id);
                                insideCall.enqueue(new Callback<GetEmergencyContactsResult>() {
                                    @Override
                                    public void onResponse(@NotNull Call<GetEmergencyContactsResult> call, @NotNull Response<GetEmergencyContactsResult> response) {
                                        if(response.code() == 200) {
                                            GetEmergencyContactsResult getEmergencyContactsResult = response.body();
                                            assert getEmergencyContactsResult != null;
                                            User.emergencyContacts = getEmergencyContactsResult.getEmergencyContacts();
                                            NavController navController = Navigation.findNavController(requireView());
                                            navController.navigate(R.id.nav_emergency_contacts);
                                        }
                                        else {
                                            Toast.makeText(requireContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<GetEmergencyContactsResult> call, @NotNull Throwable t) {
                                        Toast.makeText(requireContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                if (response.code() == 300) {
                                    Toast.makeText(requireContext(), "Ya tiene un contacto con este número", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(requireContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(@NotNull Call<DefaultResult> call, @NotNull Throwable t) {
                            Toast.makeText(requireContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_emergency_contacts);
            }
        });

        return root;
    }
}