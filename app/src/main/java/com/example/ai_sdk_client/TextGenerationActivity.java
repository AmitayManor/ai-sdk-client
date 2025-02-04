package com.example.ai_sdk_client;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ai_sdk_client.databinding.ActivityTextGenerationBinding;
import com.example.library.AiSdk;
import com.example.library.generation.AIManager;
import com.example.library.network.models.ModelResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextGenerationActivity extends AppCompatActivity {
    private static final String TAG = "TextGenerationActivity";
    private ActivityTextGenerationBinding binding;
    private boolean isGenerating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextGenerationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.generateButton.setOnClickListener(v -> generateText());
        binding.resultText.setText("Generated text will appear here...");
    }

    private void generateText() {
        String prompt = binding.promptInput.getText().toString().trim();
        if (prompt.isEmpty()) {
            Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isGenerating) {
            Toast.makeText(this, "Already generating text...", Toast.LENGTH_SHORT).show();
            return;
        }

        isGenerating = true;
        showLoading(true);
        binding.resultText.setText("Generating response...");

        AiSdk.getInstance().getAiManager().generateText(prompt, new AIManager.GenerationCallback() {
            @Override
            public void onSuccess(ModelResponse response) {
                runOnUiThread(() -> {
                    showLoading(false);
                    setFormattedText(response.getTextOutput());
                    isGenerating = false;
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    showLoading(false);
                    binding.resultText.setText("Error: " + error);
                    isGenerating = false;
                });
            }
        });
    }

    private void setFormattedText(String text) {
        if (text == null || text.isEmpty()) {
            binding.resultText.setText("");
            return;
        }

        SpannableString spannableString = new SpannableString(text);

        // Bold text between **
        Pattern boldPattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
        Matcher boldMatcher = boldPattern.matcher(text);

        while (boldMatcher.find()) {
            spannableString.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    boldMatcher.start(),
                    boldMatcher.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Remove the ** markers
        String finalText = text.replaceAll("\\*\\*", "");

        // Handle bullet points
        finalText = finalText.replaceAll("\\* ", "• ");

        // Apply proper line spacing
        finalText = finalText.replaceAll("\\n\\n", "\n\n");

        binding.resultText.setText(finalText);
    }

    private void showLoading(boolean show) {
        binding.loadingIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.generateButton.setEnabled(!show);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}