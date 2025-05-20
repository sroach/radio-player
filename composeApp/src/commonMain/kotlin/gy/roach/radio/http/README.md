# HTTP Client for Compose Multiplatform

This package provides a multiplatform HTTP client for making API requests in Compose Multiplatform applications. It uses Ktor as the underlying HTTP client library and provides a simple interface for making HTTP requests.

## Features

- Multiplatform support (Android, iOS, Desktop, Web)
- JSON serialization/deserialization using kotlinx.serialization
- Content negotiation for handling JSON responses
- Logging for debugging
- Platform-specific HTTP engines

## Usage

### Basic Usage

```kotlin
// Create an instance of ApiClient
val apiClient = ApiClient()

// Make a GET request
suspend fun fetchData(): MyDataClass {
    return apiClient.get<MyDataClass>("https://api.example.com/data")
}

// Make a POST request
suspend fun postData(data: RequestData): ResponseData {
    return apiClient.post<RequestData, ResponseData>("https://api.example.com/data", data)
}
```

### Creating a Service Class

For better organization, you can create service classes that use the ApiClient:

```kotlin
class MyService(private val apiClient: ApiClient = ApiClient()) {
    
    suspend fun fetchItems(): List<Item> {
        return apiClient.get<ItemResponse>("https://api.example.com/items").items
    }
    
    suspend fun addItem(item: Item): Item {
        return apiClient.post<Item, Item>("https://api.example.com/items", item)
    }
}

@Serializable
data class ItemResponse(val items: List<Item>)

@Serializable
data class Item(val id: Int, val name: String)
```

### Using with ViewModel

You can use the service classes with a ViewModel to manage state:

```kotlin
class MyViewModel(private val myService: MyService = MyService()) : ViewModel() {
    
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun fetchItems() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val fetchedItems = myService.fetchItems()
                _items.value = fetchedItems
                
            } catch (e: Exception) {
                _error.value = "Failed to fetch items: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
```

### Using in Compose UI

Finally, you can use the ViewModel in your Compose UI:

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel = remember { MyViewModel() }) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }
    
    Column {
        if (isLoading) {
            CircularProgressIndicator()
        }
        
        error?.let { errorMessage ->
            Text(text = errorMessage, color = MaterialTheme.colors.error)
        }
        
        LazyColumn {
            items(items) { item ->
                Text(text = item.name)
            }
        }
    }
}
```

## Implementation Details

The HTTP client implementation consists of the following components:

1. **HttpClientProvider**: A singleton object that creates and configures a Ktor HttpClient with common settings.
2. **ApiClient**: A class that provides a simple interface for making HTTP requests.
3. **Platform-specific engines**: Each platform (Android, iOS, Desktop, Web) has its own HTTP engine implementation.

## Dependencies

The HTTP client depends on the following libraries:

- Ktor Client Core
- Ktor Client Content Negotiation
- Ktor Client Logging
- Ktor Serialization Kotlinx JSON
- Platform-specific Ktor engines (OkHttp for Android/JVM, Darwin for iOS, JS for Web)