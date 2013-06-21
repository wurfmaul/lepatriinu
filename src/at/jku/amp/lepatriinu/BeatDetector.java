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
	public static final BeatDetector AUTO = new AutoCorrelationBeatDetector();
	public static final BeatDetector IOSE = new InterOnsetBeatDetector();

	/**
	 * Performs the detection operation.
	 * 
	 * @param audiofile
	 *            The audio file that is to be analyzed.
	 * @param onsets
	 *            The list of found onsets.
	 * @return A list of beat measurements.
	 */
	public abstract LinkedList<Double> execute(AudioFile audiofile,
			LinkedList<Double> onsets);
}
