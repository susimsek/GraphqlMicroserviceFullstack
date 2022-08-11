package io.github.susimsek.auth.domain

import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@QueryEntity
@Document(collection = "oauth2_authority")
data class Authority(

    @Indexed
    @Field("name")
    var name: String

    ) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Authority) return false

        return name == other.name
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
