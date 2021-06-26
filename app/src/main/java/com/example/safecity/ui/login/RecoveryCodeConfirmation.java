package com.example.safecity.ui.login;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_code_confirmation);
        VerificationCode = findViewById(R.id.VerificationCode);
        ReceiveCode = findViewById(R.id.ReceiveCode);
        cancelButton = findViewById(R.id.cancelButtonConfirmation);

        ReceiveCode.setOnClickListener(v -> onReceiveClick());
        cancelButton.setOnClickListener(v-> ReturnMenu());
    }

    private void onReceiveClick() {
        String Code = VerificationCode.getText().toString();
        if (Code.isEmpty()){
            Toast.makeText(getApplicationContext(),"Ingrese el código",Toast.LENGTH_LONG).show();
        }
        else{
            VerificateCode();
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