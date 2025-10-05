package ai.symmetrical.kotlinspringtest.infrastructure

import ai.symmetrical.kotlinspringtest.fixtures.base.FrameworkBaseTest
import ai.symmetrical.kotlinspringtest.infrastructure.rest.dto.CreateConstructionWorkerRequest
import feign.FeignException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.bson.types.ObjectId
import java.util.UUID

@ApplyExtension(SpringExtension::class)
class ConstructionWorkerControllerComponentTest : FrameworkBaseTest() {
    init {
        expect("should create and receive construction worker") {
            val createConstructionWorkerRequest =
                CreateConstructionWorkerRequest(
                    UUID.randomUUID(),
                    "feign",
                    "test",
                )
            val saveResponse = feignConstructionWorkerController.create(createConstructionWorkerRequest)
            saveResponse.statusCode.value() shouldBe 201
            val workerCreatedResponse = saveResponse.body
            val findById = fixtures.constructionWorkerRepository.findById(ObjectId(workerCreatedResponse!!.id))!!
            findById.imperialId shouldBe createConstructionWorkerRequest.imperialId
            findById.lastName shouldBe createConstructionWorkerRequest.lastName
            findById.name shouldBe createConstructionWorkerRequest.name

            val byId = feignConstructionWorkerController.getById(saveResponse.body!!.id.toString())
            byId.statusCode.value() shouldBe 200

            val getOneResponseBody = byId.body!!
            getOneResponseBody.imperialId shouldBe createConstructionWorkerRequest.imperialId
            getOneResponseBody.lastName shouldBe createConstructionWorkerRequest.lastName
            getOneResponseBody.name shouldBe createConstructionWorkerRequest.name
        }
        expect("should not-found construction worker") {
            shouldThrow<FeignException.NotFound> {
                feignConstructionWorkerController.getById(ObjectId().toString())
            }
        }
    }
}
