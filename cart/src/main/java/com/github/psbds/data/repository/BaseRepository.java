package com.github.psbds.data.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.Dependent;

/**
 * Generic base repository that provides Panache functionality for any entity type.
 * This eliminates the need to create separate PanacheRepository classes for each entity.
 * 
 * Note: Generic CDI beans must be @Dependent scoped.
 * 
 * @param <Entity> The entity type this repository manages
 * @param <Id> The ID type of the entity
 */
@Dependent
public class BaseRepository<Entity, Id> implements PanacheRepositoryBase<Entity, Id> {

}