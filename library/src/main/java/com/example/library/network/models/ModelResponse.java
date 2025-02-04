package com.example.library.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ModelResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("output_data")
    private Map<String, Object> outputData;

    @SerializedName("error_msg")
    private String errorMessage;

    @SerializedName("processing_time")
    private long processingTime;

    // Essential getter methods
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Helper method to get text output
    public String getTextOutput() {
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