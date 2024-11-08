package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackComplaintActivity extends AppCompatActivity {

    private EditText etComplaintId;
    private Button btnTrack;
    private TextView tvComplaintStatus;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_complaint);

        // Initialize UI components
        etComplaintId = findViewById(R.id.etComplaintId);
        btnTrack = findViewById(R.id.btnTrack);
        tvComplaintStatus = findViewById(R.id.tvComplaintStatus);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");

        // Set onClick listener for the track button
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackComplaint();
            }
        });
    }

    private void trackComplaint() {
        String complaintId = etComplaintId.getText().toString().trim();

        if (TextUtils.isEmpty(complaintId)) {
            Toast.makeText(this, "कृपया तक्रार आयडी भरा.", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(complaintId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the status of the complaint
                    Complaint complaint = snapshot.getValue(Complaint.class);
                    if (complaint != null) {
                        tvComplaintStatus.setText("तक्रार स्थिती: " + complaint.getStatus());
                    } else {
                        tvComplaintStatus.setText("तक्रार आयडी अवैध आहे.");
                    }
                } else {
                    tvComplaintStatus.setText("तक्रार आयडी आढळले नाही.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TrackComplaintActivity.this, "तक्रार ट्रॅक करण्यात अडचण आली.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
