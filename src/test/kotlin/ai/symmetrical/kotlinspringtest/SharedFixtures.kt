package ai.symmetrical.kotlinspringtest

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerEventPublisher
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerRepository
import ai.symmetrical.kotlinspringtest.domain.handler.CreateConstructionWorkerHandler
import ai.symmetrical.kotlinspringtest.fixtures.base.Clearable
import ai.symmetrical.kotlinspringtest.infrastructure.db.InMemoryConstructionWorkerRepository
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.InMemoryGenericKafkaPublisher
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.KafkaConstructionWorkerEventPublisher
import ai.symmetrical.kotlinspringtest.infrastructure.rest.query.GetConstructionWorkerQueryHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SharedFixtures {
    @Autowired
    lateinit var constructionWorkerRepository: ConstructionWorkerRepository

    @Autowired
    lateinit var createConstructionWorkerHandler: CreateConstructionWorkerHandler

    @Autowired
    lateinit var eventPublisher: ConstructionWorkerEventPublisher

    @Autowired
    lateinit var getConstructionWorkerQueryHandler: GetConstructionWorkerQueryHandler

    // InMemory
    var inMemoryConstructionWorkerRepository: InMemoryConstructionWorkerRepository =
        InMemoryConstructionWorkerRepository()
    var inMemoryGenericKafkaPublisher: InMemoryGenericKafkaPublisher =
        InMemoryGenericKafkaPublisher()

    val inMemoryRepositories: List<Clearable> =
        listOf(
            inMemoryConstructionWorkerRepository,
            inMemoryGenericKafkaPublisher,
        )

    fun init() {
        constructionWorkerRepository = inMemoryConstructionWorkerRepository
        eventPublisher =
            KafkaConstructionWorkerEventPublisher(
                inMemoryGenericKafkaPublisher,
            )

        createConstructionWorkerHandler =
            CreateConstructionWorkerHandler(
                inMemoryConstructionWorkerRepository,
                eventPublisher,
            )

        getConstructionWorkerQueryHandler =
            GetConstructionWorkerQueryHandler(
                inMemoryConstructionWorkerRepository,
            )
    }
}
