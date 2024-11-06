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

        // Initialize project data in Marathi with unique titles and descriptions
        projectTitles = new ArrayList<>();
        projectDescriptions = new ArrayList<>();
        projectImages = new ArrayList<>();

        // Add sample data
        projectTitles.add("जल संवर्धन प्रकल्प");
        projectDescriptions.add("या प्रकल्पाचा उद्देश पाण्याचे संरक्षण करणे आहे.");
        projectImages.add(R.drawable.scheme1);

        projectTitles.add("रस्ता बांधणी प्रकल्प");
        projectDescriptions.add("या प्रकल्पाचा उद्देश नवीन रस्त्यांचे बांधकाम करून वाहतुकीची सुविधा सुधारणे आहे.");
        projectImages.add(R.drawable.scheme2);

        projectTitles.add("शाळा पुनर्निर्माण प्रकल्प");
        projectDescriptions.add("या प्रकल्पाच्या माध्यमातून शाळांचे पुनर्निर्माण व सुधारणे करण्यात येणार आहे.");
        projectImages.add(R.drawable.scheme3);

        projectTitles.add("कृषी सुधारणा प्रकल्प");
        projectDescriptions.add("कृषी उत्पादन वाढवण्यासाठी सुधारित तंत्रज्ञानाचा वापर करणे.");
        projectImages.add(R.drawable.scheme4);

        ListView listView = findViewById(R.id.projectListView);
        CustomProjectAdapter adapter = new CustomProjectAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProjectsActivity.this, ProjectDetailActivity.class);

                switch (position) {
                    case 0:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.scheme1);
                        intent.putExtra("PROJECT_TITLE", "जल संवर्धन प्रकल्प");
                        intent.putExtra("PROJECT_DESCRIPTION", "या प्रकल्पाचा उद्देश पाण्याचे संरक्षण करणे आहे.");
                        intent.putExtra("PROJECT_OBJECTIVES", "जलसंवर्धन आणि व्यवस्थापन.");
                        intent.putExtra("PROJECT_START_DATE", "01/01/2024");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "31/12/2024");
                        intent.putExtra("PROJECT_STATUS", "प्रगतीमध्ये");
                        intent.putExtra("PROJECT_BUDGET", "₹10,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "ग्रामीण विकास संस्था");
                        intent.putExtra("PROJECT_LOCATION", "कौताळी गाव");
                        break;

                    case 1:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.scheme2);
                        intent.putExtra("PROJECT_TITLE", "रस्ता बांधणी प्रकल्प");
                        intent.putExtra("PROJECT_DESCRIPTION", "या प्रकल्पाचा उद्देश नवीन रस्त्यांचे बांधकाम करून वाहतुकीची सुविधा सुधारणे आहे.");
                        intent.putExtra("PROJECT_OBJECTIVES", "वाहतूक सुधारणा.");
                        intent.putExtra("PROJECT_START_DATE", "01/02/2024");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "01/08/2024");
                        intent.putExtra("PROJECT_STATUS", "पूर्ण");
                        intent.putExtra("PROJECT_BUDGET", "₹25,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "पंचायत समिती");
                        intent.putExtra("PROJECT_LOCATION", "कौताळी गाव");
                        break;

                    case 2:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.scheme3);
                        intent.putExtra("PROJECT_TITLE", "शाळा पुनर्निर्माण प्रकल्प");
                        intent.putExtra("PROJECT_DESCRIPTION", "या प्रकल्पाच्या माध्यमातून शाळांचे पुनर्निर्माण व सुधारणे करण्यात येणार आहे.");
                        intent.putExtra("PROJECT_OBJECTIVES", "शिक्षणाच्या सुविधा सुधारणे.");
                        intent.putExtra("PROJECT_START_DATE", "01/03/2024");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "31/12/2024");
                        intent.putExtra("PROJECT_STATUS", "प्रगतीमध्ये");
                        intent.putExtra("PROJECT_BUDGET", "₹15,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "शिक्षण विभाग");
                        intent.putExtra("PROJECT_LOCATION", "कौताळी गाव");
                        break;

                    case 3:
                        intent.putExtra("PROJECT_IMAGE", R.drawable.scheme4);
                        intent.putExtra("PROJECT_TITLE", "कृषी सुधारणा प्रकल्प");
                        intent.putExtra("PROJECT_DESCRIPTION", "कृषी उत्पादन वाढवण्यासाठी सुधारित तंत्रज्ञानाचा वापर करणे.");
                        intent.putExtra("PROJECT_OBJECTIVES", "सुधारित तंत्रज्ञानाचा वापर.");
                        intent.putExtra("PROJECT_START_DATE", "01/04/2024");
                        intent.putExtra("PROJECT_COMPLETION_DATE", "31/12/2024");
                        intent.putExtra("PROJECT_STATUS", "प्रगतीमध्ये");
                        intent.putExtra("PROJECT_BUDGET", "₹20,00,000");
                        intent.putExtra("PROJECT_STAKEHOLDERS", "कृषी विभाग");
                        intent.putExtra("PROJECT_LOCATION", "कौताळी गाव");
                        break;
                }

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

            ImageView projectImage = convertView.findViewById(R.id.project_image);
            TextView projectTitle = convertView.findViewById(R.id.project_title);
            TextView projectDescription = convertView.findViewById(R.id.project_description);

            projectImage.setImageResource(projectImages.get(position));
            projectTitle.setText(projectTitles.get(position));
            projectDescription.setText(projectDescriptions.get(position));

            projectTitle.setTypeface(projectTitle.getTypeface(), android.graphics.Typeface.BOLD);
            projectDescription.setTextColor(getResources().getColor(android.R.color.black));

            return convertView;
        }
    }
}
