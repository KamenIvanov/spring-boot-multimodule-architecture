package com.pe.multimodule.domain;

public class AbstractNamedDomain<IdType> extends AbstractDomain<IdType> {

    private String name;

    public AbstractNamedDomain() {
        // POJO
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
