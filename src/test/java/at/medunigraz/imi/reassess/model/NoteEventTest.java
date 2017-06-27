package at.medunigraz.imi.reassess.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import at.medunigraz.imi.reassess.dao.NoteEventDAO;

public class NoteEventTest {
	
	@Test
	public void testGetCUIs() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);
		
		// We should get at least some (>10) CUIs.
		assertTrue(note.getCUIs().size() > 10);
	}
	
	@Test
	public void testGetAnnotatedText() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);
		
		// Annotated text should be longer than original.
		assertTrue(note.getAnnotatedText().length() > note.getText().length());
	}
	
	@Test
	public void testIsDischargeSummary() {
		NoteEventDAO noteDao = new NoteEventDAO();

		assertTrue(noteDao.get(1).isDischargeSummary());
		assertFalse(noteDao.get(59653).isDischargeSummary());
	}
}
