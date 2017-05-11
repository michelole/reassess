package at.medunigraz.imi.reassess.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	/**
	 * Gets a paginated list of inpatient ICU notes. MIMIC-III notes states
	 * that: "If a patient is an outpatient, there will not be an HADM_ID
	 * associated with the note. If the patient is an inpatient, but was not
	 * admitted to the ICU for that particular hospital admission, then there
	 * will not be an HADM_ID associated with the note."
	 * 
	 * @param first
	 *            First result, starting with 0.
	 * @param max
	 *            Maximum number of results
	 * @return List of notes.
	 */
	public List<NoteEvent> list(int first, int max) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<NoteEvent> criteria = cb.createQuery(NoteEvent.class);
		Root<NoteEvent> root = criteria.from(NoteEvent.class);
		criteria.select(root).orderBy(cb.asc(root.get("subjectId")), cb.asc(root.get("admissionId")),
				cb.asc(root.get("chartDate")), cb.asc(root.get("chartTime")), cb.asc(root.get("rowId")));
		criteria.where(cb.isNotNull(root.get("admissionId")));
		return em.createQuery(criteria).setFirstResult(first).setMaxResults(max).getResultList();
	}

	/**
	 * Counts the number of inpatient ICU notes.
	 * 
	 * @return
	 */
	public long count() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		Root<NoteEvent> root = criteria.from(NoteEvent.class);
		criteria.select(cb.count(root));
		criteria.where(cb.isNotNull(root.get("admissionId")));
		return em.createQuery(criteria).getSingleResult();
	}
}
