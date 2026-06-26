package com.pe.multimodule.dao.api;

public interface CrudDao<IdType, Type> {

    /**
     * Save the entity in the database.
     *
     * @param e the entity being persisted
     */
    Type save(Type e);

    /**
     * Loads an entity from the database by the given id, or null if not found.
     *
     * @param id the entity's id
     * @return the loaded entity
     */
    Type loadById(IdType id);

    /**
     * @param e the entity's id being deleted
     */
    void delete(Type e);
}
