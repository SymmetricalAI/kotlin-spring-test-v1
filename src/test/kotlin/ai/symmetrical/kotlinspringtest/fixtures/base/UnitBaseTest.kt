package ai.symmetrical.kotlinspringtest.fixtures.base

import ai.symmetrical.kotlinspringtest.SharedFixtures
import ai.symmetrical.kotlinspringtest.fixtures.providers.TestDataProviders
import io.kotest.core.spec.style.ExpectSpec

abstract class UnitBaseTest : ExpectSpec() {
    val fixtures: SharedFixtures = SharedFixtures().apply { init() }
    val providers: TestDataProviders =
        TestDataProviders(
            fixtures.constructionWorkerRepository,
        )

    init {
        afterEach {
            fixtures.inMemoryRepositories.forEach { it.clear() }
        }
    }
}
