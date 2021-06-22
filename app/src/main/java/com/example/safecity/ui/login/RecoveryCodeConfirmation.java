package com.example.safecity.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.safecity.R;

public class RecoveryCodeConfirmation extends AppCompatActivity {
    private EditText VerificationCode;
    private Button ReceiveCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_code_confirmation);
        VerificationCode = findViewById(R.id.VerificationCode);
        ReceiveCode = findViewById(R.id.ReceiveCode);
        
        ReceiveCode.setOnClickListener(v -> VerificateCode());

    }

    private void VerificateCode() {
        Intent intent = new Intent(this, NewPassword.class);
        startActivity(intent);
    }
}