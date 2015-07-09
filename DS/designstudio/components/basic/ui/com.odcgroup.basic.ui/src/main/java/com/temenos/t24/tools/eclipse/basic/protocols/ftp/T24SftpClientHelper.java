package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;

public class T24SftpClientHelper {

    public static boolean checkWithUser(String message) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window != null) {
            T24MessageDialog dialog = new T24MessageDialog(window.getShell(), "Security Alert", message, MessageDialog.WARNING);
            if (dialog.open() == InputDialog.CANCEL) {
                return false;
            }
            return true;
        } else {
            final T24MessageDialogThread dialogThread = new T24MessageDialogThread(message);
            Display.getDefault().syncExec(new Runnable() {

                public void run() {
                    Thread thread = new Thread(dialogThread);
                    thread.start();
                    while (dialogThread.okPressed == null) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            Boolean result = dialogThread.okPressed;
            return result == null ? false : result;
        }
    }

    public static String getKnownHostsLocation() {
        T24BasicPlugin plugin = T24BasicPlugin.getDefault();
        IPath stateLocation = plugin.getStateLocation();
        String knownHostsLocation = stateLocation.toOSString() + "/known_hosts";
        File file = new File(knownHostsLocation);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return knownHostsLocation;
    }

    private static class T24MessageDialogThread implements Runnable {

        private Boolean okPressed = null;
        private String message = "";

        public T24MessageDialogThread(String message) {
            this.message = message;
        }

        public void run() {
            Display display = PlatformUI.createDisplay();
            final Shell shell = new Shell(display);
            T24MessageDialog dialog = new T24MessageDialog(shell, "Security Alert", message, MessageDialog.WARNING);
            if (dialog.open() != InputDialog.OK) {
                // The user clicked something else other than OK.
                okPressed = false;
            } else {
                okPressed = true;
            }
        }
    }
}
