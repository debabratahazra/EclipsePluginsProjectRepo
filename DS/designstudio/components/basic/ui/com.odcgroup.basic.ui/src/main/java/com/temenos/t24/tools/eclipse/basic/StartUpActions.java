package com.temenos.t24.tools.eclipse.basic;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.actions.local.SetLocalWorkspaceActionDelegate;
import com.temenos.t24.tools.eclipse.basic.actions.remote.ViewLocksInServerActionDelegate;
import com.temenos.t24.tools.eclipse.basic.dialogs.ViewLocksInServerDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.perspectives.T24BasicPerspectiveFactory;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.views.remote.RemoteSiteViewManager;

public class StartUpActions implements org.eclipse.ui.IStartup {

    private MementoUtil mu = MementoUtilFactory.getMementoUtil();

    public void earlyStartup() {
        /** Listener to set current local directory by clicking on any folder */
        setupSelectDefaultDirectory();
        /** Part (editors, views, ...) listener for events (close, open, ...) */
        setupWorkbenchPartListeners();
        /**
         * Set a listener to allow for actions pre/post shutting down eclipse
         * workbench
         */
        setupShutdownListener();
        /** Set a listener that traps changes to the active perspective. */
        setupPerspectiveListener();
        /** Some properties need being reset. */
        resetProperties();
        /**
         * Create Temporary local project, for copying temporarily files
         * retrieved from server. The project is removed on shutting down the
         * workbench, and created from scratch each time on start up.
         */
        // Disabled auto creation of temporary project at startup
        // it will be created on demand when called for getTemporaryProject()
        // EclipseUtil.createTemporaryProject();
    }

