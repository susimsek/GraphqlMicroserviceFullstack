package io.github.susimsek.product.graphql


import io.github.susimsek.mscommonweb.test.IntegrationTest
import io.github.susimsek.product.model.Product
import io.github.susimsek.product.repository.ProductRepository
import io.github.susimsek.product.util.ProductCreator.DEFAULT_DESCRIPTION
import io.github.susimsek.product.util.ProductCreator.DEFAULT_NAME
import io.github.susimsek.product.util.ProductCreator.createEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.execution.ErrorType
import org.springframework.graphql.test.tester.WebGraphQlTester
import org.springframework.security.test.context.support.WithMockUser


@IntegrationTest
@WithMockUser
class ProductControllerIT {

    @Autowired
    private lateinit var graphQlTester: WebGraphQlTester

    @Autowired
    private lateinit var productRepository: ProductRepository

    private lateinit var product: Product

    @BeforeEach
    fun initTest() {
        productRepository.deleteAll().block()
        product = createEntity()
    }

    @Test
    fun products_shouldReturnAllProducts() {
        productRepository.save(product).block()

        graphQlTester
            .documentName("productsQuery")
            .execute()
            .path("data.products[*]").entityList(Any::class.java).hasSize(1)
            .path("data.products[0].id").hasValue()
            .path("data.products[0].name").hasValue()
    }


    @Test
    fun product_shouldReturnProduct() {
        productRepository.save(product).block()

        val id = product.id

        graphQlTester
            .documentName("productQuery")
            .variable("id",  id)
            .execute()
            .path("data.product.id").entity(String::class.java).isEqualTo(id)
            .path("data.product.name").entity(String::class.java).isEqualTo(DEFAULT_NAME)
            .path("data.product.description").entity(String::class.java).isEqualTo(DEFAULT_DESCRIPTION)
    }

    @Test
    fun product_shouldReturnNotFoundForUnknownProduct() {
        graphQlTester
            .documentName("productQuery")
            .variable("id", "UNKNOWN")
            .execute()
            .errors()
            .satisfy { errors ->  assertThat(errors).hasSize(1)
                assertThat(errors[0].errorType).isEqualTo(ErrorType.NOT_FOUND)}
    }

    @Test
    fun product_shouldReturnValidationErrorForInvalidId() {
        graphQlTester
            .documentName("productQuery")
            .variable("id", "")
            .execute()
            .errors()
            .satisfy { errors ->  assertThat(errors).hasSize(1)
                assertThat(errors[0].errorType).isEqualTo(graphql.ErrorType.ValidationError)}
    }
}