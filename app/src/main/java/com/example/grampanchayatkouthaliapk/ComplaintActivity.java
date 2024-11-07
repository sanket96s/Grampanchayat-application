package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ComplaintActivity extends AppCompatActivity {

    private EditText etName, etAddress, etMobileNumber, etComplaint;
    private Button btnUpload;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

        // Initialize UI elements
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etComplaint = findViewById(R.id.etComplaint);
        btnUpload = findViewById(R.id.btnUpload);

        // Set onClickListener for the upload button
        btnUpload.setOnClickListener(view -> uploadComplaint());
    }

    private void uploadComplaint() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String mobileNumber = etMobileNumber.getText().toString().trim();
        String complaint = etComplaint.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(name)) {
            etName.setError("Please enter your name");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            etAddress.setError("Please enter your address");
            return;
        }
        if (TextUtils.isEmpty(mobileNumber)) {
            etMobileNumber.setError("Please enter your mobile number");
            return;
        }
        if (TextUtils.isEmpty(complaint)) {
            etComplaint.setError("Please enter your complaint");
            return;
        }

        // Create a unique ID for each complaint
        String complaintId = databaseReference.push().getKey();

        // Create a map to hold the complaint data
        Map<String, String> complaintData = new HashMap<>();
        complaintData.put("name", name);
        complaintData.put("address", address);
        complaintData.put("mobileNumber", mobileNumber);
        complaintData.put("complaint", complaint);

        if (complaintId != null) {
            // Save the complaint data to Firebase
            databaseReference.child(complaintId).setValue(complaintData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ComplaintActivity.this, "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
                    // Clear the input fields after successful submission
                    etName.setText("");
                    etAddress.setText("");
                    etMobileNumber.setText("");
                    etComplaint.setText("");
                } else {
                    Toast.makeText(ComplaintActivity.this, "Failed to submit complaint", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Failed to generate complaint ID", Toast.LENGTH_SHORT).show();
        }
    }
}
