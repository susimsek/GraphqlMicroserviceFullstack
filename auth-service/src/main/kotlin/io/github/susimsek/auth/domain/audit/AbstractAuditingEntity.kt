package io.github.susimsek.auth.domain.audit

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Clock
import java.time.OffsetDateTime

open class AbstractAuditingEntity(

    @CreatedDate
    @Field("created_date")
    var createdDate: OffsetDateTime = OffsetDateTime.now(Clock.systemDefaultZone()),

    @LastModifiedDate
    @Field("last_modified_date")
    var lastModifiedDate: OffsetDateTime = OffsetDateTime.now(Clock.systemDefaultZone())
)
