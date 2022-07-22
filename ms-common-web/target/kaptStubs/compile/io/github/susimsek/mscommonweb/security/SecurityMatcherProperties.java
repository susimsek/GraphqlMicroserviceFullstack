package io.github.susimsek.mscommonweb.security;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B%\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0006J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J)\u0010\u000f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0004H\u00d6\u0001R \u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lio/github/susimsek/mscommonweb/security/SecurityMatcherProperties;", "", "ignorePatterns", "", "", "permitAllPatterns", "(Ljava/util/List;Ljava/util/List;)V", "getIgnorePatterns", "()Ljava/util/List;", "setIgnorePatterns", "(Ljava/util/List;)V", "getPermitAllPatterns", "setPermitAllPatterns", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "ms-common-web"})
@org.springframework.boot.context.properties.ConstructorBinding
@org.springframework.boot.context.properties.ConfigurationProperties(value = "security-matcher")
public final class SecurityMatcherProperties {
    @org.jetbrains.annotations.NotNull
    private java.util.List<java.lang.String> ignorePatterns;
    @org.jetbrains.annotations.NotNull
    private java.util.List<java.lang.String> permitAllPatterns;
    
    @org.jetbrains.annotations.NotNull
    public final io.github.susimsek.mscommonweb.security.SecurityMatcherProperties copy(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> ignorePatterns, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> permitAllPatterns) {
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
    
    public SecurityMatcherProperties() {
        super();
    }
    
    public SecurityMatcherProperties(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> ignorePatterns, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> permitAllPatterns) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getIgnorePatterns() {
        return null;
    }
    
    public final void setIgnorePatterns(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getPermitAllPatterns() {
        return null;
    }
    
    public final void setPermitAllPatterns(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> p0) {
    }
}