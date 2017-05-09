package at.medunigraz.imi.reassess.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

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

	public List<NoteEvent> list(int first, int max) {
		CriteriaQuery<NoteEvent> criteria = em.getCriteriaBuilder().createQuery(NoteEvent.class);
		criteria.select(criteria.from(NoteEvent.class));
		return em.createQuery(criteria).setFirstResult(first).setMaxResults(max).getResultList();
	}
}
