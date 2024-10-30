package com.example.grampanchayatkouthaliapk;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;
import java.util.regex.Pattern;


public class ApplicationFormActivity extends AppCompatActivity {

    private EditText etName, etAge, etAddress, etContact, etPurpose;
    private Button btnSubmit, btnIdentityProof, btnAddressProof, btnLandOwnership, btnIncomeCertificate;

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
        btnIdentityProof = findViewById(R.id.btn_identity_proof);
        btnAddressProof = findViewById(R.id.btn_address_proof);
        btnLandOwnership = findViewById(R.id.btn_land_ownership);
        btnIncomeCertificate = findViewById(R.id.btn_income_certificate);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitApplication();
            }
        });

        btnIdentityProof.setOnClickListener(v -> uploadDocument("Identity Proof"));
        btnAddressProof.setOnClickListener(v -> uploadDocument("Address Proof"));
        btnLandOwnership.setOnClickListener(v -> uploadDocument("Land Ownership"));
        btnIncomeCertificate.setOnClickListener(v -> uploadDocument("Income Certificate"));
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
        if (!isValidName(name)) {
            etName.setError("Invalid name. Only letters and spaces allowed.");
            return;
        }

        String ageStr = null;
        if (!isValidAge(ageStr)) {
            etAge.setError("Age must be between 18 and 60");
            return;
        }

        if (!isValidContact(contact)) {
            etContact.setError("Contact number must be 10 digits");
            return;
        }

        if (purpose.length() < 10 || purpose.length() > 100) {
            etPurpose.setError("Purpose must be between 10 and 100 characters");
            return;
        }



        String applicationId = generateUniqueId();
        showConfirmationDialog(applicationId);


        clearFormFields();
    }
    private boolean isValidName(String name) {
        return Pattern.matches("^[\\p{L} .'-]+$", name); // Regex for letters, spaces, and basic punctuation
    }

    private boolean isValidAge(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            return age >= 18 && age <= 60;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidContact(String contact) {
        return contact.length() == 10 && TextUtils.isDigitsOnly(contact);
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
    private void uploadDocument(String documentType) {
        Toast.makeText(this, documentType + " upload clicked", Toast.LENGTH_SHORT).show();
        // Logic to handle document upload can go here
    }
}
