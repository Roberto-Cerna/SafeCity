package com.example.safecity.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.safecity.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPassword extends AppCompatActivity {
    private TextInputLayout password;
    private TextInputLayout confirmPassword;
    private Button confirm;
    private Button cancel;


    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private final String BASE_URL = "https://safecity-api.herokuapp.com/";
    private String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        password = findViewById(R.id.RecoveryNewPassword);
        confirmPassword = findViewById(R.id.RecoveryConfirmedNewPassword);
        confirm = findViewById(R.id.confirmedPasswordButton);
        cancel = findViewById(R.id.CancelToMainButton);
        id = getIntent().getStringExtra("id");
        confirm.setOnClickListener(v -> onClickConfirm());
        cancel.setOnClickListener(v -> onCancelClick());


    }

    private void onCancelClick() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void onClickConfirm() {
        String passwordtext = password.getEditText().getText().toString();
        String confirmedpasswordtext = confirmPassword.getEditText().getText().toString();
        if (passwordtext.isEmpty() || confirmedpasswordtext.isEmpty()){
            Toast.makeText(getApplicationContext(),"Ingrese su nueva contraseña",Toast.LENGTH_LONG).show();
        }
        else if (!passwordtext.equals(confirmedpasswordtext)){
            Toast.makeText(getApplicationContext(),"No conciden las contraseñas",Toast.LENGTH_LONG).show();
        }
        else{
            ChangePassword(passwordtext);
            finish();
        }

    }

    private void ChangePassword(String passwordtext) {
        HashMap<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("password",passwordtext);
        Call<Void> call = retrofitInterface.executeNewPassword(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(NewPassword.this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NewPassword.this, "Error al cambiar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(NewPassword.this, "Error, inténtelo más tarde", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }


}