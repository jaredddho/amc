package jaredddho.adobeio

import jaredddho.core.config.SharedReferences
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@ConfigurationPropertiesScan
@Import(value = [SharedReferences::class])
class AdobeIoApplication

fun main(args: Array<String>) {
    runApplication<AdobeIoApplication>(*args)
}
