package ai.symmetrical.kotlinspringtest.fixtures.providers

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerRepository
import org.bson.types.ObjectId
import java.util.UUID

class ConstructionWorkerDataProvider(
    private val repository: ConstructionWorkerRepository,
    var id: ObjectId = ObjectId(),
    var imperialId: UUID = UUID.randomUUID(),
    var name: String = "name-${UUID.randomUUID()}",
    var lastName: String = "lastName-${UUID.randomUUID()}",
) {
    fun build(): ConstructionWorker =
        ConstructionWorker.new(
            id = id,
            imperialId = imperialId,
            name = name,
            lastName = lastName,
        )

    fun inDb(): ConstructionWorker =
        build().let {
            repository.save(it)
            it
        }
}
