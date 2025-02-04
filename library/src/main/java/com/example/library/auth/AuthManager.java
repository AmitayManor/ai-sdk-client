package com.example.library.auth;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.example.library.network.ApiClient;
import com.example.library.network.api.AiApiService;
import com.example.library.network.models.AuthResponse;
import com.example.library.network.models.LoginRequest;
import com.example.library.network.models.SignupRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManager {
    private static final String PREF_NAME = "ai_sdk_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";

    private final Context context;
    private final SharedPreferences preferences;
    private final AiApiService apiService;

    public interface AuthCallback {
        void onSuccess(String result);
        void onError(String error);
    }

    public AuthManager(@NonNull Context context) {
        this.context = context.getApplicationContext();
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.apiService = ApiClient.getInstance();
    }

    public void signup(String email, String password, @NonNull AuthCallback callback) {
        SignupRequest request = new SignupRequest(email, password);
        apiService.signup(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess("Signup successful. Please check your email for verification.");
                } else {
                    callback.onError("Signup failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void login(String email, String password, @NonNull AuthCallback callback) {
        LoginRequest request = new LoginRequest(email, password);
        apiService.login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    if (authResponse.getToken() != null) {
                        saveAuthToken(authResponse.getToken());
                        callback.onSuccess(authResponse.getToken());
                    } else {
                        callback.onError("Invalid response from server");
                    }
                } else {
                    callback.onError("Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public String getAuthToken() {
        return preferences.getString(KEY_AUTH_TOKEN, null);
    }

    public boolean isAuthenticated() {
        return getAuthToken() != null;
    }

    public void logout() {
        preferences.edit().remove(KEY_AUTH_TOKEN).apply();
    }

    public String getAuthorizationHeader() {
        String token = getAuthToken();
        return token != null ? "Bearer " + token : null;
    }

    private void saveAuthToken(String token) {
        preferences.edit().putString(KEY_AUTH_TOKEN, token).apply();
    }
}