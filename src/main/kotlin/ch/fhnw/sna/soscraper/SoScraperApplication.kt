package ch.fhnw.sna.soscraper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SoScraperApplication

fun main(args: Array<String>) {
    runApplication<SoScraperApplication>(*args)
}
