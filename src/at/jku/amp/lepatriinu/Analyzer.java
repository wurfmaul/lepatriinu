package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;
import at.jku.amp.lepatriinu.OnsetDetector.Mode;

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
	// DETECTOR INSTANCES
	private static final OnsetDetector ONSET_DETECTOR = OnsetDetector.GRTR;
	private static final BeatDetector BEAT_DETECTOR = BeatDetector.IOSE;
	private static final TempoExtractor TEMPO_EXTRACTOR = TempoExtractor.SIMPLE;
	
	// GENERAL CONSTANTS
	public static final boolean DEBUG_MODE = true;
	
	// ONSET DETECTION CONSTANTS
	public static final int THRESHOLD = 13;
	public static final int THRESHOLD_RANGE = 5;
	public static final boolean FLUX_USE_TOTAL_ENERGY = true;
	public static final boolean HIFQ_USE_WPHACK = true;
	public static final Mode PEAKPICK_MODE = Mode.MOUNTAIN_CLIMBING;
	
	// BEAT DETECTOR CONSTANTS
	public static final boolean AUTO_USE_ONSETS = false;
	public static final int OCC_THRESHOLD = 30;
	public static final double TOLERANCE = 0.025;
	
	private final AudioFile audiofile;
	
	public Analyzer(AudioFile audiofile) {
		this.audiofile = audiofile;
	}

	public LinkedList<Double> performOnsetDetection() {
		return ONSET_DETECTOR.execute(audiofile);
	}
	
	public LinkedList<Double> performBeatDetection(LinkedList<Double> onsets) {
		return BEAT_DETECTOR.execute(audiofile, onsets);
	}

	public double performTempoExtraction(LinkedList<Double> beats) {
//		return TEMPO_EXTRACTOR.execute(audiofile, beats);
		return 0;
	}
}