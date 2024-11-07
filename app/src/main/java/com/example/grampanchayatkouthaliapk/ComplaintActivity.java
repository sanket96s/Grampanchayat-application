package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintActivity extends AppCompatActivity {

    // Firebase Database reference
    private DatabaseReference databaseReference;

    // UI components
    private EditText etName, etAddress, etMobileNumber, etComplaint;
    private RadioGroup radioGroupCategory;
    private Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);  // Link to the layout file

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");

        // Initialize UI components
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etComplaint = findViewById(R.id.etComplaint);
        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        btnUpload = findViewById(R.id.btnUpload);

        // Set onClick listener for the upload button
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComplaintData();
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
        String category = "";
        if (selectedCategoryId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedCategoryId);
            category = selectedRadioButton.getText().toString();
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
                            Toast.makeText(ComplaintActivity.this, "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
                            clearFields();
                        } else {
                            Toast.makeText(ComplaintActivity.this, "Failed to submit complaint", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void clearFields() {
        // Clear all input fields after successful submission
        etName.setText("");
        etAddress.setText("");
        etMobileNumber.setText("");
        etComplaint.setText("");
        radioGroupCategory.clearCheck();
    }
}
