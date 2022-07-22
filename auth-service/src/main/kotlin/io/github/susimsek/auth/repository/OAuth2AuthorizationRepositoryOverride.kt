package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.Authorization
import org.springframework.security.oauth2.core.OAuth2TokenType
import java.util.Optional

interface OAuth2AuthorizationRepositoryOverride {
    fun findByToken(token: String, tokenType: OAuth2TokenType?): Optional<Authorization>
}
