package ai.symmetrical.kotlinspringtest.domain.handler

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerEventPublisher
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerRepository
import ai.symmetrical.kotlinspringtest.domain.exception.ConstructionWorkerAlreadyEmployed
import ai.symmetrical.kotlinspringtest.logger
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateConstructionWorkerHandler(
    private val repository: ConstructionWorkerRepository,
    private val eventPublisher: ConstructionWorkerEventPublisher,
) {
    val log by logger()

    fun handle(command: CreateConstructionWorkerCommand): ConstructionWorker {
        if (repository.findByImperialId(command.imperialId) != null) {
            eventPublisher.suspiciousRebelActivity(command.imperialId)
            throw ConstructionWorkerAlreadyEmployed("ConstructionWorker with imperialId=${command.imperialId} already exists")
        }

        val constructionWorker =
            ConstructionWorker.new(
                imperialId = command.imperialId,
                name = command.name,
                lastName = command.lastName,
            )
        repository.save(constructionWorker)
        eventPublisher.employed(constructionWorker)
        log.info("Created ${ConstructionWorker::class.simpleName} with imperialId=${command.imperialId}")

        return constructionWorker
    }

    data class CreateConstructionWorkerCommand(
        val imperialId: UUID,
        val name: String,
        val lastName: String,
    )
}
