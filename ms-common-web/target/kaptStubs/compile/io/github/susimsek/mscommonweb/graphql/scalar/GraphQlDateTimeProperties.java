package io.github.susimsek.mscommonweb.graphql.scalar;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\n\u00a8\u0006\u001a"}, d2 = {"Lio/github/susimsek/mscommonweb/graphql/scalar/GraphQlDateTimeProperties;", "", "offsetDateTime", "Lio/github/susimsek/mscommonweb/graphql/scalar/ScalarDefinition;", "localDateTime", "localDate", "(Lio/github/susimsek/mscommonweb/graphql/scalar/ScalarDefinition;Lio/github/susimsek/mscommonweb/graphql/scalar/ScalarDefinition;Lio/github/susimsek/mscommonweb/graphql/scalar/ScalarDefinition;)V", "getLocalDate", "()Lio/github/susimsek/mscommonweb/graphql/scalar/ScalarDefinition;", "setLocalDate", "(Lio/github/susimsek/mscommonweb/graphql/scalar/ScalarDefinition;)V", "getLocalDateTime", "setLocalDateTime", "getOffsetDateTime", "setOffsetDateTime", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "ms-common-web"})
@org.springframework.boot.context.properties.ConstructorBinding
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "graphql.datetime.scalars")
public final class GraphQlDateTimeProperties {
    @org.jetbrains.annotations.NotNull
    private io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition offsetDateTime;
    @org.jetbrains.annotations.NotNull
    private io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition localDateTime;
    @org.jetbrains.annotations.NotNull
    private io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition localDate;
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties copy(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition offsetDateTime, @org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition localDateTime, @org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition localDate) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public GraphQlDateTimeProperties() {
        super();
    }
    
    public GraphQlDateTimeProperties(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition offsetDateTime, @org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition localDateTime, @org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition localDate) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition getOffsetDateTime() {
        return null;
    }
    
    public final void setOffsetDateTime(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition getLocalDateTime() {
        return null;
    }
    
    public final void setLocalDateTime(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition getLocalDate() {
        return null;
    }
    
    public final void setLocalDate(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.graphql.scalar.ScalarDefinition p0) {
    }
}