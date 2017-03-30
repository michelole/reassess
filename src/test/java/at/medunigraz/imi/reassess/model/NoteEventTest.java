package at.medunigraz.imi.reassess.model;

import at.medunigraz.imi.reassess.dao.NoteEventDAO;
import junit.framework.TestCase;

public class NoteEventTest extends TestCase {
	
	public void testGetCUIs() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);
		
		// We should get at least some (>10) CUIs.
		assertTrue(note.getCUIs().size() > 10);
	}
	
	public void testGetAnnotatedText() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);
		
		// Annotated text should be longer than original.
		assertTrue(note.getAnnotatedText().length() > note.getText().length());
	}
}
