package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
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
    private TableLayout tblComplaintDetails;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_complaint);

        etComplaintId = findViewById(R.id.etComplaintId);
        Button btnTrack = findViewById(R.id.btnTrack);
        tblComplaintDetails = findViewById(R.id.tblComplaintDetails);

        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");

        btnTrack.setOnClickListener(v -> trackComplaint());
    }

    private void trackComplaint() {
        String complaintId = etComplaintId.getText().toString().trim();

        if (TextUtils.isEmpty(complaintId)) {
            Toast.makeText(this, getString(R.string.enter_complaint_id), Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(complaintId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Complaint complaint = snapshot.getValue(Complaint.class);
                    if (complaint != null) {
                        ((TextView) findViewById(R.id.tvComplaintId)).setText(complaint.getComplaintId());
                        ((TextView) findViewById(R.id.tvName)).setText(complaint.getName());
                        ((TextView) findViewById(R.id.tvAddress)).setText(complaint.getAddress());
                        ((TextView) findViewById(R.id.tvMobileNumber)).setText(complaint.getMobileNumber());
                        ((TextView) findViewById(R.id.tvCategory)).setText(complaint.getCategory());
                        ((TextView) findViewById(R.id.tvComplaintDescription)).setText(complaint.getComplaintDescription());
                        ((TextView) findViewById(R.id.tvStatus)).setText(complaint.getStatus());

                        tblComplaintDetails.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(TrackComplaintActivity.this, getString(R.string.complaint_id_not_found), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TrackComplaintActivity.this, getString(R.string.track_complaint_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
