package com.example.grampanchayatkouthaliapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FakePaymentActivity extends AppCompatActivity {

    private EditText inputPin;
    private Button btnConfirmPayment;
    private TextView textPaymentStatus;
    private TextView textTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_payment);

        // Get the total amount passed from TaxPayActivity
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);

        inputPin = findViewById(R.id.input_pin);
        btnConfirmPayment = findViewById(R.id.btn_confirm_payment);
        textTotalAmount = findViewById(R.id.text_total_amount);
        textPaymentStatus = findViewById(R.id.text_payment_message);

        // Display total amount
        textTotalAmount.setText("Total Amount: ₹" + totalAmount);

        btnConfirmPayment.setOnClickListener(view -> confirmPayment());
    }

    private void confirmPayment() {
        String pin = inputPin.getText().toString().trim();

        if (pin.isEmpty()) {
            textPaymentStatus.setText("Please enter your PIN.");
            return;
        }

        // Simulate payment processing
        textPaymentStatus.setText("Processing payment...");
        new android.os.Handler().postDelayed(() -> {
            textPaymentStatus.setText("Payment successful! Transaction completed.");

            // Return updated amount and finish this activity
            returnUpdatedAmountToTaxPayActivity();
        }, 2000); // Simulate delay for payment processing
    }

    private void returnUpdatedAmountToTaxPayActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("paymentStatus", true); // Indicate payment was successful
        returnIntent.putExtra("paidAmount", Integer.parseInt(textTotalAmount.getText().toString().replace("Total Amount: ₹", "")));
        setResult(RESULT_OK, returnIntent);
        finish(); // Close the activity
    }

}
