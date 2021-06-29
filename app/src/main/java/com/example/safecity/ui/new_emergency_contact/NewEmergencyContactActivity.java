package com.example.safecity.ui.new_emergency_contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.safecity.R;
import com.google.android.material.textfield.TextInputLayout;

public class NewEmergencyContactActivity extends AppCompatActivity {

    private TextInputLayout newContactNameTextField;
    private TextInputLayout newContactNumberTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_emergency_contact);

        newContactNameTextField = findViewById(R.id.newContactNameTextField);
        newContactNumberTextField = findViewById(R.id.newContactNumberTextField);
    }

    public void cancelNewEmergencyContact(View view) {
        finish();
    }

    public void saveNewEmergencyContact(View view) {
        if(!newContactNumberTextField.getEditText().getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Contacto agregado!", Toast.LENGTH_SHORT).show();
            newContactNameTextField.getEditText().setText("");
            newContactNumberTextField.getEditText().setText("");
            finish();
        }
    }
}