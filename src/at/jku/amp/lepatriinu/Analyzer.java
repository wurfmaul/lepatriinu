package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;
import at.jku.amp.lepatriinu.AutoCorrelationBeatDetector.Mode;

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
	private static final OnsetDetector ONSET_DETECTOR = OnsetDetector.FLUX;
	private static final TempoExtractor TEMPO_EXTRACTOR = TempoExtractor.IOTE;
	private static final BeatDetector BEAT_DETECTOR = BeatDetector.AUTO;

	// GENERAL CONSTANTS
	public static final boolean DEBUG_MODE = true;

	// ONSET DETECTION CONSTANTS
	public static final int THRESHOLD = 13;
	public static final int THRESHOLD_RANGE = 5;
	public static final boolean FLUX_USE_TOTAL_ENERGY = true;
	public static final boolean HIFQ_USE_WPHACK = true;
	public static final boolean USE_MOUNTAIN_PEAKPICK = true;
	public static final boolean PEAKPICK_USE_MEAN = false;

	// BEAT DETECTOR CONSTANTS
	public static final boolean AUTO_USE_ONSETS = false;
	public static final Mode AUTO_FUNCTION = Mode.AUTO_TEMPO_CORRELATION;
	public static final double AUTO_PHASE_TOLERANCE = 0.21;

	// TEMPO EXTRACTOR CONSTANTS
	public static final double MIN_TEMPO = 0.3; // = 200 bpm
	public static final double MAX_TEMPO = 1; // = 60 bpm
	public static final double TEMPO_KEY_TOLERANCE = 0.0195;
	public static final double MAX_EXTENSION = 10;

	private final AudioFile audiofile;

	public Analyzer(AudioFile audiofile) {
		this.audiofile = audiofile;
	}

	public LinkedList<Double> performOnsetDetection() {
		return ONSET_DETECTOR.execute(audiofile);
	}

	public double performTempoExtraction(LinkedList<Double> onsets) {
		return TEMPO_EXTRACTOR.execute(audiofile, onsets);
	}

	public LinkedList<Double> performBeatDetection(LinkedList<Double> onsets,
			double tempo) {
		return BEAT_DETECTOR.execute(audiofile, onsets, tempo);
	}
}