package com.odcgroup.integrationfwk.ui.utils;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class LogConsole {

	public static final String LOG_VIEW_ID = "T24IntegrationConsole";
	public static final String SOA_INTEGRATION_CONSOLE = "T24IntegrationConsole";
	private static LogConsole SINGLETON_INSTANCE = null;
	private static MessageConsole SoaIntegrationConsole = null;

	/**
	 * Brings the SoaIntegrationConsole console to top
	 */
	private static void bringLogConsoleToTop() {
		if (SoaIntegrationConsole == null) {
			SoaIntegrationConsole = findConsole(SOA_INTEGRATION_CONSOLE);
		}
		// displayConsole(t24BasicConsole);
	}

	/**
	 * Method to locate console view with input name.
	 * 
	 * @param consoleName
	 *            - console's name e.g. "SoaIntegrationConsole"
	 * @return MessageConsole instance (or null) Three types of returns: - The
	 *         console wanted was found => return MessageConsole - The console
	 *         wasn't found. A new console created, and added to the list of
	 *         registered consoles. - null if console manager couldn't be
	 *         obtained.
	 */
	private static MessageConsole findConsole(String consoleName) {
		MessageConsole myConsole = null;
		/**
		 * The plug-in has default console, from which a console manager can be
		 * obtained.
		 */
		ConsolePlugin defaultConsolePlugin = ConsolePlugin.getDefault();
		if (defaultConsolePlugin != null) {
			IConsoleManager conMan = defaultConsolePlugin.getConsoleManager();
			IConsole[] existing = conMan.getConsoles();
			for (int i = 0; i < existing.length; i++) {
				String name = existing[i].getName();
				if (consoleName.equals(name)) {
					/** The wanted console has been found. */
					return (MessageConsole) existing[i];
				}
			}
			/** no console found, so create a new one */
			myConsole = new MessageConsole(consoleName, null);
			conMan.addConsoles(new IConsole[] { myConsole });
		}
		return myConsole;
	}

	/**
	 * Returns the active workbench page. If unable to find, returns null.
	 * 
	 * @return active page or null
	 */
	private static IWorkbenchPage getActivePage() {
		IWorkbenchPage activePage = null;
		if (PlatformUI.isWorkbenchRunning()) {
			IWorkbenchWindow awbw = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			if (awbw != null) {
				activePage = awbw.getActivePage();
			}
		}
		return activePage;
	}

	public static LogConsole getSoaConsole() {
		if (SINGLETON_INSTANCE == null) {
			SINGLETON_INSTANCE = new LogConsole();
			IWorkbenchPage activePage = getActivePage();
			if (activePage != null
					&& "com.temenos.tws.perspectives.SOAStudioPerspective"
							.equals(activePage.getPerspective().getId())
					&& !isLogConsoleOnTop()) {
				bringLogConsoleToTop();
			}
		}
		return SINGLETON_INSTANCE;
	}

	/**
	 * Checks if SoaIntegrationConsole is on top.
	 * 
	 * @return true if SoaIntegrationConsole console is on top. false otherwise.
	 */
	private static boolean isLogConsoleOnTop() {
		IWorkbenchPage activePage = getActivePage();
		IConsoleView view = null;
		if (activePage != null) {
			String id = IConsoleConstants.ID_CONSOLE_VIEW;
			try {
				view = (IConsoleView) activePage.showView(id);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		if (view != null && view.getConsole() != null
				&& view.getConsole().equals(SoaIntegrationConsole)) {
			return true;
		}
		return false;
	}

	/**
	 * Removes the action id passed
	 * 
	 * @param actionId
	 */
	public static void removeActionFromConsoleToolBar(String actionId) {
		IToolBarManager tbm = null;
		IWorkbenchPage activePage = getActivePage();
		IConsoleView view = null;
		if (activePage != null) {
			String id = IConsoleConstants.ID_CONSOLE_VIEW;
			view = (IConsoleView) activePage.findView(id);
			if (view != null) {
				tbm = view.getViewSite().getActionBars().getToolBarManager();
				tbm.remove(actionId);
				view.getViewSite().getActionBars().updateActionBars();
			}
		}
	}

	// Constructor is private to force this a singleton
	private LogConsole() {
	}

	/**
	 * Method to stream out the messages to console.
	 * 
	 * @param myConsole
	 *            - instance of MessageConsole
	 * @param strMessage
	 *            - message to be displayed
	 */
	private void logMessage(MessageConsole myConsole, String strMessage) {
		if (myConsole != null) {
			MessageConsoleStream out = myConsole.newMessageStream();
			out.println(strMessage);
		}
	}

	/**
	 * Logs a message through the default console
	 * 
	 * @param message
	 */
	public void logMessage(String message) {
		if (!isLogConsoleOnTop()) {
			bringLogConsoleToTop();
		}
		logMessage(SoaIntegrationConsole, message);
	}

}
