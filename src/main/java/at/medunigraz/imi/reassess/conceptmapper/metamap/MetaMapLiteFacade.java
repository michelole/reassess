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
import gov.nih.nlm.nls.metamap.lite.types.Entity;
import gov.nih.nlm.nls.metamap.lite.types.Ev;
import gov.nih.nlm.nls.ner.MetaMapLite;

public class MetaMapLiteFacade implements ConceptMapper {

	private static final Logger LOG = LogManager.getLogger();

	private static final MetaMapLiteFacade instance = new MetaMapLiteFacade();

	private MetaMapLite metaMapLiteInst;

	public static MetaMapLiteFacade getInstance() {
		return instance;
	}

	private MetaMapLiteFacade() {
		LOG.info("Building MetaMap instance...");

		Properties myProperties = MetaMapLite.getDefaultConfiguration();

		String configPropertyFilename = System.getProperty("metamaplite.property.file",
				getClass().getResource("/metamaplite.properties").getFile());

		try {
			myProperties.load(new FileReader(configPropertyFilename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MetaMapLite.expandModelsDir(myProperties);
		MetaMapLite.expandIndexDir(myProperties);

		try {
			metaMapLiteInst = new MetaMapLite(myProperties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOG.info("Building MetaMap instance finished.");
	}

	public List<String> map(String text) {
		List<String> ret = new ArrayList<String>();
		
		List<Entity> entityList = process(text);
		
		for (Entity entity : entityList) {
			for (Ev ev : entity.getEvSet()) {
				ret.add(ev.getConceptInfo().getCUI());
				LOG.trace(ev);
			}
		}

		return ret;
	}

	private List<Entity> process(String text) {
		int length = text.length();
		LOG.debug("Processing \"{}\"...", text.substring(0, Math.min(length, 50)));
		
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
		
		float duration = (end - start) / 1000f;

		LOG.debug("Processed {} chars in {} sec ({} chars/sec).", length, duration, length/duration);

		return entityList;
	}

}
