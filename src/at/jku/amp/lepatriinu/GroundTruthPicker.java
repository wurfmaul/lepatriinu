package at.jku.amp.lepatriinu;

import java.util.LinkedList;
import java.util.StringTokenizer;

import at.cp.jku.teaching.amprocessing.AudioFile;
import at.jku.amp.lepatriinu.ui.FileUtils;

public class GroundTruthPicker extends OnsetDetector {

	@Override
	public LinkedList<Double> execute(AudioFile audiofile) {
		String s = FileUtils.readFile(audiofile.filename.substring(0, audiofile.filename.lastIndexOf('.')) + ".onsets");
		StringTokenizer st = new StringTokenizer(s, "\n");
		
		LinkedList<Double> result = new LinkedList<>();
		
		while (st.hasMoreTokens()) {
			result.add(Double.parseDouble(st.nextToken()));
		}
		
		
		return result;
	}

}