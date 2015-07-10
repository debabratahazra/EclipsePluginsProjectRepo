package com.zealcore.se.rcp;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.ChooseWorkspaceData;
import org.eclipse.ui.internal.ide.ChooseWorkspaceDialog;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IPlatformRunnable {

    private static final String E_TITLE_INVALID_WORKSPACE = "Invalid workspace";

    private static final String E_MSG_INVALID_WORKSPACE = "The workspace you selected was invalid";

    private static final String E_TITLE_WS_MANDATORY = "Workspace is mandatory";

    private static final String E_MSG_WS_MANDATORY = "Workspace is not set";

    private static final String E_TITLE_INCOMPATIBLE_JVM = "Incompatible JVM";

    private static final String E_MSG_INCOMPATIBLE_JVM = "This program requires version %1$s of the JVM";

    private static final String E_TITLE_WS_LOCK_ERROR = "Workspace lock error";

    private static final String E_MSG_WS_LOCK_ERROR = "Could not lock workspace";

    private static final String E_TITLE_WS_UNAVAILABLE = "Workspace could not be locked";

    private static final String E_MSG_WS_UNAVAILABLE = "Could not launch the product because the associated workspace is currently in use";

    private static final String E_TITLE_WS_CANNOT_SET = "Illegal Workspace";

    private static final String E_MSG_WS_CANNOT_SET = "Workspace can not be set";

    private static final int MIN_JVM_VERSION_MAJOR = 1;

    private static final int MIN_JVM_VERSION_MINOR = 5;

    private static final int MIN_JVM_VERSION_SERVICE = 0;

    public Object run(final Object args) throws Exception {
        final Display display = PlatformUI.createDisplay();
        final Shell shell = new Shell(display, SWT.ON_TOP);
        try {
            // originally copied from IDEApplication
            if (!checkJavaRuntimeVersion(shell)) {
                Platform.endSplash();
                return IPlatformRunnable.EXIT_OK;
            }
            // originally copied from IDEApplication
            if (!checkInstanceLocation(shell)) {
                Platform.endSplash();
                return IPlatformRunnable.EXIT_OK;
            }
            final int returnCode = PlatformUI.createAndRunWorkbench(display,
                    new ApplicationWorkbenchAdvisor());
            if (returnCode == PlatformUI.RETURN_RESTART) {
                return IPlatformRunnable.EXIT_RESTART;
            }
            return IPlatformRunnable.EXIT_OK;
        } finally {
            shell.dispose();
            display.dispose();
        }
    }

    private boolean checkInstanceLocation(final Shell shell) {
        // -data @none was specified but an ide requires workspace
        final Location instanceLoc = Platform.getInstanceLocation();
        if (instanceLoc == null) {
            MessageDialog.openError(shell, Application.E_TITLE_WS_MANDATORY,
                    Application.E_MSG_WS_MANDATORY);
            return false;
        }
        // -data "/valid/path", workspace already set
        if (instanceLoc.isSet()) {
            if (!checkValidWorkspace(instanceLoc.getURL())) {
                return false;
            }
            try {
                if (instanceLoc.lock()) {
                    return true;
                }
            } catch (final IOException e) {}

            MessageDialog.openError(shell, Application.E_TITLE_WS_LOCK_ERROR,
                    Application.E_MSG_WS_LOCK_ERROR);
            return false;
        }

        // -data @noDefault or -data not specified, prompt and set
        boolean force = false;
        while (true) {
            URL workspaceUrl = null;
            final ChooseWorkspaceData launchData = new ChooseWorkspaceData(
                    instanceLoc.getDefault());
            workspaceUrl = promptForWorkspace(shell, launchData, force);
            if (workspaceUrl == null) {
                return false;
            }
            force = true;
            try {
                if (instanceLoc.setURL(workspaceUrl, true)) {
                    launchData.writePersistedData();
                    return true;
                }
            } catch (final IllegalStateException e) {
                MessageDialog.openError(shell,
                        Application.E_TITLE_WS_CANNOT_SET,
                        Application.E_MSG_WS_CANNOT_SET);
                return false;
            }
            // by this point it has been determined that the workspace
            // is already in use -- force the user to choose again
            MessageDialog.openError(shell, Application.E_TITLE_WS_UNAVAILABLE,
                    Application.E_MSG_WS_UNAVAILABLE);
        }
    }

    private boolean checkValidWorkspace(final URL url) {
        final File path = new File(url.getPath());
        return path.exists() && path.isDirectory() && path.canWrite();
    }

    private boolean checkJavaRuntimeVersion(final Shell shell) {
        try {
            if (Application.isCompatibleVersion(System
                    .getProperty("java.version"))) {
                return true;
            }
            // build the requirement into a version string
            final String reqVersion = Integer
                    .toString(Application.MIN_JVM_VERSION_MAJOR)
                    + '.'
                    + Application.MIN_JVM_VERSION_MINOR
                    + '.'
                    + Application.MIN_JVM_VERSION_SERVICE;
            MessageDialog.openError(shell,
                    Application.E_TITLE_INCOMPATIBLE_JVM, String.format(
                            Application.E_MSG_INCOMPATIBLE_JVM, reqVersion));
            return false;
        } catch (final SecurityException e) {
            // If the security manager won't allow us to get the system
            // property, continue for now and let things fail later on
            // their own if necessary.
            return true;
        } catch (final NumberFormatException e) {
            // If the version string was in a format that we don't
            // understand, continue and let things fail later on their own if
            // necessary.
            return true;
        }
    }

    private static boolean isCompatibleVersion(final String vmVersion) {
        if (vmVersion == null) {
            return false;
        }

        final StringTokenizer tokenizer = new StringTokenizer(vmVersion, " ._");
        try {
            // make sure the running vm's major is >= the requirement
            if (!tokenizer.hasMoreTokens()) {
                return true;
            }
            final int major = Integer.parseInt(tokenizer.nextToken());
            if (major != Application.MIN_JVM_VERSION_MAJOR) {
                return major > Application.MIN_JVM_VERSION_MINOR;
            }

            // make sure the running vm's minor is >= the requirement
            if (!tokenizer.hasMoreTokens()) {
                return true;
            }
            final int minor = Integer.parseInt(tokenizer.nextToken());
            if (minor != Application.MIN_JVM_VERSION_MINOR) {
                return minor > Application.MIN_JVM_VERSION_MINOR;
            }

            // make sure the running vm's service is >= the requirement
            if (!tokenizer.hasMoreTokens()) {
                return true;
            }
            final int service = Integer.parseInt(tokenizer.nextToken());
            return service >= Application.MIN_JVM_VERSION_SERVICE;
        } catch (final SecurityException e) {
            // If the security manager won't allow us to get the system
            // property, continue for now and let things fail later on
            // their own if necessary.
            return true;
        } catch (final NumberFormatException e) {
            // If the version string was in a format that we don't
            // understand,
            // continue and let things fail later on their own if necessary.
            return true;
        }
    }

    private URL promptForWorkspace(final Shell shell,
            final ChooseWorkspaceData launchData, final boolean force) {
        URL url = null;

        new ChooseWorkspaceDialog(shell, launchData, false, true).prompt(force);
        final String instancePath = launchData.getSelection();
        if (instancePath == null) {
            return null;
        }

        final File workspace = new File(instancePath);
        if (!workspace.exists()) {
            workspace.mkdir();
        }

        try {
            url = file2URL(workspace);
        } catch (final MalformedURLException e) {
            MessageDialog.openError(shell,
                    Application.E_TITLE_INVALID_WORKSPACE,
                    Application.E_MSG_INVALID_WORKSPACE);
        }
        return url;
    }

    private URL file2URL(final File file) throws MalformedURLException {
        // Don't use File.toURL() since it adds a leading slash that
        // Platform does not handle properly.
        return new URL("file", null, file.getAbsolutePath().replace(
                File.separatorChar, '/'));
    }
}
