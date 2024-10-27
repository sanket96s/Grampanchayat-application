
package com.example.grampanchayatkouthaliapk;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;




public class GovernmentSchemesActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private Handler handler;
    private Runnable runnable;
    private final int SLIDE_INTERVAL = 3000;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_schemes);// Ensure this layout exists

//        Toolbar toolbar;
//        toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);

//        drawerLayout = findViewById(R.id.drawer_layout);
//        ImageButton imageButton = findViewById(R.id.imageButton);
//
//        imageButton.setOnClickListener(v -> {
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//            } else {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


        viewFlipper = findViewById(R.id.viewFlipper);

        // Setup the handler to manage the sliding
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                viewFlipper.showNext(); // Show the next image
                handler.postDelayed(this, SLIDE_INTERVAL); // Repeat the action after the interval
            }
        };

        // Start the sliding
        handler.postDelayed(runnable, SLIDE_INTERVAL);



        TextView textJandhanYojana = findViewById(R.id.textView5);
        ImageView imageJandhanYojana = findViewById(R.id.imageView5);
        textJandhanYojana.setOnClickListener(v -> openScheme1InformationPage(v));
        imageJandhanYojana.setOnClickListener(v -> openScheme1InformationPage(v));

        TextView textAvasYojana = findViewById(R.id.textView3);
        ImageView imageAvasYojana = findViewById(R.id.imageView3);
        textAvasYojana.setOnClickListener(v -> openScheme6InformationPage(v));
        imageAvasYojana.setOnClickListener(v -> openScheme6InformationPage(v));

        TextView textAtalBhujalYojana = findViewById(R.id.textView4);
        ImageView imageAtalBhujalYojana = findViewById(R.id.imageView4);
        textAtalBhujalYojana.setOnClickListener(v -> openScheme5InformationPage(v));
        imageAtalBhujalYojana.setOnClickListener(v -> openScheme5InformationPage(v));

        TextView textGramVikasYojana = findViewById(R.id.textView);
        ImageView imageGramVikasYojana = findViewById(R.id.imageView);
        textGramVikasYojana.setOnClickListener(v -> openScheme4InformationPage(v));
        imageGramVikasYojana.setOnClickListener(v -> openScheme4InformationPage(v));

        TextView textJanArogyaYojana = findViewById(R.id.textView2);
        ImageView imageJanArogyaYojana = findViewById(R.id.imageView2);
        textJanArogyaYojana.setOnClickListener(v -> openScheme3InformationPage(v));
        imageJanArogyaYojana.setOnClickListener(v -> openScheme3InformationPage(v));

        TextView textSmartGramYojana = findViewById(R.id.textView6);
        ImageView imageSmartGramYojana = findViewById(R.id.imageView6);
        textSmartGramYojana.setOnClickListener(v -> openScheme2InformationPage(v));
        imageSmartGramYojana.setOnClickListener(v -> openScheme2InformationPage(v));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Stop the handler when activity is destroyed
    }
    public void openScheme1InformationPage(View v) {
        Intent intent = new Intent(GovernmentSchemesActivity.this, Scheme1InformationActivity.class);
        startActivity(intent);
    }

    public void openScheme2InformationPage(View v) {
        Intent intent = new Intent(GovernmentSchemesActivity.this, Scheme2InformationActivity.class);
        startActivity(intent);
    }
    public void openScheme3InformationPage(View v) {
        Intent intent = new Intent(GovernmentSchemesActivity.this, Scheme3InformationActivity.class);
        startActivity(intent);
    }
    public void openScheme4InformationPage(View v) {
        Intent intent = new Intent(GovernmentSchemesActivity.this, Scheme4InformationActivity.class);
        startActivity(intent);
    }
    public void openScheme5InformationPage(View v) {
        Intent intent = new Intent(GovernmentSchemesActivity.this, Scheme5InformationActivity.class);
        startActivity(intent);
    }
    public void openScheme6InformationPage(View v) {
        Intent intent = new Intent(GovernmentSchemesActivity.this, Scheme6InformationActivity.class);
        startActivity(intent);
    }
//    public void openApplicationTrackingPage(MenuItem item) {
//        Intent intent = new Intent(GovernmentSchemesActivity.this, ApplicationTrackingActivity.class);
//        startActivity(intent);
//    }

}
