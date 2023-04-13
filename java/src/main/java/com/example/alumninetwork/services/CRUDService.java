package com.example.alumninetwork.services;

import java.util.Collection;

public interface CRUDService<T, ID> {
    /**
     * Finds an entity by its ID.
     *
     * @param id
     * @return entity
     */
    T findById(ID id);

    /**
     * Finds all the database records for the entity
     *
     * @return all entities in the database
     */
    Collection<T> findAll();

    /**
     * Adds a new record to the database for the entity
     *
     * @param entity
     * @return new entity
     */
    T add(T entity);

    /**
     * Updates and saves an entity
     *
     * @param entity
     */
    void update(T entity);

    /**
     * Deletes an entity by its ID
     *
     * @param id
     */
    void deleteById(ID id);
}
