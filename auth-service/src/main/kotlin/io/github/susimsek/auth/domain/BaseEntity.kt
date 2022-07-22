package io.github.susimsek.auth.domain

import io.github.susimsek.auth.domain.audit.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import java.util.UUID

open class BaseEntity(
    @Id
    open var id: String = UUID.randomUUID().toString()
) : AbstractAuditingEntity()
