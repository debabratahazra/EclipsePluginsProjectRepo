package com.zealcore.se.ui.dialogs;

import junit.framework.Assert;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.TestTypeRegistry;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.ui.mock.MockTypePackage;

public final class OpenPlotDialogTest {
    private OpenPlotDialogTest() {

    }

    public static void main(final String[] args) {
        final Display display = new Display();

        try {
            final Shell shell = new Shell(display);

            final MockTypePackage tp = new MockTypePackage();
            final TestTypeRegistry mockTypeRegistry = new TestTypeRegistry();
            mockTypeRegistry.addPackage(tp);
            SeCorePlugin.getDefault().registerService(ITypeRegistry.class,
                    mockTypeRegistry);

            final OpenPlotDialog dlg = new OpenPlotDialog(shell);
            if (dlg.open() == Window.OK) {
                Assert.assertNotNull(dlg.getPlotType());
            }
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
            shell.dispose();
        } catch (final NotImplementedException ex) {
            display.dispose();
            throw ex;
        }
    }

    public static class MyPlottable extends AbstractLogEvent {
        private int value;

        @ZCProperty(name = "Value", plottable = true)
        public int getValue() {
            return value;
        }

    }
}
