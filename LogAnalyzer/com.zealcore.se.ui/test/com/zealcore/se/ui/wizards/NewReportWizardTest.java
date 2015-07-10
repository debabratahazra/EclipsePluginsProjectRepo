package com.zealcore.se.ui.wizards;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.zealcore.se.ui.core.report.IReportContributor;

public final class NewReportWizardTest {

    private NewReportWizardTest() {

    }

    private static final int SHELL_SIZE = 500;

    public static void main(final String[] args) {
        final Display display = new Display();

        final Shell shell = new Shell(display);
        shell.setSize(NewReportWizardTest.SHELL_SIZE,
                NewReportWizardTest.SHELL_SIZE);

        shell.setLayout(new FillLayout());

        final IMocksControl control = EasyMock.createNiceControl();
        final IWorkbench workbench = control.createMock(IWorkbench.class);
        final IWorkbenchWindow window = control
                .createMock(IWorkbenchWindow.class);
        final IWorkbenchPage page = control.createMock(IWorkbenchPage.class);

        final IViewReference vref = control.createMock(IViewReference.class);
        final IViewPartReportContributor contributor = control
                .createMock(IViewPartReportContributor.class);

        final IViewReference vref2 = control.createMock(IViewReference.class);
        final IViewPartReportContributor contributor2 = control
                .createMock(IViewPartReportContributor.class);

        EasyMock.expect(window.getActivePage()).andReturn(page);
        EasyMock.expect(workbench.getActiveWorkbenchWindow()).andReturn(window);
        EasyMock.expect(page.getViewReferences()).andReturn(
                new IViewReference[] { vref, vref2 });

        EasyMock.expect(vref.getView(false)).andReturn(contributor);
        EasyMock.expect(contributor.getTitle()).andReturn("Mocked Contributor");

        EasyMock.expect(vref2.getView(false)).andReturn(contributor2);
        EasyMock.expect(contributor2.getTitle())
                .andReturn("Second Contributor");

        control.replay();

        final NewReportWizard wizard = new NewReportWizard();
        wizard.init(workbench, null);

        final WizardDialog dlg = new WizardDialog(shell, wizard);

        dlg.open();
        shell.dispose();
        // shell.open();

        display.dispose();
    }

    private interface IViewPartReportContributor extends IViewPart,
            IReportContributor {

    }
}
