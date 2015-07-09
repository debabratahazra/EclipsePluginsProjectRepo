package com.odcgroup.ds.t24.packager.log;

public enum SysoutPackagerLogger implements PackagerLogger {
	
	INSTANCE;

	@Override
	public void info(String message) { 
		System.out.println("[INFO] " + message);
	}

	@Override
	public void debug(String message) {
		debug(message, null);
	}

	@Override
	public void debug(String message, Throwable t) {
		System.out.println("[DEBUG]" + message);
		printStackTrace(t);
	}

	@Override
	public void warn(String message) {
		warn(message, null);
	}

	@Override
	public void warn(String message, Throwable t) {
		System.out.println("[WARN] " + message);
		printStackTrace(t);
	}

	@Override
	public void error(String message) {
		error(message, null);
	}

	@Override
	public void error(String message, Throwable t) {
		System.out.println("[ERROR] " + message);
		printStackTrace(t);
	}

	private void printStackTrace(Throwable t) {
		if (t!=null) {
			t.printStackTrace();
		}
	}

}
