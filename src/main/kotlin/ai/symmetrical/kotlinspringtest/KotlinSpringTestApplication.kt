package ai.symmetrical.kotlinspringtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableFeignClients
class KotlinSpringTestApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<KotlinSpringTestApplication>(*args)
        }
    }
}
