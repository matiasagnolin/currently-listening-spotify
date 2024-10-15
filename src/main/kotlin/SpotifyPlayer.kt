package org.spotify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpotifyPlayer

fun main(args: Array<String>) {
    runApplication<SpotifyPlayer>(*args)
    }
