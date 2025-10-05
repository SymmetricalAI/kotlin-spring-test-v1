package ai.symmetrical.kotlinspringtest.infrastructure.query

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorker
import ai.symmetrical.kotlinspringtest.fixtures.base.UnitBaseTest
import ai.symmetrical.kotlinspringtest.infrastructure.rest.query.toDto
import io.kotest.matchers.shouldBe

class GetConstructionWorkerQueryHandlerUnitTest : UnitBaseTest() {
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
