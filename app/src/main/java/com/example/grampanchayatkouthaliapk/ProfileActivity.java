package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso; 

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Retrieve the TextViews for email and name
        TextView profileEmailTextView = findViewById(R.id.profile_email);
        TextView profileNameTextView = findViewById(R.id.profile_name);
        ImageView profileImageView = findViewById(R.id.profile_picture); // For profile picture

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userEmail = user.getEmail();
            String userName = user.getDisplayName();
            String userPhotoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;

            profileEmailTextView.setText(getString(R.string.email_format, userEmail));

            if (userName != null && !userName.isEmpty()) {
                profileNameTextView.setText(getString(R.string.username_format, userName));
            } else {
                profileNameTextView.setText(getString(R.string.username_format, getString(R.string.username_not_set)));
            }

            if (userPhotoUrl != null) {
                Picasso.get().load(userPhotoUrl).into(profileImageView); // Load image using Picasso
            } else {
                profileImageView.setImageResource(R.drawable.accountinfo);
            }
        } else {
            profileEmailTextView.setText(getString(R.string.no_user_logged_in));
            profileNameTextView.setText(getString(R.string.username_format, getString(R.string.username_not_set)));

            profileImageView.setImageResource(R.drawable.accountinfo);
        }
    }
}
