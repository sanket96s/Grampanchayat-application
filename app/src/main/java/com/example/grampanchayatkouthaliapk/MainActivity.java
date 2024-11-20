package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_splash);
        playSplashVideo();
    }

    private void playSplashVideo() {
        videoView = findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.redirectpage);
        videoView.setVideoURI(video);

        videoView.setOnPreparedListener(mp -> videoView.start());

        videoView.setOnErrorListener((mp, what, extra) -> {
            proceedToCheckAuthentication();
            return true;
        });

        videoView.setOnCompletionListener(mp -> proceedToCheckAuthentication());
        new Handler().postDelayed(this::proceedToCheckAuthentication, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
    }

    private void proceedToCheckAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intent;
        if (currentUser == null) {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(MainActivity.this, MainPageActivity.class);
        }
        startActivity(intent);
        finish();
    }
}