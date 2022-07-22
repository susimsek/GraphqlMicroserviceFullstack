package io.github.susimsek.mscommonweb.mongo.model

import io.github.susimsek.mscommonweb.mongo.model.audit.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import java.util.UUID

open class BaseEntity(
    @Id
    var id: String = UUID.randomUUID().toString()
) : AbstractAuditingEntity()
