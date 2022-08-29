package io.github.susimsek.auth.domain

import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.OffsetDateTime

@QueryEntity
@Document(collection = "oauth2_authorization")
data class Authorization(

    @Field("registered_client_id")
    var registeredClientId: String,

    @Field("principal_name")
    var principalName: String,

    @Field("authorization_grant_type")
    var authorizationGrantType: String,

    @Field("attributes")
    var attributes: String? = null,

    @Indexed
    @Field("state")
    var state: String? = null,

    @Indexed
    @Field("authorization_code_value")
    var authorizationCodeValue: String? = null,

    @Field("authorization_code_issued_at")
    var authorizationCodeIssuedAt: OffsetDateTime? = null,

    @Field("authorization_code_expires_at")
    var authorizationCodeExpiresAt: OffsetDateTime? = null,

    @Field("authorization_code_metadata")
    var authorizationCodeMetadata: String? = null,

    @Indexed
    @Field("access_token_value")
    var accessTokenValue: String? = null,

    @Field("access_token_issued_at")
    var accessTokenIssuedAt: OffsetDateTime? = null,

    @Field("access_token_expires_at")
    var accessTokenExpiresAt: OffsetDateTime? = null,

    @Field("access_token_metadata")
    var accessTokenMetadata: String? = null,

    @Field("access_token_type")
    var accessTokenType: String? = null,

    @Field("access_token_scopes")
    var accessTokenScopes: MutableSet<String>? = null,

    @Field("oidc_id_token_value")
    var oidcIdTokenValue: String? = null,

    @Field("oidc_id_token_issued_at")
    var oidcIdTokenIssuedAt: OffsetDateTime? = null,

    @Field("oidc_id_token_expires_at")
    var oidcIdTokenExpiresAt: OffsetDateTime? = null,

    @Field("oidc_id_token_metadata")
    var oidcIdTokenMetadata: String? = null,

    @Indexed
    @Field("refresh_token_value")
    var refreshTokenValue: String? = null,

    @Field("refresh_token_issued_at")
    var refreshTokenIssuedAt: OffsetDateTime? = null,

    @Field("refresh_token_expires_at")
    var refreshTokenExpiresAt: OffsetDateTime? = null,

    @Field("refresh_token_metadata")
    var refreshTokenMetadata: String? = null,

   override var id: String
) : BaseEntity(id)
