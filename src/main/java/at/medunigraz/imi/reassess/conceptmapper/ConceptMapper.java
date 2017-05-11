package at.medunigraz.imi.reassess.conceptmapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ConceptMapper {
	public List<String> map(String text);

	public String annotate(String text);
	
	default public Set<String> uniqueMap(String text) {
		return new HashSet<String>(map(text));
	}
}
