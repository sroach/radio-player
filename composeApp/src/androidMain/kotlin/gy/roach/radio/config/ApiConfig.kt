package gy.roach.radio.config

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import gy.roach.radio.BuildConfig
import gy.roach.radio.ContextProvider
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Android implementation of ApiConfig.
 * This implementation securely stores and retrieves the API key using the Android Keystore System.
 * It initially gets the API key from BuildConfig, then securely stores it for future use.
 */
class AndroidApiConfig : ApiConfig {
    private val keyAlias = "stations_api_key"
    private val sharedPrefsName = "secure_api_prefs"
    private val encryptedKeyPref = "encrypted_api_key"
    private val ivPref = "encryption_iv"

    override fun getStationsApiKey(): String {
        // Try to get the key from secure storage first
        val secureKey = getSecureApiKey()
        if (secureKey.isNotEmpty()) {
            return secureKey
        }

        // If not found in secure storage, use the key from BuildConfig and store it securely
        val apiKey = BuildConfig.STATIONS_API_KEY
        if (apiKey.isNotEmpty()) {
            storeSecureApiKey(apiKey)
            return apiKey
        }

        return "" // Return empty string if no key is available
    }

    override fun getStationsApiUrl(): String {
        return ApiConfig.DEFAULT_STATIONS_API_URL
    }

    /**
     * Retrieves the API key from secure storage.
     */
    private fun getSecureApiKey(): String {
        val context = ContextProvider.getContext() ?: return ""

        try {
            val sharedPrefs = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
            val encryptedKey = sharedPrefs.getString(encryptedKeyPref, null) ?: return ""
            val ivString = sharedPrefs.getString(ivPref, null) ?: return ""

            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            if (!keyStore.containsAlias(keyAlias)) {
                return ""
            }

            val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val iv = Base64.decode(ivString, Base64.DEFAULT)
            val spec = GCMParameterSpec(128, iv)

            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            val encryptedBytes = Base64.decode(encryptedKey, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(encryptedBytes)

            return String(decryptedBytes)
        } catch (e: Exception) {
            Log.e("AndroidApiConfig", "Error retrieving secure API key", e)
            return ""
        }
    }

    /**
     * Stores the API key in secure storage using the Android Keystore System.
     */
    private fun storeSecureApiKey(apiKey: String) {
        val context = ContextProvider.getContext() ?: return

        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            if (!keyStore.containsAlias(keyAlias)) {
                val keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, 
                    "AndroidKeyStore"
                )
                val keyGenSpec = KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()

                keyGenerator.init(keyGenSpec)
                keyGenerator.generateKey()
            }

            val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val encryptedBytes = cipher.doFinal(apiKey.toByteArray())
            val iv = cipher.iv

            val sharedPrefs = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
            sharedPrefs.edit()
                .putString(encryptedKeyPref, Base64.encodeToString(encryptedBytes, Base64.DEFAULT))
                .putString(ivPref, Base64.encodeToString(iv, Base64.DEFAULT))
                .apply()
        } catch (e: Exception) {
            Log.e("AndroidApiConfig", "Error storing secure API key", e)
        }
    }
}

/**
 * Factory implementation for Android platform.
 */
actual object ApiConfigFactory {
    actual fun create(): ApiConfig = AndroidApiConfig()
}
