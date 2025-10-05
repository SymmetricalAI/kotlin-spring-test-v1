package ai.symmetrical.kotlinspringtest.domain

import org.bson.types.ObjectId
import java.util.UUID

interface ConstructionWorkerRepository {
    fun save(constructionWorker: ConstructionWorker)

    fun findAll(): List<ConstructionWorker>

    fun findById(id: ObjectId): ConstructionWorker?

    fun findByImperialId(id: UUID): ConstructionWorker?

    fun deleteById(id: ObjectId): Unit

    fun deleteAll(): Unit
}
