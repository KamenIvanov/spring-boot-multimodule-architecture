package com.pe.multimodule.domain;

import java.time.Instant;

public abstract class AbstractDomain<IdType> {

    private IdType id;
    private Instant createdAt;
    private Instant updatedAt;

    protected AbstractDomain() {
        // hibernate will initialize later
    }

    protected AbstractDomain(IdType id) {
        setId(id);
    }

    public IdType getId() {
        return id;
    }

    public void setId(IdType id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj != null && obj.getClass() == this.getClass() && id.equals(((AbstractDomain<IdType>) obj).id));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + "]";
    }
}
