package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * Uses the idea of Spectral Difference (slide 4.20) to extract onsets.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class SpectralFluxOnsetDetector extends OnsetDetector {

	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		LinkedList<Double> result;

		// DETECTION FUNCTION
		if (Analyzer.FLUX_USE_TOTAL_ENERGY)
			result = executeTotalEnergy(audiofile);
		else
			result = executeOriginal(audiofile);

		// POSTPROCESSING
		return peakPick(result, audiofile.hopTime);
	}

	/**
	 * Uses the exact formula from slide 4.20.
	 */
	private LinkedList<Double> executeOriginal(AudioFile audiofile) {
		final int length = audiofile.spectralDataContainer.size();
		final LinkedList<Double> result = new LinkedList<>();

		double[] energy;
		double[] lastEnergy = audiofile.spectralDataContainer.get(0).magnitudes;
		double sum, x;

		for (int i = 1; i < length; i++) {
			energy = audiofile.spectralDataContainer.get(i).magnitudes;
			sum = 0;
			for (int j = 0; j < energy.length; j++) {
				// x = |Xk(n)|-|Xk(n-1)|
				x = Math.abs(energy[j]) - Math.abs(lastEnergy[j]);
				// H(x) = (x + |x|)/2
				sum += (x + Math.abs(x)) / 2;
			}
			// SD(n) = sum(...)^2
			result.add(sum * sum);

			lastEnergy = energy;
		}
		return result;
	}

	/**
	 * Uses the total energy of each bin (inspired by playing around and <a
	 * href="http://en.wikipedia.org/wiki/Spectral_flux">Wikipedia</a>).
	 */
	private LinkedList<Double> executeTotalEnergy(AudioFile audiofile) {
		final int length = audiofile.spectralDataContainer.size();
		final LinkedList<Double> result = new LinkedList<>();

		double energy;
		double lastEnergy = audiofile.spectralDataContainer.get(0).totalEnergy;
		double x;

		for (int i = 1; i < length; i++) {
			energy = audiofile.spectralDataContainer.get(i).totalEnergy;
			// x = |Xk(n)|-|Xk(n-1)|
			x = Math.abs(energy) - Math.abs(lastEnergy);
			// H(x) = (x + |x|)/2
			x = (x + Math.abs(x)) / 2;
			// SD(n) = sum(...)^2
			result.add(x * x);

			lastEnergy = energy;
		}
		return result;
	}
}