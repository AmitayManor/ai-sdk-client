package com.example.library.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class ModelRequest {
    @SerializedName("model_type")
    private String modelType;

    @SerializedName("input_data")
    private Map<String, Object> inputData;

    public ModelRequest(String modelType, String prompt) {
        this.modelType = modelType;
        this.inputData = new HashMap<>();
        this.inputData.put("prompt", prompt);
    }

    public String getModelType() {
        return modelType;
    }

    public Map<String, Object> getInputData() {
        return inputData;
    }
}