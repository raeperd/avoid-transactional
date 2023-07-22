package io.github.raeperd.avoidtransactional

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AvoidTransactionalApplication

fun main(args: Array<String>) {
    runApplication<AvoidTransactionalApplication>(*args)
}
