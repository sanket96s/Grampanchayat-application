package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Scheme5InformationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atal_bhujal);


        Button applyButton = findViewById(R.id.applyapplication);
        applyButton.setOnClickListener(v -> openApplicationPage(v));
    }

    public void openApplicationPage(View v) {
        Intent intent = new Intent(Scheme5InformationActivity.this, ApplicationFormActivity.class);
        startActivity(intent);
    }

}
