package io.github.susimsek.mscommonweb.mongo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001:\u0002\t\nB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0017J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004H\u0017J\b\u0010\u0007\u001a\u00020\bH\u0017\u00a8\u0006\u000b"}, d2 = {"Lio/github/susimsek/mscommonweb/mongo/MongoDateTimeAutoConfiguration;", "", "()V", "clock", "Ljava/time/Clock;", "dateTimeProvider", "Lorg/springframework/data/auditing/DateTimeProvider;", "mongoCustomConversions", "Lorg/springframework/data/mongodb/core/convert/MongoCustomConversions;", "OffsetDateTimeReadConverter", "OffsetDateTimeWriteConverter", "ms-common-web"})
@org.springframework.data.mongodb.config.EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
@org.springframework.context.annotation.Import(value = {org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class, org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration.class})
@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
public class MongoDateTimeAutoConfiguration {
    
    public MongoDateTimeAutoConfiguration() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public java.time.Clock clock() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public org.springframework.data.mongodb.core.convert.MongoCustomConversions mongoCustomConversions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public org.springframework.data.auditing.DateTimeProvider dateTimeProvider(@org.jetbrains.annotations.NotNull
    java.time.Clock clock) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0002H\u0016\u00a8\u0006\u0007"}, d2 = {"Lio/github/susimsek/mscommonweb/mongo/MongoDateTimeAutoConfiguration$OffsetDateTimeWriteConverter;", "Lorg/springframework/core/convert/converter/Converter;", "Ljava/time/OffsetDateTime;", "Ljava/util/Date;", "()V", "convert", "source", "ms-common-web"})
    public static final class OffsetDateTimeWriteConverter implements org.springframework.core.convert.converter.Converter<java.time.OffsetDateTime, java.util.Date> {
        
        public OffsetDateTimeWriteConverter() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.util.Date convert(@org.jetbrains.annotations.NotNull
        java.time.OffsetDateTime source) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0002H\u0016\u00a8\u0006\u0007"}, d2 = {"Lio/github/susimsek/mscommonweb/mongo/MongoDateTimeAutoConfiguration$OffsetDateTimeReadConverter;", "Lorg/springframework/core/convert/converter/Converter;", "Ljava/util/Date;", "Ljava/time/OffsetDateTime;", "()V", "convert", "source", "ms-common-web"})
    public static final class OffsetDateTimeReadConverter implements org.springframework.core.convert.converter.Converter<java.util.Date, java.time.OffsetDateTime> {
        
        public OffsetDateTimeReadConverter() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.time.OffsetDateTime convert(@org.jetbrains.annotations.NotNull
        java.util.Date source) {
            return null;
        }
    }
}