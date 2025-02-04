package com.example.ai_sdk_client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button textGenButton = findViewById(R.id.textGenButton);
        textGenButton.setOnClickListener(v ->
                startActivity(new Intent(this, TextGenerationActivity.class))
        );
    }
}