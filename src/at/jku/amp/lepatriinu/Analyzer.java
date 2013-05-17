package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Contains the configuration of all kind of constants, factors and tuning
 * details.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class Analyzer {
	// Detector/Extractor classes
	private static final OnsetDetector ONSET_DETECTOR = OnsetDetector.SIMPLE;
	private static final TempoExtractor TEMPO_EXTRACTOR = TempoExtractor.SIMPLE;
	private static final BeatDetector BEAT_DETECTOR = BeatDetector.SIMPLE;

	// Debug mode
	static final boolean DEBUG = true;
	
	// Onset detection settings
	
	// Tempo extraction settings
	
	// Beat detection settings
	
	private final AudioFile audiofile;
	
	public Analyzer(AudioFile audiofile) {
		this.audiofile = audiofile;
	}

	public LinkedList<Double> performOnsetDetection() {
		return ONSET_DETECTOR.execute(audiofile);
	}

	public double performTempoExtraction() {
		System.err.println("Tempo extraction not yet supported.");
//		return TEMPO_EXTRACTOR.execute(audiofile);
		return 0d;
	}

	public LinkedList<Double> performBeatDetection() {
		System.err.println("Beat detection not yet supported.");
//		return BEAT_DETECTOR.execute(audiofile);
		return new LinkedList<Double>();
	}
}
