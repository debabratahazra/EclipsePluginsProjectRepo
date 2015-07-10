package com.zealcore.se.ui.wizards;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.zealcore.se.ui.core.report.HtmlReportGenerator;
import com.zealcore.se.ui.core.report.IReportContributor;

public class NewReportWizard extends Wizard implements INewWizard {
    private static final String WINDOW_TITLE = "New Report";

    private ReportCreationPage reportCreationPage;

    private ReportSelectionPage reportSelectionPage;

    public NewReportWizard() {
        setWindowTitle(NewReportWizard.WINDOW_TITLE);
    }

    @Override
    public boolean performFinish() {
        final HtmlReportGenerator report = new HtmlReportGenerator();
        final Object[] contributors = reportSelectionPage
                .getCheckedContributors();
        for (final Object reportContributor : contributors) {
            ((IReportContributor) reportContributor).fillReport(report);
        }
        report.compileReport();
        final File destinationFolder = new File(reportCreationPage
                .getReportLocation(), reportCreationPage.getReportName());
        destinationFolder.mkdirs();
        final File reportHtmlFile = new File(destinationFolder, "report.htm");
        try {
            Path path = new Path(reportHtmlFile.getPath());
            report.toFile(path);

            // refresh navigator view
            IWorkspaceRoot root = reportCreationPage.getProject().getResource()
                    .getWorkspace().getRoot();
            root.refreshLocal(IResource.DEPTH_INFINITE, null);

            IFile html = root.getFile(new Path(path.toString().replaceFirst(
                    root.getRawLocation().toString(), "")));

            if (html != null && html.exists()) {
                IWorkbenchPage activePage = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                IDE.openEditor(activePage, html);
            } else if (html != null) {
                html.refreshLocal(IResource.DEPTH_INFINITE, null);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final CoreException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void addPages() {
        super.addPages();
        addPage(reportSelectionPage);
        addPage(reportCreationPage);
    }

    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {
        reportSelectionPage = new ReportSelectionPage(workbench);
        reportCreationPage = new ReportCreationPage(selection);
    }
}
