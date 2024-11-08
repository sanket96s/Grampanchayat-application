package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ComplaintActivity extends AppCompatActivity {

    // Firebase Database reference
    private DatabaseReference databaseReference;

    // Firebase Authentication
    private FirebaseAuth firebaseAuth;

    // UI components
    private EditText etName, etAddress, etMobileNumber, etComplaint;
    private RadioGroup radioGroupCategory;
    private Button btnUpload, btnTrackComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);  // Link to the layout file

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etComplaint = findViewById(R.id.etComplaint);
        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        btnUpload = findViewById(R.id.btnUpload);
        btnTrackComplaint = findViewById(R.id.btnTrackComplaint);

        // Set onClick listener for the upload button
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComplaintData();
            }
        });

        // Set onClick listener for the track complaint button
        btnTrackComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComplaintActivity.this, TrackComplaintActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveComplaintData() {
        // Get input values from EditText fields
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String complaintDescription = etComplaint.getText().toString().trim();

        // Validate that all required fields are filled in
        if (name.isEmpty() || address.isEmpty() || mobileNumber.isEmpty() || complaintDescription.isEmpty()) {
            Toast.makeText(this, "कृपया सर्व माहिती भरा.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected radio button text for complaint category
        int selectedCategoryId = radioGroupCategory.getCheckedRadioButtonId();
        String category;
        if (selectedCategoryId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedCategoryId);
            category = selectedRadioButton.getText().toString();
        } else {
            category = "";
        }

        // Generate unique complaint ID for Firebase database entry
        String complaintId = databaseReference.push().getKey();

        // Check if complaintId is valid
        if (complaintId != null) {
            // Create a complaint object to hold all information
            Complaint complaint = new Complaint(complaintId, name, address, mobileNumber, category, complaintDescription, "submitted");

            // Store the complaint object in Firebase
            databaseReference.child(complaintId).setValue(complaint)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Display success message
                            Toast.makeText(this, "तक्रार यशस्वीरित्या नोंदवली.", Toast.LENGTH_SHORT).show();
                            // Clear input fields after successful submission
                            etName.setText("");
                            etAddress.setText("");
                            etMobileNumber.setText("");
                            etComplaint.setText("");
                            radioGroupCategory.clearCheck();

                            // Retrieve logged-in user's email
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                String userEmail = user.getEmail();
                                // Send email with complaint details
                                new SendEmailTask(userEmail, name, address, mobileNumber, category, complaintDescription, complaintId).execute();
                            } else {
                                Toast.makeText(this, "वापरकर्ता लॉग इन नाही.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Display error message in case of failure
                            Toast.makeText(this, "तक्रार नोंदवताना त्रुटी आली.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Void> {

        private final String userEmail;
        private final String name;
        private final String address;
        private final String mobileNumber;
        private final String category;
        private final String complaintDescription;
        private final String complaintId;

        SendEmailTask(String userEmail, String name, String address, String mobileNumber, String category, String complaintDescription, String complaintId) {
            this.userEmail = userEmail;
            this.name = name;
            this.address = address;
            this.mobileNumber = mobileNumber;
            this.category = category;
            this.complaintDescription = complaintDescription;
            this.complaintId = complaintId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final String username = "salunkep341@gmail.com";  // Your email address
            final String password = "nmrj whzm mhql gphe";  // Your email password

            // Set up properties for the email
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Create a session with authentication
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                // Create a new email message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
                message.setSubject("नवीन तक्रार नोंदवली (Complaint ID: " + complaintId + ")");
                message.setText("तक्रार तपशील:\n\n" +
                        "नाव: " + name + "\n" +
                        "पत्ता: " + address + "\n" +
                        "मोबाईल नंबर: " + mobileNumber + "\n" +
                        "तक्रारीची श्रेणी: " + category + "\n" +
                        "तक्रार: " + complaintDescription + "\n" +
                        "तक्रार क्रमांक: " + complaintId);

                // Send the email
                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
