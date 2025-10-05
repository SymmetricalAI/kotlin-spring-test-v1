package ai.symmetrical.kotlinspringtest.domain.application.handler

import ai.symmetrical.kotlinspringtest.domain.exception.ConstructionWorkerAlreadyEmployed
import ai.symmetrical.kotlinspringtest.domain.handler.CreateConstructionWorkerHandler.CreateConstructionWorkerCommand
import ai.symmetrical.kotlinspringtest.fixtures.base.FrameworkBaseTest
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.KafkaConstructionWorkerEventPublisher
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.UUID

@ApplyExtension(SpringExtension::class)
class CreateConstructionWorkerHandlerFrameworkTest : FrameworkBaseTest() {
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
                testKafkaNadzorcaEventListener.getEventsOfType<KafkaConstructionWorkerEventPublisher.ConstructionWorkerEmployedEvent>(
                    KafkaConstructionWorkerEventPublisher.ConstructionWorkerEmployedEvent::class.java,
                )
            events.size shouldBe 1
            events.first()[KafkaConstructionWorkerEventPublisher.ConstructionWorkerEmployedEvent::imperialId.name] shouldBe
                command.imperialId.toString()
            events.first()[KafkaConstructionWorkerEventPublisher.ConstructionWorkerEmployedEvent::name.name] shouldBe command.name
            events.first()[KafkaConstructionWorkerEventPublisher.ConstructionWorkerEmployedEvent::lastName.name] shouldBe command.lastName
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
                testKafkaNadzorcaEventListener
                    .getEventsOfType<KafkaConstructionWorkerEventPublisher.ConstructionWorkerSuspiciousRebelActivityEvent>(
                        KafkaConstructionWorkerEventPublisher.ConstructionWorkerSuspiciousRebelActivityEvent::class.java,
                    )
            events.first()[KafkaConstructionWorkerEventPublisher.ConstructionWorkerSuspiciousRebelActivityEvent::imperialId.name] shouldBe
                command.imperialId.toString()
        }
    }
}
