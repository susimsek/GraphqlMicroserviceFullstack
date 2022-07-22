package io.github.susimsek.auth.service

import io.github.susimsek.auth.domain.User
import io.github.susimsek.auth.repository.OAuth2AuthorityRepository
import io.github.susimsek.auth.repository.OAuth2UserRepository
import io.github.susimsek.auth.security.user.OAuth2UserInfo
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class MongodbUserService(
    private val authorityRepository: OAuth2AuthorityRepository,
    private val userRepository: OAuth2UserRepository
) {

    fun registerOauth2User(user: OAuth2UserInfo) {
        val authorityName = "ROLE_USER"
        val authority = authorityRepository.findByName(authorityName).orElseThrow{ DataRetrievalFailureException(
            "The Authority with name '$authorityName' was not found in the RegisteredClientRepository.") }
        val newUser = User(
            email = user.email!!,
            authorities = mutableSetOf(authority)
        )
        userRepository.save(newUser)
    }

    fun findByEmail(email: String): Optional<User> {
        return userRepository.findByEmail(email)
    }
}