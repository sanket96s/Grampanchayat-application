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

        String eventTitle = getIntent().getStringExtra("EVENT_TITLE");
        String eventDescription = getIntent().getStringExtra("EVENT_DESCRIPTION");

        TextView tvEventTitle = findViewById(R.id.tvEventTitle);
        TextView tvEventDescription = findViewById(R.id.tvEventDescription);

        if (eventTitle != null) {
            tvEventTitle.setText(eventTitle);
        }
        if (eventDescription != null) {
            tvEventDescription.setText(eventDescription);
        }

        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(EventGramDetailActivity.this, ProjectFormActivity.class);
            startActivity(intent);
        });
    }
}
