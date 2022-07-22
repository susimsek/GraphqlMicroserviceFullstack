package io.github.susimsek.auth.domain

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "oauth2_user")
data class User(

    @Indexed(unique = true)
    @Field("username")
    var username: String? =  null,

    @Field("password")
    var password: String? = null,

    @Indexed(unique = true)
    @Field("email")
    var email: String,

    @Field("account_expired")
    var accountExpired: Boolean = false,

    @Field("account_locked")
    var accountLocked: Boolean = false,

    @Field("credentials_expired")
    var credentialsExpired: Boolean = false,

    @Field("enabled")
    var enabled: Boolean = true,

    @DBRef(lazy = true)
    @Field("authorities")
    var authorities: MutableSet<Authority>,

    ) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        return id == other.id
    }


    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
