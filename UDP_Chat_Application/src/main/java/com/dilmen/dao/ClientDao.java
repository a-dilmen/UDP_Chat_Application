package com.dilmen.dao;

import javax.persistence.TypedQuery;
import org.hibernate.Session;
import com.dilmen.util.HibernateUtils;

import com.dilmen.entity.Client;

import java.util.List;

public class ClientDao {

	public void create(Client client) {
		Session session = null;
		try {
			session = dataBaseConnectionHibernate();
			session.getTransaction().begin();
			session.persist(client);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Some problem occured while adding Student to DB");
		} finally {
			session.close();
		}

	}

	public void delete(String id) {
		Session session = null;
		try {
			Client deletedClient = find(id);
			if (deletedClient != null) {
				session = dataBaseConnectionHibernate();
				session.getTransaction().begin();
				session.remove(deletedClient);
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Some problem occured while adding Client to DB");
		} finally {
			session.close();
		}
	}

	public void update(Client entity) {
		Session session = null;
		try {
			Client client = find(entity.getUsername());
			if (client != null) {
				client = entity;
				session = dataBaseConnectionHibernate();
				session.getTransaction().begin();
				session.update(client);
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Some problem occured while adding Client to DB");
		} finally {
			session.close();
		}
	}

	public Client find(String username) {
		Session session = dataBaseConnectionHibernate();
		Client client;
		try {
			client = session.find(Client.class, username);
			if (client != null) {
				return client;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Some problem occured while adding Client to DB");
		} finally {
			session.close();
		}
		return null;
	}

	public Client login(String username, String password) {
		Session session = null;
		Client client = null;
		session = dataBaseConnectionHibernate();
		String query = "select client from Client as client where username = :key1 and password = :key2";
		TypedQuery<Client> typedQuery = session.createQuery(query, Client.class);
		typedQuery.setParameter("key1", username);
		typedQuery.setParameter("key2", password);
		try {
			client = typedQuery.getSingleResult();
		} catch (Exception e) {
			System.out.println("no return value");
		}
		return client;

	}

	protected Session dataBaseConnectionHibernate() {
		return HibernateUtils.getSessionFactory().openSession();
	}

	public List<Client> findOnline() {
		Session session = null;
		List<Client> client = null;
		session = dataBaseConnectionHibernate();
		String query = "select client from Client as client where online = true";
		TypedQuery<Client> typedQuery = session.createQuery(query, Client.class);
		try {
			client = typedQuery.getResultList();
		} catch (Exception e) {
			System.out.println("no return value");
		}
		return client;
	}
}
