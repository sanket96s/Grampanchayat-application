package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProjectDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        ImageView projectImage = findViewById(R.id.sample_photo);
        TextView projectTitle = findViewById(R.id.project_title);
        TextView projectDescription = findViewById(R.id.project_description);
        TextView projectObjectives = findViewById(R.id.project_objectives);
        TextView projectStartDate = findViewById(R.id.project_start_date);
        TextView projectCompletionDate = findViewById(R.id.project_completion_date);
        TextView projectStatus = findViewById(R.id.project_status);
        TextView projectBudget = findViewById(R.id.project_budget);
        TextView projectStakeholders = findViewById(R.id.project_stakeholders);
        TextView projectLocation = findViewById(R.id.project_location);

        // Retrieve data passed to this activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectImage.setImageResource(extras.getInt("PROJECT_IMAGE"));
            projectTitle.setText(extras.getString("PROJECT_TITLE"));
            projectDescription.setText(extras.getString("PROJECT_DESCRIPTION"));
            projectObjectives.setText(extras.getString("PROJECT_OBJECTIVES"));
            projectStartDate.setText(extras.getString("PROJECT_START_DATE"));
            projectCompletionDate.setText(extras.getString("PROJECT_COMPLETION_DATE"));
            projectStatus.setText(extras.getString("PROJECT_STATUS"));
            projectBudget.setText(extras.getString("PROJECT_BUDGET"));
            projectStakeholders.setText(extras.getString("PROJECT_STAKEHOLDERS"));
            projectLocation.setText(extras.getString("PROJECT_LOCATION"));
        }

        // Set up the feedback button to open Google Form
        Button feedbackButton = findViewById(R.id.feedback_button);
        feedbackButton.setOnClickListener(v -> {
            // Replace with your Google Form URL
            String googleFormUrl = "https://docs.google.com/forms/d/e/1FAIpQLSc_MKvGfF2Jm8f5AI5LwmmkZ8rM0RxpC8ITrbIBy1bWMd08Cg/viewform?usp=sf_link";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleFormUrl));
            startActivity(intent);
        });
    }
}
