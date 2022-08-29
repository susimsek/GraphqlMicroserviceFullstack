package io.github.susimsek.auth.domain

import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@QueryEntity
@Document(collection = "oauth2_authorization_consent")
data class AuthorizationConsent(

    @Indexed
    @Field("registered_client_id")
    var registeredClientId: String,

    @Indexed
    @Field("principal_name")
    var principalName: String,

    @Field("authorities")
    var authorities: MutableSet<String>
) : BaseEntity()
