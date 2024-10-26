package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
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
    private TextView textHouseNumber;
    private TextView textTotalBill;
    private EditText inputHouseNumber;
    private Button btnFetchBill;
    private Button btnPayBill; // Declare the pay bill button
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_pay);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("taxRecords");

        // Initialize Views
        textHouseNumber = findViewById(R.id.text_house_number);
        textTotalBill = findViewById(R.id.text_total_bill);
        inputHouseNumber = findViewById(R.id.input_house_number);
        btnFetchBill = findViewById(R.id.btn_fetch_bill);
        btnPayBill = findViewById(R.id.btn_pay_bill); // Initialize the pay bill button
        tableLayout = findViewById(R.id.table_layout);

        btnFetchBill.setOnClickListener(view -> {
            String houseNumberStr = inputHouseNumber.getText().toString().trim();
            if (!houseNumberStr.isEmpty()) {
                int houseNumber = Integer.parseInt(houseNumberStr);
                clearPreviousData();
                fetchTotalBill(houseNumber);
            } else {
                showMessageDialog("Please enter a valid house number.");
            }
        });
    }

    private void clearPreviousData() {
        textHouseNumber.setText("");
        textTotalBill.setText("");
        tableLayout.removeAllViews();
        btnPayBill.setVisibility(View.GONE); // Hide the Pay Bill button
    }

    private void fetchTotalBill(int houseNumber) {
        databaseReference.orderByChild("housenumber").equalTo(houseNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean recordFound = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String fullName = snapshot.child("fullname").getValue(String.class);
                            Integer totalBill = snapshot.child("totaltax").getValue(Integer.class);
                            if (totalBill != null) {
                                displayTotalBill(fullName, houseNumber, totalBill);
                                recordFound = true;
                                break;
                            }
                        }
                        if (!recordFound) {
                            showMessageDialog("Record not found.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showMessageDialog("Database error: " + error.getMessage());
                    }
                });
    }

    private void displayTotalBill(String fullName, int houseNumber, int totalBill) {
        tableLayout.removeAllViews();

        TextView textName = new TextView(this);
        textName.setText("Name: " + fullName);
        textName.setTextSize(18);
        tableLayout.addView(textName);

        TextView textHouseNumberDisplay = new TextView(this);
        textHouseNumberDisplay.setText("House Number: " + houseNumber);
        textHouseNumberDisplay.setTextSize(18);
        tableLayout.addView(textHouseNumberDisplay);

        TextView textTotalBillDisplay = new TextView(this);
        textTotalBillDisplay.setText("Total Bill: ₹" + totalBill);
        textTotalBillDisplay.setTextSize(18);
        tableLayout.addView(textTotalBillDisplay);

        // Show the Pay Bill button
        btnPayBill.setVisibility(View.VISIBLE);
    }

    private void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
