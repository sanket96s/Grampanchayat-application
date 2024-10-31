package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Handle registration logic
                registerUser(fullName, email, password);
            }
        });
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
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegisterActivity.this, "Error saving user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
