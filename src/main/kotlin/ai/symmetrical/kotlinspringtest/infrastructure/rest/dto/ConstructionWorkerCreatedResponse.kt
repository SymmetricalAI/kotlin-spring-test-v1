package ai.symmetrical.kotlinspringtest.infrastructure.rest.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "ConstructionWorkerCreatedResponse", description = "Response after successfully creating a construction worker")
class ConstructionWorkerCreatedResponse(
    @field:Schema(description = "Identifier of the created worker (MongoDB ObjectId as string)", example = "652d3b2f9f1b146f9a0a1234")
    val id: String,
)
