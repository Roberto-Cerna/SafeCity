package com.example.safecity.ui.login;

import com.example.safecity.ui.login.LoginActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safecity.MainActivity;
import com.example.safecity.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout textRegisterUsernameLayout;
    private TextInputLayout textRegisterPasswordInput;
    private Button RegisterButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textRegisterUsernameLayout = findViewById(R.id.textRegisterUsernameLayout);
        textRegisterPasswordInput = findViewById(R.id.textRegisterPasswordInput);
        RegisterButton = findViewById(R.id.RegisterButton);
        progressBar = findViewById(R.id.progressBar);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textRegisterUsernameLayout.getEditText().getText().toString();
                String password = textRegisterPasswordInput.getEditText().getText().toString();

                if(username.isEmpty()){
                    textRegisterUsernameLayout.setError("Username must not be empty");
                }else if(password.isEmpty()){
                    textRegisterPasswordInput.setError("Password must not be empty");
                }
                else{
                    RegisterForm();

                }
            }
        });

    }

    private void RegisterForm(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
