package jaredddho.campaign.messaging

import jaredddho.campaign.config.CampaignProperties
import jaredddho.core.hazelcast.HazelcastManager
import io.reactivex.schedulers.Schedulers
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TransactionManager(
        private val logger: Logger,
        private val campaignProperties: CampaignProperties,
        private val hazelcastManager: HazelcastManager
) {

    fun triggerTransactionalEvent(templateId: String, transactionalPayload: TransactionalPayload): TransactionalMessageResponse {
        logger.info("{}", transactionalPayload.context)
        val templateResponse = RestTemplate()
                .postForEntity(String.format("https://mc.adobe.io/%s/campaign/%s/%s",
                        campaignProperties.organisationId,
                        campaignProperties.tenantId,
                        templateId
                ), HttpEntity(
                        transactionalPayload,
                        HttpHeaders().apply {
                            setBearerAuth(hazelcastManager.accessToken
                                    .subscribeOn(Schedulers.io())
                                    .blockingFirst()
                                    .accessToken)
                            contentType = MediaType.APPLICATION_JSON
                            this[HEADER_X_API_KEY] = campaignProperties.apiKey
                        }
                ), TransactionalMessageResponse::class.java)
                .body!!

        logger.debug("[ Transactional ] '{}' triggered.",
                templateId)

        return templateResponse
    }
}

private const val HEADER_X_API_KEY = "X-API-Key"