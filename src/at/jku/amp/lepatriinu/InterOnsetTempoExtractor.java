package at.jku.amp.lepatriinu;

import static at.jku.amp.lepatriinu.Analyzer.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import at.cp.jku.teaching.amprocessing.AudioFile;

public class InterOnsetTempoExtractor extends TempoExtractor {
	private Map<Integer, Integer> occurrences = new LinkedHashMap<>();
	private Map<Double, Integer> map = new LinkedHashMap<>();
	private int maxInt = 0;

	@Override
	public double execute(AudioFile audiofile, LinkedList<Double> onsets) {
		double currentOnset, nextOnset, distance;

		// calculate distances
		for (int i = 0; i < onsets.size(); i++) {
			currentOnset = onsets.get(i);

			for (int j = i; j < onsets.size(); j++) {
				nextOnset = onsets.get(j);
				distance = nextOnset - currentOnset;
				count(distance);
			}
		}

		// calculate maximum
		int max = 0;
		double maxKey = -1;
		int occ;
		for (Entry<Double, Integer> e : map.entrySet()) {
			if(e.getKey() < MIN_TEMPO)
				continue;
			
			if(e.getKey() > MAX_TEMPO)
				break;
			
			occ = occurrences.get(e.getValue());
			if (occ > max) {
				max = occ;
				maxKey = e.getKey();
			}
		}

		assert maxKey > 0 : "Tempo extraction: no maximum found!";
		return 60 / maxKey;
	}

	private void count(double distance) {
		final int key = getKey(distance);
		occurrences.put(key, occurrences.get(key) + 1);
	}

	private int getKey(double value) {
		for (Double key : map.keySet()) {
			if (value - TEMPO_KEY_TOLERANCE < key && key < value + TEMPO_KEY_TOLERANCE)
				return map.get(key);
		}
		map.put(value, maxInt);
		occurrences.put(maxInt, 0);
		maxInt++;
		return maxInt - 1;
	}

}
