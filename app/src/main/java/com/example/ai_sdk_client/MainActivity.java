package com.example.ai_sdk_client;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.textGenButton).setOnClickListener(v ->
//                startActivity(new Intent(this, TextGenerationActivity.class)));
//
//        findViewById(R.id.imageGenButton).setOnClickListener(v ->
//                startActivity(new Intent(this, ImageGenerationActivity.class)));
    }
}