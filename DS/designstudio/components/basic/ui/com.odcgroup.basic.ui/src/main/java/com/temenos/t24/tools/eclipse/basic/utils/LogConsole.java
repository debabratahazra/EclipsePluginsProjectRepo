package com.temenos.t24.tools.eclipse.basic.utils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
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

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.perspectives.T24BasicPerspectiveFactory;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;

public class LogConsole {

    public static final String LOG_VIEW_ID = "T24BasicConsole";
    public static final String T24_BASIC_CONSOLE = "T24BasicConsole";
    private static LogConsole SINGLETON_INSTANCE = null;
    private static MessageConsole t24BasicConsole = null;

    // Constructor is private to force this a singleton
    private LogConsole() {
    }

    public static LogConsole getT24BasicConsole() {
        if (SINGLETON_INSTANCE == null) {
            SINGLETON_INSTANCE = new LogConsole();
            // Brings up the T24Basic console only if the active perspective is
            // T24Basic perspective
            IWorkbenchPage activePage = getActivePage();
            if (activePage != null
                    && T24BasicPerspectiveFactory.T24_BASIC_PERSPECTIVE_ID.equals(activePage.getPerspective().getId())
                    && !isLogConsoleOnTop()) {
                bringLogConsoleToTop();
            }
        }
        return SINGLETON_INSTANCE;
    }

    /**
     * Logs a message through the default console
     * 
     * @param message
     */
    public void logMessage(String message) {
        // If we are in some other perspective and still has got something to
        // display on T24Basic console, then bring it up.
        if (!isLogConsoleOnTop()) {
            bringLogConsoleToTop();
        }
        logMessage(t24BasicConsole, message);
    }

    /**
     * Method to stream out the messages to console.
     * 
     * @param myConsole - instance of MessageConsole
     * @param strMessage - message to be displayed
     */
    private void logMessage(MessageConsole myConsole, String strMessage) {
        String curTime = StringUtil.getTimeStamp();
        if (myConsole != null) {
            MessageConsoleStream out = myConsole.newMessageStream();
            out.println(curTime + ": " + strMessage);
        }
    }

    /**
     * Method to locate console view with input name.
     * 
     * @param consoleName - console's name e.g. "T24BasicConsole"
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
            Image t24Image = EclipseUtil.getImage("/icons/myt24.gif");
            ImageDescriptor descriptor = ImageDescriptor.createFromImage(t24Image);
            myConsole = new MessageConsole(consoleName, descriptor);
            conMan.addConsoles(new IConsole[] { myConsole });
        }
        return myConsole;
    }

    /**
     * Method to open and display a console View.
     * 
     * @param consoleToDisplay - console to be didplayed
     */
    private static void displayConsole(MessageConsole consoleToDisplay) {
        if (consoleToDisplay != null) {
            IWorkbenchPage page = getActivePage();
            String viewId = IConsoleConstants.ID_CONSOLE_VIEW;
            IConsoleView view = null;
            try {
                if (page != null) {
                    view = (IConsoleView) page.findView(viewId);
                    if (view != null) {
                        view.display(consoleToDisplay);
                        if (RemoteSessionManager.getInstance().isUserSignedOn()) {
                            updateConsoleToolBar("/icons/signedOnImage.gif", "User Signed On", "signon.id");
                        } else {
                            updateConsoleToolBar("/icons/signedOffImage.gif", "User Signed Off", "signon.id");
                        }
                        IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
                        String compileWithDebugProperty = store.getString(PluginConstants.T24_COMPILE_WITH_DEBUG);
                        boolean allowDebug = Boolean.parseBoolean(compileWithDebugProperty);
                        if (allowDebug) {
                            updateConsoleToolBar("/icons/debugOptionWarningImage.gif", "Compiling with DEBUG allowed", "debug.id");
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates an action, whose id is passed, within the ToolBar by changing the
     * image and text. An action in this scenario is just represented by a
     * little icon on the Console toolbar.
     * 
     * @param imageFile - e.g. "/icons/signon.gif"
     * @param imageText - String associated to the image (e.g. as a tip tool
     *            when user hoover mouse)
     * @param actionId - id associated with the action we want to update (e.g.
     *            "signon.id", "debug.id", ...)
     */
    public static void updateConsoleToolBar(String imageFile, String imageText, String actionId) {
        IToolBarManager tbm = null;
        IWorkbenchPage activePage = getActivePage();
        IConsoleView view = null;
        if (activePage != null) {
            String id = IConsoleConstants.ID_CONSOLE_VIEW;
            try {
                view = (IConsoleView) activePage.showView(id);
                tbm = view.getViewSite().getActionBars().getToolBarManager();
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
        Image image = EclipseUtil.getImage(imageFile);
        final Action openAction = new Action(imageText, ImageDescriptor.createFromImage(image)) {

            public void run() {
            }
        };
        openAction.setId(actionId);
        // remove any previous item with the same id (if any), to avoid
        // duplicated items.
        tbm.remove(actionId);
        tbm.add(openAction);
        view.getViewSite().getActionBars().updateActionBars();
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
                if(view!=null){
                tbm = view.getViewSite().getActionBars().getToolBarManager();
                tbm.remove(actionId);
                view.getViewSite().getActionBars().updateActionBars();
                }
        }
    }

    /**
     * Checks if T24Basic console is on top.
     * 
     * @return true if T24Basic console is on top. false otherwise.
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
        if (view != null && view.getConsole() != null && view.getConsole().equals(t24BasicConsole)) {
            return true;
        }
        return false;
    }

    /**
     * Brings the T24Basic console to top
     */
    private static void bringLogConsoleToTop() {
        if (t24BasicConsole == null) {
            t24BasicConsole = findConsole(T24_BASIC_CONSOLE);
        }
        displayConsole(t24BasicConsole);
    }

    /**
     * Returns the active workbench page. If unable to find, returns null.
     * 
     * @return active page or null
     */
    private static IWorkbenchPage getActivePage() {
        IWorkbenchPage activePage = null;
        if (PlatformUI.isWorkbenchRunning()) {
            IWorkbenchWindow awbw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (awbw != null) {
                activePage = awbw.getActivePage();
            }
        }
        return activePage;
    }

    /**
     * Returns the output steam for the log console to receive the terminal
     * output through jremote.
     * 
     * @return
     */
    // TODO - To be removed when debug functionality is brought in by providing
    // a dedicated console for debug.
    // This out stream is connected to the terminal output stream of
    // JConnection.
    public MessageConsoleStream getOutputStream() {
        MessageConsole console = findConsole(T24_BASIC_CONSOLE);
        return console.newMessageStream();
    }
    
}
