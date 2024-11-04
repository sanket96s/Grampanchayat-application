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

        // Initialize clickable event items
        LinearLayout eventItem1 = findViewById(R.id.eventItem1);
        LinearLayout eventItem2 = findViewById(R.id.eventItem2);

        // Set onClickListeners for the event items
        eventItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventDetail("पाणलोट व्यवस्थापन प्रकल्प", "या प्रकल्पाचा उद्देश पाण्याचे संवर्धन करणे आहे.");
            }
        });

        eventItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventDetail("रस्ता बांधणी प्रकल्प", "या प्रकल्पाचा उद्देश नवीन रस्ते बांधून वाहतुकीची सुविधा सुधारणे आहे.");
            }
        });
    }

    // Method to open EventDetailActivity with event details
    private void openEventDetail(String title, String description) {
        Intent intent = new Intent(this, EventGramDetailActivity.class);
        intent.putExtra("EVENT_TITLE", title);
        intent.putExtra("EVENT_DESCRIPTION", description);
        startActivity(intent);
    }
}

