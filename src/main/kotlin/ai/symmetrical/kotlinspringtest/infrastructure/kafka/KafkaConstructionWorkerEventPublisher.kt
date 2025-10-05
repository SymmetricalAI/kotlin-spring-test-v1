package ai.symmetrical.kotlinspringtest.infrastructure.kafka

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerEventPublisher
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.Topics.NADZORCA_TOPIC
import ai.symmetrical.kotlinspringtest.logger
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class KafkaConstructionWorkerEventPublisher(
    private val genericPublisher: GenericKafkaPublisher,
) : ConstructionWorkerEventPublisher {
    val log by logger()

    override fun suspiciousRebelActivity(imperialId: UUID) {
        genericPublisher.publish(
            NADZORCA_TOPIC,
            imperialId.toString(),
            ConstructionWorkerSuspiciousRebelActivityEvent(
                imperialId = imperialId,
            ),
        )
        log.info("Published suspicious rebel activity event for imperialId=$imperialId")
    }

    override fun employed(constructionWorker: ConstructionWorker) {
        genericPublisher.publish(
            NADZORCA_TOPIC,
            constructionWorker.imperialId.toString(),
            ConstructionWorkerEmployedEvent(
                imperialId = constructionWorker.imperialId,
                name = constructionWorker.name,
                lastName = constructionWorker.lastName,
            ),
        )
        log.info("Published employed event for imperialId=${constructionWorker.imperialId}")
    }

    data class ConstructionWorkerEmployedEvent(
        val imperialId: UUID,
        val name: String,
        val lastName: String,
    )

    data class ConstructionWorkerSuspiciousRebelActivityEvent(
        val imperialId: UUID,
    )
}
