package com.example.grampanchayatkouthaliapk;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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

    private static final int UPI_PAYMENT_REQUEST_CODE = 1001;

    private DatabaseReference databaseReference;
    private TextView textHouseNumber, textTotalBill;
    private EditText inputHouseNumber;
    private Button btnPayBill, btnFetchBill;
    private TableLayout tableLayout;
    private int totalBillAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_pay);

        databaseReference = FirebaseDatabase.getInstance().getReference("taxRecords");

        textHouseNumber = findViewById(R.id.text_house_number);
        textTotalBill = findViewById(R.id.text_total_bill);
        inputHouseNumber = findViewById(R.id.input_house_number);
        btnFetchBill = findViewById(R.id.btn_fetch_bill);
        btnPayBill = findViewById(R.id.btn_pay_bill);
        tableLayout = findViewById(R.id.table_layout);

        btnPayBill.setVisibility(View.GONE);

        btnFetchBill.setOnClickListener(view -> fetchBill());
        btnPayBill.setOnClickListener(view -> initiateUPIPayment());
    }

    private void fetchBill() {
        String houseNumberStr = inputHouseNumber.getText().toString().trim();
        if (houseNumberStr.isEmpty()) {
            showMessageDialog(getString(R.string.enter_house_number));
            return;
        }

        try {
            int houseNumber = Integer.parseInt(houseNumberStr);
            clearPreviousData();
            fetchTotalBill(houseNumber);
        } catch (NumberFormatException e) {
            showMessageDialog(getString(R.string.invalid_house_number));
        }
    }

    private void clearPreviousData() {
        textHouseNumber.setText("");
        textTotalBill.setText("");
        tableLayout.removeAllViews();
        btnPayBill.setVisibility(View.GONE);
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
                            showMessageDialog(getString(R.string.record_not_found));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showMessageDialog(getString(R.string.database_error) + error.getMessage());
                    }
                });
    }

    private void displayTotalBill(String fullName, int houseNumber, int totalBill) {
        tableLayout.removeAllViews();

        TextView textName = new TextView(this);
        textName.setText(getString(R.string.name1) + fullName);
        textName.setTextSize(18);
        tableLayout.addView(textName);

        TextView textHouseNumberDisplay = new TextView(this);
        textHouseNumberDisplay.setText(getString(R.string.house_number) + houseNumber);
        textHouseNumberDisplay.setTextSize(18);
        tableLayout.addView(textHouseNumberDisplay);

        TextView textTotalBillDisplay = new TextView(this);
        textTotalBillDisplay.setText(getString(R.string.total_bill) + totalBill);
        textTotalBillDisplay.setTextSize(18);
        tableLayout.addView(textTotalBillDisplay);

        btnPayBill.setVisibility(View.VISIBLE);
    }

    private void initiateUPIPayment() {
        String upiId = "sushrutmhetras@okhdfcbank";
        String name = getString(R.string.organization_name);
        String note = getString(R.string.payment_note);
        String amount = String.valueOf(totalBillAmount);

        Uri uri = Uri.parse("upi://pay?pa=" + upiId +
                "&pn=" + name +
                "&mc=0000&tid=1234567890&url=upi&am=" + amount +
                "&cu=INR" + "&tn=" + note);

        Intent upiPaymentIntent = new Intent(Intent.ACTION_VIEW, uri);
        upiPaymentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivityForResult(upiPaymentIntent, UPI_PAYMENT_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            showMessageDialog(getString(R.string.upi_app_not_found));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK || resultCode == 11) {
                String response = data != null ? data.getStringExtra("response") : "";
                String status = "";
                if (response != null) {
                    String[] responseArr = response.split("&");
                    for (String res : responseArr) {
                        String[] resData = res.split("=");
                        if (resData.length > 1) {
                            if (resData[0].equalsIgnoreCase("Status")) {
                                status = resData[1];
                            }
                        }
                    }
                }

                if ("Success".equalsIgnoreCase(status)) {
                    showMessageDialog(getString(R.string.payment_success) + totalBillAmount);
                    updateDatabaseAfterPayment(totalBillAmount);
                } else {
                    showMessageDialog(getString(R.string.payment_failed));
                }
            } else {
                showMessageDialog(getString(R.string.payment_failed));
            }
        }
    }

    private void updateDatabaseAfterPayment(int paidAmount) {
        String houseNumberStr = inputHouseNumber.getText().toString().trim();
        int houseNumber = Integer.parseInt(houseNumberStr);

        databaseReference.orderByChild("housenumber").equalTo(houseNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Integer totalTax = snapshot.child("totaltax").getValue(Integer.class);
                            if (totalTax != null) {
                                int updatedTax = totalTax - paidAmount;
                                snapshot.getRef().child("totaltax").setValue(updatedTax);
                                textTotalBill.setText(getString(R.string.total_bill) + updatedTax);
                                fetchTotalBill(houseNumber);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showMessageDialog(getString(R.string.database_error) + error.getMessage());
                    }
                });
    }

    private void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
