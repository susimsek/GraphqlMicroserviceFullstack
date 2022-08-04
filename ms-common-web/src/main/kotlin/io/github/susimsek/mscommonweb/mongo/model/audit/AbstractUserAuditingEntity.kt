package io.github.susimsek.mscommonweb.mongo.model.audit

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable

open class AbstractUserAuditingEntity(

    @CreatedBy
    @Indexed
    @Field("created_by")
    var createdBy: String? = null,

    @LastModifiedBy
    @Field("last_modified_by")
    var lastModifiedBy: String? = null
) : AbstractAuditingEntity(), Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
