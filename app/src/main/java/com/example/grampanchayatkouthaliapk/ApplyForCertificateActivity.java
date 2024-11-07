package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ApplyForCertificateActivity extends AppCompatActivity {
    private String selectedDocumentType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_certificate);

        Button btnBirthCertificate = findViewById(R.id.radio_birth_certificate);
        Button btnDeathCertificate = findViewById(R.id.radio_death_certificate);
        Button btnResidenceCertificate = findViewById(R.id.radio_residence_certificate);
        Button btnIncomeCertificate = findViewById(R.id.radio_income_certificate);
        Button btnSubmitApplication = findViewById(R.id.btn_submit_application);

        btnBirthCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDocumentType = "Birth Certificate";
//                showToast("जन्म प्रमाणपत्र निवडले आहे");
            }
        });

        btnDeathCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDocumentType = "Death Certificate";
               // showToast("मृत्यू प्रमाणपत्र निवडले आहे");
            }
        });

        btnResidenceCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDocumentType = "Residence Certificate";
               // showToast("रहिवासी प्रमाणपत्र निवडले आहे");
            }
        });

        btnIncomeCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDocumentType = "Income Certificate";
               // showToast("उत्पन्न प्रमाणपत्र निवडले आहे");
            }
        });

        btnSubmitApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedDocumentType.isEmpty()) {
                    Intent intent = new Intent(ApplyForCertificateActivity.this, RegisterforCertificateActivity.class);
                    intent.putExtra("DOCUMENT_TYPE", selectedDocumentType);
                    startActivity(intent);
                } else {
                    showToast("कृपया प्रमाणपत्र प्रकार निवडा");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
