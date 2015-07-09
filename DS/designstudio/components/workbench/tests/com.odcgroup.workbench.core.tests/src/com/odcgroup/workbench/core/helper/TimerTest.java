package com.odcgroup.workbench.core.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit Test for Timer util.
 *
 * @author Michael Vorburger
 */
public class TimerTest {

	@Test
	public void testTimer() throws InterruptedException {
		Timer t = new Timer();
		t.start();
		Thread.sleep(200);
		t.stop();
		assertEquals("0.200s", t.text());
	}

	@Test
	public void testTimerWithoutWait() throws InterruptedException {
		String timeText = new Timer().start().stopAndText();
		assertEquals("0.000s", timeText);
	}

	
	@Test(expected=IllegalStateException.class)
	public void testTimerStartStart() {
		Timer t = new Timer();
		t.start();
		t.start();
	}

	@Test(expected=IllegalStateException.class)
	public void testTimerStopNoStart() {
		Timer t = new Timer();
		t.stop();
	}

	@Test(expected=IllegalStateException.class)
	public void testTimerTextNoStart() {
		Timer t = new Timer();
		t.text();
	}

}
