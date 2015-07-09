package com.odcgroup.server.ui.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.odcgroup.server.preferences.RGBColor;
import com.odcgroup.server.preferences.ServerPreferences;
import com.odcgroup.server.ui.preferences.ServerUIPreferences;

/**
 * Hold the shared SWT resource (like colors)
 * @author yan, atr
 * @since 1.40
 */
public abstract class CommonConsole {

	public static class DSMessageConsole extends MessageConsole {
		public DSMessageConsole(String name, ImageDescriptor imageDescriptor) {
			super(name, imageDescriptor);
		}
		public void setName(String newName) {
			super.setName(newName);
		}
	}

	// MessageConsole and stream used to display information to the user
    protected DSMessageConsole messageConsole;
    private MessageConsoleStream infoStream;
    private MessageConsoleStream warningStream;
    private MessageConsoleStream errorStream;
    private MessageConsoleStream debugStream;

	/**
	 * is console visible
	 */
	private boolean visible = true;
	
	/**
	 * console can be visible but not on top
	 */
	private int consoleOnTop = ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_DEFAULT;
	
	protected final boolean isConsoleOnTop(int style) {
		return (consoleOnTop & style) != 0;
	}
	
	protected void setConsoleOnTop(int value) {
		consoleOnTop = value;
	}
	
    protected void bringConsoleToFront(int style) {
    	if (isConsoleOnTop(style)) {
	        IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
	        if (!visible) {
	            manager.addConsoles(new IConsole[] {messageConsole});
	        }
	        manager.showConsoleView(messageConsole);
    	}
    }
    
    protected abstract String getScopeQualifier();
    
	protected CommonConsole() {
		getDisplay().asyncExec(new Runnable() {
			public void run() {
				CommonConsole.this.loadPreferences();
			}
		});
		
	    IEclipsePreferences scope = InstanceScope.INSTANCE.getNode(getScopeQualifier());
        scope.addPreferenceChangeListener(new IPreferenceChangeListener() {
            public void preferenceChange(PreferenceChangeEvent event) {
        		if (event.getKey().startsWith(ServerPreferences.CONSOLE_BASE)) {
        			getDisplay().asyncExec(new Runnable() {
        				public void run() {
        					CommonConsole.this.loadPreferences();
        				}
        			});
        		}
            }
        });
	}

	protected abstract String consoleName();
	
	protected abstract IPreferenceStore getPreferenceStore();

	/**
	 * Super class can optionally configure the console
	 * (i.e. add attribute to the console, set the watermarks)
	 */
	protected void configureConsole(MessageConsole console) {
	}

