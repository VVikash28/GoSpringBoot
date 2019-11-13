package com.capgemini.go.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.capgemini.go.dao.HQLQuerryMapper;

public class GenerateID {

	public static long generate() {

		long n;

		Session session = null;
		SessionFactory sessionFactory = null;

		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from OrderDTO");
		n = (long) query.uniqueResult();
		return n;

	}

}