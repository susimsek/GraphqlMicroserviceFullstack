package io.github.susimsek.review.config;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0017\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0017\u00a8\u0006\u0006"}, d2 = {"Lio/github/susimsek/review/config/AppNativeHints;", "", "()V", "graphQlSourceBuilderCustomizer", "Lorg/springframework/boot/autoconfigure/graphql/GraphQlSourceBuilderCustomizer;", "Companion", "review-service"})
@org.springframework.boot.autoconfigure.condition.ConditionalOnClass(name = {"org.springframework.nativex.NativeListener"})
@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
@org.springframework.context.annotation.Lazy(value = false)
@org.springframework.nativex.hint.ResourceHint(patterns = {"graphql/schema.graphqls"})
@org.springframework.nativex.hint.TypeHint(types = {io.github.susimsek.review.model.Product.class, io.github.susimsek.review.model.Review.class}, access = {org.springframework.nativex.hint.TypeAccess.RESOURCE, org.springframework.nativex.hint.TypeAccess.PUBLIC_CLASSES, org.springframework.nativex.hint.TypeAccess.DECLARED_CLASSES, org.springframework.nativex.hint.TypeAccess.DECLARED_CONSTRUCTORS, org.springframework.nativex.hint.TypeAccess.PUBLIC_CONSTRUCTORS, org.springframework.nativex.hint.TypeAccess.DECLARED_METHODS, org.springframework.nativex.hint.TypeAccess.PUBLIC_METHODS, org.springframework.nativex.hint.TypeAccess.PUBLIC_FIELDS, org.springframework.nativex.hint.TypeAccess.DECLARED_FIELDS})
public class AppNativeHints {
    @org.jetbrains.annotations.NotNull
    public static final io.github.susimsek.review.config.AppNativeHints.Companion Companion = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GRAPHQL_SCHEMA_CONFIG_PROPERTIES = "graphql/schema.graphqls";
    
    public AppNativeHints() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer graphQlSourceBuilderCustomizer() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lio/github/susimsek/review/config/AppNativeHints$Companion;", "", "()V", "GRAPHQL_SCHEMA_CONFIG_PROPERTIES", "", "review-service"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}