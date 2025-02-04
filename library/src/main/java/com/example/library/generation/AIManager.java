package com.example.library.generation;

import androidx.annotation.NonNull;
import com.example.library.auth.AuthManager;
import com.example.library.network.ApiClient;
import com.example.library.network.api.AiApiService;
import com.example.library.network.models.ModelRequest;
import com.example.library.network.models.ModelResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AIManager {
    public static final String MODEL_TYPE_TEXT = "text2text";
    private final AuthManager authManager;
    private final AiApiService apiService;

    public interface GenerationCallback {
        void onSuccess(ModelResponse response);
        void onError(String error);
    }

    public AIManager(@NonNull AuthManager authManager) {
        this.authManager = authManager;
        this.apiService = ApiClient.getInstance();
    }

    public void generateText(@NonNull String prompt, @NonNull GenerationCallback callback) {
        if (!authManager.isAuthenticated()) {
            callback.onError("Not authenticated. Please log in first.");
            return;
        }

        ModelRequest request = new ModelRequest(MODEL_TYPE_TEXT, prompt);
        String authHeader = authManager.getAuthorizationHeader();

        apiService.generateContent(authHeader, request).enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModelResponse> call, @NonNull Response<ModelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelResponse result = response.body();
                    if (result.isSuccessful() && result.getTextOutput() != null) {
                        callback.onSuccess(result);
                    } else {
                        String error = result.getErrorMessage();
                        callback.onError(error != null && !error.isEmpty()
                                ? error
                                : "Failed to generate text");
                    }
                } else {
                    callback.onError("Server error: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResponse> call, @NonNull Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}