package ai.symmetrical.kotlinspringtest.infrastructure.rest.query

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerRepository
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.ConstructionWorkerGetResponse
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class GetConstructionWorkerQueryHandler(
    private val constructionWorkerRepository: ConstructionWorkerRepository,
) {
    fun getOne(id: ObjectId): ConstructionWorkerGetResponse =
        constructionWorkerRepository.findById(id)?.let { it.toDto() } ?: throw NoSuchElementException()

    // fixme: i am ineffective
    fun getAll(): List<ConstructionWorkerGetResponse> = constructionWorkerRepository.findAll().map { it.toDto() }
}

fun ConstructionWorker.toDto(): ConstructionWorkerGetResponse =
    ConstructionWorkerGetResponse(
        id = id,
        imperialId = imperialId,
        name = name,
        lastName = lastName,
    )
