package com.example.safecity.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safecity.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout textRegisterUsernameLayout;
    private TextInputLayout textPhoneRegisterLayout;
    private TextInputLayout textEmailRegisterLayout;
    private TextInputLayout textDniRegisterLayout;
    private TextInputLayout textRegisterPasswordInput;
    private TextInputLayout textRegisterConfirmedPasswordLayout;

    private Button RegisterButton,cancelRegisterButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textRegisterUsernameLayout = findViewById(R.id.textRegisterUsernameLayout);
        textPhoneRegisterLayout = findViewById(R.id.textRegisterPhoneLayout);
        textDniRegisterLayout = findViewById(R.id.textRegisterDNIInput);
        textEmailRegisterLayout = findViewById(R.id.textEmailInput);
        textRegisterPasswordInput = findViewById(R.id.textRegisterPasswordInput);
        textRegisterConfirmedPasswordLayout = findViewById(R.id.textConfirmedRegisterPasswordInput);

        RegisterButton = findViewById(R.id.RegisterButton);
        progressBar = findViewById(R.id.progressBar);
        cancelRegisterButton = findViewById(R.id.cancelRegisterButton);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textRegisterUsernameLayout.getEditText().getText().toString();
                String phone = textPhoneRegisterLayout.getEditText().getText().toString();
                String dni = textDniRegisterLayout.getEditText().getText().toString();
                String email = textEmailRegisterLayout.getEditText().getText().toString();
                String password = textRegisterPasswordInput.getEditText().getText().toString();
                String confirmedPassword = textRegisterConfirmedPasswordLayout.getEditText().getText().toString();

                textRegisterUsernameLayout.setError(null);
                textPhoneRegisterLayout.setError(null);
                textDniRegisterLayout.setError(null);
                textEmailRegisterLayout.setError(null);
                textRegisterPasswordInput.setError(null);
                textRegisterConfirmedPasswordLayout.setError(null);



                if(username.isEmpty()){
                    textRegisterUsernameLayout.setError("El campo usuario no debe estar vacío");
                }else if(username.length()<5){
                    textRegisterUsernameLayout.setError("El campo usuario debe tener al menos 5 carácteres");
                }else if(phone.length()<9){
                    textPhoneRegisterLayout.setError("Ingrese un número de teléfono válido");
                }else if( dni.length() != 8){
                    textDniRegisterLayout.setError("El dni debe tener 8 dígitos");
                }
                else if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    textEmailRegisterLayout.setError("Email invalido");
                }
                else if(password.isEmpty()){
                    textRegisterPasswordInput.setError("La contraseña no puede ser vacía");
                }
                else if(password.length()<6){
                    textRegisterPasswordInput.setError("La contraseña debe tener al menos 6 carácteres");
                }
                else if(!password.equals(confirmedPassword)){
                    textRegisterPasswordInput.setError("Las contraseñas no coinciden");
                    textRegisterConfirmedPasswordLayout.setError("Las contraseñas no coinciden");
                }
                else{
                    RegisterForm();
                    finish();
                }
            }
        });
        cancelRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void RegisterForm(){
        
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        
    }

}