    /**
     * Adds a selection listener to the active page, which allows to set the
     * default local directory whenever a folder is clicked. This is needed to
     * allow the user to activate easily different folder to where server files
     * can be uploaded.
     */
    private void setupSelectDefaultDirectory() {
        ISelectionListener selectionLstnr = new ISelectionListener() {

            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
                SetLocalWorkspaceActionDelegate slw = new SetLocalWorkspaceActionDelegate();
                boolean logMessageInConsole = false;
                slw.setLocalDirectory(selection, logMessageInConsole);
            }
        };
        for (IWorkbenchWindow wbw : PlatformUI.getWorkbench().getWorkbenchWindows()) {
            if (wbw != null) {
                IWorkbenchPage page = wbw.getActivePage();
                page.addSelectionListener(selectionLstnr);
            }
        }
    }

    /**
     * Listeners for handling part events (e.g. editor events such as open,
     * close etc) Notes: Sequence when opening a new editor for first time:
     * partOpened, partVisible, partBroughtToTop, partActivated. Sequence when
     * switching between editors: partVisible, partHidden, partBroughtToTop,
     * partDeactivated, partActivated Sequence when closing an editor:
     * partHidden, partDeactivated partClosed
     */
    private void setupWorkbenchPartListeners() {
        IPartListener2 partLstnr = new IPartListener2() {

            public void partActivated(IWorkbenchPartReference arg0) {
            }

            public void partBroughtToTop(IWorkbenchPartReference arg0) {
            }

            public void partClosed(IWorkbenchPartReference arg0) {
                IWorkbenchPart part = arg0.getPart(true);
                if (part instanceof T24BasicMultiPageEditor) {
                    // /** Delete temporary copy of editor if needed. */
                    // deleteTemporaryCopy((T24BasicMultiPageEditor) part);
                    /**
                     * Refresh views after editor closure will remove items
                     * (labels, calls, ...) on views
                     */
                    // refreshViewItems();
                }
            }

            public void partDeactivated(IWorkbenchPartReference arg0) {
            }

            public void partHidden(IWorkbenchPartReference arg0) {
            }

            public void partInputChanged(IWorkbenchPartReference arg0) {
            }

            public void partOpened(IWorkbenchPartReference arg0) {
            }

            public void partVisible(IWorkbenchPartReference arg0) {
                IWorkbenchPart part = arg0.getPart(true);
                if (part instanceof T24BasicMultiPageEditor) {
                    /**
                     * An editor has been made visible, update a reference to
                     * it.
                     */
                    EditorDocumentUtil.activeT24BasicMultiPageEditor = (T24BasicMultiPageEditor) part;
                }
            }
        };
        for (IWorkbenchWindow wbw : PlatformUI.getWorkbench().getWorkbenchWindows()) {
            if (wbw != null) {
                IWorkbenchPage page = wbw.getActivePage();
                page.addPartListener(partLstnr);
            }
        }
    }

    /**
     * Adds a perspective listener <code>IPerspectiveListner</code> to the
     * active window of the workbench. It is now possible to track changes in
     * perspective and run actions based on them.
     */
    private void setupPerspectiveListener() {
        // Create listener
        IPerspectiveListener perspectiveLstnr = new IPerspectiveListener() {

            MementoUtil mu = MementoUtilFactory.getMementoUtil();

            public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
                // TODO: this needs refactoring
                // If perspective activated is the BASIC plug-in, then update
                // console with appropriate icons,
                // otherwise remove them all, since they are no longer relevant.
                if (T24BasicPerspectiveFactory.T24_BASIC_PERSPECTIVE_ID.equals(perspective.getId())) {
                    // SIGNON/SIGNOFF icons
                    if (RemoteSessionManager.getInstance().isUserSignedOn()) {
                        LogConsole.updateConsoleToolBar("/icons/signedOnImage.gif", "User Signed On", "signon.id");
                    } else {
                        LogConsole.updateConsoleToolBar("/icons/signedOffImage.gif", "User Signed Off", "signon.id");
                    }
                    // DEBUG icon
                    if (Boolean.parseBoolean(mu.getProperty(PluginConstants.T24_COMPILE_WITH_DEBUG))) {
                        // The user has chosen to allow DEBUG statements in
                        // compilation => add a warning image on the console
                        // The reason for this is to remind users that DEBUG
                        // statements could still be lurking in the code
                        // and there is a risk that they may end up in
                        // production code.
                        LogConsole.updateConsoleToolBar("/icons/debugOptionWarningImage.gif", "Compiling with DEBUG allowed",
                                "debug.id");
                    } else {
                        // The user doesn't allow DEBUG, remove warning image.
                        LogConsole.removeActionFromConsoleToolBar("debug.id");
                    }
                } else {
                    // A perspective other than the BASIC plug-in has been
                    // activated.
                    // Remove all icons associated with BASIC.
                    LogConsole.removeActionFromConsoleToolBar("signon.id");
                    LogConsole.removeActionFromConsoleToolBar("debug.id");
                }
            }

            public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
            }
        };
        // add the listener.
        for (IWorkbenchWindow wbw : PlatformUI.getWorkbench().getWorkbenchWindows()) {
            if (wbw != null) {
                wbw.addPerspectiveListener(perspectiveLstnr);
            }
        }
    }

    /**
     * Set a listener to allow for actions pre/post shutting down eclipse
     * workbench
     */
    private void setupShutdownListener() {
        IWorkbenchListener wbLstnr = new IWorkbenchListener() {

            public void postShutdown(IWorkbench workbench) {
                // EclipseUtil.removeTemporaryProject();
            }

            public boolean preShutdown(IWorkbench workbench, boolean forced) {
                preShutdownCheckLocks();
                disposeRemoteViews();
                return true;
            }
        };
        IWorkbench wb = PlatformUI.getWorkbench();
        wb.addWorkbenchListener(wbLstnr);
    }

    /**
     * Checks whether the user has any locked programs before eclipse shuts
     * down. If there are some, then offer the possibility of unlock them.
     */
    private void preShutdownCheckLocks() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        ViewLocksInServerActionDelegate viewLocks = new ViewLocksInServerActionDelegate();
        // Check whether user has signed on. If not, then display message and
        // leave.
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        if (!sesMgr.isUserSignedOn()) {
            // if user is not signed on, ignore.
            return;
        }
        // Get and display the locks held by the local user in the Server
        ArrayList<String> lockedFiles = viewLocks.getLocksInServer();
        if (!lockedFiles.isEmpty()) {
            ViewLocksInServerDialog dialog = new ViewLocksInServerDialog(window.getShell(), lockedFiles);
            if (dialog.open() != InputDialog.OK) {
                // The user clicked something else other than OK. The execution
                // is finished.
                return;
            }
            // This line is reached if user clicked OK and input validation
            // passed.
            viewLocks.unlockFilesServer(dialog.getFilesSelected());
        }
    }

    /**
     * Some properties need to be reset to default values each time the Plug-in
     * is re-started.
     */
    private void resetProperties() {
        // t24.remote.compile.xml.response holds the results from last single
        // file compilation. Set it to empty.
        mu.updateProperty("t24.remote.compile.xml.response", "");
    }

    private void disposeRemoteViews() {
        RemoteSiteViewManager.getInstance().disposeAllViews();
    }
}
