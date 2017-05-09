package at.medunigraz.imi.reassess.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import at.medunigraz.imi.reassess.model.NoteEvent;

public class NoteEventDAOTest {
	
	@Test
	public void testGet() {
		NoteEventDAO noteDao = new NoteEventDAO();
		NoteEvent note = noteDao.get(1);

		assertNotNull(note.getSubjectId());
		assertNotNull(note.getHospitalAdmissionId());
		assertNotNull(note.getChartDate());
		assertNotNull(note.getCategory());
		assertNotNull(note.getDescription());
		assertFalse(note.isError());
		assertNotNull(note.getText());
	}

}
