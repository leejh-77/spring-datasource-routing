package com.example.route

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.core.io.ClassPathResource
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait

class DBExtension: BeforeAllCallback {

    override fun beforeAll(context: ExtensionContext?) {
        val file = ClassPathResource("docker-compose.yaml").file
        DockerComposeContainer(file)
            .waitingFor("postgresql-master", Wait.forLogMessage(".*database system is ready.*", 1))
            .waitingFor("postgresql-slave", Wait.forLogMessage(".*database system is ready.*", 1))
            .start()
    }
}