package com.example.safecity.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.safecity.R;

import java.util.HashMap;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LosspasswordActivity extends AppCompatActivity {
    private EditText objectForRecoverPassword;
    private Button sendCode;
    private Button cancelButton;
    private int code;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private final String BASE_URL = "https://safecity-api.herokuapp.com/";
    private String telNr;
    private String id;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.losspassword);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        objectForRecoverPassword = findViewById(R.id.objectForRecoverPasswor);
        sendCode = findViewById(R.id.SendCode);
        cancelButton = findViewById(R.id.cancelButton);

        sendCode.setOnClickListener(v -> onSendCodeClicked());
        cancelButton.setOnClickListener(v -> returnMenu());

        sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        deliveredPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);
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
            handleSearchAccount();

        }
    }

    private void handleSearchAccount() {
        String email = "",dni = "";
        String message = objectForRecoverPassword.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(message).matches()) {
            email = message;
            Toast.makeText(this, "es email", Toast.LENGTH_SHORT).show();
        }
        else {
            dni = message;
            Toast.makeText(this, "es dni", Toast.LENGTH_SHORT).show();
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("dni",dni);
        map.put("email",email);
        Call<LoginResult> call = retrofitInterface.executeRequest(map);
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.code()==200){
                    LoginResult result = response.body();
                    telNr = result.getPhone();
                    id = result.get_id();
                    Toast.makeText(LosspasswordActivity.this, "Se obtuvo la cuenta", Toast.LENGTH_SHORT).show();
                    sendCodeF();
                }
                else{
                    Toast.makeText(LosspasswordActivity.this, "CUENTA NO ENCONTRADA!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(LosspasswordActivity.this, "SMS sent!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(LosspasswordActivity.this,"Generic Failure",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(LosspasswordActivity.this, "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(LosspasswordActivity.this, "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(LosspasswordActivity.this, "Radio off!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(LosspasswordActivity.this, "SMS delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(LosspasswordActivity.this, "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        registerReceiver(smsSentReceiver,new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver,new IntentFilter(DELIVERED));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsDeliveredReceiver);
        unregisterReceiver(smsSentReceiver);
    }

    private void sendCodeF() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
        else{
            Random rand = new Random();
            int max = 9999;
            int min = 1000;
            code = rand.nextInt((max-min)+1) + min;
            String message = Integer.toString(code);


            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,RecoveryCodeConfirmation.class);

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telNr,null,message,sentPI,deliveredPI);

            intent.putExtra("Code",message);
            intent.putExtra("id",id);
            startActivity(intent);

        }

    }
}