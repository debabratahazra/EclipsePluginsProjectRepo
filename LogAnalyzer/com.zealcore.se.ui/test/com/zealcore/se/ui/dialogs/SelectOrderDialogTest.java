package com.zealcore.se.ui.dialogs;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public class SelectOrderDialogTest {

    private static final String TJOS = "Tjos";

    private static final String NOO = "Noo";

    private static final int SHELL_SIZE = 500;

    @Test
    public void testSelectOrderDialog() {}

    public static void main(final String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setSize(SelectOrderDialogTest.SHELL_SIZE,
                SelectOrderDialogTest.SHELL_SIZE);

        shell.setLayout(new FillLayout());

        final Composite parent = new Canvas(shell, SWT.NULL
                | SWT.DOUBLE_BUFFERED);

        parent.setBackground(display.getSystemColor(SWT.COLOR_WHITE));

        // COde goes here

        // String[] stuff = new String[] { "HEJ", "HOPP", };
        final SelectOrderDialog<String> dlg = SelectOrderDialog
                .newInstance(shell);
        // dlg.setTitle("Save Resources");

        dlg.setInitialElements(Arrays.asList("Hej", SelectOrderDialogTest.TJOS,
                "Foo", "Bar", SelectOrderDialogTest.NOO));
        dlg.setInitialSelection(Arrays.asList(SelectOrderDialogTest.NOO,
                SelectOrderDialogTest.TJOS));
        shell.open();
        dlg.open();
        System.out.println(dlg.getSelectedItems());

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

}
