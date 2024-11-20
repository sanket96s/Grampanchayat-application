package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ApplyForCertificateActivity extends AppCompatActivity {

    private RadioGroup radioGroupCertificateTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_certificate);

        radioGroupCertificateTypes = findViewById(R.id.radioGroup_certificate_types);
        Button btnSubmitApplication = findViewById(R.id.btn_submit_application);
        Button btnTrackCertificate = findViewById(R.id.btn_track_certificate);

        btnSubmitApplication.setOnClickListener(v -> {
            int selectedId = radioGroupCertificateTypes.getCheckedRadioButtonId();

            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedDocumentType = selectedRadioButton.getText().toString();

                Intent intent = new Intent(ApplyForCertificateActivity.this, RegisterforCertificateActivity.class);
                intent.putExtra("DOCUMENT_TYPE", selectedDocumentType);
                startActivity(intent);
            } else {
                showToast(getString(R.string.select_certificate_type));
            }
        });

        btnTrackCertificate.setOnClickListener(v -> {
            Intent intent = new Intent(ApplyForCertificateActivity.this, TrackCertificateActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
