package jaredddho.adobeio.service

import jaredddho.adobeio.config.AdobeIOProperties
import jaredddho.core.adobeio.AccessToken
import jaredddho.core.hazelcast.HazelcastManager
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.io.File
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec

@Service
class TokenManager(
        private val logger: Logger,
        private val adobeIOProperties: AdobeIOProperties,
        private val hazelcastManager: HazelcastManager
) {

    fun refreshToken() {
        val accessToken = requestAccessToken()
        hazelcastManager.publishAccessToken(accessToken!!)
    }

    private fun requestAccessToken(): AccessToken? {
        return RestTemplate().postForEntity(
                adobeIOProperties.imsExchange,
                HttpEntity<MultiValueMap<String, String>>(
                        LinkedMultiValueMap<String, String>().apply {
                            this[CLIENT_ID] = adobeIOProperties.apiKey
                            this[CLIENT_SECRET] = adobeIOProperties.secret
                            this[JWT_TOKEN] = generateJwtToken()
                        },
                        HttpHeaders().apply {
                            contentType = MediaType.APPLICATION_FORM_URLENCODED
                        }
                ),
                AccessToken::class.java)
                .body
    }

    private fun generateJwtToken(): String {
        return Jwts.builder()
                .setClaims(HashMap<String, Any>().apply {
                    this[JWT_CLAIM_ISS] = adobeIOProperties.organisationId
                    this[JWT_CLAIM_SUB] = adobeIOProperties.technicalAccountId
                    this[JWT_CLAIM_EXP] = System.currentTimeMillis() / 1000 + 86400L
                    this[JWT_CLAIM_AUD] = String.format("https://%s/c/%s",
                            adobeIOProperties.imsHost,
                            adobeIOProperties.apiKey)

                    adobeIOProperties.metascopes.forEach {
                        val metascope = String.format("https://%s/s/%s",
                                adobeIOProperties.imsHost,
                                it)
                        this[metascope] = true
                    }
                })
                .signWith(
                        SignatureAlgorithm.RS256,
                        KeyFactory.getInstance(KEY_SIGN_ALGORITHM)
                                .generatePrivate(PKCS8EncodedKeySpec(
                                        FileCopyUtils.copyToByteArray(
                                                File(adobeIOProperties.keyPath)
                                        )
                                ))
                )
                .compact()
    }
}

private const val JWT_CLAIM_ISS = "iss"
private const val JWT_CLAIM_SUB = "sub"
private const val JWT_CLAIM_EXP = "exp"
private const val JWT_CLAIM_AUD = "aud"
private const val KEY_SIGN_ALGORITHM = "RSA"

private const val CLIENT_ID = "client_id"
private const val CLIENT_SECRET = "client_secret"
private const val JWT_TOKEN = "jwt_token"