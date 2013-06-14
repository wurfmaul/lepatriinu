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
	public static final OnsetDetector HIGHFREQ = new HighFrequencyOnsetDetector();

	/**
	 * Performs the detection operation.
	 * 
	 * @param audiofile
	 *            The audio file that is to be analyzed.
	 * @return A list of onset values.
	 */
	public abstract LinkedList<Double> execute(AudioFile audiofile);

	protected LinkedList<Double> peakPick(LinkedList<Double> list) {
		LinkedList<Double> onsets = new LinkedList<>();

		for (int i = 0; i < list.size(); i++) {
			int count = 0;
			double t = 0d;
			for (int j = Math.max(0, i - THRESHOLD_RANGE); j < Math.min(i
					+ THRESHOLD_RANGE, list.size()); j++) {
				t += list.get(j);
				count++;
			}
			t = t / count * 2 ;
			if (list.get(i) > t) {
				onsets.add(t);
			}
		}
		return onsets;
	}
}