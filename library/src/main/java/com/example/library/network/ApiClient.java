package com.example.library.network;

import com.example.library.network.api.AiApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static volatile AiApiService instance;
    private static final String BASE_URL = "https://fit-harmonia-amitay-14e003a8.koyeb.app/";

    // Longer timeouts for AI model processing
    private static final int CONNECT_TIMEOUT = 30;  // seconds
    private static final int READ_TIMEOUT = 90;     // seconds
    private static final int WRITE_TIMEOUT = 30;    // seconds

    public static AiApiService getInstance() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {
                    instance = createApiService();
                }
            }
        }
        return instance;
    }

    private static AiApiService createApiService() {
        // Set up logging for debugging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Configure OkHttpClient with longer timeouts
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)      // Longest timeout for waiting for model response
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        // Create Retrofit instance
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AiApiService.class);
    }
}