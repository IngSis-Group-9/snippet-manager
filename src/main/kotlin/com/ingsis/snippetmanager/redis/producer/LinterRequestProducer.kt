package com.ingsis.snippetmanager.redis.producer

import org.austral.ingsis.redis.RedisStreamProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class LinterRequestProducer(
    @Value("\${redis.stream.request_linter_key}") streamKey: String,
    redis: RedisTemplate<String, String>,
) : RedisStreamProducer(streamKey, redis) {
    private val log = org.slf4j.LoggerFactory.getLogger(LinterRequestProducer::class.java)

    suspend fun publishEvent(event: LinterRequest) {
        log.info("Publishing event: $event")
        emit(event)
    }
}
