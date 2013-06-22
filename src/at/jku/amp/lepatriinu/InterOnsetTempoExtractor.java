package at.jku.amp.lepatriinu;

import static at.jku.amp.lepatriinu.Analyzer.MAX_TEMPO;
import static at.jku.amp.lepatriinu.Analyzer.MIN_TEMPO;
import static at.jku.amp.lepatriinu.Analyzer.TEMPO_KEY_TOLERANCE;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Uses Inter Onset Intervals (slides 5.10 - 5.14) to estimate the beat.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 */
public class InterOnsetTempoExtractor extends TempoExtractor {
	private Map<Integer, Integer> occurrences = new LinkedHashMap<>();
	private Map<Double, Integer> map = new LinkedHashMap<>();
	private int maxInt = 0;

	@Override
	public double execute(AudioFile audiofile, LinkedList<Double> onsets) {
		occurrences = new LinkedHashMap<>();
		map = new LinkedHashMap<>();
		maxInt = 0;

		double currentOnset, nextOnset, distance;

		// calculate distances
		for (int i = 0; i < onsets.size(); i++) {
			currentOnset = onsets.get(i);

			for (int j = i; j < onsets.size(); j++) {
				nextOnset = onsets.get(j);
				distance = nextOnset - currentOnset;
				if (distance < MIN_TEMPO || distance > MAX_TEMPO)
					continue;
				count(distance);
			}
		}

		// calculate maximum
		int occ, max = 0;
		double maxKey = -1;
		for (Entry<Double, Integer> e : map.entrySet()) {
			occ = occurrences.get(e.getValue());
			if (occ > max) {
				max = occ;
				maxKey = e.getKey();
			}
		}

		assert maxKey > 0 : "Tempo extraction: no maximum found!";

		// translate to bpm
		return 60 / maxKey;
	}

	private void count(double distance) {
		final int key = getKey(distance);
		occurrences.put(key, occurrences.get(key) + 1);
	}

	private int getKey(double value) {
		for (Double key : map.keySet()) {
			if (value - TEMPO_KEY_TOLERANCE < key
					&& key < value + TEMPO_KEY_TOLERANCE)
				return map.get(key);
		}
		map.put(value, maxInt);
		occurrences.put(maxInt, 0);
		maxInt++;
		return maxInt - 1;
	}

}
