package com.api.twinme

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment

@ConfigurationPropertiesScan
@SpringBootApplication
class TwinMeApplication(
    private val environment: Environment,
    private val buildProperties: BuildProperties
): ApplicationListener<ApplicationReadyEvent> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun onApplicationEvent(
        event: ApplicationReadyEvent
    ) {
        logger.info(
            "${buildProperties.name} applicationReady, profiles = ${environment.activeProfiles.contentToString()}"
        )
    }

}

fun main(args: Array<String>) {
    runApplication<TwinMeApplication>(*args)
}
