package jaredddho.core.hazelcast

import jaredddho.core.adobeio.AccessToken
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.topic.ITopic
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
final class HazelcastManager(
        private val logger: Logger
) {

    private final val hazelcastInstance: HazelcastInstance = Hazelcast.newHazelcastInstance()
    private final val accessTokenTopic: ITopic<AccessToken> = hazelcastInstance
            .getTopic<AccessToken>(ADOBEIO_ACCESS_TOKEN_TOPIC)
    private final val messageListener: AccessTokenMessageListener =
            AccessTokenMessageListener(logger, this)

    val accessToken: BehaviorSubject<AccessToken> = BehaviorSubject.create()

    init {
        accessTokenTopic.addMessageListener(messageListener)
    }

    fun publishAccessToken(accessToken: AccessToken) {
        accessTokenTopic.publish(accessToken)
    }

}

private const val ADOBEIO_ACCESS_TOKEN_TOPIC = "adobeio.accessTokenTopic"