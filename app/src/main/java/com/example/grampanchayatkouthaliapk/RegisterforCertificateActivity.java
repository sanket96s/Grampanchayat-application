package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RegisterforCertificateActivity extends AppCompatActivity {

    private EditText fullNameEditText, parentNameEditText, dobEditText, addressEditText, mobileNumberEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox confirmCheckBox;
    private Button submitButton, uploadPhotoButton, uploadAadharButton, uploadRationCardButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private String documentType; // Variable to store document type

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
            documentTypeTextView.setText(getString(R.string.document_type_label) + " " + documentType);
        } else {
            documentTypeTextView.setText(getString(R.string.document_type_label) + " " + getString(R.string.please_select_document));
            documentType = getString(R.string.please_select_document); // Fallback value
        }

        firebaseAuth = FirebaseAuth.getInstance();
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

        // Set Google Forms links
        uploadPhotoButton.setText(getString(R.string.upload_photo));
        uploadAadharButton.setText(getString(R.string.upload_aadhar));
        uploadRationCardButton.setText(getString(R.string.upload_ration_card));

        submitButton.setText(getString(R.string.submit_button));

        uploadPhotoButton.setOnClickListener(view -> openGoogleForm("https://forms.gle/onaYYktF4GQ1Nush9"));
        uploadAadharButton.setOnClickListener(view -> openGoogleForm("https://forms.gle/nVJRYXHzWxv9Qzdv7"));
        uploadRationCardButton.setOnClickListener(view -> openGoogleForm("https://forms.gle/igmA5oTrDNXNguHC8"));

        submitButton.setOnClickListener(view -> {
            if (validateForm()) {
                submitToFirebase();
            }
        });
    }

    private void openGoogleForm(String formUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(formUrl));
        startActivity(browserIntent);
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(fullNameEditText.getText())) {
            fullNameEditText.setError(getString(R.string.full_name_hint));
            return false;
        }
        if (TextUtils.isEmpty(parentNameEditText.getText())) {
            parentNameEditText.setError(getString(R.string.parent_name_hint));
            return false;
        }
        if (TextUtils.isEmpty(dobEditText.getText())) {
            dobEditText.setError(getString(R.string.dob_hint));
            return false;
        }
        if (TextUtils.isEmpty(addressEditText.getText())) {
            addressEditText.setError(getString(R.string.address_hint));
            return false;
        }
        if (TextUtils.isEmpty(mobileNumberEditText.getText()) || mobileNumberEditText.getText().toString().length() != 10) {
            mobileNumberEditText.setError(getString(R.string.mobile_number_hint));
            return false;
        }
        if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getString(R.string.please_select_gender), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirmCheckBox.isChecked()) {
            Toast.makeText(this, getString(R.string.please_accept_terms), Toast.LENGTH_SHORT).show();
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
        String gender = ((RadioButton) findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            Map<String, String> applicationData = new HashMap<>();
            applicationData.put("Full Name", fullName);
            applicationData.put("Parent's Name", parentName);
            applicationData.put("Date of Birth", dob);
            applicationData.put("Address", address);
            applicationData.put("Mobile Number", mobileNumber);
            applicationData.put("Gender", gender);
            applicationData.put("Document Type", documentType);

            databaseReference.child(userId).push().setValue(applicationData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            sendConfirmationEmail(user.getEmail());
                            Toast.makeText(RegisterforCertificateActivity.this, getString(R.string.form_submission_success), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterforCertificateActivity.this, getString(R.string.form_submission_error), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void sendConfirmationEmail(String email) {
        // Send confirmation email logic here (using JavaMail API)
        // For simplicity, this can be customized further or removed depending on your backend.
    }
}
