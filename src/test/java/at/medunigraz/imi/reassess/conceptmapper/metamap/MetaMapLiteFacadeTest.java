package at.medunigraz.imi.reassess.conceptmapper.metamap;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class MetaMapLiteFacadeTest extends TestCase {

	public void testMap() {
		MetaMapLiteFacade mm = MetaMapLiteFacade.getInstance();

		List<String> expected = new ArrayList<String>();
		expected.add("C0030705"); // Patient
		expected.add("C0006142"); // Malignant Breast Neoplasm
		expected.add("C0678222"); // Breast Carcinoma
		List<String> actual = mm.map("The patient has breast cancer.");

		assertEquals(expected, actual);
	}
}
