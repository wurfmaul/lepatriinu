package at.jku.amp.lepatriinu;

import static at.jku.amp.lepatriinu.Analyzer.PEAKPICK_USE_MEAN;
import static at.jku.amp.lepatriinu.Analyzer.THRESHOLD;
import static at.jku.amp.lepatriinu.Analyzer.THRESHOLD_RANGE;

import java.util.Arrays;
import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Super class of all onset detectors. Contains instances of all available
 * detectors and the peak picking functions.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public abstract class OnsetDetector {
	/** Use a very simple approach of detecting onsets. */
	public static final OnsetDetector SIMPLE = new SimpleOnsetDetector();
	/** Use spectral flux for onset detection. */
	public static final OnsetDetector FLUX = new SpectralFluxOnsetDetector();
	/** Use high frequency for onset detection. */
	public static final OnsetDetector HIFQ = new HighFrequencyOnsetDetector();
	/** CHEAT: use groundtruth for evaluating beats/tempo on a proper basis. */
	public static final OnsetDetector GRTR = new GroundTruthPicker();

	/**
	 * Performs the detection operation.
	 * 
	 * @param audiofile
	 *            The audio file that is to be analyzed.
	 * @return A list of onset values.
	 */
	public abstract LinkedList<Double> execute(AudioFile audiofile);

	/**
	 * Performs peak picking on a given list.
	 * 
	 * @param list
	 *            The list of possible onsets.
	 * @param hopTime
	 *            The hoptime (from the audiofile).
	 * @return The final list of onsets.
	 */
	protected LinkedList<Double> peakPick(LinkedList<Double> list,
			double hopTime) {
		if (Analyzer.USE_MOUNTAIN_PEAKPICK)
			return mountainClimbing(list, hopTime);
		else
			return adaptiveThreshold(list, hopTime);
	}

	/**
	 * Applies an adaptive threshold (slide 5.40).
	 */
	private LinkedList<Double> adaptiveThreshold(LinkedList<Double> list,
			double hopTime) {

		final LinkedList<Double> onsets = new LinkedList<>();

		for (int i = 0; i < list.size(); i++) {
			int from = Math.max(0, i - THRESHOLD_RANGE);
			int to = Math.min(i + THRESHOLD_RANGE, list.size() - 1);
			double[] values = new double[to - from];
			double sum = 0;

			for (int j = from; j <= to; j++) {
				values[j % (to - from)] = list.get(j);
				sum += list.get(j);
			}

			if (PEAKPICK_USE_MEAN) {
				double mean = sum / (to - from);
				if (list.get(i) > mean) {
					onsets.add(i * hopTime);
				}
			} else {
				Arrays.sort(values);
				double median = sum;
				if (values.length % 2 == 0)
					median = (values[values.length / 2 - 1] + values[values.length / 2]) / 2;
				else
					median = values[values.length / 2];

				if (list.get(i) > median) {
					onsets.add(i * hopTime);
				}
			}

		}
		return onsets;
	}

	// FIXME: SOURCE!?
	/**
	 * Only  
	 */
	private LinkedList<Double> mountainClimbing(LinkedList<Double> list,
			double hopTime) {

		final LinkedList<Double> result = new LinkedList<>();
		final LinkedList<Integer> indices = new LinkedList<>();

		// kill first and last
		list.set(0, 0d);
		list.set(list.size() - 1, 0d);

		// have a look at the others
		for (int i = 1; i < list.size() - 1; i++) {
			if (list.get(i) < list.get(i - 1) || list.get(i) < list.get(i + 1))
				list.set(i, 0d);
			else
				indices.add(i);
		}

		// use threshold
		for (int i : indices) {
			int from = Math.max(0, i - THRESHOLD_RANGE);
			int to = Math.min(i + THRESHOLD_RANGE, list.size() - 1);

			for (int j = from; j <= to; j++) {
				if (list.get(j) > list.get(i))
					list.set(i, 0d);
			}

			if (list.get(i) >= THRESHOLD)
				result.add(i * hopTime);
		}

		return result;
	}
}