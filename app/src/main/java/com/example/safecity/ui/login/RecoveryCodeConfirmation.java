package com.example.safecity.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.safecity.R;

public class RecoveryCodeConfirmation extends AppCompatActivity {
    private EditText VerificationCode;
    private Button ReceiveCode;
    private Button cancelButton;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        code = getIntent().getStringExtra("Code");

        setContentView(R.layout.activity_recovery_code_confirmation);
        VerificationCode = findViewById(R.id.VerificationCode);
        ReceiveCode = findViewById(R.id.ReceiveCode);
        cancelButton = findViewById(R.id.cancelButtonConfirmation);




        ReceiveCode.setOnClickListener(v -> onReceiveClick());
        cancelButton.setOnClickListener(v-> ReturnMenu());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onReceiveClick() {
        String Code = VerificationCode.getText().toString();

        if(Code.equals(code)){
            //Toast.makeText(this, code, Toast.LENGTH_LONG).show();
            VerificateCode();
        }else{
            Toast.makeText(getApplicationContext(),"Código inválido",Toast.LENGTH_LONG).show();
        }
    }
    private void VerificateCode() {

        Intent intent = new Intent(this, NewPassword.class);
        startActivity(intent);
    }

    private void ReturnMenu() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}