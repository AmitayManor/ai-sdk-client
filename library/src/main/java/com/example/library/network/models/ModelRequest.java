package com.example.library.network.models;

import java.util.HashMap;
import java.util.Map;

public class ModelRequest {
    private String modelType;
    private Map<String, Object> inputData;

    public ModelRequest(String modelType, String prompt) {
        this.modelType = modelType;
        this.inputData = new HashMap<>();
        this.inputData.put("input", prompt);
    }

    // Getter methods for Gson serialization
    public String getModelType() {
        return modelType;
    }

    public Map<String, Object> getInputData() {
        return inputData;
    }
}
