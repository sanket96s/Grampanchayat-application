package com.example.grampanchayatkouthaliapk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HelpAndSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        ListView faqListView = findViewById(R.id.faq_list);

        final String[][] faqs = {
                {getString(R.string.faq_question_1), getString(R.string.faq_answer_1)},
                {getString(R.string.faq_question_2), getString(R.string.faq_answer_2)},
                {getString(R.string.faq_question_3), getString(R.string.faq_answer_3)},
                {getString(R.string.faq_question_4), getString(R.string.faq_answer_4)}
        };

        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, R.layout.list_item_faq, faqs) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View listItem = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.list_item_faq, parent, false);

                TextView questionTextView = listItem.findViewById(R.id.question_text);
                TextView answerTextView = listItem.findViewById(R.id.answer_text);

                String questionText = getString(R.string.question_format, position + 1, faqs[position][0]);
                questionTextView.setText(questionText);


                String answerText = getString(R.string.answer_format, faqs[position][1]);
                answerTextView.setText(answerText);

                answerTextView.setVisibility(View.VISIBLE);

                return listItem;
            }
        };

        faqListView.setAdapter(adapter);
    }
}
