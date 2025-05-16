package gy.roach.radio

import gy.roach.radio.repository.StationsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * Data class representing a radio station.
 *
 * @property index Unique identifier for the station
 * @property url Streaming URL for the station
 * @property label Display name of the station
 * @property type List of types/genres of the station
 * @property imageResource Resource name for the station's SVG image
 */
@Serializable
data class StationItem(
    val index: Int,
    val url: String,
    val label: String,
    val type: List<String>,
    val imageResource: String = "radio_icon"
) {
    /**
     * Converts the list of types to a comma-separated string.
     * This method provides backward compatibility for existing code that expects a string.
     *
     * @return A comma-separated string of all types
     */
    fun typeAsString(): String {
        return type.joinToString(", ")
    }
}

/**
 * Class that manages the list of radio stations.
 */
class Station {
    private val repository = StationsRepository()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    // Use a StateFlow to hold the stations list
    private val _stationsFlow = MutableStateFlow<List<StationItem>>(emptyList())
    val stationsFlow: StateFlow<List<StationItem>> = _stationsFlow.asStateFlow()

    // State to track if refresh is in progress
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // For backward compatibility, provide direct access to the current list
    val array: List<StationItem>
        get() = _stationsFlow.value

    init {

        // Then try to fetch from API
        fetchStationsFromApi()

        // Load stations from hardcoded JSON as initial data
        val initialStations = loadStationsFromJson()
        _stationsFlow.value = initialStations


    }

