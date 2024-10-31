package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ProjectsActivity extends AppCompatActivity {

    private ArrayList<String> projectTitles;
    private ArrayList<String> projectDescriptions;
    private ArrayList<Integer> projectImages; // New list for project images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects); // Ensure this matches your XML layout file name

        // Initialize project titles, descriptions, and images
        projectTitles = new ArrayList<>();
        projectDescriptions = new ArrayList<>();
        projectImages = new ArrayList<>();

        // Add sample data (you can replace these with actual project data)
        projectTitles.add("Water Conservation Project");
        projectDescriptions.add("This project aims to conserve water resources in the region.");
        projectImages.add(R.drawable.scheme1); // Replace with your image resource

        projectTitles.add("Road Construction Project");
        projectDescriptions.add("This project is focused on building new roads to improve connectivity.");
        projectImages.add(R.drawable.scheme1); // Replace with your image resource

        // Reference the ListView
        ListView listView = findViewById(R.id.projectListView);

        // Use a custom adapter to display project titles, descriptions, and images
        CustomProjectAdapter adapter = new CustomProjectAdapter();
        listView.setAdapter(adapter);

        // Handle item click to open ProjectDetailActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProjectsActivity.this, ProjectDetailActivity.class);
                intent.putExtra("PROJECT_TITLE", projectTitles.get(position));
                intent.putExtra("PROJECT_DESCRIPTION", projectDescriptions.get(position));
                startActivity(intent);
            }
        });
    }

    private class CustomProjectAdapter extends ArrayAdapter<String> {
        public CustomProjectAdapter() {
            super(ProjectsActivity.this, R.layout.list_item_project, projectTitles);
        }

        @Override
        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_project, parent, false);
            }

            // Reference the views in the custom layout
            ImageView projectImage = convertView.findViewById(R.id.project_image);
            TextView projectTitle = convertView.findViewById(R.id.project_title);
            TextView projectDescription = convertView.findViewById(R.id.project_description);

            // Set the data to the views
            projectImage.setImageResource(projectImages.get(position));
            projectTitle.setText(projectTitles.get(position));
            projectDescription.setText(projectDescriptions.get(position));

            return convertView;
        }
    }
}
