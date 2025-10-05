package ai.symmetrical.kotlinspringtest.fixtures.testcontainers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.utility.DockerImageName

class KafkaTestContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val kafka = KafkaContainer(DockerImageName.parse("apache/kafka-native:latest"))

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        kafka.withReuse(true)
        kafka.start()

        val values =
            TestPropertyValues.of(
                "spring.kafka.bootstrap-servers=" + kafka.bootstrapServers,
            )
        values.applyTo(applicationContext)
    }
}
