package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EventGramDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_gram_detail);

        // Get the event title and description from the intent
        String eventTitle = getIntent().getStringExtra("EVENT_TITLE");
        String eventDescription = getIntent().getStringExtra("EVENT_DESCRIPTION");

        // Initialize TextViews
        TextView tvEventTitle = findViewById(R.id.tvEventTitle);
        TextView tvEventDescription = findViewById(R.id.tvEventDescription);

        // Set the event title and description to the TextViews
        if (eventTitle != null) {
            tvEventTitle.setText(eventTitle);
        }
        if (eventDescription != null) {
            tvEventDescription.setText(eventDescription);
        }

        // Find the btnRegister button in your layout
        Button btnRegister = findViewById(R.id.btnRegister);

        // Set an OnClickListener on the button
        btnRegister.setOnClickListener(v -> {
            // Google Form URL (replace with your actual Google Form URL)
            String googleFormURL = "https://docs.google.com/forms/d/e/1FAIpQLScTQc6Eas8wvY4Y4Ow1BgF5fjN_Y51PbqfGeGu9LQ-ieo5pew/viewform?usp=sf_link"; // Replace with your actual Google Form URL

            // Create an Intent to open the URL
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleFormURL));

            // Start the browser with the form
            startActivity(intent);
        });
    }
}
