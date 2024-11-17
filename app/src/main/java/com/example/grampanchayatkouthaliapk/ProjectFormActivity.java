package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProjectFormActivity extends AppCompatActivity {

    // Declare UI components
    private EditText etName, etEmail, etMessage;
    private Button btnSubmit;

    // Firebase database reference
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance().getReference();

        // Bind UI components
        etName = findViewById(R.id.feedback_etName);
        etEmail = findViewById(R.id.feedback_etEmail);
        etMessage = findViewById(R.id.feedback_etMessage);
        btnSubmit = findViewById(R.id.feedback_btnSubmit);

        // Set click listener for the submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate form fields
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String message = etMessage.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    etName.setError("Name is required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(message)) {
                    etMessage.setError("Message is required");
                    return;
                }

                // Submit data to Firebase Realtime Database
                submitFeedback(name, email, message);
            }
        });
    }

    private void submitFeedback(String name, String email, String message) {
        // Create a feedback object
        Feedback feedback = new Feedback(name, email, message);

        // Push the feedback data to the "feedbacks" node in Firebase Realtime Database
        database.child("feedbacks").push().setValue(feedback).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ProjectFormActivity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();

                // Navigate to the Success Activity after successful submission
                Intent intent = new Intent(ProjectFormActivity.this, SuccessActivity.class);
                startActivity(intent);
                finish();  // Finish current activity so the user can't go back to the form
            } else {
                Toast.makeText(ProjectFormActivity.this, "Failed to submit feedback. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
