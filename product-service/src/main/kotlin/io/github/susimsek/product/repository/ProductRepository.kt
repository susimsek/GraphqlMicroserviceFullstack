package io.github.susimsek.product.repository

import io.github.susimsek.product.model.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : ReactiveMongoRepository<Product, String>, ReactiveQuerydslPredicateExecutor<Product>
