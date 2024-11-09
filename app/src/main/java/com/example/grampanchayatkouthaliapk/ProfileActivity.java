package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Opens activity_profile.xml

        // Retrieve the TextView for the email and name
        TextView profileEmailTextView = findViewById(R.id.profile_email);
        TextView profileNameTextView = findViewById(R.id.profile_name);

        // Get the currently logged-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Check if user is logged in
        if (user != null) {
            // Get the user's email and name
            String userEmail = user.getEmail();
            String userName = user.getDisplayName();

            // Display the user's email in the desired format
            profileEmailTextView.setText(getString(R.string.email_prefix) + userEmail);

            // Display the user's name in the format: "username: real_username"
            if (userName != null && !userName.isEmpty()) {
                profileNameTextView.setText(getString(R.string.username_format, user.getDisplayName()));
            } else {
                profileNameTextView.setText(getString(R.string.username_format, getString(R.string.username_not_set)));
            }
        } else {
            // If no user is logged in, show a message in Marathi
            profileEmailTextView.setText(getString(R.string.no_user_logged_in));
            profileNameTextView.setText(getString(R.string.username_format, getString(R.string.username_not_set)));
        }
    }
}
