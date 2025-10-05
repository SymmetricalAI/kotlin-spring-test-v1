package ai.symmetrical.kotlinspringtest.infrastructure.rest.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "ErrorResponse", description = "Error payload with message explaining the problem")
data class ErrorResponse(
    @field:Schema(description = "Human-readable error message", example = "Validation failed: name must not be blank")
    val message: String,
)
