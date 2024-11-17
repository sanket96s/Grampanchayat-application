package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterforCertificateActivity extends AppCompatActivity {

    private EditText fullNameEditText, parentNameEditText, dobEditText, addressEditText, mobileNumberEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox confirmCheckBox;
    private Button submitButton, uploadPhotoButton, uploadAadharButton, uploadRationCardButton;
    private DatabaseReference databaseReference;
    private String documentType; // Variable to store document type
    private ImageView imageView; // ImageView for displaying image
    private Uri imageUri; // Uri to store image URI for upload

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_certificate);

        // Retrieve the selected document type
        Intent intent = getIntent();
        documentType = intent.getStringExtra("DOCUMENT_TYPE");

        // Display the document type in a TextView
        TextView documentTypeTextView = findViewById(R.id.document_type_text_view);
        if (documentType != null) {
            documentTypeTextView.setText("प्रमाणपत्र प्रकार: " + documentType);
        } else {
            documentTypeTextView.setText("प्रमाणपत्र प्रकार: माहिती नाही");
            documentType = "माहिती नाही"; // Fallback value
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("CertificateApplications");

        fullNameEditText = findViewById(R.id.full_name_edit_text);
        parentNameEditText = findViewById(R.id.parent_name_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        mobileNumberEditText = findViewById(R.id.mobile_number_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        confirmCheckBox = findViewById(R.id.confirm_check_box);

        submitButton = findViewById(R.id.submit_button);
        uploadPhotoButton = findViewById(R.id.upload_photo_button);
        uploadAadharButton = findViewById(R.id.upload_aadhar_button);
        uploadRationCardButton = findViewById(R.id.upload_ration_card_button);

        // Initialize the ImageView
        imageView = findViewById(R.id.imageView);

        // Set Google Forms links for image uploads
        uploadPhotoButton.setOnClickListener(view -> openGoogleForm("https://forms.gle/8i1mcvAzoh5qrEF39"));
        uploadAadharButton.setOnClickListener(view -> openGoogleForm("https://forms.gle/8i1mcvAzoh5qrEF39"));
        uploadRationCardButton.setOnClickListener(view -> openGoogleForm("https://forms.gle/8i1mcvAzoh5qrEF39"));

        submitButton.setOnClickListener(view -> {
            if (validateForm()) {
                submitToFirebase();
            }
        });

        // Retrieve image URLs from Firebase
        retrieveImageUrls();
    }

    private void openGoogleForm(String formUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(formUrl));
        startActivity(browserIntent);
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(fullNameEditText.getText())) {
            fullNameEditText.setError("कृपया पूर्ण नाव प्रविष्ट करा");
            return false;
        }
        if (TextUtils.isEmpty(parentNameEditText.getText())) {
            parentNameEditText.setError("कृपया पालकांचे नाव प्रविष्ट करा");
            return false;
        }
        if (TextUtils.isEmpty(dobEditText.getText())) {
            dobEditText.setError("कृपया जन्मतारीख प्रविष्ट करा");
            return false;
        }
        if (TextUtils.isEmpty(addressEditText.getText())) {
            addressEditText.setError("कृपया पत्ता प्रविष्ट करा");
            return false;
        }
        if (TextUtils.isEmpty(mobileNumberEditText.getText()) || mobileNumberEditText.getText().toString().length() != 10) {
            mobileNumberEditText.setError("कृपया वैध १० अंकी मोबाइल नंबर प्रविष्ट करा");
            return false;
        }
        if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "कृपया लिंग निवडा", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirmCheckBox.isChecked()) {
            Toast.makeText(this, "कृपया पुष्टी बॉक्स निवडा", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void submitToFirebase() {
        String fullName = fullNameEditText.getText().toString();
        String parentName = parentNameEditText.getText().toString();
        String dob = dobEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();
        RadioButton selectedGender = findViewById(genderRadioGroup.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        // Generate unique 7-digit ID
        String uniqueId = generateUniqueId();

        // Save data in Firebase Realtime Database
        Map<String, String> applicationData = new HashMap<>();
        applicationData.put("FullName", fullName);
        applicationData.put("ParentName", parentName);
        applicationData.put("DOB", dob);
        applicationData.put("Address", address);
        applicationData.put("MobileNumber", mobileNumber);
        applicationData.put("Gender", gender);
        applicationData.put("ApplicationID", uniqueId);
        applicationData.put("CertificateType", documentType); // Save document type

        // Upload image to Firebase Storage and get the URL
        if (imageUri != null) {
            uploadImageToFirebase(imageUri);
        }

        // Save other data to Firebase
        databaseReference.child(uniqueId).setValue(applicationData)
                .addOnSuccessListener(unused -> Toast.makeText(this, "डेटा यशस्वीरित्या सबमिट झाला", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "डेटा सबमिट करण्यात समस्या आली", Toast.LENGTH_SHORT).show());
    }

    private String generateUniqueId() {
        Random random = new Random();
        return String.format("%07d", random.nextInt(10000000));
    }

    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads/" + System.currentTimeMillis() + ".jpg");
        storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        saveImageUrlToDatabase(imageUrl);
                    }).addOnFailureListener(e -> {
                        Toast.makeText(RegisterforCertificateActivity.this, "Failed to get image URL", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegisterforCertificateActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        String uniqueId = generateUniqueId();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CertificateApplications").child(uniqueId);

        // Create a map to store the image data
        Map<String, Object> applicationData = new HashMap<>();
        applicationData.put("imageUrl", imageUrl);
        applicationData.put("imageType", "photo"); // Can be "aadhar", "rationCard", etc.

        // Save the data in Firebase
        databaseReference.setValue(applicationData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterforCertificateActivity.this, "Image URL saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterforCertificateActivity.this, "Failed to save image URL", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveImageUrls() {
        DatabaseReference imageDatabaseReference = FirebaseDatabase.getInstance().getReference("CertificateApplications");
        imageDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Use Glide to load the image from Firebase Storage
                        Glide.with(RegisterforCertificateActivity.this)
                                .load(imageUrl)
                                .into(imageView);
                    } else {
                        Toast.makeText(RegisterforCertificateActivity.this, "Image URL is not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RegisterforCertificateActivity.this, "Failed to retrieve image data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
