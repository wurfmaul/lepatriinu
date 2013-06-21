package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;
import at.cp.jku.teaching.amprocessing.SpectralData;

public class HighFrequencyOnsetDetector extends OnsetDetector {

	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		LinkedList<Double> result;
		
		// DETECTION FUNCTION
		if (Analyzer.HIFQ_USE_WPHACK)
			result = executeWikipediaHack(audiofile);
		else
			result = executeOriginal(audiofile);

		// POSTPROCESSING
		return peakPick(result, audiofile.hopTime);
	}
	
	private LinkedList<Double> executeOriginal(AudioFile audiofile) {
		final int length = audiofile.spectralDataContainer.size();
		final LinkedList<Double> result = new LinkedList<>();
		double sum;

		for (SpectralData sd: audiofile.spectralDataContainer) {
			sum = 0;
			for (int j = 1; j < sd.size; j++) {
				sum += Math.pow(j * sd.magnitudes[j], 2);
			}
			result.add(sum / length);
		}
		return result;
	}
	
	private LinkedList<Double> executeWikipediaHack(AudioFile audiofile) {
		final LinkedList<Double> result = new LinkedList<>();
		double sum;

		for (SpectralData sd: audiofile.spectralDataContainer) {
			sum = 0;
			for (int i = 0; i < sd.size; i++) {
				sum += Math.abs(i * sd.magnitudes[i]);
			}
			result.add(sum);
		}
		return result;
	}
}