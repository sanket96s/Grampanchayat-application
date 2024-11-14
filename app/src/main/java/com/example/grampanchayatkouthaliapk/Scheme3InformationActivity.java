package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Scheme3InformationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jan_arogya_yojna);
        Button applyButton = findViewById(R.id.applyapplication);
        applyButton.setOnClickListener(v -> openApplicationPage(v));
    }

    public void openApplicationPage(View v) {
        Intent intent = new Intent(Scheme3InformationActivity.this, ApplicationFormActivity.class);
        startActivity(intent);

    }
    }
}
