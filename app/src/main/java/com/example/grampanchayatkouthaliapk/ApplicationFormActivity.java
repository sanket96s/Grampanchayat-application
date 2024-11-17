package com.example.grampanchayatkouthaliapk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;
import java.util.regex.Pattern;

public class ApplicationFormActivity extends AppCompatActivity {

    private EditText etName, etAge, etAddress, etContact, etPurpose;
    private Button btnSubmit, btnIdentityProof, btnAddressProof, btnLandOwnership, btnIncomeCertificate;

    private static final int REQUEST_CODE_IDENTITY_PROOF = 1;
    private static final int REQUEST_CODE_ADDRESS_PROOF = 2;
    private static final int REQUEST_CODE_LAND_OWNERSHIP = 3;
    private static final int REQUEST_CODE_INCOME_CERTIFICATE = 4;

    private Uri selectedIdentityProofUri, selectedAddressProofUri, selectedLandOwnershipUri, selectedIncomeCertificateUri;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference applicationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_form);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        applicationsRef = firebaseDatabase.getReference("applications");

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

        btnIdentityProof.setOnClickListener(v -> openFilePicker(REQUEST_CODE_IDENTITY_PROOF));
        btnAddressProof.setOnClickListener(v -> openFilePicker(REQUEST_CODE_ADDRESS_PROOF));
        btnLandOwnership.setOnClickListener(v -> openFilePicker(REQUEST_CODE_LAND_OWNERSHIP));
        btnIncomeCertificate.setOnClickListener(v -> openFilePicker(REQUEST_CODE_INCOME_CERTIFICATE));

        btnSubmit.setOnClickListener(v -> submitApplication());
    }

    private void openFilePicker(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Select Document"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            String fileName = getFileName(fileUri);

            switch (requestCode) {
                case REQUEST_CODE_IDENTITY_PROOF:
                    selectedIdentityProofUri = fileUri;
                    Toast.makeText(this, "Identity Proof Selected: " + fileName, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_ADDRESS_PROOF:
                    selectedAddressProofUri = fileUri;
                    Toast.makeText(this, "Address Proof Selected: " + fileName, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_LAND_OWNERSHIP:
                    selectedLandOwnershipUri = fileUri;
                    Toast.makeText(this, "Land Ownership Selected: " + fileName, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_INCOME_CERTIFICATE:
                    selectedIncomeCertificateUri = fileUri;
                    Toast.makeText(this, "Income Certificate Selected: " + fileName, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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

        if (!isValidAge(age)) {
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

        if (selectedIdentityProofUri == null || selectedAddressProofUri == null ||
                selectedLandOwnershipUri == null || selectedIncomeCertificateUri == null) {
            Toast.makeText(this, "Please upload all required documents", Toast.LENGTH_SHORT).show();
            return;
        }

        String applicationId = generateUniqueId();
        uploadDocumentsAndSaveData(applicationId, name, age, address, contact, purpose);
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

    private void uploadDocumentsAndSaveData(String applicationId, String name, String age, String address, String contact, String purpose) {
        // Upload documents to Firebase
        uploadDocumentToFirebase("IdentityProof", selectedIdentityProofUri, applicationId);
        uploadDocumentToFirebase("AddressProof", selectedAddressProofUri, applicationId);
        uploadDocumentToFirebase("LandOwnership", selectedLandOwnershipUri, applicationId);
        uploadDocumentToFirebase("IncomeCertificate", selectedIncomeCertificateUri, applicationId);

        // Save application data to Firebase Realtime Database after upload
        saveApplicationDataToDatabase(applicationId, name, age, address, contact, purpose);
    }

    private void uploadDocumentToFirebase(String documentType, Uri fileUri, String applicationId) {
        if (fileUri != null) {
            String fileName = documentType + "_" + UUID.randomUUID().toString();
            StorageReference fileRef = storageReference.child("documents/" + fileName);

            fileRef.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        Log.d("Firebase", documentType + " URL: " + downloadUrl);
                        // Save the document URL to the Firebase Realtime Database
                        saveDocumentUrlToDatabase(applicationId, documentType, downloadUrl);
                    }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(ApplicationFormActivity.this, "Failed to upload " + documentType, Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error uploading " + documentType, e);
                    });
        }
    }

    private void saveDocumentUrlToDatabase(String applicationId, String documentType, String downloadUrl) {
        DatabaseReference documentRef = applicationsRef.child(applicationId).child("documents").child(documentType);
        documentRef.setValue(downloadUrl);
    }

    private void saveApplicationDataToDatabase(String applicationId, String name, String age, String address, String contact, String purpose) {
        ApplicationData applicationData = new ApplicationData(name, age, address, contact, purpose);
        applicationsRef.child(applicationId).setValue(applicationData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showConfirmationDialog(applicationId);
                    } else {
                        Toast.makeText(ApplicationFormActivity.this, "Failed to submit application", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showConfirmationDialog(String applicationId) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation, null);

        ImageView ivSubmittedSymbol = dialogView.findViewById(R.id.iv_submitted_symbol);
        TextView tvConfirmationMessage = dialogView.findViewById(R.id.tv_confirmation_message);
        TextView tvApplicationId = dialogView.findViewById(R.id.tv_application_id);
        Button btnClose = dialogView.findViewById(R.id.btn_close);

        tvConfirmationMessage.setText("अर्ज यशस्वीपणे सबमिट करण्यात आला !");
        tvApplicationId.setText("अर्ज क्रमांक : " + applicationId);
        ivSubmittedSymbol.setImageResource(R.drawable.ic_check); // Add a checkmark icon in res/drawable/ic_check.xml

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        btnClose.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }
}

class ApplicationData {
    public String name;
    public String age;
    public String address;
    public String contact;
    public String purpose;

    public ApplicationData(String name, String age, String address, String contact, String purpose) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.contact = contact;
        this.purpose = purpose;
    }
}
