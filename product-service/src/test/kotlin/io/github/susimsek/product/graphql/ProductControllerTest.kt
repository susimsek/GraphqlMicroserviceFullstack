package io.github.susimsek.product.graphql

import io.github.susimsek.mscommonweb.graphql.exception.ResourceNotFoundException
import io.github.susimsek.product.config.GraphQLConfig
import io.github.susimsek.product.model.Product
import io.github.susimsek.product.service.ProductService
import io.github.susimsek.product.util.ProductCreator
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.graphql.execution.ErrorType
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.test.context.support.WithMockUser
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@GraphQlTest(controllers = [ProductController::class])
@Import(GraphQLConfig::class)
@WithMockUser
class ProductControllerTest {

    @Autowired
    private lateinit var graphQlTester: GraphQlTester

    @MockBean
    private lateinit var productService: ProductService

    private lateinit var product: Product

    @BeforeEach
    fun initTest() {
        product = ProductCreator.createEntity()
    }

    @Test
    fun products_shouldReturnAllProducts() {
        val entities = ProductCreator.createEntityList()
        Mockito.`when`(productService.getAllProducts()).thenReturn(Flux.fromIterable(entities))

        graphQlTester
            .documentName("productsQuery")
            .execute()
            .path("data.products[*]").entityList(Any::class.java).hasSize(2)
            .path("data.products[0].id").hasValue()
            .path("data.products[0].name").hasValue()

        Mockito.verify(productService, Mockito.times(1)).getAllProducts()
    }

    @Test
    fun product_shouldReturnProduct() {
        Mockito.`when`(productService.getProduct(Mockito.anyString())).thenReturn(Mono.just(product))

        val id = product.id

        graphQlTester
            .documentName("productQuery")
            .variable("id",  id)
            .execute()
            .path("data.product.id").entity(String::class.java).isEqualTo(id)
            .path("data.product.name").entity(String::class.java).isEqualTo(ProductCreator.DEFAULT_NAME)
            .path("data.product.description").entity(String::class.java).isEqualTo(ProductCreator.DEFAULT_DESCRIPTION)

        Mockito.verify(productService, Mockito.times(1)).getProduct(Mockito.anyString())
    }

    @Test
    fun product_shouldReturnNotFoundForUnknownProduct() {
        Mockito.`when`(productService.getProduct(
            Mockito.anyString())).thenThrow(ResourceNotFoundException("Product", "id", "UNKNOWN"))
        graphQlTester
            .documentName("productQuery")
            .variable("id", "UNKNOWN")
            .execute()
            .errors()
            .satisfy { errors ->  Assertions.assertThat(errors).hasSize(1)
                Assertions.assertThat(errors[0].errorType).isEqualTo(ErrorType.NOT_FOUND)}

        Mockito.verify(productService, Mockito.times(1)).getProduct(Mockito.anyString())
    }
}