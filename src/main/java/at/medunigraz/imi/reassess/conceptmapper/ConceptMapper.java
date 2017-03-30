package at.medunigraz.imi.reassess.conceptmapper;

import java.util.List;

public interface ConceptMapper {
	public List<String> map(String text);

	public String annotate(String text);
}
