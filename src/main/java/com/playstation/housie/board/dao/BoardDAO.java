package com.playstation.housie.board.dao;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.playstation.housie.board.HousieBoard;
import com.playstation.housie.dao.DAO;
import com.playstation.housie.utils.EntityManagerUtil;

public class BoardDAO implements DAO<HousieBoard> {
	private EntityManager em = EntityManagerUtil.getEntityManager();

	@Override
	public HousieBoard load(long id) {
		return em.find(HousieBoard.class, id);
	}

	@Override
	public void create(HousieBoard entity) {
		executeInsideTransaction(entityManager -> entityManager.persist(entity));
	}

	@Override
	public HousieBoard update(HousieBoard entity) {
		long id = entity.getId();
		executeInsideTransaction(entityManager -> entityManager.merge(entity));
		return load(id);

	}

	@Override
	public void delete(HousieBoard entity) {
		executeInsideTransaction(entityManager -> entityManager.remove(entity));

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
