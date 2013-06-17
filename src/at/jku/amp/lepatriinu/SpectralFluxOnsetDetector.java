package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

public class SpectralFluxOnsetDetector extends OnsetDetector {

	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		// DETECTION FUNCTION
		final int length = audiofile.spectralDataContainer.size();
		final LinkedList<Double> result = new LinkedList<>();
		
		double[] energy;
		double[] lastEnergy = audiofile.spectralDataContainer.get(0).magnitudes;
		double sum, x;
		
		for (int i = 1; i < length; i++) {
			energy = audiofile.spectralDataContainer.get(i).magnitudes;
			sum = 0;
			for(int j= 0; j<energy.length; j++) {
				x = Math.abs(energy[j]) - Math.abs(lastEnergy[j]);
				// H(x) = (x + |x|)/2
				x = (x + Math.abs(x)) / 2;
				sum += x * x;
			}
			result.add(sum);
			lastEnergy = energy;
		}
		
		// POSTPROCESSING
		return peakPick(result, audiofile.hopTime);
	}

}
