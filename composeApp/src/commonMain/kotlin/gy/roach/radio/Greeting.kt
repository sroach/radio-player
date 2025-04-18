package gy.roach.radio

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {

        return "Hello, ${platform.name}!"
    }
}
