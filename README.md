# AI SDK Client Library

A powerful Android SDK for integrating AI text generation capabilities into your applications. This library provides a simple interface for authentication and text generation services, along with a complete example application demonstrating its implementation.

## Features

The AI SDK Client Library offers a comprehensive set of features for integrating AI capabilities:

- User Authentication System (Login/Signup)
- Text Generation API Integration
- Token Management
- Error Handling
- Example Application

## Installation

To add the library to your project, include it in your app's `settings.gradle.kts`:

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then add the dependency in your app's `build.gradle.kts`:

```gradle
dependencies {
    implementation("com.github.AmitayManor:ai-sdk-client:1.0.2")
}
```

## Setup

Initialize the SDK in your Application class or main activity:

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize the SDK
        AiSdk.initialize(this);
    }
}
```

## Usage

### Authentication

The SDK provides methods for user authentication:

```java
// Get the SDK instance
AiSdk sdk = AiSdk.getInstance();

// Login
sdk.getAuthManager().login(email, password, new AuthManager.AuthCallback() {
    @Override
    public void onSuccess(String token) {
        // Handle successful login
    }

    @Override
    public void onError(String error) {
        // Handle login error
    }
});

// Signup
sdk.getAuthManager().signup(email, password, new AuthManager.AuthCallback() {
    @Override
    public void onSuccess(String message) {
        // Handle successful signup
    }

    @Override
    public void onError(String error) {
        // Handle signup error
    }
});
```

### Text Generation

Generate text using the AI service:

```java
sdk.getAiManager().generateText(prompt, new AIManager.GenerationCallback() {
    @Override
    public void onSuccess(ModelResponse response) {
        String generatedText = response.getTextOutput();
        // Handle the generated text
    }

    @Override
    public void onError(String error) {
        // Handle generation error
    }
});
```

## Example Application

The repository includes a complete example application demonstrating the SDK's capabilities. The example app shows:

- User Authentication Flow (Login/Signup)
- Text Generation Interface
- Error Handling
- Loading States
- Response Formatting

### Running the Example App

1. Clone the repository
2. Open the project in Android Studio
3. Run the `app` configuration

### Example App Structure

The example application is organized as follows:

- `LoginActivity`: Handles user authentication
- `SignupActivity`: Manages new user registration
- `MainActivity`: Main navigation hub
- `TextGenerationActivity`: Demonstrates AI text generation

## Architecture

The SDK follows a clean architecture pattern:

```
com.example.library/
├── AiSdk.java                 # Main SDK entry point
├── auth/                      # Authentication components
│   ├── AuthException.java
│   └── AuthManager.java
├── generation/                # AI generation components
│   └── AIManager.java
└── network/                   # Network layer
    ├── ApiClient.java
    ├── api/
    │   └── AiApiService.java
    └── models/
        ├── AuthResponse.java
        ├── LoginRequest.java
        ├── ModelRequest.java
        ├── ModelResponse.java
        └── SignupRequest.java
```

## Configuration

The SDK uses the following default configuration:

- Base URL: `https://fit-harmonia-amitay-14e003a8.koyeb.app/`
- Connection Timeout: 30 seconds
- Read Timeout: 90 seconds
- Write Timeout: 30 seconds

## Error Handling

The SDK provides comprehensive error handling through callbacks. Common errors include:

- Network errors
- Authentication failures
- Invalid input errors
- Server errors

Example error handling:

```java
sdk.getAiManager().generateText(prompt, new AIManager.GenerationCallback() {
    @Override
    public void onSuccess(ModelResponse response) {
        if (!response.isSuccessful()) {
            // Handle unsuccessful generation
            String error = response.getErrorMessage();
        }
    }

    @Override
    public void onError(String error) {
        // Handle error cases
        if (error.contains("Not authenticated")) {
            // Handle authentication error
        } else if (error.contains("Network error")) {
            // Handle network error
        }
    }
});
```

## Security Considerations

The SDK implements several security measures:

- Secure token storage using SharedPreferences
- HTTPS communication
- Token-based authentication
- Input validation

## Contributing

We welcome contributions to the SDK. Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Support

For support and questions, please open an issue in the GitHub repository.

## Changelog

### Version 1.0.2
- Initial public release
- Authentication system
- Text generation capabilities
- Example application
