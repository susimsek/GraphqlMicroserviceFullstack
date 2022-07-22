package io.github.susimsek.auth.domain.audit

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Field

open class AbstractUserAuditingEntity(

    @CreatedBy
    @Indexed
    @Field("created_by")
    var createdBy: String? = null,

    @LastModifiedBy
    @Field("last_modified_by")
    var lastModifiedBy: String? = null
) : AbstractAuditingEntity()
