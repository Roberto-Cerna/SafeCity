package com.example.safecity.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.safecity.R;
import com.google.android.material.textfield.TextInputLayout;

public class NewPassword extends AppCompatActivity {
    private TextInputLayout password;
    private TextInputLayout confirmPassword;
    private Button confirm;
    private Button cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        password = findViewById(R.id.RecoveryNewPassword);
        confirmPassword = findViewById(R.id.RecoveryConfirmedNewPassword);
        confirm = findViewById(R.id.confirmedPasswordButton);
        cancel = findViewById(R.id.CancelToMainButton);

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
            ChangePassword();
        }

    }

    private void ChangePassword() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


}