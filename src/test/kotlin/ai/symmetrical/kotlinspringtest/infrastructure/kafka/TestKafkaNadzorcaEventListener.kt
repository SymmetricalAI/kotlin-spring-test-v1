package ai.symmetrical.kotlinspringtest.infrastructure.kafka

import ai.symmetrical.kotlinspringtest.fixtures.base.Clearable
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.Topics.NADZORCA_TOPIC
import ai.symmetrical.kotlinspringtest.logger
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.awaitility.Awaitility
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
@KafkaListener(topics = [NADZORCA_TOPIC])
class TestKafkaNadzorcaEventListener(
    private val objectMapper: ObjectMapper,
) : Clearable {
    val log by logger()
    val events: MutableMap<String, MutableList<Map<String, Any>>> = mutableMapOf()

    @KafkaHandler(isDefault = true)
    fun handle(message: ConsumerRecord<String, String>) {
        log.info("Received message: $message")

        val typeId = message.headers().lastHeader("__TypeId__")?.let { String(it.value()) } ?: "Unknown"
        val parsedMessage: Map<String, Any> = objectMapper.readValue(message.value())

        events.computeIfAbsent(typeId) { mutableListOf() }.add(parsedMessage)
    }

    override fun clear() {
        events.clear()
    }

    fun <T> getEventsOfType(
        type: Class<*>,
        expectedSize: Int = 1,
        timeout: Long = 3000L,
    ): MutableList<Map<String, Any>> {
        Awaitility
            .await()
            .atMost(timeout, TimeUnit.MILLISECONDS)
            .until {
                events
                    .filter { event ->
                        type.simpleName == event.key
                    }.values
                    .flatten()
                    .toMutableList()
                    .size >= expectedSize
            }

        return events
            .filter { event ->
                type.simpleName == event.key
            }.values
            .flatten()
            .toMutableList()
    }
}
