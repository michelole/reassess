package at.medunigraz.imi.reassess.conceptmapper.metamap;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.medunigraz.imi.reassess.conceptmapper.ConceptMapper;
import bioc.BioCDocument;
import gov.nih.nlm.nls.metamap.document.FreeText;
import gov.nih.nlm.nls.metamap.lite.types.ConceptInfo;
import gov.nih.nlm.nls.metamap.lite.types.Entity;
import gov.nih.nlm.nls.metamap.lite.types.Ev;
import gov.nih.nlm.nls.ner.MetaMapLite;

public class MetaMapLiteFacade implements ConceptMapper {

	private static final Logger LOG = LogManager.getLogger();

	private static final MetaMapLiteFacade instance = new MetaMapLiteFacade();

	private MetaMapLite metaMapLiteInst;

	private Properties properties;

	public static MetaMapLiteFacade getInstance() {
		return instance;
	}

	private MetaMapLiteFacade() {
		LOG.info("Building MetaMap instance...");

		properties = MetaMapLite.getDefaultConfiguration();

		String configPropertyFilename = System.getProperty("metamaplite.property.file",
				getClass().getResource("/metamaplite.properties").getFile());

		try {
			properties.load(new FileReader(configPropertyFilename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MetaMapLite.expandModelsDir(properties);
		MetaMapLite.expandIndexDir(properties);

		try {
			metaMapLiteInst = new MetaMapLite(properties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOG.info("Building MetaMap instance finished.");
	}

	/*
	 * (non-Javadoc)
	 * @see at.medunigraz.imi.reassess.conceptmapper.ConceptMapper#map(java.lang.String)
	 */
	public List<String> map(String text) {
		List<String> ret = new ArrayList<String>();

		List<Entity> entityList = process(text);

		for (Entity entity : entityList) {
			// TODO Should submatches be skipped as in annotate()?
			for (Ev ev : entity.getEvSet()) {
				ret.add(ev.getConceptInfo().getCUI());
				LOG.trace(ev);
			}
		}

		return ret;
	}

	private List<Entity> process(String text) {
		int length = text.length();
		LOG.trace("Processing \"{}\"...", text.substring(0, Math.min(length, 50)));

		long start = System.currentTimeMillis();

		BioCDocument document = FreeText.instantiateBioCDocument(text);
		document.setID("1");
		List<BioCDocument> documentList = new ArrayList<BioCDocument>();
		documentList.add(document);

		List<Entity> entityList = null;
		try {
			entityList = metaMapLiteInst.processDocumentList(documentList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();

		float duration = (end - start + 1) / 1000f;

		LOG.debug("Processed {} chars in {} sec ({} chars/sec).", length, duration, length / duration);

		return entityList;
	}

	/*
	 * (non-Javadoc)
	 * @see at.medunigraz.imi.reassess.conceptmapper.ConceptMapper#annotate(java.lang.String)
	 */
	public String annotate(String text) {
		List<Entity> entityList = process(text);

		int length = text.length();

		StringBuilder sb = new StringBuilder(length);

		int i = 0;
		for (Entity entity : entityList) {
			int start = entity.getStart();

			// Skip submatches
			if (start < i) {
				continue;
			}

			String matched = entity.getMatchedText();

			sb.append(text.substring(i, start));
			sb.append("<");
			sb.append(matched);
			sb.append("|");

			for (Ev ev : entity.getEvSet()) {
				ConceptInfo conceptInfo = ev.getConceptInfo();
				sb.append(conceptInfo.getCUI());
				sb.append(":");
				sb.append(conceptInfo.getPreferredName());
				sb.append("|");
			}
			sb.append(">");

			i = entity.getStart() + entity.getLength();
		}

		sb.append(text.substring(i, length));

		return sb.toString();
	}

}
