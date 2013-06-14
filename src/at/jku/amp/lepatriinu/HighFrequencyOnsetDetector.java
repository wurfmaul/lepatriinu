package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

public class HighFrequencyOnsetDetector extends OnsetDetector {

	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		final int length = audiofile.spectralDataContainer.size();
		final LinkedList<Double> result = new LinkedList<>();
		
		// FIXME: goes notly...
		
		double sum;
		
		for(int i=0; i<length; i++){
			sum = 0;
			for (int j = 1; j<length; j++){
				sum += j * Math.pow(Math.abs(audiofile.spectralDataContainer.get(j).totalEnergy), 2);
			}
			result.add(sum);
		}
		return result;
	}

}
