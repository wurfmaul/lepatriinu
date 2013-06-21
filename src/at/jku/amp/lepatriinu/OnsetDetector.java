package at.jku.amp.lepatriinu;

import java.util.LinkedList;
import static at.jku.amp.lepatriinu.Analyzer.*;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Super class of all onset detectors. Contains instances of all available
 * detectors.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public abstract class OnsetDetector {
	public static final OnsetDetector SIMPLE = new SimpleOnsetDetector();
	public static final OnsetDetector FLUX = new SpectralFluxOnsetDetector();
	public static final OnsetDetector HIFQ = new HighFrequencyOnsetDetector();

	/**
	 * Performs the detection operation.
	 * 
	 * @param audiofile
	 *            The audio file that is to be analyzed.
	 * @return A list of onset values.
	 */
	public abstract LinkedList<Double> execute(AudioFile audiofile);

	// GERIS VERSION
	protected LinkedList<Double> peakPick(LinkedList<Double> list,
			double hopTime) {

		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) <= list.get(i - 1)) {
				list.set(i, 0.0);
			}
		}
		for (int i = list.size() - 2; i >= 0; i--) {
			if (list.get(i) <= list.get((i + 1))) {
				list.set(i, 0.0);
			} else if (list.get(i) > list.get(i + 1)) {
				for (int j = 1; j < 9; j++) {
					if (i + j >= list.size())
						break;
					if (list.get(i + j) > list.get(i)) {
						list.set(i, 0.0);
					} else {
						list.set(i + j, 0.0);
					}
				}
			}
		}
		LinkedList<Double> result = new LinkedList<>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > 30)
				result.add(i * hopTime);
		}

		System.err.println(list.size() + " onsets found!");
		return result;
	}

	// OUR ORIGINAL VERSION
	protected LinkedList<Double> peakPick1(LinkedList<Double> list,
			double hopTime) {
		LinkedList<Double> onsets = new LinkedList<>();

		for (int i = 0; i < list.size(); i++) {
			int count = 0;
			double t = 0d;
			for (int j = Math.max(0, i - THRESHOLD_RANGE); j < Math.min(i
					+ THRESHOLD_RANGE, list.size()); j++) {
				t += list.get(j);
				count++;
			}
			t = t / count * 2;
			if (list.get(i) > t) {
				onsets.add(i * hopTime);
				System.err.println(i * hopTime);
			}
		}
		System.err.println(onsets.size() + " onsets found!");
		return onsets;
	}
}