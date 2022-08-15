package io.github.susimsek.product.service.impl

import io.github.susimsek.mscommonweb.graphql.exception.ResourceNotFoundException
import io.github.susimsek.product.model.Product
import io.github.susimsek.product.repository.ProductRepository
import io.github.susimsek.product.util.ProductCreator.DEFAULT_DESCRIPTION
import io.github.susimsek.product.util.ProductCreator.DEFAULT_ID
import io.github.susimsek.product.util.ProductCreator.DEFAULT_NAME
import io.github.susimsek.product.util.ProductCreator.createEntity
import io.github.susimsek.product.util.ProductCreator.createEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.test.assertEquals

@Extensions(
    ExtendWith(MockitoExtension::class)
)
class ProductServiceImplTest {

    @Mock
    private lateinit var productRepositoryMock: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductServiceImpl

    private lateinit var product: Product

    @BeforeEach
    fun initTest() {
        product = createEntity()
    }

    @Test
    fun saveProduct_shouldSaveAndReturnProduct() {
        `when`(productRepositoryMock.save(any(Product::class.java))).thenReturn(Mono.just(product))

        val result = productService.saveProduct(product)

        StepVerifier
            .create(result)
            .consumeNextWith{
                    product ->
                assertEquals(product.id, DEFAULT_ID)
                assertEquals(product.name, DEFAULT_NAME)
                assertEquals(product.description, DEFAULT_DESCRIPTION)
            }
            .verifyComplete()

        verify(productRepositoryMock, times(1)).save(any(Product::class.java))
    }

    @Test
    fun getProduct_shouldReturnProduct() {
        `when`(productRepositoryMock.findById(anyString())).thenReturn(Mono.just(product))

        val result = productService.getProduct(DEFAULT_ID)

        StepVerifier
            .create(result)
            .consumeNextWith{
                    product ->
                assertEquals(product.id, DEFAULT_ID)
                assertEquals(product.name, DEFAULT_NAME)
                assertEquals(product.description, DEFAULT_DESCRIPTION)
        }
        .verifyComplete()

        verify(productRepositoryMock, times(1)).findById(anyString())
    }

    @Test
    fun getProduct_shouldThrowResourceNotFoundExceptionForUnknownProduct() {
        `when`(productRepositoryMock.findById(anyString())).thenReturn(Mono.empty())

        val result = productService.getProduct(DEFAULT_ID)

        StepVerifier
            .create(result)
            .expectError(ResourceNotFoundException::class.java)
        .verify()

        verify(productRepositoryMock, times(1)).findById(anyString())
    }

    @Test
    fun getAllProducts_shouldReturnAllProducts() {
        val entities = createEntityList()
        `when`(productRepositoryMock.findAll()).thenReturn(Flux.fromIterable(entities))

        val result = productService.getAllProducts()

        StepVerifier
            .create(result)
            .expectNextCount(2)
            .verifyComplete()

        verify(productRepositoryMock, times(1)).findAll()
    }

    @Test
    fun getAllProducts_shouldReturnEmptyProductsListForEmptyProductsList() {
        `when`(productRepositoryMock.findAll()).thenReturn(Flux.empty())

        val result = productService.getAllProducts()

        StepVerifier
            .create(result)
            .expectNextCount(0)
            .verifyComplete()

        verify(productRepositoryMock, times(1)).findAll()
    }

    @Test
    fun deleteAllProducts_shouldDeleteAllProductsAndReturnTrue() {

        `when`(productRepositoryMock.deleteAll()).thenReturn(Mono.empty())

        val result = productService.deleteAllProducts()

        StepVerifier.create(result).expectNextCount(0).verifyComplete()

        verify(productRepositoryMock, times(1)).deleteAll()
    }
}