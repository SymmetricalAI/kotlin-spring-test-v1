package ai.symmetrical.kotlinspringtest.infrastructure.rest.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.bson.types.ObjectId
import java.util.UUID

@Schema(name = "ConstructionWorkerGetResponse", description = "Construction worker details")
data class ConstructionWorkerGetResponse(
    @field:Schema(type = "string", description = "MongoDB ObjectId", example = "652d3b2f9f1b146f9a0a1234")
    val id: ObjectId,
    @field:Schema(description = "External system unique identifier", example = "6d089b8f-5d3c-4b8b-8f21-5b0e2c9d7d8a")
    val imperialId: UUID,
    @field:Schema(description = "First name", example = "John")
    val name: String,
    @field:Schema(description = "Last name", example = "Doe")
    val lastName: String,
)
