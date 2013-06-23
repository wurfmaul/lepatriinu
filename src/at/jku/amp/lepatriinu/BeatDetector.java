package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Super class of all beat detectors. Contains instances of all available
 * detectors.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public abstract class BeatDetector {
	/** Use auto-correlation for beat detection */
	public static final BeatDetector AUTO = new AutoCorrelationBeatDetector();

	/**
	 * Performs the detection operation.
	 * 
	 * @param audiofile
	 *            The audio file that is to be analyzed.
	 * @param onsets
	 *            The list of found onsets.
	 * @param tempo
	 *            The result from the TempoExtractor.
	 * @return A list of beat measurements.
	 */
	public abstract LinkedList<Double> execute(AudioFile audiofile,
			LinkedList<Double> onsets, double tempo);
}
