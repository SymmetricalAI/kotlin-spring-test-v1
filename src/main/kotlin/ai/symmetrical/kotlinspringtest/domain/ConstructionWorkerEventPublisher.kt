package ai.symmetrical.kotlinspringtest.domain

import java.util.UUID

interface ConstructionWorkerEventPublisher {
    fun suspiciousRebelActivity(imperialId: UUID)

    fun employed(constructionWorker: ConstructionWorker)
}
