package com.playstation.housie.ticket.dao;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.playstation.housie.dao.DAO;
import com.playstation.housie.ticket.HousieTicket;
import com.playstation.housie.utils.EntityManagerUtil;

public class TicketDAO implements DAO<HousieTicket>{
	private EntityManager em = EntityManagerUtil.getEntityManager();
	
	@Override
	public HousieTicket load(long id) {
		return em.find(HousieTicket.class, id);
	}

	@Override
	public void create(HousieTicket entity) {
		executeInsideTransaction(entityManager -> entityManager.persist(entity));
	}

	@Override
	public HousieTicket update(HousieTicket entity) {
		long id = entity.getId();
		executeInsideTransaction(entityManager -> entityManager.merge(entity));
		return load(id);
	}

	@Override
	public void delete(HousieTicket entity) {
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
