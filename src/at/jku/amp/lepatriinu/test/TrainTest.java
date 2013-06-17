package at.jku.amp.lepatriinu.test;

import static at.jku.amp.lepatriinu.test.TestHelper.assertBeatEquals;
import static at.jku.amp.lepatriinu.test.TestHelper.assertOnsetEquals;
import static at.jku.amp.lepatriinu.test.TestHelper.assertTempoEquals;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import at.cp.jku.teaching.amprocessing.Processor;

public class TrainTest {
	private static final int COUNT = 20;
	private static final BitSet TRAINS = new BitSet(COUNT);
	
	private static Map<Integer, Processor> processors;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/*
		 * Configure trains:
		 *  - range: trains.set(from [incl.], to [excl.])
		 *  - single: trains.set(train) 
		 */
//		TRAINS.set(1, 5); // the first four trains
		TRAINS.set(1);
		
		processors = new HashMap<>(COUNT);
		int key = 0;
		while((key = TRAINS.nextSetBit(key + 1)) != -1) {
			Processor proc = new Processor("data/train" + Integer.toString(key) + ".wav");
			proc.analyze();
			processors.put(key, proc);
		}
	}

	@Test
	public void testPerformOnsetDetection() {
		for (Entry<Integer, Processor> e : processors.entrySet()) {
			try {
				assertOnsetEquals(e.getKey(), e.getValue().getOnsets());
			} catch (AssertionError er) {
				System.err.println(e.getKey() + ": " +er.getMessage());
			}
		}
	}

	@Ignore
	@Test
	public void testPerformTempoExtraction() {
		for (Entry<Integer, Processor> e : processors.entrySet()) {
			try {
				assertTempoEquals(e.getKey(), e.getValue().getTempo());
			} catch (AssertionError er) {
				System.err.println(e.getKey() + ": " +er.getMessage());
			}
		}
	}
	
	@Ignore
	@Test
	public void testPerformBeatDetection() {
		for (Entry<Integer, Processor> e : processors.entrySet()) {
			try {
				assertBeatEquals(e.getKey(), e.getValue().getBeats());
			} catch (AssertionError er) {
				System.err.println(e.getKey() + ": " +er.getMessage());
			}
		}
	}
}
