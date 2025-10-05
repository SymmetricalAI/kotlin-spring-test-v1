package ai.symmetrical.kotlinspringtest.infrastructure.db

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface SpringConstructionWorkerRepository : MongoRepository<ConstructionWorker, ObjectId> {
    fun findByImperialId(imperialId: UUID): ConstructionWorker?
}
