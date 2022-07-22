package io.github.susimsek.mscommonweb.security;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lio/github/susimsek/mscommonweb/security/WebSecurityAutoConfiguration;", "", "securityMatcherProperties", "Lio/github/susimsek/mscommonweb/security/SecurityMatcherProperties;", "(Lio/github/susimsek/mscommonweb/security/SecurityMatcherProperties;)V", "springWebFilterChain", "Lorg/springframework/security/web/server/SecurityWebFilterChain;", "http", "Lorg/springframework/security/config/web/server/ServerHttpSecurity;", "ms-common-web"})
@org.springframework.boot.context.properties.EnableConfigurationProperties(value = {io.github.susimsek.mscommonweb.security.SecurityMatcherProperties.class})
@org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
@org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
public class WebSecurityAutoConfiguration {
    private final io.github.susimsek.mscommonweb.security.SecurityMatcherProperties securityMatcherProperties = null;
    
    public WebSecurityAutoConfiguration(@org.jetbrains.annotations.NotNull
    io.github.susimsek.mscommonweb.security.SecurityMatcherProperties securityMatcherProperties) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.context.annotation.Bean
    public org.springframework.security.web.server.SecurityWebFilterChain springWebFilterChain(@org.jetbrains.annotations.NotNull
    org.springframework.security.config.web.server.ServerHttpSecurity http) {
        return null;
    }
}