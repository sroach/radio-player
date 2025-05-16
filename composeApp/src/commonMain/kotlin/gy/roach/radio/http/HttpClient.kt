package gy.roach.radio.http

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * A multiplatform HTTP client for making API requests.
 * This class provides a common interface for making HTTP requests across all platforms.
 */
object HttpClientProvider {
    /**
     * Creates and configures a Ktor HttpClient with common settings.
     * Platform-specific engines are provided by each platform's implementation.
     */
    fun createHttpClient(): HttpClient {
        return HttpClient {
            // Install content negotiation for JSON serialization/deserialization
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            // Install logging for debugging
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }
}

/**
 * Example API client that demonstrates how to use the HttpClient.
 * This is a simple example that can be extended for specific API needs.
 */
class ApiClient(val httpClient: HttpClient = HttpClientProvider.createHttpClient()) {

    /**
     * Suspending function to fetch data from a URL.
     * 
     * @param url The URL to fetch data from
     * @return The response body deserialized to type T
     */
    suspend inline fun <reified T> get(url: String): T {
        return httpClient.get(url) {
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body()
    }

    /**
     * Suspending function to post data to a URL.
     * 
     * @param url The URL to post data to
     * @param body The data to post
     * @return The response body deserialized to type R
     */
    suspend inline fun <reified T, reified R> post(url: String, body: T): R {
        return httpClient.post(url) {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
            setBody(body)
        }.body()
    }
}
