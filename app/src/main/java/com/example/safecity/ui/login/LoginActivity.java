package com.example.safecity.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safecity.MainActivity;
import com.example.safecity.R;
import com.example.safecity.ui.SafePreferences;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textUsernameLayout;
    private TextInputLayout textPasswordInput;
    private Button loginButton;
    private Button RegisterButton;
    private ProgressBar progressBar;
    private SafePreferences preferences;
    private TextView forgotPassword;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private final String BASE_URL = "http://10.0.2.2:8080";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new SafePreferences(this);
        if(preferences.isLoggedIn()){
            startMainActivity();
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        textUsernameLayout = findViewById(R.id.textUsernameLayout);
        textPasswordInput = findViewById(R.id.textPasswordInput);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        RegisterButton = findViewById(R.id.RegisterInLoginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        //Metodo normal
        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.onLoginClicked();
            }
        });*/

        //Metodo lambda
        loginButton.setOnClickListener(v -> onLoginClicked());
        RegisterButton.setOnClickListener(v -> onRegisterClicked());
        forgotPassword.setOnClickListener(v -> onForgot());
        textUsernameLayout
                .getEditText()
                .addTextChangedListener(createTextWatcher(textUsernameLayout));

        textPasswordInput
                .getEditText()
                .addTextChangedListener(createTextWatcher(textPasswordInput));
    }

    private void onForgot() {
        Intent intent = new Intent(this,LosspasswordActivity.class);
        startActivity(intent);
    }

    private void onRegisterClicked() {
        Intent intent = new Intent(this,RegisterActivity.class );
        startActivity(intent);
    }

    private TextWatcher createTextWatcher(TextInputLayout textPasswordInput) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textPasswordInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private void onLoginClicked() {
        String username = textUsernameLayout.getEditText().getText().toString();
        String password = textPasswordInput.getEditText().getText().toString();
        /*
        if(username.isEmpty()){
            textUsernameLayout.setError("Username must not be empty");
        }else if(password.isEmpty()){
            textPasswordInput.setError("Password must not be empty");
        }else if(!username.equals("admin") || !password.equals("admin")){
            showErrorDialog();
        }else{
            handleLoginDialog(username,password);

        }*/
        if(username.isEmpty()){
            textUsernameLayout.setError("Username must not be empty");
        }else if(password.isEmpty()){
            textPasswordInput.setError("Password must not be empty");
        }else{
            handleLoginDialog(username,password);

        }

    }

    private void handleLoginDialog(String username, String password) {
        HashMap<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        Call<LoginResult> call = retrofitInterface.executeLogin(map);
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.code() == 200){
                    LoginResult result = response.body();
                    Log.d("Result",result.getUsername());
                    Log.d("Result",result.getDni());
                    Log.d("Result",result.getEmail());
                    Log.d("Result",result.getPhone());

                    Toast.makeText(LoginActivity.this, "Login Correcto", Toast.LENGTH_LONG).show();
                    performLogin();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login fallido", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "error :C", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void performLogin() {
        preferences.setLoggedIn(true); // Cambiando el estado de login

        textUsernameLayout.setEnabled(false);
        textPasswordInput.setEnabled(false);
        loginButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            //your code
            startMainActivity();
            finish();
        }, 2000);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Credenciales incorretas. intente nuevamente.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}