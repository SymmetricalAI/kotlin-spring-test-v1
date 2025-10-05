package ai.symmetrical.kotlinspringtest.infrastructure.rest

import ai.symmetrical.kotlinspringtest.domain.handler.CreateConstructionWorkerHandler
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.ConstructionWorkerCreatedResponse
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.ConstructionWorkerGetResponse
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.CreateConstructionWorkerRequest
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.ErrorResponse
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.toCommand
import ai.symmetrical.kotlinspringtest.infrastructure.rest.query.GetConstructionWorkerQueryHandler
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Construction Workers", description = "Operations related to construction workers")
@RestController
@Validated
@RequestMapping("/api/v1/construction-workers")
class ConstructionWorkerController(
    private val createConstructionWorkerHandler: CreateConstructionWorkerHandler,
    private val queryHandler: GetConstructionWorkerQueryHandler,
) {
    @Operation(
        summary = "Create a construction worker",
        description = "Creates a new construction worker and returns its identifier",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Worker created",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ConstructionWorkerCreatedResponse::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "400",
                description = "Validation error",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
            ),
            ApiResponse(
                responseCode = "409",
                description = "Conflict (e.g., worker already employed)",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
            ),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody @Validated
        createConstructionWorkerRequest: CreateConstructionWorkerRequest,
    ): ConstructionWorkerCreatedResponse =
        createConstructionWorkerHandler
            .handle(createConstructionWorkerRequest.toCommand())
            .let { ConstructionWorkerCreatedResponse(id = it.id.toString()) }

    @Operation(
        summary = "List construction workers",
        description = "Returns all construction workers",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = [Content(mediaType = "application/json")],
            ),
        ],
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<ConstructionWorkerGetResponse> = queryHandler.getAll()

    @Operation(
        summary = "Get construction worker by id",
        description = "Returns a single construction worker",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ConstructionWorkerGetResponse::class))],
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid id supplied",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
            ),
            ApiResponse(
                responseCode = "404",
                description = "Worker not found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
            ),
        ],
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(
        @PathVariable id: String,
    ): ConstructionWorkerGetResponse = queryHandler.getOne(ObjectId(id))
}
