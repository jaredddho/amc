package jaredddho.core.adobeio

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class AccessToken(
        @JsonProperty(value = "token_type")
        val tokenType: String,
        @JsonProperty(value = "access_token")
        val accessToken: String,
        @JsonProperty(value = "expires_in")
        val expirationTime: Int,
        @JsonProperty(value = "error")
        val error: String?,
        @JsonProperty(value = "error_description")
        val errorDescription: String?
): Serializable