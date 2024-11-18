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
            documentTypeTextView.setText("प्रमाणपत्र प्रकार: " + documentType);
        } else {
            documentTypeTextView.setText("प्रमाणपत्र प्रकार: माहिती नाही");
            documentType = "माहिती नाही"; // Fallback value
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

        generateUniqueIdAndSubmit(fullName, parentName, dob, address, mobileNumber, gender, documentType);
    }

    private void generateUniqueIdAndSubmit(String fullName, String parentName, String dob,
                                           String address, String mobileNumber, String gender, String documentType) {
        String uniqueId = String.format("%07d", new Random().nextInt(10000000));
        databaseReference.child(uniqueId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                generateUniqueIdAndSubmit(fullName, parentName, dob, address, mobileNumber, gender, documentType);
            } else {
                Map<String, String> applicationData = new HashMap<>();
                applicationData.put("FullName", fullName);
                applicationData.put("ParentName", parentName);
                applicationData.put("DOB", dob);
                applicationData.put("Address", address);
                applicationData.put("MobileNumber", mobileNumber);
                applicationData.put("Gender", gender);
                applicationData.put("ApplicationID", uniqueId);
                applicationData.put("CertificateType", documentType);

                databaseReference.child(uniqueId).setValue(applicationData)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(this, "डेटा यशस्वीरित्या सबमिट झाला", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                new SendEmailTask(user.getEmail(), fullName, parentName, dob, address, mobileNumber, gender, uniqueId, documentType).execute();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "डेटा सबमिट करण्यात समस्या आली", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Void> {
        private final String email, fullName, parentName, dob, address, mobileNumber, gender, uniqueId, documentType;

        SendEmailTask(String email, String fullName, String parentName, String dob,
                      String address, String mobileNumber, String gender, String uniqueId, String documentType) {
            this.email = email;
            this.fullName = fullName;
            this.parentName = parentName;
            this.dob = dob;
            this.address = address;
            this.mobileNumber = mobileNumber;
            this.gender = gender;
            this.uniqueId = uniqueId;
            this.documentType = documentType;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final String username = "salunkep341@gmail.com";
            final String password = "nmrj whzm mhql gphe";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Certificate Application Submitted Successfully");
                String content = "📑 अर्जाची माहिती:\n\n"
                        + "👤 नाव: " + fullName + "\n"
                        + "👨‍👩‍👦 पालकांचे नाव: " + parentName + "\n"
                        + "🎂 जन्मतारीख: " + dob + "\n"
                        + "📍 पत्ता: " + address + "\n"
                        + "📞 मोबाइल क्रमांक: " + mobileNumber + "\n"
                        + "👥 लिंग: " + gender + "\n"
                        + "📜 प्रमाणपत्र प्रकार: " + documentType + "\n"
                        + "🆔 अर्ज क्रमांक: " + uniqueId + "\n\nधन्यवाद!";

                message.setText(content);
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
