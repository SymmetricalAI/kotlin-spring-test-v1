package ai.symmetrical.kotlinspringtest.fixtures.providers

import ai.symmetrical.kotlinspringtest.domain.ConstructionWorkerRepository
import org.springframework.stereotype.Service

@Service
class TestDataProviders(
    private val constructionWorkerRepository: ConstructionWorkerRepository,
) {
    fun constructionWorker(init: ConstructionWorkerDataProvider.() -> Unit = {}) =
        ConstructionWorkerDataProvider(
            repository = constructionWorkerRepository,
        ).apply { init() }
}
