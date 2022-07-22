package io.github.susimsek.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class ProductApplication

fun main(args: Array<String>) {
	runApplication<ProductApplication>(*args)
}
