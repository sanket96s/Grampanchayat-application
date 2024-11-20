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
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fullNameEditText = findViewById(R.id.fullName);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(view -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (validateInputs(fullName, email, password)) {
                registerUser(fullName, email, password);
            }
        });
    }

    private boolean validateInputs(String fullName, String email, String password) {
        boolean isValid = true;

        if (fullName.isEmpty()) {
            fullNameEditText.setError(getString(R.string.full_name_required));
            isValid = false;
        }

        if (email.isEmpty()) {
            emailEditText.setError(getString(R.string.email_required));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.invalid_email));
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError(getString(R.string.password_required));
            isValid = false;
        } else if (password.length() < 8 || !password.matches(".*[A-Za-z].*") || !password.matches(".*[0-9].*")) {
            passwordEditText.setError(getString(R.string.password_criteria));
            isValid = false;
        }

        return isValid;
    }

    private void registerUser(String fullName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            saveUserToFirestore(user.getUid(), fullName, email);
                        }
                    } else {
                        if (task.getException() != null) {
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage != null && errorMessage.contains("email")) {
                                emailEditText.setError(errorMessage);
                            } else {
                                emailEditText.setError(getString(R.string.registration_failed, errorMessage));
                            }
                        }
                    }
                });
    }

    private void saveUserToFirestore(String userId, String fullName, String email) {
        User user = new User(fullName, email);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    fullNameEditText.setError(getString(R.string.user_save_error, e.getMessage()));
                });
    }

    public static class User {
        private String fullName;
        private String email;

        public User() {
        }

        public User(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }
}
