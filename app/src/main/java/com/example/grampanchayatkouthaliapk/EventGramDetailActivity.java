package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
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
    }
}
