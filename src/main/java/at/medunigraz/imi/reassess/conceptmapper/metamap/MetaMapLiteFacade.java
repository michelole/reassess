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

	/** location of metamaplite.properties configuration file */
	static String configPropertyFilename = System.getProperty("metamaplite.property.file",
			"src/main/resources/metamaplite.properties");

	private MetaMapLite metaMapLiteInst;

	public MetaMapLiteFacade() {
		LOG.info("Building MetaMap instance...");

		Properties myProperties = MetaMapLite.getDefaultConfiguration();

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
	}

	public List<String> map(String text) {
		List<String> ret = new ArrayList<String>();
		LOG.debug("Mapping [" + text + "]");

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
		for (Entity entity : entityList) {
			for (Ev ev : entity.getEvSet()) {
				ret.add(ev.getConceptInfo().getCUI());
				LOG.debug(ev.getConceptInfo().getCUI() + "|" + entity.getMatchedText());
			}
		}

		return ret;
	}

}
