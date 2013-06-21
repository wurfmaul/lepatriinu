package at.jku.amp.lepatriinu;

import static at.jku.amp.lepatriinu.Analyzer.*;

import java.util.Arrays;
import java.util.LinkedList;

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

	protected LinkedList<Double> peakPick(LinkedList<Double> list,
			double hopTime) {

		if (Analyzer.PEAKPICK_USE_ADPTV_THRESHOLD)
			return adptvThreshold(list, hopTime);
		return peakPickModified(list, hopTime);
	}

	private LinkedList<Double> adptvThreshold(LinkedList<Double> list,
			double hopTime) {

		final LinkedList<Double> onsets = new LinkedList<>();

		for (int i = 0; i < list.size(); i++) {
			int from = Math.max(0, i - THRESHOLD_RANGE);
			int to = Math.min(i	+ THRESHOLD_RANGE, list.size()-1);
			double[] values = new double[to-from];
			double sum = 0;
			
			for (int j = from; j <= to; j++) {
				values[j%(to-from)] = list.get(j);
				sum += list.get(j);
			}
			
			// MEAN
			double mean = sum / (to-from);

			// MEDIAN
			Arrays.sort(values);
			double median = mean;
			if(values.length%2 == 0)
				median += (values[values.length/2-1] + values[values.length/2]) / 2;
			else
				median += values[values.length/2];
			
			if (list.get(i) > median) {
				onsets.add(i * hopTime);
			}
			
		}
		return onsets;
	}

	private LinkedList<Double> peakPickModified(LinkedList<Double> list,
			double hopTime) {
		
		final LinkedList<Double> result = new LinkedList<>();
		final LinkedList<Integer> indices = new LinkedList<>();
		
		// kill first and last
		list.set(0,  0d);
		list.set(list.size()-1, 0d);

		// have a look at the others
		for(int i=1; i<list.size()-1; i++) {
			if (list.get(i) < list.get(i - 1) || list.get(i) < list.get(i+1))
				list.set(i, 0d);
			else
				indices.add(i);
		}
		
		// use threshold
		for (int i : indices) {
			int from = Math.max(0, i - THRESHOLD_RANGE);
			int to = Math.min(i	+ THRESHOLD_RANGE, list.size()-1);
			
			for (int j = from; j <= to; j++) {
				if(list.get(j) > list.get(i))
					list.set(i, 0d);
			}
			
			if(list.get(i) >= THRESHOLD)
				result.add(i*hopTime);
		}
		
		return result;
	}
}