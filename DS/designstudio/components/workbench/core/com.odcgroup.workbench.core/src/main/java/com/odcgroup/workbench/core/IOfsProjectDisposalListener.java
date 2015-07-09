package com.odcgroup.workbench.core;

/**
 * A listener that is notified if an IOfsProject has been disposed.
 *
 * @author Kai Kreuzer
 *
 */
public interface IOfsProjectDisposalListener {

	/**
	 * Called whenever the IOfsProject is disposed
	 * 
	 * @param ofsProject the project that has been disposed
	 */
	public void ofsProjectDisposed(IOfsProject ofsProject);
}
