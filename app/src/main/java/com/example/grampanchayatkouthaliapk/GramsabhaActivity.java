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
        LinearLayout eventItem3 = findViewById(R.id.eventItem3);
        LinearLayout eventItem4 = findViewById(R.id.eventItem4);

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

        eventItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventDetail("स्वच्छता मोहीम", "या प्रकल्पाचा उद्देश गावात स्वच्छता आणि स्वच्छतेचे महत्त्व वाढविणे आहे.");
            }
        });

        eventItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventDetail("आरोग्य तपासणी शिबिर", "या प्रकल्पाचा उद्देश गावातील नागरिकांचे आरोग्य तपासणी सेवा उपलब्ध करणे आहे.");
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
