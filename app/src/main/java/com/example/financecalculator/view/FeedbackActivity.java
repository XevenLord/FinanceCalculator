package com.example.financecalculator.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.financecalculator.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedbackActivity extends AppCompatActivity {

    private EditText etFeedback;
    private Button btnSendFeedback;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etFeedback = findViewById(R.id.et_feedback);
        btnSendFeedback = findViewById(R.id.btn_send_feedback);

        db = FirebaseFirestore.getInstance();

        btnSendFeedback.setOnClickListener(v -> {
            String feedback = etFeedback.getText().toString().trim();
            if (!feedback.isEmpty()) {
                db.collection("feedback").add(new Feedback(feedback))
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(FeedbackActivity.this, "Feedback sent", Toast.LENGTH_SHORT).show();
                            etFeedback.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Log.w("FeedbackActivity", "Error adding document", e);
                            Toast.makeText(FeedbackActivity.this, "Error sending feedback", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(FeedbackActivity.this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static class Feedback {
        private String feedbackText;

        public Feedback(String feedbackText) {
            this.feedbackText = feedbackText;
        }

        public String getFeedbackText() {
            return feedbackText;
        }

        public void setFeedbackText(String feedbackText) {
            this.feedbackText = feedbackText;
        }
    }
}
