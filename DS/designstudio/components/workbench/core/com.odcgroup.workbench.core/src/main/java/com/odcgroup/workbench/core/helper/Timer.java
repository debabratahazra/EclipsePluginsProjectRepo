package com.odcgroup.workbench.core.helper;

/**
 * Timer utility.
 *
 * @author Michael Vorburger
 */
public class Timer {

	private static enum State { CREATED, STARTED, STOPPED }
	private State state = State.CREATED;
	
	private long ns = 0;
	private long d = 0;
	
	public Timer start() {
		if (state != state.CREATED)
			throw new IllegalStateException("Not a fresh Timer; it already started (stopped), you need a new Timer()");
		ns = System.nanoTime();
		state = State.STARTED;
		return this;
	}

	public void stop() {
		if (state != State.STARTED)
			throw new IllegalStateException("Timer never started");
		d = System.nanoTime() - ns;
		ns = 0;
		state = State.STOPPED;
	}

	public String text() {
		if (state == State.CREATED)
			throw new IllegalStateException("Timer never even started");
		if (state == State.STARTED)
			throw new IllegalStateException("Timer started, but never stopped");
		double dInS = d / 10e8;
		return String.format("%1$,.3fs", dInS);
	}

	public String stopAndText() {
		stop();
		return text();
	}
	
}
