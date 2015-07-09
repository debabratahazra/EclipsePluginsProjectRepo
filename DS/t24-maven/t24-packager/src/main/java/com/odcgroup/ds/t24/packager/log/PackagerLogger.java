package com.odcgroup.ds.t24.packager.log;

public interface PackagerLogger {

	public void info(String message);
	
	public void debug(String message);
	public void debug(String message, Throwable t);

	public void warn(String message);
	public void warn(String message, Throwable t);

	public void error(String message);
	public void error(String message, Throwable t);

}
