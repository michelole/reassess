package at.medunigraz.imi.reassess.model.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.medunigraz.imi.reassess.dao.NoteEventDAO;
import at.medunigraz.imi.reassess.model.NoteEvent;

public class NoteEventIterator implements Iterator<NoteEvent> {

	static final int PAGESIZE = 1000;

	private static final Logger LOG = LogManager.getLogger();

	private int currentPosition = 0;

	private boolean finished = false;

	private List<NoteEvent> buffer;

	private NoteEventDAO dao = new NoteEventDAO();

	public NoteEventIterator() {
		reset();
	}

	@Override
	public boolean hasNext() {
		return !buffer.isEmpty();
	}

	@Override
	public NoteEvent next() {
		// Keeps it between PAGESIZE / 2 and PAGESIZE + PAGESIZE / 2
		if (buffer.size() < PAGESIZE / 2) {
			fillBuffer();
		}

		return buffer.remove(0);
	}
	
	private void eraseBuffer() {
		this.buffer = new ArrayList<>();
	}

	/**
	 * Fills buffer with PAGESIZE elements. Prevents further filling when DAO
	 * returns the last elements.
	 */
	private void fillBuffer() {
		if (finished) {
			return;
		}

		List<NoteEvent> newNotes = dao.list(currentPosition, PAGESIZE);

		int size = newNotes.size();
		if (size < PAGESIZE) {
			finished = true;
		}

		buffer.addAll(newNotes);
		currentPosition += PAGESIZE;

		LOG.trace("Filled buffer with {} elements.", size);
	}

	public void reset() {
		this.fastForwardTo(0);
	}

	public void fastForwardTo(int position) {
		this.currentPosition = position;
		eraseBuffer();
		fillBuffer();
	}

}
