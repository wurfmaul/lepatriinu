package at.jku.amp.lepatriinu;

import java.util.LinkedList;

import at.cp.jku.teaching.amprocessing.AudioFile;

/**
 * This is a very simple kind of Onset Detector.
 * 
 * @author Andreas Arzt
 * 
 */
public class SimpleOnsetDetector extends OnsetDetector {

	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		final LinkedList<Double> onsetList = new LinkedList<>();

		for (int i = 0; i < audiofile.spectralDataContainer.size(); i++) {
			if (i == 0) {
				continue;
			}
			if (audiofile.spectralDataContainer.get(i).totalEnergy
					- audiofile.spectralDataContainer.get(i - 1).totalEnergy > 10) {
				onsetList.add(i * audiofile.hopTime);
			}
		}

		return onsetList;
	}
}
