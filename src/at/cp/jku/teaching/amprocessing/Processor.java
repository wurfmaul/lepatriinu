/*
 * Processor.java
 *
 * This is the class where you can implement your onset detection / tempo extraction methods
 * Of course you may also define additional classes.
 */
package at.cp.jku.teaching.amprocessing;

import java.util.LinkedList;

import at.jku.amp.lepatriinu.Analyzer;

/**
 *
 * @author andreas arzt
 */
public class Processor {
    private String m_filename;
    private AudioFile m_audiofile;
    // this List should contain your results of the onset detection step (onset times in seconds)
    private LinkedList<Double> m_onsetList;
    // this variable should contain your result of the tempo estimation algorithm
    private double m_tempo;
    // this List should contain your results of the beat detection step (beat times in seconds)
    private LinkedList<Double> m_beatList;

    public Processor(String filename) {
        System.out.println("Initializing Processor...");
        m_filename = filename;
        m_onsetList = new LinkedList<Double>();
        m_beatList = new LinkedList<Double>();

        System.out.println("Reading Audio-File " + filename);
        System.out.println("Performing FFT...");
        // an AudioFile object is created with the following Parameters: AudioFile(WAVFILENAME, FFTLENGTH in seconds, HOPLENGTH in seconds)
        // if you would like to work with multiple resolutions you simple create multiple AudioFile objects with different parameters
        // given an audio file with 44.100 Hz the parameters below translate to an FFT with size 2048 points
        // Note that the value is not taken to be precise; it is adjusted so that the FFT Size is always power of 2.
        m_audiofile = new AudioFile(m_filename, 0.046439, 0.01);
        // this starts the extraction of the basis features (the STFT)
        m_audiofile.processFile();
    }

    // This method is called from the Runner and is the starting point of onset detection / tempo extraction.
    public void analyze() {
    	System.out.println("Running Analysis...");
    	
    	Analyzer analyzer = new Analyzer(m_audiofile);
    	m_onsetList = analyzer.performOnsetDetection();
    	m_tempo = analyzer.performTempoExtraction();
    	m_beatList = analyzer.performBeatDetection();
    	
    	System.out.println("Analysis finished.");
    }

    public LinkedList<Double> getOnsets() {
        return m_onsetList;
    }

    public double getTempo() {
        return m_tempo;
    }

    public LinkedList<Double> getBeats() {
        return m_beatList;
    }
}