package com.ingsis.snippetmanager.redis.consumer

import com.ingsis.snippetmanager.service.SnippetService
import org.austral.ingsis.redis.RedisStreamConsumer
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.stream.StreamReceiver
import org.springframework.stereotype.Component

@Component
class LinterResponseConsumer(
    @Value("\${redis.stream.response_linter_key}") streamKey: String,
    @Value("\${redis.groups.linter}") groupId: String,
    redis: RedisTemplate<String, String>,
    private val snippetService: SnippetService,
) : RedisStreamConsumer<LinterResponse>(streamKey, groupId, redis) {
    init {
        subscription()
    }
    private val log = org.slf4j.LoggerFactory.getLogger(LinterResponseConsumer::class.java)

    override fun onMessage(record: ObjectRecord<String, LinterResponse>) {
        log.info("Consuming linter response for Snippet(${record.value.snippetId}) for User(${record.value.userId})")
        snippetService.updateSnippetCompliance(record.value.snippetId, record.value.userId, record.value.linterResult)
    }

    override fun options(): StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, LinterResponse>> =
        StreamReceiver.StreamReceiverOptions
            .builder()
            .pollTimeout(java.time.Duration.ofMillis(10000))
            .targetType(LinterResponse::class.java)
            .build()
}
