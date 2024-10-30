package com.example.grampanchayatkouthaliapk;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class ApplicationFormActivity extends AppCompatActivity {

    private EditText etName, etAge, etAddress, etContact, etPurpose;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_form);


        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etAddress = findViewById(R.id.et_address);
        etContact = findViewById(R.id.et_contact);
        etPurpose = findViewById(R.id.et_purpose);
        btnSubmit = findViewById(R.id.btn_submit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitApplication();
            }
        });
    }


    private void submitApplication() {

        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String purpose = etPurpose.getText().toString().trim();


        if (name.isEmpty() || age.isEmpty() || address.isEmpty() || contact.isEmpty() || purpose.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }


        String applicationId = generateUniqueId();


        showConfirmationDialog(applicationId);


        clearFormFields();
    }


    private String generateUniqueId() {

        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
    }


    private void showConfirmationDialog(String applicationId) {
        // Inflate custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation, null);


        ImageView ivSubmittedSymbol = dialogView.findViewById(R.id.iv_submitted_symbol);
        TextView tvConfirmationMessage = dialogView.findViewById(R.id.tv_confirmation_message);
        TextView tvApplicationId = dialogView.findViewById(R.id.tv_application_id);
        Button btnClose = dialogView.findViewById(R.id.btn_close);


        tvConfirmationMessage.setText(" अर्ज यशस्वीपणे सबमिट करण्यात आला !");
        tvApplicationId.setText("अर्ज क्रमांक : " + applicationId);
        ivSubmittedSymbol.setImageResource(R.drawable.ic_check); // Add a checkmark icon in res/drawable/ic_check.xml


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                navigateToHomePage();
            }
        });

        dialog.show();
    }


    private void navigateToHomePage() {

        Intent intent = new Intent(ApplicationFormActivity.this, GovernmentSchemesActivity.class);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);



        startActivity(intent);
        finish();
    }

    private void clearFormFields() {
        etName.setText("");
        etAge.setText("");
        etAddress.setText("");
        etContact.setText("");
        etPurpose.setText("");
    }
}
