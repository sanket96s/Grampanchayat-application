package com.example.grampanchayatkouthaliapk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ApplicationTrackingActivity extends AppCompatActivity {

    private EditText etApplicationId;
    private EditText etCaptchaInput;
    private TextView tvCaptchaDisplay, tvTrackingResultMessage, tvApplicationNo, tvStatus, tvGrievance, tvViewApplicationFormLink;
    private TableLayout tableTrackingResult;
    private Button btnTrackApplication;;
    private String generatedCaptcha;
    private LinearLayout resultSection;

    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_application);

        etApplicationId = findViewById(R.id.et_application_id);
        etCaptchaInput = findViewById(R.id.et_captcha_input);
        tvCaptchaDisplay = findViewById(R.id.tv_captcha_display);
        btnTrackApplication = findViewById(R.id.btn_track_application);
        tvTrackingResultMessage = findViewById(R.id.tv_tracking_result_message);
        tableTrackingResult = findViewById(R.id.table_tracking_result);
        tvApplicationNo = findViewById(R.id.tv_application_no);
        tvStatus = findViewById(R.id.tv_status);
        tvGrievance = findViewById(R.id.tv_grievance);
        tvViewApplicationFormLink = findViewById(R.id.tv_view_application_form_link);
        resultSection = findViewById(R.id.result_section);

        // Generate and display a captcha
        generatedCaptcha = generateCaptcha();
        tvCaptchaDisplay.setText(generatedCaptcha);

        btnTrackApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackApplication();
                resultSection.setVisibility(View.VISIBLE);
            }
        });
//        tvViewApplicationFormLink.setOnClickListener(v -> viewApplicationForm());

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
        if (!applicationId.matches("^[a-zA-Z0-9]{12}$")) {
            etApplicationId.setError("अर्ज क्रमांक फक्त 12 अल्फान्यूमेरिक वर्णांचा असावा");
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
        tvTrackingResultMessage.setText("अर्ज क्रमांक " + applicationId + " यशस्वीरित्या ट्रॅक करण्यात आला!");
        tvTrackingResultMessage.setVisibility(View.VISIBLE);


        // Simulate application tracking process
        // In a real application, you would fetch application details from a database or API
        // For demonstration purposes, we'll just show a success message
        showTrackingResult(applicationId);
    }

    private void showTrackingResult(String applicationId) {
        // Display a dialog or Toast with the tracking result
        // For this example, we'll use a Toast
        String status = "प्रक्रियेत आहे";  // Example status
        String grievance = "शंका नाही";

        tvApplicationNo.setText(applicationId);
        tvStatus.setText(status);
        tvGrievance.setText(grievance);

        tableTrackingResult.setVisibility(View.VISIBLE);
    }

//    private void viewApplicationForm() {
//        // Navigate to another activity or show form details in a dialog
//        Intent intent = new Intent(ApplicationTrackingActivity.this, ViewApplicationFormActivity.class);
//        startActivity(intent);
//    }
}
