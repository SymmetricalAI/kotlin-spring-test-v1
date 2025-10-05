package ai.symmetrical.kotlinspringtest.infrastructure.db

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.fixtures.base.FrameworkBaseTest
import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe

@ApplyExtension(SpringExtension::class)
class MongoConstructionWorkerRepositoryFrameworkTest : FrameworkBaseTest() {
    val inMemoryConstructionWorkerRepository = InMemoryConstructionWorkerRepository()
    lateinit var constructionWorker: ConstructionWorker

    init {
        beforeSpec {
            constructionWorker =
                providers
                    .constructionWorker {
                        name = "Jan"
                    }.build()

            inMemoryConstructionWorkerRepository.save(constructionWorker)
            fixtures.constructionWorkerRepository.save(constructionWorker)
        }

        expect("should findById construction worker to behave same as mongo") {
            inMemoryConstructionWorkerRepository.findById(constructionWorker.id) shouldBe constructionWorker
            fixtures.constructionWorkerRepository.findById(constructionWorker.id) shouldBe constructionWorker
        }

        expect("should delete construction worker to behave same as mongo") {
            inMemoryConstructionWorkerRepository.deleteById(constructionWorker.id)
            fixtures.constructionWorkerRepository.deleteById(constructionWorker.id)

            inMemoryConstructionWorkerRepository.findById(constructionWorker.id) shouldBe null
            fixtures.constructionWorkerRepository.findById(constructionWorker.id) shouldBe null
        }
    }
}
