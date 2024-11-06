package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterforCertificateActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private EditText fullNameEditText;
    private EditText parentNameEditText;
    private EditText dobEditText;
    private EditText addressEditText;
    private EditText mobileNumberEditText;
    private EditText emailEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox confirmCheckBox;
    private Button uploadPhotoButton;
    private Button uploadAadharButton;
    private Button uploadRationCardButton;
    private Button submitButton;

    private Button currentUploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_certificate);

        fullNameEditText = findViewById(R.id.full_name_edit_text);
        parentNameEditText = findViewById(R.id.parent_name_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        mobileNumberEditText = findViewById(R.id.mobile_number_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        confirmCheckBox = findViewById(R.id.confirm_check_box);
        uploadPhotoButton = findViewById(R.id.upload_photo_button);
        uploadAadharButton = findViewById(R.id.upload_aadhar_button);
        uploadRationCardButton = findViewById(R.id.upload_ration_card_button);
        submitButton = findViewById(R.id.submit_button);

        uploadPhotoButton.setOnClickListener(v -> {
            currentUploadButton = uploadPhotoButton;
            openGallery();
        });
        uploadAadharButton.setOnClickListener(v -> {
            currentUploadButton = uploadAadharButton;
            openGallery();
        });
        uploadRationCardButton.setOnClickListener(v -> {
            currentUploadButton = uploadRationCardButton;
            openGallery();
        });

        submitButton.setOnClickListener(v -> {
            if (validateForm()) {
                // Submit the form data to the server or perform desired action
                Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String fileName = getFileName(selectedImageUri);
                currentUploadButton.setText(fileName);
            }
        }
    }

    private String getFileName(Uri uri) {
        String fileName = "Selected File";
        String uriString = uri.toString();
        int lastSlashIndex = uriString.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            fileName = uriString.substring(lastSlashIndex + 1);
        }
        return fileName;
    }

    private boolean validateForm() {
        String fullName = fullNameEditText.getText().toString().trim();
        String parentName = parentNameEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String mobileNumber = mobileNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            fullNameEditText.setError("पूर्ण नाव आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(parentName)) {
            parentNameEditText.setError("आईचा/वडिलांचा नाव आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(dob)) {
            dobEditText.setError("जन्मतारीख आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            addressEditText.setError("पत्ता आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(mobileNumber)) {
            mobileNumberEditText.setError("मोबाईल नंबर आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("ई-मेल पत्ता आवश्यक आहे");
            return false;
        }
        if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "लिंग निवडा", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirmCheckBox.isChecked()) {
            Toast.makeText(this, "कृपया माहिती योग्य आहे हे निश्चित करा", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
