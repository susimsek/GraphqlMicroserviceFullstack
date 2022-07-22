package io.github.susimsek.auth.domain

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Clock
import java.time.OffsetDateTime

@Document(collection = "oauth2_registered_client")
data class Client(

    @Field("client_id")
    @Indexed
    var clientId: String,

    @Field("client_id_issued_at")
    var clientIdIssuedAt: OffsetDateTime = OffsetDateTime.now(Clock.systemDefaultZone()),

    @Field("client_secret")
    var clientSecret: String? = null,

    @Field("client_secret_expires_at")
    var clientSecretExpiresAt: OffsetDateTime? = null,

    @Field("client_name")
    var clientName: String,

    @Field("client_authentication_methods")
    var clientAuthenticationMethods: MutableSet<String>,

    @Field("authorization_grant_types")
    var authorizationGrantTypes: MutableSet<String>,

    @Field("redirect_uris")
    var redirectUris: MutableSet<String>,

    @Field("scopes")
    var scopes: MutableSet<String>,

    @Field("client_settings")
    var clientSettings: String,

    @Field("token_settings")
    var tokenSettings: String,

    override var id: String
) : BaseEntity(id)