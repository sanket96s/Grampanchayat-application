package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
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

        // Set an OnClickListener on the button to open EventFormActivity
        btnRegister.setOnClickListener(v -> {
            // Create an intent to open EventFormActivity
            Intent intent = new Intent(EventGramDetailActivity.this, EventFormActivity.class);
            startActivity(intent); // Start the EventFormActivity
        });
    }
}
