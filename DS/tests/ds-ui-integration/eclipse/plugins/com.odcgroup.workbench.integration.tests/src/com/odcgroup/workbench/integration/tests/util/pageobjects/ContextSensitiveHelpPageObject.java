package com.odcgroup.workbench.integration.tests.util.pageobjects;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.hamcrest.Matcher;

import com.odcgroup.workbench.integration.tests.help.SWTBotToolbarHelpButton;

public class ContextSensitiveHelpPageObject {
	
	private static final int HELP_OPENING_TIMEOUT = 10;
	private static final int HELP_CLOSING_TIMEOUT = 10;

	protected SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	public void displayContextSensitiveHelp() {
		@SuppressWarnings("unchecked")
		Matcher<ToolItem> matcher = WidgetMatcherFactory
				.<ToolItem> allOf(new Matcher[] {
						WidgetMatcherFactory.widgetOfType(ToolItem.class),
						WidgetMatcherFactory.withTooltip("Help"),
						WidgetMatcherFactory.withStyle(SWT.NONE, "SWT.NONE") });
		final ToolItem widget = bot.<ToolItem> widget(matcher, 0);
		SWTBotToolbarHelpButton help = new SWTBotToolbarHelpButton(
				widget, matcher);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				Listener[] listeners = widget.getListeners(SWT.Selection);
				for (Listener l: listeners) {
					System.out.println(l);
				}
			}
		});
		help.setFocus();
		help.click();
	}

	/**
	 * @return return the help display or <code>null</code> if not found
	 */
	public Display findHelpDisplay() {
		return findHelpDisplay(HELP_OPENING_TIMEOUT);
	}
	
	/**
	 * @param timeOutInSec timeout in second 
	 * @return the Display used to display the help in a separate window
	 */
	private Display findHelpDisplay(final int timeOutInSec) {
		if (timeOutInSec <= 0) {
			throw new IllegalArgumentException("The time out must be >= 1s");
		}
		
		for (int i = 0; i < timeOutInSec; i++) {
			Display disp = findHelpDisplay_INTERNAL();
			if (disp != null) {
				return disp;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		return null;
	}

	/**
	 * @param disp
	 * @return the help Display instance or <code>null</code> if not found
	 */
	private Display findHelpDisplay_INTERNAL() {
		class FindHelpDisplayRunnable implements Runnable {
			Display result;
			@Override
			public void run() {
				if (Display.getCurrent() != null &&
						Display.getCurrent().getActiveShell() != null &&
						"Help - Design Studio for Triple'A Plus".equals(Display.getCurrent().getActiveShell().getText())) {
					result = Display.getCurrent();
				}
			}
		};
		
		Display disp = null;
		for (Thread t : Thread.getAllStackTraces().keySet()) {
			disp = Display.findDisplay(t);
			if (disp != null && disp != Display.getDefault() && !disp.isDisposed()) {
				FindHelpDisplayRunnable findHelpDisplayRunnable = new FindHelpDisplayRunnable();
				disp.syncExec(findHelpDisplayRunnable);
				if (findHelpDisplayRunnable.result != null) {
					disp = findHelpDisplayRunnable.result;
					break;
				}
			}
		}
		return disp;
	}

	/**
	 * Close the help window
	 */
	public void closeHelpWindow() {
		Display display = findHelpDisplay_INTERNAL();
		if (display != null && !display.isDisposed()) {
			closeHelpWindow(display);
		}
	}
	
	/**
	 * Close the help window
	 * @param helpDisplay
	 */
	public void closeHelpWindow(Display helpDisplay) {
		helpDisplay.syncExec(new Runnable() {
			@Override
			public void run() {
				if (Display.getCurrent() != null && !Display.getCurrent().isDisposed()) {
					Display.getCurrent().dispose();
				}
			}
		});
	}
	
	/**
	 * Wait for the help window to be closed
	 */
	public void waitForHelpToClose() {
		for (int i=0; i<HELP_CLOSING_TIMEOUT; i++)  {
			if (findHelpDisplay_INTERNAL() != null) {
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
		throw new IllegalStateException("The help window is not closed after " + HELP_CLOSING_TIMEOUT + " seconds.");
	}
	
	/**
	 * Wait for the help window to be closed
	 * @param helpDisplay
	 */
	public void waitForHelpToClose(Display helpDisplay) {
		for (int i=0; i<HELP_CLOSING_TIMEOUT; i++)  {
			if (helpDisplay.isDisposed()) {
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
		throw new IllegalStateException("The help window is not closed after " + HELP_CLOSING_TIMEOUT + " seconds.");
	}

	/**
	 * @param helpDisplay
	 * @return <code>true</code> if the contextual help is displayed (the help topic is found)
	 */
	public boolean isContextualHelpFound(final Display helpDisplay) {
		class FindBrowserRunnable implements Runnable {
			String contentsUrl;
			@Override
			public void run() {
				Browser browser = getBrowser(helpDisplay.getActiveShell().getChildren());
				if (browser != null) {
					// Convert the url referencing the frame version of the help, to the url of the contents frame
					contentsUrl = browser.getUrl().replace("%2F", "/").replace("index.jsp?topic=", "topic");
				}
			}
			
			private Browser getBrowser(Control[] controls) {
				for (Control control: controls) {
					if (control instanceof Browser) {
						return (Browser)control;
					}
				}
				for (Control control: controls) {
					if (control instanceof Composite) {
						Browser browser = getBrowser(((Composite)control).getChildren());
						if (browser != null) {
							return browser;
						}
					}
				}
				return null;
			}
		}
		
		if (helpDisplay == null || helpDisplay.isDisposed()) {
			return false;
		}
		FindBrowserRunnable findBrowserRunnable = new FindBrowserRunnable();
		helpDisplay.syncExec(findBrowserRunnable);
		String helpContents = readHelpContents(findBrowserRunnable.contentsUrl);
	    return !helpContents.contains("Topic not found");
	}

	/**
	 * @param findBrowserRunnable
	 * @return the help contents served at the helpContentsUrl
	 */
	private String readHelpContents(String helpContentsUrl) {
		InputStream input = null;
		try {
			URL url = new URL(helpContentsUrl);
			URLConnection connection = url.openConnection();
			input = connection.getInputStream();        
		    return IOUtils.toString(input);
		} catch (MalformedURLException e) {
			return "";
		} catch (IOException e) {
			return "";
		} finally {
			IOUtils.closeQuietly(input);
		}
	}


}
