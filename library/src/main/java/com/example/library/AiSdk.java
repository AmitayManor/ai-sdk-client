package com.example.library;

import android.content.Context;
import androidx.annotation.NonNull;
import com.example.library.auth.AuthManager;
import com.example.library.generation.AIManager;

public class AiSdk {

    private static volatile AiSdk instance;
    private final AuthManager authManager;
    private final AIManager aiManager;

    private AiSdk(@NonNull Context context) {
        this.authManager = new AuthManager(context);
        this.aiManager = new AIManager(authManager);
    }

    /**
     * Initializes the AI SDK. Must be called before using any SDK features.
     */
    public static void initialize(@NonNull Context context) {
        if (instance == null) {
            synchronized (AiSdk.class) {
                if (instance == null) {
                    instance = new AiSdk(context);
                }
            }
        }
    }

    /**
     * Gets the SDK instance. Must call initialize() first.
     */
    public static AiSdk getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AiSdk must be initialized first");
        }
        return instance;
    }

    /**
     * Gets the authentication manager for handling login/logout.
     */
    public AuthManager getAuthManager() {
        return authManager;
    }

    /**
     * Gets the AI manager for handling text and image generation.
     */
    public AIManager getAiManager() {
        return aiManager;
    }
}
