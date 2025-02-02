package com.example.library.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ModelResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("status")
    private String status;

    @SerializedName("input_data")
    private Map<String, Object> inputData;

    @SerializedName("output_data")
    private Map<String, Object> outputData;

    @SerializedName("error_msg")
    private String errorMessage;

    @SerializedName("token_used")
    private int tokensUsed;

    @SerializedName("processing_time")
    private long processingTime;

    @SerializedName("model_type")
    private String modelType;

    // Getter methods
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, Object> getOutputData() {
        return outputData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getModelType() {
        return modelType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // Helper methods for easy access to common output data
    public String getTextOutput() {
        if (outputData != null && outputData.containsKey("output")) {
            Object output = outputData.get("output");
            return output != null ? output.toString() : null;
        }
        return null;
    }

    public String getImageUrl() {
        if (outputData != null && outputData.containsKey("output")) {
            Object output = outputData.get("output");
            return output != null ? output.toString() : null;
        }
        return null;
    }

    // Helper method to check if the request was successful
    public boolean isSuccessful() {
        return "completed".equals(status) && errorMessage == null;
    }

}
