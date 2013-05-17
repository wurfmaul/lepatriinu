package at.jku.amp.lepatriinu.test;

import static at.jku.amp.lepatriinu.Analyzer.BEAT_DETECTION_TOLERANCE_TIME;
import static at.jku.amp.lepatriinu.Analyzer.ONSET_DETECTION_TOLERANCE_TIME;
import static at.jku.amp.lepatriinu.Analyzer.TEMPO_EXTRACTION_TOLERANCE_MAX;
import static at.jku.amp.lepatriinu.Analyzer.TEMPO_EXTRACTION_TOLERANCE_MIN;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Contains helping methods for verifying the correctness of calculated values.
 * Super class of all test files.
 * 
 * @author Wolfgang Küllinger (0955711)
 * @author Fabian Jordan (0855941)
 * 
 */
public class TestHelper {
	
	static void assertBeatEquals(final int train, final Collection<Double> actual) {
		final String fileExpected = "data/train" + Integer.toString(train) + ".beats";
		try {
			final Collection<Double> expected = parseBeatList(fileExpected);
			assertEquals(expected.size(), actual.size());
			
			Iterator<Double> iterExpected = expected.iterator();
			Iterator<Double> iterActual = actual.iterator();
			RangeChecker range;
			while (iterExpected.hasNext()) {
				range = new BeatRangeChecker(iterExpected.next());
				range.assertContains(iterActual.next());
			}
		} catch (FileNotFoundException e) {
			throw new AssertionError("Could not find file " + fileExpected);
		} catch (IOException e) {
			throw new AssertionError(e.getMessage());
		}
	}

	static void assertOnsetEquals(final int train, final Collection<Double> actual) {
		final String fileExpected = "data/train" + Integer.toString(train) + ".onsets";
		try {
			final Collection<Double> expected = parseOnsetList(fileExpected);
			assertEquals(expected.size(), actual.size());
			
			Iterator<Double> iterExpected = expected.iterator();
			Iterator<Double> iterActual = actual.iterator();
			RangeChecker range;
			while (iterExpected.hasNext()) {
				range = new OnsetRangeChecker(iterExpected.next());
				range.assertContains(iterActual.next());
			}
		} catch (FileNotFoundException e) {
			throw new AssertionError("Could not find file " + fileExpected);
		} catch (IOException e) {
			throw new AssertionError(e.getMessage());
		}
	}

	static void assertTempoEquals(final int train, final double actual) {
		final String fileExpected = "data/train" + Integer.toString(train) + ".bpms";
		try {
			final RangeChecker range = new TempoRangeChecker(parseTempo(fileExpected));
			range.assertContains(actual);
		} catch (FileNotFoundException e) {
			throw new AssertionError("Could not find file " + fileExpected);
		} catch (IOException e) {
			throw new AssertionError(e.getMessage());
		}
	}

	/**
	 * NAME.beats is a list of all beat locations in the excerpt. The second
	 * column contains beat labels (metrical level) and can be ignored.
	 * 
	 * @param fileExpected
	 * @return
	 * @throws IOException
	 */
	private static Collection<Double> parseBeatList(String fileExpected) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(fileExpected));
		final List<Double> out = new LinkedList<>();
		int lineNumber = 0;
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				String value = new StringTokenizer(line, " ").nextToken();
				out.add(Double.parseDouble(value));
				lineNumber++;
			}
		} catch (NumberFormatException e) {
			throw new IOException("Could not parse number in line " + lineNumber);
		} catch (IOException e) {
			throw new IOException("Could not read line " + lineNumber);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
		return Collections.unmodifiableList(out);
	}
	
	/**
	 * NAME.onsets is a list of all onset times in the excerpt.
	 * 
	 * @param fileExpected
	 * @return
	 * @throws IOException
	 */
	private static Collection<Double> parseOnsetList(String fileExpected) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(fileExpected));
		final List<Double> out = new LinkedList<>();
		int lineNumber = 0;
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				out.add(Double.parseDouble(line.trim()));
				lineNumber++;
			}
		} catch (NumberFormatException e) {
			throw new IOException("Could not parse number in line " + lineNumber);
		} catch (IOException e) {
			throw new IOException("Could not read line " + lineNumber);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
		return Collections.unmodifiableList(out);
	}
	
	/**
	 * NAME.bpms contains the tempo of the excerpt. In fact this is manually
	 * compiled data. Humans are rarely in agreement of the tempo of a piece of
	 * music these files include 2 tempi and a weighting (e.g. 120 60 0.8 means
	 * that 80% of the people say that the tempo is 120 bpm and 20% of the
	 * people think that the tempo is 60 bpm. For our evaluation we only use the
	 * tempo which received the majority of votes.
	 * 
	 * @param fileExpected
	 * @return
	 * @throws IOException
	 */
	private static double parseTempo(String fileExpected) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(fileExpected));
		try {
			final String line = reader.readLine();
			final StringTokenizer st = new StringTokenizer(line, " ");
			final int tempo1 = Integer.parseInt(st.nextToken());
			final int tempo2 = Integer.parseInt(st.nextToken());
			final double percentage = Double.parseDouble(st.nextToken());
			return percentage >= 0.5 ? tempo1 : tempo2;
		} catch (NumberFormatException e) { 
			throw new IOException("File does not contain valid tempo information.");
		} catch (IOException e) {
			throw new IOException("Could not properly parse file: " + fileExpected);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
	}

	private static class BeatRangeChecker extends RangeChecker {
		public BeatRangeChecker(double expected) {
			super(expected - BEAT_DETECTION_TOLERANCE_TIME, expected + BEAT_DETECTION_TOLERANCE_TIME);
		}
	}
	
	private static class OnsetRangeChecker extends RangeChecker {
		public OnsetRangeChecker(double expected) {
			super(expected - ONSET_DETECTION_TOLERANCE_TIME, expected + ONSET_DETECTION_TOLERANCE_TIME);
		}
	}

	private static class TempoRangeChecker extends RangeChecker {
		public TempoRangeChecker(double expected) {
			super(expected * TEMPO_EXTRACTION_TOLERANCE_MIN, expected * TEMPO_EXTRACTION_TOLERANCE_MAX);
		}
	}

	private static abstract class RangeChecker {
		public final double lower;
		public final double upper;
		
		public RangeChecker(double lower, double upper) {
			this.lower = lower;
			this.upper = upper;
		}

		public void assertContains(double value) {
			if (value < lower && upper < value) {
				throw new AssertionError("Value " + value + " is out of range: [" + lower + ", " + upper + "]");
			}
		}
	}
}
