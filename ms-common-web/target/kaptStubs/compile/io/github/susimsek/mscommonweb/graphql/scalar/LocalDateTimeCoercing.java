package io.github.susimsek.mscommonweb.graphql.scalar;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lio/github/susimsek/mscommonweb/graphql/scalar/LocalDateTimeCoercing;", "Lgraphql/schema/Coercing;", "Ljava/time/LocalDateTime;", "", "formatter", "Ljava/time/format/DateTimeFormatter;", "(Ljava/time/format/DateTimeFormatter;)V", "parseLiteral", "input", "", "parseValue", "serialize", "dataFetcherResult", "ms-common-web"})
@graphql.Internal
public final class LocalDateTimeCoercing implements graphql.schema.Coercing<java.time.LocalDateTime, java.lang.String> {
    private final java.time.format.DateTimeFormatter formatter = null;
    
    public LocalDateTimeCoercing(@org.jetbrains.annotations.NotNull
    java.time.format.DateTimeFormatter formatter) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    @kotlin.jvm.Throws(exceptionClasses = {graphql.schema.CoercingSerializeException.class})
    @java.lang.Override
    public java.lang.String serialize(@org.jetbrains.annotations.NotNull
    java.lang.Object dataFetcherResult) throws graphql.schema.CoercingSerializeException {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @kotlin.jvm.Throws(exceptionClasses = {graphql.schema.CoercingParseValueException.class})
    @java.lang.Override
    public java.time.LocalDateTime parseValue(@org.jetbrains.annotations.NotNull
    java.lang.Object input) throws graphql.schema.CoercingParseValueException {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @kotlin.jvm.Throws(exceptionClasses = {graphql.schema.CoercingParseLiteralException.class})
    @java.lang.Override
    public java.time.LocalDateTime parseLiteral(@org.jetbrains.annotations.NotNull
    java.lang.Object input) throws graphql.schema.CoercingParseLiteralException {
        return null;
    }
}