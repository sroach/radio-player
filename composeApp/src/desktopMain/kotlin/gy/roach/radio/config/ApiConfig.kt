package gy.roach.radio.config

import java.io.FileInputStream
import java.util.Properties

/**
 * Desktop implementation of ApiConfig that reads configuration from local.properties.
 */
class DesktopApiConfig : ApiConfig {
    private val properties = Properties().apply {
        try {
            // Try to find local.properties in various locations
            val localPropertiesFile = findLocalPropertiesFile()
            if (localPropertiesFile != null) {
                load(FileInputStream(localPropertiesFile))
            } else {
                println("Warning: Could not find local.properties file")
            }
        } catch (e: Exception) {
            println("Warning: Could not load local.properties: ${e.message}")
        }
    }

    /**
     * Attempts to find the local.properties file in various locations.
     * @return The File object if found, null otherwise.
     */
    private fun findLocalPropertiesFile(): java.io.File? {
        // Check if there's a system property pointing to the local.properties file
        val systemPropertyPath = System.getProperty("local.properties.path")
        if (systemPropertyPath != null) {
            val file = java.io.File(systemPropertyPath)
            if (file.exists()) {
                println("Found local.properties from system property at: ${file.absolutePath}")
                return file
            }
        }

        // List of possible locations to check
        val possibleLocations = listOf(
            java.io.File("local.properties"),                     // Current working directory
            java.io.File("../local.properties"),                  // Parent directory
            java.io.File("../../local.properties"),               // Grandparent directory
            java.io.File(System.getProperty("user.dir"), "local.properties"), // User directory
            // Try to find the project root directory by looking for settings.gradle.kts
            findProjectRoot()?.let { java.io.File(it, "local.properties") } // Project root directory
        ).filterNotNull()

        // Return the first file that exists
        val foundFile = possibleLocations.find { it.exists() }
        if (foundFile != null) {
            println("Found local.properties at: ${foundFile.absolutePath}")
        } else {
            println("Could not find local.properties in any of the expected locations")
            // Print current working directory for debugging
            println("Current working directory: ${System.getProperty("user.dir")}")
        }
        return foundFile
    }

    /**
     * Attempts to find the project root directory by looking for settings.gradle.kts file.
     * @return The File object representing the project root directory if found, null otherwise.
     */
    private fun findProjectRoot(): java.io.File? {
        var currentDir = java.io.File(System.getProperty("user.dir"))
        val maxDepth = 5 // Limit the search depth to avoid infinite loops

        for (i in 0 until maxDepth) {
            // Check if settings.gradle.kts exists in the current directory
            if (java.io.File(currentDir, "settings.gradle.kts").exists()) {
                return currentDir
            }

            // Move up to the parent directory
            val parentDir = currentDir.parentFile ?: break
            currentDir = parentDir
        }

        return null
    }

    override fun getStationsApiKey(): String {
        return properties.getProperty("stations.api.key", "")
    }

    override fun getStationsApiUrl(): String {
        return properties.getProperty("stations.api.url", ApiConfig.DEFAULT_STATIONS_API_URL)
    }
}

/**
 * Factory implementation for desktop platform.
 */
actual object ApiConfigFactory {
    actual fun create(): ApiConfig = DesktopApiConfig()
}
