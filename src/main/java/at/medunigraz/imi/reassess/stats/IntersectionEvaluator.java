package at.medunigraz.imi.reassess.stats;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.medunigraz.imi.reassess.model.NoteEvent;
import at.medunigraz.imi.reassess.model.iterator.NoteEventIterator;
import edu.stanford.nlp.stats.PrecisionRecallStats;

public class IntersectionEvaluator {

	private static final Logger LOG = LogManager.getLogger();

	public static void main(String[] args) {
		NoteEventIterator iter = new NoteEventIterator();

		PrecisionRecallStats overallStats = new PrecisionRecallStats();
		Set<String> admissionCUIs = new HashSet<>();
		Set<String> dischargeCUIs = new HashSet<>();
		int addmissionId = 0;

		int i = 0;

		StopWatch clock = new StopWatch();
		clock.start();

		while (iter.hasNext()) {
			i++;
			
			NoteEvent note = iter.next();

			int currentAddmissionId = note.getAdmissionId();
			Set<String> cuis = note.getUniqueCUIs();

			// Just finished one admission, let's compute stats from the
			// previous one.
			if (currentAddmissionId != addmissionId) {
				Set<String> intersectCUIs = new HashSet<>(admissionCUIs);
				intersectCUIs.retainAll(dischargeCUIs);

				int tp = intersectCUIs.size();
				int fp = dischargeCUIs.size() - tp;
				int fn = admissionCUIs.size() - tp;

				PrecisionRecallStats currentStats = new PrecisionRecallStats(tp, fp, fn);

				overallStats.addCounts(currentStats);

				printStats("Admission ID " + addmissionId, currentStats);
				printStats("Overall", overallStats);

				addmissionId = currentAddmissionId;
				admissionCUIs = new HashSet<>();
				
				clock.split();
				float rate = i / (clock.getSplitTime() / 1000f);
				LOG.info("Processed {} notes in {} ({} notes/sec).", i, clock.toSplitString(), rate);
			}

			// We only consider the last discharge summary of one admission and
			// also discard any previous discharge summaries.
			if (note.isDischargeSummary()) {
				dischargeCUIs = cuis;
			} else {
				admissionCUIs.addAll(cuis);
			}
		}
	}

	private static void printStats(String id, PrecisionRecallStats stats) {
		final int digits = 2;

		int fp = stats.getFP();
		int tp = stats.getTP();
		int fn = stats.getFN();

		String p = stats.getPrecisionDescription(digits);
		String r = stats.getRecallDescription(digits);
		String f1 = stats.getF1Description(digits);

		LOG.info("{}: FP = {}, TP = {}, FN = {}, P = {}, R = {}, F1 = {}", id, fp, tp, fn, p, r, f1);
	}

}
