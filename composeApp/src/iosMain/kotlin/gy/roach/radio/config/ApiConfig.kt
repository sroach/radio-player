package gy.roach.radio.config

import platform.Foundation.NSBundle
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSString

/**
 * iOS implementation of ApiConfig.
 * This implementation uses NSUserDefaults for simplicity, but in a real production app,
 * you should use the iOS Keychain Services for storing sensitive data like API keys.
 * 
 * Note: For a production app, implement proper Keychain integration using:
 * - Security framework (SecItemAdd, SecItemCopyMatching, etc.)
 * - Proper error handling and security attributes
 */
class IosApiConfig : ApiConfig {
    private val apiKeyPreference = "stations_api_key_pref"
    private val bundleApiKeyKey = "STATIONS_API_KEY"
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun getStationsApiKey(): String {
        // Try to get the key from user defaults first
        val storedKey = userDefaults.stringForKey(apiKeyPreference)
        if (storedKey != null && storedKey.isNotEmpty()) {
            return storedKey
        }

        // If not found in user defaults, try to get it from Info.plist
        val apiKey = getBundleApiKey()
        if (apiKey.isNotEmpty()) {
            // Store for future use
            userDefaults.setObject(apiKey, apiKeyPreference)
            userDefaults.synchronize()
            return apiKey
        }

        return "" // Return empty string if no key is available
    }

    override fun getStationsApiUrl(): String {
        return ApiConfig.DEFAULT_STATIONS_API_URL
    }

    /**
     * Gets the API key from the Info.plist file.
     */
    private fun getBundleApiKey(): String {
        val bundle = NSBundle.mainBundle
        return (bundle.objectForInfoDictionaryKey(bundleApiKeyKey) as? NSString)?.toString() ?: ""
    }
}

/**
 * Factory implementation for iOS platform.
 */
actual object ApiConfigFactory {
    actual fun create(): ApiConfig = IosApiConfig()
}
