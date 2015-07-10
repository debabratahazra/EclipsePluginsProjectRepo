package com.zealcore.se.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;

import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.core.report.IReportContributor;
import com.zealcore.se.ui.editors.ILogsetBrowser;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.util.IconListLabelProvider;

public class ReportSelectionPage extends WizardPage {

    private List<IReportContributor> contributors;

    private static final String NAME = "ReportSelectionPage";

    private static final String TITLE = "Report";

    private static final String DESCRIPTION = "Select contributors";

    private CheckboxTableViewer checkboxTableViewer;

    public ReportSelectionPage(final IWorkbench workbench) {
        super(ReportSelectionPage.NAME, ReportSelectionPage.TITLE, IconManager
                .getImageDescriptor(IconManager.REPORT_MEDIUM));
        setDescription(ReportSelectionPage.DESCRIPTION);
        initializeContributors(workbench);
    }

    public void createControl(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());
        setControl(container);

        checkboxTableViewer = CheckboxTableViewer.newCheckList(container,
                SWT.BORDER);
        final Table table = checkboxTableViewer.getTable();
        final FormData fdtable = new FormData();
        fdtable.bottom = new FormAttachment(100, -5);
        fdtable.right = new FormAttachment(100, -5);
        fdtable.left = new FormAttachment(0, 5);
        fdtable.top = new FormAttachment(0, 5);
        table.setLayoutData(fdtable);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        checkboxTableViewer.setContentProvider(new ArrayContentProvider());
        checkboxTableViewer.setLabelProvider(new IconListLabelProvider() {
            @Override
            public String getText(final Object element) {
                if (element instanceof IWorkbenchPart) {
                    final IWorkbenchPart part = (IWorkbenchPart) element;
                    return part.getTitle();
                }
                if (element instanceof ILogsetBrowser) {
                    final ILogsetBrowser browser = (ILogsetBrowser) element;
                    return browser.getName();
                }
                return super.getText(element);
            }

            @Override
            public Image getImage(final Object element) {
                if (element instanceof IWorkbenchPart) {
                    final IWorkbenchPart part = (IWorkbenchPart) element;
                    return part.getTitleImage();
                }
                if (element instanceof ILogsetBrowser) {
                    final ILogsetBrowser browser = (ILogsetBrowser) element;
                    return browser.getImageDescriptor().createImage();
                }
                return super.getImage(element);
            }
        });

        checkboxTableViewer.addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(final CheckStateChangedEvent event) {
                ReportSelectionPage.this.getContainer().updateButtons();
            }

        });
        checkboxTableViewer.setInput(contributors);
        checkboxTableViewer.setAllChecked(true);
    }

    private void initializeContributors(final IWorkbench workbench) {
        contributors = new ArrayList<IReportContributor>();
        final IViewReference[] viewReferences = workbench
                .getActiveWorkbenchWindow().getActivePage().getViewReferences();
        for (final IViewReference ref : viewReferences) {
            final IViewPart view = ref.getView(false);
            if (view instanceof IReportContributor) {
                contributors.add((IReportContributor) view);
            }
        }
        final IEditorReference[] editorReferences = workbench
                .getActiveWorkbenchWindow().getActivePage()
                .getEditorReferences();
        for (final IEditorReference ref : editorReferences) {
            final IEditorPart editor = ref.getEditor(false);
            if (editor instanceof LogsetEditor) {
                final ILogsetBrowser browser = ((LogsetEditor) editor)
                        .getLogsetBrowser();
                if (browser instanceof IReportContributor) {
                    contributors.add((IReportContributor) browser);
                }
            }
        }
    }

    @Override
    public boolean isPageComplete() {
        return getCheckedContributors().length > 0;
    }

    public Object[] getCheckedContributors() {
        return checkboxTableViewer.getCheckedElements();
    }
}
