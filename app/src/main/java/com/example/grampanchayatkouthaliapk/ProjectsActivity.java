package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ProjectsActivity extends AppCompatActivity {

    private ArrayList<String> projectTitles;
    private ArrayList<String> projectDescriptions;
    private ArrayList<Integer> projectImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        // Initialize project data
        projectTitles = new ArrayList<>();
        projectDescriptions = new ArrayList<>();
        projectImages = new ArrayList<>();

        // Sample project data (replace with your actual data)
        String projectTitle1 = "Water Supply Improvement";
        String projectDescription1 = "Project to enhance water supply to the village.";
        String projectTitle2 = "Road Reconstruction";
        String projectDescription2 = "Construction of new roads in rural areas.";
        String projectTitle3 = "School Reconstruction";
        String projectDescription3 = "Reconstruction of the local school building.";
        String projectTitle4 = "Agriculture Development";
        String projectDescription4 = "Improving agricultural practices for better yield.";

        // Add data to lists
        projectTitles.add(projectTitle1);
        projectDescriptions.add(projectDescription1);
        projectImages.add(R.drawable.waterproject);

        projectTitles.add(projectTitle2);
        projectDescriptions.add(projectDescription2);
        projectImages.add(R.drawable.roadproject);

        projectTitles.add(projectTitle3);
        projectDescriptions.add(projectDescription3);
        projectImages.add(R.drawable.schoolreconstruction);

        projectTitles.add(projectTitle4);
        projectDescriptions.add(projectDescription4);
        projectImages.add(R.drawable.agricultureproject);

        // Initialize ListView and set custom adapter
        ListView listView = findViewById(R.id.projectListView);
        CustomProjectAdapter adapter = new CustomProjectAdapter();
        listView.setAdapter(adapter);

        // Handle item click to pass data to ProjectDetailActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProjectsActivity.this, ProjectDetailActivity.class);

                // Send project details based on position
                switch (position) {
                    case 0:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.waterproject);
                        intent.putExtra("PROJECT_TITLE", projectTitle1);
                        intent.putExtra("PROJECT_DESCRIPTION", projectDescription1);
                        intent.putExtra("PROJECT_OBJECTIVES", "Improvement of water supply.");
                        intent.putExtra("PROJECT_START_DATE", "01 Jan 2023");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "31 Dec 2023");
                        intent.putExtra("PROJECT_STATUS", "In Progress");
                        intent.putExtra("PROJECT_BUDGET", "₹50,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "Municipality, Local Farmers");
                        intent.putExtra("PROJECT_LOCATION", "Kouthali Village");
                        break;
                    case 1:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.roadproject);
                        intent.putExtra("PROJECT_TITLE", projectTitle2);
                        intent.putExtra("PROJECT_DESCRIPTION", projectDescription2);
                        intent.putExtra("PROJECT_OBJECTIVES", "Reconstruction of roads.");
                        intent.putExtra("PROJECT_START_DATE", "01 Feb 2023");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "31 Dec 2023");
                        intent.putExtra("PROJECT_STATUS", "Completed");
                        intent.putExtra("PROJECT_BUDGET", "₹30,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "Government, Local Authorities");
                        intent.putExtra("PROJECT_LOCATION", "Rural Area");
                        break;
                    case 2:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.schoolreconstruction);
                        intent.putExtra("PROJECT_TITLE", projectTitle3);
                        intent.putExtra("PROJECT_DESCRIPTION", projectDescription3);
                        intent.putExtra("PROJECT_OBJECTIVES", "Reconstruction of local school.");
                        intent.putExtra("PROJECT_START_DATE", "01 Mar 2023");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "31 Oct 2023");
                        intent.putExtra("PROJECT_STATUS", "In Progress");
                        intent.putExtra("PROJECT_BUDGET", "₹40,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "School Board, Local Government");
                        intent.putExtra("PROJECT_LOCATION", "Kouthali Village");
                        break;
                    case 3:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.agricultureproject);
                        intent.putExtra("PROJECT_TITLE", projectTitle4);
                        intent.putExtra("PROJECT_DESCRIPTION", projectDescription4);
                        intent.putExtra("PROJECT_OBJECTIVES", "Agricultural development program.");
                        intent.putExtra("PROJECT_START_DATE", "01 Apr 2023");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "31 Mar 2024");
                        intent.putExtra("PROJECT_STATUS", "In Progress");
                        intent.putExtra("PROJECT_BUDGET", "₹60,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "Agriculture Department, Local Farmers");
                        intent.putExtra("PROJECT_LOCATION", "Kouthali Village");
                        break;
                }

                startActivity(intent);
            }
        });
    }

    // Custom Adapter to bind data to ListView
    private class CustomProjectAdapter extends ArrayAdapter<String> {
        public CustomProjectAdapter() {
            super(ProjectsActivity.this, R.layout.list_item_project, projectTitles);
        }

        @Override
        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_project, parent, false);
            }

            // Get references for the ListView items
            ImageView projectImage = convertView.findViewById(R.id.project_image);
            TextView projectTitle = convertView.findViewById(R.id.project_title);
            TextView projectDescription = convertView.findViewById(R.id.project_description);

            // Set the data
            projectImage.setImageResource(projectImages.get(position));
            projectTitle.setText(projectTitles.get(position));
            projectDescription.setText(projectDescriptions.get(position));

            // Customize text style and color
            projectTitle.setTypeface(projectTitle.getTypeface(), android.graphics.Typeface.BOLD);
            projectDescription.setTextColor(getResources().getColor(android.R.color.black));

            return convertView;
        }
    }
}
