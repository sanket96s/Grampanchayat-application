package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProjectFormActivity extends AppCompatActivity {

    private EditText etName, etEmail, etMessage;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);

        database = FirebaseDatabase.getInstance().getReference();

        etName = findViewById(R.id.feedback_etName);
        etEmail = findViewById(R.id.feedback_etEmail);
        etMessage = findViewById(R.id.feedback_etMessage);
        Button btnSubmit = findViewById(R.id.feedback_btnSubmit);

        btnSubmit.setOnClickListener(v -> {
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

            submitFeedback(name, email, message);
        });
    }

    private void submitFeedback(String name, String email, String message) {
        Feedback feedback = new Feedback(name, email, message);

        database.child("feedbacks").push().setValue(feedback).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ProjectFormActivity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProjectFormActivity.this, SuccessActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(ProjectFormActivity.this, "Failed to submit feedback. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
