package at.medunigraz.imi.reassess.model.iterator;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoteEventIteratorTest {
	
	private static final int COUNT = 2083180;

	@Test
	public void testIterator() {
		NoteEventIterator iter = new NoteEventIterator();
		
		assertTrue(iter.hasNext());
		assertEquals(1, iter.next().getRowId().intValue());
		
		iter.fastForwardTo(1000);
		assertEquals(1001, iter.next().getRowId().intValue());
		
		// When reading the last results, it should have less elements than the pagesize.
		int halfPage = NoteEventIterator.PAGESIZE / 2;
		iter.fastForwardTo(COUNT - halfPage);
		int actual = 0;
		for (; iter.hasNext(); actual++, iter.next());
		assertEquals(halfPage, actual);

		// When reading after the end, it should has no elements.
		iter.fastForwardTo(COUNT);
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void performanceTest() {
		final int records = 1000;
		
		NoteEventIterator iter = new NoteEventIterator();
		iter.fastForwardTo(COUNT - records);
		
		long start = System.currentTimeMillis();
		while (iter.hasNext()) {
			iter.next();
		}
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
		
		// Assuming that 1000 records will be read in less than 10 seconds...
		assertThat(end - start, lessThan(10000l));
	}

}
