package com.pe.multimodule.dao.impl;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractEntity{

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    protected AbstractNamedEntity() {
        // POJO
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
