package com.example.grampanchayatkouthaliapk;

import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TaxPayActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private static final int REQUEST_CODE_PAYMENT = 1001; // Define a unique request code

    private TextView textHouseNumber, textTotalBill;
    private EditText inputHouseNumber;
    private Button btnFetchBill, btnPayBill;
    private TableLayout tableLayout;
    private int totalBillAmount = 0;

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
        btnPayBill = findViewById(R.id.btn_pay_bill); // Initialize the Pay Bill button
        tableLayout = findViewById(R.id.table_layout);

        // Set Pay Bill button to be initially hidden
        btnPayBill.setVisibility(View.GONE);


        btnFetchBill.setOnClickListener(view -> fetchBill());
        btnPayBill.setOnClickListener(view -> payBill()); // Set click listener for Pay Bill
    }


    private void fetchBill() {
        String houseNumberStr = inputHouseNumber.getText().toString().trim();
        if (houseNumberStr.isEmpty()) {
            showMessageDialog("Please enter a valid house number.");
            return;
        }

        try {
            int houseNumber = Integer.parseInt(houseNumberStr);
            clearPreviousData();
            fetchTotalBill(houseNumber);
        } catch (NumberFormatException e) {
            showMessageDialog("Invalid house number format.");
        }
    }

    private void clearPreviousData() {
        textHouseNumber.setText("");
        textTotalBill.setText("");
        tableLayout.removeAllViews();
        btnPayBill.setVisibility(View.GONE); // Hide Pay Bill button on clearing data
        totalBillAmount = 0;
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
                                totalBillAmount = totalBill;
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

    private void payBill() {
        Intent intent = new Intent(TaxPayActivity.this, FakePaymentActivity.class);
        intent.putExtra("totalAmount", totalBillAmount); // Pass total bill amount
        startActivityForResult(intent, REQUEST_CODE_PAYMENT); // Use startActivityForResult
    }

    private void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PAYMENT && resultCode == RESULT_OK) {
            boolean paymentStatus = data.getBooleanExtra("paymentStatus", false);
            int paidAmount = data.getIntExtra("paidAmount", 0);

            if (paymentStatus) {
                updateDatabaseAfterPayment(paidAmount);
                showMessageDialog("Payment successful! Amount paid: ₹" + paidAmount);
            } else {
                showMessageDialog("Payment failed. Please try again.");
            }
        }
    }

    private void updateDatabaseAfterPayment(int paidAmount) {
        String houseNumberStr = inputHouseNumber.getText().toString().trim();
        int houseNumber = Integer.parseInt(houseNumberStr);

        // Find the record in the database and reduce the total tax
        databaseReference.orderByChild("housenumber").equalTo(houseNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Integer totalTax = snapshot.child("totaltax").getValue(Integer.class);
                            if (totalTax != null) {
                                int updatedTax = totalTax - paidAmount;
                                snapshot.getRef().child("totaltax").setValue(updatedTax); // Update the total tax in database
                                textTotalBill.setText("Total Bill: ₹" + updatedTax); // Optionally update UI
                                fetchTotalBill(houseNumber);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showMessageDialog("Database error: " + error.getMessage());
                    }
                });
    }


}
