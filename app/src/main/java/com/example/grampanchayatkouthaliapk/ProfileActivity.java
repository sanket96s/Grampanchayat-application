package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso; // Import Picasso for image loading

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Set the layout

        // Retrieve the TextViews for email and name
        TextView profileEmailTextView = findViewById(R.id.profile_email);
        TextView profileNameTextView = findViewById(R.id.profile_name);
        ImageView profileImageView = findViewById(R.id.profile_picture); // For profile picture

        // Get the currently logged-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Check if user is logged in
        if (user != null) {
            // Get the user's email and name
            String userEmail = user.getEmail();
            String userName = user.getDisplayName();
            String userPhotoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;

            // Display the user's email using a formatted string resource
            profileEmailTextView.setText(getString(R.string.email_format, userEmail));

            // Display the user's name using a formatted string resource
            if (userName != null && !userName.isEmpty()) {
                profileNameTextView.setText(getString(R.string.username_format, userName));
            } else {
                profileNameTextView.setText(getString(R.string.username_format, getString(R.string.username_not_set)));
            }

            // Load the user's profile picture using Picasso
            if (userPhotoUrl != null) {
                Picasso.get().load(userPhotoUrl).into(profileImageView); // Load image using Picasso
            } else {
                // If no photo URL, set a default image
                profileImageView.setImageResource(R.drawable.accountinfo);
            }
        } else {
            // If no user is logged in, show a message in Marathi
            profileEmailTextView.setText(getString(R.string.no_user_logged_in));
            profileNameTextView.setText(getString(R.string.username_format, getString(R.string.username_not_set)));

            // Optionally, set a default image for the profile picture
            profileImageView.setImageResource(R.drawable.accountinfo);
        }
    }
}
