// GovernmentSchemesActivity.java
package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GovernmentSchemesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_schemes); // Ensure this layout exists

        RecyclerView recyclerView = findViewById(R.id.schemeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Scheme> schemeList = getSchemeList();

        SchemeAdapter adapter = new SchemeAdapter(schemeList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<Scheme> getSchemeList() {
        List<Scheme> schemes = new ArrayList<>();

        String housingTitle = getString(R.string.housing_scheme_title);
        String housingDescription = getString(R.string.housing_scheme_description);
        String housingEligibility = getString(R.string.housing_scheme_eligibility);
        String housingRequiredDocs = getString(R.string.housing_scheme_required_docs);
        schemes.add(new Scheme(housingTitle, housingDescription, R.drawable.housingscheme, housingEligibility, housingRequiredDocs));

        String waterTitle = getString(R.string.water_scheme_title);
        String waterDescription = getString(R.string.water_scheme_description);
        String waterEligibility = getString(R.string.water_scheme_eligibility);
        String waterRequiredDocs = getString(R.string.water_scheme_required_docs);
        schemes.add(new Scheme(waterTitle, waterDescription, R.drawable.waterscheme, waterEligibility, waterRequiredDocs));

        String sanitationTitle = getString(R.string.sanitation_scheme_title);
        String sanitationDescription = getString(R.string.sanitation_scheme_description);
        String sanitationEligibility = getString(R.string.sanitation_scheme_eligibility);
        String sanitationRequiredDocs = getString(R.string.sanitation_scheme_required_docs);
        schemes.add(new Scheme(sanitationTitle, sanitationDescription, R.drawable.sanitizationscheme, sanitationEligibility, sanitationRequiredDocs));

        String healthTitle = getString(R.string.health_scheme_title);
        String healthDescription = getString(R.string.health_scheme_description);
        String healthEligibility = getString(R.string.health_scheme_eligibility);
        String healthRequiredDocs = getString(R.string.health_scheme_required_docs);
        schemes.add(new Scheme(healthTitle, healthDescription, R.drawable.healthscheme, healthEligibility, healthRequiredDocs));

        String educationTitle = getString(R.string.education_scheme_title);
        String educationDescription = getString(R.string.education_scheme_description);
        String educationEligibility = getString(R.string.education_scheme_eligibility);
        String educationRequiredDocs = getString(R.string.education_scheme_required_docs);
        schemes.add(new Scheme(educationTitle, educationDescription, R.drawable.educationscheme, educationEligibility, educationRequiredDocs));

        String employmentTitle = getString(R.string.employment_scheme_title);
        String employmentDescription = getString(R.string.employment_scheme_description);
        String employmentEligibility = getString(R.string.employment_scheme_eligibility);
        String employmentRequiredDocs = getString(R.string.employment_scheme_required_docs);
        schemes.add(new Scheme(employmentTitle, employmentDescription, R.drawable.employmentscheme, employmentEligibility, employmentRequiredDocs));

        return schemes;
    }
}
