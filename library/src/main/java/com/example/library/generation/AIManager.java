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
import java.util.List;

public class AIManager {

    // Constants for model types
    public static final String MODEL_TYPE_TEXT = "text2text";
    public static final String MODEL_TYPE_IMAGE = "text2image";

    private final AuthManager authManager;
    private final AiApiService apiService;

    // Callback interface for generation results
    public interface GenerationCallback {
        void onSuccess(ModelResponse response);
        void onError(String error);
    }

    // Callback interface for request history
    public interface HistoryCallback {
        void onSuccess(List<ModelResponse> history);
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

    /**
     * Generates an image using the text-to-image model.
     * @param prompt The text prompt to generate the image from
     * @param callback Callback to handle the response
     */
    public void generateImage(@NonNull String prompt, @NonNull GenerationCallback callback) {
        if (!authManager.isAuthenticated()) {
            callback.onError("Not authenticated. Please log in first.");
            return;
        }

        ModelRequest request = new ModelRequest(MODEL_TYPE_IMAGE, prompt);
        String authHeader = authManager.getAuthorizationHeader();

        apiService.generateContent(authHeader, request).enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelResponse result = response.body();
                    if (result.isSuccessful()) {
                        callback.onSuccess(result);
                    } else {
                        callback.onError("Image generation failed: " + result.getErrorMessage());
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

    /**
     * Retrieves the history of generation requests.
     * @param callback Callback to handle the response
     */
    public void getGenerationHistory(@NonNull HistoryCallback callback) {
        if (!authManager.isAuthenticated()) {
            callback.onError("Not authenticated. Please log in first.");
            return;
        }

        String authHeader = authManager.getAuthorizationHeader();

        apiService.getHistory(authHeader).enqueue(new Callback<List<ModelResponse>>() {
            @Override
            public void onResponse(Call<List<ModelResponse>> call, Response<List<ModelResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to fetch history: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ModelResponse>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}
