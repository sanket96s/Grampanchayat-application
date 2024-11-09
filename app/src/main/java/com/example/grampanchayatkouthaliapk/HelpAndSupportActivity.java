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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        // Initialize ListView and populate FAQ
        ListView faqListView = findViewById(R.id.faq_list);

        // Populate FAQs dynamically using resources
        final String[][] faqs = {
                {getString(R.string.faq_question_1), getString(R.string.faq_answer_1)},
                {getString(R.string.faq_question_2), getString(R.string.faq_answer_2)},
                {getString(R.string.faq_question_3), getString(R.string.faq_answer_3)},
                {getString(R.string.faq_question_4), getString(R.string.faq_answer_4)}
        };

        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, R.layout.list_item_faq, faqs) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View listItem = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.list_item_faq, parent, false);

                TextView questionTextView = listItem.findViewById(R.id.question_text);
                TextView answerTextView = listItem.findViewById(R.id.answer_text);

                // Add question number before the question text
                questionTextView.setText("Q" + (position + 1) + ": " + faqs[position][0]);

                // Add "-->" before the answer text
                answerTextView.setText("--> " + faqs[position][1]);

                // Make sure the answer is visible by default
                answerTextView.setVisibility(View.VISIBLE);

                return listItem;
            }
        };

        faqListView.setAdapter(adapter);
    }
}
