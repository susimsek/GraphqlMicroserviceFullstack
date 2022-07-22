package io.github.susimsek.mscommonweb.mongo.model.audit;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005R \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t\u00a8\u0006\f"}, d2 = {"Lio/github/susimsek/mscommonweb/mongo/model/audit/AbstractUserAuditingEntity;", "Lio/github/susimsek/mscommonweb/mongo/model/audit/AbstractAuditingEntity;", "createdBy", "", "lastModifiedBy", "(Ljava/lang/String;Ljava/lang/String;)V", "getCreatedBy", "()Ljava/lang/String;", "setCreatedBy", "(Ljava/lang/String;)V", "getLastModifiedBy", "setLastModifiedBy", "ms-common-web"})
public class AbstractUserAuditingEntity extends io.github.susimsek.mscommonweb.mongo.model.audit.AbstractAuditingEntity {
    @org.jetbrains.annotations.Nullable
    @org.springframework.data.mongodb.core.mapping.Field(value = "created_by")
    @org.springframework.data.mongodb.core.index.Indexed
    @org.springframework.data.annotation.CreatedBy
    private java.lang.String createdBy;
    @org.jetbrains.annotations.Nullable
    @org.springframework.data.mongodb.core.mapping.Field(value = "last_modified_by")
    @org.springframework.data.annotation.LastModifiedBy
    private java.lang.String lastModifiedBy;
    
    public AbstractUserAuditingEntity() {
        super(null, null);
    }
    
    public AbstractUserAuditingEntity(@org.jetbrains.annotations.Nullable
    java.lang.String createdBy, @org.jetbrains.annotations.Nullable
    java.lang.String lastModifiedBy) {
        super(null, null);
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getCreatedBy() {
        return null;
    }
    
    public final void setCreatedBy(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getLastModifiedBy() {
        return null;
    }
    
    public final void setLastModifiedBy(@org.jetbrains.annotations.Nullable
    java.lang.String p0) {
    }
}