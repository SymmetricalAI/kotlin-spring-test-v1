package ai.symmetrical.kotlinspringtest.infrastructure.rest.dto

import ai.symmetrical.kotlinspringtest.domain.handler.CreateConstructionWorkerHandler
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.util.UUID

@Schema(name = "CreateConstructionWorkerRequest", description = "Payload to create a new construction worker")
data class CreateConstructionWorkerRequest(
    @field:Schema(description = "External system unique identifier", example = "6d089b8f-5d3c-4b8b-8f21-5b0e2c9d7d8a")
    val imperialId: UUID,
    @field:Size(min = 1, max = 100)
    @field:Schema(description = "First name", example = "John")
    val name: String,
    @field:Size(min = 1, max = 100)
    @field:Schema(description = "Last name", example = "Doe")
    val lastName: String,
)

fun CreateConstructionWorkerRequest.toCommand(): CreateConstructionWorkerHandler.CreateConstructionWorkerCommand =
    CreateConstructionWorkerHandler.CreateConstructionWorkerCommand(
        imperialId = imperialId,
        name = name,
        lastName = lastName,
    )