	// Create the MessageConsole instance for this console
	private void createMessageConsole() {
		messageConsole = new DSMessageConsole(consoleName(), null);
		configureConsole(messageConsole);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ messageConsole });
	}

	// Lazily create the underlying MessageConsole
	private MessageConsole getMessageConsole() {
		if (messageConsole == null) {
			createMessageConsole();
		}
		return messageConsole;
	}
	
	private MessageConsoleStream createStream(final RGBColor color) {
		final MessageConsoleStream stream = getMessageConsole().newMessageStream();
		runInUIThread(new Runnable() {
			public void run() {
				if (stream != null) {
					updateStreamColor(stream, color);
				}
			}
		});
		return stream;
	}

	// Return the stream use to log debug information
	protected MessageConsoleStream getDebugConsoleStream() {
		if (debugStream == null) {
			debugStream = createStream(ServerUIPreferences.getDebugColor(getPreferenceStore())); 
		}
		return debugStream;
	}

	// Return the stream use to log info level of information
	protected MessageConsoleStream getInfoConsoleStream() {
		if (infoStream == null) {
			infoStream = createStream(ServerUIPreferences.getInfoColor(getPreferenceStore())); 
		}
		return infoStream;
	}

	// Return the stream use to log error information
	protected MessageConsoleStream getErrorConsoleStream() {
		if (errorStream == null) {
			errorStream = createStream(ServerUIPreferences.getErrorColor(getPreferenceStore())); 
		}
		return errorStream;
	}

	// Return the stream use to log error information
	protected MessageConsoleStream getWarningConsoleStream() {
		if (warningStream == null) {
			warningStream = createStream(ServerUIPreferences.getWarningColor(getPreferenceStore()));
		}
		return warningStream;
	}

	/**
	 * Clear the related console
	 */
	public void clearConsole() {
		if (messageConsole != null) {
			messageConsole.clearConsole();
		}
	}
	
	private void dispose(MessageConsoleStream consoleStream) {
		if (consoleStream != null) {
			consoleStream.setColor(null);
			Color tmp = consoleStream.getColor();
			IOUtils.closeQuietly(consoleStream);
			if (tmp != null) {
				tmp.dispose();
			}
		}
	}

	/**
	 * Remove the color for the stream as they only need to be disposed once
	 */
	public void dispose() {
		runInUIThread(new Runnable() {
			public void run() {
				
				dispose(debugStream);
				dispose(errorStream);
				dispose(infoStream);
				dispose(warningStream);

				debugStream = null;
				errorStream = null;
				infoStream = null;
				warningStream = null;
				
				// Remove the console to the console list
				ConsolePlugin.getDefault().getConsoleManager().removeConsoles(new IConsole[] { messageConsole });
				
				visible = false;
			}
		});
	}

	public static Display getDisplay() {
		Display retVal = Display.getCurrent();
		if (retVal == null || retVal.isDisposed()) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			if (workbench != null) {
				retVal = workbench.getDisplay();
			}
			if (retVal == null || retVal.isDisposed()) {
				retVal = Display.getDefault();
			}
		}
		return retVal;
	}
	
	/**
	 * Helper method to run in a UI thread
	 * @param r
	 */
	public static void runInUIThread(Runnable r) {
		if (Display.getCurrent() != null) {
			r.run();
		} else {
			Display.getDefault().asyncExec(r);
		}
	}

	public void refreshConsoleName() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				messageConsole.setName(consoleName());
			}
		});
	}

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss");
		return sdf.format(new Date());
	}

	public void printInfo(String message) {
   		bringConsoleToFront(ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_INFO);
		getInfoConsoleStream().println(getTime() + " [INFO]: " + message);
	}

	public void printError(String message) {
   		bringConsoleToFront(ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_ERROR);
		getErrorConsoleStream().println(getTime() + " [ERROR]: " + message);
	}
	
	public void printError(String message, Throwable t) {
   		bringConsoleToFront(ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_ERROR);
		getErrorConsoleStream().println(getTime() + " [ERROR]: " + message);
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		getErrorConsoleStream().println(sw.toString());
	}


	public void printWarning(String message) {
   		bringConsoleToFront(ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_WARNING);
		getWarningConsoleStream().println(getTime() + " [WARN]: " + message);
	}

	public void printDebug(String message) {
   		bringConsoleToFront(ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_DEBUG);
		getDebugConsoleStream().println(getTime() + " [DEBUG]: " + message);
	}
	
	protected void updateStreamColor(MessageConsoleStream stream, RGBColor rgb) {
		Color tmp = stream.getColor();
		stream.setColor(new Color(getDisplay(), rgb.red(), rgb.green(), rgb.blue()));
		if (tmp != null && !tmp.equals(stream.getColor())) {
			tmp.dispose();
		}
	}
	
	protected void loadPreferences() {
		
		IPreferenceStore store = getPreferenceStore();

		// update streams color
		updateStreamColor(getDebugConsoleStream(), ServerUIPreferences.getDebugColor(store));
		updateStreamColor(getErrorConsoleStream(), ServerUIPreferences.getErrorColor(store));
		updateStreamColor(getInfoConsoleStream(), ServerUIPreferences.getInfoColor(store));
		updateStreamColor(getWarningConsoleStream(), ServerUIPreferences.getWarningColor(store));
		
		// update auto show
		setConsoleOnTop(ServerUIPreferences.getAutoShow(store));

		if (messageConsole != null) {
			if (ServerUIPreferences.isWrapEnabled(store)) {
				messageConsole.setConsoleWidth(ServerUIPreferences.getWrapWidth(store));
			}
			else {
				messageConsole.setConsoleWidth(-1); 
			}
			
			if (ServerUIPreferences.isLimitEnabled(store)) {
				int limit = ServerUIPreferences.getLimitValue(store);
				messageConsole.setWaterMarks(1000 < limit ? 1000 : limit - 1, limit);
			}
			else {
				messageConsole.setWaterMarks(-1, 0);
			}
		}

		
	}
	
}
