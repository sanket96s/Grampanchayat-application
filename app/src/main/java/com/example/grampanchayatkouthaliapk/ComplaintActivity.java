package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ComplaintActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;

    private EditText etName, etAddress, etMobileNumber, etComplaint;
    private RadioGroup radioGroupCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");

        firebaseAuth = FirebaseAuth.getInstance();

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etComplaint = findViewById(R.id.etComplaint);
        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        Button btnUpload = findViewById(R.id.btnUpload);
        Button btnTrackComplaint = findViewById(R.id.btnTrackComplaint);

        btnUpload.setText(getString(R.string.submit_button));
        btnUpload.setOnClickListener(v -> generateUniqueComplaintId());

        btnTrackComplaint.setText(getString(R.string.track_button));
        btnTrackComplaint.setOnClickListener(v -> {
            Intent intent = new Intent(ComplaintActivity.this, TrackComplaintActivity.class);
            startActivity(intent);
        });
    }

    private void generateUniqueComplaintId() {
        Random random = new Random();
        int randomId = 1000000 + random.nextInt(9000000);
        String complaintId = String.valueOf(randomId);

        databaseReference.child(complaintId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    generateUniqueComplaintId();
                } else {
                    saveComplaintData(complaintId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ComplaintActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveComplaintData(String complaintId) {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String complaintDescription = etComplaint.getText().toString().trim();

        if (mobileNumber.length() != 10) {
            Toast.makeText(this, getString(R.string.error_mobile_number), Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedCategoryId = radioGroupCategory.getCheckedRadioButtonId();
        String category;
        if (selectedCategoryId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedCategoryId);
            category = selectedRadioButton.getText().toString();
        } else {
            Toast.makeText(this, getString(R.string.error_category), Toast.LENGTH_SHORT).show();
            return;
        }

        Complaint complaint = new Complaint(complaintId, name, address, mobileNumber, category, complaintDescription, "submitted");

        databaseReference.child(complaintId).setValue(complaint)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        etName.setText("");
                        etAddress.setText("");
                        etMobileNumber.setText("");
                        etComplaint.setText("");
                        radioGroupCategory.clearCheck();

                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            String userEmail = user.getEmail();
                            Resources res = getResources();
                            new SendEmailTask(userEmail, name, address, mobileNumber, category, complaintDescription, complaintId, res).execute();
                        }

                        Intent intent = new Intent(ComplaintActivity.this, SuccessActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Void> {

        private final String userEmail;
        private final String name;
        private final String address;
        private final String mobileNumber;
        private final String category;
        private final String complaintDescription;
        private final String complaintId;
        private final Resources res;

        SendEmailTask(String userEmail, String name, String address, String mobileNumber, String category, String complaintDescription, String complaintId, Resources res) {
            this.userEmail = userEmail;
            this.name = name;
            this.address = address;
            this.mobileNumber = mobileNumber;
            this.category = category;
            this.complaintDescription = complaintDescription;
            this.complaintId = complaintId;
            this.res = res;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final String username = "salunkep341@gmail.com";
            final String password = "nmrj whzm mhql gphe";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
                message.setSubject("Complaint Registered: " + complaintId);

                String messageBody = res.getString(R.string.complaint_id_label) + " " + complaintId + "\n\n"
                        + res.getString(R.string.name_label) + " " + name + "\n"
                        + res.getString(R.string.address_label) + " " + address + "\n"
                        + res.getString(R.string.mobile_number_label) + " " + mobileNumber + "\n"
                        + res.getString(R.string.category_label) + " " + category + "\n"
                        + res.getString(R.string.complaint_description_label) + " " + complaintDescription + "\n\n"
                        + "------------------------------\n\n";


                message.setText(messageBody);

                Transport.send(message);

            } catch (MessagingException e) {
                Log.e("MessagingError", "Error occurred while sending message", e);
            }

            return null;
        }
    }
}
