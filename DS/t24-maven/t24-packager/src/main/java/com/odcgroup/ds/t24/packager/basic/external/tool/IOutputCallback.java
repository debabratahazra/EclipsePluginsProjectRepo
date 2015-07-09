package com.odcgroup.ds.t24.packager.basic.external.tool;

/**
 * Callback called when the external tool produce an output
 * @author yan
 */
public interface IOutputCallback {
	
	/**
	 * Called for each line produced by the external tool 
	 * @param line
	 * @param isError <code>true</code> if the line comes from the error stream, 
	 * <code>false</code> if it comes from the standard stream.
	 */
	public void outputLine(String line, boolean isError);
}
