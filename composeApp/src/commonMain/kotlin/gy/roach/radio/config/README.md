# API Configuration

This directory contains classes for managing API configuration, particularly API keys, across different platforms in the Kotlin Multiplatform project.

## Overview

The `ApiConfig` interface provides a common way to access configuration values like API keys across all platforms. Platform-specific implementations are provided for:

- Android
- iOS
- Desktop (JVM)
- Web (Wasm)

## Setting Up Your API Key

### Desktop (JVM)

For desktop applications, the API key is read from the `local.properties` file in the project root. Add your API key to this file:

```properties
# API Key for stations API
stations.api.key=your_actual_api_key_here
```

The `local.properties` file is already in `.gitignore` to prevent accidentally committing your API key to version control.

### Android

For Android, the API key is currently hardcoded in the `AndroidApiConfig` class. In a production application, you would want to use a more secure approach like:

1. Using the Android Keystore System
2. Storing the key in encrypted SharedPreferences
3. Using a secure backend service to provide the key

To update the API key, modify the `getStationsApiKey()` method in `AndroidApiConfig.kt`.

### iOS

For iOS, the API key is currently hardcoded in the `IosApiConfig` class. In a production application, you would want to use a more secure approach like:

1. Using the iOS Keychain
2. Using a secure backend service to provide the key

To update the API key, modify the `getStationsApiKey()` method in `IosApiConfig.kt`.

### Web (Wasm)

For web applications, the API key is currently hardcoded in the `WebApiConfig` class. In a production application, you would want to use a more secure approach like:

1. Using environment variables
2. Using a secure backend service to provide the key

To update the API key, modify the `getStationsApiKey()` method in `WebApiConfig.kt`.

## Security Considerations

Storing API keys directly in client-side code is generally not recommended for production applications, as they can be extracted from the compiled code. For a more secure approach:

1. Consider using a backend proxy service that adds the API key to requests
2. Use platform-specific secure storage mechanisms
3. Implement token exchange mechanisms where a short-lived token is obtained from a secure backend

## Usage

To use the API configuration in your code:

```kotlin
// Get the platform-specific implementation
val apiConfig = ApiConfigFactory.create()

// Use the API key
val apiKey = apiConfig.getStationsApiKey()
val apiUrl = apiConfig.getStationsApiUrl()
```
