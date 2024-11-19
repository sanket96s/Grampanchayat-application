// SchemeDetailActivity.java
package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SchemeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_detail);

        TextView schemeTitle = findViewById(R.id.schemeTitle);
        ImageView schemeImage = findViewById(R.id.schemeImage);
        TextView schemeInfo = findViewById(R.id.schemeInfo);
        TextView eligibility = findViewById(R.id.eligibility);
        TextView requiredDocs = findViewById(R.id.requiredDocs);

        // Get data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schemeTitle.setText(extras.getString("title"));
            schemeImage.setImageResource(extras.getInt("image"));
            schemeInfo.setText(extras.getString("info"));
            eligibility.setText(extras.getString("eligibility"));
            requiredDocs.setText(extras.getString("requiredDocs"));
        }
    }
}
