package at.medunigraz.imi.reassess.dao;

import javax.persistence.EntityManager;

import at.medunigraz.imi.reassess.model.NoteEvent;

public class NoteEventDAO {

	private EntityManager em;

	public NoteEventDAO() {
		em = DAOFactory.getInstance().getEntityManager();
	}

	public NoteEvent get(Integer id) {
		NoteEvent note = em.find(NoteEvent.class, id);
		return note;
	}
}
