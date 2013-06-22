package at.jku.amp.lepatriinu;

import static at.jku.amp.lepatriinu.Analyzer.OCC_THRESHOLD;
import static at.jku.amp.lepatriinu.Analyzer.TOLERANCE;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import at.cp.jku.teaching.amprocessing.AudioFile;

public class InterOnsetBeatDetector extends BeatDetector {
	private Map<Integer, Integer> occurrences = new LinkedHashMap<>();
	private Map<Double, Integer> map = new LinkedHashMap<>();
	private int maxInt = 0;

	@Override
	public LinkedList<Double> execute(AudioFile audiofile,
			LinkedList<Double> onsets, double tempo) {

		LinkedList<Double> result = new LinkedList<>();
		
		// calculate distances
		for (int i = 0; i < onsets.size(); i++) {
			double currentOnset = onsets.get(i);

			for (int j = i; j < onsets.size(); j++) {
				double nextOnset = onsets.get(j);
				double distance = nextOnset - currentOnset;
				count(distance);
			}
		}
		
		// important for normalization
		int max = 0;
		for (Integer i : occurrences.values()) {
			max = Math.max(max, i);
		}
		
		// normalization and thresholding
		for (Entry<Double, Integer> e : map.entrySet()) {
			final int occ = occurrences.get(e.getValue());
			
			if ((double) occ/(double) max > OCC_THRESHOLD) {
				result.add(e.getKey());
			}
		}
		Collections.sort(result);
		return result;
	}

	private void count(double distance) {
		final int key = getKey(distance);
		occurrences.put(key, occurrences.get(key) + 1);
	}

	private int getKey(double value) {
		for (Double key : map.keySet()) {
			if (value - TOLERANCE < key && key < value + TOLERANCE)
				return map.get(key);
		}
		map.put(value, maxInt);
		occurrences.put(maxInt, 0);
		maxInt++;
		return maxInt - 1;
	}

}