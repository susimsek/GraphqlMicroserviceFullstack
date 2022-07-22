package io.github.susimsek.mscommonweb.mongo.model.audit;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005R \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t\u00a8\u0006\f"}, d2 = {"Lio/github/susimsek/mscommonweb/mongo/model/audit/AbstractAuditingEntity;", "", "createdDate", "Ljava/time/OffsetDateTime;", "lastModifiedDate", "(Ljava/time/OffsetDateTime;Ljava/time/OffsetDateTime;)V", "getCreatedDate", "()Ljava/time/OffsetDateTime;", "setCreatedDate", "(Ljava/time/OffsetDateTime;)V", "getLastModifiedDate", "setLastModifiedDate", "ms-common-web"})
public class AbstractAuditingEntity {
    @org.jetbrains.annotations.Nullable
    @org.springframework.data.mongodb.core.mapping.Field(value = "created_date")
    @org.springframework.data.annotation.CreatedDate
    private java.time.OffsetDateTime createdDate;
    @org.jetbrains.annotations.Nullable
    @org.springframework.data.mongodb.core.mapping.Field(value = "last_modified_date")
    @org.springframework.data.annotation.LastModifiedDate
    private java.time.OffsetDateTime lastModifiedDate;
    
    public AbstractAuditingEntity() {
        super();
    }
    
    public AbstractAuditingEntity(@org.jetbrains.annotations.Nullable
    java.time.OffsetDateTime createdDate, @org.jetbrains.annotations.Nullable
    java.time.OffsetDateTime lastModifiedDate) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.time.OffsetDateTime getCreatedDate() {
        return null;
    }
    
    public final void setCreatedDate(@org.jetbrains.annotations.Nullable
    java.time.OffsetDateTime p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.time.OffsetDateTime getLastModifiedDate() {
        return null;
    }
    
    public final void setLastModifiedDate(@org.jetbrains.annotations.Nullable
    java.time.OffsetDateTime p0) {
    }
}