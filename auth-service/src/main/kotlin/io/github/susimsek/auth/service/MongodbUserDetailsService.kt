package io.github.susimsek.auth.service

import io.github.susimsek.auth.domain.User
import io.github.susimsek.auth.repository.OAuth2UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class MongodbUserDetailsService(
    private val userRepository: OAuth2UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            .map { createSpringSecurityUser(it) }
            .orElseThrow { UsernameNotFoundException(username) }
    }

    private fun createSpringSecurityUser(user: User): org.springframework.security.core.userdetails.User {
        val authorities = user.authorities.map { SimpleGrantedAuthority(it.name) }.toMutableSet()
        return org.springframework.security.core.userdetails.User(
            user.id, user.password, user.enabled,
            !user.accountExpired, !user.credentialsExpired, !user.accountLocked, authorities
        )
    }
}
