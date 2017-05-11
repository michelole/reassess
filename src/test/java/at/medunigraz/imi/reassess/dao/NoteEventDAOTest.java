package at.medunigraz.imi.reassess.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import at.medunigraz.imi.reassess.model.NoteEvent;

public class NoteEventDAOTest {
	
	@Test
	public void testGet() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);

		assertNotNull(note.getSubjectId());
		assertNotNull(note.getAdmissionId());
		assertNotNull(note.getChartDate());
		assertNotNull(note.getCategory());
		assertNotNull(note.getDescription());
		assertFalse(note.isError());
		assertNotNull(note.getText());
	}
	
	@Test
	public void testList() {
		final int pageSize = 10;
		
		NoteEventDAO noteDao = new NoteEventDAO();
		List<NoteEvent> notes = noteDao.list(0, pageSize);
		
		assertEquals(pageSize, notes.size());
		
		assertEquals(1, notes.get(0).getRowId().intValue());
	}

}
