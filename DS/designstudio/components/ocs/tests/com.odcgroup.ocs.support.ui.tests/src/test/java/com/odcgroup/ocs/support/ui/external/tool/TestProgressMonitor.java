package com.odcgroup.ocs.support.ui.external.tool;

import org.eclipse.core.runtime.IProgressMonitor;

public class TestProgressMonitor implements IProgressMonitor {

	private int totalWork;
	private int worked;
	
	@Override
	public void beginTask(String name, int totalWork) {
		this.totalWork = totalWork;
	}

	@Override
	public void worked(int work) {
		this.worked += work;
	}

	@Override
	public void done() {
		// Ignored
	}

	/**
	 * @return the totalWork
	 */
	public int getTotalWork() {
		return totalWork;
	}

	/**
	 * @return the worked
	 */
	public int getWorked() {
		return worked;
	}

	@Override
	public void internalWorked(double work) {
		// Ignored
	}

	@Override
	public boolean isCanceled() {
		// Ignored
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
		// Ignored
	}

	@Override
	public void setTaskName(String name) {
		// Ignored
	}

	@Override
	public void subTask(String name) {
		// Ignored
	}

}
