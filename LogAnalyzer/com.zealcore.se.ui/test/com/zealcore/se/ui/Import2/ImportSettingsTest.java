package com.zealcore.se.ui.Import2;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;

import com.zealcore.se.ui.importsettings.ImportSettings;

import util.MockUtil;

public class ImportSettingsTest {

    private static final int SHELL_SIZE = 500;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    public static void main(final String[] args) {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setSize(ImportSettingsTest.SHELL_SIZE,
                ImportSettingsTest.SHELL_SIZE);
        shell.setLayout(new FillLayout());
        final ImportSettings subject = new ImportSettings();

        // com.zealcore.se.ui.export.Settings subject = new
        // com.zealcore.se.ui.export.Settings();

        shell.open();

        final WizardDialog dialog = new WizardDialog(shell, subject);
        dialog.open();

        // while (!shell.isDisposed()) {
        // if (!display.readAndDispatch()) {
        // display.sleep();
        // }
        // }
        display.dispose();
        MockUtil.verifyAll();

    }

}
