package io.github.susimsek.mscommonweb.mongo

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.query.FluentQuery
import org.springframework.nativex.hint.TypeAccess
import org.springframework.nativex.hint.TypeHint
import org.springframework.nativex.type.NativeConfiguration

@TypeHint(
    types = [
        org.springframework.data.repository.PagingAndSortingRepository::class,
        org.springframework.data.repository.CrudRepository::class,
        org.springframework.data.repository.Repository::class,
        org.springframework.data.repository.query.QueryByExampleExecutor::class,
        java.util.Optional::class,
        java.time.OffsetDateTime::class,
        java.time.LocalDateTime::class,
        java.time.LocalDate::class,
        FluentQuery::class,
        FluentQuery.FetchableFluentQuery::class,
        FluentQuery.ReactiveFluentQuery::class],
    access = [TypeAccess.QUERY_PUBLIC_METHODS]
)
@Lazy(false)
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(name = ["org.springframework.nativex.NativeListener"])
class MongoNativeHints : NativeConfiguration