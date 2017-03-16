package at.medunigraz.imi.reassess.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();

	private EntityManagerFactory emf;
	private EntityManager em;

	public DAOFactory() {
		emf = Persistence.createEntityManagerFactory("at.medunigraz.imi.reassess");
		em = emf.createEntityManager();
	}

	public static DAOFactory getInstance() {
		return instance;
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public void close() {
		em.close();
		emf.close();
	}

}
