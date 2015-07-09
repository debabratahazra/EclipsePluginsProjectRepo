package com.odcgroup.t24.server.external.ui.builder;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.server.ui.internal.CommonConsole;
import com.odcgroup.t24.server.external.T24ServerExternalCore;

/**
 * Access to the deploy console
 * @author yan
 */
public class T24DeployConsole extends CommonConsole {

	private boolean displayDebug = false;
	
	@Override
	protected String getScopeQualifier() {
		return T24ServerExternalCore.PLUGIN_ID;
	}
	
	public T24DeployConsole() {
		
		Logger rootLogger = Logger.getLogger("com.jbase.jremote"); 
		Handler myHandler = new Handler() {
			@Override
			public void publish(LogRecord record) {
				String prefix = "(" + record.getLoggerName() + ") ";
				if (record.getLevel() == Level.SEVERE) {
					printError(prefix + record.getMessage());
				} else if (record.getLevel() == Level.WARNING) {
					printWarning(prefix + record.getMessage());
				} else if (isDisplayDebug()) {
					if (record.getLevel() == Level.INFO) {
						printInfo(prefix + record.getMessage());
					} else {
						printDebug(prefix + record.getMessage());
					}
				}
			}

			@Override
			public void flush() {
			}

			@Override
			public void close() throws SecurityException {
			}
			
		}; 
		myHandler.setFormatter(new SimpleFormatter()); 
		myHandler.setLevel(Level.FINEST); 
		rootLogger.setLevel(Level.FINEST); 
		rootLogger.addHandler(myHandler); 
		
	}
	 
	protected String consoleName() {
		return "T24 Deployment Console";
	}
	
	public boolean isDisplayDebug() {
		return displayDebug;
	}

	public void setDisplayDebug(boolean displayDebug) {
		this.displayDebug = displayDebug;
	}

	@Override
	public void printDebug(String message) {
		if (displayDebug) {
			getDebugConsoleStream().println(getTime() + " [DEBUG]: " + message);
		}
	}
	
	@Override
	public void printError(String message, Throwable t) {
		if (displayDebug) {
			super.printError(message, t);
		} else {
			boolean hasMessage = (t!=null) && (t.getMessage() != null);
			if (hasMessage) {
				printError(message + " (" + t.getMessage() + ")");
			} else {
				printError(message);
			}
		}
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, getScopeQualifier());
	}
}
