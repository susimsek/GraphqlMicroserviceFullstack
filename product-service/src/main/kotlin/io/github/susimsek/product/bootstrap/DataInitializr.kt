package io.github.susimsek.product.bootstrap

import io.github.susimsek.product.model.Product
import io.github.susimsek.product.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
internal class DataInitializr(private val productRepository: ProductRepository) : CommandLineRunner {

    override fun run(args: Array<String>) {
        productRepository
            .deleteAll()
            .thenMany<Any>(
                Flux
                    .just(
                        Product(id = "1", name = "Saturn V", description = "The Original Super Heavy-Lift Rocket!"),
                        Product(id = "2", name = "Lunar Module"),
                        Product(id = "3", name = "Space Shuttle"),
                        Product(id = "4", name = "Falcon 9", description = "Reusable Medium-Lift Rocket"),
                        Product(id = "5", name = "Dragon", description = "Reusable Medium-Lift Rocket"),
                        Product(id = "6", name = "Starship", description = "Super Heavy-Lift Reusable Launch Vehicle")
                    )
                    .flatMap(productRepository::save)
            )
            .subscribe()
    }
}