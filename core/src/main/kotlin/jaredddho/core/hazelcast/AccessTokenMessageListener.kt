package jaredddho.core.hazelcast

import jaredddho.core.adobeio.AccessToken
import com.hazelcast.topic.Message
import com.hazelcast.topic.MessageListener
import org.slf4j.Logger

class AccessTokenMessageListener(
        private val logger: Logger,
        private val hazelcastManager: HazelcastManager
) : MessageListener<AccessToken> {

    override fun onMessage(message: Message<AccessToken>) {
        hazelcastManager.accessToken
                .onNext(message.messageObject)
        logger.trace("Access Token Refreshed: [ {} ]",
                message.messageObject.accessToken)
    }
}