package com.zealcore.se.iw.wizard;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author cafa
 * 
 */
public final class ConfigureImportWizardTest {

    private static final int SHELL_SIZE = 100;

    private ConfigureImportWizardTest() {}

    public static void main(final String[] args) {

        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setSize(ConfigureImportWizardTest.SHELL_SIZE,
                ConfigureImportWizardTest.SHELL_SIZE);

        // File file = new File("mysecond.log");
        // File file = new File("printspoolNormal.log");
        final File file = new File("printspoolEasy.log");

        final ConfigureImportWizard wizard = new ConfigureImportWizard(file,null);
        final WizardDialog dialog = new WizardDialog(shell, wizard);
        if (dialog.open() == Window.OK) {
            MessageDialog.openWarning(null, "Warning", "Not Implemented yet");
        }

        display.dispose();

    }
}
