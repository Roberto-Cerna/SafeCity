package com.example.safecity.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import com.example.safecity.R;

public class LosspasswordActivity extends AppCompatActivity {
    private EditText objectForRecoverPassword;
    private Button sendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.losspassword);

        objectForRecoverPassword = findViewById(R.id.objectForRecoverPasswor);
        sendCode = findViewById(R.id.SendCode);

        sendCode.setOnClickListener(v -> sendCodeF());
    }

    private void sendCodeF() {
        Intent intent = new Intent(this,RecoveryCodeConfirmation.class);
        startActivity(intent);
    }
}