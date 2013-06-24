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
	private Map<Integer, Integer> occurrences;
	private Map<Double, Integer> map;
	private int maxInt;

	@Override
	public double execute(AudioFile audiofile, LinkedList<Double> onsets) {
		if (onsets.size() <= 1)
			return 0;
		
		occurrences = new LinkedHashMap<>();
		map = new LinkedHashMap<>();
		maxInt = 0;

		double currentOnset, nextOnset, distance;

		double extensionFactor = 1d;
		do {
			// calculate distances
			for (int i = 0; i < onsets.size(); i++) {
				currentOnset = onsets.get(i);
	
				for (int j = i + 1; j < onsets.size(); j++) {
					nextOnset = onsets.get(j);
					distance = nextOnset - currentOnset;
					if (distance < MIN_TEMPO / extensionFactor || distance > MAX_TEMPO * extensionFactor)
						continue;
					count(distance);
				}
			}
			
			extensionFactor++;
		} while(map.isEmpty());

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

		// translate to bpm
		return (maxKey > 0) ? 60 / maxKey : 29.7327;
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
