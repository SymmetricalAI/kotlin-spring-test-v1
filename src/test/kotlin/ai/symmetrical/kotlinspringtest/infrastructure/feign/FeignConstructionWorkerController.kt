package ai.symmetrical.kotlinspringtest.infrastructure.feign

import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.ConstructionWorkerCreatedResponse
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.ConstructionWorkerGetResponse
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.CreateConstructionWorkerRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

@FeignClient(
    name = "construction-worker",
)
interface FeignConstructionWorkerController {
    @PostMapping("/api/v1/construction-workers")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody createConstructionWorkerRequest: CreateConstructionWorkerRequest,
    ): ResponseEntity<ConstructionWorkerCreatedResponse?>

    @GetMapping("/api/v1/construction-workers")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): ResponseEntity<List<ConstructionWorkerGetResponse>?>

    @GetMapping("/api/v1/construction-workers/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(
        @PathVariable("id") id: String,
    ): ResponseEntity<ConstructionWorkerGetResponse?>
}
