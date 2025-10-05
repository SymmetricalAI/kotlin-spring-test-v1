package ai.symmetrical.kotlinspringtest.infrastructure.db

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerRepository
import ai.symmetrical.kotlinspringtest.logger
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class MongoConstructionWorkerRepository(
    private val springConstructionWorkerRepository: SpringConstructionWorkerRepository,
) : ConstructionWorkerRepository {
    val log by logger()

    override fun save(constructionWorker: ConstructionWorker) =
        springConstructionWorkerRepository.save(constructionWorker).let {
            log.info("Saved $constructionWorker")
        }

    override fun findAll(): List<ConstructionWorker> = springConstructionWorkerRepository.findAll()

    override fun findById(id: ObjectId): ConstructionWorker? = springConstructionWorkerRepository.findById(id).orElse(null)

    override fun findByImperialId(id: UUID): ConstructionWorker? = springConstructionWorkerRepository.findByImperialId(id)

    override fun deleteById(id: ObjectId) =
        springConstructionWorkerRepository.deleteById(id).let { log.info("Deleted $id ConstructionWorker") }

    override fun deleteAll() = springConstructionWorkerRepository.deleteAll().let { log.info("Deleted all ConstructionWorkers") }
}
