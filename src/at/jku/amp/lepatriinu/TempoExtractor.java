package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Super class of all tempo extractors. Contains instances of all available
 * extractors.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public abstract class TempoExtractor {
	public static final TempoExtractor SIMPLE = null;

	/**
	 * Performs the tempo extraction operation.
	 * 
	 * @param audiofile
	 *            The audio file that is to be analyzed.
	 * @param beats
	 *            The list of found beats.
	 * @return A value for the measured tempo.
	 */
	public abstract double execute(AudioFile audiofile, LinkedList<Double> beats);
}