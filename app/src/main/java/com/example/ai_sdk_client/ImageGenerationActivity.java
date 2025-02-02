package com.example.ai_sdk_client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.library.AiSdk;
import com.example.library.generation.AIManager;
import com.example.library.network.models.ModelResponse;

public class ImageGenerationActivity extends AppCompatActivity {
    private EditText promptInput;
    private Button generateButton;
    private ImageView resultImage;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_generation);

        promptInput = findViewById(R.id.promptInput);
        generateButton = findViewById(R.id.generateButton);
        resultImage = findViewById(R.id.resultImage);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        generateButton.setOnClickListener(v -> generateImage());
    }

    private void generateImage() {
        String prompt = promptInput.getText().toString();
        if (prompt.isEmpty()) {
            Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingIndicator.setVisibility(View.VISIBLE);
        generateButton.setEnabled(false);

        AiSdk.getInstance().getAiManager().generateImage(prompt, new AIManager.GenerationCallback() {
            @Override
            public void onSuccess(ModelResponse response) {
                runOnUiThread(() -> {
                    loadingIndicator.setVisibility(View.GONE);
                    generateButton.setEnabled(true);
                    String imageUrl = response.getImageUrl();
                    if (imageUrl != null) {
                        Glide.with(ImageGenerationActivity.this)
                                .load(imageUrl)
                                .into(resultImage);
                    }
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    loadingIndicator.setVisibility(View.GONE);
                    generateButton.setEnabled(true);
                    Toast.makeText(ImageGenerationActivity.this, error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}