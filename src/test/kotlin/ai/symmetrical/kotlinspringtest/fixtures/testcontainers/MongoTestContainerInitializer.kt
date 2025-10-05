package ai.symmetrical.kotlinspringtest.fixtures.testcontainers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoTestContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    val mongo = MongoDBContainer(DockerImageName.parse("mongo:latest"))

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        mongo.withReuse(true)
        mongo.start()
        val values =
            TestPropertyValues.of(
                "spring.data.mongodb.uri=" + mongo.getReplicaSetUrl("java-test-test"),
                "spring.data.mongodb.database=java-test",
            )
        values.applyTo(applicationContext)
    }
}
