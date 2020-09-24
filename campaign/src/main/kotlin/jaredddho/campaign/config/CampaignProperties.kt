package jaredddho.campaign.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "campaign")
data class CampaignProperties(
        val organisationId: String,
        val tenantId: String,
        val apiKey: String
)