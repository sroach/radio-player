package gy.roach.radio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform