package io.github.susimsek.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class SpringBootAuthorizationServerExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringBootAuthorizationServerExampleApplication>(*args)
}
