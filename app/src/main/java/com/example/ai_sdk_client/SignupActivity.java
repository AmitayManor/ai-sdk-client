package com.example.ai_sdk_client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ai_sdk_client.databinding.ActivitySignupBinding;
import com.example.library.AiSdk;
import com.example.library.auth.AuthManager;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupButton.setOnClickListener(v -> attemptSignup());
    }

    private void attemptSignup() {
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString();
        String confirmPassword = binding.confirmPasswordInput.getText().toString();

        if (!validateInputs(email, password, confirmPassword)) {
            return;
        }

        binding.loadingIndicator.setVisibility(View.VISIBLE);
        binding.signupButton.setEnabled(false);

        AiSdk.getInstance().getAuthManager().signup(email, password, new AuthManager.AuthCallback() {
            @Override
            public void onSuccess(String message) {
                // After successful signup, attempt login
                loginAfterSignup(email, password);
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    binding.loadingIndicator.setVisibility(View.GONE);
                    binding.signupButton.setEnabled(true);
                    Toast.makeText(SignupActivity.this, error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void loginAfterSignup(String email, String password) {
        AiSdk.getInstance().getAuthManager().login(email, password, new AuthManager.AuthCallback() {
            @Override
            public void onSuccess(String token) {
                runOnUiThread(() -> {
                    binding.loadingIndicator.setVisibility(View.GONE);
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    binding.loadingIndicator.setVisibility(View.GONE);
                    binding.signupButton.setEnabled(true);
                    Toast.makeText(SignupActivity.this,
                            "Signup successful but login failed. Please try logging in.",
                            Toast.LENGTH_LONG).show();
                    finish();
                });
            }
        });
    }

    private boolean validateInputs(String email, String password, String confirmPassword) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.setError("Please enter a valid email address");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            binding.passwordInput.setError("Password must be at least 6 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordInput.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}