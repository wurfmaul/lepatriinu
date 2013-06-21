package at.jku.amp.lepatriinu;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import at.cp.jku.teaching.amprocessing.AudioFile;
import static at.jku.amp.lepatriinu.Analyzer.*;

public class InterOnsetBeatDetector extends BeatDetector {

	private Map<Integer, Integer> occurrences = new LinkedHashMap<>();
	private Map<Double, Integer> map = new LinkedHashMap<>();
	private int maxInt = 0;

	@Override
	public LinkedList<Double> execute(AudioFile audiofile,
			LinkedList<Double> onsets) {
		final int maxInterval = (int) (1 / audiofile.hopTime);
		final int minInterval = (int) (maxInterval * 0.3);

		for (int i = 0; i < onsets.size() - maxInterval; i++) {
			double currentOnset = onsets.get(i);

			for (int j = i + minInterval; j < i + maxInterval; j++) {
				double nextOnset = onsets.get(j);
				double distance = nextOnset - currentOnset;
				count(distance);
			}
		}

		System.err.println(map.size());
		System.err.println(Arrays.toString(occurrences.values().toArray()));

		LinkedList<Double> result = new LinkedList<>();

		for (Entry<Double, Integer> e : map.entrySet()) {
			Integer key = e.getValue();
			Integer integer = occurrences.get(key);
			int occ = integer;

			if (occ > OCC_THRESHOLD) {
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
		final int k = maxInt++;
		return k;
	}

}