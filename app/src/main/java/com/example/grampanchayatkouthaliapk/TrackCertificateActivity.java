package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackCertificateActivity extends AppCompatActivity {

    private EditText etCertificateId;
    private TextView tvCertificateDetails;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_certificate);

        etCertificateId = findViewById(R.id.etCertificateId);
        tvCertificateDetails = findViewById(R.id.tvCertificateDetails);
        Button btnTrackCertificate = findViewById(R.id.btnTrackCertificate);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("CertificateApplications");

        btnTrackCertificate.setOnClickListener(v -> {
            String certificateId = etCertificateId.getText().toString().trim();

            if (certificateId.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_enter_certificate_id), Toast.LENGTH_SHORT).show();
            } else {
                fetchCertificateDetails(certificateId);
            }
        });
    }

    private void fetchCertificateDetails(String certificateId) {
        // Query Firebase Database
        databaseReference.child(certificateId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Fetch and display certificate details
                    StringBuilder details = new StringBuilder();
                    details.append(getString(R.string.enter_certificate_id)).append(": ").append(certificateId).append("\n");
                    for (DataSnapshot field : dataSnapshot.getChildren()) {
                        details.append(field.getKey()).append(": ").append(field.getValue()).append("\n");
                    }
                    tvCertificateDetails.setText(details.toString());
                } else {
                    // No record found for the entered certificateId
                    tvCertificateDetails.setText(getString(R.string.certificate_details_not_found));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TrackCertificateActivity.this, String.format(getString(R.string.error_occurred), databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
