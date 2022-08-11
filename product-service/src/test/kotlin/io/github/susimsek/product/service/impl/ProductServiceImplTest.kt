package io.github.susimsek.product.service.impl

import io.github.susimsek.mscommonweb.graphql.exception.ResourceNotFoundException
import io.github.susimsek.product.repository.ProductRepository
import io.github.susimsek.product.util.ProductCreator.DEFAULT_DESCRIPTION
import io.github.susimsek.product.util.ProductCreator.DEFAULT_ID
import io.github.susimsek.product.util.ProductCreator.DEFAULT_NAME
import io.github.susimsek.product.util.ProductCreator.createEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
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

    @Test
    fun getProduct_shouldReturnProduct() {
        `when`(productRepositoryMock.findById(anyString())).thenReturn(Mono.just(createEntity()))

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
}