package ai.symmetrical.kotlinspringtest.infrastructure.db

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerRepository
import ai.symmetrical.kotlinspringtest.fixtures.base.InMemoryBaseRepository
import java.util.UUID

class InMemoryConstructionWorkerRepository :
    InMemoryBaseRepository<ConstructionWorker>(),
    ConstructionWorkerRepository {
    override fun findByImperialId(id: UUID): ConstructionWorker? = findAll().find { it.imperialId == id }
}
