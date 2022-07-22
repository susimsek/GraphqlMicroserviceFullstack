package io.github.susimsek.review

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class ReviewApplication

fun main(args: Array<String>) {
	runApplication<ReviewApplication>(*args)
}
