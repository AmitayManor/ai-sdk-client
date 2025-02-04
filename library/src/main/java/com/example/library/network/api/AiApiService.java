package com.example.library.network.api;

import com.example.library.network.models.AuthResponse;
import com.example.library.network.models.LoginRequest;
import com.example.library.network.models.ModelRequest;
import com.example.library.network.models.ModelResponse;
import com.example.library.network.models.SignupRequest;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface AiApiService {
    @POST("api/auth/signup")
    Call<AuthResponse> signup(@Body SignupRequest request);

    @POST("auth/signin")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("api/requests")
    Call<ModelResponse> generateContent(
            @Header("Authorization") String token,
            @Body ModelRequest request
    );

    @GET("api/requests")
    Call<List<ModelResponse>> getHistory(@Header("Authorization") String token);
}