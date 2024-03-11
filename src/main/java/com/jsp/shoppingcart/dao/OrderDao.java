package com.jsp.shoppingcart.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.shoppingcart.dto.Orders;

	
@Repository
public class OrderDao {
	@Autowired
	EntityManagerFactory emf;
	
	public void saveOrder(Orders o) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(o);
		et.commit();
	}
	
	public void updateOrder(Order Order) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.merge(Order);
		et.commit();
	}
	
	public void deleteOrderById(int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Order o = em.find(Order.class, id);
		et.begin();
		em.remove(o);
		et.commit();
	}
	
	public Order findOrderById(int id) {
		EntityManager em = emf.createEntityManager();
		
		Order o = em.find(Order.class, id);
		
		if(o != null) {
			return o;
		}
		else {
			return null;
		}
	}
}
