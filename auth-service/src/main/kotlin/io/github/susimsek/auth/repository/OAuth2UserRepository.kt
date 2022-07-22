package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface OAuth2UserRepository : MongoRepository<User, String>{
    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
}