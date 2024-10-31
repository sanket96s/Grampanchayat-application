package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HelpAndSupportActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.gemini-ai.com/"; // Replace with Gemini AI base URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        // Initialize ListView and populate FAQ
        ListView faqListView = findViewById(R.id.faq_list);
        final String[][] faqs = {
                {"सामान्य शासकीय योजनांचा लाभ कसा घ्यावा?", "कृपया 'शासकीय योजना' विभागात जा, योजना क्लिक करा आणि अर्ज भरा."},
                {"संपर्क साधण्यासाठी कोणते नंबर आहेत?", "'ग्राम माहिती' विभागात जा, 'अधिकाऱ्यांचा टॅब' निवडा आणि सेल फोन नंबर शोधा."},
                {"ग्रामपंचायतीच्या बैठकीत कसे सहभागी व्हावे?", "'ग्रामसभा' विभागात जा, तारीख तपासा आणि उपस्थित राहा."},
                {"प्रश्न किंवा तक्रारीसाठी कोणाकडे संपर्क करावा?", "'तक्रार दाखल करा' विभागात जा आणि फॉर्म भरा"}
        };

        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, R.layout.list_item_faq, faqs) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View listItem = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.list_item_faq, parent, false);

                TextView questionTextView = listItem.findViewById(R.id.question_text);
                TextView answerTextView = listItem.findViewById(R.id.answer_text);

                questionTextView.setText(faqs[position][0]);
                answerTextView.setText(faqs[position][1]);

                questionTextView.setOnClickListener(v -> answerTextView.setVisibility(answerTextView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));

                return listItem;
            }
        };
        faqListView.setAdapter(adapter);
    }
}
