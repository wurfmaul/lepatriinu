package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

public class AutoCorrelationBeatDetector extends BeatDetector {

	@Override
	public LinkedList<Double> execute(AudioFile audiofile,
			LinkedList<Double> onsets, double tempo) {

		switch (Analyzer.AUTO_FUNCTION) {
		case AUTO_ONSET_CORRELATION:
			return executeOnOnsets(audiofile, onsets);
		case AUTO_SPECTRAL_CORRELATION:
			 return executeOnSpectralData(audiofile, onsets);
		default:
			return executeOnTempo(audiofile, onsets, tempo);
		}
	}

	private LinkedList<Double> executeOnTempo(AudioFile audiofile,
			LinkedList<Double> onsets, double tempo) {
		double realTempo = 60 / tempo;

		LinkedList<Double> result = null;
		LinkedList<Double> temp = new LinkedList<>();

		int bestCross = 0;
		double lastOnset = onsets.getLast();

		for (double slowD : onsets) {
			int crossCorr = 0;
			while (slowD < lastOnset) {

				for (double fastD : onsets) {

					if (Math.abs(slowD - fastD) < Analyzer.AUTO_PHASE_TOLERANCE) {
						crossCorr++;
						temp.add(fastD);
					}
				}

				slowD += realTempo;
			}

			if (crossCorr > bestCross) {
				result = new LinkedList<>(temp);
				bestCross = crossCorr;
			}
			temp.clear();
		}

		return result;
	}

	private LinkedList<Double> executeOnOnsets(AudioFile audiofile,
			LinkedList<Double> onsets) {

		double[] temp = new double[onsets.size()];
		LinkedList<Double> result = new LinkedList<>();

		for (int i = 0; i < onsets.size(); i++) {
			int k = (int) (onsets.get(i) / audiofile.hopTime);
			double r = 0;

			for (int j = 0; j < onsets.size(); j++) {
				int l = (int) (onsets.get(j) / audiofile.hopTime);
				double pk = audiofile.spectralDataContainer.get(k).totalEnergy;
				double pkT = audiofile.spectralDataContainer.get(l).totalEnergy;
				r += pkT * pk;
			}
			temp[i] = r;
		}

		double lastMax = 0;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] > lastMax)
				lastMax = temp[i];
			else {
				result.add(onsets.get(i));
				lastMax = 0;
			}
		}

		return result;
	}

	private LinkedList<Double> executeOnSpectralData(AudioFile audiofile,
			LinkedList<Double> onsets) {

		LinkedList<Double> result = new LinkedList<>();
		final int to = (int) (1 / audiofile.hopTime);
		final int from = (int) (to * 0.3);
		double[] temp = new double[to];

		for (int i = from; i < to; i++) {

			double sum = 0;

			double k = audiofile.spectralDataContainer.get(i).totalEnergy;

			for (int j = from; j < to; j++) {
				sum += audiofile.spectralDataContainer.get(j).totalEnergy * k;
			}

			temp[i] = sum;
		}

		double lastMax = 0;
		for (int i = from; i < to; i++) {
			if (temp[i] > lastMax)
				lastMax = temp[i];
			else {
				result.add(i * audiofile.hopTime);
				lastMax = 0;
			}
		}

		result.size();

		return result;
	}

}