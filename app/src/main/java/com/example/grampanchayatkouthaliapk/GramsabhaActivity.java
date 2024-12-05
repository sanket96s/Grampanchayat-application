package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class GramsabhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gramsabha);

        LinearLayout eventItem1 = findViewById(R.id.eventItem1);
        LinearLayout eventItem2 = findViewById(R.id.eventItem2);
        LinearLayout eventItem3 = findViewById(R.id.eventItem3);
        LinearLayout eventItem4 = findViewById(R.id.eventItem4);

        eventItem1.setOnClickListener(v -> {
            String title = getString(R.string.event1_title);
            String description = getString(R.string.event1_description);
            openEventDetail(title, description);
        });

        eventItem2.setOnClickListener(v -> {
            String title = getString(R.string.event2_title);
            String description = getString(R.string.event2_description);
            openEventDetail(title, description);
        });

        eventItem3.setOnClickListener(v -> {
            String title = getString(R.string.event3_title);
            String description = getString(R.string.event3_description);
            openEventDetail(title, description);
        });

        eventItem4.setOnClickListener(v -> {
            String title = getString(R.string.event4_title);
            String description = getString(R.string.event4_description);
            openEventDetail(title, description);
        });
    }

    private void openEventDetail(String title, String description) {
        Intent intent = new Intent(this, EventGramDetailActivity.class);
        intent.putExtra("EVENT_TITLE", title);
        intent.putExtra("EVENT_DESCRIPTION", description);
        startActivity(intent);
    }
}
