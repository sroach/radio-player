package gy.roach.radio


/**
 * Data class representing a radio station.
 *
 * @property index Unique identifier for the station
 * @property url Streaming URL for the station
 * @property label Display name of the station
 * @property type List of types/genres of the station
 * @property imageResource Resource name for the station's SVG image
 */
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
    val array = listOf(
        StationItem(index = 0, url = "https://streaming.radio.co/sca6c5aded/listen", label = "Pepperpot Radio", type = listOf("Reggae", "Soca", "Calypso")),
        StationItem(index = 1, url = "http://n02.radiojar.com/nh42m158d4duv?_=1&rj-tok=AAABfl9pxJ0Ar0s9I0FDPohs9g&rj-ttl=5", label = "Guyana Lite 104.1 FM", type = listOf("Oldies", "News")),
        StationItem(index = 2, url = "http://192.99.8.192:3630/stream/;", label = "HJ 94.1 Boom FM", type = listOf("Adult Contemporary", "Hits", "Hip Hop", "R&B")),
        StationItem(index = 3, url = "https://auds1.intacs.com/thecaribbeanradioindo", label = "Bollywood Caribbean Radio Station", type = listOf("Bollywood")),
        StationItem(index = 4, url = "https://auds1.intacs.com/thecaribbeanradio", label = "The Caribbean Radio Station", type = listOf("Dancehall", "Soca", "Reggae")),
        StationItem(index = 5, url = "https://stream.radio.co/s9668e2ab5/listen", label = "Radio Guyana Inc. live", type = listOf("Bollywood", "Chutney", "Reggae", "Soca")),
        StationItem(index = 6, url = "http://abee.radioca.st/stream", label = "Abee Radio", type = listOf("Bollywood", "Chutney", "Indian", "Reggae", "Soca")),
        StationItem(index = 7, url = "http://streaming.broadcastradio.com:8870/maad975fm", label = "MAAD 97.5 FM", type = listOf("Dancehall", "Reggae", "World")),
        StationItem(index = 8, url = "https://s4.radio.co/s75af666f2/listen", label = "96.1 VOICE FM", type = listOf("Reggae", "Soca")),
        StationItem(index = 9, url = "https://audio-edge-qse4n.yyz.g.radiomast.io/aa758625-6c37-4a31-8262-b2bf84bbe17f", label = "Mix FM", type = listOf("Reggae", "Soca")),
        StationItem(index = 10, url = "https://halo.streamerr.co/radio/8030/radio.mp3?refresh=1649376724995", label = "Radio Guyana International", type = listOf("Adult Contemporary", "Pop", "Top 40")),
        StationItem(index = 11, url = "https://stream-162.zeno.fm/m19sfp6wxd0uv?zs=vUwfSKd4T9GTg1IUkgsh4Q&adtonosListenerId=01HKKK5H6PR9KBY9N639GBTXRH&aw_0_req_lsid=206aa14d7837f0e07c484eab76f4be26&acu-uid=874281774124&dyn-uid=5561095501891397575&an-uid=0&mm-uid=fac5659b-715a-4a00-a864-69dfc84eb257&dot-uid=0a03220300e52ab026a463b8&triton-uid=cookie%3A686d6f09-2763-49ed-8955-b54deb608add&amb-uid=9114116788562960963", label = "Basil P Radio", type = listOf("Soul", "R&B", "Oldies", "Disco", "Reggae", "Soca")),
        StationItem(index = 12, url = "https://stream-153.zeno.fm/tdngaa9tszzuv?zs=c_CGWREdQIG2ht1QSVmUwA&adtonosListenerId=01HKKK5H6PR9KBY9N639GBTXRH&aw_0_req_lsid=206aa14d7837f0e07c484eab76f4be26&acu-uid=874281774124&dyn-uid=5561095501891397575&an-uid=0&mm-uid=fac5659b-715a-4a00-a864-69dfc84eb257&dot-uid=0a03220300e52ab026a463b8&triton-uid=cookie%3A686d6f09-2763-49ed-8955-b54deb608add&amb-uid=9114116788562960963", label = "Radio592FM", type = listOf("Bollywood", "Oldies")),
        StationItem(index = 13, url = "https://icecast.walmradio.com:8443/classic", label = "Classic", type = listOf("big band", "classic hits")),
        StationItem(index = 14, url = "https://cast4.asurahosting.com/proxy/ncn1001/stream", label = "Vybz 100.1 FM live", type = listOf("Reggae", "Oldies")),
        StationItem(index = 15, url = "http://185.80.220.12:8046/stream?cb=307101.mp3", label = "Bollywood Radio & Beyond", type = listOf("Bollywood")),
        StationItem(index = 16, url = "http://www.desizone.nl:8000/listen.pls?sid=1&t=.pls", label = "Desi Zone", type = listOf("Bollywood")),
        StationItem(index = 17, url = "http://109.169.46.197:8011/listen.pls?sid=1&t=.pls", label = "Diverse FM", type = listOf("Bollywood")),
        StationItem(index = 18, url = "http://cast2.asurahosting.com:8569/stream?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTY0MjM1Njk4NiwiaXNzIjoidGlzcnYifQ.Z-y3FzbhYxOEiz75k25CLu2OrKutjTQ5N-_Mr0WLqrY", label = "It's Hot Mirchi 102.4 FM", type = listOf("Bollywood")),
        StationItem(index = 19, url = "https://eu8.fastcast4u.com/proxy/clyedupq?mp=/1&DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY&partnertok=eyJhbGciOiJIUzI1NiIsImtpZCI6InR1bmVpbiIsInR5cCI6IkpXVCJ9.eyJ0cnVzdGVkX3BhcnRuZXIiOnRydWUsImlhdCI6MTY0MjM1NzI1NiwiaXNzIjoidGlzcnYifQ.EAssq2ZkcXQlSiawuzVsWrII1AJAgt_pXy03dWMFBKw", label = "Hindi Desi Bollywood", type = listOf("Bollywood")),
        StationItem(index = 20, url = "http://auds1.intacs.com/ntnradio", label = "NTN Radio 89.1 Georgetown", type = listOf("Bollywood"))
    )

    fun selectedLabel(id: Int): String {
        val item = array[id]
        return item.label
    }

    fun item(id: Int): StationItem {
        return array[id]
    }
}
