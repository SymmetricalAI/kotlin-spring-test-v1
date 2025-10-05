package ai.symmetrical.kotlinspringtest.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(ConstructionWorker.COLLECTION_NAME)
@TypeAlias("ConstructionWorker")
data class ConstructionWorker private constructor(
    @Id
    val id: ObjectId,
    @Indexed(unique = true)
    val imperialId: UUID,
    val name: String,
    val lastName: String,
) {
    companion object {
        const val COLLECTION_NAME = "construction_worker"

        fun new(
            id: ObjectId = ObjectId(),
            name: String,
            lastName: String,
            imperialId: UUID,
        ) = ConstructionWorker(
            id = id,
            name = name,
            lastName = lastName,
            imperialId = imperialId,
        )
    }
}
