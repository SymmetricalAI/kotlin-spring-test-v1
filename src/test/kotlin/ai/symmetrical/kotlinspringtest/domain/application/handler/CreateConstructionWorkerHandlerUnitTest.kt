package ai.symmetrical.kotlinspringtest.domain.application.handler

import ai.symmetrical.kotlinspringtest.domain.exception.ConstructionWorkerAlreadyEmployed
import ai.symmetrical.kotlinspringtest.domain.handler.CreateConstructionWorkerHandler.CreateConstructionWorkerCommand
import ai.symmetrical.kotlinspringtest.fixtures.base.UnitBaseTest
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.KafkaConstructionWorkerEventPublisher
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.Topics.NADZORCA_TOPIC
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.UUID

class CreateConstructionWorkerHandlerUnitTest : UnitBaseTest() {
    init {
        expect("should employ construction worker") {
            val command =
                CreateConstructionWorkerCommand(
                    imperialId = UUID.randomUUID(),
                    name = "test",
                    lastName = "test",
                )
            fixtures.createConstructionWorkerHandler.handle(command)

            val findByImperialId = fixtures.constructionWorkerRepository.findByImperialId(command.imperialId)
            findByImperialId shouldNotBe null
            findByImperialId!!.name shouldBe command.name
            findByImperialId!!.lastName shouldBe command.lastName

            val events =
                fixtures.inMemoryGenericKafkaPublisher
                    .getEventsOfTypeFromTopic<KafkaConstructionWorkerEventPublisher.ConstructionWorkerEmployedEvent>(
                        KafkaConstructionWorkerEventPublisher.ConstructionWorkerEmployedEvent::class.java,
                        NADZORCA_TOPIC,
                    )
            events.size shouldBe 1
            events.first().imperialId shouldBe command.imperialId
            events.first().name shouldBe command.name
            events.first().lastName shouldBe command.lastName
        }
        expect("should not employ construction worker if already employed") {
            val existing = providers.constructionWorker { }.inDb()
            val command =
                CreateConstructionWorkerCommand(
                    imperialId = existing.imperialId,
                    name = "test",
                    lastName = "test",
                )
            shouldThrow<ConstructionWorkerAlreadyEmployed> {
                fixtures.createConstructionWorkerHandler.handle(command)
            }

            val events =
                fixtures.inMemoryGenericKafkaPublisher
                    .getEventsOfTypeFromTopic<KafkaConstructionWorkerEventPublisher.ConstructionWorkerSuspiciousRebelActivityEvent>(
                        KafkaConstructionWorkerEventPublisher.ConstructionWorkerSuspiciousRebelActivityEvent::class.java,
                        NADZORCA_TOPIC,
                    )
            events.size shouldBe 1
            events.first().imperialId shouldBe command.imperialId
        }
    }
}
