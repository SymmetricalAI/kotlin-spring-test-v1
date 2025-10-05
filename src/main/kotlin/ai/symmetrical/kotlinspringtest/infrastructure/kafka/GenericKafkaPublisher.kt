
package ai.symmetrical.kotlinspringtest.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

interface GenericKafkaPublisher {
    fun publish(
        topic: String,
        key: String?,
        message: Any,
    )
}

@Service
internal class KafkaGenericPublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : GenericKafkaPublisher {
    override fun publish(
        topic: String,
        key: String?,
        message: Any,
    ) {
        val payload =
            when (message) {
                is String -> message
                else -> objectMapper.writeValueAsString(message)
            }

        val messageBuilder =
            MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader("__TypeId__", message::class.simpleName)

        if (key != null) {
            messageBuilder.setHeader(KafkaHeaders.KEY, key)
        }

        kafkaTemplate.send(messageBuilder.build())
    }
}
