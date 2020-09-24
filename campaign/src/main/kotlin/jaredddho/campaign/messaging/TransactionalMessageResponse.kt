package jaredddho.campaign.messaging

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TransactionalMessageResponse(
        @JsonProperty(value = "PKey")
        val primaryKey: String
)