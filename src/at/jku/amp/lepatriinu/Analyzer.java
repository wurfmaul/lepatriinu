package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Heart of detecting functionality. Contains the configuration of all kind of
 * constants, factors and tuning details. Also performs extractions and
 * detections.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class Analyzer {
	// Detector/Extractor classes
	private static final OnsetDetector ONSET_DETECTOR = OnsetDetector.SIMPLE;
	@SuppressWarnings("unused")
	private static final TempoExtractor TEMPO_EXTRACTOR = TempoExtractor.SIMPLE;
	@SuppressWarnings("unused")
	private static final BeatDetector BEAT_DETECTOR = BeatDetector.SIMPLE;

	// Debug mode
	public static final boolean DEBUG_MODE = true;
	
	// Onset detection settings
	public static final double ONSET_DETECTION_TOLERANCE_TIME = 0.05;
	
	// Tempo extraction settings
	public static final double TEMPO_EXTRACTION_TOLERANCE_MIN = 0.96;
	public static final double TEMPO_EXTRACTION_TOLERANCE_MAX = 1.04;
	
	// Beat detection settings
	public static final double BEAT_DETECTION_TOLERANCE_TIME = 0.07;
	
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
