package com.playstation.housie.dao;

/***
 * Interface for Data Access Object
 * @author sandesh.karambettu
 *
 * @param <Entity>
 */
public interface DAO<Entity> {
	/**
	 * Function to load the entity
	 * @param id
	 * @return entity loaded from the database
	 */
	Entity load(long id);
	/**
	 * Function to persist the entity in the database
	 * @param entity entity to persist
	 */
	void create(Entity entity);
	Entity update(Entity entity);
    void delete(Entity entity);
}
