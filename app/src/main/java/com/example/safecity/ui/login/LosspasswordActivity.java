package com.example.safecity.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.safecity.R;

public class LosspasswordActivity extends AppCompatActivity {
    private EditText objectForRecoverPassword;
    private Button sendCode;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.losspassword);

        objectForRecoverPassword = findViewById(R.id.objectForRecoverPasswor);
        sendCode = findViewById(R.id.SendCode);
        cancelButton = findViewById(R.id.cancelButton);

        sendCode.setOnClickListener(v -> onSendCodeClicked());
        cancelButton.setOnClickListener(v -> returnMenu());
    }

    private void returnMenu() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void onSendCodeClicked() {
        String object = objectForRecoverPassword.getText().toString();
        if(object.isEmpty()){
            Toast.makeText(getApplicationContext(),"Ingrese DNI o correo",Toast.LENGTH_LONG).show();
        }
        else{
            sendCodeF();
        }
    }


    private void sendCodeF() {
        Intent intent = new Intent(this,RecoveryCodeConfirmation.class);
        startActivity(intent);
    }
}