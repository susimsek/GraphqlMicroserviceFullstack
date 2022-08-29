package io.github.susimsek.product.bootstrap

import io.github.susimsek.product.model.Product
import io.github.susimsek.product.service.ProductService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
@ConditionalOnProperty(
    value = ["command.line.runner.enabled"],
    havingValue = "true",
    matchIfMissing = true
)
internal class DataInitializr(private val productService: ProductService) : CommandLineRunner {

    override fun run(args: Array<String>) {
        productService
            .deleteAllProducts()
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
                    .flatMap(productService::saveProduct)
            )
            .subscribe()
    }
}
