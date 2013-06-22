package at.jku.amp.lepatriinu;

import java.util.LinkedList;
import java.util.StringTokenizer;

import at.cp.jku.teaching.amprocessing.AudioFile;
import at.jku.amp.lepatriinu.ui.FileUtils;

/**
 * Just a simple class that reads out the onsets of the ground truth provided.
 * We designed it for testing purposes before our SpectralFluxDetector was good
 * enough.
 * </br></br>
 * <i>Funny fact: It seems, that the evaluation function in
 * class <code>Runner</code> doesn't even consider the ground truth as 100%
 * true.</i>
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class GroundTruthPicker extends OnsetDetector {
	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		String s = FileUtils.readFile(audiofile.filename.substring(0,
				audiofile.filename.lastIndexOf('.')) + ".onsets");
		StringTokenizer st = new StringTokenizer(s, "\n");
		LinkedList<Double> result = new LinkedList<>();
		while (st.hasMoreTokens())
			result.add(Double.parseDouble(st.nextToken()));
		return result;
	}
}