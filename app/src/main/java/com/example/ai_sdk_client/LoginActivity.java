package com.example.ai_sdk_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_sdk_client.databinding.ActivityLoginBinding;
import com.example.library.AiSdk;
import com.example.library.auth.AuthManager;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private ProgressBar loadingIndicator;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        // Initialize SDK
        AiSdk.initialize(this);

        // Set up UI
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        binding.loginButton.setOnClickListener(v -> {
            binding.loadingIndicator.setVisibility(View.VISIBLE);
            attemptLogin();
        });
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        AiSdk.getInstance().getAuthManager().login(email, password, new AuthManager.AuthCallback() {
            @Override
            public void onSuccess(String token) {
                loadingIndicator.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(String error) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
