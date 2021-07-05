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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.safecity.R;

import java.util.Random;

public class LosspasswordActivity extends AppCompatActivity {
    private EditText objectForRecoverPassword;
    private Button sendCode;
    private Button cancelButton;
    private int code;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.losspassword);
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
            sendCodeF();
        }
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
            String telNr = "940623330";

            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,RecoveryCodeConfirmation.class);

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telNr,null,message,sentPI,deliveredPI);

            intent.putExtra("Code",message);
            startActivity(intent);

        }

    }
}