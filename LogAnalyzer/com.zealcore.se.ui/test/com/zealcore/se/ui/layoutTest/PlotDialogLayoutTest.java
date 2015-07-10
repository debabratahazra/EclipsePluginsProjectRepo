package com.zealcore.se.ui.layoutTest;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.zealcore.se.ui.dialogs.OpenPlotDialog;

public final class PlotDialogLayoutTest {

    private PlotDialogLayoutTest() {

    }

    /**
     * @param args
     */
    public static void main(final String[] args) {

        final Display display = new Display();
        final Shell shell = new Shell(display);

        shell.setLayout(new FillLayout());

        final OpenPlotDialog dlg = new OpenPlotDialog(shell);
        if (dlg.open() == Window.OK) {
            System.out.println("Pressed ok");
        }

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();

    }

}
