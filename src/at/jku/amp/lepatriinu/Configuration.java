package at.jku.amp.lepatriinu;

/**
 * Contains the configuration of all kind of constants, factors and tuning
 * details.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class Configuration {
	/* #### EXTERNAL SETTINGS (public) #### */
	// Detector/Extractor classes
	public static final OnsetDetector ONSET_DETECTOR = OnsetDetector.SIMPLE;
	public static final TempoExtractor TEMPO_EXTRACTOR = TempoExtractor.SIMPLE;
	public static final BeatDetector BEAT_DETECTOR = BeatDetector.SIMPLE;

	/* #### INTERNAL SETTINGS (package protected) #### */
	// Debug mode
	static final boolean DEBUG = true;
	
	// Onset detection settings
	
	// Tempo extraction settings
	
	// Beat detection settings
	
}
