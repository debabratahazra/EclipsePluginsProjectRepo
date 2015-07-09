package com.odcgroup.ds.t24.packager.log;

public enum NullPackagerLogger implements PackagerLogger {
	
	INSTANCE;
	
	@Override public void info(String message) {}
	@Override public void debug(String message) {}
	@Override public void debug(String message, Throwable t) {}
	@Override public void warn(String message) {}
	@Override public void warn(String message, Throwable t) {}
	@Override public void error(String message) {}
	@Override public void error(String message, Throwable t) {}

}
