package at.medunigraz.imi.reassess.model;

import at.medunigraz.imi.reassess.dao.NoteEventDAO;
import junit.framework.TestCase;

public class NoteEventTest extends TestCase {
	
	public void testGetCUIs() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);
		
		assertTrue(note.getCUIs().size() > 10);
	}
}
