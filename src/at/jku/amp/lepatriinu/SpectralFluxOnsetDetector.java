package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

public class SpectralFluxOnsetDetector extends OnsetDetector {

	// OUR ORIGINAL VERSION
	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		LinkedList<Double> result;
		
		// 
		result = executeOriginal(audiofile);
		
		// POSTPROCESSING
		return peakPick(result, audiofile.hopTime);
	}
	
	private LinkedList<Double> executeOriginal(AudioFile audiofile) {
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
				// x = |Xk(n)|-|Xk(n-1)|
				x = Math.abs(energy[j]) - Math.abs(lastEnergy[j]);
				// H(x) = (x + |x|)/2
				sum += (x + Math.abs(x)) / 2;
			}
			// SD(n) = sum(...)^2
			result.add(sum*sum);
			
			lastEnergy = energy;
		}
		return result;
	}

	// GERIS VERSION
	private LinkedList<Double> executeTotalEnergy(AudioFile audiofile) {
		// DETECTION FUNCTION
		final int length = audiofile.spectralDataContainer.size();
		final LinkedList<Double> list = new LinkedList<>();
		
		double x;
		
		for (int i = 1; i < length; i++) {
			x = audiofile.spectralDataContainer.get(i).totalEnergy
					 - audiofile.spectralDataContainer.get(i- 1).totalEnergy;
			list.add(Math.pow((x + Math.abs(x) )/ 2, 2));
		}
		
		// POSTPROCESSING
		return peakPick(list, audiofile.hopTime);
	}

}
