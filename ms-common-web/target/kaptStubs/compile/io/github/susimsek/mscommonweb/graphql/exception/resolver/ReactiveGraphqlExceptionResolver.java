package io.github.susimsek.mscommonweb.graphql.exception.resolver;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\u000b"}, d2 = {"Lio/github/susimsek/mscommonweb/graphql/exception/resolver/ReactiveGraphqlExceptionResolver;", "Lorg/springframework/graphql/execution/DataFetcherExceptionResolver;", "()V", "resolveException", "Lreactor/core/publisher/Mono;", "", "Lgraphql/GraphQLError;", "ex", "", "env", "Lgraphql/schema/DataFetchingEnvironment;", "ms-common-web"})
public final class ReactiveGraphqlExceptionResolver implements org.springframework.graphql.execution.DataFetcherExceptionResolver {
    
    public ReactiveGraphqlExceptionResolver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public reactor.core.publisher.Mono<java.util.List<graphql.GraphQLError>> resolveException(@org.jetbrains.annotations.NotNull
    java.lang.Throwable ex, @org.jetbrains.annotations.NotNull
    graphql.schema.DataFetchingEnvironment env) {
        return null;
    }
}