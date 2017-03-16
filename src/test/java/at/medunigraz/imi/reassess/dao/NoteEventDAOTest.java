package at.medunigraz.imi.reassess.dao;

import at.medunigraz.imi.reassess.model.NoteEvent;
import junit.framework.TestCase;

public class NoteEventDAOTest extends TestCase {
	
	public void testGet() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);
		assertNotNull(note.getSubjectId());
	}

}
