package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaxPayActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView textName;
    private TextView textHouseNumber;
    private TextView textTotalBill;
    private EditText inputFullName;
    private EditText inputHouseNumber;
    private Button btnFetchBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_pay);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("taxRecords");

        // Initialize Views
        textName = findViewById(R.id.text_name);
        textHouseNumber = findViewById(R.id.text_house_number);
        textTotalBill = findViewById(R.id.text_total_bill);
        inputFullName = findViewById(R.id.input_full_name);
        inputHouseNumber = findViewById(R.id.input_house_number);
        btnFetchBill = findViewById(R.id.btn_fetch_bill);

        btnFetchBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = inputFullName.getText().toString().trim();
                String houseNumberStr = inputHouseNumber.getText().toString().trim();

                if (!houseNumberStr.isEmpty()) {
                    int houseNumber = Integer.parseInt(houseNumberStr);
                    // Fetch total bill from the database
                    fetchTotalBill(fullName, houseNumber);
                } else {
                    showMessageDialog(getString(R.string.please_enter_valid_house_number));
                }
            }
        });
    }

    private void fetchTotalBill(String fullName, int houseNumber) {
        // Query the database for the total bill based on fullname and housenumber
        databaseReference.orderByChild("housenumber").equalTo(houseNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean recordFound = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String nameFromDB = snapshot.child("fullname").getValue(String.class);
                            if (nameFromDB != null && nameFromDB.equals(fullName)) {
                                // Get total bill
                                Integer totalBill = snapshot.child("totaltax").getValue(Integer.class);
                                if (totalBill != null) {
                                    displayTotalBill(fullName, houseNumber, totalBill);
                                } else {
                                    showMessageDialog(getString(R.string.total_bill_not_found));
                                }
                                recordFound = true;
                                break;
                            }
                        }
                        if (!recordFound) {
                            showMessageDialog(getString(R.string.record_not_found));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showMessageDialog(getString(R.string.database_error, error.getMessage()));
                    }
                });
    }

    private void displayTotalBill(String fullName, int houseNumber, int totalBill) {
        textName.setText(getString(R.string.name, fullName));
        textHouseNumber.setText(getString(R.string.house_number, houseNumber));
        textTotalBill.setText(getString(R.string.total_bill, totalBill));
        textName.setVisibility(View.VISIBLE);
        textHouseNumber.setVisibility(View.VISIBLE);
        textTotalBill.setVisibility(View.VISIBLE);
    }

    private void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