    /**
     * Fetches stations from the API.
     */
    private fun fetchStationsFromApi() {
        scope.launch {
            try {
                _isRefreshing.value = true
                val stations = repository.getStations()
                if (stations.isNotEmpty()) {
                    _stationsFlow.value = stations
                }
            } catch (e: Exception) {
                println("Error fetching stations from API: ${e.message}")
                // Keep using the hardcoded stations if API fails
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    /**
     * Public method to refresh stations from the API.
     * This can be called from UI components to trigger a refresh.
     */
    fun refreshStations() {
        fetchStationsFromApi()
    }

    /**
     * Loads the stations from the hardcoded JSON data.
     * This serves as a fallback if the API is unavailable.
     * 
     * @return A list of StationItem objects
     */
    private fun loadStationsFromJson(): List<StationItem> {
        return try {
            kotlinx.serialization.json.Json.decodeFromString<List<StationItem>>(STATIONS_JSON)
        } catch (e: Exception) {
            println("Error parsing JSON: ${e.message}")
            // Fallback to empty list if JSON can't be parsed
            emptyList()
        }
    }

    companion object {
        /**
         * JSON data for stations.
         * This data is stored as a string constant for simplicity.
         * In a production environment, this would be loaded from an external file.
         */
        //language=JSON
        private const val STATIONS_JSON = """
            [
          {
            "index": 0,
            "url": "https://streaming.radio.co/sca6c5aded/listen",
            "label": "Pepperpot Radio",
            "type": [
              "Reggae",
              "Soca",
              "Calypso"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 1,
            "url": "http://n02.radiojar.com/nh42m158d4duv?_=1&rj-tok=AAABfl9pxJ0Ar0s9I0FDPohs9g&rj-ttl=5",
            "label": "Guyana Lite 104.1 FM",
            "type": [
              "Oldies",
              "News"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 2,
            "url": "http://192.99.8.192:3630/stream/;",
            "label": "HJ 94.1 Boom FM",
            "type": [
              "Adult Contemporary",
              "Hits",
              "Hip Hop",
              "R&B"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 3,
            "url": "https://auds1.intacs.com/thecaribbeanradioindo",
            "label": "Bollywood Caribbean Radio Station",
            "type": [
              "Bollywood"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 4,
            "url": "https://auds1.intacs.com/thecaribbeanradio",
            "label": "The Caribbean Radio Station",
            "type": [
              "Dancehall",
              "Soca",
              "Reggae"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 5,
            "url": "https://stream.radio.co/s9668e2ab5/listen",
            "label": "Radio Guyana Inc. live",
            "type": [
              "Bollywood",
              "Chutney",
              "Reggae",
              "Soca"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 6,
            "url": "http://abee.radioca.st/stream",
            "label": "Abee Radio",
            "type": [
              "Bollywood",
              "Chutney",
              "Indian",
              "Reggae",
              "Soca"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 7,
            "url": "http://streaming.broadcastradio.com:8870/maad975fm",
            "label": "MAAD 97.5 FM",
            "type": [
              "Dancehall",
              "Reggae",
              "World"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 8,
            "url": "https://s4.radio.co/s75af666f2/listen",
            "label": "96.1 VOICE FM",
            "type": [
              "Reggae",
              "Soca"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 9,
            "url": "https://audio-edge-qse4n.yyz.g.radiomast.io/aa758625-6c37-4a31-8262-b2bf84bbe17f",
            "label": "Mix FM",
            "type": [
              "Reggae",
              "Soca"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 10,
            "url": "https://halo.streamerr.co/radio/8030/radio.mp3?refresh=1649376724995",
            "label": "Radio Guyana International",
            "type": [
              "Adult Contemporary",
              "Pop",
              "Top 40"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 11,
            "url": "https://stream-162.zeno.fm/m19sfp6wxd0uv?zs=vUwfSKd4T9GTg1IUkgsh4Q&adtonosListenerId=01HKKK5H6PR9KBY9N639GBTXRH&aw_0_req_lsid=206aa14d7837f0e07c484eab76f4be26&acu-uid=874281774124&dyn-uid=5561095501891397575&an-uid=0&mm-uid=fac5659b-715a-4a00-a864-69dfc84eb257&dot-uid=0a03220300e52ab026a463b8&triton-uid=cookie%3A686d6f09-2763-49ed-8955-b54deb608add&amb-uid=9114116788562960963",
            "label": "Basil P Radio",
            "type": [
              "Soul",
              "R&B",
              "Oldies",
              "Disco",
              "Reggae",
              "Soca"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 12,
            "url": "https://stream-153.zeno.fm/tdngaa9tszzuv?zs=c_CGWREdQIG2ht1QSVmUwA&adtonosListenerId=01HKKK5H6PR9KBY9N639GBTXRH&aw_0_req_lsid=206aa14d7837f0e07c484eab76f4be26&acu-uid=874281774124&dyn-uid=5561095501891397575&an-uid=0&mm-uid=fac5659b-715a-4a00-a864-69dfc84eb257&dot-uid=0a03220300e52ab026a463b8&triton-uid=cookie%3A686d6f09-2763-49ed-8955-b54deb608add&amb-uid=9114116788562960963",
            "label": "Radio592FM",
            "type": [
              "Bollywood",
              "Oldies"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 13,
            "url": "https://icecast.walmradio.com:8443/classic",
            "label": "Classic",
            "type": [
              "big band",
              "classic hits"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 14,
            "url": "https://cast4.asurahosting.com/proxy/ncn1001/stream",
            "label": "Vybz 100.1 FM live",
            "type": [
              "Reggae",
              "Oldies"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 15,
            "url": "http://185.80.220.12:8046/stream?cb=307101.mp3",
            "label": "Bollywood Radio & Beyond",
            "type": [
              "Bollywood"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 16,
            "url": "http://www.desizone.nl:8000/listen.pls?sid=1&t=.pls",
            "label": "Desi Zone",
            "type": [
              "Bollywood"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 17,
            "url": "http://109.169.46.197:8011/listen.pls?sid=1&t=.pls",
            "label": "Diverse FM",
            "type": [
              "Bollywood"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 18,
            "url": "http://cast2.asurahosting.com:8569/stream?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTY0MjM1Njk4NiwiaXNzIjoidGlzcnYifQ.Z-y3FzbhYxOEiz75k25CLu2OrKutjTQ5N-_Mr0WLqrY",
            "label": "It's Hot Mirchi 102.4 FM",
            "type": [
              "Bollywood"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 19,
            "url": "https://eu8.fastcast4u.com/proxy/clyedupq?mp=/1&DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTY0MjM1NzI1NiwiaXNzIjoidGlzcnYifQ.EAssq2ZkcXQlSiawuzVsWrII1AJAgt_pXy03dWMFBKw",
            "label": "Hindi Desi Bollywood",
            "type": [
              "Bollywood"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 20,
            "url": "http://auds1.intacs.com/ntnradio",
            "label": "NTN Radio 89.1 Georgetown",
            "type": [
              "Bollywood"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 21,
            "url": "https://www.radiomast.io/stream/68540e88-54b8-4924-b098-5a668f7da7b6/listen",
            "label": "Slam 100.5",
            "type": [
              "Urban"
            ],
            "imageResource": "radio_icon"
          },
          {
            "index": 22,
            "url": "https://mangoradio.stream.laut.fm/mangoradio",
            "label": "Mango Radio",
            "type": [
              "Urban"
            ],
            "imageResource": "radio_icon"
          }, 
          {
          "index": 23,
          "url": "https://stream.zeno.fm/dbstwo3dvhhtv",
          "label": "92.7 Big FM online",
          "type": ["Bollywood", "Pop"],
          "imageResource": "radio_icon"
          }, {
          "index": 24,
          "url": "https://stream-174.zeno.fm/q97eczydqrhvv?zt=eyJhbGciOiJIUzI1NiJ9.eyJzdHJlYW0iOiJxOTdlY3p5ZHFyaHZ2IiwiaG9zdCI6InN0cmVhbS0xNzQuemVuby5mbSIsInJ0dGwiOjUsImp0aSI6InRhOHAxeTRDVDdHenYtN2NoeFQxRmciLCJpYXQiOjE3NDIwNDA3NDEsImV4cCI6MTc0MjA0MDgwMX0.MkjhfjpDcWKnjIHhgkq3SGxg9gH8U901CrsfPZ42PGM",
          "label": "Red FM 93.5",
          "type": ["Bollywood", "Pop"],
          "imageResource": "radio_icon"
          }
        ]
        """
    }

    fun selectedLabel(id: Int): String {
        val item = array[id]
        return item.label
    }

    fun item(id: Int): StationItem {
        return array[id]
    }
}
