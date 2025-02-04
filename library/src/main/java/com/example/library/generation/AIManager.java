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
    // Single model type for text generation
    public static final String MODEL_TYPE_TEXT = "text2text";

    private final AuthManager authManager;
    private final AiApiService apiService;

    // Callback interface for generation results
    public interface GenerationCallback {
        void onSuccess(ModelResponse response);
        void onError(String error);
    }

    public AIManager(@NonNull AuthManager authManager) {
        this.authManager = authManager;
        this.apiService = ApiClient.getInstance();
    }

    /**
     * Generates text using the text-to-text model.
     * @param prompt The text prompt to generate from
     * @param callback Callback to handle the response
     */
    public void generateText(@NonNull String prompt, @NonNull GenerationCallback callback) {
        if (!authManager.isAuthenticated()) {
            callback.onError("Not authenticated. Please log in first.");
            return;
        }

        ModelRequest request = new ModelRequest(MODEL_TYPE_TEXT, prompt);
        String authHeader = authManager.getAuthorizationHeader();

        apiService.generateContent(authHeader, request).enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelResponse result = response.body();
                    if (result.isSuccessful()) {
                        callback.onSuccess(result);
                    } else {
                        callback.onError("Generation failed: " + result.getErrorMessage());
                    }
                } else {
                    callback.onError("Server error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}