package com.playstation.housie.profile.dao;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.playstation.housie.profile.Profile;
import com.playstation.housie.utils.EntityManagerUtil;

public class ProfileDAO {
	private EntityManager em = EntityManagerUtil.getEntityManager();

	public Profile load(long id) {
		return em.find(Profile.class, id);
	}


	public void create(Profile entity) {
		executeInsideTransaction(entityManager -> entityManager.persist(entity));
	}
	
	public Profile update(Profile entity) {
		return null;
	}

	
	public void delete(Profile entity) {
		
	}
	
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			action.accept(em);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		}
	}

}
