package ai.symmetrical.kotlinspringtest.infrastructure.rest.configuration

import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.ErrorResponse
import ai.symmetrical.kotlinspringtest.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestAdvice {
    val log by logger()

    @ExceptionHandler(IllegalStateException::class)
    fun handle(ex: IllegalStateException): ResponseEntity<ErrorResponse> {
        log.error(ex::class.simpleName, ex)

        return ResponseEntity.badRequest().body(
            ErrorResponse(
                message = ex.message ?: "Unknown error",
            ),
        )
    }
}
