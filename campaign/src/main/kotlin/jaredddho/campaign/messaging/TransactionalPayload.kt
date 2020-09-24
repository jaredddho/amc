package jaredddho.campaign.messaging

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionalPayload(
        @JsonProperty(value = "ctx")
        val context: Any
)