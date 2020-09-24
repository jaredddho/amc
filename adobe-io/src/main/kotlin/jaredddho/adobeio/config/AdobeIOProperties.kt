package jaredddho.adobeio.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "adobeio")
data class AdobeIOProperties(
        val imsHost: String,
        val imsExchange: String,
        val integrationName: String,
        val apiKey: String,
        val technicalAccountId: String,
        val technicalAccountEmail: String,
        val organisationId: String,
        val secret: String,
        val metascopes: List<String>,
        val keyPath: String,
        val cronSchedule: String
)
