package ai.symmetrical.kotlinspringtest.infrastructure.query

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.fixtures.base.FrameworkBaseTest
import ai.symmetrical.kotlinspringtest.infrastructure.rest.query.toDto
import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe

@ApplyExtension(SpringExtension::class)
class GetConstructionWorkerQueryHandlerFrameworkTest : FrameworkBaseTest() {
    init {
        expect("should return opne by id") {
            val constructionWorker = providers.constructionWorker {}.inDb()

            fixtures.getConstructionWorkerQueryHandler.getOne(constructionWorker.id) shouldBe constructionWorker.toDto()
        }
        expect("should return all by id") {
            val entries = mutableListOf<ConstructionWorker>()
            for (i in 1..10) {
                entries.add(providers.constructionWorker {}.inDb())
            }

            fixtures.getConstructionWorkerQueryHandler.getAll().size shouldBe entries.size
        }
    }
}
