package com.example.safecity.ui.new_emergency_contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.safecity.R;
import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.user.DefaultResult;
import com.example.safecity.connection.user.EmergencyContactsResult;
import com.example.safecity.connection.user.PostEmergencyContactBody;
import com.example.safecity.data.user.User;
import com.example.safecity.ui.emergency_contacts.EmergencyContactsListAdapter;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEmergencyContactActivity extends AppCompatActivity {

    private TextInputLayout newContactNameTextField;
    private TextInputLayout newContactNumberTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_emergency_contact);

        newContactNameTextField = findViewById(R.id.newContactNameTextField);
        newContactNumberTextField = findViewById(R.id.newContactNumberTextField);
    }

    public void cancelNewEmergencyContact(View view) {
        finish();
    }

    public void saveNewEmergencyContact(View view) {
        String newContactName = Objects.requireNonNull(newContactNameTextField.getEditText()).getText().toString();
        String newContactPhone = Objects.requireNonNull(newContactNumberTextField.getEditText()).getText().toString();
        if(newContactName.equals("")) {
            Toast.makeText(getApplicationContext(), "El campo Nombre está vacío", Toast.LENGTH_SHORT).show();
        }
        else if(newContactPhone.equals("")) {
            Toast.makeText(getApplicationContext(), "El campo Teléfono está vacío", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), defaultResult.getMsg(), Toast.LENGTH_SHORT).show();
                        Call<EmergencyContactsResult> insideCall = MainRetrofit.userAPI.getEmergencyContacts(User.id);
                        insideCall.enqueue(new Callback<EmergencyContactsResult>() {
                            @Override
                            public void onResponse(@NotNull Call<EmergencyContactsResult> call, @NotNull Response<EmergencyContactsResult> response) {
                                if(response.code() == 200) {
                                    EmergencyContactsResult emergencyContactsResult = response.body();
                                    assert emergencyContactsResult != null;
                                    User.emergencyContacts = emergencyContactsResult.getEmergencyContacts();
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<EmergencyContactsResult> call, @NotNull Throwable t) {
                                Toast.makeText(getApplicationContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        if (response.code() == 300) {
                            Toast.makeText(getApplicationContext(), "Ya existe un contacto con este número", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(@NotNull Call<DefaultResult> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}