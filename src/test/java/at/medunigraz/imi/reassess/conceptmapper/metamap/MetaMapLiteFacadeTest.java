package at.medunigraz.imi.reassess.conceptmapper.metamap;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class MetaMapLiteFacadeTest extends TestCase {

	public void testMap() {
		MetaMapLiteFacade mm = MetaMapLiteFacade.getInstance();

		List<String> expected = new ArrayList<String>();
		expected.add("C0030705"); // Patients
		expected.add("C0006142"); // Malignant neoplasm of breast
		expected.add("C0678222"); // Breast Carcinoma
		List<String> actual = mm.map("The patient has breast cancer.");

		assertEquals(expected, actual);
	}

	public void testAnnotate() {
		MetaMapLiteFacade mm = MetaMapLiteFacade.getInstance();

		// Basic test
		String actual = mm.annotate("The patient has breast cancer.");
		String expected = "The <patient|C0030705:Patients|> has <breast cancer|C0006142:Malignant neoplasm of breast|C0678222:Breast Carcinoma|>.";
		assertEquals(expected, actual);

		// Submatches
		actual = mm.annotate("History of present illness");
		expected = "<History of present illness|C0262512:History of present illness|C0488508:History of present illness:Finding:Point in time:^Patient:Nominal:Reported|>";
		assertEquals(expected, actual);
		
		// Double spacing
		actual = mm.annotate("headache.  headache.");
		expected = "<headache|C0018681:Headache|C2096315:ENT surgical result nose headache|>.  <headache|C2096315:ENT surgical result nose headache|C0018681:Headache|>.";
		assertEquals(expected, actual);
	}
}
