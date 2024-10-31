package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Show splash screen with video
        setContentView(R.layout.activity_splash);
        playSplashVideo();
    }

    private void playSplashVideo() {
        videoView = findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.redirectpage);
        videoView.setVideoURI(video);

        videoView.setOnPreparedListener(mp -> videoView.start());

        // Handle errors during playback
        videoView.setOnErrorListener((mp, what, extra) -> {
            proceedToCheckAuthentication();
            return true; // Indicates that we handled the error
        });

        // Transition after video playback ends or after 5 seconds
        videoView.setOnCompletionListener(mp -> proceedToCheckAuthentication());
        new Handler().postDelayed(this::proceedToCheckAuthentication, 2000); // 2000 milliseconds = 5 seconds
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause(); // Pause video when activity is paused
        }
    }

    private void proceedToCheckAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intent;
        if (currentUser == null) {
            // User is not authenticated, go to login page
            intent = new Intent(MainActivity.this, LoginActivity.class);
        } else {
            // User is authenticated, go to main page
            intent = new Intent(MainActivity.this, MainPageActivity.class);
        }
        startActivity(intent);
        finish(); // Close this activity
    }
}