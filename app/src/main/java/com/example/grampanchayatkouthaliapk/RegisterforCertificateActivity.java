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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterforCertificateActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private Uri selectedImageUri;
    private String imageUrl;

    private EditText fullNameEditText;
    private EditText parentNameEditText;
    private EditText dobEditText;
    private EditText addressEditText;
    private EditText mobileNumberEditText;
    private EditText emailEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox confirmCheckBox;
    private Button uploadPhotoButton;
    private Button submitButton;

    private FirebaseFirestore db;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_certificate);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        fullNameEditText = findViewById(R.id.full_name_edit_text);
        parentNameEditText = findViewById(R.id.parent_name_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        mobileNumberEditText = findViewById(R.id.mobile_number_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        confirmCheckBox = findViewById(R.id.confirm_check_box);
        uploadPhotoButton = findViewById(R.id.upload_photo_button);
        submitButton = findViewById(R.id.submit_button);

        uploadPhotoButton.setOnClickListener(v -> openGallery());
        submitButton.setOnClickListener(v -> {
            if (validateForm()) {
                uploadImageAndSaveData();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(fullNameEditText.getText())) {
            fullNameEditText.setError("पूर्ण नाव आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(parentNameEditText.getText())) {
            parentNameEditText.setError("आईचा/वडिलांचा नाव आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(dobEditText.getText())) {
            dobEditText.setError("जन्मतारीख आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(addressEditText.getText())) {
            addressEditText.setError("पत्ता आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(mobileNumberEditText.getText())) {
            mobileNumberEditText.setError("मोबाईल नंबर आवश्यक आहे");
            return false;
        }
        if (TextUtils.isEmpty(emailEditText.getText())) {
            emailEditText.setError("ई-मेल पत्ता आवश्यक आहे");
            return false;
        }
        if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "लिंग निवडा", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirmCheckBox.isChecked()) {
            confirmCheckBox.setError("कृपया, माहितीची पुष्टी करा");
            return false;
        }
        if (selectedImageUri == null) {
            Toast.makeText(this, "कृपया फोटो अपलोड करा", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void uploadImageAndSaveData() {
        StorageReference storageRef = storage.getReference();
        String fileName = UUID.randomUUID().toString(); // unique file name
        StorageReference imageRef = storageRef.child("certificate_images/" + fileName);

        UploadTask uploadTask = imageRef.putFile(selectedImageUri);
        uploadTask.addOnSuccessListener(taskSnapshot ->
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageUrl = uri.toString();
                    saveDataToFirestore();
                })
        ).addOnFailureListener(e ->
                Toast.makeText(this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
        );
    }

    private void saveDataToFirestore() {
        String fullName = fullNameEditText.getText().toString().trim();
        String parentName = parentNameEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String mobileNumber = mobileNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        String gender = (selectedGenderId == R.id.male_radio_button) ? "पुरुष" :
                (selectedGenderId == R.id.female_radio_button) ? "महिला" : "इतर";

        Map<String, Object> formData = new HashMap<>();
        formData.put("fullName", fullName);
        formData.put("parentName", parentName);
        formData.put("dob", dob);
        formData.put("address", address);
        formData.put("mobileNumber", mobileNumber);
        formData.put("email", email);
        formData.put("gender", gender);
        formData.put("imageUrl", imageUrl); // Store image URL

        db.collection("certificateApplications")
                .add(formData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to submit form: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
