package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();  // Initialize Firestore

        fullNameEditText = findViewById(R.id.fullName);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(view -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (validateInputs(fullName, email, password)) {
                // Handle registration logic
                registerUser(fullName, email, password);
            }
        });
    }

    private boolean validateInputs(String fullName, String email, String password) {
        boolean isValid = true;

        if (fullName.isEmpty()) {
            fullNameEditText.setError("पूर्ण नाव आवश्यक आहे");
            isValid = false;
        }

        if (email.isEmpty()) {
            emailEditText.setError("ई-मेल आवश्यक आहे");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("कृपया वैध ई-मेल प्रविष्ट करा");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("पासवर्ड आवश्यक आहे");
            isValid = false;
        } else if (password.length() < 8 || !password.matches(".*[A-Za-z].*") || !password.matches(".*[0-9].*")) {
            passwordEditText.setError("पासवर्ड किमान 8 वर्णांचा असावा आणि त्यात अक्षरे व संख्यांचा समावेश असावा");
            isValid = false;
        }

        return isValid;
    }

    private void registerUser(String fullName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Create a user object to store in Firestore
                        if (user != null) {
                            saveUserToFirestore(user.getUid(), fullName, email);
                        }
                    } else {
                        // If registration fails, display a message to the user.
                        if (task.getException() != null) {
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage != null && errorMessage.contains("email")) {
                                emailEditText.setError(errorMessage);
                            } else {
                                // Use a generic error message if the specific cause is unknown
                                emailEditText.setError("नोंदणी अयशस्वी: " + errorMessage);
                            }
                        }
                    }
                });
    }

    private void saveUserToFirestore(String userId, String fullName, String email) {
        // Create a user object
        User user = new User(fullName, email);

        // Save the user to Firestore
        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Call finish() if you want to close RegisterActivity
                })
                .addOnFailureListener(e -> {
                    fullNameEditText.setError("वापरकर्ता जतन करताना त्रुटी: " + e.getMessage());
                });
    }

    // User class to model user data
    public static class User {
        private String fullName;
        private String email;

        public User() {
            // Default constructor required for Firestore
        }

        public User(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }
    }
}
