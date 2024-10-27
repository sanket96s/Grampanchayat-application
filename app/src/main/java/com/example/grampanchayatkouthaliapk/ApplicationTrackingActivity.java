package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ApplicationTrackingActivity extends AppCompatActivity {

    private EditText etApplicationId;
    private EditText etCaptchaInput;
    private TextView tvCaptchaDisplay;
    private Button btnTrackApplication;

    private String generatedCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_application);

        etApplicationId = findViewById(R.id.et_application_id);
        etCaptchaInput = findViewById(R.id.et_captcha_input);
        tvCaptchaDisplay = findViewById(R.id.tv_captcha_display);
        btnTrackApplication = findViewById(R.id.btn_track_application);

        // Generate and display a captcha
        generatedCaptcha = generateCaptcha();
        tvCaptchaDisplay.setText(generatedCaptcha);

        btnTrackApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackApplication();
            }
        });
    }

    private String generateCaptcha() {
        // Generate a random alphanumeric captcha
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(chars.length());
            captcha.append(chars.charAt(index));
        }
        return captcha.toString();
    }

    private void trackApplication() {
        String applicationId = etApplicationId.getText().toString().trim();
        String captchaInput = etCaptchaInput.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(applicationId)) {
            etApplicationId.setError("कृपया अर्ज क्रमांक प्रविष्ट करा");
            return;
        }

        if (TextUtils.isEmpty(captchaInput)) {
            etCaptchaInput.setError("कृपया कॅप्चा प्रविष्ट करा");
            return;
        }

        if (!captchaInput.equals(generatedCaptcha)) {
            etCaptchaInput.setError("कॅप्चा चुकीचा आहे");
            return;
        }

        // Simulate application tracking process
        // In a real application, you would fetch application details from a database or API
        // For demonstration purposes, we'll just show a success message
        showTrackingResult(applicationId);
    }

    private void showTrackingResult(String applicationId) {
        // Display a dialog or Toast with the tracking result
        // For this example, we'll use a Toast
        String message = "अर्ज क्रमांक " + applicationId + " यशस्वीरित्या ट्रॅक करण्यात आला!";
        Toast Toast = null;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        // In a real application, you may want to navigate to another activity or show detailed information
    }
}
