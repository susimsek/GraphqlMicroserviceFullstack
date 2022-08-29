package io.github.susimsek.auth.security

import org.springframework.security.core.AuthenticationException

class OAuth2AuthenticationProcessingException(msg: String) : AuthenticationException(msg)
