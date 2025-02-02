package com.example.ai_sdk_client;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.AiSdk;
import com.example.library.generation.AIManager;
import com.example.library.network.models.ModelResponse;


public class TextGenerationActivity extends AppCompatActivity {
    private EditText promptInput;
    private Button generateButton;
    private TextView resultText;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_generation);

        promptInput = findViewById(R.id.promptInput);
        generateButton = findViewById(R.id.generateButton);
        resultText = findViewById(R.id.resultText);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        generateButton.setOnClickListener(v -> generateText());
    }

    private void generateText() {
        String prompt = promptInput.getText().toString();
        if (prompt.isEmpty()) {
            Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingIndicator.setVisibility(View.VISIBLE);
        generateButton.setEnabled(false);

        AiSdk.getInstance().getAiManager().generateText(prompt, new AIManager.GenerationCallback() {
            @Override
            public void onSuccess(ModelResponse response) {
                runOnUiThread(() -> {
                    loadingIndicator.setVisibility(View.GONE);
                    generateButton.setEnabled(true);
                    resultText.setText(response.getTextOutput());
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    loadingIndicator.setVisibility(View.GONE);
                    generateButton.setEnabled(true);
                    Toast.makeText(TextGenerationActivity.this, error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}