package io.github.susimsek.mscommonweb.graphql;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017J \u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0017J\b\u0010\u000b\u001a\u00020\fH\u0017\u00a8\u0006\r"}, d2 = {"Lio/github/susimsek/mscommonweb/graphql/GraphqlDateTimeAutoConfiguration;", "", "()V", "graphQlLocalDateScalar", "Lgraphql/schema/GraphQLScalarType;", "configurationProperties", "Lio/github/susimsek/mscommonweb/graphql/scalar/GraphQlDateTimeProperties;", "graphQlLocalDateTimeScalar", "graphQlOffsetDateTimeScalar", "graphqlDateTimeConfigurer", "Lorg/springframework/graphql/execution/RuntimeWiringConfigurer;", "jsonCustomizer", "Lorg/springframework/boot/autoconfigure/jackson/Jackson2ObjectMapperBuilderCustomizer;", "ms-common-web"})
@org.springframework.boot.context.properties.EnableConfigurationProperties(value = {io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties.class})
@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
public class GraphqlDateTimeAutoConfiguration {
    
    public GraphqlDateTimeAutoConfiguration() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public graphql.schema.GraphQLScalarType graphQlOffsetDateTimeScalar(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties configurationProperties) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public graphql.schema.GraphQLScalarType graphQlLocalDateScalar(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties configurationProperties) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public graphql.schema.GraphQLScalarType graphQlLocalDateTimeScalar(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties configurationProperties) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public org.springframework.graphql.execution.RuntimeWiringConfigurer graphqlDateTimeConfigurer(@org.jetbrains.annotations.NotNull
    graphql.schema.GraphQLScalarType graphQlOffsetDateTimeScalar, @org.jetbrains.annotations.NotNull
    graphql.schema.GraphQLScalarType graphQlLocalDateTimeScalar, @org.jetbrains.annotations.NotNull
    graphql.schema.GraphQLScalarType graphQlLocalDateScalar) {
        return null;
    }
}