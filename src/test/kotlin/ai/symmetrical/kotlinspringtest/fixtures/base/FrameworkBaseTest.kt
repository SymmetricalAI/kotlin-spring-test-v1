package ai.symmetrical.kotlinspringtest.fixtures.base

import ai.symmetrical.kotlinspringtest.SharedFixtures
import ai.symmetrical.kotlinspringtest.fixtures.providers.TestDataProviders
import ai.symmetrical.kotlinspringtest.fixtures.testcontainers.KafkaTestContainerInitializer
import ai.symmetrical.kotlinspringtest.fixtures.testcontainers.MongoTestContainerInitializer
import ai.symmetrical.kotlinspringtest.infrastructure.feign.FeignConstructionWorkerController
import ai.symmetrical.kotlinspringtest.infrastructure.kafka.TestKafkaNadzorcaEventListener
import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
)
@Testcontainers
@ApplyExtension(SpringExtension::class)
@ContextConfiguration(initializers = [MongoTestContainerInitializer::class, KafkaTestContainerInitializer::class])
@ActiveProfiles("test")
class FrameworkBaseTest : ExpectSpec() {
    @Autowired
    lateinit var fixtures: SharedFixtures

    @Autowired
    lateinit var providers: TestDataProviders

    @Autowired
    lateinit var testKafkaNadzorcaEventListener: TestKafkaNadzorcaEventListener

    @Autowired
    lateinit var feignConstructionWorkerController: FeignConstructionWorkerController

//    TODO
//    @Autowired
//    lateinit var repositories: List<??????>

    init {
        afterSpec {
            // fixme
            // repositories.forEach { it.deleteAll() }
            testKafkaNadzorcaEventListener.clear()
        }
    }
}
